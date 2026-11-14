// port-lint: tests x86_64/sse41.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.corearch.x86.mmSet1Epi64x

// `i686`s Streaming SIMD Extensions 4.1 (SSE4.1)

public fun testMmExtractEpi64() {
    val a = mmSetrEpi64x(0, 1)
    val r = mmExtractEpi64(a, 1)
    check(r == 1L)
    val r1 = mmExtractEpi64(a, 0)
    check(r1 == 0L)
}

public fun testMmInsertEpi64() {
    val a = mmSet1Epi64x(0L)
    val e = mmSetrEpi64x(0, 32)
    val r = mmInsertEpi64(a, 32L, 1)
    assertEqM128i(r, e)
    val e1 = mmSetrEpi64x(32, 0)
    val r1 = mmInsertEpi64(a, 32L, 0)
    assertEqM128i(r1, e1)
}
