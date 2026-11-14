// port-lint: source x86/bswap.rs
package io.github.kotlinmania.rustlang.corearch.x86

// Byte swap intrinsics.

/**
 * Returns an integer with the reversed byte order of x
 *
 * [Intel's documentation](https://www.intel.com/content/www/us/en/docs/intrinsics-guide/index.html#text=_bswap)
 */
public fun bswap(x: Int): Int = ((((x) shl 24)) or (((x) and 0x0000FF00) shl 8) or (((x) ushr 8) and 0x0000FF00) or ((x) ushr 24))
