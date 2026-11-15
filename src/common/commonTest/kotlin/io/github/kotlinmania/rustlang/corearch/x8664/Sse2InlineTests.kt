// port-lint: tests x86_64/sse2.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.x86.mmSet1Pd
import io.github.kotlinmania.rustlang.x86.mmSetrPd
import io.github.kotlinmania.rustlang.x86.mmSfence

// `x8664`s Streaming SIMD Extensions 2 (SSE2)

public fun testMmCvtsdSi64() {
    val r = mmCvtsdSi64(mmSetrPd(-2.0, 5.0))
    check(r == -2L)
    val r1 = mmCvtsdSi64(mmSetrPd(Double.MAX_VALUE, (-Double.MAX_VALUE)))
    check(r1 == Long.MIN_VALUE)
}

public fun testMmCvtsdSi64x() {
    val r = mmCvtsdSi64x(mmSetrPd(Double.NaN, Double.NaN))
    check(r == Long.MIN_VALUE)
}

public fun testMmCvttsdSi64() {
    val a = mmSetrPd(-1.1, 2.2)
    val r = mmCvttsdSi64(a)
    check(r == -1L)
}

public fun testMmCvttsdSi64x() {
    val a = mmSetrPd(Double.NEGATIVE_INFINITY, Double.NaN)
    val r = mmCvttsdSi64x(a)
    check(r == Long.MIN_VALUE)
}

// Miri cannot support this until it is clear how it fits in the Rust memory model
// (non-temporal store)
public fun testMmStreamSi64() {
    val a: Long = 7L
    val mem = -1
    run {
        mmStreamSi64(addrOfMut(mem), a)
    }
    mmSfence()
    check(a == mem)
}

public fun testMmCvtsi64Si128() {
    val r = mmCvtsi64Si128(5L)
    assertEqM128i(r, mmSetrEpi64x(5, 0))
}

public fun testMmCvtsi128Si64() {
    val r = mmCvtsi128Si64(mmSetrEpi64x(5, 0))
    check(r == 5L)
}

public fun testMmCvtsi64Sd() {
    val a = mmSet1Pd(3.5)
    val r = mmCvtsi64Sd(a, 5L)
    assertEqM128d(r, mmSetrPd(5.0, 3.5))
}
