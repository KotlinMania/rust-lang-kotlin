// port-lint: source x86/avx512bw.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.array.write
import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.convert.num.from
import io.github.kotlinmania.rustlang.core.intrinsics.kmAllocZeroed
import io.github.kotlinmania.rustlang.core.intrinsics.kmDealloc
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.read
import io.github.kotlinmania.rustlang.core.ptr.readUnaligned
import io.github.kotlinmania.rustlang.core.ptr.writeUnaligned
import io.github.kotlinmania.rustlang.corearch.SimdShuffleIdx
import io.github.kotlinmania.rustlang.corearch.simd.I16x16
import io.github.kotlinmania.rustlang.corearch.simd.I16x32
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x16
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.I8x32
import io.github.kotlinmania.rustlang.corearch.simd.I8x64
import io.github.kotlinmania.rustlang.corearch.simd.I8x8
import io.github.kotlinmania.rustlang.corearch.simd.Simd
import io.github.kotlinmania.rustlang.corearch.simd.U16x16
import io.github.kotlinmania.rustlang.corearch.simd.U16x32
import io.github.kotlinmania.rustlang.corearch.simd.U16x8
import io.github.kotlinmania.rustlang.corearch.simd.U64x8
import io.github.kotlinmania.rustlang.corearch.simd.U8x16
import io.github.kotlinmania.rustlang.corearch.simd.U8x32
import io.github.kotlinmania.rustlang.corearch.simd.U8x64
import io.github.kotlinmania.rustlang.corearch.simd.U8x8
import io.github.kotlinmania.rustlang.corearch.simd.simdImax
import io.github.kotlinmania.rustlang.corearch.simd.simdImin
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or
import kotlin.experimental.xor

/**
 * Compute the absolute value of packed signed 16-bit integers in a, and store the unsigned results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_abs_epi16&expand=30)
 */
public fun mm512AbsEpi16(a: M512i): M512i =
    run {
        val a1 = a.asI16x32()
        val cmp: I16x32 = run { val simdArg0 = a1; val simdArg1 = Simd<Short>(List(32) { 0.toShort() }); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); res } }
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = cmp; val simdArg1 = a1; val simdArg2 = run { val simdArg0 = a1; simdArg0 }; simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Compute the absolute value of packed signed 16-bit integers in a, and store the unsigned results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_abs_epi16&expand=31)
 */
public fun mm512MaskAbsEpi16(src: M512i, k: Mmask32, a: M512i): M512i =
    run {
        val abs = mm512AbsEpi16(a).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = abs; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Compute the absolute value of packed signed 16-bit integers in a, and store the unsigned results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_abs_epi16&expand=32)
 */
public fun mm512MaskzAbsEpi16(k: Mmask32, a: M512i): M512i =
    run {
        val abs = mm512AbsEpi16(a).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = abs; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Compute the absolute value of packed signed 16-bit integers in a, and store the unsigned results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_abs_epi16&expand=28)
 */
public fun mm256MaskAbsEpi16(src: M256i, k: Mmask16, a: M256i): M256i =
    run {
        val abs = mm256AbsEpi16(a).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = abs; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Compute the absolute value of packed signed 16-bit integers in a, and store the unsigned results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_abs_epi16&expand=29)
 */
public fun mm256MaskzAbsEpi16(k: Mmask16, a: M256i): M256i =
    run {
        val abs = mm256AbsEpi16(a).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = abs; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Compute the absolute value of packed signed 16-bit integers in a, and store the unsigned results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_abs_epi16&expand=25)
 */
public fun mmMaskAbsEpi16(src: M128i, k: Mmask8, a: M128i): M128i =
    run {
        val abs = mmAbsEpi16(a).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = abs; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Compute the absolute value of packed signed 16-bit integers in a, and store the unsigned results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_abs_epi16&expand=26)
 */
public fun mmMaskzAbsEpi16(k: Mmask8, a: M128i): M128i =
    run {
        val abs = mmAbsEpi16(a).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = abs; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Compute the absolute value of packed signed 8-bit integers in a, and store the unsigned results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_abs_epi8&expand=57)
 */
public fun mm512AbsEpi8(a: M512i): M512i =
    run {
        val a1 = a.asI8x64()
        val cmp: I8x64 = run { val simdArg0 = a1; val simdArg1 = Simd<Byte>(List(64) { 0.toByte() }); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x64>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); res } }
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = cmp; val simdArg1 = a1; val simdArg2 = run { val simdArg0 = a1; simdArg0 }; simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Compute the absolute value of packed signed 8-bit integers in a, and store the unsigned results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_abs_epi8&expand=58)
 */
public fun mm512MaskAbsEpi8(src: M512i, k: Mmask64, a: M512i): M512i =
    run {
        val abs = mm512AbsEpi8(a).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = abs; val simdArg2 = src.asI8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Compute the absolute value of packed signed 8-bit integers in a, and store the unsigned results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_abs_epi8&expand=59)
 */
public fun mm512MaskzAbsEpi8(k: Mmask64, a: M512i): M512i =
    run {
        val abs = mm512AbsEpi8(a).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = abs; val simdArg2 = Simd<Byte>(List(64) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Compute the absolute value of packed signed 8-bit integers in a, and store the unsigned results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_abs_epi8&expand=55)
 */
public fun mm256MaskAbsEpi8(src: M256i, k: Mmask32, a: M256i): M256i =
    run {
        val abs = mm256AbsEpi8(a).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = abs; val simdArg2 = src.asI8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Compute the absolute value of packed signed 8-bit integers in a, and store the unsigned results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_abs_epi8&expand=56)
 */
public fun mm256MaskzAbsEpi8(k: Mmask32, a: M256i): M256i =
    run {
        val abs = mm256AbsEpi8(a).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = abs; val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Compute the absolute value of packed signed 8-bit integers in a, and store the unsigned results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set)
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_abs_epi8&expand=52)
 */
public fun mmMaskAbsEpi8(src: M128i, k: Mmask16, a: M128i): M128i =
    run {
        val abs = mmAbsEpi8(a).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = abs; val simdArg2 = src.asI8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Compute the absolute value of packed signed 8-bit integers in a, and store the unsigned results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_abs_epi8&expand=53)
 */
public fun mmMaskzAbsEpi8(k: Mmask16, a: M128i): M128i =
    run {
        val abs = mmAbsEpi8(a).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = abs; val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Add packed 16-bit integers in a and b, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_add_epi16&expand=91)
 */
public fun mm512AddEpi16(a: M512i, b: M512i): M512i = transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = a.asI16x32(); val simdArg1 = b.asI16x32(); simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())

/**
 * Add packed 16-bit integers in a and b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_add_epi16&expand=92)
 */
public fun mm512MaskAddEpi16(src: M512i, k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val add = mm512AddEpi16(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Add packed 16-bit integers in a and b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_add_epi16&expand=93)
 */
public fun mm512MaskzAddEpi16(k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val add = mm512AddEpi16(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Add packed 16-bit integers in a and b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_add_epi16&expand=89)
 */
public fun mm256MaskAddEpi16(src: M256i, k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val add = mm256AddEpi16(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Add packed 16-bit integers in a and b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_add_epi16&expand=90)
 */
public fun mm256MaskzAddEpi16(k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val add = mm256AddEpi16(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Add packed 16-bit integers in a and b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_add_epi16&expand=86)
 */
public fun mmMaskAddEpi16(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val add = mmAddEpi16(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Add packed 16-bit integers in a and b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_add_epi16&expand=87)
 */
public fun mmMaskzAddEpi16(k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val add = mmAddEpi16(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Add packed 8-bit integers in a and b, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_add_epi8&expand=118)
 */
public fun mm512AddEpi8(a: M512i, b: M512i): M512i = transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = a.asI8x64(); val simdArg1 = b.asI8x64(); simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())

/**
 * Add packed 8-bit integers in a and b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_add_epi8&expand=119)
 */
public fun mm512MaskAddEpi8(src: M512i, k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val add = mm512AddEpi8(a, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = src.asI8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Add packed 8-bit integers in a and b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_add_epi8&expand=120)
 */
public fun mm512MaskzAddEpi8(k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val add = mm512AddEpi8(a, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = Simd<Byte>(List(64) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Add packed 8-bit integers in a and b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_add_epi8&expand=116)
 */
public fun mm256MaskAddEpi8(src: M256i, k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val add = mm256AddEpi8(a, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = src.asI8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Add packed 8-bit integers in a and b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_add_epi8&expand=117)
 */
public fun mm256MaskzAddEpi8(k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val add = mm256AddEpi8(a, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Add packed 8-bit integers in a and b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_add_epi8&expand=113)
 */
public fun mmMaskAddEpi8(src: M128i, k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val add = mmAddEpi8(a, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = src.asI8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Add packed 8-bit integers in a and b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_add_epi8&expand=114)
 */
public fun mmMaskzAddEpi8(k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val add = mmAddEpi8(a, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Add packed unsigned 16-bit integers in a and b using saturation, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_adds_epu16&expand=197)
 */
public fun mm512AddsEpu16(a: M512i, b: M512i): M512i = transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = a.asU16x32(); val simdArg1 = b.asU16x32(); simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())

/**
 * Add packed unsigned 16-bit integers in a and b using saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_adds_epu16&expand=198)
 */
public fun mm512MaskAddsEpu16(src: M512i, k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val add = mm512AddsEpu16(a, b).asU16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = src.asU16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Add packed unsigned 16-bit integers in a and b using saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_adds_epu16&expand=199)
 */
public fun mm512MaskzAddsEpu16(k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val add = mm512AddsEpu16(a, b).asU16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = Simd<UShort>(List(32) { 0u.toUShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Add packed unsigned 16-bit integers in a and b using saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_adds_epu16&expand=195)
 */
public fun mm256MaskAddsEpu16(src: M256i, k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val add = mm256AddsEpu16(a, b).asU16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = src.asU16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Add packed unsigned 16-bit integers in a and b using saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_adds_epu16&expand=196)
 */
public fun mm256MaskzAddsEpu16(k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val add = mm256AddsEpu16(a, b).asU16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = Simd<UShort>(List(16) { 0u.toUShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Add packed unsigned 16-bit integers in a and b using saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_adds_epu16&expand=192)
 */
public fun mmMaskAddsEpu16(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val add = mmAddsEpu16(a, b).asU16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = src.asU16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Add packed unsigned 16-bit integers in a and b using saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_adds_epu16&expand=193)
 */
public fun mmMaskzAddsEpu16(k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val add = mmAddsEpu16(a, b).asU16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = Simd<UShort>(List(8) { 0u.toUShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Add packed unsigned 8-bit integers in a and b using saturation, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_adds_epu8&expand=206)
 */
public fun mm512AddsEpu8(a: M512i, b: M512i): M512i = transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = a.asU8x64(); val simdArg1 = b.asU8x64(); simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())

/**
 * Add packed unsigned 8-bit integers in a and b using saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_adds_epu8&expand=207)
 */
public fun mm512MaskAddsEpu8(src: M512i, k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val add = mm512AddsEpu8(a, b).asU8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = src.asU8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Add packed unsigned 8-bit integers in a and b using saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_adds_epu8&expand=208)
 */
public fun mm512MaskzAddsEpu8(k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val add = mm512AddsEpu8(a, b).asU8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = Simd<UByte>(List(64) { 0u.toUByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Add packed unsigned 8-bit integers in a and b using saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_adds_epu8&expand=204)
 */
public fun mm256MaskAddsEpu8(src: M256i, k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val add = mm256AddsEpu8(a, b).asU8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = src.asU8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Add packed unsigned 8-bit integers in a and b using saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_adds_epu8&expand=205)
 */
public fun mm256MaskzAddsEpu8(k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val add = mm256AddsEpu8(a, b).asU8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = Simd<UByte>(List(32) { 0u.toUByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Add packed unsigned 8-bit integers in a and b using saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_adds_epu8&expand=201)
 */
public fun mmMaskAddsEpu8(src: M128i, k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val add = mmAddsEpu8(a, b).asU8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = src.asU8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Add packed unsigned 8-bit integers in a and b using saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_adds_epu8&expand=202)
 */
public fun mmMaskzAddsEpu8(k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val add = mmAddsEpu8(a, b).asU8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = Simd<UByte>(List(16) { 0u.toUByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Add packed signed 16-bit integers in a and b using saturation, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_adds_epi16&expand=179)
 */
public fun mm512AddsEpi16(a: M512i, b: M512i): M512i = transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = a.asI16x32(); val simdArg1 = b.asI16x32(); simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())

/**
 * Add packed signed 16-bit integers in a and b using saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_adds_epi16&expand=180)
 */
public fun mm512MaskAddsEpi16(src: M512i, k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val add = mm512AddsEpi16(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Add packed signed 16-bit integers in a and b using saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_adds_epi16&expand=181)
 */
public fun mm512MaskzAddsEpi16(k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val add = mm512AddsEpi16(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Add packed signed 16-bit integers in a and b using saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_adds_epi16&expand=177)
 */
public fun mm256MaskAddsEpi16(src: M256i, k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val add = mm256AddsEpi16(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Add packed signed 16-bit integers in a and b using saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_adds_epi16&expand=178)
 */
public fun mm256MaskzAddsEpi16(k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val add = mm256AddsEpi16(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Add packed signed 16-bit integers in a and b using saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_adds_epi16&expand=174)
 */
public fun mmMaskAddsEpi16(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val add = mmAddsEpi16(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Add packed signed 16-bit integers in a and b using saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_adds_epi16&expand=175)
 */
public fun mmMaskzAddsEpi16(k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val add = mmAddsEpi16(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Add packed signed 8-bit integers in a and b using saturation, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_adds_epi8&expand=188)
 */
public fun mm512AddsEpi8(a: M512i, b: M512i): M512i = transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = a.asI8x64(); val simdArg1 = b.asI8x64(); simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())

/**
 * Add packed signed 8-bit integers in a and b using saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_adds_epi8&expand=189)
 */
public fun mm512MaskAddsEpi8(src: M512i, k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val add = mm512AddsEpi8(a, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = src.asI8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Add packed signed 8-bit integers in a and b using saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_adds_epi8&expand=190)
 */
public fun mm512MaskzAddsEpi8(k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val add = mm512AddsEpi8(a, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = Simd<Byte>(List(64) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Add packed signed 8-bit integers in a and b using saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_adds_epi8&expand=186)
 */
public fun mm256MaskAddsEpi8(src: M256i, k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val add = mm256AddsEpi8(a, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = src.asI8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Add packed signed 8-bit integers in a and b using saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_adds_epi8&expand=187)
 */
public fun mm256MaskzAddsEpi8(k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val add = mm256AddsEpi8(a, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Add packed signed 8-bit integers in a and b using saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_adds_epi8&expand=183)
 */
public fun mmMaskAddsEpi8(src: M128i, k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val add = mmAddsEpi8(a, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = src.asI8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Add packed signed 8-bit integers in a and b using saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_adds_epi8&expand=184)
 */
public fun mmMaskzAddsEpi8(k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val add = mmAddsEpi8(a, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = add; val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Subtract packed 16-bit integers in b from packed 16-bit integers in a, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_sub_epi16&expand=5685)
 */
public fun mm512SubEpi16(a: M512i, b: M512i): M512i = transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = a.asI16x32(); val simdArg1 = b.asI16x32(); simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())

/**
 * Subtract packed 16-bit integers in b from packed 16-bit integers in a, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_sub_epi16&expand=5683)
 */
public fun mm512MaskSubEpi16(src: M512i, k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val sub = mm512SubEpi16(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Subtract packed 16-bit integers in b from packed 16-bit integers in a, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_sub_epi16&expand=5684)
 */
public fun mm512MaskzSubEpi16(k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val sub = mm512SubEpi16(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Subtract packed 16-bit integers in b from packed 16-bit integers in a, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_sub_epi16&expand=5680)
 */
public fun mm256MaskSubEpi16(src: M256i, k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val sub = mm256SubEpi16(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Subtract packed 16-bit integers in b from packed 16-bit integers in a, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_sub_epi16&expand=5681)
 */
public fun mm256MaskzSubEpi16(k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val sub = mm256SubEpi16(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Subtract packed 16-bit integers in b from packed 16-bit integers in a, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_sub_epi16&expand=5677)
 */
public fun mmMaskSubEpi16(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val sub = mmSubEpi16(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Subtract packed 16-bit integers in b from packed 16-bit integers in a, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_sub_epi16&expand=5678)
 */
public fun mmMaskzSubEpi16(k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val sub = mmSubEpi16(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Subtract packed 8-bit integers in b from packed 8-bit integers in a, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_sub_epi8&expand=5712)
 */
public fun mm512SubEpi8(a: M512i, b: M512i): M512i = transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = a.asI8x64(); val simdArg1 = b.asI8x64(); simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())

/**
 * Subtract packed 8-bit integers in b from packed 8-bit integers in a, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_sub_epi8&expand=5710)
 */
public fun mm512MaskSubEpi8(src: M512i, k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val sub = mm512SubEpi8(a, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = src.asI8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Subtract packed 8-bit integers in b from packed 8-bit integers in a, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_sub_epi8&expand=5711)
 */
public fun mm512MaskzSubEpi8(k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val sub = mm512SubEpi8(a, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = Simd<Byte>(List(64) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Subtract packed 8-bit integers in b from packed 8-bit integers in a, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_sub_epi8&expand=5707)
 */
public fun mm256MaskSubEpi8(src: M256i, k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val sub = mm256SubEpi8(a, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = src.asI8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Subtract packed 8-bit integers in b from packed 8-bit integers in a, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_sub_epi8&expand=5708)
 */
public fun mm256MaskzSubEpi8(k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val sub = mm256SubEpi8(a, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Subtract packed 8-bit integers in b from packed 8-bit integers in a, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_sub_epi8&expand=5704)
 */
public fun mmMaskSubEpi8(src: M128i, k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val sub = mmSubEpi8(a, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = src.asI8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Subtract packed 8-bit integers in b from packed 8-bit integers in a, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_sub_epi8&expand=5705)
 */
public fun mmMaskzSubEpi8(k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val sub = mmSubEpi8(a, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Subtract packed unsigned 16-bit integers in b from packed unsigned 16-bit integers in a using saturation, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_subs_epu16&expand=5793)
 */
public fun mm512SubsEpu16(a: M512i, b: M512i): M512i = transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = a.asU16x32(); val simdArg1 = b.asU16x32(); simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())

/**
 * Subtract packed unsigned 16-bit integers in b from packed unsigned 16-bit integers in a using saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_subs_epu16&expand=5791)
 */
public fun mm512MaskSubsEpu16(src: M512i, k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val sub = mm512SubsEpu16(a, b).asU16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = src.asU16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Subtract packed unsigned 16-bit integers in b from packed unsigned 16-bit integers in a using saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_subs_epu16&expand=5792)
 */
public fun mm512MaskzSubsEpu16(k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val sub = mm512SubsEpu16(a, b).asU16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = Simd<UShort>(List(32) { 0u.toUShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Subtract packed unsigned 16-bit integers in b from packed unsigned 16-bit integers in a using saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_subs_epu16&expand=5788)
 */
public fun mm256MaskSubsEpu16(src: M256i, k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val sub = mm256SubsEpu16(a, b).asU16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = src.asU16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Subtract packed unsigned 16-bit integers in b from packed unsigned 16-bit integers in a using saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_subs_epu16&expand=5789)
 */
public fun mm256MaskzSubsEpu16(k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val sub = mm256SubsEpu16(a, b).asU16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = Simd<UShort>(List(16) { 0u.toUShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Subtract packed unsigned 16-bit integers in b from packed unsigned 16-bit integers in a using saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_subs_epu16&expand=5785)
 */
public fun mmMaskSubsEpu16(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val sub = mmSubsEpu16(a, b).asU16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = src.asU16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Subtract packed unsigned 16-bit integers in b from packed unsigned 16-bit integers in a using saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_subs_epu16&expand=5786)
 */
public fun mmMaskzSubsEpu16(k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val sub = mmSubsEpu16(a, b).asU16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = Simd<UShort>(List(8) { 0u.toUShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Subtract packed unsigned 8-bit integers in b from packed unsigned 8-bit integers in a using saturation, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_subs_epu8&expand=5802)
 */
public fun mm512SubsEpu8(a: M512i, b: M512i): M512i = transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = a.asU8x64(); val simdArg1 = b.asU8x64(); simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())

/**
 * Subtract packed unsigned 8-bit integers in b from packed unsigned 8-bit integers in a using saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_subs_epu8&expand=5800)
 */
public fun mm512MaskSubsEpu8(src: M512i, k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val sub = mm512SubsEpu8(a, b).asU8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = src.asU8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Subtract packed unsigned 8-bit integers in b from packed unsigned 8-bit integers in a using saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_subs_epu8&expand=5801)
 */
public fun mm512MaskzSubsEpu8(k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val sub = mm512SubsEpu8(a, b).asU8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = Simd<UByte>(List(64) { 0u.toUByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Subtract packed unsigned 8-bit integers in b from packed unsigned 8-bit integers in a using saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_subs_epu8&expand=5797)
 */
public fun mm256MaskSubsEpu8(src: M256i, k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val sub = mm256SubsEpu8(a, b).asU8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = src.asU8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Subtract packed unsigned 8-bit integers in b from packed unsigned 8-bit integers in a using saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_subs_epu8&expand=5798)
 */
public fun mm256MaskzSubsEpu8(k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val sub = mm256SubsEpu8(a, b).asU8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = Simd<UByte>(List(32) { 0u.toUByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Subtract packed unsigned 8-bit integers in b from packed unsigned 8-bit integers in a using saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_subs_epu8&expand=5794)
 */
public fun mmMaskSubsEpu8(src: M128i, k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val sub = mmSubsEpu8(a, b).asU8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = src.asU8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Subtract packed unsigned 8-bit integers in b from packed unsigned 8-bit integers in a using saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_subs_epu8&expand=5795)
 */
public fun mmMaskzSubsEpu8(k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val sub = mmSubsEpu8(a, b).asU8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = Simd<UByte>(List(16) { 0u.toUByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Subtract packed signed 16-bit integers in b from packed 16-bit integers in a using saturation, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_subs_epi16&expand=5775)
 */
public fun mm512SubsEpi16(a: M512i, b: M512i): M512i = transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = a.asI16x32(); val simdArg1 = b.asI16x32(); simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())

/**
 * Subtract packed signed 16-bit integers in b from packed 16-bit integers in a using saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_subs_epi16&expand=5773)
 */
public fun mm512MaskSubsEpi16(src: M512i, k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val sub = mm512SubsEpi16(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Subtract packed signed 16-bit integers in b from packed 16-bit integers in a using saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_subs_epi16&expand=5774)
 */
public fun mm512MaskzSubsEpi16(k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val sub = mm512SubsEpi16(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Subtract packed signed 16-bit integers in b from packed 16-bit integers in a using saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_subs_epi16&expand=5770)
 */
public fun mm256MaskSubsEpi16(src: M256i, k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val sub = mm256SubsEpi16(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Subtract packed signed 16-bit integers in b from packed 16-bit integers in a using saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_subs_epi16&expand=5771)
 */
public fun mm256MaskzSubsEpi16(k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val sub = mm256SubsEpi16(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Subtract packed signed 16-bit integers in b from packed 16-bit integers in a using saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_subs_epi16&expand=5767)
 */
public fun mmMaskSubsEpi16(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val sub = mmSubsEpi16(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Subtract packed signed 16-bit integers in b from packed 16-bit integers in a using saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_subs_epi16&expand=5768)
 */
public fun mmMaskzSubsEpi16(k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val sub = mmSubsEpi16(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Subtract packed signed 8-bit integers in b from packed 8-bit integers in a using saturation, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_subs_epi8&expand=5784)
 */
public fun mm512SubsEpi8(a: M512i, b: M512i): M512i = transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = a.asI8x64(); val simdArg1 = b.asI8x64(); simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())

/**
 * Subtract packed signed 8-bit integers in b from packed 8-bit integers in a using saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_subs_epi8&expand=5782)
 */
public fun mm512MaskSubsEpi8(src: M512i, k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val sub = mm512SubsEpi8(a, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = src.asI8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Subtract packed signed 8-bit integers in b from packed 8-bit integers in a using saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_subs_epi8&expand=5783)
 */
public fun mm512MaskzSubsEpi8(k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val sub = mm512SubsEpi8(a, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = Simd<Byte>(List(64) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Subtract packed signed 8-bit integers in b from packed 8-bit integers in a using saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_subs_epi8&expand=5779)
 */
public fun mm256MaskSubsEpi8(src: M256i, k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val sub = mm256SubsEpi8(a, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = src.asI8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Subtract packed signed 8-bit integers in b from packed 8-bit integers in a using saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_subs_epi8&expand=5780)
 */
public fun mm256MaskzSubsEpi8(k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val sub = mm256SubsEpi8(a, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Subtract packed signed 8-bit integers in b from packed 8-bit integers in a using saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_subs_epi8&expand=5776)
 */
public fun mmMaskSubsEpi8(src: M128i, k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val sub = mmSubsEpi8(a, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = src.asI8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Subtract packed signed 8-bit integers in b from packed 8-bit integers in a using saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_subs_epi8&expand=5777)
 */
public fun mmMaskzSubsEpi8(k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val sub = mmSubsEpi8(a, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = sub; val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Multiply the packed unsigned 16-bit integers in a and b, producing intermediate 32-bit integers, and store the high 16 bits of the intermediate integers in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mulhi_epu16&expand=3973)
 */
public fun mm512MulhiEpu16(a: M512i, b: M512i): M512i = transmuteWithLayouts<U16x32, M512i>(vpmulhuw(a.asU16x32(), b.asU16x32()), UnresolvedTypeLayout<U16x32>(), UnresolvedTypeLayout<M512i>())

/**
 * Multiply the packed unsigned 16-bit integers in a and b, producing intermediate 32-bit integers, and store the high 16 bits of the intermediate integers in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_mulhi_epu16&expand=3971)
 */
public fun mm512MaskMulhiEpu16(src: M512i, k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val mul = mm512MulhiEpu16(a, b).asU16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = mul; val simdArg2 = src.asU16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Multiply the packed unsigned 16-bit integers in a and b, producing intermediate 32-bit integers, and store the high 16 bits of the intermediate integers in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_mulhi_epu16&expand=3972)
 */
public fun mm512MaskzMulhiEpu16(k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val mul = mm512MulhiEpu16(a, b).asU16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = mul; val simdArg2 = Simd<UShort>(List(32) { 0u.toUShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Multiply the packed unsigned 16-bit integers in a and b, producing intermediate 32-bit integers, and store the high 16 bits of the intermediate integers in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_mulhi_epu16&expand=3968)
 */
public fun mm256MaskMulhiEpu16(src: M256i, k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val mul = mm256MulhiEpu16(a, b).asU16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = mul; val simdArg2 = src.asU16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Multiply the packed unsigned 16-bit integers in a and b, producing intermediate 32-bit integers, and store the high 16 bits of the intermediate integers in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_mulhi_epu16&expand=3969)
 */
public fun mm256MaskzMulhiEpu16(k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val mul = mm256MulhiEpu16(a, b).asU16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = mul; val simdArg2 = Simd<UShort>(List(16) { 0u.toUShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Multiply the packed unsigned 16-bit integers in a and b, producing intermediate 32-bit integers, and store the high 16 bits of the intermediate integers in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_mulhi_epu16&expand=3965)
 */
public fun mmMaskMulhiEpu16(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val mul = mmMulhiEpu16(a, b).asU16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = mul; val simdArg2 = src.asU16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Multiply the packed unsigned 16-bit integers in a and b, producing intermediate 32-bit integers, and store the high 16 bits of the intermediate integers in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_mulhi_epu16&expand=3966)
 */
public fun mmMaskzMulhiEpu16(k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val mul = mmMulhiEpu16(a, b).asU16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = mul; val simdArg2 = Simd<UShort>(List(8) { 0u.toUShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Multiply the packed signed 16-bit integers in a and b, producing intermediate 32-bit integers, and store the high 16 bits of the intermediate integers in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mulhi_epi16&expand=3962)
 */
public fun mm512MulhiEpi16(a: M512i, b: M512i): M512i = transmuteWithLayouts<I16x32, M512i>(vpmulhw(a.asI16x32(), b.asI16x32()), UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())

/**
 * Multiply the packed signed 16-bit integers in a and b, producing intermediate 32-bit integers, and store the high 16 bits of the intermediate integers in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_mulhi_epi16&expand=3960)
 */
public fun mm512MaskMulhiEpi16(src: M512i, k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val mul = mm512MulhiEpi16(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = mul; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Multiply the packed signed 16-bit integers in a and b, producing intermediate 32-bit integers, and store the high 16 bits of the intermediate integers in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_mulhi_epi16&expand=3961)
 */
public fun mm512MaskzMulhiEpi16(k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val mul = mm512MulhiEpi16(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = mul; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Multiply the packed signed 16-bit integers in a and b, producing intermediate 32-bit integers, and store the high 16 bits of the intermediate integers in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_mulhi_epi16&expand=3957)
 */
public fun mm256MaskMulhiEpi16(src: M256i, k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val mul = mm256MulhiEpi16(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = mul; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Multiply the packed signed 16-bit integers in a and b, producing intermediate 32-bit integers, and store the high 16 bits of the intermediate integers in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_mulhi_epi16&expand=3958)
 */
public fun mm256MaskzMulhiEpi16(k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val mul = mm256MulhiEpi16(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = mul; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Multiply the packed signed 16-bit integers in a and b, producing intermediate 32-bit integers, and store the high 16 bits of the intermediate integers in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_mulhi_epi16&expand=3954)
 */
public fun mmMaskMulhiEpi16(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val mul = mmMulhiEpi16(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = mul; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Multiply the packed signed 16-bit integers in a and b, producing intermediate 32-bit integers, and store the high 16 bits of the intermediate integers in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_mulhi_epi16&expand=3955)
 */
public fun mmMaskzMulhiEpi16(k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val mul = mmMulhiEpi16(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = mul; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Multiply packed signed 16-bit integers in a and b, producing intermediate signed 32-bit integers. Truncate each intermediate integer to the 18 most significant bits, round by adding 1, and store bits \[16:1\] to dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mulhrs_epi16&expand=3986)
 */
public fun mm512MulhrsEpi16(a: M512i, b: M512i): M512i = transmuteWithLayouts<I16x32, M512i>(vpmulhrsw(a.asI16x32(), b.asI16x32()), UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())

/**
 * Multiply packed signed 16-bit integers in a and b, producing intermediate signed 32-bit integers. Truncate each intermediate integer to the 18 most significant bits, round by adding 1, and store bits \[16:1\] to dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_mulhrs_epi16&expand=3984)
 */
public fun mm512MaskMulhrsEpi16(src: M512i, k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val mul = mm512MulhrsEpi16(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = mul; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Multiply packed signed 16-bit integers in a and b, producing intermediate signed 32-bit integers. Truncate each intermediate integer to the 18 most significant bits, round by adding 1, and store bits \[16:1\] to dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_mulhrs_epi16&expand=3985)
 */
public fun mm512MaskzMulhrsEpi16(k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val mul = mm512MulhrsEpi16(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = mul; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Multiply packed signed 16-bit integers in a and b, producing intermediate signed 32-bit integers. Truncate each intermediate integer to the 18 most significant bits, round by adding 1, and store bits \[16:1\] to dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_mulhrs_epi16&expand=3981)
 */
public fun mm256MaskMulhrsEpi16(src: M256i, k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val mul = mm256MulhrsEpi16(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = mul; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Multiply packed signed 16-bit integers in a and b, producing intermediate signed 32-bit integers. Truncate each intermediate integer to the 18 most significant bits, round by adding 1, and store bits \[16:1\] to dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_mulhrs_epi16&expand=3982)
 */
public fun mm256MaskzMulhrsEpi16(k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val mul = mm256MulhrsEpi16(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = mul; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Multiply packed signed 16-bit integers in a and b, producing intermediate signed 32-bit integers. Truncate each intermediate integer to the 18 most significant bits, round by adding 1, and store bits \[16:1\] to dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_mulhrs_epi16&expand=3978)
 */
public fun mmMaskMulhrsEpi16(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val mul = mmMulhrsEpi16(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = mul; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Multiply packed signed 16-bit integers in a and b, producing intermediate signed 32-bit integers. Truncate each intermediate integer to the 18 most significant bits, round by adding 1, and store bits \[16:1\] to dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_mulhrs_epi16&expand=3979)
 */
public fun mmMaskzMulhrsEpi16(k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val mul = mmMulhrsEpi16(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = mul; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Multiply the packed 16-bit integers in a and b, producing intermediate 32-bit integers, and store the low 16 bits of the intermediate integers in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mullo_epi16&expand=3996)
 */
public fun mm512MulloEpi16(a: M512i, b: M512i): M512i = transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = a.asI16x32(); val simdArg1 = b.asI16x32(); simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())

/**
 * Multiply the packed 16-bit integers in a and b, producing intermediate 32-bit integers, and store the low 16 bits of the intermediate integers in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_mullo_epi16&expand=3994)
 */
public fun mm512MaskMulloEpi16(src: M512i, k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val mul = mm512MulloEpi16(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = mul; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Multiply the packed 16-bit integers in a and b, producing intermediate 32-bit integers, and store the low 16 bits of the intermediate integers in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_mullo_epi16&expand=3995)
 */
public fun mm512MaskzMulloEpi16(k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val mul = mm512MulloEpi16(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = mul; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Multiply the packed 16-bit integers in a and b, producing intermediate 32-bit integers, and store the low 16 bits of the intermediate integers in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_mullo_epi16&expand=3991)
 */
public fun mm256MaskMulloEpi16(src: M256i, k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val mul = mm256MulloEpi16(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = mul; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Multiply the packed 16-bit integers in a and b, producing intermediate 32-bit integers, and store the low 16 bits of the intermediate integers in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_mullo_epi16&expand=3992)
 */
public fun mm256MaskzMulloEpi16(k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val mul = mm256MulloEpi16(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = mul; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Multiply the packed 16-bit integers in a and b, producing intermediate 32-bit integers, and store the low 16 bits of the intermediate integers in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_mullo_epi16&expand=3988)
 */
public fun mmMaskMulloEpi16(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val mul = mmMulloEpi16(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = mul; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Multiply the packed 16-bit integers in a and b, producing intermediate 32-bit integers, and store the low 16 bits of the intermediate integers in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_mullo_epi16&expand=3989)
 */
public fun mmMaskzMulloEpi16(k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val mul = mmMulloEpi16(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = mul; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Compare packed unsigned 16-bit integers in a and b, and store packed maximum values in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_max_epu16&expand=3609)
 */
public fun mm512MaxEpu16(a: M512i, b: M512i): M512i = simdImax(a.asU16x32(), b.asU16x32()).asM512i()

/**
 * Compare packed unsigned 16-bit integers in a and b, and store packed maximum values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_max_epu16&expand=3607)
 */
public fun mm512MaskMaxEpu16(src: M512i, k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val max = mm512MaxEpu16(a, b).asU16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = max; val simdArg2 = src.asU16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Compare packed unsigned 16-bit integers in a and b, and store packed maximum values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_max_epu16&expand=3608)
 */
public fun mm512MaskzMaxEpu16(k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val max = mm512MaxEpu16(a, b).asU16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = max; val simdArg2 = Simd<UShort>(List(32) { 0u.toUShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Compare packed unsigned 16-bit integers in a and b, and store packed maximum values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_max_epu16&expand=3604)
 */
public fun mm256MaskMaxEpu16(src: M256i, k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val max = mm256MaxEpu16(a, b).asU16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = max; val simdArg2 = src.asU16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Compare packed unsigned 16-bit integers in a and b, and store packed maximum values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_max_epu16&expand=3605)
 */
public fun mm256MaskzMaxEpu16(k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val max = mm256MaxEpu16(a, b).asU16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = max; val simdArg2 = Simd<UShort>(List(16) { 0u.toUShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Compare packed unsigned 16-bit integers in a and b, and store packed maximum values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_max_epu16&expand=3601)
 */
public fun mmMaskMaxEpu16(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val max = mmMaxEpu16(a, b).asU16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = max; val simdArg2 = src.asU16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Compare packed unsigned 16-bit integers in a and b, and store packed maximum values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_max_epu16&expand=3602)
 */
public fun mmMaskzMaxEpu16(k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val max = mmMaxEpu16(a, b).asU16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = max; val simdArg2 = Simd<UShort>(List(8) { 0u.toUShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Compare packed unsigned 8-bit integers in a and b, and store packed maximum values in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_max_epu8&expand=3636)
 */
public fun mm512MaxEpu8(a: M512i, b: M512i): M512i = simdImax(a.asU8x64(), b.asU8x64()).asM512i()

/**
 * Compare packed unsigned 8-bit integers in a and b, and store packed maximum values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_max_epu8&expand=3634)
 */
public fun mm512MaskMaxEpu8(src: M512i, k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val max = mm512MaxEpu8(a, b).asU8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = max; val simdArg2 = src.asU8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Compare packed unsigned 8-bit integers in a and b, and store packed maximum values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_max_epu8&expand=3635)
 */
public fun mm512MaskzMaxEpu8(k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val max = mm512MaxEpu8(a, b).asU8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = max; val simdArg2 = Simd<UByte>(List(64) { 0u.toUByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Compare packed unsigned 8-bit integers in a and b, and store packed maximum values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_max_epu8&expand=3631)
 */
public fun mm256MaskMaxEpu8(src: M256i, k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val max = mm256MaxEpu8(a, b).asU8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = max; val simdArg2 = src.asU8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Compare packed unsigned 8-bit integers in a and b, and store packed maximum values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_max_epu8&expand=3632)
 */
public fun mm256MaskzMaxEpu8(k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val max = mm256MaxEpu8(a, b).asU8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = max; val simdArg2 = Simd<UByte>(List(32) { 0u.toUByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Compare packed unsigned 8-bit integers in a and b, and store packed maximum values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_max_epu8&expand=3628)
 */
public fun mmMaskMaxEpu8(src: M128i, k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val max = mmMaxEpu8(a, b).asU8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = max; val simdArg2 = src.asU8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Compare packed unsigned 8-bit integers in a and b, and store packed maximum values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_max_epu8&expand=3629)
 */
public fun mmMaskzMaxEpu8(k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val max = mmMaxEpu8(a, b).asU8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = max; val simdArg2 = Simd<UByte>(List(16) { 0u.toUByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Compare packed signed 16-bit integers in a and b, and store packed maximum values in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_max_epi16&expand=3573)
 */
public fun mm512MaxEpi16(a: M512i, b: M512i): M512i = simdImax(a.asI16x32(), b.asI16x32()).asM512i()

/**
 * Compare packed signed 16-bit integers in a and b, and store packed maximum values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_max_epi16&expand=3571)
 */
public fun mm512MaskMaxEpi16(src: M512i, k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val max = mm512MaxEpi16(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = max; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Compare packed signed 16-bit integers in a and b, and store packed maximum values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_max_epi16&expand=3572)
 */
public fun mm512MaskzMaxEpi16(k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val max = mm512MaxEpi16(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = max; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Compare packed signed 16-bit integers in a and b, and store packed maximum values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_max_epi16&expand=3568)
 */
public fun mm256MaskMaxEpi16(src: M256i, k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val max = mm256MaxEpi16(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = max; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Compare packed signed 16-bit integers in a and b, and store packed maximum values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_max_epi16&expand=3569)
 */
public fun mm256MaskzMaxEpi16(k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val max = mm256MaxEpi16(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = max; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Compare packed signed 16-bit integers in a and b, and store packed maximum values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_max_epi16&expand=3565)
 */
public fun mmMaskMaxEpi16(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val max = mmMaxEpi16(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = max; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Compare packed signed 16-bit integers in a and b, and store packed maximum values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_max_epi16&expand=3566)
 */
public fun mmMaskzMaxEpi16(k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val max = mmMaxEpi16(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = max; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Compare packed signed 8-bit integers in a and b, and store packed maximum values in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_max_epi8&expand=3600)
 */
public fun mm512MaxEpi8(a: M512i, b: M512i): M512i = simdImax(a.asI8x64(), b.asI8x64()).asM512i()

/**
 * Compare packed signed 8-bit integers in a and b, and store packed maximum values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_max_epi8&expand=3598)
 */
public fun mm512MaskMaxEpi8(src: M512i, k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val max = mm512MaxEpi8(a, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = max; val simdArg2 = src.asI8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Compare packed signed 8-bit integers in a and b, and store packed maximum values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_max_epi8&expand=3599)
 */
public fun mm512MaskzMaxEpi8(k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val max = mm512MaxEpi8(a, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = max; val simdArg2 = Simd<Byte>(List(64) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Compare packed signed 8-bit integers in a and b, and store packed maximum values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_max_epi8&expand=3595)
 */
public fun mm256MaskMaxEpi8(src: M256i, k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val max = mm256MaxEpi8(a, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = max; val simdArg2 = src.asI8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Compare packed signed 8-bit integers in a and b, and store packed maximum values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_max_epi8&expand=3596)
 */
public fun mm256MaskzMaxEpi8(k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val max = mm256MaxEpi8(a, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = max; val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Compare packed signed 8-bit integers in a and b, and store packed maximum values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_max_epi8&expand=3592)
 */
public fun mmMaskMaxEpi8(src: M128i, k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val max = mmMaxEpi8(a, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = max; val simdArg2 = src.asI8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Compare packed signed 8-bit integers in a and b, and store packed maximum values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_max_epi8&expand=3593)
 */
public fun mmMaskzMaxEpi8(k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val max = mmMaxEpi8(a, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = max; val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Compare packed unsigned 16-bit integers in a and b, and store packed minimum values in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_min_epu16&expand=3723)
 */
public fun mm512MinEpu16(a: M512i, b: M512i): M512i = simdImin(a.asU16x32(), b.asU16x32()).asM512i()

/**
 * Compare packed unsigned 16-bit integers in a and b, and store packed minimum values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_min_epu16&expand=3721)
 */
public fun mm512MaskMinEpu16(src: M512i, k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val min = mm512MinEpu16(a, b).asU16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = min; val simdArg2 = src.asU16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Compare packed unsigned 16-bit integers in a and b, and store packed minimum values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_min_epu16&expand=3722)
 */
public fun mm512MaskzMinEpu16(k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val min = mm512MinEpu16(a, b).asU16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = min; val simdArg2 = Simd<UShort>(List(32) { 0u.toUShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Compare packed unsigned 16-bit integers in a and b, and store packed minimum values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_min_epu16&expand=3718)
 */
public fun mm256MaskMinEpu16(src: M256i, k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val min = mm256MinEpu16(a, b).asU16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = min; val simdArg2 = src.asU16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Compare packed unsigned 16-bit integers in a and b, and store packed minimum values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_min_epu16&expand=3719)
 */
public fun mm256MaskzMinEpu16(k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val min = mm256MinEpu16(a, b).asU16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = min; val simdArg2 = Simd<UShort>(List(16) { 0u.toUShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Compare packed unsigned 16-bit integers in a and b, and store packed minimum values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_min_epu16&expand=3715)
 */
public fun mmMaskMinEpu16(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val min = mmMinEpu16(a, b).asU16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = min; val simdArg2 = src.asU16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Compare packed unsigned 16-bit integers in a and b, and store packed minimum values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_min_epu16&expand=3716)
 */
public fun mmMaskzMinEpu16(k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val min = mmMinEpu16(a, b).asU16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = min; val simdArg2 = Simd<UShort>(List(8) { 0u.toUShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Compare packed unsigned 8-bit integers in a and b, and store packed minimum values in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_min_epu8&expand=3750)
 */
public fun mm512MinEpu8(a: M512i, b: M512i): M512i = simdImin(a.asU8x64(), b.asU8x64()).asM512i()

/**
 * Compare packed unsigned 8-bit integers in a and b, and store packed minimum values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_min_epu8&expand=3748)
 */
public fun mm512MaskMinEpu8(src: M512i, k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val min = mm512MinEpu8(a, b).asU8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = min; val simdArg2 = src.asU8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Compare packed unsigned 8-bit integers in a and b, and store packed minimum values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_min_epu8&expand=3749)
 */
public fun mm512MaskzMinEpu8(k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val min = mm512MinEpu8(a, b).asU8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = min; val simdArg2 = Simd<UByte>(List(64) { 0u.toUByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Compare packed unsigned 8-bit integers in a and b, and store packed minimum values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_min_epu8&expand=3745)
 */
public fun mm256MaskMinEpu8(src: M256i, k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val min = mm256MinEpu8(a, b).asU8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = min; val simdArg2 = src.asU8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Compare packed unsigned 8-bit integers in a and b, and store packed minimum values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_min_epu8&expand=3746)
 */
public fun mm256MaskzMinEpu8(k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val min = mm256MinEpu8(a, b).asU8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = min; val simdArg2 = Simd<UByte>(List(32) { 0u.toUByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Compare packed unsigned 8-bit integers in a and b, and store packed minimum values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_min_epu8&expand=3742)
 */
public fun mmMaskMinEpu8(src: M128i, k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val min = mmMinEpu8(a, b).asU8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = min; val simdArg2 = src.asU8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Compare packed unsigned 8-bit integers in a and b, and store packed minimum values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_min_epu8&expand=3743)
 */
public fun mmMaskzMinEpu8(k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val min = mmMinEpu8(a, b).asU8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = min; val simdArg2 = Simd<UByte>(List(16) { 0u.toUByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Compare packed signed 16-bit integers in a and b, and store packed minimum values in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_min_epi16&expand=3687)
 */
public fun mm512MinEpi16(a: M512i, b: M512i): M512i = simdImin(a.asI16x32(), b.asI16x32()).asM512i()

/**
 * Compare packed signed 16-bit integers in a and b, and store packed minimum values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_min_epi16&expand=3685)
 */
public fun mm512MaskMinEpi16(src: M512i, k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val min = mm512MinEpi16(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = min; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Compare packed signed 16-bit integers in a and b, and store packed minimum values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_min_epi16&expand=3686)
 */
public fun mm512MaskzMinEpi16(k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val min = mm512MinEpi16(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = min; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Compare packed signed 16-bit integers in a and b, and store packed minimum values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_min_epi16&expand=3682)
 */
public fun mm256MaskMinEpi16(src: M256i, k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val min = mm256MinEpi16(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = min; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Compare packed signed 16-bit integers in a and b, and store packed minimum values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_min_epi16&expand=3683)
 */
public fun mm256MaskzMinEpi16(k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val min = mm256MinEpi16(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = min; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Compare packed signed 16-bit integers in a and b, and store packed minimum values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_min_epi16&expand=3679)
 */
public fun mmMaskMinEpi16(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val min = mmMinEpi16(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = min; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Compare packed signed 16-bit integers in a and b, and store packed minimum values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_min_epi16&expand=3680)
 */
public fun mmMaskzMinEpi16(k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val min = mmMinEpi16(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = min; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Compare packed signed 8-bit integers in a and b, and store packed minimum values in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_min_epi8&expand=3714)
 */
public fun mm512MinEpi8(a: M512i, b: M512i): M512i = simdImin(a.asI8x64(), b.asI8x64()).asM512i()

/**
 * Compare packed signed 8-bit integers in a and b, and store packed minimum values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_min_epi8&expand=3712)
 */
public fun mm512MaskMinEpi8(src: M512i, k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val min = mm512MinEpi8(a, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = min; val simdArg2 = src.asI8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Compare packed signed 8-bit integers in a and b, and store packed minimum values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_min_epi8&expand=3713)
 */
public fun mm512MaskzMinEpi8(k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val min = mm512MinEpi8(a, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = min; val simdArg2 = Simd<Byte>(List(64) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Compare packed signed 8-bit integers in a and b, and store packed minimum values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_min_epi8&expand=3709)
 */
public fun mm256MaskMinEpi8(src: M256i, k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val min = mm256MinEpi8(a, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = min; val simdArg2 = src.asI8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Compare packed signed 8-bit integers in a and b, and store packed minimum values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_min_epi8&expand=3710)
 */
public fun mm256MaskzMinEpi8(k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val min = mm256MinEpi8(a, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = min; val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Compare packed signed 8-bit integers in a and b, and store packed minimum values in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_min_epi8&expand=3706)
 */
public fun mmMaskMinEpi8(src: M128i, k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val min = mmMinEpi8(a, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = min; val simdArg2 = src.asI8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Compare packed signed 8-bit integers in a and b, and store packed minimum values in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_min_epi8&expand=3707)
 */
public fun mmMaskzMinEpi8(k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val min = mmMinEpi8(a, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = min; val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Compare packed unsigned 16-bit integers in a and b for less-than, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmplt_epu16_mask&expand=1050)
 */
public fun mm512CmpltEpu16Mask(a: M512i, b: M512i): Mmask32 = run { val simdArg0 = run { val simdArg0 = a.asU16x32(); val simdArg1 = b.asU16x32(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x32>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x32>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 16-bit integers in a and b for less-than, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmplt_epu16_mask&expand=1051)
 */
public fun mm512MaskCmpltEpu16Mask(k1: Mmask32, a: M512i, b: M512i): Mmask32 = mm512MaskCmpEpu16Mask(k1, a, b, MM_CMPINT_LT)

/**
 * Compare packed unsigned 16-bit integers in a and b for less-than, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmplt_epu16_mask&expand=1050)
 */
public fun mm256CmpltEpu16Mask(a: M256i, b: M256i): Mmask16 = run { val simdArg0 = run { val simdArg0 = a.asU16x16(); val simdArg1 = b.asU16x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x16>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x16>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 16-bit integers in a and b for less-than, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmplt_epu16_mask&expand=1049)
 */
public fun mm256MaskCmpltEpu16Mask(k1: Mmask16, a: M256i, b: M256i): Mmask16 = mm256MaskCmpEpu16Mask(k1, a, b, MM_CMPINT_LT)

/**
 * Compare packed unsigned 16-bit integers in a and b for less-than, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmplt_epu16_mask&expand=1018)
 */
public fun mmCmpltEpu16Mask(a: M128i, b: M128i): Mmask8 = run { val simdArg0 = run { val simdArg0 = a.asU16x8(); val simdArg1 = b.asU16x8(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 16-bit integers in a and b for less-than, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmplt_epu16_mask&expand=1019)
 */
public fun mmMaskCmpltEpu16Mask(k1: Mmask8, a: M128i, b: M128i): Mmask8 = mmMaskCmpEpu16Mask(k1, a, b, MM_CMPINT_LT)

/**
 * Compare packed unsigned 8-bit integers in a and b for less-than, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=mm512CmpltEpu8Mask&expand=1068)
 */
public fun mm512CmpltEpu8Mask(a: M512i, b: M512i): Mmask64 = run { val simdArg0 = run { val simdArg0 = a.asU8x64(); val simdArg1 = b.asU8x64(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x64>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x64>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x64>()).alignBytes)); res } }; 0uL }

/**
 * Compare packed unsigned 8-bit integers in a and b for less-than, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmplt_epu8_mask&expand=1069)
 */
public fun mm512MaskCmpltEpu8Mask(k1: Mmask64, a: M512i, b: M512i): Mmask64 = mm512MaskCmpEpu8Mask(k1, a, b, MM_CMPINT_LT)

/**
 * Compare packed unsigned 8-bit integers in a and b for less-than, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmplt_epu8_mask&expand=1066)
 */
public fun mm256CmpltEpu8Mask(a: M256i, b: M256i): Mmask32 = run { val simdArg0 = run { val simdArg0 = a.asU8x32(); val simdArg1 = b.asU8x32(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x32>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x32>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 8-bit integers in a and b for less-than, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmplt_epu8_mask&expand=1067)
 */
public fun mm256MaskCmpltEpu8Mask(k1: Mmask32, a: M256i, b: M256i): Mmask32 = mm256MaskCmpEpu8Mask(k1, a, b, MM_CMPINT_LT)

/**
 * Compare packed unsigned 8-bit integers in a and b for less-than, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmplt_epu8_mask&expand=1064)
 */
public fun mmCmpltEpu8Mask(a: M128i, b: M128i): Mmask16 = run { val simdArg0 = run { val simdArg0 = a.asU8x16(); val simdArg1 = b.asU8x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x16>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 8-bit integers in a and b for less-than, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmplt_epu8_mask&expand=1065)
 */
public fun mmMaskCmpltEpu8Mask(k1: Mmask16, a: M128i, b: M128i): Mmask16 = mmMaskCmpEpu8Mask(k1, a, b, MM_CMPINT_LT)

/**
 * Compare packed signed 16-bit integers in a and b for less-than, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmplt_epi16_mask&expand=1022)
 */
public fun mm512CmpltEpi16Mask(a: M512i, b: M512i): Mmask32 = run { val simdArg0 = run { val simdArg0 = a.asI16x32(); val simdArg1 = b.asI16x32(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 16-bit integers in a and b for less-than, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmplt_epi16_mask&expand=1023)
 */
public fun mm512MaskCmpltEpi16Mask(k1: Mmask32, a: M512i, b: M512i): Mmask32 = mm512MaskCmpEpi16Mask(k1, a, b, MM_CMPINT_LT)

/**
 * Compare packed signed 16-bit integers in a and b for less-than, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmplt_epi16_mask&expand=1020)
 */
public fun mm256CmpltEpi16Mask(a: M256i, b: M256i): Mmask16 = run { val simdArg0 = run { val simdArg0 = a.asI16x16(); val simdArg1 = b.asI16x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 16-bit integers in a and b for less-than, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmplt_epi16_mask&expand=1021)
 */
public fun mm256MaskCmpltEpi16Mask(k1: Mmask16, a: M256i, b: M256i): Mmask16 = mm256MaskCmpEpi16Mask(k1, a, b, MM_CMPINT_LT)

/**
 * Compare packed signed 16-bit integers in a and b for less-than, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmplt_epi16_mask&expand=1018)
 */
public fun mmCmpltEpi16Mask(a: M128i, b: M128i): Mmask8 = run { val simdArg0 = run { val simdArg0 = a.asI16x8(); val simdArg1 = b.asI16x8(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 16-bit integers in a and b for less-than, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmplt_epi16_mask&expand=1019)
 */
public fun mmMaskCmpltEpi16Mask(k1: Mmask8, a: M128i, b: M128i): Mmask8 = mmMaskCmpEpi16Mask(k1, a, b, MM_CMPINT_LT)

/**
 * Compare packed signed 8-bit integers in a and b for less-than, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmplt_epi8_mask&expand=1044)
 */
public fun mm512CmpltEpi8Mask(a: M512i, b: M512i): Mmask64 = run { val simdArg0 = run { val simdArg0 = a.asI8x64(); val simdArg1 = b.asI8x64(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x64>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); res } }; 0uL }

/**
 * Compare packed signed 8-bit integers in a and b for less-than, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmplt_epi8_mask&expand=1045)
 */
public fun mm512MaskCmpltEpi8Mask(k1: Mmask64, a: M512i, b: M512i): Mmask64 = mm512MaskCmpEpi8Mask(k1, a, b, MM_CMPINT_LT)

/**
 * Compare packed signed 8-bit integers in a and b for less-than, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmplt_epi8_mask&expand=1042)
 */
public fun mm256CmpltEpi8Mask(a: M256i, b: M256i): Mmask32 = run { val simdArg0 = run { val simdArg0 = a.asI8x32(); val simdArg1 = b.asI8x32(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x32>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x32>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 8-bit integers in a and b for less-than, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmplt_epi8_mask&expand=1043)
 */
public fun mm256MaskCmpltEpi8Mask(k1: Mmask32, a: M256i, b: M256i): Mmask32 = mm256MaskCmpEpi8Mask(k1, a, b, MM_CMPINT_LT)

/**
 * Compare packed signed 8-bit integers in a and b for less-than, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmplt_epi8_mask&expand=1040)
 */
public fun mmCmpltEpi8Mask(a: M128i, b: M128i): Mmask16 = run { val simdArg0 = run { val simdArg0 = a.asI8x16(); val simdArg1 = b.asI8x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 8-bit integers in a and b for less-than, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmplt_epi8_mask&expand=1041)
 */
public fun mmMaskCmpltEpi8Mask(k1: Mmask16, a: M128i, b: M128i): Mmask16 = mmMaskCmpEpi8Mask(k1, a, b, MM_CMPINT_LT)

/**
 * Compare packed unsigned 16-bit integers in a and b for greater-than, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmpgt_epu16_mask&expand=927)
 */
public fun mm512CmpgtEpu16Mask(a: M512i, b: M512i): Mmask32 = run { val simdArg0 = run { val simdArg0 = a.asU16x32(); val simdArg1 = b.asU16x32(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x32>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x32>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 16-bit integers in a and b for greater-than, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmpgt_epu16_mask&expand=928)
 */
public fun mm512MaskCmpgtEpu16Mask(k1: Mmask32, a: M512i, b: M512i): Mmask32 = mm512MaskCmpEpu16Mask(k1, a, b, MM_CMPINT_NLE)

/**
 * Compare packed unsigned 16-bit integers in a and b for greater-than, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmpgt_epu16_mask&expand=925)
 */
public fun mm256CmpgtEpu16Mask(a: M256i, b: M256i): Mmask16 = run { val simdArg0 = run { val simdArg0 = a.asU16x16(); val simdArg1 = b.asU16x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x16>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x16>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 16-bit integers in a and b for greater-than, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmpgt_epu16_mask&expand=926)
 */
public fun mm256MaskCmpgtEpu16Mask(k1: Mmask16, a: M256i, b: M256i): Mmask16 = mm256MaskCmpEpu16Mask(k1, a, b, MM_CMPINT_NLE)

/**
 * Compare packed unsigned 16-bit integers in a and b for greater-than, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpgt_epu16_mask&expand=923)
 */
public fun mmCmpgtEpu16Mask(a: M128i, b: M128i): Mmask8 = run { val simdArg0 = run { val simdArg0 = a.asU16x8(); val simdArg1 = b.asU16x8(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 16-bit integers in a and b for greater-than, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmpgt_epu16_mask&expand=924)
 */
public fun mmMaskCmpgtEpu16Mask(k1: Mmask8, a: M128i, b: M128i): Mmask8 = mmMaskCmpEpu16Mask(k1, a, b, MM_CMPINT_NLE)

/**
 * Compare packed unsigned 8-bit integers in a and b for greater-than, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmpgt_epu8_mask&expand=945)
 */
public fun mm512CmpgtEpu8Mask(a: M512i, b: M512i): Mmask64 = run { val simdArg0 = run { val simdArg0 = a.asU8x64(); val simdArg1 = b.asU8x64(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x64>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x64>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x64>()).alignBytes)); res } }; 0uL }

/**
 * Compare packed unsigned 8-bit integers in a and b for greater-than, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmpgt_epu8_mask&expand=946)
 */
public fun mm512MaskCmpgtEpu8Mask(k1: Mmask64, a: M512i, b: M512i): Mmask64 = mm512MaskCmpEpu8Mask(k1, a, b, MM_CMPINT_NLE)

/**
 * Compare packed unsigned 8-bit integers in a and b for greater-than, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmpgt_epu8_mask&expand=943)
 */
public fun mm256CmpgtEpu8Mask(a: M256i, b: M256i): Mmask32 = run { val simdArg0 = run { val simdArg0 = a.asU8x32(); val simdArg1 = b.asU8x32(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x32>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x32>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 8-bit integers in a and b for greater-than, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmpgt_epu8_mask&expand=944)
 */
public fun mm256MaskCmpgtEpu8Mask(k1: Mmask32, a: M256i, b: M256i): Mmask32 = mm256MaskCmpEpu8Mask(k1, a, b, MM_CMPINT_NLE)

/**
 * Compare packed unsigned 8-bit integers in a and b for greater-than, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpgt_epu8_mask&expand=941)
 */
public fun mmCmpgtEpu8Mask(a: M128i, b: M128i): Mmask16 = run { val simdArg0 = run { val simdArg0 = a.asU8x16(); val simdArg1 = b.asU8x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x16>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 8-bit integers in a and b for greater-than, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmpgt_epu8_mask&expand=942)
 */
public fun mmMaskCmpgtEpu8Mask(k1: Mmask16, a: M128i, b: M128i): Mmask16 = mmMaskCmpEpu8Mask(k1, a, b, MM_CMPINT_NLE)

/**
 * Compare packed signed 16-bit integers in a and b for greater-than, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmpgt_epi16_mask&expand=897)
 */
public fun mm512CmpgtEpi16Mask(a: M512i, b: M512i): Mmask32 = run { val simdArg0 = run { val simdArg0 = a.asI16x32(); val simdArg1 = b.asI16x32(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 16-bit integers in a and b for greater-than, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmpgt_epi16_mask&expand=898)
 */
public fun mm512MaskCmpgtEpi16Mask(k1: Mmask32, a: M512i, b: M512i): Mmask32 = mm512MaskCmpEpi16Mask(k1, a, b, MM_CMPINT_NLE)

/**
 * Compare packed signed 16-bit integers in a and b for greater-than, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmpgt_epi16_mask&expand=895)
 */
public fun mm256CmpgtEpi16Mask(a: M256i, b: M256i): Mmask16 = run { val simdArg0 = run { val simdArg0 = a.asI16x16(); val simdArg1 = b.asI16x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 16-bit integers in a and b for greater-than, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmpgt_epi16_mask&expand=896)
 */
public fun mm256MaskCmpgtEpi16Mask(k1: Mmask16, a: M256i, b: M256i): Mmask16 = mm256MaskCmpEpi16Mask(k1, a, b, MM_CMPINT_NLE)

/**
 * Compare packed signed 16-bit integers in a and b for greater-than, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpgt_epi16_mask&expand=893)
 */
public fun mmCmpgtEpi16Mask(a: M128i, b: M128i): Mmask8 = run { val simdArg0 = run { val simdArg0 = a.asI16x8(); val simdArg1 = b.asI16x8(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 16-bit integers in a and b for greater-than, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmpgt_epi16_mask&expand=894)
 */
public fun mmMaskCmpgtEpi16Mask(k1: Mmask8, a: M128i, b: M128i): Mmask8 = mmMaskCmpEpi16Mask(k1, a, b, MM_CMPINT_NLE)

/**
 * Compare packed signed 8-bit integers in a and b for greater-than, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmpgt_epi8_mask&expand=921)
 */
public fun mm512CmpgtEpi8Mask(a: M512i, b: M512i): Mmask64 = run { val simdArg0 = run { val simdArg0 = a.asI8x64(); val simdArg1 = b.asI8x64(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x64>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); res } }; 0uL }

/**
 * Compare packed signed 8-bit integers in a and b for greater-than, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmpgt_epi8_mask&expand=922)
 */
public fun mm512MaskCmpgtEpi8Mask(k1: Mmask64, a: M512i, b: M512i): Mmask64 = mm512MaskCmpEpi8Mask(k1, a, b, MM_CMPINT_NLE)

/**
 * Compare packed signed 8-bit integers in a and b for greater-than, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmpgt_epi8_mask&expand=919)
 */
public fun mm256CmpgtEpi8Mask(a: M256i, b: M256i): Mmask32 = run { val simdArg0 = run { val simdArg0 = a.asI8x32(); val simdArg1 = b.asI8x32(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x32>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x32>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 8-bit integers in a and b for greater-than, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmpgt_epi8_mask&expand=920)
 */
public fun mm256MaskCmpgtEpi8Mask(k1: Mmask32, a: M256i, b: M256i): Mmask32 = mm256MaskCmpEpi8Mask(k1, a, b, MM_CMPINT_NLE)

/**
 * Compare packed signed 8-bit integers in a and b for greater-than, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpgt_epi8_mask&expand=917)
 */
public fun mmCmpgtEpi8Mask(a: M128i, b: M128i): Mmask16 = run { val simdArg0 = run { val simdArg0 = a.asI8x16(); val simdArg1 = b.asI8x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 8-bit integers in a and b for greater-than, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmpgt_epi8_mask&expand=918)
 */
public fun mmMaskCmpgtEpi8Mask(k1: Mmask16, a: M128i, b: M128i): Mmask16 = mmMaskCmpEpi8Mask(k1, a, b, MM_CMPINT_NLE)

/**
 * Compare packed unsigned 16-bit integers in a and b for less-than-or-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmple_epu16_mask&expand=989)
 */
public fun mm512CmpleEpu16Mask(a: M512i, b: M512i): Mmask32 = run { val simdArg0 = run { val simdArg0 = a.asU16x32(); val simdArg1 = b.asU16x32(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x32>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x32>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 16-bit integers in a and b for less-than-or-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmple_epu16_mask&expand=990)
 */
public fun mm512MaskCmpleEpu16Mask(k1: Mmask32, a: M512i, b: M512i): Mmask32 = mm512MaskCmpEpu16Mask(k1, a, b, MM_CMPINT_LE)

/**
 * Compare packed unsigned 16-bit integers in a and b for less-than-or-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmple_epu16_mask&expand=987)
 */
public fun mm256CmpleEpu16Mask(a: M256i, b: M256i): Mmask16 = run { val simdArg0 = run { val simdArg0 = a.asU16x16(); val simdArg1 = b.asU16x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x16>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x16>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 16-bit integers in a and b for less-than-or-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmple_epu16_mask&expand=988)
 */
public fun mm256MaskCmpleEpu16Mask(k1: Mmask16, a: M256i, b: M256i): Mmask16 = mm256MaskCmpEpu16Mask(k1, a, b, MM_CMPINT_LE)

/**
 * Compare packed unsigned 16-bit integers in a and b for less-than-or-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmple_epu16_mask&expand=985)
 */
public fun mmCmpleEpu16Mask(a: M128i, b: M128i): Mmask8 = run { val simdArg0 = run { val simdArg0 = a.asU16x8(); val simdArg1 = b.asU16x8(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 16-bit integers in a and b for less-than-or-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmple_epu16_mask&expand=986)
 */
public fun mmMaskCmpleEpu16Mask(k1: Mmask8, a: M128i, b: M128i): Mmask8 = mmMaskCmpEpu16Mask(k1, a, b, MM_CMPINT_LE)

/**
 * Compare packed unsigned 8-bit integers in a and b for less-than-or-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmple_epu8_mask&expand=1007)
 */
public fun mm512CmpleEpu8Mask(a: M512i, b: M512i): Mmask64 = run { val simdArg0 = run { val simdArg0 = a.asU8x64(); val simdArg1 = b.asU8x64(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x64>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x64>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x64>()).alignBytes)); res } }; 0uL }

/**
 * Compare packed unsigned 8-bit integers in a and b for less-than-or-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmple_epu8_mask&expand=1008)
 */
public fun mm512MaskCmpleEpu8Mask(k1: Mmask64, a: M512i, b: M512i): Mmask64 = mm512MaskCmpEpu8Mask(k1, a, b, MM_CMPINT_LE)

/**
 * Compare packed unsigned 8-bit integers in a and b for less-than-or-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmple_epu8_mask&expand=1005)
 */
public fun mm256CmpleEpu8Mask(a: M256i, b: M256i): Mmask32 = run { val simdArg0 = run { val simdArg0 = a.asU8x32(); val simdArg1 = b.asU8x32(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x32>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x32>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 8-bit integers in a and b for less-than-or-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmple_epu8_mask&expand=1006)
 */
public fun mm256MaskCmpleEpu8Mask(k1: Mmask32, a: M256i, b: M256i): Mmask32 = mm256MaskCmpEpu8Mask(k1, a, b, MM_CMPINT_LE)

/**
 * Compare packed unsigned 8-bit integers in a and b for less-than-or-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmple_epu8_mask&expand=1003)
 */
public fun mmCmpleEpu8Mask(a: M128i, b: M128i): Mmask16 = run { val simdArg0 = run { val simdArg0 = a.asU8x16(); val simdArg1 = b.asU8x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x16>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 8-bit integers in a and b for less-than-or-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmple_epu8_mask&expand=1004)
 */
public fun mmMaskCmpleEpu8Mask(k1: Mmask16, a: M128i, b: M128i): Mmask16 = mmMaskCmpEpu8Mask(k1, a, b, MM_CMPINT_LE)

/**
 * Compare packed signed 16-bit integers in a and b for less-than-or-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmple_epi16_mask&expand=965)
 */
public fun mm512CmpleEpi16Mask(a: M512i, b: M512i): Mmask32 = run { val simdArg0 = run { val simdArg0 = a.asI16x32(); val simdArg1 = b.asI16x32(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 16-bit integers in a and b for less-than-or-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmple_epi16_mask&expand=966)
 */
public fun mm512MaskCmpleEpi16Mask(k1: Mmask32, a: M512i, b: M512i): Mmask32 = mm512MaskCmpEpi16Mask(k1, a, b, MM_CMPINT_LE)

/**
 * Compare packed signed 16-bit integers in a and b for less-than-or-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmple_epi16_mask&expand=963)
 */
public fun mm256CmpleEpi16Mask(a: M256i, b: M256i): Mmask16 = run { val simdArg0 = run { val simdArg0 = a.asI16x16(); val simdArg1 = b.asI16x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 16-bit integers in a and b for less-than-or-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmple_epi16_mask&expand=964)
 */
public fun mm256MaskCmpleEpi16Mask(k1: Mmask16, a: M256i, b: M256i): Mmask16 = mm256MaskCmpEpi16Mask(k1, a, b, MM_CMPINT_LE)

/**
 * Compare packed signed 16-bit integers in a and b for less-than-or-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmple_epi16_mask&expand=961)
 */
public fun mmCmpleEpi16Mask(a: M128i, b: M128i): Mmask8 = run { val simdArg0 = run { val simdArg0 = a.asI16x8(); val simdArg1 = b.asI16x8(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 16-bit integers in a and b for less-than-or-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmple_epi16_mask&expand=962)
 */
public fun mmMaskCmpleEpi16Mask(k1: Mmask8, a: M128i, b: M128i): Mmask8 = mmMaskCmpEpi16Mask(k1, a, b, MM_CMPINT_LE)

/**
 * Compare packed signed 8-bit integers in a and b for less-than-or-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmple_epi8_mask&expand=983)
 */
public fun mm512CmpleEpi8Mask(a: M512i, b: M512i): Mmask64 = run { val simdArg0 = run { val simdArg0 = a.asI8x64(); val simdArg1 = b.asI8x64(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x64>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); res } }; 0uL }

/**
 * Compare packed signed 8-bit integers in a and b for less-than-or-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmple_epi8_mask&expand=984)
 */
public fun mm512MaskCmpleEpi8Mask(k1: Mmask64, a: M512i, b: M512i): Mmask64 = mm512MaskCmpEpi8Mask(k1, a, b, MM_CMPINT_LE)

/**
 * Compare packed signed 8-bit integers in a and b for less-than-or-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmple_epi8_mask&expand=981)
 */
public fun mm256CmpleEpi8Mask(a: M256i, b: M256i): Mmask32 = run { val simdArg0 = run { val simdArg0 = a.asI8x32(); val simdArg1 = b.asI8x32(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x32>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x32>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 8-bit integers in a and b for less-than-or-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmple_epi8_mask&expand=982)
 */
public fun mm256MaskCmpleEpi8Mask(k1: Mmask32, a: M256i, b: M256i): Mmask32 = mm256MaskCmpEpi8Mask(k1, a, b, MM_CMPINT_LE)

/**
 * Compare packed signed 8-bit integers in a and b for less-than-or-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmple_epi8_mask&expand=979)
 */
public fun mmCmpleEpi8Mask(a: M128i, b: M128i): Mmask16 = run { val simdArg0 = run { val simdArg0 = a.asI8x16(); val simdArg1 = b.asI8x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 8-bit integers in a and b for less-than-or-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmple_epi8_mask&expand=980)
 */
public fun mmMaskCmpleEpi8Mask(k1: Mmask16, a: M128i, b: M128i): Mmask16 = mmMaskCmpEpi8Mask(k1, a, b, MM_CMPINT_LE)

/**
 * Compare packed unsigned 16-bit integers in a and b for greater-than-or-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmpge_epu16_mask&expand=867)
 */
public fun mm512CmpgeEpu16Mask(a: M512i, b: M512i): Mmask32 = run { val simdArg0 = run { val simdArg0 = a.asU16x32(); val simdArg1 = b.asU16x32(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x32>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x32>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 16-bit integers in a and b for greater-than-or-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmpge_epu16_mask&expand=868)
 */
public fun mm512MaskCmpgeEpu16Mask(k1: Mmask32, a: M512i, b: M512i): Mmask32 = mm512MaskCmpEpu16Mask(k1, a, b, MM_CMPINT_NLT)

/**
 * Compare packed unsigned 16-bit integers in a and b for greater-than-or-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmpge_epu16_mask&expand=865)
 */
public fun mm256CmpgeEpu16Mask(a: M256i, b: M256i): Mmask16 = run { val simdArg0 = run { val simdArg0 = a.asU16x16(); val simdArg1 = b.asU16x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x16>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x16>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 16-bit integers in a and b for greater-than-or-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmpge_epu16_mask&expand=866)
 */
public fun mm256MaskCmpgeEpu16Mask(k1: Mmask16, a: M256i, b: M256i): Mmask16 = mm256MaskCmpEpu16Mask(k1, a, b, MM_CMPINT_NLT)

/**
 * Compare packed unsigned 16-bit integers in a and b for greater-than-or-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpge_epu16_mask&expand=863)
 */
public fun mmCmpgeEpu16Mask(a: M128i, b: M128i): Mmask8 = run { val simdArg0 = run { val simdArg0 = a.asU16x8(); val simdArg1 = b.asU16x8(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 16-bit integers in a and b for greater-than-or-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmpge_epu16_mask&expand=864)
 */
public fun mmMaskCmpgeEpu16Mask(k1: Mmask8, a: M128i, b: M128i): Mmask8 = mmMaskCmpEpu16Mask(k1, a, b, MM_CMPINT_NLT)

/**
 * Compare packed unsigned 8-bit integers in a and b for greater-than-or-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmpge_epu8_mask&expand=885)
 */
public fun mm512CmpgeEpu8Mask(a: M512i, b: M512i): Mmask64 = run { val simdArg0 = run { val simdArg0 = a.asU8x64(); val simdArg1 = b.asU8x64(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x64>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x64>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x64>()).alignBytes)); res } }; 0uL }

/**
 * Compare packed unsigned 8-bit integers in a and b for greater-than-or-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmpge_epu8_mask&expand=886)
 */
public fun mm512MaskCmpgeEpu8Mask(k1: Mmask64, a: M512i, b: M512i): Mmask64 = mm512MaskCmpEpu8Mask(k1, a, b, MM_CMPINT_NLT)

/**
 * Compare packed unsigned 8-bit integers in a and b for greater-than-or-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmpge_epu8_mask&expand=883)
 */
public fun mm256CmpgeEpu8Mask(a: M256i, b: M256i): Mmask32 = run { val simdArg0 = run { val simdArg0 = a.asU8x32(); val simdArg1 = b.asU8x32(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x32>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x32>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 8-bit integers in a and b for greater-than-or-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmpge_epu8_mask&expand=884)
 */
public fun mm256MaskCmpgeEpu8Mask(k1: Mmask32, a: M256i, b: M256i): Mmask32 = mm256MaskCmpEpu8Mask(k1, a, b, MM_CMPINT_NLT)

/**
 * Compare packed unsigned 8-bit integers in a and b for greater-than-or-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpge_epu8_mask&expand=881)
 */
public fun mmCmpgeEpu8Mask(a: M128i, b: M128i): Mmask16 = run { val simdArg0 = run { val simdArg0 = a.asU8x16(); val simdArg1 = b.asU8x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x16>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 8-bit integers in a and b for greater-than-or-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmpge_epu8_mask&expand=882)
 */
public fun mmMaskCmpgeEpu8Mask(k1: Mmask16, a: M128i, b: M128i): Mmask16 = mmMaskCmpEpu8Mask(k1, a, b, MM_CMPINT_NLT)

/**
 * Compare packed signed 16-bit integers in a and b for greater-than-or-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmpge_epi16_mask&expand=843)
 */
public fun mm512CmpgeEpi16Mask(a: M512i, b: M512i): Mmask32 = run { val simdArg0 = run { val simdArg0 = a.asI16x32(); val simdArg1 = b.asI16x32(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 16-bit integers in a and b for greater-than-or-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmpge_epi16_mask&expand=844)
 */
public fun mm512MaskCmpgeEpi16Mask(k1: Mmask32, a: M512i, b: M512i): Mmask32 = mm512MaskCmpEpi16Mask(k1, a, b, MM_CMPINT_NLT)

/**
 * Compare packed signed 16-bit integers in a and b for greater-than-or-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmpge_epi16_mask&expand=841)
 */
public fun mm256CmpgeEpi16Mask(a: M256i, b: M256i): Mmask16 = run { val simdArg0 = run { val simdArg0 = a.asI16x16(); val simdArg1 = b.asI16x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 16-bit integers in a and b for greater-than-or-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmpge_epi16_mask&expand=842)
 */
public fun mm256MaskCmpgeEpi16Mask(k1: Mmask16, a: M256i, b: M256i): Mmask16 = mm256MaskCmpEpi16Mask(k1, a, b, MM_CMPINT_NLT)

/**
 * Compare packed signed 16-bit integers in a and b for greater-than-or-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpge_epi16_mask&expand=839)
 */
public fun mmCmpgeEpi16Mask(a: M128i, b: M128i): Mmask8 = run { val simdArg0 = run { val simdArg0 = a.asI16x8(); val simdArg1 = b.asI16x8(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 16-bit integers in a and b for greater-than-or-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmpge_epi16_mask&expand=840)
 */
public fun mmMaskCmpgeEpi16Mask(k1: Mmask8, a: M128i, b: M128i): Mmask8 = mmMaskCmpEpi16Mask(k1, a, b, MM_CMPINT_NLT)

/**
 * Compare packed signed 8-bit integers in a and b for greater-than-or-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmpge_epi8_mask&expand=861)
 */
public fun mm512CmpgeEpi8Mask(a: M512i, b: M512i): Mmask64 = run { val simdArg0 = run { val simdArg0 = a.asI8x64(); val simdArg1 = b.asI8x64(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x64>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); res } }; 0uL }

/**
 * Compare packed signed 8-bit integers in a and b for greater-than-or-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmpge_epi8_mask&expand=862)
 */
public fun mm512MaskCmpgeEpi8Mask(k1: Mmask64, a: M512i, b: M512i): Mmask64 = mm512MaskCmpEpi8Mask(k1, a, b, MM_CMPINT_NLT)

/**
 * Compare packed signed 8-bit integers in a and b for greater-than-or-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmpge_epi8_mask&expand=859)
 */
public fun mm256CmpgeEpi8Mask(a: M256i, b: M256i): Mmask32 = run { val simdArg0 = run { val simdArg0 = a.asI8x32(); val simdArg1 = b.asI8x32(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x32>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x32>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 8-bit integers in a and b for greater-than-or-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmpge_epi8_mask&expand=860)
 */
public fun mm256MaskCmpgeEpi8Mask(k1: Mmask32, a: M256i, b: M256i): Mmask32 = mm256MaskCmpEpi8Mask(k1, a, b, MM_CMPINT_NLT)

/**
 * Compare packed signed 8-bit integers in a and b for greater-than-or-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpge_epi8_mask&expand=857)
 */
public fun mmCmpgeEpi8Mask(a: M128i, b: M128i): Mmask16 = run { val simdArg0 = run { val simdArg0 = a.asI8x16(); val simdArg1 = b.asI8x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 8-bit integers in a and b for greater-than-or-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmpge_epi8_mask&expand=858)
 */
public fun mmMaskCmpgeEpi8Mask(k1: Mmask16, a: M128i, b: M128i): Mmask16 = mmMaskCmpEpi8Mask(k1, a, b, MM_CMPINT_NLT)

/**
 * Compare packed unsigned 16-bit integers in a and b for equality, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmpeq_epu16_mask&expand=801)
 */
public fun mm512CmpeqEpu16Mask(a: M512i, b: M512i): Mmask32 = run { val simdArg0 = run { val simdArg0 = a.asU16x32(); val simdArg1 = b.asU16x32(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x32>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x32>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 16-bit integers in a and b for equality, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmpeq_epu16_mask&expand=802)
 */
public fun mm512MaskCmpeqEpu16Mask(k1: Mmask32, a: M512i, b: M512i): Mmask32 = mm512MaskCmpEpu16Mask(k1, a, b, MM_CMPINT_EQ)

/**
 * Compare packed unsigned 16-bit integers in a and b for equality, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmpeq_epu16_mask&expand=799)
 */
public fun mm256CmpeqEpu16Mask(a: M256i, b: M256i): Mmask16 = run { val simdArg0 = run { val simdArg0 = a.asU16x16(); val simdArg1 = b.asU16x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x16>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x16>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 16-bit integers in a and b for equality, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmpeq_epu16_mask&expand=800)
 */
public fun mm256MaskCmpeqEpu16Mask(k1: Mmask16, a: M256i, b: M256i): Mmask16 = mm256MaskCmpEpu16Mask(k1, a, b, MM_CMPINT_EQ)

/**
 * Compare packed unsigned 16-bit integers in a and b for equality, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpeq_epu16_mask&expand=797)
 */
public fun mmCmpeqEpu16Mask(a: M128i, b: M128i): Mmask8 = run { val simdArg0 = run { val simdArg0 = a.asU16x8(); val simdArg1 = b.asU16x8(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 16-bit integers in a and b for equality, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmpeq_epu16_mask&expand=798)
 */
public fun mmMaskCmpeqEpu16Mask(k1: Mmask8, a: M128i, b: M128i): Mmask8 = mmMaskCmpEpu16Mask(k1, a, b, MM_CMPINT_EQ)

/**
 * Compare packed unsigned 8-bit integers in a and b for equality, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmpeq_epu8_mask&expand=819)
 */
public fun mm512CmpeqEpu8Mask(a: M512i, b: M512i): Mmask64 = run { val simdArg0 = run { val simdArg0 = a.asU8x64(); val simdArg1 = b.asU8x64(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x64>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x64>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x64>()).alignBytes)); res } }; 0uL }

/**
 * Compare packed unsigned 8-bit integers in a and b for equality, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmpeq_epu8_mask&expand=820)
 */
public fun mm512MaskCmpeqEpu8Mask(k1: Mmask64, a: M512i, b: M512i): Mmask64 = mm512MaskCmpEpu8Mask(k1, a, b, MM_CMPINT_EQ)

/**
 * Compare packed unsigned 8-bit integers in a and b for equality, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmpeq_epu8_mask&expand=817)
 */
public fun mm256CmpeqEpu8Mask(a: M256i, b: M256i): Mmask32 = run { val simdArg0 = run { val simdArg0 = a.asU8x32(); val simdArg1 = b.asU8x32(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x32>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x32>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 8-bit integers in a and b for equality, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmpeq_epu8_mask&expand=818)
 */
public fun mm256MaskCmpeqEpu8Mask(k1: Mmask32, a: M256i, b: M256i): Mmask32 = mm256MaskCmpEpu8Mask(k1, a, b, MM_CMPINT_EQ)

/**
 * Compare packed unsigned 8-bit integers in a and b for equality, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpeq_epu8_mask&expand=815)
 */
public fun mmCmpeqEpu8Mask(a: M128i, b: M128i): Mmask16 = run { val simdArg0 = run { val simdArg0 = a.asU8x16(); val simdArg1 = b.asU8x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x16>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 8-bit integers in a and b for equality, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmpeq_epu8_mask&expand=816)
 */
public fun mmMaskCmpeqEpu8Mask(k1: Mmask16, a: M128i, b: M128i): Mmask16 = mmMaskCmpEpu8Mask(k1, a, b, MM_CMPINT_EQ)

/**
 * Compare packed signed 16-bit integers in a and b for equality, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmpeq_epi16_mask&expand=771)
 */
public fun mm512CmpeqEpi16Mask(a: M512i, b: M512i): Mmask32 = run { val simdArg0 = run { val simdArg0 = a.asI16x32(); val simdArg1 = b.asI16x32(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 16-bit integers in a and b for equality, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmpeq_epi16_mask&expand=772)
 */
public fun mm512MaskCmpeqEpi16Mask(k1: Mmask32, a: M512i, b: M512i): Mmask32 = mm512MaskCmpEpi16Mask(k1, a, b, MM_CMPINT_EQ)

/**
 * Compare packed signed 16-bit integers in a and b for equality, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmpeq_epi16_mask&expand=769)
 */
public fun mm256CmpeqEpi16Mask(a: M256i, b: M256i): Mmask16 = run { val simdArg0 = run { val simdArg0 = a.asI16x16(); val simdArg1 = b.asI16x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 16-bit integers in a and b for equality, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmpeq_epi16_mask&expand=770)
 */
public fun mm256MaskCmpeqEpi16Mask(k1: Mmask16, a: M256i, b: M256i): Mmask16 = mm256MaskCmpEpi16Mask(k1, a, b, MM_CMPINT_EQ)

/**
 * Compare packed signed 16-bit integers in a and b for equality, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpeq_epi16_mask&expand=767)
 */
public fun mmCmpeqEpi16Mask(a: M128i, b: M128i): Mmask8 = run { val simdArg0 = run { val simdArg0 = a.asI16x8(); val simdArg1 = b.asI16x8(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 16-bit integers in a and b for equality, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmpeq_epi16_mask&expand=768)
 */
public fun mmMaskCmpeqEpi16Mask(k1: Mmask8, a: M128i, b: M128i): Mmask8 = mmMaskCmpEpi16Mask(k1, a, b, MM_CMPINT_EQ)

/**
 * Compare packed signed 8-bit integers in a and b for equality, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmpeq_epi8_mask&expand=795)
 */
public fun mm512CmpeqEpi8Mask(a: M512i, b: M512i): Mmask64 = run { val simdArg0 = run { val simdArg0 = a.asI8x64(); val simdArg1 = b.asI8x64(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x64>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); res } }; 0uL }

/**
 * Compare packed signed 8-bit integers in a and b for equality, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmpeq_epi8_mask&expand=796)
 */
public fun mm512MaskCmpeqEpi8Mask(k1: Mmask64, a: M512i, b: M512i): Mmask64 = mm512MaskCmpEpi8Mask(k1, a, b, MM_CMPINT_EQ)

/**
 * Compare packed signed 8-bit integers in a and b for equality, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmpeq_epi8_mask&expand=793)
 */
public fun mm256CmpeqEpi8Mask(a: M256i, b: M256i): Mmask32 = run { val simdArg0 = run { val simdArg0 = a.asI8x32(); val simdArg1 = b.asI8x32(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x32>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x32>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 8-bit integers in a and b for equality, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmpeq_epi8_mask&expand=794)
 */
public fun mm256MaskCmpeqEpi8Mask(k1: Mmask32, a: M256i, b: M256i): Mmask32 = mm256MaskCmpEpi8Mask(k1, a, b, MM_CMPINT_EQ)

/**
 * Compare packed signed 8-bit integers in a and b for equality, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpeq_epi8_mask&expand=791)
 */
public fun mmCmpeqEpi8Mask(a: M128i, b: M128i): Mmask16 = run { val simdArg0 = run { val simdArg0 = a.asI8x16(); val simdArg1 = b.asI8x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 8-bit integers in a and b for equality, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmpeq_epi8_mask&expand=792)
 */
public fun mmMaskCmpeqEpi8Mask(k1: Mmask16, a: M128i, b: M128i): Mmask16 = mmMaskCmpEpi8Mask(k1, a, b, MM_CMPINT_EQ)

/**
 * Compare packed unsigned 16-bit integers in a and b for not-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmpneq_epu16_mask&expand=1106)
 */
public fun mm512CmpneqEpu16Mask(a: M512i, b: M512i): Mmask32 = run { val simdArg0 = run { val simdArg0 = a.asU16x32(); val simdArg1 = b.asU16x32(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x32>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x32>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 16-bit integers in a and b for not-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmpneq_epu16_mask&expand=1107)
 */
public fun mm512MaskCmpneqEpu16Mask(k1: Mmask32, a: M512i, b: M512i): Mmask32 = mm512MaskCmpEpu16Mask(k1, a, b, MM_CMPINT_NE)

/**
 * Compare packed unsigned 16-bit integers in a and b for not-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmpneq_epu16_mask&expand=1104)
 */
public fun mm256CmpneqEpu16Mask(a: M256i, b: M256i): Mmask16 = run { val simdArg0 = run { val simdArg0 = a.asU16x16(); val simdArg1 = b.asU16x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x16>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x16>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 16-bit integers in a and b for not-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmpneq_epu16_mask&expand=1105)
 */
public fun mm256MaskCmpneqEpu16Mask(k1: Mmask16, a: M256i, b: M256i): Mmask16 = mm256MaskCmpEpu16Mask(k1, a, b, MM_CMPINT_NE)

/**
 * Compare packed unsigned 16-bit integers in a and b for not-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpneq_epu16_mask&expand=1102)
 */
public fun mmCmpneqEpu16Mask(a: M128i, b: M128i): Mmask8 = run { val simdArg0 = run { val simdArg0 = a.asU16x8(); val simdArg1 = b.asU16x8(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 16-bit integers in a and b for not-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmpneq_epu16_mask&expand=1103)
 */
public fun mmMaskCmpneqEpu16Mask(k1: Mmask8, a: M128i, b: M128i): Mmask8 = mmMaskCmpEpu16Mask(k1, a, b, MM_CMPINT_NE)

/**
 * Compare packed unsigned 8-bit integers in a and b for not-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmpneq_epu8_mask&expand=1124)
 */
public fun mm512CmpneqEpu8Mask(a: M512i, b: M512i): Mmask64 = run { val simdArg0 = run { val simdArg0 = a.asU8x64(); val simdArg1 = b.asU8x64(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x64>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x64>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x64>()).alignBytes)); res } }; 0uL }

/**
 * Compare packed unsigned 8-bit integers in a and b for not-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmpneq_epu8_mask&expand=1125)
 */
public fun mm512MaskCmpneqEpu8Mask(k1: Mmask64, a: M512i, b: M512i): Mmask64 = mm512MaskCmpEpu8Mask(k1, a, b, MM_CMPINT_NE)

/**
 * Compare packed unsigned 8-bit integers in a and b for not-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmpneq_epu8_mask&expand=1122)
 */
public fun mm256CmpneqEpu8Mask(a: M256i, b: M256i): Mmask32 = run { val simdArg0 = run { val simdArg0 = a.asU8x32(); val simdArg1 = b.asU8x32(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x32>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x32>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 8-bit integers in a and b for not-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmpneq_epu8_mask&expand=1123)
 */
public fun mm256MaskCmpneqEpu8Mask(k1: Mmask32, a: M256i, b: M256i): Mmask32 = mm256MaskCmpEpu8Mask(k1, a, b, MM_CMPINT_NE)

/**
 * Compare packed unsigned 8-bit integers in a and b for not-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpneq_epu8_mask&expand=1120)
 */
public fun mmCmpneqEpu8Mask(a: M128i, b: M128i): Mmask16 = run { val simdArg0 = run { val simdArg0 = a.asU8x16(); val simdArg1 = b.asU8x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x16>()).alignBytes)); res } }; 0u }

/**
 * Compare packed unsigned 8-bit integers in a and b for not-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmpneq_epu8_mask&expand=1121)
 */
public fun mmMaskCmpneqEpu8Mask(k1: Mmask16, a: M128i, b: M128i): Mmask16 = mmMaskCmpEpu8Mask(k1, a, b, MM_CMPINT_NE)

/**
 * Compare packed signed 16-bit integers in a and b for not-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmpneq_epi16_mask&expand=1082)
 */
public fun mm512CmpneqEpi16Mask(a: M512i, b: M512i): Mmask32 = run { val simdArg0 = run { val simdArg0 = a.asI16x32(); val simdArg1 = b.asI16x32(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 16-bit integers in a and b for not-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmpneq_epi16_mask&expand=1083)
 */
public fun mm512MaskCmpneqEpi16Mask(k1: Mmask32, a: M512i, b: M512i): Mmask32 = mm512MaskCmpEpi16Mask(k1, a, b, MM_CMPINT_NE)

/**
 * Compare packed signed 16-bit integers in a and b for not-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmpneq_epi16_mask&expand=1080)
 */
public fun mm256CmpneqEpi16Mask(a: M256i, b: M256i): Mmask16 = run { val simdArg0 = run { val simdArg0 = a.asI16x16(); val simdArg1 = b.asI16x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 16-bit integers in a and b for not-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmpneq_epi16_mask&expand=1081)
 */
public fun mm256MaskCmpneqEpi16Mask(k1: Mmask16, a: M256i, b: M256i): Mmask16 = mm256MaskCmpEpi16Mask(k1, a, b, MM_CMPINT_NE)

/**
 * Compare packed signed 16-bit integers in a and b for not-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpneq_epi16_mask&expand=1078)
 */
public fun mmCmpneqEpi16Mask(a: M128i, b: M128i): Mmask8 = run { val simdArg0 = run { val simdArg0 = a.asI16x8(); val simdArg1 = b.asI16x8(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 16-bit integers in a and b for not-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmpneq_epi16_mask&expand=1079)
 */
public fun mmMaskCmpneqEpi16Mask(k1: Mmask8, a: M128i, b: M128i): Mmask8 = mmMaskCmpEpi16Mask(k1, a, b, MM_CMPINT_NE)

/**
 * Compare packed signed 8-bit integers in a and b for not-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmpneq_epi8_mask&expand=1100)
 */
public fun mm512CmpneqEpi8Mask(a: M512i, b: M512i): Mmask64 = run { val simdArg0 = run { val simdArg0 = a.asI8x64(); val simdArg1 = b.asI8x64(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x64>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); res } }; 0uL }

/**
 * Compare packed signed 8-bit integers in a and b for not-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmpneq_epi8_mask&expand=1101)
 */
public fun mm512MaskCmpneqEpi8Mask(k1: Mmask64, a: M512i, b: M512i): Mmask64 = mm512MaskCmpEpi8Mask(k1, a, b, MM_CMPINT_NE)

/**
 * Compare packed signed 8-bit integers in a and b for not-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmpneq_epi8_mask&expand=1098)
 */
public fun mm256CmpneqEpi8Mask(a: M256i, b: M256i): Mmask32 = run { val simdArg0 = run { val simdArg0 = a.asI8x32(); val simdArg1 = b.asI8x32(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x32>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x32>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 8-bit integers in a and b for not-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmpneq_epi8_mask&expand=1099)
 */
public fun mm256MaskCmpneqEpi8Mask(k1: Mmask32, a: M256i, b: M256i): Mmask32 = mm256MaskCmpEpi8Mask(k1, a, b, MM_CMPINT_NE)

/**
 * Compare packed signed 8-bit integers in a and b for not-equal, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmpneq_epi8_mask&expand=1096)
 */
public fun mmCmpneqEpi8Mask(a: M128i, b: M128i): Mmask16 = run { val simdArg0 = run { val simdArg0 = a.asI8x16(); val simdArg1 = b.asI8x16(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }; 0u }

/**
 * Compare packed signed 8-bit integers in a and b for not-equal, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmpneq_epi8_mask&expand=1097)
 */
public fun mmMaskCmpneqEpi8Mask(k1: Mmask16, a: M128i, b: M128i): Mmask16 = mmMaskCmpEpi8Mask(k1, a, b, MM_CMPINT_NE)

/**
 * Compare packed unsigned 16-bit integers in a and b based on the comparison operand specified by `IMM8`, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmp_epu16_mask&expand=715)
 */
public fun mm512CmpEpu16Mask(a: M512i, b: M512i, imm8: Int): Mmask32 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
        }
        val a1 = a.asU16x32()
        val b1 = b.asU16x32()
        val r =
            when (imm8) {
                0 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                1 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                2 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                3 -> Simd<Short>(List(32) { 0.toShort() })
                4 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                5 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                6 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                else -> Simd<Short>(List(32) { (-1).toShort() })
            }
        run { val simdArg0 = r; 0u }
    }

/**
 * Compare packed unsigned 16-bit integers in a and b based on the comparison operand specified by imm8, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmp_epu16_mask&expand=716)
 */
public fun mm512MaskCmpEpu16Mask(k1: Mmask32, a: M512i, b: M512i, imm8: Int): Mmask32 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
        }
        val a1 = a.asU16x32()
        val b1 = b.asU16x32()
        val k11 = run { val simdArg0 = k1; val simdArg1 = Simd<Short>(List(32) { (-1).toShort() }); val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }
        val r =
            when (imm8) {
                0 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                1 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                2 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                3 -> Simd<Short>(List(32) { 0.toShort() })
                4 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                5 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                6 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                else -> k11
            }
        run { val simdArg0 = r; 0u }
    }

/**
 * Compare packed unsigned 16-bit integers in a and b based on the comparison operand specified by imm8, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmp_epu16_mask&expand=713)
 */
public fun mm256CmpEpu16Mask(a: M256i, b: M256i, imm8: Int): Mmask16 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
        }
        val a1 = a.asU16x16()
        val b1 = b.asU16x16()
        val r =
            when (imm8) {
                0 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                1 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                2 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                3 -> Simd<Short>(List(16) { 0.toShort() })
                4 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                5 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                6 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                else -> Simd<Short>(List(16) { (-1).toShort() })
            }
        run { val simdArg0 = r; 0u }
    }

/**
 * Compare packed unsigned 16-bit integers in a and b based on the comparison operand specified by imm8, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmp_epu16_mask&expand=714)
 */
public fun mm256MaskCmpEpu16Mask(k1: Mmask16, a: M256i, b: M256i, imm8: Int): Mmask16 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
        }
        val a1 = a.asU16x16()
        val b1 = b.asU16x16()
        val k11 = run { val simdArg0 = k1; val simdArg1 = Simd<Short>(List(16) { (-1).toShort() }); val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }
        val r =
            when (imm8) {
                0 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                1 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                2 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                3 -> Simd<Short>(List(16) { 0.toShort() })
                4 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                5 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                6 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                else -> k11
            }
        run { val simdArg0 = r; 0u }
    }

/**
 * Compare packed unsigned 16-bit integers in a and b based on the comparison operand specified by imm8, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmp_epu16_mask&expand=711)
 */
public fun mmCmpEpu16Mask(a: M128i, b: M128i, imm8: Int): Mmask8 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
        }
        val a1 = a.asU16x8()
        val b1 = b.asU16x8()
        val r =
            when (imm8) {
                0 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                1 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                2 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                3 -> Simd<Short>(List(8) { 0.toShort() })
                4 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                5 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                6 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                else -> Simd<Short>(List(8) { (-1).toShort() })
            }
        run { val simdArg0 = r; 0u }
    }

/**
 * Compare packed unsigned 16-bit integers in a and b based on the comparison operand specified by imm8, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmp_epu16_mask&expand=712)
 */
public fun mmMaskCmpEpu16Mask(k1: Mmask8, a: M128i, b: M128i, imm8: Int): Mmask8 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
        }
        val a1 = a.asU16x8()
        val b1 = b.asU16x8()
        val k11 = run { val simdArg0 = k1; val simdArg1 = Simd<Short>(List(8) { (-1).toShort() }); val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }
        val r =
            when (imm8) {
                0 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                1 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                2 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                3 -> Simd<Short>(List(8) { 0.toShort() })
                4 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                5 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                6 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                else -> k11
            }
        run { val simdArg0 = r; 0u }
    }

/**
 * Compare packed unsigned 8-bit integers in a and b based on the comparison operand specified by imm8, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmp_epu8_mask&expand=733)
 */
public fun mm512CmpEpu8Mask(a: M512i, b: M512i, imm8: Int): Mmask64 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
        }
        val a1 = a.asU8x64()
        val b1 = b.asU8x64()
        val r =
            when (imm8) {
                0 -> run { val simdArg0 = a1; val simdArg1 = b1; 0uL }
                1 -> run { val simdArg0 = a1; val simdArg1 = b1; 0uL }
                2 -> run { val simdArg0 = a1; val simdArg1 = b1; 0uL }
                3 -> Simd<Byte>(List(64) { 0.toByte() })
                4 -> run { val simdArg0 = a1; val simdArg1 = b1; 0uL }
                5 -> run { val simdArg0 = a1; val simdArg1 = b1; 0uL }
                6 -> run { val simdArg0 = a1; val simdArg1 = b1; 0uL }
                else -> Simd<Byte>(List(64) { (-1).toByte() })
            }
        run { val simdArg0 = r; 0uL }
    }

/**
 * Compare packed unsigned 8-bit integers in a and b based on the comparison operand specified by imm8, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmp_epu8_mask&expand=734)
 */
public fun mm512MaskCmpEpu8Mask(k1: Mmask64, a: M512i, b: M512i, imm8: Int): Mmask64 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
        }
        val a1 = a.asU8x64()
        val b1 = b.asU8x64()
        val k11 = run { val simdArg0 = k1; val simdArg1 = Simd<Byte>(List(64) { (-1).toByte() }); val simdArg2 = Simd<Byte>(List(64) { 0.toByte() }); simdArg1 }
        val r =
            when (imm8) {
                0 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0uL }; simdArg0 }
                1 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0uL }; simdArg0 }
                2 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0uL }; simdArg0 }
                3 -> Simd<Byte>(List(64) { 0.toByte() })
                4 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0uL }; simdArg0 }
                5 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0uL }; simdArg0 }
                6 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0uL }; simdArg0 }
                else -> k11
            }
        run { val simdArg0 = r; 0uL }
    }

/**
 * Compare packed unsigned 8-bit integers in a and b based on the comparison operand specified by imm8, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmp_epu8_mask&expand=731)
 */
public fun mm256CmpEpu8Mask(a: M256i, b: M256i, imm8: Int): Mmask32 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
        }
        val a1 = a.asU8x32()
        val b1 = b.asU8x32()
        val r =
            when (imm8) {
                0 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                1 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                2 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                3 -> Simd<Byte>(List(32) { 0.toByte() })
                4 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                5 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                6 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                else -> Simd<Byte>(List(32) { (-1).toByte() })
            }
        run { val simdArg0 = r; 0u }
    }

/**
 * Compare packed unsigned 8-bit integers in a and b based on the comparison operand specified by imm8, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmp_epu8_mask&expand=732)
 */
public fun mm256MaskCmpEpu8Mask(k1: Mmask32, a: M256i, b: M256i, imm8: Int): Mmask32 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
        }
        val a1 = a.asU8x32()
        val b1 = b.asU8x32()
        val k11 = run { val simdArg0 = k1; val simdArg1 = Simd<Byte>(List(32) { (-1).toByte() }); val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }
        val r =
            when (imm8) {
                0 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                1 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                2 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                3 -> Simd<Byte>(List(32) { 0.toByte() })
                4 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                5 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                6 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                else -> k11
            }
        run { val simdArg0 = r; 0u }
    }

/**
 * Compare packed unsigned 8-bit integers in a and b based on the comparison operand specified by imm8, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmp_epu8_mask&expand=729)
 */
public fun mmCmpEpu8Mask(a: M128i, b: M128i, imm8: Int): Mmask16 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
        }
        val a1 = a.asU8x16()
        val b1 = b.asU8x16()
        val r =
            when (imm8) {
                0 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                1 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                2 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                3 -> Simd<Byte>(List(16) { 0.toByte() })
                4 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                5 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                6 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                else -> Simd<Byte>(List(16) { (-1).toByte() })
            }
        run { val simdArg0 = r; 0u }
    }

/**
 * Compare packed unsigned 8-bit integers in a and b based on the comparison operand specified by imm8, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmp_epu8_mask&expand=730)
 */
public fun mmMaskCmpEpu8Mask(k1: Mmask16, a: M128i, b: M128i, imm8: Int): Mmask16 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
        }
        val a1 = a.asU8x16()
        val b1 = b.asU8x16()
        val k11 = run { val simdArg0 = k1; val simdArg1 = Simd<Byte>(List(16) { (-1).toByte() }); val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }
        val r =
            when (imm8) {
                0 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                1 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                2 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                3 -> Simd<Byte>(List(16) { 0.toByte() })
                4 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                5 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                6 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                else -> k11
            }
        run { val simdArg0 = r; 0u }
    }

/**
 * Compare packed signed 16-bit integers in a and b based on the comparison operand specified by imm8, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmp_epi16_mask&expand=691)
 */
public fun mm512CmpEpi16Mask(a: M512i, b: M512i, imm8: Int): Mmask32 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
        }
        val a1 = a.asI16x32()
        val b1 = b.asI16x32()
        val r =
            when (imm8) {
                0 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                1 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                2 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                3 -> Simd<Short>(List(32) { 0.toShort() })
                4 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                5 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                6 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                else -> Simd<Short>(List(32) { (-1).toShort() })
            }
        run { val simdArg0 = r; 0u }
    }

/**
 * Compare packed signed 16-bit integers in a and b based on the comparison operand specified by imm8, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmp_epi16_mask&expand=692)
 */
public fun mm512MaskCmpEpi16Mask(k1: Mmask32, a: M512i, b: M512i, imm8: Int): Mmask32 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
        }
        val a1 = a.asI16x32()
        val b1 = b.asI16x32()
        val k11 = run { val simdArg0 = k1; val simdArg1 = Simd<Short>(List(32) { (-1).toShort() }); val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }
        val r =
            when (imm8) {
                0 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                1 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                2 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                3 -> Simd<Short>(List(32) { 0.toShort() })
                4 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                5 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                6 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                else -> k11
            }
        run { val simdArg0 = r; 0u }
    }

/**
 * Compare packed signed 16-bit integers in a and b based on the comparison operand specified by imm8, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmp_epi16_mask&expand=689)
 */
public fun mm256CmpEpi16Mask(a: M256i, b: M256i, imm8: Int): Mmask16 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
        }
        val a1 = a.asI16x16()
        val b1 = b.asI16x16()
        val r =
            when (imm8) {
                0 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                1 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                2 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                3 -> Simd<Short>(List(16) { 0.toShort() })
                4 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                5 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                6 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                else -> Simd<Short>(List(16) { (-1).toShort() })
            }
        run { val simdArg0 = r; 0u }
    }

/**
 * Compare packed signed 16-bit integers in a and b based on the comparison operand specified by imm8, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmp_epi16_mask&expand=690)
 */
public fun mm256MaskCmpEpi16Mask(k1: Mmask16, a: M256i, b: M256i, imm8: Int): Mmask16 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
        }
        val a1 = a.asI16x16()
        val b1 = b.asI16x16()
        val k11 = run { val simdArg0 = k1; val simdArg1 = Simd<Short>(List(16) { (-1).toShort() }); val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }
        val r =
            when (imm8) {
                0 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                1 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                2 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                3 -> Simd<Short>(List(16) { 0.toShort() })
                4 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                5 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                6 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                else -> k11
            }
        run { val simdArg0 = r; 0u }
    }

/**
 * Compare packed signed 16-bit integers in a and b based on the comparison operand specified by imm8, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmp_epi16_mask&expand=687)
 */
public fun mmCmpEpi16Mask(a: M128i, b: M128i, imm8: Int): Mmask8 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
        }
        val a1 = a.asI16x8()
        val b1 = b.asI16x8()
        val r =
            when (imm8) {
                0 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                1 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                2 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                3 -> Simd<Short>(List(8) { 0.toShort() })
                4 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                5 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                6 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                else -> Simd<Short>(List(8) { (-1).toShort() })
            }
        run { val simdArg0 = r; 0u }
    }

/**
 * Compare packed signed 16-bit integers in a and b based on the comparison operand specified by imm8, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmp_epi16_mask&expand=688)
 */
public fun mmMaskCmpEpi16Mask(k1: Mmask8, a: M128i, b: M128i, imm8: Int): Mmask8 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
        }
        val a1 = a.asI16x8()
        val b1 = b.asI16x8()
        val k11 = run { val simdArg0 = k1; val simdArg1 = Simd<Short>(List(8) { (-1).toShort() }); val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }
        val r =
            when (imm8) {
                0 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                1 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                2 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                3 -> Simd<Short>(List(8) { 0.toShort() })
                4 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                5 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                6 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                else -> k11
            }
        run { val simdArg0 = r; 0u }
    }

/**
 * Compare packed signed 8-bit integers in a and b based on the comparison operand specified by imm8, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cmp_epi8_mask&expand=709)
 */
public fun mm512CmpEpi8Mask(a: M512i, b: M512i, imm8: Int): Mmask64 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
        }
        val a1 = a.asI8x64()
        val b1 = b.asI8x64()
        val r =
            when (imm8) {
                0 -> run { val simdArg0 = a1; val simdArg1 = b1; 0uL }
                1 -> run { val simdArg0 = a1; val simdArg1 = b1; 0uL }
                2 -> run { val simdArg0 = a1; val simdArg1 = b1; 0uL }
                3 -> Simd<Byte>(List(64) { 0.toByte() })
                4 -> run { val simdArg0 = a1; val simdArg1 = b1; 0uL }
                5 -> run { val simdArg0 = a1; val simdArg1 = b1; 0uL }
                6 -> run { val simdArg0 = a1; val simdArg1 = b1; 0uL }
                else -> Simd<Byte>(List(64) { (-1).toByte() })
            }
        run { val simdArg0 = r; 0uL }
    }

/**
 * Compare packed signed 8-bit integers in a and b based on the comparison operand specified by imm8, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cmp_epi8_mask&expand=710)
 */
public fun mm512MaskCmpEpi8Mask(k1: Mmask64, a: M512i, b: M512i, imm8: Int): Mmask64 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
        }
        val a1 = a.asI8x64()
        val b1 = b.asI8x64()
        val k11 = run { val simdArg0 = k1; val simdArg1 = Simd<Byte>(List(64) { (-1).toByte() }); val simdArg2 = Simd<Byte>(List(64) { 0.toByte() }); simdArg1 }
        val r =
            when (imm8) {
                0 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0uL }; simdArg0 }
                1 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0uL }; simdArg0 }
                2 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0uL }; simdArg0 }
                3 -> Simd<Byte>(List(64) { 0.toByte() })
                4 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0uL }; simdArg0 }
                5 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0uL }; simdArg0 }
                6 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0uL }; simdArg0 }
                else -> k11
            }
        run { val simdArg0 = r; 0uL }
    }

/**
 * Compare packed signed 8-bit integers in a and b based on the comparison operand specified by imm8, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cmp_epi8_mask&expand=707)
 */
public fun mm256CmpEpi8Mask(a: M256i, b: M256i, imm8: Int): Mmask32 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
        }
        val a1 = a.asI8x32()
        val b1 = b.asI8x32()
        val r =
            when (imm8) {
                0 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                1 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                2 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                3 -> Simd<Byte>(List(32) { 0.toByte() })
                4 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                5 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                6 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                else -> Simd<Byte>(List(32) { (-1).toByte() })
            }
        run { val simdArg0 = r; 0u }
    }

/**
 * Compare packed signed 8-bit integers in a and b based on the comparison operand specified by imm8, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cmp_epi8_mask&expand=708)
 */
public fun mm256MaskCmpEpi8Mask(k1: Mmask32, a: M256i, b: M256i, imm8: Int): Mmask32 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
        }
        val a1 = a.asI8x32()
        val b1 = b.asI8x32()
        val k11 = run { val simdArg0 = k1; val simdArg1 = Simd<Byte>(List(32) { (-1).toByte() }); val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }
        val r =
            when (imm8) {
                0 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                1 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                2 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                3 -> Simd<Byte>(List(32) { 0.toByte() })
                4 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                5 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                6 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                else -> k11
            }
        run { val simdArg0 = r; 0u }
    }

/**
 * Compare packed signed 8-bit integers in a and b based on the comparison operand specified by imm8, and store the results in mask vector k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cmp_epi8_mask&expand=705)
 */
public fun mmCmpEpi8Mask(a: M128i, b: M128i, imm8: Int): Mmask16 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
        }
        val a1 = a.asI8x16()
        val b1 = b.asI8x16()
        val r =
            when (imm8) {
                0 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                1 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                2 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                3 -> Simd<Byte>(List(16) { 0.toByte() })
                4 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                5 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                6 -> run { val simdArg0 = a1; val simdArg1 = b1; 0u }
                else -> Simd<Byte>(List(16) { (-1).toByte() })
            }
        run { val simdArg0 = r; 0u }
    }

/**
 * Compare packed signed 8-bit integers in a and b based on the comparison operand specified by imm8, and store the results in mask vector k using zeromask k1 (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cmp_epi8_mask&expand=706)
 */
public fun mmMaskCmpEpi8Mask(k1: Mmask16, a: M128i, b: M128i, imm8: Int): Mmask16 =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 3)) { "IMM8 doesn't fit in 3 bits (unsigned)" }
        }
        val a1 = a.asI8x16()
        val b1 = b.asI8x16()
        val k11 = run { val simdArg0 = k1; val simdArg1 = Simd<Byte>(List(16) { (-1).toByte() }); val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }
        val r =
            when (imm8) {
                0 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                1 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                2 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                3 -> Simd<Byte>(List(16) { 0.toByte() })
                4 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                5 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                6 -> run { val simdArg0 = k11; val simdArg1 = run { val simdArg0 = a1; val simdArg1 = b1; 0u }; simdArg0 }
                else -> k11
            }
        run { val simdArg0 = r; 0u }
    }

/**
 * Reduce the packed 16-bit integers in a by addition. Returns the sum of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_reduce_add_epi16)
 */
public fun mm256ReduceAddEpi16(a: M256i): Short = run { val simdArg0 = a.asI16x16(); val simdArg1 = 0; simdArg1 }

/**
 * Reduce the packed 16-bit integers in a by addition using mask k. Returns the sum of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_reduce_add_epi16)
 */
public fun mm256MaskReduceAddEpi16(k: Mmask16, a: M256i): Short = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asI16x16(); val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }; val simdArg1 = 0; simdArg1 }

/**
 * Reduce the packed 16-bit integers in a by addition. Returns the sum of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_reduce_add_epi16)
 */
public fun mmReduceAddEpi16(a: M128i): Short = run { val simdArg0 = a.asI16x8(); val simdArg1 = 0; simdArg1 }

/**
 * Reduce the packed 16-bit integers in a by addition using mask k. Returns the sum of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_reduce_add_epi16)
 */
public fun mmMaskReduceAddEpi16(k: Mmask8, a: M128i): Short = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asI16x8(); val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }; val simdArg1 = 0; simdArg1 }

/**
 * Reduce the packed 8-bit integers in a by addition. Returns the sum of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_reduce_add_epi8)
 */
public fun mm256ReduceAddEpi8(a: M256i): Byte = run { val simdArg0 = a.asI8x32(); val simdArg1 = 0; simdArg1 }

/**
 * Reduce the packed 8-bit integers in a by addition using mask k. Returns the sum of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_reduce_add_epi8)
 */
public fun mm256MaskReduceAddEpi8(k: Mmask32, a: M256i): Byte = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asI8x32(); val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }; val simdArg1 = 0; simdArg1 }

/**
 * Reduce the packed 8-bit integers in a by addition. Returns the sum of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_reduce_add_epi8)
 */
public fun mmReduceAddEpi8(a: M128i): Byte = run { val simdArg0 = a.asI8x16(); val simdArg1 = 0; simdArg1 }

/**
 * Reduce the packed 8-bit integers in a by addition using mask k. Returns the sum of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_reduce_add_epi8)
 */
public fun mmMaskReduceAddEpi8(k: Mmask16, a: M128i): Byte = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asI8x16(); val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }; val simdArg1 = 0; simdArg1 }

/**
 * Reduce the packed 16-bit integers in a by bitwise AND. Returns the bitwise AND of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_reduce_and_epi16)
 */
public fun mm256ReduceAndEpi16(a: M256i): Short = run { val simdArg0 = a.asI16x16(); 0 }

/**
 * Reduce the packed 16-bit integers in a by bitwise AND using mask k. Returns the bitwise AND of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_reduce_and_epi16)
 */
public fun mm256MaskReduceAndEpi16(k: Mmask16, a: M256i): Short = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asI16x16(); val simdArg2 = mm256Set1Epi64x(-1L).asI16x16(); simdArg1 }; 0 }

/**
 * Reduce the packed 16-bit integers in a by bitwise AND. Returns the bitwise AND of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_reduce_and_epi16)
 */
public fun mmReduceAndEpi16(a: M128i): Short = run { val simdArg0 = a.asI16x8(); 0 }

/**
 * Reduce the packed 16-bit integers in a by bitwise AND using mask k. Returns the bitwise AND of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_reduce_and_epi16)
 */
public fun mmMaskReduceAndEpi16(k: Mmask8, a: M128i): Short = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asI16x8(); val simdArg2 = mmSet1Epi64x(-1L).asI16x8(); simdArg1 }; 0 }

/**
 * Reduce the packed 8-bit integers in a by bitwise AND. Returns the bitwise AND of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_reduce_and_epi8)
 */
public fun mm256ReduceAndEpi8(a: M256i): Byte = run { val simdArg0 = a.asI8x32(); 0 }

/**
 * Reduce the packed 8-bit integers in a by bitwise AND using mask k. Returns the bitwise AND of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_reduce_and_epi8)
 */
public fun mm256MaskReduceAndEpi8(k: Mmask32, a: M256i): Byte = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asI8x32(); val simdArg2 = mm256Set1Epi64x(-1L).asI8x32(); simdArg1 }; 0 }

/**
 * Reduce the packed 8-bit integers in a by bitwise AND. Returns the bitwise AND of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_reduce_and_epi8)
 */
public fun mmReduceAndEpi8(a: M128i): Byte = run { val simdArg0 = a.asI8x16(); 0 }

/**
 * Reduce the packed 8-bit integers in a by bitwise AND using mask k. Returns the bitwise AND of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_reduce_and_epi8)
 */
public fun mmMaskReduceAndEpi8(k: Mmask16, a: M128i): Byte = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asI8x16(); val simdArg2 = mmSet1Epi64x(-1L).asI8x16(); simdArg1 }; 0 }

/**
 * Reduce the packed 16-bit integers in a by maximum. Returns the maximum of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_reduce_max_epi16)
 */
public fun mm256ReduceMaxEpi16(a: M256i): Short = run { val simdArg0 = a.asI16x16(); 0 }

/**
 * Reduce the packed 16-bit integers in a by maximum using mask k. Returns the maximum of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_reduce_max_epi16)
 */
public fun mm256MaskReduceMaxEpi16(k: Mmask16, a: M256i): Short = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asI16x16(); val simdArg2 = Simd<Short>(List(16) { (-32768).toShort() }); simdArg1 }; 0 }

/**
 * Reduce the packed 16-bit integers in a by maximum. Returns the maximum of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_reduce_max_epi16)
 */
public fun mmReduceMaxEpi16(a: M128i): Short = run { val simdArg0 = a.asI16x8(); 0 }

/**
 * Reduce the packed 16-bit integers in a by maximum using mask k. Returns the maximum of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_reduce_max_epi16)
 */
public fun mmMaskReduceMaxEpi16(k: Mmask8, a: M128i): Short = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asI16x8(); val simdArg2 = Simd<Short>(List(8) { (-32768).toShort() }); simdArg1 }; 0 }

/**
 * Reduce the packed 8-bit integers in a by maximum. Returns the maximum of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_reduce_max_epi8)
 */
public fun mm256ReduceMaxEpi8(a: M256i): Byte = run { val simdArg0 = a.asI8x32(); 0 }

/**
 * Reduce the packed 8-bit integers in a by maximum using mask k. Returns the maximum of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_reduce_max_epi8)
 */
public fun mm256MaskReduceMaxEpi8(k: Mmask32, a: M256i): Byte = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asI8x32(); val simdArg2 = Simd<Byte>(List(32) { (-128).toByte() }); simdArg1 }; 0 }

/**
 * Reduce the packed 8-bit integers in a by maximum. Returns the maximum of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_reduce_max_epi8)
 */
public fun mmReduceMaxEpi8(a: M128i): Byte = run { val simdArg0 = a.asI8x16(); 0 }

/**
 * Reduce the packed 8-bit integers in a by maximum using mask k. Returns the maximum of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_reduce_max_epi8)
 */
public fun mmMaskReduceMaxEpi8(k: Mmask16, a: M128i): Byte = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asI8x16(); val simdArg2 = Simd<Byte>(List(16) { (-128).toByte() }); simdArg1 }; 0 }

/**
 * Reduce the packed unsigned 16-bit integers in a by maximum. Returns the maximum of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_reduce_max_epu16)
 */
public fun mm256ReduceMaxEpu16(a: M256i): UShort = run { val simdArg0 = a.asU16x16(); 0u }

/**
 * Reduce the packed unsigned 16-bit integers in a by maximum using mask k. Returns the maximum of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_reduce_max_epu16)
 */
public fun mm256MaskReduceMaxEpu16(k: Mmask16, a: M256i): UShort = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asU16x16(); val simdArg2 = Simd<UShort>(List(16) { 0u.toUShort() }); simdArg1 }; 0u }

/**
 * Reduce the packed unsigned 16-bit integers in a by maximum. Returns the maximum of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_reduce_max_epu16)
 */
public fun mmReduceMaxEpu16(a: M128i): UShort = run { val simdArg0 = a.asU16x8(); 0u }

/**
 * Reduce the packed unsigned 16-bit integers in a by maximum using mask k. Returns the maximum of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_reduce_max_epu16)
 */
public fun mmMaskReduceMaxEpu16(k: Mmask8, a: M128i): UShort = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asU16x8(); val simdArg2 = Simd<UShort>(List(8) { 0u.toUShort() }); simdArg1 }; 0u }

/**
 * Reduce the packed unsigned 8-bit integers in a by maximum. Returns the maximum of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_reduce_max_epu8)
 */
public fun mm256ReduceMaxEpu8(a: M256i): UByte = run { val simdArg0 = a.asU8x32(); 0u }

/**
 * Reduce the packed unsigned 8-bit integers in a by maximum using mask k. Returns the maximum of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_reduce_max_epu8)
 */
public fun mm256MaskReduceMaxEpu8(k: Mmask32, a: M256i): UByte = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asU8x32(); val simdArg2 = Simd<UByte>(List(32) { 0u.toUByte() }); simdArg1 }; 0u }

/**
 * Reduce the packed unsigned 8-bit integers in a by maximum. Returns the maximum of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_reduce_max_epu8)
 */
public fun mmReduceMaxEpu8(a: M128i): UByte = run { val simdArg0 = a.asU8x16(); 0u }

/**
 * Reduce the packed unsigned 8-bit integers in a by maximum using mask k. Returns the maximum of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_reduce_max_epu8)
 */
public fun mmMaskReduceMaxEpu8(k: Mmask16, a: M128i): UByte = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asU8x16(); val simdArg2 = Simd<UByte>(List(16) { 0u.toUByte() }); simdArg1 }; 0u }

/**
 * Reduce the packed 16-bit integers in a by minimum. Returns the minimum of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_reduce_min_epi16)
 */
public fun mm256ReduceMinEpi16(a: M256i): Short = run { val simdArg0 = a.asI16x16(); 0 }

/**
 * Reduce the packed 16-bit integers in a by minimum using mask k. Returns the minimum of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_reduce_min_epi16)
 */
public fun mm256MaskReduceMinEpi16(k: Mmask16, a: M256i): Short = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asI16x16(); val simdArg2 = Simd<Short>(List(16) { 0x7fff.toShort() }); simdArg1 }; 0 }

/**
 * Reduce the packed 16-bit integers in a by minimum. Returns the minimum of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_reduce_min_epi16)
 */
public fun mmReduceMinEpi16(a: M128i): Short = run { val simdArg0 = a.asI16x8(); 0 }

/**
 * Reduce the packed 16-bit integers in a by minimum using mask k. Returns the minimum of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_reduce_min_epi16)
 */
public fun mmMaskReduceMinEpi16(k: Mmask8, a: M128i): Short = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asI16x8(); val simdArg2 = Simd<Short>(List(8) { 0x7fff.toShort() }); simdArg1 }; 0 }

/**
 * Reduce the packed 8-bit integers in a by minimum. Returns the minimum of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_reduce_min_epi8)
 */
public fun mm256ReduceMinEpi8(a: M256i): Byte = run { val simdArg0 = a.asI8x32(); 0 }

/**
 * Reduce the packed 8-bit integers in a by minimum using mask k. Returns the minimum of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_reduce_min_epi8)
 */
public fun mm256MaskReduceMinEpi8(k: Mmask32, a: M256i): Byte = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asI8x32(); val simdArg2 = Simd<Byte>(List(32) { 0x7f.toByte() }); simdArg1 }; 0 }

/**
 * Reduce the packed 8-bit integers in a by minimum. Returns the minimum of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_reduce_min_epi8)
 */
public fun mmReduceMinEpi8(a: M128i): Byte = run { val simdArg0 = a.asI8x16(); 0 }

/**
 * Reduce the packed 8-bit integers in a by minimum using mask k. Returns the minimum of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_reduce_min_epi8)
 */
public fun mmMaskReduceMinEpi8(k: Mmask16, a: M128i): Byte = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asI8x16(); val simdArg2 = Simd<Byte>(List(16) { 0x7f.toByte() }); simdArg1 }; 0 }

/**
 * Reduce the packed unsigned 16-bit integers in a by minimum. Returns the minimum of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_reduce_min_epu16)
 */
public fun mm256ReduceMinEpu16(a: M256i): UShort = run { val simdArg0 = a.asU16x16(); 0u }

/**
 * Reduce the packed unsigned 16-bit integers in a by minimum using mask k. Returns the minimum of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_reduce_min_epu16)
 */
public fun mm256MaskReduceMinEpu16(k: Mmask16, a: M256i): UShort = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asU16x16(); val simdArg2 = Simd<UShort>(List(16) { 0xffffu.toUShort() }); simdArg1 }; 0u }

/**
 * Reduce the packed unsigned 16-bit integers in a by minimum. Returns the minimum of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_reduce_min_epu16)
 */
public fun mmReduceMinEpu16(a: M128i): UShort = run { val simdArg0 = a.asU16x8(); 0u }

/**
 * Reduce the packed unsigned 16-bit integers in a by minimum using mask k. Returns the minimum of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_reduce_min_epu16)
 */
public fun mmMaskReduceMinEpu16(k: Mmask8, a: M128i): UShort = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asU16x8(); val simdArg2 = Simd<UShort>(List(8) { 0xffffu.toUShort() }); simdArg1 }; 0u }

/**
 * Reduce the packed unsigned 8-bit integers in a by minimum. Returns the minimum of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_reduce_min_epu8)
 */
public fun mm256ReduceMinEpu8(a: M256i): UByte = run { val simdArg0 = a.asU8x32(); 0u }

/**
 * Reduce the packed unsigned 8-bit integers in a by minimum using mask k. Returns the minimum of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_reduce_min_epu8)
 */
public fun mm256MaskReduceMinEpu8(k: Mmask32, a: M256i): UByte = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asU8x32(); val simdArg2 = Simd<UByte>(List(32) { 0xffu.toUByte() }); simdArg1 }; 0u }

/**
 * Reduce the packed unsigned 8-bit integers in a by minimum. Returns the minimum of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_reduce_min_epu8)
 */
public fun mmReduceMinEpu8(a: M128i): UByte = run { val simdArg0 = a.asU8x16(); 0u }

/**
 * Reduce the packed unsigned 8-bit integers in a by minimum using mask k. Returns the minimum of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_reduce_min_epu8)
 */
public fun mmMaskReduceMinEpu8(k: Mmask16, a: M128i): UByte = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asU8x16(); val simdArg2 = Simd<UByte>(List(16) { 0xffu.toUByte() }); simdArg1 }; 0u }

/**
 * Reduce the packed 16-bit integers in a by multiplication. Returns the product of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_reduce_mul_epi16)
 */
public fun mm256ReduceMulEpi16(a: M256i): Short = run { val simdArg0 = a.asI16x16(); val simdArg1 = 1; simdArg1 }

/**
 * Reduce the packed 16-bit integers in a by multiplication using mask k. Returns the product of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_reduce_mul_epi16)
 */
public fun mm256MaskReduceMulEpi16(k: Mmask16, a: M256i): Short = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asI16x16(); val simdArg2 = Simd<Short>(List(16) { 1.toShort() }); simdArg1 }; val simdArg1 = 1; simdArg1 }

/**
 * Reduce the packed 16-bit integers in a by multiplication. Returns the product of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_reduce_mul_epi16)
 */
public fun mmReduceMulEpi16(a: M128i): Short = run { val simdArg0 = a.asI16x8(); val simdArg1 = 1; simdArg1 }

/**
 * Reduce the packed 16-bit integers in a by multiplication using mask k. Returns the product of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_reduce_mul_epi16)
 */
public fun mmMaskReduceMulEpi16(k: Mmask8, a: M128i): Short = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asI16x8(); val simdArg2 = Simd<Short>(List(8) { 1.toShort() }); simdArg1 }; val simdArg1 = 1; simdArg1 }

/**
 * Reduce the packed 8-bit integers in a by multiplication. Returns the product of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_reduce_mul_epi8)
 */
public fun mm256ReduceMulEpi8(a: M256i): Byte = run { val simdArg0 = a.asI8x32(); val simdArg1 = 1; simdArg1 }

/**
 * Reduce the packed 8-bit integers in a by multiplication using mask k. Returns the product of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_reduce_mul_epi8)
 */
public fun mm256MaskReduceMulEpi8(k: Mmask32, a: M256i): Byte = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asI8x32(); val simdArg2 = Simd<Byte>(List(32) { 1.toByte() }); simdArg1 }; val simdArg1 = 1; simdArg1 }

/**
 * Reduce the packed 8-bit integers in a by multiplication. Returns the product of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_reduce_mul_epi8)
 */
public fun mmReduceMulEpi8(a: M128i): Byte = run { val simdArg0 = a.asI8x16(); val simdArg1 = 1; simdArg1 }

/**
 * Reduce the packed 8-bit integers in a by multiplication using mask k. Returns the product of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_reduce_mul_epi8)
 */
public fun mmMaskReduceMulEpi8(k: Mmask16, a: M128i): Byte = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asI8x16(); val simdArg2 = Simd<Byte>(List(16) { 1.toByte() }); simdArg1 }; val simdArg1 = 1; simdArg1 }

/**
 * Reduce the packed 16-bit integers in a by bitwise OR. Returns the bitwise OR of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_reduce_or_epi16)
 */
public fun mm256ReduceOrEpi16(a: M256i): Short = run { val simdArg0 = a.asI16x16(); 0 }

/**
 * Reduce the packed 16-bit integers in a by bitwise OR using mask k. Returns the bitwise OR of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_reduce_or_epi16)
 */
public fun mm256MaskReduceOrEpi16(k: Mmask16, a: M256i): Short = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asI16x16(); val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }; 0 }

/**
 * Reduce the packed 16-bit integers in a by bitwise OR. Returns the bitwise OR of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_reduce_or_epi16)
 */
public fun mmReduceOrEpi16(a: M128i): Short = run { val simdArg0 = a.asI16x8(); 0 }

/**
 * Reduce the packed 16-bit integers in a by bitwise OR using mask k. Returns the bitwise OR of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_reduce_or_epi16)
 */
public fun mmMaskReduceOrEpi16(k: Mmask8, a: M128i): Short = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asI16x8(); val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }; 0 }

/**
 * Reduce the packed 8-bit integers in a by bitwise OR. Returns the bitwise OR of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_reduce_or_epi8)
 */
public fun mm256ReduceOrEpi8(a: M256i): Byte = run { val simdArg0 = a.asI8x32(); 0 }

/**
 * Reduce the packed 8-bit integers in a by bitwise OR using mask k. Returns the bitwise OR of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_reduce_or_epi8)
 */
public fun mm256MaskReduceOrEpi8(k: Mmask32, a: M256i): Byte = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asI8x32(); val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }; 0 }

/**
 * Reduce the packed 8-bit integers in a by bitwise OR. Returns the bitwise OR of all elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_reduce_or_epi8)
 */
public fun mmReduceOrEpi8(a: M128i): Byte = run { val simdArg0 = a.asI8x16(); 0 }

/**
 * Reduce the packed 8-bit integers in a by bitwise OR using mask k. Returns the bitwise OR of all active elements in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_reduce_or_epi8)
 */
public fun mmMaskReduceOrEpi8(k: Mmask16, a: M128i): Byte = run { val simdArg0 = run { val simdArg0 = k; val simdArg1 = a.asI8x16(); val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }; 0 }

// should be vmovdqu16

/**
 * Load 512-bits (composed of 32 packed 16-bit integers) from memory into dst. memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_loadu_epi16&expand=3368)
 */
public fun mm512LoaduEpi16(memAddr: ConstPtr): M512i = readUnaligned(ConstPtr(memAddr.rawAddress, memAddr.rawMeta), typeLayoutT = UnresolvedTypeLayout<M512i>())

// should be vmovdqu16

/**
 * Load 256-bits (composed of 16 packed 16-bit integers) from memory into dst. memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_loadu_epi16&expand=3365)
 */
public fun mm256LoaduEpi16(memAddr: ConstPtr): M256i = readUnaligned(ConstPtr(memAddr.rawAddress, memAddr.rawMeta), typeLayoutT = UnresolvedTypeLayout<M256i>())

// should be vmovdqu16

/**
 * Load 128-bits (composed of 8 packed 16-bit integers) from memory into dst. memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_loadu_epi16&expand=3362)
 */
public fun mmLoaduEpi16(memAddr: ConstPtr): M128i = readUnaligned(ConstPtr(memAddr.rawAddress, memAddr.rawMeta), typeLayoutT = UnresolvedTypeLayout<M128i>())

// should be vmovdqu8

/**
 * Load 512-bits (composed of 64 packed 8-bit integers) from memory into dst. memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_loadu_epi8&expand=3395)
 */
public fun mm512LoaduEpi8(memAddr: ConstPtr): M512i = readUnaligned(ConstPtr(memAddr.rawAddress, memAddr.rawMeta), typeLayoutT = UnresolvedTypeLayout<M512i>())

// should be vmovdqu8

/**
 * Load 256-bits (composed of 32 packed 8-bit integers) from memory into dst. memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_loadu_epi8&expand=3392)
 */
public fun mm256LoaduEpi8(memAddr: ConstPtr): M256i = readUnaligned(ConstPtr(memAddr.rawAddress, memAddr.rawMeta), typeLayoutT = UnresolvedTypeLayout<M256i>())

// should be vmovdqu8

/**
 * Load 128-bits (composed of 16 packed 8-bit integers) from memory into dst. memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_loadu_epi8&expand=3389)
 */
public fun mmLoaduEpi8(memAddr: ConstPtr): M128i = readUnaligned(ConstPtr(memAddr.rawAddress, memAddr.rawMeta), typeLayoutT = UnresolvedTypeLayout<M128i>())

// should be vmovdqu16

/**
 * Store 512-bits (composed of 32 packed 16-bit integers) from a into memory. memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_storeu_epi16&expand=5622)
 */
public fun mm512StoreuEpi16(memAddr: MutPtr, a: M512i) {
    writeUnaligned(MutPtr(memAddr.rawAddress, memAddr.rawMeta), a, typeLayoutT = UnresolvedTypeLayout<M512i>())
}

// should be vmovdqu16

/**
 * Store 256-bits (composed of 16 packed 16-bit integers) from a into memory. memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_storeu_epi16&expand=5620)
 */
public fun mm256StoreuEpi16(memAddr: MutPtr, a: M256i) {
    writeUnaligned(MutPtr(memAddr.rawAddress, memAddr.rawMeta), a, typeLayoutT = UnresolvedTypeLayout<M256i>())
}

// should be vmovdqu16

/**
 * Store 128-bits (composed of 8 packed 16-bit integers) from a into memory. memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_storeu_epi16&expand=5618)
 */
public fun mmStoreuEpi16(memAddr: MutPtr, a: M128i) {
    writeUnaligned(MutPtr(memAddr.rawAddress, memAddr.rawMeta), a, typeLayoutT = UnresolvedTypeLayout<M128i>())
}

// should be vmovdqu8

/**
 * Store 512-bits (composed of 64 packed 8-bit integers) from a into memory. memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_storeu_epi8&expand=5640)
 */
public fun mm512StoreuEpi8(memAddr: MutPtr, a: M512i) {
    writeUnaligned(MutPtr(memAddr.rawAddress, memAddr.rawMeta), a, typeLayoutT = UnresolvedTypeLayout<M512i>())
}

// should be vmovdqu8

/**
 * Store 256-bits (composed of 32 packed 8-bit integers) from a into memory. memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_storeu_epi8&expand=5638)
 */
public fun mm256StoreuEpi8(memAddr: MutPtr, a: M256i) {
    writeUnaligned(MutPtr(memAddr.rawAddress, memAddr.rawMeta), a, typeLayoutT = UnresolvedTypeLayout<M256i>())
}

// should be vmovdqu8

/**
 * Store 128-bits (composed of 16 packed 8-bit integers) from a into memory. memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_storeu_epi8&expand=5636)
 */
public fun mmStoreuEpi8(memAddr: MutPtr, a: M128i) {
    writeUnaligned(MutPtr(memAddr.rawAddress, memAddr.rawMeta), a, typeLayoutT = UnresolvedTypeLayout<M128i>())
}

/**
 * Load packed 16-bit integers from memory into dst using writemask k
 * (elements are copied from src when the corresponding mask bit is not set).
 * memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_loadu_epi16)
 */
public fun mm512MaskLoaduEpi16(src: M512i, k: Mmask32, memAddr: ConstPtr): M512i {
    val mask = run { val simdArg0 = k; val simdArg1 = Simd<Short>(List(32) { (0.inv()).toShort() }); val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }
    return run { val simdArg0 = mask; val simdArg1 = memAddr; val simdArg2 = src.asI16x32(); simdArg2 }.asM512i()
}

/**
 * Load packed 16-bit integers from memory into dst using zeromask k
 * (elements are zeroed out when the corresponding mask bit is not set).
 * memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_loadu_epi16)
 */
public fun mm512MaskzLoaduEpi16(k: Mmask32, memAddr: ConstPtr): M512i = mm512MaskLoaduEpi16(mm512SetzeroSi512(), k, memAddr)

/**
 * Load packed 8-bit integers from memory into dst using writemask k
 * (elements are copied from src when the corresponding mask bit is not set).
 * memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_loadu_epi8)
 */
public fun mm512MaskLoaduEpi8(src: M512i, k: Mmask64, memAddr: ConstPtr): M512i {
    val mask = run { val simdArg0 = k; val simdArg1 = Simd<Byte>(List(64) { (0.inv()).toByte() }); val simdArg2 = Simd<Byte>(List(64) { 0.toByte() }); simdArg1 }
    return run { val simdArg0 = mask; val simdArg1 = memAddr; val simdArg2 = src.asI8x64(); simdArg2 }.asM512i()
}

/**
 * Load packed 8-bit integers from memory into dst using zeromask k
 * (elements are zeroed out when the corresponding mask bit is not set).
 * memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_loadu_epi8)
 */
public fun mm512MaskzLoaduEpi8(k: Mmask64, memAddr: ConstPtr): M512i = mm512MaskLoaduEpi8(mm512SetzeroSi512(), k, memAddr)

/**
 * Load packed 16-bit integers from memory into dst using writemask k
 * (elements are copied from src when the corresponding mask bit is not set).
 * memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_loadu_epi16)
 */
public fun mm256MaskLoaduEpi16(src: M256i, k: Mmask16, memAddr: ConstPtr): M256i {
    val mask = run { val simdArg0 = k; val simdArg1 = Simd<Short>(List(16) { (0.inv()).toShort() }); val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }
    return run { val simdArg0 = mask; val simdArg1 = memAddr; val simdArg2 = src.asI16x16(); simdArg2 }.asM256i()
}

/**
 * Load packed 16-bit integers from memory into dst using zeromask k
 * (elements are zeroed out when the corresponding mask bit is not set).
 * memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_loadu_epi16)
 */
public fun mm256MaskzLoaduEpi16(k: Mmask16, memAddr: ConstPtr): M256i = mm256MaskLoaduEpi16(mm256SetzeroSi256(), k, memAddr)

/**
 * Load packed 8-bit integers from memory into dst using writemask k
 * (elements are copied from src when the corresponding mask bit is not set).
 * memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_loadu_epi8)
 */
public fun mm256MaskLoaduEpi8(src: M256i, k: Mmask32, memAddr: ConstPtr): M256i {
    val mask = run { val simdArg0 = k; val simdArg1 = Simd<Byte>(List(32) { (0.inv()).toByte() }); val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }
    return run { val simdArg0 = mask; val simdArg1 = memAddr; val simdArg2 = src.asI8x32(); simdArg2 }.asM256i()
}

/**
 * Load packed 8-bit integers from memory into dst using zeromask k
 * (elements are zeroed out when the corresponding mask bit is not set).
 * memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_loadu_epi8)
 */
public fun mm256MaskzLoaduEpi8(k: Mmask32, memAddr: ConstPtr): M256i = mm256MaskLoaduEpi8(mm256SetzeroSi256(), k, memAddr)

/**
 * Load packed 16-bit integers from memory into dst using writemask k
 * (elements are copied from src when the corresponding mask bit is not set).
 * memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_loadu_epi16)
 */
public fun mmMaskLoaduEpi16(src: M128i, k: Mmask8, memAddr: ConstPtr): M128i {
    val mask = run { val simdArg0 = k; val simdArg1 = Simd<Short>(List(8) { (0.inv()).toShort() }); val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }
    return run { val simdArg0 = mask; val simdArg1 = memAddr; val simdArg2 = src.asI16x8(); simdArg2 }.asM128i()
}

/**
 * Load packed 16-bit integers from memory into dst using zeromask k
 * (elements are zeroed out when the corresponding mask bit is not set).
 * memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_loadu_epi16)
 */
public fun mmMaskzLoaduEpi16(k: Mmask8, memAddr: ConstPtr): M128i = mmMaskLoaduEpi16(mmSetzeroSi128(), k, memAddr)

/**
 * Load packed 8-bit integers from memory into dst using writemask k
 * (elements are copied from src when the corresponding mask bit is not set).
 * memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_loadu_epi8)
 */
public fun mmMaskLoaduEpi8(src: M128i, k: Mmask16, memAddr: ConstPtr): M128i {
    val mask = run { val simdArg0 = k; val simdArg1 = Simd<Byte>(List(16) { (0.inv()).toByte() }); val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }
    return run { val simdArg0 = mask; val simdArg1 = memAddr; val simdArg2 = src.asI8x16(); simdArg2 }.asM128i()
}

/**
 * Load packed 8-bit integers from memory into dst using zeromask k
 * (elements are zeroed out when the corresponding mask bit is not set).
 * memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_loadu_epi8)
 */
public fun mmMaskzLoaduEpi8(k: Mmask16, memAddr: ConstPtr): M128i = mmMaskLoaduEpi8(mmSetzeroSi128(), k, memAddr)

/**
 * Store packed 16-bit integers from a into memory using writemask k.
 * memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_storeu_epi16)
 */
public fun mm512MaskStoreuEpi16(memAddr: MutPtr, mask: Mmask32, a: M512i) {
    val mask1 = run { val simdArg0 = mask; val simdArg1 = Simd<Short>(List(32) { (0.inv()).toShort() }); val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }
    run { val simdArg0 = mask1; val simdArg1 = memAddr; val simdArg2 = a.asI16x32(); Unit }
}

/**
 * Store packed 8-bit integers from a into memory using writemask k.
 * memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_storeu_epi8)
 */
public fun mm512MaskStoreuEpi8(memAddr: MutPtr, mask: Mmask64, a: M512i) {
    val mask1 = run { val simdArg0 = mask; val simdArg1 = Simd<Byte>(List(64) { (0.inv()).toByte() }); val simdArg2 = Simd<Byte>(List(64) { 0.toByte() }); simdArg1 }
    run { val simdArg0 = mask1; val simdArg1 = memAddr; val simdArg2 = a.asI8x64(); Unit }
}

/**
 * Store packed 16-bit integers from a into memory using writemask k.
 * memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_storeu_epi16)
 */
public fun mm256MaskStoreuEpi16(memAddr: MutPtr, mask: Mmask16, a: M256i) {
    val mask1 = run { val simdArg0 = mask; val simdArg1 = Simd<Short>(List(16) { (0.inv()).toShort() }); val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }
    run { val simdArg0 = mask1; val simdArg1 = memAddr; val simdArg2 = a.asI16x16(); Unit }
}

/**
 * Store packed 8-bit integers from a into memory using writemask k.
 * memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_storeu_epi8)
 */
public fun mm256MaskStoreuEpi8(memAddr: MutPtr, mask: Mmask32, a: M256i) {
    val mask1 = run { val simdArg0 = mask; val simdArg1 = Simd<Byte>(List(32) { (0.inv()).toByte() }); val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }
    run { val simdArg0 = mask1; val simdArg1 = memAddr; val simdArg2 = a.asI8x32(); Unit }
}

/**
 * Store packed 16-bit integers from a into memory using writemask k.
 * memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_storeu_epi16)
 */
public fun mmMaskStoreuEpi16(memAddr: MutPtr, mask: Mmask8, a: M128i) {
    val mask1 = run { val simdArg0 = mask; val simdArg1 = Simd<Short>(List(8) { (0.inv()).toShort() }); val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }
    run { val simdArg0 = mask1; val simdArg1 = memAddr; val simdArg2 = a.asI16x8(); Unit }
}

/**
 * Store packed 8-bit integers from a into memory using writemask k.
 * memAddr does not need to be aligned on any particular boundary.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_storeu_epi8)
 */
public fun mmMaskStoreuEpi8(memAddr: MutPtr, mask: Mmask16, a: M128i) {
    val mask1 = run { val simdArg0 = mask; val simdArg1 = Simd<Byte>(List(16) { (0.inv()).toByte() }); val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }
    run { val simdArg0 = mask1; val simdArg1 = memAddr; val simdArg2 = a.asI8x16(); Unit }
}

/**
 * Multiply packed signed 16-bit integers in a and b, producing intermediate signed 32-bit integers. Horizontally add adjacent pairs of intermediate 32-bit integers, and pack the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_madd_epi16&expand=3511)
 */
public fun mm512MaddEpi16(a: M512i, b: M512i): M512i {
    // It's a trick used in the Adler-32 algorithm to perform a widening addition.
    //
    // `rust
    // @targetFeature(enable = "avx512bw")
    // unchecked fn wideningAdd(mad: __m512i) -> __m512i (
    // _mm512_madd_epi16(mad, _mm512_set1_epi16(1))
    // )
    // `
    //
    // If we implement this using generic vector intrinsics, the optimizer
    // will eliminate this pattern, and `vpmaddwd` will no longer be emitted.
    // For this reason, we use x86 intrinsics.
    return transmuteWithLayouts<I32x16, M512i>(vpmaddwd(a.asI16x32(), b.asI16x32()), UnresolvedTypeLayout<I32x16>(), UnresolvedTypeLayout<M512i>())
}

/**
 * Multiply packed signed 16-bit integers in a and b, producing intermediate signed 32-bit integers. Horizontally add adjacent pairs of intermediate 32-bit integers, and pack the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_madd_epi16&expand=3512)
 */
public fun mm512MaskMaddEpi16(src: M512i, k: Mmask16, a: M512i, b: M512i): M512i =
    run {
        val madd = mm512MaddEpi16(a, b).asI32x16()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = madd; val simdArg2 = src.asI32x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Multiply packed signed 16-bit integers in a and b, producing intermediate signed 32-bit integers. Horizontally add adjacent pairs of intermediate 32-bit integers, and pack the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_madd_epi16&expand=3513)
 */
public fun mm512MaskzMaddEpi16(k: Mmask16, a: M512i, b: M512i): M512i =
    run {
        val madd = mm512MaddEpi16(a, b).asI32x16()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = madd; val simdArg2 = Simd<Int>(List(16) { 0 }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Multiply packed signed 16-bit integers in a and b, producing intermediate signed 32-bit integers. Horizontally add adjacent pairs of intermediate 32-bit integers, and pack the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_madd_epi16&expand=3509)
 */
public fun mm256MaskMaddEpi16(src: M256i, k: Mmask8, a: M256i, b: M256i): M256i =
    run {
        val madd = mm256MaddEpi16(a, b).asI32x8()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = madd; val simdArg2 = src.asI32x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Multiply packed signed 16-bit integers in a and b, producing intermediate signed 32-bit integers. Horizontally add adjacent pairs of intermediate 32-bit integers, and pack the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_madd_epi16&expand=3510)
 */
public fun mm256MaskzMaddEpi16(k: Mmask8, a: M256i, b: M256i): M256i =
    run {
        val madd = mm256MaddEpi16(a, b).asI32x8()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = madd; val simdArg2 = Simd<Int>(List(8) { 0 }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Multiply packed signed 16-bit integers in a and b, producing intermediate signed 32-bit integers. Horizontally add adjacent pairs of intermediate 32-bit integers, and pack the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_madd_epi16&expand=3506)
 */
public fun mmMaskMaddEpi16(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val madd = mmMaddEpi16(a, b).asI32x4()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = madd; val simdArg2 = src.asI32x4(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Multiply packed signed 16-bit integers in a and b, producing intermediate signed 32-bit integers. Horizontally add adjacent pairs of intermediate 32-bit integers, and pack the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_madd_epi16&expand=3507)
 */
public fun mmMaskzMaddEpi16(k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val madd = mmMaddEpi16(a, b).asI32x4()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = madd; val simdArg2 = Simd<Int>(List(4) { 0 }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Vertically multiply each unsigned 8-bit integer from a with the corresponding signed 8-bit integer from b, producing intermediate signed 16-bit integers. Horizontally add adjacent pairs of intermediate signed 16-bit integers, and pack the saturated results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maddubs_epi16&expand=3539)
 */
public fun mm512MaddubsEpi16(a: M512i, b: M512i): M512i = transmuteWithLayouts<I16x32, M512i>(vpmaddubsw(a.asU8x64(), b.asI8x64()), UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())

/**
 * Multiply packed unsigned 8-bit integers in a by packed signed 8-bit integers in b, producing intermediate signed 16-bit integers. Horizontally add adjacent pairs of intermediate signed 16-bit integers, and pack the saturated results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_maddubs_epi16&expand=3540)
 */
public fun mm512MaskMaddubsEpi16(src: M512i, k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val madd = mm512MaddubsEpi16(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = madd; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Multiply packed unsigned 8-bit integers in a by packed signed 8-bit integers in b, producing intermediate signed 16-bit integers. Horizontally add adjacent pairs of intermediate signed 16-bit integers, and pack the saturated results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_maddubs_epi16&expand=3541)
 */
public fun mm512MaskzMaddubsEpi16(k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val madd = mm512MaddubsEpi16(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = madd; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Multiply packed unsigned 8-bit integers in a by packed signed 8-bit integers in b, producing intermediate signed 16-bit integers. Horizontally add adjacent pairs of intermediate signed 16-bit integers, and pack the saturated results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_maddubs_epi16&expand=3537)
 */
public fun mm256MaskMaddubsEpi16(src: M256i, k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val madd = mm256MaddubsEpi16(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = madd; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Multiply packed unsigned 8-bit integers in a by packed signed 8-bit integers in b, producing intermediate signed 16-bit integers. Horizontally add adjacent pairs of intermediate signed 16-bit integers, and pack the saturated results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_maddubs_epi16&expand=3538)
 */
public fun mm256MaskzMaddubsEpi16(k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val madd = mm256MaddubsEpi16(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = madd; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Multiply packed unsigned 8-bit integers in a by packed signed 8-bit integers in b, producing intermediate signed 16-bit integers. Horizontally add adjacent pairs of intermediate signed 16-bit integers, and pack the saturated results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_maddubs_epi16&expand=3534)
 */
public fun mmMaskMaddubsEpi16(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val madd = mmMaddubsEpi16(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = madd; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Multiply packed unsigned 8-bit integers in a by packed signed 8-bit integers in b, producing intermediate signed 16-bit integers. Horizontally add adjacent pairs of intermediate signed 16-bit integers, and pack the saturated results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_maddubs_epi16&expand=3535)
 */
public fun mmMaskzMaddubsEpi16(k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val madd = mmMaddubsEpi16(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = madd; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Convert packed signed 32-bit integers from a and b to packed 16-bit integers using signed saturation, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_packs_epi32&expand=4091)
 */
public fun mm512PacksEpi32(a: M512i, b: M512i): M512i = transmuteWithLayouts<I16x32, M512i>(vpackssdw(a.asI32x16(), b.asI32x16()), UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())

/**
 * Convert packed signed 32-bit integers from a and b to packed 16-bit integers using signed saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_packs_epi32&expand=4089)
 */
public fun mm512MaskPacksEpi32(src: M512i, k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val pack = mm512PacksEpi32(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = pack; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Convert packed signed 32-bit integers from a and b to packed 16-bit integers using signed saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_packs_epi32&expand=4090)
 */
public fun mm512MaskzPacksEpi32(k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val pack = mm512PacksEpi32(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = pack; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Convert packed signed 32-bit integers from a and b to packed 16-bit integers using signed saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_packs_epi32&expand=4086)
 */
public fun mm256MaskPacksEpi32(src: M256i, k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val pack = mm256PacksEpi32(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = pack; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Convert packed signed 32-bit integers from a and b to packed 16-bit integers using signed saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_packs_epi32&expand=4087)
 */
public fun mm256MaskzPacksEpi32(k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val pack = mm256PacksEpi32(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = pack; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Convert packed signed 32-bit integers from a and b to packed 16-bit integers using signed saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_packs_epi32&expand=4083)
 */
public fun mmMaskPacksEpi32(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val pack = mmPacksEpi32(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = pack; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Convert packed signed 32-bit integers from a and b to packed 16-bit integers using signed saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_packs_epi32&expand=4084)
 */
public fun mmMaskzPacksEpi32(k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val pack = mmPacksEpi32(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = pack; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Convert packed signed 16-bit integers from a and b to packed 8-bit integers using signed saturation, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_packs_epi16&expand=4082)
 */
public fun mm512PacksEpi16(a: M512i, b: M512i): M512i = transmuteWithLayouts<I8x64, M512i>(vpacksswb(a.asI16x32(), b.asI16x32()), UnresolvedTypeLayout<I8x64>(), UnresolvedTypeLayout<M512i>())

/**
 * Convert packed signed 16-bit integers from a and b to packed 8-bit integers using signed saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_packs_epi16&expand=4080)
 */
public fun mm512MaskPacksEpi16(src: M512i, k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val pack = mm512PacksEpi16(a, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = pack; val simdArg2 = src.asI8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Convert packed signed 16-bit integers from a and b to packed 8-bit integers using signed saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_packs_epi16&expand=4081)
 */
public fun mm512MaskzPacksEpi16(k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val pack = mm512PacksEpi16(a, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = pack; val simdArg2 = Simd<Byte>(List(64) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Convert packed signed 16-bit integers from a and b to packed 8-bit integers using signed saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_packs_epi16&expand=4077)
 */
public fun mm256MaskPacksEpi16(src: M256i, k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val pack = mm256PacksEpi16(a, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = pack; val simdArg2 = src.asI8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Convert packed signed 16-bit integers from a and b to packed 8-bit integers using signed saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=#text=_mm256_maskz_packs_epi16&expand=4078)
 */
public fun mm256MaskzPacksEpi16(k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val pack = mm256PacksEpi16(a, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = pack; val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Convert packed signed 16-bit integers from a and b to packed 8-bit integers using signed saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_packs_epi16&expand=4074)
 */
public fun mmMaskPacksEpi16(src: M128i, k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val pack = mmPacksEpi16(a, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = pack; val simdArg2 = src.asI8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Convert packed signed 16-bit integers from a and b to packed 8-bit integers using signed saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_packs_epi16&expand=4075)
 */
public fun mmMaskzPacksEpi16(k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val pack = mmPacksEpi16(a, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = pack; val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Convert packed signed 32-bit integers from a and b to packed 16-bit integers using unsigned saturation, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_packus_epi32&expand=4130)
 */
public fun mm512PackusEpi32(a: M512i, b: M512i): M512i = transmuteWithLayouts<U16x32, M512i>(vpackusdw(a.asI32x16(), b.asI32x16()), UnresolvedTypeLayout<U16x32>(), UnresolvedTypeLayout<M512i>())

/**
 * Convert packed signed 32-bit integers from a and b to packed 16-bit integers using unsigned saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_packus_epi32&expand=4128)
 */
public fun mm512MaskPackusEpi32(src: M512i, k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val pack = mm512PackusEpi32(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = pack; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Convert packed signed 32-bit integers from a and b to packed 16-bit integers using unsigned saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_packus_epi32&expand=4129)
 */
public fun mm512MaskzPackusEpi32(k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val pack = mm512PackusEpi32(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = pack; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Convert packed signed 32-bit integers from a and b to packed 16-bit integers using unsigned saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_packus_epi32&expand=4125)
 */
public fun mm256MaskPackusEpi32(src: M256i, k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val pack = mm256PackusEpi32(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = pack; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Convert packed signed 32-bit integers from a and b to packed 16-bit integers using unsigned saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_packus_epi32&expand=4126)
 */
public fun mm256MaskzPackusEpi32(k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val pack = mm256PackusEpi32(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = pack; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Convert packed signed 32-bit integers from a and b to packed 16-bit integers using unsigned saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_packus_epi32&expand=4122)
 */
public fun mmMaskPackusEpi32(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val pack = mmPackusEpi32(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = pack; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Convert packed signed 32-bit integers from a and b to packed 16-bit integers using unsigned saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_packus_epi32&expand=4123)
 */
public fun mmMaskzPackusEpi32(k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val pack = mmPackusEpi32(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = pack; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Convert packed signed 16-bit integers from a and b to packed 8-bit integers using unsigned saturation, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_packus_epi16&expand=4121)
 */
public fun mm512PackusEpi16(a: M512i, b: M512i): M512i = transmuteWithLayouts<U8x64, M512i>(vpackuswb(a.asI16x32(), b.asI16x32()), UnresolvedTypeLayout<U8x64>(), UnresolvedTypeLayout<M512i>())

/**
 * Convert packed signed 16-bit integers from a and b to packed 8-bit integers using unsigned saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_packus_epi16&expand=4119)
 */
public fun mm512MaskPackusEpi16(src: M512i, k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val pack = mm512PackusEpi16(a, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = pack; val simdArg2 = src.asI8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Convert packed signed 16-bit integers from a and b to packed 8-bit integers using unsigned saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_packus_epi16&expand=4120)
 */
public fun mm512MaskzPackusEpi16(k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val pack = mm512PackusEpi16(a, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = pack; val simdArg2 = Simd<Byte>(List(64) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Convert packed signed 16-bit integers from a and b to packed 8-bit integers using unsigned saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_packus_epi16&expand=4116)
 */
public fun mm256MaskPackusEpi16(src: M256i, k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val pack = mm256PackusEpi16(a, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = pack; val simdArg2 = src.asI8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Convert packed signed 16-bit integers from a and b to packed 8-bit integers using unsigned saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_packus_epi16&expand=4117)
 */
public fun mm256MaskzPackusEpi16(k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val pack = mm256PackusEpi16(a, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = pack; val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Convert packed signed 16-bit integers from a and b to packed 8-bit integers using unsigned saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_packus_epi16&expand=4113)
 */
public fun mmMaskPackusEpi16(src: M128i, k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val pack = mmPackusEpi16(a, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = pack; val simdArg2 = src.asI8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Convert packed signed 16-bit integers from a and b to packed 8-bit integers using unsigned saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_packus_epi16&expand=4114)
 */
public fun mmMaskzPackusEpi16(k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val pack = mmPackusEpi16(a, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = pack; val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Average packed unsigned 16-bit integers in a and b, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_avg_epu16&expand=388)
 */
public fun mm512AvgEpu16(a: M512i, b: M512i): M512i = transmuteWithLayouts<U16x32, M512i>(vpavgw(a.asU16x32(), b.asU16x32()), UnresolvedTypeLayout<U16x32>(), UnresolvedTypeLayout<M512i>())

/**
 * Average packed unsigned 16-bit integers in a and b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_avg_epu16&expand=389)
 */
public fun mm512MaskAvgEpu16(src: M512i, k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val avg = mm512AvgEpu16(a, b).asU16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = avg; val simdArg2 = src.asU16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Average packed unsigned 16-bit integers in a and b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_avg_epu16&expand=390)
 */
public fun mm512MaskzAvgEpu16(k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val avg = mm512AvgEpu16(a, b).asU16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = avg; val simdArg2 = Simd<UShort>(List(32) { 0u.toUShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Average packed unsigned 16-bit integers in a and b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_avg_epu16&expand=386)
 */
public fun mm256MaskAvgEpu16(src: M256i, k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val avg = mm256AvgEpu16(a, b).asU16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = avg; val simdArg2 = src.asU16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Average packed unsigned 16-bit integers in a and b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_avg_epu16&expand=387)
 */
public fun mm256MaskzAvgEpu16(k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val avg = mm256AvgEpu16(a, b).asU16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = avg; val simdArg2 = Simd<UShort>(List(16) { 0u.toUShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Average packed unsigned 16-bit integers in a and b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_avg_epu16&expand=383)
 */
public fun mmMaskAvgEpu16(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val avg = mmAvgEpu16(a, b).asU16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = avg; val simdArg2 = src.asU16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Average packed unsigned 16-bit integers in a and b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_avg_epu16&expand=384)
 */
public fun mmMaskzAvgEpu16(k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val avg = mmAvgEpu16(a, b).asU16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = avg; val simdArg2 = Simd<UShort>(List(8) { 0u.toUShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Average packed unsigned 8-bit integers in a and b, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_avg_epu8&expand=397)
 */
public fun mm512AvgEpu8(a: M512i, b: M512i): M512i = transmuteWithLayouts<U8x64, M512i>(vpavgb(a.asU8x64(), b.asU8x64()), UnresolvedTypeLayout<U8x64>(), UnresolvedTypeLayout<M512i>())

/**
 * Average packed unsigned 8-bit integers in a and b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_avg_epu8&expand=398)
 */
public fun mm512MaskAvgEpu8(src: M512i, k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val avg = mm512AvgEpu8(a, b).asU8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = avg; val simdArg2 = src.asU8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Average packed unsigned 8-bit integers in a and b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_avg_epu8&expand=399)
 */
public fun mm512MaskzAvgEpu8(k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val avg = mm512AvgEpu8(a, b).asU8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = avg; val simdArg2 = Simd<UByte>(List(64) { 0u.toUByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Average packed unsigned 8-bit integers in a and b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_avg_epu8&expand=395)
 */
public fun mm256MaskAvgEpu8(src: M256i, k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val avg = mm256AvgEpu8(a, b).asU8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = avg; val simdArg2 = src.asU8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Average packed unsigned 8-bit integers in a and b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_avg_epu8&expand=396)
 */
public fun mm256MaskzAvgEpu8(k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val avg = mm256AvgEpu8(a, b).asU8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = avg; val simdArg2 = Simd<UByte>(List(32) { 0u.toUByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Average packed unsigned 8-bit integers in a and b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_avg_epu8&expand=392)
 */
public fun mmMaskAvgEpu8(src: M128i, k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val avg = mmAvgEpu8(a, b).asU8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = avg; val simdArg2 = src.asU8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Average packed unsigned 8-bit integers in a and b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_avg_epu8&expand=393)
 */
public fun mmMaskzAvgEpu8(k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val avg = mmAvgEpu8(a, b).asU8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = avg; val simdArg2 = Simd<UByte>(List(16) { 0u.toUByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shift packed 16-bit integers in a left by count while shifting in zeros, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_sll_epi16&expand=5271)
 */
public fun mm512SllEpi16(a: M512i, count: M128i): M512i = transmuteWithLayouts<I16x32, M512i>(vpsllw(a.asI16x32(), count.asI16x8()), UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())

/**
 * Shift packed 16-bit integers in a left by count while shifting in zeros, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_sll_epi16&expand=5269)
 */
public fun mm512MaskSllEpi16(src: M512i, k: Mmask32, a: M512i, count: M128i): M512i =
    run {
        val shf = mm512SllEpi16(a, count).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shift packed 16-bit integers in a left by count while shifting in zeros, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_sll_epi16&expand=5270)
 */
public fun mm512MaskzSllEpi16(k: Mmask32, a: M512i, count: M128i): M512i =
    run {
        val shf = mm512SllEpi16(a, count).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shift packed 16-bit integers in a left by count while shifting in zeros, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_sll_epi16&expand=5266)
 */
public fun mm256MaskSllEpi16(src: M256i, k: Mmask16, a: M256i, count: M128i): M256i =
    run {
        val shf = mm256SllEpi16(a, count).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shift packed 16-bit integers in a left by count while shifting in zeros, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_sll_epi16&expand=5267)
 */
public fun mm256MaskzSllEpi16(k: Mmask16, a: M256i, count: M128i): M256i =
    run {
        val shf = mm256SllEpi16(a, count).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shift packed 16-bit integers in a left by count while shifting in zeros, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_sll_epi16&expand=5263)
 */
public fun mmMaskSllEpi16(src: M128i, k: Mmask8, a: M128i, count: M128i): M128i =
    run {
        val shf = mmSllEpi16(a, count).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shift packed 16-bit integers in a left by count while shifting in zeros, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_sll_epi16&expand=5264)
 */
public fun mmMaskzSllEpi16(k: Mmask8, a: M128i, count: M128i): M128i =
    run {
        val shf = mmSllEpi16(a, count).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shift packed 16-bit integers in a left by imm8 while shifting in zeros, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_slli_epi16&expand=5301)
 */
public fun mm512SlliEpi16(a: M512i, imm8: UInt): M512i =
    run {
        run {
            check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        if (imm8 >= 16u) {
            mm512SetzeroSi512()
        } else {
            transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = a.asU16x32(); val simdArg1 = Simd<UShort>(List(32) { imm8.toUShort() }); simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
        }
    }

/**
 * Shift packed 16-bit integers in a left by imm8 while shifting in zeros, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_slli_epi16&expand=5299)
 */
public fun mm512MaskSlliEpi16(src: M512i, k: Mmask32, a: M512i, imm8: UInt): M512i =
    run {
        run {
            check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf =
            if (imm8 >= 16u) {
                Simd<UShort>(List(32) { 0u.toUShort() })
            } else {
                run { val simdArg0 = a.asU16x32(); val simdArg1 = Simd<UShort>(List(32) { imm8.toUShort() }); simdArg0 }
            }
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = src.asU16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shift packed 16-bit integers in a left by imm8 while shifting in zeros, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_slli_epi16&expand=5300)
 */
public fun mm512MaskzSlliEpi16(k: Mmask32, a: M512i, imm8: UInt): M512i =
    run {
        run {
            check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        if (imm8 >= 16u) {
            mm512SetzeroSi512()
        } else {
            val shf = run { val simdArg0 = a.asU16x32(); val simdArg1 = Simd<UShort>(List(32) { imm8.toUShort() }); simdArg0 }
            transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = Simd<UShort>(List(32) { 0u.toUShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
        }
    }

/**
 * Shift packed 16-bit integers in a left by imm8 while shifting in zeros, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_slli_epi16&expand=5296)
 */
public fun mm256MaskSlliEpi16(src: M256i, k: Mmask16, a: M256i, imm8: UInt): M256i =
    run {
        run {
            check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf =
            if (imm8 >= 16u) {
                Simd<UShort>(List(16) { 0u.toUShort() })
            } else {
                run { val simdArg0 = a.asU16x16(); val simdArg1 = Simd<UShort>(List(16) { imm8.toUShort() }); simdArg0 }
            }
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = src.asU16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shift packed 16-bit integers in a left by imm8 while shifting in zeros, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_slli_epi16&expand=5297)
 */
public fun mm256MaskzSlliEpi16(k: Mmask16, a: M256i, imm8: UInt): M256i =
    run {
        run {
            check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        if (imm8 >= 16u) {
            mm256SetzeroSi256()
        } else {
            val shf = run { val simdArg0 = a.asU16x16(); val simdArg1 = Simd<UShort>(List(16) { imm8.toUShort() }); simdArg0 }
            transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = Simd<UShort>(List(16) { 0u.toUShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
        }
    }

/**
 * Shift packed 16-bit integers in a left by imm8 while shifting in zeros, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_slli_epi16&expand=5293)
 */
public fun mmMaskSlliEpi16(src: M128i, k: Mmask8, a: M128i, imm8: UInt): M128i =
    run {
        run {
            check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf =
            if (imm8 >= 16u) {
                Simd<UShort>(List(8) { 0u.toUShort() })
            } else {
                run { val simdArg0 = a.asU16x8(); val simdArg1 = Simd<UShort>(List(8) { imm8.toUShort() }); simdArg0 }
            }
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = src.asU16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shift packed 16-bit integers in a left by imm8 while shifting in zeros, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_slli_epi16&expand=5294)
 */
public fun mmMaskzSlliEpi16(k: Mmask8, a: M128i, imm8: UInt): M128i =
    run {
        run {
            check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        if (imm8 >= 16u) {
            mmSetzeroSi128()
        } else {
            val shf = run { val simdArg0 = a.asU16x8(); val simdArg1 = Simd<UShort>(List(8) { imm8.toUShort() }); simdArg0 }
            transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = Simd<UShort>(List(8) { 0u.toUShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
        }
    }

/**
 * Shift packed 16-bit integers in a left by the amount specified by the corresponding element in count while shifting in zeros, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_sllv_epi16&expand=5333)
 */
public fun mm512SllvEpi16(a: M512i, count: M512i): M512i = transmuteWithLayouts<I16x32, M512i>(vpsllvw(a.asI16x32(), count.asI16x32()), UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())

/**
 * Shift packed 16-bit integers in a left by the amount specified by the corresponding element in count while shifting in zeros, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_sllv_epi16&expand=5331)
 */
public fun mm512MaskSllvEpi16(src: M512i, k: Mmask32, a: M512i, count: M512i): M512i =
    run {
        val shf = mm512SllvEpi16(a, count).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shift packed 16-bit integers in a left by the amount specified by the corresponding element in count while shifting in zeros, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_sllv_epi16&expand=5332)
 */
public fun mm512MaskzSllvEpi16(k: Mmask32, a: M512i, count: M512i): M512i =
    run {
        val shf = mm512SllvEpi16(a, count).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shift packed 16-bit integers in a left by the amount specified by the corresponding element in count while shifting in zeros, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_sllv_epi16&expand=5330)
 */
public fun mm256SllvEpi16(a: M256i, count: M256i): M256i = transmuteWithLayouts<I16x16, M256i>(vpsllvw256(a.asI16x16(), count.asI16x16()), UnresolvedTypeLayout<I16x16>(), UnresolvedTypeLayout<M256i>())

/**
 * Shift packed 16-bit integers in a left by the amount specified by the corresponding element in count while shifting in zeros, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_sllv_epi16&expand=5328)
 */
public fun mm256MaskSllvEpi16(src: M256i, k: Mmask16, a: M256i, count: M256i): M256i =
    run {
        val shf = mm256SllvEpi16(a, count).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shift packed 16-bit integers in a left by the amount specified by the corresponding element in count while shifting in zeros, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_sllv_epi16&expand=5329)
 */
public fun mm256MaskzSllvEpi16(k: Mmask16, a: M256i, count: M256i): M256i =
    run {
        val shf = mm256SllvEpi16(a, count).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shift packed 16-bit integers in a left by the amount specified by the corresponding element in count while shifting in zeros, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_sllv_epi16&expand=5327)
 */
public fun mmSllvEpi16(a: M128i, count: M128i): M128i = transmuteWithLayouts<I16x8, M128i>(vpsllvw128(a.asI16x8(), count.asI16x8()), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<M128i>())

/**
 * Shift packed 16-bit integers in a left by the amount specified by the corresponding element in count while shifting in zeros, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_sllv_epi16&expand=5325)
 */
public fun mmMaskSllvEpi16(src: M128i, k: Mmask8, a: M128i, count: M128i): M128i =
    run {
        val shf = mmSllvEpi16(a, count).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shift packed 16-bit integers in a left by the amount specified by the corresponding element in count while shifting in zeros, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_sllv_epi16&expand=5326)
 */
public fun mmMaskzSllvEpi16(k: Mmask8, a: M128i, count: M128i): M128i =
    run {
        val shf = mmSllvEpi16(a, count).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shift packed 16-bit integers in a right by count while shifting in zeros, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_srl_epi16&expand=5483)
 */
public fun mm512SrlEpi16(a: M512i, count: M128i): M512i = transmuteWithLayouts<I16x32, M512i>(vpsrlw(a.asI16x32(), count.asI16x8()), UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())

/**
 * Shift packed 16-bit integers in a right by count while shifting in zeros, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_srl_epi16&expand=5481)
 */
public fun mm512MaskSrlEpi16(src: M512i, k: Mmask32, a: M512i, count: M128i): M512i =
    run {
        val shf = mm512SrlEpi16(a, count).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shift packed 16-bit integers in a right by count while shifting in zeros, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_srl_epi16&expand=5482)
 */
public fun mm512MaskzSrlEpi16(k: Mmask32, a: M512i, count: M128i): M512i =
    run {
        val shf = mm512SrlEpi16(a, count).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shift packed 16-bit integers in a right by count while shifting in zeros, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_srl_epi16&expand=5478)
 */
public fun mm256MaskSrlEpi16(src: M256i, k: Mmask16, a: M256i, count: M128i): M256i =
    run {
        val shf = mm256SrlEpi16(a, count).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shift packed 16-bit integers in a right by count while shifting in zeros, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_srl_epi16&expand=5479)
 */
public fun mm256MaskzSrlEpi16(k: Mmask16, a: M256i, count: M128i): M256i =
    run {
        val shf = mm256SrlEpi16(a, count).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shift packed 16-bit integers in a right by count while shifting in zeros, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_srl_epi16&expand=5475)
 */
public fun mmMaskSrlEpi16(src: M128i, k: Mmask8, a: M128i, count: M128i): M128i =
    run {
        val shf = mmSrlEpi16(a, count).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shift packed 16-bit integers in a right by count while shifting in zeros, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_srl_epi16&expand=5476)
 */
public fun mmMaskzSrlEpi16(k: Mmask8, a: M128i, count: M128i): M128i =
    run {
        val shf = mmSrlEpi16(a, count).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shift packed 16-bit integers in a right by imm8 while shifting in zeros, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_srli_epi16&expand=5513)
 */
public fun mm512SrliEpi16(a: M512i, imm8: UInt): M512i =
    run {
        run {
            check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        if (imm8 >= 16u) {
            mm512SetzeroSi512()
        } else {
            transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = a.asU16x32(); val simdArg1 = Simd<UShort>(List(32) { imm8.toUShort() }); simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
        }
    }

/**
 * Shift packed 16-bit integers in a right by imm8 while shifting in zeros, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_srli_epi16&expand=5511)
 */
public fun mm512MaskSrliEpi16(src: M512i, k: Mmask32, a: M512i, imm8: UInt): M512i =
    run {
        run {
            check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf =
            if (imm8 >= 16u) {
                Simd<UShort>(List(32) { 0u.toUShort() })
            } else {
                run { val simdArg0 = a.asU16x32(); val simdArg1 = Simd<UShort>(List(32) { imm8.toUShort() }); simdArg0 }
            }
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = src.asU16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shift packed 16-bit integers in a right by imm8 while shifting in zeros, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_srli_epi16&expand=5512)
 */
public fun mm512MaskzSrliEpi16(k: Mmask32, a: M512i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        // imm8 should be u32, it seems the document to verify is incorrect
        if (imm8 >= 16) {
            mm512SetzeroSi512()
        } else {
            val shf = run { val simdArg0 = a.asU16x32(); val simdArg1 = Simd<UShort>(List(32) { imm8.toUShort() }); simdArg0 }
            transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = Simd<UShort>(List(32) { 0u.toUShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
        }
    }

/**
 * Shift packed 16-bit integers in a right by imm8 while shifting in zeros, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_srli_epi16&expand=5508)
 */
public fun mm256MaskSrliEpi16(src: M256i, k: Mmask16, a: M256i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm256SrliEpi16(a, imm8)
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = shf.asI16x16(); val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shift packed 16-bit integers in a right by imm8 while shifting in zeros, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_srli_epi16&expand=5509)
 */
public fun mm256MaskzSrliEpi16(k: Mmask16, a: M256i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mm256SrliEpi16(a, imm8)
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = shf.asI16x16(); val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shift packed 16-bit integers in a right by imm8 while shifting in zeros, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_srli_epi16&expand=5505)
 */
public fun mmMaskSrliEpi16(src: M128i, k: Mmask8, a: M128i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mmSrliEpi16(a, imm8)
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = shf.asI16x8(); val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shift packed 16-bit integers in a right by imm8 while shifting in zeros, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_srli_epi16&expand=5506)
 */
public fun mmMaskzSrliEpi16(k: Mmask8, a: M128i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = mmSrliEpi16(a, imm8)
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = shf.asI16x8(); val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shift packed 16-bit integers in a right by the amount specified by the corresponding element in count while shifting in zeros, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_srlv_epi16&expand=5545)
 */
public fun mm512SrlvEpi16(a: M512i, count: M512i): M512i = transmuteWithLayouts<I16x32, M512i>(vpsrlvw(a.asI16x32(), count.asI16x32()), UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())

/**
 * Shift packed 16-bit integers in a right by the amount specified by the corresponding element in count while shifting in zeros, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_srlv_epi16&expand=5543)
 */
public fun mm512MaskSrlvEpi16(src: M512i, k: Mmask32, a: M512i, count: M512i): M512i =
    run {
        val shf = mm512SrlvEpi16(a, count).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shift packed 16-bit integers in a right by the amount specified by the corresponding element in count while shifting in zeros, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_srlv_epi16&expand=5544)
 */
public fun mm512MaskzSrlvEpi16(k: Mmask32, a: M512i, count: M512i): M512i =
    run {
        val shf = mm512SrlvEpi16(a, count).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shift packed 16-bit integers in a right by the amount specified by the corresponding element in count while shifting in zeros, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_srlv_epi16&expand=5542)
 */
public fun mm256SrlvEpi16(a: M256i, count: M256i): M256i = transmuteWithLayouts<I16x16, M256i>(vpsrlvw256(a.asI16x16(), count.asI16x16()), UnresolvedTypeLayout<I16x16>(), UnresolvedTypeLayout<M256i>())

/**
 * Shift packed 16-bit integers in a right by the amount specified by the corresponding element in count while shifting in zeros, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_srlv_epi16&expand=5540)
 */
public fun mm256MaskSrlvEpi16(src: M256i, k: Mmask16, a: M256i, count: M256i): M256i =
    run {
        val shf = mm256SrlvEpi16(a, count).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shift packed 16-bit integers in a right by the amount specified by the corresponding element in count while shifting in zeros, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_srlv_epi16&expand=5541)
 */
public fun mm256MaskzSrlvEpi16(k: Mmask16, a: M256i, count: M256i): M256i =
    run {
        val shf = mm256SrlvEpi16(a, count).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shift packed 16-bit integers in a right by the amount specified by the corresponding element in count while shifting in zeros, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_srlv_epi16&expand=5539)
 */
public fun mmSrlvEpi16(a: M128i, count: M128i): M128i = transmuteWithLayouts<I16x8, M128i>(vpsrlvw128(a.asI16x8(), count.asI16x8()), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<M128i>())

/**
 * Shift packed 16-bit integers in a right by the amount specified by the corresponding element in count while shifting in zeros, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_srlv_epi16&expand=5537)
 */
public fun mmMaskSrlvEpi16(src: M128i, k: Mmask8, a: M128i, count: M128i): M128i =
    run {
        val shf = mmSrlvEpi16(a, count).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shift packed 16-bit integers in a right by the amount specified by the corresponding element in count while shifting in zeros, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_srlv_epi16&expand=5538)
 */
public fun mmMaskzSrlvEpi16(k: Mmask8, a: M128i, count: M128i): M128i =
    run {
        val shf = mmSrlvEpi16(a, count).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shift packed 16-bit integers in a right by count while shifting in sign bits, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_sra_epi16&expand=5398)
 */
public fun mm512SraEpi16(a: M512i, count: M128i): M512i = transmuteWithLayouts<I16x32, M512i>(vpsraw(a.asI16x32(), count.asI16x8()), UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())

/**
 * Shift packed 16-bit integers in a right by count while shifting in sign bits, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_sra_epi16&expand=5396)
 */
public fun mm512MaskSraEpi16(src: M512i, k: Mmask32, a: M512i, count: M128i): M512i =
    run {
        val shf = mm512SraEpi16(a, count).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shift packed 16-bit integers in a right by count while shifting in sign bits, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_sra_epi16&expand=5397)
 */
public fun mm512MaskzSraEpi16(k: Mmask32, a: M512i, count: M128i): M512i =
    run {
        val shf = mm512SraEpi16(a, count).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shift packed 16-bit integers in a right by count while shifting in sign bits, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_sra_epi16&expand=5393)
 */
public fun mm256MaskSraEpi16(src: M256i, k: Mmask16, a: M256i, count: M128i): M256i =
    run {
        val shf = mm256SraEpi16(a, count).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shift packed 16-bit integers in a right by count while shifting in sign bits, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_sra_epi16&expand=5394)
 */
public fun mm256MaskzSraEpi16(k: Mmask16, a: M256i, count: M128i): M256i =
    run {
        val shf = mm256SraEpi16(a, count).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shift packed 16-bit integers in a right by count while shifting in sign bits, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_sra_epi16&expand=5390)
 */
public fun mmMaskSraEpi16(src: M128i, k: Mmask8, a: M128i, count: M128i): M128i =
    run {
        val shf = mmSraEpi16(a, count).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shift packed 16-bit integers in a right by count while shifting in sign bits, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_sra_epi16&expand=5391)
 */
public fun mmMaskzSraEpi16(k: Mmask8, a: M128i, count: M128i): M128i =
    run {
        val shf = mmSraEpi16(a, count).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shift packed 16-bit integers in a right by imm8 while shifting in sign bits, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_srai_epi16&expand=5427)
 */
public fun mm512SraiEpi16(a: M512i, imm8: UInt): M512i =
    run {
        run {
            check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = a.asI16x32(); val simdArg1 = Simd<Short>(List(32) { imm8.min(15u).toShort() }); simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shift packed 16-bit integers in a right by imm8 while shifting in sign bits, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_srai_epi16&expand=5425)
 */
public fun mm512MaskSraiEpi16(src: M512i, k: Mmask32, a: M512i, imm8: UInt): M512i =
    run {
        run {
            check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = run { val simdArg0 = a.asI16x32(); val simdArg1 = Simd<Short>(List(32) { imm8.min(15u).toShort() }); simdArg0 }
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shift packed 16-bit integers in a right by imm8 while shifting in sign bits, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_srai_epi16&expand=5426)
 */
public fun mm512MaskzSraiEpi16(k: Mmask32, a: M512i, imm8: UInt): M512i =
    run {
        run {
            check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shf = run { val simdArg0 = a.asI16x32(); val simdArg1 = Simd<Short>(List(32) { imm8.min(15u).toShort() }); simdArg0 }
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shift packed 16-bit integers in a right by imm8 while shifting in sign bits, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_srai_epi16&expand=5422)
 */
public fun mm256MaskSraiEpi16(src: M256i, k: Mmask16, a: M256i, imm8: UInt): M256i =
    run {
        run {
            check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val r = run { val simdArg0 = a.asI16x16(); val simdArg1 = Simd<Short>(List(16) { imm8.min(15u).toShort() }); simdArg0 }
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = r; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shift packed 16-bit integers in a right by imm8 while shifting in sign bits, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_srai_epi16&expand=5423)
 */
public fun mm256MaskzSraiEpi16(k: Mmask16, a: M256i, imm8: UInt): M256i =
    run {
        run {
            check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val r = run { val simdArg0 = a.asI16x16(); val simdArg1 = Simd<Short>(List(16) { imm8.min(15u).toShort() }); simdArg0 }
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = r; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shift packed 16-bit integers in a right by imm8 while shifting in sign bits, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_srai_epi16&expand=5419)
 */
public fun mmMaskSraiEpi16(src: M128i, k: Mmask8, a: M128i, imm8: UInt): M128i =
    run {
        run {
            check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val r = run { val simdArg0 = a.asI16x8(); val simdArg1 = Simd<Short>(List(8) { imm8.min(15u).toShort() }); simdArg0 }
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = r; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shift packed 16-bit integers in a right by imm8 while shifting in sign bits, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_srai_epi16&expand=5420)
 */
public fun mmMaskzSraiEpi16(k: Mmask8, a: M128i, imm8: UInt): M128i =
    run {
        run {
            check(0u <= imm8 && imm8 < (1u shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val r = run { val simdArg0 = a.asI16x8(); val simdArg1 = Simd<Short>(List(8) { imm8.min(15u).toShort() }); simdArg0 }
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = r; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shift packed 16-bit integers in a right by the amount specified by the corresponding element in count while shifting in sign bits, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_srav_epi16&expand=5456)
 */
public fun mm512SravEpi16(a: M512i, count: M512i): M512i = transmuteWithLayouts<I16x32, M512i>(vpsravw(a.asI16x32(), count.asI16x32()), UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())

/**
 * Shift packed 16-bit integers in a right by the amount specified by the corresponding element in count while shifting in sign bits, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_srav_epi16&expand=5454)
 */
public fun mm512MaskSravEpi16(src: M512i, k: Mmask32, a: M512i, count: M512i): M512i =
    run {
        val shf = mm512SravEpi16(a, count).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shift packed 16-bit integers in a right by the amount specified by the corresponding element in count while shifting in sign bits, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_srav_epi16&expand=5455)
 */
public fun mm512MaskzSravEpi16(k: Mmask32, a: M512i, count: M512i): M512i =
    run {
        val shf = mm512SravEpi16(a, count).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shift packed 16-bit integers in a right by the amount specified by the corresponding element in count while shifting in sign bits, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_srav_epi16&expand=5453)
 */
public fun mm256SravEpi16(a: M256i, count: M256i): M256i = transmuteWithLayouts<I16x16, M256i>(vpsravw256(a.asI16x16(), count.asI16x16()), UnresolvedTypeLayout<I16x16>(), UnresolvedTypeLayout<M256i>())

/**
 * Shift packed 16-bit integers in a right by the amount specified by the corresponding element in count while shifting in sign bits, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_srav_epi16&expand=5451)
 */
public fun mm256MaskSravEpi16(src: M256i, k: Mmask16, a: M256i, count: M256i): M256i =
    run {
        val shf = mm256SravEpi16(a, count).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shift packed 16-bit integers in a right by the amount specified by the corresponding element in count while shifting in sign bits, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_srav_epi16&expand=5452)
 */
public fun mm256MaskzSravEpi16(k: Mmask16, a: M256i, count: M256i): M256i =
    run {
        val shf = mm256SravEpi16(a, count).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shift packed 16-bit integers in a right by the amount specified by the corresponding element in count while shifting in sign bits, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_srav_epi16&expand=5450)
 */
public fun mmSravEpi16(a: M128i, count: M128i): M128i = transmuteWithLayouts<I16x8, M128i>(vpsravw128(a.asI16x8(), count.asI16x8()), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<M128i>())

/**
 * Shift packed 16-bit integers in a right by the amount specified by the corresponding element in count while shifting in sign bits, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_srav_epi16&expand=5448)
 */
public fun mmMaskSravEpi16(src: M128i, k: Mmask8, a: M128i, count: M128i): M128i =
    run {
        val shf = mmSravEpi16(a, count).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shift packed 16-bit integers in a right by the amount specified by the corresponding element in count while shifting in sign bits, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_srav_epi16&expand=5449)
 */
public fun mmMaskzSravEpi16(k: Mmask8, a: M128i, count: M128i): M128i =
    run {
        val shf = mmSravEpi16(a, count).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = shf; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

// vpermi2w or vpermt2w

/**
 * Shuffle 16-bit integers in a and b across lanes using the corresponding selector and index in idx, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_permutex2var_epi16&expand=4226)
 */
public fun mm512Permutex2varEpi16(a: M512i, idx: M512i, b: M512i): M512i = transmuteWithLayouts<I16x32, M512i>(vpermi2w(a.asI16x32(), idx.asI16x32(), b.asI16x32()), UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())

/**
 * Shuffle 16-bit integers in a and b across lanes using the corresponding selector and index in idx, and store the results in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_permutex2var_epi16&expand=4223)
 */
public fun mm512MaskPermutex2varEpi16(a: M512i, k: Mmask32, idx: M512i, b: M512i): M512i =
    run {
        val permute = mm512Permutex2varEpi16(a, idx, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = a.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

// vpermi2w or vpermt2w

/**
 * Shuffle 16-bit integers in a and b across lanes using the corresponding selector and index in idx, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_permutex2var_epi16&expand=4225)
 */
public fun mm512MaskzPermutex2varEpi16(k: Mmask32, a: M512i, idx: M512i, b: M512i): M512i =
    run {
        val permute = mm512Permutex2varEpi16(a, idx, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shuffle 16-bit integers in a and b across lanes using the corresponding selector and index in idx, and store the results in dst using writemask k (elements are copied from idx when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask2_permutex2var_epi16&expand=4224)
 */
public fun mm512Mask2Permutex2varEpi16(a: M512i, idx: M512i, k: Mmask32, b: M512i): M512i =
    run {
        val permute = mm512Permutex2varEpi16(a, idx, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = idx.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

// vpermi2w or vpermt2w

/**
 * Shuffle 16-bit integers in a and b across lanes using the corresponding selector and index in idx, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_permutex2var_epi16&expand=4222)
 */
public fun mm256Permutex2varEpi16(a: M256i, idx: M256i, b: M256i): M256i = transmuteWithLayouts<I16x16, M256i>(vpermi2w256(a.asI16x16(), idx.asI16x16(), b.asI16x16()), UnresolvedTypeLayout<I16x16>(), UnresolvedTypeLayout<M256i>())

/**
 * Shuffle 16-bit integers in a and b across lanes using the corresponding selector and index in idx, and store the results in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_permutex2var_epi16&expand=4219)
 */
public fun mm256MaskPermutex2varEpi16(a: M256i, k: Mmask16, idx: M256i, b: M256i): M256i =
    run {
        val permute = mm256Permutex2varEpi16(a, idx, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = a.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

// vpermi2w or vpermt2w

/**
 * Shuffle 16-bit integers in a and b across lanes using the corresponding selector and index in idx, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_permutex2var_epi16&expand=4221)
 */
public fun mm256MaskzPermutex2varEpi16(k: Mmask16, a: M256i, idx: M256i, b: M256i): M256i =
    run {
        val permute = mm256Permutex2varEpi16(a, idx, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shuffle 16-bit integers in a and b across lanes using the corresponding selector and index in idx, and store the results in dst using writemask k (elements are copied from idx when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask2_permutex2var_epi16&expand=4220)
 */
public fun mm256Mask2Permutex2varEpi16(a: M256i, idx: M256i, k: Mmask16, b: M256i): M256i =
    run {
        val permute = mm256Permutex2varEpi16(a, idx, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = idx.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

// vpermi2w or vpermt2w

/**
 * Shuffle 16-bit integers in a and b across lanes using the corresponding selector and index in idx, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_permutex2var_epi16&expand=4218)
 */
public fun mmPermutex2varEpi16(a: M128i, idx: M128i, b: M128i): M128i = transmuteWithLayouts<I16x8, M128i>(vpermi2w128(a.asI16x8(), idx.asI16x8(), b.asI16x8()), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<M128i>())

/**
 * Shuffle 16-bit integers in a and b across lanes using the corresponding selector and index in idx, and store the results in dst using writemask k (elements are copied from a when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_permutex2var_epi16&expand=4215)
 */
public fun mmMaskPermutex2varEpi16(a: M128i, k: Mmask8, idx: M128i, b: M128i): M128i =
    run {
        val permute = mmPermutex2varEpi16(a, idx, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = a.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

// vpermi2w or vpermt2w

/**
 * Shuffle 16-bit integers in a and b across lanes using the corresponding selector and index in idx, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_permutex2var_epi16&expand=4217)
 */
public fun mmMaskzPermutex2varEpi16(k: Mmask8, a: M128i, idx: M128i, b: M128i): M128i =
    run {
        val permute = mmPermutex2varEpi16(a, idx, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shuffle 16-bit integers in a and b across lanes using the corresponding selector and index in idx, and store the results in dst using writemask k (elements are copied from idx when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask2_permutex2var_epi16&expand=4216)
 */
public fun mmMask2Permutex2varEpi16(a: M128i, idx: M128i, k: Mmask8, b: M128i): M128i =
    run {
        val permute = mmPermutex2varEpi16(a, idx, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = idx.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shuffle 16-bit integers in a across lanes using the corresponding index in idx, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_permutexvar_epi16&expand=4295)
 */
public fun mm512PermutexvarEpi16(idx: M512i, a: M512i): M512i = transmuteWithLayouts<I16x32, M512i>(vpermw(a.asI16x32(), idx.asI16x32()), UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())

/**
 * Shuffle 16-bit integers in a across lanes using the corresponding index in idx, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_permutexvar_epi16&expand=4293)
 */
public fun mm512MaskPermutexvarEpi16(src: M512i, k: Mmask32, idx: M512i, a: M512i): M512i =
    run {
        val permute = mm512PermutexvarEpi16(idx, a).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shuffle 16-bit integers in a across lanes using the corresponding index in idx, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_permutexvar_epi16&expand=4294)
 */
public fun mm512MaskzPermutexvarEpi16(k: Mmask32, idx: M512i, a: M512i): M512i =
    run {
        val permute = mm512PermutexvarEpi16(idx, a).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shuffle 16-bit integers in a across lanes using the corresponding index in idx, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_permutexvar_epi16&expand=4292)
 */
public fun mm256PermutexvarEpi16(idx: M256i, a: M256i): M256i = transmuteWithLayouts<I16x16, M256i>(vpermw256(a.asI16x16(), idx.asI16x16()), UnresolvedTypeLayout<I16x16>(), UnresolvedTypeLayout<M256i>())

/**
 * Shuffle 16-bit integers in a across lanes using the corresponding index in idx, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_permutexvar_epi16&expand=4290)
 */
public fun mm256MaskPermutexvarEpi16(src: M256i, k: Mmask16, idx: M256i, a: M256i): M256i =
    run {
        val permute = mm256PermutexvarEpi16(idx, a).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shuffle 16-bit integers in a across lanes using the corresponding index in idx, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_permutexvar_epi16&expand=4291)
 */
public fun mm256MaskzPermutexvarEpi16(k: Mmask16, idx: M256i, a: M256i): M256i =
    run {
        val permute = mm256PermutexvarEpi16(idx, a).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shuffle 16-bit integers in a across lanes using the corresponding index in idx, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_permutexvar_epi16&expand=4289)
 */
public fun mmPermutexvarEpi16(idx: M128i, a: M128i): M128i = transmuteWithLayouts<I16x8, M128i>(vpermw128(a.asI16x8(), idx.asI16x8()), UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<M128i>())

/**
 * Shuffle 16-bit integers in a across lanes using the corresponding index in idx, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_permutexvar_epi16&expand=4287)
 */
public fun mmMaskPermutexvarEpi16(src: M128i, k: Mmask8, idx: M128i, a: M128i): M128i =
    run {
        val permute = mmPermutexvarEpi16(idx, a).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shuffle 16-bit integers in a across lanes using the corresponding index in idx, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_permutexvar_epi16&expand=4288)
 */
public fun mmMaskzPermutexvarEpi16(k: Mmask8, idx: M128i, a: M128i): M128i =
    run {
        val permute = mmPermutexvarEpi16(idx, a).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = permute; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

// should be vpblendmw

/**
 * Blend packed 16-bit integers from a and b using control mask k, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_blend_epi16&expand=430)
 */
public fun mm512MaskBlendEpi16(k: Mmask32, a: M512i, b: M512i): M512i = transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = b.asI16x32(); val simdArg2 = a.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())

// should be vpblendmw

/**
 * Blend packed 16-bit integers from a and b using control mask k, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_blend_epi16&expand=429)
 */
public fun mm256MaskBlendEpi16(k: Mmask16, a: M256i, b: M256i): M256i = transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = b.asI16x16(); val simdArg2 = a.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())

// should be vpblendmw

/**
 * Blend packed 16-bit integers from a and b using control mask k, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_blend_epi16&expand=427)
 */
public fun mmMaskBlendEpi16(k: Mmask8, a: M128i, b: M128i): M128i = transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = b.asI16x8(); val simdArg2 = a.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())

// should be vpblendmb

/**
 * Blend packed 8-bit integers from a and b using control mask k, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_blend_epi8&expand=441)
 */
public fun mm512MaskBlendEpi8(k: Mmask64, a: M512i, b: M512i): M512i = transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = b.asI8x64(); val simdArg2 = a.asI8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())

// should be vpblendmb

/**
 * Blend packed 8-bit integers from a and b using control mask k, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_blend_epi8&expand=440)
 */
public fun mm256MaskBlendEpi8(k: Mmask32, a: M256i, b: M256i): M256i = transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = b.asI8x32(); val simdArg2 = a.asI8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())

// should be vpblendmb

/**
 * Blend packed 8-bit integers from a and b using control mask k, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_blend_epi8&expand=439)
 */
public fun mmMaskBlendEpi8(k: Mmask16, a: M128i, b: M128i): M128i = transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = b.asI8x16(); val simdArg2 = a.asI8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())

/**
 * Broadcast the low packed 16-bit integer from a to all elements of dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_broadcastw_epi16&expand=587)
 */
public fun mm512BroadcastwEpi16(a: M128i): M512i =
    run {
        val a1 = mm512Castsi128Si512(a).asI16x32()
        val ret: I16x32 = run { val simdArg0 = a1; val simdArg1 = a1; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); res } }
        transmuteWithLayouts<I16x32, M512i>(ret, UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Broadcast the low packed 16-bit integer from a to all elements of dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_broadcastw_epi16&expand=588)
 */
public fun mm512MaskBroadcastwEpi16(src: M512i, k: Mmask32, a: M128i): M512i =
    run {
        val broadcast = mm512BroadcastwEpi16(a).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = broadcast; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Broadcast the low packed 16-bit integer from a to all elements of dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_broadcastw_epi16&expand=589)
 */
public fun mm512MaskzBroadcastwEpi16(k: Mmask32, a: M128i): M512i =
    run {
        val broadcast = mm512BroadcastwEpi16(a).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = broadcast; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Broadcast the low packed 16-bit integer from a to all elements of dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_broadcastw_epi16&expand=585)
 */
public fun mm256MaskBroadcastwEpi16(src: M256i, k: Mmask16, a: M128i): M256i =
    run {
        val broadcast = mm256BroadcastwEpi16(a).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = broadcast; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Broadcast the low packed 16-bit integer from a to all elements of dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_broadcastw_epi16&expand=586)
 */
public fun mm256MaskzBroadcastwEpi16(k: Mmask16, a: M128i): M256i =
    run {
        val broadcast = mm256BroadcastwEpi16(a).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = broadcast; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Broadcast the low packed 16-bit integer from a to all elements of dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_broadcastw_epi16&expand=582)
 */
public fun mmMaskBroadcastwEpi16(src: M128i, k: Mmask8, a: M128i): M128i =
    run {
        val broadcast = mmBroadcastwEpi16(a).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = broadcast; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Broadcast the low packed 16-bit integer from a to all elements of dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_broadcastw_epi16&expand=583)
 */
public fun mmMaskzBroadcastwEpi16(k: Mmask8, a: M128i): M128i =
    run {
        val broadcast = mmBroadcastwEpi16(a).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = broadcast; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Broadcast the low packed 8-bit integer from a to all elements of dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_broadcastb_epi8&expand=536)
 */
public fun mm512BroadcastbEpi8(a: M128i): M512i =
    run {
        val a1 = mm512Castsi128Si512(a).asI8x64()
        val ret: I8x64 = run { val simdArg0 = a1; val simdArg1 = a1; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x64>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); res } }
        transmuteWithLayouts<I8x64, M512i>(ret, UnresolvedTypeLayout<I8x64>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Broadcast the low packed 8-bit integer from a to all elements of dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_broadcastb_epi8&expand=537)
 */
public fun mm512MaskBroadcastbEpi8(src: M512i, k: Mmask64, a: M128i): M512i =
    run {
        val broadcast = mm512BroadcastbEpi8(a).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = broadcast; val simdArg2 = src.asI8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Broadcast the low packed 8-bit integer from a to all elements of dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_broadcastb_epi8&expand=538)
 */
public fun mm512MaskzBroadcastbEpi8(k: Mmask64, a: M128i): M512i =
    run {
        val broadcast = mm512BroadcastbEpi8(a).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = broadcast; val simdArg2 = Simd<Byte>(List(64) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Broadcast the low packed 8-bit integer from a to all elements of dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_broadcastb_epi8&expand=534)
 */
public fun mm256MaskBroadcastbEpi8(src: M256i, k: Mmask32, a: M128i): M256i =
    run {
        val broadcast = mm256BroadcastbEpi8(a).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = broadcast; val simdArg2 = src.asI8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Broadcast the low packed 8-bit integer from a to all elements of dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_broadcastb_epi8&expand=535)
 */
public fun mm256MaskzBroadcastbEpi8(k: Mmask32, a: M128i): M256i =
    run {
        val broadcast = mm256BroadcastbEpi8(a).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = broadcast; val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Broadcast the low packed 8-bit integer from a to all elements of dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_broadcastb_epi8&expand=531)
 */
public fun mmMaskBroadcastbEpi8(src: M128i, k: Mmask16, a: M128i): M128i =
    run {
        val broadcast = mmBroadcastbEpi8(a).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = broadcast; val simdArg2 = src.asI8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Broadcast the low packed 8-bit integer from a to all elements of dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_broadcastb_epi8&expand=532)
 */
public fun mmMaskzBroadcastbEpi8(k: Mmask16, a: M128i): M128i =
    run {
        val broadcast = mmBroadcastbEpi8(a).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = broadcast; val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Unpack and interleave 16-bit integers from the high half of each 128-bit lane in a and b, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_unpackhi_epi16&expand=6012)
 */
public fun mm512UnpackhiEpi16(a: M512i, b: M512i): M512i =
    run {
        val a1 = a.asI16x32()
        val b1 = b.asI16x32()
        val r: I16x32 = run { val simdArg0 = a1; val simdArg1 = b1; val simdArg2 = SimdShuffleIdx(uintArrayOf(4u, 32u + 4u, 5u, 32u + 5u, 6u, 32u + 6u, 7u, 32u + 7u, 12u, 32u + 12u, 13u, 32u + 13u, 14u, 32u + 14u, 15u, 32u + 15u, 20u, 32u + 20u, 21u, 32u + 21u, 22u, 32u + 22u, 23u, 32u + 23u, 28u, 32u + 28u, 29u, 32u + 29u, 30u, 32u + 30u, 31u, 32u + 31u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); res } }
        transmuteWithLayouts<I16x32, M512i>(r, UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Unpack and interleave 16-bit integers from the high half of each 128-bit lane in a and b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_unpackhi_epi16&expand=6010)
 */
public fun mm512MaskUnpackhiEpi16(src: M512i, k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val unpackhi = mm512UnpackhiEpi16(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = unpackhi; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Unpack and interleave 16-bit integers from the high half of each 128-bit lane in a and b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_unpackhi_epi16&expand=6011)
 */
public fun mm512MaskzUnpackhiEpi16(k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val unpackhi = mm512UnpackhiEpi16(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = unpackhi; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Unpack and interleave 16-bit integers from the high half of each 128-bit lane in a and b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_unpackhi_epi16&expand=6007)
 */
public fun mm256MaskUnpackhiEpi16(src: M256i, k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val unpackhi = mm256UnpackhiEpi16(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = unpackhi; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Unpack and interleave 16-bit integers from the high half of each 128-bit lane in a and b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_unpackhi_epi16&expand=6008)
 */
public fun mm256MaskzUnpackhiEpi16(k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val unpackhi = mm256UnpackhiEpi16(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = unpackhi; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Unpack and interleave 16-bit integers from the high half of each 128-bit lane in a and b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_unpackhi_epi16&expand=6004)
 */
public fun mmMaskUnpackhiEpi16(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val unpackhi = mmUnpackhiEpi16(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = unpackhi; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Unpack and interleave 16-bit integers from the high half of each 128-bit lane in a and b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_unpackhi_epi16&expand=6005)
 */
public fun mmMaskzUnpackhiEpi16(k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val unpackhi = mmUnpackhiEpi16(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = unpackhi; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Unpack and interleave 8-bit integers from the high half of each 128-bit lane in a and b, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_unpackhi_epi8&expand=6039)
 */
public fun mm512UnpackhiEpi8(a: M512i, b: M512i): M512i =
    run {
        val a1 = a.asI8x64()
        val b1 = b.asI8x64()
        val r: I8x64 = run { val simdArg0 = a1; val simdArg1 = b1; val simdArg2 = SimdShuffleIdx(uintArrayOf(8u, 64u + 8u, 9u, 64u + 9u, 10u, 64u + 10u, 11u, 64u + 11u, 12u, 64u + 12u, 13u, 64u + 13u, 14u, 64u + 14u, 15u, 64u + 15u, 24u, 64u + 24u, 25u, 64u + 25u, 26u, 64u + 26u, 27u, 64u + 27u, 28u, 64u + 28u, 29u, 64u + 29u, 30u, 64u + 30u, 31u, 64u + 31u, 40u, 64u + 40u, 41u, 64u + 41u, 42u, 64u + 42u, 43u, 64u + 43u, 44u, 64u + 44u, 45u, 64u + 45u, 46u, 64u + 46u, 47u, 64u + 47u, 56u, 64u + 56u, 57u, 64u + 57u, 58u, 64u + 58u, 59u, 64u + 59u, 60u, 64u + 60u, 61u, 64u + 61u, 62u, 64u + 62u, 63u, 64u + 63u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x64>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); res } }
        transmuteWithLayouts<I8x64, M512i>(r, UnresolvedTypeLayout<I8x64>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Unpack and interleave 8-bit integers from the high half of each 128-bit lane in a and b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_unpackhi_epi8&expand=6037)
 */
public fun mm512MaskUnpackhiEpi8(src: M512i, k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val unpackhi = mm512UnpackhiEpi8(a, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = unpackhi; val simdArg2 = src.asI8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Unpack and interleave 8-bit integers from the high half of each 128-bit lane in a and b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_unpackhi_epi8&expand=6038)
 */
public fun mm512MaskzUnpackhiEpi8(k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val unpackhi = mm512UnpackhiEpi8(a, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = unpackhi; val simdArg2 = Simd<Byte>(List(64) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Unpack and interleave 8-bit integers from the high half of each 128-bit lane in a and b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_unpackhi_epi8&expand=6034)
 */
public fun mm256MaskUnpackhiEpi8(src: M256i, k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val unpackhi = mm256UnpackhiEpi8(a, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = unpackhi; val simdArg2 = src.asI8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Unpack and interleave 8-bit integers from the high half of each 128-bit lane in a and b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_unpackhi_epi8&expand=6035)
 */
public fun mm256MaskzUnpackhiEpi8(k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val unpackhi = mm256UnpackhiEpi8(a, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = unpackhi; val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Unpack and interleave 8-bit integers from the high half of each 128-bit lane in a and b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_unpackhi_epi8&expand=6031)
 */
public fun mmMaskUnpackhiEpi8(src: M128i, k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val unpackhi = mmUnpackhiEpi8(a, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = unpackhi; val simdArg2 = src.asI8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Unpack and interleave 8-bit integers from the high half of each 128-bit lane in a and b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_unpackhi_epi8&expand=6032)
 */
public fun mmMaskzUnpackhiEpi8(k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val unpackhi = mmUnpackhiEpi8(a, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = unpackhi; val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Unpack and interleave 16-bit integers from the low half of each 128-bit lane in a and b, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_unpacklo_epi16&expand=6069)
 */
public fun mm512UnpackloEpi16(a: M512i, b: M512i): M512i =
    run {
        val a1 = a.asI16x32()
        val b1 = b.asI16x32()
        val r: I16x32 = run { val simdArg0 = a1; val simdArg1 = b1; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 32u + 0u, 1u, 32u + 1u, 2u, 32u + 2u, 3u, 32u + 3u, 8u, 32u + 8u, 9u, 32u + 9u, 10u, 32u + 10u, 11u, 32u + 11u, 16u, 32u + 16u, 17u, 32u + 17u, 18u, 32u + 18u, 19u, 32u + 19u, 24u, 32u + 24u, 25u, 32u + 25u, 26u, 32u + 26u, 27u, 32u + 27u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); res } }
        transmuteWithLayouts<I16x32, M512i>(r, UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Unpack and interleave 16-bit integers from the low half of each 128-bit lane in a and b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_unpacklo_epi16&expand=6067)
 */
public fun mm512MaskUnpackloEpi16(src: M512i, k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val unpacklo = mm512UnpackloEpi16(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = unpacklo; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Unpack and interleave 16-bit integers from the low half of each 128-bit lane in a and b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_unpacklo_epi16&expand=6068)
 */
public fun mm512MaskzUnpackloEpi16(k: Mmask32, a: M512i, b: M512i): M512i =
    run {
        val unpacklo = mm512UnpackloEpi16(a, b).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = unpacklo; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Unpack and interleave 16-bit integers from the low half of each 128-bit lane in a and b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_unpacklo_epi16&expand=6064)
 */
public fun mm256MaskUnpackloEpi16(src: M256i, k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val unpacklo = mm256UnpackloEpi16(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = unpacklo; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Unpack and interleave 16-bit integers from the low half of each 128-bit lane in a and b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_unpacklo_epi16&expand=6065)
 */
public fun mm256MaskzUnpackloEpi16(k: Mmask16, a: M256i, b: M256i): M256i =
    run {
        val unpacklo = mm256UnpackloEpi16(a, b).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = unpacklo; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Unpack and interleave 16-bit integers from the low half of each 128-bit lane in a and b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_unpacklo_epi16&expand=6061)
 */
public fun mmMaskUnpackloEpi16(src: M128i, k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val unpacklo = mmUnpackloEpi16(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = unpacklo; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Unpack and interleave 16-bit integers from the low half of each 128-bit lane in a and b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_unpacklo_epi16&expand=6062)
 */
public fun mmMaskzUnpackloEpi16(k: Mmask8, a: M128i, b: M128i): M128i =
    run {
        val unpacklo = mmUnpackloEpi16(a, b).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = unpacklo; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Unpack and interleave 8-bit integers from the low half of each 128-bit lane in a and b, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_unpacklo_epi8&expand=6096)
 */
public fun mm512UnpackloEpi8(a: M512i, b: M512i): M512i =
    run {
        val a1 = a.asI8x64()
        val b1 = b.asI8x64()
        val r: I8x64 = run { val simdArg0 = a1; val simdArg1 = b1; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 64u + 0u, 1u, 64u + 1u, 2u, 64u + 2u, 3u, 64u + 3u, 4u, 64u + 4u, 5u, 64u + 5u, 6u, 64u + 6u, 7u, 64u + 7u, 16u, 64u + 16u, 17u, 64u + 17u, 18u, 64u + 18u, 19u, 64u + 19u, 20u, 64u + 20u, 21u, 64u + 21u, 22u, 64u + 22u, 23u, 64u + 23u, 32u, 64u + 32u, 33u, 64u + 33u, 34u, 64u + 34u, 35u, 64u + 35u, 36u, 64u + 36u, 37u, 64u + 37u, 38u, 64u + 38u, 39u, 64u + 39u, 48u, 64u + 48u, 49u, 64u + 49u, 50u, 64u + 50u, 51u, 64u + 51u, 52u, 64u + 52u, 53u, 64u + 53u, 54u, 64u + 54u, 55u, 64u + 55u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x64>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); res } }
        transmuteWithLayouts<I8x64, M512i>(r, UnresolvedTypeLayout<I8x64>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Unpack and interleave 8-bit integers from the low half of each 128-bit lane in a and b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_unpacklo_epi8&expand=6094)
 */
public fun mm512MaskUnpackloEpi8(src: M512i, k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val unpacklo = mm512UnpackloEpi8(a, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = unpacklo; val simdArg2 = src.asI8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Unpack and interleave 8-bit integers from the low half of each 128-bit lane in a and b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_unpacklo_epi8&expand=6095)
 */
public fun mm512MaskzUnpackloEpi8(k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val unpacklo = mm512UnpackloEpi8(a, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = unpacklo; val simdArg2 = Simd<Byte>(List(64) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Unpack and interleave 8-bit integers from the low half of each 128-bit lane in a and b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_unpacklo_epi8&expand=6091)
 */
public fun mm256MaskUnpackloEpi8(src: M256i, k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val unpacklo = mm256UnpackloEpi8(a, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = unpacklo; val simdArg2 = src.asI8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Unpack and interleave 8-bit integers from the low half of each 128-bit lane in a and b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_unpacklo_epi8&expand=6092)
 */
public fun mm256MaskzUnpackloEpi8(k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val unpacklo = mm256UnpackloEpi8(a, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = unpacklo; val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Unpack and interleave 8-bit integers from the low half of each 128-bit lane in a and b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_unpacklo_epi8&expand=6088)
 */
public fun mmMaskUnpackloEpi8(src: M128i, k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val unpacklo = mmUnpackloEpi8(a, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = unpacklo; val simdArg2 = src.asI8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Unpack and interleave 8-bit integers from the low half of each 128-bit lane in a and b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_unpacklo_epi8&expand=6089)
 */
public fun mmMaskzUnpackloEpi8(k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val unpacklo = mmUnpackloEpi8(a, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = unpacklo; val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Move packed 16-bit integers from a into dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_mov_epi16&expand=3795)
 */
public fun mm512MaskMovEpi16(src: M512i, k: Mmask32, a: M512i): M512i =
    run {
        val mov = a.asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = mov; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Move packed 16-bit integers from a into dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_mov_epi16&expand=3796)
 */
public fun mm512MaskzMovEpi16(k: Mmask32, a: M512i): M512i =
    run {
        val mov = a.asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = mov; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Move packed 16-bit integers from a into dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_mov_epi16&expand=3793)
 */
public fun mm256MaskMovEpi16(src: M256i, k: Mmask16, a: M256i): M256i =
    run {
        val mov = a.asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = mov; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Move packed 16-bit integers from a into dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_mov_epi16&expand=3794)
 */
public fun mm256MaskzMovEpi16(k: Mmask16, a: M256i): M256i =
    run {
        val mov = a.asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = mov; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Move packed 16-bit integers from a into dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_mov_epi16&expand=3791)
 */
public fun mmMaskMovEpi16(src: M128i, k: Mmask8, a: M128i): M128i =
    run {
        val mov = a.asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = mov; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Move packed 16-bit integers from a into dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_mov_epi16&expand=3792)
 */
public fun mmMaskzMovEpi16(k: Mmask8, a: M128i): M128i =
    run {
        val mov = a.asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = mov; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Move packed 8-bit integers from a into dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_mov_epi8&expand=3813)
 */
public fun mm512MaskMovEpi8(src: M512i, k: Mmask64, a: M512i): M512i =
    run {
        val mov = a.asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = mov; val simdArg2 = src.asI8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Move packed 8-bit integers from a into dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_mov_epi8&expand=3814)
 */
public fun mm512MaskzMovEpi8(k: Mmask64, a: M512i): M512i =
    run {
        val mov = a.asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = mov; val simdArg2 = Simd<Byte>(List(64) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Move packed 8-bit integers from a into dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_mov_epi8&expand=3811)
 */
public fun mm256MaskMovEpi8(src: M256i, k: Mmask32, a: M256i): M256i =
    run {
        val mov = a.asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = mov; val simdArg2 = src.asI8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Move packed 8-bit integers from a into dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_mov_epi8&expand=3812)
 */
public fun mm256MaskzMovEpi8(k: Mmask32, a: M256i): M256i =
    run {
        val mov = a.asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = mov; val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Move packed 8-bit integers from a into dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_mov_epi8&expand=3809)
 */
public fun mmMaskMovEpi8(src: M128i, k: Mmask16, a: M128i): M128i =
    run {
        val mov = a.asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = mov; val simdArg2 = src.asI8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Move packed 8-bit integers from a into dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_mov_epi8&expand=3810)
 */
public fun mmMaskzMovEpi8(k: Mmask16, a: M128i): M128i =
    run {
        val mov = a.asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = mov; val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Broadcast 16-bit integer a to all elements of dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_set1_epi16&expand=4942)
 */
public fun mm512MaskSet1Epi16(src: M512i, k: Mmask32, a: Short): M512i =
    run {
        val r = mm512Set1Epi16(a).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = r; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Broadcast the low packed 16-bit integer from a to all elements of dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_set1_epi16&expand=4943)
 */
public fun mm512MaskzSet1Epi16(k: Mmask32, a: Short): M512i =
    run {
        val r = mm512Set1Epi16(a).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = r; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Broadcast 16-bit integer a to all elements of dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_set1_epi16&expand=4939)
 */
public fun mm256MaskSet1Epi16(src: M256i, k: Mmask16, a: Short): M256i =
    run {
        val r = mm256Set1Epi16(a).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = r; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Broadcast the low packed 16-bit integer from a to all elements of dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_set1_epi16&expand=4940)
 */
public fun mm256MaskzSet1Epi16(k: Mmask16, a: Short): M256i =
    run {
        val r = mm256Set1Epi16(a).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = r; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Broadcast 16-bit integer a to all elements of dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_set1_epi16&expand=4936)
 */
public fun mmMaskSet1Epi16(src: M128i, k: Mmask8, a: Short): M128i =
    run {
        val r = mmSet1Epi16(a).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = r; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Broadcast the low packed 16-bit integer from a to all elements of dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_set1_epi16&expand=4937)
 */
public fun mmMaskzSet1Epi16(k: Mmask8, a: Short): M128i =
    run {
        val r = mmSet1Epi16(a).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = r; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Broadcast 8-bit integer a to all elements of dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_set1_epi8&expand=4970)
 */
public fun mm512MaskSet1Epi8(src: M512i, k: Mmask64, a: Byte): M512i =
    run {
        val r = mm512Set1Epi8(a).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = r; val simdArg2 = src.asI8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Broadcast 8-bit integer a to all elements of dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_set1_epi8&expand=4971)
 */
public fun mm512MaskzSet1Epi8(k: Mmask64, a: Byte): M512i =
    run {
        val r = mm512Set1Epi8(a).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = r; val simdArg2 = Simd<Byte>(List(64) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Broadcast 8-bit integer a to all elements of dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_set1_epi8&expand=4967)
 */
public fun mm256MaskSet1Epi8(src: M256i, k: Mmask32, a: Byte): M256i =
    run {
        val r = mm256Set1Epi8(a).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = r; val simdArg2 = src.asI8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Broadcast 8-bit integer a to all elements of dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_set1_epi8&expand=4968)
 */
public fun mm256MaskzSet1Epi8(k: Mmask32, a: Byte): M256i =
    run {
        val r = mm256Set1Epi8(a).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = r; val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Broadcast 8-bit integer a to all elements of dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_set1_epi8&expand=4964)
 */
public fun mmMaskSet1Epi8(src: M128i, k: Mmask16, a: Byte): M128i =
    run {
        val r = mmSet1Epi8(a).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = r; val simdArg2 = src.asI8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Broadcast 8-bit integer a to all elements of dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_set1_epi8&expand=4965)
 */
public fun mmMaskzSet1Epi8(k: Mmask16, a: Byte): M128i =
    run {
        val r = mmSet1Epi8(a).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = r; val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shuffle 16-bit integers in the low 64 bits of 128-bit lanes of a using the control in imm8. Store the results in the low 64 bits of 128-bit lanes of dst, with the high 64 bits of 128-bit lanes being copied from a to dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_shufflelo_epi16&expand=5221)
 */
public fun mm512ShuffleloEpi16(a: M512i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val a1 = a.asI16x32()
        val r: I16x32 = run { val simdArg0 = a1; val simdArg1 = a1; val simdArg2 = SimdShuffleIdx(uintArrayOf(imm8.toUInt() and 0x3u, (imm8.toUInt() shr 2) and 0x3u, (imm8.toUInt() shr 4) and 0x3u, (imm8.toUInt() shr 6) and 0x3u, 4u, 5u, 6u, 7u, (imm8.toUInt() and 0x3u) + 8u, ((imm8.toUInt() shr 2) and 0x3u) + 8u, ((imm8.toUInt() shr 4) and 0x3u) + 8u, ((imm8.toUInt() shr 6) and 0x3u) + 8u, 12u, 13u, 14u, 15u, (imm8.toUInt() and 0x3u) + 16u, ((imm8.toUInt() shr 2) and 0x3u) + 16u, ((imm8.toUInt() shr 4) and 0x3u) + 16u, ((imm8.toUInt() shr 6) and 0x3u) + 16u, 20u, 21u, 22u, 23u, (imm8.toUInt() and 0x3u) + 24u, ((imm8.toUInt() shr 2) and 0x3u) + 24u, ((imm8.toUInt() shr 4) and 0x3u) + 24u, ((imm8.toUInt() shr 6) and 0x3u) + 24u, 28u, 29u, 30u, 31u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); res } }
        transmuteWithLayouts<I16x32, M512i>(r, UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shuffle 16-bit integers in the low 64 bits of 128-bit lanes of a using the control in imm8. Store the results in the low 64 bits of 128-bit lanes of dst, with the high 64 bits of 128-bit lanes being copied from a to dst, using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_shufflelo_epi16&expand=5219)
 */
public fun mm512MaskShuffleloEpi16(src: M512i, k: Mmask32, a: M512i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val r = mm512ShuffleloEpi16(a, imm8)
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = r.asI16x32(); val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shuffle 16-bit integers in the low 64 bits of 128-bit lanes of a using the control in imm8. Store the results in the low 64 bits of 128-bit lanes of dst, with the high 64 bits of 128-bit lanes being copied from a to dst, using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_shufflelo_epi16&expand=5220)
 */
public fun mm512MaskzShuffleloEpi16(k: Mmask32, a: M512i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val r = mm512ShuffleloEpi16(a, imm8)
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = r.asI16x32(); val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shuffle 16-bit integers in the low 64 bits of 128-bit lanes of a using the control in imm8. Store the results in the low 64 bits of 128-bit lanes of dst, with the high 64 bits of 128-bit lanes being copied from a to dst, using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_shufflelo_epi16&expand=5216)
 */
public fun mm256MaskShuffleloEpi16(src: M256i, k: Mmask16, a: M256i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shuffle = mm256ShuffleloEpi16(a, imm8)
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = shuffle.asI16x16(); val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shuffle 16-bit integers in the low 64 bits of 128-bit lanes of a using the control in imm8. Store the results in the low 64 bits of 128-bit lanes of dst, with the high 64 bits of 128-bit lanes being copied from a to dst, using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_shufflelo_epi16&expand=5217)
 */
public fun mm256MaskzShuffleloEpi16(k: Mmask16, a: M256i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shuffle = mm256ShuffleloEpi16(a, imm8)
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = shuffle.asI16x16(); val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shuffle 16-bit integers in the low 64 bits of 128-bit lanes of a using the control in imm8. Store the results in the low 64 bits of 128-bit lanes of dst, with the high 64 bits of 128-bit lanes being copied from a to dst, using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_shufflelo_epi16&expand=5213)
 */
public fun mmMaskShuffleloEpi16(src: M128i, k: Mmask8, a: M128i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shuffle = mmShuffleloEpi16(a, imm8)
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = shuffle.asI16x8(); val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shuffle 16-bit integers in the low 64 bits of 128-bit lanes of a using the control in imm8. Store the results in the low 64 bits of 128-bit lanes of dst, with the high 64 bits of 128-bit lanes being copied from a to dst, using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_shufflelo_epi16&expand=5214)
 */
public fun mmMaskzShuffleloEpi16(k: Mmask8, a: M128i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shuffle = mmShuffleloEpi16(a, imm8)
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = shuffle.asI16x8(); val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shuffle 16-bit integers in the high 64 bits of 128-bit lanes of a using the control in imm8. Store the results in the high 64 bits of 128-bit lanes of dst, with the low 64 bits of 128-bit lanes being copied from a to dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_shufflehi_epi16&expand=5212)
 */
public fun mm512ShufflehiEpi16(a: M512i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val a1 = a.asI16x32()
        val r: I16x32 = run { val simdArg0 = a1; val simdArg1 = a1; val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, (imm8.toUInt() and 0x3u) + 4u, ((imm8.toUInt() shr 2) and 0x3u) + 4u, ((imm8.toUInt() shr 4) and 0x3u) + 4u, ((imm8.toUInt() shr 6) and 0x3u) + 4u, 8u, 9u, 10u, 11u, (imm8.toUInt() and 0x3u) + 12u, ((imm8.toUInt() shr 2) and 0x3u) + 12u, ((imm8.toUInt() shr 4) and 0x3u) + 12u, ((imm8.toUInt() shr 6) and 0x3u) + 12u, 16u, 17u, 18u, 19u, (imm8.toUInt() and 0x3u) + 20u, ((imm8.toUInt() shr 2) and 0x3u) + 20u, ((imm8.toUInt() shr 4) and 0x3u) + 20u, ((imm8.toUInt() shr 6) and 0x3u) + 20u, 24u, 25u, 26u, 27u, (imm8.toUInt() and 0x3u) + 28u, ((imm8.toUInt() shr 2) and 0x3u) + 28u, ((imm8.toUInt() shr 4) and 0x3u) + 28u, ((imm8.toUInt() shr 6) and 0x3u) + 28u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); res } }
        transmuteWithLayouts<I16x32, M512i>(r, UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shuffle 16-bit integers in the high 64 bits of 128-bit lanes of a using the control in imm8. Store the results in the high 64 bits of 128-bit lanes of dst, with the low 64 bits of 128-bit lanes being copied from a to dst, using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_shufflehi_epi16&expand=5210)
 */
public fun mm512MaskShufflehiEpi16(src: M512i, k: Mmask32, a: M512i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val r = mm512ShufflehiEpi16(a, imm8)
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = r.asI16x32(); val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shuffle 16-bit integers in the high 64 bits of 128-bit lanes of a using the control in imm8. Store the results in the high 64 bits of 128-bit lanes of dst, with the low 64 bits of 128-bit lanes being copied from a to dst, using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_shufflehi_epi16&expand=5211)
 */
public fun mm512MaskzShufflehiEpi16(k: Mmask32, a: M512i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val r = mm512ShufflehiEpi16(a, imm8)
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = r.asI16x32(); val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shuffle 16-bit integers in the high 64 bits of 128-bit lanes of a using the control in imm8. Store the results in the high 64 bits of 128-bit lanes of dst, with the low 64 bits of 128-bit lanes being copied from a to dst, using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_shufflehi_epi16&expand=5207)
 */
public fun mm256MaskShufflehiEpi16(src: M256i, k: Mmask16, a: M256i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shuffle = mm256ShufflehiEpi16(a, imm8)
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = shuffle.asI16x16(); val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shuffle 16-bit integers in the high 64 bits of 128-bit lanes of a using the control in imm8. Store the results in the high 64 bits of 128-bit lanes of dst, with the low 64 bits of 128-bit lanes being copied from a to dst, using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_shufflehi_epi16&expand=5208)
 */
public fun mm256MaskzShufflehiEpi16(k: Mmask16, a: M256i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shuffle = mm256ShufflehiEpi16(a, imm8)
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = shuffle.asI16x16(); val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shuffle 16-bit integers in the high 64 bits of 128-bit lanes of a using the control in imm8. Store the results in the high 64 bits of 128-bit lanes of dst, with the low 64 bits of 128-bit lanes being copied from a to dst, using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_shufflehi_epi16&expand=5204)
 */
public fun mmMaskShufflehiEpi16(src: M128i, k: Mmask8, a: M128i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shuffle = mmShufflehiEpi16(a, imm8)
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = shuffle.asI16x8(); val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shuffle 16-bit integers in the high 64 bits of 128-bit lanes of a using the control in imm8. Store the results in the high 64 bits of 128-bit lanes of dst, with the low 64 bits of 128-bit lanes being copied from a to dst, using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_shufflehi_epi16&expand=5205)
 */
public fun mmMaskzShufflehiEpi16(k: Mmask8, a: M128i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val shuffle = mmShufflehiEpi16(a, imm8)
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = shuffle.asI16x8(); val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shuffle packed 8-bit integers in a according to shuffle control mask in the corresponding 8-bit element of b, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_shuffle_epi8&expand=5159)
 */
public fun mm512ShuffleEpi8(a: M512i, b: M512i): M512i = transmuteWithLayouts<I8x64, M512i>(vpshufb(a.asI8x64(), b.asI8x64()), UnresolvedTypeLayout<I8x64>(), UnresolvedTypeLayout<M512i>())

/**
 * Shuffle 8-bit integers in a within 128-bit lanes using the control in the corresponding 8-bit element of b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_shuffle_epi8&expand=5157)
 */
public fun mm512MaskShuffleEpi8(src: M512i, k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val shuffle = mm512ShuffleEpi8(a, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = shuffle; val simdArg2 = src.asI8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shuffle packed 8-bit integers in a according to shuffle control mask in the corresponding 8-bit element of b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_shuffle_epi8&expand=5158)
 */
public fun mm512MaskzShuffleEpi8(k: Mmask64, a: M512i, b: M512i): M512i =
    run {
        val shuffle = mm512ShuffleEpi8(a, b).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = shuffle; val simdArg2 = Simd<Byte>(List(64) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Shuffle 8-bit integers in a within 128-bit lanes using the control in the corresponding 8-bit element of b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_shuffle_epi8&expand=5154)
 */
public fun mm256MaskShuffleEpi8(src: M256i, k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val shuffle = mm256ShuffleEpi8(a, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = shuffle; val simdArg2 = src.asI8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shuffle packed 8-bit integers in a according to shuffle control mask in the corresponding 8-bit element of b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_shuffle_epi8&expand=5155)
 */
public fun mm256MaskzShuffleEpi8(k: Mmask32, a: M256i, b: M256i): M256i =
    run {
        val shuffle = mm256ShuffleEpi8(a, b).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = shuffle; val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Shuffle 8-bit integers in a within 128-bit lanes using the control in the corresponding 8-bit element of b, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_shuffle_epi8&expand=5151)
 */
public fun mmMaskShuffleEpi8(src: M128i, k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val shuffle = mmShuffleEpi8(a, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = shuffle; val simdArg2 = src.asI8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shuffle packed 8-bit integers in a according to shuffle control mask in the corresponding 8-bit element of b, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_shuffle_epi8&expand=5152)
 */
public fun mmMaskzShuffleEpi8(k: Mmask16, a: M128i, b: M128i): M128i =
    run {
        val shuffle = mmShuffleEpi8(a, b).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = shuffle; val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Compute the bitwise AND of packed 16-bit integers in a and b, producing intermediate 16-bit values, and set the corresponding bit in result mask k if the intermediate value is non-zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_test_epi16_mask&expand=5884)
 */
public fun mm512TestEpi16Mask(a: M512i, b: M512i): Mmask32 {
    val and = mm512AndSi512(a, b)
    val zero = mm512SetzeroSi512()
    return mm512CmpneqEpi16Mask(and, zero)
}

/**
 * Compute the bitwise AND of packed 16-bit integers in a and b, producing intermediate 16-bit values, and set the corresponding bit in result mask k (subject to writemask k) if the intermediate value is non-zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_test_epi16_mask&expand=5883)
 */
public fun mm512MaskTestEpi16Mask(k: Mmask32, a: M512i, b: M512i): Mmask32 {
    val and = mm512AndSi512(a, b)
    val zero = mm512SetzeroSi512()
    return mm512MaskCmpneqEpi16Mask(k, and, zero)
}

/**
 * Compute the bitwise AND of packed 16-bit integers in a and b, producing intermediate 16-bit values, and set the corresponding bit in result mask k if the intermediate value is non-zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_test_epi16_mask&expand=5882)
 */
public fun mm256TestEpi16Mask(a: M256i, b: M256i): Mmask16 {
    val and = mm256AndSi256(a, b)
    val zero = mm256SetzeroSi256()
    return mm256CmpneqEpi16Mask(and, zero)
}

/**
 * Compute the bitwise AND of packed 16-bit integers in a and b, producing intermediate 16-bit values, and set the corresponding bit in result mask k (subject to writemask k) if the intermediate value is non-zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_test_epi16_mask&expand=5881)
 */
public fun mm256MaskTestEpi16Mask(k: Mmask16, a: M256i, b: M256i): Mmask16 {
    val and = mm256AndSi256(a, b)
    val zero = mm256SetzeroSi256()
    return mm256MaskCmpneqEpi16Mask(k, and, zero)
}

/**
 * Compute the bitwise AND of packed 16-bit integers in a and b, producing intermediate 16-bit values, and set the corresponding bit in result mask k if the intermediate value is non-zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_test_epi16_mask&expand=5880)
 */
public fun mmTestEpi16Mask(a: M128i, b: M128i): Mmask8 {
    val and = mmAndSi128(a, b)
    val zero = mmSetzeroSi128()
    return mmCmpneqEpi16Mask(and, zero)
}

/**
 * Compute the bitwise AND of packed 16-bit integers in a and b, producing intermediate 16-bit values, and set the corresponding bit in result mask k (subject to writemask k) if the intermediate value is non-zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_test_epi16_mask&expand=5879)
 */
public fun mmMaskTestEpi16Mask(k: Mmask8, a: M128i, b: M128i): Mmask8 {
    val and = mmAndSi128(a, b)
    val zero = mmSetzeroSi128()
    return mmMaskCmpneqEpi16Mask(k, and, zero)
}

/**
 * Compute the bitwise AND of packed 8-bit integers in a and b, producing intermediate 8-bit values, and set the corresponding bit in result mask k if the intermediate value is non-zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_test_epi8_mask&expand=5902)
 */
public fun mm512TestEpi8Mask(a: M512i, b: M512i): Mmask64 {
    val and = mm512AndSi512(a, b)
    val zero = mm512SetzeroSi512()
    return mm512CmpneqEpi8Mask(and, zero)
}

/**
 * Compute the bitwise AND of packed 8-bit integers in a and b, producing intermediate 8-bit values, and set the corresponding bit in result mask k (subject to writemask k) if the intermediate value is non-zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_test_epi8_mask&expand=5901)
 */
public fun mm512MaskTestEpi8Mask(k: Mmask64, a: M512i, b: M512i): Mmask64 {
    val and = mm512AndSi512(a, b)
    val zero = mm512SetzeroSi512()
    return mm512MaskCmpneqEpi8Mask(k, and, zero)
}

/**
 * Compute the bitwise AND of packed 8-bit integers in a and b, producing intermediate 8-bit values, and set the corresponding bit in result mask k if the intermediate value is non-zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_test_epi8_mask&expand=5900)
 */
public fun mm256TestEpi8Mask(a: M256i, b: M256i): Mmask32 {
    val and = mm256AndSi256(a, b)
    val zero = mm256SetzeroSi256()
    return mm256CmpneqEpi8Mask(and, zero)
}

/**
 * Compute the bitwise AND of packed 8-bit integers in a and b, producing intermediate 8-bit values, and set the corresponding bit in result mask k (subject to writemask k) if the intermediate value is non-zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_test_epi8_mask&expand=5899)
 */
public fun mm256MaskTestEpi8Mask(k: Mmask32, a: M256i, b: M256i): Mmask32 {
    val and = mm256AndSi256(a, b)
    val zero = mm256SetzeroSi256()
    return mm256MaskCmpneqEpi8Mask(k, and, zero)
}

/**
 * Compute the bitwise AND of packed 8-bit integers in a and b, producing intermediate 8-bit values, and set the corresponding bit in result mask k if the intermediate value is non-zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_test_epi8_mask&expand=5898)
 */
public fun mmTestEpi8Mask(a: M128i, b: M128i): Mmask16 {
    val and = mmAndSi128(a, b)
    val zero = mmSetzeroSi128()
    return mmCmpneqEpi8Mask(and, zero)
}

/**
 * Compute the bitwise AND of packed 8-bit integers in a and b, producing intermediate 8-bit values, and set the corresponding bit in result mask k (subject to writemask k) if the intermediate value is non-zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_test_epi8_mask&expand=5897)
 */
public fun mmMaskTestEpi8Mask(k: Mmask16, a: M128i, b: M128i): Mmask16 {
    val and = mmAndSi128(a, b)
    val zero = mmSetzeroSi128()
    return mmMaskCmpneqEpi8Mask(k, and, zero)
}

/**
 * Compute the bitwise NAND of packed 16-bit integers in a and b, producing intermediate 16-bit values, and set the corresponding bit in result mask k if the intermediate value is zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_testn_epi16_mask&expand=5915)
 */
public fun mm512TestnEpi16Mask(a: M512i, b: M512i): Mmask32 {
    val and = mm512AndSi512(a, b)
    val zero = mm512SetzeroSi512()
    return mm512CmpeqEpi16Mask(and, zero)
}

/**
 * Compute the bitwise NAND of packed 16-bit integers in a and b, producing intermediate 16-bit values, and set the corresponding bit in result mask k (subject to writemask k) if the intermediate value is zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_testn_epi16_mask&expand=5914)
 */
public fun mm512MaskTestnEpi16Mask(k: Mmask32, a: M512i, b: M512i): Mmask32 {
    val and = mm512AndSi512(a, b)
    val zero = mm512SetzeroSi512()
    return mm512MaskCmpeqEpi16Mask(k, and, zero)
}

/**
 * Compute the bitwise NAND of packed 16-bit integers in a and b, producing intermediate 16-bit values, and set the corresponding bit in result mask k if the intermediate value is zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_testn_epi16_mask&expand=5913)
 */
public fun mm256TestnEpi16Mask(a: M256i, b: M256i): Mmask16 {
    val and = mm256AndSi256(a, b)
    val zero = mm256SetzeroSi256()
    return mm256CmpeqEpi16Mask(and, zero)
}

/**
 * Compute the bitwise NAND of packed 16-bit integers in a and b, producing intermediate 16-bit values, and set the corresponding bit in result mask k (subject to writemask k) if the intermediate value is zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_testn_epi16_mask&expand=5912)
 */
public fun mm256MaskTestnEpi16Mask(k: Mmask16, a: M256i, b: M256i): Mmask16 {
    val and = mm256AndSi256(a, b)
    val zero = mm256SetzeroSi256()
    return mm256MaskCmpeqEpi16Mask(k, and, zero)
}

/**
 * Compute the bitwise NAND of packed 16-bit integers in a and b, producing intermediate 16-bit values, and set the corresponding bit in result mask k if the intermediate value is zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_testn_epi16_mask&expand=5911)
 */
public fun mmTestnEpi16Mask(a: M128i, b: M128i): Mmask8 {
    val and = mmAndSi128(a, b)
    val zero = mmSetzeroSi128()
    return mmCmpeqEpi16Mask(and, zero)
}

/**
 * Compute the bitwise NAND of packed 16-bit integers in a and b, producing intermediate 16-bit values, and set the corresponding bit in result mask k (subject to writemask k) if the intermediate value is zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_testn_epi16_mask&expand=5910)
 */
public fun mmMaskTestnEpi16Mask(k: Mmask8, a: M128i, b: M128i): Mmask8 {
    val and = mmAndSi128(a, b)
    val zero = mmSetzeroSi128()
    return mmMaskCmpeqEpi16Mask(k, and, zero)
}

/**
 * Compute the bitwise NAND of packed 8-bit integers in a and b, producing intermediate 8-bit values, and set the corresponding bit in result mask k if the intermediate value is zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_testn_epi8_mask&expand=5933)
 */
public fun mm512TestnEpi8Mask(a: M512i, b: M512i): Mmask64 {
    val and = mm512AndSi512(a, b)
    val zero = mm512SetzeroSi512()
    return mm512CmpeqEpi8Mask(and, zero)
}

/**
 * Compute the bitwise NAND of packed 8-bit integers in a and b, producing intermediate 8-bit values, and set the corresponding bit in result mask k (subject to writemask k) if the intermediate value is zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_testn_epi8_mask&expand=5932)
 */
public fun mm512MaskTestnEpi8Mask(k: Mmask64, a: M512i, b: M512i): Mmask64 {
    val and = mm512AndSi512(a, b)
    val zero = mm512SetzeroSi512()
    return mm512MaskCmpeqEpi8Mask(k, and, zero)
}

/**
 * Compute the bitwise NAND of packed 8-bit integers in a and b, producing intermediate 8-bit values, and set the corresponding bit in result mask k if the intermediate value is zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_testn_epi8_mask&expand=5931)
 */
public fun mm256TestnEpi8Mask(a: M256i, b: M256i): Mmask32 {
    val and = mm256AndSi256(a, b)
    val zero = mm256SetzeroSi256()
    return mm256CmpeqEpi8Mask(and, zero)
}

/**
 * Compute the bitwise NAND of packed 8-bit integers in a and b, producing intermediate 8-bit values, and set the corresponding bit in result mask k (subject to writemask k) if the intermediate value is zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_testn_epi8_mask&expand=5930)
 */
public fun mm256MaskTestnEpi8Mask(k: Mmask32, a: M256i, b: M256i): Mmask32 {
    val and = mm256AndSi256(a, b)
    val zero = mm256SetzeroSi256()
    return mm256MaskCmpeqEpi8Mask(k, and, zero)
}

/**
 * Compute the bitwise NAND of packed 8-bit integers in a and b, producing intermediate 8-bit values, and set the corresponding bit in result mask k if the intermediate value is zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_testn_epi8_mask&expand=5929)
 */
public fun mmTestnEpi8Mask(a: M128i, b: M128i): Mmask16 {
    val and = mmAndSi128(a, b)
    val zero = mmSetzeroSi128()
    return mmCmpeqEpi8Mask(and, zero)
}

/**
 * Compute the bitwise NAND of packed 8-bit integers in a and b, producing intermediate 8-bit values, and set the corresponding bit in result mask k (subject to writemask k) if the intermediate value is zero.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_testn_epi8_mask&expand=5928)
 */
public fun mmMaskTestnEpi8Mask(k: Mmask16, a: M128i, b: M128i): Mmask16 {
    val and = mmAndSi128(a, b)
    val zero = mmSetzeroSi128()
    return mmMaskCmpeqEpi8Mask(k, and, zero)
}

// should be kmovq

/**
 * Store 64-bit mask from a into memory.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_store_mask64&expand=5578)
 */
public fun storeMask64(memAddr: MutPtr, a: Mmask64) {
    write(memAddr, a, typeLayoutT = UnresolvedTypeLayout<Mmask64>())
}

// should be kmovd

/**
 * Store 32-bit mask from a into memory.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_store_mask32&expand=5577)
 */
public fun storeMask32(memAddr: MutPtr, a: Mmask32) {
    write(memAddr, a, typeLayoutT = UnresolvedTypeLayout<Mmask32>())
}

// should be kmovq

/**
 * Load 64-bit mask from memory into k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_load_mask64&expand=3318)
 */
public fun loadMask64(memAddr: ConstPtr): Mmask64 = read(memAddr, typeLayoutT = UnresolvedTypeLayout<Mmask64>())

// should be kmovd

/**
 * Load 32-bit mask from memory into k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_load_mask32&expand=3317)
 */
public fun loadMask32(memAddr: ConstPtr): Mmask32 = read(memAddr, typeLayoutT = UnresolvedTypeLayout<Mmask32>())

/**
 * Compute the absolute differences of packed unsigned 8-bit integers in a and b, then horizontally sum each consecutive 8 differences to produce eight unsigned 16-bit integers, and pack these unsigned 16-bit integers in the low 16 bits of 64-bit elements in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_sad_epu8&expand=4855)
 */
public fun mm512SadEpu8(a: M512i, b: M512i): M512i = transmuteWithLayouts<U64x8, M512i>(vpsadbw(a.asU8x64(), b.asU8x64()), UnresolvedTypeLayout<U64x8>(), UnresolvedTypeLayout<M512i>())

/**
 * Compute the sum of absolute differences (SADs) of quadruplets of unsigned 8-bit integers in a compared to those in b, and store the 16-bit results in dst. Four SADs are performed on four 8-bit quadruplets for each 64-bit lane. The first two SADs use the lower 8-bit quadruplet of the lane from a, and the last two SADs use the uppper 8-bit quadruplet of the lane from a. Quadruplets from b are selected from within 128-bit lanes according to the control in imm8, and each SAD in each 64-bit lane uses the selected quadruplet at 8-bit offsets.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_dbsad_epu8&expand=2114)
 */
public fun mm512DbsadEpu8(a: M512i, b: M512i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val a1 = a.asU8x64()
        val b1 = b.asU8x64()
        val r = vdbpsadbw(a1, b1, imm8)
        transmuteWithLayouts<U16x32, M512i>(r, UnresolvedTypeLayout<U16x32>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Compute the sum of absolute differences (SADs) of quadruplets of unsigned 8-bit integers in a compared to those in b, and store the 16-bit results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set). Four SADs are performed on four 8-bit quadruplets for each 64-bit lane. The first two SADs use the lower 8-bit quadruplet of the lane from a, and the last two SADs use the uppper 8-bit quadruplet of the lane from a. Quadruplets from b are selected from within 128-bit lanes according to the control in imm8, and each SAD in each 64-bit lane uses the selected quadruplet at 8-bit offsets.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_dbsad_epu8&expand=2115)
 */
public fun mm512MaskDbsadEpu8(src: M512i, k: Mmask32, a: M512i, b: M512i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val a1 = a.asU8x64()
        val b1 = b.asU8x64()
        val r = vdbpsadbw(a1, b1, imm8)
        transmuteWithLayouts<U16x32, M512i>(run { val simdArg0 = k; val simdArg1 = r; val simdArg2 = src.asU16x32(); simdArg1 }, UnresolvedTypeLayout<U16x32>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Compute the sum of absolute differences (SADs) of quadruplets of unsigned 8-bit integers in a compared to those in b, and store the 16-bit results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set). Four SADs are performed on four 8-bit quadruplets for each 64-bit lane. The first two SADs use the lower 8-bit quadruplet of the lane from a, and the last two SADs use the uppper 8-bit quadruplet of the lane from a. Quadruplets from b are selected from within 128-bit lanes according to the control in imm8, and each SAD in each 64-bit lane uses the selected quadruplet at 8-bit offsets.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_dbsad_epu8&expand=2116)
 */
public fun mm512MaskzDbsadEpu8(k: Mmask32, a: M512i, b: M512i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val a1 = a.asU8x64()
        val b1 = b.asU8x64()
        val r = vdbpsadbw(a1, b1, imm8)
        transmuteWithLayouts<U16x32, M512i>(run { val simdArg0 = k; val simdArg1 = r; val simdArg2 = Simd<UShort>(List(32) { 0u.toUShort() }); simdArg1 }, UnresolvedTypeLayout<U16x32>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Compute the sum of absolute differences (SADs) of quadruplets of unsigned 8-bit integers in a compared to those in b, and store the 16-bit results in dst. Four SADs are performed on four 8-bit quadruplets for each 64-bit lane. The first two SADs use the lower 8-bit quadruplet of the lane from a, and the last two SADs use the uppper 8-bit quadruplet of the lane from a. Quadruplets from b are selected from within 128-bit lanes according to the control in imm8, and each SAD in each 64-bit lane uses the selected quadruplet at 8-bit offsets.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_dbsad_epu8&expand=2111)
 */
public fun mm256DbsadEpu8(a: M256i, b: M256i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val a1 = a.asU8x32()
        val b1 = b.asU8x32()
        val r = vdbpsadbw256(a1, b1, imm8)
        transmuteWithLayouts<U16x16, M256i>(r, UnresolvedTypeLayout<U16x16>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Compute the sum of absolute differences (SADs) of quadruplets of unsigned 8-bit integers in a compared to those in b, and store the 16-bit results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set). Four SADs are performed on four 8-bit quadruplets for each 64-bit lane. The first two SADs use the lower 8-bit quadruplet of the lane from a, and the last two SADs use the uppper 8-bit quadruplet of the lane from a. Quadruplets from b are selected from within 128-bit lanes according to the control in imm8, and each SAD in each 64-bit lane uses the selected quadruplet at 8-bit offsets.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_dbsad_epu8&expand=2112)
 */
public fun mm256MaskDbsadEpu8(src: M256i, k: Mmask16, a: M256i, b: M256i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val a1 = a.asU8x32()
        val b1 = b.asU8x32()
        val r = vdbpsadbw256(a1, b1, imm8)
        transmuteWithLayouts<U16x16, M256i>(run { val simdArg0 = k; val simdArg1 = r; val simdArg2 = src.asU16x16(); simdArg1 }, UnresolvedTypeLayout<U16x16>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Compute the sum of absolute differences (SADs) of quadruplets of unsigned 8-bit integers in a compared to those in b, and store the 16-bit results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set). Four SADs are performed on four 8-bit quadruplets for each 64-bit lane. The first two SADs use the lower 8-bit quadruplet of the lane from a, and the last two SADs use the uppper 8-bit quadruplet of the lane from a. Quadruplets from b are selected from within 128-bit lanes according to the control in imm8, and each SAD in each 64-bit lane uses the selected quadruplet at 8-bit offsets.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_dbsad_epu8&expand=2113)
 */
public fun mm256MaskzDbsadEpu8(k: Mmask16, a: M256i, b: M256i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val a1 = a.asU8x32()
        val b1 = b.asU8x32()
        val r = vdbpsadbw256(a1, b1, imm8)
        transmuteWithLayouts<U16x16, M256i>(run { val simdArg0 = k; val simdArg1 = r; val simdArg2 = Simd<UShort>(List(16) { 0u.toUShort() }); simdArg1 }, UnresolvedTypeLayout<U16x16>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Compute the sum of absolute differences (SADs) of quadruplets of unsigned 8-bit integers in a compared to those in b, and store the 16-bit results in dst. Four SADs are performed on four 8-bit quadruplets for each 64-bit lane. The first two SADs use the lower 8-bit quadruplet of the lane from a, and the last two SADs use the uppper 8-bit quadruplet of the lane from a. Quadruplets from b are selected from within 128-bit lanes according to the control in imm8, and each SAD in each 64-bit lane uses the selected quadruplet at 8-bit offsets.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_dbsad_epu8&expand=2108)
 */
public fun mmDbsadEpu8(a: M128i, b: M128i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val a1 = a.asU8x16()
        val b1 = b.asU8x16()
        val r = vdbpsadbw128(a1, b1, imm8)
        transmuteWithLayouts<U16x8, M128i>(r, UnresolvedTypeLayout<U16x8>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Compute the sum of absolute differences (SADs) of quadruplets of unsigned 8-bit integers in a compared to those in b, and store the 16-bit results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set). Four SADs are performed on four 8-bit quadruplets for each 64-bit lane. The first two SADs use the lower 8-bit quadruplet of the lane from a, and the last two SADs use the uppper 8-bit quadruplet of the lane from a. Quadruplets from b are selected from within 128-bit lanes according to the control in imm8, and each SAD in each 64-bit lane uses the selected quadruplet at 8-bit offsets.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_dbsad_epu8&expand=2109)
 */
public fun mmMaskDbsadEpu8(src: M128i, k: Mmask8, a: M128i, b: M128i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val a1 = a.asU8x16()
        val b1 = b.asU8x16()
        val r = vdbpsadbw128(a1, b1, imm8)
        transmuteWithLayouts<U16x8, M128i>(run { val simdArg0 = k; val simdArg1 = r; val simdArg2 = src.asU16x8(); simdArg1 }, UnresolvedTypeLayout<U16x8>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Compute the sum of absolute differences (SADs) of quadruplets of unsigned 8-bit integers in a compared to those in b, and store the 16-bit results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set). Four SADs are performed on four 8-bit quadruplets for each 64-bit lane. The first two SADs use the lower 8-bit quadruplet of the lane from a, and the last two SADs use the uppper 8-bit quadruplet of the lane from a. Quadruplets from b are selected from within 128-bit lanes according to the control in imm8, and each SAD in each 64-bit lane uses the selected quadruplet at 8-bit offsets.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_dbsad_epu8&expand=2110)
 */
public fun mmMaskzDbsadEpu8(k: Mmask8, a: M128i, b: M128i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val a1 = a.asU8x16()
        val b1 = b.asU8x16()
        val r = vdbpsadbw128(a1, b1, imm8)
        transmuteWithLayouts<U16x8, M128i>(run { val simdArg0 = k; val simdArg1 = r; val simdArg2 = Simd<UShort>(List(8) { 0u.toUShort() }); simdArg1 }, UnresolvedTypeLayout<U16x8>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Set each bit of mask register k based on the most significant bit of the corresponding packed 16-bit integer in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_movepi16_mask&expand=3873)
 */
public fun mm512Movepi16Mask(a: M512i): Mmask32 {
    val filter = mm512Set1Epi16((1.toInt() shl 15).toShort())
    val a1 = mm512AndSi512(a, filter)
    return mm512CmpeqEpi16Mask(a1, filter)
}

/**
 * Set each bit of mask register k based on the most significant bit of the corresponding packed 16-bit integer in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_movepi16_mask&expand=3872)
 */
public fun mm256Movepi16Mask(a: M256i): Mmask16 {
    val filter = mm256Set1Epi16((1.toInt() shl 15).toShort())
    val a1 = mm256AndSi256(a, filter)
    return mm256CmpeqEpi16Mask(a1, filter)
}

/**
 * Set each bit of mask register k based on the most significant bit of the corresponding packed 16-bit integer in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_movepi16_mask&expand=3871)
 */
public fun mmMovepi16Mask(a: M128i): Mmask8 {
    val filter = mmSet1Epi16((1.toInt() shl 15).toShort())
    val a1 = mmAndSi128(a, filter)
    return mmCmpeqEpi16Mask(a1, filter)
}

/**
 * Set each bit of mask register k based on the most significant bit of the corresponding packed 8-bit integer in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_movepi8_mask&expand=3883)
 */
public fun mm512Movepi8Mask(a: M512i): Mmask64 {
    val filter = mm512Set1Epi8((1.toInt() shl 7).toByte())
    val a1 = mm512AndSi512(a, filter)
    return mm512CmpeqEpi8Mask(a1, filter)
}

// should be vpmovb2m but compiled to vpmovmskb in the test shim because that takes less cycles than
// using vpmovb2m plus converting the mask register to a standard register.

/**
 * Set each bit of mask register k based on the most significant bit of the corresponding packed 8-bit integer in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_movepi8_mask&expand=3882)
 */
public fun mm256Movepi8Mask(a: M256i): Mmask32 {
    val filter = mm256Set1Epi8((1.toInt() shl 7).toByte())
    val a1 = mm256AndSi256(a, filter)
    return mm256CmpeqEpi8Mask(a1, filter)
}

// should be vpmovb2m but compiled to vpmovmskb in the test shim because that takes less cycles than
// using vpmovb2m plus converting the mask register to a standard register.

/**
 * Set each bit of mask register k based on the most significant bit of the corresponding packed 8-bit integer in a.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_movepi8_mask&expand=3881)
 */
public fun mmMovepi8Mask(a: M128i): Mmask16 {
    val filter = mmSet1Epi8((1.toInt() shl 7).toByte())
    val a1 = mmAndSi128(a, filter)
    return mmCmpeqEpi8Mask(a1, filter)
}

/**
 * Set each packed 16-bit integer in dst to all ones or all zeros based on the value of the corresponding bit in k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_movm_epi16&expand=3886)
 */
public fun mm512MovmEpi16(k: Mmask32): M512i =
    run {
        val one = mm512Set1Epi16((((((((((((((((((((((((((((((((1.toInt() shl 15).toShort()).toInt() or ((1.toInt() shl 14).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 13).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 12).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 11).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 10).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 9).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 8).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 7).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 6).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 5).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 4).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 3).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 2).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 1).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 0).toShort()).toInt()).toShort()).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = one; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Set each packed 16-bit integer in dst to all ones or all zeros based on the value of the corresponding bit in k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_movm_epi16&expand=3885)
 */
public fun mm256MovmEpi16(k: Mmask16): M256i =
    run {
        val one = mm256Set1Epi16((((((((((((((((((((((((((((((((1.toInt() shl 15).toShort()).toInt() or ((1.toInt() shl 14).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 13).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 12).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 11).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 10).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 9).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 8).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 7).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 6).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 5).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 4).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 3).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 2).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 1).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 0).toShort()).toInt()).toShort()).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = one; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Set each packed 16-bit integer in dst to all ones or all zeros based on the value of the corresponding bit in k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_movm_epi16&expand=3884)
 */
public fun mmMovmEpi16(k: Mmask8): M128i =
    run {
        val one = mmSet1Epi16((((((((((((((((((((((((((((((((1.toInt() shl 15).toShort()).toInt() or ((1.toInt() shl 14).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 13).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 12).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 11).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 10).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 9).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 8).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 7).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 6).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 5).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 4).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 3).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 2).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 1).toShort()).toInt()).toShort()).toInt() or ((1.toInt() shl 0).toShort()).toInt()).toShort()).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = one; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Set each packed 8-bit integer in dst to all ones or all zeros based on the value of the corresponding bit in k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_movm_epi8&expand=3895)
 */
public fun mm512MovmEpi8(k: Mmask64): M512i =
    run {
        val one = mm512Set1Epi8((((((((((((((((1.toInt() shl 7).toByte()).toInt() or ((1.toInt() shl 6).toByte()).toInt()).toByte()).toInt() or ((1.toInt() shl 5).toByte()).toInt()).toByte()).toInt() or ((1.toInt() shl 4).toByte()).toInt()).toByte()).toInt() or ((1.toInt() shl 3).toByte()).toInt()).toByte()).toInt() or ((1.toInt() shl 2).toByte()).toInt()).toByte()).toInt() or ((1.toInt() shl 1).toByte()).toInt()).toByte()).toInt() or ((1.toInt() shl 0).toByte()).toInt()).toByte()).asI8x64()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = one; val simdArg2 = Simd<Byte>(List(64) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Set each packed 8-bit integer in dst to all ones or all zeros based on the value of the corresponding bit in k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_movm_epi8&expand=3894)
 */
public fun mm256MovmEpi8(k: Mmask32): M256i =
    run {
        val one = mm256Set1Epi8((((((((((((((((1.toInt() shl 7).toByte()).toInt() or ((1.toInt() shl 6).toByte()).toInt()).toByte()).toInt() or ((1.toInt() shl 5).toByte()).toInt()).toByte()).toInt() or ((1.toInt() shl 4).toByte()).toInt()).toByte()).toInt() or ((1.toInt() shl 3).toByte()).toInt()).toByte()).toInt() or ((1.toInt() shl 2).toByte()).toInt()).toByte()).toInt() or ((1.toInt() shl 1).toByte()).toInt()).toByte()).toInt() or ((1.toInt() shl 0).toByte()).toInt()).toByte()).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = one; val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Set each packed 8-bit integer in dst to all ones or all zeros based on the value of the corresponding bit in k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_movm_epi8&expand=3893)
 */
public fun mmMovmEpi8(k: Mmask16): M128i =
    run {
        val one = mmSet1Epi8((((((((((((((((1.toInt() shl 7).toByte()).toInt() or ((1.toInt() shl 6).toByte()).toInt()).toByte()).toInt() or ((1.toInt() shl 5).toByte()).toInt()).toByte()).toInt() or ((1.toInt() shl 4).toByte()).toInt()).toByte()).toInt() or ((1.toInt() shl 3).toByte()).toInt()).toByte()).toInt() or ((1.toInt() shl 2).toByte()).toInt()).toByte()).toInt() or ((1.toInt() shl 1).toByte()).toInt()).toByte()).toInt() or ((1.toInt() shl 0).toByte()).toInt()).toByte()).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = one; val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Convert 32-bit mask a into an integer value, and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#_cvtmask32_u32)
 */
public fun cvtmask32U32(a: Mmask32): UInt = a

/**
 * Convert integer value a into an 32-bit mask, and store the result in k.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_cvtu32_mask32)
 */
public fun cvtu32Mask32(a: UInt): Mmask32 = a

/**
 * Add 32-bit masks in a and b, and store the result in k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kadd_mask32&expand=3207)
 */
public fun kaddMask32(a: Mmask32, b: Mmask32): Mmask32 = (a + (b.toUInt()))

/**
 * Add 64-bit masks in a and b, and store the result in k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kadd_mask64&expand=3208)
 */
public fun kaddMask64(a: Mmask64, b: Mmask64): Mmask64 = (a + (b.toULong()))

/**
 * Compute the bitwise AND of 32-bit masks a and b, and store the result in k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kand_mask32&expand=3213)
 */
public fun kandMask32(a: Mmask32, b: Mmask32): Mmask32 = a and b

/**
 * Compute the bitwise AND of 64-bit masks a and b, and store the result in k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kand_mask64&expand=3214)
 */
public fun kandMask64(a: Mmask64, b: Mmask64): Mmask64 = a and b

/**
 * Compute the bitwise NOT of 32-bit mask a, and store the result in k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_knot_mask32&expand=3234)
 */
public fun knotMask32(a: Mmask32): Mmask32 = a.inv()

/**
 * Compute the bitwise NOT of 64-bit mask a, and store the result in k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_knot_mask64&expand=3235)
 */
public fun knotMask64(a: Mmask64): Mmask64 = a.inv()

/**
 * Compute the bitwise NOT of 32-bit masks a and then AND with b, and store the result in k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kandn_mask32&expand=3219)
 */
public fun kandnMask32(a: Mmask32, b: Mmask32): Mmask32 = knotMask32(a) and b

/**
 * Compute the bitwise NOT of 64-bit masks a and then AND with b, and store the result in k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kandn_mask64&expand=3220)
 */
public fun kandnMask64(a: Mmask64, b: Mmask64): Mmask64 = knotMask64(a) and b

/**
 * Compute the bitwise OR of 32-bit masks a and b, and store the result in k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kor_mask32&expand=3240)
 */
public fun korMask32(a: Mmask32, b: Mmask32): Mmask32 = a or b

/**
 * Compute the bitwise OR of 64-bit masks a and b, and store the result in k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kor_mask64&expand=3241)
 */
public fun korMask64(a: Mmask64, b: Mmask64): Mmask64 = a or b

/**
 * Compute the bitwise XOR of 32-bit masks a and b, and store the result in k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kxor_mask32&expand=3292)
 */
public fun kxorMask32(a: Mmask32, b: Mmask32): Mmask32 = a xor b

/**
 * Compute the bitwise XOR of 64-bit masks a and b, and store the result in k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kxor_mask64&expand=3293)
 */
public fun kxorMask64(a: Mmask64, b: Mmask64): Mmask64 = a xor b

/**
 * Compute the bitwise XNOR of 32-bit masks a and b, and store the result in k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kxnor_mask32&expand=3286)
 */
public fun kxnorMask32(a: Mmask32, b: Mmask32): Mmask32 = knotMask32(a xor b)

/**
 * Compute the bitwise XNOR of 64-bit masks a and b, and store the result in k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kxnor_mask64&expand=3287)
 */
public fun kxnorMask64(a: Mmask64, b: Mmask64): Mmask64 = knotMask64(a xor b)

/**
 * Compute the bitwise OR of 32-bit masks a and b. If the result is all zeros, store 1 in dst, otherwise
 * store 0 in dst. If the result is all ones, store 1 in allOnes, otherwise store 0 in allOnes.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kortest_mask32_u8)
 */
public fun kortestMask32U8(a: Mmask32, b: Mmask32, allOnes: MutPtr): UByte {
    val allOnes1 = allOnes
    val tmp = korMask32(a, b)
    (PrimitiveLayouts.U8).writeNative((allOnes1).rawAddress, (if (tmp == 0xffffffffu) 1u else 0u).toUByte())
    return (if (tmp == 0u) 1u else 0u).toUByte()
}

/**
 * Compute the bitwise OR of 64-bit masks a and b. If the result is all zeros, store 1 in dst, otherwise
 * store 0 in dst. If the result is all ones, store 1 in allOnes, otherwise store 0 in allOnes.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kortest_mask64_u8)
 */
public fun kortestMask64U8(a: Mmask64, b: Mmask64, allOnes: MutPtr): UByte {
    val allOnes1 = allOnes
    val tmp = korMask64(a, b)
    (PrimitiveLayouts.U8).writeNative((allOnes1).rawAddress, (if (tmp == 0xffffffffffffffffuL) 1u else 0u).toUByte())
    return (if (tmp == 0uL) 1u else 0u).toUByte()
}

/**
 * Compute the bitwise OR of 32-bit masks a and b. If the result is all ones, store 1 in dst, otherwise
 * store 0 in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kortestc_mask32_u8)
 */
public fun kortestcMask32U8(a: Mmask32, b: Mmask32): UByte = (if (korMask32(a, b) == 0xffffffffu) 1u else 0u).toUByte()

/**
 * Compute the bitwise OR of 64-bit masks a and b. If the result is all ones, store 1 in dst, otherwise
 * store 0 in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kortestc_mask64_u8)
 */
public fun kortestcMask64U8(a: Mmask64, b: Mmask64): UByte = (if (korMask64(a, b) == 0xffffffffffffffffuL) 1u else 0u).toUByte()

/**
 * Compute the bitwise OR of 32-bit masks a and b. If the result is all zeros, store 1 in dst, otherwise
 * store 0 in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kortestz_mask32_u8)
 */
public fun kortestzMask32U8(a: Mmask32, b: Mmask32): UByte = (if (korMask32(a, b) == 0u) 1u else 0u).toUByte()

/**
 * Compute the bitwise OR of 64-bit masks a and b. If the result is all zeros, store 1 in dst, otherwise
 * store 0 in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kortestz_mask64_u8)
 */
public fun kortestzMask64U8(a: Mmask64, b: Mmask64): UByte = (if (korMask64(a, b) == 0uL) 1u else 0u).toUByte()

/**
 * Shift the bits of 32-bit mask a left by count while shifting in zeros, and store the least significant 32 bits of the result in k.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kshiftli_mask32)
 */
public fun kshiftliMask32(a: Mmask32, count: UInt): Mmask32 = a.unboundedShl(count)

/**
 * Shift the bits of 64-bit mask a left by count while shifting in zeros, and store the least significant 32 bits of the result in k.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kshiftli_mask64)
 */
public fun kshiftliMask64(a: Mmask64, count: UInt): Mmask64 = a.unboundedShl(count)

/**
 * Shift the bits of 32-bit mask a right by count while shifting in zeros, and store the least significant 32 bits of the result in k.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kshiftri_mask32)
 */
public fun kshiftriMask32(a: Mmask32, count: UInt): Mmask32 = a.unboundedShr(count)

/**
 * Shift the bits of 64-bit mask a right by count while shifting in zeros, and store the least significant 32 bits of the result in k.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_kshiftri_mask64)
 */
public fun kshiftriMask64(a: Mmask64, count: UInt): Mmask64 = a.unboundedShr(count)

/**
 * Compute the bitwise AND of 32-bit masks a and b, and if the result is all zeros, store 1 in dst,
 * otherwise store 0 in dst. Compute the bitwise NOT of a and then AND with b, if the result is all
 * zeros, store 1 in andNot, otherwise store 0 in andNot.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_ktest_mask32_u8)
 */
public fun ktestMask32U8(a: Mmask32, b: Mmask32, andNot: MutPtr): UByte {
    val andNot1 = andNot
    (PrimitiveLayouts.U8).writeNative((andNot1).rawAddress, (if (kandnMask32(a, b) == 0u) 1u else 0u).toUByte())
    return (if (kandMask32(a, b) == 0u) 1u else 0u).toUByte()
}

/**
 * Compute the bitwise AND of 64-bit masks a and b, and if the result is all zeros, store 1 in dst,
 * otherwise store 0 in dst. Compute the bitwise NOT of a and then AND with b, if the result is all
 * zeros, store 1 in andNot, otherwise store 0 in andNot.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_ktest_mask64_u8)
 */
public fun ktestMask64U8(a: Mmask64, b: Mmask64, andNot: MutPtr): UByte {
    val andNot1 = andNot
    (PrimitiveLayouts.U8).writeNative((andNot1).rawAddress, (if (kandnMask64(a, b) == 0uL) 1u else 0u).toUByte())
    return (if (kandMask64(a, b) == 0uL) 1u else 0u).toUByte()
}

/**
 * Compute the bitwise NOT of 32-bit mask a and then AND with 16-bit mask b, if the result is all
 * zeros, store 1 in dst, otherwise store 0 in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_ktestc_mask32_u8)
 */
public fun ktestcMask32U8(a: Mmask32, b: Mmask32): UByte = (if (kandnMask32(a, b) == 0u) 1u else 0u).toUByte()

/**
 * Compute the bitwise NOT of 64-bit mask a and then AND with 8-bit mask b, if the result is all
 * zeros, store 1 in dst, otherwise store 0 in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_ktestc_mask64_u8)
 */
public fun ktestcMask64U8(a: Mmask64, b: Mmask64): UByte = (if (kandnMask64(a, b) == 0uL) 1u else 0u).toUByte()

/**
 * Compute the bitwise AND of 32-bit masks a and  b, if the result is all zeros, store 1 in dst, otherwise
 * store 0 in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_ktestz_mask32_u8)
 */
public fun ktestzMask32U8(a: Mmask32, b: Mmask32): UByte = (if (kandMask32(a, b) == 0u) 1u else 0u).toUByte()

/**
 * Compute the bitwise AND of 64-bit masks a and  b, if the result is all zeros, store 1 in dst, otherwise
 * store 0 in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_ktestz_mask64_u8)
 */
public fun ktestzMask64U8(a: Mmask64, b: Mmask64): UByte = (if (kandMask64(a, b) == 0uL) 1u else 0u).toUByte()

// generate normal and code instead of kunpckwd

/**
 * Unpack and interleave 16 bits from masks a and b, and store the 32-bit result in k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_kunpackw)
 */
public fun mm512Kunpackw(a: Mmask32, b: Mmask32): Mmask32 = ((a and 0xffffu) shl 16) or (b and 0xffffu)

// generate normal and code instead of kunpckdq

/**
 * Unpack and interleave 32 bits from masks a and b, and store the 64-bit result in k.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_kunpackd)
 */
public fun mm512Kunpackd(a: Mmask64, b: Mmask64): Mmask64 = ((a and 0xffffffffuL) shl 32) or (b and 0xffffffffuL)

/**
 * Convert packed 16-bit integers in a to packed 8-bit integers with truncation, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvtepi16_epi8&expand=1407)
 */
public fun mm512Cvtepi16Epi8(a: M512i): M256i =
    run {
        val a1 = a.asI16x32()
        transmuteWithLayouts<I8x32, M256i>(run { val simdArg0 = a1; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x32>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x32>()).alignBytes)); res } }, UnresolvedTypeLayout<I8x32>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Convert packed 16-bit integers in a to packed 8-bit integers with truncation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvtepi16_epi8&expand=1408)
 */
public fun mm512MaskCvtepi16Epi8(src: M256i, k: Mmask32, a: M512i): M256i =
    run {
        val convert = mm512Cvtepi16Epi8(a).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = convert; val simdArg2 = src.asI8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Convert packed 16-bit integers in a to packed 8-bit integers with truncation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvtepi16_epi8&expand=1409)
 */
public fun mm512MaskzCvtepi16Epi8(k: Mmask32, a: M512i): M256i =
    run {
        val convert = mm512Cvtepi16Epi8(a).asI8x32()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = convert; val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Convert packed 16-bit integers in a to packed 8-bit integers with truncation, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtepi16_epi8&expand=1404)
 */
public fun mm256Cvtepi16Epi8(a: M256i): M128i =
    run {
        val a1 = a.asI16x16()
        transmuteWithLayouts<I8x16, M128i>(run { val simdArg0 = a1; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }, UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Convert packed 16-bit integers in a to packed 8-bit integers with truncation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cvtepi16_epi8&expand=1405)
 */
public fun mm256MaskCvtepi16Epi8(src: M128i, k: Mmask16, a: M256i): M128i =
    run {
        val convert = mm256Cvtepi16Epi8(a).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = convert; val simdArg2 = src.asI8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Convert packed 16-bit integers in a to packed 8-bit integers with truncation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_cvtepi16_epi8&expand=1406)
 */
public fun mm256MaskzCvtepi16Epi8(k: Mmask16, a: M256i): M128i =
    run {
        val convert = mm256Cvtepi16Epi8(a).asI8x16()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = convert; val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Convert packed 16-bit integers in a to packed 8-bit integers with truncation, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtepi16_epi8&expand=1401)
 */
public fun mmCvtepi16Epi8(a: M128i): M128i =
    run {
        val a1 = a.asI16x8()
        val v256: I16x16 = run { val simdArg0 = a1; val simdArg1 = Simd<Short>(List(8) { 0.toShort() }); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 8u, 8u, 8u, 8u, 8u, 8u, 8u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); res } }
        transmuteWithLayouts<I8x16, M128i>(run { val simdArg0 = v256; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }, UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Convert packed 16-bit integers in a to packed 8-bit integers with truncation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cvtepi16_epi8&expand=1402)
 */
public fun mmMaskCvtepi16Epi8(src: M128i, k: Mmask8, a: M128i): M128i =
    run {
        val a1 = mmCvtepi16Epi8(a).asI8x16()
        val src1 = run { val simdArg0 = src.asI8x16(); val simdArg1 = Simd<Byte>(List(16) { 0.toByte() }); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 16u, 16u, 16u, 16u, 16u, 16u, 16u, 16u)); M128i() }
        run { val simdArg0 = k.toUShort(); val simdArg1 = a1; val simdArg2 = src1; simdArg1 }.asM128i()
    }

/**
 * Convert packed 16-bit integers in a to packed 8-bit integers with truncation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_cvtepi16_epi8&expand=1403)
 */
public fun mmMaskzCvtepi16Epi8(k: Mmask8, a: M128i): M128i = mmMaskCvtepi16Epi8(mmSetzeroSi128(), k, a)

/**
 * Convert packed signed 16-bit integers in a to packed 8-bit integers with signed saturation, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvtsepi16_epi8&expand=1807)
 */
public fun mm512Cvtsepi16Epi8(a: M512i): M256i = run { val simdArg0 = simdImax(simdImin(a.asI16x32(), Simd<Short>(List(32) { Byte.MAX_VALUE.toShort() })), Simd<Short>(List(32) { Byte.MIN_VALUE.toShort() })); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x32>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x32>()).alignBytes)); res } }.asM256i()

/**
 * Convert packed signed 16-bit integers in a to packed 8-bit integers with signed saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvtsepi16_epi8&expand=1808)
 */
public fun mm512MaskCvtsepi16Epi8(src: M256i, k: Mmask32, a: M512i): M256i = run { val simdArg0 = k; val simdArg1 = mm512Cvtsepi16Epi8(a).asI8x32(); val simdArg2 = src.asI8x32(); simdArg1 }.asM256i()

/**
 * Convert packed signed 16-bit integers in a to packed 8-bit integers with signed saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvtsepi16_epi8&expand=1809)
 */
public fun mm512MaskzCvtsepi16Epi8(k: Mmask32, a: M512i): M256i = run { val simdArg0 = k; val simdArg1 = mm512Cvtsepi16Epi8(a).asI8x32(); val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }.asM256i()

/**
 * Convert packed signed 16-bit integers in a to packed 8-bit integers with signed saturation, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtsepi16_epi8&expand=1804)
 */
public fun mm256Cvtsepi16Epi8(a: M256i): M128i = run { val simdArg0 = simdImax(simdImin(a.asI16x16(), Simd<Short>(List(16) { Byte.MAX_VALUE.toShort() })), Simd<Short>(List(16) { Byte.MIN_VALUE.toShort() })); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }.asM128i()

/**
 * Convert packed signed 16-bit integers in a to packed 8-bit integers with signed saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cvtsepi16_epi8&expand=1805)
 */
public fun mm256MaskCvtsepi16Epi8(src: M128i, k: Mmask16, a: M256i): M128i = run { val simdArg0 = k; val simdArg1 = mm256Cvtsepi16Epi8(a).asI8x16(); val simdArg2 = src.asI8x16(); simdArg1 }.asM128i()

/**
 * Convert packed signed 16-bit integers in a to packed 8-bit integers with signed saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_cvtsepi16_epi8&expand=1806)
 */
public fun mm256MaskzCvtsepi16Epi8(k: Mmask16, a: M256i): M128i = run { val simdArg0 = k; val simdArg1 = mm256Cvtsepi16Epi8(a).asI8x16(); val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }.asM128i()

/**
 * Convert packed signed 16-bit integers in a to packed 8-bit integers with signed saturation, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtsepi16_epi8&expand=1801)
 */
public fun mmCvtsepi16Epi8(a: M128i): M128i = transmuteWithLayouts<I8x16, M128i>(vpmovswb128(a.asI16x8(), Simd<Byte>(List(16) { 0.toByte() }), 0xFFu), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<M128i>())

/**
 * Convert packed signed 16-bit integers in a to packed 8-bit integers with signed saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cvtsepi16_epi8&expand=1802)
 */
public fun mmMaskCvtsepi16Epi8(src: M128i, k: Mmask8, a: M128i): M128i = transmuteWithLayouts<I8x16, M128i>(vpmovswb128(a.asI16x8(), src.asI8x16(), k), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<M128i>())

/**
 * Convert packed signed 16-bit integers in a to packed 8-bit integers with signed saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_cvtsepi16_epi8&expand=1803)
 */
public fun mmMaskzCvtsepi16Epi8(k: Mmask8, a: M128i): M128i = transmuteWithLayouts<I8x16, M128i>(vpmovswb128(a.asI16x8(), Simd<Byte>(List(16) { 0.toByte() }), k), UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<M128i>())

/**
 * Convert packed unsigned 16-bit integers in a to packed unsigned 8-bit integers with unsigned saturation, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvtusepi16_epi8&expand=2042)
 */
public fun mm512Cvtusepi16Epi8(a: M512i): M256i = run { val simdArg0 = simdImin(a.asU16x32(), Simd<UShort>(List(32) { UByte.MAX_VALUE.toUShort() })); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x32>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x32>()).alignBytes)); res } }.asM256i()

/**
 * Convert packed unsigned 16-bit integers in a to packed unsigned 8-bit integers with unsigned saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvtusepi16_epi8&expand=2043)
 */
public fun mm512MaskCvtusepi16Epi8(src: M256i, k: Mmask32, a: M512i): M256i = run { val simdArg0 = k; val simdArg1 = mm512Cvtusepi16Epi8(a).asU8x32(); val simdArg2 = src.asU8x32(); simdArg1 }.asM256i()

/**
 * Convert packed unsigned 16-bit integers in a to packed unsigned 8-bit integers with unsigned saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvtusepi16_epi8&expand=2044)
 */
public fun mm512MaskzCvtusepi16Epi8(k: Mmask32, a: M512i): M256i = run { val simdArg0 = k; val simdArg1 = mm512Cvtusepi16Epi8(a).asU8x32(); val simdArg2 = Simd<UByte>(List(32) { 0u.toUByte() }); simdArg1 }.asM256i()

/**
 * Convert packed unsigned 16-bit integers in a to packed unsigned 8-bit integers with unsigned saturation, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_cvtusepi16_epi8&expand=2039)
 */
public fun mm256Cvtusepi16Epi8(a: M256i): M128i = run { val simdArg0 = simdImin(a.asU16x16(), Simd<UShort>(List(16) { UByte.MAX_VALUE.toUShort() })); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x16>()).alignBytes)); res } }.asM128i()

/**
 * Convert packed unsigned 16-bit integers in a to packed unsigned 8-bit integers with unsigned saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cvtusepi16_epi8&expand=2040)
 */
public fun mm256MaskCvtusepi16Epi8(src: M128i, k: Mmask16, a: M256i): M128i = run { val simdArg0 = k; val simdArg1 = mm256Cvtusepi16Epi8(a).asU8x16(); val simdArg2 = src.asU8x16(); simdArg1 }.asM128i()

/**
 * Convert packed unsigned 16-bit integers in a to packed unsigned 8-bit integers with unsigned saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_cvtusepi16_epi8&expand=2041)
 */
public fun mm256MaskzCvtusepi16Epi8(k: Mmask16, a: M256i): M128i = run { val simdArg0 = k; val simdArg1 = mm256Cvtusepi16Epi8(a).asU8x16(); val simdArg2 = Simd<UByte>(List(16) { 0u.toUByte() }); simdArg1 }.asM128i()

/**
 * Convert packed unsigned 16-bit integers in a to packed unsigned 8-bit integers with unsigned saturation, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtusepi16_epi8&expand=2036)
 */
public fun mmCvtusepi16Epi8(a: M128i): M128i = transmuteWithLayouts<U8x16, M128i>(vpmovuswb128(a.asU16x8(), Simd<UByte>(List(16) { 0u.toUByte() }), 0xFFu), UnresolvedTypeLayout<U8x16>(), UnresolvedTypeLayout<M128i>())

/**
 * Convert packed unsigned 16-bit integers in a to packed unsigned 8-bit integers with unsigned saturation, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cvtusepi16_epi8&expand=2037)
 */
public fun mmMaskCvtusepi16Epi8(src: M128i, k: Mmask8, a: M128i): M128i = transmuteWithLayouts<U8x16, M128i>(vpmovuswb128(a.asU16x8(), src.asU8x16(), k), UnresolvedTypeLayout<U8x16>(), UnresolvedTypeLayout<M128i>())

/**
 * Convert packed unsigned 16-bit integers in a to packed unsigned 8-bit integers with unsigned saturation, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_cvtusepi16_epi8&expand=2038)
 */
public fun mmMaskzCvtusepi16Epi8(k: Mmask8, a: M128i): M128i = transmuteWithLayouts<U8x16, M128i>(vpmovuswb128(a.asU16x8(), Simd<UByte>(List(16) { 0u.toUByte() }), k), UnresolvedTypeLayout<U8x16>(), UnresolvedTypeLayout<M128i>())

/**
 * Sign extend packed 8-bit integers in a to packed 16-bit integers, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvtepi8_epi16&expand=1526)
 */
public fun mm512Cvtepi8Epi16(a: M256i): M512i =
    run {
        val a1 = a.asI8x32()
        transmuteWithLayouts<I16x32, M512i>(run { val simdArg0 = a1; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); res } }, UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Sign extend packed 8-bit integers in a to packed 16-bit integers, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvtepi8_epi16&expand=1527)
 */
public fun mm512MaskCvtepi8Epi16(src: M512i, k: Mmask32, a: M256i): M512i =
    run {
        val convert = mm512Cvtepi8Epi16(a).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = convert; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Sign extend packed 8-bit integers in a to packed 16-bit integers, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvtepi8_epi16&expand=1528)
 */
public fun mm512MaskzCvtepi8Epi16(k: Mmask32, a: M256i): M512i =
    run {
        val convert = mm512Cvtepi8Epi16(a).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = convert; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Sign extend packed 8-bit integers in a to packed 16-bit integers, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cvtepi8_epi16&expand=1524)
 */
public fun mm256MaskCvtepi8Epi16(src: M256i, k: Mmask16, a: M128i): M256i =
    run {
        val convert = mm256Cvtepi8Epi16(a).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = convert; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Sign extend packed 8-bit integers in a to packed 16-bit integers, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_cvtepi8_epi16&expand=1525)
 */
public fun mm256MaskzCvtepi8Epi16(k: Mmask16, a: M128i): M256i =
    run {
        val convert = mm256Cvtepi8Epi16(a).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = convert; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Sign extend packed 8-bit integers in a to packed 16-bit integers, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cvtepi8_epi16&expand=1521)
 */
public fun mmMaskCvtepi8Epi16(src: M128i, k: Mmask8, a: M128i): M128i =
    run {
        val convert = mmCvtepi8Epi16(a).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = convert; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Sign extend packed 8-bit integers in a to packed 16-bit integers, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_cvtepi8_epi16&expand=1522)
 */
public fun mmMaskzCvtepi8Epi16(k: Mmask8, a: M128i): M128i =
    run {
        val convert = mmCvtepi8Epi16(a).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = convert; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Zero extend packed unsigned 8-bit integers in a to packed 16-bit integers, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_cvtepu8_epi16&expand=1612)
 */
public fun mm512Cvtepu8Epi16(a: M256i): M512i =
    run {
        val a1 = a.asU8x32()
        transmuteWithLayouts<I16x32, M512i>(run { val simdArg0 = a1; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); res } }, UnresolvedTypeLayout<I16x32>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Zero extend packed unsigned 8-bit integers in a to packed 16-bit integers, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvtepu8_epi16&expand=1613)
 */
public fun mm512MaskCvtepu8Epi16(src: M512i, k: Mmask32, a: M256i): M512i =
    run {
        val convert = mm512Cvtepu8Epi16(a).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = convert; val simdArg2 = src.asI16x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Zero extend packed unsigned 8-bit integers in a to packed 16-bit integers, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_cvtepu8_epi16&expand=1614)
 */
public fun mm512MaskzCvtepu8Epi16(k: Mmask32, a: M256i): M512i =
    run {
        val convert = mm512Cvtepu8Epi16(a).asI16x32()
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = convert; val simdArg2 = Simd<Short>(List(32) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Zero extend packed unsigned 8-bit integers in a to packed 16-bit integers, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cvtepu8_epi16&expand=1610)
 */
public fun mm256MaskCvtepu8Epi16(src: M256i, k: Mmask16, a: M128i): M256i =
    run {
        val convert = mm256Cvtepu8Epi16(a).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = convert; val simdArg2 = src.asI16x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Zero extend packed unsigned 8-bit integers in a to packed 16-bit integers, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_cvtepu8_epi16&expand=1611)
 */
public fun mm256MaskzCvtepu8Epi16(k: Mmask16, a: M128i): M256i =
    run {
        val convert = mm256Cvtepu8Epi16(a).asI16x16()
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = convert; val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Zero extend packed unsigned 8-bit integers in a to packed 16-bit integers, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cvtepu8_epi16&expand=1607)
 */
public fun mmMaskCvtepu8Epi16(src: M128i, k: Mmask8, a: M128i): M128i =
    run {
        val convert = mmCvtepu8Epi16(a).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = convert; val simdArg2 = src.asI16x8(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Zero extend packed unsigned 8-bit integers in a to packed 16-bit integers, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_cvtepu8_epi16&expand=1608)
 */
public fun mmMaskzCvtepu8Epi16(k: Mmask8, a: M128i): M128i =
    run {
        val convert = mmCvtepu8Epi16(a).asI16x8()
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = convert; val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Shift 128-bit lanes in a left by imm8 bytes while shifting in zeros, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_bslli_epi128&expand=591)
 */
public fun mm512BslliEpi128(a: M512i, imm8: Int): M512i {
    return run {
        fun mask(shift: Int, i: UInt): UInt {
            val shift1 = shift.toUInt() and 0xffu
            return if (shift1 > 15u || i % 16u < shift1) {
                0u
            } else {
                64u + (i - shift1)
            }
        }
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val a1 = a.asI8x64()
        val zero = Simd<Byte>(List(64) { 0.toByte() })
        val r: I8x64 = run { val simdArg0 = zero; val simdArg1 = a1; val simdArg2 = SimdShuffleIdx(uintArrayOf(mask(imm8, 0u), mask(imm8, 1u), mask(imm8, 2u), mask(imm8, 3u), mask(imm8, 4u), mask(imm8, 5u), mask(imm8, 6u), mask(imm8, 7u), mask(imm8, 8u), mask(imm8, 9u), mask(imm8, 10u), mask(imm8, 11u), mask(imm8, 12u), mask(imm8, 13u), mask(imm8, 14u), mask(imm8, 15u), mask(imm8, 16u), mask(imm8, 17u), mask(imm8, 18u), mask(imm8, 19u), mask(imm8, 20u), mask(imm8, 21u), mask(imm8, 22u), mask(imm8, 23u), mask(imm8, 24u), mask(imm8, 25u), mask(imm8, 26u), mask(imm8, 27u), mask(imm8, 28u), mask(imm8, 29u), mask(imm8, 30u), mask(imm8, 31u), mask(imm8, 32u), mask(imm8, 33u), mask(imm8, 34u), mask(imm8, 35u), mask(imm8, 36u), mask(imm8, 37u), mask(imm8, 38u), mask(imm8, 39u), mask(imm8, 40u), mask(imm8, 41u), mask(imm8, 42u), mask(imm8, 43u), mask(imm8, 44u), mask(imm8, 45u), mask(imm8, 46u), mask(imm8, 47u), mask(imm8, 48u), mask(imm8, 49u), mask(imm8, 50u), mask(imm8, 51u), mask(imm8, 52u), mask(imm8, 53u), mask(imm8, 54u), mask(imm8, 55u), mask(imm8, 56u), mask(imm8, 57u), mask(imm8, 58u), mask(imm8, 59u), mask(imm8, 60u), mask(imm8, 61u), mask(imm8, 62u), mask(imm8, 63u))); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x64>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); res } }
        transmuteWithLayouts<I8x64, M512i>(r, UnresolvedTypeLayout<I8x64>(), UnresolvedTypeLayout<M512i>())
    }
}

/**
 * Shift 128-bit lanes in a right by imm8 bytes while shifting in zeros, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_bsrli_epi128&expand=594)
 */
public fun mm512BsrliEpi128(a: M512i, imm8: Int): M512i {
    return run {
        fun mask(shift: Int, i: UInt): UInt {
            val shift1 = shift.toUInt() and 0xffu
            return if (shift1 > 15u || (15u - (i % 16u)) < shift1) {
                0u
            } else {
                64u + (i + shift1)
            }
        }
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val a1 = a.asI8x64()
        val zero = Simd<Byte>(List(64) { 0.toByte() })
        val r: I8x64 = run { val simdArg0 = zero; val simdArg1 = a1; val simdArg2 = SimdShuffleIdx(uintArrayOf(mask(imm8, 0u), mask(imm8, 1u), mask(imm8, 2u), mask(imm8, 3u), mask(imm8, 4u), mask(imm8, 5u), mask(imm8, 6u), mask(imm8, 7u), mask(imm8, 8u), mask(imm8, 9u), mask(imm8, 10u), mask(imm8, 11u), mask(imm8, 12u), mask(imm8, 13u), mask(imm8, 14u), mask(imm8, 15u), mask(imm8, 16u), mask(imm8, 17u), mask(imm8, 18u), mask(imm8, 19u), mask(imm8, 20u), mask(imm8, 21u), mask(imm8, 22u), mask(imm8, 23u), mask(imm8, 24u), mask(imm8, 25u), mask(imm8, 26u), mask(imm8, 27u), mask(imm8, 28u), mask(imm8, 29u), mask(imm8, 30u), mask(imm8, 31u), mask(imm8, 32u), mask(imm8, 33u), mask(imm8, 34u), mask(imm8, 35u), mask(imm8, 36u), mask(imm8, 37u), mask(imm8, 38u), mask(imm8, 39u), mask(imm8, 40u), mask(imm8, 41u), mask(imm8, 42u), mask(imm8, 43u), mask(imm8, 44u), mask(imm8, 45u), mask(imm8, 46u), mask(imm8, 47u), mask(imm8, 48u), mask(imm8, 49u), mask(imm8, 50u), mask(imm8, 51u), mask(imm8, 52u), mask(imm8, 53u), mask(imm8, 54u), mask(imm8, 55u), mask(imm8, 56u), mask(imm8, 57u), mask(imm8, 58u), mask(imm8, 59u), mask(imm8, 60u), mask(imm8, 61u), mask(imm8, 62u), mask(imm8, 63u))); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x64>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); res } }
        transmuteWithLayouts<I8x64, M512i>(r, UnresolvedTypeLayout<I8x64>(), UnresolvedTypeLayout<M512i>())
    }
}

/**
 * Concatenate pairs of 16-byte blocks in a and b into a 32-byte temporary result, shift the result right by imm8 bytes, and store the low 16 bytes in dst.
 * Unlike [mmAlignrEpi8], [mm256AlignrEpi8] functions, where the entire input vectors are concatenated to the temporary result,
 * this concatenation happens in 4 steps, where each step builds 32-byte temporary result.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_alignr_epi8&expand=263)
 */
public fun mm512AlignrEpi8(a: M512i, b: M512i, imm8: Int): M512i {
    fun mask(shift: UInt, i: UInt): UInt {
        val shift1 = shift % 16u
        val modI = i % 16u
        return if (modI < (16u - shift1)) {
            i + shift1
        } else {
            i + 48u + shift1
        }
    }
    // If palignr is shifting the pair of vectors more than the size of two
    // lanes, emit zero.
    if (imm8 >= 32) {
        return mm512SetzeroSi512()
    }
    // If palignr is shifting the pair of input vectors more than one lane,
    // but less than two lanes, convert to shifting in zeroes.
    val (a1, b1) =
        if (imm8 > 16) {
            Pair(mm512SetzeroSi512(), a)
        } else {
            Pair(a, b)
        }
    return run {
        if (imm8 == 16) {
            return a1
        }
        val r: I8x64 = run { val simdArg0 = b1.asI8x64(); val simdArg1 = a1.asI8x64(); val simdArg2 = SimdShuffleIdx(uintArrayOf(mask(imm8.toUInt(), 0u), mask(imm8.toUInt(), 1u), mask(imm8.toUInt(), 2u), mask(imm8.toUInt(), 3u), mask(imm8.toUInt(), 4u), mask(imm8.toUInt(), 5u), mask(imm8.toUInt(), 6u), mask(imm8.toUInt(), 7u), mask(imm8.toUInt(), 8u), mask(imm8.toUInt(), 9u), mask(imm8.toUInt(), 10u), mask(imm8.toUInt(), 11u), mask(imm8.toUInt(), 12u), mask(imm8.toUInt(), 13u), mask(imm8.toUInt(), 14u), mask(imm8.toUInt(), 15u), mask(imm8.toUInt(), 16u), mask(imm8.toUInt(), 17u), mask(imm8.toUInt(), 18u), mask(imm8.toUInt(), 19u), mask(imm8.toUInt(), 20u), mask(imm8.toUInt(), 21u), mask(imm8.toUInt(), 22u), mask(imm8.toUInt(), 23u), mask(imm8.toUInt(), 24u), mask(imm8.toUInt(), 25u), mask(imm8.toUInt(), 26u), mask(imm8.toUInt(), 27u), mask(imm8.toUInt(), 28u), mask(imm8.toUInt(), 29u), mask(imm8.toUInt(), 30u), mask(imm8.toUInt(), 31u), mask(imm8.toUInt(), 32u), mask(imm8.toUInt(), 33u), mask(imm8.toUInt(), 34u), mask(imm8.toUInt(), 35u), mask(imm8.toUInt(), 36u), mask(imm8.toUInt(), 37u), mask(imm8.toUInt(), 38u), mask(imm8.toUInt(), 39u), mask(imm8.toUInt(), 40u), mask(imm8.toUInt(), 41u), mask(imm8.toUInt(), 42u), mask(imm8.toUInt(), 43u), mask(imm8.toUInt(), 44u), mask(imm8.toUInt(), 45u), mask(imm8.toUInt(), 46u), mask(imm8.toUInt(), 47u), mask(imm8.toUInt(), 48u), mask(imm8.toUInt(), 49u), mask(imm8.toUInt(), 50u), mask(imm8.toUInt(), 51u), mask(imm8.toUInt(), 52u), mask(imm8.toUInt(), 53u), mask(imm8.toUInt(), 54u), mask(imm8.toUInt(), 55u), mask(imm8.toUInt(), 56u), mask(imm8.toUInt(), 57u), mask(imm8.toUInt(), 58u), mask(imm8.toUInt(), 59u), mask(imm8.toUInt(), 60u), mask(imm8.toUInt(), 61u), mask(imm8.toUInt(), 62u), mask(imm8.toUInt(), 63u))); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x64>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x64>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x64>()).alignBytes)); res } }
        transmuteWithLayouts<I8x64, M512i>(r, UnresolvedTypeLayout<I8x64>(), UnresolvedTypeLayout<M512i>())
    }
}

/**
 * Concatenate pairs of 16-byte blocks in a and b into a 32-byte temporary result, shift the result right by imm8 bytes, and store the low 16 bytes in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_alignr_epi8&expand=264)
 */
public fun mm512MaskAlignrEpi8(src: M512i, k: Mmask64, a: M512i, b: M512i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val r = mm512AlignrEpi8(a, b, imm8)
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = r.asI8x64(); val simdArg2 = src.asI8x64(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Concatenate pairs of 16-byte blocks in a and b into a 32-byte temporary result, shift the result right by imm8 bytes, and store the low 16 bytes in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_alignr_epi8&expand=265)
 */
public fun mm512MaskzAlignrEpi8(k: Mmask64, a: M512i, b: M512i, imm8: Int): M512i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val r = mm512AlignrEpi8(a, b, imm8)
        transmuteWithLayouts<Any?, M512i>(run { val simdArg0 = k; val simdArg1 = r.asI8x64(); val simdArg2 = Simd<Byte>(List(64) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M512i>())
    }

/**
 * Concatenate pairs of 16-byte blocks in a and b into a 32-byte temporary result, shift the result right by imm8 bytes, and store the low 16 bytes in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_alignr_epi8&expand=261)
 */
public fun mm256MaskAlignrEpi8(src: M256i, k: Mmask32, a: M256i, b: M256i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val r = mm256AlignrEpi8(a, b, imm8)
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = r.asI8x32(); val simdArg2 = src.asI8x32(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Concatenate pairs of 16-byte blocks in a and b into a 32-byte temporary result, shift the result right by imm8 bytes, and store the low 16 bytes in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_alignr_epi8&expand=262)
 */
public fun mm256MaskzAlignrEpi8(k: Mmask32, a: M256i, b: M256i, imm8: Int): M256i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val r = mm256AlignrEpi8(a, b, imm8)
        transmuteWithLayouts<Any?, M256i>(run { val simdArg0 = k; val simdArg1 = r.asI8x32(); val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M256i>())
    }

/**
 * Concatenate pairs of 16-byte blocks in a and b into a 32-byte temporary result, shift the result right by imm8 bytes, and store the low 16 bytes in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_alignr_epi8&expand=258)
 */
public fun mmMaskAlignrEpi8(src: M128i, k: Mmask16, a: M128i, b: M128i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val r = mmAlignrEpi8(a, b, imm8)
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = r.asI8x16(); val simdArg2 = src.asI8x16(); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Concatenate pairs of 16-byte blocks in a and b into a 32-byte temporary result, shift the result right by imm8 bytes, and store the low 16 bytes in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_alignr_epi8&expand=259)
 */
public fun mmMaskzAlignrEpi8(k: Mmask16, a: M128i, b: M128i, imm8: Int): M128i =
    run {
        run {
            check(0 <= imm8 && imm8 < (1 shl 8)) { "IMM8 doesn't fit in 8 bits (unsigned)" }
        }
        val r = mmAlignrEpi8(a, b, imm8)
        transmuteWithLayouts<Any?, M128i>(run { val simdArg0 = k; val simdArg1 = r.asI8x16(); val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<M128i>())
    }

/**
 * Convert packed signed 16-bit integers in a to packed 8-bit integers with signed saturation, and store the active results (those with their respective bit set in writemask k) to unaligned memory at baseAddr.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvtsepi16_storeu_epi8&expand=1812)
 */
public fun mm512MaskCvtsepi16StoreuEpi8(memAddr: MutPtr, k: Mmask32, a: M512i) {
    vpmovswbmem(memAddr, a.asI16x32(), k)
}

/**
 * Convert packed signed 16-bit integers in a to packed 8-bit integers with signed saturation, and store the active results (those with their respective bit set in writemask k) to unaligned memory at baseAddr.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cvtsepi16_storeu_epi8&expand=1811)
 */
public fun mm256MaskCvtsepi16StoreuEpi8(memAddr: MutPtr, k: Mmask16, a: M256i) {
    val mask = run { val simdArg0 = k; val simdArg1 = Simd<Short>(List(16) { (0.inv()).toShort() }); val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }
    val max = run { val simdArg0 = Short.from(Byte.MAX_VALUE); simdArg0 }
    val min = run { val simdArg0 = Short.from(Byte.MIN_VALUE); simdArg0 }
    val v = simdImax(simdImin(a.asI16x16(), max), min)
    val truncated: I8x16 = run { val simdArg0 = v; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }
    run { val simdArg0 = mask; val simdArg1 = memAddr; val simdArg2 = truncated; Unit }
}

/**
 * Convert packed signed 16-bit integers in a to packed 8-bit integers with signed saturation, and store the active results (those with their respective bit set in writemask k) to unaligned memory at baseAddr.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cvtsepi16_storeu_epi8&expand=1810)
 */
public fun mmMaskCvtsepi16StoreuEpi8(memAddr: MutPtr, k: Mmask8, a: M128i) {
    val mask = run { val simdArg0 = k; val simdArg1 = Simd<Short>(List(8) { (0.inv()).toShort() }); val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }
    val max = run { val simdArg0 = Short.from(Byte.MAX_VALUE); simdArg0 }
    val min = run { val simdArg0 = Short.from(Byte.MIN_VALUE); simdArg0 }
    val v = simdImax(simdImin(a.asI16x8(), max), min)
    val truncated: I8x8 = run { val simdArg0 = v; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); res } }
    run { val simdArg0 = mask; val simdArg1 = memAddr; val simdArg2 = truncated; Unit }
}

/**
 * Convert packed 16-bit integers in a to packed 8-bit integers with truncation, and store the active results (those with their respective bit set in writemask k) to unaligned memory at baseAddr.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvtepi16_storeu_epi8&expand=1412)
 */
public fun mm512MaskCvtepi16StoreuEpi8(memAddr: MutPtr, k: Mmask32, a: M512i) {
    val result = mm512Cvtepi16Epi8(a).asI8x32()
    val mask = run { val simdArg0 = k; val simdArg1 = Simd<Byte>(List(32) { (0.inv()).toByte() }); val simdArg2 = Simd<Byte>(List(32) { 0.toByte() }); simdArg1 }
    run { val simdArg0 = mask; val simdArg1 = memAddr; val simdArg2 = result; Unit }
}

/**
 * Convert packed 16-bit integers in a to packed 8-bit integers with truncation, and store the active results (those with their respective bit set in writemask k) to unaligned memory at baseAddr.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cvtepi16_storeu_epi8&expand=1411)
 */
public fun mm256MaskCvtepi16StoreuEpi8(memAddr: MutPtr, k: Mmask16, a: M256i) {
    val result = mm256Cvtepi16Epi8(a).asI8x16()
    val mask = run { val simdArg0 = k; val simdArg1 = Simd<Byte>(List(16) { (0.inv()).toByte() }); val simdArg2 = Simd<Byte>(List(16) { 0.toByte() }); simdArg1 }
    run { val simdArg0 = mask; val simdArg1 = memAddr; val simdArg2 = result; Unit }
}

/**
 * Convert packed 16-bit integers in a to packed 8-bit integers with truncation, and store the active results (those with their respective bit set in writemask k) to unaligned memory at baseAddr.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cvtepi16_storeu_epi8&expand=1410)
 */
public fun mmMaskCvtepi16StoreuEpi8(memAddr: MutPtr, k: Mmask8, a: M128i) {
    val result: I8x8 = run { val simdArg0 = mmCvtepi16Epi8(a).asI8x16(); val simdArg1 = Simd<Byte>(List(16) { 0.toByte() }); val simdArg2 = SimdShuffleIdx(uintArrayOf(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x8>()).alignBytes)); res } }
    val mask = run { val simdArg0 = k; val simdArg1 = Simd<Byte>(List(8) { (0.inv()).toByte() }); val simdArg2 = Simd<Byte>(List(8) { 0.toByte() }); simdArg1 }
    run { val simdArg0 = mask; val simdArg1 = memAddr; val simdArg2 = result; Unit }
}

/**
 * Convert packed unsigned 16-bit integers in a to packed unsigned 8-bit integers with unsigned saturation, and store the active results (those with their respective bit set in writemask k) to unaligned memory at baseAddr.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_cvtusepi16_storeu_epi8&expand=2047)
 */
public fun mm512MaskCvtusepi16StoreuEpi8(memAddr: MutPtr, k: Mmask32, a: M512i) {
    vpmovuswbmem(memAddr, a.asI16x32(), k)
}

/**
 * Convert packed unsigned 16-bit integers in a to packed unsigned 8-bit integers with unsigned saturation, and store the active results (those with their respective bit set in writemask k) to unaligned memory at baseAddr.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_cvtusepi16_storeu_epi8&expand=2046)
 */
public fun mm256MaskCvtusepi16StoreuEpi8(memAddr: MutPtr, k: Mmask16, a: M256i) {
    val mask = run { val simdArg0 = k; val simdArg1 = Simd<Short>(List(16) { (0.inv()).toShort() }); val simdArg2 = Simd<Short>(List(16) { 0.toShort() }); simdArg1 }
    val memAddr1 = MutPtr((memAddr).rawAddress)
    val max = run { val simdArg0 = UShort.from(UByte.MAX_VALUE); simdArg0 }
    val truncated: U8x16 = run { val simdArg0 = simdImin(a.asU16x16(), max); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x16>()).alignBytes)); res } }
    run { val simdArg0 = mask; val simdArg1 = memAddr1; val simdArg2 = truncated; Unit }
}

/**
 * Convert packed unsigned 16-bit integers in a to packed unsigned 8-bit integers with unsigned saturation, and store the active results (those with their respective bit set in writemask k) to unaligned memory at baseAddr.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_cvtusepi16_storeu_epi8&expand=2045)
 */
public fun mmMaskCvtusepi16StoreuEpi8(memAddr: MutPtr, k: Mmask8, a: M128i) {
    val mask = run { val simdArg0 = k; val simdArg1 = Simd<Short>(List(8) { (0.inv()).toShort() }); val simdArg2 = Simd<Short>(List(8) { 0.toShort() }); simdArg1 }
    val memAddr1 = MutPtr((memAddr).rawAddress)
    val max = run { val simdArg0 = UShort.from(UByte.MAX_VALUE); simdArg0 }
    val v = a.asU16x8()
    val v1 = simdImin(v, max)
    val truncated: U8x8 = run { val simdArg0 = v1; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x8>()).alignBytes)); res } }
    run { val simdArg0 = mask; val simdArg1 = memAddr1; val simdArg2 = truncated; Unit }
}

public expect fun vpmulhrsw(a: I16x32, b: I16x32): I16x32

public expect fun vpmulhuw(a: U16x32, b: U16x32): U16x32

public expect fun vpmulhw(a: I16x32, b: I16x32): I16x32

public expect fun vpmaddwd(a: I16x32, b: I16x32): I32x16

public expect fun vpmaddubsw(a: U8x64, b: I8x64): I16x32

public expect fun vpackssdw(a: I32x16, b: I32x16): I16x32

public expect fun vpacksswb(a: I16x32, b: I16x32): I8x64

public expect fun vpackusdw(a: I32x16, b: I32x16): U16x32

public expect fun vpackuswb(a: I16x32, b: I16x32): U8x64

public expect fun vpsllw(a: I16x32, count: I16x8): I16x32

public expect fun vpsllvw(a: I16x32, b: I16x32): I16x32

public expect fun vpsllvw256(a: I16x16, b: I16x16): I16x16

public expect fun vpsllvw128(a: I16x8, b: I16x8): I16x8

public expect fun vpsrlw(a: I16x32, count: I16x8): I16x32

public expect fun vpsrlvw(a: I16x32, b: I16x32): I16x32

public expect fun vpsrlvw256(a: I16x16, b: I16x16): I16x16

public expect fun vpsrlvw128(a: I16x8, b: I16x8): I16x8

public expect fun vpsraw(a: I16x32, count: I16x8): I16x32

public expect fun vpsravw(a: I16x32, count: I16x32): I16x32

public expect fun vpsravw256(a: I16x16, count: I16x16): I16x16

public expect fun vpsravw128(a: I16x8, count: I16x8): I16x8

public expect fun vpermi2w(a: I16x32, idx: I16x32, b: I16x32): I16x32

public expect fun vpermi2w256(a: I16x16, idx: I16x16, b: I16x16): I16x16

public expect fun vpermi2w128(a: I16x8, idx: I16x8, b: I16x8): I16x8

public expect fun vpermw(a: I16x32, idx: I16x32): I16x32

public expect fun vpermw256(a: I16x16, idx: I16x16): I16x16

public expect fun vpermw128(a: I16x8, idx: I16x8): I16x8

public expect fun vpshufb(a: I8x64, b: I8x64): I8x64

public expect fun vpsadbw(a: U8x64, b: U8x64): U64x8

public expect fun vdbpsadbw(a: U8x64, b: U8x64, imm8: Int): U16x32

public expect fun vdbpsadbw256(a: U8x32, b: U8x32, imm8: Int): U16x16

public expect fun vdbpsadbw128(a: U8x16, b: U8x16, imm8: Int): U16x8

public expect fun vpmovswb128(a: I16x8, src: I8x16, mask: UByte): I8x16

public expect fun vpmovuswb128(a: U16x8, src: U8x16, mask: UByte): U8x16

public expect fun vpmovswbmem(memAddr: MutPtr, a: I16x32, mask: UInt)

public expect fun vpmovuswbmem(memAddr: MutPtr, a: I16x32, mask: UInt)

public expect fun vpavgb(a: U8x64, b: U8x64): U8x64

public expect fun vpavgw(a: U16x32, b: U16x32): U16x32
