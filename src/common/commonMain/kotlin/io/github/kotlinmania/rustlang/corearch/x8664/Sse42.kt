// port-lint: source x86_64/sse42.rs
package io.github.kotlinmania.rustlang.corearch.x8664

// `x8664`s Streaming SIMD Extensions 4.2 (SSE4.2)

public expect fun crc326464(crc: ULong, v: ULong): ULong

/**
 * Starting with the initial value in `crc`, return the accumulated
 * CRC32-C value for unsigned 64-bit integer `v`.
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_mm_crc32_u64)
 */
public fun mmCrc32U64(crc: ULong, v: ULong): ULong = crc326464(crc, v)
