// port-lint: source x86/avx.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.SimdShuffleIdx
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I32x8
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I64x4
import io.github.kotlinmania.rustlang.corearch.simd.I8x32
import io.github.kotlinmania.rustlang.corearch.simd.U32x8
import io.github.kotlinmania.rustlang.corearch.simd.U64x4

// Advanced Vector Extensions (AVX)
//
// The references are:
//
// - [Intel 64 and IA-32 Architectures Software Developer's Manual Volume 2:
//   Instruction Set Reference, A-Z][intel64Ref]. - [AMD64 Architecture
//   Programmer's Manual, Volume 3: General-Purpose and System
//   Instructions][amd64Ref].
//
// [Wikipedia][wiki] provides a quick overview of the instructions available.
//
// [intel64Ref]: https://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf
// [amd64Ref]: https://docs.amd.com/v/u/en-US/24594_3.37
// [wiki]: https://en.wikipedia.org/wiki/AdvancedVectorExtensions

/**
 * Adds packed double-precision (64-bit) floating-point elements
 * in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_add_pd)
 */
public fun mm256AddPd(a: M256d, b: M256d): M256d = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Adds packed single-precision (32-bit) floating-point elements in `a` and
 * `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_add_ps)
 */
public fun mm256AddPs(a: M256, b: M256): M256 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

// See https://github.com/rust-lang/stdarch/issues/71

/**
 * Computes the bitwise AND of a packed double-precision (64-bit)
 * floating-point elements in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_and_pd)
 */
public fun mm256AndPd(a: M256d, b: M256d): M256d =
    run {
        val a1: U64x4 = transmuteWithLayouts<M256d, U64x4>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<U64x4>())
        val b1: U64x4 = transmuteWithLayouts<M256d, U64x4>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<U64x4>())
        transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
    }

/**
 * Computes the bitwise AND of packed single-precision (32-bit) floating-point
 * elements in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_and_ps)
 */
public fun mm256AndPs(a: M256, b: M256): M256 =
    run {
        val a1: U32x8 = transmuteWithLayouts<M256, U32x8>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<U32x8>())
        val b1: U32x8 = transmuteWithLayouts<M256, U32x8>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<U32x8>())
        transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
    }

// See <https://github.com/rust-lang/stdarch/issues/71>.

/**
 * Computes the bitwise OR packed double-precision (64-bit) floating-point
 * elements in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_or_pd)
 */
public fun mm256OrPd(a: M256d, b: M256d): M256d =
    run {
        val a1: U64x4 = transmuteWithLayouts<M256d, U64x4>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<U64x4>())
        val b1: U64x4 = transmuteWithLayouts<M256d, U64x4>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<U64x4>())
        transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
    }

/**
 * Computes the bitwise OR packed single-precision (32-bit) floating-point
 * elements in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_or_ps)
 */
public fun mm256OrPs(a: M256, b: M256): M256 =
    run {
        val a1: U32x8 = transmuteWithLayouts<M256, U32x8>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<U32x8>())
        val b1: U32x8 = transmuteWithLayouts<M256, U32x8>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<U32x8>())
        transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
    }

/**
 * Shuffles double-precision (64-bit) floating-point elements within 128-bit
 * lanes using the control in `imm8`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_shuffle_pd)
 */
public fun mm256ShufflePd(a: M256d, b: M256d, mask: Int): M256d {
    run {
        check(0 <= mask && mask < (1 shl 8)) { "MASK doesn't fit in 8 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(mask.toUInt() and 0x1u, ((mask.toUInt() shr 1) and 0x1u) + 4u, ((mask.toUInt() shr 2) and 0x1u) + 2u, ((mask.toUInt() shr 3) and 0x1u) + 6u)); _simdArg0 }
}

/**
 * Shuffles single-precision (32-bit) floating-point elements in `a` within
 * 128-bit lanes using the control in `imm8`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_shuffle_ps)
 */
public fun mm256ShufflePs(a: M256, b: M256, mask: Int): M256 {
    run {
        check(0 <= mask && mask < (1 shl 8)) { "MASK doesn't fit in 8 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(mask.toUInt() and 0x3u, (mask.toUInt() shr 2) and 0x3u, ((mask.toUInt() shr 4) and 0x3u) + 8u, ((mask.toUInt() shr 6) and 0x3u) + 8u, (mask.toUInt() and 0x3u) + 4u, ((mask.toUInt() shr 2) and 0x3u) + 4u, ((mask.toUInt() shr 4) and 0x3u) + 12u, ((mask.toUInt() shr 6) and 0x3u) + 12u)); _simdArg0 }
}

/**
 * Computes the bitwise NOT of packed double-precision (64-bit) floating-point
 * elements in `a`, and then AND with `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_andnot_pd)
 */
public fun mm256AndnotPd(a: M256d, b: M256d): M256d =
    run {
        val a1: U64x4 = transmuteWithLayouts<M256d, U64x4>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<U64x4>())
        val b1: U64x4 = transmuteWithLayouts<M256d, U64x4>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<U64x4>())
        transmute(run { val _simdArg0 = run { val _simdArg0 = u64x4.splat((0uL).inv()); val _simdArg1 = a1; _simdArg0 }; val _simdArg1 = b1; _simdArg0 })
    }

/**
 * Computes the bitwise NOT of packed single-precision (32-bit) floating-point
 * elements in `a`
 * and then AND with `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_andnot_ps)
 */
public fun mm256AndnotPs(a: M256, b: M256): M256 =
    run {
        val a1: U32x8 = transmuteWithLayouts<M256, U32x8>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<U32x8>())
        val b1: U32x8 = transmuteWithLayouts<M256, U32x8>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<U32x8>())
        transmute(run { val _simdArg0 = run { val _simdArg0 = u32x8.splat((0u).inv()); val _simdArg1 = a1; _simdArg0 }; val _simdArg1 = b1; _simdArg0 })
    }

/**
 * Compares packed double-precision (64-bit) floating-point elements
 * in `a` and `b`, and returns packed maximum values
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_max_pd)
 */
public fun mm256MaxPd(a: M256d, b: M256d): M256d = vmaxpd(a, b)

/**
 * Compares packed single-precision (32-bit) floating-point elements in `a`
 * and `b`, and returns packed maximum values
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_max_ps)
 */
public fun mm256MaxPs(a: M256, b: M256): M256 = vmaxps(a, b)

/**
 * Compares packed double-precision (64-bit) floating-point elements
 * in `a` and `b`, and returns packed minimum values
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_min_pd)
 */
public fun mm256MinPd(a: M256d, b: M256d): M256d = vminpd(a, b)

/**
 * Compares packed single-precision (32-bit) floating-point elements in `a`
 * and `b`, and returns packed minimum values
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_min_ps)
 */
public fun mm256MinPs(a: M256, b: M256): M256 = vminps(a, b)

/**
 * Multiplies packed double-precision (64-bit) floating-point elements
 * in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mul_pd)
 */
public fun mm256MulPd(a: M256d, b: M256d): M256d = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Multiplies packed single-precision (32-bit) floating-point elements in `a` and
 * `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mul_ps)
 */
public fun mm256MulPs(a: M256, b: M256): M256 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Alternatively adds and subtracts packed double-precision (64-bit)
 * floating-point elements in `a` to/from packed elements in `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_addsub_pd)
 */
public fun mm256AddsubPd(a: M256d, b: M256d): M256d =
    run {
        val a1 = a.asF64x4()
        val b1 = b.asF64x4()
        val add = run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 }
        val sub = run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 }
        run { val _simdArg0 = add; val _simdArg1 = sub; val _simdArg2 = SimdShuffleIdx(listOf(4, 1, 6, 3)); _simdArg0 }
    }

/**
 * Alternatively adds and subtracts packed single-precision (32-bit)
 * floating-point elements in `a` to/from packed elements in `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_addsub_ps)
 */
public fun mm256AddsubPs(a: M256, b: M256): M256 =
    run {
        val a1 = a.asF32x8()
        val b1 = b.asF32x8()
        val add = run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 }
        val sub = run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 }
        run { val _simdArg0 = add; val _simdArg1 = sub; val _simdArg2 = SimdShuffleIdx(listOf(8, 1, 10, 3, 12, 5, 14, 7)); _simdArg0 }
    }

/**
 * Subtracts packed double-precision (64-bit) floating-point elements in `b`
 * from packed elements in `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_sub_pd)
 */
public fun mm256SubPd(a: M256d, b: M256d): M256d = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Subtracts packed single-precision (32-bit) floating-point elements in `b`
 * from packed elements in `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_sub_ps)
 */
public fun mm256SubPs(a: M256, b: M256): M256 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Computes the division of each of the 8 packed 32-bit floating-point elements
 * in `a` by the corresponding packed elements in `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_div_ps)
 */
public fun mm256DivPs(a: M256, b: M256): M256 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Computes the division of each of the 4 packed 64-bit floating-point elements
 * in `a` by the corresponding packed elements in `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_div_pd)
 */
public fun mm256DivPd(a: M256d, b: M256d): M256d = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Rounds packed double-precision (64-bit) floating point elements in `a`
 * according to the flag `ROUNDING`. The value of `ROUNDING` may be as follows:
 *
 * - `0x00`: Round to the nearest whole number.
 * - `0x01`: Round down, toward negative infinity.
 * - `0x02`: Round up, toward positive infinity.
 * - `0x03`: Truncate the values.
 *
 * For a complete list of options, check [the LLVM docs][llvmDocs].
 *
 * [llvmDocs]: https://github.com/llvm-mirror/clang/blob/dcd8d797b20291f1a6b3e0ddda085aa2bbb382a8/lib/Headers/avxintrin.h#L382
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_round_pd)
 */
public fun mm256RoundPd(a: M256d, rounding: Int): M256d {
    run {
        check(0 <= rounding && rounding < (1 shl 4)) { "ROUNDING doesn't fit in 4 bits (unsigned)" }
    }
    return roundpd256(a, rounding)
}

/**
 * Rounds packed double-precision (64-bit) floating point elements in `a`
 * toward positive infinity.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_ceil_pd)
 */
public fun mm256CeilPd(a: M256d): M256d = run { val _simdArg0 = a; _simdArg0 }

/**
 * Rounds packed double-precision (64-bit) floating point elements in `a`
 * toward negative infinity.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_floor_pd)
 */
public fun mm256FloorPd(a: M256d): M256d = run { val _simdArg0 = a; _simdArg0 }

/**
 * Rounds packed single-precision (32-bit) floating point elements in `a`
 * according to the flag `ROUNDING`. The value of `ROUNDING` may be as follows:
 *
 * - `0x00`: Round to the nearest whole number.
 * - `0x01`: Round down, toward negative infinity.
 * - `0x02`: Round up, toward positive infinity.
 * - `0x03`: Truncate the values.
 *
 * For a complete list of options, check [the LLVM docs][llvmDocs].
 *
 * [llvmDocs]: https://github.com/llvm-mirror/clang/blob/dcd8d797b20291f1a6b3e0ddda085aa2bbb382a8/lib/Headers/avxintrin.h#L382
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_round_ps)
 */
public fun mm256RoundPs(a: M256, rounding: Int): M256 {
    run {
        check(0 <= rounding && rounding < (1 shl 4)) { "ROUNDING doesn't fit in 4 bits (unsigned)" }
    }
    return roundps256(a, rounding)
}

/**
 * Rounds packed single-precision (32-bit) floating point elements in `a`
 * toward positive infinity.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_ceil_ps)
 */
public fun mm256CeilPs(a: M256): M256 = run { val _simdArg0 = a; _simdArg0 }

/**
 * Rounds packed single-precision (32-bit) floating point elements in `a`
 * toward negative infinity.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_floor_ps)
 */
public fun mm256FloorPs(a: M256): M256 = run { val _simdArg0 = a; _simdArg0 }

/**
 * Returns the square root of packed single-precision (32-bit) floating point
 * elements in `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_sqrt_ps)
 */
public fun mm256SqrtPs(a: M256): M256 = run { val _simdArg0 = a; _simdArg0 }

/**
 * Returns the square root of packed double-precision (64-bit) floating point
 * elements in `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_sqrt_pd)
 */
public fun mm256SqrtPd(a: M256d): M256d = run { val _simdArg0 = a; _simdArg0 }

// Note: LLVM7 prefers single-precision blend instructions when
// possible, see: https://bugs.llvm.org/showBug.cgi?id=38194
// @cfgAttr(test, assertInstr(vblendpd, imm8 = 9))

/**
 * Blends packed double-precision (64-bit) floating-point elements from
 * `a` and `b` using control mask `imm8`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_blend_pd)
 */
public fun mm256BlendPd(a: M256d, b: M256d, imm4: Int): M256d {
    run {
        check(0 <= imm4 && imm4 < (1 shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(((imm4.toUInt() shr 0) and 1u) * 4u + 0u, ((imm4.toUInt() shr 1) and 1u) * 4u + 1u, ((imm4.toUInt() shr 2) and 1u) * 4u + 2u, ((imm4.toUInt() shr 3) and 1u) * 4u + 3u)); _simdArg0 }
}

/**
 * Blends packed single-precision (32-bit) floating-point elements from
 * `a` and `b` using control mask `imm8`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_blend_ps)
 */
public fun mm256BlendPs(a: M256, b: M256, imm8: Int): M256 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(((imm8.toUInt() shr 0) and 1u) * 8u + 0u, ((imm8.toUInt() shr 1) and 1u) * 8u + 1u, ((imm8.toUInt() shr 2) and 1u) * 8u + 2u, ((imm8.toUInt() shr 3) and 1u) * 8u + 3u, ((imm8.toUInt() shr 4) and 1u) * 8u + 4u, ((imm8.toUInt() shr 5) and 1u) * 8u + 5u, ((imm8.toUInt() shr 6) and 1u) * 8u + 6u, ((imm8.toUInt() shr 7) and 1u) * 8u + 7u)); _simdArg0 }
}

/**
 * Blends packed double-precision (64-bit) floating-point elements from
 * `a` and `b` using `c` as a mask.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_blendv_pd)
 */
public fun mm256BlendvPd(a: M256d, b: M256d, c: M256d): M256d =
    run {
        val mask: I64x4 = run { val _simdArg0 = transmuteWithLayouts<M256d, I64x4>(c, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<I64x4>()); val _simdArg1 = i64x4.ZERO; _simdArg0 }
        transmute(run { val _simdArg0 = mask; val _simdArg1 = b.asF64x4(); val _simdArg2 = a.asF64x4(); _simdArg1 })
    }

/**
 * Blends packed single-precision (32-bit) floating-point elements from
 * `a` and `b` using `c` as a mask.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_blendv_ps)
 */
public fun mm256BlendvPs(a: M256, b: M256, c: M256): M256 =
    run {
        val mask: I32x8 = run { val _simdArg0 = transmuteWithLayouts<M256, I32x8>(c, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<I32x8>()); val _simdArg1 = i32x8.ZERO; _simdArg0 }
        transmute(run { val _simdArg0 = mask; val _simdArg1 = b.asF32x8(); val _simdArg2 = a.asF32x8(); _simdArg1 })
    }

/**
 * Conditionally multiplies the packed single-precision (32-bit) floating-point
 * elements in `a` and `b` using the high 4 bits in `imm8`,
 * sum the four products, and conditionally return the sum
 *  using the low 4 bits of `imm8`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_dp_ps)
 */
public fun mm256DpPs(a: M256, b: M256, imm8: Int): M256 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return vdpps(a, b, imm8.toByte())
}

/**
 * Horizontal addition of adjacent pairs in the two packed vectors
 * of 4 64-bit floating points `a` and `b`.
 * In the result, sums of elements from `a` are returned in even locations,
 * while sums of elements from `b` are returned in odd locations.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_hadd_pd)
 */
public fun mm256HaddPd(a: M256d, b: M256d): M256d =
    run {
        val even = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 4, 2, 6)); _simdArg0 }
        val odd = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 5, 3, 7)); _simdArg0 }
        run { val _simdArg0 = even; val _simdArg1 = odd; _simdArg0 }
    }

/**
 * Horizontal addition of adjacent pairs in the two packed vectors
 * of 8 32-bit floating points `a` and `b`.
 * In the result, sums of elements from `a` are returned in locations of
 * indices 0, 1, 4, 5; while sums of elements from `b` are locations
 * 2, 3, 6, 7.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_hadd_ps)
 */
public fun mm256HaddPs(a: M256, b: M256): M256 =
    run {
        val even = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2, 8, 10, 4, 6, 12, 14)); _simdArg0 }
        val odd = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3, 9, 11, 5, 7, 13, 15)); _simdArg0 }
        run { val _simdArg0 = even; val _simdArg1 = odd; _simdArg0 }
    }

/**
 * Horizontal subtraction of adjacent pairs in the two packed vectors
 * of 4 64-bit floating points `a` and `b`.
 * In the result, sums of elements from `a` are returned in even locations,
 * while sums of elements from `b` are returned in odd locations.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_hsub_pd)
 */
public fun mm256HsubPd(a: M256d, b: M256d): M256d =
    run {
        val even = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 4, 2, 6)); _simdArg0 }
        val odd = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 5, 3, 7)); _simdArg0 }
        run { val _simdArg0 = even; val _simdArg1 = odd; _simdArg0 }
    }

/**
 * Horizontal subtraction of adjacent pairs in the two packed vectors
 * of 8 32-bit floating points `a` and `b`.
 * In the result, sums of elements from `a` are returned in locations of
 * indices 0, 1, 4, 5; while sums of elements from `b` are locations
 * 2, 3, 6, 7.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_hsub_ps)
 */
public fun mm256HsubPs(a: M256, b: M256): M256 =
    run {
        val even = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2, 8, 10, 4, 6, 12, 14)); _simdArg0 }
        val odd = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3, 9, 11, 5, 7, 13, 15)); _simdArg0 }
        run { val _simdArg0 = even; val _simdArg1 = odd; _simdArg0 }
    }

/**
 * Computes the bitwise XOR of packed double-precision (64-bit) floating-point
 * elements in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_xor_pd)
 */
public fun mm256XorPd(a: M256d, b: M256d): M256d =
    run {
        val a1: U64x4 = transmuteWithLayouts<M256d, U64x4>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<U64x4>())
        val b1: U64x4 = transmuteWithLayouts<M256d, U64x4>(b, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<U64x4>())
        transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
    }

/**
 * Computes the bitwise XOR of packed single-precision (32-bit) floating-point
 * elements in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_xor_ps)
 */
public fun mm256XorPs(a: M256, b: M256): M256 =
    run {
        val a1: U32x8 = transmuteWithLayouts<M256, U32x8>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<U32x8>())
        val b1: U32x8 = transmuteWithLayouts<M256, U32x8>(b, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<U32x8>())
        transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
    }

/**
 * Equal (ordered, non-signaling)
 */
public val CMP_EQ_OQ: Int = 0x00

/**
 * Less-than (ordered, signaling)
 */
public val CMP_LT_OS: Int = 0x01

/**
 * Less-than-or-equal (ordered, signaling)
 */
public val CMP_LE_OS: Int = 0x02

/**
 * Unordered (non-signaling)
 */
public val CMP_UNORD_Q: Int = 0x03

/**
 * Not-equal (unordered, non-signaling)
 */
public val CMP_NEQ_UQ: Int = 0x04

/**
 * Not-less-than (unordered, signaling)
 */
public val CMP_NLT_US: Int = 0x05

/**
 * Not-less-than-or-equal (unordered, signaling)
 */
public val CMP_NLE_US: Int = 0x06

/**
 * Ordered (non-signaling)
 */
public val CMP_ORD_Q: Int = 0x07

/**
 * Equal (unordered, non-signaling)
 */
public val CMP_EQ_UQ: Int = 0x08

/**
 * Not-greater-than-or-equal (unordered, signaling)
 */
public val CMP_NGE_US: Int = 0x09

/**
 * Not-greater-than (unordered, signaling)
 */
public val CMP_NGT_US: Int = 0x0a

/**
 * False (ordered, non-signaling)
 */
public val CMP_FALSE_OQ: Int = 0x0b

/**
 * Not-equal (ordered, non-signaling)
 */
public val CMP_NEQ_OQ: Int = 0x0c

/**
 * Greater-than-or-equal (ordered, signaling)
 */
public val CMP_GE_OS: Int = 0x0d

/**
 * Greater-than (ordered, signaling)
 */
public val CMP_GT_OS: Int = 0x0e

/**
 * True (unordered, non-signaling)
 */
public val CMP_TRUE_UQ: Int = 0x0f

/**
 * Equal (ordered, signaling)
 */
public val CMP_EQ_OS: Int = 0x10

/**
 * Less-than (ordered, non-signaling)
 */
public val CMP_LT_OQ: Int = 0x11

/**
 * Less-than-or-equal (ordered, non-signaling)
 */
public val CMP_LE_OQ: Int = 0x12

/**
 * Unordered (signaling)
 */
public val CMP_UNORD_S: Int = 0x13

/**
 * Not-equal (unordered, signaling)
 */
public val CMP_NEQ_US: Int = 0x14

/**
 * Not-less-than (unordered, non-signaling)
 */
public val CMP_NLT_UQ: Int = 0x15

/**
 * Not-less-than-or-equal (unordered, non-signaling)
 */
public val CMP_NLE_UQ: Int = 0x16

/**
 * Ordered (signaling)
 */
public val CMP_ORD_S: Int = 0x17

/**
 * Equal (unordered, signaling)
 */
public val CMP_EQ_US: Int = 0x18

/**
 * Not-greater-than-or-equal (unordered, non-signaling)
 */
public val CMP_NGE_UQ: Int = 0x19

/**
 * Not-greater-than (unordered, non-signaling)
 */
public val CMP_NGT_UQ: Int = 0x1a

/**
 * False (ordered, signaling)
 */
public val CMP_FALSE_OS: Int = 0x1b

/**
 * Not-equal (ordered, signaling)
 */
public val CMP_NEQ_OS: Int = 0x1c

/**
 * Greater-than-or-equal (ordered, non-signaling)
 */
public val CMP_GE_OQ: Int = 0x1d

/**
 * Greater-than (ordered, non-signaling)
 */
public val CMP_GT_OQ: Int = 0x1e

/**
 * True (unordered, signaling)
 */
public val CMP_TRUE_US: Int = 0x1f

// Note Validate vcmppd

/**
 * Compares packed double-precision (64-bit) floating-point
 * elements in `a` and `b` based on the comparison operand
 * specified by `IMM5`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmp_pd)
 */
public fun mmCmpPd(a: M128d, b: M128d, imm5: Int): M128d {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return vcmppd(a, b, imm5.toByte())
}

// Note Validate vcmppd

/**
 * Compares packed double-precision (64-bit) floating-point
 * elements in `a` and `b` based on the comparison operand
 * specified by `IMM5`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmp_pd)
 */
public fun mm256CmpPd(a: M256d, b: M256d, imm5: Int): M256d {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return vcmppd256(a, b, imm5.toUByte())
}

// Note Validate vcmpps

/**
 * Compares packed single-precision (32-bit) floating-point
 * elements in `a` and `b` based on the comparison operand
 * specified by `IMM5`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmp_ps)
 */
public fun mmCmpPs(a: M128, b: M128, imm5: Int): M128 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return vcmpps(a, b, imm5.toByte())
}

// Note Validate vcmpps

/**
 * Compares packed single-precision (32-bit) floating-point
 * elements in `a` and `b` based on the comparison operand
 * specified by `IMM5`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmp_ps)
 */
public fun mm256CmpPs(a: M256, b: M256, imm5: Int): M256 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return vcmpps256(a, b, imm5.toUByte())
}

// Note Validate vcmpsd

/**
 * Compares the lower double-precision (64-bit) floating-point element in
 * `a` and `b` based on the comparison operand specified by `IMM5`,
 * store the result in the lower element of returned vector,
 * and copies the upper element from `a` to the upper element of returned
 * vector.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmp_sd)
 */
public fun mmCmpSd(a: M128d, b: M128d, imm5: Int): M128d {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return vcmpsd(a, b, imm5.toByte())
}

// Note Validate vcmpss

/**
 * Compares the lower single-precision (32-bit) floating-point element in
 * `a` and `b` based on the comparison operand specified by `IMM5`,
 * store the result in the lower element of returned vector,
 * and copies the upper 3 packed elements from `a` to the upper elements of
 * returned vector.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmp_ss)
 */
public fun mmCmpSs(a: M128, b: M128, imm5: Int): M128 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return vcmpss(a, b, imm5.toByte())
}

/**
 * Converts packed 32-bit integers in `a` to packed double-precision (64-bit)
 * floating-point elements.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtepi32_pd)
 */
public fun mm256Cvtepi32Pd(a: M128i): M256d = run { val _simdArg0 = a.asI32x4(); _simdArg0 }

/**
 * Converts packed 32-bit integers in `a` to packed single-precision (32-bit)
 * floating-point elements.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtepi32_ps)
 */
public fun mm256Cvtepi32Ps(a: M256i): M256 = run { val _simdArg0 = a.asI32x8(); _simdArg0 }

/**
 * Converts packed double-precision (64-bit) floating-point elements in `a`
 * to packed single-precision (32-bit) floating-point elements.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtpd_ps)
 */
public fun mm256CvtpdPs(a: M256d): M128 = run { val _simdArg0 = a; _simdArg0 }

/**
 * Converts packed single-precision (32-bit) floating-point elements in `a`
 * to packed 32-bit integers.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtps_epi32)
 */
public fun mm256CvtpsEpi32(a: M256): M256i = transmuteWithLayouts<I32x8, M256i>(vcvtps2dq(a), UnresolvedTypeLayout<I32x8>(), UnresolvedTypeLayout<M256i>())

/**
 * Converts packed single-precision (32-bit) floating-point elements in `a`
 * to packed double-precision (64-bit) floating-point elements.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtps_pd)
 */
public fun mm256CvtpsPd(a: M128): M256d = run { val _simdArg0 = a; _simdArg0 }

// @cfgAttr(test, assertInstr(movsd)) Note

/**
 * Returns the first element of the input vector of `[4 x double]`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtsd_f64)
 */
public fun mm256CvtsdF64(a: M256d): Double = run { val _simdArg0 = a; val _simdArg1 = 0.0; _simdArg0 }

/**
 * Converts packed double-precision (64-bit) floating-point elements in `a`
 * to packed 32-bit integers with truncation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvttpd_epi32)
 */
public fun mm256CvttpdEpi32(a: M256d): M128i = transmuteWithLayouts<I32x4, M128i>(vcvttpd2dq(a), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<M128i>())

/**
 * Converts packed double-precision (64-bit) floating-point elements in `a`
 * to packed 32-bit integers.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtpd_epi32)
 */
public fun mm256CvtpdEpi32(a: M256d): M128i = transmuteWithLayouts<I32x4, M128i>(vcvtpd2dq(a), UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<M128i>())

/**
 * Converts packed single-precision (32-bit) floating-point elements in `a`
 * to packed 32-bit integers with truncation.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvttps_epi32)
 */
public fun mm256CvttpsEpi32(a: M256): M256i = transmuteWithLayouts<I32x8, M256i>(vcvttps2dq(a), UnresolvedTypeLayout<I32x8>(), UnresolvedTypeLayout<M256i>())

/**
 * Extracts 128 bits (composed of 4 packed single-precision (32-bit)
 * floating-point elements) from `a`, selected with `imm8`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_extractf128_ps)
 */
public fun mm256Extractf128Ps(a: M256, imm1: Int): M128 {
    run {
        check(0 <= imm1 && imm1 < (1 shl 1)) { "IMM1 doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = mm256UndefinedPs(); val _simdArg2 = SimdShuffleIdx(listOf(listOf(0, 1, 2, 3), listOf(4, 5, 6, 7))[imm1]); _simdArg0 }
}

/**
 * Extracts 128 bits (composed of 2 packed double-precision (64-bit)
 * floating-point elements) from `a`, selected with `imm8`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_extractf128_pd)
 */
public fun mm256Extractf128Pd(a: M256d, imm1: Int): M128d {
    run {
        check(0 <= imm1 && imm1 < (1 shl 1)) { "IMM1 doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = mm256UndefinedPd(); val _simdArg2 = SimdShuffleIdx(listOf(listOf(0, 1), listOf(2, 3))[imm1]); _simdArg0 }
}

/**
 * Extracts 128 bits (composed of integer data) from `a`, selected with `imm8`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_extractf128_si256)
 */
public fun mm256Extractf128Si256(a: M256i, imm1: Int): M128i {
    run {
        check(0 <= imm1 && imm1 < (1 shl 1)) { "IMM1 doesn't fit in 1 bits (unsigned)" }
    }
    return run {
        val dst: I64x2 = run { val _simdArg0 = a.asI64x4(); val _simdArg1 = i64x4.ZERO; val _simdArg2 = SimdShuffleIdx(listOf(listOf(0, 1), listOf(2, 3))[imm1]); _simdArg0 }
        transmuteWithLayouts<I64x2, M128i>(dst, UnresolvedTypeLayout<I64x2>(), UnresolvedTypeLayout<M128i>())
    }
}

// This intrinsic has no corresponding instruction.

/**
 * Extracts a 32-bit integer from `a`, selected with `INDEX`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_extract_epi32)
 */
public fun mm256ExtractEpi32(a: M256i, index: Int): Int {
    run {
        check(0 <= index && index < (1 shl 3)) { "INDEX doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a.asI32x8(); val _simdArg1 = index.toUInt(); _simdArg0 }
}

/**
 * Returns the first element of the input vector of `[8 x Int]`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtsi256_si32)
 */
public fun mm256Cvtsi256Si32(a: M256i): Int = run { val _simdArg0 = a.asI32x8(); val _simdArg1 = 0; _simdArg0 }

/**
 * Zeroes the contents of all XMM or YMM registers.
 *
 * This operation is purely a performance hint for the CPU and has no effect on the Abstract
 * Machine state.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_zeroall)
 */
public fun mm256Zeroall() {
    vzeroall()
}

/**
 * Zeroes the upper 128 bits of all YMM registers;
 * the lower 128-bits of the registers are unmodified.
 *
 * This operation is purely a performance hint for the CPU and has no effect on the Abstract
 * Machine state.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_zeroupper)
 */
public fun mm256Zeroupper() {
    vzeroupper()
}

/**
 * Shuffles single-precision (32-bit) floating-point elements in `a`
 * within 128-bit lanes using the control in `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_permutevar_ps)
 */
public fun mm256PermutevarPs(a: M256, b: M256i): M256 = vpermilps256(a, b.asI32x8())

/**
 * Shuffles single-precision (32-bit) floating-point elements in `a`
 * using the control in `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_permutevar_ps)
 */
public fun mmPermutevarPs(a: M128, b: M128i): M128 = vpermilps(a, b.asI32x4())

/**
 * Shuffles single-precision (32-bit) floating-point elements in `a`
 * within 128-bit lanes using the control in `imm8`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_permute_ps)
 */
public fun mm256PermutePs(a: M256, imm8: Int): M256 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = mm256UndefinedPs(); val _simdArg2 = SimdShuffleIdx(uintArrayOf((imm8.toUInt() shr 0) and 0x3u, (imm8.toUInt() shr 2) and 0x3u, (imm8.toUInt() shr 4) and 0x3u, (imm8.toUInt() shr 6) and 0x3u, ((imm8.toUInt() shr 0) and 0x3u) + 4u, ((imm8.toUInt() shr 2) and 0x3u) + 4u, ((imm8.toUInt() shr 4) and 0x3u) + 4u, ((imm8.toUInt() shr 6) and 0x3u) + 4u)); _simdArg0 }
}

/**
 * Shuffles single-precision (32-bit) floating-point elements in `a`
 * using the control in `imm8`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_permute_ps)
 */
public fun mmPermutePs(a: M128, imm8: Int): M128 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = mmUndefinedPs(); val _simdArg2 = SimdShuffleIdx(uintArrayOf((imm8.toUInt() shr 0) and 0x3u, (imm8.toUInt() shr 2) and 0x3u, (imm8.toUInt() shr 4) and 0x3u, (imm8.toUInt() shr 6) and 0x3u)); _simdArg0 }
}

/**
 * Shuffles double-precision (64-bit) floating-point elements in `a`
 * within 256-bit lanes using the control in `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_permutevar_pd)
 */
public fun mm256PermutevarPd(a: M256d, b: M256i): M256d = vpermilpd256(a, b.asI64x4())

/**
 * Shuffles double-precision (64-bit) floating-point elements in `a`
 * using the control in `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_permutevar_pd)
 */
public fun mmPermutevarPd(a: M128d, b: M128i): M128d = vpermilpd(a, b.asI64x2())

/**
 * Shuffles double-precision (64-bit) floating-point elements in `a`
 * within 128-bit lanes using the control in `imm8`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_permute_pd)
 */
public fun mm256PermutePd(a: M256d, imm4: Int): M256d {
    run {
        check(0 <= imm4 && imm4 < (1 shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = mm256UndefinedPd(); val _simdArg2 = SimdShuffleIdx(uintArrayOf(((imm4.toUInt() shr 0) and 1u), ((imm4.toUInt() shr 1) and 1u), ((imm4.toUInt() shr 2) and 1u) + 2u, ((imm4.toUInt() shr 3) and 1u) + 2u)); _simdArg0 }
}

/**
 * Shuffles double-precision (64-bit) floating-point elements in `a`
 * using the control in `imm8`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_permute_pd)
 */
public fun mmPermutePd(a: M128d, imm2: Int): M128d {
    run {
        check(0 <= imm2 && imm2 < (1 shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = mmUndefinedPd(); val _simdArg2 = SimdShuffleIdx(uintArrayOf((imm2.toUInt()) and 1u, (imm2.toUInt() shr 1) and 1u)); _simdArg0 }
}

/**
 * Shuffles 256 bits (composed of 8 packed single-precision (32-bit)
 * floating-point elements) selected by `imm8` from `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_permute2f128_ps)
 */
public fun mm256Permute2f128Ps(a: M256, b: M256, imm8: Int): M256 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm256Castsi256Ps(mm256Permute2f128Si256(mm256CastpsSi256(a), mm256CastpsSi256(b), imm8))
}

/**
 * Shuffles 256 bits (composed of 4 packed double-precision (64-bit)
 * floating-point elements) selected by `imm8` from `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_permute2f128_pd)
 */
public fun mm256Permute2f128Pd(a: M256d, b: M256d, imm8: Int): M256d {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return mm256Castsi256Pd(mm256Permute2f128Si256(mm256CastpdSi256(a), mm256CastpdSi256(b), imm8))
}

/**
 * Shuffles 128-bits (composed of integer data) selected by `imm8`
 * from `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_permute2f128_si256)
 */
public fun mm256Permute2f128Si256(a: M256i, b: M256i, imm8: Int): M256i {
    fun idx(imm8: Int, pos: UInt): UInt {
        val part =
            if (pos < 2u) {
                imm8 and 0xf
            } else {
                (imm8 and 0xf0) shr 4
            }
        return 2u * (part.toUInt() and 0x3u) + (pos and 1u)
    }
    fun idx0(imm8: Int, pos: UInt): UInt {
        val part =
            if (pos < 2u) {
                imm8 and 0xf
            } else {
                (imm8 and 0xf0) shr 4
            }
        return if (part and 0x8 != 0) {
            4u
        } else {
            pos
        }
    }
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return run {
        val r = run { val _simdArg0 = a.asI64x4(); val _simdArg1 = b.asI64x4(); val _simdArg2 = SimdShuffleIdx(uintArrayOf(idx(imm8, 0u), idx(imm8, 1u), idx(imm8, 2u), idx(imm8, 3u))); _simdArg0 }
        val r1: I64x4 = run { val _simdArg0 = r; val _simdArg1 = i64x4.ZERO; val _simdArg2 = SimdShuffleIdx(uintArrayOf(idx0(imm8, 0u), idx0(imm8, 1u), idx0(imm8, 2u), idx0(imm8, 3u))); _simdArg0 }
        r1.asM256i()
    }
}

/**
 * Broadcasts a single-precision (32-bit) floating-point element from memory
 * to all elements of the returned vector.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_broadcast_ss)
 */
public fun mm256BroadcastSs(f: Float): M256 = mm256Set1Ps(f)

/**
 * Broadcasts a single-precision (32-bit) floating-point element from memory
 * to all elements of the returned vector.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_broadcast_ss)
 */
public fun mmBroadcastSs(f: Float): M128 = mmSet1Ps(f)

/**
 * Broadcasts a double-precision (64-bit) floating-point element from memory
 * to all elements of the returned vector.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_broadcast_sd)
 */
public fun mm256BroadcastSd(f: Double): M256d = mm256Set1Pd(f)

/**
 * Broadcasts 128 bits from memory (composed of 4 packed single-precision
 * (32-bit) floating-point elements) to all elements of the returned vector.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_broadcast_ps)
 */
public fun mm256BroadcastPs(a: M128): M256 = run { val _simdArg0 = a; val _simdArg1 = mmSetzeroPs(); val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 0, 1, 2, 3)); _simdArg0 }

/**
 * Broadcasts 128 bits from memory (composed of 2 packed double-precision
 * (64-bit) floating-point elements) to all elements of the returned vector.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_broadcast_pd)
 */
public fun mm256BroadcastPd(a: M128d): M256d = run { val _simdArg0 = a; val _simdArg1 = mmSetzeroPd(); val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 0, 1)); _simdArg0 }

/**
 * Copies `a` to result, then inserts 128 bits (composed of 4 packed
 * single-precision (32-bit) floating-point elements) from `b` into result
 * at the location specified by `imm8`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_insertf128_ps)
 */
public fun mm256Insertf128Ps(a: M256, b: M128, imm1: Int): M256 {
    run {
        check(0 <= imm1 && imm1 < (1 shl 1)) { "IMM1 doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = mm256Castps128Ps256(b); val _simdArg2 = SimdShuffleIdx(listOf(listOf(8, 9, 10, 11, 4, 5, 6, 7), listOf(0, 1, 2, 3, 8, 9, 10, 11))[imm1]); _simdArg0 }
}

/**
 * Copies `a` to result, then inserts 128 bits (composed of 2 packed
 * double-precision (64-bit) floating-point elements) from `b` into result
 * at the location specified by `imm8`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_insertf128_pd)
 */
public fun mm256Insertf128Pd(a: M256d, b: M128d, imm1: Int): M256d {
    run {
        check(0 <= imm1 && imm1 < (1 shl 1)) { "IMM1 doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = mm256Castpd128Pd256(b); val _simdArg2 = SimdShuffleIdx(listOf(listOf(4, 5, 2, 3), listOf(0, 1, 4, 5))[imm1]); _simdArg0 }
}

/**
 * Copies `a` to result, then inserts 128 bits from `b` into result
 * at the location specified by `imm8`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_insertf128_si256)
 */
public fun mm256Insertf128Si256(a: M256i, b: M128i, imm1: Int): M256i {
    run {
        check(0 <= imm1 && imm1 < (1 shl 1)) { "IMM1 doesn't fit in 1 bits (unsigned)" }
    }
    return run {
        val dst: I64x4 = run { val _simdArg0 = a.asI64x4(); val _simdArg1 = mm256Castsi128Si256(b).asI64x4(); val _simdArg2 = SimdShuffleIdx(listOf(listOf(4, 5, 2, 3), listOf(0, 1, 4, 5))[imm1]); _simdArg0 }
        transmuteWithLayouts<I64x4, M256i>(dst, UnresolvedTypeLayout<I64x4>(), UnresolvedTypeLayout<M256i>())
    }
}

// This intrinsic has no corresponding instruction.

/**
 * Copies `a` to result, and inserts the 8-bit integer `i` into result
 * at the location specified by `index`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_insert_epi8)
 */
public fun mm256InsertEpi8(a: M256i, i: Byte, index: Int): M256i {
    run {
        check(0 <= index && index < (1 shl 5)) { "INDEX doesn't fit in 5 bits (unsigned)" }
    }
    return transmute(run { val _simdArg0 = a.asI8x32(); val _simdArg1 = index.toUInt(); val _simdArg2 = i; _simdArg0 })
}

// This intrinsic has no corresponding instruction.

/**
 * Copies `a` to result, and inserts the 16-bit integer `i` into result
 * at the location specified by `index`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_insert_epi16)
 */
public fun mm256InsertEpi16(a: M256i, i: Short, index: Int): M256i {
    run {
        check(0 <= index && index < (1 shl 4)) { "INDEX doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(run { val _simdArg0 = a.asI16x16(); val _simdArg1 = index.toUInt(); val _simdArg2 = i; _simdArg0 })
}

// This intrinsic has no corresponding instruction.

/**
 * Copies `a` to result, and inserts the 32-bit integer `i` into result
 * at the location specified by `index`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_insert_epi32)
 */
public fun mm256InsertEpi32(a: M256i, i: Int, index: Int): M256i {
    run {
        check(0 <= index && index < (1 shl 3)) { "INDEX doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(run { val _simdArg0 = a.asI32x8(); val _simdArg1 = index.toUInt(); val _simdArg2 = i; _simdArg0 })
}

/**
 * Loads 256-bits (composed of 4 packed double-precision (64-bit)
 * floating-point elements) from memory into result.
 * `memAddr` must be aligned on a 32-byte boundary or a
 * general-protection exception may be generated.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_load_pd)
 */
public fun mm256LoadPd(memAddr: ConstPtr): M256d = (UnresolvedTypeLayout<M256d>()).readNative(((ConstPtr(memAddr.rawAddress, memAddr.rawMeta))).rawAddress)

/**
 * Stores 256-bits (composed of 4 packed double-precision (64-bit)
 * floating-point elements) from `a` into memory.
 * `memAddr` must be aligned on a 32-byte boundary or a
 * general-protection exception may be generated.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_store_pd)
 */
public fun mm256StorePd(memAddr: MutPtr, a: M256d) {
    (UnresolvedTypeLayout<M256d>()).writeNative(((MutPtr(memAddr.rawAddress, memAddr.rawMeta))).rawAddress, a)
}

/**
 * Loads 256-bits (composed of 8 packed single-precision (32-bit)
 * floating-point elements) from memory into result.
 * `memAddr` must be aligned on a 32-byte boundary or a
 * general-protection exception may be generated.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_load_ps)
 */
public fun mm256LoadPs(memAddr: ConstPtr): M256 = (UnresolvedTypeLayout<M256>()).readNative(((ConstPtr(memAddr.rawAddress, memAddr.rawMeta))).rawAddress)

/**
 * Stores 256-bits (composed of 8 packed single-precision (32-bit)
 * floating-point elements) from `a` into memory.
 * `memAddr` must be aligned on a 32-byte boundary or a
 * general-protection exception may be generated.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_store_ps)
 */
public fun mm256StorePs(memAddr: MutPtr, a: M256) {
    (UnresolvedTypeLayout<M256>()).writeNative(((MutPtr(memAddr.rawAddress, memAddr.rawMeta))).rawAddress, a)
}

/**
 * Loads 256-bits (composed of 4 packed double-precision (64-bit)
 * floating-point elements) from memory into result.
 * `memAddr` does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_loadu_pd)
 */
public fun mm256LoaduPd(memAddr: ConstPtr): M256d {
    val dst = mm256UndefinedPd()
    copyNonoverlapping(ConstPtr(memAddr.rawAddress, memAddr.rawMeta), mutPtrFromRustCast(addrOfMut(dst)), sizeOf<M256d>(typeLayoutT = UnresolvedTypeLayout<M256d>()), typeLayoutT = PrimitiveLayouts.U8)
    return dst
}

/**
 * Stores 256-bits (composed of 4 packed double-precision (64-bit)
 * floating-point elements) from `a` into memory.
 * `memAddr` does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_storeu_pd)
 */
public fun mm256StoreuPd(memAddr: MutPtr, a: M256d) {
    (UnresolvedTypeLayout<M256d>()).writeNative((MutPtr((memAddr).rawAddress)).rawAddress, a)
}

/**
 * Loads 256-bits (composed of 8 packed single-precision (32-bit)
 * floating-point elements) from memory into result.
 * `memAddr` does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_loadu_ps)
 */
public fun mm256LoaduPs(memAddr: ConstPtr): M256 {
    val dst = mm256UndefinedPs()
    copyNonoverlapping(ConstPtr(memAddr.rawAddress, memAddr.rawMeta), mutPtrFromRustCast(addrOfMut(dst)), sizeOf<M256>(typeLayoutT = UnresolvedTypeLayout<M256>()), typeLayoutT = PrimitiveLayouts.U8)
    return dst
}

/**
 * Stores 256-bits (composed of 8 packed single-precision (32-bit)
 * floating-point elements) from `a` into memory.
 * `memAddr` does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_storeu_ps)
 */
public fun mm256StoreuPs(memAddr: MutPtr, a: M256) {
    (UnresolvedTypeLayout<M256>()).writeNative((MutPtr((memAddr).rawAddress)).rawAddress, a)
}

// Note vmovdqa expected

/**
 * Loads 256-bits of integer data from memory into result.
 * `memAddr` must be aligned on a 32-byte boundary or a
 * general-protection exception may be generated.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_load_si256)
 */
public fun mm256LoadSi256(memAddr: ConstPtr): M256i = (UnresolvedTypeLayout<M256i>()).readNative((memAddr).rawAddress)

// Note vmovdqa expected

/**
 * Stores 256-bits of integer data from `a` into memory.
 * `memAddr` must be aligned on a 32-byte boundary or a
 * general-protection exception may be generated.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_store_si256)
 */
public fun mm256StoreSi256(memAddr: MutPtr, a: M256i) {
    val memAddr1 = memAddr
    (UnresolvedTypeLayout<M256i>()).writeNative((memAddr1).rawAddress, a)
}

// Note vmovdqu expected

/**
 * Loads 256-bits of integer data from memory into result.
 * `memAddr` does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_loadu_si256)
 */
public fun mm256LoaduSi256(memAddr: ConstPtr): M256i {
    val dst = mm256UndefinedSi256()
    copyNonoverlapping(ConstPtr(memAddr.rawAddress, memAddr.rawMeta), mutPtrFromRustCast(addrOfMut(dst)), sizeOf<M256i>(typeLayoutT = UnresolvedTypeLayout<M256i>()), typeLayoutT = PrimitiveLayouts.U8)
    return dst
}

// Note vmovdqu expected

/**
 * Stores 256-bits of integer data from `a` into memory.
 * `memAddr` does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_storeu_si256)
 */
public fun mm256StoreuSi256(memAddr: MutPtr, a: M256i) {
    (UnresolvedTypeLayout<M256i>()).writeNative((memAddr).rawAddress, a)
}

/**
 * Loads packed double-precision (64-bit) floating-point elements from memory
 * into result using `mask` (elements are zeroed out when the high bit of the
 * corresponding element is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskload_pd)
 */
public fun mm256MaskloadPd(memAddr: ConstPtr, mask: M256i): M256d {
    val mask1 = run { val _simdArg0 = mask.asI64x4(); val _simdArg1 = i64x4.splat(63); _simdArg0 }
    return run { val _simdArg0 = mask1; val _simdArg1 = memAddr; val _simdArg2 = mm256SetzeroPd(); _simdArg2 }
}

/**
 * Stores packed double-precision (64-bit) floating-point elements from `a`
 * into memory using `mask`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskstore_pd)
 */
public fun mm256MaskstorePd(memAddr: MutPtr, mask: M256i, a: M256d) {
    val mask1 = run { val _simdArg0 = mask.asI64x4(); val _simdArg1 = i64x4.splat(63); _simdArg0 }
    run { val _simdArg0 = mask1; val _simdArg1 = memAddr; val _simdArg2 = a; Unit }
}

/**
 * Loads packed double-precision (64-bit) floating-point elements from memory
 * into result using `mask` (elements are zeroed out when the high bit of the
 * corresponding element is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskload_pd)
 */
public fun mmMaskloadPd(memAddr: ConstPtr, mask: M128i): M128d {
    val mask1 = run { val _simdArg0 = mask.asI64x2(); val _simdArg1 = i64x2.splat(63); _simdArg0 }
    return run { val _simdArg0 = mask1; val _simdArg1 = memAddr; val _simdArg2 = mmSetzeroPd(); _simdArg2 }
}

/**
 * Stores packed double-precision (64-bit) floating-point elements from `a`
 * into memory using `mask`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskstore_pd)
 */
public fun mmMaskstorePd(memAddr: MutPtr, mask: M128i, a: M128d) {
    val mask1 = run { val _simdArg0 = mask.asI64x2(); val _simdArg1 = i64x2.splat(63); _simdArg0 }
    run { val _simdArg0 = mask1; val _simdArg1 = memAddr; val _simdArg2 = a; Unit }
}

/**
 * Loads packed single-precision (32-bit) floating-point elements from memory
 * into result using `mask` (elements are zeroed out when the high bit of the
 * corresponding element is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskload_ps)
 */
public fun mm256MaskloadPs(memAddr: ConstPtr, mask: M256i): M256 {
    val mask1 = run { val _simdArg0 = mask.asI32x8(); val _simdArg1 = i32x8.splat(31); _simdArg0 }
    return run { val _simdArg0 = mask1; val _simdArg1 = memAddr; val _simdArg2 = mm256SetzeroPs(); _simdArg2 }
}

/**
 * Stores packed single-precision (32-bit) floating-point elements from `a`
 * into memory using `mask`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskstore_ps)
 */
public fun mm256MaskstorePs(memAddr: MutPtr, mask: M256i, a: M256) {
    val mask1 = run { val _simdArg0 = mask.asI32x8(); val _simdArg1 = i32x8.splat(31); _simdArg0 }
    run { val _simdArg0 = mask1; val _simdArg1 = memAddr; val _simdArg2 = a; Unit }
}

/**
 * Loads packed single-precision (32-bit) floating-point elements from memory
 * into result using `mask` (elements are zeroed out when the high bit of the
 * corresponding element is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskload_ps)
 */
public fun mmMaskloadPs(memAddr: ConstPtr, mask: M128i): M128 {
    val mask1 = run { val _simdArg0 = mask.asI32x4(); val _simdArg1 = i32x4.splat(31); _simdArg0 }
    return run { val _simdArg0 = mask1; val _simdArg1 = memAddr; val _simdArg2 = mmSetzeroPs(); _simdArg2 }
}

/**
 * Stores packed single-precision (32-bit) floating-point elements from `a`
 * into memory using `mask`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskstore_ps)
 */
public fun mmMaskstorePs(memAddr: MutPtr, mask: M128i, a: M128) {
    val mask1 = run { val _simdArg0 = mask.asI32x4(); val _simdArg1 = i32x4.splat(31); _simdArg0 }
    run { val _simdArg0 = mask1; val _simdArg1 = memAddr; val _simdArg2 = a; Unit }
}

/**
 * Duplicate odd-indexed single-precision (32-bit) floating-point elements
 * from `a`, and returns the results.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_movehdup_ps)
 */
public fun mm256MovehdupPs(a: M256): M256 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(1, 1, 3, 3, 5, 5, 7, 7)); _simdArg0 }

/**
 * Duplicate even-indexed single-precision (32-bit) floating-point elements
 * from `a`, and returns the results.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_moveldup_ps)
 */
public fun mm256MoveldupPs(a: M256): M256 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(0, 0, 2, 2, 4, 4, 6, 6)); _simdArg0 }

/**
 * Duplicate even-indexed double-precision (64-bit) floating-point elements
 * from `a`, and returns the results.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_movedup_pd)
 */
public fun mm256MovedupPd(a: M256d): M256d = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(0, 0, 2, 2)); _simdArg0 }

/**
 * Loads 256-bits of integer data from unaligned memory into result.
 * This intrinsic may perform better than `_mm256_loadu_si256` when the
 * data crosses a cache line boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_lddqu_si256)
 */
public fun mm256LddquSi256(memAddr: ConstPtr): M256i = transmuteWithLayouts<I8x32, M256i>(vlddqu(ConstPtr(memAddr.rawAddress, memAddr.rawMeta)), UnresolvedTypeLayout<I8x32>(), UnresolvedTypeLayout<M256i>())

/**
 * Moves integer data from a 256-bit integer vector to a 32-byte
 * aligned memory location. To minimize caching, the data is flagged as
 * non-temporal (unlikely to be used again soon)
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_stream_si256)
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
public fun mm256StreamSi256(memAddr: MutPtr, a: M256i) {
    // see #1541, we should use inline asm to be sure, because LangRef isn't clear enough
    Unit
}

/**
 * Moves double-precision values from a 256-bit vector of `[4 x double]`
 * to a 32-byte aligned memory location. To minimize caching, the data is
 * flagged as non-temporal (unlikely to be used again soon).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_stream_pd)
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
public fun mm256StreamPd(memAddr: MutPtr, a: M256d) {
    // see #1541, we should use inline asm to be sure, because LangRef isn't clear enough
    Unit
}

/**
 * Moves single-precision floating point values from a 256-bit vector
 * of `[8 x float]` to a 32-byte aligned memory location. To minimize
 * caching, the data is flagged as non-temporal (unlikely to be used again
 * soon).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_stream_ps)
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
public fun mm256StreamPs(memAddr: MutPtr, a: M256) {
    // see #1541, we should use inline asm to be sure, because LangRef isn't clear enough
    Unit
}

/**
 * Computes the approximate reciprocal of packed single-precision (32-bit)
 * floating-point elements in `a`, and returns the results. The maximum
 * relative error for this approximation is less than 1.5*2^-12.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_rcp_ps)
 */
public fun mm256RcpPs(a: M256): M256 = vrcpps(a)

/**
 * Computes the approximate reciprocal square root of packed single-precision
 * (32-bit) floating-point elements in `a`, and returns the results.
 * The maximum relative error for this approximation is less than 1.5*2^-12.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_rsqrt_ps)
 */
public fun mm256RsqrtPs(a: M256): M256 = vrsqrtps(a)

/**
 * Unpacks and interleave double-precision (64-bit) floating-point elements
 * from the high half of each 128-bit lane in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_unpackhi_pd)
 */
public fun mm256UnpackhiPd(a: M256d, b: M256d): M256d = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 5, 3, 7)); _simdArg0 }

/**
 * Unpacks and interleave single-precision (32-bit) floating-point elements
 * from the high half of each 128-bit lane in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_unpackhi_ps)
 */
public fun mm256UnpackhiPs(a: M256, b: M256): M256 = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(2, 10, 3, 11, 6, 14, 7, 15)); _simdArg0 }

/**
 * Unpacks and interleave double-precision (64-bit) floating-point elements
 * from the low half of each 128-bit lane in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_unpacklo_pd)
 */
public fun mm256UnpackloPd(a: M256d, b: M256d): M256d = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 4, 2, 6)); _simdArg0 }

/**
 * Unpacks and interleave single-precision (32-bit) floating-point elements
 * from the low half of each 128-bit lane in `a` and `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_unpacklo_ps)
 */
public fun mm256UnpackloPs(a: M256, b: M256): M256 = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 8, 1, 9, 4, 12, 5, 13)); _simdArg0 }

/**
 * Computes the bitwise AND of 256 bits (representing integer data) in `a` and
 * `b`, and set `ZF` to 1 if the result is zero, otherwise set `ZF` to 0.
 * Computes the bitwise NOT of `a` and then AND with `b`, and set `CF` to 1 if
 * the result is zero, otherwise set `CF` to 0. Return the `ZF` value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_testz_si256)
 */
public fun mm256TestzSi256(a: M256i, b: M256i): Int =
    run {
        val r = run { val _simdArg0 = a.asI64x4(); val _simdArg1 = b.asI64x4(); _simdArg0 }
        (if (0L == run { val _simdArg0 = r; _simdArg0 }) 1 else 0)
    }

/**
 * Computes the bitwise AND of 256 bits (representing integer data) in `a` and
 * `b`, and set `ZF` to 1 if the result is zero, otherwise set `ZF` to 0.
 * Computes the bitwise NOT of `a` and then AND with `b`, and set `CF` to 1 if
 * the result is zero, otherwise set `CF` to 0. Return the `CF` value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_testc_si256)
 */
public fun mm256TestcSi256(a: M256i, b: M256i): Int =
    run {
        val r = run { val _simdArg0 = run { val _simdArg0 = a.asI64x4(); val _simdArg1 = i64x4.splat(0.inv()); _simdArg0 }; val _simdArg1 = b.asI64x4(); _simdArg0 }
        (if (0L == run { val _simdArg0 = r; _simdArg0 }) 1 else 0)
    }

/**
 * Computes the bitwise AND of 256 bits (representing integer data) in `a` and
 * `b`, and set `ZF` to 1 if the result is zero, otherwise set `ZF` to 0.
 * Computes the bitwise NOT of `a` and then AND with `b`, and set `CF` to 1 if
 * the result is zero, otherwise set `CF` to 0. Return 1 if both the `ZF` and
 * `CF` values are zero, otherwise return 0.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_testnzc_si256)
 */
public fun mm256TestnzcSi256(a: M256i, b: M256i): Int = ptestnzc256(a.asI64x4(), b.asI64x4())

/**
 * Computes the bitwise AND of 256 bits (representing double-precision (64-bit)
 * floating-point elements) in `a` and `b`, producing an intermediate 256-bit
 * value, and set `ZF` to 1 if the sign bit of each 64-bit element in the
 * intermediate value is zero, otherwise set `ZF` to 0. Compute the bitwise
 * NOT of `a` and then AND with `b`, producing an intermediate value, and set
 * `CF` to 1 if the sign bit of each 64-bit element in the intermediate value
 * is zero, otherwise set `CF` to 0. Return the `ZF` value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_testz_pd)
 */
public fun mm256TestzPd(a: M256d, b: M256d): Int = vtestzpd256(a, b)

/**
 * Computes the bitwise AND of 256 bits (representing double-precision (64-bit)
 * floating-point elements) in `a` and `b`, producing an intermediate 256-bit
 * value, and set `ZF` to 1 if the sign bit of each 64-bit element in the
 * intermediate value is zero, otherwise set `ZF` to 0. Compute the bitwise
 * NOT of `a` and then AND with `b`, producing an intermediate value, and set
 * `CF` to 1 if the sign bit of each 64-bit element in the intermediate value
 * is zero, otherwise set `CF` to 0. Return the `CF` value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_testc_pd)
 */
public fun mm256TestcPd(a: M256d, b: M256d): Int = vtestcpd256(a, b)

/**
 * Computes the bitwise AND of 256 bits (representing double-precision (64-bit)
 * floating-point elements) in `a` and `b`, producing an intermediate 256-bit
 * value, and set `ZF` to 1 if the sign bit of each 64-bit element in the
 * intermediate value is zero, otherwise set `ZF` to 0. Compute the bitwise
 * NOT of `a` and then AND with `b`, producing an intermediate value, and set
 * `CF` to 1 if the sign bit of each 64-bit element in the intermediate value
 * is zero, otherwise set `CF` to 0. Return 1 if both the `ZF` and `CF` values
 * are zero, otherwise return 0.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_testnzc_pd)
 */
public fun mm256TestnzcPd(a: M256d, b: M256d): Int = vtestnzcpd256(a, b)

/**
 * Computes the bitwise AND of 128 bits (representing double-precision (64-bit)
 * floating-point elements) in `a` and `b`, producing an intermediate 128-bit
 * value, and set `ZF` to 1 if the sign bit of each 64-bit element in the
 * intermediate value is zero, otherwise set `ZF` to 0. Compute the bitwise
 * NOT of `a` and then AND with `b`, producing an intermediate value, and set
 * `CF` to 1 if the sign bit of each 64-bit element in the intermediate value
 * is zero, otherwise set `CF` to 0. Return the `ZF` value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_testz_pd)
 */
public fun mmTestzPd(a: M128d, b: M128d): Int =
    run {
        val r: I64x2 = run { val _simdArg0 = transmuteWithLayouts<M128d, I64x2>(mmAndPd(a, b), UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<I64x2>()); val _simdArg1 = i64x2.ZERO; _simdArg0 }
        (if (0L == run { val _simdArg0 = r; _simdArg0 }) 1 else 0)
    }

/**
 * Computes the bitwise AND of 128 bits (representing double-precision (64-bit)
 * floating-point elements) in `a` and `b`, producing an intermediate 128-bit
 * value, and set `ZF` to 1 if the sign bit of each 64-bit element in the
 * intermediate value is zero, otherwise set `ZF` to 0. Compute the bitwise
 * NOT of `a` and then AND with `b`, producing an intermediate value, and set
 * `CF` to 1 if the sign bit of each 64-bit element in the intermediate value
 * is zero, otherwise set `CF` to 0. Return the `CF` value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_testc_pd)
 */
public fun mmTestcPd(a: M128d, b: M128d): Int =
    run {
        val r: I64x2 = run { val _simdArg0 = transmuteWithLayouts<M128d, I64x2>(mmAndnotPd(a, b), UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<I64x2>()); val _simdArg1 = i64x2.ZERO; _simdArg0 }
        (if (0L == run { val _simdArg0 = r; _simdArg0 }) 1 else 0)
    }

/**
 * Computes the bitwise AND of 128 bits (representing double-precision (64-bit)
 * floating-point elements) in `a` and `b`, producing an intermediate 128-bit
 * value, and set `ZF` to 1 if the sign bit of each 64-bit element in the
 * intermediate value is zero, otherwise set `ZF` to 0. Compute the bitwise
 * NOT of `a` and then AND with `b`, producing an intermediate value, and set
 * `CF` to 1 if the sign bit of each 64-bit element in the intermediate value
 * is zero, otherwise set `CF` to 0. Return 1 if both the `ZF` and `CF` values
 * are zero, otherwise return 0.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_testnzc_pd)
 */
public fun mmTestnzcPd(a: M128d, b: M128d): Int = vtestnzcpd(a, b)

/**
 * Computes the bitwise AND of 256 bits (representing single-precision (32-bit)
 * floating-point elements) in `a` and `b`, producing an intermediate 256-bit
 * value, and set `ZF` to 1 if the sign bit of each 32-bit element in the
 * intermediate value is zero, otherwise set `ZF` to 0. Compute the bitwise
 * NOT of `a` and then AND with `b`, producing an intermediate value, and set
 * `CF` to 1 if the sign bit of each 32-bit element in the intermediate value
 * is zero, otherwise set `CF` to 0. Return the `ZF` value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_testz_ps)
 */
public fun mm256TestzPs(a: M256, b: M256): Int = vtestzps256(a, b)

/**
 * Computes the bitwise AND of 256 bits (representing single-precision (32-bit)
 * floating-point elements) in `a` and `b`, producing an intermediate 256-bit
 * value, and set `ZF` to 1 if the sign bit of each 32-bit element in the
 * intermediate value is zero, otherwise set `ZF` to 0. Compute the bitwise
 * NOT of `a` and then AND with `b`, producing an intermediate value, and set
 * `CF` to 1 if the sign bit of each 32-bit element in the intermediate value
 * is zero, otherwise set `CF` to 0. Return the `CF` value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_testc_ps)
 */
public fun mm256TestcPs(a: M256, b: M256): Int = vtestcps256(a, b)

/**
 * Computes the bitwise AND of 256 bits (representing single-precision (32-bit)
 * floating-point elements) in `a` and `b`, producing an intermediate 256-bit
 * value, and set `ZF` to 1 if the sign bit of each 32-bit element in the
 * intermediate value is zero, otherwise set `ZF` to 0. Compute the bitwise
 * NOT of `a` and then AND with `b`, producing an intermediate value, and set
 * `CF` to 1 if the sign bit of each 32-bit element in the intermediate value
 * is zero, otherwise set `CF` to 0. Return 1 if both the `ZF` and `CF` values
 * are zero, otherwise return 0.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_testnzc_ps)
 */
public fun mm256TestnzcPs(a: M256, b: M256): Int = vtestnzcps256(a, b)

/**
 * Computes the bitwise AND of 128 bits (representing single-precision (32-bit)
 * floating-point elements) in `a` and `b`, producing an intermediate 128-bit
 * value, and set `ZF` to 1 if the sign bit of each 32-bit element in the
 * intermediate value is zero, otherwise set `ZF` to 0. Compute the bitwise
 * NOT of `a` and then AND with `b`, producing an intermediate value, and set
 * `CF` to 1 if the sign bit of each 32-bit element in the intermediate value
 * is zero, otherwise set `CF` to 0. Return the `ZF` value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_testz_ps)
 */
public fun mmTestzPs(a: M128, b: M128): Int =
    run {
        val r: I32x4 = run { val _simdArg0 = transmuteWithLayouts<M128, I32x4>(mmAndPs(a, b), UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<I32x4>()); val _simdArg1 = i32x4.ZERO; _simdArg0 }
        (if (0 == run { val _simdArg0 = r; _simdArg0 }) 1 else 0)
    }

/**
 * Computes the bitwise AND of 128 bits (representing single-precision (32-bit)
 * floating-point elements) in `a` and `b`, producing an intermediate 128-bit
 * value, and set `ZF` to 1 if the sign bit of each 32-bit element in the
 * intermediate value is zero, otherwise set `ZF` to 0. Compute the bitwise
 * NOT of `a` and then AND with `b`, producing an intermediate value, and set
 * `CF` to 1 if the sign bit of each 32-bit element in the intermediate value
 * is zero, otherwise set `CF` to 0. Return the `CF` value.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_testc_ps)
 */
public fun mmTestcPs(a: M128, b: M128): Int =
    run {
        val r: I32x4 = run { val _simdArg0 = transmuteWithLayouts<M128, I32x4>(mmAndnotPs(a, b), UnresolvedTypeLayout<M128>(), UnresolvedTypeLayout<I32x4>()); val _simdArg1 = i32x4.ZERO; _simdArg0 }
        (if (0 == run { val _simdArg0 = r; _simdArg0 }) 1 else 0)
    }

/**
 * Computes the bitwise AND of 128 bits (representing single-precision (32-bit)
 * floating-point elements) in `a` and `b`, producing an intermediate 128-bit
 * value, and set `ZF` to 1 if the sign bit of each 32-bit element in the
 * intermediate value is zero, otherwise set `ZF` to 0. Compute the bitwise
 * NOT of `a` and then AND with `b`, producing an intermediate value, and set
 * `CF` to 1 if the sign bit of each 32-bit element in the intermediate value
 * is zero, otherwise set `CF` to 0. Return 1 if both the `ZF` and `CF` values
 * are zero, otherwise return 0.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_testnzc_ps)
 */
public fun mmTestnzcPs(a: M128, b: M128): Int = vtestnzcps(a, b)

/**
 * Sets each bit of the returned mask based on the most significant bit of the
 * corresponding packed double-precision (64-bit) floating-point element in
 * `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_movemask_pd)
 */
public fun mm256MovemaskPd(a: M256d): Int {
    // Propagate the highest bit to the rest, because simdBitmask
    // requires all-1 or all-0.
    return run {
        val mask: I64x4 = run { val _simdArg0 = transmuteWithLayouts<M256d, I64x4>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<I64x4>()); val _simdArg1 = i64x4.ZERO; _simdArg0 }
        (run { val _simdArg0 = mask; 0u }).toInt()
    }
}

/**
 * Sets each bit of the returned mask based on the most significant bit of the
 * corresponding packed single-precision (32-bit) floating-point element in
 * `a`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_movemask_ps)
 */
public fun mm256MovemaskPs(a: M256): Int {
    // Propagate the highest bit to the rest, because simdBitmask
    // requires all-1 or all-0.
    return run {
        val mask: I32x8 = run { val _simdArg0 = transmuteWithLayouts<M256, I32x8>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<I32x8>()); val _simdArg1 = i32x8.ZERO; _simdArg0 }
        (run { val _simdArg0 = mask; 0u }).toInt()
    }
}

/**
 * Returns vector of type __m256d with all elements set to zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_setzero_pd)
 */
public fun mm256SetzeroPd(): M256d = M256d()

/**
 * Returns vector of type __m256 with all elements set to zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_setzero_ps)
 */
public fun mm256SetzeroPs(): M256 = M256()

/**
 * Returns vector of type __m256i with all elements set to zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_setzero_si256)
 */
public fun mm256SetzeroSi256(): M256i = M256i()

// This intrinsic has no corresponding instruction.

/**
 * Sets packed double-precision (64-bit) floating-point elements in returned
 * vector with the supplied values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_set_pd)
 */
public fun mm256SetPd(a: Double, b: Double, c: Double, d: Double): M256d = mm256SetrPd(d, c, b, a)

// This intrinsic has no corresponding instruction.

/**
 * Sets packed single-precision (32-bit) floating-point elements in returned
 * vector with the supplied values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_set_ps)
 */
public fun mm256SetPs(a: Float, b: Float, c: Float, d: Float, e: Float, f: Float, g: Float, h: Float): M256 = mm256SetrPs(h, g, f, e, d, c, b, a)

// This intrinsic has no corresponding instruction.

/**
 * Sets packed 8-bit integers in returned vector with the supplied values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_set_epi8)
 */
public fun mm256SetEpi8(e00: Byte, e01: Byte, e02: Byte, e03: Byte, e04: Byte, e05: Byte, e06: Byte, e07: Byte, e08: Byte, e09: Byte, e10: Byte, e11: Byte, e12: Byte, e13: Byte, e14: Byte, e15: Byte, e16: Byte, e17: Byte, e18: Byte, e19: Byte, e20: Byte, e21: Byte, e22: Byte, e23: Byte, e24: Byte, e25: Byte, e26: Byte, e27: Byte, e28: Byte, e29: Byte, e30: Byte, e31: Byte): M256i = mm256SetrEpi8(e31, e30, e29, e28, e27, e26, e25, e24, e23, e22, e21, e20, e19, e18, e17, e16, e15, e14, e13, e12, e11, e10, e09, e08, e07, e06, e05, e04, e03, e02, e01, e00)

// This intrinsic has no corresponding instruction.

/**
 * Sets packed 16-bit integers in returned vector with the supplied values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_set_epi16)
 */
public fun mm256SetEpi16(e00: Short, e01: Short, e02: Short, e03: Short, e04: Short, e05: Short, e06: Short, e07: Short, e08: Short, e09: Short, e10: Short, e11: Short, e12: Short, e13: Short, e14: Short, e15: Short): M256i = mm256SetrEpi16(e15, e14, e13, e12, e11, e10, e09, e08, e07, e06, e05, e04, e03, e02, e01, e00)

// This intrinsic has no corresponding instruction.

/**
 * Sets packed 32-bit integers in returned vector with the supplied values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_set_epi32)
 */
public fun mm256SetEpi32(e0: Int, e1: Int, e2: Int, e3: Int, e4: Int, e5: Int, e6: Int, e7: Int): M256i = mm256SetrEpi32(e7, e6, e5, e4, e3, e2, e1, e0)

// This intrinsic has no corresponding instruction.

/**
 * Sets packed 64-bit integers in returned vector with the supplied values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_set_epi64x)
 */
public fun mm256SetEpi64x(a: Long, b: Long, c: Long, d: Long): M256i = mm256SetrEpi64x(d, c, b, a)

// This intrinsic has no corresponding instruction.

/**
 * Sets packed double-precision (64-bit) floating-point elements in returned
 * vector with the supplied values in reverse order.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_setr_pd)
 */
public fun mm256SetrPd(a: Double, b: Double, c: Double, d: Double): M256d = m256d(doubleArrayOf(a, b, c, d))

// This intrinsic has no corresponding instruction.

/**
 * Sets packed single-precision (32-bit) floating-point elements in returned
 * vector with the supplied values in reverse order.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_setr_ps)
 */
public fun mm256SetrPs(a: Float, b: Float, c: Float, d: Float, e: Float, f: Float, g: Float, h: Float): M256 = m256(floatArrayOf(a, b, c, d, e, f, g, h))

// This intrinsic has no corresponding instruction.

/**
 * Sets packed 8-bit integers in returned vector with the supplied values in
 * reverse order.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_setr_epi8)
 */
public fun mm256SetrEpi8(e00: Byte, e01: Byte, e02: Byte, e03: Byte, e04: Byte, e05: Byte, e06: Byte, e07: Byte, e08: Byte, e09: Byte, e10: Byte, e11: Byte, e12: Byte, e13: Byte, e14: Byte, e15: Byte, e16: Byte, e17: Byte, e18: Byte, e19: Byte, e20: Byte, e21: Byte, e22: Byte, e23: Byte, e24: Byte, e25: Byte, e26: Byte, e27: Byte, e28: Byte, e29: Byte, e30: Byte, e31: Byte): M256i = transmute(i8x32.new(e00, e01, e02, e03, e04, e05, e06, e07, e08, e09, e10, e11, e12, e13, e14, e15, e16, e17, e18, e19, e20, e21, e22, e23, e24, e25, e26, e27, e28, e29, e30, e31))

// This intrinsic has no corresponding instruction.

/**
 * Sets packed 16-bit integers in returned vector with the supplied values in
 * reverse order.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_setr_epi16)
 */
public fun mm256SetrEpi16(e00: Short, e01: Short, e02: Short, e03: Short, e04: Short, e05: Short, e06: Short, e07: Short, e08: Short, e09: Short, e10: Short, e11: Short, e12: Short, e13: Short, e14: Short, e15: Short): M256i = transmute(i16x16.new(e00, e01, e02, e03, e04, e05, e06, e07, e08, e09, e10, e11, e12, e13, e14, e15))

// This intrinsic has no corresponding instruction.

/**
 * Sets packed 32-bit integers in returned vector with the supplied values in
 * reverse order.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_setr_epi32)
 */
public fun mm256SetrEpi32(e0: Int, e1: Int, e2: Int, e3: Int, e4: Int, e5: Int, e6: Int, e7: Int): M256i = transmute(i32x8.new(e0, e1, e2, e3, e4, e5, e6, e7))

// This intrinsic has no corresponding instruction.

/**
 * Sets packed 64-bit integers in returned vector with the supplied values in
 * reverse order.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_setr_epi64x)
 */
public fun mm256SetrEpi64x(a: Long, b: Long, c: Long, d: Long): M256i = transmute(i64x4.new(a, b, c, d))

// This intrinsic has no corresponding instruction.

/**
 * Broadcasts double-precision (64-bit) floating-point value `a` to all
 * elements of returned vector.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_set1_pd)
 */
public fun mm256Set1Pd(a: Double): M256d = f64x4.splat(a).asM256d()

// This intrinsic has no corresponding instruction.

/**
 * Broadcasts single-precision (32-bit) floating-point value `a` to all
 * elements of returned vector.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_set1_ps)
 */
public fun mm256Set1Ps(a: Float): M256 = f32x8.splat(a).asM256()

// This intrinsic has no corresponding instruction.

/**
 * Broadcasts 8-bit integer `a` to all elements of returned vector.
 * This intrinsic may generate the `vpbroadcastb`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_set1_epi8)
 */
public fun mm256Set1Epi8(a: Byte): M256i = i8x32.splat(a).asM256i()

// @cfgAttr(test, assertInstr(vpshufb))
// This intrinsic has no corresponding instruction.

/**
 * Broadcasts 16-bit integer `a` to all elements of returned vector.
 * This intrinsic may generate the `vpbroadcastw`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_set1_epi16)
 */
public fun mm256Set1Epi16(a: Short): M256i = i16x16.splat(a).asM256i()

// This intrinsic has no corresponding instruction.

/**
 * Broadcasts 32-bit integer `a` to all elements of returned vector.
 * This intrinsic may generate the `vpbroadcastd`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_set1_epi32)
 */
public fun mm256Set1Epi32(a: Int): M256i = i32x8.splat(a).asM256i()

// This intrinsic has no corresponding instruction.

/**
 * Broadcasts 64-bit integer `a` to all elements of returned vector.
 * This intrinsic may generate the `vpbroadcastq`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_set1_epi64x)
 */
public fun mm256Set1Epi64x(a: Long): M256i = i64x4.splat(a).asM256i()

// This intrinsic is only used for compilation and does not generate any
// instructions, thus it has zero latency.

/**
 * Cast vector of type __m256d to type __m256.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_castpd_ps)
 */
public fun mm256CastpdPs(a: M256d): M256 = transmuteWithLayouts<M256d, M256>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256>())

// This intrinsic is only used for compilation and does not generate any
// instructions, thus it has zero latency.

/**
 * Cast vector of type __m256 to type __m256d.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_castps_pd)
 */
public fun mm256CastpsPd(a: M256): M256d = transmuteWithLayouts<M256, M256d>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256d>())

// This intrinsic is only used for compilation and does not generate any
// instructions, thus it has zero latency.

/**
 * Casts vector of type __m256 to type __m256i.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_castps_si256)
 */
public fun mm256CastpsSi256(a: M256): M256i = transmuteWithLayouts<M256, M256i>(a, UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())

// This intrinsic is only used for compilation and does not generate any
// instructions, thus it has zero latency.

/**
 * Casts vector of type __m256i to type __m256.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_castsi256_ps)
 */
public fun mm256Castsi256Ps(a: M256i): M256 = transmuteWithLayouts<M256i, M256>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<M256>())

// This intrinsic is only used for compilation and does not generate any
// instructions, thus it has zero latency.

/**
 * Casts vector of type __m256d to type __m256i.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_castpd_si256)
 */
public fun mm256CastpdSi256(a: M256d): M256i = transmuteWithLayouts<M256d, M256i>(a, UnresolvedTypeLayout<M256d>(), UnresolvedTypeLayout<M256i>())

// This intrinsic is only used for compilation and does not generate any
// instructions, thus it has zero latency.

/**
 * Casts vector of type __m256i to type __m256d.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_castsi256_pd)
 */
public fun mm256Castsi256Pd(a: M256i): M256d = transmuteWithLayouts<M256i, M256d>(a, UnresolvedTypeLayout<M256i>(), UnresolvedTypeLayout<M256d>())

// This intrinsic is only used for compilation and does not generate any
// instructions, thus it has zero latency.

/**
 * Casts vector of type __m256 to type __m128.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_castps256_ps128)
 */
public fun mm256Castps256Ps128(a: M256): M128 = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3)); _simdArg0 }

// This intrinsic is only used for compilation and does not generate any
// instructions, thus it has zero latency.

/**
 * Casts vector of type __m256d to type __m128d.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_castpd256_pd128)
 */
public fun mm256Castpd256Pd128(a: M256d): M128d = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(0, 1)); _simdArg0 }

// This intrinsic is only used for compilation and does not generate any
// instructions, thus it has zero latency.

/**
 * Casts vector of type __m256i to type __m128i.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_castsi256_si128)
 */
public fun mm256Castsi256Si128(a: M256i): M128i =
    run {
        val a1 = a.asI64x4()
        val dst: I64x2 = run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1)); _simdArg0 }
        transmuteWithLayouts<I64x2, M128i>(dst, UnresolvedTypeLayout<I64x2>(), UnresolvedTypeLayout<M128i>())
    }

// This intrinsic is only used for compilation and does not generate any
// instructions, thus it has zero latency.

/**
 * Casts vector of type __m128 to type __m256;
 * the upper 128 bits of the result are indeterminate.
 *
 * In the Intel documentation, the upper bits are declared to be "undefined".
 * This is not equivalent to [mem.MaybeUninit]; instead, these bits are non-deterministically
 * set to some valid value. In practice, this is typically equivalent to [mem.zeroed].
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_castps128_ps256)
 */
public fun mm256Castps128Ps256(a: M128): M256 = run { val _simdArg0 = a; val _simdArg1 = mmUndefinedPs(); val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 4, 4, 4)); _simdArg0 }

// This intrinsic is only used for compilation and does not generate any
// instructions, thus it has zero latency.

/**
 * Casts vector of type __m128d to type __m256d;
 * the upper 128 bits of the result are indeterminate.
 *
 * In the Intel documentation, the upper bits are declared to be "undefined".
 * This is not equivalent to [mem.MaybeUninit]; instead, these bits are non-deterministically
 * set to some valid value. In practice, this is typically equivalent to [mem.zeroed].
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_castpd128_pd256)
 */
public fun mm256Castpd128Pd256(a: M128d): M256d = run { val _simdArg0 = a; val _simdArg1 = mmUndefinedPd(); val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 2)); _simdArg0 }

// This intrinsic is only used for compilation and does not generate any
// instructions, thus it has zero latency.

/**
 * Casts vector of type __m128i to type __m256i;
 * the upper 128 bits of the result are indeterminate.
 *
 * In the Intel documentation, the upper bits are declared to be "undefined".
 * This is not equivalent to [mem.MaybeUninit]; instead, these bits are non-deterministically
 * set to some valid value. In practice, this is typically equivalent to [mem.zeroed].
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_castsi128_si256)
 */
public fun mm256Castsi128Si256(a: M128i): M256i =
    run {
        val a1 = a.asI64x2()
        val undefined = i64x2.ZERO
        val dst: I64x4 = run { val _simdArg0 = a1; val _simdArg1 = undefined; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 2)); _simdArg0 }
        transmuteWithLayouts<I64x4, M256i>(dst, UnresolvedTypeLayout<I64x4>(), UnresolvedTypeLayout<M256i>())
    }

// This intrinsic is only used for compilation and does not generate any
// instructions, thus it has zero latency.

/**
 * Constructs a 256-bit floating-point vector of `[8 x float]` from a
 * 128-bit floating-point vector of `[4 x float]`. The lower 128 bits contain
 * the value of the source vector. The upper 128 bits are set to zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_zextps128_ps256)
 */
public fun mm256Zextps128Ps256(a: M128): M256 = run { val _simdArg0 = a; val _simdArg1 = mmSetzeroPs(); val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7)); _simdArg0 }

// This intrinsic is only used for compilation and does not generate any
// instructions, thus it has zero latency.

/**
 * Constructs a 256-bit integer vector from a 128-bit integer vector.
 * The lower 128 bits contain the value of the source vector. The upper
 * 128 bits are set to zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_zextsi128_si256)
 */
public fun mm256Zextsi128Si256(a: M128i): M256i =
    run {
        val b = i64x2.ZERO
        val dst: I64x4 = run { val _simdArg0 = a.asI64x2(); val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3)); _simdArg0 }
        transmuteWithLayouts<I64x4, M256i>(dst, UnresolvedTypeLayout<I64x4>(), UnresolvedTypeLayout<M256i>())
    }

// This intrinsic is only used for compilation and does not generate any
// instructions, thus it has zero latency.

/**
 * Constructs a 256-bit floating-point vector of `[4 x double]` from a
 * 128-bit floating-point vector of `[2 x double]`. The lower 128 bits
 * contain the value of the source vector. The upper 128 bits are set
 * to zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_zextpd128_pd256)
 */
public fun mm256Zextpd128Pd256(a: M128d): M256d = run { val _simdArg0 = a; val _simdArg1 = mmSetzeroPd(); val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3)); _simdArg0 }

// This intrinsic has no corresponding instruction.

/**
 * Returns vector of type `__m256` with indeterminate elements.
 * Despite using the word "undefined" (following Intel's naming scheme), this non-deterministically
 * picks some valid value and is not equivalent to [mem.MaybeUninit].
 * In practice, this is typically equivalent to [mem.zeroed].
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_undefined_ps)
 */
public fun mm256UndefinedPs(): M256 = M256()

// This intrinsic has no corresponding instruction.

/**
 * Returns vector of type `__m256d` with indeterminate elements.
 * Despite using the word "undefined" (following Intel's naming scheme), this non-deterministically
 * picks some valid value and is not equivalent to [mem.MaybeUninit].
 * In practice, this is typically equivalent to [mem.zeroed].
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_undefined_pd)
 */
public fun mm256UndefinedPd(): M256d = M256d()

// This intrinsic has no corresponding instruction.

/**
 * Returns vector of type __m256i with with indeterminate elements.
 * Despite using the word "undefined" (following Intel's naming scheme), this non-deterministically
 * picks some valid value and is not equivalent to [mem.MaybeUninit].
 * In practice, this is typically equivalent to [mem.zeroed].
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_undefined_si256)
 */
public fun mm256UndefinedSi256(): M256i = M256i()

/**
 * Sets packed __m256 returned vector with the supplied values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_set_m128)
 */
public fun mm256SetM128(hi: M128, lo: M128): M256 = run { val _simdArg0 = lo; val _simdArg1 = hi; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7)); _simdArg0 }

/**
 * Sets packed __m256d returned vector with the supplied values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_set_m128d)
 */
public fun mm256SetM128d(hi: M128d, lo: M128d): M256d =
    run {
        val hi1: M128 = transmuteWithLayouts<M128d, M128>(hi, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128>())
        val lo1: M128 = transmuteWithLayouts<M128d, M128>(lo, UnresolvedTypeLayout<M128d>(), UnresolvedTypeLayout<M128>())
        transmuteWithLayouts<M256, M256d>(mm256SetM128(hi1, lo1), UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256d>())
    }

/**
 * Sets packed __m256i returned vector with the supplied values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_set_m128i)
 */
public fun mm256SetM128i(hi: M128i, lo: M128i): M256i =
    run {
        val hi1: M128 = transmuteWithLayouts<M128i, M128>(hi, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M128>())
        val lo1: M128 = transmuteWithLayouts<M128i, M128>(lo, UnresolvedTypeLayout<M128i>(), UnresolvedTypeLayout<M128>())
        transmuteWithLayouts<M256, M256i>(mm256SetM128(hi1, lo1), UnresolvedTypeLayout<M256>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Sets packed __m256 returned vector with the supplied values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_setr_m128)
 */
public fun mm256SetrM128(lo: M128, hi: M128): M256 = mm256SetM128(hi, lo)

/**
 * Sets packed __m256d returned vector with the supplied values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_setr_m128d)
 */
public fun mm256SetrM128d(lo: M128d, hi: M128d): M256d = mm256SetM128d(hi, lo)

/**
 * Sets packed __m256i returned vector with the supplied values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_setr_m128i)
 */
public fun mm256SetrM128i(lo: M128i, hi: M128i): M256i = mm256SetM128i(hi, lo)

// This intrinsic has no corresponding instruction.

/**
 * Loads two 128-bit values (composed of 4 packed single-precision (32-bit)
 * floating-point elements) from memory, and combine them into a 256-bit
 * value.
 * `hiaddr` and `loaddr` do not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_loadu2_m128)
 */
public fun mm256Loadu2M128(hiaddr: ConstPtr, loaddr: ConstPtr): M256 {
    val a = mm256Castps128Ps256(mmLoaduPs(loaddr))
    return mm256Insertf128Ps(a, mmLoaduPs(hiaddr), 1)
}

// This intrinsic has no corresponding instruction.

/**
 * Loads two 128-bit values (composed of 2 packed double-precision (64-bit)
 * floating-point elements) from memory, and combine them into a 256-bit
 * value.
 * `hiaddr` and `loaddr` do not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_loadu2_m128d)
 */
public fun mm256Loadu2M128d(hiaddr: ConstPtr, loaddr: ConstPtr): M256d {
    val a = mm256Castpd128Pd256(mmLoaduPd(loaddr))
    return mm256Insertf128Pd(a, mmLoaduPd(hiaddr), 1)
}

// This intrinsic has no corresponding instruction.

/**
 * Loads two 128-bit values (composed of integer data) from memory, and combine
 * them into a 256-bit value.
 * `hiaddr` and `loaddr` do not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_loadu2_m128i)
 */
public fun mm256Loadu2M128i(hiaddr: ConstPtr, loaddr: ConstPtr): M256i {
    val a = mm256Castsi128Si256(mmLoaduSi128(loaddr))
    return mm256Insertf128Si256(a, mmLoaduSi128(hiaddr), 1)
}

// This intrinsic has no corresponding instruction.

/**
 * Stores the high and low 128-bit halves (each composed of 4 packed
 * single-precision (32-bit) floating-point elements) from `a` into memory two
 * different 128-bit locations.
 * `hiaddr` and `loaddr` do not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_storeu2_m128)
 */
public fun mm256Storeu2M128(hiaddr: MutPtr, loaddr: MutPtr, a: M256) {
    val lo = mm256Castps256Ps128(a)
    mmStoreuPs(loaddr, lo)
    val hi = mm256Extractf128Ps(a, 1)
    mmStoreuPs(hiaddr, hi)
}

// This intrinsic has no corresponding instruction.

/**
 * Stores the high and low 128-bit halves (each composed of 2 packed
 * double-precision (64-bit) floating-point elements) from `a` into memory two
 * different 128-bit locations.
 * `hiaddr` and `loaddr` do not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_storeu2_m128d)
 */
public fun mm256Storeu2M128d(hiaddr: MutPtr, loaddr: MutPtr, a: M256d) {
    val lo = mm256Castpd256Pd128(a)
    mmStoreuPd(loaddr, lo)
    val hi = mm256Extractf128Pd(a, 1)
    mmStoreuPd(hiaddr, hi)
}

// This intrinsic has no corresponding instruction.

/**
 * Stores the high and low 128-bit halves (each composed of integer data) from
 * `a` into memory two different 128-bit locations.
 * `hiaddr` and `loaddr` do not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_storeu2_m128i)
 */
public fun mm256Storeu2M128i(hiaddr: MutPtr, loaddr: MutPtr, a: M256i) {
    val lo = mm256Castsi256Si128(a)
    mmStoreuSi128(loaddr, lo)
    val hi = mm256Extractf128Si256(a, 1)
    mmStoreuSi128(hiaddr, hi)
}

// @cfgAttr(test, assertInstr(movss)) Note

/**
 * Returns the first element of the input vector of `[8 x float]`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtss_f32)
 */
public fun mm256CvtssF32(a: M256): Float = run { val _simdArg0 = a; val _simdArg1 = 0.0f; _simdArg0 }

// LLVM intrinsics used in the above functions
public expect fun roundpd256(a: M256d, b: Int): M256d

public expect fun roundps256(a: M256, b: Int): M256

public expect fun vdpps(a: M256, b: M256, imm8: Byte): M256

public expect fun vcmppd(a: M128d, b: M128d, imm8: Byte): M128d

public expect fun vcmppd256(a: M256d, b: M256d, imm8: UByte): M256d

public expect fun vcmpps(a: M128, b: M128, imm8: Byte): M128

public expect fun vcmpps256(a: M256, b: M256, imm8: UByte): M256

public expect fun vcmpsd(a: M128d, b: M128d, imm8: Byte): M128d

public expect fun vcmpss(a: M128, b: M128, imm8: Byte): M128

public expect fun vcvtps2dq(a: M256): I32x8

public expect fun vcvttpd2dq(a: M256d): I32x4

public expect fun vcvtpd2dq(a: M256d): I32x4

public expect fun vcvttps2dq(a: M256): I32x8

public expect fun vzeroall()

public expect fun vzeroupper()

public expect fun vpermilps256(a: M256, b: I32x8): M256

public expect fun vpermilps(a: M128, b: I32x4): M128

public expect fun vpermilpd256(a: M256d, b: I64x4): M256d

public expect fun vpermilpd(a: M128d, b: I64x2): M128d

public expect fun vlddqu(memAddr: ConstPtr): I8x32

public expect fun vrcpps(a: M256): M256

public expect fun vrsqrtps(a: M256): M256

public expect fun ptestnzc256(a: I64x4, b: I64x4): Int

public expect fun vtestzpd256(a: M256d, b: M256d): Int

public expect fun vtestcpd256(a: M256d, b: M256d): Int

public expect fun vtestnzcpd256(a: M256d, b: M256d): Int

public expect fun vtestnzcpd(a: M128d, b: M128d): Int

public expect fun vtestzps256(a: M256, b: M256): Int

public expect fun vtestcps256(a: M256, b: M256): Int

public expect fun vtestnzcps256(a: M256, b: M256): Int

public expect fun vtestnzcps(a: M128, b: M128): Int

public expect fun vminps(a: M256, b: M256): M256

public expect fun vmaxps(a: M256, b: M256): M256

public expect fun vminpd(a: M256d, b: M256d): M256d

public expect fun vmaxpd(a: M256d, b: M256d): M256d
