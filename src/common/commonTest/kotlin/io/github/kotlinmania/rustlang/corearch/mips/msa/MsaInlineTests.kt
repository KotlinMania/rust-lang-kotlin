// port-lint: tests mips/msa.rs
package io.github.kotlinmania.rustlang.corearch.mips.msa

import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.simd.Simd

// MIPS SIMD Architecture intrinsics
//
// The reference is [MIPS Architecture for Programmers Volume IV-j: The
// MIPS32 SIMD Architecture Module Revision 1.12][msaRef].
//
// [msaRef]: http://cdn2.imgtec.com/documentation/MD00866-2B-MSA32-AFP-01.12.pdf

public fun testMsaAddAB() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte()))
    val b = Simd<Byte>(listOf((-4).toByte(), (-3).toByte(), (-2).toByte(), (-1).toByte(), (-4).toByte(), (-3).toByte(), (-2).toByte(), (-1).toByte(), (-4).toByte(), (-3).toByte(), (-2).toByte(), (-1).toByte(), (-4).toByte(), (-3).toByte(), (-2).toByte(), (-1).toByte()))
    val r = Simd<Byte>(listOf(5.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 5.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaAddAB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddAH() {
    val a = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort()))
    val b = Simd<Short>(listOf((-4).toShort(), (-3).toShort(), (-2).toShort(), (-1).toShort(), (-4).toShort(), (-3).toShort(), (-2).toShort(), (-1).toShort()))
    val r = Simd<Short>(listOf(5.toShort(), 5.toShort(), 5.toShort(), 5.toShort(), 5.toShort(), 5.toShort(), 5.toShort(), 5.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaAddAH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddAW() {
    val a = Simd<Int>(listOf(1, 2, 3, 4))
    val b = Simd<Int>(listOf(-4, -3, -2, -1))
    val r = Simd<Int>(listOf(5, 5, 5, 5))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaAddAW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddAD() {
    val a = Simd<Long>(listOf(1L, 2L))
    val b = Simd<Long>(listOf(-4L, -3L))
    val r = Simd<Long>(listOf(5L, 5L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaAddAD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddsAB() {
    val a = Simd<Byte>(listOf(100.toByte(), Byte.MAX_VALUE, 100.toByte(), Byte.MAX_VALUE, 100.toByte(), Byte.MAX_VALUE, 100.toByte(), Byte.MAX_VALUE, 100.toByte(), Byte.MAX_VALUE, 100.toByte(), Byte.MAX_VALUE, 100.toByte(), Byte.MAX_VALUE, 100.toByte(), Byte.MAX_VALUE))
    val b = Simd<Byte>(listOf((-4).toByte(), (-3).toByte(), (-2).toByte(), (-100).toByte(), (-4).toByte(), (-3).toByte(), (-2).toByte(), (-100).toByte(), (-4).toByte(), (-3).toByte(), (-2).toByte(), (-100).toByte(), (-4).toByte(), (-3).toByte(), (-2).toByte(), (-100).toByte()))
    val r = Simd<Byte>(listOf(104.toByte(), 127.toByte(), 102.toByte(), 127.toByte(), 104.toByte(), 127.toByte(), 102.toByte(), 127.toByte(), 104.toByte(), 127.toByte(), 102.toByte(), 127.toByte(), 104.toByte(), 127.toByte(), 102.toByte(), 127.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaAddsAB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddsAH() {
    val a = Simd<Short>(listOf(100.toShort(), Short.MAX_VALUE, 100.toShort(), Short.MAX_VALUE, 100.toShort(), Short.MAX_VALUE, 100.toShort(), Short.MAX_VALUE))
    val b = Simd<Short>(listOf((-4).toShort(), (-3).toShort(), (-2).toShort(), (-1).toShort(), (-4).toShort(), (-3).toShort(), (-2).toShort(), (-1).toShort()))
    val r = Simd<Short>(listOf(104.toShort(), Short.MAX_VALUE, 102.toShort(), Short.MAX_VALUE, 104.toShort(), Short.MAX_VALUE, 102.toShort(), Short.MAX_VALUE))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaAddsAH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddsAW() {
    val a = Simd<Int>(listOf(100, Int.MAX_VALUE, 100, Int.MAX_VALUE))
    val b = Simd<Int>(listOf(-4, -3, -2, -1))
    val r = Simd<Int>(listOf(104, Int.MAX_VALUE, 102, Int.MAX_VALUE))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaAddsAW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddsAD() {
    val a = Simd<Long>(listOf(100L, Long.MAX_VALUE))
    val b = Simd<Long>(listOf(-4L, -3L))
    val r = Simd<Long>(listOf(104L, Long.MAX_VALUE))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaAddsAD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddsSB() {
    val a = Simd<Byte>(listOf(100.toByte(), Byte.MIN_VALUE, 100.toByte(), Byte.MAX_VALUE, 100.toByte(), Byte.MIN_VALUE, 100.toByte(), Byte.MAX_VALUE, 100.toByte(), Byte.MIN_VALUE, 100.toByte(), Byte.MAX_VALUE, 100.toByte(), Byte.MIN_VALUE, 100.toByte(), Byte.MAX_VALUE))
    val b = Simd<Byte>(listOf((-4).toByte(), (-3).toByte(), (-2).toByte(), 100.toByte(), (-4).toByte(), (-3).toByte(), (-2).toByte(), 100.toByte(), (-4).toByte(), (-3).toByte(), (-2).toByte(), 100.toByte(), (-4).toByte(), (-3).toByte(), (-2).toByte(), 100.toByte()))
    val r = Simd<Byte>(listOf(96.toByte(), Byte.MIN_VALUE, 98.toByte(), Byte.MAX_VALUE, 96.toByte(), Byte.MIN_VALUE, 98.toByte(), Byte.MAX_VALUE, 96.toByte(), Byte.MIN_VALUE, 98.toByte(), Byte.MAX_VALUE, 96.toByte(), Byte.MIN_VALUE, 98.toByte(), Byte.MAX_VALUE))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaAddsSB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddsSH() {
    val a = Simd<Short>(listOf(100.toShort(), Short.MIN_VALUE, 100.toShort(), Short.MAX_VALUE, 100.toShort(), Short.MIN_VALUE, 100.toShort(), Short.MAX_VALUE))
    val b = Simd<Short>(listOf((-4).toShort(), (-3).toShort(), (-2).toShort(), 1.toShort(), (-4).toShort(), (-3).toShort(), (-2).toShort(), 1.toShort()))
    val r = Simd<Short>(listOf(96.toShort(), Short.MIN_VALUE, 98.toShort(), Short.MAX_VALUE, 96.toShort(), Short.MIN_VALUE, 98.toShort(), Short.MAX_VALUE))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaAddsSH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddsSW() {
    val a = Simd<Int>(listOf(100, Int.MAX_VALUE, 100, Int.MIN_VALUE))
    val b = Simd<Int>(listOf(-4, 3, -2, -1))
    val r = Simd<Int>(listOf(96, Int.MAX_VALUE, 98, Int.MIN_VALUE))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaAddsSW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddsSD() {
    val a = Simd<Long>(listOf(100L, Long.MIN_VALUE))
    val b = Simd<Long>(listOf(-4L, -3L))
    val r = Simd<Long>(listOf(96L, Long.MIN_VALUE))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaAddsSD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddsUB() {
    val a = Simd<UByte>(listOf(100u.toUByte(), UByte.MAX_VALUE, 100u.toUByte(), UByte.MAX_VALUE, 100u.toUByte(), UByte.MAX_VALUE, 100u.toUByte(), UByte.MAX_VALUE, 100u.toUByte(), UByte.MAX_VALUE, 100u.toUByte(), UByte.MAX_VALUE, 100u.toUByte(), UByte.MAX_VALUE, 100u.toUByte(), UByte.MAX_VALUE))
    val b = Simd<UByte>(listOf(4u.toUByte(), 3u.toUByte(), 2u.toUByte(), 100u.toUByte(), 4u.toUByte(), 3u.toUByte(), 2u.toUByte(), 100u.toUByte(), 4u.toUByte(), 3u.toUByte(), 2u.toUByte(), 100u.toUByte(), 4u.toUByte(), 3u.toUByte(), 2u.toUByte(), 100u.toUByte()))
    val r = Simd<UByte>(listOf(104u.toUByte(), UByte.MAX_VALUE, 102u.toUByte(), UByte.MAX_VALUE, 104u.toUByte(), UByte.MAX_VALUE, 102u.toUByte(), UByte.MAX_VALUE, 104u.toUByte(), UByte.MAX_VALUE, 102u.toUByte(), UByte.MAX_VALUE, 104u.toUByte(), UByte.MAX_VALUE, 102u.toUByte(), UByte.MAX_VALUE))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaAddsUB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddsUH() {
    val a = Simd<UShort>(listOf(100u.toUShort(), UShort.MAX_VALUE, 100u.toUShort(), UShort.MAX_VALUE, 100u.toUShort(), UShort.MAX_VALUE, 100u.toUShort(), UShort.MAX_VALUE))
    val b = Simd<UShort>(listOf(4u.toUShort(), 3u.toUShort(), 2u.toUShort(), 1u.toUShort(), 4u.toUShort(), 3u.toUShort(), 2u.toUShort(), 1u.toUShort()))
    val r = Simd<UShort>(listOf(104u.toUShort(), UShort.MAX_VALUE, 102u.toUShort(), UShort.MAX_VALUE, 104u.toUShort(), UShort.MAX_VALUE, 102u.toUShort(), UShort.MAX_VALUE))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaAddsUH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddsUW() {
    val a = Simd<UInt>(listOf(100u, UInt.MAX_VALUE, 100u, UInt.MAX_VALUE))
    val b = Simd<UInt>(listOf(4u, 3u, 2u, 1u))
    val r = Simd<UInt>(listOf(104u, UInt.MAX_VALUE, 102u, UInt.MAX_VALUE))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaAddsUW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddsUD() {
    val a = Simd<ULong>(listOf(100uL, ULong.MAX_VALUE))
    val b = Simd<ULong>(listOf(4uL, 3uL))
    val r = Simd<ULong>(listOf(104uL, ULong.MAX_VALUE))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaAddsUD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<Any?, V2u64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddvB() {
    val a = Simd<Byte>(listOf(100.toByte(), Byte.MIN_VALUE, 100.toByte(), Byte.MAX_VALUE, 100.toByte(), Byte.MIN_VALUE, 100.toByte(), Byte.MAX_VALUE, 100.toByte(), Byte.MIN_VALUE, 100.toByte(), Byte.MAX_VALUE, 100.toByte(), Byte.MIN_VALUE, 100.toByte(), Byte.MAX_VALUE))
    val b = Simd<Byte>(listOf((-4).toByte(), (-3).toByte(), (-2).toByte(), 100.toByte(), (-4).toByte(), (-3).toByte(), (-2).toByte(), 100.toByte(), (-4).toByte(), (-3).toByte(), (-2).toByte(), 100.toByte(), (-4).toByte(), (-3).toByte(), (-2).toByte(), 100.toByte()))
    val r = Simd<Byte>(listOf(96.toByte(), 125.toByte(), 98.toByte(), (-29).toByte(), 96.toByte(), 125.toByte(), 98.toByte(), (-29).toByte(), 96.toByte(), 125.toByte(), 98.toByte(), (-29).toByte(), 96.toByte(), 125.toByte(), 98.toByte(), (-29).toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaAddvB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddvH() {
    val a = Simd<Short>(listOf(100.toShort(), Short.MIN_VALUE, 100.toShort(), Short.MAX_VALUE, 100.toShort(), Short.MIN_VALUE, 100.toShort(), Short.MAX_VALUE))
    val b = Simd<Short>(listOf((-4).toShort(), (-3).toShort(), (-2).toShort(), 1.toShort(), (-4).toShort(), (-3).toShort(), (-2).toShort(), 1.toShort()))
    val r = Simd<Short>(listOf(96.toShort(), 32765.toShort(), 98.toShort(), (-32768).toShort(), 96.toShort(), 32765.toShort(), 98.toShort(), (-32768).toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaAddvH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddvW() {
    val a = Simd<Int>(listOf(100, Int.MAX_VALUE, 100, Int.MIN_VALUE))
    val b = Simd<Int>(listOf(-4, 3, -2, -1))
    val r = Simd<Int>(listOf(96, -2147483646, 98, 2147483647))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaAddvW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddvD() {
    val a = Simd<Long>(listOf(100L, Long.MIN_VALUE))
    val b = Simd<Long>(listOf(-4L, -3L))
    val r = Simd<Long>(listOf(96L, 9223372036854775805L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaAddvD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddviB() {
    val a = Simd<Byte>(listOf(100.toByte(), Byte.MAX_VALUE, 100.toByte(), Byte.MAX_VALUE, 100.toByte(), Byte.MAX_VALUE, 100.toByte(), Byte.MAX_VALUE, 100.toByte(), Byte.MAX_VALUE, 100.toByte(), Byte.MAX_VALUE, 100.toByte(), Byte.MAX_VALUE, 100.toByte(), Byte.MAX_VALUE))
    val r = Simd<Byte>(listOf(103.toByte(), (-126).toByte(), 103.toByte(), (-126).toByte(), 103.toByte(), (-126).toByte(), 103.toByte(), (-126).toByte(), 103.toByte(), (-126).toByte(), 103.toByte(), (-126).toByte(), 103.toByte(), (-126).toByte(), 103.toByte(), (-126).toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaAddviB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), 3), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddviH() {
    val a = Simd<Short>(listOf(Short.MAX_VALUE, 3276.toShort(), (-100).toShort(), (-127).toShort(), Short.MAX_VALUE, 3276.toShort(), (-100).toShort(), (-127).toShort()))
    val r = Simd<Short>(listOf((-32766).toShort(), 3279.toShort(), (-97).toShort(), (-124).toShort(), (-32766).toShort(), 3279.toShort(), (-97).toShort(), (-124).toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaAddviH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), 3), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddviW() {
    val a = Simd<Int>(listOf(100, Int.MAX_VALUE, 100, Int.MIN_VALUE))
    val r = Simd<Int>(listOf(103, -2147483646, 103, -2147483645))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaAddviW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), 3), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddviD() {
    val a = Simd<Long>(listOf(100L, Long.MIN_VALUE))
    val r = Simd<Long>(listOf(117L, -9223372036854775791L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaAddviD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), 17), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAndV() {
    val a = Simd<UByte>(listOf(100u.toUByte(), UByte.MAX_VALUE, 100u.toUByte(), UByte.MAX_VALUE, 100u.toUByte(), UByte.MAX_VALUE, 100u.toUByte(), UByte.MAX_VALUE, 100u.toUByte(), UByte.MAX_VALUE, 100u.toUByte(), UByte.MAX_VALUE, 100u.toUByte(), UByte.MAX_VALUE, 100u.toUByte(), UByte.MAX_VALUE))
    val b = Simd<UByte>(listOf(4u.toUByte(), 3u.toUByte(), 2u.toUByte(), 100u.toUByte(), 4u.toUByte(), 3u.toUByte(), 2u.toUByte(), 100u.toUByte(), 4u.toUByte(), 3u.toUByte(), 2u.toUByte(), 100u.toUByte(), 4u.toUByte(), 3u.toUByte(), 2u.toUByte(), 100u.toUByte()))
    val r = Simd<UByte>(listOf(4u.toUByte(), 3u.toUByte(), 0u.toUByte(), 100u.toUByte(), 4u.toUByte(), 3u.toUByte(), 0u.toUByte(), 100u.toUByte(), 4u.toUByte(), 3u.toUByte(), 0u.toUByte(), 100u.toUByte(), 4u.toUByte(), 3u.toUByte(), 0u.toUByte(), 100u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaAndV(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAndiB() {
    val a = Simd<UByte>(listOf(100u.toUByte(), UByte.MAX_VALUE, 100u.toUByte(), UByte.MAX_VALUE, 100u.toUByte(), UByte.MAX_VALUE, 100u.toUByte(), UByte.MAX_VALUE, 100u.toUByte(), UByte.MAX_VALUE, 100u.toUByte(), UByte.MAX_VALUE, 100u.toUByte(), UByte.MAX_VALUE, 100u.toUByte(), UByte.MAX_VALUE))
    val r = Simd<UByte>(listOf(4u.toUByte(), 5u.toUByte(), 4u.toUByte(), 5u.toUByte(), 4u.toUByte(), 5u.toUByte(), 4u.toUByte(), 5u.toUByte(), 4u.toUByte(), 5u.toUByte(), 4u.toUByte(), 5u.toUByte(), 4u.toUByte(), 5u.toUByte(), 4u.toUByte(), 5u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaAndiB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), 5), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAsubSB() {
    val a = Simd<Byte>(listOf((-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte()))
    val b = Simd<Byte>(listOf((-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte()))
    val r = Simd<Byte>(listOf(5.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 5.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaAsubSB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAsubSH() {
    val a = Simd<Short>(listOf((-1).toShort(), (-2).toShort(), (-3).toShort(), (-4).toShort(), (-1).toShort(), (-2).toShort(), (-3).toShort(), (-4).toShort()))
    val b = Simd<Short>(listOf((-6).toShort(), (-7).toShort(), (-8).toShort(), (-9).toShort(), (-6).toShort(), (-7).toShort(), (-8).toShort(), (-9).toShort()))
    val r = Simd<Short>(listOf(5.toShort(), 5.toShort(), 5.toShort(), 5.toShort(), 5.toShort(), 5.toShort(), 5.toShort(), 5.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaAsubSH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAsubSW() {
    val a = Simd<Int>(listOf(-1, -2, -3, -4))
    val b = Simd<Int>(listOf(-6, -7, -8, -9))
    val r = Simd<Int>(listOf(5, 5, 5, 5))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaAsubSW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAsubSD() {
    val a = Simd<Long>(listOf(-1L, -2L))
    val b = Simd<Long>(listOf(-6L, -7L))
    val r = Simd<Long>(listOf(5L, 5L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaAsubSD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAsubUB() {
    val a = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte()))
    val b = Simd<UByte>(listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte()))
    val r = Simd<UByte>(listOf(5u.toUByte(), 5u.toUByte(), 5u.toUByte(), 5u.toUByte(), 5u.toUByte(), 5u.toUByte(), 5u.toUByte(), 5u.toUByte(), 5u.toUByte(), 5u.toUByte(), 5u.toUByte(), 5u.toUByte(), 5u.toUByte(), 5u.toUByte(), 5u.toUByte(), 5u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaAsubUB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAsubUH() {
    val a = Simd<UShort>(listOf(1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort()))
    val b = Simd<UShort>(listOf(6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort(), 6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort()))
    val r = Simd<UShort>(listOf(5u.toUShort(), 5u.toUShort(), 5u.toUShort(), 5u.toUShort(), 5u.toUShort(), 5u.toUShort(), 5u.toUShort(), 5u.toUShort()))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaAsubUH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAsubUW() {
    val a = Simd<UInt>(listOf(1u, 2u, 3u, 4u))
    val b = Simd<UInt>(listOf(6u, 7u, 8u, 9u))
    val r = Simd<UInt>(listOf(5u, 5u, 5u, 5u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaAsubUW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAsubUD() {
    val a = Simd<ULong>(listOf(1uL, 2uL))
    val b = Simd<ULong>(listOf(6uL, 7uL))
    val r = Simd<ULong>(listOf(5uL, 5uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaAsubUD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<Any?, V2u64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAveSB() {
    val a = Simd<Byte>(listOf((-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte()))
    val b = Simd<Byte>(listOf(6.toByte(), (-7).toByte(), 8.toByte(), (-9).toByte(), 6.toByte(), (-7).toByte(), 8.toByte(), (-9).toByte(), 6.toByte(), (-7).toByte(), 8.toByte(), (-9).toByte(), 6.toByte(), (-7).toByte(), 8.toByte(), (-9).toByte()))
    val r = Simd<Byte>(listOf(2.toByte(), (-5).toByte(), 2.toByte(), (-7).toByte(), 2.toByte(), (-5).toByte(), 2.toByte(), (-7).toByte(), 2.toByte(), (-5).toByte(), 2.toByte(), (-7).toByte(), 2.toByte(), (-5).toByte(), 2.toByte(), (-7).toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaAveSB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAveSH() {
    val a = Simd<Short>(listOf((-1).toShort(), (-2).toShort(), (-3).toShort(), (-4).toShort(), (-1).toShort(), (-2).toShort(), (-3).toShort(), (-4).toShort()))
    val b = Simd<Short>(listOf(6.toShort(), (-7).toShort(), 8.toShort(), (-9).toShort(), 6.toShort(), (-7).toShort(), 8.toShort(), (-9).toShort()))
    val r = Simd<Short>(listOf(2.toShort(), (-5).toShort(), 2.toShort(), (-7).toShort(), 2.toShort(), (-5).toShort(), 2.toShort(), (-7).toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaAveSH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAveSW() {
    val a = Simd<Int>(listOf(-1, -2, -3, -4))
    val b = Simd<Int>(listOf(6, -7, 8, -9))
    val r = Simd<Int>(listOf(2, -5, 2, -7))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaAveSW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAveSD() {
    val a = Simd<Long>(listOf(-1L, -2L))
    val b = Simd<Long>(listOf(-6L, -7L))
    val r = Simd<Long>(listOf(-4L, -5L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaAveSD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAveUB() {
    val a = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte()))
    val b = Simd<UByte>(listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte()))
    val r = Simd<UByte>(listOf(3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaAveUB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAveUH() {
    val a = Simd<UShort>(listOf(1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort()))
    val b = Simd<UShort>(listOf(6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort(), 6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort()))
    val r = Simd<UShort>(listOf(3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort()))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaAveUH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAveUW() {
    val a = Simd<UInt>(listOf(1u, 2u, 3u, 4u))
    val b = Simd<UInt>(listOf(6u, 7u, 8u, 9u))
    val r = Simd<UInt>(listOf(3u, 4u, 5u, 6u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaAveUW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAveUD() {
    val a = Simd<ULong>(listOf(1uL, 2uL))
    val b = Simd<ULong>(listOf(6uL, 7uL))
    val r = Simd<ULong>(listOf(3uL, 4uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaAveUD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<Any?, V2u64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAverSB() {
    val a = Simd<Byte>(listOf((-1).toByte(), (-2).toByte(), 3.toByte(), (-4).toByte(), (-1).toByte(), (-2).toByte(), 3.toByte(), (-4).toByte(), (-1).toByte(), (-2).toByte(), 3.toByte(), (-4).toByte(), (-1).toByte(), (-2).toByte(), 3.toByte(), (-4).toByte()))
    val b = Simd<Byte>(listOf((-6).toByte(), 7.toByte(), (-8).toByte(), (-9).toByte(), (-6).toByte(), 7.toByte(), (-8).toByte(), (-9).toByte(), (-6).toByte(), 7.toByte(), (-8).toByte(), (-9).toByte(), (-6).toByte(), 7.toByte(), (-8).toByte(), (-9).toByte()))
    val r = Simd<Byte>(listOf((-3).toByte(), 3.toByte(), (-2).toByte(), (-6).toByte(), (-3).toByte(), 3.toByte(), (-2).toByte(), (-6).toByte(), (-3).toByte(), 3.toByte(), (-2).toByte(), (-6).toByte(), (-3).toByte(), 3.toByte(), (-2).toByte(), (-6).toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaAverSB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAverSH() {
    val a = Simd<Short>(listOf((-1).toShort(), (-2).toShort(), 3.toShort(), (-4).toShort(), (-1).toShort(), (-2).toShort(), 3.toShort(), (-4).toShort()))
    val b = Simd<Short>(listOf((-6).toShort(), 7.toShort(), (-8).toShort(), (-9).toShort(), (-6).toShort(), 7.toShort(), (-8).toShort(), (-9).toShort()))
    val r = Simd<Short>(listOf((-3).toShort(), 3.toShort(), (-2).toShort(), (-6).toShort(), (-3).toShort(), 3.toShort(), (-2).toShort(), (-6).toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaAverSH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAverSW() {
    val a = Simd<Int>(listOf(-1, -2, 3, -4))
    val b = Simd<Int>(listOf(-6, 7, -8, -9))
    val r = Simd<Int>(listOf(-3, 3, -2, -6))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaAverSW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAverSD() {
    val a = Simd<Long>(listOf(-1L, -2L))
    val b = Simd<Long>(listOf(-6L, -7L))
    val r = Simd<Long>(listOf(-3L, -4L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaAverSD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAverUB() {
    val a = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte()))
    val b = Simd<UByte>(listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte()))
    val r = Simd<UByte>(listOf(4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaAverUB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAverUH() {
    val a = Simd<UShort>(listOf(1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort()))
    val b = Simd<UShort>(listOf(6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort(), 6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort()))
    val r = Simd<UShort>(listOf(4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 7u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 7u.toUShort()))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaAverUH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAverUW() {
    val a = Simd<UInt>(listOf(1u, 2u, 3u, 4u))
    val b = Simd<UInt>(listOf(6u, 7u, 8u, 9u))
    val r = Simd<UInt>(listOf(4u, 5u, 6u, 7u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaAverUW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAverUD() {
    val a = Simd<ULong>(listOf(1uL, 2uL))
    val b = Simd<ULong>(listOf(6uL, 7uL))
    val r = Simd<ULong>(listOf(4uL, 5uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaAverUD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<Any?, V2u64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBclrB() {
    val a = Simd<UByte>(listOf(255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte()))
    val b = Simd<UByte>(listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte()))
    val r = Simd<UByte>(listOf(191u.toUByte(), 27u.toUByte(), 54u.toUByte(), 1u.toUByte(), 191u.toUByte(), 27u.toUByte(), 54u.toUByte(), 1u.toUByte(), 191u.toUByte(), 27u.toUByte(), 54u.toUByte(), 1u.toUByte(), 191u.toUByte(), 27u.toUByte(), 54u.toUByte(), 1u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBclrB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBclrH() {
    val a = Simd<UShort>(listOf(255u.toUShort(), 155u.toUShort(), 55u.toUShort(), 1u.toUShort(), 255u.toUShort(), 155u.toUShort(), 55u.toUShort(), 1u.toUShort()))
    val b = Simd<UShort>(listOf(6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort(), 6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort()))
    val r = Simd<UShort>(listOf(191u.toUShort(), 27u.toUShort(), 55u.toUShort(), 1u.toUShort(), 191u.toUShort(), 27u.toUShort(), 55u.toUShort(), 1u.toUShort()))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaBclrH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBclrW() {
    val a = Simd<UInt>(listOf(255u, 155u, 55u, 1u))
    val b = Simd<UInt>(listOf(6u, 7u, 8u, 9u))
    val r = Simd<UInt>(listOf(191u, 27u, 55u, 1u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaBclrW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBclrD() {
    val a = Simd<ULong>(listOf(255uL, 155uL))
    val b = Simd<ULong>(listOf(6uL, 7uL))
    val r = Simd<ULong>(listOf(191uL, 27uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaBclrD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<Any?, V2u64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBclriB() {
    val a = Simd<UByte>(listOf(255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte()))
    val r = Simd<UByte>(listOf(247u.toUByte(), 147u.toUByte(), 55u.toUByte(), 1u.toUByte(), 247u.toUByte(), 147u.toUByte(), 55u.toUByte(), 1u.toUByte(), 247u.toUByte(), 147u.toUByte(), 55u.toUByte(), 1u.toUByte(), 247u.toUByte(), 147u.toUByte(), 55u.toUByte(), 1u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBclriB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), 3), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBclriH() {
    val a = Simd<UShort>(listOf(2155u.toUShort(), 1155u.toUShort(), 155u.toUShort(), 1u.toUShort(), 2155u.toUShort(), 1155u.toUShort(), 155u.toUShort(), 1u.toUShort()))
    val r = Simd<UShort>(listOf(107u.toUShort(), 1155u.toUShort(), 155u.toUShort(), 1u.toUShort(), 107u.toUShort(), 1155u.toUShort(), 155u.toUShort(), 1u.toUShort()))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaBclriH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), 11), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBclriW() {
    val a = Simd<UInt>(listOf(211111155u, 111111155u, 11111155u, 1u))
    val r = Simd<UInt>(listOf(202722547u, 102722547u, 2722547u, 1u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaBclriW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), 23), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBclriD() {
    val a = Simd<ULong>(listOf(211111111155uL, 11111111111111155uL))
    val r = Simd<ULong>(listOf(73672157683uL, 11110973672157683uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaBclriD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), 37), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinslB() {
    val a = Simd<UByte>(listOf(255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte()))
    val b = Simd<UByte>(listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte()))
    val c = Simd<UByte>(listOf(1u.toUByte(), 3u.toUByte(), 5u.toUByte(), 9u.toUByte(), 1u.toUByte(), 3u.toUByte(), 5u.toUByte(), 9u.toUByte(), 1u.toUByte(), 3u.toUByte(), 5u.toUByte(), 9u.toUByte(), 1u.toUByte(), 3u.toUByte(), 5u.toUByte(), 9u.toUByte()))
    val r = Simd<UByte>(listOf(63u.toUByte(), 11u.toUByte(), 11u.toUByte(), 1u.toUByte(), 63u.toUByte(), 11u.toUByte(), 11u.toUByte(), 1u.toUByte(), 63u.toUByte(), 11u.toUByte(), 11u.toUByte(), 1u.toUByte(), 63u.toUByte(), 11u.toUByte(), 11u.toUByte(), 1u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBinslB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinslH() {
    val a = Simd<UShort>(listOf(32767u.toUShort(), 16384u.toUShort(), 8192u.toUShort(), 4096u.toUShort(), 32767u.toUShort(), 16384u.toUShort(), 8192u.toUShort(), 4096u.toUShort()))
    val b = Simd<UShort>(listOf(21656u.toUShort(), 5273u.toUShort(), 7081u.toUShort(), 2985u.toUShort(), 21656u.toUShort(), 5273u.toUShort(), 7081u.toUShort(), 2985u.toUShort()))
    val c = Simd<UShort>(listOf(3u.toUShort(), 7u.toUShort(), 9u.toUShort(), 13u.toUShort(), 15u.toUShort(), 17u.toUShort(), 21u.toUShort(), 23u.toUShort()))
    val r = Simd<UShort>(listOf(24575u.toUShort(), 5120u.toUShort(), 7040u.toUShort(), 2984u.toUShort(), 21656u.toUShort(), 0u.toUShort(), 6144u.toUShort(), 2816u.toUShort()))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaBinslH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinslW() {
    val a = Simd<UInt>(listOf(2147483647u, 536870912u, 67108864u, 8388608u))
    val b = Simd<UInt>(listOf(1036372536u, 259093134u, 78219975u, 1119499719u))
    val c = Simd<UInt>(listOf(11u, 15u, 31u, 37u))
    val r = Simd<UInt>(listOf(1037041663u, 259063808u, 78219975u, 1082130432u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaBinslW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinslD() {
    val a = Simd<ULong>(listOf(8006399338uL, 2882303762uL))
    val b = Simd<ULong>(listOf(9223372036854775805uL, 536870912uL))
    val c = Simd<ULong>(listOf(12uL, 48uL))
    val r = Simd<ULong>(listOf(9221120245047489898uL, 536901394uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaBinslD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<Any?, V2u64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<Any?, V2u64>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinsliB() {
    val a = Simd<UByte>(listOf(UByte.MAX_VALUE, 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), UByte.MAX_VALUE, 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), UByte.MAX_VALUE, 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), UByte.MAX_VALUE, 155u.toUByte(), 55u.toUByte(), 1u.toUByte()))
    val b = Simd<UByte>(listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte()))
    val r = Simd<UByte>(listOf(7u.toUByte(), 7u.toUByte(), 11u.toUByte(), 9u.toUByte(), 7u.toUByte(), 7u.toUByte(), 11u.toUByte(), 9u.toUByte(), 7u.toUByte(), 7u.toUByte(), 11u.toUByte(), 9u.toUByte(), 7u.toUByte(), 7u.toUByte(), 11u.toUByte(), 9u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBinsliB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), 5), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinsliH() {
    val a = Simd<UShort>(listOf(32767u.toUShort(), 16384u.toUShort(), 8192u.toUShort(), 4096u.toUShort(), 32767u.toUShort(), 16384u.toUShort(), 8192u.toUShort(), 4096u.toUShort()))
    val b = Simd<UShort>(listOf(21656u.toUShort(), 5273u.toUShort(), 7081u.toUShort(), 2985u.toUShort(), 21656u.toUShort(), 5273u.toUShort(), 7081u.toUShort(), 2985u.toUShort()))
    val r = Simd<UShort>(listOf(21659u.toUShort(), 5272u.toUShort(), 7080u.toUShort(), 2984u.toUShort(), 21659u.toUShort(), 5272u.toUShort(), 7080u.toUShort(), 2984u.toUShort()))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaBinsliH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), 13), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinsliW() {
    val a = Simd<UInt>(listOf(2147483647u, 536870912u, 67108864u, 8388608u))
    val b = Simd<UInt>(listOf(1036372536u, 259093134u, 78219975u, 1119499719u))
    val r = Simd<UInt>(listOf(1036386303u, 259080192u, 78217216u, 1119485952u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaBinsliW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), 17), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinsliD() {
    val a = Simd<ULong>(listOf(8006399338uL, 2882303762uL))
    val b = Simd<ULong>(listOf(9223372036854775805uL, 536870912uL))
    val r = Simd<ULong>(listOf(9223372036854773098uL, 536901394uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaBinsliD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<Any?, V2u64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), 48), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinsrB() {
    val a = Simd<UByte>(listOf(255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte()))
    val b = Simd<UByte>(listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte()))
    val c = Simd<UByte>(listOf(1u.toUByte(), 3u.toUByte(), 5u.toUByte(), 9u.toUByte(), 1u.toUByte(), 3u.toUByte(), 5u.toUByte(), 9u.toUByte(), 1u.toUByte(), 3u.toUByte(), 5u.toUByte(), 9u.toUByte(), 1u.toUByte(), 3u.toUByte(), 5u.toUByte(), 9u.toUByte()))
    val r = Simd<UByte>(listOf(254u.toUByte(), 151u.toUByte(), 8u.toUByte(), 1u.toUByte(), 254u.toUByte(), 151u.toUByte(), 8u.toUByte(), 1u.toUByte(), 254u.toUByte(), 151u.toUByte(), 8u.toUByte(), 1u.toUByte(), 254u.toUByte(), 151u.toUByte(), 8u.toUByte(), 1u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBinsrB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinsrH() {
    val a = Simd<UShort>(listOf(32767u.toUShort(), 16384u.toUShort(), 8192u.toUShort(), 4096u.toUShort(), 32767u.toUShort(), 16384u.toUShort(), 8192u.toUShort(), 4096u.toUShort()))
    val b = Simd<UShort>(listOf(21656u.toUShort(), 5273u.toUShort(), 7081u.toUShort(), 2985u.toUShort(), 21656u.toUShort(), 5273u.toUShort(), 7081u.toUShort(), 2985u.toUShort()))
    val c = Simd<UShort>(listOf(3u.toUShort(), 7u.toUShort(), 9u.toUShort(), 13u.toUShort(), 15u.toUShort(), 17u.toUShort(), 21u.toUShort(), 23u.toUShort()))
    val r = Simd<UShort>(listOf(32760u.toUShort(), 16537u.toUShort(), 9129u.toUShort(), 2985u.toUShort(), 21656u.toUShort(), 16385u.toUShort(), 8233u.toUShort(), 4265u.toUShort()))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaBinsrH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinsrW() {
    val a = Simd<UInt>(listOf(2147483647u, 536870912u, 67108864u, 8388608u))
    val b = Simd<UInt>(listOf(1036372536u, 259093134u, 78219975u, 1119499719u))
    val c = Simd<UInt>(listOf(11u, 15u, 31u, 37u))
    val r = Simd<UInt>(listOf(2147482168u, 536900238u, 78219975u, 8388615u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaBinsrW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinsrD() {
    val a = Simd<ULong>(listOf(8006399338uL, 2882303762uL))
    val b = Simd<ULong>(listOf(9223372036854775805uL, 536870912uL))
    val c = Simd<ULong>(listOf(12uL, 48uL))
    val r = Simd<ULong>(listOf(8006402045uL, 536870912uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaBinsrD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<Any?, V2u64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<Any?, V2u64>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinsriB() {
    val a = Simd<UByte>(listOf(255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte()))
    val b = Simd<UByte>(listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte()))
    val r = Simd<UByte>(listOf(198u.toUByte(), 135u.toUByte(), 8u.toUByte(), 9u.toUByte(), 198u.toUByte(), 135u.toUByte(), 8u.toUByte(), 9u.toUByte(), 198u.toUByte(), 135u.toUByte(), 8u.toUByte(), 9u.toUByte(), 198u.toUByte(), 135u.toUByte(), 8u.toUByte(), 9u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBinsriB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), 5), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinsriH() {
    val a = Simd<UShort>(listOf(32767u.toUShort(), 16384u.toUShort(), 8192u.toUShort(), 4096u.toUShort(), 32767u.toUShort(), 16384u.toUShort(), 8192u.toUShort(), 4096u.toUShort()))
    val b = Simd<UShort>(listOf(21656u.toUShort(), 5273u.toUShort(), 7081u.toUShort(), 2985u.toUShort(), 21656u.toUShort(), 5273u.toUShort(), 7081u.toUShort(), 2985u.toUShort()))
    val r = Simd<UShort>(listOf(21656u.toUShort(), 21657u.toUShort(), 7081u.toUShort(), 2985u.toUShort(), 21656u.toUShort(), 21657u.toUShort(), 7081u.toUShort(), 2985u.toUShort()))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaBinsriH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), 13), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinsriW() {
    val a = Simd<UInt>(listOf(2147483647u, 536870912u, 67108864u, 8388608u))
    val b = Simd<UInt>(listOf(1036372536u, 259093134u, 78219975u, 1119499719u))
    val r = Simd<UInt>(listOf(2147338808u, 536965774u, 67209927u, 8533447u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaBinsriW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), 17), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinsriD() {
    val a = Simd<ULong>(listOf(8006399338uL, 2882303762uL))
    val b = Simd<ULong>(listOf(9223372036854775805uL, 536870912uL))
    val r = Simd<ULong>(listOf(562949953421309uL, 536870912uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaBinsriD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<Any?, V2u64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), 48), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBmnzV() {
    val a = Simd<UByte>(listOf(UByte.MAX_VALUE, 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), UByte.MAX_VALUE, 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), UByte.MAX_VALUE, 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), UByte.MAX_VALUE, 155u.toUByte(), 55u.toUByte(), 1u.toUByte()))
    val b = Simd<UByte>(listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte()))
    val c = Simd<UByte>(listOf(3u.toUByte(), 5u.toUByte(), 7u.toUByte(), 1u.toUByte(), 3u.toUByte(), 5u.toUByte(), 7u.toUByte(), 1u.toUByte(), 3u.toUByte(), 5u.toUByte(), 7u.toUByte(), 1u.toUByte(), 3u.toUByte(), 5u.toUByte(), 7u.toUByte(), 1u.toUByte()))
    val r = Simd<UByte>(listOf(254u.toUByte(), 159u.toUByte(), 48u.toUByte(), 1u.toUByte(), 254u.toUByte(), 159u.toUByte(), 48u.toUByte(), 1u.toUByte(), 254u.toUByte(), 159u.toUByte(), 48u.toUByte(), 1u.toUByte(), 254u.toUByte(), 159u.toUByte(), 48u.toUByte(), 1u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBmnzV(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBmnziB() {
    val a = Simd<UByte>(listOf(UByte.MAX_VALUE, 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), UByte.MAX_VALUE, 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), UByte.MAX_VALUE, 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), UByte.MAX_VALUE, 155u.toUByte(), 55u.toUByte(), 1u.toUByte()))
    val b = Simd<UByte>(listOf(1u.toUByte(), UByte.MAX_VALUE, 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), UByte.MAX_VALUE, 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), UByte.MAX_VALUE, 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), UByte.MAX_VALUE, 155u.toUByte(), 55u.toUByte()))
    val r = Simd<UByte>(listOf(249u.toUByte(), 159u.toUByte(), 51u.toUByte(), 7u.toUByte(), 249u.toUByte(), 159u.toUByte(), 51u.toUByte(), 7u.toUByte(), 249u.toUByte(), 159u.toUByte(), 51u.toUByte(), 7u.toUByte(), 249u.toUByte(), 159u.toUByte(), 51u.toUByte(), 7u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBmnziB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), 7), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBmzV() {
    val a = Simd<UByte>(listOf(UByte.MAX_VALUE, 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), UByte.MAX_VALUE, 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), UByte.MAX_VALUE, 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), UByte.MAX_VALUE, 155u.toUByte(), 55u.toUByte(), 1u.toUByte()))
    val b = Simd<UByte>(listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte()))
    val c = Simd<UByte>(listOf(3u.toUByte(), 5u.toUByte(), 7u.toUByte(), 1u.toUByte(), 3u.toUByte(), 5u.toUByte(), 7u.toUByte(), 1u.toUByte(), 3u.toUByte(), 5u.toUByte(), 7u.toUByte(), 1u.toUByte(), 3u.toUByte(), 5u.toUByte(), 7u.toUByte(), 1u.toUByte()))
    val r = Simd<UByte>(listOf(7u.toUByte(), 3u.toUByte(), 15u.toUByte(), 9u.toUByte(), 7u.toUByte(), 3u.toUByte(), 15u.toUByte(), 9u.toUByte(), 7u.toUByte(), 3u.toUByte(), 15u.toUByte(), 9u.toUByte(), 7u.toUByte(), 3u.toUByte(), 15u.toUByte(), 9u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBmzV(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBmziB() {
    val a = Simd<UByte>(listOf(UByte.MAX_VALUE, 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), UByte.MAX_VALUE, 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), UByte.MAX_VALUE, 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), UByte.MAX_VALUE, 155u.toUByte(), 55u.toUByte(), 1u.toUByte()))
    val b = Simd<UByte>(listOf(1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte()))
    val r = Simd<UByte>(listOf(7u.toUByte(), 251u.toUByte(), 159u.toUByte(), 49u.toUByte(), 7u.toUByte(), 251u.toUByte(), 159u.toUByte(), 49u.toUByte(), 7u.toUByte(), 251u.toUByte(), 159u.toUByte(), 49u.toUByte(), 7u.toUByte(), 251u.toUByte(), 159u.toUByte(), 49u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBmziB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), 7), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnegB() {
    val a = Simd<UByte>(listOf(255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte()))
    val b = Simd<UByte>(listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte()))
    val r = Simd<UByte>(listOf(191u.toUByte(), 27u.toUByte(), 54u.toUByte(), 3u.toUByte(), 191u.toUByte(), 27u.toUByte(), 54u.toUByte(), 3u.toUByte(), 191u.toUByte(), 27u.toUByte(), 54u.toUByte(), 3u.toUByte(), 191u.toUByte(), 27u.toUByte(), 54u.toUByte(), 3u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBnegB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnegH() {
    val a = Simd<UShort>(listOf(255u.toUShort(), 155u.toUShort(), 55u.toUShort(), 1u.toUShort(), 255u.toUShort(), 155u.toUShort(), 55u.toUShort(), 1u.toUShort()))
    val b = Simd<UShort>(listOf(6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort(), 6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort()))
    val r = Simd<UShort>(listOf(191u.toUShort(), 27u.toUShort(), 311u.toUShort(), 513u.toUShort(), 191u.toUShort(), 27u.toUShort(), 311u.toUShort(), 513u.toUShort()))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaBnegH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnegW() {
    val a = Simd<UInt>(listOf(255u, 155u, 55u, 1u))
    val b = Simd<UInt>(listOf(6u, 7u, 8u, 9u))
    val r = Simd<UInt>(listOf(191u, 27u, 311u, 513u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaBnegW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnegD() {
    val a = Simd<ULong>(listOf(255uL, 155uL))
    val b = Simd<ULong>(listOf(6uL, 7uL))
    val r = Simd<ULong>(listOf(191uL, 27uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaBnegD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<Any?, V2u64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnegiB() {
    val a = Simd<UByte>(listOf(50u.toUByte(), 100u.toUByte(), 127u.toUByte(), UByte.MAX_VALUE, 50u.toUByte(), 100u.toUByte(), 127u.toUByte(), UByte.MAX_VALUE, 50u.toUByte(), 100u.toUByte(), 127u.toUByte(), UByte.MAX_VALUE, 50u.toUByte(), 100u.toUByte(), 127u.toUByte(), UByte.MAX_VALUE))
    val r = Simd<UByte>(listOf(34u.toUByte(), 116u.toUByte(), 111u.toUByte(), 239u.toUByte(), 34u.toUByte(), 116u.toUByte(), 111u.toUByte(), 239u.toUByte(), 34u.toUByte(), 116u.toUByte(), 111u.toUByte(), 239u.toUByte(), 34u.toUByte(), 116u.toUByte(), 111u.toUByte(), 239u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBnegiB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), 4), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnegiH() {
    val a = Simd<UShort>(listOf(32767u.toUShort(), 3276u.toUShort(), 100u.toUShort(), 127u.toUShort(), 32767u.toUShort(), 3276u.toUShort(), 100u.toUShort(), 127u.toUShort()))
    val r = Simd<UShort>(listOf(30719u.toUShort(), 1228u.toUShort(), 2148u.toUShort(), 2175u.toUShort(), 30719u.toUShort(), 1228u.toUShort(), 2148u.toUShort(), 2175u.toUShort()))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaBnegiH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), 11), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnegiW() {
    val a = Simd<UInt>(listOf(100u, 2147483647u, 100u, 2147483648u))
    val r = Simd<UInt>(listOf(16777316u, 2130706431u, 16777316u, 2164260864u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaBnegiW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), 24), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnegiD() {
    val a = Simd<ULong>(listOf(100uL, 9223372036854775808uL))
    val r = Simd<ULong>(listOf(4398046511204uL, 9223376434901286912uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaBnegiD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), 42), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnzB() {
    val a = Simd<UByte>(listOf(1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 2u.toUByte(), 2u.toUByte(), 2u.toUByte(), 2u.toUByte(), 4u.toUByte(), 4u.toUByte(), 0u.toUByte(), 4u.toUByte()))
    val r = 0
    check(r == transmuteWithLayouts<Int, Unit>(msaBnzB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnzH() {
    val a = Simd<UShort>(listOf(32767u.toUShort(), 3276u.toUShort(), 100u.toUShort(), 127u.toUShort(), 32767u.toUShort(), 0u.toUShort(), 100u.toUShort(), 127u.toUShort()))
    val r = 0
    check(r == transmuteWithLayouts<Int, Unit>(msaBnzH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnzW() {
    val a = Simd<UInt>(listOf(100u, 2147483647u, 0u, 2147483648u))
    val r = 0
    check(r == transmuteWithLayouts<Int, Unit>(msaBnzW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnzD() {
    val a = Simd<ULong>(listOf(100uL, 9223372036854775808uL))
    val r = 1
    check(r == transmuteWithLayouts<Int, Unit>(msaBnzD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnzV() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 1u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte()))
    val r = 1
    check(r == transmuteWithLayouts<Int, Unit>(msaBnzV(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBselV() {
    val a = Simd<UByte>(listOf(3u.toUByte(), 5u.toUByte(), 7u.toUByte(), 1u.toUByte(), 3u.toUByte(), 5u.toUByte(), 7u.toUByte(), 1u.toUByte(), 3u.toUByte(), 5u.toUByte(), 7u.toUByte(), 1u.toUByte(), 3u.toUByte(), 5u.toUByte(), 7u.toUByte(), 1u.toUByte()))
    val b = Simd<UByte>(listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte()))
    val c = Simd<UByte>(listOf(255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte()))
    val r = Simd<UByte>(listOf(7u.toUByte(), 3u.toUByte(), 15u.toUByte(), 9u.toUByte(), 7u.toUByte(), 3u.toUByte(), 15u.toUByte(), 9u.toUByte(), 7u.toUByte(), 3u.toUByte(), 15u.toUByte(), 9u.toUByte(), 7u.toUByte(), 3u.toUByte(), 15u.toUByte(), 9u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBselV(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBseliB() {
    val a = Simd<UByte>(listOf(255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte()))
    val b = Simd<UByte>(listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte()))
    val r = Simd<UByte>(listOf(121u.toUByte(), 29u.toUByte(), 57u.toUByte(), 9u.toUByte(), 121u.toUByte(), 29u.toUByte(), 57u.toUByte(), 9u.toUByte(), 121u.toUByte(), 29u.toUByte(), 57u.toUByte(), 9u.toUByte(), 121u.toUByte(), 29u.toUByte(), 57u.toUByte(), 9u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBseliB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), 121), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBsetB() {
    val a = Simd<UByte>(listOf(255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte()))
    val b = Simd<UByte>(listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte()))
    val r = Simd<UByte>(listOf(255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 3u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 3u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 3u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 3u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBsetB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBsetH() {
    val a = Simd<UShort>(listOf(255u.toUShort(), 155u.toUShort(), 55u.toUShort(), 1u.toUShort(), 255u.toUShort(), 155u.toUShort(), 55u.toUShort(), 1u.toUShort()))
    val b = Simd<UShort>(listOf(6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort(), 6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort()))
    val r = Simd<UShort>(listOf(255u.toUShort(), 155u.toUShort(), 311u.toUShort(), 513u.toUShort(), 255u.toUShort(), 155u.toUShort(), 311u.toUShort(), 513u.toUShort()))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaBsetH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBsetW() {
    val a = Simd<UInt>(listOf(255u, 155u, 55u, 1u))
    val b = Simd<UInt>(listOf(6u, 7u, 8u, 9u))
    val r = Simd<UInt>(listOf(255u, 155u, 311u, 513u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaBsetW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBsetD() {
    val a = Simd<ULong>(listOf(255uL, 155uL))
    val b = Simd<ULong>(listOf(6uL, 7uL))
    val r = Simd<ULong>(listOf(255uL, 155uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaBsetD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<Any?, V2u64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBsetiB() {
    val a = Simd<UByte>(listOf(255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte()))
    val r = Simd<UByte>(listOf(255u.toUByte(), 159u.toUByte(), 55u.toUByte(), 5u.toUByte(), 255u.toUByte(), 159u.toUByte(), 55u.toUByte(), 5u.toUByte(), 255u.toUByte(), 159u.toUByte(), 55u.toUByte(), 5u.toUByte(), 255u.toUByte(), 159u.toUByte(), 55u.toUByte(), 5u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBsetiB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), 2), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBsetiH() {
    val a = Simd<UShort>(listOf(255u.toUShort(), 155u.toUShort(), 55u.toUShort(), 1u.toUShort(), 255u.toUShort(), 155u.toUShort(), 55u.toUShort(), 1u.toUShort()))
    val r = Simd<UShort>(listOf(255u.toUShort(), 159u.toUShort(), 55u.toUShort(), 5u.toUShort(), 255u.toUShort(), 159u.toUShort(), 55u.toUShort(), 5u.toUShort()))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaBsetiH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), 2), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBsetiW() {
    val a = Simd<UInt>(listOf(255u, 155u, 55u, 1u))
    val r = Simd<UInt>(listOf(255u, 159u, 55u, 5u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaBsetiW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), 2), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBsetiD() {
    val a = Simd<ULong>(listOf(255uL, 155uL))
    val r = Simd<ULong>(listOf(255uL, 159uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaBsetiD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), 2), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBzB() {
    val a = Simd<UByte>(listOf(255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte(), 255u.toUByte(), 155u.toUByte(), 55u.toUByte(), 1u.toUByte()))
    val r = 0
    check(r == transmuteWithLayouts<Int, Unit>(msaBzB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBzH() {
    val a = Simd<UShort>(listOf(0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort()))
    val r = 1
    check(r == transmuteWithLayouts<Int, Unit>(msaBzH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBzW() {
    val a = Simd<UInt>(listOf(255u, 0u, 55u, 1u))
    val r = 1
    check(r == transmuteWithLayouts<Int, Unit>(msaBzW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBzD() {
    val a = Simd<ULong>(listOf(255uL, 0uL))
    val r = 1
    check(r == transmuteWithLayouts<Int, Unit>(msaBzD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBzV() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte()))
    val r = 1
    check(r == transmuteWithLayouts<Int, Unit>(msaBzV(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCeqB() {
    val a = Simd<Byte>(listOf((-128).toByte(), 127.toByte(), 55.toByte(), 1.toByte(), (-128).toByte(), 127.toByte(), 55.toByte(), 1.toByte(), (-128).toByte(), 127.toByte(), 55.toByte(), 1.toByte(), (-128).toByte(), 127.toByte(), 55.toByte(), 1.toByte()))
    val b = Simd<Byte>(listOf((-128).toByte(), 126.toByte(), 55.toByte(), 1.toByte(), (-128).toByte(), 126.toByte(), 55.toByte(), 1.toByte(), (-128).toByte(), 126.toByte(), 55.toByte(), 1.toByte(), (-128).toByte(), 126.toByte(), 55.toByte(), 1.toByte()))
    val r = Simd<Byte>(listOf((-1).toByte(), 0.toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), (-1).toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaCeqB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCeqH() {
    val a = Simd<Short>(listOf(255.toShort(), 155.toShort(), 55.toShort(), 1.toShort(), 255.toShort(), 155.toShort(), 55.toShort(), 1.toShort()))
    val b = Simd<Short>(listOf(255.toShort(), 155.toShort(), 56.toShort(), 1.toShort(), 255.toShort(), 155.toShort(), 56.toShort(), 1.toShort()))
    val r = Simd<Short>(listOf((-1).toShort(), (-1).toShort(), 0.toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), 0.toShort(), (-1).toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaCeqH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCeqW() {
    val a = Simd<Int>(listOf(255, 155, 55, 1))
    val b = Simd<Int>(listOf(255, 156, 55, 1))
    val r = Simd<Int>(listOf(-1, 0, -1, -1))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaCeqW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCeqD() {
    val a = Simd<Long>(listOf(255L, 155L))
    val b = Simd<Long>(listOf(255L, 156L))
    val r = Simd<Long>(listOf(-1L, 0L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaCeqD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCeqiB() {
    val a = Simd<Byte>(listOf(100.toByte(), (-1).toByte(), (-4).toByte(), 15.toByte(), 100.toByte(), (-1).toByte(), (-4).toByte(), 15.toByte(), 100.toByte(), (-1).toByte(), (-4).toByte(), 15.toByte(), 100.toByte(), (-1).toByte(), (-4).toByte(), 15.toByte()))
    val r = Simd<Byte>(listOf(0.toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), 0.toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), 0.toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), 0.toByte(), 0.toByte(), (-1).toByte(), 0.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaCeqiB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), -4), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCeqiH() {
    val a = Simd<Short>(listOf(32767.toShort(), 3276.toShort(), 100.toShort(), (-11).toShort(), 32767.toShort(), 3276.toShort(), 100.toShort(), (-11).toShort()))
    val r = Simd<Short>(listOf(0.toShort(), 0.toShort(), 0.toShort(), (-1).toShort(), 0.toShort(), 0.toShort(), 0.toShort(), (-1).toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaCeqiH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), -11), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCeqiW() {
    val a = Simd<Int>(listOf(1, 3, 5, -3))
    val r = Simd<Int>(listOf(0, 0, -1, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaCeqiW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), 5), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

// Note: https://reviews.llvm.org/D59884
// If target type is i64, negative immediate loses the sign
// Test passes if 4294967293 is used instead -3 in vector `a`
// @simdTest(enable = "msa")
// unchecked fn testMsaCeqiD() (
// #[rustfmt.skip]
// let a = i64x2.new(-3, 2);
// #[rustfmt.skip]
// let r = i64x2.new(-1, 0);
// assertEq(r, mem.transmute(__msa_ceqi_d(mem.transmute(a), -3)));
// )
// Can not be tested in user mode
// @simdTest(enable = "msa")
// unchecked fn testMsaCfcmsa() (
// let r = 5;
// assertEq(r, mem.transmute(__msa_cfcmsa(5));
// )
public fun testMsaCleSB() {
    val a = Simd<Byte>(listOf((-128).toByte(), 127.toByte(), 55.toByte(), 2.toByte(), (-128).toByte(), 127.toByte(), 55.toByte(), 2.toByte(), (-128).toByte(), 127.toByte(), 55.toByte(), 2.toByte(), (-128).toByte(), 127.toByte(), 55.toByte(), 2.toByte()))
    val b = Simd<Byte>(listOf((-128).toByte(), 126.toByte(), 55.toByte(), 1.toByte(), (-128).toByte(), 126.toByte(), 55.toByte(), 1.toByte(), (-128).toByte(), 126.toByte(), 55.toByte(), 1.toByte(), (-128).toByte(), 126.toByte(), 55.toByte(), 1.toByte()))
    val r = Simd<Byte>(listOf((-1).toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), 0.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaCleSB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleSH() {
    val a = Simd<Short>(listOf(255.toShort(), 155.toShort(), 55.toShort(), 2.toShort(), 255.toShort(), 155.toShort(), 55.toShort(), 2.toShort()))
    val b = Simd<Short>(listOf(255.toShort(), 155.toShort(), 56.toShort(), 1.toShort(), 255.toShort(), 155.toShort(), 56.toShort(), 1.toShort()))
    val r = Simd<Short>(listOf((-1).toShort(), (-1).toShort(), (-1).toShort(), 0.toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), 0.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaCleSH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleSW() {
    val a = Simd<Int>(listOf(255, 155, 55, 2))
    val b = Simd<Int>(listOf(255, 156, 55, 1))
    val r = Simd<Int>(listOf(-1, -1, -1, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaCleSW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleSD() {
    val a = Simd<Long>(listOf(255L, 155L))
    val b = Simd<Long>(listOf(255L, 156L))
    val r = Simd<Long>(listOf(-1L, -1L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaCleSD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleUB() {
    val a = Simd<UByte>(listOf(UByte.MAX_VALUE, 127u.toUByte(), 55u.toUByte(), 2u.toUByte(), UByte.MAX_VALUE, 127u.toUByte(), 55u.toUByte(), 2u.toUByte(), UByte.MAX_VALUE, 127u.toUByte(), 55u.toUByte(), 2u.toUByte(), UByte.MAX_VALUE, 127u.toUByte(), 55u.toUByte(), 2u.toUByte()))
    val b = Simd<UByte>(listOf(UByte.MAX_VALUE, 126u.toUByte(), 55u.toUByte(), 1u.toUByte(), UByte.MAX_VALUE, 126u.toUByte(), 55u.toUByte(), 1u.toUByte(), UByte.MAX_VALUE, 126u.toUByte(), 55u.toUByte(), 1u.toUByte(), UByte.MAX_VALUE, 126u.toUByte(), 55u.toUByte(), 1u.toUByte()))
    val r = Simd<Byte>(listOf((-1).toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), 0.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaCleUB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleUH() {
    val a = Simd<UShort>(listOf(UShort.MAX_VALUE, 155u.toUShort(), 55u.toUShort(), 2u.toUShort(), UShort.MAX_VALUE, 155u.toUShort(), 55u.toUShort(), 2u.toUShort()))
    val b = Simd<UShort>(listOf(UShort.MAX_VALUE, 155u.toUShort(), 56u.toUShort(), 1u.toUShort(), UShort.MAX_VALUE, 155u.toUShort(), 56u.toUShort(), 1u.toUShort()))
    val r = Simd<Short>(listOf((-1).toShort(), (-1).toShort(), (-1).toShort(), 0.toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), 0.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaCleUH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleUW() {
    val a = Simd<UInt>(listOf(UInt.MAX_VALUE, 155u, 55u, 2u))
    val b = Simd<UInt>(listOf(UInt.MAX_VALUE, 156u, 55u, 1u))
    val r = Simd<Int>(listOf(-1, -1, -1, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaCleUW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleUD() {
    val a = Simd<ULong>(listOf(ULong.MAX_VALUE, 155uL))
    val b = Simd<ULong>(listOf(ULong.MAX_VALUE, 156uL))
    val r = Simd<Long>(listOf(-1L, -1L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaCleUD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<Any?, V2u64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleiSB() {
    val a = Simd<Byte>(listOf((-2).toByte(), (-127).toByte(), 100.toByte(), (-127).toByte(), (-2).toByte(), (-127).toByte(), 100.toByte(), (-127).toByte(), (-2).toByte(), (-127).toByte(), 100.toByte(), (-127).toByte(), (-2).toByte(), (-127).toByte(), 100.toByte(), (-127).toByte()))
    val r = Simd<Byte>(listOf((-1).toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 0.toByte(), (-1).toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaCleiSB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), -2), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleiSH() {
    val a = Simd<Short>(listOf(32767.toShort(), 3276.toShort(), 10.toShort(), (-1).toShort(), 32767.toShort(), 3276.toShort(), 10.toShort(), (-1).toShort()))
    val r = Simd<Short>(listOf(0.toShort(), 0.toShort(), 0.toShort(), (-1).toShort(), 0.toShort(), 0.toShort(), 0.toShort(), (-1).toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaCleiSH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), -1), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleiSW() {
    val a = Simd<Int>(listOf(100, 2147483647, 6, 2147483647))
    val r = Simd<Int>(listOf(0, 0, -1, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaCleiSW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), 6), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

// Note: https://reviews.llvm.org/D59884
// If target type is i64, negative immediate loses the sign
// -3 is represented as 4294967293
// @simdTest(enable = "msa")
// unchecked fn testMsaCleiSD() (
// #[rustfmt.skip]
// let a = i64x2.new(-3, 11);
// #[rustfmt.skip]
// let r = i64x2.new(-1, 0);
// assertEq(r, mem.transmute(__msa_clei_s_d(mem.transmute(a), -3)));
// )
public fun testMsaCleiUB() {
    val a = Simd<UByte>(listOf(2u.toUByte(), 127u.toUByte(), 100u.toUByte(), 127u.toUByte(), 2u.toUByte(), 127u.toUByte(), 100u.toUByte(), 127u.toUByte(), 2u.toUByte(), 127u.toUByte(), 100u.toUByte(), 127u.toUByte(), 2u.toUByte(), 127u.toUByte(), 100u.toUByte(), 127u.toUByte()))
    val r = Simd<Byte>(listOf((-1).toByte(), 0.toByte(), 0.toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), 0.toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), 0.toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), 0.toByte(), 0.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaCleiUB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), 25), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleiUH() {
    val a = Simd<UShort>(listOf(1u.toUShort(), 26u.toUShort(), 15u.toUShort(), 36u.toUShort(), 1u.toUShort(), 26u.toUShort(), 15u.toUShort(), 36u.toUShort()))
    val r = Simd<Short>(listOf((-1).toShort(), 0.toShort(), (-1).toShort(), 0.toShort(), (-1).toShort(), 0.toShort(), (-1).toShort(), 0.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaCleiUH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), 25), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleiUW() {
    val a = Simd<UInt>(listOf(25u, 32u, 25u, 32u))
    val r = Simd<Int>(listOf(-1, 0, -1, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaCleiUW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), 31), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleiUD() {
    val a = Simd<ULong>(listOf(10uL, 26uL))
    val r = Simd<Long>(listOf(-1L, 0L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaCleiUD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), 25), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltSB() {
    val a = Simd<Byte>(listOf((-128).toByte(), 127.toByte(), 55.toByte(), 2.toByte(), (-128).toByte(), 127.toByte(), 55.toByte(), 2.toByte(), (-128).toByte(), 127.toByte(), 55.toByte(), 2.toByte(), (-128).toByte(), 127.toByte(), 55.toByte(), 2.toByte()))
    val b = Simd<Byte>(listOf((-127).toByte(), 126.toByte(), 56.toByte(), 1.toByte(), (-127).toByte(), 126.toByte(), 56.toByte(), 1.toByte(), (-127).toByte(), 126.toByte(), 56.toByte(), 1.toByte(), (-127).toByte(), 126.toByte(), 56.toByte(), 1.toByte()))
    val r = Simd<Byte>(listOf((-1).toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), 0.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaCltSB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltSH() {
    val a = Simd<Short>(listOf((-255).toShort(), 155.toShort(), 55.toShort(), 2.toShort(), (-255).toShort(), 155.toShort(), 55.toShort(), 2.toShort()))
    val b = Simd<Short>(listOf(255.toShort(), 156.toShort(), 56.toShort(), 1.toShort(), 255.toShort(), 156.toShort(), 56.toShort(), 1.toShort()))
    val r = Simd<Short>(listOf((-1).toShort(), (-1).toShort(), (-1).toShort(), 0.toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), 0.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaCltSH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltSW() {
    val a = Simd<Int>(listOf(-255, 155, 55, 2))
    val b = Simd<Int>(listOf(255, 156, 55, 1))
    val r = Simd<Int>(listOf(-1, -1, 0, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaCltSW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltSD() {
    val a = Simd<Long>(listOf(-255L, 155L))
    val b = Simd<Long>(listOf(255L, 156L))
    val r = Simd<Long>(listOf(-1L, -1L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaCltSD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltUB() {
    val a = Simd<UByte>(listOf(128u.toUByte(), 127u.toUByte(), 55u.toUByte(), 2u.toUByte(), 128u.toUByte(), 127u.toUByte(), 55u.toUByte(), 2u.toUByte(), 128u.toUByte(), 127u.toUByte(), 55u.toUByte(), 2u.toUByte(), 128u.toUByte(), 127u.toUByte(), 55u.toUByte(), 2u.toUByte()))
    val b = Simd<UByte>(listOf(127u.toUByte(), 126u.toUByte(), 56u.toUByte(), 1u.toUByte(), 127u.toUByte(), 126u.toUByte(), 56u.toUByte(), 1u.toUByte(), 127u.toUByte(), 126u.toUByte(), 56u.toUByte(), 1u.toUByte(), 127u.toUByte(), 126u.toUByte(), 56u.toUByte(), 1u.toUByte()))
    val r = Simd<Byte>(listOf(0.toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), 0.toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), 0.toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), 0.toByte(), 0.toByte(), (-1).toByte(), 0.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaCltUB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltUH() {
    val a = Simd<UShort>(listOf(255u.toUShort(), 155u.toUShort(), 55u.toUShort(), 2u.toUShort(), 255u.toUShort(), 155u.toUShort(), 55u.toUShort(), 2u.toUShort()))
    val b = Simd<UShort>(listOf(255u.toUShort(), 156u.toUShort(), 56u.toUShort(), 1u.toUShort(), 255u.toUShort(), 156u.toUShort(), 56u.toUShort(), 1u.toUShort()))
    val r = Simd<Short>(listOf(0.toShort(), (-1).toShort(), (-1).toShort(), 0.toShort(), 0.toShort(), (-1).toShort(), (-1).toShort(), 0.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaCltUH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltUW() {
    val a = Simd<UInt>(listOf(255u, 155u, 55u, 2u))
    val b = Simd<UInt>(listOf(255u, 156u, 55u, 1u))
    val r = Simd<Int>(listOf(0, -1, 0, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaCltUW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltUD() {
    val a = Simd<ULong>(listOf(255uL, 155uL))
    val b = Simd<ULong>(listOf(255uL, 156uL))
    val r = Simd<Long>(listOf(0L, -1L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaCltUD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<Any?, V2u64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltiSB() {
    val a = Simd<Byte>(listOf(2.toByte(), (-127).toByte(), (-5).toByte(), 127.toByte(), 2.toByte(), (-127).toByte(), (-5).toByte(), 127.toByte(), 2.toByte(), (-127).toByte(), (-5).toByte(), 127.toByte(), 2.toByte(), (-127).toByte(), (-5).toByte(), 127.toByte()))
    val r = Simd<Byte>(listOf(0.toByte(), (-1).toByte(), 0.toByte(), 0.toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), 0.toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), 0.toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), 0.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaCltiSB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), -5), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltiSH() {
    val a = Simd<Short>(listOf((-1024).toShort(), 3276.toShort(), 15.toShort(), 127.toShort(), (-1024).toShort(), 3276.toShort(), 15.toShort(), 127.toShort()))
    val r = Simd<Short>(listOf((-1).toShort(), 0.toShort(), 0.toShort(), 0.toShort(), (-1).toShort(), 0.toShort(), 0.toShort(), 0.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaCltiSH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), 15), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltiSW() {
    val a = Simd<Int>(listOf(-15, 2147483647, -15, 2147483647))
    val r = Simd<Int>(listOf(-1, 0, -1, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaCltiSW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), -10), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

// Note: https://reviews.llvm.org/D59884
// If target type is i64, negative immediate loses the sign
// -3 is represented as 4294967293
// @simdTest(enable = "msa")
// unchecked fn testMsaCltiSD() (
// #[rustfmt.skip]
// let a = i64x2.new(-5, -2);
// #[rustfmt.skip]
// let r = i64x2.new(-1, 0);
// assertEq(r, mem.transmute(__msa_clti_s_d(mem.transmute(a), -3)));
// )
public fun testMsaCltiUB() {
    val a = Simd<UByte>(listOf(2u.toUByte(), 127u.toUByte(), 49u.toUByte(), 127u.toUByte(), 2u.toUByte(), 127u.toUByte(), 49u.toUByte(), 127u.toUByte(), 2u.toUByte(), 127u.toUByte(), 49u.toUByte(), 127u.toUByte(), 2u.toUByte(), 127u.toUByte(), 49u.toUByte(), 127u.toUByte()))
    val r = Simd<Byte>(listOf((-1).toByte(), 0.toByte(), 0.toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), 0.toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), 0.toByte(), 0.toByte(), (-1).toByte(), 0.toByte(), 0.toByte(), 0.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaCltiUB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), 3), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltiUH() {
    val a = Simd<UShort>(listOf(327u.toUShort(), 3276u.toUShort(), 100u.toUShort(), 127u.toUShort(), 327u.toUShort(), 3276u.toUShort(), 100u.toUShort(), 127u.toUShort()))
    val r = Simd<Short>(listOf(0.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaCltiUH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), 30), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltiUW() {
    val a = Simd<UInt>(listOf(100u, 2147483647u, 100u, 2147483647u))
    val r = Simd<Int>(listOf(0, 0, 0, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaCltiUW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), 10), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltiUD() {
    val a = Simd<ULong>(listOf(1uL, 9223372036854775807uL))
    val r = Simd<Long>(listOf(-1L, 0L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaCltiUD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), 10), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCopySB() {
    val a = Simd<Byte>(listOf((-100).toByte(), 127.toByte(), 4.toByte(), 127.toByte(), (-100).toByte(), 127.toByte(), 4.toByte(), 127.toByte(), (-100).toByte(), 127.toByte(), 4.toByte(), 127.toByte(), (-100).toByte(), 127.toByte(), 4.toByte(), 127.toByte()))
    val r = -100
    check(r == transmuteWithLayouts<Int, Unit>(msaCopySB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), 12), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCopySH() {
    val a = Simd<Short>(listOf(32767.toShort(), 3276.toShort(), 100.toShort(), 11.toShort(), 32767.toShort(), 3276.toShort(), 100.toShort(), 11.toShort()))
    val r = 32767
    check(r == transmuteWithLayouts<Int, Unit>(msaCopySH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), 4), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCopySW() {
    val a = Simd<Int>(listOf(100, 2147483647, 5, -2147483647))
    val r = 2147483647
    check(r == transmuteWithLayouts<Int, Unit>(msaCopySW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), 1), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCopySD() {
    val a = Simd<Long>(listOf(3L, 9223372036854775807L))
    val r = 9223372036854775807.toLong()
    check(r == transmuteWithLayouts<Long, Unit>(msaCopySD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), 1L), UnresolvedTypeLayout<Long>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCopyUB() {
    val a = Simd<Byte>(listOf(100.toByte(), 127.toByte(), 4.toByte(), 127.toByte(), 100.toByte(), 127.toByte(), 4.toByte(), 127.toByte(), 100.toByte(), 127.toByte(), 4.toByte(), 127.toByte(), 100.toByte(), 127.toByte(), 4.toByte(), 127.toByte()))
    val r = 100.toUInt()
    check(r == transmuteWithLayouts<UInt, Unit>(msaCopyUB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), 12u), UnresolvedTypeLayout<UInt>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCopyUH() {
    val a = Simd<Short>(listOf(32767.toShort(), 3276.toShort(), 100.toShort(), 11.toShort(), 32767.toShort(), 3276.toShort(), 100.toShort(), 11.toShort()))
    val r = 32767.toUInt()
    check(r == transmuteWithLayouts<UInt, Unit>(msaCopyUH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), 4u), UnresolvedTypeLayout<UInt>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCopyUW() {
    val a = Simd<Int>(listOf(100, 2147483647, 5, 2147483647))
    val r = 2147483647.toUInt()
    check(r == transmuteWithLayouts<UInt, Unit>(msaCopyUW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), 1u), UnresolvedTypeLayout<UInt>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCopyUD() {
    val a = Simd<Long>(listOf(3L, Long.MAX_VALUE))
    val r = 9223372036854775807.toULong()
    check(r == transmuteWithLayouts<ULong, Unit>(msaCopyUD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), 1uL), UnresolvedTypeLayout<ULong>(), UnresolvedTypeLayout<Unit>()))
}

// Can not be tested in user mode
// @simdTest(enable = "msa")
// unchecked fn testMsaCtcmsa() (
// )
public fun testMsaDivSB() {
    val a = Simd<Byte>(listOf((-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte()))
    val b = Simd<Byte>(listOf((-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte()))
    val r = Simd<Byte>(listOf(6.toByte(), 3.toByte(), 2.toByte(), 2.toByte(), 6.toByte(), 3.toByte(), 2.toByte(), 2.toByte(), 6.toByte(), 3.toByte(), 2.toByte(), 2.toByte(), 6.toByte(), 3.toByte(), 2.toByte(), 2.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaDivSB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDivSH() {
    val a = Simd<Short>(listOf((-6).toShort(), (-7).toShort(), (-8).toShort(), (-9).toShort(), 6.toShort(), 7.toShort(), 8.toShort(), 9.toShort()))
    val b = Simd<Short>(listOf((-1).toShort(), (-2).toShort(), (-3).toShort(), (-4).toShort(), (-1).toShort(), (-2).toShort(), (-3).toShort(), (-4).toShort()))
    val r = Simd<Short>(listOf(6.toShort(), 3.toShort(), 2.toShort(), 2.toShort(), (-6).toShort(), (-3).toShort(), (-2).toShort(), (-2).toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaDivSH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDivSW() {
    val a = Simd<Int>(listOf(-6, -7, 8, 9))
    val b = Simd<Int>(listOf(-1, -2, -3, -4))
    val r = Simd<Int>(listOf(6, 3, -2, -2))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaDivSW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDivSD() {
    val a = Simd<Long>(listOf(-6L, 7L))
    val b = Simd<Long>(listOf(-1L, -2L))
    val r = Simd<Long>(listOf(6L, -3L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaDivSD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDivUB() {
    val a = Simd<UByte>(listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte()))
    val b = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte()))
    val r = Simd<UByte>(listOf(6u.toUByte(), 3u.toUByte(), 2u.toUByte(), 2u.toUByte(), 6u.toUByte(), 3u.toUByte(), 2u.toUByte(), 2u.toUByte(), 6u.toUByte(), 3u.toUByte(), 2u.toUByte(), 2u.toUByte(), 6u.toUByte(), 3u.toUByte(), 2u.toUByte(), 2u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaDivUB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDivUH() {
    val a = Simd<UShort>(listOf(6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort(), 6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort()))
    val b = Simd<UShort>(listOf(1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort()))
    val r = Simd<UShort>(listOf(6u.toUShort(), 3u.toUShort(), 2u.toUShort(), 2u.toUShort(), 6u.toUShort(), 3u.toUShort(), 2u.toUShort(), 2u.toUShort()))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaDivUH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDivUW() {
    val a = Simd<UInt>(listOf(6u, 7u, 8u, 9u))
    val b = Simd<UInt>(listOf(1u, 2u, 3u, 4u))
    val r = Simd<UInt>(listOf(6u, 3u, 2u, 2u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaDivUW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDivUD() {
    val a = Simd<ULong>(listOf(6uL, 7uL))
    val b = Simd<ULong>(listOf(1uL, 2uL))
    val r = Simd<ULong>(listOf(6uL, 3uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaDivUD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<Any?, V2u64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDotpSH() {
    val a = Simd<Byte>(listOf((-1).toByte(), (-2).toByte(), (-3).toByte(), 4.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), 4.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte()))
    val b = Simd<Byte>(listOf((-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte()))
    val r = Simd<Short>(listOf(20.toShort(), (-12).toShort(), 20.toShort(), 60.toShort(), 20.toShort(), (-12).toShort(), 20.toShort(), 60.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaDotpSH(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDotpSW() {
    val a = Simd<Short>(listOf((-1).toShort(), (-2).toShort(), (-3).toShort(), (-4).toShort(), (-1).toShort(), (-2).toShort(), (-3).toShort(), 4.toShort()))
    val b = Simd<Short>(listOf((-6).toShort(), (-7).toShort(), (-8).toShort(), (-9).toShort(), (-6).toShort(), (-7).toShort(), (-8).toShort(), (-9).toShort()))
    val r = Simd<Int>(listOf(20, 60, 20, -12))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaDotpSW(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDotpSD() {
    val a = Simd<Int>(listOf(-1, -2, -3, 4))
    val b = Simd<Int>(listOf(-6, -7, -8, -9))
    val r = Simd<Long>(listOf(20L, -12L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaDotpSD(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDotpUH() {
    val a = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte()))
    val b = Simd<UByte>(listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte()))
    val r = Simd<UShort>(listOf(20u.toUShort(), 60u.toUShort(), 20u.toUShort(), 60u.toUShort(), 20u.toUShort(), 60u.toUShort(), 20u.toUShort(), 60u.toUShort()))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaDotpUH(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDotpUW() {
    val a = Simd<UShort>(listOf(1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort()))
    val b = Simd<UShort>(listOf(6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort(), 6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort()))
    val r = Simd<UInt>(listOf(20u, 60u, 20u, 60u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaDotpUW(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDotpUD() {
    val a = Simd<UInt>(listOf(1u, 2u, 3u, 4u))
    val b = Simd<UInt>(listOf(6u, 7u, 8u, 9u))
    val r = Simd<ULong>(listOf(20uL, 60uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaDotpUD(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDpaddSH() {
    val a = Simd<Short>(listOf((-1).toShort(), (-2).toShort(), (-3).toShort(), (-4).toShort(), (-1).toShort(), (-2).toShort(), (-3).toShort(), 4.toShort()))
    val b = Simd<Byte>(listOf((-1).toByte(), (-2).toByte(), (-3).toByte(), 4.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), 4.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte()))
    val c = Simd<Byte>(listOf((-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte()))
    val r = Simd<Short>(listOf(19.toShort(), (-14).toShort(), 17.toShort(), 56.toShort(), 19.toShort(), (-14).toShort(), 17.toShort(), 64.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaDpaddSH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDpaddSW() {
    val a = Simd<Int>(listOf(-1, -2, -3, -4))
    val b = Simd<Short>(listOf((-1).toShort(), (-2).toShort(), (-3).toShort(), 4.toShort(), (-1).toShort(), (-2).toShort(), (-3).toShort(), (-4).toShort()))
    val c = Simd<Short>(listOf((-6).toShort(), (-7).toShort(), (-8).toShort(), (-9).toShort(), (-6).toShort(), (-7).toShort(), (-8).toShort(), (-9).toShort()))
    val r = Simd<Int>(listOf(19, -14, 17, 56))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaDpaddSW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDpaddSD() {
    val a = Simd<Long>(listOf(-1L, -2L))
    val b = Simd<Int>(listOf(-1, -2, -3, 4))
    val c = Simd<Int>(listOf(-6, -7, -8, -9))
    val r = Simd<Long>(listOf(19L, -14L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaDpaddSD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDpaddUH() {
    val a = Simd<UShort>(listOf(1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort()))
    val b = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte()))
    val c = Simd<UByte>(listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte()))
    val r = Simd<UShort>(listOf(21u.toUShort(), 62u.toUShort(), 23u.toUShort(), 64u.toUShort(), 21u.toUShort(), 62u.toUShort(), 23u.toUShort(), 64u.toUShort()))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaDpaddUH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDpaddUW() {
    val a = Simd<UInt>(listOf(1u, 2u, 3u, 4u))
    val b = Simd<UShort>(listOf(1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort()))
    val c = Simd<UShort>(listOf(6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort(), 6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort()))
    val r = Simd<UInt>(listOf(21u, 62u, 23u, 64u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaDpaddUW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V8u16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDpaddUD() {
    val a = Simd<ULong>(listOf(1uL, 2uL))
    val b = Simd<UInt>(listOf(1u, 2u, 3u, 4u))
    val c = Simd<UInt>(listOf(6u, 7u, 8u, 9u))
    val r = Simd<ULong>(listOf(21uL, 62uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaDpaddUD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<Any?, V4u32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDpsubSH() {
    val a = Simd<Short>(listOf((-1).toShort(), (-2).toShort(), (-3).toShort(), (-4).toShort(), (-1).toShort(), (-2).toShort(), (-3).toShort(), 4.toShort()))
    val b = Simd<Byte>(listOf((-1).toByte(), (-2).toByte(), (-3).toByte(), 4.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), 4.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte()))
    val c = Simd<Byte>(listOf((-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte()))
    val r = Simd<Short>(listOf((-21).toShort(), 10.toShort(), (-23).toShort(), (-64).toShort(), (-21).toShort(), 10.toShort(), (-23).toShort(), (-56).toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaDpsubSH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDpsubSW() {
    val a = Simd<Int>(listOf(-1, -2, -3, -4))
    val b = Simd<Short>(listOf((-1).toShort(), (-2).toShort(), (-3).toShort(), 4.toShort(), (-1).toShort(), (-2).toShort(), (-3).toShort(), (-4).toShort()))
    val c = Simd<Short>(listOf((-6).toShort(), (-7).toShort(), (-8).toShort(), (-9).toShort(), (-6).toShort(), (-7).toShort(), (-8).toShort(), (-9).toShort()))
    val r = Simd<Int>(listOf(-21, 10, -23, -64))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaDpsubSW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDpsubSD() {
    val a = Simd<Long>(listOf(-1L, -2L))
    val b = Simd<Int>(listOf(-1, -2, -3, 4))
    val c = Simd<Int>(listOf(-6, -7, -8, -9))
    val r = Simd<Long>(listOf(-21L, 10L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaDpsubSD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDpsubUH() {
    val a = Simd<Short>(listOf(1.toShort(), (-2).toShort(), 3.toShort(), (-4).toShort(), (-1).toShort(), 2.toShort(), (-3).toShort(), 4.toShort()))
    val b = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte()))
    val c = Simd<UByte>(listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte()))
    val r = Simd<Short>(listOf((-19).toShort(), (-62).toShort(), (-17).toShort(), (-64).toShort(), (-21).toShort(), (-58).toShort(), (-23).toShort(), (-56).toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaDpsubUH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDpsubUW() {
    val a = Simd<Int>(listOf(1, -2, 3, -4))
    val b = Simd<UShort>(listOf(1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort()))
    val c = Simd<UShort>(listOf(6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort(), 6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort()))
    val r = Simd<Int>(listOf(-19, -62, -17, -64))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaDpsubUW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V8u16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDpsubUD() {
    val a = Simd<Long>(listOf(1L, -2L))
    val b = Simd<UInt>(listOf(1u, 2u, 3u, 4u))
    val c = Simd<UInt>(listOf(6u, 7u, 8u, 9u))
    val r = Simd<Long>(listOf(-19L, -62L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaDpsubUD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V4u32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFaddW() {
    val a = Simd<Float>(listOf(1.1f.toFloat(), (-2.2f).toFloat(), 3.3f.toFloat(), (-4.4f).toFloat()))
    val b = Simd<Float>(listOf(4.4f.toFloat(), (-3.3f).toFloat(), 2.2f.toFloat(), (-1.1f).toFloat()))
    val r = Simd<Float>(listOf(5.5f.toFloat(), (-5.5f).toFloat(), 5.5f.toFloat(), (-5.5f).toFloat()))
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFaddW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFaddD() {
    val a = Simd<Double>(listOf(1.1.toDouble(), (-2.2).toDouble()))
    val b = Simd<Double>(listOf(4.4.toDouble(), (-3.3).toDouble()))
    val r = Simd<Double>(listOf(5.5.toDouble(), (-5.5).toDouble()))
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFaddD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

// Only observed beahiour should be SIGFPE signal
// Can not be tested
public fun testMsaFcafW() {
    val a = Simd<Float>(listOf(1.1f.toFloat(), (-2.2f).toFloat(), 3.3f.toFloat(), (-4.4f).toFloat()))
    val b = Simd<Float>(listOf(0.0f.toFloat(), (-1.2f).toFloat(), 3.3f.toFloat(), Float.NaN))
    val r = Simd<Int>(listOf(0, 0, 0, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFcafW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

// Only observed beahiour should be SIGFPE signal
// Can not be tested
public fun testMsaFcafD() {
    val a = Simd<Double>(listOf(1.1.toDouble(), (-2.2).toDouble()))
    val b = Simd<Double>(listOf((-2.2).toDouble(), 1.1.toDouble()))
    val r = Simd<Long>(listOf(0L, 0L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFcafD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFceqW() {
    val a = Simd<Float>(listOf(1.1f.toFloat(), (-2.2f).toFloat(), 3.3f.toFloat(), Float.NaN))
    val b = Simd<Float>(listOf((-4.4f).toFloat(), (-2.2f).toFloat(), 3.3f.toFloat(), Float.NaN))
    val r = Simd<Int>(listOf(0, -1, -1, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFceqW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFceqD() {
    val a = Simd<Double>(listOf(1.1.toDouble(), (-2.2).toDouble()))
    val b = Simd<Double>(listOf(1.1.toDouble(), 1.1.toDouble()))
    val r = Simd<Long>(listOf(-1L, 0L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFceqD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFclassW() {
    val a = Simd<Float>(listOf(1.1f.toFloat(), (-2.2f).toFloat(), 3.3f.toFloat(), Float.NaN))
    val r = Simd<Int>(listOf(128, 8, 128, 2))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFclassW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFclassD() {
    val a = Simd<Double>(listOf(1.1.toDouble(), (-2.2).toDouble()))
    val r = Simd<Long>(listOf(128L, 8L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFclassD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcleW() {
    val a = Simd<Float>(listOf(1.1f.toFloat(), (-2.2f).toFloat(), 3.3f.toFloat(), Float.NaN))
    val b = Simd<Float>(listOf((-4.4f).toFloat(), (-1.2f).toFloat(), 3.3f.toFloat(), Float.NaN))
    val r = Simd<Int>(listOf(0, -1, -1, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFcleW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcleD() {
    val a = Simd<Double>(listOf(1.1.toDouble(), (-2.2).toDouble()))
    val b = Simd<Double>(listOf(1.1.toDouble(), 1.1.toDouble()))
    val r = Simd<Long>(listOf(-1L, -1L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFcleD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcltW() {
    val a = Simd<Float>(listOf(1.1f.toFloat(), (-2.2f).toFloat(), 3.3f.toFloat(), Float.NaN))
    val b = Simd<Float>(listOf((-4.4f).toFloat(), (-1.2f).toFloat(), 3.3f.toFloat(), Float.NaN))
    val r = Simd<Int>(listOf(0, -1, 0, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFcltW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcltD() {
    val a = Simd<Double>(listOf(1.1.toDouble(), (-2.2).toDouble()))
    val b = Simd<Double>(listOf(1.1.toDouble(), 1.1.toDouble()))
    val r = Simd<Long>(listOf(0L, -1L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFcltD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcneW() {
    val a = Simd<Float>(listOf(1.1f.toFloat(), (-2.2f).toFloat(), 3.3f.toFloat(), Float.NaN))
    val b = Simd<Float>(listOf((-4.4f).toFloat(), (-1.2f).toFloat(), 3.3f.toFloat(), Float.NaN))
    val r = Simd<Int>(listOf(-1, -1, 0, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFcneW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcneD() {
    val a = Simd<Double>(listOf(1.1.toDouble(), (-2.2).toDouble()))
    val b = Simd<Double>(listOf(1.1.toDouble(), 1.1.toDouble()))
    val r = Simd<Long>(listOf(0L, -1L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFcneD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcorW() {
    val a = Simd<Float>(listOf(1.1f.toFloat(), (-2.2f).toFloat(), 3.3f.toFloat(), Float.NaN))
    val b = Simd<Float>(listOf(Float.NaN, (-1.2f).toFloat(), 3.3f.toFloat(), Float.NaN))
    val r = Simd<Int>(listOf(0, -1, -1, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFcorW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcorD() {
    val a = Simd<Double>(listOf(1.1.toDouble(), Double.NaN))
    val b = Simd<Double>(listOf(1.1.toDouble(), 1.1.toDouble()))
    val r = Simd<Long>(listOf(-1L, 0L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFcorD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcueqW() {
    val a = Simd<Float>(listOf(1.1f.toFloat(), (-2.2f).toFloat(), 3.3f.toFloat(), Float.NaN))
    val b = Simd<Float>(listOf(Float.NaN, (-1.2f).toFloat(), 3.3f.toFloat(), Float.NaN))
    val r = Simd<Int>(listOf(-1, 0, -1, -1))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFcueqW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcueqD() {
    val a = Simd<Double>(listOf(1.1.toDouble(), Double.NaN))
    val b = Simd<Double>(listOf(1.1.toDouble(), 1.1.toDouble()))
    val r = Simd<Long>(listOf(-1L, -1L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFcueqD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFculeW() {
    val a = Simd<Float>(listOf(1.1f.toFloat(), (-2.2f).toFloat(), 3.3f.toFloat(), Float.NaN))
    val b = Simd<Float>(listOf(Float.NaN, (-1.2f).toFloat(), 3.3f.toFloat(), Float.NaN))
    val r = Simd<Int>(listOf(-1, -1, -1, -1))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFculeW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFculeD() {
    val a = Simd<Double>(listOf(1.1.toDouble(), Double.NaN))
    val b = Simd<Double>(listOf(1.1.toDouble(), 1.1.toDouble()))
    val r = Simd<Long>(listOf(-1L, -1L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFculeD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcultW() {
    val a = Simd<Float>(listOf(1.1f.toFloat(), (-2.2f).toFloat(), 3.3f.toFloat(), Float.NaN))
    val b = Simd<Float>(listOf(Float.NaN, (-1.2f).toFloat(), 3.3f.toFloat(), Float.NaN))
    val r = Simd<Int>(listOf(-1, -1, 0, -1))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFcultW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcultD() {
    val a = Simd<Double>(listOf(1.1.toDouble(), Double.NaN))
    val b = Simd<Double>(listOf(1.1.toDouble(), 1.1.toDouble()))
    val r = Simd<Long>(listOf(0L, -1L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFcultD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcunW() {
    val a = Simd<Float>(listOf(1.1f.toFloat(), (-2.2f).toFloat(), 3.3f.toFloat(), Float.NaN))
    val b = Simd<Float>(listOf(Float.NaN, (-1.2f).toFloat(), 3.3f.toFloat(), Float.NaN))
    val r = Simd<Int>(listOf(-1, 0, 0, -1))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFcunW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcunD() {
    val a = Simd<Double>(listOf(1.1.toDouble(), Double.NaN))
    val b = Simd<Double>(listOf(1.1.toDouble(), 1.1.toDouble()))
    val r = Simd<Long>(listOf(0L, -1L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFcunD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcuneW() {
    val a = Simd<Float>(listOf(1.1f.toFloat(), (-2.2f).toFloat(), 3.3f.toFloat(), Float.NaN))
    val b = Simd<Float>(listOf(Float.NaN, (-1.2f).toFloat(), 3.3f.toFloat(), Float.NaN))
    val r = Simd<Int>(listOf(-1, -1, 0, -1))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFcuneW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcuneD() {
    val a = Simd<Double>(listOf(1.1.toDouble(), Double.NaN))
    val b = Simd<Double>(listOf(1.1.toDouble(), 1.1.toDouble()))
    val r = Simd<Long>(listOf(0L, -1L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFcuneD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFdivW() {
    val a = Simd<Float>(listOf(5.25f.toFloat(), (-20.2f).toFloat(), 333.333f.toFloat(), (-425.0f).toFloat()))
    val b = Simd<Float>(listOf(4.0f.toFloat(), (-2.1f).toFloat(), 11.11f.toFloat(), 8.2f.toFloat()))
    val r = Simd<Float>(listOf(1.3125f.toFloat(), 9.619048f.toFloat(), 30.002972f.toFloat(), (-51.82927f).toFloat()))
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFdivW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFdivD() {
    val a = Simd<Double>(listOf(1111.11.toDouble(), (-222222.2).toDouble()))
    val b = Simd<Double>(listOf((-4.85).toDouble(), 3.33.toDouble()))
    val r = Simd<Double>(listOf((-229.09484536082473).toDouble(), (-66733.3933933934).toDouble()))
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFdivD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

// // Note: 16-bit floats
// @simdTest(enable = "msa")
// unchecked fn testMsaFexdoH() (
// #[rustfmt.skip]
// let a = f32x4.new(20.5, 2.3, 4.5, 5.4);
// #[rustfmt.skip]
// let b = f32x4.new(1.1, 1.0, 1.0, 1.0);
// let r = i16x8.new(1, 9, 30, 51, 1, 9, 30, 51);
//
// assertEq(r, mem.transmute(__msa_fexdo_h(mem.transmute(a), mem.transmute(b))));
// )
public fun testMsaFexdoW() {
    val a = Simd<Double>(listOf(2000005.5.toDouble(), 2.3.toDouble()))
    val b = Simd<Double>(listOf(1235689784512.1.toDouble(), 2147483649998.5.toDouble()))
    val r = Simd<Float>(listOf(1235689800000.0f.toFloat(), 2147483600000.0f.toFloat(), 2000005.5f.toFloat(), 2.3f.toFloat()))
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFexdoW(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFexp2W() {
    val a = Simd<Float>(listOf(1.1f.toFloat(), (-2.2f).toFloat(), 3.3f.toFloat(), (-4.4f).toFloat()))
    val b = Simd<Int>(listOf(4, -3, 2, 1))
    val r = Simd<Float>(listOf(17.6f.toFloat(), (-0.275f).toFloat(), 13.2f.toFloat(), (-8.8f).toFloat()))
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFexp2W(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFexp2D() {
    val a = Simd<Double>(listOf(1.1.toDouble(), (-2.2).toDouble()))
    val b = Simd<Long>(listOf(-4L, 3L))
    val r = Simd<Double>(listOf(0.06875.toDouble(), (-17.6).toDouble()))
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFexp2D(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

// Note: 16-bit floats
// @simdTest(enable = "msa")
// unchecked fn testMsaFexuplW() (
// #[rustfmt.skip]
// let a = f16x8(1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5);
// #[rustfmt.skip]
// let r = f32x4.new(5.5, 6.5, 7.5, 8.5);
// assertEq(r, mem.transmute(__msa_fexupl_w(mem.transmute(a))));
// )
public fun testMsaFexuplD() {
    val a = Simd<Float>(listOf(5.5f.toFloat(), 6.5f.toFloat(), 7.5f.toFloat(), 8.5f.toFloat()))
    val r = Simd<Double>(listOf(7.5.toDouble(), 8.5.toDouble()))
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFexuplD(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

// Note: 16-bit floats
// @simdTest(enable = "msa")
// unchecked fn testMsaFexuprW() (
// #[rustfmt.skip]
// let a = f16x8(1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5);
// #[rustfmt.skip]
// let r = f32x4.new(1.5, 2.5, 3.5, 4.5);
// assertEq(r, mem.transmute(__msa_fexupr_w(mem.transmute(a))));
// )
public fun testMsaFexuprD() {
    val a = Simd<Float>(listOf(5.5f.toFloat(), 6.5f.toFloat(), 7.5f.toFloat(), 8.5f.toFloat()))
    val r = Simd<Double>(listOf(5.5.toDouble(), 6.5.toDouble()))
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFexuprD(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFfintSW() {
    val a = Simd<Int>(listOf(-1, 2, -3, 4))
    val r = Simd<Float>(listOf((-1.0f).toFloat(), 2.0f.toFloat(), (-3.0f).toFloat(), 4.0f.toFloat()))
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFfintSW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFfintSD() {
    val a = Simd<Long>(listOf(-1L, 2L))
    val r = Simd<Double>(listOf((-1.0).toDouble(), 2.0.toDouble()))
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFfintSD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFfintUW() {
    val a = Simd<UInt>(listOf(1u, 2u, 3u, 4u))
    val r = Simd<Float>(listOf(1.0f.toFloat(), 2.0f.toFloat(), 3.0f.toFloat(), 4.0f.toFloat()))
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFfintUW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFfintUD() {
    val a = Simd<ULong>(listOf(1uL, 2uL))
    val r = Simd<Double>(listOf(1.0.toDouble(), 2.0.toDouble()))
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFfintUD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFfqlW() {
    val a = Simd<Short>(listOf(11.toShort(), 25.toShort(), 33.toShort(), 47.toShort(), 11.toShort(), 25.toShort(), 33.toShort(), 47.toShort()))
    val r = Simd<Float>(listOf(0.00033569336f.toFloat(), 0.00076293945f.toFloat(), 0.0010070801f.toFloat(), 0.0014343262f.toFloat()))
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFfqlW(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFfqlD() {
    val a = Simd<Int>(listOf(1111, 2222, 3333, 4444))
    val r = Simd<Double>(listOf(0.000001552049070596695.toDouble(), 0.0000020693987607955933.toDouble()))
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFfqlD(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFfqrW() {
    val a = Simd<Short>(listOf(12.toShort(), 26.toShort(), 34.toShort(), 48.toShort(), 11.toShort(), 25.toShort(), 33.toShort(), 47.toShort()))
    val r = Simd<Float>(listOf(0.00036621094f.toFloat(), 0.00079345703f.toFloat(), 0.0010375977f.toFloat(), 0.0014648438f.toFloat()))
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFfqrW(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFfqrD() {
    val a = Simd<Int>(listOf(1111, 2555, 3333, 475))
    val r = Simd<Double>(listOf(0.0000005173496901988983.toDouble(), 0.0000011897645890712738.toDouble()))
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFfqrD(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFillB() {
    val r = Simd<Byte>(listOf(2.toByte(), 2.toByte(), 2.toByte(), 2.toByte(), 2.toByte(), 2.toByte(), 2.toByte(), 2.toByte(), 2.toByte(), 2.toByte(), 2.toByte(), 2.toByte(), 2.toByte(), 2.toByte(), 2.toByte(), 2.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaFillB(2), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFillH() {
    val r = Simd<Short>(listOf(2.toShort(), 2.toShort(), 2.toShort(), 2.toShort(), 2.toShort(), 2.toShort(), 2.toShort(), 2.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaFillH(2), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFillW() {
    val r = Simd<Int>(listOf(2, 2, 2, 2))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFillW(2), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFillD() {
    val r = Simd<Long>(listOf(2L, 2L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFillD(2L), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFlog2W() {
    val a = Simd<Float>(listOf(8.0f.toFloat(), 16.0f.toFloat(), 32.0f.toFloat(), 64.0f.toFloat()))
    val r = Simd<Float>(listOf(3.0f.toFloat(), 4.0f.toFloat(), 5.0f.toFloat(), 6.0f.toFloat()))
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFlog2W(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFlog2D() {
    val a = Simd<Double>(listOf(8.0.toDouble(), 16.0.toDouble()))
    val r = Simd<Double>(listOf(3.0.toDouble(), 4.0.toDouble()))
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFlog2D(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFmaddW() {
    val a = Simd<Float>(listOf(1.0f.toFloat(), 2.0f.toFloat(), 3.0f.toFloat(), 4.0f.toFloat()))
    val b = Simd<Float>(listOf(5.0f.toFloat(), 6.0f.toFloat(), 7.0f.toFloat(), 8.0f.toFloat()))
    val c = Simd<Float>(listOf(9.0f.toFloat(), 10.0f.toFloat(), 11.0f.toFloat(), 12.0f.toFloat()))
    val r = Simd<Float>(listOf(46.0f.toFloat(), 62.0f.toFloat(), 80.0f.toFloat(), 100.0f.toFloat()))
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFmaddW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFmaddD() {
    val a = Simd<Double>(listOf(1.0.toDouble(), 2.0.toDouble()))
    val b = Simd<Double>(listOf(3.0.toDouble(), 4.0.toDouble()))
    val c = Simd<Double>(listOf(5.0.toDouble(), 6.0.toDouble()))
    val r = Simd<Double>(listOf(16.0.toDouble(), 26.0.toDouble()))
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFmaddD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFmaxW() {
    val a = Simd<Float>(listOf(1.0f.toFloat(), (-6.0f).toFloat(), 7.0f.toFloat(), 8.0f.toFloat()))
    val b = Simd<Float>(listOf(5.0f.toFloat(), (-2.0f).toFloat(), 3.0f.toFloat(), 4.0f.toFloat()))
    val r = Simd<Float>(listOf(5.0f.toFloat(), (-2.0f).toFloat(), 7.0f.toFloat(), 8.0f.toFloat()))
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFmaxW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFmaxD() {
    val a = Simd<Double>(listOf(1.0.toDouble(), 4.0.toDouble()))
    val b = Simd<Double>(listOf(3.0.toDouble(), 2.0.toDouble()))
    val r = Simd<Double>(listOf(3.0.toDouble(), 4.0.toDouble()))
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFmaxD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFmaxAW() {
    val a = Simd<Float>(listOf(1.0f.toFloat(), (-6.0f).toFloat(), (-7.0f).toFloat(), (-8.0f).toFloat()))
    val b = Simd<Float>(listOf(5.0f.toFloat(), (-2.0f).toFloat(), 3.0f.toFloat(), 4.0f.toFloat()))
    val r = Simd<Float>(listOf(5.0f.toFloat(), (-6.0f).toFloat(), (-7.0f).toFloat(), (-8.0f).toFloat()))
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFmaxAW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFmaxAD() {
    val a = Simd<Double>(listOf(1.0.toDouble(), (-4.0).toDouble()))
    val b = Simd<Double>(listOf(3.0.toDouble(), 2.0.toDouble()))
    val r = Simd<Double>(listOf(3.0.toDouble(), (-4.0).toDouble()))
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFmaxAD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFminW() {
    val a = Simd<Float>(listOf(1.0f.toFloat(), (-6.0f).toFloat(), 7.0f.toFloat(), 8.0f.toFloat()))
    val b = Simd<Float>(listOf(5.0f.toFloat(), (-2.0f).toFloat(), 3.0f.toFloat(), 4.0f.toFloat()))
    val r = Simd<Float>(listOf(1.0f.toFloat(), (-6.0f).toFloat(), 3.0f.toFloat(), 4.0f.toFloat()))
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFminW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFminD() {
    val a = Simd<Double>(listOf(1.0.toDouble(), 4.0.toDouble()))
    val b = Simd<Double>(listOf(3.0.toDouble(), 2.0.toDouble()))
    val r = Simd<Double>(listOf(1.0.toDouble(), 2.0.toDouble()))
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFminD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFminAW() {
    val a = Simd<Float>(listOf(1.0f.toFloat(), (-6.0f).toFloat(), (-7.0f).toFloat(), (-8.0f).toFloat()))
    val b = Simd<Float>(listOf(5.0f.toFloat(), (-2.0f).toFloat(), 3.0f.toFloat(), 4.0f.toFloat()))
    val r = Simd<Float>(listOf(1.0f.toFloat(), (-2.0f).toFloat(), 3.0f.toFloat(), 4.0f.toFloat()))
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFminAW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFminAD() {
    val a = Simd<Double>(listOf(1.0.toDouble(), (-4.0).toDouble()))
    val b = Simd<Double>(listOf(3.0.toDouble(), 2.0.toDouble()))
    val r = Simd<Double>(listOf(1.0.toDouble(), 2.0.toDouble()))
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFminAD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFmsubW() {
    val a = Simd<Float>(listOf(1.0f.toFloat(), 2.0f.toFloat(), 3.0f.toFloat(), 4.0f.toFloat()))
    val b = Simd<Float>(listOf(5.0f.toFloat(), 6.0f.toFloat(), 7.0f.toFloat(), 8.0f.toFloat()))
    val c = Simd<Float>(listOf(9.0f.toFloat(), 10.0f.toFloat(), 11.0f.toFloat(), 12.0f.toFloat()))
    val r = Simd<Float>(listOf((-44.0f).toFloat(), (-58.0f).toFloat(), (-74.0f).toFloat(), (-92.0f).toFloat()))
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFmsubW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFmsubD() {
    val a = Simd<Double>(listOf(1.0.toDouble(), 2.0.toDouble()))
    val b = Simd<Double>(listOf(3.0.toDouble(), 4.0.toDouble()))
    val c = Simd<Double>(listOf(5.0.toDouble(), 6.0.toDouble()))
    val r = Simd<Double>(listOf((-14.0).toDouble(), (-22.0).toDouble()))
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFmsubD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFmulW() {
    val a = Simd<Float>(listOf(1.1f.toFloat(), (-2.2f).toFloat(), 3.3f.toFloat(), 4.4f.toFloat()))
    val b = Simd<Float>(listOf(4.4f.toFloat(), 3.3f.toFloat(), 2.2f.toFloat(), (-1.1f).toFloat()))
    val r = Simd<Float>(listOf(4.84f.toFloat(), (-7.26f).toFloat(), 7.26f.toFloat(), (-4.84f).toFloat()))
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFmulW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFmulD() {
    val a = Simd<Double>(listOf(1.1.toDouble(), (-2.2).toDouble()))
    val b = Simd<Double>(listOf(4.0.toDouble(), (-3.3).toDouble()))
    val r = Simd<Double>(listOf(4.4.toDouble(), 7.26.toDouble()))
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFmulD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFrintW() {
    val a = Simd<Float>(listOf(2.6f.toFloat(), (-2.7f).toFloat(), 1.3f.toFloat(), (-1.7f).toFloat()))
    val r = Simd<Float>(listOf(3.0f.toFloat(), (-3.0f).toFloat(), 1.0f.toFloat(), (-2.0f).toFloat()))
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFrintW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFrintD() {
    val a = Simd<Double>(listOf(2.6.toDouble(), 1.3.toDouble()))
    val r = Simd<Double>(listOf(3.0.toDouble(), 1.0.toDouble()))
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFrintD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFrcpW() {
    val a = Simd<Float>(listOf(2.6f.toFloat(), (-2.7f).toFloat(), 1.3f.toFloat(), (-1.7f).toFloat()))
    val r = Simd<Float>(listOf(0.3846154f.toFloat(), (-0.37037036f).toFloat(), 0.7692308f.toFloat(), (-0.58823526f).toFloat()))
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFrcpW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFrcpD() {
    val a = Simd<Double>(listOf(2.6.toDouble(), 1.3.toDouble()))
    val r = Simd<Double>(listOf(0.3846153846153846.toDouble(), 0.7692307692307692.toDouble()))
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFrcpD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFrsqrtW() {
    val a = Simd<Float>(listOf(2.6f.toFloat(), 2.7f.toFloat(), 1.3f.toFloat(), 1.7f.toFloat()))
    val r = Simd<Float>(listOf(0.6201737f.toFloat(), 0.6085806f.toFloat(), 0.87705797f.toFloat(), 0.766965f.toFloat()))
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFrsqrtW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFrsqrtD() {
    val a = Simd<Double>(listOf(2.6.toDouble(), 1.3.toDouble()))
    val r = Simd<Double>(listOf(0.6201736729460422.toDouble(), 0.8770580193070292.toDouble()))
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFrsqrtD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsafW() {
    val a = Simd<Float>(listOf((-5.5f).toFloat(), 5.5f.toFloat(), 5.5f.toFloat(), 5.5f.toFloat()))
    val b = Simd<Float>(listOf((-5.5f).toFloat(), 5.5f.toFloat(), 5.5f.toFloat(), 5.5f.toFloat()))
    val r = Simd<Int>(listOf(0, 0, 0, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFsafW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsafD() {
    val a = Simd<Double>(listOf((-125.5).toDouble(), 5.5.toDouble()))
    val b = Simd<Double>(listOf(125.5.toDouble(), 3.3.toDouble()))
    val r = Simd<Long>(listOf(0L, 0L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFsafD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFseqW() {
    val a = Simd<Float>(listOf((-5.5f).toFloat(), (-3.3f).toFloat(), Float.NaN, Float.NaN))
    val b = Simd<Float>(listOf(5.5f.toFloat(), (-3.3f).toFloat(), Float.NaN, 1.1f.toFloat()))
    val r = Simd<Int>(listOf(0, -1, 0, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFseqW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFseqD() {
    val a = Simd<Double>(listOf((-125.5).toDouble(), 5.5.toDouble()))
    val b = Simd<Double>(listOf(125.5.toDouble(), 5.5.toDouble()))
    val r = Simd<Long>(listOf(0L, -1L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFseqD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsleW() {
    val a = Simd<Float>(listOf(5.5f.toFloat(), 5.5f.toFloat(), 5.5f.toFloat(), Float.NaN))
    val b = Simd<Float>(listOf((-5.5f).toFloat(), 3.3f.toFloat(), 5.5f.toFloat(), Float.NaN))
    val r = Simd<Int>(listOf(0, 0, -1, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFsleW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsleD() {
    val a = Simd<Double>(listOf((-125.5).toDouble(), 5.5.toDouble()))
    val b = Simd<Double>(listOf(125.5.toDouble(), 3.3.toDouble()))
    val r = Simd<Long>(listOf(-1L, 0L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFsleD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsltW() {
    val a = Simd<Float>(listOf((-5.5f).toFloat(), 5.5f.toFloat(), 5.5f.toFloat(), 5.5f.toFloat()))
    val b = Simd<Float>(listOf(5.5f.toFloat(), 3.3f.toFloat(), 5.5f.toFloat(), 1.1f.toFloat()))
    val r = Simd<Int>(listOf(-1, 0, 0, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFsltW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsltD() {
    val a = Simd<Double>(listOf((-125.5).toDouble(), 5.5.toDouble()))
    val b = Simd<Double>(listOf(125.5.toDouble(), 3.3.toDouble()))
    val r = Simd<Long>(listOf(-1L, 0L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFsltD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsneW() {
    val a = Simd<Float>(listOf((-5.5f).toFloat(), 5.5f.toFloat(), 5.5f.toFloat(), 5.5f.toFloat()))
    val b = Simd<Float>(listOf(5.5f.toFloat(), 3.3f.toFloat(), 5.5f.toFloat(), 1.1f.toFloat()))
    val r = Simd<Int>(listOf(-1, -1, 0, -1))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFsneW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsneD() {
    val a = Simd<Double>(listOf((-125.5).toDouble(), 5.5.toDouble()))
    val b = Simd<Double>(listOf(125.5.toDouble(), 5.5.toDouble()))
    val r = Simd<Long>(listOf(-1L, 0L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFsneD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsorW() {
    val a = Simd<Float>(listOf((-5.5f).toFloat(), Float.NaN, 5.5f.toFloat(), 5.5f.toFloat()))
    val b = Simd<Float>(listOf(5.5f.toFloat(), 3.3f.toFloat(), 5.5f.toFloat(), 1.1f.toFloat()))
    val r = Simd<Int>(listOf(-1, 0, -1, -1))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFsorW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsorD() {
    val a = Simd<Double>(listOf((-125.5).toDouble(), 5.5.toDouble()))
    val b = Simd<Double>(listOf(125.5.toDouble(), Double.NaN))
    val r = Simd<Long>(listOf(-1L, 0L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFsorD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsqrtW() {
    val a = Simd<Float>(listOf(9.0f.toFloat(), 81.0f.toFloat(), 1089.0f.toFloat(), 10000.0f.toFloat()))
    val r = Simd<Float>(listOf(3.0f.toFloat(), 9.0f.toFloat(), 33.0f.toFloat(), 100.0f.toFloat()))
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFsqrtW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsqrtD() {
    val a = Simd<Double>(listOf(81.0.toDouble(), 10000.0.toDouble()))
    val r = Simd<Double>(listOf(9.0.toDouble(), 100.0.toDouble()))
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFsqrtD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsubW() {
    val a = Simd<Float>(listOf(5.5f.toFloat(), 6.5f.toFloat(), 7.5f.toFloat(), 8.5f.toFloat()))
    val b = Simd<Float>(listOf(1.25f.toFloat(), 1.75f.toFloat(), 2.25f.toFloat(), 2.75f.toFloat()))
    val r = Simd<Float>(listOf(4.25f.toFloat(), 4.75f.toFloat(), 5.25f.toFloat(), 5.75f.toFloat()))
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFsubW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsubD() {
    val a = Simd<Double>(listOf(555.5.toDouble(), 55.5.toDouble()))
    val b = Simd<Double>(listOf(4.25.toDouble(), 3.25.toDouble()))
    val r = Simd<Double>(listOf(551.25.toDouble(), 52.25.toDouble()))
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFsubD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsueqW() {
    val a = Simd<Float>(listOf(5.5f.toFloat(), Float.NaN, 5.5f.toFloat(), 5.5f.toFloat()))
    val b = Simd<Float>(listOf(5.5f.toFloat(), 5.5f.toFloat(), (-5.5f).toFloat(), 5.5f.toFloat()))
    val r = Simd<Int>(listOf(-1, -1, 0, -1))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFsueqW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsueqD() {
    val a = Simd<Double>(listOf((-5.5).toDouble(), 5.5.toDouble()))
    val b = Simd<Double>(listOf(5.5.toDouble(), Double.NaN))
    val r = Simd<Long>(listOf(0L, -1L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFsueqD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsuleW() {
    val a = Simd<Float>(listOf(5.7f.toFloat(), 5.8f.toFloat(), 5.9f.toFloat(), Float.NaN))
    val b = Simd<Float>(listOf(5.6f.toFloat(), 5.9f.toFloat(), 5.9f.toFloat(), Float.NaN))
    val r = Simd<Int>(listOf(0, -1, -1, -1))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFsuleW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsuleD() {
    val a = Simd<Double>(listOf(5.5.toDouble(), 5.5.toDouble()))
    val b = Simd<Double>(listOf(5.5.toDouble(), 5.5.toDouble()))
    val r = Simd<Long>(listOf(-1L, -1L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFsuleD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsultW() {
    val a = Simd<Float>(listOf(5.5f.toFloat(), 5.5f.toFloat(), 5.5f.toFloat(), 5.5f.toFloat()))
    val b = Simd<Float>(listOf(5.6f.toFloat(), Float.NaN, 2.2f.toFloat(), 1.1f.toFloat()))
    val r = Simd<Int>(listOf(-1, -1, 0, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFsultW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsultD() {
    val a = Simd<Double>(listOf(5.5.toDouble(), Double.NaN))
    val b = Simd<Double>(listOf(4.4.toDouble(), 3.3.toDouble()))
    val r = Simd<Long>(listOf(0L, -1L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFsultD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsunW() {
    val a = Simd<Float>(listOf(5.5f.toFloat(), 5.5f.toFloat(), Float.NaN, 5.5f.toFloat()))
    val b = Simd<Float>(listOf(4.4f.toFloat(), 3.3f.toFloat(), 2.2f.toFloat(), Float.NaN))
    val r = Simd<Int>(listOf(0, 0, -1, -1))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFsunW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsunD() {
    val a = Simd<Double>(listOf(5.5.toDouble(), Double.NaN))
    val b = Simd<Double>(listOf(4.4.toDouble(), 3.3.toDouble()))
    val r = Simd<Long>(listOf(0L, -1L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFsunD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsuneW() {
    val a = Simd<Float>(listOf(5.5f.toFloat(), 5.5f.toFloat(), Float.NaN, 5.5f.toFloat()))
    val b = Simd<Float>(listOf(4.4f.toFloat(), 3.3f.toFloat(), 2.2f.toFloat(), 5.5f.toFloat()))
    val r = Simd<Int>(listOf(-1, -1, -1, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFsuneW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsuneD() {
    val a = Simd<Double>(listOf(5.5.toDouble(), Double.NaN))
    val b = Simd<Double>(listOf(5.5.toDouble(), 3.3.toDouble()))
    val r = Simd<Long>(listOf(0L, -1L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFsuneD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFtintSW() {
    val a = Simd<Float>(listOf((-5.5f).toFloat(), 75.6f.toFloat(), (-1000.7f).toFloat(), 1219.3f.toFloat()))
    val r = Simd<Int>(listOf(-6, 76, -1001, 1219))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFtintSW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFtintSD() {
    val a = Simd<Double>(listOf((-5.5).toDouble(), 25656.4.toDouble()))
    val r = Simd<Long>(listOf(-6L, 25656L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFtintSD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFtintUW() {
    val a = Simd<Float>(listOf((-5.5f).toFloat(), 75.6f.toFloat(), (-1000.7f).toFloat(), 1219.3f.toFloat()))
    val r = Simd<UInt>(listOf(0u, 76u, 0u, 1219u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaFtintUW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFtintUD() {
    val a = Simd<Double>(listOf(5.5.toDouble(), (-25656.4).toDouble()))
    val r = Simd<ULong>(listOf(6uL, 0uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaFtintUD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFtqH() {
    val a = Simd<Float>(listOf(0.00001f.toFloat(), 0.0002f.toFloat(), 0.00001f.toFloat(), (-0.0002f).toFloat()))
    val b = Simd<Float>(listOf(0.0001f.toFloat(), (-0.002f).toFloat(), 0.0001f.toFloat(), 0.002f.toFloat()))
    val r = Simd<Short>(listOf(3.toShort(), (-66).toShort(), 3.toShort(), 66.toShort(), 0.toShort(), 7.toShort(), 0.toShort(), (-7).toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaFtqH(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>()), transmuteWithLayouts<Any?, V4f32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFtqW() {
    val a = Simd<Double>(listOf(0.00001.toDouble(), (-0.0002).toDouble()))
    val b = Simd<Double>(listOf(0.00000045.toDouble(), 0.000015.toDouble()))
    val r = Simd<Int>(listOf(966, 32212, 21475, -429497))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFtqW(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>()), transmuteWithLayouts<Any?, V2f64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFtruncSW() {
    val a = Simd<Float>(listOf((-5.5f).toFloat(), 75.6f.toFloat(), (-1000.7f).toFloat(), 1219.3f.toFloat()))
    val r = Simd<Int>(listOf(-5, 75, -1000, 1219))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFtruncSW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFtruncSD() {
    val a = Simd<Double>(listOf((-5.5).toDouble(), 25656.4.toDouble()))
    val r = Simd<Long>(listOf(-5L, 25656L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFtruncSD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFtruncUW() {
    val a = Simd<Float>(listOf((-5.5f).toFloat(), 75.6f.toFloat(), (-1000.7f).toFloat(), 1219.3f.toFloat()))
    val r = Simd<UInt>(listOf(0u, 75u, 0u, 1219u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaFtruncUW(transmuteWithLayouts<Any?, V4f32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4f32>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFtruncUD() {
    val a = Simd<Double>(listOf(5.5.toDouble(), (-25656.4).toDouble()))
    val r = Simd<ULong>(listOf(5uL, 0uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaFtruncUD(transmuteWithLayouts<Any?, V2f64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2f64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaHaddSH() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte()))
    val b = Simd<Byte>(listOf(4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte()))
    val r = Simd<Short>(listOf(6.toShort(), 6.toShort(), 2.toShort(), (-2).toShort(), 6.toShort(), 6.toShort(), 2.toShort(), (-2).toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaHaddSH(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaHaddSW() {
    val a = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), (-1).toShort(), (-2).toShort(), (-3).toShort(), (-4).toShort()))
    val b = Simd<Short>(listOf(4.toShort(), 3.toShort(), 2.toShort(), 1.toShort(), 4.toShort(), 3.toShort(), 2.toShort(), 1.toShort()))
    val r = Simd<Int>(listOf(6, 6, 2, -2))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaHaddSW(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaHaddSD() {
    val a = Simd<Int>(listOf(1, -2, 3, -4))
    val b = Simd<Int>(listOf(4, 3, 2, 1))
    val r = Simd<Long>(listOf(2L, -2L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaHaddSD(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaHaddUH() {
    val a = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte()))
    val b = Simd<UByte>(listOf(4u.toUByte(), 3u.toUByte(), 2u.toUByte(), 1u.toUByte(), 4u.toUByte(), 3u.toUByte(), 2u.toUByte(), 1u.toUByte(), 4u.toUByte(), 3u.toUByte(), 2u.toUByte(), 1u.toUByte(), 4u.toUByte(), 3u.toUByte(), 2u.toUByte(), 1u.toUByte()))
    val r = Simd<UShort>(listOf(6u.toUShort(), 6u.toUShort(), 6u.toUShort(), 6u.toUShort(), 6u.toUShort(), 6u.toUShort(), 6u.toUShort(), 6u.toUShort()))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaHaddUH(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaHaddUW() {
    val a = Simd<UShort>(listOf(1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort()))
    val b = Simd<UShort>(listOf(4u.toUShort(), 3u.toUShort(), 2u.toUShort(), 1u.toUShort(), 4u.toUShort(), 3u.toUShort(), 2u.toUShort(), 1u.toUShort()))
    val r = Simd<UInt>(listOf(6u, 6u, 6u, 6u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaHaddUW(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaHaddUD() {
    val a = Simd<UInt>(listOf(1u, 2u, 3u, 4u))
    val b = Simd<UInt>(listOf(4u, 3u, 2u, 1u))
    val r = Simd<ULong>(listOf(6uL, 6uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaHaddUD(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaHsubSH() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte()))
    val b = Simd<Byte>(listOf(4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte()))
    val r = Simd<Short>(listOf((-2).toShort(), 2.toShort(), (-6).toShort(), (-6).toShort(), (-2).toShort(), 2.toShort(), (-6).toShort(), (-6).toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaHsubSH(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaHsubSW() {
    val a = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), (-1).toShort(), (-2).toShort(), (-3).toShort(), (-4).toShort()))
    val b = Simd<Short>(listOf(4.toShort(), 3.toShort(), 2.toShort(), 1.toShort(), 4.toShort(), 3.toShort(), 2.toShort(), 1.toShort()))
    val r = Simd<Int>(listOf(-2, 2, -6, -6))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaHsubSW(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaHsubSD() {
    val a = Simd<Int>(listOf(1, -2, 3, -4))
    val b = Simd<Int>(listOf(4, 3, 2, 1))
    val r = Simd<Long>(listOf(-6L, -6L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaHsubSD(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaHsubUH() {
    val a = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte()))
    val b = Simd<UByte>(listOf(4u.toUByte(), 3u.toUByte(), 2u.toUByte(), 1u.toUByte(), 4u.toUByte(), 3u.toUByte(), 2u.toUByte(), 1u.toUByte(), 4u.toUByte(), 3u.toUByte(), 2u.toUByte(), 1u.toUByte(), 4u.toUByte(), 3u.toUByte(), 2u.toUByte(), 1u.toUByte()))
    val r = Simd<Short>(listOf((-2).toShort(), 2.toShort(), (-2).toShort(), 2.toShort(), (-2).toShort(), 2.toShort(), (-2).toShort(), 2.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaHsubUH(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaHsubUW() {
    val a = Simd<UShort>(listOf(1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort()))
    val b = Simd<UShort>(listOf(4u.toUShort(), 3u.toUShort(), 2u.toUShort(), 1u.toUShort(), 4u.toUShort(), 3u.toUShort(), 2u.toUShort(), 1u.toUShort()))
    val r = Simd<Int>(listOf(-2, 2, -2, 2))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaHsubUW(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaHsubUD() {
    val a = Simd<UInt>(listOf(1u, 2u, 3u, 4u))
    val b = Simd<UInt>(listOf(4u, 3u, 2u, 1u))
    val r = Simd<Long>(listOf(-2L, 2L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaHsubUD(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvevB() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte()))
    val b = Simd<Byte>(listOf(4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte()))
    val r = Simd<Byte>(listOf(4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaIlvevB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvevH() {
    val a = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort()))
    val b = Simd<Short>(listOf(4.toShort(), 3.toShort(), 2.toShort(), 1.toShort(), 4.toShort(), 3.toShort(), 2.toShort(), 1.toShort()))
    val r = Simd<Short>(listOf(4.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 1.toShort(), 2.toShort(), 3.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaIlvevH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvevW() {
    val a = Simd<Int>(listOf(1, 2, 3, 4))
    val b = Simd<Int>(listOf(4, 3, 2, 1))
    val r = Simd<Int>(listOf(4, 1, 2, 3))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaIlvevW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvevD() {
    val a = Simd<Long>(listOf(1L, 2L))
    val b = Simd<Long>(listOf(4L, 3L))
    val r = Simd<Long>(listOf(4L, 1L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaIlvevD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvlB() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte(), 10.toByte(), 11.toByte(), 12.toByte(), 13.toByte(), 14.toByte(), 15.toByte(), 16.toByte()))
    val b = Simd<Byte>(listOf(16.toByte(), 15.toByte(), 14.toByte(), 13.toByte(), 12.toByte(), 11.toByte(), 10.toByte(), 9.toByte(), 8.toByte(), 7.toByte(), 6.toByte(), 5.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte()))
    val r = Simd<Byte>(listOf(8.toByte(), 9.toByte(), 7.toByte(), 10.toByte(), 6.toByte(), 11.toByte(), 5.toByte(), 12.toByte(), 4.toByte(), 13.toByte(), 3.toByte(), 14.toByte(), 2.toByte(), 15.toByte(), 1.toByte(), 16.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaIlvlB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvlH() {
    val a = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 7.toShort(), 8.toShort()))
    val b = Simd<Short>(listOf(8.toShort(), 7.toShort(), 6.toShort(), 5.toShort(), 4.toShort(), 3.toShort(), 2.toShort(), 1.toShort()))
    val r = Simd<Short>(listOf(4.toShort(), 5.toShort(), 3.toShort(), 6.toShort(), 2.toShort(), 7.toShort(), 1.toShort(), 8.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaIlvlH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvlW() {
    val a = Simd<Int>(listOf(1, 2, 3, 4))
    val b = Simd<Int>(listOf(4, 3, 2, 1))
    val r = Simd<Int>(listOf(2, 3, 1, 4))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaIlvlW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvlD() {
    val a = Simd<Long>(listOf(1L, 2L))
    val b = Simd<Long>(listOf(2L, 1L))
    val r = Simd<Long>(listOf(1L, 2L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaIlvlD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvodB() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte(), 10.toByte(), 11.toByte(), 12.toByte(), 13.toByte(), 14.toByte(), 15.toByte(), 16.toByte()))
    val b = Simd<Byte>(listOf(16.toByte(), 15.toByte(), 14.toByte(), 13.toByte(), 12.toByte(), 11.toByte(), 10.toByte(), 9.toByte(), 8.toByte(), 7.toByte(), 6.toByte(), 5.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte()))
    val r = Simd<Byte>(listOf(15.toByte(), 2.toByte(), 13.toByte(), 4.toByte(), 11.toByte(), 6.toByte(), 9.toByte(), 8.toByte(), 7.toByte(), 10.toByte(), 5.toByte(), 12.toByte(), 3.toByte(), 14.toByte(), 1.toByte(), 16.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaIlvodB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvodH() {
    val a = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 7.toShort(), 8.toShort()))
    val b = Simd<Short>(listOf(8.toShort(), 7.toShort(), 6.toShort(), 5.toShort(), 4.toShort(), 3.toShort(), 2.toShort(), 1.toShort()))
    val r = Simd<Short>(listOf(7.toShort(), 2.toShort(), 5.toShort(), 4.toShort(), 3.toShort(), 6.toShort(), 1.toShort(), 8.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaIlvodH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvodW() {
    val a = Simd<Int>(listOf(1, 2, 3, 4))
    val b = Simd<Int>(listOf(4, 3, 2, 1))
    val r = Simd<Int>(listOf(3, 2, 1, 4))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaIlvodW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvodD() {
    val a = Simd<Long>(listOf(1L, 2L))
    val b = Simd<Long>(listOf(2L, 1L))
    val r = Simd<Long>(listOf(1L, 2L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaIlvodD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvrB() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte(), 10.toByte(), 11.toByte(), 12.toByte(), 13.toByte(), 14.toByte(), 15.toByte(), 16.toByte()))
    val b = Simd<Byte>(listOf(16.toByte(), 15.toByte(), 14.toByte(), 13.toByte(), 12.toByte(), 11.toByte(), 10.toByte(), 9.toByte(), 8.toByte(), 7.toByte(), 6.toByte(), 5.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte()))
    val r = Simd<Byte>(listOf(16.toByte(), 1.toByte(), 15.toByte(), 2.toByte(), 14.toByte(), 3.toByte(), 13.toByte(), 4.toByte(), 12.toByte(), 5.toByte(), 11.toByte(), 6.toByte(), 10.toByte(), 7.toByte(), 9.toByte(), 8.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaIlvrB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvrH() {
    val a = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 7.toShort(), 8.toShort()))
    val b = Simd<Short>(listOf(8.toShort(), 7.toShort(), 6.toShort(), 5.toShort(), 4.toShort(), 3.toShort(), 2.toShort(), 1.toShort()))
    val r = Simd<Short>(listOf(8.toShort(), 1.toShort(), 7.toShort(), 2.toShort(), 6.toShort(), 3.toShort(), 5.toShort(), 4.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaIlvrH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvrW() {
    val a = Simd<Int>(listOf(1, 2, 3, 4))
    val b = Simd<Int>(listOf(4, 3, 2, 1))
    val r = Simd<Int>(listOf(4, 1, 3, 2))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaIlvrW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvrD() {
    val a = Simd<Long>(listOf(1L, 2L))
    val b = Simd<Long>(listOf(2L, 1L))
    val r = Simd<Long>(listOf(2L, 1L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaIlvrD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaInsertB() {
    val a = Simd<Byte>(listOf((-100).toByte(), 127.toByte(), 4.toByte(), 127.toByte(), (-100).toByte(), 127.toByte(), 4.toByte(), 127.toByte(), (-100).toByte(), 127.toByte(), 4.toByte(), 127.toByte(), (-100).toByte(), 127.toByte(), 4.toByte(), 127.toByte()))
    val r = Simd<Byte>(listOf((-100).toByte(), 127.toByte(), 4.toByte(), 127.toByte(), (-100).toByte(), 127.toByte(), 4.toByte(), 127.toByte(), (-100).toByte(), 127.toByte(), 4.toByte(), 127.toByte(), 5.toByte(), 127.toByte(), 4.toByte(), 127.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaInsertB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), 5, 12), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaInsertH() {
    val a = Simd<Short>(listOf(32767.toShort(), 3276.toShort(), 100.toShort(), 11.toShort(), 32767.toShort(), 3276.toShort(), 100.toShort(), 11.toShort()))
    val r = Simd<Short>(listOf(32767.toShort(), 3276.toShort(), 100.toShort(), 11.toShort(), 5.toShort(), 3276.toShort(), 100.toShort(), 11.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaInsertH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), 5, 4), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaInsertW() {
    val a = Simd<Int>(listOf(100, 2147483647, 5, -2147483647))
    val r = Simd<Int>(listOf(100, 7, 5, -2147483647))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaInsertW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), 7, 1), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaInsertD() {
    val a = Simd<Long>(listOf(3L, Long.MAX_VALUE))
    val r = Simd<Long>(listOf(3L, 100L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaInsertD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), 100L, 1), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaInsveB() {
    val a = Simd<Byte>(listOf((-100).toByte(), Byte.MAX_VALUE, 4.toByte(), Byte.MAX_VALUE, (-100).toByte(), Byte.MAX_VALUE, 4.toByte(), Byte.MAX_VALUE, (-100).toByte(), Byte.MAX_VALUE, 4.toByte(), Byte.MAX_VALUE, (-100).toByte(), Byte.MAX_VALUE, 4.toByte(), Byte.MAX_VALUE))
    val b = Simd<Byte>(listOf(5.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte()))
    val r = Simd<Byte>(listOf((-100).toByte(), 127.toByte(), 4.toByte(), 127.toByte(), (-100).toByte(), 127.toByte(), 4.toByte(), 127.toByte(), (-100).toByte(), 127.toByte(), 4.toByte(), 127.toByte(), 5.toByte(), 127.toByte(), 4.toByte(), 127.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaInsveB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, Int>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<Int>()), 12), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaInsveH() {
    val a = Simd<Short>(listOf(Short.MAX_VALUE, 3276.toShort(), 100.toShort(), 11.toShort(), Short.MAX_VALUE, 3276.toShort(), 100.toShort(), 11.toShort()))
    val b = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort()))
    val r = Simd<Short>(listOf(32767.toShort(), 3276.toShort(), 100.toShort(), 11.toShort(), 1.toShort(), 3276.toShort(), 100.toShort(), 11.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaInsveH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, Int>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<Int>()), 4), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaInsveW() {
    val a = Simd<Int>(listOf(100, 2147483647, 5, -2147483647))
    val b = Simd<Int>(listOf(1, 2, 3, 4))
    val r = Simd<Int>(listOf(100, 2147483647, 5, 1))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaInsveW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, Int>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<Int>()), 3), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaInsveD() {
    val a = Simd<Long>(listOf(3L, Long.MAX_VALUE))
    val b = Simd<Long>(listOf(1L, 2L))
    val r = Simd<Long>(listOf(3L, 1L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaInsveD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, Int>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<Int>()), 1), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaLdB() {
    val a: ByteArray  = byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val p = (mutPtrFromRustCast(a[4])).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    val r = Simd<Byte>(listOf(13.toByte(), 14.toByte(), 15.toByte(), 16.toByte(), 17.toByte(), 18.toByte(), 19.toByte(), 20.toByte(), 21.toByte(), 22.toByte(), 23.toByte(), 24.toByte(), 25.toByte(), 26.toByte(), 27.toByte(), 28.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaLdB(p, 9), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaLdH() {
    val a: ShortArray  = shortArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val p = (mutPtrFromRustCast(a[4])).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    val r = Simd<Short>(listOf(3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 7.toShort(), 8.toShort(), 9.toShort(), 10.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaLdH(p, -2), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaLdW() {
    val a: IntArray  = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7)
    val p = (mutPtrFromRustCast(a[3])).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    val r = Simd<Int>(listOf(2, 3, 4, 5))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaLdW(p, -4), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaLdD() {
    val a: LongArray  = longArrayOf(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val p = (mutPtrFromRustCast(a[4])).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    val r = Simd<Long>(listOf(0L, 1L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaLdD(p, -32), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaLdiB() {
    val r = Simd<Byte>(listOf((-20).toByte(), (-20).toByte(), (-20).toByte(), (-20).toByte(), (-20).toByte(), (-20).toByte(), (-20).toByte(), (-20).toByte(), (-20).toByte(), (-20).toByte(), (-20).toByte(), (-20).toByte(), (-20).toByte(), (-20).toByte(), (-20).toByte(), (-20).toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaLdiB(-20), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaLdiH() {
    val r = Simd<Short>(listOf(255.toShort(), 255.toShort(), 255.toShort(), 255.toShort(), 255.toShort(), 255.toShort(), 255.toShort(), 255.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaLdiH(255), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaLdiW() {
    val r = Simd<Int>(listOf(-509, -509, -509, -509))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaLdiW(-509), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

// Note: https://reviews.llvm.org/D59884
// If target type is i64, negative immediate loses the sign
// Test passes if 4294967185 is used instead -111 in vector `r`
// @simdTest(enable = "msa")
// unchecked fn testMsaLdiD() (
// let r = i64x2.new(-111, -111);
// assertEq(r, mem.transmute(__msa_ldi_d(-111)));
// )
public fun testMsaMaddQH() {
    val a = Simd<Short>(listOf(Short.MAX_VALUE, 1024.toShort(), Short.MIN_VALUE, (-1024).toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort()))
    val b = Simd<Short>(listOf(1024.toShort(), 1024.toShort(), 1024.toShort(), 1024.toShort(), 1024.toShort(), 1024.toShort(), 1024.toShort(), 1024.toShort()))
    val c = Simd<Short>(listOf(Short.MAX_VALUE, Short.MAX_VALUE, 1.toShort(), (-1).toShort(), 33.toShort(), 66.toShort(), 99.toShort(), 132.toShort()))
    val r = Simd<Short>(listOf(32767.toShort(), 2047.toShort(), (-32768).toShort(), (-1025).toShort(), 2.toShort(), 4.toShort(), 6.toShort(), 8.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMaddQH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaddQW() {
    val a = Simd<Int>(listOf(Int.MAX_VALUE, Int.MIN_VALUE, 1, 2))
    val b = Simd<Int>(listOf(102401, 102401, 102401, 102401))
    val c = Simd<Int>(listOf(10240, 20480, 30720, 40960))
    val r = Simd<Int>(listOf(2147483647, -2147483648.toInt(), 2, 3))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMaddQW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaddrQH() {
    val a = Simd<Short>(listOf(32767.toShort(), 1024.toShort(), (-32768).toShort(), (-1024).toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort()))
    val b = Simd<Short>(listOf(1024.toShort(), 1024.toShort(), 1024.toShort(), 1024.toShort(), 1024.toShort(), 1024.toShort(), 1024.toShort(), 1024.toShort()))
    val c = Simd<Short>(listOf(32767.toShort(), 32767.toShort(), 32767.toShort(), 32767.toShort(), 33.toShort(), 66.toShort(), 99.toShort(), 132.toShort()))
    val r = Simd<Short>(listOf(32767.toShort(), 2048.toShort(), (-31744).toShort(), 0.toShort(), 2.toShort(), 4.toShort(), 6.toShort(), 8.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMaddrQH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaddrQW() {
    val a = Simd<Int>(listOf(Int.MAX_VALUE, Int.MIN_VALUE, 1, 2))
    val b = Simd<Int>(listOf(102401, 102401, 102401, 102401))
    val c = Simd<Int>(listOf(10240, 20480, 30720, 40960))
    val r = Simd<Int>(listOf(2147483647, -2147483647, 2, 4))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMaddrQW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaddvB() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte()))
    val b = Simd<Byte>(listOf(5.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte()))
    val c = Simd<Byte>(listOf(9.toByte(), 10.toByte(), 11.toByte(), 12.toByte(), 9.toByte(), 10.toByte(), 11.toByte(), 12.toByte(), 9.toByte(), 10.toByte(), 11.toByte(), 12.toByte(), 9.toByte(), 10.toByte(), 11.toByte(), 12.toByte()))
    val r = Simd<Byte>(listOf(46.toByte(), 62.toByte(), 80.toByte(), 100.toByte(), 46.toByte(), 62.toByte(), 80.toByte(), 100.toByte(), 46.toByte(), 62.toByte(), 80.toByte(), 100.toByte(), 46.toByte(), 62.toByte(), 80.toByte(), 100.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaMaddvB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaddvH() {
    val a = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort()))
    val b = Simd<Short>(listOf(5.toShort(), 6.toShort(), 7.toShort(), 8.toShort(), 5.toShort(), 6.toShort(), 7.toShort(), 8.toShort()))
    val c = Simd<Short>(listOf(9.toShort(), 10.toShort(), 11.toShort(), 12.toShort(), 9.toShort(), 10.toShort(), 11.toShort(), 12.toShort()))
    val r = Simd<Short>(listOf(46.toShort(), 62.toShort(), 80.toShort(), 100.toShort(), 46.toShort(), 62.toShort(), 80.toShort(), 100.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMaddvH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaddvW() {
    val a = Simd<Int>(listOf(1, 2, 1, 2))
    val b = Simd<Int>(listOf(3, 4, 3, 4))
    val c = Simd<Int>(listOf(5, 6, 5, 6))
    val r = Simd<Int>(listOf(16, 26, 16, 26))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMaddvW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaddvD() {
    val a = Simd<Long>(listOf(1L, 2L))
    val b = Simd<Long>(listOf(3L, 4L))
    val c = Simd<Long>(listOf(5L, 6L))
    val r = Simd<Long>(listOf(16L, 26L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaMaddvD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxAB() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte()))
    val b = Simd<Byte>(listOf((-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte()))
    val r = Simd<Byte>(listOf((-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaMaxAB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxAH() {
    val a = Simd<Short>(listOf(1.toShort(), (-2).toShort(), 3.toShort(), (-4).toShort(), 1.toShort(), (-2).toShort(), 3.toShort(), (-4).toShort()))
    val b = Simd<Short>(listOf((-6).toShort(), 7.toShort(), (-8).toShort(), 9.toShort(), (-6).toShort(), 7.toShort(), (-8).toShort(), 9.toShort()))
    val r = Simd<Short>(listOf((-6).toShort(), 7.toShort(), (-8).toShort(), 9.toShort(), (-6).toShort(), 7.toShort(), (-8).toShort(), 9.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMaxAH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxAW() {
    val a = Simd<Int>(listOf(1, -2, 3, -4))
    val b = Simd<Int>(listOf(6, 7, 8, 9))
    val r = Simd<Int>(listOf(6, 7, 8, 9))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMaxAW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxAD() {
    val a = Simd<Long>(listOf(-1L, 2L))
    val b = Simd<Long>(listOf(6L, -7L))
    val r = Simd<Long>(listOf(6L, -7L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaMaxAD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxSB() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte()))
    val b = Simd<Byte>(listOf((-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte()))
    val r = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaMaxSB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxSH() {
    val a = Simd<Short>(listOf(1.toShort(), (-2).toShort(), 3.toShort(), (-4).toShort(), 1.toShort(), (-2).toShort(), 3.toShort(), (-4).toShort()))
    val b = Simd<Short>(listOf((-6).toShort(), 7.toShort(), (-8).toShort(), 9.toShort(), (-6).toShort(), 7.toShort(), (-8).toShort(), 9.toShort()))
    val r = Simd<Short>(listOf(1.toShort(), 7.toShort(), 3.toShort(), 9.toShort(), 1.toShort(), 7.toShort(), 3.toShort(), 9.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMaxSH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxSW() {
    val a = Simd<Int>(listOf(1, -2, 3, -4))
    val b = Simd<Int>(listOf(6, 7, 8, 9))
    val r = Simd<Int>(listOf(6, 7, 8, 9))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMaxSW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxSD() {
    val a = Simd<Long>(listOf(-1L, 2L))
    val b = Simd<Long>(listOf(6L, -7L))
    val r = Simd<Long>(listOf(6L, 2L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaMaxSD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxUB() {
    val a = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte()))
    val b = Simd<UByte>(listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte()))
    val r = Simd<UByte>(listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaMaxUB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxUH() {
    val a = Simd<UShort>(listOf(1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort()))
    val b = Simd<UShort>(listOf(6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort(), 6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort()))
    val r = Simd<UShort>(listOf(6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort(), 6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort()))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaMaxUH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxUW() {
    val a = Simd<UInt>(listOf(1u, 2u, 3u, 4u))
    val b = Simd<UInt>(listOf(6u, 7u, 8u, 9u))
    val r = Simd<UInt>(listOf(6u, 7u, 8u, 9u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaMaxUW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxUD() {
    val a = Simd<ULong>(listOf(1uL, 2uL))
    val b = Simd<ULong>(listOf(6uL, 7uL))
    val r = Simd<ULong>(listOf(6uL, 7uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaMaxUD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<Any?, V2u64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxiSB() {
    val a = Simd<Byte>(listOf(1.toByte(), (-20).toByte(), (-6).toByte(), 8.toByte(), 1.toByte(), (-20).toByte(), (-6).toByte(), 8.toByte(), 1.toByte(), (-20).toByte(), (-6).toByte(), 8.toByte(), 1.toByte(), (-20).toByte(), (-6).toByte(), 8.toByte()))
    val r = Simd<Byte>(listOf(1.toByte(), (-16).toByte(), (-6).toByte(), 8.toByte(), 1.toByte(), (-16).toByte(), (-6).toByte(), 8.toByte(), 1.toByte(), (-16).toByte(), (-6).toByte(), 8.toByte(), 1.toByte(), (-16).toByte(), (-6).toByte(), 8.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaMaxiSB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), -16), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxiSH() {
    val a = Simd<Short>(listOf(1.toShort(), 3.toShort(), (-60).toShort(), (-8).toShort(), 1.toShort(), 3.toShort(), (-6).toShort(), (-8).toShort()))
    val r = Simd<Short>(listOf(15.toShort(), 15.toShort(), 15.toShort(), 15.toShort(), 15.toShort(), 15.toShort(), 15.toShort(), 15.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMaxiSH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), 15), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxiSW() {
    val a = Simd<Int>(listOf(1, 3, -6, -8))
    val r = Simd<Int>(listOf(1, 3, -5, -5))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMaxiSW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), -5), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

// Note: https://reviews.llvm.org/D59884
// If target type is i64, negative immediate loses the sign
// Test passes if 4294967293 is used instead -3 in vector `r`
// @simdTest(enable = "msa")
// unchecked fn testMsaMaxiSD() (
// #[rustfmt.skip]
// let a = i64x2.new(1, -8);
// #[rustfmt.skip]
// let r = i64x2.new(-3, -3);
// assertEq(r, mem.transmute(__msa_maxi_s_d(mem.transmute(a), -3)));
// )
public fun testMsaMaxiUB() {
    val a = Simd<UByte>(listOf(1u.toUByte(), 3u.toUByte(), 6u.toUByte(), 8u.toUByte(), 1u.toUByte(), 3u.toUByte(), 6u.toUByte(), 8u.toUByte(), 1u.toUByte(), 3u.toUByte(), 6u.toUByte(), 8u.toUByte(), 1u.toUByte(), 3u.toUByte(), 6u.toUByte(), 8u.toUByte()))
    val r = Simd<UByte>(listOf(5u.toUByte(), 5u.toUByte(), 6u.toUByte(), 8u.toUByte(), 5u.toUByte(), 5u.toUByte(), 6u.toUByte(), 8u.toUByte(), 5u.toUByte(), 5u.toUByte(), 6u.toUByte(), 8u.toUByte(), 5u.toUByte(), 5u.toUByte(), 6u.toUByte(), 8u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaMaxiUB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), 5), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxiUH() {
    val a = Simd<UShort>(listOf(1u.toUShort(), 3u.toUShort(), 6u.toUShort(), 8u.toUShort(), 1u.toUShort(), 3u.toUShort(), 6u.toUShort(), 8u.toUShort()))
    val r = Simd<UShort>(listOf(5u.toUShort(), 5u.toUShort(), 6u.toUShort(), 8u.toUShort(), 5u.toUShort(), 5u.toUShort(), 6u.toUShort(), 8u.toUShort()))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaMaxiUH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), 5), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxiUW() {
    val a = Simd<UInt>(listOf(1u, 3u, 6u, 8u))
    val r = Simd<UInt>(listOf(5u, 5u, 6u, 8u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaMaxiUW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), 5), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxiUD() {
    val a = Simd<ULong>(listOf(1uL, 8uL))
    val r = Simd<ULong>(listOf(5uL, 8uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaMaxiUD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), 5), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMinAB() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte()))
    val b = Simd<Byte>(listOf((-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte()))
    val r = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaMinAB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMinAH() {
    val a = Simd<Short>(listOf(1.toShort(), (-2).toShort(), 3.toShort(), (-4).toShort(), 1.toShort(), (-2).toShort(), 3.toShort(), (-4).toShort()))
    val b = Simd<Short>(listOf((-6).toShort(), 7.toShort(), (-8).toShort(), 9.toShort(), (-6).toShort(), 7.toShort(), (-8).toShort(), 9.toShort()))
    val r = Simd<Short>(listOf(1.toShort(), (-2).toShort(), 3.toShort(), (-4).toShort(), 1.toShort(), (-2).toShort(), 3.toShort(), (-4).toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMinAH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMinAW() {
    val a = Simd<Int>(listOf(1, -2, 3, -4))
    val b = Simd<Int>(listOf(6, 7, 8, 9))
    val r = Simd<Int>(listOf(1, -2, 3, -4))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMinAW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMinAD() {
    val a = Simd<Long>(listOf(-1L, 2L))
    val b = Simd<Long>(listOf(6L, -7L))
    val r = Simd<Long>(listOf(-1L, 2L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaMinAD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMinSB() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte()))
    val b = Simd<Byte>(listOf((-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte()))
    val r = Simd<Byte>(listOf((-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaMinSB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMinSH() {
    val a = Simd<Short>(listOf(1.toShort(), (-2).toShort(), 3.toShort(), (-4).toShort(), 1.toShort(), (-2).toShort(), 3.toShort(), (-4).toShort()))
    val b = Simd<Short>(listOf((-6).toShort(), 7.toShort(), (-8).toShort(), 9.toShort(), (-6).toShort(), 7.toShort(), (-8).toShort(), 9.toShort()))
    val r = Simd<Short>(listOf((-6).toShort(), (-2).toShort(), (-8).toShort(), (-4).toShort(), (-6).toShort(), (-2).toShort(), (-8).toShort(), (-4).toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMinSH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMinSW() {
    val a = Simd<Int>(listOf(1, -2, 3, -4))
    val b = Simd<Int>(listOf(6, 7, 8, 9))
    val r = Simd<Int>(listOf(1, -2, 3, -4))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMinSW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMinSD() {
    val a = Simd<Long>(listOf(-1L, 2L))
    val b = Simd<Long>(listOf(6L, -7L))
    val r = Simd<Long>(listOf(-1L, -7L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaMinSD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMiniSB() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte()))
    val r = Simd<Byte>(listOf((-10).toByte(), (-10).toByte(), (-10).toByte(), (-10).toByte(), (-10).toByte(), (-10).toByte(), (-10).toByte(), (-10).toByte(), (-10).toByte(), (-10).toByte(), (-10).toByte(), (-10).toByte(), (-10).toByte(), (-10).toByte(), (-10).toByte(), (-10).toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaMiniSB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), -10), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMiniSH() {
    val a = Simd<Short>(listOf(1.toShort(), (-2).toShort(), 3.toShort(), (-4).toShort(), 1.toShort(), (-2).toShort(), 3.toShort(), (-4).toShort()))
    val r = Simd<Short>(listOf((-3).toShort(), (-3).toShort(), (-3).toShort(), (-4).toShort(), (-3).toShort(), (-3).toShort(), (-3).toShort(), (-4).toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMiniSH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), -3), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMiniSW() {
    val a = Simd<Int>(listOf(1, -2, 3, -4))
    val r = Simd<Int>(listOf(-3, -3, -3, -4))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMiniSW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), -3), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

// Note: https://reviews.llvm.org/D59884
// If target type is i64, negative immediate loses the sign
// -3 is represented as 4294967293
// @simdTest(enable = "msa")
// unchecked fn testMsaMiniSD() (
// #[rustfmt.skip]
// let a = i64x2.new(-3, 2);
// #[rustfmt.skip]
// let r = i64x2.new(-1, -3);
// assertEq(r, mem.transmute(__msa_mini_s_d(mem.transmute(a), -3)));
// )
public fun testMsaMinUB() {
    val a = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte()))
    val b = Simd<UByte>(listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte()))
    val r = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaMinUB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMinUH() {
    val a = Simd<UShort>(listOf(1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort()))
    val b = Simd<UShort>(listOf(6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort(), 6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort()))
    val r = Simd<UShort>(listOf(1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort()))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaMinUH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMinUW() {
    val a = Simd<UInt>(listOf(1u, 2u, 3u, 4u))
    val b = Simd<UInt>(listOf(6u, 7u, 8u, 9u))
    val r = Simd<UInt>(listOf(1u, 2u, 3u, 4u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaMinUW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMinUD() {
    val a = Simd<ULong>(listOf(1uL, 2uL))
    val b = Simd<ULong>(listOf(6uL, 7uL))
    val r = Simd<ULong>(listOf(1uL, 2uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaMinUD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<Any?, V2u64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMiniUB() {
    val a = Simd<UByte>(listOf(1u.toUByte(), 3u.toUByte(), 6u.toUByte(), 8u.toUByte(), 1u.toUByte(), 3u.toUByte(), 6u.toUByte(), 8u.toUByte(), 1u.toUByte(), 3u.toUByte(), 6u.toUByte(), 8u.toUByte(), 1u.toUByte(), 3u.toUByte(), 6u.toUByte(), 8u.toUByte()))
    val r = Simd<UByte>(listOf(1u.toUByte(), 3u.toUByte(), 5u.toUByte(), 5u.toUByte(), 1u.toUByte(), 3u.toUByte(), 5u.toUByte(), 5u.toUByte(), 1u.toUByte(), 3u.toUByte(), 5u.toUByte(), 5u.toUByte(), 1u.toUByte(), 3u.toUByte(), 5u.toUByte(), 5u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaMiniUB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), 5), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMiniUH() {
    val a = Simd<UShort>(listOf(1u.toUShort(), 3u.toUShort(), 6u.toUShort(), 8u.toUShort(), 1u.toUShort(), 3u.toUShort(), 6u.toUShort(), 8u.toUShort()))
    val r = Simd<UShort>(listOf(1u.toUShort(), 3u.toUShort(), 5u.toUShort(), 5u.toUShort(), 1u.toUShort(), 3u.toUShort(), 5u.toUShort(), 5u.toUShort()))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaMiniUH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), 5), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMiniUW() {
    val a = Simd<UInt>(listOf(1u, 3u, 6u, 8u))
    val r = Simd<UInt>(listOf(1u, 3u, 5u, 5u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaMiniUW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), 5), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMiniUD() {
    val a = Simd<ULong>(listOf(1uL, 8uL))
    val r = Simd<ULong>(listOf(1uL, 5uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaMiniUD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), 5), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaModSB() {
    val a = Simd<Byte>(listOf((-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte()))
    val b = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte()))
    val r = Simd<Byte>(listOf(0.toByte(), (-1).toByte(), (-2).toByte(), (-1).toByte(), 0.toByte(), 1.toByte(), 2.toByte(), 1.toByte(), 0.toByte(), (-1).toByte(), (-2).toByte(), (-1).toByte(), 0.toByte(), 1.toByte(), 2.toByte(), 1.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaModSB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaModSH() {
    val a = Simd<Short>(listOf((-6).toShort(), 7.toShort(), (-8).toShort(), 9.toShort(), (-6).toShort(), 7.toShort(), (-8).toShort(), 9.toShort()))
    val b = Simd<Short>(listOf(1.toShort(), (-2).toShort(), 3.toShort(), (-4).toShort(), 1.toShort(), (-2).toShort(), 3.toShort(), (-4).toShort()))
    val r = Simd<Short>(listOf(0.toShort(), 1.toShort(), (-2).toShort(), 1.toShort(), 0.toShort(), 1.toShort(), (-2).toShort(), 1.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaModSH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaModSW() {
    val a = Simd<Int>(listOf(6, 7, 8, 9))
    val b = Simd<Int>(listOf(1, -2, 3, -4))
    val r = Simd<Int>(listOf(0, 1, 2, 1))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaModSW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaModSD() {
    val a = Simd<Long>(listOf(6L, -7L))
    val b = Simd<Long>(listOf(-1L, 2L))
    val r = Simd<Long>(listOf(0L, -1L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaModSD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaModUB() {
    val a = Simd<UByte>(listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte()))
    val b = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte()))
    val r = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 1u.toUByte(), 0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 1u.toUByte(), 0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 1u.toUByte(), 0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 1u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaModUB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaModUH() {
    val a = Simd<UShort>(listOf(6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort(), 6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort()))
    val b = Simd<UShort>(listOf(1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort()))
    val r = Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 1u.toUShort(), 0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 1u.toUShort()))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaModUH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaModUW() {
    val a = Simd<UInt>(listOf(6u, 7u, 8u, 9u))
    val b = Simd<UInt>(listOf(1u, 2u, 3u, 4u))
    val r = Simd<UInt>(listOf(0u, 1u, 2u, 1u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaModUW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaModUD() {
    val a = Simd<ULong>(listOf(6uL, 7uL))
    val b = Simd<ULong>(listOf(1uL, 2uL))
    val r = Simd<ULong>(listOf(0uL, 1uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaModUD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<Any?, V2u64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMoveV() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte()))
    val r = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaMoveV(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMsubQH() {
    val a = Simd<Short>(listOf(1024.toShort(), (-1024).toShort(), 1024.toShort(), (-1024).toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort()))
    val b = Simd<Short>(listOf(1025.toShort(), 1025.toShort(), 1025.toShort(), 1025.toShort(), 1025.toShort(), 1025.toShort(), 1025.toShort(), 1025.toShort()))
    val c = Simd<Short>(listOf(1024.toShort(), 2048.toShort(), 3072.toShort(), 4096.toShort(), 1024.toShort(), 2048.toShort(), 3072.toShort(), 4096.toShort()))
    val r = Simd<Short>(listOf(991.toShort(), (-1089).toShort(), 927.toShort(), (-1153).toShort(), (-32).toShort(), (-63).toShort(), (-94).toShort(), (-125).toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMsubQH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMsubQW() {
    val a = Simd<Int>(listOf(2147483647, -2147483647, 1, 2))
    val b = Simd<Int>(listOf(10240, 10240, 10240, 10240))
    val c = Simd<Int>(listOf(10240, 20480, 30720, 40960))
    val r = Simd<Int>(listOf(2147483646, -2147483648.toInt(), 0, 1))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMsubQW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMsubrQH() {
    val a = Simd<Short>(listOf(1024.toShort(), (-1024).toShort(), 1024.toShort(), (-1024).toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort()))
    val b = Simd<Short>(listOf(1025.toShort(), 1025.toShort(), 1025.toShort(), 1025.toShort(), 1025.toShort(), 1025.toShort(), 1025.toShort(), 1025.toShort()))
    val c = Simd<Short>(listOf(1024.toShort(), 2048.toShort(), 3072.toShort(), 4096.toShort(), 1024.toShort(), 2048.toShort(), 3072.toShort(), 4096.toShort()))
    val r = Simd<Short>(listOf(992.toShort(), (-1088).toShort(), 928.toShort(), (-1152).toShort(), (-31).toShort(), (-62).toShort(), (-93).toShort(), (-124).toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMsubrQH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMsubrQW() {
    val a = Simd<Int>(listOf(Int.MAX_VALUE, -2147483647, 1, 2))
    val b = Simd<Int>(listOf(10240, 10240, 10240, 10240))
    val c = Simd<Int>(listOf(10240, 20480, 30720, 40960))
    val r = Simd<Int>(listOf(2147483647, -2147483647, 1, 2))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMsubrQW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMsubvB() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte()))
    val b = Simd<Byte>(listOf(5.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte()))
    val c = Simd<Byte>(listOf(9.toByte(), 10.toByte(), 11.toByte(), 12.toByte(), 9.toByte(), 10.toByte(), 11.toByte(), 12.toByte(), 9.toByte(), 10.toByte(), 11.toByte(), 12.toByte(), 9.toByte(), 10.toByte(), 11.toByte(), 12.toByte()))
    val r = Simd<Byte>(listOf((-44).toByte(), (-58).toByte(), (-74).toByte(), (-92).toByte(), (-44).toByte(), (-58).toByte(), (-74).toByte(), (-92).toByte(), (-44).toByte(), (-58).toByte(), (-74).toByte(), (-92).toByte(), (-44).toByte(), (-58).toByte(), (-74).toByte(), (-92).toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaMsubvB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMsubvH() {
    val a = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort()))
    val b = Simd<Short>(listOf(5.toShort(), 6.toShort(), 7.toShort(), 8.toShort(), 5.toShort(), 6.toShort(), 7.toShort(), 8.toShort()))
    val c = Simd<Short>(listOf(9.toShort(), 10.toShort(), 11.toShort(), 12.toShort(), 9.toShort(), 10.toShort(), 11.toShort(), 12.toShort()))
    val r = Simd<Short>(listOf((-44).toShort(), (-58).toShort(), (-74).toShort(), (-92).toShort(), (-44).toShort(), (-58).toShort(), (-74).toShort(), (-92).toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMsubvH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMsubvW() {
    val a = Simd<Int>(listOf(1, 2, 1, 2))
    val b = Simd<Int>(listOf(3, 4, 3, 4))
    val c = Simd<Int>(listOf(5, 6, 5, 6))
    val r = Simd<Int>(listOf(-14, -22, -14, -22))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMsubvW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMsubvD() {
    val a = Simd<Long>(listOf(1L, 2L))
    val b = Simd<Long>(listOf(3L, 4L))
    val c = Simd<Long>(listOf(5L, 6L))
    val r = Simd<Long>(listOf(-14L, -22L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaMsubvD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMulQH() {
    val a = Simd<Short>(listOf(12500.toShort(), (-20).toShort(), (-300).toShort(), 400.toShort(), 12500.toShort(), 20.toShort(), 300.toShort(), 400.toShort()))
    val b = Simd<Short>(listOf(1250.toShort(), 10240.toShort(), (-7585).toShort(), 8456.toShort(), 1250.toShort(), 10240.toShort(), (-7585).toShort(), 8456.toShort()))
    val r = Simd<Short>(listOf(476.toShort(), (-7).toShort(), 69.toShort(), 103.toShort(), 476.toShort(), 6.toShort(), (-70).toShort(), 103.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMulQH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMulQW() {
    val a = Simd<Int>(listOf(Int.MAX_VALUE, Int.MAX_VALUE, Int.MIN_VALUE, Int.MIN_VALUE))
    val b = Simd<Int>(listOf(30, 60, 30, 60))
    val r = Simd<Int>(listOf(29, 59, -30, -60))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMulQW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMulrQH() {
    val a = Simd<Short>(listOf(12500.toShort(), (-20).toShort(), (-300).toShort(), 400.toShort(), 12500.toShort(), 20.toShort(), 300.toShort(), 400.toShort()))
    val b = Simd<Short>(listOf(1250.toShort(), 10240.toShort(), (-7585).toShort(), 8456.toShort(), 1250.toShort(), 10240.toShort(), (-7585).toShort(), 8456.toShort()))
    val r = Simd<Short>(listOf(477.toShort(), (-6).toShort(), 69.toShort(), 103.toShort(), 477.toShort(), 6.toShort(), (-69).toShort(), 103.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMulrQH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMulrQW() {
    val a = Simd<Int>(listOf(Int.MAX_VALUE, Int.MAX_VALUE, Int.MIN_VALUE, Int.MIN_VALUE))
    val b = Simd<Int>(listOf(30, 60, 30, 60))
    val r = Simd<Int>(listOf(30, 60, -30, -60))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMulrQW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMulvB() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte(), 10.toByte(), 11.toByte(), 12.toByte(), 13.toByte(), 14.toByte(), 15.toByte(), 16.toByte()))
    val b = Simd<Byte>(listOf(16.toByte(), 15.toByte(), 14.toByte(), 13.toByte(), 12.toByte(), 11.toByte(), 10.toByte(), 9.toByte(), 8.toByte(), 7.toByte(), 6.toByte(), 5.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte()))
    val r = Simd<Byte>(listOf(16.toByte(), 30.toByte(), 42.toByte(), 52.toByte(), 60.toByte(), 66.toByte(), 70.toByte(), 72.toByte(), 72.toByte(), 70.toByte(), 66.toByte(), 60.toByte(), 52.toByte(), 42.toByte(), 30.toByte(), 16.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaMulvB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMulvH() {
    val a = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 7.toShort(), 8.toShort()))
    val b = Simd<Short>(listOf(8.toShort(), 7.toShort(), 6.toShort(), 5.toShort(), 4.toShort(), 3.toShort(), 2.toShort(), 1.toShort()))
    val r = Simd<Short>(listOf(8.toShort(), 14.toShort(), 18.toShort(), 20.toShort(), 20.toShort(), 18.toShort(), 14.toShort(), 8.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMulvH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMulvW() {
    val a = Simd<Int>(listOf(1, 2, 3, 4))
    val b = Simd<Int>(listOf(4, 3, 2, 1))
    val r = Simd<Int>(listOf(4, 6, 6, 4))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMulvW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMulvD() {
    val a = Simd<Long>(listOf(1L, 2L))
    val b = Simd<Long>(listOf(2L, 1L))
    val r = Simd<Long>(listOf(2L, 2L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaMulvD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaNlocB() {
    val a = Simd<Byte>(listOf((-128).toByte(), (-64).toByte(), (-32).toByte(), (-16).toByte(), (-8).toByte(), (-4).toByte(), (-2).toByte(), (-1).toByte(), 1.toByte(), 2.toByte(), 4.toByte(), 8.toByte(), 16.toByte(), 32.toByte(), 64.toByte(), 127.toByte()))
    val r = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaNlocB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaNlocH() {
    val a = Simd<Short>(listOf((-32768).toShort(), (-16384).toShort(), (-8192).toShort(), (-4096).toShort(), 4096.toShort(), 8192.toShort(), 16384.toShort(), 32767.toShort()))
    val r = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaNlocH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaNlocW() {
    val a = Simd<Int>(listOf(Int.MIN_VALUE, -1073741824, 1073741824, Int.MAX_VALUE))
    val r = Simd<Int>(listOf(1, 2, 0, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaNlocW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaNlocD() {
    val a = Simd<Long>(listOf(Long.MIN_VALUE, Long.MAX_VALUE))
    val r = Simd<Long>(listOf(1L, 0L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaNlocD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaNlzcB() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte(), 10.toByte(), 11.toByte(), 12.toByte(), 13.toByte(), 14.toByte(), 15.toByte(), 16.toByte()))
    val r = Simd<Byte>(listOf(7.toByte(), 6.toByte(), 6.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 5.toByte(), 4.toByte(), 4.toByte(), 4.toByte(), 4.toByte(), 4.toByte(), 4.toByte(), 4.toByte(), 4.toByte(), 3.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaNlzcB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaNlzcH() {
    val a = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 7.toShort(), 8.toShort()))
    val r = Simd<Short>(listOf(15.toShort(), 14.toShort(), 14.toShort(), 13.toShort(), 13.toShort(), 13.toShort(), 13.toShort(), 12.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaNlzcH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaNlzcW() {
    val a = Simd<Int>(listOf(1, 2, 3, 4))
    val r = Simd<Int>(listOf(31, 30, 30, 29))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaNlzcW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaNlzcD() {
    val a = Simd<Long>(listOf(1L, 2L))
    val r = Simd<Long>(listOf(63L, 62L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaNlzcD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaNorV() {
    val a = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte(), 16u.toUByte()))
    val b = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte(), 16u.toUByte()))
    val r = Simd<UByte>(listOf(254u.toUByte(), 253u.toUByte(), 252u.toUByte(), 251u.toUByte(), 250u.toUByte(), 249u.toUByte(), 248u.toUByte(), 247u.toUByte(), 246u.toUByte(), 245u.toUByte(), 244u.toUByte(), 243u.toUByte(), 242u.toUByte(), 241u.toUByte(), 240u.toUByte(), 239u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaNorV(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaNoriB() {
    val a = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte(), 16u.toUByte()))
    val r = Simd<UByte>(listOf(250u.toUByte(), 249u.toUByte(), 248u.toUByte(), 251u.toUByte(), 250u.toUByte(), 249u.toUByte(), 248u.toUByte(), 243u.toUByte(), 242u.toUByte(), 241u.toUByte(), 240u.toUByte(), 243u.toUByte(), 242u.toUByte(), 241u.toUByte(), 240u.toUByte(), 235u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaNoriB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), 4), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaOrV() {
    val a = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte(), 16u.toUByte()))
    val b = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte(), 16u.toUByte()))
    val r = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte(), 16u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaOrV(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaOriB() {
    val a = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte(), 16u.toUByte()))
    val r = Simd<UByte>(listOf(5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte(), 20u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaOriB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), 4), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaPckevB() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte()))
    val b = Simd<Byte>(listOf(4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte()))
    val r = Simd<Byte>(listOf(4.toByte(), 2.toByte(), 4.toByte(), 2.toByte(), 4.toByte(), 2.toByte(), 4.toByte(), 2.toByte(), 1.toByte(), 3.toByte(), 1.toByte(), 3.toByte(), 1.toByte(), 3.toByte(), 1.toByte(), 3.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaPckevB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaPckevH() {
    val a = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort()))
    val b = Simd<Short>(listOf(4.toShort(), 3.toShort(), 2.toShort(), 1.toShort(), 4.toShort(), 3.toShort(), 2.toShort(), 1.toShort()))
    val r = Simd<Short>(listOf(4.toShort(), 2.toShort(), 4.toShort(), 2.toShort(), 1.toShort(), 3.toShort(), 1.toShort(), 3.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaPckevH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaPckevW() {
    val a = Simd<Int>(listOf(1, 2, 3, 4))
    val b = Simd<Int>(listOf(4, 3, 2, 1))
    val r = Simd<Int>(listOf(4, 2, 1, 3))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaPckevW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaPckevD() {
    val a = Simd<Long>(listOf(1L, 2L))
    val b = Simd<Long>(listOf(4L, 3L))
    val r = Simd<Long>(listOf(4L, 1L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaPckevD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaPckodB() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte()))
    val b = Simd<Byte>(listOf(4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte()))
    val r = Simd<Byte>(listOf(3.toByte(), 1.toByte(), 3.toByte(), 1.toByte(), 3.toByte(), 1.toByte(), 3.toByte(), 1.toByte(), 2.toByte(), 4.toByte(), 2.toByte(), 4.toByte(), 2.toByte(), 4.toByte(), 2.toByte(), 4.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaPckodB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaPckodH() {
    val a = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort()))
    val b = Simd<Short>(listOf(4.toShort(), 3.toShort(), 2.toShort(), 1.toShort(), 4.toShort(), 3.toShort(), 2.toShort(), 1.toShort()))
    val r = Simd<Short>(listOf(3.toShort(), 1.toShort(), 3.toShort(), 1.toShort(), 2.toShort(), 4.toShort(), 2.toShort(), 4.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaPckodH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaPckodW() {
    val a = Simd<Int>(listOf(1, 2, 3, 4))
    val b = Simd<Int>(listOf(4, 3, 2, 1))
    val r = Simd<Int>(listOf(3, 1, 2, 4))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaPckodW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaPckodD() {
    val a = Simd<Long>(listOf(1L, 2L))
    val b = Simd<Long>(listOf(4L, 3L))
    val r = Simd<Long>(listOf(3L, 2L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaPckodD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaPcntB() {
    val a = Simd<Byte>(listOf((-128).toByte(), (-64).toByte(), (-32).toByte(), (-16).toByte(), (-8).toByte(), (-4).toByte(), (-2).toByte(), (-1).toByte(), 1.toByte(), 2.toByte(), 4.toByte(), 8.toByte(), 16.toByte(), 32.toByte(), 64.toByte(), 127.toByte()))
    val r = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 7.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaPcntB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaPcntH() {
    val a = Simd<Short>(listOf((-32768).toShort(), (-16384).toShort(), (-8192).toShort(), (-4096).toShort(), 4096.toShort(), 8192.toShort(), 16384.toShort(), 32767.toShort()))
    val r = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 1.toShort(), 1.toShort(), 1.toShort(), 15.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaPcntH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaPcntW() {
    val a = Simd<Int>(listOf(Int.MIN_VALUE, -1073741824, 1073741824, Int.MAX_VALUE))
    val r = Simd<Int>(listOf(1, 2, 1, 31))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaPcntW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaPcntD() {
    val a = Simd<Long>(listOf(-2147483648L, 2147483647L))
    val r = Simd<Long>(listOf(33L, 31L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaPcntD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSatSB() {
    val a = Simd<Byte>(listOf(Byte.MAX_VALUE, 105.toByte(), 30.toByte(), 1.toByte(), Byte.MAX_VALUE, 105.toByte(), 30.toByte(), 1.toByte(), Byte.MAX_VALUE, 105.toByte(), 30.toByte(), 1.toByte(), Byte.MAX_VALUE, 105.toByte(), 30.toByte(), 1.toByte()))
    val r = Simd<Byte>(listOf(3.toByte(), 3.toByte(), 3.toByte(), 1.toByte(), 3.toByte(), 3.toByte(), 3.toByte(), 1.toByte(), 3.toByte(), 3.toByte(), 3.toByte(), 1.toByte(), 3.toByte(), 3.toByte(), 3.toByte(), 1.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSatSB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), 2), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSatSH() {
    val a = Simd<Short>(listOf(Short.MAX_VALUE, 1155.toShort(), 155.toShort(), 1.toShort(), Short.MAX_VALUE, 1155.toShort(), 155.toShort(), 1.toShort()))
    val r = Simd<Short>(listOf(127.toShort(), 127.toShort(), 127.toShort(), 1.toShort(), 127.toShort(), 127.toShort(), 127.toShort(), 1.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSatSH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), 7), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSatSW() {
    val a = Simd<Int>(listOf(Int.MAX_VALUE, 111111155, Int.MAX_VALUE, 1))
    val r = Simd<Int>(listOf(131071, 131071, 131071, 1))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSatSW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), 17), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSatSD() {
    val a = Simd<Long>(listOf(Long.MAX_VALUE, 1L))
    val r = Simd<Long>(listOf(137438953471L, 1L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSatSD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), 37), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSatUB() {
    val a = Simd<UByte>(listOf(UByte.MAX_VALUE, 105u.toUByte(), 30u.toUByte(), 1u.toUByte(), UByte.MAX_VALUE, 105u.toUByte(), 30u.toUByte(), 1u.toUByte(), UByte.MAX_VALUE, 105u.toUByte(), 30u.toUByte(), 1u.toUByte(), UByte.MAX_VALUE, 105u.toUByte(), 30u.toUByte(), 1u.toUByte()))
    val r = Simd<UByte>(listOf(7u.toUByte(), 7u.toUByte(), 7u.toUByte(), 1u.toUByte(), 7u.toUByte(), 7u.toUByte(), 7u.toUByte(), 1u.toUByte(), 7u.toUByte(), 7u.toUByte(), 7u.toUByte(), 1u.toUByte(), 7u.toUByte(), 7u.toUByte(), 7u.toUByte(), 1u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaSatUB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), 2), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSatUH() {
    val a = Simd<UShort>(listOf(UShort.MAX_VALUE, 1155u.toUShort(), 155u.toUShort(), 1u.toUShort(), UShort.MAX_VALUE, 1155u.toUShort(), 155u.toUShort(), 1u.toUShort()))
    val r = Simd<UShort>(listOf(255u.toUShort(), 255u.toUShort(), 155u.toUShort(), 1u.toUShort(), 255u.toUShort(), 255u.toUShort(), 155u.toUShort(), 1u.toUShort()))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaSatUH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), 7), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSatUW() {
    val a = Simd<UInt>(listOf(UInt.MAX_VALUE, 111111155u, UInt.MAX_VALUE, 1u))
    val r = Simd<UInt>(listOf(262143u, 262143u, 262143u, 1u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaSatUW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), 17), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSatUD() {
    val a = Simd<ULong>(listOf(ULong.MAX_VALUE, 1uL))
    val r = Simd<ULong>(listOf(274877906943uL, 1uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaSatUD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), 37), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaShfB() {
    val a = Simd<Byte>(listOf(11.toByte(), 12.toByte(), 3.toByte(), 4.toByte(), 11.toByte(), 12.toByte(), 3.toByte(), 4.toByte(), 11.toByte(), 12.toByte(), 3.toByte(), 4.toByte(), 11.toByte(), 12.toByte(), 3.toByte(), 4.toByte()))
    val r = Simd<Byte>(listOf(11.toByte(), 3.toByte(), 4.toByte(), 12.toByte(), 11.toByte(), 3.toByte(), 4.toByte(), 12.toByte(), 11.toByte(), 3.toByte(), 4.toByte(), 12.toByte(), 11.toByte(), 3.toByte(), 4.toByte(), 12.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaShfB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), 120), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaShfH() {
    val a = Simd<Short>(listOf(11.toShort(), 12.toShort(), 13.toShort(), 14.toShort(), 11.toShort(), 12.toShort(), 13.toShort(), 14.toShort()))
    val r = Simd<Short>(listOf(11.toShort(), 14.toShort(), 12.toShort(), 13.toShort(), 11.toShort(), 14.toShort(), 12.toShort(), 13.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaShfH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), 156), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaShfW() {
    val a = Simd<Int>(listOf(1, 2, 3, 4))
    val r = Simd<Int>(listOf(1, 3, 2, 4))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaShfW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), 216), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSldB() {
    val a = Simd<Byte>(listOf(0.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte(), 10.toByte(), 11.toByte(), 12.toByte(), 13.toByte(), 14.toByte(), 15.toByte()))
    val b = Simd<Byte>(listOf(16.toByte(), 17.toByte(), 18.toByte(), 19.toByte(), 20.toByte(), 21.toByte(), 22.toByte(), 23.toByte(), 24.toByte(), 25.toByte(), 26.toByte(), 27.toByte(), 28.toByte(), 29.toByte(), 30.toByte(), 31.toByte()))
    val r = Simd<Byte>(listOf(21.toByte(), 22.toByte(), 23.toByte(), 24.toByte(), 25.toByte(), 26.toByte(), 27.toByte(), 28.toByte(), 29.toByte(), 30.toByte(), 31.toByte(), 0.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSldB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), 5), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSldH() {
    val a = Simd<Short>(listOf(0.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 7.toShort()))
    val b = Simd<Short>(listOf(8.toShort(), 9.toShort(), 10.toShort(), 11.toShort(), 12.toShort(), 13.toShort(), 14.toShort(), 15.toShort()))
    // let c = 5 as i32;
    val r = Simd<Short>(listOf(9.toShort(), 10.toShort(), 11.toShort(), 0.toShort(), 13.toShort(), 14.toShort(), 15.toShort(), 4.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSldH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), 2), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSldW() {
    val a = Simd<Int>(listOf(0, 1, 2, 3))
    val b = Simd<Int>(listOf(4, 5, 6, 7))
    val r = Simd<Int>(listOf(4, 5, 6, 7))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSldW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), 4), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSldD() {
    val a = Simd<Long>(listOf(0L, 1L))
    val b = Simd<Long>(listOf(2L, 3L))
    val r = Simd<Long>(listOf(2L, 3L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSldD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), 2), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSldiB() {
    val a = Simd<Byte>(listOf(0.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte(), 10.toByte(), 11.toByte(), 12.toByte(), 13.toByte(), 14.toByte(), 15.toByte()))
    val b = Simd<Byte>(listOf(16.toByte(), 17.toByte(), 18.toByte(), 19.toByte(), 20.toByte(), 21.toByte(), 22.toByte(), 23.toByte(), 24.toByte(), 25.toByte(), 26.toByte(), 27.toByte(), 28.toByte(), 29.toByte(), 30.toByte(), 31.toByte()))
    val r = Simd<Byte>(listOf(21.toByte(), 22.toByte(), 23.toByte(), 24.toByte(), 25.toByte(), 26.toByte(), 27.toByte(), 28.toByte(), 29.toByte(), 30.toByte(), 31.toByte(), 0.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSldiB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), 5), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSldiH() {
    val a = Simd<Short>(listOf(0.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 7.toShort()))
    val b = Simd<Short>(listOf(8.toShort(), 9.toShort(), 10.toShort(), 11.toShort(), 12.toShort(), 13.toShort(), 14.toShort(), 15.toShort()))
    // let c = 5 as i32;
    val r = Simd<Short>(listOf(9.toShort(), 10.toShort(), 11.toShort(), 0.toShort(), 13.toShort(), 14.toShort(), 15.toShort(), 4.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSldiH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), 2), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSldiW() {
    val a = Simd<Int>(listOf(0, 1, 2, 3))
    val b = Simd<Int>(listOf(4, 5, 6, 7))
    val r = Simd<Int>(listOf(4, 5, 6, 7))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSldiW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), 0), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSldiD() {
    val a = Simd<Long>(listOf(0L, 1L))
    val b = Simd<Long>(listOf(2L, 3L))
    val r = Simd<Long>(listOf(2L, 3L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSldiD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), 0), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSllB() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte()))
    val b = Simd<Byte>(listOf(4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte()))
    val r = Simd<Byte>(listOf(16.toByte(), 16.toByte(), 12.toByte(), 8.toByte(), 16.toByte(), 16.toByte(), 12.toByte(), 8.toByte(), 16.toByte(), 16.toByte(), 12.toByte(), 8.toByte(), 16.toByte(), 16.toByte(), 12.toByte(), 8.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSllB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSllH() {
    val a = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort()))
    val b = Simd<Short>(listOf(4.toShort(), 3.toShort(), 2.toShort(), 1.toShort(), 4.toShort(), 3.toShort(), 2.toShort(), 1.toShort()))
    val r = Simd<Short>(listOf(16.toShort(), 16.toShort(), 12.toShort(), 8.toShort(), 16.toShort(), 16.toShort(), 12.toShort(), 8.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSllH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSllW() {
    val a = Simd<Int>(listOf(1, 2, 3, 4))
    val b = Simd<Int>(listOf(4, 3, 2, 1))
    val r = Simd<Int>(listOf(16, 16, 12, 8))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSllW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSllD() {
    val a = Simd<Long>(listOf(1L, 2L))
    val b = Simd<Long>(listOf(4L, 3L))
    val r = Simd<Long>(listOf(16L, 16L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSllD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSlliB() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte()))
    val r = Simd<Byte>(listOf(4.toByte(), 8.toByte(), 12.toByte(), 16.toByte(), 4.toByte(), 8.toByte(), 12.toByte(), 16.toByte(), 4.toByte(), 8.toByte(), 12.toByte(), 16.toByte(), 4.toByte(), 8.toByte(), 12.toByte(), 16.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSlliB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), 2), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSlliH() {
    val a = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort()))
    val r = Simd<Short>(listOf(4.toShort(), 8.toShort(), 12.toShort(), 16.toShort(), 4.toShort(), 8.toShort(), 12.toShort(), 16.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSlliH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), 2), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSlliW() {
    val a = Simd<Int>(listOf(1, 2, 3, 4))
    val r = Simd<Int>(listOf(4, 8, 12, 16))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSlliW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), 2), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSlliD() {
    val a = Simd<Long>(listOf(1L, 2L))
    val r = Simd<Long>(listOf(2L, 4L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSlliD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), 1), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSplatB() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte()))
    val r = Simd<Byte>(listOf(4.toByte(), 4.toByte(), 4.toByte(), 4.toByte(), 4.toByte(), 4.toByte(), 4.toByte(), 4.toByte(), 4.toByte(), 4.toByte(), 4.toByte(), 4.toByte(), 4.toByte(), 4.toByte(), 4.toByte(), 4.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSplatB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), 3), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSplatH() {
    val a = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort()))
    val r = Simd<Short>(listOf(4.toShort(), 4.toShort(), 4.toShort(), 4.toShort(), 4.toShort(), 4.toShort(), 4.toShort(), 4.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSplatH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), 3), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSplatW() {
    val a = Simd<Int>(listOf(1, 2, 3, 4))
    val r = Simd<Int>(listOf(4, 4, 4, 4))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSplatW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), 3), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSplatD() {
    val a = Simd<Long>(listOf(1L, 2L))
    val r = Simd<Long>(listOf(2L, 2L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSplatD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), 3), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSplatiB() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte()))
    val r = Simd<Byte>(listOf(3.toByte(), 3.toByte(), 3.toByte(), 3.toByte(), 3.toByte(), 3.toByte(), 3.toByte(), 3.toByte(), 3.toByte(), 3.toByte(), 3.toByte(), 3.toByte(), 3.toByte(), 3.toByte(), 3.toByte(), 3.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSplatiB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), 2), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSplatiH() {
    val a = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort()))
    val r = Simd<Short>(listOf(3.toShort(), 3.toShort(), 3.toShort(), 3.toShort(), 3.toShort(), 3.toShort(), 3.toShort(), 3.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSplatiH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), 2), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSplatiW() {
    val a = Simd<Int>(listOf(1, 2, 3, 4))
    val r = Simd<Int>(listOf(3, 3, 3, 3))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSplatiW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), 2), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSplatiD() {
    val a = Simd<Long>(listOf(1L, 2L))
    val r = Simd<Long>(listOf(2L, 2L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSplatiD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), 1), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSraB() {
    val a = Simd<Byte>(listOf((-128).toByte(), (-64).toByte(), (-32).toByte(), (-16).toByte(), (-8).toByte(), (-4).toByte(), (-2).toByte(), (-1).toByte(), 1.toByte(), 2.toByte(), 4.toByte(), 8.toByte(), 16.toByte(), 32.toByte(), 64.toByte(), 127.toByte()))
    val b = Simd<Byte>(listOf(8.toByte(), 7.toByte(), 6.toByte(), 5.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 8.toByte(), 7.toByte(), 6.toByte(), 5.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte()))
    val r = Simd<Byte>(listOf((-128).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), (-1).toByte(), 1.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 1.toByte(), 4.toByte(), 16.toByte(), 63.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSraB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSraH() {
    val a = Simd<Short>(listOf((-32768).toShort(), (-16384).toShort(), (-8192).toShort(), (-4096).toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort()))
    val b = Simd<Short>(listOf(15.toShort(), 14.toShort(), 13.toShort(), 12.toShort(), 12.toShort(), 13.toShort(), 14.toShort(), 15.toShort()))
    val r = Simd<Short>(listOf((-1).toShort(), (-1).toShort(), (-1).toShort(), (-1).toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSraH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSraW() {
    val a = Simd<Int>(listOf(Int.MIN_VALUE, -1073741824, 1, 2))
    val b = Simd<Int>(listOf(16, 15, 16, 15))
    val r = Simd<Int>(listOf(-32768, -32768, 0, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSraW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSraD() {
    val a = Simd<Long>(listOf(Long.MIN_VALUE, Long.MAX_VALUE))
    val b = Simd<Long>(listOf(32L, 31L))
    val r = Simd<Long>(listOf(-2147483648L, 4294967295L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSraD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSraiB() {
    val a = Simd<Byte>(listOf(Byte.MAX_VALUE, 125.toByte(), 55.toByte(), 1.toByte(), Byte.MAX_VALUE, 125.toByte(), 55.toByte(), 1.toByte(), Byte.MAX_VALUE, 125.toByte(), 55.toByte(), 1.toByte(), Byte.MAX_VALUE, 125.toByte(), 55.toByte(), 1.toByte()))
    val r = Simd<Byte>(listOf(31.toByte(), 31.toByte(), 13.toByte(), 0.toByte(), 31.toByte(), 31.toByte(), 13.toByte(), 0.toByte(), 31.toByte(), 31.toByte(), 13.toByte(), 0.toByte(), 31.toByte(), 31.toByte(), 13.toByte(), 0.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSraiB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), 2), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSraiH() {
    val a = Simd<Short>(listOf(Short.MAX_VALUE, 125.toShort(), 55.toShort(), 1.toShort(), Short.MAX_VALUE, 125.toShort(), 55.toShort(), 1.toShort()))
    val r = Simd<Short>(listOf(8191.toShort(), 31.toShort(), 13.toShort(), 0.toShort(), 8191.toShort(), 31.toShort(), 13.toShort(), 0.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSraiH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), 2), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSraiW() {
    val a = Simd<Int>(listOf(Int.MAX_VALUE, 125, 55, 1))
    val r = Simd<Int>(listOf(536870911, 31, 13, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSraiW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), 2), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSraiD() {
    val a = Simd<Long>(listOf(Long.MAX_VALUE, 55L))
    val r = Simd<Long>(listOf(2305843009213693951L, 13L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSraiD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), 2), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrarB() {
    val a = Simd<Byte>(listOf((-128).toByte(), (-64).toByte(), (-32).toByte(), (-16).toByte(), (-8).toByte(), (-4).toByte(), (-2).toByte(), (-1).toByte(), 1.toByte(), 2.toByte(), 4.toByte(), 8.toByte(), 16.toByte(), 32.toByte(), 64.toByte(), 127.toByte()))
    val b = Simd<Byte>(listOf(4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 8.toByte(), 7.toByte(), 6.toByte(), 5.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte()))
    val r = Simd<Byte>(listOf((-8).toByte(), (-8).toByte(), (-8).toByte(), (-8).toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 1.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 1.toByte(), 4.toByte(), 16.toByte(), 64.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSrarB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrarH() {
    val a = Simd<Short>(listOf(Short.MIN_VALUE, (-16384).toShort(), (-8192).toShort(), (-4096).toShort(), 150.toShort(), 50.toShort(), 25.toShort(), 15.toShort()))
    val b = Simd<Short>(listOf(4.toShort(), 3.toShort(), 2.toShort(), 1.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort()))
    val r = Simd<Short>(listOf((-2048).toShort(), (-2048).toShort(), (-2048).toShort(), (-2048).toShort(), 75.toShort(), 13.toShort(), 3.toShort(), 1.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSrarH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrarW() {
    val a = Simd<Int>(listOf(Int.MIN_VALUE, -1073741824, 100, 50))
    val b = Simd<Int>(listOf(16, 15, 1, 2))
    val r = Simd<Int>(listOf(-32768, -32768, 50, 13))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSrarW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrarD() {
    val a = Simd<Long>(listOf(Long.MIN_VALUE, Long.MAX_VALUE))
    val b = Simd<Long>(listOf(32L, 31L))
    val r = Simd<Long>(listOf(-2147483648L, 4294967296L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSrarD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrariB() {
    val a = Simd<Byte>(listOf(125.toByte(), Byte.MAX_VALUE, 55.toByte(), 1.toByte(), 125.toByte(), Byte.MAX_VALUE, 55.toByte(), 1.toByte(), 125.toByte(), Byte.MAX_VALUE, 55.toByte(), 1.toByte(), 125.toByte(), Byte.MAX_VALUE, 55.toByte(), 1.toByte()))
    val r = Simd<Byte>(listOf(31.toByte(), 32.toByte(), 14.toByte(), 0.toByte(), 31.toByte(), 32.toByte(), 14.toByte(), 0.toByte(), 31.toByte(), 32.toByte(), 14.toByte(), 0.toByte(), 31.toByte(), 32.toByte(), 14.toByte(), 0.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSrariB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), 2), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrariH() {
    val a = Simd<Short>(listOf(2155.toShort(), 1155.toShort(), 155.toShort(), 1.toShort(), 2155.toShort(), 1155.toShort(), 155.toShort(), 1.toShort()))
    val r = Simd<Short>(listOf(539.toShort(), 289.toShort(), 39.toShort(), 0.toShort(), 539.toShort(), 289.toShort(), 39.toShort(), 0.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSrariH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), 2), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrariW() {
    val a = Simd<Int>(listOf(211111155, 111111155, 11111155, 1))
    val r = Simd<Int>(listOf(52777789, 27777789, 2777789, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSrariW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), 2), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrariD() {
    val a = Simd<Long>(listOf(211111111155L, 111111111155L))
    val r = Simd<Long>(listOf(52777777789L, 27777777789L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSrariD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), 2), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrlB() {
    val a = Simd<Byte>(listOf((-128).toByte(), (-64).toByte(), (-32).toByte(), (-16).toByte(), (-8).toByte(), (-4).toByte(), (-2).toByte(), (-1).toByte(), 1.toByte(), 2.toByte(), 4.toByte(), 8.toByte(), 16.toByte(), 32.toByte(), 64.toByte(), 127.toByte()))
    val b = Simd<Byte>(listOf(8.toByte(), 7.toByte(), 6.toByte(), 5.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 8.toByte(), 7.toByte(), 6.toByte(), 5.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte()))
    val r = Simd<Byte>(listOf((-128).toByte(), 1.toByte(), 3.toByte(), 7.toByte(), 15.toByte(), 31.toByte(), 63.toByte(), 127.toByte(), 1.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 1.toByte(), 4.toByte(), 16.toByte(), 63.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSrlB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrlH() {
    val a = Simd<Short>(listOf((-32768).toShort(), (-16384).toShort(), (-8192).toShort(), (-4096).toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort()))
    val b = Simd<Short>(listOf(15.toShort(), 14.toShort(), 13.toShort(), 12.toShort(), 4.toShort(), 3.toShort(), 2.toShort(), 1.toShort()))
    val r = Simd<Short>(listOf(1.toShort(), 3.toShort(), 7.toShort(), 15.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 2.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSrlH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrlW() {
    val a = Simd<Int>(listOf(Int.MIN_VALUE, -1073741824, 1, 2))
    val b = Simd<Int>(listOf(16, 15, 16, 15))
    val r = Simd<Int>(listOf(32768, 98304, 0, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSrlW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrlD() {
    val a = Simd<Long>(listOf(Long.MIN_VALUE, Long.MAX_VALUE))
    val b = Simd<Long>(listOf(32L, 31L))
    val r = Simd<Long>(listOf(2147483648L, 4294967295L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSrlD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrliB() {
    val a = Simd<Byte>(listOf(25.toByte(), 50.toByte(), 100.toByte(), 127.toByte(), 25.toByte(), 50.toByte(), 100.toByte(), 127.toByte(), 25.toByte(), 50.toByte(), 100.toByte(), 127.toByte(), 25.toByte(), 50.toByte(), 100.toByte(), 127.toByte()))
    val r = Simd<Byte>(listOf(6.toByte(), 12.toByte(), 25.toByte(), 31.toByte(), 6.toByte(), 12.toByte(), 25.toByte(), 31.toByte(), 6.toByte(), 12.toByte(), 25.toByte(), 31.toByte(), 6.toByte(), 12.toByte(), 25.toByte(), 31.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSrliB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), 2), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrliH() {
    val a = Simd<Short>(listOf(Short.MAX_VALUE, 3276.toShort(), 100.toShort(), 127.toShort(), Short.MAX_VALUE, 3276.toShort(), 100.toShort(), 127.toShort()))
    val r = Simd<Short>(listOf(8191.toShort(), 819.toShort(), 25.toShort(), 31.toShort(), 8191.toShort(), 819.toShort(), 25.toShort(), 31.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSrliH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), 2), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrliW() {
    val a = Simd<Int>(listOf(100, Int.MAX_VALUE, 100, Int.MAX_VALUE))
    val r = Simd<Int>(listOf(25, 536870911, 25, 536870911))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSrliW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), 2), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrliD() {
    val a = Simd<Long>(listOf(100L, Long.MAX_VALUE))
    val r = Simd<Long>(listOf(50L, 4611686018427387903L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSrliD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), 1), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrlrB() {
    val a = Simd<Byte>(listOf((-128).toByte(), (-64).toByte(), (-32).toByte(), (-16).toByte(), (-8).toByte(), (-4).toByte(), (-2).toByte(), (-1).toByte(), 1.toByte(), 2.toByte(), 4.toByte(), 8.toByte(), 16.toByte(), 32.toByte(), 64.toByte(), 127.toByte()))
    val b = Simd<Byte>(listOf(8.toByte(), 7.toByte(), 6.toByte(), 5.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 8.toByte(), 7.toByte(), 6.toByte(), 5.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte()))
    val r = Simd<Byte>(listOf((-128).toByte(), 2.toByte(), 4.toByte(), 8.toByte(), 16.toByte(), 32.toByte(), 64.toByte(), (-128).toByte(), 1.toByte(), 0.toByte(), 0.toByte(), 0.toByte(), 1.toByte(), 4.toByte(), 16.toByte(), 64.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSrlrB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrlrH() {
    val a = Simd<Short>(listOf((-32768).toShort(), (-16384).toShort(), (-8192).toShort(), (-4096).toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort()))
    val b = Simd<Short>(listOf(15.toShort(), 14.toShort(), 13.toShort(), 12.toShort(), 4.toShort(), 3.toShort(), 2.toShort(), 1.toShort()))
    val r = Simd<Short>(listOf(1.toShort(), 3.toShort(), 7.toShort(), 15.toShort(), 0.toShort(), 0.toShort(), 1.toShort(), 2.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSrlrH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrlrW() {
    val a = Simd<Int>(listOf(Int.MIN_VALUE, -1073741824, 1, 2))
    val b = Simd<Int>(listOf(16, 15, 16, 15))
    val r = Simd<Int>(listOf(32768, 98304, 0, 0))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSrlrW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrlrD() {
    val a = Simd<Long>(listOf(Long.MIN_VALUE, Long.MAX_VALUE))
    val b = Simd<Long>(listOf(32L, 31L))
    val r = Simd<Long>(listOf(2147483648L, 4294967296L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSrlrD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrlriB() {
    val a = Simd<Byte>(listOf(25.toByte(), 50.toByte(), 100.toByte(), Byte.MAX_VALUE, 25.toByte(), 50.toByte(), 100.toByte(), Byte.MAX_VALUE, 25.toByte(), 50.toByte(), 100.toByte(), Byte.MAX_VALUE, 25.toByte(), 50.toByte(), 100.toByte(), Byte.MAX_VALUE))
    val r = Simd<Byte>(listOf(6.toByte(), 13.toByte(), 25.toByte(), 32.toByte(), 6.toByte(), 13.toByte(), 25.toByte(), 32.toByte(), 6.toByte(), 13.toByte(), 25.toByte(), 32.toByte(), 6.toByte(), 13.toByte(), 25.toByte(), 32.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSrlriB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), 2), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrlriH() {
    val a = Simd<Short>(listOf(Short.MAX_VALUE, 3276.toShort(), 100.toShort(), 127.toShort(), Short.MAX_VALUE, 3276.toShort(), 100.toShort(), 127.toShort()))
    val r = Simd<Short>(listOf(8192.toShort(), 819.toShort(), 25.toShort(), 32.toShort(), 8192.toShort(), 819.toShort(), 25.toShort(), 32.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSrlriH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), 2), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrlriW() {
    val a = Simd<Int>(listOf(100, 150, 200, Int.MAX_VALUE))
    val r = Simd<Int>(listOf(25, 38, 50, 536870912))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSrlriW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), 2), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrlriD() {
    val a = Simd<Long>(listOf(100L, Long.MAX_VALUE))
    val r = Simd<Long>(listOf(50L, 4611686018427387904L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSrlriD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), 1), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaStB() {
    val a = Simd<Byte>(listOf(13.toByte(), 14.toByte(), 15.toByte(), 16.toByte(), 17.toByte(), 18.toByte(), 19.toByte(), 20.toByte(), 21.toByte(), 22.toByte(), 23.toByte(), 24.toByte(), 25.toByte(), 26.toByte(), 27.toByte(), 28.toByte()))
    val localStorageL17742C8 = AddressableArrayLocal.fromByteArray(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))
    var arr by localStorageL17742C8
    val r: ByteArray = byteArrayOf(13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28)
    msaStB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), (localStorageL17742C8.elementMutPtr(0)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0)
    check((arr).contentEquals(r))
}

public fun testMsaStH() {
    val a = Simd<Short>(listOf(13.toShort(), 14.toShort(), 15.toShort(), 16.toShort(), 17.toShort(), 18.toShort(), 19.toShort(), 20.toShort()))
    val localStorageL17764C8 = AddressableArrayLocal.fromShortArray(shortArrayOf(0, 0, 0, 0, 0, 0, 0, 0))
    var arr by localStorageL17764C8
    val r: ShortArray = shortArrayOf(13, 14, 15, 16, 17, 18, 19, 20)
    msaStH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), (localStorageL17764C8.elementMutPtr(0)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0)
    check((arr).contentEquals(r))
}

public fun testMsaStW() {
    val a = Simd<Int>(listOf(13, 14, 15, 16))
    val localStorageL17775C8 = AddressableArrayLocal.fromIntArray(intArrayOf(0, 0, 0, 0))
    var arr by localStorageL17775C8
    val r: IntArray = intArrayOf(13, 14, 15, 16)
    msaStW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), (localStorageL17775C8.elementMutPtr(0)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0)
    check((arr).contentEquals(r))
}

public fun testMsaStD() {
    val a = Simd<Long>(listOf(13L, 14L))
    val localStorageL17786C8 = AddressableArrayLocal.fromLongArray(longArrayOf(0L, 0L))
    var arr by localStorageL17786C8
    val r: LongArray = longArrayOf(13L, 14L)
    msaStD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), (localStorageL17786C8.elementMutPtr(0)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0)
    check((arr).contentEquals(r))
}

public fun testMsaSubsSB() {
    val a = Simd<Byte>(listOf(Byte.MIN_VALUE, (-2).toByte(), (-3).toByte(), (-4).toByte(), Byte.MIN_VALUE, (-2).toByte(), (-3).toByte(), (-4).toByte(), Byte.MIN_VALUE, (-2).toByte(), (-3).toByte(), (-4).toByte(), Byte.MIN_VALUE, (-2).toByte(), (-3).toByte(), (-4).toByte()))
    val b = Simd<Byte>(listOf(6.toByte(), (-7).toByte(), 8.toByte(), (-9).toByte(), 6.toByte(), (-7).toByte(), 8.toByte(), (-9).toByte(), 6.toByte(), (-7).toByte(), 8.toByte(), (-9).toByte(), 6.toByte(), (-7).toByte(), 8.toByte(), (-9).toByte()))
    val r = Simd<Byte>(listOf(Byte.MIN_VALUE, 5.toByte(), (-11).toByte(), 5.toByte(), Byte.MIN_VALUE, 5.toByte(), (-11).toByte(), 5.toByte(), Byte.MIN_VALUE, 5.toByte(), (-11).toByte(), 5.toByte(), Byte.MIN_VALUE, 5.toByte(), (-11).toByte(), 5.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSubsSB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsSH() {
    val a = Simd<Short>(listOf(Short.MIN_VALUE, (-2).toShort(), (-3).toShort(), (-4).toShort(), Short.MIN_VALUE, (-2).toShort(), (-3).toShort(), (-4).toShort()))
    val b = Simd<Short>(listOf(6.toShort(), (-7).toShort(), 8.toShort(), (-9).toShort(), 6.toShort(), (-7).toShort(), 8.toShort(), (-9).toShort()))
    val r = Simd<Short>(listOf(Short.MIN_VALUE, 5.toShort(), (-11).toShort(), 5.toShort(), Short.MIN_VALUE, 5.toShort(), (-11).toShort(), 5.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSubsSH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsSW() {
    val a = Simd<Int>(listOf(Int.MIN_VALUE, -2, -3, -4))
    val b = Simd<Int>(listOf(6, -7, 8, -9))
    val r = Simd<Int>(listOf(Int.MIN_VALUE, 5, -11, 5))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSubsSW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsSD() {
    val a = Simd<Long>(listOf(Long.MIN_VALUE, -2L))
    val b = Simd<Long>(listOf(6L, -7L))
    val r = Simd<Long>(listOf(Long.MIN_VALUE, 5L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSubsSD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsUB() {
    val a = Simd<UByte>(listOf(UByte.MAX_VALUE, 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), UByte.MAX_VALUE, 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), UByte.MAX_VALUE, 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), UByte.MAX_VALUE, 2u.toUByte(), 3u.toUByte(), 4u.toUByte()))
    val b = Simd<UByte>(listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte()))
    val r = Simd<UByte>(listOf(249u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 249u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 249u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 249u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaSubsUB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsUH() {
    val a = Simd<UShort>(listOf(UShort.MAX_VALUE, 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), UShort.MAX_VALUE, 2u.toUShort(), 3u.toUShort(), 4u.toUShort()))
    val b = Simd<UShort>(listOf(6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort(), 6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort()))
    val r = Simd<UShort>(listOf(65529u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 65529u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort()))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaSubsUH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsUW() {
    val a = Simd<UInt>(listOf(UInt.MAX_VALUE, 2u, 3u, 4u))
    val b = Simd<UInt>(listOf(6u, 7u, 8u, 9u))
    val r = Simd<UInt>(listOf(4294967289u, 0u, 0u, 0u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaSubsUW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsUD() {
    val a = Simd<ULong>(listOf(ULong.MAX_VALUE, 2uL))
    val b = Simd<ULong>(listOf(6uL, 7uL))
    val r = Simd<ULong>(listOf(18446744073709551609uL, 0uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaSubsUD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<Any?, V2u64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsusUB() {
    val a = Simd<UByte>(listOf(UByte.MAX_VALUE, 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), UByte.MAX_VALUE, 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), UByte.MAX_VALUE, 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), UByte.MAX_VALUE, 2u.toUByte(), 3u.toUByte(), 4u.toUByte()))
    val b = Simd<Byte>(listOf((-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte()))
    val r = Simd<UByte>(listOf(255u.toUByte(), 9u.toUByte(), 11u.toUByte(), 13u.toUByte(), 255u.toUByte(), 9u.toUByte(), 11u.toUByte(), 13u.toUByte(), 255u.toUByte(), 9u.toUByte(), 11u.toUByte(), 13u.toUByte(), 255u.toUByte(), 9u.toUByte(), 11u.toUByte(), 13u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaSubsusUB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsusUH() {
    val a = Simd<UShort>(listOf(UShort.MAX_VALUE, 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), UShort.MAX_VALUE, 2u.toUShort(), 3u.toUShort(), 4u.toUShort()))
    val b = Simd<Short>(listOf((-6).toShort(), (-7).toShort(), (-8).toShort(), (-9).toShort(), (-6).toShort(), (-7).toShort(), (-8).toShort(), (-9).toShort()))
    val r = Simd<UShort>(listOf(65535u.toUShort(), 9u.toUShort(), 11u.toUShort(), 13u.toUShort(), 65535u.toUShort(), 9u.toUShort(), 11u.toUShort(), 13u.toUShort()))
    check(r == transmuteWithLayouts<V8u16, Unit>(msaSubsusUH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsusUW() {
    val a = Simd<UInt>(listOf(UInt.MAX_VALUE, 2u, 3u, 4u))
    val b = Simd<Int>(listOf(-6, -7, -8, -9))
    val r = Simd<UInt>(listOf(4294967295u, 9u, 11u, 13u))
    check(r == transmuteWithLayouts<V4u32, Unit>(msaSubsusUW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsusUD() {
    val a = Simd<ULong>(listOf(ULong.MAX_VALUE, 2uL))
    val b = Simd<Long>(listOf(-6L, -7L))
    val r = Simd<ULong>(listOf(18446744073709551615uL, 9uL))
    check(r == transmuteWithLayouts<V2u64, Unit>(msaSubsusUD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsuuSB() {
    val a = Simd<UByte>(listOf(UByte.MAX_VALUE, 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), UByte.MAX_VALUE, 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), UByte.MAX_VALUE, 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), UByte.MAX_VALUE, 2u.toUByte(), 3u.toUByte(), 4u.toUByte()))
    val b = Simd<UByte>(listOf(6u.toUByte(), 7u.toUByte(), 8u.toUByte(), UByte.MAX_VALUE, 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), UByte.MAX_VALUE, 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), UByte.MAX_VALUE, 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), UByte.MAX_VALUE))
    val r = Simd<Byte>(listOf(127.toByte(), (-5).toByte(), (-5).toByte(), (-128).toByte(), 127.toByte(), (-5).toByte(), (-5).toByte(), (-128).toByte(), 127.toByte(), (-5).toByte(), (-5).toByte(), (-128).toByte(), 127.toByte(), (-5).toByte(), (-5).toByte(), (-128).toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSubsuuSB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsuuSH() {
    val a = Simd<UShort>(listOf(UShort.MAX_VALUE, 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), UShort.MAX_VALUE, 2u.toUShort(), 3u.toUShort(), 4u.toUShort()))
    val b = Simd<UShort>(listOf(6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 65535u.toUShort(), 6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 65535u.toUShort()))
    val r = Simd<Short>(listOf(32767.toShort(), (-5).toShort(), (-5).toShort(), (-32768).toShort(), 32767.toShort(), (-5).toShort(), (-5).toShort(), (-32768).toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSubsuuSH(transmuteWithLayouts<Any?, V8u16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>()), transmuteWithLayouts<Any?, V8u16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8u16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsuuSW() {
    val a = Simd<UInt>(listOf(UInt.MAX_VALUE, 2u, 3u, 4u))
    val b = Simd<UInt>(listOf(6u, 7u, 8u, 4294967295u))
    val r = Simd<Int>(listOf(2147483647, -5, -5, -2147483648.toInt()))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSubsuuSW(transmuteWithLayouts<Any?, V4u32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>()), transmuteWithLayouts<Any?, V4u32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4u32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsuuSD() {
    val a = Simd<ULong>(listOf(ULong.MAX_VALUE, 2uL))
    val b = Simd<ULong>(listOf(6uL, 7uL))
    val r = Simd<Long>(listOf(Long.MAX_VALUE, -5L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSubsuuSD(transmuteWithLayouts<Any?, V2u64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>()), transmuteWithLayouts<Any?, V2u64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2u64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubvB() {
    val a = Simd<Byte>(listOf(Byte.MIN_VALUE, (-2).toByte(), (-3).toByte(), (-4).toByte(), Byte.MIN_VALUE, (-2).toByte(), (-3).toByte(), (-4).toByte(), Byte.MIN_VALUE, (-2).toByte(), (-3).toByte(), (-4).toByte(), Byte.MIN_VALUE, (-2).toByte(), (-3).toByte(), (-4).toByte()))
    val b = Simd<Byte>(listOf(6.toByte(), (-7).toByte(), 8.toByte(), (-9).toByte(), 6.toByte(), (-7).toByte(), 8.toByte(), (-9).toByte(), 6.toByte(), (-7).toByte(), 8.toByte(), (-9).toByte(), 6.toByte(), (-7).toByte(), 8.toByte(), (-9).toByte()))
    val r = Simd<Byte>(listOf(122.toByte(), 5.toByte(), (-11).toByte(), 5.toByte(), 122.toByte(), 5.toByte(), (-11).toByte(), 5.toByte(), 122.toByte(), 5.toByte(), (-11).toByte(), 5.toByte(), 122.toByte(), 5.toByte(), (-11).toByte(), 5.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSubvB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubvH() {
    val a = Simd<Short>(listOf(Short.MIN_VALUE, (-2).toShort(), (-3).toShort(), (-4).toShort(), Short.MIN_VALUE, (-2).toShort(), (-3).toShort(), (-4).toShort()))
    val b = Simd<Short>(listOf(6.toShort(), (-7).toShort(), 8.toShort(), (-9).toShort(), 6.toShort(), (-7).toShort(), 8.toShort(), (-9).toShort()))
    val r = Simd<Short>(listOf(32762.toShort(), 5.toShort(), (-11).toShort(), 5.toShort(), 32762.toShort(), 5.toShort(), (-11).toShort(), 5.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSubvH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubvW() {
    val a = Simd<Int>(listOf(Int.MIN_VALUE, -2, -3, -4))
    val b = Simd<Int>(listOf(6, -7, 8, -9))
    val r = Simd<Int>(listOf(2147483642, 5, -11, 5))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSubvW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubvD() {
    val a = Simd<Long>(listOf(Long.MAX_VALUE, -2L))
    val b = Simd<Long>(listOf(6L, -7L))
    val r = Simd<Long>(listOf(9223372036854775801L, 5L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSubvD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubviB() {
    val a = Simd<Byte>(listOf(100.toByte(), Byte.MAX_VALUE, 50.toByte(), Byte.MIN_VALUE, 100.toByte(), Byte.MAX_VALUE, 50.toByte(), Byte.MIN_VALUE, 100.toByte(), Byte.MAX_VALUE, 50.toByte(), Byte.MIN_VALUE, 100.toByte(), Byte.MAX_VALUE, 50.toByte(), Byte.MIN_VALUE))
    val r = Simd<Byte>(listOf(95.toByte(), 122.toByte(), 45.toByte(), 123.toByte(), 95.toByte(), 122.toByte(), 45.toByte(), 123.toByte(), 95.toByte(), 122.toByte(), 45.toByte(), 123.toByte(), 95.toByte(), 122.toByte(), 45.toByte(), 123.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSubviB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), 5), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubviH() {
    val a = Simd<Short>(listOf(Short.MAX_VALUE, 3276.toShort(), (-100).toShort(), Short.MIN_VALUE, Short.MAX_VALUE, 3276.toShort(), (-100).toShort(), Short.MIN_VALUE))
    val r = Simd<Short>(listOf(32762.toShort(), 3271.toShort(), (-105).toShort(), 32763.toShort(), 32762.toShort(), 3271.toShort(), (-105).toShort(), 32763.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSubviH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), 5), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubviW() {
    val a = Simd<Int>(listOf(100, 150, 200, Int.MAX_VALUE))
    val r = Simd<Int>(listOf(95, 145, 195, 2147483642))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSubviW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), 5), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubviD() {
    val a = Simd<Long>(listOf(100L, Long.MAX_VALUE))
    val r = Simd<Long>(listOf(95L, 9223372036854775802L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSubviD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), 5), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaVshfB() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte()))
    val b = Simd<Byte>(listOf(4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte()))
    val c = Simd<Byte>(listOf(4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte()))
    val r = Simd<Byte>(listOf(3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 4.toByte()))
    check(r == transmuteWithLayouts<V16i8, Unit>(msaVshfB(transmuteWithLayouts<Any?, V16i8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>()), transmuteWithLayouts<Any?, V16i8>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16i8>())), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaVshfH() {
    val a = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort()))
    val b = Simd<Short>(listOf(4.toShort(), 3.toShort(), 2.toShort(), 1.toShort(), 4.toShort(), 3.toShort(), 2.toShort(), 1.toShort()))
    val c = Simd<Short>(listOf(4.toShort(), 3.toShort(), 2.toShort(), 1.toShort(), 4.toShort(), 3.toShort(), 2.toShort(), 1.toShort()))
    val r = Simd<Short>(listOf(3.toShort(), 2.toShort(), 1.toShort(), 4.toShort(), 3.toShort(), 2.toShort(), 1.toShort(), 4.toShort()))
    check(r == transmuteWithLayouts<V8i16, Unit>(msaVshfH(transmuteWithLayouts<Any?, V8i16>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>()), transmuteWithLayouts<Any?, V8i16>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V8i16>())), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaVshfW() {
    val a = Simd<Int>(listOf(1, 2, 3, 4))
    val b = Simd<Int>(listOf(4, 3, 2, 1))
    val c = Simd<Int>(listOf(4, 3, 2, 1))
    val r = Simd<Int>(listOf(3, 2, 1, 4))
    check(r == transmuteWithLayouts<V4i32, Unit>(msaVshfW(transmuteWithLayouts<Any?, V4i32>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>()), transmuteWithLayouts<Any?, V4i32>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V4i32>())), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaVshfD() {
    val a = Simd<Long>(listOf(1L, 2L))
    val b = Simd<Long>(listOf(4L, 3L))
    val c = Simd<Long>(listOf(4L, 3L))
    val r = Simd<Long>(listOf(3L, 4L))
    check(r == transmuteWithLayouts<V2i64, Unit>(msaVshfD(transmuteWithLayouts<Any?, V2i64>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>()), transmuteWithLayouts<Any?, V2i64>(c, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V2i64>())), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaXorV() {
    val a = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte(), 16u.toUByte()))
    val b = Simd<UByte>(listOf(16u.toUByte(), 15u.toUByte(), 14u.toUByte(), 13u.toUByte(), 12u.toUByte(), 11u.toUByte(), 10u.toUByte(), 9u.toUByte(), 8u.toUByte(), 7u.toUByte(), 6u.toUByte(), 5u.toUByte(), 4u.toUByte(), 3u.toUByte(), 2u.toUByte(), 1u.toUByte()))
    val r = Simd<UByte>(listOf(17u.toUByte(), 13u.toUByte(), 13u.toUByte(), 9u.toUByte(), 9u.toUByte(), 13u.toUByte(), 13u.toUByte(), 1u.toUByte(), 1u.toUByte(), 13u.toUByte(), 13u.toUByte(), 9u.toUByte(), 9u.toUByte(), 13u.toUByte(), 13u.toUByte(), 17u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaXorV(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), transmuteWithLayouts<Any?, V16u8>(b, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>())), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaXoriB() {
    val a = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte(), 16u.toUByte()))
    val r = Simd<UByte>(listOf(5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 20u.toUByte()))
    check(r == transmuteWithLayouts<V16u8, Unit>(msaXoriB(transmuteWithLayouts<Any?, V16u8>(a, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V16u8>()), 4), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}
