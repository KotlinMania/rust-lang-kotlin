// port-lint: source x86/sse41.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.intrinsics.kmAllocZeroed
import io.github.kotlinmania.rustlang.core.intrinsics.kmDealloc
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.corearch.SimdShuffleIdx
import io.github.kotlinmania.rustlang.corearch.simd.F32x4
import io.github.kotlinmania.rustlang.corearch.simd.F64x2
import io.github.kotlinmania.rustlang.corearch.simd.I16x2
import io.github.kotlinmania.rustlang.corearch.simd.I16x4
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x2
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.I8x2
import io.github.kotlinmania.rustlang.corearch.simd.I8x4
import io.github.kotlinmania.rustlang.corearch.simd.I8x8
import io.github.kotlinmania.rustlang.corearch.simd.U16x2
import io.github.kotlinmania.rustlang.corearch.simd.U16x4
import io.github.kotlinmania.rustlang.corearch.simd.U16x8
import io.github.kotlinmania.rustlang.corearch.simd.U32x2
import io.github.kotlinmania.rustlang.corearch.simd.U8x16
import io.github.kotlinmania.rustlang.corearch.simd.U8x2
import io.github.kotlinmania.rustlang.corearch.simd.U8x4
import io.github.kotlinmania.rustlang.corearch.simd.U8x8
import io.github.kotlinmania.rustlang.corearch.simd.simdImax
import io.github.kotlinmania.rustlang.corearch.simd.simdImin

// Streaming SIMD Extensions 4.1 (SSE4.1)

// SSE4 rounding constants

/**
 * round to nearest
 */
public val MM_FROUND_TO_NEAREST_INT: Int = 0x00

/**
 * round down
 */
public val MM_FROUND_TO_NEG_INF: Int = 0x01

/**
 * round up
 */
public val MM_FROUND_TO_POS_INF: Int = 0x02

/**
 * truncate
 */
public val MM_FROUND_TO_ZERO: Int = 0x03

/**
 * use MXCSR.RC; see `vendor._MM_SET_ROUNDING_MODE`
 */
public val MM_FROUND_CUR_DIRECTION: Int = 0x04

/**
 * do not suppress exceptions
 */
public val MM_FROUND_RAISE_EXC: Int = 0x00

/**
 * suppress exceptions
 */
public val MM_FROUND_NO_EXC: Int = 0x08

/**
 * round to nearest and do not suppress exceptions
 */
public val MM_FROUND_NINT: Int = 0x00

/**
 * round down and do not suppress exceptions
 */
public val MM_FROUND_FLOOR: Int = MM_FROUND_RAISE_EXC or MM_FROUND_TO_NEG_INF

/**
 * round up and do not suppress exceptions
 */
public val MM_FROUND_CEIL: Int = MM_FROUND_RAISE_EXC or MM_FROUND_TO_POS_INF

/**
 * truncate and do not suppress exceptions
 */
public val MM_FROUND_TRUNC: Int = MM_FROUND_RAISE_EXC or MM_FROUND_TO_ZERO

/**
 * use MXCSR.RC and do not suppress exceptions; see
 * `vendor._MM_SET_ROUNDING_MODE`
 */
public val MM_FROUND_RINT: Int = MM_FROUND_RAISE_EXC or MM_FROUND_CUR_DIRECTION

/**
 * use MXCSR.RC and suppress exceptions; see `vendor._MM_SET_ROUNDING_MODE`
 */
public val MM_FROUND_NEARBYINT: Int = MM_FROUND_NO_EXC or MM_FROUND_CUR_DIRECTION

/**
 * Blend packed 8-bit integers from `a` and `b` using `mask`
 *
 * The high bit of each corresponding mask byte determines the selection.
 * If the high bit is set, the element of `b` is selected.
 * Otherwise, the element of `a` is selected.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_blendv_epi8)
 */
public fun mmBlendvEpi8(a: M128i, b: M128i, mask: M128i): M128i =
    run {
        val mask1: I8x16 = run { val _simdArg0 = mask.asI8x16(); val _simdArg1 = i8x16.ZERO; _simdArg0 }
        transmute(run { val _simdArg0 = mask1; val _simdArg1 = b.asI8x16(); val _simdArg2 = a.asI8x16(); _simdArg1 })
    }

/**
 * Blend packed 16-bit integers from `a` and `b` using the mask `IMM8`.
 *
 * The mask bits determine the selection. A clear bit selects the
 * corresponding element of `a`, and a set bit the corresponding
 * element of `b`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_blend_epi16)
 */
public fun mmBlendEpi16(a: M128i, b: M128i, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return transmute<I16x8>(run { val _simdArg0 = a.asI16x8(); val _simdArg1 = b.asI16x8(); val _simdArg2 = SimdShuffleIdx(listOf(listOf(0, 8)[imm8 and 1], listOf(1, 9)[(imm8 shr 1) and 1], listOf(2, 10)[(imm8 shr 2) and 1], listOf(3, 11)[(imm8 shr 3) and 1], listOf(4, 12)[(imm8 shr 4) and 1], listOf(5, 13)[(imm8 shr 5) and 1], listOf(6, 14)[(imm8 shr 6) and 1], listOf(7, 15)[(imm8 shr 7) and 1])); _simdArg0 })
}

/**
 * Blend packed double-precision (64-bit) floating-point elements from `a`
 * and `b` using `mask`
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_blendv_pd)
 */
public fun mmBlendvPd(a: M128d, b: M128d, mask: M128d): M128d =
    run {
        val mask1: I64x2 = run { val _simdArg0 = transmute<I64x2>(mask); val _simdArg1 = i64x2.ZERO; _simdArg0 }
        transmute(run { val _simdArg0 = mask1; val _simdArg1 = b.asF64x2(); val _simdArg2 = a.asF64x2(); _simdArg1 })
    }

/**
 * Blend packed single-precision (32-bit) floating-point elements from `a`
 * and `b` using `mask`
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_blendv_ps)
 */
public fun mmBlendvPs(a: M128, b: M128, mask: M128): M128 =
    run {
        val mask1: I32x4 = run { val _simdArg0 = transmute<I32x4>(mask); val _simdArg1 = i32x4.ZERO; _simdArg0 }
        transmute(run { val _simdArg0 = mask1; val _simdArg1 = b.asF32x4(); val _simdArg2 = a.asF32x4(); _simdArg1 })
    }

// Note: LLVM7 prefers the single-precision floating-point domain when possible
// see https://bugs.llvm.org/showBug.cgi?id=38195
// @cfgAttr(test, assertInstr(blendpd, IMM2 = 0b10))

/**
 * Blend packed double-precision (64-bit) floating-point elements from `a`
 * and `b` using control mask `IMM2`
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_blend_pd)
 */
public fun mmBlendPd(a: M128d, b: M128d, imm2: Int): M128d {
    run {
        check(0 <= imm2 && imm2 < (1 shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return transmute<F64x2>(run { val _simdArg0 = a.asF64x2(); val _simdArg1 = b.asF64x2(); val _simdArg2 = SimdShuffleIdx(listOf(listOf(0, 2)[imm2 and 1], listOf(1, 3)[(imm2 shr 1) and 1])); _simdArg0 })
}

/**
 * Blend packed single-precision (32-bit) floating-point elements from `a`
 * and `b` using mask `IMM4`
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_blend_ps)
 */
public fun mmBlendPs(a: M128, b: M128, imm4: Int): M128 {
    run {
        check(0 <= imm4 && imm4 < (1 shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute<F32x4>(run { val _simdArg0 = a.asF32x4(); val _simdArg1 = b.asF32x4(); val _simdArg2 = SimdShuffleIdx(listOf(listOf(0, 4)[imm4 and 1], listOf(1, 5)[(imm4 shr 1) and 1], listOf(2, 6)[(imm4 shr 2) and 1], listOf(3, 7)[(imm4 shr 3) and 1])); _simdArg0 })
}

/**
 * Extracts a single-precision (32-bit) floating-point element from `a`,
 * selected with `IMM8`. The returned `Int` stores the float's bit-pattern,
 * and may be converted back to a floating point number via casting.
 *
 * # Example
 * ```kotlin
 * var floatStore = mutableListOf(1.0, 1.0, 2.0, 3.0)
 * val simdFloats = mmSetPs(2.5, 5.0, 7.5, 10.0)
 * val x: Int = mmExtractPs(simdFloats, 2)
 * floatStore.add(Float.fromBits((x.toUInt()).toInt()))
 * check(floatStore == mutableListOf(1.0, 1.0, 2.0, 3.0, 5.0))
 * ```
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_extract_ps)
 */
public fun mmExtractPs(a: M128, imm8: Int): Int {
    run {
        check(0 <= imm8 && imm8 < (1 shl 2)) { "IMM8 doesn't fit in 2 bits (unsigned)" }
    }
    return (run { val _simdArg0 = a; val _simdArg1 = imm8.toUInt(); 0.0f }.toBits()).toInt()
}

/**
 * Extracts an 8-bit integer from `a`, selected with `IMM8`. Returns a 32-bit
 * integer containing the zero-extended integer data.
 *
 * See [LLVM commit D20468](https://reviews.llvm.org/D20468).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_extract_epi8)
 */
public fun mmExtractEpi8(a: M128i, imm8: Int): Int {
    run {
        check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
    }
    return (run { val _simdArg0 = a.asU8x16(); val _simdArg1 = imm8.toUInt(); 0u }).toInt()
}

/**
 * Extracts an 32-bit integer from `a` selected with `IMM8`
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_extract_epi32)
 */
public fun mmExtractEpi32(a: M128i, imm8: Int): Int {
    run {
        check(0 <= imm8 && imm8 < (1 shl 2)) { "IMM8 doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a.asI32x4(); val _simdArg1 = imm8.toUInt(); 0 }
}

/**
 * Select a single value in `b` to store at some position in `a`,
 * Then zero elements according to `IMM8`.
 *
 * `IMM8` specifies which bits from operand `b` will be copied, which bits in
 * the result they will be copied to, and which bits in the result will be
 * cleared. The following assignments are made:
 *
 * * Bits `[7: 6]` specify the bits to copy from operand `b`:
 *     - `00`: Selects bits `[31: 0]` from operand `b`.
 *     - `01`: Selects bits `[63: 32]` from operand `b`.
 *     - `10`: Selects bits `[95: 64]` from operand `b`.
 *     - `11`: Selects bits `[127: 96]` from operand `b`.
 *
 * * Bits `[5: 4]` specify the bits in the result to which the selected bits
 *   from operand `b` are copied:
 *     - `00`: Copies the selected bits from `b` to result bits `[31: 0]`.
 *     - `01`: Copies the selected bits from `b` to result bits `[63: 32]`.
 *     - `10`: Copies the selected bits from `b` to result bits `[95: 64]`.
 *     - `11`: Copies the selected bits from `b` to result bits `[127: 96]`.
 *
 * * Bits `[3: 0]`: If any of these bits are set, the corresponding result
 *   element is cleared.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_insert_ps)
 */
public fun mmInsertPs(a: M128, b: M128, imm8: Int): M128 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return insertps(a, b, imm8.toUByte())
}

/**
 * Returns a copy of `a` with the 8-bit integer from `i` inserted at a
 * location specified by `IMM8`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_insert_epi8)
 */
public fun mmInsertEpi8(a: M128i, i: Int, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 4)) { "IMM8 doesn't fit in 4 bits (unsigned)" }
    }
    return transmute(run { val _simdArg0 = a.asI8x16(); val _simdArg1 = imm8.toUInt(); val _simdArg2 = i.toByte(); _simdArg0 })
}

/**
 * Returns a copy of `a` with the 32-bit integer from `i` inserted at a
 * location specified by `IMM8`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_insert_epi32)
 */
public fun mmInsertEpi32(a: M128i, i: Int, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 2)) { "IMM8 doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(run { val _simdArg0 = a.asI32x4(); val _simdArg1 = imm8.toUInt(); val _simdArg2 = i; _simdArg0 })
}

/**
 * Compares packed 8-bit integers in `a` and `b` and returns packed maximum
 * values in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_max_epi8)
 */
public fun mmMaxEpi8(a: M128i, b: M128i): M128i = simdImax(a.asI8x16(), b.asI8x16()).asM128i()

/**
 * Compares packed unsigned 16-bit integers in `a` and `b`, and returns packed
 * maximum.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_max_epu16)
 */
public fun mmMaxEpu16(a: M128i, b: M128i): M128i = simdImax(a.asU16x8(), b.asU16x8()).asM128i()

/**
 * Compares packed 32-bit integers in `a` and `b`, and returns packed maximum
 * values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_max_epi32)
 */
public fun mmMaxEpi32(a: M128i, b: M128i): M128i = simdImax(a.asI32x4(), b.asI32x4()).asM128i()

/**
 * Compares packed unsigned 32-bit integers in `a` and `b`, and returns packed
 * maximum values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_max_epu32)
 */
public fun mmMaxEpu32(a: M128i, b: M128i): M128i = simdImax(a.asU32x4(), b.asU32x4()).asM128i()

/**
 * Compares packed 8-bit integers in `a` and `b` and returns packed minimum
 * values in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_min_epi8)
 */
public fun mmMinEpi8(a: M128i, b: M128i): M128i = simdImin(a.asI8x16(), b.asI8x16()).asM128i()

/**
 * Compares packed unsigned 16-bit integers in `a` and `b`, and returns packed
 * minimum.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_min_epu16)
 */
public fun mmMinEpu16(a: M128i, b: M128i): M128i = simdImin(a.asU16x8(), b.asU16x8()).asM128i()

/**
 * Compares packed 32-bit integers in `a` and `b`, and returns packed minimum
 * values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_min_epi32)
 */
public fun mmMinEpi32(a: M128i, b: M128i): M128i = simdImin(a.asI32x4(), b.asI32x4()).asM128i()

/**
 * Compares packed unsigned 32-bit integers in `a` and `b`, and returns packed
 * minimum values.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_min_epu32)
 */
public fun mmMinEpu32(a: M128i, b: M128i): M128i = simdImin(a.asU32x4(), b.asU32x4()).asM128i()

/**
 * Converts packed 32-bit integers from `a` and `b` to packed 16-bit integers
 * using unsigned saturation
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_packus_epi32)
 */
public fun mmPackusEpi32(a: M128i, b: M128i): M128i = transmute(packusdw(a.asI32x4(), b.asI32x4()))

/**
 * Compares packed 64-bit integers in `a` and `b` for equality
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpeq_epi64)
 */
public fun mmCmpeqEpi64(a: M128i, b: M128i): M128i = transmute(run { val _simdArg0 = a.asI64x2(); val _simdArg1 = b.asI64x2(); run { val ptr = kmAllocZeroed(maxOf(1L, (ObjectSlotLayout<I64x2>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I64x2>()).alignBytes)); val res = (ObjectSlotLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (ObjectSlotLayout<I64x2>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I64x2>()).alignBytes)); res } })

/**
 * Sign extend packed 8-bit integers in `a` to packed 16-bit integers
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtepi8_epi16)
 */
public fun mmCvtepi8Epi16(a: M128i): M128i =
    run {
        val a1 = a.asI8x16()
        val a2: I8x8 = run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7)); _simdArg0 }
        transmute(run { val _simdArg0 = a2; run { val ptr = kmAllocZeroed(maxOf(1L, (ObjectSlotLayout<I16x8>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I16x8>()).alignBytes)); val res = (ObjectSlotLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (ObjectSlotLayout<I16x8>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I16x8>()).alignBytes)); res } })
    }

/**
 * Sign extend packed 8-bit integers in `a` to packed 32-bit integers
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtepi8_epi32)
 */
public fun mmCvtepi8Epi32(a: M128i): M128i =
    run {
        val a1 = a.asI8x16()
        val a2: I8x4 = run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3)); _simdArg0 }
        transmute(run { val _simdArg0 = a2; run { val ptr = kmAllocZeroed(maxOf(1L, (ObjectSlotLayout<I32x4>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I32x4>()).alignBytes)); val res = (ObjectSlotLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (ObjectSlotLayout<I32x4>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I32x4>()).alignBytes)); res } })
    }

/**
 * Sign extend packed 8-bit integers in the low 8 bytes of `a` to packed
 * 64-bit integers
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtepi8_epi64)
 */
public fun mmCvtepi8Epi64(a: M128i): M128i =
    run {
        val a1 = a.asI8x16()
        val a2: I8x2 = run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1)); _simdArg0 }
        transmute(run { val _simdArg0 = a2; run { val ptr = kmAllocZeroed(maxOf(1L, (ObjectSlotLayout<I64x2>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I64x2>()).alignBytes)); val res = (ObjectSlotLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (ObjectSlotLayout<I64x2>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I64x2>()).alignBytes)); res } })
    }

/**
 * Sign extend packed 16-bit integers in `a` to packed 32-bit integers
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtepi16_epi32)
 */
public fun mmCvtepi16Epi32(a: M128i): M128i =
    run {
        val a1 = a.asI16x8()
        val a2: I16x4 = run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3)); _simdArg0 }
        transmute(run { val _simdArg0 = a2; run { val ptr = kmAllocZeroed(maxOf(1L, (ObjectSlotLayout<I32x4>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I32x4>()).alignBytes)); val res = (ObjectSlotLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (ObjectSlotLayout<I32x4>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I32x4>()).alignBytes)); res } })
    }

/**
 * Sign extend packed 16-bit integers in `a` to packed 64-bit integers
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtepi16_epi64)
 */
public fun mmCvtepi16Epi64(a: M128i): M128i =
    run {
        val a1 = a.asI16x8()
        val a2: I16x2 = run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1)); _simdArg0 }
        transmute(run { val _simdArg0 = a2; run { val ptr = kmAllocZeroed(maxOf(1L, (ObjectSlotLayout<I64x2>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I64x2>()).alignBytes)); val res = (ObjectSlotLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (ObjectSlotLayout<I64x2>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I64x2>()).alignBytes)); res } })
    }

/**
 * Sign extend packed 32-bit integers in `a` to packed 64-bit integers
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtepi32_epi64)
 */
public fun mmCvtepi32Epi64(a: M128i): M128i =
    run {
        val a1 = a.asI32x4()
        val a2: I32x2 = run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1)); _simdArg0 }
        transmute(run { val _simdArg0 = a2; run { val ptr = kmAllocZeroed(maxOf(1L, (ObjectSlotLayout<I64x2>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I64x2>()).alignBytes)); val res = (ObjectSlotLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (ObjectSlotLayout<I64x2>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I64x2>()).alignBytes)); res } })
    }

/**
 * Zeroes extend packed unsigned 8-bit integers in `a` to packed 16-bit integers
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtepu8_epi16)
 */
public fun mmCvtepu8Epi16(a: M128i): M128i =
    run {
        val a1 = a.asU8x16()
        val a2: U8x8 = run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7)); _simdArg0 }
        transmute(run { val _simdArg0 = a2; run { val ptr = kmAllocZeroed(maxOf(1L, (ObjectSlotLayout<I16x8>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I16x8>()).alignBytes)); val res = (ObjectSlotLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (ObjectSlotLayout<I16x8>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I16x8>()).alignBytes)); res } })
    }

/**
 * Zeroes extend packed unsigned 8-bit integers in `a` to packed 32-bit integers
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtepu8_epi32)
 */
public fun mmCvtepu8Epi32(a: M128i): M128i =
    run {
        val a1 = a.asU8x16()
        val a2: U8x4 = run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3)); _simdArg0 }
        transmute(run { val _simdArg0 = a2; run { val ptr = kmAllocZeroed(maxOf(1L, (ObjectSlotLayout<I32x4>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I32x4>()).alignBytes)); val res = (ObjectSlotLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (ObjectSlotLayout<I32x4>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I32x4>()).alignBytes)); res } })
    }

/**
 * Zeroes extend packed unsigned 8-bit integers in `a` to packed 64-bit integers
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtepu8_epi64)
 */
public fun mmCvtepu8Epi64(a: M128i): M128i =
    run {
        val a1 = a.asU8x16()
        val a2: U8x2 = run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1)); _simdArg0 }
        transmute(run { val _simdArg0 = a2; run { val ptr = kmAllocZeroed(maxOf(1L, (ObjectSlotLayout<I64x2>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I64x2>()).alignBytes)); val res = (ObjectSlotLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (ObjectSlotLayout<I64x2>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I64x2>()).alignBytes)); res } })
    }

/**
 * Zeroes extend packed unsigned 16-bit integers in `a`
 * to packed 32-bit integers
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtepu16_epi32)
 */
public fun mmCvtepu16Epi32(a: M128i): M128i =
    run {
        val a1 = a.asU16x8()
        val a2: U16x4 = run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3)); _simdArg0 }
        transmute(run { val _simdArg0 = a2; run { val ptr = kmAllocZeroed(maxOf(1L, (ObjectSlotLayout<I32x4>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I32x4>()).alignBytes)); val res = (ObjectSlotLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (ObjectSlotLayout<I32x4>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I32x4>()).alignBytes)); res } })
    }

/**
 * Zeroes extend packed unsigned 16-bit integers in `a`
 * to packed 64-bit integers
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtepu16_epi64)
 */
public fun mmCvtepu16Epi64(a: M128i): M128i =
    run {
        val a1 = a.asU16x8()
        val a2: U16x2 = run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1)); _simdArg0 }
        transmute(run { val _simdArg0 = a2; run { val ptr = kmAllocZeroed(maxOf(1L, (ObjectSlotLayout<I64x2>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I64x2>()).alignBytes)); val res = (ObjectSlotLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (ObjectSlotLayout<I64x2>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I64x2>()).alignBytes)); res } })
    }

/**
 * Zeroes extend packed unsigned 32-bit integers in `a`
 * to packed 64-bit integers
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtepu32_epi64)
 */
public fun mmCvtepu32Epi64(a: M128i): M128i =
    run {
        val a1 = a.asU32x4()
        val a2: U32x2 = run { val _simdArg0 = a1; val _simdArg1 = a1; val _simdArg2 = SimdShuffleIdx(listOf(0, 1)); _simdArg0 }
        transmute(run { val _simdArg0 = a2; run { val ptr = kmAllocZeroed(maxOf(1L, (ObjectSlotLayout<I64x2>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I64x2>()).alignBytes)); val res = (ObjectSlotLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (ObjectSlotLayout<I64x2>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I64x2>()).alignBytes)); res } })
    }

/**
 * Returns the dot product of two __m128d vectors.
 *
 * `IMM8[1: 0]` is the broadcast mask, and `IMM8[5: 4]` is the condition mask.
 * If a condition mask bit is zero, the corresponding multiplication is
 * replaced by a value of `0.0`. If a broadcast mask bit is one, the result of
 * the dot product will be stored in the return value component. Otherwise if
 * the broadcast mask bit is zero then the return component will be zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_dp_pd)
 */
public fun mmDpPd(a: M128d, b: M128d, imm8: Int): M128d =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        dppd(a, b, imm8.toUByte())
    }

/**
 * Returns the dot product of two __m128 vectors.
 *
 * `IMM8[3: 0]` is the broadcast mask, and `IMM8[7: 4]` is the condition mask.
 * If a condition mask bit is zero, the corresponding multiplication is
 * replaced by a value of `0.0`. If a broadcast mask bit is one, the result of
 * the dot product will be stored in the return value component. Otherwise if
 * the broadcast mask bit is zero then the return component will be zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_dp_ps)
 */
public fun mmDpPs(a: M128, b: M128, imm8: Int): M128 {
    run {
        check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
    }
    return dpps(a, b, imm8.toUByte())
}

/**
 * Round the packed double-precision (64-bit) floating-point elements in `a`
 * down to an integer value, and stores the results as packed double-precision
 * floating-point elements.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_floor_pd)
 */
public fun mmFloorPd(a: M128d): M128d = run { val _simdArg0 = a; _simdArg0 }

/**
 * Round the packed single-precision (32-bit) floating-point elements in `a`
 * down to an integer value, and stores the results as packed single-precision
 * floating-point elements.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_floor_ps)
 */
public fun mmFloorPs(a: M128): M128 = run { val _simdArg0 = a; _simdArg0 }

/**
 * Round the lower double-precision (64-bit) floating-point element in `b`
 * down to an integer value, store the result as a double-precision
 * floating-point element in the lower element of the intrinsic result,
 * and copies the upper element from `a` to the upper element of the intrinsic
 * result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_floor_sd)
 */
public fun mmFloorSd(a: M128d, b: M128d): M128d = roundsd(a, b, MM_FROUND_FLOOR)

/**
 * Round the lower single-precision (32-bit) floating-point element in `b`
 * down to an integer value, store the result as a single-precision
 * floating-point element in the lower element of the intrinsic result,
 * and copies the upper 3 packed elements from `a` to the upper elements
 * of the intrinsic result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_floor_ss)
 */
public fun mmFloorSs(a: M128, b: M128): M128 = roundss(a, b, MM_FROUND_FLOOR)

/**
 * Round the packed double-precision (64-bit) floating-point elements in `a`
 * up to an integer value, and stores the results as packed double-precision
 * floating-point elements.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_ceil_pd)
 */
public fun mmCeilPd(a: M128d): M128d = run { val _simdArg0 = a; _simdArg0 }

/**
 * Round the packed single-precision (32-bit) floating-point elements in `a`
 * up to an integer value, and stores the results as packed single-precision
 * floating-point elements.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_ceil_ps)
 */
public fun mmCeilPs(a: M128): M128 = run { val _simdArg0 = a; _simdArg0 }

/**
 * Round the lower double-precision (64-bit) floating-point element in `b`
 * up to an integer value, store the result as a double-precision
 * floating-point element in the lower element of the intrinsic result,
 * and copies the upper element from `a` to the upper element
 * of the intrinsic result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_ceil_sd)
 */
public fun mmCeilSd(a: M128d, b: M128d): M128d = roundsd(a, b, MM_FROUND_CEIL)

/**
 * Round the lower single-precision (32-bit) floating-point element in `b`
 * up to an integer value, store the result as a single-precision
 * floating-point element in the lower element of the intrinsic result,
 * and copies the upper 3 packed elements from `a` to the upper elements
 * of the intrinsic result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_ceil_ss)
 */
public fun mmCeilSs(a: M128, b: M128): M128 = roundss(a, b, MM_FROUND_CEIL)

/**
 * Round the packed double-precision (64-bit) floating-point elements in `a`
 * using the `ROUNDING` parameter, and stores the results as packed
 * double-precision floating-point elements.
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_round_pd)
 */
public fun mmRoundPd(a: M128d, rounding: Int): M128d {
    run {
        check(0 <= rounding && rounding < (1 shl 4)) { "ROUNDING doesn't fit in 4 bits (unsigned)" }
    }
    return roundpd(a, rounding)
}

/**
 * Round the packed single-precision (32-bit) floating-point elements in `a`
 * using the `ROUNDING` parameter, and stores the results as packed
 * single-precision floating-point elements.
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_round_ps)
 */
public fun mmRoundPs(a: M128, rounding: Int): M128 {
    run {
        check(0 <= rounding && rounding < (1 shl 4)) { "ROUNDING doesn't fit in 4 bits (unsigned)" }
    }
    return roundps(a, rounding)
}

/**
 * Round the lower double-precision (64-bit) floating-point element in `b`
 * using the `ROUNDING` parameter, store the result as a double-precision
 * floating-point element in the lower element of the intrinsic result,
 * and copies the upper element from `a` to the upper element of the intrinsic
 * result.
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_round_sd)
 */
public fun mmRoundSd(a: M128d, b: M128d, rounding: Int): M128d {
    run {
        check(0 <= rounding && rounding < (1 shl 4)) { "ROUNDING doesn't fit in 4 bits (unsigned)" }
    }
    return roundsd(a, b, rounding)
}

/**
 * Round the lower single-precision (32-bit) floating-point element in `b`
 * using the `ROUNDING` parameter, store the result as a single-precision
 * floating-point element in the lower element of the intrinsic result,
 * and copies the upper 3 packed elements from `a` to the upper elements
 * of the intrinsic result.
 * Rounding is done according to the rounding parameter, which can be one of:
 *
 * * [mmFroundToNearestInt] | [mmFroundNoExc] : round to nearest and suppress exceptions
 * * [mmFroundToNegInf] | [mmFroundNoExc] : round down and suppress exceptions
 * * [mmFroundToPosInf] | [mmFroundNoExc] : round up and suppress exceptions
 * * [mmFroundToZero] | [mmFroundNoExc] : truncate and suppress exceptions
 * * [mmFroundCurDirection] : use `MXCSR.RC` - see [mmSetRoundingMode]
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_round_ss)
 */
public fun mmRoundSs(a: M128, b: M128, rounding: Int): M128 {
    run {
        check(0 <= rounding && rounding < (1 shl 4)) { "ROUNDING doesn't fit in 4 bits (unsigned)" }
    }
    return roundss(a, b, rounding)
}

/**
 * Finds the minimum unsigned 16-bit element in the 128-bit __m128i vector,
 * returning a vector containing its value in its first position, and its
 * index
 * in its second position; all other elements are set to zero.
 *
 * This intrinsic corresponds to the `VPHMINPOSUW` / `PHMINPOSUW`
 * instruction.
 *
 * Arguments:
 *
 * * `a` - A 128-bit vector of type `__m128i`.
 *
 * Returns:
 *
 * A 128-bit value where:
 *
 * * bits `[15: 0]` - contain the minimum value found in parameter `a`,
 * * bits `[18: 16]` - contain the index of the minimum value
 * * remaining bits are set to `0`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_minpos_epu16)
 */
public fun mmMinposEpu16(a: M128i): M128i = transmute(phminposuw(a.asU16x8()))

/**
 * Multiplies the low 32-bit integers from each packed 64-bit
 * element in `a` and `b`, and returns the signed 64-bit result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mul_epi32)
 */
public fun mmMulEpi32(a: M128i, b: M128i): M128i =
    run {
        val a1 = run { val _simdArg0 = run { val _simdArg0 = a.asI64x2(); run { val ptr = kmAllocZeroed(maxOf(1L, (ObjectSlotLayout<I32x2>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I32x2>()).alignBytes)); val res = (ObjectSlotLayout<I32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (ObjectSlotLayout<I32x2>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I32x2>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (ObjectSlotLayout<I64x2>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I64x2>()).alignBytes)); val res = (ObjectSlotLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (ObjectSlotLayout<I64x2>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I64x2>()).alignBytes)); res } }
        val b1 = run { val _simdArg0 = run { val _simdArg0 = b.asI64x2(); run { val ptr = kmAllocZeroed(maxOf(1L, (ObjectSlotLayout<I32x2>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I32x2>()).alignBytes)); val res = (ObjectSlotLayout<I32x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (ObjectSlotLayout<I32x2>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I32x2>()).alignBytes)); res } }; run { val ptr = kmAllocZeroed(maxOf(1L, (ObjectSlotLayout<I64x2>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I64x2>()).alignBytes)); val res = (ObjectSlotLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (ObjectSlotLayout<I64x2>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<I64x2>()).alignBytes)); res } }
        transmute(run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 })
    }

/**
 * Multiplies the packed 32-bit integers in `a` and `b`, producing intermediate
 * 64-bit integers, and returns the lowest 32-bit, whatever they might be,
 * reinterpreted as a signed integer. While `pmulld m128i.splat(2),
 * m128i.splat(2)`returns the obvious`m128i.splat(4)`, due to wrapping
 * arithmetic `pmulld __m128i.splat(Int.MAX), __m128i.splat(2)` would
 * return a negative number.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mullo_epi32)
 */
public fun mmMulloEpi32(a: M128i, b: M128i): M128i = transmute(run { val _simdArg0 = a.asI32x4(); val _simdArg1 = b.asI32x4(); _simdArg0 })

/**
 * Subtracts 8-bit unsigned integer values and computes the absolute
 * values of the differences to the corresponding bits in the destination.
 * Then sums of the absolute differences are returned according to the bit
 * fields in the immediate operand.
 *
 * The following algorithm is performed:
 *
 * ```ignore
 * i = IMM8[2] * 4
 * j = IMM8[1:0] * 4
 * for k := 0 to 7
 *     d0 = abs(a[i + k + 0] - b[j + 0])
 *     d1 = abs(a[i + k + 1] - b[j + 1])
 *     d2 = abs(a[i + k + 2] - b[j + 2])
 *     d3 = abs(a[i + k + 3] - b[j + 3])
 *     r[k] = d0 + d1 + d2 + d3
 * ```
 *
 * Arguments:
 *
 * * `a` - A 128-bit vector of type `__m128i`.
 * * `b` - A 128-bit vector of type `__m128i`.
 * * `IMM8` - An 8-bit immediate operand specifying how the absolute
 *   differences are to be calculated
 *     * Bit `[2]` specify the offset for operand `a`
 *     * Bits `[1: 0]` specify the offset for operand `b`
 *
 * Returns:
 *
 * * A `__m128i` vector containing the sums of the sets of   absolute
 *   differences between both operands.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mpsadbw_epu8)
 */
public fun mmMpsadbwEpu8(a: M128i, b: M128i, imm8: Int): M128i {
    run {
        check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(mpsadbw(a.asU8x16(), b.asU8x16(), imm8.toUByte()))
}

/**
 * Tests whether the specified bits in a 128-bit integer vector are all
 * zeros.
 *
 * Arguments:
 *
 * * `a` - A 128-bit integer vector containing the bits to be tested.
 * * `mask` - A 128-bit integer vector selecting which bits to test in
 *   operand `a`.
 *
 * Returns:
 *
 * * `1` - if the specified bits are all zeros,
 * * `0` - otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_testz_si128)
 */
public fun mmTestzSi128(a: M128i, mask: M128i): Int =
    run {
        val r = run { val _simdArg0 = run { val _simdArg0 = a.asI64x2(); val _simdArg1 = mask.asI64x2(); _simdArg0 }; _simdArg0 }
        (if (0L == r) 1 else 0)
    }

/**
 * Tests whether the specified bits in a 128-bit integer vector are all
 * ones.
 *
 * Arguments:
 *
 * * `a` - A 128-bit integer vector containing the bits to be tested.
 * * `mask` - A 128-bit integer vector selecting which bits to test in
 *   operand `a`.
 *
 * Returns:
 *
 * * `1` - if the specified bits are all ones,
 * * `0` - otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_testc_si128)
 */
public fun mmTestcSi128(a: M128i, mask: M128i): Int =
    run {
        val r = run { val _simdArg0 = run { val _simdArg0 = run { val _simdArg0 = a.asI64x2(); val _simdArg1 = i64x2.splat(0.inv()); _simdArg0 }; val _simdArg1 = mask.asI64x2(); _simdArg0 }; _simdArg0 }
        (if (0L == r) 1 else 0)
    }

/**
 * Tests whether the specified bits in a 128-bit integer vector are
 * neither all zeros nor all ones.
 *
 * Arguments:
 *
 * * `a` - A 128-bit integer vector containing the bits to be tested.
 * * `mask` - A 128-bit integer vector selecting which bits to test in
 *   operand `a`.
 *
 * Returns:
 *
 * * `1` - if the specified bits are neither all zeros nor all ones,
 * * `0` - otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_testnzc_si128)
 */
public fun mmTestnzcSi128(a: M128i, mask: M128i): Int = ptestnzc(a.asI64x2(), mask.asI64x2())

/**
 * Tests whether the specified bits in a 128-bit integer vector are all
 * zeros.
 *
 * Arguments:
 *
 * * `a` - A 128-bit integer vector containing the bits to be tested.
 * * `mask` - A 128-bit integer vector selecting which bits to test in
 *   operand `a`.
 *
 * Returns:
 *
 * * `1` - if the specified bits are all zeros,
 * * `0` - otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_test_all_zeros)
 */
public fun mmTestAllZeros(a: M128i, mask: M128i): Int = mmTestzSi128(a, mask)

/**
 * Tests whether the specified bits in `a` 128-bit integer vector are all
 * ones.
 *
 * Argument:
 *
 * * `a` - A 128-bit integer vector containing the bits to be tested.
 *
 * Returns:
 *
 * * `1` - if the bits specified in the operand are all set to 1,
 * * `0` - otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_test_all_ones)
 */
public fun mmTestAllOnes(a: M128i): Int = mmTestcSi128(a, mmCmpeqEpi32(a, a))

/**
 * Tests whether the specified bits in a 128-bit integer vector are
 * neither all zeros nor all ones.
 *
 * Arguments:
 *
 * * `a` - A 128-bit integer vector containing the bits to be tested.
 * * `mask` - A 128-bit integer vector selecting which bits to test in
 *   operand `a`.
 *
 * Returns:
 *
 * * `1` - if the specified bits are neither all zeros nor all ones,
 * * `0` - otherwise.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_test_mix_ones_zeros)
 */
public fun mmTestMixOnesZeros(a: M128i, mask: M128i): Int = mmTestnzcSi128(a, mask)

/**
 * Load 128-bits of integer data from memory into dst. memAddr must be aligned on a 16-byte
 * boundary or a general-protection exception may be generated. To minimize caching, the data
 * is flagged as non-temporal (unlikely to be used again soon)
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_stream_load_si128)
 */
public fun mmStreamLoadSi128(memAddr: ConstPtr): M128i {
    val dst: M128i
    Unit
    return dst
}

public expect fun insertps(a: M128, b: M128, imm8: UByte): M128

public expect fun packusdw(a: I32x4, b: I32x4): U16x8

public expect fun dppd(a: M128d, b: M128d, imm8: UByte): M128d

public expect fun dpps(a: M128, b: M128, imm8: UByte): M128

public expect fun roundpd(a: M128d, rounding: Int): M128d

public expect fun roundps(a: M128, rounding: Int): M128

public expect fun roundsd(a: M128d, b: M128d, rounding: Int): M128d

public expect fun roundss(a: M128, b: M128, rounding: Int): M128

public expect fun phminposuw(a: U16x8): U16x8

public expect fun mpsadbw(a: U8x16, b: U8x16, imm8: UByte): U16x8

public expect fun ptestnzc(a: I64x2, mask: I64x2): Int
