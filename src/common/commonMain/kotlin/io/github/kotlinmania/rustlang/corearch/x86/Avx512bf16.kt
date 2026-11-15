// port-lint: source x86/avx512bf16.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.simd.F32x16
import io.github.kotlinmania.rustlang.simd.F32x4
import io.github.kotlinmania.rustlang.simd.F32x8
import io.github.kotlinmania.rustlang.simd.I16x16
import io.github.kotlinmania.rustlang.simd.I16x32
import io.github.kotlinmania.rustlang.simd.I16x8
import io.github.kotlinmania.rustlang.x86.Bf16
import io.github.kotlinmania.rustlang.x86.M128
import io.github.kotlinmania.rustlang.x86.M128bh
import io.github.kotlinmania.rustlang.x86.M256
import io.github.kotlinmania.rustlang.x86.M256bh
import io.github.kotlinmania.rustlang.x86.M512
import io.github.kotlinmania.rustlang.x86.M512bh
import io.github.kotlinmania.rustlang.x86.Mmask16
import io.github.kotlinmania.rustlang.x86.Mmask32
import io.github.kotlinmania.rustlang.x86.Mmask8
import io.github.kotlinmania.rustlang.x86.mm256Castsi256Ps
import io.github.kotlinmania.rustlang.x86.mm256Cvtepi16Epi32
import io.github.kotlinmania.rustlang.x86.mm256SlliEpi32
import io.github.kotlinmania.rustlang.x86.mm512Castsi512Ps
import io.github.kotlinmania.rustlang.x86.mm512Cvtepi16Epi32
import io.github.kotlinmania.rustlang.x86.mm512SlliEpi32
import io.github.kotlinmania.rustlang.x86.mmCastsi128Ps
import io.github.kotlinmania.rustlang.x86.mmCvtepi16Epi32
import io.github.kotlinmania.rustlang.x86.mmSet1Ps
import io.github.kotlinmania.rustlang.x86.mmSetSs
import io.github.kotlinmania.rustlang.x86.mmSlliEpi32

// [AVX512BF16 intrinsics].
//
// [AVX512BF16 intrinsics]: https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769&avx512techs=AVX512_BF16

public expect fun cvtne2ps2bf16(a: F32x4, b: F32x4): I16x8

public expect fun cvtne2ps2bf16256(a: F32x8, b: F32x8): I16x16

public expect fun cvtne2ps2bf16512(a: F32x16, b: F32x16): I16x32

public expect fun cvtneps2bf16128(a: F32x4, src: I16x8, k: Mmask8): I16x8

public expect fun cvtneps2bf16256(a: F32x8): I16x8

public expect fun cvtneps2bf16512(a: F32x16): I16x16

public expect fun dpbf16ps(a: F32x4, b: I16x8, c: I16x8): F32x4

public expect fun dpbf16ps256(a: F32x8, b: I16x16, c: I16x16): F32x8

public expect fun dpbf16ps512(a: F32x16, b: I16x32, c: I16x32): F32x16

/**
 * Convert packed single-precision (32-bit) floating-point elements in two 128-bit vectors
 * a and b to packed BF16 (16-bit) floating-point elements, and store the results in a
 * 128-bit wide vector.
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769,1651&avx512techs=AVX512_BF16&text=_mm_cvtne2ps_pbh)
 */
public fun mmCvtne2psPbh(a: M128, b: M128): M128bh = transmute(cvtne2ps2bf16(a.asF32x4(), b.asF32x4()))

/**
 * Convert packed single-precision (32-bit) floating-point elements in two vectors
 * a and b to packed BF16 (16-bit) floating-point elements, and store the results
 * in single vector dst using writemask k (elements are copied from src when the
 * corresponding mask bit is not set).
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769,1651&avx512techs=AVX512_BF16&text=_mm_mask_cvtne2ps_pbh)
 */
public fun mmMaskCvtne2psPbh(src: M128bh, k: Mmask8, a: M128, b: M128): M128bh =
    run {
        val cvt = mmCvtne2psPbh(a, b).asU16x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = cvt; val _simdArg2 = src.asU16x8(); _simdArg1 })
    }

/**
 * Convert packed single-precision (32-bit) floating-point elements in two vectors
 * a and b to packed BF16 (16-bit) floating-point elements, and store the results
 * in single vector dst using zeromask k (elements are zeroed out when the corresponding
 * mask bit is not set).
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769,1651&avx512techs=AVX512_BF16&text=_mm_maskz_cvtne2ps_pbh)
 */
public fun mmMaskzCvtne2psPbh(k: Mmask8, a: M128, b: M128): M128bh =
    run {
        val cvt = mmCvtne2psPbh(a, b).asU16x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = cvt; val _simdArg2 = u16x8.ZERO; _simdArg1 })
    }

/**
 * Convert packed single-precision (32-bit) floating-point elements in two 256-bit vectors
 * a and b to packed BF16 (16-bit) floating-point elements, and store the results in a
 * 256-bit wide vector.
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769,1651,1654&avx512techs=AVX512_BF16&text=_mm256_cvtne2ps_pbh)
 */
public fun mm256Cvtne2psPbh(a: M256, b: M256): M256bh = transmute(cvtne2ps2bf16256(a.asF32x8(), b.asF32x8()))

/**
 * Convert packed single-precision (32-bit) floating-point elements in two vectors a and b
 * to packed BF16 (16-bit) floating-point elements and store the results in single vector
 * dst using writemask k (elements are copied from src when the corresponding mask bit is not set).
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769,1651,1654&avx512techs=AVX512_BF16&text=_mm256_mask_cvtne2ps_pbh)
 */
public fun mm256MaskCvtne2psPbh(src: M256bh, k: Mmask16, a: M256, b: M256): M256bh =
    run {
        val cvt = mm256Cvtne2psPbh(a, b).asU16x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = cvt; val _simdArg2 = src.asU16x16(); _simdArg1 })
    }

/**
 * Convert packed single-precision (32-bit) floating-point elements in two vectors a and b
 * to packed BF16 (16-bit) floating-point elements, and store the results in single vector
 * dst using zeromask k (elements are zeroed out when the corresponding mask bit is not set).
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769,1651,1654&avx512techs=AVX512_BF16&text=_mm256_maskz_cvtne2ps_pbh)
 */
public fun mm256MaskzCvtne2psPbh(k: Mmask16, a: M256, b: M256): M256bh =
    run {
        val cvt = mm256Cvtne2psPbh(a, b).asU16x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = cvt; val _simdArg2 = u16x16.ZERO; _simdArg1 })
    }

/**
 * Convert packed single-precision (32-bit) floating-point elements in two 512-bit vectors
 * a and b to packed BF16 (16-bit) floating-point elements, and store the results in a
 * 512-bit wide vector.
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769,1651,1654,1657&avx512techs=AVX512_BF16&text=_mm512_cvtne2ps_pbh)
 */
public fun mm512Cvtne2psPbh(a: M512, b: M512): M512bh = transmute(cvtne2ps2bf16512(a.asF32x16(), b.asF32x16()))

/**
 * Convert packed single-precision (32-bit) floating-point elements in two vectors
 * a and b to packed BF16 (16-bit) floating-point elements, and store the results
 * in single vector dst using writemask k (elements are copied from src when the
 * corresponding mask bit is not set).
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769,1651,1654,1657&avx512techs=AVX512_BF16&text=_mm512_mask_cvtne2ps_pbh)
 */
public fun mm512MaskCvtne2psPbh(src: M512bh, k: Mmask32, a: M512, b: M512): M512bh =
    run {
        val cvt = mm512Cvtne2psPbh(a, b).asU16x32()
        transmute(run { val _simdArg0 = k; val _simdArg1 = cvt; val _simdArg2 = src.asU16x32(); _simdArg1 })
    }

/**
 * Convert packed single-precision (32-bit) floating-point elements in two vectors
 * a and b to packed BF16 (16-bit) floating-point elements, and store the results
 * in single vector dst using zeromask k (elements are zeroed out when the corresponding
 * mask bit is not set).
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769,1651,1654,1657&avx512techs=AVX512_BF16&text=_mm512_maskz_cvtne2ps_pbh)
 */
public fun mm512MaskzCvtne2psPbh(k: Mmask32, a: M512, b: M512): M512bh =
    run {
        val cvt = mm512Cvtne2psPbh(a, b).asU16x32()
        transmute(run { val _simdArg0 = k; val _simdArg1 = cvt; val _simdArg2 = u16x32.ZERO; _simdArg1 })
    }

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed BF16 (16-bit)
 * floating-point elements, and store the results in dst.
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769,1651,1654,1657,1660&avx512techs=AVX512_BF16&text=_mm256_cvtneps_pbh)
 */
public fun mm256CvtnepsPbh(a: M256): M128bh = transmute(cvtneps2bf16256(a.asF32x8()))

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed BF16 (16-bit)
 * floating-point elements, and store the results in dst using writemask k
 * (elements are copied from src when the corresponding mask bit is not set).
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769,1651,1654,1657,1660&avx512techs=AVX512_BF16&text=_mm256_mask_cvtneps_pbh)
 */
public fun mm256MaskCvtnepsPbh(src: M128bh, k: Mmask8, a: M256): M128bh =
    run {
        val cvt = mm256CvtnepsPbh(a).asU16x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = cvt; val _simdArg2 = src.asU16x8(); _simdArg1 })
    }

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed BF16 (16-bit)
 * floating-point elements, and store the results in dst using zeromask k
 * (elements are zeroed out when the corresponding mask bit is not set).
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769,1651,1654,1657,1660&avx512techs=AVX512_BF16&text=_mm256_maskz_cvtneps_pbh)
 */
public fun mm256MaskzCvtnepsPbh(k: Mmask8, a: M256): M128bh =
    run {
        val cvt = mm256CvtnepsPbh(a).asU16x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = cvt; val _simdArg2 = u16x8.ZERO; _simdArg1 })
    }

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed BF16 (16-bit)
 * floating-point elements, and store the results in dst.
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769,1651,1654,1657,1660&avx512techs=AVX512_BF16&text=_mm512_cvtneps_pbh)
 */
public fun mm512CvtnepsPbh(a: M512): M256bh = transmute(cvtneps2bf16512(a.asF32x16()))

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed BF16 (16-bit)
 * floating-point elements, and store the results in dst using writemask k
 * (elements are copied from src when the corresponding mask bit is not set).
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769,1651,1654,1657,1660&avx512techs=AVX512_BF16&text=_mm512_mask_cvtneps_pbh)
 */
public fun mm512MaskCvtnepsPbh(src: M256bh, k: Mmask16, a: M512): M256bh =
    run {
        val cvt = mm512CvtnepsPbh(a).asU16x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = cvt; val _simdArg2 = src.asU16x16(); _simdArg1 })
    }

/**
 * Convert packed single-precision (32-bit) floating-point elements in a to packed BF16 (16-bit)
 * floating-point elements, and store the results in dst using zeromask k
 * (elements are zeroed out when the corresponding mask bit is not set).
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769,1651,1654,1657,1660&avx512techs=AVX512_BF16&text=_mm512_maskz_cvtneps_pbh)
 */
public fun mm512MaskzCvtnepsPbh(k: Mmask16, a: M512): M256bh =
    run {
        val cvt = mm512CvtnepsPbh(a).asU16x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = cvt; val _simdArg2 = u16x16.ZERO; _simdArg1 })
    }

/**
 * Compute dot-product of BF16 (16-bit) floating-point pairs in a and b,
 * accumulating the intermediate single-precision (32-bit) floating-point elements
 * with elements in src, and store the results in dst.
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769,1651,1654,1657,1660&avx512techs=AVX512_BF16&text=_mm_dpbf16_ps)
 */
public fun mmDpbf16Ps(src: M128, a: M128bh, b: M128bh): M128 = transmute(dpbf16ps(src.asF32x4(), a.asI16x8(), b.asI16x8()))

/**
 * Compute dot-product of BF16 (16-bit) floating-point pairs in a and b,
 * accumulating the intermediate single-precision (32-bit) floating-point elements
 * with elements in src, and store the results in dst using writemask k
 * (elements are copied from src when the corresponding mask bit is not set).
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769,1651,1654,1657,1660&avx512techs=AVX512_BF16&text=_mm_mask_dpbf16_ps)
 */
public fun mmMaskDpbf16Ps(src: M128, k: Mmask8, a: M128bh, b: M128bh): M128 =
    run {
        val rst = mmDpbf16Ps(src, a, b).asF32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = rst; val _simdArg2 = src.asF32x4(); _simdArg1 })
    }

/**
 * Compute dot-product of BF16 (16-bit) floating-point pairs in a and b,
 * accumulating the intermediate single-precision (32-bit) floating-point elements
 * with elements in src, and store the results in dst using zeromask k
 * (elements are zeroed out when the corresponding mask bit is not set).
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769,1651,1654,1657,1660&avx512techs=AVX512_BF16&text=_mm_maskz_dpbf16_ps)
 */
public fun mmMaskzDpbf16Ps(k: Mmask8, src: M128, a: M128bh, b: M128bh): M128 =
    run {
        val rst = mmDpbf16Ps(src, a, b).asF32x4()
        val zero = mmSet1Ps(0.0f).asF32x4()
        transmute(run { val _simdArg0 = k; val _simdArg1 = rst; val _simdArg2 = zero; _simdArg1 })
    }

/**
 * Compute dot-product of BF16 (16-bit) floating-point pairs in a and b,
 * accumulating the intermediate single-precision (32-bit) floating-point elements
 * with elements in src, and store the results in dst.
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769,1651,1654,1657,1660&avx512techs=AVX512_BF16&text=_mm256_dpbf16_ps)
 */
public fun mm256Dpbf16Ps(src: M256, a: M256bh, b: M256bh): M256 = transmute(dpbf16ps256(src.asF32x8(), a.asI16x16(), b.asI16x16()))

/**
 * Compute dot-product of BF16 (16-bit) floating-point pairs in a and b,
 * accumulating the intermediate single-precision (32-bit) floating-point elements
 * with elements in src, and store the results in dst using writemask k
 * (elements are copied from src when the corresponding mask bit is not set).
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769,1651,1654,1657,1660&avx512techs=AVX512_BF16&text=_mm256_mask_dpbf16_ps)
 */
public fun mm256MaskDpbf16Ps(src: M256, k: Mmask8, a: M256bh, b: M256bh): M256 =
    run {
        val rst = mm256Dpbf16Ps(src, a, b).asF32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = rst; val _simdArg2 = src.asF32x8(); _simdArg1 })
    }

/**
 * Compute dot-product of BF16 (16-bit) floating-point pairs in a and b,
 * accumulating the intermediate single-precision (32-bit) floating-point elements
 * with elements in src, and store the results in dst using zeromask k
 * (elements are zeroed out when the corresponding mask bit is not set).
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769,1651,1654,1657,1660&avx512techs=AVX512_BF16&text=_mm256_maskz_dpbf16_ps)
 */
public fun mm256MaskzDpbf16Ps(k: Mmask8, src: M256, a: M256bh, b: M256bh): M256 =
    run {
        val rst = mm256Dpbf16Ps(src, a, b).asF32x8()
        transmute(run { val _simdArg0 = k; val _simdArg1 = rst; val _simdArg2 = f32x8.ZERO; _simdArg1 })
    }

/**
 * Compute dot-product of BF16 (16-bit) floating-point pairs in a and b,
 * accumulating the intermediate single-precision (32-bit) floating-point elements
 * with elements in src, and store the results in dst.Compute dot-product of BF16 (16-bit)
 * floating-point pairs in a and b, accumulating the intermediate single-precision (32-bit)
 * floating-point elements with elements in src, and store the results in dst.
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769,1651,1654,1657,1660&avx512techs=AVX512_BF16&text=_mm512_dpbf16_ps)
 */
public fun mm512Dpbf16Ps(src: M512, a: M512bh, b: M512bh): M512 = transmute(dpbf16ps512(src.asF32x16(), a.asI16x32(), b.asI16x32()))

/**
 * Compute dot-product of BF16 (16-bit) floating-point pairs in a and b,
 * accumulating the intermediate single-precision (32-bit) floating-point elements
 * with elements in src, and store the results in dst using writemask k
 * (elements are copied from src when the corresponding mask bit is not set).
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769,1651,1654,1657,1660&avx512techs=AVX512_BF16&text=_mm512_mask_dpbf16_ps)
 */
public fun mm512MaskDpbf16Ps(src: M512, k: Mmask16, a: M512bh, b: M512bh): M512 =
    run {
        val rst = mm512Dpbf16Ps(src, a, b).asF32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = rst; val _simdArg2 = src.asF32x16(); _simdArg1 })
    }

/**
 * Compute dot-product of BF16 (16-bit) floating-point pairs in a and b,
 * accumulating the intermediate single-precision (32-bit) floating-point elements
 * with elements in src, and store the results in dst using zeromask k
 * (elements are zeroed out when the corresponding mask bit is not set).
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769,1651,1654,1657,1660&avx512techs=AVX512_BF16&text=_mm512_maskz_dpbf16_ps)
 */
public fun mm512MaskzDpbf16Ps(k: Mmask16, src: M512, a: M512bh, b: M512bh): M512 =
    run {
        val rst = mm512Dpbf16Ps(src, a, b).asF32x16()
        transmute(run { val _simdArg0 = k; val _simdArg1 = rst; val _simdArg2 = f32x16.ZERO; _simdArg1 })
    }

/**
 * Converts packed BF16 (16-bit) floating-point elements in a to packed single-precision (32-bit)
 * floating-point elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_cvtpbh_ps)
 */
public fun mm512CvtpbhPs(a: M256bh): M512 = mm512Castsi512Ps(mm512SlliEpi32(mm512Cvtepi16Epi32(transmute(a)), 16))

/**
 * Converts packed BF16 (16-bit) floating-point elements in a to packed single-precision (32-bit)
 * floating-point elements, and store the results in dst using writemask k (elements are copied
 * from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_mask_cvtpbh_ps)
 */
public fun mm512MaskCvtpbhPs(src: M512, k: Mmask16, a: M256bh): M512 =
    run {
        val cvt = mm512CvtpbhPs(a)
        transmute(run { val _simdArg0 = k; val _simdArg1 = cvt.asF32x16(); val _simdArg2 = src.asF32x16(); _simdArg1 })
    }

/**
 * Converts packed BF16 (16-bit) floating-point elements in a to packed single-precision (32-bit)
 * floating-point elements, and store the results in dst using zeromask k (elements are zeroed out
 * when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm512_maskz_cvtpbh_ps)
 */
public fun mm512MaskzCvtpbhPs(k: Mmask16, a: M256bh): M512 =
    run {
        val cvt = mm512CvtpbhPs(a)
        transmute(run { val _simdArg0 = k; val _simdArg1 = cvt.asF32x16(); val _simdArg2 = f32x16.ZERO; _simdArg1 })
    }

/**
 * Converts packed BF16 (16-bit) floating-point elements in a to packed single-precision (32-bit)
 * floating-point elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvtpbh_ps)
 */
public fun mm256CvtpbhPs(a: M128bh): M256 = mm256Castsi256Ps(mm256SlliEpi32(mm256Cvtepi16Epi32(transmute(a)), 16))

/**
 * Converts packed BF16 (16-bit) floating-point elements in a to packed single-precision (32-bit)
 * floating-point elements, and store the results in dst using writemask k (elements are copied
 * from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_mask_cvtpbh_ps)
 */
public fun mm256MaskCvtpbhPs(src: M256, k: Mmask8, a: M128bh): M256 =
    run {
        val cvt = mm256CvtpbhPs(a)
        transmute(run { val _simdArg0 = k; val _simdArg1 = cvt.asF32x8(); val _simdArg2 = src.asF32x8(); _simdArg1 })
    }

/**
 * Converts packed BF16 (16-bit) floating-point elements in a to packed single-precision (32-bit)
 * floating-point elements, and store the results in dst using zeromask k (elements are zeroed out
 * when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_maskz_cvtpbh_ps)
 */
public fun mm256MaskzCvtpbhPs(k: Mmask8, a: M128bh): M256 =
    run {
        val cvt = mm256CvtpbhPs(a)
        transmute(run { val _simdArg0 = k; val _simdArg1 = cvt.asF32x8(); val _simdArg2 = f32x8.ZERO; _simdArg1 })
    }

/**
 * Converts packed BF16 (16-bit) floating-point elements in a to single-precision (32-bit) floating-point
 * elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtpbh_ps)
 */
public fun mmCvtpbhPs(a: M128bh): M128 = mmCastsi128Ps(mmSlliEpi32(mmCvtepi16Epi32(transmute(a)), 16))

/**
 * Converts packed BF16 (16-bit) floating-point elements in a to single-precision (32-bit) floating-point
 * elements, and store the results in dst using writemask k (elements are copied from src when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvtpbh_ps)
 */
public fun mmMaskCvtpbhPs(src: M128, k: Mmask8, a: M128bh): M128 =
    run {
        val cvt = mmCvtpbhPs(a)
        transmute(run { val _simdArg0 = k; val _simdArg1 = cvt.asF32x4(); val _simdArg2 = src.asF32x4(); _simdArg1 })
    }

/**
 * Converts packed BF16 (16-bit) floating-point elements in a to single-precision (32-bit) floating-point
 * elements, and store the results in dst using zeromask k (elements are zeroed out when the corresponding
 * mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvtpbh_ps)
 */
public fun mmMaskzCvtpbhPs(k: Mmask8, a: M128bh): M128 =
    run {
        val cvt = mmCvtpbhPs(a)
        transmute(run { val _simdArg0 = k; val _simdArg1 = cvt.asF32x4(); val _simdArg2 = f32x4.ZERO; _simdArg1 })
    }

/**
 * Converts a single BF16 (16-bit) floating-point element in a to a single-precision (32-bit) floating-point
 * element, and store the result in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtsbh_ss)
 */
public fun mmCvtsbhSs(a: Bf16): Float = Float.fromBits(((a.toBits().toUInt()) shl 16).toInt())

/**
 * Converts packed single-precision (32-bit) floating-point elements in a to packed BF16 (16-bit)
 * floating-point elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtneps_pbh)
 */
public fun mmCvtnepsPbh(a: M128): M128bh = mmMaskCvtnepsPbh(m128bh.splat(0), (0u.inv()).toUByte(), a)

/**
 * Converts packed single-precision (32-bit) floating-point elements in a to packed BF16 (16-bit)
 * floating-point elements, and store the results in dst using writemask k (elements are copied
 * from src when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_mask_cvtneps_pbh)
 */
public fun mmMaskCvtnepsPbh(src: M128bh, k: Mmask8, a: M128): M128bh = cvtneps2bf16128(a.asF32x4(), src.asI16x8(), k).asM128bh()

/**
 * Converts packed single-precision (32-bit) floating-point elements in a to packed BF16 (16-bit)
 * floating-point elements, and store the results in dst using zeromask k (elements are zeroed out
 * when the corresponding mask bit is not set).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_maskz_cvtneps_pbh)
 */
public fun mmMaskzCvtnepsPbh(k: Mmask8, a: M128): M128bh = mmMaskCvtnepsPbh(m128bh.splat(0), k, a)

/**
 * Converts a single-precision (32-bit) floating-point element in a to a BF16 (16-bit) floating-point
 * element, and store the result in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtness_sbh)
 */
public fun mmCvtnessSbh(a: Float): Bf16 =
    run {
        val value: UShort = run { val _simdArg0 = mmCvtnepsPbh(mmSetSs(a)); val _simdArg1 = 0u; 0u }
        bf16.fromBits(value)
    }
