// port-lint: tests x86_64/bswap.rs
package io.github.kotlinmania.rustlang.corearch.x8664

// Byte swap intrinsics.

public fun testBswap64() {
    check(bswap64(0x0EADBEEFFADECA0EL) == 0x0ECADEFAEFBEAD0EL)
    check(bswap64(0x0000000000000000L) == 0x0000000000000000L)
}
