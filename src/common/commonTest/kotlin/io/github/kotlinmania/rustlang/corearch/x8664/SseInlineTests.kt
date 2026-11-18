// port-lint: tests x86_64/sse.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.corearch.x86.mmSetrPs

// `x8664` Streaming SIMD Extensions (SSE)

public fun testMmCvtssSi64() {
    val inputs = listOf(Pair(42.0f, 42L), Pair(-31.4, -31), Pair(-33.5, -34), Pair(-34.5, -34), Pair(4.0e10, 40000000000), Pair(4.0e-10, 0), Pair(Float.NaN, Long.MIN_VALUE), Pair(2147483500.1, 2147483520), Pair(9.223371e18, 9223370937343148032))
    for ((i, xi, e) in inputs.mapIndexed { index, item -> Pair(index, item) }) {
        val x = mmSetrPs(xi, 1.0f, 3.0f, 4.0f)
        val r = mmCvtssSi64(x)
        check(e == r)
    }
}

public fun testMmCvttssSi64() {
    val inputs = listOf(Pair(42.0f, 42L), Pair(-31.4, -31), Pair(-33.5, -33), Pair(-34.5, -34), Pair(10.999, 10), Pair(-5.99, -5), Pair(4.0e10, 40000000000), Pair(4.0e-10, 0), Pair(Float.NaN, Long.MIN_VALUE), Pair(2147483500.1, 2147483520), Pair(9.223371e18, 9223370937343148032), Pair(9.223372e18, Long.MIN_VALUE))
    for ((i, xi, e) in inputs.mapIndexed { index, item -> Pair(index, item) }) {
        val x = mmSetrPs(xi, 1.0f, 3.0f, 4.0f)
        val r = mmCvttssSi64(x)
        check(e == r)
    }
}

public fun testMmCvtsi64Ss() {
    val a = mmSetrPs(5.0f, 6.0f, 7.0f, 8.0f)
    val r = mmCvtsi64Ss(a, 4555L)
    val e = mmSetrPs(4555.0f, 6.0f, 7.0f, 8.0f)
    assertEqM128(e, r)
    val r1 = mmCvtsi64Ss(a, 322223333L)
    val e1 = mmSetrPs(322223333.0f, 6.0f, 7.0f, 8.0f)
    assertEqM128(e1, r1)
    val r2 = mmCvtsi64Ss(a, -432L)
    val e2 = mmSetrPs(-432.0f, 6.0f, 7.0f, 8.0f)
    assertEqM128(e2, r2)
    val r3 = mmCvtsi64Ss(a, -322223333L)
    val e3 = mmSetrPs(-322223333.0f, 6.0f, 7.0f, 8.0f)
    assertEqM128(e3, r3)
    val r4 = mmCvtsi64Ss(a, 9223372036854775807L)
    val e4 = mmSetrPs(9.223372e18f, 6.0f, 7.0f, 8.0f)
    assertEqM128(e4, r4)
    val r5 = mmCvtsi64Ss(a, -9223372036854775808uL.toLong())
    val e5 = mmSetrPs(-9.223372e18f, 6.0f, 7.0f, 8.0f)
    assertEqM128(e5, r5)
}
