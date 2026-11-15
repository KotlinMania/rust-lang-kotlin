// port-lint: source x86_64/sse.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.corearch.loongarch64.lsx.M128

// `x8664` Streaming SIMD Extensions (SSE)

public expect fun cvtss2si64(a: M128): Long

public expect fun cvttss2si64(a: M128): Long

/**
 * Converts the lowest 32 bit float in the input vector to a 64 bit integer.
 *
 * The result is rounded according to the current rounding mode. If the result
 * cannot be represented as a 64 bit integer the result will be
 * `0x8000000000000000` (`Long.MIN`) or trigger an invalid operation
 * floating point exception if unmasked (see
 * [`mmSetcsr`](fn._mm_setcsr.html)).
 *
 * This corresponds to the `CVTSS2SI` instruction (with 64 bit output).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtss_si64)
 */
public fun mmCvtssSi64(a: M128): Long = cvtss2si64(a)

/**
 * Converts the lowest 32 bit float in the input vector to a 64 bit integer
 * with truncation.
 *
 * The result is rounded always using truncation (round towards zero). If the
 * result cannot be represented as a 64 bit integer the result will be
 * `0x8000000000000000` (`Long.MIN`) or an invalid operation floating
 * point exception if unmasked (see [`mmSetcsr`](fn._mm_setcsr.html)).
 *
 * This corresponds to the `CVTTSS2SI` instruction (with 64 bit output).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvttss_si64)
 */
public fun mmCvttssSi64(a: M128): Long = cvttss2si64(a)

/**
 * Converts a 64 bit integer to a 32 bit float. The result vector is the input
 * vector `a` with the lowest 32 bit float replaced by the converted integer.
 *
 * This intrinsic corresponds to the `CVTSI2SS` instruction (with 64 bit
 * input).
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_cvtsi64_ss)
 */
public fun mmCvtsi64Ss(a: M128, b: Long): M128 = run { val _simdArg0 = a; val _simdArg1 = 0; val _simdArg2 = b.toFloat(); _simdArg0 }
