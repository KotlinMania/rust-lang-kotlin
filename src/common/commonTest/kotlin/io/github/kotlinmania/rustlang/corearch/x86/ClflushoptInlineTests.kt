// port-lint: tests x86/clflushopt.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.addrOf

// `CLFLUSHOPT` cache-line flush.

public fun testMmClflushopt() {
    val x = 0u.toUByte()
    mmClflushopt(addrOf(x))
}
