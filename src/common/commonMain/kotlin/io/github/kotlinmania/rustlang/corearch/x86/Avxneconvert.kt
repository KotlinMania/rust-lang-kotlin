// port-lint: source x86/avxneconvert.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.x86.M128
import io.github.kotlinmania.rustlang.x86.M128bh
import io.github.kotlinmania.rustlang.x86.M256

/**
 * Convert scalar BF16 (16-bit) floating point element stored at memory locations starting at location
 * a to single precision (32-bit) floating-point, broadcast it to packed single precision (32-bit)
 * floating-point elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_bcstnebf16_ps)
 */
public fun mmBcstnebf16Ps(a: ConstPtr): M128 = bcstnebf162ps128(a)

/**
 * Convert scalar BF16 (16-bit) floating point element stored at memory locations starting at location
 * a to single precision (32-bit) floating-point, broadcast it to packed single precision (32-bit) floating-point
 * elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_bcstnebf16_ps)
 */
public fun mm256Bcstnebf16Ps(a: ConstPtr): M256 = bcstnebf162ps256(a)

/**
 * Convert scalar half-precision (16-bit) floating-point element stored at memory locations starting
 * at location a to a single-precision (32-bit) floating-point, broadcast it to packed single-precision
 * (32-bit) floating-point elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_bcstnesh_ps)
 */
public fun mmBcstneshPs(a: ConstPtr): M128 = bcstnesh2ps128(a)

/**
 * Convert scalar half-precision (16-bit) floating-point element stored at memory locations starting
 * at location a to a single-precision (32-bit) floating-point, broadcast it to packed single-precision
 * (32-bit) floating-point elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_bcstnesh_ps)
 */
public fun mm256BcstneshPs(a: ConstPtr): M256 = bcstnesh2ps256(a)

/**
 * Convert packed BF16 (16-bit) floating-point even-indexed elements stored at memory locations starting at
 * location a to single precision (32-bit) floating-point elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtneebf16_ps)
 */
public fun mmCvtneebf16Ps(a: ConstPtr): M128 = transmute(cvtneebf162ps128(a))

/**
 * Convert packed BF16 (16-bit) floating-point even-indexed elements stored at memory locations starting at
 * location a to single precision (32-bit) floating-point elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvtneebf16_ps)
 */
public fun mm256Cvtneebf16Ps(a: ConstPtr): M256 = transmute(cvtneebf162ps256(a))

/**
 * Convert packed half-precision (16-bit) floating-point even-indexed elements stored at memory locations starting at
 * location a to single precision (32-bit) floating-point elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtneeph_ps)
 */
public fun mmCvtneephPs(a: ConstPtr): M128 = transmute(cvtneeph2ps128(a))

/**
 * Convert packed half-precision (16-bit) floating-point even-indexed elements stored at memory locations starting at
 * location a to single precision (32-bit) floating-point elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvtneeph_ps)
 */
public fun mm256CvtneephPs(a: ConstPtr): M256 = transmute(cvtneeph2ps256(a))

/**
 * Convert packed BF16 (16-bit) floating-point odd-indexed elements stored at memory locations starting at
 * location a to single precision (32-bit) floating-point elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtneobf16_ps)
 */
public fun mmCvtneobf16Ps(a: ConstPtr): M128 = transmute(cvtneobf162ps128(a))

/**
 * Convert packed BF16 (16-bit) floating-point odd-indexed elements stored at memory locations starting at
 * location a to single precision (32-bit) floating-point elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvtneobf16_ps)
 */
public fun mm256Cvtneobf16Ps(a: ConstPtr): M256 = transmute(cvtneobf162ps256(a))

/**
 * Convert packed half-precision (16-bit) floating-point odd-indexed elements stored at memory locations starting at
 * location a to single precision (32-bit) floating-point elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtneoph_ps)
 */
public fun mmCvtneophPs(a: ConstPtr): M128 = transmute(cvtneoph2ps128(a))

/**
 * Convert packed half-precision (16-bit) floating-point odd-indexed elements stored at memory locations starting at
 * location a to single precision (32-bit) floating-point elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvtneoph_ps)
 */
public fun mm256CvtneophPs(a: ConstPtr): M256 = transmute(cvtneoph2ps256(a))

/**
 * Convert packed single precision (32-bit) floating-point elements in a to packed BF16 (16-bit) floating-point
 * elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm_cvtneps_avx_pbh)
 */
public fun mmCvtnepsAvxPbh(a: M128): M128bh = vcvtneps2bf16128(a)

/**
 * Convert packed single precision (32-bit) floating-point elements in a to packed BF16 (16-bit) floating-point
 * elements, and store the results in dst.
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_mm256_cvtneps_avx_pbh)
 */
public fun mm256CvtnepsAvxPbh(a: M256): M128bh = vcvtneps2bf16256(a)

public expect fun bcstnebf162ps128(a: ConstPtr): M128

public expect fun bcstnebf162ps256(a: ConstPtr): M256

public expect fun bcstnesh2ps128(a: ConstPtr): M128

public expect fun bcstnesh2ps256(a: ConstPtr): M256

public expect fun cvtneebf162ps128(a: ConstPtr): M128

public expect fun cvtneebf162ps256(a: ConstPtr): M256

public expect fun cvtneeph2ps128(a: ConstPtr): M128

public expect fun cvtneeph2ps256(a: ConstPtr): M256

public expect fun cvtneobf162ps128(a: ConstPtr): M128

public expect fun cvtneobf162ps256(a: ConstPtr): M256

public expect fun cvtneoph2ps128(a: ConstPtr): M128

public expect fun cvtneoph2ps256(a: ConstPtr): M256

public expect fun vcvtneps2bf16128(a: M128): M128bh

public expect fun vcvtneps2bf16256(a: M256): M128bh
