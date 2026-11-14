// port-lint: source x86_64/bswap.rs
package io.github.kotlinmania.rustlang.corearch.x8664

// Byte swap intrinsics.

/**
 * Returns an integer with the reversed byte order of x
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_bswap64)
 */
public fun bswap64(x: Long): Long = (((((x).toULong() shl 56)) or (((x).toULong() and 0x000000000000FF00uL) shl 40) or (((x).toULong() and 0x0000000000FF0000uL) shl 24) or (((x).toULong() and 0x00000000FF000000uL) shl 8) or ((((x).toULong() shr 8) and 0x00000000FF000000uL)) or ((((x).toULong() shr 24) and 0x0000000000FF0000uL)) or ((((x).toULong() shr 40) and 0x000000000000FF00uL)) or ((x).toULong() shr 56)).toLong())
