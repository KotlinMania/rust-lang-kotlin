// port-lint: tests x86_64/sse42.rs
package io.github.kotlinmania.rustlang.corearch.x8664

// `x8664`s Streaming SIMD Extensions 4.2 (SSE4.2)

public fun testMmCrc32U64() {
    val crc = 0x7819dccd3e824
    val v = 0x2a22b845fed
    val i = mmCrc32U64(crc, v)
    check(i == 0xbb6cdc6cuL)
}
