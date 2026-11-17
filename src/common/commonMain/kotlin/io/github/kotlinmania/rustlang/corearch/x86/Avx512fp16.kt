// port-lint: source x86/avx512fp16.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.SimdShuffleIdx
import io.github.kotlinmania.rustlang.corearch.simd.I16x16
import io.github.kotlinmania.rustlang.corearch.simd.I16x32
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x16
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I32x8
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I64x4
import io.github.kotlinmania.rustlang.corearch.simd.I64x8
import io.github.kotlinmania.rustlang.corearch.simd.Simd
import io.github.kotlinmania.rustlang.corearch.simd.U16x16
import io.github.kotlinmania.rustlang.corearch.simd.U16x32
import io.github.kotlinmania.rustlang.corearch.simd.U16x8
import io.github.kotlinmania.rustlang.corearch.simd.U32x16
import io.github.kotlinmania.rustlang.corearch.simd.U32x4
import io.github.kotlinmania.rustlang.corearch.simd.U32x8
import io.github.kotlinmania.rustlang.corearch.simd.U64x2
import io.github.kotlinmania.rustlang.corearch.simd.U64x4
import io.github.kotlinmania.rustlang.corearch.simd.U64x8

/**
 * Set packed half-precision (16-bit) floating-point elements in dst with the supplied values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_set_ph)
 */
public fun mmSetPh(e7: F16, e6: F16, e5: F16, e4: F16, e3: F16, e2: F16, e1: F16, e0: F16): M128h = m128h(listOf(e0, e1, e2, e3, e4, e5, e6, e7))

/**
 * Set packed half-precision (16-bit) floating-point elements in dst with the supplied values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_set_ph)
 */
public fun mm256SetPh(e15: F16, e14: F16, e13: F16, e12: F16, e11: F16, e10: F16, e9: F16, e8: F16, e7: F16, e6: F16, e5: F16, e4: F16, e3: F16, e2: F16, e1: F16, e0: F16): M256h = m256h(listOf(e0, e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15))

/**
 * Set packed half-precision (16-bit) floating-point elements in dst with the supplied values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_set_ph)
 */
public fun mm512SetPh(e31: F16, e30: F16, e29: F16, e28: F16, e27: F16, e26: F16, e25: F16, e24: F16, e23: F16, e22: F16, e21: F16, e20: F16, e19: F16, e18: F16, e17: F16, e16: F16, e15: F16, e14: F16, e13: F16, e12: F16, e11: F16, e10: F16, e9: F16, e8: F16, e7: F16, e6: F16, e5: F16, e4: F16, e3: F16, e2: F16, e1: F16, e0: F16): M512h = m512h(listOf(e0, e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15, e16, e17, e18, e19, e20, e21, e22, e23, e24, e25, e26, e27, e28, e29, e30, e31))

/**
 * Copy half-precision (16-bit) floating-point elements from a to the lower element of dst and zero
 * the upper 7 elements.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_set_sh)
 */
public fun mmSetSh(a: F16): M128h = m128h(listOf(a, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0))

/**
 * Broadcast the half-precision (16-bit) floating-point value a to all elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_set1_ph)
 */
public fun mmSet1Ph(a: F16): M128h = transmute(Simd<F16>(List(8) { a }))

/**
 * Broadcast the half-precision (16-bit) floating-point value a to all elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_set1_ph)
 */
public fun mm256Set1Ph(a: F16): M256h = transmute(Simd<F16>(List(16) { a }))

/**
 * Broadcast the half-precision (16-bit) floating-point value a to all elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_set1_ph)
 */
public fun mm512Set1Ph(a: F16): M512h = transmute(Simd<F16>(List(32) { a }))

/**
 * Set packed half-precision (16-bit) floating-point elements in dst with the supplied values in reverse order.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_setr_ph)
 */
public fun mmSetrPh(e0: F16, e1: F16, e2: F16, e3: F16, e4: F16, e5: F16, e6: F16, e7: F16): M128h = m128h(listOf(e0, e1, e2, e3, e4, e5, e6, e7))

/**
 * Set packed half-precision (16-bit) floating-point elements in dst with the supplied values in reverse order.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_setr_ph)
 */
public fun mm256SetrPh(e0: F16, e1: F16, e2: F16, e3: F16, e4: F16, e5: F16, e6: F16, e7: F16, e8: F16, e9: F16, e10: F16, e11: F16, e12: F16, e13: F16, e14: F16, e15: F16): M256h = m256h(listOf(e0, e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15))

/**
 * Set packed half-precision (16-bit) floating-point elements in dst with the supplied values in reverse order.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_setr_ph)
 */
public fun mm512SetrPh(e0: F16, e1: F16, e2: F16, e3: F16, e4: F16, e5: F16, e6: F16, e7: F16, e8: F16, e9: F16, e10: F16, e11: F16, e12: F16, e13: F16, e14: F16, e15: F16, e16: F16, e17: F16, e18: F16, e19: F16, e20: F16, e21: F16, e22: F16, e23: F16, e24: F16, e25: F16, e26: F16, e27: F16, e28: F16, e29: F16, e30: F16, e31: F16): M512h = m512h(listOf(e0, e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15, e16, e17, e18, e19, e20, e21, e22, e23, e24, e25, e26, e27, e28, e29, e30, e31))

/**
 * Return vector of type __m128h with all elements set to zero.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_setzero_ph)
 */
public fun mmSetzeroPh(): M128h = transmute(Simd<F16>(List(8) { F16.fromFloat(0.0f) }))

/**
 * Return vector of type __m256h with all elements set to zero.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_setzero_ph)
 */
public fun mm256SetzeroPh(): M256h = Simd<F16>(List(16) { F16.fromFloat(0.0f) }).asM256h()

/**
 * Return vector of type __m512h with all elements set to zero.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_setzero_ph)
 */
public fun mm512SetzeroPh(): M512h = Simd<F16>(List(32) { F16.fromFloat(0.0f) }).asM512h()

/**
 * Return vector of type `__m128h` with indetermination elements.
 * Despite using the word "undefined" (following Intel's naming scheme), this non-deterministically
 * picks some valid value and is not equivalent to [`mem.MaybeUninit`](mem.MaybeUninit).
 * In practice, this is typically equivalent to [`mem.zeroed`](mem.zeroed).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_undefined_ph)
 */
public fun mmUndefinedPh(): M128h = Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h()

/**
 * Return vector of type `__m256h` with indetermination elements.
 * Despite using the word "undefined" (following Intel's naming scheme), this non-deterministically
 * picks some valid value and is not equivalent to [`mem.MaybeUninit`](mem.MaybeUninit).
 * In practice, this is typically equivalent to [`mem.zeroed`](mem.zeroed).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_undefined_ph)
 */
public fun mm256UndefinedPh(): M256h = Simd<F16>(List(16) { F16.fromFloat(0.0f) }).asM256h()

/**
 * Return vector of type `__m512h` with indetermination elements.
 * Despite using the word "undefined" (following Intel's naming scheme), this non-deterministically
 * picks some valid value and is not equivalent to [`mem.MaybeUninit`](mem.MaybeUninit).
 * In practice, this is typically equivalent to [`mem.zeroed`](mem.zeroed).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_undefined_ph)
 */
public fun mm512UndefinedPh(): M512h = Simd<F16>(List(32) { F16.fromFloat(0.0f) }).asM512h()

/**
 * Cast vector of type `__m128d` to type `__m128h`. This intrinsic is only used for compilation and
 * does not generate any instructions, thus it has zero latency.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_castpd_ph)
 */
public fun mmCastpdPh(a: M128d): M128h = transmuteWithLayouts<M128d, M128h>(a, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128h>())

/**
 * Cast vector of type `__m256d` to type `__m256h`. This intrinsic is only used for compilation and
 * does not generate any instructions, thus it has zero latency.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_castpd_ph)
 */
public fun mm256CastpdPh(a: M256d): M256h = transmuteWithLayouts<M256d, M256h>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256h>())

/**
 * Cast vector of type `__m512d` to type `__m512h`. This intrinsic is only used for compilation and
 * does not generate any instructions, thus it has zero latency.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_castpd_ph)
 */
public fun mm512CastpdPh(a: M512d): M512h = transmuteWithLayouts<M512d, M512h>(a, UnresolvedTypeLayout<M512d>(), UnresolvedTypeLayout<M512h>())

/**
 * Cast vector of type `__m128h` to type `__m128d`. This intrinsic is only used for compilation and
 * does not generate any instructions, thus it has zero latency.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_castph_pd)
 */
public fun mmCastphPd(a: M128h): M128d = transmuteWithLayouts<M128h, M128d>(a, UnresolvedTypeLayout<M128h>(), UnresolvedTypeLayout<M128d>())

/**
 * Cast vector of type `__m256h` to type `__m256d`. This intrinsic is only used for compilation and
 * does not generate any instructions, thus it has zero latency.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_castph_pd)
 */
public fun mm256CastphPd(a: M256h): M256d = transmuteWithLayouts<M256h, M256d>(a, UnresolvedTypeLayout<M256h>(), UnresolvedTypeLayout<M256d>())

/**
 * Cast vector of type `__m512h` to type `__m512d`. This intrinsic is only used for compilation and
 * does not generate any instructions, thus it has zero latency.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_castph_pd)
 */
public fun mm512CastphPd(a: M512h): M512d = transmuteWithLayouts<M512h, M512d>(a, UnresolvedTypeLayout<M512h>(), UnresolvedTypeLayout<M512d>())

/**
 * Cast vector of type `__m128` to type `__m128h`. This intrinsic is only used for compilation and
 * does not generate any instructions, thus it has zero latency.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_castps_ph)
 */
public fun mmCastpsPh(a: M128): M128h = transmuteWithLayouts<M128, M128h>(a, UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128h>())

/**
 * Cast vector of type `__m256` to type `__m256h`. This intrinsic is only used for compilation and
 * does not generate any instructions, thus it has zero latency.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_castps_ph)
 */
public fun mm256CastpsPh(a: M256): M256h = transmuteWithLayouts<M256, M256h>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256h>())

/**
 * Cast vector of type `__m512` to type `__m512h`. This intrinsic is only used for compilation and
 * does not generate any instructions, thus it has zero latency.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_castps_ph)
 */
public fun mm512CastpsPh(a: M512): M512h = transmuteWithLayouts<M512, M512h>(a, UnresolvedTypeLayout<M512>(), UnresolvedTypeLayout<M512h>())

/**
 * Cast vector of type `__m128h` to type `__m128`. This intrinsic is only used for compilation and
 * does not generate any instructions, thus it has zero latency.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_castph_ps)
 */
public fun mmCastphPs(a: M128h): M128 = transmuteWithLayouts<M128h, M128>(a, UnresolvedTypeLayout<M128h>(), UnresolvedTypeLayout<M128>())

/**
 * Cast vector of type `__m256h` to type `__m256`. This intrinsic is only used for compilation and
 * does not generate any instructions, thus it has zero latency.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_castph_ps)
 */
public fun mm256CastphPs(a: M256h): M256 = transmuteWithLayouts<M256h, M256>(a, UnresolvedTypeLayout<M256h>(), UnresolvedTypeLayout<M256>())

/**
 * Cast vector of type `__m512h` to type `__m512`. This intrinsic is only used for compilation and
 * does not generate any instructions, thus it has zero latency.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_castph_ps)
 */
public fun mm512CastphPs(a: M512h): M512 = transmuteWithLayouts<M512h, M512>(a, UnresolvedTypeLayout<M512h>(), UnresolvedTypeLayout<M512>())

/**
 * Cast vector of type `__m128i` to type `__m128h`. This intrinsic is only used for compilation and
 * does not generate any instructions, thus it has zero latency.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_castsi128_ph)
 */
public fun mmCastsi128Ph(a: M128i): M128h = transmuteWithLayouts<M128i, M128h>(a, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M128h>())

/**
 * Cast vector of type `__m256i` to type `__m256h`. This intrinsic is only used for compilation and
 * does not generate any instructions, thus it has zero latency.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_castsi256_ph)
 */
public fun mm256Castsi256Ph(a: M256i): M256h = transmuteWithLayouts<M256i, M256h>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<M256h>())

/**
 * Cast vector of type `__m512i` to type `__m512h`. This intrinsic is only used for compilation and
 * does not generate any instructions, thus it has zero latency.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_castsi512_ph)
 */
public fun mm512Castsi512Ph(a: M512i): M512h = transmuteWithLayouts<M512i, M512h>(a, UnresolvedTypeLayout<M512i>(), UnresolvedTypeLayout<M512h>())

/**
 * Cast vector of type `__m128h` to type `__m128i`. This intrinsic is only used for compilation and
 * does not generate any instructions, thus it has zero latency.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_castph_si128)
 */
public fun mmCastphSi128(a: M128h): M128i = transmuteWithLayouts<M128h, M128i>(a, UnresolvedTypeLayout<M128h>(), UnresolvedTypeLayout<M128i>())

/**
 * Cast vector of type `__m256h` to type `__m256i`. This intrinsic is only used for compilation and
 * does not generate any instructions, thus it has zero latency.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_castph_si256)
 */
public fun mm256CastphSi256(a: M256h): M256i = transmuteWithLayouts<M256h, M256i>(a, UnresolvedTypeLayout<M256h>(), UnresolvedTypeLayout<M256i>())

/**
 * Cast vector of type `__m512h` to type `__m512i`. This intrinsic is only used for compilation and
 * does not generate any instructions, thus it has zero latency.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_castph_si512)
 */
public fun mm512CastphSi512(a: M512h): M512i = transmuteWithLayouts<M512h, M512i>(a, UnresolvedTypeLayout<M512h>(), UnresolvedTypeLayout<M512i>())

/**
 * Cast vector of type `__m256h` to type `__m128h`. This intrinsic is only used for compilation and
 * does not generate any instructions, thus it has zero latency.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_castph256_ph128)
 */
public fun mm256Castph256Ph128(a: M256h): M128h = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u)); _simdArg0 }

/**
 * Cast vector of type `__m512h` to type `__m128h`. This intrinsic is only used for compilation and
 * does not generate any instructions, thus it has zero latency.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_castph512_ph128)
 */
public fun mm512Castph512Ph128(a: M512h): M128h = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u)); _simdArg0 }

/**
 * Cast vector of type `__m512h` to type `__m256h`. This intrinsic is only used for compilation and
 * does not generate any instructions, thus it has zero latency.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_castph512_ph256)
 */
public fun mm512Castph512Ph256(a: M512h): M256h = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u)); _simdArg0 }

/**
 * Cast vector of type `__m128h` to type `__m256h`. The upper 8 elements of the result are undefined.
 * In practice, the upper elements are zeroed. This intrinsic can generate the `vzeroupper` instruction,
 * but most of the time it does not generate any instructions.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_castph128_ph256)
 */
public fun mm256Castph128Ph256(a: M128h): M256h = run { val _simdArg0 = a; val _simdArg1 = mmUndefinedPh(); val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u)); _simdArg0 }

/**
 * Cast vector of type `__m128h` to type `__m512h`. The upper 24 elements of the result are undefined.
 * In practice, the upper elements are zeroed. This intrinsic can generate the `vzeroupper` instruction,
 * but most of the time it does not generate any instructions.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_castph128_ph512)
 */
public fun mm512Castph128Ph512(a: M128h): M512h = run { val _simdArg0 = a; val _simdArg1 = mmUndefinedPh(); val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u)); _simdArg0 }

/**
 * Cast vector of type `__m256h` to type `__m512h`. The upper 16 elements of the result are undefined.
 * In practice, the upper elements are zeroed. This intrinsic can generate the `vzeroupper` instruction,
 * but most of the time it does not generate any instructions.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_castph256_ph512)
 */
public fun mm512Castph256Ph512(a: M256h): M512h = run { val _simdArg0 = a; val _simdArg1 = mm256UndefinedPh(); val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u)); _simdArg0 }

/**
 * Cast vector of type `__m256h` to type `__m128h`. The upper 8 elements of the result are zeroed.
 * This intrinsic can generate the `vzeroupper` instruction, but most of the time it does not generate
 * any instructions.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_zextph128_ph256)
 */
public fun mm256Zextph128Ph256(a: M128h): M256h = run { val _simdArg0 = a; val _simdArg1 = mmSetzeroPh(); val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u)); _simdArg0 }

/**
 * Cast vector of type `__m256h` to type `__m512h`. The upper 16 elements of the result are zeroed.
 * This intrinsic can generate the `vzeroupper` instruction, but most of the time it does not generate
 * any instructions.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_zextph256_ph512)
 */
public fun mm512Zextph256Ph512(a: M256h): M512h = run { val _simdArg0 = a; val _simdArg1 = mm256SetzeroPh(); val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u)); _simdArg0 }

/**
 * Cast vector of type `__m128h` to type `__m512h`. The upper 24 elements of the result are zeroed.
 * This intrinsic can generate the `vzeroupper` instruction, but most of the time it does not generate
 * any instructions.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_zextph128_ph512)
 */
public fun mm512Zextph128Ph512(a: M128h): M512h = run { val _simdArg0 = a; val _simdArg1 = mmSetzeroPh(); val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u)); _simdArg0 }

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b based on the comparison
 * operand specified by imm8, and store the results in mask vector k.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cmp_ph_mask)
 */
public fun mmCmpPhMask(a: M128h, b: M128h, imm5: Int): Mmask8 = mmMaskCmpPhMask((0u.inv()).toUByte(), a, b, imm5)

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b based on the comparison
 * operand specified by imm8, and store the results in mask vector k using zeromask k (elements are
 * zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cmp_ph_mask)
 */
public fun mmMaskCmpPhMask(k1: Mmask8, a: M128h, b: M128h, imm5: Int): Mmask8 =
    run {
        run {
            check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
        }
        vcmpph128(a, b, imm5, k1)
    }

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b based on the comparison
 * operand specified by imm8, and store the results in mask vector k.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cmp_ph_mask)
 */
public fun mm256CmpPhMask(a: M256h, b: M256h, imm5: Int): Mmask16 = mm256MaskCmpPhMask((0u.inv()).toUShort(), a, b, imm5)

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b based on the comparison
 * operand specified by imm8, and store the results in mask vector k using zeromask k (elements are
 * zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_cmp_ph_mask)
 */
public fun mm256MaskCmpPhMask(k1: Mmask16, a: M256h, b: M256h, imm5: Int): Mmask16 =
    run {
        run {
            check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
        }
        vcmpph256(a, b, imm5, k1)
    }

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b based on the comparison
 * operand specified by imm8, and store the results in mask vector k.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cmp_ph_mask)
 */
public fun mm512CmpPhMask(a: M512h, b: M512h, imm5: Int): Mmask32 = mm512MaskCmpPhMask(0u.inv(), a, b, imm5)

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b based on the comparison
 * operand specified by imm8, and store the results in mask vector k using zeromask k (elements are
 * zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cmp_ph_mask)
 */
public fun mm512MaskCmpPhMask(k1: Mmask32, a: M512h, b: M512h, imm5: Int): Mmask32 = mm512MaskCmpRoundPhMask<MmFroundCurDirection>(k1, a, b, imm5)

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b based on the comparison
 * operand specified by imm8, and store the results in mask vector k.
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cmp_round_ph_mask)
 */
public fun mm512CmpRoundPhMask(a: M512h, b: M512h, imm5: Int, sae: Int): Mmask32 = mm512MaskCmpRoundPhMask(0u.inv(), a, b, imm5, sae)

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b based on the comparison
 * operand specified by imm8, and store the results in mask vector k using zeromask k (elements are
 * zeroed out when the corresponding mask bit is not set).
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cmp_round_ph_mask)
 */
public fun mm512MaskCmpRoundPhMask(k1: Mmask32, a: M512h, b: M512h, imm5: Int, sae: Int): Mmask32 =
    run {
        run {
            check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
        }
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        vcmpph512(a, b, imm5, k1, sae)
    }

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b based on the comparison
 * operand specified by imm8, and store the result in mask vector k. Exceptions can be suppressed by
 * passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cmp_round_sh_mask)
 */
public fun mmCmpRoundShMask(a: M128h, b: M128h, imm5: Int, sae: Int): Mmask8 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mmMaskCmpRoundShMask(0xffu, a, b, imm5, sae)
}

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b based on the comparison
 * operand specified by imm8, and store the result in mask vector k using zeromask k1. Exceptions can be
 * suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cmp_round_sh_mask)
 */
public fun mmMaskCmpRoundShMask(k1: Mmask8, a: M128h, b: M128h, imm5: Int, sae: Int): Mmask8 =
    run {
        run {
            check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
        }
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        vcmpsh(a, b, imm5, k1, sae)
    }

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b based on the comparison
 * operand specified by imm8, and store the result in mask vector k.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cmp_sh_mask)
 */
public fun mmCmpShMask(a: M128h, b: M128h, imm5: Int): Mmask8 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return mmCmpRoundShMask<MmFroundCurDirection>(a, b, imm5)
}

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b based on the comparison
 * operand specified by imm8, and store the result in mask vector k using zeromask k1.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cmp_sh_mask)
 */
public fun mmMaskCmpShMask(k1: Mmask8, a: M128h, b: M128h, imm5: Int): Mmask8 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return mmMaskCmpRoundShMask<MmFroundCurDirection>(k1, a, b, imm5)
}

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b based on the comparison
 * operand specified by imm8, and return the boolean result (0 or 1).
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_comi_round_sh)
 */
public fun mmComiRoundSh(a: M128h, b: M128h, imm5: Int, sae: Int): Int =
    run {
        run {
            check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
        }
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        vcomish(a, b, imm5, sae)
    }

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b based on the comparison
 * operand specified by imm8, and return the boolean result (0 or 1).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_comi_sh)
 */
public fun mmComiSh(a: M128h, b: M128h, imm5: Int): Int {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return mmComiRoundSh<MmFroundCurDirection>(a, b, imm5)
}

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b for equality, and return
 * the boolean result (0 or 1).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_comieq_sh)
 */
public fun mmComieqSh(a: M128h, b: M128h): Int = mmComiSh<CmpEqOs>(a, b)

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b for greater-than-or-equal,
 * and return the boolean result (0 or 1).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_comige_sh)
 */
public fun mmComigeSh(a: M128h, b: M128h): Int = mmComiSh<CmpGeOs>(a, b)

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b for greater-than, and return
 * the boolean result (0 or 1).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_comigt_sh)
 */
public fun mmComigtSh(a: M128h, b: M128h): Int = mmComiSh<CmpGtOs>(a, b)

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b for less-than-or-equal, and
 * return the boolean result (0 or 1).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_comile_sh)
 */
public fun mmComileSh(a: M128h, b: M128h): Int = mmComiSh<CmpLeOs>(a, b)

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b for less-than, and return
 * the boolean result (0 or 1).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_comilt_sh)
 */
public fun mmComiltSh(a: M128h, b: M128h): Int = mmComiSh<CmpLtOs>(a, b)

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b for not-equal, and return
 * the boolean result (0 or 1).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_comineq_sh)
 */
public fun mmComineqSh(a: M128h, b: M128h): Int = mmComiSh<CmpNeqUs>(a, b)

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b for equality, and
 * return the boolean result (0 or 1). This instruction will not signal an exception for QNaNs.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_ucomieq_sh)
 */
public fun mmUcomieqSh(a: M128h, b: M128h): Int = mmComiSh<CmpEqOq>(a, b)

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b for greater-than-or-equal,
 * and return the boolean result (0 or 1). This instruction will not signal an exception for QNaNs.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_ucomige_sh)
 */
public fun mmUcomigeSh(a: M128h, b: M128h): Int = mmComiSh<CmpGeOq>(a, b)

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b for greater-than, and return
 * the boolean result (0 or 1). This instruction will not signal an exception for QNaNs.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_ucomigt_sh)
 */
public fun mmUcomigtSh(a: M128h, b: M128h): Int = mmComiSh<CmpGtOq>(a, b)

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b for less-than-or-equal, and
 * return the boolean result (0 or 1). This instruction will not signal an exception for QNaNs.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_ucomile_sh)
 */
public fun mmUcomileSh(a: M128h, b: M128h): Int = mmComiSh<CmpLeOq>(a, b)

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b for less-than, and return
 * the boolean result (0 or 1). This instruction will not signal an exception for QNaNs.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_ucomilt_sh)
 */
public fun mmUcomiltSh(a: M128h, b: M128h): Int = mmComiSh<CmpLtOq>(a, b)

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b for not-equal, and return
 * the boolean result (0 or 1). This instruction will not signal an exception for QNaNs.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_ucomineq_sh)
 */
public fun mmUcomineqSh(a: M128h, b: M128h): Int = mmComiSh<CmpNeqUq>(a, b)

/**
 * Load 128-bits (composed of 8 packed half-precision (16-bit) floating-point elements) from memory into
 * a new vector. The address must be aligned to 16 bytes or a general-protection exception may be generated.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_load_ph)
 */
public fun mmLoadPh(memAddr: ConstPtr): M128h = memAddr

/**
 * Load 256-bits (composed of 16 packed half-precision (16-bit) floating-point elements) from memory into
 * a new vector. The address must be aligned to 32 bytes or a general-protection exception may be generated.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_load_ph)
 */
public fun mm256LoadPh(memAddr: ConstPtr): M256h = memAddr

/**
 * Load 512-bits (composed of 32 packed half-precision (16-bit) floating-point elements) from memory into
 * a new vector. The address must be aligned to 64 bytes or a general-protection exception may be generated.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_load_ph)
 */
public fun mm512LoadPh(memAddr: ConstPtr): M512h = memAddr

/**
 * Load a half-precision (16-bit) floating-point element from memory into the lower element of a new vector,
 * and zero the upper elements
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_load_sh)
 */
public fun mmLoadSh(memAddr: ConstPtr): M128h = mmSetSh((PrimitiveLayouts.F16_LAYOUT).readNative((memAddr).rawAddress))

/**
 * Load a half-precision (16-bit) floating-point element from memory into the lower element of a new vector
 * using writemask k (the element is copied from src when mask bit 0 is not set), and zero the upper elements.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_load_sh)
 */
public fun mmMaskLoadSh(src: M128h, k: Mmask8, memAddr: ConstPtr): M128h {
    val dst = src
    Unit
    return dst
}

/**
 * Load a half-precision (16-bit) floating-point element from memory into the lower element of a new vector
 * using zeromask k (the element is zeroed out when mask bit 0 is not set), and zero the upper elements.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_load_sh)
 */
public fun mmMaskzLoadSh(k: Mmask8, memAddr: ConstPtr): M128h {
    var dst: M128h
    Unit
    return dst
}

/**
 * Load 128-bits (composed of 8 packed half-precision (16-bit) floating-point elements) from memory into
 * a new vector. The address does not need to be aligned to any particular boundary.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_loadu_ph)
 */
public fun mmLoaduPh(memAddr: ConstPtr): M128h = readUnaligned(memAddr, typeLayoutT = PrimitiveLayouts.F16_LAYOUT)

/**
 * Load 256-bits (composed of 16 packed half-precision (16-bit) floating-point elements) from memory into
 * a new vector. The address does not need to be aligned to any particular boundary.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_loadu_ph)
 */
public fun mm256LoaduPh(memAddr: ConstPtr): M256h = readUnaligned(memAddr, typeLayoutT = PrimitiveLayouts.F16_LAYOUT)

/**
 * Load 512-bits (composed of 32 packed half-precision (16-bit) floating-point elements) from memory into
 * a new vector. The address does not need to be aligned to any particular boundary.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_loadu_ph)
 */
public fun mm512LoaduPh(memAddr: ConstPtr): M512h = readUnaligned(memAddr, typeLayoutT = PrimitiveLayouts.F16_LAYOUT)

/**
 * Move the lower half-precision (16-bit) floating-point element from b to the lower element of dst
 * using writemask k (the element is copied from src when mask bit 0 is not set), and copy the upper
 * 7 packed elements from a to the upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_move_sh)
 */
public fun mmMaskMoveSh(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h =
    run {
        var mov: F16 = run { val _simdArg0 = src; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        if ((k and 1u.toUByte()) != 0u.toUByte()) {
            mov = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = mov; _simdArg0 }
    }

/**
 * Move the lower half-precision (16-bit) floating-point element from b to the lower element of dst
 * using zeromask k (the element is zeroed out when mask bit 0 is not set), and copy the upper 7 packed
 * elements from a to the upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_move_sh)
 */
public fun mmMaskzMoveSh(k: Mmask8, a: M128h, b: M128h): M128h =
    run {
        var mov: F16 = F16.fromFloat(0.0f)
        if ((k and 1u.toUByte()) != 0u.toUByte()) {
            mov = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = mov; _simdArg0 }
    }

/**
 * Move the lower half-precision (16-bit) floating-point element from b to the lower element of dst,
 * and copy the upper 7 packed elements from a to the upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_move_sh)
 */
public fun mmMoveSh(a: M128h, b: M128h): M128h =
    run {
        val mov: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = mov; _simdArg0 }
    }

/**
 * Store 128-bits (composed of 8 packed half-precision (16-bit) floating-point elements) from a into memory.
 * The address must be aligned to 16 bytes or a general-protection exception may be generated.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_store_ph)
 */
public fun mmStorePh(memAddr: MutPtr, a: M128h) {
    memAddr = a
}

/**
 * Store 256-bits (composed of 16 packed half-precision (16-bit) floating-point elements) from a into memory.
 * The address must be aligned to 32 bytes or a general-protection exception may be generated.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_store_ph)
 */
public fun mm256StorePh(memAddr: MutPtr, a: M256h) {
    memAddr = a
}

/**
 * Store 512-bits (composed of 32 packed half-precision (16-bit) floating-point elements) from a into memory.
 * The address must be aligned to 64 bytes or a general-protection exception may be generated.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_store_ph)
 */
public fun mm512StorePh(memAddr: MutPtr, a: M512h) {
    memAddr = a
}

/**
 * Store the lower half-precision (16-bit) floating-point element from a into memory.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_store_sh)
 */
public fun mmStoreSh(memAddr: MutPtr, a: M128h) {
    val memAddr1 = memAddr
    (PrimitiveLayouts.F16_LAYOUT).writeNative((memAddr1).rawAddress, run { val _simdArg0 = a; val _simdArg1 = 0; _simdArg0 })
}

/**
 * Store the lower half-precision (16-bit) floating-point element from a into memory using writemask k
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_store_sh)
 */
public fun mmMaskStoreSh(memAddr: MutPtr, k: Mmask8, a: M128h) {
    Unit
}

/**
 * Store 128-bits (composed of 8 packed half-precision (16-bit) floating-point elements) from a into memory.
 * The address does not need to be aligned to any particular boundary.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_storeu_ph)
 */
public fun mmStoreuPh(memAddr: MutPtr, a: M128h) {
    writeUnaligned(memAddr, a, typeLayoutT = PrimitiveLayouts.F16_LAYOUT)
}

/**
 * Store 256-bits (composed of 16 packed half-precision (16-bit) floating-point elements) from a into memory.
 * The address does not need to be aligned to any particular boundary.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_storeu_ph)
 */
public fun mm256StoreuPh(memAddr: MutPtr, a: M256h) {
    writeUnaligned(memAddr, a, typeLayoutT = PrimitiveLayouts.F16_LAYOUT)
}

/**
 * Store 512-bits (composed of 32 packed half-precision (16-bit) floating-point elements) from a into memory.
 * The address does not need to be aligned to any particular boundary.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_storeu_ph)
 */
public fun mm512StoreuPh(memAddr: MutPtr, a: M512h) {
    writeUnaligned(memAddr, a, typeLayoutT = PrimitiveLayouts.F16_LAYOUT)
}

/**
 * Add packed half-precision (16-bit) floating-point elements in a and b, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_add_ph)
 */
public fun mmAddPh(a: M128h, b: M128h): M128h = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Add packed half-precision (16-bit) floating-point elements in a and b, and store the results in dst using
 * writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_add_ph)
 */
public fun mmMaskAddPh(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h =
    run {
        val r = mmAddPh(a, b)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src; _simdArg1 }
    }

/**
 * Add packed half-precision (16-bit) floating-point elements in a and b, and store the results in dst using
 * zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_add_ph)
 */
public fun mmMaskzAddPh(k: Mmask8, a: M128h, b: M128h): M128h =
    run {
        val r = mmAddPh(a, b)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = mmSetzeroPh(); _simdArg1 }
    }

/**
 * Add packed half-precision (16-bit) floating-point elements in a and b, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_add_ph)
 */
public fun mm256AddPh(a: M256h, b: M256h): M256h = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Add packed half-precision (16-bit) floating-point elements in a and b, and store the results in dst using
 * writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_add_ph)
 */
public fun mm256MaskAddPh(src: M256h, k: Mmask16, a: M256h, b: M256h): M256h =
    run {
        val r = mm256AddPh(a, b)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src; _simdArg1 }
    }

/**
 * Add packed half-precision (16-bit) floating-point elements in a and b, and store the results in dst using
 * zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_add_ph)
 */
public fun mm256MaskzAddPh(k: Mmask16, a: M256h, b: M256h): M256h =
    run {
        val r = mm256AddPh(a, b)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = mm256SetzeroPh(); _simdArg1 }
    }

/**
 * Add packed half-precision (16-bit) floating-point elements in a and b, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_add_ph)
 */
public fun mm512AddPh(a: M512h, b: M512h): M512h = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Add packed half-precision (16-bit) floating-point elements in a and b, and store the results in dst using
 * writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_add_ph)
 */
public fun mm512MaskAddPh(src: M512h, k: Mmask32, a: M512h, b: M512h): M512h =
    run {
        val r = mm512AddPh(a, b)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src; _simdArg1 }
    }

/**
 * Add packed half-precision (16-bit) floating-point elements in a and b, and store the results in dst using
 * zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_add_ph)
 */
public fun mm512MaskzAddPh(k: Mmask32, a: M512h, b: M512h): M512h =
    run {
        val r = mm512AddPh(a, b)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = mm512SetzeroPh(); _simdArg1 }
    }

/**
 * Add packed half-precision (16-bit) floating-point elements in a and b, and store the results in dst.
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_add_round_ph)
 */
public fun mm512AddRoundPh(a: M512h, b: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vaddph(a, b, rounding)
    }

/**
 * Add packed half-precision (16-bit) floating-point elements in a and b, and store the results in dst using
 * writemask k (elements are copied from src when the corresponding mask bit is not set).
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_add_round_ph)
 */
public fun mm512MaskAddRoundPh(src: M512h, k: Mmask32, a: M512h, b: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val r = mm512AddRoundPh(a, b, rounding)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src; _simdArg1 }
    }

/**
 * Add packed half-precision (16-bit) floating-point elements in a and b, and store the results in dst using
 * zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_add_round_ph)
 */
public fun mm512MaskzAddRoundPh(k: Mmask32, a: M512h, b: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val r = mm512AddRoundPh(a, b, rounding)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = mm512SetzeroPh(); _simdArg1 }
    }

/**
 * Add the lower half-precision (16-bit) floating-point elements in a and b, store the result in the
 * lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst.
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_add_round_sh)
 */
public fun mmAddRoundSh(a: M128h, b: M128h, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmMaskAddRoundSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b, rounding)
}

/**
 * Add the lower half-precision (16-bit) floating-point elements in a and b, store the result in the
 * lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst using
 * writemask k (the element is copied from src when mask bit 0 is not set).
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_add_round_sh)
 */
public fun mmMaskAddRoundSh(src: M128h, k: Mmask8, a: M128h, b: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vaddsh(a, b, src, k, rounding)
    }

/**
 * Add the lower half-precision (16-bit) floating-point elements in a and b, store the result in the
 * lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst using
 * zeromask k (the element is zeroed out when mask bit 0 is not set).
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_add_round_sh)
 */
public fun mmMaskzAddRoundSh(k: Mmask8, a: M128h, b: M128h, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmMaskAddRoundSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b, rounding)
}

/**
 * Add the lower half-precision (16-bit) floating-point elements in a and b, store the result in the
 * lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_add_sh)
 */
public fun mmAddSh(a: M128h, b: M128h): M128h = run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = mmCvtshH(a) + mmCvtshH(b); _simdArg0 }

/**
 * Add the lower half-precision (16-bit) floating-point elements in a and b, store the result in the
 * lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst using
 * writemask k (the element is copied from src when mask bit 0 is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_add_sh)
 */
public fun mmMaskAddSh(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h =
    run {
        val extractsrc: F16 = run { val _simdArg0 = src; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        var add: F16 = extractsrc
        if ((k and 0x1u.toUByte()) != 0u.toUByte()) {
            val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            add = extracta + extractb
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = add; _simdArg0 }
    }

/**
 * Add the lower half-precision (16-bit) floating-point elements in a and b, store the result in the
 * lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst using
 * zeromask k (the element is zeroed out when mask bit 0 is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_add_sh)
 */
public fun mmMaskzAddSh(k: Mmask8, a: M128h, b: M128h): M128h =
    run {
        var add: F16 = F16.fromFloat(0.0f)
        if ((k and 0x1u.toUByte()) != 0u.toUByte()) {
            val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            add = extracta + extractb
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = add; _simdArg0 }
    }

/**
 * Subtract packed half-precision (16-bit) floating-point elements in b from a, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_sub_ph)
 */
public fun mmSubPh(a: M128h, b: M128h): M128h = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Subtract packed half-precision (16-bit) floating-point elements in b from a, and store the results in dst using
 * writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_sub_ph)
 */
public fun mmMaskSubPh(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h =
    run {
        val r = mmSubPh(a, b)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src; _simdArg1 }
    }

/**
 * Subtract packed half-precision (16-bit) floating-point elements in b from a, and store the results in dst using
 * zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_sub_ph)
 */
public fun mmMaskzSubPh(k: Mmask8, a: M128h, b: M128h): M128h =
    run {
        val r = mmSubPh(a, b)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = mmSetzeroPh(); _simdArg1 }
    }

/**
 * Subtract packed half-precision (16-bit) floating-point elements in b from a, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_sub_ph)
 */
public fun mm256SubPh(a: M256h, b: M256h): M256h = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Subtract packed half-precision (16-bit) floating-point elements in b from a, and store the results in dst using
 * writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_sub_ph)
 */
public fun mm256MaskSubPh(src: M256h, k: Mmask16, a: M256h, b: M256h): M256h =
    run {
        val r = mm256SubPh(a, b)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src; _simdArg1 }
    }

/**
 * Subtract packed half-precision (16-bit) floating-point elements in b from a, and store the results in dst using
 * zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_sub_ph)
 */
public fun mm256MaskzSubPh(k: Mmask16, a: M256h, b: M256h): M256h =
    run {
        val r = mm256SubPh(a, b)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = mm256SetzeroPh(); _simdArg1 }
    }

/**
 * Subtract packed half-precision (16-bit) floating-point elements in b from a, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_sub_ph)
 */
public fun mm512SubPh(a: M512h, b: M512h): M512h = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Subtract packed half-precision (16-bit) floating-point elements in b from a, and store the results in dst using
 * writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_sub_ph)
 */
public fun mm512MaskSubPh(src: M512h, k: Mmask32, a: M512h, b: M512h): M512h =
    run {
        val r = mm512SubPh(a, b)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src; _simdArg1 }
    }

/**
 * Subtract packed half-precision (16-bit) floating-point elements in b from a, and store the results in dst using
 * zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_sub_ph)
 */
public fun mm512MaskzSubPh(k: Mmask32, a: M512h, b: M512h): M512h =
    run {
        val r = mm512SubPh(a, b)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = mm512SetzeroPh(); _simdArg1 }
    }

/**
 * Subtract packed half-precision (16-bit) floating-point elements in b from a, and store the results in dst.
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_sub_round_ph)
 */
public fun mm512SubRoundPh(a: M512h, b: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vsubph(a, b, rounding)
    }

/**
 * Subtract packed half-precision (16-bit) floating-point elements in b from a, and store the results in dst using
 * writemask k (elements are copied from src when the corresponding mask bit is not set).
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_sub_round_ph)
 */
public fun mm512MaskSubRoundPh(src: M512h, k: Mmask32, a: M512h, b: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val r = mm512SubRoundPh(a, b, rounding)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src; _simdArg1 }
    }

/**
 * Subtract packed half-precision (16-bit) floating-point elements in b from a, and store the results in dst using
 * zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_sub_round_ph)
 */
public fun mm512MaskzSubRoundPh(k: Mmask32, a: M512h, b: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val r = mm512SubRoundPh(a, b, rounding)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = mm512SetzeroPh(); _simdArg1 }
    }

/**
 * Subtract the lower half-precision (16-bit) floating-point elements in b from a, store the result in the
 * lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst.
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_sub_round_sh)
 */
public fun mmSubRoundSh(a: M128h, b: M128h, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmMaskSubRoundSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b, rounding)
}

/**
 * Subtract the lower half-precision (16-bit) floating-point elements in b from a, store the result in the
 * lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst using
 * writemask k (the element is copied from src when mask bit 0 is not set).
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_sub_round_sh)
 */
public fun mmMaskSubRoundSh(src: M128h, k: Mmask8, a: M128h, b: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vsubsh(a, b, src, k, rounding)
    }

/**
 * Subtract the lower half-precision (16-bit) floating-point elements in b from a, store the result in the
 * lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst using
 * zeromask k (the element is zeroed out when mask bit 0 is not set).
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_sub_round_sh)
 */
public fun mmMaskzSubRoundSh(k: Mmask8, a: M128h, b: M128h, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmMaskSubRoundSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b, rounding)
}

/**
 * Subtract the lower half-precision (16-bit) floating-point elements in b from a, store the result in the
 * lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_sub_sh)
 */
public fun mmSubSh(a: M128h, b: M128h): M128h = run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = mmCvtshH(a) - mmCvtshH(b); _simdArg0 }

/**
 * Subtract the lower half-precision (16-bit) floating-point elements in b from a, store the result in the
 * lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst using
 * writemask k (the element is copied from src when mask bit 0 is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_sub_sh)
 */
public fun mmMaskSubSh(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h =
    run {
        val extractsrc: F16 = run { val _simdArg0 = src; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        var add: F16 = extractsrc
        if ((k and 0x1u.toUByte()) != 0u.toUByte()) {
            val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            add = extracta - extractb
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = add; _simdArg0 }
    }

/**
 * Subtract the lower half-precision (16-bit) floating-point elements in b from a, store the result in the
 * lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst using
 * zeromask k (the element is zeroed out when mask bit 0 is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_sub_sh)
 */
public fun mmMaskzSubSh(k: Mmask8, a: M128h, b: M128h): M128h =
    run {
        var add: F16 = F16.fromFloat(0.0f)
        if ((k and 0x1u.toUByte()) != 0u.toUByte()) {
            val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            add = extracta - extractb
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = add; _simdArg0 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mul_ph)
 */
public fun mmMulPh(a: M128h, b: M128h): M128h = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, and store the results in dst using
 * writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_mul_ph)
 */
public fun mmMaskMulPh(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h =
    run {
        val r = mmMulPh(a, b)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src; _simdArg1 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, and store the results in dst using
 * zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_mul_ph)
 */
public fun mmMaskzMulPh(k: Mmask8, a: M128h, b: M128h): M128h =
    run {
        val r = mmMulPh(a, b)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = mmSetzeroPh(); _simdArg1 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mul_ph)
 */
public fun mm256MulPh(a: M256h, b: M256h): M256h = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, and store the results in dst using
 * writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_mul_ph)
 */
public fun mm256MaskMulPh(src: M256h, k: Mmask16, a: M256h, b: M256h): M256h =
    run {
        val r = mm256MulPh(a, b)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src; _simdArg1 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, and store the results in dst using
 * zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_mul_ph)
 */
public fun mm256MaskzMulPh(k: Mmask16, a: M256h, b: M256h): M256h =
    run {
        val r = mm256MulPh(a, b)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = mm256SetzeroPh(); _simdArg1 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mul_ph)
 */
public fun mm512MulPh(a: M512h, b: M512h): M512h = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, and store the results in dst using
 * writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_mul_ph)
 */
public fun mm512MaskMulPh(src: M512h, k: Mmask32, a: M512h, b: M512h): M512h =
    run {
        val r = mm512MulPh(a, b)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src; _simdArg1 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, and store the results in dst using
 * zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_mul_ph)
 */
public fun mm512MaskzMulPh(k: Mmask32, a: M512h, b: M512h): M512h =
    run {
        val r = mm512MulPh(a, b)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = mm512SetzeroPh(); _simdArg1 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, and store the results in dst.
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mul_round_ph)
 */
public fun mm512MulRoundPh(a: M512h, b: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vmulph(a, b, rounding)
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, and store the results in dst using
 * writemask k (elements are copied from src when the corresponding mask bit is not set).
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_mul_round_ph)
 */
public fun mm512MaskMulRoundPh(src: M512h, k: Mmask32, a: M512h, b: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val r = mm512MulRoundPh(a, b, rounding)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src; _simdArg1 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, and store the results in dst using
 * zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_mul_round_ph)
 */
public fun mm512MaskzMulRoundPh(k: Mmask32, a: M512h, b: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val r = mm512MulRoundPh(a, b, rounding)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = mm512SetzeroPh(); _simdArg1 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, store the result in the
 * lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst.
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mul_round_sh)
 */
public fun mmMulRoundSh(a: M128h, b: M128h, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmMaskMulRoundSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b, rounding)
}

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, store the result in the
 * lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst using
 * writemask k (the element is copied from src when mask bit 0 is not set).
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_mul_round_sh)
 */
public fun mmMaskMulRoundSh(src: M128h, k: Mmask8, a: M128h, b: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vmulsh(a, b, src, k, rounding)
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, store the result in the
 * lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst using
 * zeromask k (the element is zeroed out when mask bit 0 is not set).
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_mul_round_sh)
 */
public fun mmMaskzMulRoundSh(k: Mmask8, a: M128h, b: M128h, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmMaskMulRoundSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b, rounding)
}

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, store the result in the
 * lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mul_sh)
 */
public fun mmMulSh(a: M128h, b: M128h): M128h = run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = mmCvtshH(a) * mmCvtshH(b); _simdArg0 }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, store the result in the
 * lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst using
 * writemask k (the element is copied from src when mask bit 0 is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_mul_sh)
 */
public fun mmMaskMulSh(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h =
    run {
        val extractsrc: F16 = run { val _simdArg0 = src; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        var add: F16 = extractsrc
        if ((k and 0x1u.toUByte()) != 0u.toUByte()) {
            val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            add = extracta * extractb
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = add; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, store the result in the
 * lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst using
 * zeromask k (the element is zeroed out when mask bit 0 is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_mul_sh)
 */
public fun mmMaskzMulSh(k: Mmask8, a: M128h, b: M128h): M128h =
    run {
        var add: F16 = F16.fromFloat(0.0f)
        if ((k and 0x1u.toUByte()) != 0u.toUByte()) {
            val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            add = extracta * extractb
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = add; _simdArg0 }
    }

/**
 * Divide packed half-precision (16-bit) floating-point elements in a by b, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_div_ph)
 */
public fun mmDivPh(a: M128h, b: M128h): M128h = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Divide packed half-precision (16-bit) floating-point elements in a by b, and store the results in dst using
 * writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_div_ph)
 */
public fun mmMaskDivPh(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h =
    run {
        val r = mmDivPh(a, b)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src; _simdArg1 }
    }

/**
 * Divide packed half-precision (16-bit) floating-point elements in a by b, and store the results in dst using
 * zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_div_ph)
 */
public fun mmMaskzDivPh(k: Mmask8, a: M128h, b: M128h): M128h =
    run {
        val r = mmDivPh(a, b)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = mmSetzeroPh(); _simdArg1 }
    }

/**
 * Divide packed half-precision (16-bit) floating-point elements in a by b, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_div_ph)
 */
public fun mm256DivPh(a: M256h, b: M256h): M256h = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Divide packed half-precision (16-bit) floating-point elements in a by b, and store the results in dst using
 * writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_div_ph)
 */
public fun mm256MaskDivPh(src: M256h, k: Mmask16, a: M256h, b: M256h): M256h =
    run {
        val r = mm256DivPh(a, b)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src; _simdArg1 }
    }

/**
 * Divide packed half-precision (16-bit) floating-point elements in a by b, and store the results in dst using
 * zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_div_ph)
 */
public fun mm256MaskzDivPh(k: Mmask16, a: M256h, b: M256h): M256h =
    run {
        val r = mm256DivPh(a, b)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = mm256SetzeroPh(); _simdArg1 }
    }

/**
 * Divide packed half-precision (16-bit) floating-point elements in a by b, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_div_ph)
 */
public fun mm512DivPh(a: M512h, b: M512h): M512h = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Divide packed half-precision (16-bit) floating-point elements in a by b, and store the results in dst using
 * writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_div_ph)
 */
public fun mm512MaskDivPh(src: M512h, k: Mmask32, a: M512h, b: M512h): M512h =
    run {
        val r = mm512DivPh(a, b)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src; _simdArg1 }
    }

/**
 * Divide packed half-precision (16-bit) floating-point elements in a by b, and store the results in dst using
 * zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_div_ph)
 */
public fun mm512MaskzDivPh(k: Mmask32, a: M512h, b: M512h): M512h =
    run {
        val r = mm512DivPh(a, b)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = mm512SetzeroPh(); _simdArg1 }
    }

/**
 * Divide packed half-precision (16-bit) floating-point elements in a by b, and store the results in dst.
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_div_round_ph)
 */
public fun mm512DivRoundPh(a: M512h, b: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vdivph(a, b, rounding)
    }

/**
 * Divide packed half-precision (16-bit) floating-point elements in a by b, and store the results in dst using
 * writemask k (elements are copied from src when the corresponding mask bit is not set).
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_div_round_ph)
 */
public fun mm512MaskDivRoundPh(src: M512h, k: Mmask32, a: M512h, b: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val r = mm512DivRoundPh(a, b, rounding)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src; _simdArg1 }
    }

/**
 * Divide packed half-precision (16-bit) floating-point elements in a by b, and store the results in dst using
 * zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_div_round_ph)
 */
public fun mm512MaskzDivRoundPh(k: Mmask32, a: M512h, b: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val r = mm512DivRoundPh(a, b, rounding)
        run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = mm512SetzeroPh(); _simdArg1 }
    }

/**
 * Divide the lower half-precision (16-bit) floating-point elements in a by b, store the result in the
 * lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst.
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_div_round_sh)
 */
public fun mmDivRoundSh(a: M128h, b: M128h, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmMaskDivRoundSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b, rounding)
}

/**
 * Divide the lower half-precision (16-bit) floating-point elements in a by b, store the result in the
 * lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst using
 * writemask k (the element is copied from src when mask bit 0 is not set).
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_div_round_sh)
 */
public fun mmMaskDivRoundSh(src: M128h, k: Mmask8, a: M128h, b: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vdivsh(a, b, src, k, rounding)
    }

/**
 * Divide the lower half-precision (16-bit) floating-point elements in a by b, store the result in the
 * lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst using
 * zeromask k (the element is zeroed out when mask bit 0 is not set).
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_div_round_sh)
 */
public fun mmMaskzDivRoundSh(k: Mmask8, a: M128h, b: M128h, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmMaskDivRoundSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b, rounding)
}

/**
 * Divide the lower half-precision (16-bit) floating-point elements in a by b, store the result in the
 * lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_div_sh)
 */
public fun mmDivSh(a: M128h, b: M128h): M128h = run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = mmCvtshH(a) / mmCvtshH(b); _simdArg0 }

/**
 * Divide the lower half-precision (16-bit) floating-point elements in a by b, store the result in the
 * lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst using
 * writemask k (the element is copied from src when mask bit 0 is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_div_sh)
 */
public fun mmMaskDivSh(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h =
    run {
        val extractsrc: F16 = run { val _simdArg0 = src; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        var add: F16 = extractsrc
        if ((k and 0x1u.toUByte()) != 0u.toUByte()) {
            val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            add = extracta / extractb
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = add; _simdArg0 }
    }

/**
 * Divide the lower half-precision (16-bit) floating-point elements in a by b, store the result in the
 * lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst using
 * zeromask k (the element is zeroed out when mask bit 0 is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_div_sh)
 */
public fun mmMaskzDivSh(k: Mmask8, a: M128h, b: M128h): M128h =
    run {
        var add: F16 = F16.fromFloat(0.0f)
        if ((k and 0x1u.toUByte()) != 0u.toUByte()) {
            val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            add = extracta / extractb
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = add; _simdArg0 }
    }

/**
 * Multiply packed complex numbers in a and b, and store the results in dst. Each complex number is
 * composed of two adjacent half-precision (16-bit) floating-point elements, which defines the complex
 * number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mul_pch)
 */
public fun mmMulPch(a: M128h, b: M128h): M128h = mmMaskMulPch(mmUndefinedPh(), 0xffu, a, b)

/**
 * Multiply packed complex numbers in a and b, and store the results in dst using writemask k (the element
 * is copied from src when corresponding mask bit is not set). Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_mul_pch)
 */
public fun mmMaskMulPch(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h = transmuteWithLayouts<M128, M128h>(vfmulcph128(a, b, src, k), UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128h>())

/**
 * Multiply packed complex numbers in a and b, and store the results in dst using zeromask k (the element
 * is zeroed out when corresponding mask bit is not set). Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_mul_pch)
 */
public fun mmMaskzMulPch(k: Mmask8, a: M128h, b: M128h): M128h = mmMaskMulPch(mmSetzeroPh(), k, a, b)

/**
 * Multiply packed complex numbers in a and b, and store the results in dst. Each complex number is
 * composed of two adjacent half-precision (16-bit) floating-point elements, which defines the complex
 * number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mul_pch)
 */
public fun mm256MulPch(a: M256h, b: M256h): M256h = mm256MaskMulPch(mm256UndefinedPh(), 0xffu, a, b)

/**
 * Multiply packed complex numbers in a and b, and store the results in dst using writemask k (the element
 * is copied from src when corresponding mask bit is not set). Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_mul_pch)
 */
public fun mm256MaskMulPch(src: M256h, k: Mmask8, a: M256h, b: M256h): M256h = transmuteWithLayouts<M256, M256h>(vfmulcph256(a, b, src, k), UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256h>())

/**
 * Multiply packed complex numbers in a and b, and store the results in dst using zeromask k (the element
 * is zeroed out when corresponding mask bit is not set). Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_mul_pch)
 */
public fun mm256MaskzMulPch(k: Mmask8, a: M256h, b: M256h): M256h = mm256MaskMulPch(mm256SetzeroPh(), k, a, b)

/**
 * Multiply packed complex numbers in a and b, and store the results in dst. Each complex number is
 * composed of two adjacent half-precision (16-bit) floating-point elements, which defines the complex
 * number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mul_pch)
 */
public fun mm512MulPch(a: M512h, b: M512h): M512h = mm512MaskMulPch(mm512UndefinedPh(), 0xffffu, a, b)

/**
 * Multiply packed complex numbers in a and b, and store the results in dst using writemask k (the element
 * is copied from src when corresponding mask bit is not set). Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_mul_pch)
 */
public fun mm512MaskMulPch(src: M512h, k: Mmask16, a: M512h, b: M512h): M512h = mm512MaskMulRoundPch<MmFroundCurDirection>(src, k, a, b)

/**
 * Multiply packed complex numbers in a and b, and store the results in dst using zeromask k (the element
 * is zeroed out when corresponding mask bit is not set). Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_mul_pch)
 */
public fun mm512MaskzMulPch(k: Mmask16, a: M512h, b: M512h): M512h = mm512MaskMulPch(mm512SetzeroPh(), k, a, b)

/**
 * Multiply the packed complex numbers in a and b, and store the results in dst. Each complex number is
 * composed of two adjacent half-precision (16-bit) floating-point elements, which defines the complex
 * number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mul_round_pch)
 */
public fun mm512MulRoundPch(a: M512h, b: M512h, rounding: Int): M512h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskMulRoundPch(mm512UndefinedPh(), 0xffffu, a, b, rounding)
}

/**
 * Multiply the packed complex numbers in a and b, and store the results in dst using writemask k (the element
 * is copied from src when corresponding mask bit is not set). Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_mul_round_pch)
 */
public fun mm512MaskMulRoundPch(src: M512h, k: Mmask16, a: M512h, b: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        transmuteWithLayouts<M512, M512h>(vfmulcph512(a, b, src, k, rounding), UnresolvedTypeLayout<M512>(), UnresolvedTypeLayout<M512h>())
    }

/**
 * Multiply the packed complex numbers in a and b, and store the results in dst using zeromask k (the element
 * is zeroed out when corresponding mask bit is not set). Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_mul_round_pch)
 */
public fun mm512MaskzMulRoundPch(k: Mmask16, a: M512h, b: M512h, rounding: Int): M512h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskMulRoundPch(mm512SetzeroPh(), k, a, b, rounding)
}

/**
 * Multiply the lower complex numbers in a and b, and store the result in the lower elements of dst,
 * and copy the upper 6 packed elements from a to the upper elements of dst. Each complex number is
 * composed of two adjacent half-precision (16-bit) floating-point elements, which defines the complex
 * number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mul_sch)
 */
public fun mmMulSch(a: M128h, b: M128h): M128h = mmMaskMulSch(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b)

/**
 * Multiply the lower complex numbers in a and b, and store the result in the lower elements of dst using
 * writemask k (the element is copied from src when mask bit 0 is not set), and copy the upper 6 packed
 * elements from a to the upper elements of dst. Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_mul_sch)
 */
public fun mmMaskMulSch(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h = mmMaskMulRoundSch<MmFroundCurDirection>(src, k, a, b)

/**
 * Multiply the lower complex numbers in a and b, and store the result in the lower elements of dst using
 * zeromask k (the element is zeroed out when mask bit 0 is not set), and copy the upper 6 packed elements
 * from a to the upper elements of dst. Each complex number is composed of two adjacent half-precision
 * (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_mul_sch)
 */
public fun mmMaskzMulSch(k: Mmask8, a: M128h, b: M128h): M128h = mmMaskMulSch(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b)

/**
 * Multiply the lower complex numbers in a and b, and store the result in the lower elements of dst,
 * and copy the upper 6 packed elements from a to the upper elements of dst. Each complex number is
 * composed of two adjacent half-precision (16-bit) floating-point elements, which defines the complex
 * number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mul_round_sch)
 */
public fun mmMulRoundSch(a: M128h, b: M128h, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmMaskMulRoundSch(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b, rounding)
}

/**
 * Multiply the lower complex numbers in a and b, and store the result in the lower elements of dst using
 * writemask k (the element is copied from src when mask bit 0 is not set), and copy the upper 6 packed
 * elements from a to the upper elements of dst. Each complex number is composed of two adjacent half-precision
 * (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_mul_round_sch)
 */
public fun mmMaskMulRoundSch(src: M128h, k: Mmask8, a: M128h, b: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        transmuteWithLayouts<M128, M128h>(vfmulcsh(a, b, src, k, rounding), UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128h>())
    }

/**
 * Multiply the lower complex numbers in a and b, and store the result in the lower elements of dst using
 * zeromask k (the element is zeroed out when mask bit 0 is not set), and copy the upper 6 packed elements
 * from a to the upper elements of dst. Each complex number is composed of two adjacent half-precision
 * (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_mul_round_sch)
 */
public fun mmMaskzMulRoundSch(k: Mmask8, a: M128h, b: M128h, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmMaskMulRoundSch(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b, rounding)
}

/**
 * Multiply packed complex numbers in a and b, and store the results in dst. Each complex number is
 * composed of two adjacent half-precision (16-bit) floating-point elements, which defines the complex
 * number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fmul_pch)
 */
public fun mmFmulPch(a: M128h, b: M128h): M128h = mmMulPch(a, b)

/**
 * Multiply packed complex numbers in a and b, and store the results in dst using writemask k (the element
 * is copied from src when corresponding mask bit is not set). Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fmul_pch)
 */
public fun mmMaskFmulPch(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h = mmMaskMulPch(src, k, a, b)

/**
 * Multiply packed complex numbers in a and b, and store the results in dst using zeromask k (the element
 * is zeroed out when corresponding mask bit is not set). Each complex number is composed of two adjacent half-precision
 * (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fmul_pch)
 */
public fun mmMaskzFmulPch(k: Mmask8, a: M128h, b: M128h): M128h = mmMaskzMulPch(k, a, b)

/**
 * Multiply packed complex numbers in a and b, and store the results in dst. Each complex number is
 * composed of two adjacent half-precision (16-bit) floating-point elements, which defines the complex
 * number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_fmul_pch)
 */
public fun mm256FmulPch(a: M256h, b: M256h): M256h = mm256MulPch(a, b)

/**
 * Multiply packed complex numbers in a and b, and store the results in dst using writemask k (the element
 * is copied from src when corresponding mask bit is not set). Each complex number is composed of two adjacent half-precision
 * (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_fmul_pch)
 */
public fun mm256MaskFmulPch(src: M256h, k: Mmask8, a: M256h, b: M256h): M256h = mm256MaskMulPch(src, k, a, b)

/**
 * Multiply packed complex numbers in a and b, and store the results in dst using zeromask k (the element
 * is zeroed out when corresponding mask bit is not set). Each complex number is composed of two adjacent half-precision
 * (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_fmul_pch)
 */
public fun mm256MaskzFmulPch(k: Mmask8, a: M256h, b: M256h): M256h = mm256MaskzMulPch(k, a, b)

/**
 * Multiply packed complex numbers in a and b, and store the results in dst. Each complex number is composed
 * of two adjacent half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_fmul_pch)
 */
public fun mm512FmulPch(a: M512h, b: M512h): M512h = mm512MulPch(a, b)

/**
 * Multiply packed complex numbers in a and b, and store the results in dst using writemask k (the element
 * is copied from src when corresponding mask bit is not set). Each complex number is composed of two adjacent half-precision
 * (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_fmul_pch)
 */
public fun mm512MaskFmulPch(src: M512h, k: Mmask16, a: M512h, b: M512h): M512h = mm512MaskMulPch(src, k, a, b)

/**
 * Multiply packed complex numbers in a and b, and store the results in dst using zeromask k (the element
 * is zeroed out when corresponding mask bit is not set). Each complex number is composed of two adjacent half-precision
 * (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_fmul_pch)
 */
public fun mm512MaskzFmulPch(k: Mmask16, a: M512h, b: M512h): M512h = mm512MaskzMulPch(k, a, b)

/**
 * Multiply packed complex numbers in a and b, and store the results in dst. Each complex number is composed
 * of two adjacent half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_fmul_round_pch)
 */
public fun mm512FmulRoundPch(a: M512h, b: M512h, rounding: Int): M512h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MulRoundPch(a, b, rounding)
}

/**
 * Multiply packed complex numbers in a and b, and store the results in dst using writemask k (the element
 * is copied from src when corresponding mask bit is not set). Each complex number is composed of two adjacent half-precision
 * (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_fmul_round_pch)
 */
public fun mm512MaskFmulRoundPch(src: M512h, k: Mmask16, a: M512h, b: M512h, rounding: Int): M512h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskMulRoundPch(src, k, a, b, rounding)
}

/**
 * Multiply packed complex numbers in a and b, and store the results in dst using zeromask k (the element
 * is zeroed out when corresponding mask bit is not set). Each complex number is composed of two adjacent half-precision
 * (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_fmul_round_pch)
 */
public fun mm512MaskzFmulRoundPch(k: Mmask16, a: M512h, b: M512h, rounding: Int): M512h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskzMulRoundPch(k, a, b, rounding)
}

/**
 * Multiply the lower complex numbers in a and b, and store the results in dst. Each complex number is
 * composed of two adjacent half-precision (16-bit) floating-point elements, which defines the complex
 * number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fmul_sch)
 */
public fun mmFmulSch(a: M128h, b: M128h): M128h = mmMulSch(a, b)

/**
 * Multiply the lower complex numbers in a and b, and store the results in dst using writemask k (the element
 * is copied from src when mask bit 0 is not set). Each complex number is composed of two adjacent half-precision
 * (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fmul_sch)
 */
public fun mmMaskFmulSch(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h = mmMaskMulSch(src, k, a, b)

/**
 * Multiply the lower complex numbers in a and b, and store the results in dst using zeromask k (the element
 * is zeroed out when mask bit 0 is not set). Each complex number is composed of two adjacent half-precision
 * (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fmul_sch)
 */
public fun mmMaskzFmulSch(k: Mmask8, a: M128h, b: M128h): M128h = mmMaskzMulSch(k, a, b)

/**
 * Multiply the lower complex numbers in a and b, and store the results in dst. Each complex number is composed
 * of two adjacent half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fmul_round_sch)
 */
public fun mmFmulRoundSch(a: M128h, b: M128h, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmMulRoundSch(a, b, rounding)
}

/**
 * Multiply the lower complex numbers in a and b, and store the results in dst using writemask k (the element
 * is copied from src when mask bit 0 is not set). Each complex number is composed of two adjacent half-precision
 * (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fmul_round_sch)
 */
public fun mmMaskFmulRoundSch(src: M128h, k: Mmask8, a: M128h, b: M128h, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmMaskMulRoundSch(src, k, a, b, rounding)
}

/**
 * Multiply the lower complex numbers in a and b, and store the results in dst using zeromask k (the element
 * is zeroed out when mask bit 0 is not set). Each complex number is composed of two adjacent half-precision
 * (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fmul_round_sch)
 */
public fun mmMaskzFmulRoundSch(k: Mmask8, a: M128h, b: M128h, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmMaskzMulRoundSch(k, a, b, rounding)
}

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, and
 * store the results in dst. Each complex number is composed of two adjacent half-precision (16-bit)
 * floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cmul_pch)
 */
public fun mmCmulPch(a: M128h, b: M128h): M128h = mmMaskCmulPch(mmUndefinedPh(), 0xffu, a, b)

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, and
 * store the results in dst using writemask k (the element is copied from src when corresponding mask bit is not set).
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cmul_pch)
 */
public fun mmMaskCmulPch(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h = transmuteWithLayouts<M128, M128h>(vfcmulcph128(a, b, src, k), UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128h>())

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, and
 * store the results in dst using zeromask k (the element is zeroed out when corresponding mask bit is not set).
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cmul_pch)
 */
public fun mmMaskzCmulPch(k: Mmask8, a: M128h, b: M128h): M128h = mmMaskCmulPch(mmSetzeroPh(), k, a, b)

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, and
 * store the results in dst. Each complex number is composed of two adjacent half-precision (16-bit)
 * floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cmul_pch)
 */
public fun mm256CmulPch(a: M256h, b: M256h): M256h = mm256MaskCmulPch(mm256UndefinedPh(), 0xffu, a, b)

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, and
 * store the results in dst using writemask k (the element is copied from src when corresponding mask bit is not set).
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_cmul_pch)
 */
public fun mm256MaskCmulPch(src: M256h, k: Mmask8, a: M256h, b: M256h): M256h = transmuteWithLayouts<M256, M256h>(vfcmulcph256(a, b, src, k), UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256h>())

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, and
 * store the results in dst using zeromask k (the element is zeroed out when corresponding mask bit is not set).
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_cmul_pch)
 */
public fun mm256MaskzCmulPch(k: Mmask8, a: M256h, b: M256h): M256h = mm256MaskCmulPch(mm256SetzeroPh(), k, a, b)

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, and
 * store the results in dst. Each complex number is composed of two adjacent half-precision (16-bit)
 * floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cmul_pch)
 */
public fun mm512CmulPch(a: M512h, b: M512h): M512h = mm512MaskCmulPch(mm512UndefinedPh(), 0xffffu, a, b)

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, and
 * store the results in dst using writemask k (the element is copied from src when corresponding mask bit is not set).
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cmul_pch)
 */
public fun mm512MaskCmulPch(src: M512h, k: Mmask16, a: M512h, b: M512h): M512h = mm512MaskCmulRoundPch<MmFroundCurDirection>(src, k, a, b)

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, and
 * store the results in dst using zeromask k (the element is zeroed out when corresponding mask bit is not set).
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cmul_pch)
 */
public fun mm512MaskzCmulPch(k: Mmask16, a: M512h, b: M512h): M512h = mm512MaskCmulPch(mm512SetzeroPh(), k, a, b)

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, and
 * store the results in dst. Each complex number is composed of two adjacent half-precision (16-bit)
 * floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cmul_round_pch)
 */
public fun mm512CmulRoundPch(a: M512h, b: M512h, rounding: Int): M512h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCmulRoundPch(mm512UndefinedPh(), 0xffffu, a, b, rounding)
}

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, and
 * store the results in dst using writemask k (the element is copied from src when corresponding mask bit is not set).
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cmul_round_pch)
 */
public fun mm512MaskCmulRoundPch(src: M512h, k: Mmask16, a: M512h, b: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        transmuteWithLayouts<M512, M512h>(vfcmulcph512(a, b, src, k, rounding), UnresolvedTypeLayout<M512>(), UnresolvedTypeLayout<M512h>())
    }

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, and
 * store the results in dst using zeromask k (the element is zeroed out when corresponding mask bit is not set).
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cmul_round_pch)
 */
public fun mm512MaskzCmulRoundPch(k: Mmask16, a: M512h, b: M512h, rounding: Int): M512h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCmulRoundPch(mm512SetzeroPh(), k, a, b, rounding)
}

/**
 * Multiply the lower complex numbers in a by the complex conjugates of the lower complex numbers in b,
 * and store the results in dst. Each complex number is composed of two adjacent half-precision (16-bit)
 * floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cmul_sch)
 */
public fun mmCmulSch(a: M128h, b: M128h): M128h = mmMaskCmulSch(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b)

/**
 * Multiply the lower complex numbers in a by the complex conjugates of the lower complex numbers in b,
 * and store the results in dst using writemask k (the element is copied from src when mask bit 0 is not set).
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cmul_sch)
 */
public fun mmMaskCmulSch(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h = mmMaskCmulRoundSch<MmFroundCurDirection>(src, k, a, b)

/**
 * Multiply the lower complex numbers in a by the complex conjugates of the lower complex numbers in b,
 * and store the results in dst using zeromask k (the element is zeroed out when mask bit 0 is not set).
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cmul_sch)
 */
public fun mmMaskzCmulSch(k: Mmask8, a: M128h, b: M128h): M128h = mmMaskCmulSch(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b)

/**
 * Multiply the lower complex numbers in a by the complex conjugates of the lower complex numbers in b,
 * and store the results in dst. Each complex number is composed of two adjacent half-precision (16-bit)
 * floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cmul_round_sch)
 */
public fun mmCmulRoundSch(a: M128h, b: M128h, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmMaskCmulRoundSch(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b, rounding)
}

/**
 * Multiply the lower complex numbers in a by the complex conjugates of the lower complex numbers in b,
 * and store the results in dst using writemask k (the element is copied from src when mask bit 0 is not set).
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cmul_round_sch)
 */
public fun mmMaskCmulRoundSch(src: M128h, k: Mmask8, a: M128h, b: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        transmuteWithLayouts<M128, M128h>(vfcmulcsh(a, b, src, k, rounding), UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128h>())
    }

/**
 * Multiply the lower complex numbers in a by the complex conjugates of the lower complex numbers in b,
 * and store the results in dst using zeromask k (the element is zeroed out when mask bit 0 is not set).
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cmul_round_sch)
 */
public fun mmMaskzCmulRoundSch(k: Mmask8, a: M128h, b: M128h, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmMaskCmulRoundSch(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b, rounding)
}

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, and
 * store the results in dst. Each complex number is composed of two adjacent half-precision (16-bit)
 * floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fcmul_pch)
 */
public fun mmFcmulPch(a: M128h, b: M128h): M128h = mmCmulPch(a, b)

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, and
 * store the results in dst using writemask k (the element is copied from src when corresponding mask bit is not set).
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fcmul_pch)
 */
public fun mmMaskFcmulPch(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h = mmMaskCmulPch(src, k, a, b)

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, and
 * store the results in dst using zeromask k (the element is zeroed out when corresponding mask bit is not set).
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fcmul_pch)
 */
public fun mmMaskzFcmulPch(k: Mmask8, a: M128h, b: M128h): M128h = mmMaskzCmulPch(k, a, b)

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, and
 * store the results in dst. Each complex number is composed of two adjacent half-precision (16-bit)
 * floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_fcmul_pch)
 */
public fun mm256FcmulPch(a: M256h, b: M256h): M256h = mm256CmulPch(a, b)

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, and
 * store the results in dst using writemask k (the element is copied from src when corresponding mask bit is not set).
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_fcmul_pch)
 */
public fun mm256MaskFcmulPch(src: M256h, k: Mmask8, a: M256h, b: M256h): M256h = mm256MaskCmulPch(src, k, a, b)

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, and
 * store the results in dst using zeromask k (the element is zeroed out when corresponding mask bit is not set).
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_fcmul_pch)
 */
public fun mm256MaskzFcmulPch(k: Mmask8, a: M256h, b: M256h): M256h = mm256MaskzCmulPch(k, a, b)

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, and
 * store the results in dst. Each complex number is composed of two adjacent half-precision (16-bit)
 * floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_fcmul_pch)
 */
public fun mm512FcmulPch(a: M512h, b: M512h): M512h = mm512CmulPch(a, b)

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, and
 * store the results in dst using writemask k (the element is copied from src when corresponding mask bit is not set).
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_fcmul_pch)
 */
public fun mm512MaskFcmulPch(src: M512h, k: Mmask16, a: M512h, b: M512h): M512h = mm512MaskCmulPch(src, k, a, b)

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, and
 * store the results in dst using zeromask k (the element is zeroed out when corresponding mask bit is not set).
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_fcmul_pch)
 */
public fun mm512MaskzFcmulPch(k: Mmask16, a: M512h, b: M512h): M512h = mm512MaskzCmulPch(k, a, b)

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, and
 * store the results in dst. Each complex number is composed of two adjacent half-precision (16-bit)
 * floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_fcmul_round_pch)
 */
public fun mm512FcmulRoundPch(a: M512h, b: M512h, rounding: Int): M512h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512CmulRoundPch(a, b, rounding)
}

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, and
 * store the results in dst using writemask k (the element is copied from src when corresponding mask bit is not set).
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_fcmul_round_pch)
 */
public fun mm512MaskFcmulRoundPch(src: M512h, k: Mmask16, a: M512h, b: M512h, rounding: Int): M512h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCmulRoundPch(src, k, a, b, rounding)
}

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, and
 * store the results in dst using zeromask k (the element is zeroed out when corresponding mask bit is not set).
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_fcmul_round_pch)
 */
public fun mm512MaskzFcmulRoundPch(k: Mmask16, a: M512h, b: M512h, rounding: Int): M512h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskzCmulRoundPch(k, a, b, rounding)
}

/**
 * Multiply the lower complex numbers in a by the complex conjugates of the lower complex numbers in b,
 * and store the results in dst. Each complex number is composed of two adjacent half-precision (16-bit)
 * floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fcmul_sch)
 */
public fun mmFcmulSch(a: M128h, b: M128h): M128h = mmCmulSch(a, b)

/**
 * Multiply the lower complex numbers in a by the complex conjugates of the lower complex numbers in b,
 * and store the results in dst using writemask k (the element is copied from src when mask bit 0 is not set).
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fcmul_sch)
 */
public fun mmMaskFcmulSch(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h = mmMaskCmulSch(src, k, a, b)

/**
 * Multiply the lower complex numbers in a by the complex conjugates of the lower complex numbers in b,
 * and store the results in dst using zeromask k (the element is zeroed out when mask bit 0 is not set).
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fcmul_sch)
 */
public fun mmMaskzFcmulSch(k: Mmask8, a: M128h, b: M128h): M128h = mmMaskzCmulSch(k, a, b)

/**
 * Multiply the lower complex numbers in a by the complex conjugates of the lower complex numbers in b,
 * and store the results in dst. Each complex number is composed of two adjacent half-precision (16-bit)
 * floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fcmul_round_sch)
 */
public fun mmFcmulRoundSch(a: M128h, b: M128h, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmCmulRoundSch(a, b, rounding)
}

/**
 * Multiply the lower complex numbers in a by the complex conjugates of the lower complex numbers in b,
 * and store the results in dst using writemask k (the element is copied from src when mask bit 0 is not set).
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fcmul_round_sch)
 */
public fun mmMaskFcmulRoundSch(src: M128h, k: Mmask8, a: M128h, b: M128h, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmMaskCmulRoundSch(src, k, a, b, rounding)
}

/**
 * Multiply the lower complex numbers in a by the complex conjugates of the lower complex numbers in b,
 * and store the results in dst using zeromask k (the element is zeroed out when mask bit 0 is not set).
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fcmul_round_sch)
 */
public fun mmMaskzFcmulRoundSch(k: Mmask8, a: M128h, b: M128h, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmMaskzCmulRoundSch(k, a, b, rounding)
}

/**
 * Finds the absolute value of each packed half-precision (16-bit) floating-point element in v2, storing
 * the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_abs_ph)
 */
public fun mmAbsPh(v2: M128h): M128h = transmuteWithLayouts<M128i, M128h>(mmAndSi128(v2, mmSet1Epi16(Short.MAX_VALUE)), UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M128h>())

/**
 * Finds the absolute value of each packed half-precision (16-bit) floating-point element in v2, storing
 * the result in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_abs_ph)
 */
public fun mm256AbsPh(v2: M256h): M256h = transmuteWithLayouts<M256i, M256h>(mm256AndSi256(v2, mm256Set1Epi16(Short.MAX_VALUE)), UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<M256h>())

/**
 * Finds the absolute value of each packed half-precision (16-bit) floating-point element in v2, storing
 * the result in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_abs_ph)
 */
public fun mm512AbsPh(v2: M512h): M512h = transmuteWithLayouts<M512i, M512h>(mm512AndSi512(v2, mm512Set1Epi16(Short.MAX_VALUE)), UnresolvedTypeLayout<M512i>(), UnresolvedTypeLayout<M512h>())

/**
 * Compute the complex conjugates of complex numbers in a, and store the results in dst. Each complex
 * number is composed of two adjacent half-precision (16-bit) floating-point elements, which defines
 * the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate
 * `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_conj_pch)
 */
public fun mmConjPch(a: M128h): M128h = transmuteWithLayouts<M128i, M128h>(mmXorSi128(a, mmSet1Epi32(Int.MIN_VALUE)), UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M128h>())

/**
 * Compute the complex conjugates of complex numbers in a, and store the results in dst using writemask k
 * (the element is copied from src when corresponding mask bit is not set). Each complex number is composed of two
 * adjacent half-precision (16-bit) floating-point elements, which defines the complex number
 * `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_conj_pch)
 */
public fun mmMaskConjPch(src: M128h, k: Mmask8, a: M128h): M128h =
    run {
        val r: M128 = transmuteWithLayouts<M128h, M128>(mmConjPch(a), UnresolvedTypeLayout<M128h>(), UnresolvedTypeLayout<M128>())
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src; _simdArg1 })
    }

/**
 * Compute the complex conjugates of complex numbers in a, and store the results in dst using zeromask k
 * (the element is zeroed out when corresponding mask bit is not set). Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_conj_pch)
 */
public fun mmMaskzConjPch(k: Mmask8, a: M128h): M128h = mmMaskConjPch(mmSetzeroPh(), k, a)

/**
 * Compute the complex conjugates of complex numbers in a, and store the results in dst. Each complex number
 * is composed of two adjacent half-precision (16-bit) floating-point elements, which defines the complex
 * number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_conj_pch)
 */
public fun mm256ConjPch(a: M256h): M256h = transmuteWithLayouts<M256i, M256h>(mm256XorSi256(a, mm256Set1Epi32(Int.MIN_VALUE)), UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<M256h>())

/**
 * Compute the complex conjugates of complex numbers in a, and store the results in dst using writemask k
 * (the element is copied from src when corresponding mask bit is not set). Each complex number is composed of two
 * adjacent half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_conj_pch)
 */
public fun mm256MaskConjPch(src: M256h, k: Mmask8, a: M256h): M256h =
    run {
        val r: M256 = transmuteWithLayouts<M256h, M256>(mm256ConjPch(a), UnresolvedTypeLayout<M256h>(), UnresolvedTypeLayout<M256>())
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src; _simdArg1 })
    }

/**
 * Compute the complex conjugates of complex numbers in a, and store the results in dst using zeromask k
 * (the element is zeroed out when corresponding mask bit is not set). Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_conj_pch)
 */
public fun mm256MaskzConjPch(k: Mmask8, a: M256h): M256h = mm256MaskConjPch(mm256SetzeroPh(), k, a)

/**
 * Compute the complex conjugates of complex numbers in a, and store the results in dst. Each complex number
 * is composed of two adjacent half-precision (16-bit) floating-point elements, which defines the complex
 * number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_conj_pch)
 */
public fun mm512ConjPch(a: M512h): M512h = transmuteWithLayouts<M512i, M512h>(mm512XorSi512(a, mm512Set1Epi32(Int.MIN_VALUE)), UnresolvedTypeLayout<M512i>(), UnresolvedTypeLayout<M512h>())

/**
 * Compute the complex conjugates of complex numbers in a, and store the results in dst using writemask k
 * (the element is copied from src when corresponding mask bit is not set). Each complex number is composed of two
 * adjacent half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_conj_pch)
 */
public fun mm512MaskConjPch(src: M512h, k: Mmask16, a: M512h): M512h =
    run {
        val r: M512 = transmuteWithLayouts<M512h, M512>(mm512ConjPch(a), UnresolvedTypeLayout<M512h>(), UnresolvedTypeLayout<M512>())
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = src; _simdArg1 })
    }

/**
 * Compute the complex conjugates of complex numbers in a, and store the results in dst using zeromask k
 * (the element is zeroed out when corresponding mask bit is not set). Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_conj_pch)
 */
public fun mm512MaskzConjPch(k: Mmask16, a: M512h): M512h = mm512MaskConjPch(mm512SetzeroPh(), k, a)

/**
 * Multiply packed complex numbers in a and b, accumulate to the corresponding complex numbers in c,
 * and store the results in dst. Each complex number is composed of two adjacent half-precision (16-bit)
 * floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fmadd_pch)
 */
public fun mmFmaddPch(a: M128h, b: M128h, c: M128h): M128h = mmMask3FmaddPch(a, b, c, 0xffu)

/**
 * Multiply packed complex numbers in a and b, accumulate to the corresponding complex numbers in c,
 * and store the results in dst using writemask k (the element is copied from a when the corresponding
 * mask bit is not set). Each complex number is composed of two adjacent half-precision (16-bit)
 * floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fmadd_pch)
 */
public fun mmMaskFmaddPch(a: M128h, k: Mmask8, b: M128h, c: M128h): M128h =
    run {
        val r: M128 = transmuteWithLayouts<M128h, M128>(mmMask3FmaddPch(a, b, c, k), UnresolvedTypeLayout<M128h>(), UnresolvedTypeLayout<M128>())
        // using `0xff` would have been fine here, but this is what CLang does
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = a; _simdArg1 })
    }

/**
 * Multiply packed complex numbers in a and b, accumulate to the corresponding complex numbers in c,
 * and store the results in dst using writemask k (the element is copied from c when the corresponding
 * mask bit is not set). Each complex number is composed of two adjacent half-precision (16-bit)
 * floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask3_fmadd_pch)
 */
public fun mmMask3FmaddPch(a: M128h, b: M128h, c: M128h, k: Mmask8): M128h = transmuteWithLayouts<M128, M128h>(vfmaddcphMask3128(a, b, c, k), UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128h>())

/**
 * Multiply packed complex numbers in a and b, accumulate to the corresponding complex numbers in c,
 * and store the results in dst using zeromask k (the element is zeroed out when the corresponding mask
 * bit is not set). Each complex number is composed of two adjacent half-precision (16-bit) floating-point
 * elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fmadd_pch)
 */
public fun mmMaskzFmaddPch(k: Mmask8, a: M128h, b: M128h, c: M128h): M128h = transmuteWithLayouts<M128, M128h>(vfmaddcphMaskz128(a, b, c, k), UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128h>())

/**
 * Multiply packed complex numbers in a and b, accumulate to the corresponding complex numbers in c,
 * and store the results in dst. Each complex number is composed of two adjacent half-precision (16-bit)
 * floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_fmadd_pch)
 */
public fun mm256FmaddPch(a: M256h, b: M256h, c: M256h): M256h = mm256Mask3FmaddPch(a, b, c, 0xffu)

/**
 * Multiply packed complex numbers in a and b, accumulate to the corresponding complex numbers in c,
 * and store the results in dst using writemask k (the element is copied from a when the corresponding mask
 * bit is not set). Each complex number is composed of two adjacent half-precision (16-bit) floating-point
 * elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_fmadd_pch)
 */
public fun mm256MaskFmaddPch(a: M256h, k: Mmask8, b: M256h, c: M256h): M256h =
    run {
        val r: M256 = transmuteWithLayouts<M256h, M256>(mm256Mask3FmaddPch(a, b, c, k), UnresolvedTypeLayout<M256h>(), UnresolvedTypeLayout<M256>())
        // using `0xff` would have been fine here, but this is what CLang does
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = a; _simdArg1 })
    }

/**
 * Multiply packed complex numbers in a and b, accumulate to the corresponding complex numbers in c,
 * and store the results in dst using writemask k (the element is copied from c when the corresponding
 * mask bit is not set). Each complex number is composed of two adjacent half-precision (16-bit)
 * floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask3_fmadd_pch)
 */
public fun mm256Mask3FmaddPch(a: M256h, b: M256h, c: M256h, k: Mmask8): M256h = transmuteWithLayouts<M256, M256h>(vfmaddcphMask3256(a, b, c, k), UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256h>())

/**
 * Multiply packed complex numbers in a and b, accumulate to the corresponding complex numbers in c,
 * and store the results in dst using zeromask k (the element is zeroed out when the corresponding mask
 * bit is not set). Each complex number is composed of two adjacent half-precision (16-bit) floating-point
 * elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_fmadd_pch)
 */
public fun mm256MaskzFmaddPch(k: Mmask8, a: M256h, b: M256h, c: M256h): M256h = transmuteWithLayouts<M256, M256h>(vfmaddcphMaskz256(a, b, c, k), UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256h>())

/**
 * Multiply packed complex numbers in a and b, accumulate to the corresponding complex numbers in c,
 * and store the results in dst. Each complex number is composed of two adjacent half-precision (16-bit)
 * floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_fmadd_pch)
 */
public fun mm512FmaddPch(a: M512h, b: M512h, c: M512h): M512h = mm512FmaddRoundPch<MmFroundCurDirection>(a, b, c)

/**
 * Multiply packed complex numbers in a and b, accumulate to the corresponding complex numbers in c,
 * and store the results in dst using writemask k (the element is copied from a when the corresponding mask
 * bit is not set). Each complex number is composed of two adjacent half-precision (16-bit) floating-point
 * elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_fmadd_pch)
 */
public fun mm512MaskFmaddPch(a: M512h, k: Mmask16, b: M512h, c: M512h): M512h = mm512MaskFmaddRoundPch<MmFroundCurDirection>(a, k, b, c)

/**
 * Multiply packed complex numbers in a and b, accumulate to the corresponding complex numbers in c,
 * and store the results in dst using writemask k (the element is copied from c when the corresponding
 * mask bit is not set). Each complex number is composed of two adjacent half-precision (16-bit)
 * floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask3_fmadd_pch)
 */
public fun mm512Mask3FmaddPch(a: M512h, b: M512h, c: M512h, k: Mmask16): M512h = mm512Mask3FmaddRoundPch<MmFroundCurDirection>(a, b, c, k)

/**
 * Multiply packed complex numbers in a and b, accumulate to the corresponding complex numbers in c,
 * and store the results in dst using zeromask k (the element is zeroed out when the corresponding mask
 * bit is not set). Each complex number is composed of two adjacent half-precision (16-bit) floating-point
 * elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_fmadd_pch)
 */
public fun mm512MaskzFmaddPch(k: Mmask16, a: M512h, b: M512h, c: M512h): M512h = mm512MaskzFmaddRoundPch<MmFroundCurDirection>(k, a, b, c)

/**
 * Multiply packed complex numbers in a and b, accumulate to the corresponding complex numbers in c,
 * and store the results in dst. Each complex number is composed of two adjacent half-precision (16-bit)
 * floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_fmadd_round_pch)
 */
public fun mm512FmaddRoundPch(a: M512h, b: M512h, c: M512h, rounding: Int): M512h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512Mask3FmaddRoundPch(a, b, c, 0xffffu, rounding)
}

/**
 * Multiply packed complex numbers in a and b, accumulate to the corresponding complex numbers in c,
 * and store the results in dst using writemask k (the element is copied from a when the corresponding mask
 * bit is not set). Each complex number is composed of two adjacent half-precision (16-bit) floating-point
 * elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_fmadd_round_pch)
 */
public fun mm512MaskFmaddRoundPch(a: M512h, k: Mmask16, b: M512h, c: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val r: M512 = transmuteWithLayouts<M512h, M512>(mm512Mask3FmaddRoundPch(a, b, c, k, rounding), UnresolvedTypeLayout<M512h>(), UnresolvedTypeLayout<M512>())
        // using `0xffff` would have been fine here, but this is what CLang does
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = a; _simdArg1 })
    }

/**
 * Multiply packed complex numbers in a and b, accumulate to the corresponding complex numbers in c,
 * and store the results in dst using writemask k (the element is copied from c when the corresponding
 * mask bit is not set). Each complex number is composed of two adjacent half-precision (16-bit)
 * floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask3_fmadd_round_pch)
 */
public fun mm512Mask3FmaddRoundPch(a: M512h, b: M512h, c: M512h, k: Mmask16, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        transmuteWithLayouts<M512, M512h>(vfmaddcphMask3512(a, b, c, k, rounding), UnresolvedTypeLayout<M512>(), UnresolvedTypeLayout<M512h>())
    }

/**
 * Multiply packed complex numbers in a and b, accumulate to the corresponding complex numbers in c,
 * and store the results in dst using zeromask k (the element is zeroed out when the corresponding mask
 * bit is not set). Each complex number is composed of two adjacent half-precision (16-bit) floating-point
 * elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_fmadd_round_pch)
 */
public fun mm512MaskzFmaddRoundPch(k: Mmask16, a: M512h, b: M512h, c: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        transmuteWithLayouts<M512, M512h>(vfmaddcphMaskz512(a, b, c, k, rounding), UnresolvedTypeLayout<M512>(), UnresolvedTypeLayout<M512h>())
    }

/**
 * Multiply the lower complex numbers in a and b, accumulate to the lower complex number in c, and
 * store the result in the lower elements of dst, and copy the upper 6 packed elements from a to the
 * upper elements of dst. Each complex number is composed of two adjacent half-precision (16-bit)
 * floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fmadd_sch)
 */
public fun mmFmaddSch(a: M128h, b: M128h, c: M128h): M128h = mmFmaddRoundSch<MmFroundCurDirection>(a, b, c)

/**
 * Multiply the lower complex numbers in a and b, accumulate to the lower complex number in c, and
 * store the result in the lower elements of dst using writemask k (elements are copied from a when
 * mask bit 0 is not set), and copy the upper 6 packed elements from a to the upper elements of dst.
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements,
 * which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fmadd_sch)
 */
public fun mmMaskFmaddSch(a: M128h, k: Mmask8, b: M128h, c: M128h): M128h = mmMaskFmaddRoundSch<MmFroundCurDirection>(a, k, b, c)

/**
 * Multiply the lower complex numbers in a and b, accumulate to the lower complex number in c, and
 * store the result in the lower elements of dst using writemask k (elements are copied from c when
 * mask bit 0 is not set), and copy the upper 6 packed elements from a to the upper elements of dst.
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements,
 * which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask3_fmadd_sch)
 */
public fun mmMask3FmaddSch(a: M128h, b: M128h, c: M128h, k: Mmask8): M128h = mmMask3FmaddRoundSch<MmFroundCurDirection>(a, b, c, k)

/**
 * Multiply the lower complex numbers in a and b, accumulate to the lower complex number in c, and
 * store the result in the lower elements of dst using zeromask k (elements are zeroed out when mask
 * bit 0 is not set), and copy the upper 6 packed elements from a to the upper elements of dst. Each
 * complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fmadd_sch)
 */
public fun mmMaskzFmaddSch(k: Mmask8, a: M128h, b: M128h, c: M128h): M128h = mmMaskzFmaddRoundSch<MmFroundCurDirection>(k, a, b, c)

/**
 * Multiply the lower complex numbers in a and b, accumulate to the lower complex number in c, and
 * store the result in the lower elements of dst. Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fmadd_round_sch)
 */
public fun mmFmaddRoundSch(a: M128h, b: M128h, c: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        transmuteWithLayouts<M128, M128h>(vfmaddcshMask(a, b, c, 0xffu, rounding), UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128h>())
    }

/**
 * Multiply the lower complex numbers in a and b, accumulate to the lower complex number in c, and
 * store the result in the lower elements of dst using writemask k (elements are copied from a when
 * mask bit 0 is not set), and copy the upper 6 packed elements from a to the upper elements of dst.
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements,
 * which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fmadd_round_sch)
 */
public fun mmMaskFmaddRoundSch(a: M128h, k: Mmask8, b: M128h, c: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val a1 = a
        val r = vfmaddcshMask(a1, b, c, k, rounding)
        // using `0xff` would have been fine here, but this is what CLang does
        transmuteWithLayouts<M128, M128h>(mmMaskMoveSs(a1, k, a1, r), UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128h>())
    }

/**
 * Multiply the lower complex numbers in a and b, accumulate to the lower complex number in c, and
 * store the result in the lower elements of dst using writemask k (elements are copied from c when
 * mask bit 0 is not set), and copy the upper 6 packed elements from a to the upper elements of dst.
 * Each complex number is composed of two adjacent half-precision (16-bit) floating-point elements,
 * which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask3_fmadd_round_sch)
 */
public fun mmMask3FmaddRoundSch(a: M128h, b: M128h, c: M128h, k: Mmask8, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val c1 = c
        val r = vfmaddcshMask(a, b, c1, k, rounding)
        transmuteWithLayouts<M128, M128h>(mmMoveSs(c1, r), UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128h>())
    }

/**
 * Multiply the lower complex numbers in a and b, accumulate to the lower complex number in c, and
 * store the result in the lower elements of dst using zeromask k (elements are zeroed out when mask
 * bit 0 is not set), and copy the upper 6 packed elements from a to the upper elements of dst. Each
 * complex number is composed of two adjacent half-precision (16-bit) floating-point elements, which
 * defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fmadd_round_sch)
 */
public fun mmMaskzFmaddRoundSch(k: Mmask8, a: M128h, b: M128h, c: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        transmuteWithLayouts<M128, M128h>(vfmaddcshMaskz(a, b, c, k, rounding), UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128h>())
    }

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, accumulate
 * to the corresponding complex numbers in c, and store the results in dst. Each complex number is composed
 * of two adjacent half-precision (16-bit) floating-point elements, which defines the complex number
 * `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fcmadd_pch)
 */
public fun mmFcmaddPch(a: M128h, b: M128h, c: M128h): M128h = mmMask3FcmaddPch(a, b, c, 0xffu)

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, accumulate
 * to the corresponding complex numbers in c, and store the results in dst using writemask k (the element is
 * copied from a when the corresponding mask bit is not set). Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fcmadd_pch)
 */
public fun mmMaskFcmaddPch(a: M128h, k: Mmask8, b: M128h, c: M128h): M128h =
    run {
        val r: M128 = transmuteWithLayouts<M128h, M128>(mmMask3FcmaddPch(a, b, c, k), UnresolvedTypeLayout<M128h>(), UnresolvedTypeLayout<M128>())
        // using `0xff` would have been fine here, but this is what CLang does
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = a; _simdArg1 })
    }

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, accumulate
 * to the corresponding complex numbers in c, and store the results in dst using writemask k (the element is
 * copied from c when the corresponding mask bit is not set). Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask3_fcmadd_pch)
 */
public fun mmMask3FcmaddPch(a: M128h, b: M128h, c: M128h, k: Mmask8): M128h = transmuteWithLayouts<M128, M128h>(vfcmaddcphMask3128(a, b, c, k), UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128h>())

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, accumulate
 * to the corresponding complex numbers in c, and store the results in dst using zeromask k (the element is
 * zeroed out when the corresponding mask bit is not set). Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fcmadd_pch)
 */
public fun mmMaskzFcmaddPch(k: Mmask8, a: M128h, b: M128h, c: M128h): M128h = transmuteWithLayouts<M128, M128h>(vfcmaddcphMaskz128(a, b, c, k), UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128h>())

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, accumulate
 * to the corresponding complex numbers in c, and store the results in dst. Each complex number is composed
 * of two adjacent half-precision (16-bit) floating-point elements, which defines the complex number
 * `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_fcmadd_pch)
 */
public fun mm256FcmaddPch(a: M256h, b: M256h, c: M256h): M256h = mm256Mask3FcmaddPch(a, b, c, 0xffu)

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, accumulate
 * to the corresponding complex numbers in c, and store the results in dst using writemask k (the element is
 * copied from a when the corresponding mask bit is not set). Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_fcmadd_pch)
 */
public fun mm256MaskFcmaddPch(a: M256h, k: Mmask8, b: M256h, c: M256h): M256h =
    run {
        val r: M256 = transmuteWithLayouts<M256h, M256>(mm256Mask3FcmaddPch(a, b, c, k), UnresolvedTypeLayout<M256h>(), UnresolvedTypeLayout<M256>())
        // using `0xff` would have been fine here, but this is what CLang does
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = a; _simdArg1 })
    }

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, accumulate
 * to the corresponding complex numbers in c, and store the results in dst using writemask k (the element is
 * copied from c when the corresponding mask bit is not set). Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask3_fcmadd_pch)
 */
public fun mm256Mask3FcmaddPch(a: M256h, b: M256h, c: M256h, k: Mmask8): M256h = transmuteWithLayouts<M256, M256h>(vfcmaddcphMask3256(a, b, c, k), UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256h>())

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, accumulate
 * to the corresponding complex numbers in c, and store the results in dst using zeromask k (the element is
 * zeroed out when the corresponding mask bit is not set). Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_fcmadd_pch)
 */
public fun mm256MaskzFcmaddPch(k: Mmask8, a: M256h, b: M256h, c: M256h): M256h = transmuteWithLayouts<M256, M256h>(vfcmaddcphMaskz256(a, b, c, k), UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256h>())

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, accumulate
 * to the corresponding complex numbers in c, and store the results in dst. Each complex number is composed
 * of two adjacent half-precision (16-bit) floating-point elements, which defines the complex number
 * `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_fcmadd_pch)
 */
public fun mm512FcmaddPch(a: M512h, b: M512h, c: M512h): M512h = mm512FcmaddRoundPch<MmFroundCurDirection>(a, b, c)

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, accumulate
 * to the corresponding complex numbers in c, and store the results in dst using writemask k (the element is
 * copied from a when the corresponding mask bit is not set). Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_fcmadd_pch)
 */
public fun mm512MaskFcmaddPch(a: M512h, k: Mmask16, b: M512h, c: M512h): M512h = mm512MaskFcmaddRoundPch<MmFroundCurDirection>(a, k, b, c)

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, accumulate
 * to the corresponding complex numbers in c, and store the results in dst using writemask k (the element is
 * copied from c when the corresponding mask bit is not set). Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask3_fcmadd_pch)
 */
public fun mm512Mask3FcmaddPch(a: M512h, b: M512h, c: M512h, k: Mmask16): M512h = mm512Mask3FcmaddRoundPch<MmFroundCurDirection>(a, b, c, k)

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, accumulate
 * to the corresponding complex numbers in c, and store the results in dst using zeromask k (the element is
 * zeroed out when the corresponding mask bit is not set). Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_fcmadd_pch)
 */
public fun mm512MaskzFcmaddPch(k: Mmask16, a: M512h, b: M512h, c: M512h): M512h = mm512MaskzFcmaddRoundPch<MmFroundCurDirection>(k, a, b, c)

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, accumulate
 * to the corresponding complex numbers in c, and store the results in dst. Each complex number is composed
 * of two adjacent half-precision (16-bit) floating-point elements, which defines the complex number
 * `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_fcmadd_round_pch)
 */
public fun mm512FcmaddRoundPch(a: M512h, b: M512h, c: M512h, rounding: Int): M512h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512Mask3FcmaddRoundPch(a, b, c, 0xffffu, rounding)
}

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, accumulate
 * to the corresponding complex numbers in c, and store the results in dst using writemask k (the element is
 * copied from a when the corresponding mask bit is not set). Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_fcmadd_round_pch)
 */
public fun mm512MaskFcmaddRoundPch(a: M512h, k: Mmask16, b: M512h, c: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val r: M512 = transmuteWithLayouts<M512h, M512>(mm512Mask3FcmaddRoundPch(a, b, c, k, rounding), UnresolvedTypeLayout<M512h>(), UnresolvedTypeLayout<M512>())
        // using `0xffff` would have been fine here, but this is what CLang does
        transmute(run { val _simdArg0 = k; val _simdArg1 = r; val _simdArg2 = a; _simdArg1 })
    }

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, accumulate
 * to the corresponding complex numbers in c using writemask k (the element is copied from c when the corresponding
 * mask bit is not set), and store the results in dst. Each complex number is composed of two adjacent half-precision
 * (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1`, or the complex
 * conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask3_fcmadd_round_pch)
 */
public fun mm512Mask3FcmaddRoundPch(a: M512h, b: M512h, c: M512h, k: Mmask16, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        transmuteWithLayouts<M512, M512h>(vfcmaddcphMask3512(a, b, c, k, rounding), UnresolvedTypeLayout<M512>(), UnresolvedTypeLayout<M512h>())
    }

/**
 * Multiply packed complex numbers in a by the complex conjugates of packed complex numbers in b, accumulate
 * to the corresponding complex numbers in c using zeromask k (the element is zeroed out when the corresponding
 * mask bit is not set), and store the results in dst. Each complex number is composed of two adjacent half-precision
 * (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1`, or the complex
 * conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_fcmadd_round_pch)
 */
public fun mm512MaskzFcmaddRoundPch(k: Mmask16, a: M512h, b: M512h, c: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        transmuteWithLayouts<M512, M512h>(vfcmaddcphMaskz512(a, b, c, k, rounding), UnresolvedTypeLayout<M512>(), UnresolvedTypeLayout<M512h>())
    }

/**
 * Multiply the lower complex number in a by the complex conjugate of the lower complex number in b,
 * accumulate to the lower complex number in c, and store the result in the lower elements of dst,
 * and copy the upper 6 packed elements from a to the upper elements of dst. Each complex number is
 * composed of two adjacent half-precision (16-bit) floating-point elements, which defines the complex
 * number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fcmadd_sch)
 */
public fun mmFcmaddSch(a: M128h, b: M128h, c: M128h): M128h = mmFcmaddRoundSch<MmFroundCurDirection>(a, b, c)

/**
 * Multiply the lower complex number in a by the complex conjugate of the lower complex number in b,
 * accumulate to the lower complex number in c, and store the result in the lower elements of dst using
 * writemask k (the element is copied from a when the corresponding mask bit is not set), and copy the upper
 * 6 packed elements from a to the upper elements of dst. Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fcmadd_sch)
 */
public fun mmMaskFcmaddSch(a: M128h, k: Mmask8, b: M128h, c: M128h): M128h = mmMaskFcmaddRoundSch<MmFroundCurDirection>(a, k, b, c)

/**
 * Multiply the lower complex number in a by the complex conjugate of the lower complex number in b,
 * accumulate to the lower complex number in c, and store the result in the lower elements of dst using
 * writemask k (the element is copied from c when the corresponding mask bit is not set), and copy the upper
 * 6 packed elements from a to the upper elements of dst. Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask3_fcmadd_sch)
 */
public fun mmMask3FcmaddSch(a: M128h, b: M128h, c: M128h, k: Mmask8): M128h = mmMask3FcmaddRoundSch<MmFroundCurDirection>(a, b, c, k)

/**
 * Multiply the lower complex number in a by the complex conjugate of the lower complex number in b,
 * accumulate to the lower complex number in c, and store the result in the lower elements of dst using
 * zeromask k (the element is zeroed out when the corresponding mask bit is not set), and copy the upper
 * 6 packed elements from a to the upper elements of dst. Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fcmadd_sch)
 */
public fun mmMaskzFcmaddSch(k: Mmask8, a: M128h, b: M128h, c: M128h): M128h = mmMaskzFcmaddRoundSch<MmFroundCurDirection>(k, a, b, c)

/**
 * Multiply the lower complex number in a by the complex conjugate of the lower complex number in b,
 * accumulate to the lower complex number in c, and store the result in the lower elements of dst,
 * and copy the upper 6 packed elements from a to the upper elements of dst. Each complex number is
 * composed of two adjacent half-precision (16-bit) floating-point elements, which defines the complex
 * number `complex = vec.fp16[0] + i * vec.fp16[1]`, or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fcmadd_round_sch)
 */
public fun mmFcmaddRoundSch(a: M128h, b: M128h, c: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        transmuteWithLayouts<M128, M128h>(vfcmaddcshMask(a, b, c, 0xffu, rounding), UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128h>())
    }

/**
 * Multiply the lower complex number in a by the complex conjugate of the lower complex number in b,
 * accumulate to the lower complex number in c, and store the result in the lower elements of dst using
 * writemask k (the element is copied from a when the corresponding mask bit is not set), and copy the upper
 * 6 packed elements from a to the upper elements of dst. Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fcmadd_round_sch)
 */
public fun mmMaskFcmaddRoundSch(a: M128h, k: Mmask8, b: M128h, c: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val a1 = a
        val r = vfcmaddcshMask(a1, b, c, k, rounding)
        transmuteWithLayouts<M128, M128h>(mmMaskMoveSs(a1, k, a1, r), UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128h>())
    }

/**
 * Multiply the lower complex number in a by the complex conjugate of the lower complex number in b,
 * accumulate to the lower complex number in c, and store the result in the lower elements of dst using
 * writemask k (the element is copied from c when the corresponding mask bit is not set), and copy the upper
 * 6 packed elements from a to the upper elements of dst. Each complex number is composed of two adjacent
 * half-precision (16-bit) floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1]`,
 * or the complex conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask3_fcmadd_round_sch)
 */
public fun mmMask3FcmaddRoundSch(a: M128h, b: M128h, c: M128h, k: Mmask8, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val c1 = c
        val r = vfcmaddcshMask(a, b, c1, k, rounding)
        transmuteWithLayouts<M128, M128h>(mmMoveSs(c1, r), UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128h>())
    }

/**
 * Multiply the lower complex number in a by the complex conjugate of the lower complex number in b,
 * accumulate to the lower complex number in c using zeromask k (the element is zeroed out when the corresponding
 * mask bit is not set), and store the result in the lower elements of dst, and copy the upper 6 packed elements
 * from a to the upper elements of dst. Each complex number is composed of two adjacent half-precision (16-bit)
 * floating-point elements, which defines the complex number `complex = vec.fp16[0] + i * vec.fp16[1`, or the complex
 * conjugate `conjugate = vec.fp16[0] - i * vec.fp16[1]`.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fcmadd_round_sch)
 */
public fun mmMaskzFcmaddRoundSch(k: Mmask8, a: M128h, b: M128h, c: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        transmuteWithLayouts<M128, M128h>(vfcmaddcshMaskz(a, b, c, k, rounding), UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<M128h>())
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, add the intermediate
 * result to packed elements in c, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fmadd_ph)
 */
public fun mmFmaddPh(a: M128h, b: M128h, c: M128h): M128h = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, add the intermediate
 * result to packed elements in c, and store the results in dst using writemask k (the element is copied
 * from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fmadd_ph)
 */
public fun mmMaskFmaddPh(a: M128h, k: Mmask8, b: M128h, c: M128h): M128h = run { val _simdArg0 = k; val _simdArg1 = mmFmaddPh(a, b, c); val _simdArg2 = a; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, add the intermediate
 * result to packed elements in c, and store the results in dst using writemask k (the element is copied
 * from c when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask3_fmadd_ph)
 */
public fun mmMask3FmaddPh(a: M128h, b: M128h, c: M128h, k: Mmask8): M128h = run { val _simdArg0 = k; val _simdArg1 = mmFmaddPh(a, b, c); val _simdArg2 = c; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, add the intermediate
 * result to packed elements in c, and store the results in dst using zeromask k (the element is zeroed
 * out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fmadd_ph)
 */
public fun mmMaskzFmaddPh(k: Mmask8, a: M128h, b: M128h, c: M128h): M128h = run { val _simdArg0 = k; val _simdArg1 = mmFmaddPh(a, b, c); val _simdArg2 = mmSetzeroPh(); _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, add the intermediate
 * result to packed elements in c, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_fmadd_ph)
 */
public fun mm256FmaddPh(a: M256h, b: M256h, c: M256h): M256h = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, add the intermediate
 * result to packed elements in c, and store the results in dst using writemask k (the element is copied
 * from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_fmadd_ph)
 */
public fun mm256MaskFmaddPh(a: M256h, k: Mmask16, b: M256h, c: M256h): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256FmaddPh(a, b, c); val _simdArg2 = a; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, add the intermediate
 * result to packed elements in c, and store the results in dst using writemask k (the element is copied
 * from c when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask3_fmadd_ph)
 */
public fun mm256Mask3FmaddPh(a: M256h, b: M256h, c: M256h, k: Mmask16): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256FmaddPh(a, b, c); val _simdArg2 = c; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, add the intermediate
 * result to packed elements in c, and store the results in dst using zeromask k (the element is zeroed
 * out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_fmadd_ph)
 */
public fun mm256MaskzFmaddPh(k: Mmask16, a: M256h, b: M256h, c: M256h): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256FmaddPh(a, b, c); val _simdArg2 = mm256SetzeroPh(); _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, add the intermediate
 * result to packed elements in c, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_fmadd_ph)
 */
public fun mm512FmaddPh(a: M512h, b: M512h, c: M512h): M512h = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, add the intermediate
 * result to packed elements in c, and store the results in dst using writemask k (the element is copied
 * from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_fmadd_ph)
 */
public fun mm512MaskFmaddPh(a: M512h, k: Mmask32, b: M512h, c: M512h): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512FmaddPh(a, b, c); val _simdArg2 = a; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, add the intermediate
 * result to packed elements in c, and store the results in dst using writemask k (the element is copied
 * from c when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask3_fmadd_ph)
 */
public fun mm512Mask3FmaddPh(a: M512h, b: M512h, c: M512h, k: Mmask32): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512FmaddPh(a, b, c); val _simdArg2 = c; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, add the intermediate
 * result to packed elements in c, and store the results in dst using zeromask k (the element is zeroed
 * out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_fmadd_ph)
 */
public fun mm512MaskzFmaddPh(k: Mmask32, a: M512h, b: M512h, c: M512h): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512FmaddPh(a, b, c); val _simdArg2 = mm512SetzeroPh(); _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, add the intermediate
 * result to packed elements in c, and store the results in dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_fmadd_round_ph)
 */
public fun mm512FmaddRoundPh(a: M512h, b: M512h, c: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vfmaddph512(a, b, c, rounding)
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, add the intermediate
 * result to packed elements in c, and store the results in dst using writemask k (the element is copied
 * from a when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_fmadd_round_ph)
 */
public fun mm512MaskFmaddRoundPh(a: M512h, k: Mmask32, b: M512h, c: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512FmaddRoundPh(a, b, c, rounding); val _simdArg2 = a; _simdArg1 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, add the intermediate
 * result to packed elements in c, and store the results in dst using writemask k (the element is copied
 * from c when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask3_fmadd_round_ph)
 */
public fun mm512Mask3FmaddRoundPh(a: M512h, b: M512h, c: M512h, k: Mmask32, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512FmaddRoundPh(a, b, c, rounding); val _simdArg2 = c; _simdArg1 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, add the intermediate
 * result to packed elements in c, and store the results in dst using zeromask k (the element is zeroed
 * out when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_fmadd_round_ph)
 */
public fun mm512MaskzFmaddRoundPh(k: Mmask32, a: M512h, b: M512h, c: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512FmaddRoundPh(a, b, c, rounding); val _simdArg2 = mm512SetzeroPh(); _simdArg1 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and add the intermediate
 * result to the lower element in c. Store the result in the lower element of dst, and copy the upper
 * 7 packed elements from a to the upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fmadd_sh)
 */
public fun mmFmaddSh(a: M128h, b: M128h, c: M128h): M128h =
    run {
        val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        val extractc: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        val r = fma(extracta, extractb, extractc)
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = r; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and add the intermediate
 * result to the lower element in c. Store the result in the lower element of dst using writemask k (the element
 * is copied from a when the mask bit 0 is not set), and copy the upper 7 packed elements from a to the
 * upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fmadd_sh)
 */
public fun mmMaskFmaddSh(a: M128h, k: Mmask8, b: M128h, c: M128h): M128h =
    run {
        var fmadd: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        if (k and 1u.toUByte() != 0u.toUByte()) {
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractc: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            fmadd = fma(fmadd, extractb, extractc)
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = fmadd; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and add the intermediate
 * result to the lower element in c. Store the result in the lower element of dst using writemask k (the element
 * is copied from c when the mask bit 0 is not set), and copy the upper 7 packed elements from c to the
 * upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask3_fmadd_sh)
 */
public fun mmMask3FmaddSh(a: M128h, b: M128h, c: M128h, k: Mmask8): M128h =
    run {
        var fmadd: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        if (k and 1u.toUByte() != 0u.toUByte()) {
            val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            fmadd = fma(extracta, extractb, fmadd)
        }
        run { val _simdArg0 = c; val _simdArg1 = 0; val _simdArg2 = fmadd; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and add the intermediate
 * result to the lower element in c. Store the result in the lower element of dst using zeromask k (the element
 * is zeroed out when mask bit 0 is not set), and copy the upper 7 packed elements from a to the
 * upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fmadd_sh)
 */
public fun mmMaskzFmaddSh(k: Mmask8, a: M128h, b: M128h, c: M128h): M128h =
    run {
        var fmadd: F16 = F16.fromFloat(0.0f)
        if (k and 1u.toUByte() != 0u.toUByte()) {
            val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractc: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            fmadd = fma(extracta, extractb, extractc)
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = fmadd; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and add the intermediate
 * result to the lower element in c. Store the result in the lower element of dst, and copy the upper
 * 7 packed elements from a to the upper elements of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fmadd_round_sh)
 */
public fun mmFmaddRoundSh(a: M128h, b: M128h, c: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        val extractc: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        val r = vfmaddsh(extracta, extractb, extractc, rounding)
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = r; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and add the intermediate
 * result to the lower element in c. Store the result in the lower element of dst using writemask k (the element
 * is copied from a when the mask bit 0 is not set), and copy the upper 7 packed elements from a to the
 * upper elements of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fmadd_round_sh)
 */
public fun mmMaskFmaddRoundSh(a: M128h, k: Mmask8, b: M128h, c: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        var fmadd: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        if (k and 1u.toUByte() != 0u.toUByte()) {
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractc: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            fmadd = vfmaddsh(fmadd, extractb, extractc, rounding)
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = fmadd; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and add the intermediate
 * result to the lower element in c. Store the result in the lower element of dst using writemask k (the element
 * is copied from c when the mask bit 0 is not set), and copy the upper 7 packed elements from c to the
 * upper elements of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask3_fmadd_round_sh)
 */
public fun mmMask3FmaddRoundSh(a: M128h, b: M128h, c: M128h, k: Mmask8, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        var fmadd: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        if (k and 1u.toUByte() != 0u.toUByte()) {
            val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            fmadd = vfmaddsh(extracta, extractb, fmadd, rounding)
        }
        run { val _simdArg0 = c; val _simdArg1 = 0; val _simdArg2 = fmadd; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and add the intermediate
 * result to the lower element in c. Store the result in the lower element of dst using zeromask k (the element
 * is zeroed out when mask bit 0 is not set), and copy the upper 7 packed elements from a to the
 * upper elements of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fmadd_round_sh)
 */
public fun mmMaskzFmaddRoundSh(k: Mmask8, a: M128h, b: M128h, c: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        var fmadd: F16 = F16.fromFloat(0.0f)
        if (k and 1u.toUByte() != 0u.toUByte()) {
            val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractc: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            fmadd = vfmaddsh(extracta, extractb, extractc, rounding)
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = fmadd; _simdArg0 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the intermediate result, and store the results in dst.
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fmsub_ph)
 */
public fun mmFmsubPh(a: M128h, b: M128h, c: M128h): M128h = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the intermediate result, and store the results in dst using writemask k (the element is copied
 * from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fmsub_ph)
 */
public fun mmMaskFmsubPh(a: M128h, k: Mmask8, b: M128h, c: M128h): M128h = run { val _simdArg0 = k; val _simdArg1 = mmFmsubPh(a, b, c); val _simdArg2 = a; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the intermediate result, and store the results in dst using writemask k (the element is copied
 * from c when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask3_fmsub_ph)
 */
public fun mmMask3FmsubPh(a: M128h, b: M128h, c: M128h, k: Mmask8): M128h = run { val _simdArg0 = k; val _simdArg1 = mmFmsubPh(a, b, c); val _simdArg2 = c; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the intermediate result, and store the results in dst using zeromask k (the element is zeroed
 * out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fmsub_ph)
 */
public fun mmMaskzFmsubPh(k: Mmask8, a: M128h, b: M128h, c: M128h): M128h = run { val _simdArg0 = k; val _simdArg1 = mmFmsubPh(a, b, c); val _simdArg2 = mmSetzeroPh(); _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the intermediate result, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_fmsub_ph)
 */
public fun mm256FmsubPh(a: M256h, b: M256h, c: M256h): M256h = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the intermediate result, and store the results in dst using writemask k (the element is copied
 * from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_fmsub_ph)
 */
public fun mm256MaskFmsubPh(a: M256h, k: Mmask16, b: M256h, c: M256h): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256FmsubPh(a, b, c); val _simdArg2 = a; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the intermediate result, and store the results in dst using writemask k (the element is copied
 * from c when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask3_fmsub_ph)
 */
public fun mm256Mask3FmsubPh(a: M256h, b: M256h, c: M256h, k: Mmask16): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256FmsubPh(a, b, c); val _simdArg2 = c; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the intermediate result, and store the results in dst using zeromask k (the element is zeroed
 * out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_fmsub_ph)
 */
public fun mm256MaskzFmsubPh(k: Mmask16, a: M256h, b: M256h, c: M256h): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256FmsubPh(a, b, c); val _simdArg2 = mm256SetzeroPh(); _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the intermediate result, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_fmsub_ph)
 */
public fun mm512FmsubPh(a: M512h, b: M512h, c: M512h): M512h = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the intermediate result, and store the results in dst using writemask k (the element is copied
 * from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_fmsub_ph)
 */
public fun mm512MaskFmsubPh(a: M512h, k: Mmask32, b: M512h, c: M512h): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512FmsubPh(a, b, c); val _simdArg2 = a; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the intermediate result, and store the results in dst using writemask k (the element is copied
 * from c when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask3_fmsub_ph)
 */
public fun mm512Mask3FmsubPh(a: M512h, b: M512h, c: M512h, k: Mmask32): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512FmsubPh(a, b, c); val _simdArg2 = c; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the intermediate result, and store the results in dst using zeromask k (the element is zeroed
 * out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_fmsub_ph)
 */
public fun mm512MaskzFmsubPh(k: Mmask32, a: M512h, b: M512h, c: M512h): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512FmsubPh(a, b, c); val _simdArg2 = mm512SetzeroPh(); _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the intermediate result, and store the results in dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_fmsub_round_ph)
 */
public fun mm512FmsubRoundPh(a: M512h, b: M512h, c: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vfmaddph512(a, b, run { val _simdArg0 = c; _simdArg0 }, rounding)
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the intermediate result, and store the results in dst using writemask k (the element is copied
 * from a when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_fmsub_round_ph)
 */
public fun mm512MaskFmsubRoundPh(a: M512h, k: Mmask32, b: M512h, c: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512FmsubRoundPh(a, b, c, rounding); val _simdArg2 = a; _simdArg1 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the intermediate result, and store the results in dst using writemask k (the element is copied
 * from c when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask3_fmsub_round_ph)
 */
public fun mm512Mask3FmsubRoundPh(a: M512h, b: M512h, c: M512h, k: Mmask32, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512FmsubRoundPh(a, b, c, rounding); val _simdArg2 = c; _simdArg1 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the intermediate result, and store the results in dst using zeromask k (the element is zeroed
 * out when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_fmsub_round_ph)
 */
public fun mm512MaskzFmsubRoundPh(k: Mmask32, a: M512h, b: M512h, c: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512FmsubRoundPh(a, b, c, rounding); val _simdArg2 = mm512SetzeroPh(); _simdArg1 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and subtract packed elements
 * in c from the intermediate result. Store the result in the lower element of dst, and copy the upper
 * 7 packed elements from a to the upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fmsub_sh)
 */
public fun mmFmsubSh(a: M128h, b: M128h, c: M128h): M128h =
    run {
        val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        val extractc: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        val r = fma(extracta, extractb, -extractc)
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = r; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and subtract packed elements
 * in c from the intermediate result. Store the result in the lower element of dst using writemask k (the element
 * is copied from a when the mask bit 0 is not set), and copy the upper 7 packed elements from a to the
 * upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fmsub_sh)
 */
public fun mmMaskFmsubSh(a: M128h, k: Mmask8, b: M128h, c: M128h): M128h =
    run {
        var fmsub: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        if (k and 1u.toUByte() != 0u.toUByte()) {
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractc: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            fmsub = fma(fmsub, extractb, -extractc)
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = fmsub; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and subtract packed elements
 * in c from the intermediate result. Store the result in the lower element of dst using writemask k (the element
 * is copied from c when the mask bit 0 is not set), and copy the upper 7 packed elements from c to the
 * upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask3_fmsub_sh)
 */
public fun mmMask3FmsubSh(a: M128h, b: M128h, c: M128h, k: Mmask8): M128h =
    run {
        var fmsub: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        if (k and 1u.toUByte() != 0u.toUByte()) {
            val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            fmsub = fma(extracta, extractb, -fmsub)
        }
        run { val _simdArg0 = c; val _simdArg1 = 0; val _simdArg2 = fmsub; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and subtract packed elements
 * in c from the intermediate result. Store the result in the lower element of dst using zeromask k (the element
 * is zeroed out when mask bit 0 is not set), and copy the upper 7 packed elements from a to the
 * upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fmsub_sh)
 */
public fun mmMaskzFmsubSh(k: Mmask8, a: M128h, b: M128h, c: M128h): M128h =
    run {
        var fmsub: F16 = F16.fromFloat(0.0f)
        if (k and 1u.toUByte() != 0u.toUByte()) {
            val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractc: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            fmsub = fma(extracta, extractb, -extractc)
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = fmsub; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and subtract packed elements
 * in c from the intermediate result. Store the result in the lower element of dst, and copy the upper
 * 7 packed elements from a to the upper elements of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fmsub_round_sh)
 */
public fun mmFmsubRoundSh(a: M128h, b: M128h, c: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        val extractc: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        val r = vfmaddsh(extracta, extractb, -extractc, rounding)
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = r; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and subtract packed elements
 * in c from the intermediate result. Store the result in the lower element of dst using writemask k (the element
 * is copied from a when the mask bit 0 is not set), and copy the upper 7 packed elements from a to the
 * upper elements of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fmsub_round_sh)
 */
public fun mmMaskFmsubRoundSh(a: M128h, k: Mmask8, b: M128h, c: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        var fmsub: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        if (k and 1u.toUByte() != 0u.toUByte()) {
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractc: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            fmsub = vfmaddsh(fmsub, extractb, -extractc, rounding)
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = fmsub; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and subtract packed elements
 * in c from the intermediate result. Store the result in the lower element of dst using writemask k (the element
 * is copied from c when the mask bit 0 is not set), and copy the upper 7 packed elements from c to the
 * upper elements of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask3_fmsub_round_sh)
 */
public fun mmMask3FmsubRoundSh(a: M128h, b: M128h, c: M128h, k: Mmask8, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        var fmsub: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        if (k and 1u.toUByte() != 0u.toUByte()) {
            val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            fmsub = vfmaddsh(extracta, extractb, -fmsub, rounding)
        }
        run { val _simdArg0 = c; val _simdArg1 = 0; val _simdArg2 = fmsub; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and subtract packed elements
 * in c from the intermediate result. Store the result in the lower element of dst using zeromask k (the element
 * is zeroed out when mask bit 0 is not set), and copy the upper 7 packed elements from a to the
 * upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fmsub_round_sh)
 */
public fun mmMaskzFmsubRoundSh(k: Mmask8, a: M128h, b: M128h, c: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        var fmsub: F16 = F16.fromFloat(0.0f)
        if (k and 1u.toUByte() != 0u.toUByte()) {
            val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractc: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            fmsub = vfmaddsh(extracta, extractb, -extractc, rounding)
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = fmsub; _simdArg0 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract the intermediate
 * result from packed elements in c, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fnmadd_ph)
 */
public fun mmFnmaddPh(a: M128h, b: M128h, c: M128h): M128h = run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract the intermediate
 * result from packed elements in c, and store the results in dst using writemask k (the element is copied
 * from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fnmadd_ph)
 */
public fun mmMaskFnmaddPh(a: M128h, k: Mmask8, b: M128h, c: M128h): M128h = run { val _simdArg0 = k; val _simdArg1 = mmFnmaddPh(a, b, c); val _simdArg2 = a; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract the intermediate
 * result from packed elements in c, and store the results in dst using writemask k (the element is copied
 * from c when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask3_fnmadd_ph)
 */
public fun mmMask3FnmaddPh(a: M128h, b: M128h, c: M128h, k: Mmask8): M128h = run { val _simdArg0 = k; val _simdArg1 = mmFnmaddPh(a, b, c); val _simdArg2 = c; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract the intermediate
 * result from packed elements in c, and store the results in dst using zeromask k (the element is zeroed
 * out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fnmadd_ph)
 */
public fun mmMaskzFnmaddPh(k: Mmask8, a: M128h, b: M128h, c: M128h): M128h = run { val _simdArg0 = k; val _simdArg1 = mmFnmaddPh(a, b, c); val _simdArg2 = mmSetzeroPh(); _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract the intermediate
 * result from packed elements in c, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_fnmadd_ph)
 */
public fun mm256FnmaddPh(a: M256h, b: M256h, c: M256h): M256h = run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract the intermediate
 * result from packed elements in c, and store the results in dst using writemask k (the element is copied
 * from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_fnmadd_ph)
 */
public fun mm256MaskFnmaddPh(a: M256h, k: Mmask16, b: M256h, c: M256h): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256FnmaddPh(a, b, c); val _simdArg2 = a; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract the intermediate
 * result from packed elements in c, and store the results in dst using writemask k (the element is copied
 * from c when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask3_fnmadd_ph)
 */
public fun mm256Mask3FnmaddPh(a: M256h, b: M256h, c: M256h, k: Mmask16): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256FnmaddPh(a, b, c); val _simdArg2 = c; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract the intermediate
 * result from packed elements in c, and store the results in dst using zeromask k (the element is zeroed
 * out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_fnmadd_ph)
 */
public fun mm256MaskzFnmaddPh(k: Mmask16, a: M256h, b: M256h, c: M256h): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256FnmaddPh(a, b, c); val _simdArg2 = mm256SetzeroPh(); _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract the intermediate
 * result from packed elements in c, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_fnmadd_ph)
 */
public fun mm512FnmaddPh(a: M512h, b: M512h, c: M512h): M512h = run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract the intermediate
 * result from packed elements in c, and store the results in dst using writemask k (the element is copied
 * from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_fnmadd_ph)
 */
public fun mm512MaskFnmaddPh(a: M512h, k: Mmask32, b: M512h, c: M512h): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512FnmaddPh(a, b, c); val _simdArg2 = a; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract the intermediate
 * result from packed elements in c, and store the results in dst using writemask k (the element is copied
 * from c when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask3_fnmadd_ph)
 */
public fun mm512Mask3FnmaddPh(a: M512h, b: M512h, c: M512h, k: Mmask32): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512FnmaddPh(a, b, c); val _simdArg2 = c; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract the intermediate
 * result from packed elements in c, and store the results in dst using zeromask k (the element is zeroed
 * out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_fnmadd_ph)
 */
public fun mm512MaskzFnmaddPh(k: Mmask32, a: M512h, b: M512h, c: M512h): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512FnmaddPh(a, b, c); val _simdArg2 = mm512SetzeroPh(); _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract the intermediate
 * result from packed elements in c, and store the results in dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_fnmadd_round_ph)
 */
public fun mm512FnmaddRoundPh(a: M512h, b: M512h, c: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vfmaddph512(run { val _simdArg0 = a; _simdArg0 }, b, c, rounding)
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract the intermediate
 * result from packed elements in c, and store the results in dst using writemask k (the element is copied
 * from a when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_fnmadd_round_ph)
 */
public fun mm512MaskFnmaddRoundPh(a: M512h, k: Mmask32, b: M512h, c: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512FnmaddRoundPh(a, b, c, rounding); val _simdArg2 = a; _simdArg1 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract the intermediate
 * result from packed elements in c, and store the results in dst using writemask k (the element is copied
 * from c when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask3_fnmadd_round_ph)
 */
public fun mm512Mask3FnmaddRoundPh(a: M512h, b: M512h, c: M512h, k: Mmask32, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512FnmaddRoundPh(a, b, c, rounding); val _simdArg2 = c; _simdArg1 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract the intermediate
 * result from packed elements in c, and store the results in dst using zeromask k (the element is zeroed
 * out when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_fnmadd_round_ph)
 */
public fun mm512MaskzFnmaddRoundPh(k: Mmask32, a: M512h, b: M512h, c: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512FnmaddRoundPh(a, b, c, rounding); val _simdArg2 = mm512SetzeroPh(); _simdArg1 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and subtract the intermediate
 * result from the lower element in c. Store the result in the lower element of dst, and copy the upper 7 packed
 * elements from a to the upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fnmadd_sh)
 */
public fun mmFnmaddSh(a: M128h, b: M128h, c: M128h): M128h =
    run {
        val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        val extractc: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        val r = fma(-extracta, extractb, extractc)
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = r; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and subtract the intermediate
 * result from the lower element in c. Store the result in the lower element of dst using writemask k (the element
 * is copied from a when the mask bit 0 is not set), and copy the upper 7 packed elements from a to the upper
 * elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fnmadd_sh)
 */
public fun mmMaskFnmaddSh(a: M128h, k: Mmask8, b: M128h, c: M128h): M128h =
    run {
        var fnmadd: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        if (k and 1u.toUByte() != 0u.toUByte()) {
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractc: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            fnmadd = fma(-fnmadd, extractb, extractc)
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = fnmadd; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and subtract the intermediate
 * result from the lower element in c. Store the result in the lower element of dst using writemask k (the element
 * is copied from c when the mask bit 0 is not set), and copy the upper 7 packed elements from c to the upper
 * elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask3_fnmadd_sh)
 */
public fun mmMask3FnmaddSh(a: M128h, b: M128h, c: M128h, k: Mmask8): M128h =
    run {
        var fnmadd: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        if (k and 1u.toUByte() != 0u.toUByte()) {
            val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            fnmadd = fma(-extracta, extractb, fnmadd)
        }
        run { val _simdArg0 = c; val _simdArg1 = 0; val _simdArg2 = fnmadd; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and subtract the intermediate
 * result from the lower element in c. Store the result in the lower element of dst using zeromask k (the element
 * is zeroed out when the mask bit 0 is not set), and copy the upper 7 packed elements from a to the upper
 * elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fnmadd_sh)
 */
public fun mmMaskzFnmaddSh(k: Mmask8, a: M128h, b: M128h, c: M128h): M128h =
    run {
        var fnmadd: F16 = F16.fromFloat(0.0f)
        if (k and 1u.toUByte() != 0u.toUByte()) {
            val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractc: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            fnmadd = fma(-extracta, extractb, extractc)
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = fnmadd; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and subtract the intermediate
 * result from the lower element in c. Store the result in the lower element of dst, and copy the upper 7 packed
 * elements from a to the upper elements of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fnmadd_round_sh)
 */
public fun mmFnmaddRoundSh(a: M128h, b: M128h, c: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        val extractc: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        val r = vfmaddsh(-extracta, extractb, extractc, rounding)
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = r; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and subtract the intermediate
 * result from the lower element in c. Store the result in the lower element of dst using writemask k (the element
 * is copied from a when the mask bit 0 is not set), and copy the upper 7 packed elements from a to the upper
 * elements of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fnmadd_round_sh)
 */
public fun mmMaskFnmaddRoundSh(a: M128h, k: Mmask8, b: M128h, c: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        var fnmadd: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        if (k and 1u.toUByte() != 0u.toUByte()) {
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractc: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            fnmadd = vfmaddsh(-fnmadd, extractb, extractc, rounding)
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = fnmadd; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and subtract the intermediate
 * result from the lower element in c. Store the result in the lower element of dst using writemask k (the element
 * is copied from c when the mask bit 0 is not set), and copy the upper 7 packed elements from c to the upper
 * elements of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask3_fnmadd_round_sh)
 */
public fun mmMask3FnmaddRoundSh(a: M128h, b: M128h, c: M128h, k: Mmask8, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        var fnmadd: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        if (k and 1u.toUByte() != 0u.toUByte()) {
            val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            fnmadd = vfmaddsh(-extracta, extractb, fnmadd, rounding)
        }
        run { val _simdArg0 = c; val _simdArg1 = 0; val _simdArg2 = fnmadd; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and subtract the intermediate
 * result from the lower element in c. Store the result in the lower element of dst using zeromask k (the element
 * is zeroed out when the mask bit 0 is not set), and copy the upper 7 packed elements from a to the upper
 * elements of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fnmadd_round_sh)
 */
public fun mmMaskzFnmaddRoundSh(k: Mmask8, a: M128h, b: M128h, c: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        var fnmadd: F16 = F16.fromFloat(0.0f)
        if (k and 1u.toUByte() != 0u.toUByte()) {
            val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractc: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            fnmadd = vfmaddsh(-extracta, extractb, extractc, rounding)
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = fnmadd; _simdArg0 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the negated intermediate result, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fnmsub_ph)
 */
public fun mmFnmsubPh(a: M128h, b: M128h, c: M128h): M128h = run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the negated intermediate result, and store the results in dst using writemask k (the element is
 * copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fnmsub_ph)
 */
public fun mmMaskFnmsubPh(a: M128h, k: Mmask8, b: M128h, c: M128h): M128h = run { val _simdArg0 = k; val _simdArg1 = mmFnmsubPh(a, b, c); val _simdArg2 = a; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the negated intermediate result, and store the results in dst using writemask k (the element is
 * copied from c when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask3_fnmsub_ph)
 */
public fun mmMask3FnmsubPh(a: M128h, b: M128h, c: M128h, k: Mmask8): M128h = run { val _simdArg0 = k; val _simdArg1 = mmFnmsubPh(a, b, c); val _simdArg2 = c; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the negated intermediate result, and store the results in dst using zeromask k (the element is
 * zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fnmsub_ph)
 */
public fun mmMaskzFnmsubPh(k: Mmask8, a: M128h, b: M128h, c: M128h): M128h = run { val _simdArg0 = k; val _simdArg1 = mmFnmsubPh(a, b, c); val _simdArg2 = mmSetzeroPh(); _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the negated intermediate result, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_fnmsub_ph)
 */
public fun mm256FnmsubPh(a: M256h, b: M256h, c: M256h): M256h = run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the negated intermediate result, and store the results in dst using writemask k (the element is
 * copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_fnmsub_ph)
 */
public fun mm256MaskFnmsubPh(a: M256h, k: Mmask16, b: M256h, c: M256h): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256FnmsubPh(a, b, c); val _simdArg2 = a; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the negated intermediate result, and store the results in dst using writemask k (the element is
 * copied from c when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask3_fnmsub_ph)
 */
public fun mm256Mask3FnmsubPh(a: M256h, b: M256h, c: M256h, k: Mmask16): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256FnmsubPh(a, b, c); val _simdArg2 = c; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the negated intermediate result, and store the results in dst using zeromask k (the element is
 * zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_fnmsub_ph)
 */
public fun mm256MaskzFnmsubPh(k: Mmask16, a: M256h, b: M256h, c: M256h): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256FnmsubPh(a, b, c); val _simdArg2 = mm256SetzeroPh(); _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the negated intermediate result, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_fnmsub_ph)
 */
public fun mm512FnmsubPh(a: M512h, b: M512h, c: M512h): M512h = run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the negated intermediate result, and store the results in dst using writemask k (the element is
 * copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_fnmsub_ph)
 */
public fun mm512MaskFnmsubPh(a: M512h, k: Mmask32, b: M512h, c: M512h): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512FnmsubPh(a, b, c); val _simdArg2 = a; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the negated intermediate result, and store the results in dst using writemask k (the element is
 * copied from c when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask3_fnmsub_ph)
 */
public fun mm512Mask3FnmsubPh(a: M512h, b: M512h, c: M512h, k: Mmask32): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512FnmsubPh(a, b, c); val _simdArg2 = c; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the negated intermediate result, and store the results in dst using zeromask k (the element is
 * zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_fnmsub_ph)
 */
public fun mm512MaskzFnmsubPh(k: Mmask32, a: M512h, b: M512h, c: M512h): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512FnmsubPh(a, b, c); val _simdArg2 = mm512SetzeroPh(); _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the negated intermediate result, and store the results in dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_fnmsub_round_ph)
 */
public fun mm512FnmsubRoundPh(a: M512h, b: M512h, c: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vfmaddph512(run { val _simdArg0 = a; _simdArg0 }, b, run { val _simdArg0 = c; _simdArg0 }, rounding)
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the negated intermediate result, and store the results in dst using writemask k (the element is
 * copied from a when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_fnmsub_round_ph)
 */
public fun mm512MaskFnmsubRoundPh(a: M512h, k: Mmask32, b: M512h, c: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512FnmsubRoundPh(a, b, c, rounding); val _simdArg2 = a; _simdArg1 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the negated intermediate result, and store the results in dst using writemask k (the element is
 * copied from c when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask3_fnmsub_round_ph)
 */
public fun mm512Mask3FnmsubRoundPh(a: M512h, b: M512h, c: M512h, k: Mmask32, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512FnmsubRoundPh(a, b, c, rounding); val _simdArg2 = c; _simdArg1 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, subtract packed elements
 * in c from the negated intermediate result, and store the results in dst using zeromask k (the element is
 * zeroed out when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_fnmsub_round_ph)
 */
public fun mm512MaskzFnmsubRoundPh(k: Mmask32, a: M512h, b: M512h, c: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512FnmsubRoundPh(a, b, c, rounding); val _simdArg2 = mm512SetzeroPh(); _simdArg1 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and subtract the intermediate
 * result from the lower element in c. Store the result in the lower element of dst, and copy the upper 7 packed
 * elements from a to the upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fnmsub_sh)
 */
public fun mmFnmsubSh(a: M128h, b: M128h, c: M128h): M128h =
    run {
        val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        val extractc: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        val r = fma(-extracta, extractb, -extractc)
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = r; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and subtract the intermediate
 * result from the lower element in c. Store the result in the lower element of dst using writemask k (the element
 * is copied from a when the mask bit 0 is not set), and copy the upper 7 packed elements from a to the upper
 * elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fnmsub_sh)
 */
public fun mmMaskFnmsubSh(a: M128h, k: Mmask8, b: M128h, c: M128h): M128h =
    run {
        var fnmsub: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        if (k and 1u.toUByte() != 0u.toUByte()) {
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractc: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            fnmsub = fma(-fnmsub, extractb, -extractc)
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = fnmsub; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and subtract the intermediate
 * result from the lower element in c. Store the result in the lower element of dst using writemask k (the element
 * is copied from c when the mask bit 0 is not set), and copy the upper 7 packed elements from c to the upper
 * elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask3_fnmsub_sh)
 */
public fun mmMask3FnmsubSh(a: M128h, b: M128h, c: M128h, k: Mmask8): M128h =
    run {
        var fnmsub: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        if (k and 1u.toUByte() != 0u.toUByte()) {
            val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            fnmsub = fma(-extracta, extractb, -fnmsub)
        }
        run { val _simdArg0 = c; val _simdArg1 = 0; val _simdArg2 = fnmsub; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and subtract the intermediate
 * result from the lower element in c. Store the result in the lower element of dst using zeromask k (the element
 * is zeroed out when the mask bit 0 is not set), and copy the upper 7 packed elements from a to the upper
 * elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fnmsub_sh)
 */
public fun mmMaskzFnmsubSh(k: Mmask8, a: M128h, b: M128h, c: M128h): M128h =
    run {
        var fnmsub: F16 = F16.fromFloat(0.0f)
        if (k and 1u.toUByte() != 0u.toUByte()) {
            val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractc: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            fnmsub = fma(-extracta, extractb, -extractc)
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = fnmsub; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and subtract the intermediate
 * result from the lower element in c. Store the result in the lower element of dst, and copy the upper 7 packed
 * elements from a to the upper elements of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fnmsub_round_sh)
 */
public fun mmFnmsubRoundSh(a: M128h, b: M128h, c: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        val extractc: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        val r = vfmaddsh(-extracta, extractb, -extractc, rounding)
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = r; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and subtract the intermediate
 * result from the lower element in c. Store the result in the lower element of dst using writemask k (the element
 * is copied from a when the mask bit 0 is not set), and copy the upper 7 packed elements from a to the upper
 * elements of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fnmsub_round_sh)
 */
public fun mmMaskFnmsubRoundSh(a: M128h, k: Mmask8, b: M128h, c: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        var fnmsub: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        if (k and 1u.toUByte() != 0u.toUByte()) {
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractc: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            fnmsub = vfmaddsh(-fnmsub, extractb, -extractc, rounding)
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = fnmsub; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and subtract the intermediate
 * result from the lower element in c. Store the result in the lower element of dst using writemask k (the element
 * is copied from c when the mask bit 0 is not set), and copy the upper 7 packed elements from c to the upper
 * elements of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask3_fnmsub_round_sh)
 */
public fun mmMask3FnmsubRoundSh(a: M128h, b: M128h, c: M128h, k: Mmask8, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        var fnmsub: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
        if (k and 1u.toUByte() != 0u.toUByte()) {
            val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            fnmsub = vfmaddsh(-extracta, extractb, -fnmsub, rounding)
        }
        run { val _simdArg0 = c; val _simdArg1 = 0; val _simdArg2 = fnmsub; _simdArg0 }
    }

/**
 * Multiply the lower half-precision (16-bit) floating-point elements in a and b, and subtract the intermediate
 * result from the lower element in c. Store the result in the lower element of dst using zeromask k (the element
 * is zeroed out when the mask bit 0 is not set), and copy the upper 7 packed elements from a to the upper
 * elements of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fnmsub_round_sh)
 */
public fun mmMaskzFnmsubRoundSh(k: Mmask8, a: M128h, b: M128h, c: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        var fnmsub: F16 = F16.fromFloat(0.0f)
        if (k and 1u.toUByte() != 0u.toUByte()) {
            val extracta: F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractb: F16 = run { val _simdArg0 = b; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            val extractc: F16 = run { val _simdArg0 = c; val _simdArg1 = F16.fromFloat(0.0f); F16() }
            fnmsub = vfmaddsh(-extracta, extractb, -extractc, rounding)
        }
        run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = fnmsub; _simdArg0 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively add and
 * subtract packed elements in c to/from the intermediate result, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fmaddsub_ph)
 */
public fun mmFmaddsubPh(a: M128h, b: M128h, c: M128h): M128h =
    run {
        val add = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }
        val sub = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }
        run { val _simdArg0 = sub; val _simdArg1 = add; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 9u, 2u, 11u, 4u, 13u, 6u, 15u)); _simdArg0 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively add and
 * subtract packed elements in c to/from the intermediate result, and store the results in dst using writemask k
 * (the element is copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fmaddsub_ph)
 */
public fun mmMaskFmaddsubPh(a: M128h, k: Mmask8, b: M128h, c: M128h): M128h = run { val _simdArg0 = k; val _simdArg1 = mmFmaddsubPh(a, b, c); val _simdArg2 = a; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively add and
 * subtract packed elements in c to/from the intermediate result, and store the results in dst using writemask k
 * (the element is copied from c when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask3_fmaddsub_ph)
 */
public fun mmMask3FmaddsubPh(a: M128h, b: M128h, c: M128h, k: Mmask8): M128h = run { val _simdArg0 = k; val _simdArg1 = mmFmaddsubPh(a, b, c); val _simdArg2 = c; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively add and
 * subtract packed elements in c to/from the intermediate result, and store the results in dst using zeromask k
 * (the element is zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fmaddsub_ph)
 */
public fun mmMaskzFmaddsubPh(k: Mmask8, a: M128h, b: M128h, c: M128h): M128h = run { val _simdArg0 = k; val _simdArg1 = mmFmaddsubPh(a, b, c); val _simdArg2 = mmSetzeroPh(); _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively add and
 * subtract packed elements in c to/from the intermediate result, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_fmaddsub_ph)
 */
public fun mm256FmaddsubPh(a: M256h, b: M256h, c: M256h): M256h =
    run {
        val add = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }
        val sub = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }
        run { val _simdArg0 = sub; val _simdArg1 = add; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 17u, 2u, 19u, 4u, 21u, 6u, 23u, 8u, 25u, 10u, 27u, 12u, 29u, 14u, 31u)); _simdArg0 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively add and
 * subtract packed elements in c to/from the intermediate result, and store the results in dst using writemask k
 * (the element is copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_fmaddsub_ph)
 */
public fun mm256MaskFmaddsubPh(a: M256h, k: Mmask16, b: M256h, c: M256h): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256FmaddsubPh(a, b, c); val _simdArg2 = a; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively add and
 * subtract packed elements in c to/from the intermediate result, and store the results in dst using writemask k
 * (the element is copied from c when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask3_fmaddsub_ph)
 */
public fun mm256Mask3FmaddsubPh(a: M256h, b: M256h, c: M256h, k: Mmask16): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256FmaddsubPh(a, b, c); val _simdArg2 = c; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively add and
 * subtract packed elements in c to/from the intermediate result, and store the results in dst using zeromask k
 * (the element is zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_fmaddsub_ph)
 */
public fun mm256MaskzFmaddsubPh(k: Mmask16, a: M256h, b: M256h, c: M256h): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256FmaddsubPh(a, b, c); val _simdArg2 = mm256SetzeroPh(); _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively add and
 * subtract packed elements in c to/from the intermediate result, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_fmaddsub_ph)
 */
public fun mm512FmaddsubPh(a: M512h, b: M512h, c: M512h): M512h =
    run {
        val add = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }
        val sub = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }
        run { val _simdArg0 = sub; val _simdArg1 = add; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 33u, 2u, 35u, 4u, 37u, 6u, 39u, 8u, 41u, 10u, 43u, 12u, 45u, 14u, 47u, 16u, 49u, 18u, 51u, 20u, 53u, 22u, 55u, 24u, 57u, 26u, 59u, 28u, 61u, 30u, 63u)); _simdArg0 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively add and
 * subtract packed elements in c to/from the intermediate result, and store the results in dst using writemask k
 * (the element is copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_fmaddsub_ph)
 */
public fun mm512MaskFmaddsubPh(a: M512h, k: Mmask32, b: M512h, c: M512h): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512FmaddsubPh(a, b, c); val _simdArg2 = a; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively add and
 * subtract packed elements in c to/from the intermediate result, and store the results in dst using writemask k
 * (the element is copied from c when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask3_fmaddsub_ph)
 */
public fun mm512Mask3FmaddsubPh(a: M512h, b: M512h, c: M512h, k: Mmask32): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512FmaddsubPh(a, b, c); val _simdArg2 = c; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively add and
 * subtract packed elements in c to/from the intermediate result, and store the results in dst using zeromask k
 * (the element is zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_fmaddsub_ph)
 */
public fun mm512MaskzFmaddsubPh(k: Mmask32, a: M512h, b: M512h, c: M512h): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512FmaddsubPh(a, b, c); val _simdArg2 = mm512SetzeroPh(); _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively add and
 * subtract packed elements in c to/from the intermediate result, and store the results in dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_fmaddsub_round_ph)
 */
public fun mm512FmaddsubRoundPh(a: M512h, b: M512h, c: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vfmaddsubph512(a, b, c, rounding)
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively add and
 * subtract packed elements in c to/from the intermediate result, and store the results in dst using writemask k
 * (the element is copied from a when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_fmaddsub_round_ph)
 */
public fun mm512MaskFmaddsubRoundPh(a: M512h, k: Mmask32, b: M512h, c: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512FmaddsubRoundPh(a, b, c, rounding); val _simdArg2 = a; _simdArg1 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively add and
 * subtract packed elements in c to/from the intermediate result, and store the results in dst using writemask k
 * (the element is copied from c when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask3_fmaddsub_round_ph)
 */
public fun mm512Mask3FmaddsubRoundPh(a: M512h, b: M512h, c: M512h, k: Mmask32, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512FmaddsubRoundPh(a, b, c, rounding); val _simdArg2 = c; _simdArg1 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively add and
 * subtract packed elements in c to/from the intermediate result, and store the results in dst using zeromask k
 * (the element is zeroed out when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_fmaddsub_round_ph)
 */
public fun mm512MaskzFmaddsubRoundPh(k: Mmask32, a: M512h, b: M512h, c: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512FmaddsubRoundPh(a, b, c, rounding); val _simdArg2 = mm512SetzeroPh(); _simdArg1 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively subtract
 * and add packed elements in c to/from the intermediate result, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fmsubadd_ph)
 */
public fun mmFmsubaddPh(a: M128h, b: M128h, c: M128h): M128h = mmFmaddsubPh(a, b, run { val _simdArg0 = c; _simdArg0 })

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively subtract
 * and add packed elements in c to/from the intermediate result, and store the results in dst using writemask k
 * (the element is copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fmsubadd_ph)
 */
public fun mmMaskFmsubaddPh(a: M128h, k: Mmask8, b: M128h, c: M128h): M128h = run { val _simdArg0 = k; val _simdArg1 = mmFmsubaddPh(a, b, c); val _simdArg2 = a; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively subtract
 * and add packed elements in c to/from the intermediate result, and store the results in dst using writemask k
 * (the element is copied from c when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask3_fmsubadd_ph)
 */
public fun mmMask3FmsubaddPh(a: M128h, b: M128h, c: M128h, k: Mmask8): M128h = run { val _simdArg0 = k; val _simdArg1 = mmFmsubaddPh(a, b, c); val _simdArg2 = c; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively subtract
 * and add packed elements in c to/from the intermediate result, and store the results in dst using zeromask k
 * (the element is zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_fmsubadd_ph)
 */
public fun mmMaskzFmsubaddPh(k: Mmask8, a: M128h, b: M128h, c: M128h): M128h = run { val _simdArg0 = k; val _simdArg1 = mmFmsubaddPh(a, b, c); val _simdArg2 = mmSetzeroPh(); _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively subtract
 * and add packed elements in c to/from the intermediate result, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_fmsubadd_ph)
 */
public fun mm256FmsubaddPh(a: M256h, b: M256h, c: M256h): M256h = mm256FmaddsubPh(a, b, run { val _simdArg0 = c; _simdArg0 })

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively subtract
 * and add packed elements in c to/from the intermediate result, and store the results in dst using writemask k
 * (the element is copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_fmsubadd_ph)
 */
public fun mm256MaskFmsubaddPh(a: M256h, k: Mmask16, b: M256h, c: M256h): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256FmsubaddPh(a, b, c); val _simdArg2 = a; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively subtract
 * and add packed elements in c to/from the intermediate result, and store the results in dst using writemask k
 * (the element is copied from c when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask3_fmsubadd_ph)
 */
public fun mm256Mask3FmsubaddPh(a: M256h, b: M256h, c: M256h, k: Mmask16): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256FmsubaddPh(a, b, c); val _simdArg2 = c; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively subtract
 * and add packed elements in c to/from the intermediate result, and store the results in dst using zeromask k
 * (the element is zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_fmsubadd_ph)
 */
public fun mm256MaskzFmsubaddPh(k: Mmask16, a: M256h, b: M256h, c: M256h): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256FmsubaddPh(a, b, c); val _simdArg2 = mm256SetzeroPh(); _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively subtract
 * and add packed elements in c to/from the intermediate result, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_fmsubadd_ph)
 */
public fun mm512FmsubaddPh(a: M512h, b: M512h, c: M512h): M512h = mm512FmaddsubPh(a, b, run { val _simdArg0 = c; _simdArg0 })

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively subtract
 * and add packed elements in c to/from the intermediate result, and store the results in dst using writemask k
 * (the element is copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_fmsubadd_ph)
 */
public fun mm512MaskFmsubaddPh(a: M512h, k: Mmask32, b: M512h, c: M512h): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512FmsubaddPh(a, b, c); val _simdArg2 = a; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively subtract
 * and add packed elements in c to/from the intermediate result, and store the results in dst using writemask k
 * (the element is copied from c when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask3_fmsubadd_ph)
 */
public fun mm512Mask3FmsubaddPh(a: M512h, b: M512h, c: M512h, k: Mmask32): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512FmsubaddPh(a, b, c); val _simdArg2 = c; _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively subtract
 * and add packed elements in c to/from the intermediate result, and store the results in dst using zeromask k
 * (the element is zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_fmsubadd_ph)
 */
public fun mm512MaskzFmsubaddPh(k: Mmask32, a: M512h, b: M512h, c: M512h): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512FmsubaddPh(a, b, c); val _simdArg2 = mm512SetzeroPh(); _simdArg1 }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively subtract
 * and add packed elements in c to/from the intermediate result, and store the results in dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_fmsubadd_round_ph)
 */
public fun mm512FmsubaddRoundPh(a: M512h, b: M512h, c: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vfmaddsubph512(a, b, run { val _simdArg0 = c; _simdArg0 }, rounding)
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively subtract
 * and add packed elements in c to/from the intermediate result, and store the results in dst using writemask k
 * (the element is copied from a when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_fmsubadd_round_ph)
 */
public fun mm512MaskFmsubaddRoundPh(a: M512h, k: Mmask32, b: M512h, c: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512FmsubaddRoundPh(a, b, c, rounding); val _simdArg2 = a; _simdArg1 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively subtract
 * and add packed elements in c to/from the intermediate result, and store the results in dst using writemask k
 * (the element is copied from c when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask3_fmsubadd_round_ph)
 */
public fun mm512Mask3FmsubaddRoundPh(a: M512h, b: M512h, c: M512h, k: Mmask32, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512FmsubaddRoundPh(a, b, c, rounding); val _simdArg2 = c; _simdArg1 }
    }

/**
 * Multiply packed half-precision (16-bit) floating-point elements in a and b, alternatively subtract
 * and add packed elements in c to/from the intermediate result, and store the results in dst using zeromask k
 * (the element is zeroed out when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_fmsubadd_round_ph)
 */
public fun mm512MaskzFmsubaddRoundPh(k: Mmask32, a: M512h, b: M512h, c: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512FmsubaddRoundPh(a, b, c, rounding); val _simdArg2 = mm512SetzeroPh(); _simdArg1 }
    }

/**
 * Compute the approximate reciprocal of packed 16-bit floating-point elements in `a` and stores the results in `dst`.
 * The maximum relative error for this approximation is less than `1.5 * 2 ^ - 12`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_rcp_ph)
 */
public fun mmRcpPh(a: M128h): M128h = mmMaskRcpPh(mmUndefinedPh(), 0xffu, a)

/**
 * Compute the approximate reciprocal of packed 16-bit floating-point elements in `a` and stores the results in `dst`
 * using writemask `k` (elements are copied from `src` when the corresponding mask bit is not set).
 * The maximum relative error for this approximation is less than `1.5 * 2 ^ - 12`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_rcp_ph)
 */
public fun mmMaskRcpPh(src: M128h, k: Mmask8, a: M128h): M128h = vrcpph128(a, src, k)

/**
 * Compute the approximate reciprocal of packed 16-bit floating-point elements in `a` and stores the results in `dst`
 * using zeromask `k` (elements are zeroed out when the corresponding mask bit is not set).
 * The maximum relative error for this approximation is less than `1.5 * 2 ^ - 12`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_rcp_ph)
 */
public fun mmMaskzRcpPh(k: Mmask8, a: M128h): M128h = mmMaskRcpPh(mmSetzeroPh(), k, a)

/**
 * Compute the approximate reciprocal of packed 16-bit floating-point elements in `a` and stores the results in `dst`.
 * The maximum relative error for this approximation is less than `1.5 * 2 ^ - 12`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_rcp_ph)
 */
public fun mm256RcpPh(a: M256h): M256h = mm256MaskRcpPh(mm256UndefinedPh(), 0xffffu, a)

/**
 * Compute the approximate reciprocal of packed 16-bit floating-point elements in `a` and stores the results in `dst`
 * using writemask `k` (elements are copied from `src` when the corresponding mask bit is not set).
 * The maximum relative error for this approximation is less than `1.5 * 2 ^ - 12`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_rcp_ph)
 */
public fun mm256MaskRcpPh(src: M256h, k: Mmask16, a: M256h): M256h = vrcpph256(a, src, k)

/**
 * Compute the approximate reciprocal of packed 16-bit floating-point elements in `a` and stores the results in `dst`
 * using zeromask `k` (elements are zeroed out when the corresponding mask bit is not set).
 * The maximum relative error for this approximation is less than `1.5 * 2 ^ - 12`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_rcp_ph)
 */
public fun mm256MaskzRcpPh(k: Mmask16, a: M256h): M256h = mm256MaskRcpPh(mm256SetzeroPh(), k, a)

/**
 * Compute the approximate reciprocal of packed 16-bit floating-point elements in `a` and stores the results in `dst`.
 * The maximum relative error for this approximation is less than `1.5 * 2 ^ - 12`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_rcp_ph)
 */
public fun mm512RcpPh(a: M512h): M512h = mm512MaskRcpPh(mm512UndefinedPh(), 0xffffffffu, a)

/**
 * Compute the approximate reciprocal of packed 16-bit floating-point elements in `a` and stores the results in `dst`
 * using writemask `k` (elements are copied from `src` when the corresponding mask bit is not set).
 * The maximum relative error for this approximation is less than `1.5 * 2 ^ - 12`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_rcp_ph)
 */
public fun mm512MaskRcpPh(src: M512h, k: Mmask32, a: M512h): M512h = vrcpph512(a, src, k)

/**
 * Compute the approximate reciprocal of packed 16-bit floating-point elements in `a` and stores the results in `dst`
 * using zeromask `k` (elements are zeroed out when the corresponding mask bit is not set).
 * The maximum relative error for this approximation is less than `1.5 * 2 ^ - 12`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_rcp_ph)
 */
public fun mm512MaskzRcpPh(k: Mmask32, a: M512h): M512h = mm512MaskRcpPh(mm512SetzeroPh(), k, a)

/**
 * Compute the approximate reciprocal of the lower half-precision (16-bit) floating-point element in b,
 * store the result in the lower element of dst, and copy the upper 7 packed elements from a to the
 * upper elements of dst.
 * The maximum relative error for this approximation is less than `1.5 * 2 ^ - 12`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_rcp_sh)
 */
public fun mmRcpSh(a: M128h, b: M128h): M128h = mmMaskRcpSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b)

/**
 * Compute the approximate reciprocal of the lower half-precision (16-bit) floating-point element in b,
 * store the result in the lower element of dst using writemask k (the element is copied from src when
 * mask bit 0 is not set), and copy the upper 7 packed elements from a to the upper elements of dst.
 * The maximum relative error for this approximation is less than `1.5 * 2 ^ - 12`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_rcp_sh)
 */
public fun mmMaskRcpSh(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h = vrcpsh(a, b, src, k)

/**
 * Compute the approximate reciprocal of the lower half-precision (16-bit) floating-point element in b,
 * store the result in the lower element of dst using zeromask k (the element is zeroed out when mask bit 0
 * is not set), and copy the upper 7 packed elements from a to the upper elements of dst.
 * The maximum relative error for this approximation is less than `1.5 * 2 ^ - 12`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_rcp_sh)
 */
public fun mmMaskzRcpSh(k: Mmask8, a: M128h, b: M128h): M128h = mmMaskRcpSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b)

/**
 * Compute the approximate reciprocal square root of packed half-precision (16-bit) floating-point
 * elements in a, and store the results in dst.
 * The maximum relative error for this approximation is less than `1.5 * 2 ^ - 12`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_rsqrt_ph)
 */
public fun mmRsqrtPh(a: M128h): M128h = mmMaskRsqrtPh(mmUndefinedPh(), 0xffu, a)

/**
 * Compute the approximate reciprocal square root of packed half-precision (16-bit) floating-point
 * elements in a, and store the results in dst using writemask k (elements are copied from src when
 * the corresponding mask bit is not set).
 * The maximum relative error for this approximation is less than `1.5 * 2 ^ - 12`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_rsqrt_ph)
 */
public fun mmMaskRsqrtPh(src: M128h, k: Mmask8, a: M128h): M128h = vrsqrtph128(a, src, k)

/**
 * Compute the approximate reciprocal square root of packed half-precision (16-bit) floating-point
 * elements in a, and store the results in dst using zeromask k (elements are zeroed out when the
 * corresponding mask bit is not set).
 * The maximum relative error for this approximation is less than `1.5 * 2 ^ - 12`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_rsqrt_ph)
 */
public fun mmMaskzRsqrtPh(k: Mmask8, a: M128h): M128h = mmMaskRsqrtPh(mmSetzeroPh(), k, a)

/**
 * Compute the approximate reciprocal square root of packed half-precision (16-bit) floating-point
 * elements in a, and store the results in dst.
 * The maximum relative error for this approximation is less than `1.5 * 2 ^ - 12`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_rsqrt_ph)
 */
public fun mm256RsqrtPh(a: M256h): M256h = mm256MaskRsqrtPh(mm256UndefinedPh(), 0xffffu, a)

/**
 * Compute the approximate reciprocal square root of packed half-precision (16-bit) floating-point
 * elements in a, and store the results in dst using writemask k (elements are copied from src when
 * the corresponding mask bit is not set).
 * The maximum relative error for this approximation is less than `1.5 * 2 ^ - 12`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_rsqrt_ph)
 */
public fun mm256MaskRsqrtPh(src: M256h, k: Mmask16, a: M256h): M256h = vrsqrtph256(a, src, k)

/**
 * Compute the approximate reciprocal square root of packed half-precision (16-bit) floating-point
 * elements in a, and store the results in dst using zeromask k (elements are zeroed out when the
 * corresponding mask bit is not set).
 * The maximum relative error for this approximation is less than `1.5 * 2 ^ - 12`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_rsqrt_ph)
 */
public fun mm256MaskzRsqrtPh(k: Mmask16, a: M256h): M256h = mm256MaskRsqrtPh(mm256SetzeroPh(), k, a)

/**
 * Compute the approximate reciprocal square root of packed half-precision (16-bit) floating-point
 * elements in a, and store the results in dst.
 * The maximum relative error for this approximation is less than `1.5 * 2 ^ - 12`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_rsqrt_ph)
 */
public fun mm512RsqrtPh(a: M512h): M512h = mm512MaskRsqrtPh(mm512UndefinedPh(), 0xffffffffu, a)

/**
 * Compute the approximate reciprocal square root of packed half-precision (16-bit) floating-point
 * elements in a, and store the results in dst using writemask k (elements are copied from src when
 * the corresponding mask bit is not set).
 * The maximum relative error for this approximation is less than `1.5 * 2 ^ - 12`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_rsqrt_ph)
 */
public fun mm512MaskRsqrtPh(src: M512h, k: Mmask32, a: M512h): M512h = vrsqrtph512(a, src, k)

/**
 * Compute the approximate reciprocal square root of packed half-precision (16-bit) floating-point
 * elements in a, and store the results in dst using zeromask k (elements are zeroed out when the
 * corresponding mask bit is not set).
 * The maximum relative error for this approximation is less than `1.5 * 2 ^ - 12`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_rsqrt_ph)
 */
public fun mm512MaskzRsqrtPh(k: Mmask32, a: M512h): M512h = mm512MaskRsqrtPh(mm512SetzeroPh(), k, a)

/**
 * Compute the approximate reciprocal square root of the lower half-precision (16-bit) floating-point
 * element in b, store the result in the lower element of dst, and copy the upper 7 packed elements from a
 * to the upper elements of dst.
 * The maximum relative error for this approximation is less than `1.5 * 2 ^ - 12`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_rsqrt_sh)
 */
public fun mmRsqrtSh(a: M128h, b: M128h): M128h = mmMaskRsqrtSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b)

/**
 * Compute the approximate reciprocal square root of the lower half-precision (16-bit) floating-point
 * element in b, store the result in the lower element of dst using writemask k (the element is copied from src
 * when mask bit 0 is not set), and copy the upper 7 packed elements from a to the upper elements of dst.
 * The maximum relative error for this approximation is less than `1.5 * 2 ^ - 12`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_rsqrt_sh)
 */
public fun mmMaskRsqrtSh(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h = vrsqrtsh(a, b, src, k)

/**
 * Compute the approximate reciprocal square root of the lower half-precision (16-bit) floating-point
 * element in b, store the result in the lower element of dst using zeromask k (the element is zeroed out when
 * mask bit 0 is not set), and copy the upper 7 packed elements from a to the upper elements of dst.
 * The maximum relative error for this approximation is less than `1.5 * 2 ^ - 12`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_rsqrt_sh)
 */
public fun mmMaskzRsqrtSh(k: Mmask8, a: M128h, b: M128h): M128h = mmMaskRsqrtSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b)

/**
 * Compute the square root of packed half-precision (16-bit) floating-point elements in a, and store the
 * results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_sqrt_ph)
 */
public fun mmSqrtPh(a: M128h): M128h = run { val _simdArg0 = a; _simdArg0 }

/**
 * Compute the square root of packed half-precision (16-bit) floating-point elements in a, and store the
 * results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_sqrt_ph)
 */
public fun mmMaskSqrtPh(src: M128h, k: Mmask8, a: M128h): M128h = run { val _simdArg0 = k; val _simdArg1 = mmSqrtPh(a); val _simdArg2 = src; _simdArg1 }

/**
 * Compute the square root of packed half-precision (16-bit) floating-point elements in a, and store the
 * results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_sqrt_ph)
 */
public fun mmMaskzSqrtPh(k: Mmask8, a: M128h): M128h = run { val _simdArg0 = k; val _simdArg1 = mmSqrtPh(a); val _simdArg2 = mmSetzeroPh(); _simdArg1 }

/**
 * Compute the square root of packed half-precision (16-bit) floating-point elements in a, and store the
 * results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_sqrt_ph)
 */
public fun mm256SqrtPh(a: M256h): M256h = run { val _simdArg0 = a; _simdArg0 }

/**
 * Compute the square root of packed half-precision (16-bit) floating-point elements in a, and store the
 * results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_sqrt_ph)
 */
public fun mm256MaskSqrtPh(src: M256h, k: Mmask16, a: M256h): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256SqrtPh(a); val _simdArg2 = src; _simdArg1 }

/**
 * Compute the square root of packed half-precision (16-bit) floating-point elements in a, and store the
 * results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_sqrt_ph)
 */
public fun mm256MaskzSqrtPh(k: Mmask16, a: M256h): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256SqrtPh(a); val _simdArg2 = mm256SetzeroPh(); _simdArg1 }

/**
 * Compute the square root of packed half-precision (16-bit) floating-point elements in a, and store the
 * results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_sqrt_ph)
 */
public fun mm512SqrtPh(a: M512h): M512h = run { val _simdArg0 = a; _simdArg0 }

/**
 * Compute the square root of packed half-precision (16-bit) floating-point elements in a, and store the
 * results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_sqrt_ph)
 */
public fun mm512MaskSqrtPh(src: M512h, k: Mmask32, a: M512h): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512SqrtPh(a); val _simdArg2 = src; _simdArg1 }

/**
 * Compute the square root of packed half-precision (16-bit) floating-point elements in a, and store the
 * results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_sqrt_ph)
 */
public fun mm512MaskzSqrtPh(k: Mmask32, a: M512h): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512SqrtPh(a); val _simdArg2 = mm512SetzeroPh(); _simdArg1 }

/**
 * Compute the square root of packed half-precision (16-bit) floating-point elements in a, and store the
 * results in dst.
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_sqrt_round_ph)
 */
public fun mm512SqrtRoundPh(a: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vsqrtph512(a, rounding)
    }

/**
 * Compute the square root of packed half-precision (16-bit) floating-point elements in a, and store the
 * results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_sqrt_round_ph)
 */
public fun mm512MaskSqrtRoundPh(src: M512h, k: Mmask32, a: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512SqrtRoundPh(a, rounding); val _simdArg2 = src; _simdArg1 }
    }

/**
 * Compute the square root of packed half-precision (16-bit) floating-point elements in a, and store the
 * results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_sqrt_round_ph)
 */
public fun mm512MaskzSqrtRoundPh(k: Mmask32, a: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512SqrtRoundPh(a, rounding); val _simdArg2 = mm512SetzeroPh(); _simdArg1 }
    }

/**
 * Compute the square root of the lower half-precision (16-bit) floating-point element in b, store
 * the result in the lower element of dst, and copy the upper 7 packed elements from a to the upper
 * elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_sqrt_sh)
 */
public fun mmSqrtSh(a: M128h, b: M128h): M128h = mmMaskSqrtSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b)

/**
 * Compute the square root of the lower half-precision (16-bit) floating-point element in b, store
 * the result in the lower element of dst using writemask k (the element is copied from src when mask
 * bit 0 is not set), and copy the upper 7 packed elements from a to the upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_sqrt_sh)
 */
public fun mmMaskSqrtSh(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h = mmMaskSqrtRoundSh<MmFroundCurDirection>(src, k, a, b)

/**
 * Compute the square root of the lower half-precision (16-bit) floating-point element in b, store
 * the result in the lower element of dst using zeromask k (the element is zeroed out when mask bit 0
 * is not set), and copy the upper 7 packed elements from a to the upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_sqrt_sh)
 */
public fun mmMaskzSqrtSh(k: Mmask8, a: M128h, b: M128h): M128h = mmMaskSqrtSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b)

/**
 * Compute the square root of the lower half-precision (16-bit) floating-point element in b, store
 * the result in the lower element of dst, and copy the upper 7 packed elements from a to the upper
 * elements of dst.
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_sqrt_round_sh)
 */
public fun mmSqrtRoundSh(a: M128h, b: M128h, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmMaskSqrtRoundSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b, rounding)
}

/**
 * Compute the square root of the lower half-precision (16-bit) floating-point element in b, store
 * the result in the lower element of dst using writemask k (the element is copied from src when mask
 * bit 0 is not set), and copy the upper 7 packed elements from a to the upper elements of dst.
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_sqrt_round_sh)
 */
public fun mmMaskSqrtRoundSh(src: M128h, k: Mmask8, a: M128h, b: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vsqrtsh(a, b, src, k, rounding)
    }

/**
 * Compute the square root of the lower half-precision (16-bit) floating-point element in b, store
 * the result in the lower element of dst using zeromask k (the element is zeroed out when mask bit 0
 * is not set), and copy the upper 7 packed elements from a to the upper elements of dst.
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_sqrt_round_sh)
 */
public fun mmMaskzSqrtRoundSh(k: Mmask8, a: M128h, b: M128h, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmMaskSqrtRoundSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b, rounding)
}

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b, and store packed maximum
 * values in dst. Does not follow the IEEE Standard for Floating-Point Arithmetic (IEEE 754) maximum
 * value when inputs are NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_max_ph)
 */
public fun mmMaxPh(a: M128h, b: M128h): M128h = vmaxph128(a, b)

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b, and store packed maximum
 * values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 * Does not follow the IEEE Standard for Floating-Point Arithmetic (IEEE 754) maximum value when inputs are
 * NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_max_ph)
 */
public fun mmMaskMaxPh(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h = run { val _simdArg0 = k; val _simdArg1 = mmMaxPh(a, b); val _simdArg2 = src; _simdArg1 }

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b, and store packed maximum
 * values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 * Does not follow the IEEE Standard for Floating-Point Arithmetic (IEEE 754) maximum value when inputs are
 * NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_max_ph)
 */
public fun mmMaskzMaxPh(k: Mmask8, a: M128h, b: M128h): M128h = run { val _simdArg0 = k; val _simdArg1 = mmMaxPh(a, b); val _simdArg2 = mmSetzeroPh(); _simdArg1 }

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b, and store packed maximum
 * values in dst. Does not follow the IEEE Standard for Floating-Point Arithmetic (IEEE 754) maximum
 * value when inputs are NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_max_ph)
 */
public fun mm256MaxPh(a: M256h, b: M256h): M256h = vmaxph256(a, b)

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b, and store packed maximum
 * values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 * Does not follow the IEEE Standard for Floating-Point Arithmetic (IEEE 754) maximum value when inputs are
 * NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_max_ph)
 */
public fun mm256MaskMaxPh(src: M256h, k: Mmask16, a: M256h, b: M256h): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256MaxPh(a, b); val _simdArg2 = src; _simdArg1 }

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b, and store packed maximum
 * values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 * Does not follow the IEEE Standard for Floating-Point Arithmetic (IEEE 754) maximum value when inputs are
 * NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_max_ph)
 */
public fun mm256MaskzMaxPh(k: Mmask16, a: M256h, b: M256h): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256MaxPh(a, b); val _simdArg2 = mm256SetzeroPh(); _simdArg1 }

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b, and store packed maximum
 * values in dst. Does not follow the IEEE Standard for Floating-Point Arithmetic (IEEE 754) maximum
 * value when inputs are NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_max_ph)
 */
public fun mm512MaxPh(a: M512h, b: M512h): M512h = mm512MaxRoundPh<MmFroundCurDirection>(a, b)

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b, and store packed maximum
 * values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 * Does not follow the IEEE Standard for Floating-Point Arithmetic (IEEE 754) maximum value when inputs are
 * NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_max_ph)
 */
public fun mm512MaskMaxPh(src: M512h, k: Mmask32, a: M512h, b: M512h): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512MaxPh(a, b); val _simdArg2 = src; _simdArg1 }

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b, and store packed maximum
 * values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 * Does not follow the IEEE Standard for Floating-Point Arithmetic (IEEE 754) maximum value when inputs are
 * NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_max_ph)
 */
public fun mm512MaskzMaxPh(k: Mmask32, a: M512h, b: M512h): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512MaxPh(a, b); val _simdArg2 = mm512SetzeroPh(); _simdArg1 }

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b, and store packed maximum
 * values in dst. Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 * Does not follow the IEEE Standard for Floating-Point Arithmetic (IEEE 754) maximum value when inputs are
 * NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_max_round_ph)
 */
public fun mm512MaxRoundPh(a: M512h, b: M512h, sae: Int): M512h =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        vmaxph512(a, b, sae)
    }

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b, and store packed maximum
 * values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter. Does not follow the
 * IEEE Standard for Floating-Point Arithmetic (IEEE 754) maximum value when inputs are NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_max_round_ph)
 */
public fun mm512MaskMaxRoundPh(src: M512h, k: Mmask32, a: M512h, b: M512h, sae: Int): M512h =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512MaxRoundPh(a, b, sae); val _simdArg2 = src; _simdArg1 }
    }

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b, and store packed maximum
 * values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter. Does not follow the
 * IEEE Standard for Floating-Point Arithmetic (IEEE 754) maximum value when inputs are NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_max_round_ph)
 */
public fun mm512MaskzMaxRoundPh(k: Mmask32, a: M512h, b: M512h, sae: Int): M512h =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512MaxRoundPh(a, b, sae); val _simdArg2 = mm512SetzeroPh(); _simdArg1 }
    }

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b, store the maximum
 * value in the lower element of dst, and copy the upper 7 packed elements from a to the upper elements
 * of dst. Does not follow the IEEE Standard for Floating-Point Arithmetic (IEEE 754) maximum value
 * when inputs are NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_max_sh)
 */
public fun mmMaxSh(a: M128h, b: M128h): M128h = mmMaskMaxSh(mmUndefinedPh(), 0xffu, a, b)

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b, store the maximum
 * value in the lower element of dst using writemask k (the element is copied from src when mask bit 0
 * is not set), and copy the upper 7 packed elements from a to the upper elements of dst. Does not follow
 * the IEEE Standard for Floating-Point Arithmetic (IEEE 754) maximum value when inputs are NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_max_sh)
 */
public fun mmMaskMaxSh(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h = mmMaskMaxRoundSh<MmFroundCurDirection>(src, k, a, b)

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b, store the maximum value
 * in the lower element of dst using zeromask k (the element is zeroed out when mask bit 0 is not set), and
 * copy the upper 7 packed elements from a to the upper elements of dst. Does not follow the IEEE Standard
 * for Floating-Point Arithmetic (IEEE 754) maximum value when inputs are NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_max_sh)
 */
public fun mmMaskzMaxSh(k: Mmask8, a: M128h, b: M128h): M128h = mmMaskMaxSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b)

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b, store the maximum value
 * in the lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst.
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter. Does not follow the
 * IEEE Standard for Floating-Point Arithmetic (IEEE 754) maximum value when inputs are NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_max_round_sh)
 */
public fun mmMaxRoundSh(a: M128h, b: M128h, sae: Int): M128h {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mmMaskMaxRoundSh(mmUndefinedPh(), 0xffu, a, b, sae)
}

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b, store the maximum value
 * in the lower element of dst using writemask k (the element is copied from src when mask bit 0 is not set),
 * and copy the upper 7 packed elements from a to the upper elements of dst. Exceptions can be suppressed by
 * passing _MM_FROUND_NO_EXC in the sae parameter. Does not follow the IEEE Standard for Floating-Point Arithmetic
 * (IEEE 754) maximum value when inputs are NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_max_round_sh)
 */
public fun mmMaskMaxRoundSh(src: M128h, k: Mmask8, a: M128h, b: M128h, sae: Int): M128h =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        vmaxsh(a, b, src, k, sae)
    }

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b, store the maximum value
 * in the lower element of dst using zeromask k (the element is zeroed out when mask bit 0 is not set), and
 * copy the upper 7 packed elements from a to the upper elements of dst. Exceptions can be suppressed by
 * passing _MM_FROUND_NO_EXC in the sae parameter. Does not follow the IEEE Standard for Floating-Point Arithmetic
 * (IEEE 754) maximum value when inputs are NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_max_round_sh)
 */
public fun mmMaskzMaxRoundSh(k: Mmask8, a: M128h, b: M128h, sae: Int): M128h {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mmMaskMaxRoundSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b, sae)
}

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b, and store packed minimum
 * values in dst. Does not follow the IEEE Standard for Floating-Point Arithmetic (IEEE 754) minimum value
 * when inputs are NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_min_ph)
 */
public fun mmMinPh(a: M128h, b: M128h): M128h = vminph128(a, b)

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b, and store packed minimum
 * values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 * Does not follow the IEEE Standard for Floating-Point Arithmetic (IEEE 754) minimum value when inputs are
 * NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_min_ph)
 */
public fun mmMaskMinPh(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h = run { val _simdArg0 = k; val _simdArg1 = mmMinPh(a, b); val _simdArg2 = src; _simdArg1 }

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b, and store packed minimum
 * values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 * Does not follow the IEEE Standard for Floating-Point Arithmetic (IEEE 754) minimum value when inputs are
 * NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_min_ph)
 */
public fun mmMaskzMinPh(k: Mmask8, a: M128h, b: M128h): M128h = run { val _simdArg0 = k; val _simdArg1 = mmMinPh(a, b); val _simdArg2 = mmSetzeroPh(); _simdArg1 }

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b, and store packed minimum
 * values in dst. Does not follow the IEEE Standard for Floating-Point Arithmetic (IEEE 754) minimum value
 * when inputs are NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_min_ph)
 */
public fun mm256MinPh(a: M256h, b: M256h): M256h = vminph256(a, b)

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b, and store packed minimum
 * values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 * Does not follow the IEEE Standard for Floating-Point Arithmetic (IEEE 754) minimum value when inputs are
 * NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_min_ph)
 */
public fun mm256MaskMinPh(src: M256h, k: Mmask16, a: M256h, b: M256h): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256MinPh(a, b); val _simdArg2 = src; _simdArg1 }

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b, and store packed minimum
 * values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 * Does not follow the IEEE Standard for Floating-Point Arithmetic (IEEE 754) minimum value when inputs are
 * NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_min_ph)
 */
public fun mm256MaskzMinPh(k: Mmask16, a: M256h, b: M256h): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256MinPh(a, b); val _simdArg2 = mm256SetzeroPh(); _simdArg1 }

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b, and store packed minimum
 * values in dst. Does not follow the IEEE Standard for Floating-Point Arithmetic (IEEE 754) minimum value
 * when inputs are NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_min_ph)
 */
public fun mm512MinPh(a: M512h, b: M512h): M512h = mm512MinRoundPh<MmFroundCurDirection>(a, b)

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b, and store packed minimum
 * values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 * Does not follow the IEEE Standard for Floating-Point Arithmetic (IEEE 754) minimum value when inputs are
 * NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_min_ph)
 */
public fun mm512MaskMinPh(src: M512h, k: Mmask32, a: M512h, b: M512h): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512MinPh(a, b); val _simdArg2 = src; _simdArg1 }

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b, and store packed minimum
 * values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 * Does not follow the IEEE Standard for Floating-Point Arithmetic (IEEE 754) minimum value when inputs are
 * NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_min_ph)
 */
public fun mm512MaskzMinPh(k: Mmask32, a: M512h, b: M512h): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512MinPh(a, b); val _simdArg2 = mm512SetzeroPh(); _simdArg1 }

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b, and store packed minimum
 * values in dst. Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter. Does not
 * follow the IEEE Standard for Floating-Point Arithmetic (IEEE 754) minimum value when inputs are NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_min_round_ph)
 */
public fun mm512MinRoundPh(a: M512h, b: M512h, sae: Int): M512h =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        vminph512(a, b, sae)
    }

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b, and store packed minimum
 * values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter. Does not follow the
 * IEEE Standard for Floating-Point Arithmetic (IEEE 754) minimum value when inputs are NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_min_round_ph)
 */
public fun mm512MaskMinRoundPh(src: M512h, k: Mmask32, a: M512h, b: M512h, sae: Int): M512h =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512MinRoundPh(a, b, sae); val _simdArg2 = src; _simdArg1 }
    }

/**
 * Compare packed half-precision (16-bit) floating-point elements in a and b, and store packed minimum
 * values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter. Does not follow the
 * IEEE Standard for Floating-Point Arithmetic (IEEE 754) minimum value when inputs are NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_min_round_ph)
 */
public fun mm512MaskzMinRoundPh(k: Mmask32, a: M512h, b: M512h, sae: Int): M512h =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512MinRoundPh(a, b, sae); val _simdArg2 = mm512SetzeroPh(); _simdArg1 }
    }

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b, store the minimum
 * value in the lower element of dst, and copy the upper 7 packed elements from a to the upper elements
 * of dst. Does not follow the IEEE Standard for Floating-Point Arithmetic (IEEE 754) minimum value when
 * inputs are NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_min_sh)
 */
public fun mmMinSh(a: M128h, b: M128h): M128h = mmMaskMinSh(mmUndefinedPh(), 0xffu, a, b)

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b, store the minimum
 * value in the lower element of dst using writemask k (the element is copied from src when mask bit 0
 * is not set), and copy the upper 7 packed elements from a to the upper elements of dst. Does not follow
 * the IEEE Standard for Floating-Point Arithmetic (IEEE 754) minimum value when inputs are NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_min_sh)
 */
public fun mmMaskMinSh(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h = mmMaskMinRoundSh<MmFroundCurDirection>(src, k, a, b)

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b, store the minimum value
 * in the lower element of dst using zeromask k (the element is zeroed out when mask bit 0 is not set), and
 * copy the upper 7 packed elements from a to the upper elements of dst. Does not follow the IEEE Standard
 * for Floating-Point Arithmetic (IEEE 754) minimum value when inputs are NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_min_sh)
 */
public fun mmMaskzMinSh(k: Mmask8, a: M128h, b: M128h): M128h = mmMaskMinSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b)

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b, store the minimum value
 * in the lower element of dst, and copy the upper 7 packed elements from a to the upper elements of dst.
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter. Does not follow the
 * IEEE Standard for Floating-Point Arithmetic (IEEE 754) minimum value when inputs are NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_min_round_sh)
 */
public fun mmMinRoundSh(a: M128h, b: M128h, sae: Int): M128h {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mmMaskMinRoundSh(mmUndefinedPh(), 0xffu, a, b, sae)
}

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b, store the minimum value
 * in the lower element of dst using writemask k (the element is copied from src when mask bit 0 is not set),
 * and copy the upper 7 packed elements from a to the upper elements of dst. Exceptions can be suppressed by
 * passing _MM_FROUND_NO_EXC in the sae parameter. Does not follow the IEEE Standard for Floating-Point Arithmetic
 * (IEEE 754) minimum value when inputs are NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_min_round_sh)
 */
public fun mmMaskMinRoundSh(src: M128h, k: Mmask8, a: M128h, b: M128h, sae: Int): M128h =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        vminsh(a, b, src, k, sae)
    }

/**
 * Compare the lower half-precision (16-bit) floating-point elements in a and b, store the minimum value
 * in the lower element of dst using zeromask k (the element is zeroed out when mask bit 0 is not set), and
 * copy the upper 7 packed elements from a to the upper elements of dst. Exceptions can be suppressed by
 * passing _MM_FROUND_NO_EXC in the sae parameter. Does not follow the IEEE Standard for Floating-Point Arithmetic
 * (IEEE 754) minimum value when inputs are NaN or signed-zero values.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_min_round_sh)
 */
public fun mmMaskzMinRoundSh(k: Mmask8, a: M128h, b: M128h, sae: Int): M128h {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mmMaskMinRoundSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b, sae)
}

/**
 * Convert the exponent of each packed half-precision (16-bit) floating-point element in a to a half-precision
 * (16-bit) floating-point number representing the integer exponent, and store the results in dst.
 * This intrinsic essentially calculates `floor(log2(x))` for each element.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_getexp_ph)
 */
public fun mmGetexpPh(a: M128h): M128h = mmMaskGetexpPh(mmUndefinedPh(), 0xffu, a)

/**
 * Convert the exponent of each packed half-precision (16-bit) floating-point element in a to a half-precision
 * (16-bit) floating-point number representing the integer exponent, and store the results in dst using writemask k
 * (elements are copied from src when the corresponding mask bit is not set). This intrinsic essentially calculates
 * `floor(log2(x))` for each element.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_getexp_ph)
 */
public fun mmMaskGetexpPh(src: M128h, k: Mmask8, a: M128h): M128h = vgetexpph128(a, src, k)

/**
 * Convert the exponent of each packed half-precision (16-bit) floating-point element in a to a half-precision
 * (16-bit) floating-point number representing the integer exponent, and store the results in dst using zeromask
 * k (elements are zeroed out when the corresponding mask bit is not set). This intrinsic essentially calculates
 * `floor(log2(x))` for each element.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_getexp_ph)
 */
public fun mmMaskzGetexpPh(k: Mmask8, a: M128h): M128h = mmMaskGetexpPh(mmSetzeroPh(), k, a)

/**
 * Convert the exponent of each packed half-precision (16-bit) floating-point element in a to a half-precision
 * (16-bit) floating-point number representing the integer exponent, and store the results in dst.
 * This intrinsic essentially calculates `floor(log2(x))` for each element.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_getexp_ph)
 */
public fun mm256GetexpPh(a: M256h): M256h = mm256MaskGetexpPh(mm256UndefinedPh(), 0xffffu, a)

/**
 * Convert the exponent of each packed half-precision (16-bit) floating-point element in a to a half-precision
 * (16-bit) floating-point number representing the integer exponent, and store the results in dst using writemask k
 * (elements are copied from src when the corresponding mask bit is not set). This intrinsic essentially calculates
 * `floor(log2(x))` for each element.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_getexp_ph)
 */
public fun mm256MaskGetexpPh(src: M256h, k: Mmask16, a: M256h): M256h = vgetexpph256(a, src, k)

/**
 * Convert the exponent of each packed half-precision (16-bit) floating-point element in a to a half-precision
 * (16-bit) floating-point number representing the integer exponent, and store the results in dst using zeromask
 * k (elements are zeroed out when the corresponding mask bit is not set). This intrinsic essentially calculates
 * `floor(log2(x))` for each element.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_getexp_ph)
 */
public fun mm256MaskzGetexpPh(k: Mmask16, a: M256h): M256h = mm256MaskGetexpPh(mm256SetzeroPh(), k, a)

/**
 * Convert the exponent of each packed half-precision (16-bit) floating-point element in a to a half-precision
 * (16-bit) floating-point number representing the integer exponent, and store the results in dst.
 * This intrinsic essentially calculates `floor(log2(x))` for each element.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_getexp_ph)
 */
public fun mm512GetexpPh(a: M512h): M512h = mm512MaskGetexpPh(mm512UndefinedPh(), 0xffffffffu, a)

/**
 * Convert the exponent of each packed half-precision (16-bit) floating-point element in a to a half-precision
 * (16-bit) floating-point number representing the integer exponent, and store the results in dst using writemask k
 * (elements are copied from src when the corresponding mask bit is not set). This intrinsic essentially calculates
 * `floor(log2(x))` for each element.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_getexp_ph)
 */
public fun mm512MaskGetexpPh(src: M512h, k: Mmask32, a: M512h): M512h = mm512MaskGetexpRoundPh<MmFroundCurDirection>(src, k, a)

/**
 * Convert the exponent of each packed half-precision (16-bit) floating-point element in a to a half-precision
 * (16-bit) floating-point number representing the integer exponent, and store the results in dst using zeromask
 * k (elements are zeroed out when the corresponding mask bit is not set). This intrinsic essentially calculates
 * `floor(log2(x))` for each element.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_getexp_ph)
 */
public fun mm512MaskzGetexpPh(k: Mmask32, a: M512h): M512h = mm512MaskGetexpPh(mm512SetzeroPh(), k, a)

/**
 * Convert the exponent of each packed half-precision (16-bit) floating-point element in a to a half-precision
 * (16-bit) floating-point number representing the integer exponent, and store the results in dst.
 * This intrinsic essentially calculates `floor(log2(x))` for each element. Exceptions can be suppressed
 * by passing _MM_FROUND_NO_EXC in the sae parameter
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_getexp_round_ph)
 */
public fun mm512GetexpRoundPh(a: M512h, sae: Int): M512h {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskGetexpRoundPh(mm512UndefinedPh(), 0xffffffffu, a, sae)
}

/**
 * Convert the exponent of each packed half-precision (16-bit) floating-point element in a to a half-precision
 * (16-bit) floating-point number representing the integer exponent, and store the results in dst using writemask k
 * (elements are copied from src when the corresponding mask bit is not set). This intrinsic essentially calculates
 * `floor(log2(x))` for each element. Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_getexp_round_ph)
 */
public fun mm512MaskGetexpRoundPh(src: M512h, k: Mmask32, a: M512h, sae: Int): M512h =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        vgetexpph512(a, src, k, sae)
    }

/**
 * Convert the exponent of each packed half-precision (16-bit) floating-point element in a to a half-precision
 * (16-bit) floating-point number representing the integer exponent, and store the results in dst using zeromask
 * k (elements are zeroed out when the corresponding mask bit is not set). This intrinsic essentially calculates
 * `floor(log2(x))` for each element. Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_getexp_round_ph)
 */
public fun mm512MaskzGetexpRoundPh(k: Mmask32, a: M512h, sae: Int): M512h {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskGetexpRoundPh(mm512SetzeroPh(), k, a, sae)
}

/**
 * Convert the exponent of the lower half-precision (16-bit) floating-point element in b to a half-precision
 * (16-bit) floating-point number representing the integer exponent, store the result in the lower element
 * of dst, and copy the upper 7 packed elements from a to the upper elements of dst. This intrinsic essentially
 * calculates `floor(log2(x))` for the lower element.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_getexp_sh)
 */
public fun mmGetexpSh(a: M128h, b: M128h): M128h = mmMaskGetexpSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b)

/**
 * Convert the exponent of the lower half-precision (16-bit) floating-point element in b to a half-precision
 * (16-bit) floating-point number representing the integer exponent, store the result in the lower element
 * of dst using writemask k (the element is copied from src when mask bit 0 is not set), and copy the upper 7
 * packed elements from a to the upper elements of dst. This intrinsic essentially calculates `floor(log2(x))`
 * for the lower element.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_getexp_sh)
 */
public fun mmMaskGetexpSh(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h = mmMaskGetexpRoundSh<MmFroundCurDirection>(src, k, a, b)

/**
 * Convert the exponent of the lower half-precision (16-bit) floating-point element in b to a half-precision
 * (16-bit) floating-point number representing the integer exponent, store the result in the lower element
 * of dst using zeromask k (the element is zeroed out when mask bit 0 is not set), and copy the upper 7 packed
 * elements from a to the upper elements of dst. This intrinsic essentially calculates `floor(log2(x))` for the
 * lower element.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_getexp_sh)
 */
public fun mmMaskzGetexpSh(k: Mmask8, a: M128h, b: M128h): M128h = mmMaskGetexpSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b)

/**
 * Convert the exponent of the lower half-precision (16-bit) floating-point element in b to a half-precision
 * (16-bit) floating-point number representing the integer exponent, store the result in the lower element
 * of dst, and copy the upper 7 packed elements from a to the upper elements of dst. This intrinsic essentially
 * calculates `floor(log2(x))` for the lower element. Exceptions can be suppressed by passing _MM_FROUND_NO_EXC
 * in the sae parameter
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_getexp_round_sh)
 */
public fun mmGetexpRoundSh(a: M128h, b: M128h, sae: Int): M128h {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mmMaskGetexpRoundSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b, sae)
}

/**
 * Convert the exponent of the lower half-precision (16-bit) floating-point element in b to a half-precision
 * (16-bit) floating-point number representing the integer exponent, store the result in the lower element
 * of dst using writemask k (the element is copied from src when mask bit 0 is not set), and copy the upper 7
 * packed elements from a to the upper elements of dst. This intrinsic essentially calculates `floor(log2(x))`
 * for the lower element. Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_getexp_round_sh)
 */
public fun mmMaskGetexpRoundSh(src: M128h, k: Mmask8, a: M128h, b: M128h, sae: Int): M128h =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        vgetexpsh(a, b, src, k, sae)
    }

/**
 * Convert the exponent of the lower half-precision (16-bit) floating-point element in b to a half-precision
 * (16-bit) floating-point number representing the integer exponent, store the result in the lower element
 * of dst using zeromask k (the element is zeroed out when mask bit 0 is not set), and copy the upper 7 packed
 * elements from a to the upper elements of dst. This intrinsic essentially calculates `floor(log2(x))` for the
 * lower element. Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_getexp_round_sh)
 */
public fun mmMaskzGetexpRoundSh(k: Mmask8, a: M128h, b: M128h, sae: Int): M128h {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mmMaskGetexpRoundSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b, sae)
}

/**
 * Normalize the mantissas of packed half-precision (16-bit) floating-point elements in a, and store
 * the results in dst. This intrinsic essentially calculates `±(2^k)*|x.significand|`, where k depends
 * on the interval range defined by norm and the sign depends on sign and the source sign.
 *
 * The mantissa is normalized to the interval specified by interv, which can take the following values:
 *
 *     _MM_MANT_NORM_1_2     // interval [1, 2)
 *     _MM_MANT_NORM_p5_2    // interval [0.5, 2)
 *     _MM_MANT_NORM_p5_1    // interval [0.5, 1)
 *     _MM_MANT_NORM_p75_1p5 // interval [0.75, 1.5)
 *
 * The sign is determined by sc which can take the following values:
 *
 *     _MM_MANT_SIGN_src     // sign = sign(src)
 *     _MM_MANT_SIGN_zero    // sign = 0
 *     _MM_MANT_SIGN_nan     // dst = NaN if sign(src) = 1
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_getmant_ph)
 */
public fun mmGetmantPh(a: M128h, norm: MmMantissaNormEnum, sign: MmMantissaSignEnum): M128h {
    run {
        check(0 <= norm && norm < (1 shl 4)) { "NORM doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(0 <= sign && sign < (1 shl 2)) { "SIGN doesn't fit in 2 bits (unsigned)" }
    }
    return mmMaskGetmantPh(mmUndefinedPh(), 0xffu, a, norm, sign)
}

/**
 * Normalize the mantissas of packed half-precision (16-bit) floating-point elements in a, and store
 * the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 * This intrinsic essentially calculates `±(2^k)*|x.significand|`, where k depends on the interval range defined
 * by norm and the sign depends on sign and the source sign.
 *
 * The mantissa is normalized to the interval specified by interv, which can take the following values:
 *
 *     _MM_MANT_NORM_1_2     // interval [1, 2)
 *     _MM_MANT_NORM_p5_2    // interval [0.5, 2)
 *     _MM_MANT_NORM_p5_1    // interval [0.5, 1)
 *     _MM_MANT_NORM_p75_1p5 // interval [0.75, 1.5)
 *
 * The sign is determined by sc which can take the following values:
 *
 *     _MM_MANT_SIGN_src     // sign = sign(src)
 *     _MM_MANT_SIGN_zero    // sign = 0
 *     _MM_MANT_SIGN_nan     // dst = NaN if sign(src) = 1
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_getmant_ph)
 */
public fun mmMaskGetmantPh(src: M128h, k: Mmask8, a: M128h, norm: MmMantissaNormEnum, sign: MmMantissaSignEnum): M128h =
    run {
        run {
            check(0 <= norm && norm < (1 shl 4)) { "NORM doesn't fit in 4 bits (unsigned)" }
        }
        run {
            check(0 <= sign && sign < (1 shl 2)) { "SIGN doesn't fit in 2 bits (unsigned)" }
        }
        vgetmantph128(a, (sign shl 2) or norm, src, k)
    }

/**
 * Normalize the mantissas of packed half-precision (16-bit) floating-point elements in a, and store
 * the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 * This intrinsic essentially calculates `±(2^k)*|x.significand|`, where k depends on the interval range defined
 * by norm and the sign depends on sign and the source sign.
 *
 * The mantissa is normalized to the interval specified by interv, which can take the following values:
 *
 *     _MM_MANT_NORM_1_2     // interval [1, 2)
 *     _MM_MANT_NORM_p5_2    // interval [0.5, 2)
 *     _MM_MANT_NORM_p5_1    // interval [0.5, 1)
 *     _MM_MANT_NORM_p75_1p5 // interval [0.75, 1.5)
 *
 * The sign is determined by sc which can take the following values:
 *
 *     _MM_MANT_SIGN_src     // sign = sign(src)
 *     _MM_MANT_SIGN_zero    // sign = 0
 *     _MM_MANT_SIGN_nan     // dst = NaN if sign(src) = 1
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_getmant_ph)
 */
public fun mmMaskzGetmantPh(k: Mmask8, a: M128h, norm: MmMantissaNormEnum, sign: MmMantissaSignEnum): M128h {
    run {
        check(0 <= norm && norm < (1 shl 4)) { "NORM doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(0 <= sign && sign < (1 shl 2)) { "SIGN doesn't fit in 2 bits (unsigned)" }
    }
    return mmMaskGetmantPh(mmSetzeroPh(), k, a, norm, sign)
}

/**
 * Normalize the mantissas of packed half-precision (16-bit) floating-point elements in a, and store
 * the results in dst. This intrinsic essentially calculates `±(2^k)*|x.significand|`, where k depends
 * on the interval range defined by norm and the sign depends on sign and the source sign.
 *
 * The mantissa is normalized to the interval specified by interv, which can take the following values:
 *
 *     _MM_MANT_NORM_1_2     // interval [1, 2)
 *     _MM_MANT_NORM_p5_2    // interval [0.5, 2)
 *     _MM_MANT_NORM_p5_1    // interval [0.5, 1)
 *     _MM_MANT_NORM_p75_1p5 // interval [0.75, 1.5)
 *
 * The sign is determined by sc which can take the following values:
 *
 *     _MM_MANT_SIGN_src     // sign = sign(src)
 *     _MM_MANT_SIGN_zero    // sign = 0
 *     _MM_MANT_SIGN_nan     // dst = NaN if sign(src) = 1
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_getmant_ph)
 */
public fun mm256GetmantPh(a: M256h, norm: MmMantissaNormEnum, sign: MmMantissaSignEnum): M256h {
    run {
        check(0 <= norm && norm < (1 shl 4)) { "NORM doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(0 <= sign && sign < (1 shl 2)) { "SIGN doesn't fit in 2 bits (unsigned)" }
    }
    return mm256MaskGetmantPh(mm256UndefinedPh(), 0xffffu, a, norm, sign)
}

/**
 * Normalize the mantissas of packed half-precision (16-bit) floating-point elements in a, and store
 * the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 * This intrinsic essentially calculates `±(2^k)*|x.significand|`, where k depends on the interval range defined
 * by norm and the sign depends on sign and the source sign.
 *
 * The mantissa is normalized to the interval specified by interv, which can take the following values:
 *
 *     _MM_MANT_NORM_1_2     // interval [1, 2)
 *     _MM_MANT_NORM_p5_2    // interval [0.5, 2)
 *     _MM_MANT_NORM_p5_1    // interval [0.5, 1)
 *     _MM_MANT_NORM_p75_1p5 // interval [0.75, 1.5)
 *
 * The sign is determined by sc which can take the following values:
 *
 *     _MM_MANT_SIGN_src     // sign = sign(src)
 *     _MM_MANT_SIGN_zero    // sign = 0
 *     _MM_MANT_SIGN_nan     // dst = NaN if sign(src) = 1
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_getmant_ph)
 */
public fun mm256MaskGetmantPh(src: M256h, k: Mmask16, a: M256h, norm: MmMantissaNormEnum, sign: MmMantissaSignEnum): M256h =
    run {
        run {
            check(0 <= norm && norm < (1 shl 4)) { "NORM doesn't fit in 4 bits (unsigned)" }
        }
        run {
            check(0 <= sign && sign < (1 shl 2)) { "SIGN doesn't fit in 2 bits (unsigned)" }
        }
        vgetmantph256(a, (sign shl 2) or norm, src, k)
    }

/**
 * Normalize the mantissas of packed half-precision (16-bit) floating-point elements in a, and store
 * the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 * This intrinsic essentially calculates `±(2^k)*|x.significand|`, where k depends on the interval range defined
 * by norm and the sign depends on sign and the source sign.
 *
 * The mantissa is normalized to the interval specified by interv, which can take the following values:
 *
 *     _MM_MANT_NORM_1_2     // interval [1, 2)
 *     _MM_MANT_NORM_p5_2    // interval [0.5, 2)
 *     _MM_MANT_NORM_p5_1    // interval [0.5, 1)
 *     _MM_MANT_NORM_p75_1p5 // interval [0.75, 1.5)
 *
 * The sign is determined by sc which can take the following values:
 *
 *     _MM_MANT_SIGN_src     // sign = sign(src)
 *     _MM_MANT_SIGN_zero    // sign = 0
 *     _MM_MANT_SIGN_nan     // dst = NaN if sign(src) = 1
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_getmant_ph)
 */
public fun mm256MaskzGetmantPh(k: Mmask16, a: M256h, norm: MmMantissaNormEnum, sign: MmMantissaSignEnum): M256h {
    run {
        check(0 <= norm && norm < (1 shl 4)) { "NORM doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(0 <= sign && sign < (1 shl 2)) { "SIGN doesn't fit in 2 bits (unsigned)" }
    }
    return mm256MaskGetmantPh(mm256SetzeroPh(), k, a, norm, sign)
}

/**
 * Normalize the mantissas of packed half-precision (16-bit) floating-point elements in a, and store
 * the results in dst. This intrinsic essentially calculates `±(2^k)*|x.significand|`, where k depends
 * on the interval range defined by norm and the sign depends on sign and the source sign.
 *
 * The mantissa is normalized to the interval specified by interv, which can take the following values:
 *
 *     _MM_MANT_NORM_1_2     // interval [1, 2)
 *     _MM_MANT_NORM_p5_2    // interval [0.5, 2)
 *     _MM_MANT_NORM_p5_1    // interval [0.5, 1)
 *     _MM_MANT_NORM_p75_1p5 // interval [0.75, 1.5)
 *
 * The sign is determined by sc which can take the following values:
 *
 *     _MM_MANT_SIGN_src     // sign = sign(src)
 *     _MM_MANT_SIGN_zero    // sign = 0
 *     _MM_MANT_SIGN_nan     // dst = NaN if sign(src) = 1
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_getmant_ph)
 */
public fun mm512GetmantPh(a: M512h, norm: MmMantissaNormEnum, sign: MmMantissaSignEnum): M512h {
    run {
        check(0 <= norm && norm < (1 shl 4)) { "NORM doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(0 <= sign && sign < (1 shl 2)) { "SIGN doesn't fit in 2 bits (unsigned)" }
    }
    return mm512MaskGetmantPh(mm512UndefinedPh(), 0xffffffffu, a, norm, sign)
}

/**
 * Normalize the mantissas of packed half-precision (16-bit) floating-point elements in a, and store
 * the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 * This intrinsic essentially calculates `±(2^k)*|x.significand|`, where k depends on the interval range defined
 * by norm and the sign depends on sign and the source sign.
 *
 * The mantissa is normalized to the interval specified by interv, which can take the following values:
 *
 *     _MM_MANT_NORM_1_2     // interval [1, 2)
 *     _MM_MANT_NORM_p5_2    // interval [0.5, 2)
 *     _MM_MANT_NORM_p5_1    // interval [0.5, 1)
 *     _MM_MANT_NORM_p75_1p5 // interval [0.75, 1.5)
 *
 * The sign is determined by sc which can take the following values:
 *
 *     _MM_MANT_SIGN_src     // sign = sign(src)
 *     _MM_MANT_SIGN_zero    // sign = 0
 *     _MM_MANT_SIGN_nan     // dst = NaN if sign(src) = 1
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_getmant_ph)
 */
public fun mm512MaskGetmantPh(src: M512h, k: Mmask32, a: M512h, norm: MmMantissaNormEnum, sign: MmMantissaSignEnum): M512h {
    run {
        check(0 <= norm && norm < (1 shl 4)) { "NORM doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(0 <= sign && sign < (1 shl 2)) { "SIGN doesn't fit in 2 bits (unsigned)" }
    }
    return mm512MaskGetmantRoundPh<MmFroundCurDirection>(src, k, a, norm, sign)
}

/**
 * Normalize the mantissas of packed half-precision (16-bit) floating-point elements in a, and store
 * the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 * This intrinsic essentially calculates `±(2^k)*|x.significand|`, where k depends on the interval range defined
 * by norm and the sign depends on sign and the source sign.
 *
 * The mantissa is normalized to the interval specified by interv, which can take the following values:
 *
 *     _MM_MANT_NORM_1_2     // interval [1, 2)
 *     _MM_MANT_NORM_p5_2    // interval [0.5, 2)
 *     _MM_MANT_NORM_p5_1    // interval [0.5, 1)
 *     _MM_MANT_NORM_p75_1p5 // interval [0.75, 1.5)
 *
 * The sign is determined by sc which can take the following values:
 *
 *     _MM_MANT_SIGN_src     // sign = sign(src)
 *     _MM_MANT_SIGN_zero    // sign = 0
 *     _MM_MANT_SIGN_nan     // dst = NaN if sign(src) = 1
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_getmant_ph)
 */
public fun mm512MaskzGetmantPh(k: Mmask32, a: M512h, norm: MmMantissaNormEnum, sign: MmMantissaSignEnum): M512h {
    run {
        check(0 <= norm && norm < (1 shl 4)) { "NORM doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(0 <= sign && sign < (1 shl 2)) { "SIGN doesn't fit in 2 bits (unsigned)" }
    }
    return mm512MaskGetmantPh(mm512SetzeroPh(), k, a, norm, sign)
}

/**
 * Normalize the mantissas of packed half-precision (16-bit) floating-point elements in a, and store
 * the results in dst. This intrinsic essentially calculates `±(2^k)*|x.significand|`, where k depends
 * on the interval range defined by norm and the sign depends on sign and the source sign. Exceptions can
 * be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter
 *
 * The mantissa is normalized to the interval specified by interv, which can take the following values:
 *
 *     _MM_MANT_NORM_1_2     // interval [1, 2)
 *     _MM_MANT_NORM_p5_2    // interval [0.5, 2)
 *     _MM_MANT_NORM_p5_1    // interval [0.5, 1)
 *     _MM_MANT_NORM_p75_1p5 // interval [0.75, 1.5)
 *
 * The sign is determined by sc which can take the following values:
 *
 *     _MM_MANT_SIGN_src     // sign = sign(src)
 *     _MM_MANT_SIGN_zero    // sign = 0
 *     _MM_MANT_SIGN_nan     // dst = NaN if sign(src) = 1
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_getmant_round_ph)
 */
public fun mm512GetmantRoundPh(a: M512h, norm: MmMantissaNormEnum, sign: MmMantissaSignEnum, sae: Int): M512h {
    run {
        check(0 <= norm && norm < (1 shl 4)) { "NORM doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(0 <= sign && sign < (1 shl 2)) { "SIGN doesn't fit in 2 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskGetmantRoundPh(mm512UndefinedPh(), 0xffffffffu, a, norm, sign, sae)
}

/**
 * Normalize the mantissas of packed half-precision (16-bit) floating-point elements in a, and store
 * the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 * This intrinsic essentially calculates `±(2^k)*|x.significand|`, where k depends on the interval range defined
 * by norm and the sign depends on sign and the source sign. Exceptions can be suppressed by passing _MM_FROUND_NO_EXC
 * in the sae parameter
 *
 * The mantissa is normalized to the interval specified by interv, which can take the following values:
 *
 *     _MM_MANT_NORM_1_2     // interval [1, 2)
 *     _MM_MANT_NORM_p5_2    // interval [0.5, 2)
 *     _MM_MANT_NORM_p5_1    // interval [0.5, 1)
 *     _MM_MANT_NORM_p75_1p5 // interval [0.75, 1.5)
 *
 * The sign is determined by sc which can take the following values:
 *
 *     _MM_MANT_SIGN_src     // sign = sign(src)
 *     _MM_MANT_SIGN_zero    // sign = 0
 *     _MM_MANT_SIGN_nan     // dst = NaN if sign(src) = 1
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_getmant_round_ph)
 */
public fun mm512MaskGetmantRoundPh(src: M512h, k: Mmask32, a: M512h, norm: MmMantissaNormEnum, sign: MmMantissaSignEnum, sae: Int): M512h =
    run {
        run {
            check(0 <= norm && norm < (1 shl 4)) { "NORM doesn't fit in 4 bits (unsigned)" }
        }
        run {
            check(0 <= sign && sign < (1 shl 2)) { "SIGN doesn't fit in 2 bits (unsigned)" }
        }
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        vgetmantph512(a, (sign shl 2) or norm, src, k, sae)
    }

/**
 * Normalize the mantissas of packed half-precision (16-bit) floating-point elements in a, and store
 * the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 * This intrinsic essentially calculates `±(2^k)*|x.significand|`, where k depends on the interval range defined
 * by norm and the sign depends on sign and the source sign. Exceptions can be suppressed by passing _MM_FROUND_NO_EXC
 * in the sae parameter
 *
 * The mantissa is normalized to the interval specified by interv, which can take the following values:
 *
 *     _MM_MANT_NORM_1_2     // interval [1, 2)
 *     _MM_MANT_NORM_p5_2    // interval [0.5, 2)
 *     _MM_MANT_NORM_p5_1    // interval [0.5, 1)
 *     _MM_MANT_NORM_p75_1p5 // interval [0.75, 1.5)
 *
 * The sign is determined by sc which can take the following values:
 *
 *     _MM_MANT_SIGN_src     // sign = sign(src)
 *     _MM_MANT_SIGN_zero    // sign = 0
 *     _MM_MANT_SIGN_nan     // dst = NaN if sign(src) = 1
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_getmant_round_ph)
 */
public fun mm512MaskzGetmantRoundPh(k: Mmask32, a: M512h, norm: MmMantissaNormEnum, sign: MmMantissaSignEnum, sae: Int): M512h {
    run {
        check(0 <= norm && norm < (1 shl 4)) { "NORM doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(0 <= sign && sign < (1 shl 2)) { "SIGN doesn't fit in 2 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskGetmantRoundPh(mm512SetzeroPh(), k, a, norm, sign, sae)
}

/**
 * Normalize the mantissas of the lower half-precision (16-bit) floating-point element in b, store
 * the result in the lower element of dst, and copy the upper 7 packed elements from a to the upper
 * elements of dst. This intrinsic essentially calculates `±(2^k)*|x.significand|`, where k depends
 * on the interval range defined by norm and the sign depends on sign and the source sign.
 *
 * The mantissa is normalized to the interval specified by interv, which can take the following values:
 *
 *     _MM_MANT_NORM_1_2     // interval [1, 2)
 *     _MM_MANT_NORM_p5_2    // interval [0.5, 2)
 *     _MM_MANT_NORM_p5_1    // interval [0.5, 1)
 *     _MM_MANT_NORM_p75_1p5 // interval [0.75, 1.5)
 *
 * The sign is determined by sc which can take the following values:
 *
 *     _MM_MANT_SIGN_src     // sign = sign(src)
 *     _MM_MANT_SIGN_zero    // sign = 0
 *     _MM_MANT_SIGN_nan     // dst = NaN if sign(src) = 1
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_getmant_sh)
 */
public fun mmGetmantSh(a: M128h, b: M128h, norm: MmMantissaNormEnum, sign: MmMantissaSignEnum): M128h {
    run {
        check(0 <= norm && norm < (1 shl 4)) { "NORM doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(0 <= sign && sign < (1 shl 2)) { "SIGN doesn't fit in 2 bits (unsigned)" }
    }
    return mmMaskGetmantSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b, norm, sign)
}

/**
 * Normalize the mantissas of the lower half-precision (16-bit) floating-point element in b, store
 * the result in the lower element of dst using writemask k (the element is copied from src when mask bit 0 is not set),
 * and copy the upper 7 packed elements from a to the upper elements of dst. This intrinsic essentially calculates
 * `±(2^k)*|x.significand|`, where k depends on the interval range defined by norm and the sign depends on sign and
 * the source sign.
 *
 * The mantissa is normalized to the interval specified by interv, which can take the following values:
 *
 *     _MM_MANT_NORM_1_2     // interval [1, 2)
 *     _MM_MANT_NORM_p5_2    // interval [0.5, 2)
 *     _MM_MANT_NORM_p5_1    // interval [0.5, 1)
 *     _MM_MANT_NORM_p75_1p5 // interval [0.75, 1.5)
 *
 * The sign is determined by sc which can take the following values:
 *
 *     _MM_MANT_SIGN_src     // sign = sign(src)
 *     _MM_MANT_SIGN_zero    // sign = 0
 *     _MM_MANT_SIGN_nan     // dst = NaN if sign(src) = 1
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_getmant_sh)
 */
public fun mmMaskGetmantSh(src: M128h, k: Mmask8, a: M128h, b: M128h, norm: MmMantissaNormEnum, sign: MmMantissaSignEnum): M128h {
    run {
        check(0 <= norm && norm < (1 shl 4)) { "NORM doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(0 <= sign && sign < (1 shl 2)) { "SIGN doesn't fit in 2 bits (unsigned)" }
    }
    return mmMaskGetmantRoundSh<MmFroundCurDirection>(src, k, a, b, norm, sign)
}

/**
 * Normalize the mantissas of the lower half-precision (16-bit) floating-point element in b, store
 * the result in the lower element of dst using zeromask k (the element is zeroed out when mask bit 0 is not set),
 * and copy the upper 7 packed elements from a to the upper elements of dst. This intrinsic essentially calculates
 * `±(2^k)*|x.significand|`, where k depends on the interval range defined by norm and the sign depends on sign and
 * the source sign.
 *
 * The mantissa is normalized to the interval specified by interv, which can take the following values:
 *
 *     _MM_MANT_NORM_1_2     // interval [1, 2)
 *     _MM_MANT_NORM_p5_2    // interval [0.5, 2)
 *     _MM_MANT_NORM_p5_1    // interval [0.5, 1)
 *     _MM_MANT_NORM_p75_1p5 // interval [0.75, 1.5)
 *
 * The sign is determined by sc which can take the following values:
 *
 *     _MM_MANT_SIGN_src     // sign = sign(src)
 *     _MM_MANT_SIGN_zero    // sign = 0
 *     _MM_MANT_SIGN_nan     // dst = NaN if sign(src) = 1
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_getmant_sh)
 */
public fun mmMaskzGetmantSh(k: Mmask8, a: M128h, b: M128h, norm: MmMantissaNormEnum, sign: MmMantissaSignEnum): M128h {
    run {
        check(0 <= norm && norm < (1 shl 4)) { "NORM doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(0 <= sign && sign < (1 shl 2)) { "SIGN doesn't fit in 2 bits (unsigned)" }
    }
    return mmMaskGetmantSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b, norm, sign)
}

/**
 * Normalize the mantissas of the lower half-precision (16-bit) floating-point element in b, store
 * the result in the lower element of dst, and copy the upper 7 packed elements from a to the upper
 * elements of dst. This intrinsic essentially calculates `±(2^k)*|x.significand|`, where k depends
 * on the interval range defined by norm and the sign depends on sign and the source sign. Exceptions can
 * be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter
 *
 * The mantissa is normalized to the interval specified by interv, which can take the following values:
 *
 *     _MM_MANT_NORM_1_2     // interval [1, 2)
 *     _MM_MANT_NORM_p5_2    // interval [0.5, 2)
 *     _MM_MANT_NORM_p5_1    // interval [0.5, 1)
 *     _MM_MANT_NORM_p75_1p5 // interval [0.75, 1.5)
 *
 * The sign is determined by sc which can take the following values:
 *
 *     _MM_MANT_SIGN_src     // sign = sign(src)
 *     _MM_MANT_SIGN_zero    // sign = 0
 *     _MM_MANT_SIGN_nan     // dst = NaN if sign(src) = 1
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_getmant_round_sh)
 */
public fun mmGetmantRoundSh(a: M128h, b: M128h, norm: MmMantissaNormEnum, sign: MmMantissaSignEnum, sae: Int): M128h {
    run {
        check(0 <= norm && norm < (1 shl 4)) { "NORM doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(0 <= sign && sign < (1 shl 2)) { "SIGN doesn't fit in 2 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mmMaskGetmantRoundSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b, norm, sign, sae)
}

/**
 * Normalize the mantissas of the lower half-precision (16-bit) floating-point element in b, store
 * the result in the lower element of dst using writemask k (the element is copied from src when mask bit 0 is not set),
 * and copy the upper 7 packed elements from a to the upper elements of dst. This intrinsic essentially calculates
 * `±(2^k)*|x.significand|`, where k depends on the interval range defined by norm and the sign depends on sign and
 * the source sign. Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter
 *
 * The mantissa is normalized to the interval specified by interv, which can take the following values:
 *
 *     _MM_MANT_NORM_1_2     // interval [1, 2)
 *     _MM_MANT_NORM_p5_2    // interval [0.5, 2)
 *     _MM_MANT_NORM_p5_1    // interval [0.5, 1)
 *     _MM_MANT_NORM_p75_1p5 // interval [0.75, 1.5)
 *
 * The sign is determined by sc which can take the following values:
 *
 *     _MM_MANT_SIGN_src     // sign = sign(src)
 *     _MM_MANT_SIGN_zero    // sign = 0
 *     _MM_MANT_SIGN_nan     // dst = NaN if sign(src) = 1
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_getmant_round_sh)
 */
public fun mmMaskGetmantRoundSh(src: M128h, k: Mmask8, a: M128h, b: M128h, norm: MmMantissaNormEnum, sign: MmMantissaSignEnum, sae: Int): M128h =
    run {
        run {
            check(0 <= norm && norm < (1 shl 4)) { "NORM doesn't fit in 4 bits (unsigned)" }
        }
        run {
            check(0 <= sign && sign < (1 shl 2)) { "SIGN doesn't fit in 2 bits (unsigned)" }
        }
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        vgetmantsh(a, b, (sign shl 2) or norm, src, k, sae)
    }

/**
 * Normalize the mantissas of the lower half-precision (16-bit) floating-point element in b, store
 * the result in the lower element of dst using zeromask k (the element is zeroed out when mask bit 0 is not set),
 * and copy the upper 7 packed elements from a to the upper elements of dst. This intrinsic essentially calculates
 * `±(2^k)*|x.significand|`, where k depends on the interval range defined by norm and the sign depends on sign and
 * the source sign. Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter
 *
 * The mantissa is normalized to the interval specified by interv, which can take the following values:
 *
 *     _MM_MANT_NORM_1_2     // interval [1, 2)
 *     _MM_MANT_NORM_p5_2    // interval [0.5, 2)
 *     _MM_MANT_NORM_p5_1    // interval [0.5, 1)
 *     _MM_MANT_NORM_p75_1p5 // interval [0.75, 1.5)
 *
 * The sign is determined by sc which can take the following values:
 *
 *     _MM_MANT_SIGN_src     // sign = sign(src)
 *     _MM_MANT_SIGN_zero    // sign = 0
 *     _MM_MANT_SIGN_nan     // dst = NaN if sign(src) = 1
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_getmant_round_sh)
 */
public fun mmMaskzGetmantRoundSh(k: Mmask8, a: M128h, b: M128h, norm: MmMantissaNormEnum, sign: MmMantissaSignEnum, sae: Int): M128h {
    run {
        check(0 <= norm && norm < (1 shl 4)) { "NORM doesn't fit in 4 bits (unsigned)" }
    }
    run {
        check(0 <= sign && sign < (1 shl 2)) { "SIGN doesn't fit in 2 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mmMaskGetmantRoundSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b, norm, sign, sae)
}

/**
 * Round packed half-precision (16-bit) floating-point elements in a to the number of fraction bits
 * specified by imm8, and store the results in dst.
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_roundscale_ph)
 */
public fun mmRoundscalePh(a: M128h, imm8: Int): M128h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmMaskRoundscalePh(mmUndefinedPh(), 0xffu, a, imm8)
}

/**
 * Round packed half-precision (16-bit) floating-point elements in a to the number of fraction bits
 * specified by imm8, and store the results in dst using writemask k (elements are copied from src when
 * the corresponding mask bit is not set).
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_roundscale_ph)
 */
public fun mmMaskRoundscalePh(src: M128h, k: Mmask8, a: M128h, imm8: Int): M128h =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        vrndscaleph128(a, imm8, src, k)
    }

/**
 * Round packed half-precision (16-bit) floating-point elements in a to the number of fraction bits
 * specified by imm8, and store the results in dst using zeromask k (elements are zeroed out when the corresponding
 * mask bit is not set).
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_roundscale_ph)
 */
public fun mmMaskzRoundscalePh(k: Mmask8, a: M128h, imm8: Int): M128h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmMaskRoundscalePh(mmSetzeroPh(), k, a, imm8)
}

/**
 * Round packed half-precision (16-bit) floating-point elements in a to the number of fraction bits
 * specified by imm8, and store the results in dst.
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_roundscale_ph)
 */
public fun mm256RoundscalePh(a: M256h, imm8: Int): M256h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm256MaskRoundscalePh(mm256UndefinedPh(), 0xffffu, a, imm8)
}

/**
 * Round packed half-precision (16-bit) floating-point elements in a to the number of fraction bits
 * specified by imm8, and store the results in dst using writemask k (elements are copied from src when
 * the corresponding mask bit is not set).
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_roundscale_ph)
 */
public fun mm256MaskRoundscalePh(src: M256h, k: Mmask16, a: M256h, imm8: Int): M256h =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        vrndscaleph256(a, imm8, src, k)
    }

/**
 * Round packed half-precision (16-bit) floating-point elements in a to the number of fraction bits
 * specified by imm8, and store the results in dst using zeromask k (elements are zeroed out when the corresponding
 * mask bit is not set).
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_roundscale_ph)
 */
public fun mm256MaskzRoundscalePh(k: Mmask16, a: M256h, imm8: Int): M256h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm256MaskRoundscalePh(mm256SetzeroPh(), k, a, imm8)
}

/**
 * Round packed half-precision (16-bit) floating-point elements in a to the number of fraction bits
 * specified by imm8, and store the results in dst.
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_roundscale_ph)
 */
public fun mm512RoundscalePh(a: M512h, imm8: Int): M512h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm512MaskRoundscalePh(mm512UndefinedPh(), 0xffffffffu, a, imm8)
}

/**
 * Round packed half-precision (16-bit) floating-point elements in a to the number of fraction bits
 * specified by imm8, and store the results in dst using writemask k (elements are copied from src when
 * the corresponding mask bit is not set).
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_roundscale_ph)
 */
public fun mm512MaskRoundscalePh(src: M512h, k: Mmask32, a: M512h, imm8: Int): M512h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm512MaskRoundscaleRoundPh<MmFroundCurDirection>(src, k, a, imm8)
}

/**
 * Round packed half-precision (16-bit) floating-point elements in a to the number of fraction bits
 * specified by imm8, and store the results in dst using zeromask k (elements are zeroed out when the corresponding
 * mask bit is not set).
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_roundscale_ph)
 */
public fun mm512MaskzRoundscalePh(k: Mmask32, a: M512h, imm8: Int): M512h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm512MaskRoundscalePh(mm512SetzeroPh(), k, a, imm8)
}

/**
 * Round packed half-precision (16-bit) floating-point elements in a to the number of fraction bits
 * specified by imm8, and store the results in dst. Exceptions can be suppressed by passing _MM_FROUND_NO_EXC
 * in the sae parameter
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_roundscale_round_ph)
 */
public fun mm512RoundscaleRoundPh(a: M512h, imm8: Int, sae: Int): M512h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskRoundscaleRoundPh(mm512UndefinedPh(), 0xffffffffu, a, imm8, sae)
}

/**
 * Round packed half-precision (16-bit) floating-point elements in a to the number of fraction bits
 * specified by imm8, and store the results in dst using writemask k (elements are copied from src when
 * the corresponding mask bit is not set). Exceptions can be suppressed by passing _MM_FROUND_NO_EXC
 * in the sae parameter
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_roundscale_round_ph)
 */
public fun mm512MaskRoundscaleRoundPh(src: M512h, k: Mmask32, a: M512h, imm8: Int, sae: Int): M512h =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        vrndscaleph512(a, imm8, src, k, sae)
    }

/**
 * Round packed half-precision (16-bit) floating-point elements in a to the number of fraction bits
 * specified by imm8, and store the results in dst using zeromask k (elements are zeroed out when the corresponding
 * mask bit is not set). Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_roundscale_round_ph)
 */
public fun mm512MaskzRoundscaleRoundPh(k: Mmask32, a: M512h, imm8: Int, sae: Int): M512h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskRoundscaleRoundPh(mm512SetzeroPh(), k, a, imm8, sae)
}

/**
 * Round the lower half-precision (16-bit) floating-point element in b to the number of fraction bits
 * specified by imm8, store the result in the lower element of dst, and copy the upper 7 packed elements
 * from a to the upper elements of dst.
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_roundscale_sh)
 */
public fun mmRoundscaleSh(a: M128h, b: M128h, imm8: Int): M128h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmMaskRoundscaleSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b, imm8)
}

/**
 * Round the lower half-precision (16-bit) floating-point element in b to the number of fraction bits
 * specified by imm8, store the result in the lower element of dst using writemask k (the element is copied
 * from src when mask bit 0 is not set), and copy the upper 7 packed elements from a to the upper elements of dst.
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_roundscale_sh)
 */
public fun mmMaskRoundscaleSh(src: M128h, k: Mmask8, a: M128h, b: M128h, imm8: Int): M128h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmMaskRoundscaleRoundSh<MmFroundCurDirection>(src, k, a, b, imm8)
}

/**
 * Round the lower half-precision (16-bit) floating-point element in b to the number of fraction bits
 * specified by imm8, store the result in the lower element of dst using zeromask k (the element is zeroed
 * out when mask bit 0 is not set), and copy the upper 7 packed elements from a to the upper elements of dst.
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_roundscale_sh)
 */
public fun mmMaskzRoundscaleSh(k: Mmask8, a: M128h, b: M128h, imm8: Int): M128h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmMaskRoundscaleSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b, imm8)
}

/**
 * Round the lower half-precision (16-bit) floating-point element in b to the number of fraction bits
 * specified by imm8, store the result in the lower element of dst, and copy the upper 7 packed elements
 * from a to the upper elements of dst.
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_roundscale_round_sh)
 */
public fun mmRoundscaleRoundSh(a: M128h, b: M128h, imm8: Int, sae: Int): M128h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mmMaskRoundscaleRoundSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b, imm8, sae)
}

/**
 * Round the lower half-precision (16-bit) floating-point element in b to the number of fraction bits
 * specified by imm8, store the result in the lower element of dst using writemask k (the element is copied
 * from src when mask bit 0 is not set), and copy the upper 7 packed elements from a to the upper elements of dst.
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_roundscale_round_sh)
 */
public fun mmMaskRoundscaleRoundSh(src: M128h, k: Mmask8, a: M128h, b: M128h, imm8: Int, sae: Int): M128h =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        vrndscalesh(a, b, src, k, imm8, sae)
    }

/**
 * Round the lower half-precision (16-bit) floating-point element in b to the number of fraction bits
 * specified by imm8, store the result in the lower element of dst using zeromask k (the element is zeroed
 * out when mask bit 0 is not set), and copy the upper 7 packed elements from a to the upper elements of dst.
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_roundscale_round_sh)
 */
public fun mmMaskzRoundscaleRoundSh(k: Mmask8, a: M128h, b: M128h, imm8: Int, sae: Int): M128h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mmMaskRoundscaleRoundSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b, imm8, sae)
}

/**
 * Scale the packed half-precision (16-bit) floating-point elements in a using values from b, and store
 * the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_scalef_ph)
 */
public fun mmScalefPh(a: M128h, b: M128h): M128h = mmMaskScalefPh(mmUndefinedPh(), 0xffu, a, b)

/**
 * Scale the packed half-precision (16-bit) floating-point elements in a using values from b, and store
 * the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_scalef_ph)
 */
public fun mmMaskScalefPh(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h = vscalefph128(a, b, src, k)

/**
 * Scale the packed half-precision (16-bit) floating-point elements in a using values from b, and store
 * the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_scalef_ph)
 */
public fun mmMaskzScalefPh(k: Mmask8, a: M128h, b: M128h): M128h = mmMaskScalefPh(mmSetzeroPh(), k, a, b)

/**
 * Scale the packed half-precision (16-bit) floating-point elements in a using values from b, and store
 * the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_scalef_ph)
 */
public fun mm256ScalefPh(a: M256h, b: M256h): M256h = mm256MaskScalefPh(mm256UndefinedPh(), 0xffffu, a, b)

/**
 * Scale the packed half-precision (16-bit) floating-point elements in a using values from b, and store
 * the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_scalef_ph)
 */
public fun mm256MaskScalefPh(src: M256h, k: Mmask16, a: M256h, b: M256h): M256h = vscalefph256(a, b, src, k)

/**
 * Scale the packed half-precision (16-bit) floating-point elements in a using values from b, and store
 * the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_scalef_ph)
 */
public fun mm256MaskzScalefPh(k: Mmask16, a: M256h, b: M256h): M256h = mm256MaskScalefPh(mm256SetzeroPh(), k, a, b)

/**
 * Scale the packed half-precision (16-bit) floating-point elements in a using values from b, and store
 * the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_scalef_ph)
 */
public fun mm512ScalefPh(a: M512h, b: M512h): M512h = mm512MaskScalefPh(mm512UndefinedPh(), 0xffffffffu, a, b)

/**
 * Scale the packed half-precision (16-bit) floating-point elements in a using values from b, and store
 * the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_scalef_ph)
 */
public fun mm512MaskScalefPh(src: M512h, k: Mmask32, a: M512h, b: M512h): M512h = mm512MaskScalefRoundPh<MmFroundCurDirection>(src, k, a, b)

/**
 * Scale the packed half-precision (16-bit) floating-point elements in a using values from b, and store
 * the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_scalef_ph)
 */
public fun mm512MaskzScalefPh(k: Mmask32, a: M512h, b: M512h): M512h = mm512MaskScalefPh(mm512SetzeroPh(), k, a, b)

/**
 * Scale the packed half-precision (16-bit) floating-point elements in a using values from b, and store
 * the results in dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_scalef_round_ph)
 */
public fun mm512ScalefRoundPh(a: M512h, b: M512h, rounding: Int): M512h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskScalefRoundPh(mm512UndefinedPh(), 0xffffffffu, a, b, rounding)
}

/**
 * Scale the packed half-precision (16-bit) floating-point elements in a using values from b, and store
 * the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_scalef_round_ph)
 */
public fun mm512MaskScalefRoundPh(src: M512h, k: Mmask32, a: M512h, b: M512h, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vscalefph512(a, b, src, k, rounding)
    }

/**
 * Scale the packed half-precision (16-bit) floating-point elements in a using values from b, and store
 * the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_scalef_round_ph)
 */
public fun mm512MaskzScalefRoundPh(k: Mmask32, a: M512h, b: M512h, rounding: Int): M512h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskScalefRoundPh(mm512SetzeroPh(), k, a, b, rounding)
}

/**
 * Scale the packed single-precision (32-bit) floating-point elements in a using values from b, store
 * the result in the lower element of dst, and copy the upper 7 packed elements from a to the upper
 * elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_scalef_sh)
 */
public fun mmScalefSh(a: M128h, b: M128h): M128h = mmMaskScalefSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b)

/**
 * Scale the packed single-precision (32-bit) floating-point elements in a using values from b, store
 * the result in the lower element of dst using writemask k (the element is copied from src when mask bit 0 is not set),
 * and copy the upper 7 packed elements from a to the upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_scalef_sh)
 */
public fun mmMaskScalefSh(src: M128h, k: Mmask8, a: M128h, b: M128h): M128h = mmMaskScalefRoundSh<MmFroundCurDirection>(src, k, a, b)

/**
 * Scale the packed single-precision (32-bit) floating-point elements in a using values from b, store
 * the result in the lower element of dst using zeromask k (the element is zeroed out when mask bit 0 is not set),
 * and copy the upper 7 packed elements from a to the upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_scalef_sh)
 */
public fun mmMaskzScalefSh(k: Mmask8, a: M128h, b: M128h): M128h = mmMaskScalefSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b)

/**
 * Scale the packed single-precision (32-bit) floating-point elements in a using values from b, store
 * the result in the lower element of dst, and copy the upper 7 packed elements from a to the upper
 * elements of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_scalef_round_sh)
 */
public fun mmScalefRoundSh(a: M128h, b: M128h, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmMaskScalefRoundSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b, rounding)
}

/**
 * Scale the packed single-precision (32-bit) floating-point elements in a using values from b, store
 * the result in the lower element of dst using writemask k (the element is copied from src when mask bit 0 is not set),
 * and copy the upper 7 packed elements from a to the upper elements of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_scalef_round_sh)
 */
public fun mmMaskScalefRoundSh(src: M128h, k: Mmask8, a: M128h, b: M128h, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vscalefsh(a, b, src, k, rounding)
    }

/**
 * Scale the packed single-precision (32-bit) floating-point elements in a using values from b, store
 * the result in the lower element of dst using zeromask k (the element is zeroed out when mask bit 0 is not set),
 * and copy the upper 7 packed elements from a to the upper elements of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_scalef_round_sh)
 */
public fun mmMaskzScalefRoundSh(k: Mmask8, a: M128h, b: M128h, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmMaskScalefRoundSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b, rounding)
}

/**
 * Extract the reduced argument of packed half-precision (16-bit) floating-point elements in a by the
 * number of bits specified by imm8, and store the results in dst.
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_reduce_ph)
 */
public fun mmReducePh(a: M128h, imm8: Int): M128h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmMaskReducePh(mmUndefinedPh(), 0xffu, a, imm8)
}

/**
 * Extract the reduced argument of packed half-precision (16-bit) floating-point elements in a by the
 * number of bits specified by imm8, and store the results in dst using writemask k (elements are copied
 * from src when the corresponding mask bit is not set).
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_reduce_ph)
 */
public fun mmMaskReducePh(src: M128h, k: Mmask8, a: M128h, imm8: Int): M128h =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        vreduceph128(a, imm8, src, k)
    }

/**
 * Extract the reduced argument of packed half-precision (16-bit) floating-point elements in a by the
 * number of bits specified by imm8, and store the results in dst using zeromask k (elements are zeroed
 * out when the corresponding mask bit is not set).
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_reduce_ph)
 */
public fun mmMaskzReducePh(k: Mmask8, a: M128h, imm8: Int): M128h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmMaskReducePh(mmSetzeroPh(), k, a, imm8)
}

/**
 * Extract the reduced argument of packed half-precision (16-bit) floating-point elements in a by the
 * number of bits specified by imm8, and store the results in dst.
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_reduce_ph)
 */
public fun mm256ReducePh(a: M256h, imm8: Int): M256h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm256MaskReducePh(mm256UndefinedPh(), 0xffffu, a, imm8)
}

/**
 * Extract the reduced argument of packed half-precision (16-bit) floating-point elements in a by the
 * number of bits specified by imm8, and store the results in dst using writemask k (elements are copied
 * from src when the corresponding mask bit is not set).
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_reduce_ph)
 */
public fun mm256MaskReducePh(src: M256h, k: Mmask16, a: M256h, imm8: Int): M256h =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        vreduceph256(a, imm8, src, k)
    }

/**
 * Extract the reduced argument of packed half-precision (16-bit) floating-point elements in a by the
 * number of bits specified by imm8, and store the results in dst using zeromask k (elements are zeroed
 * out when the corresponding mask bit is not set).
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_reduce_ph)
 */
public fun mm256MaskzReducePh(k: Mmask16, a: M256h, imm8: Int): M256h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm256MaskReducePh(mm256SetzeroPh(), k, a, imm8)
}

/**
 * Extract the reduced argument of packed half-precision (16-bit) floating-point elements in a by the
 * number of bits specified by imm8, and store the results in dst.
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_reduce_ph)
 */
public fun mm512ReducePh(a: M512h, imm8: Int): M512h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm512MaskReducePh(mm512UndefinedPh(), 0xffffffffu, a, imm8)
}

/**
 * Extract the reduced argument of packed half-precision (16-bit) floating-point elements in a by the
 * number of bits specified by imm8, and store the results in dst using writemask k (elements are copied
 * from src when the corresponding mask bit is not set).
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_reduce_ph)
 */
public fun mm512MaskReducePh(src: M512h, k: Mmask32, a: M512h, imm8: Int): M512h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm512MaskReduceRoundPh<MmFroundCurDirection>(src, k, a, imm8)
}

/**
 * Extract the reduced argument of packed half-precision (16-bit) floating-point elements in a by the
 * number of bits specified by imm8, and store the results in dst using zeromask k (elements are zeroed
 * out when the corresponding mask bit is not set).
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_reduce_ph)
 */
public fun mm512MaskzReducePh(k: Mmask32, a: M512h, imm8: Int): M512h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm512MaskReducePh(mm512SetzeroPh(), k, a, imm8)
}

/**
 * Extract the reduced argument of packed half-precision (16-bit) floating-point elements in a by the
 * number of bits specified by imm8, and store the results in dst.
 *
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
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_reduce_round_ph)
 */
public fun mm512ReduceRoundPh(a: M512h, imm8: Int, sae: Int): M512h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskReduceRoundPh(mm512UndefinedPh(), 0xffffffffu, a, imm8, sae)
}

/**
 * Extract the reduced argument of packed half-precision (16-bit) floating-point elements in a by the
 * number of bits specified by imm8, and store the results in dst using writemask k (elements are copied
 * from src when the corresponding mask bit is not set).
 *
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
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_reduce_round_ph)
 */
public fun mm512MaskReduceRoundPh(src: M512h, k: Mmask32, a: M512h, imm8: Int, sae: Int): M512h =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        vreduceph512(a, imm8, src, k, sae)
    }

/**
 * Extract the reduced argument of packed half-precision (16-bit) floating-point elements in a by the
 * number of bits specified by imm8, and store the results in dst using zeromask k (elements are zeroed
 * out when the corresponding mask bit is not set).
 *
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
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_reduce_round_ph)
 */
public fun mm512MaskzReduceRoundPh(k: Mmask32, a: M512h, imm8: Int, sae: Int): M512h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskReduceRoundPh(mm512SetzeroPh(), k, a, imm8, sae)
}

/**
 * Extract the reduced argument of the lower half-precision (16-bit) floating-point element in b by
 * the number of bits specified by imm8, store the result in the lower element of dst, and copy the
 * upper 7 packed elements from a to the upper elements of dst.
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_reduce_sh)
 */
public fun mmReduceSh(a: M128h, b: M128h, imm8: Int): M128h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmMaskReduceSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b, imm8)
}

/**
 * Extract the reduced argument of the lower half-precision (16-bit) floating-point element in b by
 * the number of bits specified by imm8, store the result in the lower element of dst using writemask k
 * (the element is copied from src when mask bit 0 is not set), and copy the upper 7 packed elements from
 * a to the upper elements of dst.
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_reduce_sh)
 */
public fun mmMaskReduceSh(src: M128h, k: Mmask8, a: M128h, b: M128h, imm8: Int): M128h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmMaskReduceRoundSh<MmFroundCurDirection>(src, k, a, b, imm8)
}

/**
 * Extract the reduced argument of the lower half-precision (16-bit) floating-point element in b by
 * the number of bits specified by imm8, store the result in the lower element of dst using zeromask k
 * (the element is zeroed out when mask bit 0 is not set), and copy the upper 7 packed elements from a
 * to the upper elements of dst.
 *
 * Rounding is done according to the imm8 parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] : round to nearest
 * * [mmFroundToNegInf] : round down
 * * [mmFroundToPosInf] : round up
 * * [mmFroundToZero] : truncate
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_reduce_sh)
 */
public fun mmMaskzReduceSh(k: Mmask8, a: M128h, b: M128h, imm8: Int): M128h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mmMaskReduceSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b, imm8)
}

/**
 * Extract the reduced argument of the lower half-precision (16-bit) floating-point element in b by
 * the number of bits specified by imm8, store the result in the lower element of dst, and copy the upper
 * 7 packed elements from a to the upper elements of dst.
 *
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
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_reduce_round_sh)
 */
public fun mmReduceRoundSh(a: M128h, b: M128h, imm8: Int, sae: Int): M128h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mmMaskReduceRoundSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b, imm8, sae)
}

/**
 * Extract the reduced argument of the lower half-precision (16-bit) floating-point element in b by
 * the number of bits specified by imm8, store the result in the lower element of dst using writemask k
 * (the element is copied from src when mask bit 0 is not set), and copy the upper 7 packed elements from a
 * to the upper elements of dst.
 *
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
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_reduce_round_sh)
 */
public fun mmMaskReduceRoundSh(src: M128h, k: Mmask8, a: M128h, b: M128h, imm8: Int, sae: Int): M128h =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        vreducesh(a, b, src, k, imm8, sae)
    }

/**
 * Extract the reduced argument of the lower half-precision (16-bit) floating-point element in b by
 * the number of bits specified by imm8, store the result in the lower element of dst using zeromask k
 * (the element is zeroed out when mask bit 0 is not set), and copy the upper 7 packed elements from a
 * to the upper elements of dst.
 *
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
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_reduce_round_sh)
 */
public fun mmMaskzReduceRoundSh(k: Mmask8, a: M128h, b: M128h, imm8: Int, sae: Int): M128h {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mmMaskReduceRoundSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b, imm8, sae)
}

/**
 * Reduce the packed half-precision (16-bit) floating-point elements in a by addition. Returns the
 * sum of all elements in a.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_reduce_add_ph)
 */
public fun mmReduceAddPh(a: M128h): F16 =
    run {
        val b = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 5u, 6u, 7u, 0u, 1u, 2u, 3u)); _simdArg0 }
        val a1 = mmAddPh(a, b)
        val b1 = run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(uintArrayOf(2u, 3u, 0u, 1u, 4u, 5u, 6u, 7u)); _simdArg0 }
        val a2 = mmAddPh(a1, b1)
        run { val _simdArg0 = a2; val _simdArg1 = F16.fromFloat(0.0f); F16() } + run { val _simdArg0 = a2; val _simdArg1 = F16.fromFloat(1.0f); F16() }
    }

/**
 * Reduce the packed half-precision (16-bit) floating-point elements in a by addition. Returns the
 * sum of all elements in a.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_reduce_add_ph)
 */
public fun mm256ReduceAddPh(a: M256h): F16 =
    run {
        val p = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u)); _simdArg0 }
        val q = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u)); _simdArg0 }
        mmReduceAddPh(mmAddPh(p, q))
    }

/**
 * Reduce the packed half-precision (16-bit) floating-point elements in a by addition. Returns the
 * sum of all elements in a.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_reduce_add_ph)
 */
public fun mm512ReduceAddPh(a: M512h): F16 =
    run {
        val p = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u)); _simdArg0 }
        val q = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(16u, 17u, 18u, 19u, 20u, 21u, 22u, 23u, 24u, 25u, 26u, 27u, 28u, 29u, 30u, 31u)); _simdArg0 }
        mm256ReduceAddPh(mm256AddPh(p, q))
    }

/**
 * Reduce the packed half-precision (16-bit) floating-point elements in a by multiplication. Returns
 * the product of all elements in a.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_reduce_mul_ph)
 */
public fun mmReduceMulPh(a: M128h): F16 =
    run {
        val b = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 5u, 6u, 7u, 0u, 1u, 2u, 3u)); _simdArg0 }
        val a1 = mmMulPh(a, b)
        val b1 = run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(uintArrayOf(2u, 3u, 0u, 1u, 4u, 5u, 6u, 7u)); _simdArg0 }
        val a2 = mmMulPh(a1, b1)
        run { val _simdArg0 = a2; val _simdArg1 = F16.fromFloat(0.0f); F16() } * run { val _simdArg0 = a2; val _simdArg1 = F16.fromFloat(1.0f); F16() }
    }

/**
 * Reduce the packed half-precision (16-bit) floating-point elements in a by multiplication. Returns
 * the product of all elements in a.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_reduce_mul_ph)
 */
public fun mm256ReduceMulPh(a: M256h): F16 =
    run {
        val p = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u)); _simdArg0 }
        val q = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u)); _simdArg0 }
        mmReduceMulPh(mmMulPh(p, q))
    }

/**
 * Reduce the packed half-precision (16-bit) floating-point elements in a by multiplication. Returns
 * the product of all elements in a.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_reduce_mul_ph)
 */
public fun mm512ReduceMulPh(a: M512h): F16 =
    run {
        val p = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u)); _simdArg0 }
        val q = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(16u, 17u, 18u, 19u, 20u, 21u, 22u, 23u, 24u, 25u, 26u, 27u, 28u, 29u, 30u, 31u)); _simdArg0 }
        mm256ReduceMulPh(mm256MulPh(p, q))
    }

/**
 * Reduce the packed half-precision (16-bit) floating-point elements in a by minimum. Returns the
 * minimum of all elements in a.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_reduce_min_ph)
 */
public fun mmReduceMinPh(a: M128h): F16 =
    run {
        val b = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 5u, 6u, 7u, 0u, 1u, 2u, 3u)); _simdArg0 }
        val a1 = mmMinPh(a, b)
        val b1 = run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(uintArrayOf(2u, 3u, 0u, 1u, 4u, 5u, 6u, 7u)); _simdArg0 }
        val a2 = mmMinPh(a1, b1)
        val b2 = run { val _simdArg0 = a2; val _simdArg1 = a2; val _simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 0u, 2u, 3u, 4u, 5u, 6u, 7u)); _simdArg0 }
        run { val _simdArg0 = mmMinSh(a2, b2); val _simdArg1 = F16.fromFloat(0.0f); _simdArg0 }
    }

/**
 * Reduce the packed half-precision (16-bit) floating-point elements in a by minimum. Returns the
 * minimum of all elements in a.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_reduce_min_ph)
 */
public fun mm256ReduceMinPh(a: M256h): F16 =
    run {
        val p = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u)); _simdArg0 }
        val q = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u)); _simdArg0 }
        mmReduceMinPh(mmMinPh(p, q))
    }

/**
 * Reduce the packed half-precision (16-bit) floating-point elements in a by minimum. Returns the
 * minimum of all elements in a.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_reduce_min_ph)
 */
public fun mm512ReduceMinPh(a: M512h): F16 =
    run {
        val p = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u)); _simdArg0 }
        val q = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(16u, 17u, 18u, 19u, 20u, 21u, 22u, 23u, 24u, 25u, 26u, 27u, 28u, 29u, 30u, 31u)); _simdArg0 }
        mm256ReduceMinPh(mm256MinPh(p, q))
    }

/**
 * Reduce the packed half-precision (16-bit) floating-point elements in a by maximum. Returns the
 * maximum of all elements in a.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_reduce_max_ph)
 */
public fun mmReduceMaxPh(a: M128h): F16 =
    run {
        val b = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 5u, 6u, 7u, 0u, 1u, 2u, 3u)); _simdArg0 }
        val a1 = mmMaxPh(a, b)
        val b1 = run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(uintArrayOf(2u, 3u, 0u, 1u, 4u, 5u, 6u, 7u)); _simdArg0 }
        val a2 = mmMaxPh(a1, b1)
        val b2 = run { val _simdArg0 = a2; val _simdArg1 = a2; val _simdArg2 = SimdShuffleIdx(uintArrayOf(1u, 0u, 2u, 3u, 4u, 5u, 6u, 7u)); _simdArg0 }
        run { val _simdArg0 = mmMaxSh(a2, b2); val _simdArg1 = F16.fromFloat(0.0f); _simdArg0 }
    }

/**
 * Reduce the packed half-precision (16-bit) floating-point elements in a by maximum. Returns the
 * maximum of all elements in a.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_reduce_max_ph)
 */
public fun mm256ReduceMaxPh(a: M256h): F16 =
    run {
        val p = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u)); _simdArg0 }
        val q = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u)); _simdArg0 }
        mmReduceMaxPh(mmMaxPh(p, q))
    }

/**
 * Reduce the packed half-precision (16-bit) floating-point elements in a by maximum. Returns the
 * maximum of all elements in a.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_reduce_max_ph)
 */
public fun mm512ReduceMaxPh(a: M512h): F16 =
    run {
        val p = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u)); _simdArg0 }
        val q = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(16u, 17u, 18u, 19u, 20u, 21u, 22u, 23u, 24u, 25u, 26u, 27u, 28u, 29u, 30u, 31u)); _simdArg0 }
        mm256ReduceMaxPh(mm256MaxPh(p, q))
    }

/**
 * Test packed half-precision (16-bit) floating-point elements in a for special categories specified
 * by imm8, and store the results in mask vector k.
 * imm can be a combination of:
 *
 *     0x01 // QNaN
 *     0x02 // Positive Zero
 *     0x04 // Negative Zero
 *     0x08 // Positive Infinity
 *     0x10 // Negative Infinity
 *     0x20 // Denormal
 *     0x40 // Negative
 *     0x80 // SNaN
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fpclass_ph_mask)
 */
public fun mmFpclassPhMask(a: M128h, imm8: Int): Mmask8 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        vfpclassph128(a, imm8)
    }

/**
 * Test packed half-precision (16-bit) floating-point elements in a for special categories specified
 * by imm8, and store the results in mask vector k using zeromask k (elements are zeroed out when the
 * corresponding mask bit is not set).
 * imm can be a combination of:
 *
 *     0x01 // QNaN
 *     0x02 // Positive Zero
 *     0x04 // Negative Zero
 *     0x08 // Positive Infinity
 *     0x10 // Negative Infinity
 *     0x20 // Denormal
 *     0x40 // Negative
 *     0x80 // SNaN
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fpclass_ph_mask)
 */
public fun mmMaskFpclassPhMask(k1: Mmask8, a: M128h, imm8: Int): Mmask8 = mmFpclassPhMask(a, imm8) and k1

/**
 * Test packed half-precision (16-bit) floating-point elements in a for special categories specified
 * by imm8, and store the results in mask vector k.
 * imm can be a combination of:
 *
 *     0x01 // QNaN
 *     0x02 // Positive Zero
 *     0x04 // Negative Zero
 *     0x08 // Positive Infinity
 *     0x10 // Negative Infinity
 *     0x20 // Denormal
 *     0x40 // Negative
 *     0x80 // SNaN
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_fpclass_ph_mask)
 */
public fun mm256FpclassPhMask(a: M256h, imm8: Int): Mmask16 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        vfpclassph256(a, imm8)
    }

/**
 * Test packed half-precision (16-bit) floating-point elements in a for special categories specified
 * by imm8, and store the results in mask vector k using zeromask k (elements are zeroed out when the
 * corresponding mask bit is not set).
 * imm can be a combination of:
 *
 *     0x01 // QNaN
 *     0x02 // Positive Zero
 *     0x04 // Negative Zero
 *     0x08 // Positive Infinity
 *     0x10 // Negative Infinity
 *     0x20 // Denormal
 *     0x40 // Negative
 *     0x80 // SNaN
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_fpclass_ph_mask)
 */
public fun mm256MaskFpclassPhMask(k1: Mmask16, a: M256h, imm8: Int): Mmask16 = mm256FpclassPhMask(a, imm8) and k1

/**
 * Test packed half-precision (16-bit) floating-point elements in a for special categories specified
 * by imm8, and store the results in mask vector k.
 * imm can be a combination of:
 *
 *     0x01 // QNaN
 *     0x02 // Positive Zero
 *     0x04 // Negative Zero
 *     0x08 // Positive Infinity
 *     0x10 // Negative Infinity
 *     0x20 // Denormal
 *     0x40 // Negative
 *     0x80 // SNaN
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_fpclass_ph_mask)
 */
public fun mm512FpclassPhMask(a: M512h, imm8: Int): Mmask32 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        vfpclassph512(a, imm8)
    }

/**
 * Test packed half-precision (16-bit) floating-point elements in a for special categories specified
 * by imm8, and store the results in mask vector k using zeromask k (elements are zeroed out when the
 * corresponding mask bit is not set).
 * imm can be a combination of:
 *
 *     0x01 // QNaN
 *     0x02 // Positive Zero
 *     0x04 // Negative Zero
 *     0x08 // Positive Infinity
 *     0x10 // Negative Infinity
 *     0x20 // Denormal
 *     0x40 // Negative
 *     0x80 // SNaN
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_fpclass_ph_mask)
 */
public fun mm512MaskFpclassPhMask(k1: Mmask32, a: M512h, imm8: Int): Mmask32 = mm512FpclassPhMask(a, imm8) and k1

/**
 * Test the lower half-precision (16-bit) floating-point element in a for special categories specified
 * by imm8, and store the result in mask vector k.
 * imm can be a combination of:
 *
 *     0x01 // QNaN
 *     0x02 // Positive Zero
 *     0x04 // Negative Zero
 *     0x08 // Positive Infinity
 *     0x10 // Negative Infinity
 *     0x20 // Denormal
 *     0x40 // Negative
 *     0x80 // SNaN
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_fpclass_sh_mask)
 */
public fun mmFpclassShMask(a: M128h, imm8: Int): Mmask8 = mmMaskFpclassShMask(0xffu, a, imm8)

/**
 * Test the lower half-precision (16-bit) floating-point element in a for special categories specified
 * by imm8, and store the result in mask vector k using zeromask k (elements are zeroed out when the
 * corresponding mask bit is not set).
 * imm can be a combination of:
 *
 *     0x01 // QNaN
 *     0x02 // Positive Zero
 *     0x04 // Negative Zero
 *     0x08 // Positive Infinity
 *     0x10 // Negative Infinity
 *     0x20 // Denormal
 *     0x40 // Negative
 *     0x80 // SNaN
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_fpclass_sh_mask)
 */
public fun mmMaskFpclassShMask(k1: Mmask8, a: M128h, imm8: Int): Mmask8 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        vfpclasssh(a, imm8, k1)
    }

/**
 * Blend packed half-precision (16-bit) floating-point elements from a and b using control mask k,
 * and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_blend_ph)
 */
public fun mmMaskBlendPh(k: Mmask8, a: M128h, b: M128h): M128h = run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = a; _simdArg1 }

/**
 * Blend packed half-precision (16-bit) floating-point elements from a and b using control mask k,
 * and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_blend_ph)
 */
public fun mm256MaskBlendPh(k: Mmask16, a: M256h, b: M256h): M256h = run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = a; _simdArg1 }

/**
 * Blend packed half-precision (16-bit) floating-point elements from a and b using control mask k,
 * and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_blend_ph)
 */
public fun mm512MaskBlendPh(k: Mmask32, a: M512h, b: M512h): M512h = run { val _simdArg0 = k; val _simdArg1 = b; val _simdArg2 = a; _simdArg1 }

/**
 * Shuffle half-precision (16-bit) floating-point elements in a and b using the corresponding selector
 * and index in idx, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_permutex2var_ph)
 */
public fun mmPermutex2varPh(a: M128h, idx: M128i, b: M128h): M128h = mmCastsi128Ph(mmPermutex2varEpi16(mmCastphSi128(a), idx, mmCastphSi128(b)))

/**
 * Shuffle half-precision (16-bit) floating-point elements in a and b using the corresponding selector
 * and index in idx, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_permutex2var_ph)
 */
public fun mm256Permutex2varPh(a: M256h, idx: M256i, b: M256h): M256h = mm256Castsi256Ph(mm256Permutex2varEpi16(mm256CastphSi256(a), idx, mm256CastphSi256(b)))

/**
 * Shuffle half-precision (16-bit) floating-point elements in a and b using the corresponding selector
 * and index in idx, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_permutex2var_ph)
 */
public fun mm512Permutex2varPh(a: M512h, idx: M512i, b: M512h): M512h = mm512Castsi512Ph(mm512Permutex2varEpi16(mm512CastphSi512(a), idx, mm512CastphSi512(b)))

/**
 * Shuffle half-precision (16-bit) floating-point elements in a using the corresponding index in idx,
 * and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_permutexvar_ph)
 */
public fun mmPermutexvarPh(idx: M128i, a: M128h): M128h = mmCastsi128Ph(mmPermutexvarEpi16(idx, mmCastphSi128(a)))

/**
 * Shuffle half-precision (16-bit) floating-point elements in a using the corresponding index in idx,
 * and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_permutexvar_ph)
 */
public fun mm256PermutexvarPh(idx: M256i, a: M256h): M256h = mm256Castsi256Ph(mm256PermutexvarEpi16(idx, mm256CastphSi256(a)))

/**
 * Shuffle half-precision (16-bit) floating-point elements in a using the corresponding index in idx,
 * and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_permutexvar_ph)
 */
public fun mm512PermutexvarPh(idx: M512i, a: M512h): M512h = mm512Castsi512Ph(mm512PermutexvarEpi16(idx, mm512CastphSi512(a)))

/**
 * Convert packed signed 16-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtepi16_ph)
 */
public fun mmCvtepi16Ph(a: M128i): M128h = vcvtw2ph128(a.asI16x8(), MM_FROUND_CUR_DIRECTION)

/**
 * Convert packed signed 16-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src to dst when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvtepi16_ph)
 */
public fun mmMaskCvtepi16Ph(src: M128h, k: Mmask8, a: M128i): M128h = run { val _simdArg0 = k; val _simdArg1 = mmCvtepi16Ph(a); val _simdArg2 = src; _simdArg1 }

/**
 * Convert packed signed 16-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvtepi16_ph)
 */
public fun mmMaskzCvtepi16Ph(k: Mmask8, a: M128i): M128h = mmMaskCvtepi16Ph(mmSetzeroPh(), k, a)

/**
 * Convert packed signed 16-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvtepi16_ph)
 */
public fun mm256Cvtepi16Ph(a: M256i): M256h = vcvtw2ph256(a.asI16x16(), MM_FROUND_CUR_DIRECTION)

/**
 * Convert packed signed 16-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src to dst when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_cvtepi16_ph)
 */
public fun mm256MaskCvtepi16Ph(src: M256h, k: Mmask16, a: M256i): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256Cvtepi16Ph(a); val _simdArg2 = src; _simdArg1 }

/**
 * Convert packed signed 16-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_cvtepi16_ph)
 */
public fun mm256MaskzCvtepi16Ph(k: Mmask16, a: M256i): M256h = mm256MaskCvtepi16Ph(mm256SetzeroPh(), k, a)

/**
 * Convert packed signed 16-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtepi16_ph)
 */
public fun mm512Cvtepi16Ph(a: M512i): M512h = vcvtw2ph512(a.asI16x32(), MM_FROUND_CUR_DIRECTION)

/**
 * Convert packed signed 16-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src to dst when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvtepi16_ph)
 */
public fun mm512MaskCvtepi16Ph(src: M512h, k: Mmask32, a: M512i): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512Cvtepi16Ph(a); val _simdArg2 = src; _simdArg1 }

/**
 * Convert packed signed 16-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvtepi16_ph)
 */
public fun mm512MaskzCvtepi16Ph(k: Mmask32, a: M512i): M512h = mm512MaskCvtepi16Ph(mm512SetzeroPh(), k, a)

/**
 * Convert packed signed 16-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvt_roundepi16_ph)
 */
public fun mm512CvtRoundepi16Ph(a: M512i, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vcvtw2ph512(a.asI16x32(), rounding)
    }

/**
 * Convert packed signed 16-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src to dst when the corresponding
 * mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvt_roundepi16_ph)
 */
public fun mm512MaskCvtRoundepi16Ph(src: M512h, k: Mmask32, a: M512i, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512CvtRoundepi16Ph(a, rounding); val _simdArg2 = src; _simdArg1 }
    }

/**
 * Convert packed signed 16-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvt_roundepi16_ph)
 */
public fun mm512MaskzCvtRoundepi16Ph(k: Mmask32, a: M512i, rounding: Int): M512h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundepi16Ph(mm512SetzeroPh(), k, a, rounding)
}

/**
 * Convert packed unsigned 16-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtepu16_ph)
 */
public fun mmCvtepu16Ph(a: M128i): M128h = vcvtuw2ph128(a.asU16x8(), MM_FROUND_CUR_DIRECTION)

/**
 * Convert packed unsigned 16-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src to dst when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvtepu16_ph)
 */
public fun mmMaskCvtepu16Ph(src: M128h, k: Mmask8, a: M128i): M128h = run { val _simdArg0 = k; val _simdArg1 = mmCvtepu16Ph(a); val _simdArg2 = src; _simdArg1 }

/**
 * Convert packed unsigned 16-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvtepu16_ph)
 */
public fun mmMaskzCvtepu16Ph(k: Mmask8, a: M128i): M128h = mmMaskCvtepu16Ph(mmSetzeroPh(), k, a)

/**
 * Convert packed unsigned 16-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvtepu16_ph)
 */
public fun mm256Cvtepu16Ph(a: M256i): M256h = vcvtuw2ph256(a.asU16x16(), MM_FROUND_CUR_DIRECTION)

/**
 * Convert packed unsigned 16-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src to dst when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_cvtepu16_ph)
 */
public fun mm256MaskCvtepu16Ph(src: M256h, k: Mmask16, a: M256i): M256h = run { val _simdArg0 = k; val _simdArg1 = mm256Cvtepu16Ph(a); val _simdArg2 = src; _simdArg1 }

/**
 * Convert packed unsigned 16-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_cvtepu16_ph)
 */
public fun mm256MaskzCvtepu16Ph(k: Mmask16, a: M256i): M256h = mm256MaskCvtepu16Ph(mm256SetzeroPh(), k, a)

/**
 * Convert packed unsigned 16-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtepu16_ph)
 */
public fun mm512Cvtepu16Ph(a: M512i): M512h = vcvtuw2ph512(a.asU16x32(), MM_FROUND_CUR_DIRECTION)

/**
 * Convert packed unsigned 16-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src to dst when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvtepu16_ph)
 */
public fun mm512MaskCvtepu16Ph(src: M512h, k: Mmask32, a: M512i): M512h = run { val _simdArg0 = k; val _simdArg1 = mm512Cvtepu16Ph(a); val _simdArg2 = src; _simdArg1 }

/**
 * Convert packed unsigned 16-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvtepu16_ph)
 */
public fun mm512MaskzCvtepu16Ph(k: Mmask32, a: M512i): M512h = mm512MaskCvtepu16Ph(mm512SetzeroPh(), k, a)

/**
 * Convert packed unsigned 16-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvt_roundepu16_ph)
 */
public fun mm512CvtRoundepu16Ph(a: M512i, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vcvtuw2ph512(a.asU16x32(), rounding)
    }

/**
 * Convert packed unsigned 16-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src to dst when the corresponding
 * mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvt_roundepu16_ph)
 */
public fun mm512MaskCvtRoundepu16Ph(src: M512h, k: Mmask32, a: M512i, rounding: Int): M512h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512CvtRoundepu16Ph(a, rounding); val _simdArg2 = src; _simdArg1 }
    }

/**
 * Convert packed unsigned 16-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvt_roundepu16_ph)
 */
public fun mm512MaskzCvtRoundepu16Ph(k: Mmask32, a: M512i, rounding: Int): M512h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundepu16Ph(mm512SetzeroPh(), k, a, rounding)
}

/**
 * Convert packed signed 32-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst. The upper 64 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtepi32_ph)
 */
public fun mmCvtepi32Ph(a: M128i): M128h = mmMaskCvtepi32Ph(mmSetzeroPh(), 0xffu, a)

/**
 * Convert packed signed 32-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src to dst when the corresponding
 * mask bit is not set). The upper 64 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvtepi32_ph)
 */
public fun mmMaskCvtepi32Ph(src: M128h, k: Mmask8, a: M128i): M128h = vcvtdq2ph128(a.asI32x4(), src, k)

/**
 * Convert packed signed 32-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 * The upper 64 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvtepi32_ph)
 */
public fun mmMaskzCvtepi32Ph(k: Mmask8, a: M128i): M128h = mmMaskCvtepi32Ph(mmSetzeroPh(), k, a)

/**
 * Convert packed signed 32-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvtepi32_ph)
 */
public fun mm256Cvtepi32Ph(a: M256i): M128h = vcvtdq2ph256(a.asI32x8(), MM_FROUND_CUR_DIRECTION)

/**
 * Convert packed signed 32-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src to dst when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_cvtepi32_ph)
 */
public fun mm256MaskCvtepi32Ph(src: M128h, k: Mmask8, a: M256i): M128h = run { val _simdArg0 = k; val _simdArg1 = mm256Cvtepi32Ph(a); val _simdArg2 = src; _simdArg1 }

/**
 * Convert packed signed 32-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_cvtepi32_ph)
 */
public fun mm256MaskzCvtepi32Ph(k: Mmask8, a: M256i): M128h = mm256MaskCvtepi32Ph(mmSetzeroPh(), k, a)

/**
 * Convert packed signed 32-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtepi32_ph)
 */
public fun mm512Cvtepi32Ph(a: M512i): M256h = vcvtdq2ph512(a.asI32x16(), MM_FROUND_CUR_DIRECTION)

/**
 * Convert packed signed 32-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src to dst when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvtepi32_ph)
 */
public fun mm512MaskCvtepi32Ph(src: M256h, k: Mmask16, a: M512i): M256h = run { val _simdArg0 = k; val _simdArg1 = mm512Cvtepi32Ph(a); val _simdArg2 = src; _simdArg1 }

/**
 * Convert packed signed 32-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvtepi32_ph)
 */
public fun mm512MaskzCvtepi32Ph(k: Mmask16, a: M512i): M256h = mm512MaskCvtepi32Ph(Simd<F16>(List(16) { F16.fromFloat(0.0f) }).asM256h(), k, a)

/**
 * Convert packed signed 32-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvt_roundepi32_ph)
 */
public fun mm512CvtRoundepi32Ph(a: M512i, rounding: Int): M256h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vcvtdq2ph512(a.asI32x16(), rounding)
    }

/**
 * Convert packed signed 32-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src to dst when the corresponding
 * mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvt_roundepi32_ph)
 */
public fun mm512MaskCvtRoundepi32Ph(src: M256h, k: Mmask16, a: M512i, rounding: Int): M256h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512CvtRoundepi32Ph(a, rounding); val _simdArg2 = src; _simdArg1 }
    }

/**
 * Convert packed signed 32-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvt_roundepi32_ph)
 */
public fun mm512MaskzCvtRoundepi32Ph(k: Mmask16, a: M512i, rounding: Int): M256h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundepi32Ph(Simd<F16>(List(16) { F16.fromFloat(0.0f) }).asM256h(), k, a, rounding)
}

/**
 * Convert the signed 32-bit integer b to a half-precision (16-bit) floating-point element, store the
 * result in the lower element of dst, and copy the upper 7 packed elements from a to the upper elements
 * of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvti32_sh)
 */
public fun mmCvti32Sh(a: M128h, b: Int): M128h = vcvtsi2sh(a, b, MM_FROUND_CUR_DIRECTION)

/**
 * Convert the signed 32-bit integer b to a half-precision (16-bit) floating-point element, store the
 * result in the lower element of dst, and copy the upper 7 packed elements from a to the upper elements
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
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvt_roundi32_sh)
 */
public fun mmCvtRoundi32Sh(a: M128h, b: Int, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vcvtsi2sh(a, b, rounding)
    }

/**
 * Convert packed unsigned 32-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst. The upper 64 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtepu32_ph)
 */
public fun mmCvtepu32Ph(a: M128i): M128h = mmMaskCvtepu32Ph(mmSetzeroPh(), 0xffu, a)

/**
 * Convert packed unsigned 32-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src to dst when the corresponding
 * mask bit is not set). The upper 64 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvtepu32_ph)
 */
public fun mmMaskCvtepu32Ph(src: M128h, k: Mmask8, a: M128i): M128h = vcvtudq2ph128(a.asU32x4(), src, k)

/**
 * Convert packed unsigned 32-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 * The upper 64 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvtepu32_ph)
 */
public fun mmMaskzCvtepu32Ph(k: Mmask8, a: M128i): M128h = mmMaskCvtepu32Ph(mmSetzeroPh(), k, a)

/**
 * Convert packed unsigned 32-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvtepu32_ph)
 */
public fun mm256Cvtepu32Ph(a: M256i): M128h = vcvtudq2ph256(a.asU32x8(), MM_FROUND_CUR_DIRECTION)

/**
 * Convert packed unsigned 32-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src to dst when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_cvtepu32_ph)
 */
public fun mm256MaskCvtepu32Ph(src: M128h, k: Mmask8, a: M256i): M128h = run { val _simdArg0 = k; val _simdArg1 = mm256Cvtepu32Ph(a); val _simdArg2 = src; _simdArg1 }

/**
 * Convert packed unsigned 32-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_cvtepu32_ph)
 */
public fun mm256MaskzCvtepu32Ph(k: Mmask8, a: M256i): M128h = mm256MaskCvtepu32Ph(mmSetzeroPh(), k, a)

/**
 * Convert packed unsigned 32-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtepu32_ph)
 */
public fun mm512Cvtepu32Ph(a: M512i): M256h = vcvtudq2ph512(a.asU32x16(), MM_FROUND_CUR_DIRECTION)

/**
 * Convert packed unsigned 32-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src to dst when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvtepu32_ph)
 */
public fun mm512MaskCvtepu32Ph(src: M256h, k: Mmask16, a: M512i): M256h = run { val _simdArg0 = k; val _simdArg1 = mm512Cvtepu32Ph(a); val _simdArg2 = src; _simdArg1 }

/**
 * Convert packed unsigned 32-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvtepu32_ph)
 */
public fun mm512MaskzCvtepu32Ph(k: Mmask16, a: M512i): M256h = mm512MaskCvtepu32Ph(Simd<F16>(List(16) { F16.fromFloat(0.0f) }).asM256h(), k, a)

/**
 * Convert packed unsigned 32-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvt_roundepu32_ph)
 */
public fun mm512CvtRoundepu32Ph(a: M512i, rounding: Int): M256h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vcvtudq2ph512(a.asU32x16(), rounding)
    }

/**
 * Convert packed unsigned 32-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src to dst when the corresponding
 * mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvt_roundepu32_ph)
 */
public fun mm512MaskCvtRoundepu32Ph(src: M256h, k: Mmask16, a: M512i, rounding: Int): M256h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512CvtRoundepu32Ph(a, rounding); val _simdArg2 = src; _simdArg1 }
    }

/**
 * Convert packed unsigned 32-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvt_roundepu32_ph)
 */
public fun mm512MaskzCvtRoundepu32Ph(k: Mmask16, a: M512i, rounding: Int): M256h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundepu32Ph(Simd<F16>(List(16) { F16.fromFloat(0.0f) }).asM256h(), k, a, rounding)
}

/**
 * Convert the unsigned 32-bit integer b to a half-precision (16-bit) floating-point element, store the
 * result in the lower element of dst, and copy the upper 7 packed elements from a to the upper elements
 * of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtu32_sh)
 */
public fun mmCvtu32Sh(a: M128h, b: UInt): M128h = vcvtusi2sh(a, b, MM_FROUND_CUR_DIRECTION)

/**
 * Convert the unsigned 32-bit integer b to a half-precision (16-bit) floating-point element, store the
 * result in the lower element of dst, and copy the upper 7 packed elements from a to the upper elements
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
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvt_roundu32_sh)
 */
public fun mmCvtRoundu32Sh(a: M128h, b: UInt, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vcvtusi2sh(a, b, rounding)
    }

/**
 * Convert packed signed 64-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst. The upper 96 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtepi64_ph)
 */
public fun mmCvtepi64Ph(a: M128i): M128h = mmMaskCvtepi64Ph(mmSetzeroPh(), 0xffu, a)

/**
 * Convert packed signed 64-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src to dst when the corresponding
 * mask bit is not set). The upper 96 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvtepi64_ph)
 */
public fun mmMaskCvtepi64Ph(src: M128h, k: Mmask8, a: M128i): M128h = vcvtqq2ph128(a.asI64x2(), src, k)

/**
 * Convert packed signed 64-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 * The upper 96 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvtepi64_ph)
 */
public fun mmMaskzCvtepi64Ph(k: Mmask8, a: M128i): M128h = mmMaskCvtepi64Ph(mmSetzeroPh(), k, a)

/**
 * Convert packed signed 64-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst. The upper 64 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvtepi64_ph)
 */
public fun mm256Cvtepi64Ph(a: M256i): M128h = mm256MaskCvtepi64Ph(mmSetzeroPh(), 0xffu, a)

/**
 * Convert packed signed 64-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src to dst when the corresponding
 * mask bit is not set). The upper 64 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_cvtepi64_ph)
 */
public fun mm256MaskCvtepi64Ph(src: M128h, k: Mmask8, a: M256i): M128h = vcvtqq2ph256(a.asI64x4(), src, k)

/**
 * Convert packed signed 64-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 * The upper 64 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_cvtepi64_ph)
 */
public fun mm256MaskzCvtepi64Ph(k: Mmask8, a: M256i): M128h = mm256MaskCvtepi64Ph(mmSetzeroPh(), k, a)

/**
 * Convert packed signed 64-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtepi64_ph)
 */
public fun mm512Cvtepi64Ph(a: M512i): M128h = vcvtqq2ph512(a.asI64x8(), MM_FROUND_CUR_DIRECTION)

/**
 * Convert packed signed 64-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src to dst when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvtepi64_ph)
 */
public fun mm512MaskCvtepi64Ph(src: M128h, k: Mmask8, a: M512i): M128h = run { val _simdArg0 = k; val _simdArg1 = mm512Cvtepi64Ph(a); val _simdArg2 = src; _simdArg1 }

/**
 * Convert packed signed 64-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvtepi64_ph)
 */
public fun mm512MaskzCvtepi64Ph(k: Mmask8, a: M512i): M128h = mm512MaskCvtepi64Ph(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a)

/**
 * Convert packed signed 64-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvt_roundepi64_ph)
 */
public fun mm512CvtRoundepi64Ph(a: M512i, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vcvtqq2ph512(a.asI64x8(), rounding)
    }

/**
 * Convert packed signed 64-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src to dst when the corresponding
 * mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvt_roundepi64_ph)
 */
public fun mm512MaskCvtRoundepi64Ph(src: M128h, k: Mmask8, a: M512i, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512CvtRoundepi64Ph(a, rounding); val _simdArg2 = src; _simdArg1 }
    }

/**
 * Convert packed signed 64-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvt_roundepi64_ph)
 */
public fun mm512MaskzCvtRoundepi64Ph(k: Mmask8, a: M512i, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundepi64Ph(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, rounding)
}

/**
 * Convert packed unsigned 64-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst. The upper 96 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtepu64_ph)
 */
public fun mmCvtepu64Ph(a: M128i): M128h = mmMaskCvtepu64Ph(mmSetzeroPh(), 0xffu, a)

/**
 * Convert packed unsigned 64-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src to dst when the corresponding
 * mask bit is not set). The upper 96 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvtepu64_ph)
 */
public fun mmMaskCvtepu64Ph(src: M128h, k: Mmask8, a: M128i): M128h = vcvtuqq2ph128(a.asU64x2(), src, k)

/**
 * Convert packed unsigned 64-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 * The upper 96 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvtepu64_ph)
 */
public fun mmMaskzCvtepu64Ph(k: Mmask8, a: M128i): M128h = mmMaskCvtepu64Ph(mmSetzeroPh(), k, a)

/**
 * Convert packed unsigned 64-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst. The upper 64 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvtepu64_ph)
 */
public fun mm256Cvtepu64Ph(a: M256i): M128h = mm256MaskCvtepu64Ph(mmSetzeroPh(), 0xffu, a)

/**
 * Convert packed unsigned 64-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src to dst when the corresponding
 * mask bit is not set). The upper 64 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_cvtepu64_ph)
 */
public fun mm256MaskCvtepu64Ph(src: M128h, k: Mmask8, a: M256i): M128h = vcvtuqq2ph256(a.asU64x4(), src, k)

/**
 * Convert packed unsigned 64-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 * The upper 64 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_cvtepu64_ph)
 */
public fun mm256MaskzCvtepu64Ph(k: Mmask8, a: M256i): M128h = mm256MaskCvtepu64Ph(mmSetzeroPh(), k, a)

/**
 * Convert packed unsigned 64-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtepu64_ph)
 */
public fun mm512Cvtepu64Ph(a: M512i): M128h = vcvtuqq2ph512(a.asU64x8(), MM_FROUND_CUR_DIRECTION)

/**
 * Convert packed unsigned 64-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src to dst when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvtepu64_ph)
 */
public fun mm512MaskCvtepu64Ph(src: M128h, k: Mmask8, a: M512i): M128h = run { val _simdArg0 = k; val _simdArg1 = mm512Cvtepu64Ph(a); val _simdArg2 = src; _simdArg1 }

/**
 * Convert packed unsigned 64-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvtepu64_ph)
 */
public fun mm512MaskzCvtepu64Ph(k: Mmask8, a: M512i): M128h = mm512MaskCvtepu64Ph(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a)

/**
 * Convert packed unsigned 64-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvt_roundepu64_ph)
 */
public fun mm512CvtRoundepu64Ph(a: M512i, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vcvtuqq2ph512(a.asU64x8(), rounding)
    }

/**
 * Convert packed unsigned 64-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using writemask k (elements are copied from src to dst when the corresponding
 * mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvt_roundepu64_ph)
 */
public fun mm512MaskCvtRoundepu64Ph(src: M128h, k: Mmask8, a: M512i, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        run { val _simdArg0 = k; val _simdArg1 = mm512CvtRoundepu64Ph(a, rounding); val _simdArg2 = src; _simdArg1 }
    }

/**
 * Convert packed unsigned 64-bit integers in a to packed half-precision (16-bit) floating-point elements,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvt_roundepu64_ph)
 */
public fun mm512MaskzCvtRoundepu64Ph(k: Mmask8, a: M512i, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundepu64Ph(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, rounding)
}

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed half-precision (16-bit)
 * floating-point elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtxps_ph)
 */
public fun mmCvtxpsPh(a: M128): M128h = mmMaskCvtxpsPh(mmSetzeroPh(), 0xffu, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed half-precision (16-bit)
 * floating-point elements, and store the results in dst using writemask k (elements are copied from src to dst
 * when the corresponding mask bit is not set). The upper 64 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvtxps_ph)
 */
public fun mmMaskCvtxpsPh(src: M128h, k: Mmask8, a: M128): M128h = vcvtps2phx128(a, src, k)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed half-precision (16-bit)
 * floating-point elements, and store the results in dst using zeromask k (elements are zeroed out when the
 * corresponding mask bit is not set). The upper 64 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvtxps_ph)
 */
public fun mmMaskzCvtxpsPh(k: Mmask8, a: M128): M128h = mmMaskCvtxpsPh(mmSetzeroPh(), k, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed half-precision (16-bit)
 * floating-point elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvtxps_ph)
 */
public fun mm256CvtxpsPh(a: M256): M128h = mm256MaskCvtxpsPh(mmSetzeroPh(), 0xffu, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed half-precision (16-bit)
 * floating-point elements, and store the results in dst using writemask k (elements are copied from src to dst
 * when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_cvtxps_ph)
 */
public fun mm256MaskCvtxpsPh(src: M128h, k: Mmask8, a: M256): M128h = vcvtps2phx256(a, src, k)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed half-precision (16-bit)
 * floating-point elements, and store the results in dst using zeromask k (elements are zeroed out when the
 * corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_cvtxps_ph)
 */
public fun mm256MaskzCvtxpsPh(k: Mmask8, a: M256): M128h = mm256MaskCvtxpsPh(mmSetzeroPh(), k, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed half-precision (16-bit)
 * floating-point elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtxps_ph)
 */
public fun mm512CvtxpsPh(a: M512): M256h = mm512MaskCvtxpsPh(Simd<F16>(List(16) { F16.fromFloat(0.0f) }).asM256h(), 0xffffu, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed half-precision (16-bit)
 * floating-point elements, and store the results in dst using writemask k (elements are copied from src to dst
 * when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvtxps_ph)
 */
public fun mm512MaskCvtxpsPh(src: M256h, k: Mmask16, a: M512): M256h = vcvtps2phx512(a, src, k, MM_FROUND_CUR_DIRECTION)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed half-precision (16-bit)
 * floating-point elements, and store the results in dst using zeromask k (elements are zeroed out when the
 * corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvtxps_ph)
 */
public fun mm512MaskzCvtxpsPh(k: Mmask16, a: M512): M256h = mm512MaskCvtxpsPh(Simd<F16>(List(16) { F16.fromFloat(0.0f) }).asM256h(), k, a)

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed half-precision (16-bit)
 * floating-point elements, and store the results in dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtx_roundps_ph)
 */
public fun mm512CvtxRoundpsPh(a: M512, rounding: Int): M256h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtxRoundpsPh(Simd<F16>(List(16) { F16.fromFloat(0.0f) }).asM256h(), 0xffffu, a, rounding)
}

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed half-precision (16-bit)
 * floating-point elements, and store the results in dst using writemask k (elements are copied from src to dst
 * when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvtx_roundps_ph)
 */
public fun mm512MaskCvtxRoundpsPh(src: M256h, k: Mmask16, a: M512, rounding: Int): M256h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vcvtps2phx512(a, src, k, rounding)
    }

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed half-precision (16-bit)
 * floating-point elements, and store the results in dst using zeromask k (elements are zeroed out when the
 * corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvtx_roundps_ph)
 */
public fun mm512MaskzCvtxRoundpsPh(k: Mmask16, a: M512, rounding: Int): M256h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtxRoundpsPh(Simd<F16>(List(16) { F16.fromFloat(0.0f) }).asM256h(), k, a, rounding)
}

/**
 * Convert the lower single-precision (32-bit) floating-point element in b to a half-precision (16-bit)
 * floating-point elements, store the result in the lower element of dst, and copy the upper 7 packed
 * elements from a to the upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtss_sh)
 */
public fun mmCvtssSh(a: M128h, b: M128): M128h = mmMaskCvtssSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b)

/**
 * Convert the lower single-precision (32-bit) floating-point element in b to a half-precision (16-bit)
 * floating-point elements, store the result in the lower element of dst using writemask k (the element
 * if copied from src when mask bit 0 is not set), and copy the upper 7 packed elements from a to the
 * upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvtss_sh)
 */
public fun mmMaskCvtssSh(src: M128h, k: Mmask8, a: M128h, b: M128): M128h = vcvtss2sh(a, b, src, k, MM_FROUND_CUR_DIRECTION)

/**
 * Convert the lower single-precision (32-bit) floating-point element in b to a half-precision (16-bit)
 * floating-point elements, store the result in the lower element of dst using zeromask k (the element
 * is zeroed out when mask bit 0 is not set), and copy the upper 7 packed elements from a to the upper
 * elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvtss_sh)
 */
public fun mmMaskzCvtssSh(k: Mmask8, a: M128h, b: M128): M128h = mmMaskCvtssSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b)

/**
 * Convert the lower single-precision (32-bit) floating-point element in b to a half-precision (16-bit)
 * floating-point elements, store the result in the lower element of dst, and copy the upper 7 packed
 * elements from a to the upper elements of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvt_roundss_sh)
 */
public fun mmCvtRoundssSh(a: M128h, b: M128, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmMaskCvtRoundssSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b, rounding)
}

/**
 * Convert the lower single-precision (32-bit) floating-point element in b to a half-precision (16-bit)
 * floating-point elements, store the result in the lower element of dst using writemask k (the element
 * if copied from src when mask bit 0 is not set), and copy the upper 7 packed elements from a to the
 * upper elements of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvt_roundss_sh)
 */
public fun mmMaskCvtRoundssSh(src: M128h, k: Mmask8, a: M128h, b: M128, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vcvtss2sh(a, b, src, k, rounding)
    }

/**
 * Convert the lower single-precision (32-bit) floating-point element in b to a half-precision (16-bit)
 * floating-point elements, store the result in the lower element of dst using zeromask k (the element
 * is zeroed out when mask bit 0 is not set), and copy the upper 7 packed elements from a to the upper
 * elements of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvt_roundss_sh)
 */
public fun mmMaskzCvtRoundssSh(k: Mmask8, a: M128h, b: M128, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmMaskCvtRoundssSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b, rounding)
}

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed half-precision (16-bit)
 * floating-point elements, and store the results in dst. The upper 96 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtpd_ph)
 */
public fun mmCvtpdPh(a: M128d): M128h = mmMaskCvtpdPh(mmSetzeroPh(), 0xffu, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed half-precision (16-bit)
 * floating-point elements, and store the results in dst using writemask k (elements are copied from src to dst
 * when the corresponding mask bit is not set). The upper 96 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvtpd_ph)
 */
public fun mmMaskCvtpdPh(src: M128h, k: Mmask8, a: M128d): M128h = vcvtpd2ph128(a, src, k)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed half-precision (16-bit)
 * floating-point elements, and store the results in dst using zeromask k (elements are zeroed out when the
 * corresponding mask bit is not set). The upper 96 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvtpd_ph)
 */
public fun mmMaskzCvtpdPh(k: Mmask8, a: M128d): M128h = mmMaskCvtpdPh(mmSetzeroPh(), k, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed half-precision (16-bit)
 * floating-point elements, and store the results in dst. The upper 64 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvtpd_ph)
 */
public fun mm256CvtpdPh(a: M256d): M128h = mm256MaskCvtpdPh(mmSetzeroPh(), 0xffu, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed half-precision (16-bit)
 * floating-point elements, and store the results in dst using writemask k (elements are copied from src to dst
 * when the corresponding mask bit is not set). The upper 64 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_cvtpd_ph)
 */
public fun mm256MaskCvtpdPh(src: M128h, k: Mmask8, a: M256d): M128h = vcvtpd2ph256(a, src, k)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed half-precision (16-bit)
 * floating-point elements, and store the results in dst using zeromask k (elements are zeroed out when the
 * corresponding mask bit is not set). The upper 64 bits of dst are zeroed out.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_cvtpd_ph)
 */
public fun mm256MaskzCvtpdPh(k: Mmask8, a: M256d): M128h = mm256MaskCvtpdPh(mmSetzeroPh(), k, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed half-precision (16-bit)
 * floating-point elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtpd_ph)
 */
public fun mm512CvtpdPh(a: M512d): M128h = mm512MaskCvtpdPh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed half-precision (16-bit)
 * floating-point elements, and store the results in dst using writemask k (elements are copied from src to dst
 * when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvtpd_ph)
 */
public fun mm512MaskCvtpdPh(src: M128h, k: Mmask8, a: M512d): M128h = vcvtpd2ph512(a, src, k, MM_FROUND_CUR_DIRECTION)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed half-precision (16-bit)
 * floating-point elements, and store the results in dst using zeromask k (elements are zeroed out when the
 * corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvtpd_ph)
 */
public fun mm512MaskzCvtpdPh(k: Mmask8, a: M512d): M128h = mm512MaskCvtpdPh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a)

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed half-precision (16-bit)
 * floating-point elements, and store the results in dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvt_roundpd_ph)
 */
public fun mm512CvtRoundpdPh(a: M512d, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundpdPh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, rounding)
}

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed half-precision (16-bit)
 * floating-point elements, and store the results in dst using writemask k (elements are copied from src to dst
 * when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvt_roundpd_ph)
 */
public fun mm512MaskCvtRoundpdPh(src: M128h, k: Mmask8, a: M512d, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vcvtpd2ph512(a, src, k, rounding)
    }

/**
 * Convert packed double-precision (64-bit) floating-point elements in a to packed half-precision (16-bit)
 * floating-point elements, and store the results in dst using zeromask k (elements are zeroed out when the
 * corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvt_roundpd_ph)
 */
public fun mm512MaskzCvtRoundpdPh(k: Mmask8, a: M512d, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundpdPh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, rounding)
}

/**
 * Convert the lower double-precision (64-bit) floating-point element in b to a half-precision (16-bit)
 * floating-point elements, store the result in the lower element of dst, and copy the upper 7 packed
 * elements from a to the upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtsd_sh)
 */
public fun mmCvtsdSh(a: M128h, b: M128d): M128h = mmMaskCvtsdSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b)

/**
 * Convert the lower double-precision (64-bit) floating-point element in b to a half-precision (16-bit)
 * floating-point elements, store the result in the lower element of dst using writemask k (the element
 * if copied from src when mask bit 0 is not set), and copy the upper 7 packed elements from a to the
 * upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvtsd_sh)
 */
public fun mmMaskCvtsdSh(src: M128h, k: Mmask8, a: M128h, b: M128d): M128h = vcvtsd2sh(a, b, src, k, MM_FROUND_CUR_DIRECTION)

/**
 * Convert the lower double-precision (64-bit) floating-point element in b to a half-precision (16-bit)
 * floating-point elements, store the result in the lower element of dst using zeromask k (the element
 * is zeroed out when mask bit 0 is not set), and copy the upper 7 packed elements from a to the upper
 * elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvtsd_sh)
 */
public fun mmMaskzCvtsdSh(k: Mmask8, a: M128h, b: M128d): M128h = mmMaskCvtsdSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b)

/**
 * Convert the lower double-precision (64-bit) floating-point element in b to a half-precision (16-bit)
 * floating-point elements, store the result in the lower element of dst, and copy the upper 7 packed
 * elements from a to the upper elements of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvt_roundsd_sh)
 */
public fun mmCvtRoundsdSh(a: M128h, b: M128d, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmMaskCvtRoundsdSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), 0xffu, a, b, rounding)
}

/**
 * Convert the lower double-precision (64-bit) floating-point element in b to a half-precision (16-bit)
 * floating-point elements, store the result in the lower element of dst using writemask k (the element
 * if copied from src when mask bit 0 is not set), and copy the upper 7 packed elements from a to the
 * upper elements of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvt_roundsd_sh)
 */
public fun mmMaskCvtRoundsdSh(src: M128h, k: Mmask8, a: M128h, b: M128d, rounding: Int): M128h =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vcvtsd2sh(a, b, src, k, rounding)
    }

/**
 * Convert the lower double-precision (64-bit) floating-point element in b to a half-precision (16-bit)
 * floating-point elements, store the result in the lower element of dst using zeromask k (the element
 * is zeroed out when mask bit 0 is not set), and copy the upper 7 packed elements from a to the upper
 * elements of dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvt_roundsd_sh)
 */
public fun mmMaskzCvtRoundsdSh(k: Mmask8, a: M128h, b: M128d, rounding: Int): M128h {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mmMaskCvtRoundsdSh(Simd<F16>(List(8) { F16.fromFloat(0.0f) }).asM128h(), k, a, b, rounding)
}

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 16-bit integers, and
 * store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtph_epi16)
 */
public fun mmCvtphEpi16(a: M128h): M128i = mmMaskCvtphEpi16(mmUndefinedSi128(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 16-bit integers, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvtph_epi16)
 */
public fun mmMaskCvtphEpi16(src: M128i, k: Mmask8, a: M128h): M128i = transmuteWithLayouts<I16x8, M128i>(vcvtph2w128(a, src.asI16x8(), k), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<M128i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 16-bit integers, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvtph_epi16)
 */
public fun mmMaskzCvtphEpi16(k: Mmask8, a: M128h): M128i = mmMaskCvtphEpi16(mmSetzeroSi128(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 16-bit integers, and
 * store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvtph_epi16)
 */
public fun mm256CvtphEpi16(a: M256h): M256i = mm256MaskCvtphEpi16(mm256UndefinedSi256(), 0xffffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 16-bit integers, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_cvtph_epi16)
 */
public fun mm256MaskCvtphEpi16(src: M256i, k: Mmask16, a: M256h): M256i = transmuteWithLayouts<I16x16, M256i>(vcvtph2w256(a, src.asI16x16(), k), UnresolvedTypeLayout<I16x16>(), UnresolvedTypeLayout<M256i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 16-bit integers, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_cvtph_epi16)
 */
public fun mm256MaskzCvtphEpi16(k: Mmask16, a: M256h): M256i = mm256MaskCvtphEpi16(mm256SetzeroSi256(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 16-bit integers, and
 * store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtph_epi16)
 */
public fun mm512CvtphEpi16(a: M512h): M512i = mm512MaskCvtphEpi16(mm512UndefinedEpi32(), 0xffffffffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 16-bit integers, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvtph_epi16)
 */
public fun mm512MaskCvtphEpi16(src: M512i, k: Mmask32, a: M512h): M512i = transmuteWithLayouts<I16x32, M512i>(vcvtph2w512(a, src.asI16x32(), k, MM_FROUND_CUR_DIRECTION), UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 16-bit integers, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvtph_epi16)
 */
public fun mm512MaskzCvtphEpi16(k: Mmask32, a: M512h): M512i = mm512MaskCvtphEpi16(mm512SetzeroSi512(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 16-bit integers, and
 * store the results in dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvt_roundph_epi16)
 */
public fun mm512CvtRoundphEpi16(a: M512h, rounding: Int): M512i {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundphEpi16(mm512UndefinedEpi32(), 0xffffffffu, a, rounding)
}

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 16-bit integers, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding
 * mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvt_roundph_epi16)
 */
public fun mm512MaskCvtRoundphEpi16(src: M512i, k: Mmask32, a: M512h, rounding: Int): M512i =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        transmuteWithLayouts<I16x32, M512i>(vcvtph2w512(a, src.asI16x32(), k, rounding), UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 16-bit integers, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvt_roundph_epi16)
 */
public fun mm512MaskzCvtRoundphEpi16(k: Mmask32, a: M512h, rounding: Int): M512i {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundphEpi16(mm512SetzeroSi512(), k, a, rounding)
}

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed unsigned 16-bit integers,
 * and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtph_epu16)
 */
public fun mmCvtphEpu16(a: M128h): M128i = mmMaskCvtphEpu16(mmUndefinedSi128(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed unsigned 16-bit integers,
 * and store the results in dst using writemask k (elements are copied from src when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvtph_epu16)
 */
public fun mmMaskCvtphEpu16(src: M128i, k: Mmask8, a: M128h): M128i = transmuteWithLayouts<U16x8, M128i>(vcvtph2uw128(a, src.asU16x8(), k), UnresolvedTypeLayout<U16x8>(), UnresolvedTypeLayout<M128i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed unsigned 16-bit integers,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvtph_epu16)
 */
public fun mmMaskzCvtphEpu16(k: Mmask8, a: M128h): M128i = mmMaskCvtphEpu16(mmSetzeroSi128(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed unsigned 16-bit integers,
 * and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvtph_epu16)
 */
public fun mm256CvtphEpu16(a: M256h): M256i = mm256MaskCvtphEpu16(mm256UndefinedSi256(), 0xffffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed unsigned 16-bit integers,
 * and store the results in dst using writemask k (elements are copied from src when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_cvtph_epu16)
 */
public fun mm256MaskCvtphEpu16(src: M256i, k: Mmask16, a: M256h): M256i = transmuteWithLayouts<U16x16, M256i>(vcvtph2uw256(a, src.asU16x16(), k), UnresolvedTypeLayout<U16x16>(), UnresolvedTypeLayout<M256i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed unsigned 16-bit integers,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_cvtph_epu16)
 */
public fun mm256MaskzCvtphEpu16(k: Mmask16, a: M256h): M256i = mm256MaskCvtphEpu16(mm256SetzeroSi256(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed unsigned 16-bit integers,
 * and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtph_epu16)
 */
public fun mm512CvtphEpu16(a: M512h): M512i = mm512MaskCvtphEpu16(mm512UndefinedEpi32(), 0xffffffffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed unsigned 16-bit integers,
 * and store the results in dst using writemask k (elements are copied from src when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvtph_epu16)
 */
public fun mm512MaskCvtphEpu16(src: M512i, k: Mmask32, a: M512h): M512i = transmuteWithLayouts<U16x32, M512i>(vcvtph2uw512(a, src.asU16x32(), k, MM_FROUND_CUR_DIRECTION), UnresolvedTypeLayout<U16x32>(), UnresolvedTypeLayout<M512i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed unsigned 16-bit integers,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvtph_epu16)
 */
public fun mm512MaskzCvtphEpu16(k: Mmask32, a: M512h): M512i = mm512MaskCvtphEpu16(mm512SetzeroSi512(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed unsigned 16-bit integers,
 * and store the results in dst.
 *
 * Exceptions can be suppressed by passing [mmFroundNoExc] in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvt_roundph_epu16)
 */
public fun mm512CvtRoundphEpu16(a: M512h, sae: Int): M512i {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundphEpu16(mm512UndefinedEpi32(), 0xffffffffu, a, sae)
}

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed unsigned 16-bit integers,
 * and store the results in dst using writemask k (elements are copied from src when the corresponding
 * mask bit is not set).
 *
 * Exceptions can be suppressed by passing [mmFroundNoExc] in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvt_roundph_epu16)
 */
public fun mm512MaskCvtRoundphEpu16(src: M512i, k: Mmask32, a: M512h, sae: Int): M512i =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        transmuteWithLayouts<U16x32, M512i>(vcvtph2uw512(a, src.asU16x32(), k, sae), UnresolvedTypeLayout<U16x32>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed unsigned 16-bit integers,
 * and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * Exceptions can be suppressed by passing [mmFroundNoExc] in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvt_roundph_epu16)
 */
public fun mm512MaskzCvtRoundphEpu16(k: Mmask32, a: M512h, sae: Int): M512i {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundphEpu16(mm512SetzeroSi512(), k, a, sae)
}

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 16-bit integers with
 * truncation, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvttph_epi16)
 */
public fun mmCvttphEpi16(a: M128h): M128i = mmMaskCvttphEpi16(mmUndefinedSi128(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 16-bit integers with
 * truncation, and store the results in dst using writemask k (elements are copied from src when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvttph_epi16)
 */
public fun mmMaskCvttphEpi16(src: M128i, k: Mmask8, a: M128h): M128i = transmuteWithLayouts<I16x8, M128i>(vcvttph2w128(a, src.asI16x8(), k), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<M128i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 16-bit integers with
 * truncation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvttph_epi16)
 */
public fun mmMaskzCvttphEpi16(k: Mmask8, a: M128h): M128i = mmMaskCvttphEpi16(mmSetzeroSi128(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 16-bit integers with
 * truncation, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvttph_epi16)
 */
public fun mm256CvttphEpi16(a: M256h): M256i = mm256MaskCvttphEpi16(mm256UndefinedSi256(), 0xffffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 16-bit integers with
 * truncation, and store the results in dst using writemask k (elements are copied from src when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_cvttph_epi16)
 */
public fun mm256MaskCvttphEpi16(src: M256i, k: Mmask16, a: M256h): M256i = transmuteWithLayouts<I16x16, M256i>(vcvttph2w256(a, src.asI16x16(), k), UnresolvedTypeLayout<I16x16>(), UnresolvedTypeLayout<M256i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 16-bit integers with
 * truncation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_cvttph_epi16)
 */
public fun mm256MaskzCvttphEpi16(k: Mmask16, a: M256h): M256i = mm256MaskCvttphEpi16(mm256SetzeroSi256(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 16-bit integers with
 * truncation, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvttph_epi16)
 */
public fun mm512CvttphEpi16(a: M512h): M512i = mm512MaskCvttphEpi16(mm512UndefinedEpi32(), 0xffffffffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 16-bit integers with
 * truncation, and store the results in dst using writemask k (elements are copied from src when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvttph_epi16)
 */
public fun mm512MaskCvttphEpi16(src: M512i, k: Mmask32, a: M512h): M512i = transmuteWithLayouts<I16x32, M512i>(vcvttph2w512(a, src.asI16x32(), k, MM_FROUND_CUR_DIRECTION), UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 16-bit integers with
 * truncation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvttph_epi16)
 */
public fun mm512MaskzCvttphEpi16(k: Mmask32, a: M512h): M512i = mm512MaskCvttphEpi16(mm512SetzeroSi512(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 16-bit integers with
 * truncation, and store the results in dst.
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtt_roundph_epi16)
 */
public fun mm512CvttRoundphEpi16(a: M512h, sae: Int): M512i {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvttRoundphEpi16(mm512UndefinedEpi32(), 0xffffffffu, a, sae)
}

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 16-bit integers with
 * truncation, and store the results in dst using writemask k (elements are copied from src when the corresponding
 * mask bit is not set).
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvtt_roundph_epi16)
 */
public fun mm512MaskCvttRoundphEpi16(src: M512i, k: Mmask32, a: M512h, sae: Int): M512i =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        transmuteWithLayouts<I16x32, M512i>(vcvttph2w512(a, src.asI16x32(), k, sae), UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 16-bit integers with
 * truncation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding
 * mask bit is not set).
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvtt_roundph_epi16)
 */
public fun mm512MaskzCvttRoundphEpi16(k: Mmask32, a: M512h, sae: Int): M512i {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvttRoundphEpi16(mm512SetzeroSi512(), k, a, sae)
}

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed unsigned 16-bit integers with
 * truncation, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvttph_epu16)
 */
public fun mmCvttphEpu16(a: M128h): M128i = mmMaskCvttphEpu16(mmUndefinedSi128(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed unsigned 16-bit integers with
 * truncation, and store the results in dst using writemask k (elements are copied from src when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvttph_epu16)
 */
public fun mmMaskCvttphEpu16(src: M128i, k: Mmask8, a: M128h): M128i = transmuteWithLayouts<U16x8, M128i>(vcvttph2uw128(a, src.asU16x8(), k), UnresolvedTypeLayout<U16x8>(), UnresolvedTypeLayout<M128i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed unsigned 16-bit integers with
 * truncation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvttph_epu16)
 */
public fun mmMaskzCvttphEpu16(k: Mmask8, a: M128h): M128i = mmMaskCvttphEpu16(mmSetzeroSi128(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed unsigned 16-bit integers with
 * truncation, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvttph_epu16)
 */
public fun mm256CvttphEpu16(a: M256h): M256i = mm256MaskCvttphEpu16(mm256UndefinedSi256(), 0xffffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed unsigned 16-bit integers with
 * truncation, and store the results in dst using writemask k (elements are copied from src when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_cvttph_epu16)
 */
public fun mm256MaskCvttphEpu16(src: M256i, k: Mmask16, a: M256h): M256i = transmuteWithLayouts<U16x16, M256i>(vcvttph2uw256(a, src.asU16x16(), k), UnresolvedTypeLayout<U16x16>(), UnresolvedTypeLayout<M256i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed unsigned 16-bit integers with
 * truncation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_cvttph_epu16)
 */
public fun mm256MaskzCvttphEpu16(k: Mmask16, a: M256h): M256i = mm256MaskCvttphEpu16(mm256SetzeroSi256(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed unsigned 16-bit integers with
 * truncation, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvttph_epu16)
 */
public fun mm512CvttphEpu16(a: M512h): M512i = mm512MaskCvttphEpu16(mm512UndefinedEpi32(), 0xffffffffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed unsigned 16-bit integers with
 * truncation, and store the results in dst using writemask k (elements are copied from src when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvttph_epu16)
 */
public fun mm512MaskCvttphEpu16(src: M512i, k: Mmask32, a: M512h): M512i = transmuteWithLayouts<U16x32, M512i>(vcvttph2uw512(a, src.asU16x32(), k, MM_FROUND_CUR_DIRECTION), UnresolvedTypeLayout<U16x32>(), UnresolvedTypeLayout<M512i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed unsigned 16-bit integers with
 * truncation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvttph_epu16)
 */
public fun mm512MaskzCvttphEpu16(k: Mmask32, a: M512h): M512i = mm512MaskCvttphEpu16(mm512SetzeroSi512(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed unsigned 16-bit integers with
 * truncation, and store the results in dst.
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtt_roundph_epu16)
 */
public fun mm512CvttRoundphEpu16(a: M512h, sae: Int): M512i {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvttRoundphEpu16(mm512UndefinedEpi32(), 0xffffffffu, a, sae)
}

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed unsigned 16-bit integers with
 * truncation, and store the results in dst using writemask k (elements are copied from src when the corresponding
 * mask bit is not set).
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvtt_roundph_epu16)
 */
public fun mm512MaskCvttRoundphEpu16(src: M512i, k: Mmask32, a: M512h, sae: Int): M512i =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        transmuteWithLayouts<U16x32, M512i>(vcvttph2uw512(a, src.asU16x32(), k, sae), UnresolvedTypeLayout<U16x32>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed unsigned 16-bit integers with
 * truncation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding
 * mask bit is not set).
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvtt_roundph_epu16)
 */
public fun mm512MaskzCvttRoundphEpu16(k: Mmask32, a: M512h, sae: Int): M512i {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvttRoundphEpu16(mm512SetzeroSi512(), k, a, sae)
}

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit integers, and store the
 * results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtph_epi32)
 */
public fun mmCvtphEpi32(a: M128h): M128i = mmMaskCvtphEpi32(mmUndefinedSi128(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit integers, and store the
 * results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvtph_epi32)
 */
public fun mmMaskCvtphEpi32(src: M128i, k: Mmask8, a: M128h): M128i = transmuteWithLayouts<I32x4, M128i>(vcvtph2dq128(a, src.asI32x4(), k), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<M128i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit integers, and store the
 * results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvtph_epi32)
 */
public fun mmMaskzCvtphEpi32(k: Mmask8, a: M128h): M128i = mmMaskCvtphEpi32(mmSetzeroSi128(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit integers, and store the
 * results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvtph_epi32)
 */
public fun mm256CvtphEpi32(a: M128h): M256i = mm256MaskCvtphEpi32(mm256UndefinedSi256(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit integers, and store the
 * results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_cvtph_epi32)
 */
public fun mm256MaskCvtphEpi32(src: M256i, k: Mmask8, a: M128h): M256i = transmuteWithLayouts<I32x8, M256i>(vcvtph2dq256(a, src.asI32x8(), k), UnresolvedTypeLayout<I32x8>(), UnresolvedTypeLayout<M256i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit integers, and store the
 * results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_cvtph_epi32)
 */
public fun mm256MaskzCvtphEpi32(k: Mmask8, a: M128h): M256i = mm256MaskCvtphEpi32(mm256SetzeroSi256(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit integers, and store the
 * results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtph_epi32)
 */
public fun mm512CvtphEpi32(a: M256h): M512i = mm512MaskCvtphEpi32(mm512UndefinedEpi32(), 0xffffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit integers, and store the
 * results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvtph_epi32)
 */
public fun mm512MaskCvtphEpi32(src: M512i, k: Mmask16, a: M256h): M512i = transmuteWithLayouts<I32x16, M512i>(vcvtph2dq512(a, src.asI32x16(), k, MM_FROUND_CUR_DIRECTION), UnresolvedTypeLayout<I32x16>(), UnresolvedTypeLayout<M512i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit integers, and store the
 * results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvtph_epi32)
 */
public fun mm512MaskzCvtphEpi32(k: Mmask16, a: M256h): M512i = mm512MaskCvtphEpi32(mm512SetzeroSi512(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit integers, and store the
 * results in dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvt_roundph_epi32)
 */
public fun mm512CvtRoundphEpi32(a: M256h, rounding: Int): M512i {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundphEpi32(mm512UndefinedEpi32(), 0xffffu, a, rounding)
}

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit integers, and store the
 * results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvt_roundph_epi32)
 */
public fun mm512MaskCvtRoundphEpi32(src: M512i, k: Mmask16, a: M256h, rounding: Int): M512i =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        transmuteWithLayouts<I32x16, M512i>(vcvtph2dq512(a, src.asI32x16(), k, rounding), UnresolvedTypeLayout<I32x16>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit integers, and store the
 * results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvt_roundph_epi32)
 */
public fun mm512MaskzCvtRoundphEpi32(k: Mmask16, a: M256h, rounding: Int): M512i {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundphEpi32(mm512SetzeroSi512(), k, a, rounding)
}

/**
 * Convert the lower half-precision (16-bit) floating-point element in a to a 32-bit integer, and store
 * the result in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtsh_i32)
 */
public fun mmCvtshI32(a: M128h): Int = vcvtsh2si32(a, MM_FROUND_CUR_DIRECTION)

/**
 * Convert the lower half-precision (16-bit) floating-point element in a to a 32-bit integer, and store
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
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvt_roundsh_i32)
 */
public fun mmCvtRoundshI32(a: M128h, rounding: Int): Int =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        vcvtsh2si32(a, rounding)
    }

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit integers, and store the
 * results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtph_epu32)
 */
public fun mmCvtphEpu32(a: M128h): M128i = mmMaskCvtphEpu32(mmUndefinedSi128(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit unsigned integers, and store
 * the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvtph_epu32)
 */
public fun mmMaskCvtphEpu32(src: M128i, k: Mmask8, a: M128h): M128i = transmuteWithLayouts<U32x4, M128i>(vcvtph2udq128(a, src.asU32x4(), k), UnresolvedTypeLayout<U32x4>(), UnresolvedTypeLayout<M128i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit unsigned integers, and store
 * the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvtph_epu32)
 */
public fun mmMaskzCvtphEpu32(k: Mmask8, a: M128h): M128i = mmMaskCvtphEpu32(mmSetzeroSi128(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit unsigned integers, and store
 * the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvtph_epu32)
 */
public fun mm256CvtphEpu32(a: M128h): M256i = mm256MaskCvtphEpu32(mm256UndefinedSi256(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit unsigned integers, and store
 * the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_cvtph_epu32)
 */
public fun mm256MaskCvtphEpu32(src: M256i, k: Mmask8, a: M128h): M256i = transmuteWithLayouts<U32x8, M256i>(vcvtph2udq256(a, src.asU32x8(), k), UnresolvedTypeLayout<U32x8>(), UnresolvedTypeLayout<M256i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit unsigned integers, and store
 * the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_cvtph_epu32)
 */
public fun mm256MaskzCvtphEpu32(k: Mmask8, a: M128h): M256i = mm256MaskCvtphEpu32(mm256SetzeroSi256(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit unsigned integers, and store
 * the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtph_epu32)
 */
public fun mm512CvtphEpu32(a: M256h): M512i = mm512MaskCvtphEpu32(mm512UndefinedEpi32(), 0xffffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit unsigned integers, and store
 * the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvtph_epu32)
 */
public fun mm512MaskCvtphEpu32(src: M512i, k: Mmask16, a: M256h): M512i = transmuteWithLayouts<U32x16, M512i>(vcvtph2udq512(a, src.asU32x16(), k, MM_FROUND_CUR_DIRECTION), UnresolvedTypeLayout<U32x16>(), UnresolvedTypeLayout<M512i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit unsigned integers, and store
 * the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvtph_epu32)
 */
public fun mm512MaskzCvtphEpu32(k: Mmask16, a: M256h): M512i = mm512MaskCvtphEpu32(mm512SetzeroSi512(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit unsigned integers, and store
 * the results in dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvt_roundph_epu32)
 */
public fun mm512CvtRoundphEpu32(a: M256h, rounding: Int): M512i {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundphEpu32(mm512UndefinedEpi32(), 0xffffu, a, rounding)
}

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit unsigned integers, and store
 * the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvt_roundph_epu32)
 */
public fun mm512MaskCvtRoundphEpu32(src: M512i, k: Mmask16, a: M256h, rounding: Int): M512i =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        transmuteWithLayouts<U32x16, M512i>(vcvtph2udq512(a, src.asU32x16(), k, rounding), UnresolvedTypeLayout<U32x16>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit unsigned integers, and store
 * the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvt_roundph_epu32)
 */
public fun mm512MaskzCvtRoundphEpu32(k: Mmask16, a: M256h, rounding: Int): M512i {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundphEpu32(mm512SetzeroSi512(), k, a, rounding)
}

/**
 * Convert the lower half-precision (16-bit) floating-point element in a to a 32-bit unsigned integer, and store
 * the result in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtsh_u32)
 */
public fun mmCvtshU32(a: M128h): UInt = vcvtsh2usi32(a, MM_FROUND_CUR_DIRECTION)

/**
 * Convert the lower half-precision (16-bit) floating-point element in a to a 32-bit unsigned integer, and store
 * the result in dst.
 *
 * Exceptions can be suppressed by passing [mmFroundNoExc] in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvt_roundsh_u32)
 */
public fun mmCvtRoundshU32(a: M128h, sae: Int): UInt =
    run {
        run {
            check(sae == 4 || sae == 8 || sae == 9 || sae == 10 || sae == 11) { "Invalid IMM value" }
        }
        vcvtsh2usi32(a, sae)
    }

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit integers with truncation, and
 * store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvttph_epi32)
 */
public fun mmCvttphEpi32(a: M128h): M128i = mmMaskCvttphEpi32(mmUndefinedSi128(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit integers with truncation, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvttph_epi32)
 */
public fun mmMaskCvttphEpi32(src: M128i, k: Mmask8, a: M128h): M128i = transmuteWithLayouts<I32x4, M128i>(vcvttph2dq128(a, src.asI32x4(), k), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<M128i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit integers with truncation, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvttph_epi32)
 */
public fun mmMaskzCvttphEpi32(k: Mmask8, a: M128h): M128i = mmMaskCvttphEpi32(mmSetzeroSi128(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit integers with truncation, and
 * store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvttph_epi32)
 */
public fun mm256CvttphEpi32(a: M128h): M256i = mm256MaskCvttphEpi32(mm256UndefinedSi256(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit integers with truncation, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_cvttph_epi32)
 */
public fun mm256MaskCvttphEpi32(src: M256i, k: Mmask8, a: M128h): M256i = transmuteWithLayouts<I32x8, M256i>(vcvttph2dq256(a, src.asI32x8(), k), UnresolvedTypeLayout<I32x8>(), UnresolvedTypeLayout<M256i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit integers with truncation, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_cvttph_epi32)
 */
public fun mm256MaskzCvttphEpi32(k: Mmask8, a: M128h): M256i = mm256MaskCvttphEpi32(mm256SetzeroSi256(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit integers with truncation, and
 * store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvttph_epi32)
 */
public fun mm512CvttphEpi32(a: M256h): M512i = mm512MaskCvttphEpi32(mm512UndefinedEpi32(), 0xffffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit integers with truncation, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvttph_epi32)
 */
public fun mm512MaskCvttphEpi32(src: M512i, k: Mmask16, a: M256h): M512i = transmuteWithLayouts<I32x16, M512i>(vcvttph2dq512(a, src.asI32x16(), k, MM_FROUND_CUR_DIRECTION), UnresolvedTypeLayout<I32x16>(), UnresolvedTypeLayout<M512i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit integers with truncation, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvttph_epi32)
 */
public fun mm512MaskzCvttphEpi32(k: Mmask16, a: M256h): M512i = mm512MaskCvttphEpi32(mm512SetzeroSi512(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit integers with truncation, and
 * store the results in dst.
 *
 * Exceptions can be suppressed by passing `_MM_FROUND_NO_EXC` in the `sae` parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtt_roundph_epi32)
 */
public fun mm512CvttRoundphEpi32(a: M256h, sae: Int): M512i {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvttRoundphEpi32(mm512UndefinedEpi32(), 0xffffu, a, sae)
}

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit integers with truncation, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * Exceptions can be suppressed by passing `_MM_FROUND_NO_EXC` in the `sae` parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvtt_roundph_epi32)
 */
public fun mm512MaskCvttRoundphEpi32(src: M512i, k: Mmask16, a: M256h, sae: Int): M512i =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        transmuteWithLayouts<I32x16, M512i>(vcvttph2dq512(a, src.asI32x16(), k, sae), UnresolvedTypeLayout<I32x16>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit integers with truncation, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * Exceptions can be suppressed by passing `_MM_FROUND_NO_EXC` in the `sae` parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvtt_roundph_epi32)
 */
public fun mm512MaskzCvttRoundphEpi32(k: Mmask16, a: M256h, sae: Int): M512i {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvttRoundphEpi32(mm512SetzeroSi512(), k, a, sae)
}

/**
 * Convert the lower half-precision (16-bit) floating-point element in a to a 32-bit integer with truncation, and store
 * the result in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvttsh_i32)
 */
public fun mmCvttshI32(a: M128h): Int = vcvttsh2si32(a, MM_FROUND_CUR_DIRECTION)

/**
 * Convert the lower half-precision (16-bit) floating-point element in a to a 32-bit integer with truncation, and store
 * the result in dst.
 *
 * Exceptions can be suppressed by passing `_MM_FROUND_NO_EXC` in the `sae` parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtt_roundsh_i32)
 */
public fun mmCvttRoundshI32(a: M128h, sae: Int): Int =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        vcvttsh2si32(a, sae)
    }

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit unsigned integers with truncation, and
 * store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvttph_epu32)
 */
public fun mmCvttphEpu32(a: M128h): M128i = mmMaskCvttphEpu32(mmUndefinedSi128(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit unsigned integers with truncation, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvttph_epu32)
 */
public fun mmMaskCvttphEpu32(src: M128i, k: Mmask8, a: M128h): M128i = transmuteWithLayouts<U32x4, M128i>(vcvttph2udq128(a, src.asU32x4(), k), UnresolvedTypeLayout<U32x4>(), UnresolvedTypeLayout<M128i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit unsigned integers with truncation, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvttph_epu32)
 */
public fun mmMaskzCvttphEpu32(k: Mmask8, a: M128h): M128i = mmMaskCvttphEpu32(mmSetzeroSi128(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit unsigned integers with truncation, and
 * store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvttph_epu32)
 */
public fun mm256CvttphEpu32(a: M128h): M256i = mm256MaskCvttphEpu32(mm256UndefinedSi256(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit unsigned integers with truncation, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_cvttph_epu32)
 */
public fun mm256MaskCvttphEpu32(src: M256i, k: Mmask8, a: M128h): M256i = transmuteWithLayouts<U32x8, M256i>(vcvttph2udq256(a, src.asU32x8(), k), UnresolvedTypeLayout<U32x8>(), UnresolvedTypeLayout<M256i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit unsigned integers with truncation, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_cvttph_epu32)
 */
public fun mm256MaskzCvttphEpu32(k: Mmask8, a: M128h): M256i = mm256MaskCvttphEpu32(mm256SetzeroSi256(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit unsigned integers with truncation, and
 * store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvttph_epu32)
 */
public fun mm512CvttphEpu32(a: M256h): M512i = mm512MaskCvttphEpu32(mm512UndefinedEpi32(), 0xffffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit unsigned integers with truncation, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvttph_epu32)
 */
public fun mm512MaskCvttphEpu32(src: M512i, k: Mmask16, a: M256h): M512i = transmuteWithLayouts<U32x16, M512i>(vcvttph2udq512(a, src.asU32x16(), k, MM_FROUND_CUR_DIRECTION), UnresolvedTypeLayout<U32x16>(), UnresolvedTypeLayout<M512i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit unsigned integers with truncation, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvttph_epu32)
 */
public fun mm512MaskzCvttphEpu32(k: Mmask16, a: M256h): M512i = mm512MaskCvttphEpu32(mm512SetzeroSi512(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit unsigned integers with truncation, and
 * store the results in dst.
 *
 * Exceptions can be suppressed by passing `_MM_FROUND_NO_EXC` in the `sae` parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtt_roundph_epu32)
 */
public fun mm512CvttRoundphEpu32(a: M256h, sae: Int): M512i {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvttRoundphEpu32(mm512UndefinedEpi32(), 0xffffu, a, sae)
}

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit unsigned integers with truncation, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * Exceptions can be suppressed by passing `_MM_FROUND_NO_EXC` in the `sae` parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvtt_roundph_epu32)
 */
public fun mm512MaskCvttRoundphEpu32(src: M512i, k: Mmask16, a: M256h, sae: Int): M512i =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        transmuteWithLayouts<U32x16, M512i>(vcvttph2udq512(a, src.asU32x16(), k, sae), UnresolvedTypeLayout<U32x16>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 32-bit unsigned integers with truncation, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * Exceptions can be suppressed by passing `_MM_FROUND_NO_EXC` in the `sae` parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvtt_roundph_epu32)
 */
public fun mm512MaskzCvttRoundphEpu32(k: Mmask16, a: M256h, sae: Int): M512i {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvttRoundphEpu32(mm512SetzeroSi512(), k, a, sae)
}

/**
 * Convert the lower half-precision (16-bit) floating-point element in a to a 32-bit unsigned integer with truncation, and store
 * the result in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvttsh_u32)
 */
public fun mmCvttshU32(a: M128h): UInt = vcvttsh2usi32(a, MM_FROUND_CUR_DIRECTION)

/**
 * Convert the lower half-precision (16-bit) floating-point element in a to a 32-bit unsigned integer with truncation, and store
 * the result in dst.
 *
 * Exceptions can be suppressed by passing `_MM_FROUND_NO_EXC` in the `sae` parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtt_roundsh_u32)
 */
public fun mmCvttRoundshU32(a: M128h, sae: Int): UInt =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        vcvttsh2usi32(a, sae)
    }

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit integers, and
 * store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtph_epi64)
 */
public fun mmCvtphEpi64(a: M128h): M128i = mmMaskCvtphEpi64(mmUndefinedSi128(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit integers, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvtph_epi64)
 */
public fun mmMaskCvtphEpi64(src: M128i, k: Mmask8, a: M128h): M128i = transmuteWithLayouts<I64x2, M128i>(vcvtph2qq128(a, src.asI64x2(), k), UnresolvedTypeLayout<I64x2>(), UnresolvedTypeLayout<M128i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit integers, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvtph_epi64)
 */
public fun mmMaskzCvtphEpi64(k: Mmask8, a: M128h): M128i = mmMaskCvtphEpi64(mmSetzeroSi128(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit integers, and
 * store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvtph_epi64)
 */
public fun mm256CvtphEpi64(a: M128h): M256i = mm256MaskCvtphEpi64(mm256UndefinedSi256(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit integers, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_cvtph_epi64)
 */
public fun mm256MaskCvtphEpi64(src: M256i, k: Mmask8, a: M128h): M256i = transmuteWithLayouts<I64x4, M256i>(vcvtph2qq256(a, src.asI64x4(), k), UnresolvedTypeLayout<I64x4>(), UnresolvedTypeLayout<M256i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit integers, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_cvtph_epi64)
 */
public fun mm256MaskzCvtphEpi64(k: Mmask8, a: M128h): M256i = mm256MaskCvtphEpi64(mm256SetzeroSi256(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit integers, and
 * store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtph_epi64)
 */
public fun mm512CvtphEpi64(a: M128h): M512i = mm512MaskCvtphEpi64(mm512UndefinedEpi32(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit integers, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvtph_epi64)
 */
public fun mm512MaskCvtphEpi64(src: M512i, k: Mmask8, a: M128h): M512i = transmuteWithLayouts<I64x8, M512i>(vcvtph2qq512(a, src.asI64x8(), k, MM_FROUND_CUR_DIRECTION), UnresolvedTypeLayout<I64x8>(), UnresolvedTypeLayout<M512i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit integers, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvtph_epi64)
 */
public fun mm512MaskzCvtphEpi64(k: Mmask8, a: M128h): M512i = mm512MaskCvtphEpi64(mm512SetzeroSi512(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit integers, and
 * store the results in dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvt_roundph_epi64)
 */
public fun mm512CvtRoundphEpi64(a: M128h, rounding: Int): M512i {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundphEpi64(mm512UndefinedEpi32(), 0xffu, a, rounding)
}

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit integers, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvt_roundph_epi64)
 */
public fun mm512MaskCvtRoundphEpi64(src: M512i, k: Mmask8, a: M128h, rounding: Int): M512i =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        transmuteWithLayouts<I64x8, M512i>(vcvtph2qq512(a, src.asI64x8(), k, rounding), UnresolvedTypeLayout<I64x8>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit integers, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvt_roundph_epi64)
 */
public fun mm512MaskzCvtRoundphEpi64(k: Mmask8, a: M128h, rounding: Int): M512i {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundphEpi64(mm512SetzeroSi512(), k, a, rounding)
}

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit unsigned integers, and
 * store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtph_epu64)
 */
public fun mmCvtphEpu64(a: M128h): M128i = mmMaskCvtphEpu64(mmUndefinedSi128(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit unsigned integers, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvtph_epu64)
 */
public fun mmMaskCvtphEpu64(src: M128i, k: Mmask8, a: M128h): M128i = transmuteWithLayouts<U64x2, M128i>(vcvtph2uqq128(a, src.asU64x2(), k), UnresolvedTypeLayout<U64x2>(), UnresolvedTypeLayout<M128i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit unsigned integers, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvtph_epu64)
 */
public fun mmMaskzCvtphEpu64(k: Mmask8, a: M128h): M128i = mmMaskCvtphEpu64(mmSetzeroSi128(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit unsigned integers, and
 * store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvtph_epu64)
 */
public fun mm256CvtphEpu64(a: M128h): M256i = mm256MaskCvtphEpu64(mm256UndefinedSi256(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit unsigned integers, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_cvtph_epu64)
 */
public fun mm256MaskCvtphEpu64(src: M256i, k: Mmask8, a: M128h): M256i = transmuteWithLayouts<U64x4, M256i>(vcvtph2uqq256(a, src.asU64x4(), k), UnresolvedTypeLayout<U64x4>(), UnresolvedTypeLayout<M256i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit unsigned integers, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_cvtph_epu64)
 */
public fun mm256MaskzCvtphEpu64(k: Mmask8, a: M128h): M256i = mm256MaskCvtphEpu64(mm256SetzeroSi256(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit unsigned integers, and
 * store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtph_epu64)
 */
public fun mm512CvtphEpu64(a: M128h): M512i = mm512MaskCvtphEpu64(mm512UndefinedEpi32(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit unsigned integers, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvtph_epu64)
 */
public fun mm512MaskCvtphEpu64(src: M512i, k: Mmask8, a: M128h): M512i = transmuteWithLayouts<U64x8, M512i>(vcvtph2uqq512(a, src.asU64x8(), k, MM_FROUND_CUR_DIRECTION), UnresolvedTypeLayout<U64x8>(), UnresolvedTypeLayout<M512i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit unsigned integers, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvtph_epu64)
 */
public fun mm512MaskzCvtphEpu64(k: Mmask8, a: M128h): M512i = mm512MaskCvtphEpu64(mm512SetzeroSi512(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit unsigned integers, and
 * store the results in dst.
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvt_roundph_epu64)
 */
public fun mm512CvtRoundphEpu64(a: M128h, rounding: Int): M512i {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundphEpu64(mm512UndefinedEpi32(), 0xffu, a, rounding)
}

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit unsigned integers, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvt_roundph_epu64)
 */
public fun mm512MaskCvtRoundphEpu64(src: M512i, k: Mmask8, a: M128h, rounding: Int): M512i =
    run {
        run {
            check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
        }
        transmuteWithLayouts<U64x8, M512i>(vcvtph2uqq512(a, src.asU64x8(), k, rounding), UnresolvedTypeLayout<U64x8>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit unsigned integers, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvt_roundph_epu64)
 */
public fun mm512MaskzCvtRoundphEpu64(k: Mmask8, a: M128h, rounding: Int): M512i {
    run {
        check(rounding == 4 || rounding == 8 || rounding == 9 || rounding == 10 || rounding == 11) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundphEpu64(mm512SetzeroSi512(), k, a, rounding)
}

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit integers with truncation, and
 * store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvttph_epi64)
 */
public fun mmCvttphEpi64(a: M128h): M128i = mmMaskCvttphEpi64(mmUndefinedSi128(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit integers with truncation, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvttph_epi64)
 */
public fun mmMaskCvttphEpi64(src: M128i, k: Mmask8, a: M128h): M128i = transmuteWithLayouts<I64x2, M128i>(vcvttph2qq128(a, src.asI64x2(), k), UnresolvedTypeLayout<I64x2>(), UnresolvedTypeLayout<M128i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit integers with truncation, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvttph_epi64)
 */
public fun mmMaskzCvttphEpi64(k: Mmask8, a: M128h): M128i = mmMaskCvttphEpi64(mmSetzeroSi128(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit integers with truncation, and
 * store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvttph_epi64)
 */
public fun mm256CvttphEpi64(a: M128h): M256i = mm256MaskCvttphEpi64(mm256UndefinedSi256(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit integers with truncation, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_cvttph_epi64)
 */
public fun mm256MaskCvttphEpi64(src: M256i, k: Mmask8, a: M128h): M256i = transmuteWithLayouts<I64x4, M256i>(vcvttph2qq256(a, src.asI64x4(), k), UnresolvedTypeLayout<I64x4>(), UnresolvedTypeLayout<M256i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit integers with truncation, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_cvttph_epi64)
 */
public fun mm256MaskzCvttphEpi64(k: Mmask8, a: M128h): M256i = mm256MaskCvttphEpi64(mm256SetzeroSi256(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit integers with truncation, and
 * store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvttph_epi64)
 */
public fun mm512CvttphEpi64(a: M128h): M512i = mm512MaskCvttphEpi64(mm512UndefinedEpi32(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit integers with truncation, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvttph_epi64)
 */
public fun mm512MaskCvttphEpi64(src: M512i, k: Mmask8, a: M128h): M512i = transmuteWithLayouts<I64x8, M512i>(vcvttph2qq512(a, src.asI64x8(), k, MM_FROUND_CUR_DIRECTION), UnresolvedTypeLayout<I64x8>(), UnresolvedTypeLayout<M512i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit integers with truncation, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvttph_epi64)
 */
public fun mm512MaskzCvttphEpi64(k: Mmask8, a: M128h): M512i = mm512MaskCvttphEpi64(mm512SetzeroSi512(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit integers with truncation, and
 * store the results in dst.
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtt_roundph_epi64)
 */
public fun mm512CvttRoundphEpi64(a: M128h, sae: Int): M512i {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvttRoundphEpi64(mm512UndefinedEpi32(), 0xffu, a, sae)
}

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit integers with truncation, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvtt_roundph_epi64)
 */
public fun mm512MaskCvttRoundphEpi64(src: M512i, k: Mmask8, a: M128h, sae: Int): M512i =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        transmuteWithLayouts<I64x8, M512i>(vcvttph2qq512(a, src.asI64x8(), k, sae), UnresolvedTypeLayout<I64x8>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit integers with truncation, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvtt_roundph_epi64)
 */
public fun mm512MaskzCvttRoundphEpi64(k: Mmask8, a: M128h, sae: Int): M512i {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvttRoundphEpi64(mm512SetzeroSi512(), k, a, sae)
}

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit unsigned integers with truncation, and
 * store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvttph_epu64)
 */
public fun mmCvttphEpu64(a: M128h): M128i = mmMaskCvttphEpu64(mmUndefinedSi128(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit unsigned integers with truncation, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvttph_epu64)
 */
public fun mmMaskCvttphEpu64(src: M128i, k: Mmask8, a: M128h): M128i = transmuteWithLayouts<U64x2, M128i>(vcvttph2uqq128(a, src.asU64x2(), k), UnresolvedTypeLayout<U64x2>(), UnresolvedTypeLayout<M128i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit unsigned integers with truncation, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvttph_epu64)
 */
public fun mmMaskzCvttphEpu64(k: Mmask8, a: M128h): M128i = mmMaskCvttphEpu64(mmSetzeroSi128(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit unsigned integers with truncation, and
 * store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvttph_epu64)
 */
public fun mm256CvttphEpu64(a: M128h): M256i = mm256MaskCvttphEpu64(mm256UndefinedSi256(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit unsigned integers with truncation, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_cvttph_epu64)
 */
public fun mm256MaskCvttphEpu64(src: M256i, k: Mmask8, a: M128h): M256i = transmuteWithLayouts<U64x4, M256i>(vcvttph2uqq256(a, src.asU64x4(), k), UnresolvedTypeLayout<U64x4>(), UnresolvedTypeLayout<M256i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit unsigned integers with truncation, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_cvttph_epu64)
 */
public fun mm256MaskzCvttphEpu64(k: Mmask8, a: M128h): M256i = mm256MaskCvttphEpu64(mm256SetzeroSi256(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit unsigned integers with truncation, and
 * store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvttph_epu64)
 */
public fun mm512CvttphEpu64(a: M128h): M512i = mm512MaskCvttphEpu64(mm512UndefinedEpi32(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit unsigned integers with truncation, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvttph_epu64)
 */
public fun mm512MaskCvttphEpu64(src: M512i, k: Mmask8, a: M128h): M512i = transmuteWithLayouts<U64x8, M512i>(vcvttph2uqq512(a, src.asU64x8(), k, MM_FROUND_CUR_DIRECTION), UnresolvedTypeLayout<U64x8>(), UnresolvedTypeLayout<M512i>())

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit unsigned integers with truncation, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvttph_epu64)
 */
public fun mm512MaskzCvttphEpu64(k: Mmask8, a: M128h): M512i = mm512MaskCvttphEpu64(mm512SetzeroSi512(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit unsigned integers with truncation, and
 * store the results in dst.
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtt_roundph_epu64)
 */
public fun mm512CvttRoundphEpu64(a: M128h, sae: Int): M512i {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvttRoundphEpu64(mm512UndefinedEpi32(), 0xffu, a, sae)
}

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit unsigned integers with truncation, and
 * store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvtt_roundph_epu64)
 */
public fun mm512MaskCvttRoundphEpu64(src: M512i, k: Mmask8, a: M128h, sae: Int): M512i =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        transmuteWithLayouts<U64x8, M512i>(vcvttph2uqq512(a, src.asU64x8(), k, sae), UnresolvedTypeLayout<U64x8>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed 64-bit unsigned integers with truncation, and
 * store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvtt_roundph_epu64)
 */
public fun mm512MaskzCvttRoundphEpu64(k: Mmask8, a: M128h, sae: Int): M512i {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvttRoundphEpu64(mm512SetzeroSi512(), k, a, sae)
}

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed single-precision (32-bit)
 * floating-point elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtxph_ps)
 */
public fun mmCvtxphPs(a: M128h): M128 = mmMaskCvtxphPs(mmSetzeroPs(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed single-precision (32-bit)
 * floating-point elements, and store the results in dst using writemask k (elements are copied from src to
 * dst when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvtxph_ps)
 */
public fun mmMaskCvtxphPs(src: M128, k: Mmask8, a: M128h): M128 = vcvtph2psx128(a, src, k)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed single-precision (32-bit)
 * floating-point elements, and store the results in dst using zeromask k (elements are zeroed out when the
 * corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvtxph_ps)
 */
public fun mmMaskzCvtxphPs(k: Mmask8, a: M128h): M128 = mmMaskCvtxphPs(mmSetzeroPs(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed single-precision (32-bit)
 * floating-point elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvtxph_ps)
 */
public fun mm256CvtxphPs(a: M128h): M256 = mm256MaskCvtxphPs(mm256SetzeroPs(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed single-precision (32-bit)
 * floating-point elements, and store the results in dst using writemask k (elements are copied from src to
 * dst when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_cvtxph_ps)
 */
public fun mm256MaskCvtxphPs(src: M256, k: Mmask8, a: M128h): M256 = vcvtph2psx256(a, src, k)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed single-precision (32-bit)
 * floating-point elements, and store the results in dst using zeromask k (elements are zeroed out when the
 * corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_cvtxph_ps)
 */
public fun mm256MaskzCvtxphPs(k: Mmask8, a: M128h): M256 = mm256MaskCvtxphPs(mm256SetzeroPs(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed single-precision (32-bit)
 * floating-point elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtxph_ps)
 */
public fun mm512CvtxphPs(a: M256h): M512 = mm512MaskCvtxphPs(mm512SetzeroPs(), 0xffffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed single-precision (32-bit)
 * floating-point elements, and store the results in dst using writemask k (elements are copied from src to
 * dst when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvtxph_ps)
 */
public fun mm512MaskCvtxphPs(src: M512, k: Mmask16, a: M256h): M512 = vcvtph2psx512(a, src, k, MM_FROUND_CUR_DIRECTION)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed single-precision (32-bit)
 * floating-point elements, and store the results in dst using zeromask k (elements are zeroed out when the
 * corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvtxph_ps)
 */
public fun mm512MaskzCvtxphPs(k: Mmask16, a: M256h): M512 = mm512MaskCvtxphPs(mm512SetzeroPs(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed single-precision (32-bit)
 * floating-point elements, and store the results in dst.
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtx_roundph_ps)
 */
public fun mm512CvtxRoundphPs(a: M256h, sae: Int): M512 {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvtxRoundphPs(mm512SetzeroPs(), 0xffffu, a, sae)
}

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed single-precision (32-bit)
 * floating-point elements, and store the results in dst using writemask k (elements are copied from src to
 * dst when the corresponding mask bit is not set).
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvtx_roundph_ps)
 */
public fun mm512MaskCvtxRoundphPs(src: M512, k: Mmask16, a: M256h, sae: Int): M512 =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        vcvtph2psx512(a, src, k, sae)
    }

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed single-precision (32-bit)
 * floating-point elements, and store the results in dst using zeromask k (elements are zeroed out when the
 * corresponding mask bit is not set).
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvtx_roundph_ps)
 */
public fun mm512MaskzCvtxRoundphPs(k: Mmask16, a: M256h, sae: Int): M512 {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvtxRoundphPs(mm512SetzeroPs(), k, a, sae)
}

/**
 * Convert the lower half-precision (16-bit) floating-point element in b to a single-precision (32-bit)
 * floating-point element, store the result in the lower element of dst, and copy the upper 3 packed
 * elements from a to the upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtsh_ss)
 */
public fun mmCvtshSs(a: M128, b: M128h): M128 = mmMaskCvtshSs(a, 0xffu, a, b)

/**
 * Convert the lower half-precision (16-bit) floating-point element in b to a single-precision (32-bit)
 * floating-point element, store the result in the lower element of dst using writemask k (the element is
 * copied from src to dst when mask bit 0 is not set), and copy the upper 3 packed elements from a to the
 * upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvtsh_ss)
 */
public fun mmMaskCvtshSs(src: M128, k: Mmask8, a: M128, b: M128h): M128 = vcvtsh2ss(a, b, src, k, MM_FROUND_CUR_DIRECTION)

/**
 * Convert the lower half-precision (16-bit) floating-point element in b to a single-precision (32-bit)
 * floating-point element, store the result in the lower element of dst using zeromask k (the element is
 * zeroed out when mask bit 0 is not set), and copy the upper 3 packed elements from a to the upper elements
 * of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvtsh_ss)
 */
public fun mmMaskzCvtshSs(k: Mmask8, a: M128, b: M128h): M128 = mmMaskCvtshSs(mmSetSs(0.0f), k, a, b)

/**
 * Convert the lower half-precision (16-bit) floating-point element in b to a single-precision (32-bit)
 * floating-point element, store the result in the lower element of dst, and copy the upper 3 packed elements
 * from a to the upper elements of dst.
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvt_roundsh_ss)
 */
public fun mmCvtRoundshSs(a: M128, b: M128h, sae: Int): M128 {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mmMaskCvtRoundshSs(mmUndefinedPs(), 0xffu, a, b, sae)
}

/**
 * Convert the lower half-precision (16-bit) floating-point element in b to a single-precision (32-bit)
 * floating-point element, store the result in the lower element of dst using writemask k (the element is
 * copied from src to dst when mask bit 0 is not set), and copy the upper 3 packed elements from a to the
 * upper elements of dst.
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvt_roundsh_ss)
 */
public fun mmMaskCvtRoundshSs(src: M128, k: Mmask8, a: M128, b: M128h, sae: Int): M128 =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        vcvtsh2ss(a, b, src, k, sae)
    }

/**
 * Convert the lower half-precision (16-bit) floating-point element in b to a single-precision (32-bit)
 * floating-point element, store the result in the lower element of dst using zeromask k (the element is
 * zeroed out when mask bit 0 is not set), and copy the upper 3 packed elements from a to the upper elements
 * of dst.
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvt_roundsh_ss)
 */
public fun mmMaskzCvtRoundshSs(k: Mmask8, a: M128, b: M128h, sae: Int): M128 {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mmMaskCvtRoundshSs(mmSetSs(0.0f), k, a, b, sae)
}

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed double-precision (64-bit)
 * floating-point elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtph_pd)
 */
public fun mmCvtphPd(a: M128h): M128d = mmMaskCvtphPd(mmSetzeroPd(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed double-precision (64-bit)
 * floating-point elements, and store the results in dst using writemask k (elements are copied from src to
 * dst when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvtph_pd)
 */
public fun mmMaskCvtphPd(src: M128d, k: Mmask8, a: M128h): M128d = vcvtph2pd128(a, src, k)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed double-precision (64-bit)
 * floating-point elements, and store the results in dst using zeromask k (elements are zeroed out when the
 * corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvtph_pd)
 */
public fun mmMaskzCvtphPd(k: Mmask8, a: M128h): M128d = mmMaskCvtphPd(mmSetzeroPd(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed double-precision (64-bit)
 * floating-point elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvtph_pd)
 */
public fun mm256CvtphPd(a: M128h): M256d = mm256MaskCvtphPd(mm256SetzeroPd(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed double-precision (64-bit)
 * floating-point elements, and store the results in dst using writemask k (elements are copied from src to
 * dst when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_cvtph_pd)
 */
public fun mm256MaskCvtphPd(src: M256d, k: Mmask8, a: M128h): M256d = vcvtph2pd256(a, src, k)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed double-precision (64-bit)
 * floating-point elements, and store the results in dst using zeromask k (elements are zeroed out when the
 * corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_cvtph_pd)
 */
public fun mm256MaskzCvtphPd(k: Mmask8, a: M128h): M256d = mm256MaskCvtphPd(mm256SetzeroPd(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed double-precision (64-bit)
 * floating-point elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtph_pd)
 */
public fun mm512CvtphPd(a: M128h): M512d = mm512MaskCvtphPd(mm512SetzeroPd(), 0xffu, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed double-precision (64-bit)
 * floating-point elements, and store the results in dst using writemask k (elements are copied from src to
 * dst when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvtph_pd)
 */
public fun mm512MaskCvtphPd(src: M512d, k: Mmask8, a: M128h): M512d = vcvtph2pd512(a, src, k, MM_FROUND_CUR_DIRECTION)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed double-precision (64-bit)
 * floating-point elements, and store the results in dst using zeromask k (elements are zeroed out when the
 * corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvtph_pd)
 */
public fun mm512MaskzCvtphPd(k: Mmask8, a: M128h): M512d = mm512MaskCvtphPd(mm512SetzeroPd(), k, a)

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed double-precision (64-bit)
 * floating-point elements, and store the results in dst.
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvt_roundph_pd)
 */
public fun mm512CvtRoundphPd(a: M128h, sae: Int): M512d {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundphPd(mm512SetzeroPd(), 0xffu, a, sae)
}

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed double-precision (64-bit)
 * floating-point elements, and store the results in dst using writemask k (elements are copied from src to
 * dst when the corresponding mask bit is not set).
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvt_roundph_pd)
 */
public fun mm512MaskCvtRoundphPd(src: M512d, k: Mmask8, a: M128h, sae: Int): M512d =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        vcvtph2pd512(a, src, k, sae)
    }

/**
 * Convert packed half-precision (16-bit) floating-point elements in a to packed double-precision (64-bit)
 * floating-point elements, and store the results in dst using zeromask k (elements are zeroed out when the
 * corresponding mask bit is not set).
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvt_roundph_pd)
 */
public fun mm512MaskzCvtRoundphPd(k: Mmask8, a: M128h, sae: Int): M512d {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mm512MaskCvtRoundphPd(mm512SetzeroPd(), k, a, sae)
}

/**
 * Convert the lower half-precision (16-bit) floating-point element in b to a double-precision (64-bit)
 * floating-point element, store the result in the lower element of dst, and copy the upper element
 * from a to the upper element of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtsh_sd)
 */
public fun mmCvtshSd(a: M128d, b: M128h): M128d = mmMaskCvtshSd(a, 0xffu, a, b)

/**
 * Convert the lower half-precision (16-bit) floating-point element in b to a double-precision (64-bit)
 * floating-point element, store the result in the lower element of dst using writemask k (the element is
 * copied from src to dst when mask bit 0 is not set), and copy the upper element from a to the upper element
 * of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvtsh_sd)
 */
public fun mmMaskCvtshSd(src: M128d, k: Mmask8, a: M128d, b: M128h): M128d = vcvtsh2sd(a, b, src, k, MM_FROUND_CUR_DIRECTION)

/**
 * Convert the lower half-precision (16-bit) floating-point element in b to a double-precision (64-bit)
 * floating-point element, store the result in the lower element of dst using zeromask k (the element is
 * zeroed out when mask bit 0 is not set), and copy the upper element from a to the upper element of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvtsh_sd)
 */
public fun mmMaskzCvtshSd(k: Mmask8, a: M128d, b: M128h): M128d = mmMaskCvtshSd(mmSetSd(0.0), k, a, b)

/**
 * Convert the lower half-precision (16-bit) floating-point element in b to a double-precision (64-bit)
 * floating-point element, store the result in the lower element of dst, and copy the upper element from a
 * to the upper element of dst.
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvt_roundsh_sd)
 */
public fun mmCvtRoundshSd(a: M128d, b: M128h, sae: Int): M128d {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mmMaskCvtRoundshSd(a, 0xffu, a, b, sae)
}

/**
 * Convert the lower half-precision (16-bit) floating-point element in b to a double-precision (64-bit)
 * floating-point element, store the result in the lower element of dst using writemask k (the element is
 * copied from src to dst when mask bit 0 is not set), and copy the upper element from a to the upper element
 * of dst.
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvt_roundsh_sd)
 */
public fun mmMaskCvtRoundshSd(src: M128d, k: Mmask8, a: M128d, b: M128h, sae: Int): M128d =
    run {
        run {
            check(sae == 4 || sae == 8) { "Invalid IMM value" }
        }
        vcvtsh2sd(a, b, src, k, sae)
    }

/**
 * Convert the lower half-precision (16-bit) floating-point element in b to a double-precision (64-bit)
 * floating-point element, store the result in the lower element of dst using zeromask k (the element is
 * zeroed out when mask bit 0 is not set), and copy the upper element from a to the upper element of dst.
 *
 * Exceptions can be suppressed by passing _MM_FROUND_NO_EXC in the sae parameter.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvt_roundsh_sd)
 */
public fun mmMaskzCvtRoundshSd(k: Mmask8, a: M128d, b: M128h, sae: Int): M128d {
    run {
        check(sae == 4 || sae == 8) { "Invalid IMM value" }
    }
    return mmMaskCvtRoundshSd(mmSetSd(0.0), k, a, b, sae)
}

/**
 * Copy the lower half-precision (16-bit) floating-point element from `a` to `dst`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtsh_h)
 */
public fun mmCvtshH(a: M128h): F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); _simdArg0 }

/**
 * Copy the lower half-precision (16-bit) floating-point element from `a` to `dst`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvtsh_h)
 */
public fun mm256CvtshH(a: M256h): F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); _simdArg0 }

/**
 * Copy the lower half-precision (16-bit) floating-point element from `a` to `dst`.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtsh_h)
 */
public fun mm512CvtshH(a: M512h): F16 = run { val _simdArg0 = a; val _simdArg1 = F16.fromFloat(0.0f); _simdArg0 }

/**
 * Copy the lower 16-bit integer in a to dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtsi128_si16)
 */
public fun mmCvtsi128Si16(a: M128i): Short = run { val _simdArg0 = a.asI16x8(); val _simdArg1 = 0; _simdArg0 }

/**
 * Copy 16-bit integer a to the lower elements of dst, and zero the upper elements of dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtsi16_si128)
 */
public fun mmCvtsi16Si128(a: Short): M128i = transmute(run { val _simdArg0 = Simd<Short>(List(8) { 0.toShort() }); val _simdArg1 = 0; val _simdArg2 = a; _simdArg0 })

public expect fun vcmpph128(a: M128h, b: M128h, imm5: Int, mask: Mmask8): Mmask8

public expect fun vcmpph256(a: M256h, b: M256h, imm5: Int, mask: Mmask16): Mmask16

public expect fun vcmpph512(a: M512h, b: M512h, imm5: Int, mask: Mmask32, sae: Int): Mmask32

public expect fun vcmpsh(a: M128h, b: M128h, imm5: Int, mask: Mmask8, sae: Int): Mmask8

public expect fun vcomish(a: M128h, b: M128h, imm5: Int, sae: Int): Int

public expect fun vaddph(a: M512h, b: M512h, rounding: Int): M512h

public expect fun vsubph(a: M512h, b: M512h, rounding: Int): M512h

public expect fun vmulph(a: M512h, b: M512h, rounding: Int): M512h

public expect fun vdivph(a: M512h, b: M512h, rounding: Int): M512h

public expect fun vaddsh(a: M128h, b: M128h, src: M128h, k: Mmask8, rounding: Int): M128h

public expect fun vsubsh(a: M128h, b: M128h, src: M128h, k: Mmask8, rounding: Int): M128h

public expect fun vmulsh(a: M128h, b: M128h, src: M128h, k: Mmask8, rounding: Int): M128h

public expect fun vdivsh(a: M128h, b: M128h, src: M128h, k: Mmask8, rounding: Int): M128h

public expect fun vfmulcph128(a: M128, b: M128, src: M128, k: Mmask8): M128

public expect fun vfmulcph256(a: M256, b: M256, src: M256, k: Mmask8): M256

public expect fun vfmulcph512(a: M512, b: M512, src: M512, k: Mmask16, rounding: Int): M512

public expect fun vfmulcsh(a: M128, b: M128, src: M128, k: Mmask8, rounding: Int): M128

public expect fun vfcmulcph128(a: M128, b: M128, src: M128, k: Mmask8): M128

public expect fun vfcmulcph256(a: M256, b: M256, src: M256, k: Mmask8): M256

public expect fun vfcmulcph512(a: M512, b: M512, src: M512, k: Mmask16, rounding: Int): M512

public expect fun vfcmulcsh(a: M128, b: M128, src: M128, k: Mmask8, rounding: Int): M128

public expect fun vfmaddcphMask3128(a: M128, b: M128, c: M128, k: Mmask8): M128

public expect fun vfmaddcphMaskz128(a: M128, b: M128, c: M128, k: Mmask8): M128

public expect fun vfmaddcphMask3256(a: M256, b: M256, c: M256, k: Mmask8): M256

public expect fun vfmaddcphMaskz256(a: M256, b: M256, c: M256, k: Mmask8): M256

public expect fun vfmaddcphMask3512(a: M512, b: M512, c: M512, k: Mmask16, rounding: Int): M512

public expect fun vfmaddcphMaskz512(a: M512, b: M512, c: M512, k: Mmask16, rounding: Int): M512

public expect fun vfmaddcshMask(a: M128, b: M128, c: M128, k: Mmask8, rounding: Int): M128

public expect fun vfmaddcshMaskz(a: M128, b: M128, c: M128, k: Mmask8, rounding: Int): M128

public expect fun vfcmaddcphMask3128(a: M128, b: M128, c: M128, k: Mmask8): M128

public expect fun vfcmaddcphMaskz128(a: M128, b: M128, c: M128, k: Mmask8): M128

public expect fun vfcmaddcphMask3256(a: M256, b: M256, c: M256, k: Mmask8): M256

public expect fun vfcmaddcphMaskz256(a: M256, b: M256, c: M256, k: Mmask8): M256

public expect fun vfcmaddcphMask3512(a: M512, b: M512, c: M512, k: Mmask16, rounding: Int): M512

public expect fun vfcmaddcphMaskz512(a: M512, b: M512, c: M512, k: Mmask16, rounding: Int): M512

public expect fun vfcmaddcshMask(a: M128, b: M128, c: M128, k: Mmask8, rounding: Int): M128

public expect fun vfcmaddcshMaskz(a: M128, b: M128, c: M128, k: Mmask8, rounding: Int): M128

public expect fun vfmaddph512(a: M512h, b: M512h, c: M512h, rounding: Int): M512h

public expect fun vfmaddsh(a: F16, b: F16, c: F16, rounding: Int): F16

public expect fun vfmaddsubph512(a: M512h, b: M512h, c: M512h, rounding: Int): M512h

public expect fun vrcpph128(a: M128h, src: M128h, k: Mmask8): M128h

public expect fun vrcpph256(a: M256h, src: M256h, k: Mmask16): M256h

public expect fun vrcpph512(a: M512h, src: M512h, k: Mmask32): M512h

public expect fun vrcpsh(a: M128h, b: M128h, src: M128h, k: Mmask8): M128h

public expect fun vrsqrtph128(a: M128h, src: M128h, k: Mmask8): M128h

public expect fun vrsqrtph256(a: M256h, src: M256h, k: Mmask16): M256h

public expect fun vrsqrtph512(a: M512h, src: M512h, k: Mmask32): M512h

public expect fun vrsqrtsh(a: M128h, b: M128h, src: M128h, k: Mmask8): M128h

public expect fun vsqrtph512(a: M512h, rounding: Int): M512h

public expect fun vsqrtsh(a: M128h, b: M128h, src: M128h, k: Mmask8, rounding: Int): M128h

public expect fun vmaxph128(a: M128h, b: M128h): M128h

public expect fun vmaxph256(a: M256h, b: M256h): M256h

public expect fun vmaxph512(a: M512h, b: M512h, sae: Int): M512h

public expect fun vmaxsh(a: M128h, b: M128h, src: M128h, k: Mmask8, sae: Int): M128h

public expect fun vminph128(a: M128h, b: M128h): M128h

public expect fun vminph256(a: M256h, b: M256h): M256h

public expect fun vminph512(a: M512h, b: M512h, sae: Int): M512h

public expect fun vminsh(a: M128h, b: M128h, src: M128h, k: Mmask8, sae: Int): M128h

public expect fun vgetexpph128(a: M128h, src: M128h, k: Mmask8): M128h

public expect fun vgetexpph256(a: M256h, src: M256h, k: Mmask16): M256h

public expect fun vgetexpph512(a: M512h, src: M512h, k: Mmask32, sae: Int): M512h

public expect fun vgetexpsh(a: M128h, b: M128h, src: M128h, k: Mmask8, sae: Int): M128h

public expect fun vgetmantph128(a: M128h, imm8: Int, src: M128h, k: Mmask8): M128h

public expect fun vgetmantph256(a: M256h, imm8: Int, src: M256h, k: Mmask16): M256h

public expect fun vgetmantph512(a: M512h, imm8: Int, src: M512h, k: Mmask32, sae: Int): M512h

public expect fun vgetmantsh(a: M128h, b: M128h, imm8: Int, src: M128h, k: Mmask8, sae: Int): M128h

public expect fun vrndscaleph128(a: M128h, imm8: Int, src: M128h, k: Mmask8): M128h

public expect fun vrndscaleph256(a: M256h, imm8: Int, src: M256h, k: Mmask16): M256h

public expect fun vrndscaleph512(a: M512h, imm8: Int, src: M512h, k: Mmask32, sae: Int): M512h

public expect fun vrndscalesh(a: M128h, b: M128h, src: M128h, k: Mmask8, imm8: Int, sae: Int): M128h

public expect fun vscalefph128(a: M128h, b: M128h, src: M128h, k: Mmask8): M128h

public expect fun vscalefph256(a: M256h, b: M256h, src: M256h, k: Mmask16): M256h

public expect fun vscalefph512(a: M512h, b: M512h, src: M512h, k: Mmask32, rounding: Int): M512h

public expect fun vscalefsh(a: M128h, b: M128h, src: M128h, k: Mmask8, rounding: Int): M128h

public expect fun vreduceph128(a: M128h, imm8: Int, src: M128h, k: Mmask8): M128h

public expect fun vreduceph256(a: M256h, imm8: Int, src: M256h, k: Mmask16): M256h

public expect fun vreduceph512(a: M512h, imm8: Int, src: M512h, k: Mmask32, sae: Int): M512h

public expect fun vreducesh(a: M128h, b: M128h, src: M128h, k: Mmask8, imm8: Int, sae: Int): M128h

public expect fun vfpclassph128(a: M128h, imm8: Int): Mmask8

public expect fun vfpclassph256(a: M256h, imm8: Int): Mmask16

public expect fun vfpclassph512(a: M512h, imm8: Int): Mmask32

public expect fun vfpclasssh(a: M128h, imm8: Int, k: Mmask8): Mmask8

public expect fun vcvtw2ph128(a: I16x8, rounding: Int): M128h

public expect fun vcvtw2ph256(a: I16x16, rounding: Int): M256h

public expect fun vcvtw2ph512(a: I16x32, rounding: Int): M512h

public expect fun vcvtuw2ph128(a: U16x8, rounding: Int): M128h

public expect fun vcvtuw2ph256(a: U16x16, rounding: Int): M256h

public expect fun vcvtuw2ph512(a: U16x32, rounding: Int): M512h

public expect fun vcvtdq2ph128(a: I32x4, src: M128h, k: Mmask8): M128h

public expect fun vcvtdq2ph256(a: I32x8, rounding: Int): M128h

public expect fun vcvtdq2ph512(a: I32x16, rounding: Int): M256h

public expect fun vcvtsi2sh(a: M128h, b: Int, rounding: Int): M128h

public expect fun vcvtudq2ph128(a: U32x4, src: M128h, k: Mmask8): M128h

public expect fun vcvtudq2ph256(a: U32x8, rounding: Int): M128h

public expect fun vcvtudq2ph512(a: U32x16, rounding: Int): M256h

public expect fun vcvtusi2sh(a: M128h, b: UInt, rounding: Int): M128h

public expect fun vcvtqq2ph128(a: I64x2, src: M128h, k: Mmask8): M128h

public expect fun vcvtqq2ph256(a: I64x4, src: M128h, k: Mmask8): M128h

public expect fun vcvtqq2ph512(a: I64x8, rounding: Int): M128h

public expect fun vcvtuqq2ph128(a: U64x2, src: M128h, k: Mmask8): M128h

public expect fun vcvtuqq2ph256(a: U64x4, src: M128h, k: Mmask8): M128h

public expect fun vcvtuqq2ph512(a: U64x8, rounding: Int): M128h

public expect fun vcvtps2phx128(a: M128, src: M128h, k: Mmask8): M128h

public expect fun vcvtps2phx256(a: M256, src: M128h, k: Mmask8): M128h

public expect fun vcvtps2phx512(a: M512, src: M256h, k: Mmask16, rounding: Int): M256h

public expect fun vcvtss2sh(a: M128h, b: M128, src: M128h, k: Mmask8, rounding: Int): M128h

public expect fun vcvtpd2ph128(a: M128d, src: M128h, k: Mmask8): M128h

public expect fun vcvtpd2ph256(a: M256d, src: M128h, k: Mmask8): M128h

public expect fun vcvtpd2ph512(a: M512d, src: M128h, k: Mmask8, rounding: Int): M128h

public expect fun vcvtsd2sh(a: M128h, b: M128d, src: M128h, k: Mmask8, rounding: Int): M128h

public expect fun vcvtph2w128(a: M128h, src: I16x8, k: Mmask8): I16x8

public expect fun vcvtph2w256(a: M256h, src: I16x16, k: Mmask16): I16x16

public expect fun vcvtph2w512(a: M512h, src: I16x32, k: Mmask32, rounding: Int): I16x32

public expect fun vcvtph2uw128(a: M128h, src: U16x8, k: Mmask8): U16x8

public expect fun vcvtph2uw256(a: M256h, src: U16x16, k: Mmask16): U16x16

public expect fun vcvtph2uw512(a: M512h, src: U16x32, k: Mmask32, sae: Int): U16x32

public expect fun vcvttph2w128(a: M128h, src: I16x8, k: Mmask8): I16x8

public expect fun vcvttph2w256(a: M256h, src: I16x16, k: Mmask16): I16x16

public expect fun vcvttph2w512(a: M512h, src: I16x32, k: Mmask32, sae: Int): I16x32

public expect fun vcvttph2uw128(a: M128h, src: U16x8, k: Mmask8): U16x8

public expect fun vcvttph2uw256(a: M256h, src: U16x16, k: Mmask16): U16x16

public expect fun vcvttph2uw512(a: M512h, src: U16x32, k: Mmask32, sae: Int): U16x32

public expect fun vcvtph2dq128(a: M128h, src: I32x4, k: Mmask8): I32x4

public expect fun vcvtph2dq256(a: M128h, src: I32x8, k: Mmask8): I32x8

public expect fun vcvtph2dq512(a: M256h, src: I32x16, k: Mmask16, rounding: Int): I32x16

public expect fun vcvtsh2si32(a: M128h, rounding: Int): Int

public expect fun vcvtph2udq128(a: M128h, src: U32x4, k: Mmask8): U32x4

public expect fun vcvtph2udq256(a: M128h, src: U32x8, k: Mmask8): U32x8

public expect fun vcvtph2udq512(a: M256h, src: U32x16, k: Mmask16, rounding: Int): U32x16

public expect fun vcvtsh2usi32(a: M128h, sae: Int): UInt

public expect fun vcvttph2dq128(a: M128h, src: I32x4, k: Mmask8): I32x4

public expect fun vcvttph2dq256(a: M128h, src: I32x8, k: Mmask8): I32x8

public expect fun vcvttph2dq512(a: M256h, src: I32x16, k: Mmask16, sae: Int): I32x16

public expect fun vcvttsh2si32(a: M128h, sae: Int): Int

public expect fun vcvttph2udq128(a: M128h, src: U32x4, k: Mmask8): U32x4

public expect fun vcvttph2udq256(a: M128h, src: U32x8, k: Mmask8): U32x8

public expect fun vcvttph2udq512(a: M256h, src: U32x16, k: Mmask16, sae: Int): U32x16

public expect fun vcvttsh2usi32(a: M128h, sae: Int): UInt

public expect fun vcvtph2qq128(a: M128h, src: I64x2, k: Mmask8): I64x2

public expect fun vcvtph2qq256(a: M128h, src: I64x4, k: Mmask8): I64x4

public expect fun vcvtph2qq512(a: M128h, src: I64x8, k: Mmask8, rounding: Int): I64x8

public expect fun vcvtph2uqq128(a: M128h, src: U64x2, k: Mmask8): U64x2

public expect fun vcvtph2uqq256(a: M128h, src: U64x4, k: Mmask8): U64x4

public expect fun vcvtph2uqq512(a: M128h, src: U64x8, k: Mmask8, rounding: Int): U64x8

public expect fun vcvttph2qq128(a: M128h, src: I64x2, k: Mmask8): I64x2

public expect fun vcvttph2qq256(a: M128h, src: I64x4, k: Mmask8): I64x4

public expect fun vcvttph2qq512(a: M128h, src: I64x8, k: Mmask8, sae: Int): I64x8

public expect fun vcvttph2uqq128(a: M128h, src: U64x2, k: Mmask8): U64x2

public expect fun vcvttph2uqq256(a: M128h, src: U64x4, k: Mmask8): U64x4

public expect fun vcvttph2uqq512(a: M128h, src: U64x8, k: Mmask8, sae: Int): U64x8

public expect fun vcvtph2psx128(a: M128h, src: M128, k: Mmask8): M128

public expect fun vcvtph2psx256(a: M128h, src: M256, k: Mmask8): M256

public expect fun vcvtph2psx512(a: M256h, src: M512, k: Mmask16, sae: Int): M512

public expect fun vcvtsh2ss(a: M128, b: M128h, src: M128, k: Mmask8, sae: Int): M128

public expect fun vcvtph2pd128(a: M128h, src: M128d, k: Mmask8): M128d

public expect fun vcvtph2pd256(a: M128h, src: M256d, k: Mmask8): M256d

public expect fun vcvtph2pd512(a: M128h, src: M512d, k: Mmask8, sae: Int): M512d

public expect fun vcvtsh2sd(a: M128d, b: M128h, src: M128d, k: Mmask8, sae: Int): M128d
