// port-lint: tests x86/avx512vp2intersect.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

// Vector Pair Intersection to a Pair of Mask Registers (VP2INTERSECT)

public fun testMm2intersectEpi32() {
    val localStorageL137C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    var k1 by localStorageL137C8
    val localStorageL138C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    var k2 by localStorageL138C8
    val a = mmSetEpi32(1, 2, 3, 4)
    val b = mmSetEpi32(3, 4, 5, 6)
    mm2intersectEpi32(a, b, RustMutableReference(localStorageL137C8.asMutPtr(), localStorageL137C8.layout), RustMutableReference(localStorageL138C8.asMutPtr(), localStorageL138C8.layout))
    check(k1 == 0x3)
    check(k2 == 0xC)
    val a1 = mmSetEpi32(1, 2, 3, 4)
    val b1 = mmSetEpi32(2, 3, 4, 5)
    mm2intersectEpi32(a1, b1, RustMutableReference(localStorageL137C8.asMutPtr(), localStorageL137C8.layout), RustMutableReference(localStorageL138C8.asMutPtr(), localStorageL138C8.layout))
    check(k1 == 0x7)
    check(k2 == 0xE)
}

public fun testMm2intersectEpi64() {
    val localStorageL155C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    var k1 by localStorageL155C8
    val localStorageL156C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    var k2 by localStorageL156C8
    val a = mmSetEpi64x(1L, 2L)
    val b = mmSetEpi64x(2L, 3L)
    mm2intersectEpi64(a, b, RustMutableReference(localStorageL155C8.asMutPtr(), localStorageL155C8.layout), RustMutableReference(localStorageL156C8.asMutPtr(), localStorageL156C8.layout))
    check(k1 == 0x1)
    check(k2 == 0x2)
    val a1 = mmSetEpi64x(1L, 2L)
    val b1 = mmSetEpi64x(2L, 2L)
    mm2intersectEpi64(a1, b1, RustMutableReference(localStorageL155C8.asMutPtr(), localStorageL155C8.layout), RustMutableReference(localStorageL156C8.asMutPtr(), localStorageL156C8.layout))
    check(k1 == 0x1)
    check(k2 == 0x3)
}

public fun testMm2562intersectEpi32() {
    val localStorageL173C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    var k1 by localStorageL173C8
    val localStorageL174C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    var k2 by localStorageL174C8
    val a = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val b = mm256SetEpi32(5, 6, 7, 8, 9, 10, 11, 12)
    mm2562intersectEpi32(a, b, RustMutableReference(localStorageL173C8.asMutPtr(), localStorageL173C8.layout), RustMutableReference(localStorageL174C8.asMutPtr(), localStorageL174C8.layout))
    check(k1 == 0xF)
    check(k2 == 0xF0)
    val a1 = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val b1 = mm256SetEpi32(2, 3, 4, 5, 6, 7, 8, 9)
    mm2562intersectEpi32(a1, b1, RustMutableReference(localStorageL173C8.asMutPtr(), localStorageL173C8.layout), RustMutableReference(localStorageL174C8.asMutPtr(), localStorageL174C8.layout))
    check(k1 == 0x7F)
    check(k2 == 0xFE)
}

public fun testMm2562intersectEpi64() {
    val localStorageL191C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    var k1 by localStorageL191C8
    val localStorageL192C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    var k2 by localStorageL192C8
    val a = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val b = mm256SetEpi64x(3L, 4L, 5L, 6L)
    mm2562intersectEpi64(a, b, RustMutableReference(localStorageL191C8.asMutPtr(), localStorageL191C8.layout), RustMutableReference(localStorageL192C8.asMutPtr(), localStorageL192C8.layout))
    check(k1 == 0x3)
    check(k2 == 0xC)
    val a1 = mm256SetEpi64x(1L, 2L, 3L, 4L)
    val b1 = mm256SetEpi64x(2L, 3L, 4L, 5L)
    mm2562intersectEpi64(a1, b1, RustMutableReference(localStorageL191C8.asMutPtr(), localStorageL191C8.layout), RustMutableReference(localStorageL192C8.asMutPtr(), localStorageL192C8.layout))
    check(k1 == 0x7)
    check(k2 == 0xE)
}

public fun testMm5122intersectEpi32() {
    val localStorageL209C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    var k1 by localStorageL209C8
    val localStorageL210C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    var k2 by localStorageL210C8
    val a = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = mm512SetEpi32(9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24)
    mm5122intersectEpi32(a, b, RustMutableReference(localStorageL209C8.asMutPtr(), localStorageL209C8.layout), RustMutableReference(localStorageL210C8.asMutPtr(), localStorageL210C8.layout))
    check(k1 == 0xFF)
    check(k2 == 0xFF00)
    val a1 = mm512SetEpi32(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b1 = mm512SetEpi32(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17)
    mm5122intersectEpi32(a1, b1, RustMutableReference(localStorageL209C8.asMutPtr(), localStorageL209C8.layout), RustMutableReference(localStorageL210C8.asMutPtr(), localStorageL210C8.layout))
    check(k1 == 0x7FFF)
    check(k2 == 0xFFFE)
}

public fun testMm5122intersectEpi64() {
    val localStorageL229C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    var k1 by localStorageL229C8
    val localStorageL230C8 = AddressableLocal(0, PrimitiveLayouts.I32)
    var k2 by localStorageL230C8
    val a = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b = mm512SetEpi64(5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L)
    mm5122intersectEpi64(a, b, RustMutableReference(localStorageL229C8.asMutPtr(), localStorageL229C8.layout), RustMutableReference(localStorageL230C8.asMutPtr(), localStorageL230C8.layout))
    check(k1 == 0xF)
    check(k2 == 0xF0)
    val a1 = mm512SetEpi64(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L)
    val b1 = mm512SetEpi64(2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L)
    mm5122intersectEpi64(a1, b1, RustMutableReference(localStorageL229C8.asMutPtr(), localStorageL229C8.layout), RustMutableReference(localStorageL230C8.asMutPtr(), localStorageL230C8.layout))
    check(k1 == 0x7F)
    check(k2 == 0xFE)
}
