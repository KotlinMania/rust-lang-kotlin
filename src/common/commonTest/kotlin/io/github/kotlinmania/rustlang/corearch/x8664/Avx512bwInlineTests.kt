// port-lint: tests x86_64/avx512bw.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.corearch.x86.Mmask64

public fun testCvtmask64U64() {
    val a: Mmask64 = 0xCC336699uL
    val r = cvtmask64U64(a)
    val e: ULong = 0xCC336699uL
    check(r == e)
}

public fun testCvtu64Mask64() {
    val a: ULong = 0xCC336699uL
    val r = cvtu64Mask64(a)
    val e: Mmask64 = 0xCC336699uL
    check(r == e)
}
