// port-lint: source x86_64/avx512bw.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.corearch.x86.Mmask64

/**
 * Convert 64-bit mask a into an integer value, and store the result in dst.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_cvtmask64_u64)
 */
public fun cvtmask64U64(a: Mmask64): ULong = a

/**
 * Convert integer value a into an 64-bit mask, and store the result in k.
 *
 * [Intel's Documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_cvtu64_mask64)
 */
public fun cvtu64Mask64(a: ULong): Mmask64 = a
