// port-lint: tests x86/sse3.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout

// Streaming SIMD Extensions 3 (SSE3)

public fun testMmAddsubPs() {
    val a = mmSetrPs(-1.0f, 5.0f, 0.0f, -10.0f)
    val b = mmSetrPs(-100.0f, 20.0f, 0.0f, -5.0f)
    val r = mmAddsubPs(a, b)
    assertEqM128(r, mmSetrPs(99.0f, 25.0f, 0.0f, -15.0f))
}

public fun testMmAddsubPd() {
    val a = mmSetrPd(-1.0, 5.0)
    val b = mmSetrPd(-100.0, 20.0)
    val r = mmAddsubPd(a, b)
    assertEqM128d(r, mmSetrPd(99.0, 25.0))
}

public fun testMmHaddPd() {
    val a = mmSetrPd(-1.0, 5.0)
    val b = mmSetrPd(-100.0, 20.0)
    val r = mmHaddPd(a, b)
    assertEqM128d(r, mmSetrPd(4.0, -80.0))
}

public fun testMmHaddPs() {
    val a = mmSetrPs(-1.0f, 5.0f, 0.0f, -10.0f)
    val b = mmSetrPs(-100.0f, 20.0f, 0.0f, -5.0f)
    val r = mmHaddPs(a, b)
    assertEqM128(r, mmSetrPs(4.0f, -10.0f, -80.0f, -5.0f))
}

public fun testMmHsubPd() {
    val a = mmSetrPd(-1.0, 5.0)
    val b = mmSetrPd(-100.0, 20.0)
    val r = mmHsubPd(a, b)
    assertEqM128d(r, mmSetrPd(-6.0, -120.0))
}

public fun testMmHsubPs() {
    val a = mmSetrPs(-1.0f, 5.0f, 0.0f, -10.0f)
    val b = mmSetrPs(-100.0f, 20.0f, 0.0f, -5.0f)
    val r = mmHsubPs(a, b)
    assertEqM128(r, mmSetrPs(-6.0f, 10.0f, -120.0f, 5.0f))
}

public fun testMmLddquSi128() {
    val localStorageL243C8 = AddressableLocal(mmSetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16), ObjectSlotLayout<M128i>())
    val a by localStorageL243C8
    val r = mmLddquSi128(a)
    assertEqM128i(a, r)
}

public fun testMmMovedupPd() {
    val a = mmSetrPd(-1.0, 5.0)
    val r = mmMovedupPd(a)
    assertEqM128d(r, mmSetrPd(-1.0, -1.0))
}

public fun testMmMovehdupPs() {
    val a = mmSetrPs(-1.0f, 5.0f, 0.0f, -10.0f)
    val r = mmMovehdupPs(a)
    assertEqM128(r, mmSetrPs(5.0f, 5.0f, -10.0f, -10.0f))
}

public fun testMmMoveldupPs() {
    val a = mmSetrPs(-1.0f, 5.0f, 0.0f, -10.0f)
    val r = mmMoveldupPs(a)
    assertEqM128(r, mmSetrPs(-1.0f, -1.0f, 0.0f, 0.0f))
}

public fun testMmLoaddupPd() {
    val localStorageL277C8 = AddressableLocal(-5.0, ObjectSlotLayout<Any?>())
    val d by localStorageL277C8
    val r = mmLoaddupPd(d)
    assertEqM128d(r, mmSetrPd(d, d))
}
