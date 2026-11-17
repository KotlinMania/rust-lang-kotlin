// port-lint: source x86/avx512cd.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.corearch.simd.I32x16
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I32x8
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I64x4
import io.github.kotlinmania.rustlang.corearch.simd.I64x8

// should be vpbroadcastmw2d

/**
 * Broadcast the low 16-bits from input mask k to all 32-bit elements of dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_broadcastmw_epi32&expand=553)
 */
public fun mm512BroadcastmwEpi32(k: Mmask16): M512i = mm512Set1Epi32(k.toInt())

// should be vpbroadcastmw2d

/**
 * Broadcast the low 16-bits from input mask k to all 32-bit elements of dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_broadcastmw_epi32&expand=552)
 */
public fun mm256BroadcastmwEpi32(k: Mmask16): M256i = mm256Set1Epi32(k.toInt())

// should be vpbroadcastmw2d

/**
 * Broadcast the low 16-bits from input mask k to all 32-bit elements of dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_broadcastmw_epi32&expand=551)
 */
public fun mmBroadcastmwEpi32(k: Mmask16): M128i = mmSet1Epi32(k.toInt())

// should be vpbroadcastmb2q

/**
 * Broadcast the low 8-bits from input mask k to all 64-bit elements of dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_broadcastmb_epi64&expand=550)
 */
public fun mm512BroadcastmbEpi64(k: Mmask8): M512i = mm512Set1Epi64(k.toLong())

// should be vpbroadcastmb2q

/**
 * Broadcast the low 8-bits from input mask k to all 64-bit elements of dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_broadcastmb_epi64&expand=549)
 */
public fun mm256BroadcastmbEpi64(k: Mmask8): M256i = mm256Set1Epi64x(k.toLong())

// should be vpbroadcastmb2q

/**
 * Broadcast the low 8-bits from input mask k to all 64-bit elements of dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_broadcastmb_epi64&expand=548)
 */
public fun mmBroadcastmbEpi64(k: Mmask8): M128i = mmSet1Epi64x(k.toLong())

/**
 * Test each 32-bit element of a for equality with all other elements in a closer to the least significant bit. Each element's comparison forms a zero extended bit vector in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_conflict_epi32&expand=1248)
 */
public fun mm512ConflictEpi32(a: M512i): M512i = transmute(vpconflictd(a.asI32x16()))

/**
 * Test each 32-bit element of a for equality with all other elements in a closer to the least significant bit using writemask k (elements are copied from src when the corresponding mask bit is not set). Each element's comparison forms a zero extended bit vector in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_conflict_epi32&expand=1249)
 */
public fun mm512MaskConflictEpi32(src: M512i, k: Mmask16, a: M512i): M512i =
    run {
        val conflict = mm512ConflictEpi32(a).asI32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = conflict; val _simdArg2 = src.asI32x16(); _simdArg1 })
    }

/**
 * Test each 32-bit element of a for equality with all other elements in a closer to the least significant bit using zeromask k (elements are zeroed out when the corresponding mask bit is not set). Each element's comparison forms a zero extended bit vector in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_conflict_epi32&expand=1250)
 */
public fun mm512MaskzConflictEpi32(k: Mmask16, a: M512i): M512i =
    run {
        val conflict = mm512ConflictEpi32(a).asI32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = conflict; val _simdArg2 = i32x16.ZERO; _simdArg1 })
    }

/**
 * Test each 32-bit element of a for equality with all other elements in a closer to the least significant bit. Each element's comparison forms a zero extended bit vector in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_conflict_epi32&expand=1245)
 */
public fun mm256ConflictEpi32(a: M256i): M256i = transmute(vpconflictd256(a.asI32x8()))

/**
 * Test each 32-bit element of a for equality with all other elements in a closer to the least significant bit using writemask k (elements are copied from src when the corresponding mask bit is not set). Each element's comparison forms a zero extended bit vector in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_conflict_epi32&expand=1246)
 */
public fun mm256MaskConflictEpi32(src: M256i, k: Mmask8, a: M256i): M256i =
    run {
        val conflict = mm256ConflictEpi32(a).asI32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = conflict; val _simdArg2 = src.asI32x8(); _simdArg1 })
    }

/**
 * Test each 32-bit element of a for equality with all other elements in a closer to the least significant bit using zeromask k (elements are zeroed out when the corresponding mask bit is not set). Each element's comparison forms a zero extended bit vector in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_conflict_epi32&expand=1247)
 */
public fun mm256MaskzConflictEpi32(k: Mmask8, a: M256i): M256i =
    run {
        val conflict = mm256ConflictEpi32(a).asI32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = conflict; val _simdArg2 = i32x8.ZERO; _simdArg1 })
    }

/**
 * Test each 32-bit element of a for equality with all other elements in a closer to the least significant bit. Each element's comparison forms a zero extended bit vector in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_conflict_epi32&expand=1242)
 */
public fun mmConflictEpi32(a: M128i): M128i = transmute(vpconflictd128(a.asI32x4()))

/**
 * Test each 32-bit element of a for equality with all other elements in a closer to the least significant bit using writemask k (elements are copied from src when the corresponding mask bit is not set). Each element's comparison forms a zero extended bit vector in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_conflict_epi32&expand=1243)
 */
public fun mmMaskConflictEpi32(src: M128i, k: Mmask8, a: M128i): M128i =
    run {
        val conflict = mmConflictEpi32(a).asI32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = conflict; val _simdArg2 = src.asI32x4(); _simdArg1 })
    }

/**
 * Test each 32-bit element of a for equality with all other elements in a closer to the least significant bit using zeromask k (elements are zeroed out when the corresponding mask bit is not set). Each element's comparison forms a zero extended bit vector in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_conflict_epi32&expand=1244)
 */
public fun mmMaskzConflictEpi32(k: Mmask8, a: M128i): M128i =
    run {
        val conflict = mmConflictEpi32(a).asI32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = conflict; val _simdArg2 = i32x4.ZERO; _simdArg1 })
    }

/**
 * Test each 64-bit element of a for equality with all other elements in a closer to the least significant bit. Each element's comparison forms a zero extended bit vector in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_conflict_epi64&expand=1257)
 */
public fun mm512ConflictEpi64(a: M512i): M512i = transmute(vpconflictq(a.asI64x8()))

/**
 * Test each 64-bit element of a for equality with all other elements in a closer to the least significant bit using writemask k (elements are copied from src when the corresponding mask bit is not set). Each element's comparison forms a zero extended bit vector in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_conflict_epi64&expand=1258)
 */
public fun mm512MaskConflictEpi64(src: M512i, k: Mmask8, a: M512i): M512i =
    run {
        val conflict = mm512ConflictEpi64(a).asI64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = conflict; val _simdArg2 = src.asI64x8(); _simdArg1 })
    }

/**
 * Test each 64-bit element of a for equality with all other elements in a closer to the least significant bit using zeromask k (elements are zeroed out when the corresponding mask bit is not set). Each element's comparison forms a zero extended bit vector in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_conflict_epi64&expand=1259)
 */
public fun mm512MaskzConflictEpi64(k: Mmask8, a: M512i): M512i =
    run {
        val conflict = mm512ConflictEpi64(a).asI64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = conflict; val _simdArg2 = i64x8.ZERO; _simdArg1 })
    }

/**
 * Test each 64-bit element of a for equality with all other elements in a closer to the least significant bit. Each element's comparison forms a zero extended bit vector in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_conflict_epi64&expand=1254)
 */
public fun mm256ConflictEpi64(a: M256i): M256i = transmute(vpconflictq256(a.asI64x4()))

/**
 * Test each 64-bit element of a for equality with all other elements in a closer to the least significant bit using writemask k (elements are copied from src when the corresponding mask bit is not set). Each element's comparison forms a zero extended bit vector in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_conflict_epi64&expand=1255)
 */
public fun mm256MaskConflictEpi64(src: M256i, k: Mmask8, a: M256i): M256i =
    run {
        val conflict = mm256ConflictEpi64(a).asI64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = conflict; val _simdArg2 = src.asI64x4(); _simdArg1 })
    }

/**
 * Test each 64-bit element of a for equality with all other elements in a closer to the least significant bit using zeromask k (elements are zeroed out when the corresponding mask bit is not set). Each element's comparison forms a zero extended bit vector in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_conflict_epi64&expand=1256)
 */
public fun mm256MaskzConflictEpi64(k: Mmask8, a: M256i): M256i =
    run {
        val conflict = mm256ConflictEpi64(a).asI64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = conflict; val _simdArg2 = i64x4.ZERO; _simdArg1 })
    }

/**
 * Test each 64-bit element of a for equality with all other elements in a closer to the least significant bit. Each element's comparison forms a zero extended bit vector in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_conflict_epi64&expand=1251)
 */
public fun mmConflictEpi64(a: M128i): M128i = transmute(vpconflictq128(a.asI64x2()))

/**
 * Test each 64-bit element of a for equality with all other elements in a closer to the least significant bit using writemask k (elements are copied from src when the corresponding mask bit is not set). Each element's comparison forms a zero extended bit vector in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_conflict_epi64&expand=1252)
 */
public fun mmMaskConflictEpi64(src: M128i, k: Mmask8, a: M128i): M128i =
    run {
        val conflict = mmConflictEpi64(a).asI64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = conflict; val _simdArg2 = src.asI64x2(); _simdArg1 })
    }

/**
 * Test each 64-bit element of a for equality with all other elements in a closer to the least significant bit using zeromask k (elements are zeroed out when the corresponding mask bit is not set). Each element's comparison forms a zero extended bit vector in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_conflict_epi64&expand=1253)
 */
public fun mmMaskzConflictEpi64(k: Mmask8, a: M128i): M128i =
    run {
        val conflict = mmConflictEpi64(a).asI64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = conflict; val _simdArg2 = i64x2.ZERO; _simdArg1 })
    }

/**
 * Counts the number of leading zero bits in each packed 32-bit integer in a, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_lzcnt_epi32&expand=3491)
 */
public fun mm512LzcntEpi32(a: M512i): M512i = transmute(run { val _simdArg0 = a.asI32x16(); _simdArg0 })

/**
 * Counts the number of leading zero bits in each packed 32-bit integer in a, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_lzcnt_epi32&expand=3492)
 */
public fun mm512MaskLzcntEpi32(src: M512i, k: Mmask16, a: M512i): M512i =
    run {
        val zerocount = mm512LzcntEpi32(a).asI32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = zerocount; val _simdArg2 = src.asI32x16(); _simdArg1 })
    }

/**
 * Counts the number of leading zero bits in each packed 32-bit integer in a, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_lzcnt_epi32&expand=3493)
 */
public fun mm512MaskzLzcntEpi32(k: Mmask16, a: M512i): M512i =
    run {
        val zerocount = mm512LzcntEpi32(a).asI32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = zerocount; val _simdArg2 = i32x16.ZERO; _simdArg1 })
    }

/**
 * Counts the number of leading zero bits in each packed 32-bit integer in a, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_lzcnt_epi32&expand=3488)
 */
public fun mm256LzcntEpi32(a: M256i): M256i = transmute(run { val _simdArg0 = a.asI32x8(); _simdArg0 })

/**
 * Counts the number of leading zero bits in each packed 32-bit integer in a, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_lzcnt_epi32&expand=3489)
 */
public fun mm256MaskLzcntEpi32(src: M256i, k: Mmask8, a: M256i): M256i =
    run {
        val zerocount = mm256LzcntEpi32(a).asI32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = zerocount; val _simdArg2 = src.asI32x8(); _simdArg1 })
    }

/**
 * Counts the number of leading zero bits in each packed 32-bit integer in a, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_lzcnt_epi32&expand=3490)
 */
public fun mm256MaskzLzcntEpi32(k: Mmask8, a: M256i): M256i =
    run {
        val zerocount = mm256LzcntEpi32(a).asI32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = zerocount; val _simdArg2 = i32x8.ZERO; _simdArg1 })
    }

/**
 * Counts the number of leading zero bits in each packed 32-bit integer in a, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_lzcnt_epi32&expand=3485)
 */
public fun mmLzcntEpi32(a: M128i): M128i = transmute(run { val _simdArg0 = a.asI32x4(); _simdArg0 })

/**
 * Counts the number of leading zero bits in each packed 32-bit integer in a, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_lzcnt_epi32&expand=3486)
 */
public fun mmMaskLzcntEpi32(src: M128i, k: Mmask8, a: M128i): M128i =
    run {
        val zerocount = mmLzcntEpi32(a).asI32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = zerocount; val _simdArg2 = src.asI32x4(); _simdArg1 })
    }

/**
 * Counts the number of leading zero bits in each packed 32-bit integer in a, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_lzcnt_epi32&expand=3487)
 */
public fun mmMaskzLzcntEpi32(k: Mmask8, a: M128i): M128i =
    run {
        val zerocount = mmLzcntEpi32(a).asI32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = zerocount; val _simdArg2 = i32x4.ZERO; _simdArg1 })
    }

/**
 * Counts the number of leading zero bits in each packed 64-bit integer in a, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_lzcnt_epi64&expand=3500)
 */
public fun mm512LzcntEpi64(a: M512i): M512i = transmute(run { val _simdArg0 = a.asI64x8(); _simdArg0 })

/**
 * Counts the number of leading zero bits in each packed 64-bit integer in a, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_mask_lzcnt_epi64&expand=3501)
 */
public fun mm512MaskLzcntEpi64(src: M512i, k: Mmask8, a: M512i): M512i =
    run {
        val zerocount = mm512LzcntEpi64(a).asI64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = zerocount; val _simdArg2 = src.asI64x8(); _simdArg1 })
    }

/**
 * Counts the number of leading zero bits in each packed 64-bit integer in a, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm512_maskz_lzcnt_epi64&expand=3502)
 */
public fun mm512MaskzLzcntEpi64(k: Mmask8, a: M512i): M512i =
    run {
        val zerocount = mm512LzcntEpi64(a).asI64x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = zerocount; val _simdArg2 = i64x8.ZERO; _simdArg1 })
    }

/**
 * Counts the number of leading zero bits in each packed 64-bit integer in a, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_lzcnt_epi64&expand=3497)
 */
public fun mm256LzcntEpi64(a: M256i): M256i = transmute(run { val _simdArg0 = a.asI64x4(); _simdArg0 })

/**
 * Counts the number of leading zero bits in each packed 64-bit integer in a, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_mask_lzcnt_epi64&expand=3498)
 */
public fun mm256MaskLzcntEpi64(src: M256i, k: Mmask8, a: M256i): M256i =
    run {
        val zerocount = mm256LzcntEpi64(a).asI64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = zerocount; val _simdArg2 = src.asI64x4(); _simdArg1 })
    }

/**
 * Counts the number of leading zero bits in each packed 64-bit integer in a, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm256_maskz_lzcnt_epi64&expand=3499)
 */
public fun mm256MaskzLzcntEpi64(k: Mmask8, a: M256i): M256i =
    run {
        val zerocount = mm256LzcntEpi64(a).asI64x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = zerocount; val _simdArg2 = i64x4.ZERO; _simdArg1 })
    }

/**
 * Counts the number of leading zero bits in each packed 64-bit integer in a, and store the results in dst.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_lzcnt_epi64&expand=3494)
 */
public fun mmLzcntEpi64(a: M128i): M128i = transmute(run { val _simdArg0 = a.asI64x2(); _simdArg0 })

/**
 * Counts the number of leading zero bits in each packed 64-bit integer in a, and store the results in dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_mask_lzcnt_epi64&expand=3495)
 */
public fun mmMaskLzcntEpi64(src: M128i, k: Mmask8, a: M128i): M128i =
    run {
        val zerocount = mmLzcntEpi64(a).asI64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = zerocount; val _simdArg2 = src.asI64x2(); _simdArg1 })
    }

/**
 * Counts the number of leading zero bits in each packed 64-bit integer in a, and store the results in dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_maskz_lzcnt_epi64&expand=3496)
 */
public fun mmMaskzLzcntEpi64(k: Mmask8, a: M128i): M128i =
    run {
        val zerocount = mmLzcntEpi64(a).asI64x2()
        transmute(run { val _simdArg0 = k; val _simdArg1 = zerocount; val _simdArg2 = i64x2.ZERO; _simdArg1 })
    }

public expect fun vpconflictd(a: I32x16): I32x16

public expect fun vpconflictd256(a: I32x8): I32x8

public expect fun vpconflictd128(a: I32x4): I32x4

public expect fun vpconflictq(a: I64x8): I64x8

public expect fun vpconflictq256(a: I64x4): I64x4

public expect fun vpconflictq128(a: I64x2): I64x2
