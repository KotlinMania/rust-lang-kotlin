// port-lint: tests x86/bswap.rs
package io.github.kotlinmania.rustlang.corearch.x86

// Byte swap intrinsics.

public fun testBswap() {
    check(bswap(0x0EADBE0F) == 0x0FBEAD0E)
    check(bswap(0x00000000) == 0x00000000)
}
