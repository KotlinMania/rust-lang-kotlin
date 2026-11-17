// port-lint: tests mips/msa.rs
package io.github.kotlinmania.rustlang.corearch.mips.msa

import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// MIPS SIMD Architecture intrinsics
//
// The reference is [MIPS Architecture for Programmers Volume IV-j: The
// MIPS32 SIMD Architecture Module Revision 1.12][msaRef].
//
// [msaRef]: http://cdn2.imgtec.com/documentation/MD00866-2B-MSA32-AFP-01.12.pdf

public fun testMsaAddAB() {
    val a = i8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = i8x16.new(-4, -3, -2, -1, -4, -3, -2, -1, -4, -3, -2, -1, -4, -3, -2, -1)
    val r = i8x16.new(5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaAddAB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddAH() {
    val a = i16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = i16x8.new(-4, -3, -2, -1, -4, -3, -2, -1)
    val r = i16x8.new(5, 5, 5, 5, 5, 5, 5, 5)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaAddAH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddAW() {
    val a = i32x4.new(1, 2, 3, 4)
    val b = i32x4.new(-4, -3, -2, -1)
    val r = i32x4.new(5, 5, 5, 5)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaAddAW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddAD() {
    val a = i64x2.new(1, 2)
    val b = i64x2.new(-4, -3)
    val r = i64x2.new(5, 5)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaAddAD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddsAB() {
    val a = i8x16.new(100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE)
    val b = i8x16.new(-4, -3, -2, -100, -4, -3, -2, -100, -4, -3, -2, -100, -4, -3, -2, -100)
    val r = i8x16.new(104, 127, 102, 127, 104, 127, 102, 127, 104, 127, 102, 127, 104, 127, 102, 127)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaAddsAB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddsAH() {
    val a = i16x8.new(100, Short.MAX_VALUE, 100, Short.MAX_VALUE, 100, Short.MAX_VALUE, 100, Short.MAX_VALUE)
    val b = i16x8.new(-4, -3, -2, -1, -4, -3, -2, -1)
    val r = i16x8.new(104, Short.MAX_VALUE, 102, Short.MAX_VALUE, 104, Short.MAX_VALUE, 102, Short.MAX_VALUE)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaAddsAH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddsAW() {
    val a = i32x4.new(100, Int.MAX_VALUE, 100, Int.MAX_VALUE)
    val b = i32x4.new(-4, -3, -2, -1)
    val r = i32x4.new(104, Int.MAX_VALUE, 102, Int.MAX_VALUE)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaAddsAW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddsAD() {
    val a = i64x2.new(100, Long.MAX_VALUE)
    val b = i64x2.new(-4, -3)
    val r = i64x2.new(104, Long.MAX_VALUE)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaAddsAD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddsSB() {
    val a = i8x16.new(100, Byte.MIN_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MIN_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MIN_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MIN_VALUE, 100, Byte.MAX_VALUE)
    val b = i8x16.new(-4, -3, -2, 100, -4, -3, -2, 100, -4, -3, -2, 100, -4, -3, -2, 100)
    val r = i8x16.new(96, Byte.MIN_VALUE, 98, Byte.MAX_VALUE, 96, Byte.MIN_VALUE, 98, Byte.MAX_VALUE, 96, Byte.MIN_VALUE, 98, Byte.MAX_VALUE, 96, Byte.MIN_VALUE, 98, Byte.MAX_VALUE)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaAddsSB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddsSH() {
    val a = i16x8.new(100, Short.MIN_VALUE, 100, Short.MAX_VALUE, 100, Short.MIN_VALUE, 100, Short.MAX_VALUE)
    val b = i16x8.new(-4, -3, -2, 1, -4, -3, -2, 1)
    val r = i16x8.new(96, Short.MIN_VALUE, 98, Short.MAX_VALUE, 96, Short.MIN_VALUE, 98, Short.MAX_VALUE)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaAddsSH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddsSW() {
    val a = i32x4.new(100, Int.MAX_VALUE, 100, Int.MIN_VALUE)
    val b = i32x4.new(-4, 3, -2, -1)
    val r = i32x4.new(96, Int.MAX_VALUE, 98, Int.MIN_VALUE)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaAddsSW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddsSD() {
    val a = i64x2.new(100, Long.MIN_VALUE)
    val b = i64x2.new(-4, -3)
    val r = i64x2.new(96, Long.MIN_VALUE)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaAddsSD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddsUB() {
    val a = u8x16.new(100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE)
    val b = u8x16.new(4, 3, 2, 100, 4, 3, 2, 100, 4, 3, 2, 100, 4, 3, 2, 100)
    val r = u8x16.new(104, UByte.MAX_VALUE, 102, UByte.MAX_VALUE, 104, UByte.MAX_VALUE, 102, UByte.MAX_VALUE, 104, UByte.MAX_VALUE, 102, UByte.MAX_VALUE, 104, UByte.MAX_VALUE, 102, UByte.MAX_VALUE)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaAddsUB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddsUH() {
    val a = u16x8.new(100, UShort.MAX_VALUE, 100, UShort.MAX_VALUE, 100, UShort.MAX_VALUE, 100, UShort.MAX_VALUE)
    val b = u16x8.new(4, 3, 2, 1, 4, 3, 2, 1)
    val r = u16x8.new(104, UShort.MAX_VALUE, 102, UShort.MAX_VALUE, 104, UShort.MAX_VALUE, 102, UShort.MAX_VALUE)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaAddsUH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddsUW() {
    val a = u32x4.new(100, UInt.MAX_VALUE, 100, UInt.MAX_VALUE)
    val b = u32x4.new(4, 3, 2, 1)
    val r = u32x4.new(104, UInt.MAX_VALUE, 102, UInt.MAX_VALUE)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaAddsUW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddsUD() {
    val a = u64x2.new(100, ULong.MAX_VALUE)
    val b = u64x2.new(4, 3)
    val r = u64x2.new(104, ULong.MAX_VALUE)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaAddsUD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddvB() {
    val a = i8x16.new(100, Byte.MIN_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MIN_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MIN_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MIN_VALUE, 100, Byte.MAX_VALUE)
    val b = i8x16.new(-4, -3, -2, 100, -4, -3, -2, 100, -4, -3, -2, 100, -4, -3, -2, 100)
    val r = i8x16.new(96, 125, 98, -29, 96, 125, 98, -29, 96, 125, 98, -29, 96, 125, 98, -29)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaAddvB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddvH() {
    val a = i16x8.new(100, Short.MIN_VALUE, 100, Short.MAX_VALUE, 100, Short.MIN_VALUE, 100, Short.MAX_VALUE)
    val b = i16x8.new(-4, -3, -2, 1, -4, -3, -2, 1)
    val r = i16x8.new(96, 32765, 98, -32768, 96, 32765, 98, -32768)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaAddvH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddvW() {
    val a = i32x4.new(100, Int.MAX_VALUE, 100, Int.MIN_VALUE)
    val b = i32x4.new(-4, 3, -2, -1)
    val r = i32x4.new(96, -2147483646, 98, 2147483647)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaAddvW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddvD() {
    val a = i64x2.new(100, Long.MIN_VALUE)
    val b = i64x2.new(-4, -3)
    val r = i64x2.new(96, 9223372036854775805)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaAddvD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddviB() {
    val a = i8x16.new(100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE)
    val r = i8x16.new(103, -126, 103, -126, 103, -126, 103, -126, 103, -126, 103, -126, 103, -126, 103, -126)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaAddviB(transmute(a), 3), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddviH() {
    val a = i16x8.new(Short.MAX_VALUE, 3276, -100, -127, Short.MAX_VALUE, 3276, -100, -127)
    val r = i16x8.new(-32766, 3279, -97, -124, -32766, 3279, -97, -124)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaAddviH(transmute(a), 3), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddviW() {
    val a = i32x4.new(100, Int.MAX_VALUE, 100, Int.MIN_VALUE)
    val r = i32x4.new(103, -2147483646, 103, -2147483645)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaAddviW(transmute(a), 3), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAddviD() {
    val a = i64x2.new(100, Long.MIN_VALUE)
    val r = i64x2.new(117, -9223372036854775791)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaAddviD(transmute(a), 17), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAndV() {
    val a = u8x16.new(100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE)
    val b = u8x16.new(4, 3, 2, 100, 4, 3, 2, 100, 4, 3, 2, 100, 4, 3, 2, 100)
    val r = u8x16.new(4, 3, 0, 100, 4, 3, 0, 100, 4, 3, 0, 100, 4, 3, 0, 100)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaAndV(transmute(a), transmute(b)), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAndiB() {
    val a = u8x16.new(100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE)
    val r = u8x16.new(4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaAndiB(transmute(a), 5), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAsubSB() {
    val a = i8x16.new(-1, -2, -3, -4, -1, -2, -3, -4, -1, -2, -3, -4, -1, -2, -3, -4)
    val b = i8x16.new(-6, -7, -8, -9, -6, -7, -8, -9, -6, -7, -8, -9, -6, -7, -8, -9)
    val r = i8x16.new(5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaAsubSB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAsubSH() {
    val a = i16x8.new(-1, -2, -3, -4, -1, -2, -3, -4)
    val b = i16x8.new(-6, -7, -8, -9, -6, -7, -8, -9)
    val r = i16x8.new(5, 5, 5, 5, 5, 5, 5, 5)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaAsubSH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAsubSW() {
    val a = i32x4.new(-1, -2, -3, -4)
    val b = i32x4.new(-6, -7, -8, -9)
    val r = i32x4.new(5, 5, 5, 5)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaAsubSW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAsubSD() {
    val a = i64x2.new(-1, -2)
    val b = i64x2.new(-6, -7)
    val r = i64x2.new(5, 5)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaAsubSD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAsubUB() {
    val a = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u8x16.new(5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaAsubUB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAsubUH() {
    val a = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val r = u16x8.new(5, 5, 5, 5, 5, 5, 5, 5)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaAsubUH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAsubUW() {
    val a = u32x4.new(1, 2, 3, 4)
    val b = u32x4.new(6, 7, 8, 9)
    val r = u32x4.new(5, 5, 5, 5)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaAsubUW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAsubUD() {
    val a = u64x2.new(1, 2)
    val b = u64x2.new(6, 7)
    val r = u64x2.new(5, 5)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaAsubUD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAveSB() {
    val a = i8x16.new(-1, -2, -3, -4, -1, -2, -3, -4, -1, -2, -3, -4, -1, -2, -3, -4)
    val b = i8x16.new(6, -7, 8, -9, 6, -7, 8, -9, 6, -7, 8, -9, 6, -7, 8, -9)
    val r = i8x16.new(2, -5, 2, -7, 2, -5, 2, -7, 2, -5, 2, -7, 2, -5, 2, -7)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaAveSB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAveSH() {
    val a = i16x8.new(-1, -2, -3, -4, -1, -2, -3, -4)
    val b = i16x8.new(6, -7, 8, -9, 6, -7, 8, -9)
    val r = i16x8.new(2, -5, 2, -7, 2, -5, 2, -7)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaAveSH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAveSW() {
    val a = i32x4.new(-1, -2, -3, -4)
    val b = i32x4.new(6, -7, 8, -9)
    val r = i32x4.new(2, -5, 2, -7)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaAveSW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAveSD() {
    val a = i64x2.new(-1, -2)
    val b = i64x2.new(-6, -7)
    val r = i64x2.new(-4, -5)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaAveSD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAveUB() {
    val a = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u8x16.new(3, 4, 5, 6, 3, 4, 5, 6, 3, 4, 5, 6, 3, 4, 5, 6)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaAveUB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAveUH() {
    val a = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val r = u16x8.new(3, 4, 5, 6, 3, 4, 5, 6)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaAveUH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAveUW() {
    val a = u32x4.new(1, 2, 3, 4)
    val b = u32x4.new(6, 7, 8, 9)
    val r = u32x4.new(3, 4, 5, 6)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaAveUW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAveUD() {
    val a = u64x2.new(1, 2)
    val b = u64x2.new(6, 7)
    val r = u64x2.new(3, 4)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaAveUD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAverSB() {
    val a = i8x16.new(-1, -2, 3, -4, -1, -2, 3, -4, -1, -2, 3, -4, -1, -2, 3, -4)
    val b = i8x16.new(-6, 7, -8, -9, -6, 7, -8, -9, -6, 7, -8, -9, -6, 7, -8, -9)
    val r = i8x16.new(-3, 3, -2, -6, -3, 3, -2, -6, -3, 3, -2, -6, -3, 3, -2, -6)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaAverSB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAverSH() {
    val a = i16x8.new(-1, -2, 3, -4, -1, -2, 3, -4)
    val b = i16x8.new(-6, 7, -8, -9, -6, 7, -8, -9)
    val r = i16x8.new(-3, 3, -2, -6, -3, 3, -2, -6)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaAverSH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAverSW() {
    val a = i32x4.new(-1, -2, 3, -4)
    val b = i32x4.new(-6, 7, -8, -9)
    val r = i32x4.new(-3, 3, -2, -6)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaAverSW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAverSD() {
    val a = i64x2.new(-1, -2)
    val b = i64x2.new(-6, -7)
    val r = i64x2.new(-3, -4)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaAverSD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAverUB() {
    val a = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u8x16.new(4, 5, 6, 7, 4, 5, 6, 7, 4, 5, 6, 7, 4, 5, 6, 7)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaAverUB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAverUH() {
    val a = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val r = u16x8.new(4, 5, 6, 7, 4, 5, 6, 7)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaAverUH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAverUW() {
    val a = u32x4.new(1, 2, 3, 4)
    val b = u32x4.new(6, 7, 8, 9)
    val r = u32x4.new(4, 5, 6, 7)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaAverUW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaAverUD() {
    val a = u64x2.new(1, 2)
    val b = u64x2.new(6, 7)
    val r = u64x2.new(4, 5)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaAverUD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBclrB() {
    val a = u8x16.new(255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u8x16.new(191, 27, 54, 1, 191, 27, 54, 1, 191, 27, 54, 1, 191, 27, 54, 1)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBclrB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBclrH() {
    val a = u16x8.new(255, 155, 55, 1, 255, 155, 55, 1)
    val b = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val r = u16x8.new(191, 27, 55, 1, 191, 27, 55, 1)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaBclrH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBclrW() {
    val a = u32x4.new(255, 155, 55, 1)
    val b = u32x4.new(6, 7, 8, 9)
    val r = u32x4.new(191, 27, 55, 1)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaBclrW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBclrD() {
    val a = u64x2.new(255, 155)
    val b = u64x2.new(6, 7)
    val r = u64x2.new(191, 27)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaBclrD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBclriB() {
    val a = u8x16.new(255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1)
    val r = u8x16.new(247, 147, 55, 1, 247, 147, 55, 1, 247, 147, 55, 1, 247, 147, 55, 1)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBclriB(transmute(a), 3), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBclriH() {
    val a = u16x8.new(2155, 1155, 155, 1, 2155, 1155, 155, 1)
    val r = u16x8.new(107, 1155, 155, 1, 107, 1155, 155, 1)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaBclriH(transmute(a), 11), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBclriW() {
    val a = u32x4.new(211111155, 111111155, 11111155, 1)
    val r = u32x4.new(202722547, 102722547, 2722547, 1)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaBclriW(transmute(a), 23), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBclriD() {
    val a = u64x2.new(211111111155, 11111111111111155)
    val r = u64x2.new(73672157683, 11110973672157683)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaBclriD(transmute(a), 37), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinslB() {
    val a = u8x16.new(255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val c = u8x16.new(1, 3, 5, 9, 1, 3, 5, 9, 1, 3, 5, 9, 1, 3, 5, 9)
    val r = u8x16.new(63, 11, 11, 1, 63, 11, 11, 1, 63, 11, 11, 1, 63, 11, 11, 1)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBinslB(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinslH() {
    val a = u16x8.new(32767, 16384, 8192, 4096, 32767, 16384, 8192, 4096)
    val b = u16x8.new(21656, 5273, 7081, 2985, 21656, 5273, 7081, 2985)
    val c = u16x8.new(3, 7, 9, 13, 15, 17, 21, 23)
    val r = u16x8.new(24575, 5120, 7040, 2984, 21656, 0, 6144, 2816)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaBinslH(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinslW() {
    val a = u32x4.new(2147483647, 536870912, 67108864, 8388608)
    val b = u32x4.new(1036372536, 259093134, 78219975, 1119499719)
    val c = u32x4.new(11, 15, 31, 37)
    val r = u32x4.new(1037041663, 259063808, 78219975, 1082130432)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaBinslW(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinslD() {
    val a = u64x2.new(8006399338, 2882303762)
    val b = u64x2.new(9223372036854775805, 536870912)
    val c = u64x2.new(12, 48)
    val r = u64x2.new(9221120245047489898, 536901394)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaBinslD(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinsliB() {
    val a = u8x16.new(UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u8x16.new(7, 7, 11, 9, 7, 7, 11, 9, 7, 7, 11, 9, 7, 7, 11, 9)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBinsliB(transmute(a), transmute(b), 5), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinsliH() {
    val a = u16x8.new(32767, 16384, 8192, 4096, 32767, 16384, 8192, 4096)
    val b = u16x8.new(21656, 5273, 7081, 2985, 21656, 5273, 7081, 2985)
    val r = u16x8.new(21659, 5272, 7080, 2984, 21659, 5272, 7080, 2984)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaBinsliH(transmute(a), transmute(b), 13), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinsliW() {
    val a = u32x4.new(2147483647, 536870912, 67108864, 8388608)
    val b = u32x4.new(1036372536, 259093134, 78219975, 1119499719)
    val r = u32x4.new(1036386303, 259080192, 78217216, 1119485952)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaBinsliW(transmute(a), transmute(b), 17), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinsliD() {
    val a = u64x2.new(8006399338, 2882303762)
    val b = u64x2.new(9223372036854775805, 536870912)
    val r = u64x2.new(9223372036854773098, 536901394)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaBinsliD(transmute(a), transmute(b), 48), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinsrB() {
    val a = u8x16.new(255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val c = u8x16.new(1, 3, 5, 9, 1, 3, 5, 9, 1, 3, 5, 9, 1, 3, 5, 9)
    val r = u8x16.new(254, 151, 8, 1, 254, 151, 8, 1, 254, 151, 8, 1, 254, 151, 8, 1)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBinsrB(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinsrH() {
    val a = u16x8.new(32767, 16384, 8192, 4096, 32767, 16384, 8192, 4096)
    val b = u16x8.new(21656, 5273, 7081, 2985, 21656, 5273, 7081, 2985)
    val c = u16x8.new(3, 7, 9, 13, 15, 17, 21, 23)
    val r = u16x8.new(32760, 16537, 9129, 2985, 21656, 16385, 8233, 4265)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaBinsrH(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinsrW() {
    val a = u32x4.new(2147483647, 536870912, 67108864, 8388608)
    val b = u32x4.new(1036372536, 259093134, 78219975, 1119499719)
    val c = u32x4.new(11, 15, 31, 37)
    val r = u32x4.new(2147482168, 536900238, 78219975, 8388615)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaBinsrW(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinsrD() {
    val a = u64x2.new(8006399338, 2882303762)
    val b = u64x2.new(9223372036854775805, 536870912)
    val c = u64x2.new(12, 48)
    val r = u64x2.new(8006402045, 536870912)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaBinsrD(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinsriB() {
    val a = u8x16.new(255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u8x16.new(198, 135, 8, 9, 198, 135, 8, 9, 198, 135, 8, 9, 198, 135, 8, 9)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBinsriB(transmute(a), transmute(b), 5), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinsriH() {
    val a = u16x8.new(32767, 16384, 8192, 4096, 32767, 16384, 8192, 4096)
    val b = u16x8.new(21656, 5273, 7081, 2985, 21656, 5273, 7081, 2985)
    val r = u16x8.new(21656, 21657, 7081, 2985, 21656, 21657, 7081, 2985)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaBinsriH(transmute(a), transmute(b), 13), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinsriW() {
    val a = u32x4.new(2147483647, 536870912, 67108864, 8388608)
    val b = u32x4.new(1036372536, 259093134, 78219975, 1119499719)
    val r = u32x4.new(2147338808, 536965774, 67209927, 8533447)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaBinsriW(transmute(a), transmute(b), 17), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBinsriD() {
    val a = u64x2.new(8006399338, 2882303762)
    val b = u64x2.new(9223372036854775805, 536870912)
    val r = u64x2.new(562949953421309, 536870912)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaBinsriD(transmute(a), transmute(b), 48), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBmnzV() {
    val a = u8x16.new(UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val c = u8x16.new(3, 5, 7, 1, 3, 5, 7, 1, 3, 5, 7, 1, 3, 5, 7, 1)
    val r = u8x16.new(254, 159, 48, 1, 254, 159, 48, 1, 254, 159, 48, 1, 254, 159, 48, 1)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBmnzV(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBmnziB() {
    val a = u8x16.new(UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1)
    val b = u8x16.new(1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55)
    val r = u8x16.new(249, 159, 51, 7, 249, 159, 51, 7, 249, 159, 51, 7, 249, 159, 51, 7)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBmnziB(transmute(a), transmute(b), 7), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBmzV() {
    val a = u8x16.new(UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val c = u8x16.new(3, 5, 7, 1, 3, 5, 7, 1, 3, 5, 7, 1, 3, 5, 7, 1)
    val r = u8x16.new(7, 3, 15, 9, 7, 3, 15, 9, 7, 3, 15, 9, 7, 3, 15, 9)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBmzV(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBmziB() {
    val a = u8x16.new(UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1)
    val b = u8x16.new(1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55)
    val r = u8x16.new(7, 251, 159, 49, 7, 251, 159, 49, 7, 251, 159, 49, 7, 251, 159, 49)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBmziB(transmute(a), transmute(b), 7), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnegB() {
    val a = u8x16.new(255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u8x16.new(191, 27, 54, 3, 191, 27, 54, 3, 191, 27, 54, 3, 191, 27, 54, 3)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBnegB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnegH() {
    val a = u16x8.new(255, 155, 55, 1, 255, 155, 55, 1)
    val b = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val r = u16x8.new(191, 27, 311, 513, 191, 27, 311, 513)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaBnegH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnegW() {
    val a = u32x4.new(255, 155, 55, 1)
    val b = u32x4.new(6, 7, 8, 9)
    val r = u32x4.new(191, 27, 311, 513)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaBnegW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnegD() {
    val a = u64x2.new(255, 155)
    val b = u64x2.new(6, 7)
    val r = u64x2.new(191, 27)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaBnegD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnegiB() {
    val a = u8x16.new(50, 100, 127, UByte.MAX_VALUE, 50, 100, 127, UByte.MAX_VALUE, 50, 100, 127, UByte.MAX_VALUE, 50, 100, 127, UByte.MAX_VALUE)
    val r = u8x16.new(34, 116, 111, 239, 34, 116, 111, 239, 34, 116, 111, 239, 34, 116, 111, 239)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBnegiB(transmute(a), 4), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnegiH() {
    val a = u16x8.new(32767, 3276, 100, 127, 32767, 3276, 100, 127)
    val r = u16x8.new(30719, 1228, 2148, 2175, 30719, 1228, 2148, 2175)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaBnegiH(transmute(a), 11), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnegiW() {
    val a = u32x4.new(100, 2147483647, 100, 2147483648)
    val r = u32x4.new(16777316, 2130706431, 16777316, 2164260864)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaBnegiW(transmute(a), 24), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnegiD() {
    val a = u64x2.new(100, 9223372036854775808uL)
    val r = u64x2.new(4398046511204, 9223376434901286912uL)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaBnegiD(transmute(a), 42), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnzB() {
    val a = u8x16.new(1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 4, 4, 0, 4)
    val r = 0
    check(r == transmuteWithLayouts<Int, Unit>(msaBnzB(transmute(a)), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnzH() {
    val a = u16x8.new(32767, 3276, 100, 127, 32767, 0, 100, 127)
    val r = 0
    check(r == transmuteWithLayouts<Int, Unit>(msaBnzH(transmute(a)), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnzW() {
    val a = u32x4.new(100, 2147483647, 0, 2147483648)
    val r = 0
    check(r == transmuteWithLayouts<Int, Unit>(msaBnzW(transmute(a)), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnzD() {
    val a = u64x2.new(100, 9223372036854775808uL)
    val r = 1
    check(r == transmuteWithLayouts<Int, Unit>(msaBnzD(transmute(a)), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBnzV() {
    val a = u8x16.new(0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val r = 1
    check(r == transmuteWithLayouts<Int, Unit>(msaBnzV(transmute(a)), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBselV() {
    val a = u8x16.new(3, 5, 7, 1, 3, 5, 7, 1, 3, 5, 7, 1, 3, 5, 7, 1)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val c = u8x16.new(255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1)
    val r = u8x16.new(7, 3, 15, 9, 7, 3, 15, 9, 7, 3, 15, 9, 7, 3, 15, 9)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBselV(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBseliB() {
    val a = u8x16.new(255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u8x16.new(121, 29, 57, 9, 121, 29, 57, 9, 121, 29, 57, 9, 121, 29, 57, 9)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBseliB(transmute(a), transmute(b), 121), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBsetB() {
    val a = u8x16.new(255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u8x16.new(255, 155, 55, 3, 255, 155, 55, 3, 255, 155, 55, 3, 255, 155, 55, 3)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBsetB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBsetH() {
    val a = u16x8.new(255, 155, 55, 1, 255, 155, 55, 1)
    val b = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val r = u16x8.new(255, 155, 311, 513, 255, 155, 311, 513)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaBsetH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBsetW() {
    val a = u32x4.new(255, 155, 55, 1)
    val b = u32x4.new(6, 7, 8, 9)
    val r = u32x4.new(255, 155, 311, 513)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaBsetW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBsetD() {
    val a = u64x2.new(255, 155)
    val b = u64x2.new(6, 7)
    val r = u64x2.new(255, 155)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaBsetD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBsetiB() {
    val a = u8x16.new(255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1)
    val r = u8x16.new(255, 159, 55, 5, 255, 159, 55, 5, 255, 159, 55, 5, 255, 159, 55, 5)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaBsetiB(transmute(a), 2), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBsetiH() {
    val a = u16x8.new(255, 155, 55, 1, 255, 155, 55, 1)
    val r = u16x8.new(255, 159, 55, 5, 255, 159, 55, 5)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaBsetiH(transmute(a), 2), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBsetiW() {
    val a = u32x4.new(255, 155, 55, 1)
    val r = u32x4.new(255, 159, 55, 5)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaBsetiW(transmute(a), 2), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBsetiD() {
    val a = u64x2.new(255, 155)
    val r = u64x2.new(255, 159)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaBsetiD(transmute(a), 2), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBzB() {
    val a = u8x16.new(255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1)
    val r = 0
    check(r == transmuteWithLayouts<Int, Unit>(msaBzB(transmute(a)), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBzH() {
    val a = u16x8.new(0, 0, 0, 0, 0, 0, 0, 0)
    val r = 1
    check(r == transmuteWithLayouts<Int, Unit>(msaBzH(transmute(a)), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBzW() {
    val a = u32x4.new(255, 0, 55, 1)
    val r = 1
    check(r == transmuteWithLayouts<Int, Unit>(msaBzW(transmute(a)), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBzD() {
    val a = u64x2.new(255, 0)
    val r = 1
    check(r == transmuteWithLayouts<Int, Unit>(msaBzD(transmute(a)), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaBzV() {
    val a = u8x16.new(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val r = 1
    check(r == transmuteWithLayouts<Int, Unit>(msaBzV(transmute(a)), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCeqB() {
    val a = i8x16.new(-128, 127, 55, 1, -128, 127, 55, 1, -128, 127, 55, 1, -128, 127, 55, 1)
    val b = i8x16.new(-128, 126, 55, 1, -128, 126, 55, 1, -128, 126, 55, 1, -128, 126, 55, 1)
    val r = i8x16.new(-1, 0, -1, -1, -1, 0, -1, -1, -1, 0, -1, -1, -1, 0, -1, -1)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaCeqB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCeqH() {
    val a = i16x8.new(255, 155, 55, 1, 255, 155, 55, 1)
    val b = i16x8.new(255, 155, 56, 1, 255, 155, 56, 1)
    val r = i16x8.new(-1, -1, 0, -1, -1, -1, 0, -1)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaCeqH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCeqW() {
    val a = i32x4.new(255, 155, 55, 1)
    val b = i32x4.new(255, 156, 55, 1)
    val r = i32x4.new(-1, 0, -1, -1)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaCeqW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCeqD() {
    val a = i64x2.new(255, 155)
    val b = i64x2.new(255, 156)
    val r = i64x2.new(-1, 0)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaCeqD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCeqiB() {
    val a = i8x16.new(100, -1, -4, 15, 100, -1, -4, 15, 100, -1, -4, 15, 100, -1, -4, 15)
    val r = i8x16.new(0, 0, -1, 0, 0, 0, -1, 0, 0, 0, -1, 0, 0, 0, -1, 0)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaCeqiB(transmute(a), -4), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCeqiH() {
    val a = i16x8.new(32767, 3276, 100, -11, 32767, 3276, 100, -11)
    val r = i16x8.new(0, 0, 0, -1, 0, 0, 0, -1)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaCeqiH(transmute(a), -11), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCeqiW() {
    val a = i32x4.new(1, 3, 5, -3)
    val r = i32x4.new(0, 0, -1, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaCeqiW(transmute(a), 5), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
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
    val a = i8x16.new(-128, 127, 55, 2, -128, 127, 55, 2, -128, 127, 55, 2, -128, 127, 55, 2)
    val b = i8x16.new(-128, 126, 55, 1, -128, 126, 55, 1, -128, 126, 55, 1, -128, 126, 55, 1)
    val r = i8x16.new(-1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaCleSB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleSH() {
    val a = i16x8.new(255, 155, 55, 2, 255, 155, 55, 2)
    val b = i16x8.new(255, 155, 56, 1, 255, 155, 56, 1)
    val r = i16x8.new(-1, -1, -1, 0, -1, -1, -1, 0)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaCleSH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleSW() {
    val a = i32x4.new(255, 155, 55, 2)
    val b = i32x4.new(255, 156, 55, 1)
    val r = i32x4.new(-1, -1, -1, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaCleSW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleSD() {
    val a = i64x2.new(255, 155)
    val b = i64x2.new(255, 156)
    val r = i64x2.new(-1, -1)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaCleSD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleUB() {
    val a = u8x16.new(UByte.MAX_VALUE, 127, 55, 2, UByte.MAX_VALUE, 127, 55, 2, UByte.MAX_VALUE, 127, 55, 2, UByte.MAX_VALUE, 127, 55, 2)
    val b = u8x16.new(UByte.MAX_VALUE, 126, 55, 1, UByte.MAX_VALUE, 126, 55, 1, UByte.MAX_VALUE, 126, 55, 1, UByte.MAX_VALUE, 126, 55, 1)
    val r = i8x16.new(-1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaCleUB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleUH() {
    val a = u16x8.new(UShort.MAX_VALUE, 155, 55, 2, UShort.MAX_VALUE, 155, 55, 2)
    val b = u16x8.new(UShort.MAX_VALUE, 155, 56, 1, UShort.MAX_VALUE, 155, 56, 1)
    val r = i16x8.new(-1, -1, -1, 0, -1, -1, -1, 0)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaCleUH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleUW() {
    val a = u32x4.new(UInt.MAX_VALUE, 155, 55, 2)
    val b = u32x4.new(UInt.MAX_VALUE, 156, 55, 1)
    val r = i32x4.new(-1, -1, -1, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaCleUW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleUD() {
    val a = u64x2.new(ULong.MAX_VALUE, 155)
    val b = u64x2.new(ULong.MAX_VALUE, 156)
    val r = i64x2.new(-1, -1)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaCleUD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleiSB() {
    val a = i8x16.new(-2, -127, 100, -127, -2, -127, 100, -127, -2, -127, 100, -127, -2, -127, 100, -127)
    val r = i8x16.new(-1, -1, 0, -1, -1, -1, 0, -1, -1, -1, 0, -1, -1, -1, 0, -1)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaCleiSB(transmute(a), -2), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleiSH() {
    val a = i16x8.new(32767, 3276, 10, -1, 32767, 3276, 10, -1)
    val r = i16x8.new(0, 0, 0, -1, 0, 0, 0, -1)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaCleiSH(transmute(a), -1), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleiSW() {
    val a = i32x4.new(100, 2147483647, 6, 2147483647)
    val r = i32x4.new(0, 0, -1, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaCleiSW(transmute(a), 6), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
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
    val a = u8x16.new(2, 127, 100, 127, 2, 127, 100, 127, 2, 127, 100, 127, 2, 127, 100, 127)
    val r = i8x16.new(-1, 0, 0, 0, -1, 0, 0, 0, -1, 0, 0, 0, -1, 0, 0, 0)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaCleiUB(transmute(a), 25), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleiUH() {
    val a = u16x8.new(1, 26, 15, 36, 1, 26, 15, 36)
    val r = i16x8.new(-1, 0, -1, 0, -1, 0, -1, 0)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaCleiUH(transmute(a), 25), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleiUW() {
    val a = u32x4.new(25, 32, 25, 32)
    val r = i32x4.new(-1, 0, -1, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaCleiUW(transmute(a), 31), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCleiUD() {
    val a = u64x2.new(10, 26)
    val r = i64x2.new(-1, 0)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaCleiUD(transmute(a), 25), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltSB() {
    val a = i8x16.new(-128, 127, 55, 2, -128, 127, 55, 2, -128, 127, 55, 2, -128, 127, 55, 2)
    val b = i8x16.new(-127, 126, 56, 1, -127, 126, 56, 1, -127, 126, 56, 1, -127, 126, 56, 1)
    val r = i8x16.new(-1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaCltSB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltSH() {
    val a = i16x8.new(-255, 155, 55, 2, -255, 155, 55, 2)
    val b = i16x8.new(255, 156, 56, 1, 255, 156, 56, 1)
    val r = i16x8.new(-1, -1, -1, 0, -1, -1, -1, 0)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaCltSH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltSW() {
    val a = i32x4.new(-255, 155, 55, 2)
    val b = i32x4.new(255, 156, 55, 1)
    val r = i32x4.new(-1, -1, 0, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaCltSW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltSD() {
    val a = i64x2.new(-255, 155)
    val b = i64x2.new(255, 156)
    val r = i64x2.new(-1, -1)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaCltSD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltUB() {
    val a = u8x16.new(128, 127, 55, 2, 128, 127, 55, 2, 128, 127, 55, 2, 128, 127, 55, 2)
    val b = u8x16.new(127, 126, 56, 1, 127, 126, 56, 1, 127, 126, 56, 1, 127, 126, 56, 1)
    val r = i8x16.new(0, 0, -1, 0, 0, 0, -1, 0, 0, 0, -1, 0, 0, 0, -1, 0)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaCltUB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltUH() {
    val a = u16x8.new(255, 155, 55, 2, 255, 155, 55, 2)
    val b = u16x8.new(255, 156, 56, 1, 255, 156, 56, 1)
    val r = i16x8.new(0, -1, -1, 0, 0, -1, -1, 0)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaCltUH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltUW() {
    val a = u32x4.new(255, 155, 55, 2)
    val b = u32x4.new(255, 156, 55, 1)
    val r = i32x4.new(0, -1, 0, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaCltUW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltUD() {
    val a = u64x2.new(255, 155)
    val b = u64x2.new(255, 156)
    val r = i64x2.new(0, -1)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaCltUD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltiSB() {
    val a = i8x16.new(2, -127, -5, 127, 2, -127, -5, 127, 2, -127, -5, 127, 2, -127, -5, 127)
    val r = i8x16.new(0, -1, 0, 0, 0, -1, 0, 0, 0, -1, 0, 0, 0, -1, 0, 0)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaCltiSB(transmute(a), -5), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltiSH() {
    val a = i16x8.new(-1024, 3276, 15, 127, -1024, 3276, 15, 127)
    val r = i16x8.new(-1, 0, 0, 0, -1, 0, 0, 0)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaCltiSH(transmute(a), 15), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltiSW() {
    val a = i32x4.new(-15, 2147483647, -15, 2147483647)
    val r = i32x4.new(-1, 0, -1, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaCltiSW(transmute(a), -10), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
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
    val a = u8x16.new(2, 127, 49, 127, 2, 127, 49, 127, 2, 127, 49, 127, 2, 127, 49, 127)
    val r = i8x16.new(-1, 0, 0, 0, -1, 0, 0, 0, -1, 0, 0, 0, -1, 0, 0, 0)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaCltiUB(transmute(a), 3), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltiUH() {
    val a = u16x8.new(327, 3276, 100, 127, 327, 3276, 100, 127)
    val r = i16x8.new(0, 0, 0, 0, 0, 0, 0, 0)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaCltiUH(transmute(a), 30), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltiUW() {
    val a = u32x4.new(100, 2147483647, 100, 2147483647)
    val r = i32x4.new(0, 0, 0, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaCltiUW(transmute(a), 10), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCltiUD() {
    val a = u64x2.new(1, 9223372036854775807)
    val r = i64x2.new(-1, 0)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaCltiUD(transmute(a), 10), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCopySB() {
    val a = i8x16.new(-100, 127, 4, 127, -100, 127, 4, 127, -100, 127, 4, 127, -100, 127, 4, 127)
    val r = -100
    check(r == transmuteWithLayouts<Int, Unit>(msaCopySB(transmute(a), 12), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCopySH() {
    val a = i16x8.new(32767, 3276, 100, 11, 32767, 3276, 100, 11)
    val r = 32767
    check(r == transmuteWithLayouts<Int, Unit>(msaCopySH(transmute(a), 4), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCopySW() {
    val a = i32x4.new(100, 2147483647, 5, -2147483647)
    val r = 2147483647
    check(r == transmuteWithLayouts<Int, Unit>(msaCopySW(transmute(a), 1), UnresolvedTypeLayout<Int>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCopySD() {
    val a = i64x2.new(3, 9223372036854775807)
    val r = 9223372036854775807.toLong()
    check(r == transmuteWithLayouts<Long, Unit>(msaCopySD(transmute(a), 1L), UnresolvedTypeLayout<Long>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCopyUB() {
    val a = i8x16.new(100, 127, 4, 127, 100, 127, 4, 127, 100, 127, 4, 127, 100, 127, 4, 127)
    val r = 100.toUInt()
    check(r == transmuteWithLayouts<UInt, Unit>(msaCopyUB(transmute(a), 12u), UnresolvedTypeLayout<UInt>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCopyUH() {
    val a = i16x8.new(32767, 3276, 100, 11, 32767, 3276, 100, 11)
    val r = 32767.toUInt()
    check(r == transmuteWithLayouts<UInt, Unit>(msaCopyUH(transmute(a), 4u), UnresolvedTypeLayout<UInt>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCopyUW() {
    val a = i32x4.new(100, 2147483647, 5, 2147483647)
    val r = 2147483647.toUInt()
    check(r == transmuteWithLayouts<UInt, Unit>(msaCopyUW(transmute(a), 1u), UnresolvedTypeLayout<UInt>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaCopyUD() {
    val a = i64x2.new(3, Long.MAX_VALUE)
    val r = 9223372036854775807.toULong()
    check(r == transmuteWithLayouts<ULong, Unit>(msaCopyUD(transmute(a), 1uL), UnresolvedTypeLayout<ULong>(), UnresolvedTypeLayout<Unit>()))
}

// Can not be tested in user mode
// @simdTest(enable = "msa")
// unchecked fn testMsaCtcmsa() (
// )
public fun testMsaDivSB() {
    val a = i8x16.new(-6, -7, -8, -9, -6, -7, -8, -9, -6, -7, -8, -9, -6, -7, -8, -9)
    val b = i8x16.new(-1, -2, -3, -4, -1, -2, -3, -4, -1, -2, -3, -4, -1, -2, -3, -4)
    val r = i8x16.new(6, 3, 2, 2, 6, 3, 2, 2, 6, 3, 2, 2, 6, 3, 2, 2)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaDivSB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDivSH() {
    val a = i16x8.new(-6, -7, -8, -9, 6, 7, 8, 9)
    val b = i16x8.new(-1, -2, -3, -4, -1, -2, -3, -4)
    val r = i16x8.new(6, 3, 2, 2, -6, -3, -2, -2)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaDivSH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDivSW() {
    val a = i32x4.new(-6, -7, 8, 9)
    val b = i32x4.new(-1, -2, -3, -4)
    val r = i32x4.new(6, 3, -2, -2)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaDivSW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDivSD() {
    val a = i64x2.new(-6, 7)
    val b = i64x2.new(-1, -2)
    val r = i64x2.new(6, -3)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaDivSD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDivUB() {
    val a = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val b = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val r = u8x16.new(6, 3, 2, 2, 6, 3, 2, 2, 6, 3, 2, 2, 6, 3, 2, 2)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaDivUB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDivUH() {
    val a = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val b = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val r = u16x8.new(6, 3, 2, 2, 6, 3, 2, 2)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaDivUH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDivUW() {
    val a = u32x4.new(6, 7, 8, 9)
    val b = u32x4.new(1, 2, 3, 4)
    val r = u32x4.new(6, 3, 2, 2)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaDivUW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDivUD() {
    val a = u64x2.new(6, 7)
    val b = u64x2.new(1, 2)
    val r = u64x2.new(6, 3)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaDivUD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDotpSH() {
    val a = i8x16.new(-1, -2, -3, 4, -1, -2, -3, -4, -1, -2, -3, 4, -1, -2, -3, -4)
    val b = i8x16.new(-6, -7, -8, -9, -6, -7, -8, -9, -6, -7, -8, -9, -6, -7, -8, -9)
    val r = i16x8.new(20, -12, 20, 60, 20, -12, 20, 60)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaDotpSH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDotpSW() {
    val a = i16x8.new(-1, -2, -3, -4, -1, -2, -3, 4)
    val b = i16x8.new(-6, -7, -8, -9, -6, -7, -8, -9)
    val r = i32x4.new(20, 60, 20, -12)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaDotpSW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDotpSD() {
    val a = i32x4.new(-1, -2, -3, 4)
    val b = i32x4.new(-6, -7, -8, -9)
    val r = i64x2.new(20, -12)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaDotpSD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDotpUH() {
    val a = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u16x8.new(20, 60, 20, 60, 20, 60, 20, 60)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaDotpUH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDotpUW() {
    val a = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val r = u32x4.new(20, 60, 20, 60)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaDotpUW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDotpUD() {
    val a = u32x4.new(1, 2, 3, 4)
    val b = u32x4.new(6, 7, 8, 9)
    val r = u64x2.new(20, 60)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaDotpUD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDpaddSH() {
    val a = i16x8.new(-1, -2, -3, -4, -1, -2, -3, 4)
    val b = i8x16.new(-1, -2, -3, 4, -1, -2, -3, -4, -1, -2, -3, 4, -1, -2, -3, -4)
    val c = i8x16.new(-6, -7, -8, -9, -6, -7, -8, -9, -6, -7, -8, -9, -6, -7, -8, -9)
    val r = i16x8.new(19, -14, 17, 56, 19, -14, 17, 64)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaDpaddSH(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDpaddSW() {
    val a = i32x4.new(-1, -2, -3, -4)
    val b = i16x8.new(-1, -2, -3, 4, -1, -2, -3, -4)
    val c = i16x8.new(-6, -7, -8, -9, -6, -7, -8, -9)
    val r = i32x4.new(19, -14, 17, 56)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaDpaddSW(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDpaddSD() {
    val a = i64x2.new(-1, -2)
    val b = i32x4.new(-1, -2, -3, 4)
    val c = i32x4.new(-6, -7, -8, -9)
    val r = i64x2.new(19, -14)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaDpaddSD(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDpaddUH() {
    val a = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val c = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u16x8.new(21, 62, 23, 64, 21, 62, 23, 64)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaDpaddUH(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDpaddUW() {
    val a = u32x4.new(1, 2, 3, 4)
    val b = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val c = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val r = u32x4.new(21, 62, 23, 64)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaDpaddUW(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDpaddUD() {
    val a = u64x2.new(1, 2)
    val b = u32x4.new(1, 2, 3, 4)
    val c = u32x4.new(6, 7, 8, 9)
    val r = u64x2.new(21, 62)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaDpaddUD(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDpsubSH() {
    val a = i16x8.new(-1, -2, -3, -4, -1, -2, -3, 4)
    val b = i8x16.new(-1, -2, -3, 4, -1, -2, -3, -4, -1, -2, -3, 4, -1, -2, -3, -4)
    val c = i8x16.new(-6, -7, -8, -9, -6, -7, -8, -9, -6, -7, -8, -9, -6, -7, -8, -9)
    val r = i16x8.new(-21, 10, -23, -64, -21, 10, -23, -56)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaDpsubSH(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDpsubSW() {
    val a = i32x4.new(-1, -2, -3, -4)
    val b = i16x8.new(-1, -2, -3, 4, -1, -2, -3, -4)
    val c = i16x8.new(-6, -7, -8, -9, -6, -7, -8, -9)
    val r = i32x4.new(-21, 10, -23, -64)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaDpsubSW(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDpsubSD() {
    val a = i64x2.new(-1, -2)
    val b = i32x4.new(-1, -2, -3, 4)
    val c = i32x4.new(-6, -7, -8, -9)
    val r = i64x2.new(-21, 10)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaDpsubSD(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDpsubUH() {
    val a = i16x8.new(1, -2, 3, -4, -1, 2, -3, 4)
    val b = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val c = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = i16x8.new(-19, -62, -17, -64, -21, -58, -23, -56)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaDpsubUH(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDpsubUW() {
    val a = i32x4.new(1, -2, 3, -4)
    val b = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val c = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val r = i32x4.new(-19, -62, -17, -64)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaDpsubUW(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaDpsubUD() {
    val a = i64x2.new(1, -2)
    val b = u32x4.new(1, 2, 3, 4)
    val c = u32x4.new(6, 7, 8, 9)
    val r = i64x2.new(-19, -62)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaDpsubUD(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFaddW() {
    val a = f32x4.new(1.1, -2.2, 3.3, -4.4)
    val b = f32x4.new(4.4, -3.3, 2.2, -1.1)
    val r = f32x4.new(5.5, -5.5, 5.5, -5.5)
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFaddW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFaddD() {
    val a = f64x2.new(1.1, -2.2)
    val b = f64x2.new(4.4, -3.3)
    val r = f64x2.new(5.5, -5.5)
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFaddD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

// Only observed beahiour should be SIGFPE signal
// Can not be tested
public fun testMsaFcafW() {
    val a = f32x4.new(1.1, -2.2, 3.3, -4.4)
    val b = f32x4.new(0.0, -1.2, 3.3, Float.NaN)
    val r = i32x4.new(0, 0, 0, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFcafW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

// Only observed beahiour should be SIGFPE signal
// Can not be tested
public fun testMsaFcafD() {
    val a = f64x2.new(1.1, -2.2)
    val b = f64x2.new(-2.2, 1.1)
    val r = i64x2.new(0, 0)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFcafD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFceqW() {
    val a = f32x4.new(1.1, -2.2, 3.3, Float.NaN)
    val b = f32x4.new(-4.4, -2.2, 3.3, Float.NaN)
    val r = i32x4.new(0, -1, -1, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFceqW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFceqD() {
    val a = f64x2.new(1.1, -2.2)
    val b = f64x2.new(1.1, 1.1)
    val r = i64x2.new(-1, 0)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFceqD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFclassW() {
    val a = f32x4.new(1.1, -2.2, 3.3, Float.NaN)
    val r = i32x4.new(128, 8, 128, 2)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFclassW(transmute(a)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFclassD() {
    val a = f64x2.new(1.1, -2.2)
    val r = i64x2.new(128, 8)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFclassD(transmute(a)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcleW() {
    val a = f32x4.new(1.1, -2.2, 3.3, Float.NaN)
    val b = f32x4.new(-4.4, -1.2, 3.3, Float.NaN)
    val r = i32x4.new(0, -1, -1, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFcleW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcleD() {
    val a = f64x2.new(1.1, -2.2)
    val b = f64x2.new(1.1, 1.1)
    val r = i64x2.new(-1, -1)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFcleD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcltW() {
    val a = f32x4.new(1.1, -2.2, 3.3, Float.NaN)
    val b = f32x4.new(-4.4, -1.2, 3.3, Float.NaN)
    val r = i32x4.new(0, -1, 0, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFcltW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcltD() {
    val a = f64x2.new(1.1, -2.2)
    val b = f64x2.new(1.1, 1.1)
    val r = i64x2.new(0, -1)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFcltD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcneW() {
    val a = f32x4.new(1.1, -2.2, 3.3, Float.NaN)
    val b = f32x4.new(-4.4, -1.2, 3.3, Float.NaN)
    val r = i32x4.new(-1, -1, 0, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFcneW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcneD() {
    val a = f64x2.new(1.1, -2.2)
    val b = f64x2.new(1.1, 1.1)
    val r = i64x2.new(0, -1)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFcneD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcorW() {
    val a = f32x4.new(1.1, -2.2, 3.3, Float.NaN)
    val b = f32x4.new(Float.NaN, -1.2, 3.3, Float.NaN)
    val r = i32x4.new(0, -1, -1, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFcorW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcorD() {
    val a = f64x2.new(1.1, Double.NaN)
    val b = f64x2.new(1.1, 1.1)
    val r = i64x2.new(-1, 0)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFcorD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcueqW() {
    val a = f32x4.new(1.1, -2.2, 3.3, Float.NaN)
    val b = f32x4.new(Float.NaN, -1.2, 3.3, Float.NaN)
    val r = i32x4.new(-1, 0, -1, -1)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFcueqW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcueqD() {
    val a = f64x2.new(1.1, Double.NaN)
    val b = f64x2.new(1.1, 1.1)
    val r = i64x2.new(-1, -1)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFcueqD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFculeW() {
    val a = f32x4.new(1.1, -2.2, 3.3, Float.NaN)
    val b = f32x4.new(Float.NaN, -1.2, 3.3, Float.NaN)
    val r = i32x4.new(-1, -1, -1, -1)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFculeW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFculeD() {
    val a = f64x2.new(1.1, Double.NaN)
    val b = f64x2.new(1.1, 1.1)
    val r = i64x2.new(-1, -1)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFculeD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcultW() {
    val a = f32x4.new(1.1, -2.2, 3.3, Float.NaN)
    val b = f32x4.new(Float.NaN, -1.2, 3.3, Float.NaN)
    val r = i32x4.new(-1, -1, 0, -1)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFcultW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcultD() {
    val a = f64x2.new(1.1, Double.NaN)
    val b = f64x2.new(1.1, 1.1)
    val r = i64x2.new(0, -1)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFcultD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcunW() {
    val a = f32x4.new(1.1, -2.2, 3.3, Float.NaN)
    val b = f32x4.new(Float.NaN, -1.2, 3.3, Float.NaN)
    val r = i32x4.new(-1, 0, 0, -1)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFcunW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcunD() {
    val a = f64x2.new(1.1, Double.NaN)
    val b = f64x2.new(1.1, 1.1)
    val r = i64x2.new(0, -1)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFcunD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcuneW() {
    val a = f32x4.new(1.1, -2.2, 3.3, Float.NaN)
    val b = f32x4.new(Float.NaN, -1.2, 3.3, Float.NaN)
    val r = i32x4.new(-1, -1, 0, -1)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFcuneW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFcuneD() {
    val a = f64x2.new(1.1, Double.NaN)
    val b = f64x2.new(1.1, 1.1)
    val r = i64x2.new(0, -1)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFcuneD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFdivW() {
    val a = f32x4.new(5.25, -20.2, 333.333, -425.0)
    val b = f32x4.new(4.0, -2.1, 11.11, 8.2)
    val r = f32x4.new(1.3125, 9.619048, 30.002972, -51.82927)
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFdivW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFdivD() {
    val a = f64x2.new(1111.11, -222222.2)
    val b = f64x2.new(-4.85, 3.33)
    val r = f64x2.new(-229.09484536082473, -66733.3933933934)
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFdivD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
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
    val a = f64x2.new(2000005.5, 2.3)
    val b = f64x2.new(1235689784512.1, 2147483649998.5)
    val r = f32x4.new(1235689800000.0, 2147483600000.0, 2000005.5, 2.3)
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFexdoW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFexp2W() {
    val a = f32x4.new(1.1, -2.2, 3.3, -4.4)
    val b = i32x4.new(4, -3, 2, 1)
    val r = f32x4.new(17.6, -0.275, 13.2, -8.8)
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFexp2W(transmute(a), transmute(b)), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFexp2D() {
    val a = f64x2.new(1.1, -2.2)
    val b = i64x2.new(-4, 3)
    val r = f64x2.new(0.06875, -17.6)
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFexp2D(transmute(a), transmute(b)), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
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
    val a = f32x4.new(5.5, 6.5, 7.5, 8.5)
    val r = f64x2.new(7.5, 8.5)
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFexuplD(transmute(a)), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
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
    val a = f32x4.new(5.5, 6.5, 7.5, 8.5)
    val r = f64x2.new(5.5, 6.5)
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFexuprD(transmute(a)), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFfintSW() {
    val a = i32x4.new(-1, 2, -3, 4)
    val r = f32x4.new(-1.0, 2.0, -3.0, 4.0)
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFfintSW(transmute(a)), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFfintSD() {
    val a = i64x2.new(-1, 2)
    val r = f64x2.new(-1.0, 2.0)
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFfintSD(transmute(a)), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFfintUW() {
    val a = u32x4.new(1, 2, 3, 4)
    val r = f32x4.new(1.0, 2.0, 3.0, 4.0)
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFfintUW(transmute(a)), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFfintUD() {
    val a = u64x2.new(1, 2)
    val r = f64x2.new(1.0, 2.0)
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFfintUD(transmute(a)), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFfqlW() {
    val a = i16x8.new(11, 25, 33, 47, 11, 25, 33, 47)
    val r = f32x4.new(0.00033569336, 0.00076293945, 0.0010070801, 0.0014343262)
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFfqlW(transmute(a)), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFfqlD() {
    val a = i32x4.new(1111, 2222, 3333, 4444)
    val r = f64x2.new(0.000001552049070596695, 0.0000020693987607955933)
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFfqlD(transmute(a)), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFfqrW() {
    val a = i16x8.new(12, 26, 34, 48, 11, 25, 33, 47)
    val r = f32x4.new(0.00036621094, 0.00079345703, 0.0010375977, 0.0014648438)
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFfqrW(transmute(a)), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFfqrD() {
    val a = i32x4.new(1111, 2555, 3333, 475)
    val r = f64x2.new(0.0000005173496901988983, 0.0000011897645890712738)
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFfqrD(transmute(a)), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFillB() {
    val r = i8x16.new(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaFillB(2), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFillH() {
    val r = i16x8.new(2, 2, 2, 2, 2, 2, 2, 2)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaFillH(2), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFillW() {
    val r = i32x4.new(2, 2, 2, 2)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFillW(2), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFillD() {
    val r = i64x2.new(2, 2)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFillD(2L), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFlog2W() {
    val a = f32x4.new(8.0, 16.0, 32.0, 64.0)
    val r = f32x4.new(3.0, 4.0, 5.0, 6.0)
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFlog2W(transmute(a)), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFlog2D() {
    val a = f64x2.new(8.0, 16.0)
    val r = f64x2.new(3.0, 4.0)
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFlog2D(transmute(a)), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFmaddW() {
    val a = f32x4.new(1.0, 2.0, 3.0, 4.0)
    val b = f32x4.new(5.0, 6.0, 7.0, 8.0)
    val c = f32x4.new(9.0, 10.0, 11.0, 12.0)
    val r = f32x4.new(46.0, 62.0, 80.0, 100.0)
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFmaddW(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFmaddD() {
    val a = f64x2.new(1.0, 2.0)
    val b = f64x2.new(3.0, 4.0)
    val c = f64x2.new(5.0, 6.0)
    val r = f64x2.new(16.0, 26.0)
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFmaddD(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFmaxW() {
    val a = f32x4.new(1.0, -6.0, 7.0, 8.0)
    val b = f32x4.new(5.0, -2.0, 3.0, 4.0)
    val r = f32x4.new(5.0, -2.0, 7.0, 8.0)
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFmaxW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFmaxD() {
    val a = f64x2.new(1.0, 4.0)
    val b = f64x2.new(3.0, 2.0)
    val r = f64x2.new(3.0, 4.0)
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFmaxD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFmaxAW() {
    val a = f32x4.new(1.0, -6.0, -7.0, -8.0)
    val b = f32x4.new(5.0, -2.0, 3.0, 4.0)
    val r = f32x4.new(5.0, -6.0, -7.0, -8.0)
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFmaxAW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFmaxAD() {
    val a = f64x2.new(1.0, -4.0)
    val b = f64x2.new(3.0, 2.0)
    val r = f64x2.new(3.0, -4.0)
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFmaxAD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFminW() {
    val a = f32x4.new(1.0, -6.0, 7.0, 8.0)
    val b = f32x4.new(5.0, -2.0, 3.0, 4.0)
    val r = f32x4.new(1.0, -6.0, 3.0, 4.0)
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFminW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFminD() {
    val a = f64x2.new(1.0, 4.0)
    val b = f64x2.new(3.0, 2.0)
    val r = f64x2.new(1.0, 2.0)
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFminD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFminAW() {
    val a = f32x4.new(1.0, -6.0, -7.0, -8.0)
    val b = f32x4.new(5.0, -2.0, 3.0, 4.0)
    val r = f32x4.new(1.0, -2.0, 3.0, 4.0)
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFminAW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFminAD() {
    val a = f64x2.new(1.0, -4.0)
    val b = f64x2.new(3.0, 2.0)
    val r = f64x2.new(1.0, 2.0)
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFminAD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFmsubW() {
    val a = f32x4.new(1.0, 2.0, 3.0, 4.0)
    val b = f32x4.new(5.0, 6.0, 7.0, 8.0)
    val c = f32x4.new(9.0, 10.0, 11.0, 12.0)
    val r = f32x4.new(-44.0, -58.0, -74.0, -92.0)
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFmsubW(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFmsubD() {
    val a = f64x2.new(1.0, 2.0)
    val b = f64x2.new(3.0, 4.0)
    val c = f64x2.new(5.0, 6.0)
    val r = f64x2.new(-14.0, -22.0)
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFmsubD(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFmulW() {
    val a = f32x4.new(1.1, -2.2, 3.3, 4.4)
    val b = f32x4.new(4.4, 3.3, 2.2, -1.1)
    val r = f32x4.new(4.84, -7.26, 7.26, -4.84)
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFmulW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFmulD() {
    val a = f64x2.new(1.1, -2.2)
    val b = f64x2.new(4.0, -3.3)
    val r = f64x2.new(4.4, 7.26)
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFmulD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFrintW() {
    val a = f32x4.new(2.6, -2.7, 1.3, -1.7)
    val r = f32x4.new(3.0, -3.0, 1.0, -2.0)
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFrintW(transmute(a)), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFrintD() {
    val a = f64x2.new(2.6, 1.3)
    val r = f64x2.new(3.0, 1.0)
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFrintD(transmute(a)), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFrcpW() {
    val a = f32x4.new(2.6, -2.7, 1.3, -1.7)
    val r = f32x4.new(0.3846154, -0.37037036, 0.7692308, -0.58823526)
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFrcpW(transmute(a)), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFrcpD() {
    val a = f64x2.new(2.6, 1.3)
    val r = f64x2.new(0.3846153846153846, 0.7692307692307692)
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFrcpD(transmute(a)), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFrsqrtW() {
    val a = f32x4.new(2.6, 2.7, 1.3, 1.7)
    val r = f32x4.new(0.6201737, 0.6085806, 0.87705797, 0.766965)
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFrsqrtW(transmute(a)), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFrsqrtD() {
    val a = f64x2.new(2.6, 1.3)
    val r = f64x2.new(0.6201736729460422, 0.8770580193070292)
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFrsqrtD(transmute(a)), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsafW() {
    val a = f32x4.new(-5.5, 5.5, 5.5, 5.5)
    val b = f32x4.new(-5.5, 5.5, 5.5, 5.5)
    val r = i32x4.new(0, 0, 0, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFsafW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsafD() {
    val a = f64x2.new(-125.5, 5.5)
    val b = f64x2.new(125.5, 3.3)
    val r = i64x2.new(0, 0)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFsafD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFseqW() {
    val a = f32x4.new(-5.5, -3.3, Float.NaN, Float.NaN)
    val b = f32x4.new(5.5, -3.3, Float.NaN, 1.1)
    val r = i32x4.new(0, -1, 0, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFseqW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFseqD() {
    val a = f64x2.new(-125.5, 5.5)
    val b = f64x2.new(125.5, 5.5)
    val r = i64x2.new(0, -1)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFseqD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsleW() {
    val a = f32x4.new(5.5, 5.5, 5.5, Float.NaN)
    val b = f32x4.new(-5.5, 3.3, 5.5, Float.NaN)
    val r = i32x4.new(0, 0, -1, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFsleW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsleD() {
    val a = f64x2.new(-125.5, 5.5)
    val b = f64x2.new(125.5, 3.3)
    val r = i64x2.new(-1, 0)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFsleD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsltW() {
    val a = f32x4.new(-5.5, 5.5, 5.5, 5.5)
    val b = f32x4.new(5.5, 3.3, 5.5, 1.1)
    val r = i32x4.new(-1, 0, 0, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFsltW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsltD() {
    val a = f64x2.new(-125.5, 5.5)
    val b = f64x2.new(125.5, 3.3)
    val r = i64x2.new(-1, 0)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFsltD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsneW() {
    val a = f32x4.new(-5.5, 5.5, 5.5, 5.5)
    val b = f32x4.new(5.5, 3.3, 5.5, 1.1)
    val r = i32x4.new(-1, -1, 0, -1)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFsneW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsneD() {
    val a = f64x2.new(-125.5, 5.5)
    val b = f64x2.new(125.5, 5.5)
    val r = i64x2.new(-1, 0)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFsneD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsorW() {
    val a = f32x4.new(-5.5, Float.NaN, 5.5, 5.5)
    val b = f32x4.new(5.5, 3.3, 5.5, 1.1)
    val r = i32x4.new(-1, 0, -1, -1)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFsorW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsorD() {
    val a = f64x2.new(-125.5, 5.5)
    val b = f64x2.new(125.5, Double.NaN)
    val r = i64x2.new(-1, 0)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFsorD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsqrtW() {
    val a = f32x4.new(9.0, 81.0, 1089.0, 10000.0)
    val r = f32x4.new(3.0, 9.0, 33.0, 100.0)
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFsqrtW(transmute(a)), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsqrtD() {
    val a = f64x2.new(81.0, 10000.0)
    val r = f64x2.new(9.0, 100.0)
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFsqrtD(transmute(a)), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsubW() {
    val a = f32x4.new(5.5, 6.5, 7.5, 8.5)
    val b = f32x4.new(1.25, 1.75, 2.25, 2.75)
    val r = f32x4.new(4.25, 4.75, 5.25, 5.75)
    check(r == transmuteWithLayouts<V4f32, Unit>(msaFsubW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4f32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsubD() {
    val a = f64x2.new(555.5, 55.5)
    val b = f64x2.new(4.25, 3.25)
    val r = f64x2.new(551.25, 52.25)
    check(r == transmuteWithLayouts<V2f64, Unit>(msaFsubD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2f64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsueqW() {
    val a = f32x4.new(5.5, Float.NaN, 5.5, 5.5)
    val b = f32x4.new(5.5, 5.5, -5.5, 5.5)
    val r = i32x4.new(-1, -1, 0, -1)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFsueqW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsueqD() {
    val a = f64x2.new(-5.5, 5.5)
    val b = f64x2.new(5.5, Double.NaN)
    val r = i64x2.new(0, -1)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFsueqD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsuleW() {
    val a = f32x4.new(5.7, 5.8, 5.9, Float.NaN)
    val b = f32x4.new(5.6, 5.9, 5.9, Float.NaN)
    val r = i32x4.new(0, -1, -1, -1)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFsuleW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsuleD() {
    val a = f64x2.new(5.5, 5.5)
    val b = f64x2.new(5.5, 5.5)
    val r = i64x2.new(-1, -1)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFsuleD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsultW() {
    val a = f32x4.new(5.5, 5.5, 5.5, 5.5)
    val b = f32x4.new(5.6, Float.NaN, 2.2, 1.1)
    val r = i32x4.new(-1, -1, 0, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFsultW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsultD() {
    val a = f64x2.new(5.5, Double.NaN)
    val b = f64x2.new(4.4, 3.3)
    val r = i64x2.new(0, -1)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFsultD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsunW() {
    val a = f32x4.new(5.5, 5.5, Float.NaN, 5.5)
    val b = f32x4.new(4.4, 3.3, 2.2, Float.NaN)
    val r = i32x4.new(0, 0, -1, -1)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFsunW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsunD() {
    val a = f64x2.new(5.5, Double.NaN)
    val b = f64x2.new(4.4, 3.3)
    val r = i64x2.new(0, -1)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFsunD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsuneW() {
    val a = f32x4.new(5.5, 5.5, Float.NaN, 5.5)
    val b = f32x4.new(4.4, 3.3, 2.2, 5.5)
    val r = i32x4.new(-1, -1, -1, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFsuneW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFsuneD() {
    val a = f64x2.new(5.5, Double.NaN)
    val b = f64x2.new(5.5, 3.3)
    val r = i64x2.new(0, -1)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFsuneD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFtintSW() {
    val a = f32x4.new(-5.5, 75.6, -1000.7, 1219.3)
    val r = i32x4.new(-6, 76, -1001, 1219)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFtintSW(transmute(a)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFtintSD() {
    val a = f64x2.new(-5.5, 25656.4)
    val r = i64x2.new(-6, 25656)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFtintSD(transmute(a)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFtintUW() {
    val a = f32x4.new(-5.5, 75.6, -1000.7, 1219.3)
    val r = u32x4.new(0, 76, 0, 1219)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaFtintUW(transmute(a)), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFtintUD() {
    val a = f64x2.new(5.5, -25656.4)
    val r = u64x2.new(6, 0)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaFtintUD(transmute(a)), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFtqH() {
    val a = f32x4.new(0.00001, 0.0002, 0.00001, -0.0002)
    val b = f32x4.new(0.0001, -0.002, 0.0001, 0.002)
    val r = i16x8.new(3, -66, 3, 66, 0, 7, 0, -7)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaFtqH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFtqW() {
    val a = f64x2.new(0.00001, -0.0002)
    val b = f64x2.new(0.00000045, 0.000015)
    val r = i32x4.new(966, 32212, 21475, -429497)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFtqW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFtruncSW() {
    val a = f32x4.new(-5.5, 75.6, -1000.7, 1219.3)
    val r = i32x4.new(-5, 75, -1000, 1219)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaFtruncSW(transmute(a)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFtruncSD() {
    val a = f64x2.new(-5.5, 25656.4)
    val r = i64x2.new(-5, 25656)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaFtruncSD(transmute(a)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFtruncUW() {
    val a = f32x4.new(-5.5, 75.6, -1000.7, 1219.3)
    val r = u32x4.new(0, 75, 0, 1219)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaFtruncUW(transmute(a)), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaFtruncUD() {
    val a = f64x2.new(5.5, -25656.4)
    val r = u64x2.new(5, 0)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaFtruncUD(transmute(a)), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaHaddSH() {
    val a = i8x16.new(1, 2, 3, 4, -1, -2, -3, -4, 1, 2, 3, 4, -1, -2, -3, -4)
    val b = i8x16.new(4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1)
    val r = i16x8.new(6, 6, 2, -2, 6, 6, 2, -2)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaHaddSH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaHaddSW() {
    val a = i16x8.new(1, 2, 3, 4, -1, -2, -3, -4)
    val b = i16x8.new(4, 3, 2, 1, 4, 3, 2, 1)
    val r = i32x4.new(6, 6, 2, -2)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaHaddSW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaHaddSD() {
    val a = i32x4.new(1, -2, 3, -4)
    val b = i32x4.new(4, 3, 2, 1)
    val r = i64x2.new(2, -2)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaHaddSD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaHaddUH() {
    val a = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = u8x16.new(4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1)
    val r = u16x8.new(6, 6, 6, 6, 6, 6, 6, 6)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaHaddUH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaHaddUW() {
    val a = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = u16x8.new(4, 3, 2, 1, 4, 3, 2, 1)
    val r = u32x4.new(6, 6, 6, 6)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaHaddUW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaHaddUD() {
    val a = u32x4.new(1, 2, 3, 4)
    val b = u32x4.new(4, 3, 2, 1)
    val r = u64x2.new(6, 6)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaHaddUD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaHsubSH() {
    val a = i8x16.new(1, 2, 3, 4, -1, -2, -3, -4, 1, 2, 3, 4, -1, -2, -3, -4)
    val b = i8x16.new(4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1)
    val r = i16x8.new(-2, 2, -6, -6, -2, 2, -6, -6)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaHsubSH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaHsubSW() {
    val a = i16x8.new(1, 2, 3, 4, -1, -2, -3, -4)
    val b = i16x8.new(4, 3, 2, 1, 4, 3, 2, 1)
    val r = i32x4.new(-2, 2, -6, -6)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaHsubSW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaHsubSD() {
    val a = i32x4.new(1, -2, 3, -4)
    val b = i32x4.new(4, 3, 2, 1)
    val r = i64x2.new(-6, -6)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaHsubSD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaHsubUH() {
    val a = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = u8x16.new(4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1)
    val r = i16x8.new(-2, 2, -2, 2, -2, 2, -2, 2)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaHsubUH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaHsubUW() {
    val a = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = u16x8.new(4, 3, 2, 1, 4, 3, 2, 1)
    val r = i32x4.new(-2, 2, -2, 2)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaHsubUW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaHsubUD() {
    val a = u32x4.new(1, 2, 3, 4)
    val b = u32x4.new(4, 3, 2, 1)
    val r = i64x2.new(-2, 2)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaHsubUD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvevB() {
    val a = i8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = i8x16.new(4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1)
    val r = i8x16.new(4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaIlvevB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvevH() {
    val a = i16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = i16x8.new(4, 3, 2, 1, 4, 3, 2, 1)
    val r = i16x8.new(4, 1, 2, 3, 4, 1, 2, 3)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaIlvevH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvevW() {
    val a = i32x4.new(1, 2, 3, 4)
    val b = i32x4.new(4, 3, 2, 1)
    val r = i32x4.new(4, 1, 2, 3)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaIlvevW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvevD() {
    val a = i64x2.new(1, 2)
    val b = i64x2.new(4, 3)
    val r = i64x2.new(4, 1)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaIlvevD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvlB() {
    val a = i8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = i8x16.new(16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    val r = i8x16.new(8, 9, 7, 10, 6, 11, 5, 12, 4, 13, 3, 14, 2, 15, 1, 16)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaIlvlB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvlH() {
    val a = i16x8.new(1, 2, 3, 4, 5, 6, 7, 8)
    val b = i16x8.new(8, 7, 6, 5, 4, 3, 2, 1)
    val r = i16x8.new(4, 5, 3, 6, 2, 7, 1, 8)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaIlvlH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvlW() {
    val a = i32x4.new(1, 2, 3, 4)
    val b = i32x4.new(4, 3, 2, 1)
    val r = i32x4.new(2, 3, 1, 4)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaIlvlW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvlD() {
    val a = i64x2.new(1, 2)
    val b = i64x2.new(2, 1)
    val r = i64x2.new(1, 2)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaIlvlD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvodB() {
    val a = i8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = i8x16.new(16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    val r = i8x16.new(15, 2, 13, 4, 11, 6, 9, 8, 7, 10, 5, 12, 3, 14, 1, 16)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaIlvodB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvodH() {
    val a = i16x8.new(1, 2, 3, 4, 5, 6, 7, 8)
    val b = i16x8.new(8, 7, 6, 5, 4, 3, 2, 1)
    val r = i16x8.new(7, 2, 5, 4, 3, 6, 1, 8)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaIlvodH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvodW() {
    val a = i32x4.new(1, 2, 3, 4)
    val b = i32x4.new(4, 3, 2, 1)
    val r = i32x4.new(3, 2, 1, 4)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaIlvodW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvodD() {
    val a = i64x2.new(1, 2)
    val b = i64x2.new(2, 1)
    val r = i64x2.new(1, 2)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaIlvodD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvrB() {
    val a = i8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = i8x16.new(16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    val r = i8x16.new(16, 1, 15, 2, 14, 3, 13, 4, 12, 5, 11, 6, 10, 7, 9, 8)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaIlvrB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvrH() {
    val a = i16x8.new(1, 2, 3, 4, 5, 6, 7, 8)
    val b = i16x8.new(8, 7, 6, 5, 4, 3, 2, 1)
    val r = i16x8.new(8, 1, 7, 2, 6, 3, 5, 4)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaIlvrH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvrW() {
    val a = i32x4.new(1, 2, 3, 4)
    val b = i32x4.new(4, 3, 2, 1)
    val r = i32x4.new(4, 1, 3, 2)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaIlvrW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaIlvrD() {
    val a = i64x2.new(1, 2)
    val b = i64x2.new(2, 1)
    val r = i64x2.new(2, 1)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaIlvrD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaInsertB() {
    val a = i8x16.new(-100, 127, 4, 127, -100, 127, 4, 127, -100, 127, 4, 127, -100, 127, 4, 127)
    val r = i8x16.new(-100, 127, 4, 127, -100, 127, 4, 127, -100, 127, 4, 127, 5, 127, 4, 127)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaInsertB(transmute(a), 5, 12), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaInsertH() {
    val a = i16x8.new(32767, 3276, 100, 11, 32767, 3276, 100, 11)
    val r = i16x8.new(32767, 3276, 100, 11, 5, 3276, 100, 11)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaInsertH(transmute(a), 5, 4), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaInsertW() {
    val a = i32x4.new(100, 2147483647, 5, -2147483647)
    val r = i32x4.new(100, 7, 5, -2147483647)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaInsertW(transmute(a), 7, 1), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaInsertD() {
    val a = i64x2.new(3, Long.MAX_VALUE)
    val r = i64x2.new(3, 100)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaInsertD(transmute(a), 100L, 1), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaInsveB() {
    val a = i8x16.new(-100, Byte.MAX_VALUE, 4, Byte.MAX_VALUE, -100, Byte.MAX_VALUE, 4, Byte.MAX_VALUE, -100, Byte.MAX_VALUE, 4, Byte.MAX_VALUE, -100, Byte.MAX_VALUE, 4, Byte.MAX_VALUE)
    val b = i8x16.new(5, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val r = i8x16.new(-100, 127, 4, 127, -100, 127, 4, 127, -100, 127, 4, 127, 5, 127, 4, 127)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaInsveB(transmute(a), transmute(b), 12), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaInsveH() {
    val a = i16x8.new(Short.MAX_VALUE, 3276, 100, 11, Short.MAX_VALUE, 3276, 100, 11)
    val b = i16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val r = i16x8.new(32767, 3276, 100, 11, 1, 3276, 100, 11)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaInsveH(transmute(a), transmute(b), 4), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaInsveW() {
    val a = i32x4.new(100, 2147483647, 5, -2147483647)
    val b = i32x4.new(1, 2, 3, 4)
    val r = i32x4.new(100, 2147483647, 5, 1)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaInsveW(transmute(a), transmute(b), 3), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaInsveD() {
    val a = i64x2.new(3, Long.MAX_VALUE)
    val b = i64x2.new(1, 2)
    val r = i64x2.new(3, 1)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaInsveD(transmute(a), transmute(b), 1), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaLdB() {
    val a: ByteArray  = byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val p = (mutPtrFromRustCast(a[4])).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    val r = i8x16.new(13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaLdB(p, 9), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaLdH() {
    val a: ShortArray  = shortArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val p = (mutPtrFromRustCast(a[4])).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    val r = i16x8.new(3, 4, 5, 6, 7, 8, 9, 10)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaLdH(p, -2), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaLdW() {
    val a: IntArray  = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7)
    val p = (mutPtrFromRustCast(a[3])).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    val r = i32x4.new(2, 3, 4, 5)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaLdW(p, -4), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaLdD() {
    val a: LongArray  = longArrayOf(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val p = (mutPtrFromRustCast(a[4])).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    val r = i64x2.new(0, 1)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaLdD(p, -32), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaLdiB() {
    val r = i8x16.new(-20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaLdiB(-20), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaLdiH() {
    val r = i16x8.new(255, 255, 255, 255, 255, 255, 255, 255)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaLdiH(255), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaLdiW() {
    val r = i32x4.new(-509, -509, -509, -509)
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
    val a = i16x8.new(Short.MAX_VALUE, 1024, Short.MIN_VALUE, -1024, 1, 2, 3, 4)
    val b = i16x8.new(1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024)
    val c = i16x8.new(Short.MAX_VALUE, Short.MAX_VALUE, 1, -1, 33, 66, 99, 132)
    val r = i16x8.new(32767, 2047, -32768, -1025, 2, 4, 6, 8)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMaddQH(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaddQW() {
    val a = i32x4.new(Int.MAX_VALUE, Int.MIN_VALUE, 1, 2)
    val b = i32x4.new(102401, 102401, 102401, 102401)
    val c = i32x4.new(10240, 20480, 30720, 40960)
    val r = i32x4.new(2147483647, -2147483648, 2, 3)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMaddQW(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaddrQH() {
    val a = i16x8.new(32767, 1024, -32768, -1024, 1, 2, 3, 4)
    val b = i16x8.new(1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024)
    val c = i16x8.new(32767, 32767, 32767, 32767, 33, 66, 99, 132)
    val r = i16x8.new(32767, 2048, -31744, 0, 2, 4, 6, 8)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMaddrQH(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaddrQW() {
    val a = i32x4.new(Int.MAX_VALUE, Int.MIN_VALUE, 1, 2)
    val b = i32x4.new(102401, 102401, 102401, 102401)
    val c = i32x4.new(10240, 20480, 30720, 40960)
    val r = i32x4.new(2147483647, -2147483647, 2, 4)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMaddrQW(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaddvB() {
    val a = i8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = i8x16.new(5, 6, 7, 8, 5, 6, 7, 8, 5, 6, 7, 8, 5, 6, 7, 8)
    val c = i8x16.new(9, 10, 11, 12, 9, 10, 11, 12, 9, 10, 11, 12, 9, 10, 11, 12)
    val r = i8x16.new(46, 62, 80, 100, 46, 62, 80, 100, 46, 62, 80, 100, 46, 62, 80, 100)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaMaddvB(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaddvH() {
    val a = i16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = i16x8.new(5, 6, 7, 8, 5, 6, 7, 8)
    val c = i16x8.new(9, 10, 11, 12, 9, 10, 11, 12)
    val r = i16x8.new(46, 62, 80, 100, 46, 62, 80, 100)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMaddvH(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaddvW() {
    val a = i32x4.new(1, 2, 1, 2)
    val b = i32x4.new(3, 4, 3, 4)
    val c = i32x4.new(5, 6, 5, 6)
    val r = i32x4.new(16, 26, 16, 26)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMaddvW(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaddvD() {
    val a = i64x2.new(1, 2)
    val b = i64x2.new(3, 4)
    val c = i64x2.new(5, 6)
    val r = i64x2.new(16, 26)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaMaddvD(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxAB() {
    val a = i8x16.new(1, 2, 3, 4, -1, -2, -3, -4, 1, 2, 3, 4, -1, -2, -3, -4)
    val b = i8x16.new(-6, -7, -8, -9, 6, 7, 8, 9, -6, -7, -8, -9, 6, 7, 8, 9)
    val r = i8x16.new(-6, -7, -8, -9, 6, 7, 8, 9, -6, -7, -8, -9, 6, 7, 8, 9)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaMaxAB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxAH() {
    val a = i16x8.new(1, -2, 3, -4, 1, -2, 3, -4)
    val b = i16x8.new(-6, 7, -8, 9, -6, 7, -8, 9)
    val r = i16x8.new(-6, 7, -8, 9, -6, 7, -8, 9)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMaxAH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxAW() {
    val a = i32x4.new(1, -2, 3, -4)
    val b = i32x4.new(6, 7, 8, 9)
    val r = i32x4.new(6, 7, 8, 9)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMaxAW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxAD() {
    val a = i64x2.new(-1, 2)
    val b = i64x2.new(6, -7)
    val r = i64x2.new(6, -7)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaMaxAD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxSB() {
    val a = i8x16.new(1, 2, 3, 4, -1, -2, -3, -4, 1, 2, 3, 4, -1, -2, -3, -4)
    val b = i8x16.new(-6, -7, -8, -9, 6, 7, 8, 9, -6, -7, -8, -9, 6, 7, 8, 9)
    val r = i8x16.new(1, 2, 3, 4, 6, 7, 8, 9, 1, 2, 3, 4, 6, 7, 8, 9)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaMaxSB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxSH() {
    val a = i16x8.new(1, -2, 3, -4, 1, -2, 3, -4)
    val b = i16x8.new(-6, 7, -8, 9, -6, 7, -8, 9)
    val r = i16x8.new(1, 7, 3, 9, 1, 7, 3, 9)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMaxSH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxSW() {
    val a = i32x4.new(1, -2, 3, -4)
    val b = i32x4.new(6, 7, 8, 9)
    val r = i32x4.new(6, 7, 8, 9)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMaxSW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxSD() {
    val a = i64x2.new(-1, 2)
    val b = i64x2.new(6, -7)
    val r = i64x2.new(6, 2)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaMaxSD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxUB() {
    val a = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaMaxUB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxUH() {
    val a = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val r = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaMaxUH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxUW() {
    val a = u32x4.new(1, 2, 3, 4)
    val b = u32x4.new(6, 7, 8, 9)
    val r = u32x4.new(6, 7, 8, 9)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaMaxUW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxUD() {
    val a = u64x2.new(1, 2)
    val b = u64x2.new(6, 7)
    val r = u64x2.new(6, 7)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaMaxUD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxiSB() {
    val a = i8x16.new(1, -20, -6, 8, 1, -20, -6, 8, 1, -20, -6, 8, 1, -20, -6, 8)
    val r = i8x16.new(1, -16, -6, 8, 1, -16, -6, 8, 1, -16, -6, 8, 1, -16, -6, 8)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaMaxiSB(transmute(a), -16), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxiSH() {
    val a = i16x8.new(1, 3, -60, -8, 1, 3, -6, -8)
    val r = i16x8.new(15, 15, 15, 15, 15, 15, 15, 15)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMaxiSH(transmute(a), 15), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxiSW() {
    val a = i32x4.new(1, 3, -6, -8)
    val r = i32x4.new(1, 3, -5, -5)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMaxiSW(transmute(a), -5), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
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
    val a = u8x16.new(1, 3, 6, 8, 1, 3, 6, 8, 1, 3, 6, 8, 1, 3, 6, 8)
    val r = u8x16.new(5, 5, 6, 8, 5, 5, 6, 8, 5, 5, 6, 8, 5, 5, 6, 8)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaMaxiUB(transmute(a), 5), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxiUH() {
    val a = u16x8.new(1, 3, 6, 8, 1, 3, 6, 8)
    val r = u16x8.new(5, 5, 6, 8, 5, 5, 6, 8)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaMaxiUH(transmute(a), 5), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxiUW() {
    val a = u32x4.new(1, 3, 6, 8)
    val r = u32x4.new(5, 5, 6, 8)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaMaxiUW(transmute(a), 5), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMaxiUD() {
    val a = u64x2.new(1, 8)
    val r = u64x2.new(5, 8)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaMaxiUD(transmute(a), 5), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMinAB() {
    val a = i8x16.new(1, 2, 3, 4, -1, -2, -3, -4, 1, 2, 3, 4, -1, -2, -3, -4)
    val b = i8x16.new(-6, -7, -8, -9, 6, 7, 8, 9, -6, -7, -8, -9, 6, 7, 8, 9)
    val r = i8x16.new(1, 2, 3, 4, -1, -2, -3, -4, 1, 2, 3, 4, -1, -2, -3, -4)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaMinAB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMinAH() {
    val a = i16x8.new(1, -2, 3, -4, 1, -2, 3, -4)
    val b = i16x8.new(-6, 7, -8, 9, -6, 7, -8, 9)
    val r = i16x8.new(1, -2, 3, -4, 1, -2, 3, -4)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMinAH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMinAW() {
    val a = i32x4.new(1, -2, 3, -4)
    val b = i32x4.new(6, 7, 8, 9)
    val r = i32x4.new(1, -2, 3, -4)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMinAW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMinAD() {
    val a = i64x2.new(-1, 2)
    val b = i64x2.new(6, -7)
    val r = i64x2.new(-1, 2)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaMinAD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMinSB() {
    val a = i8x16.new(1, 2, 3, 4, -1, -2, -3, -4, 1, 2, 3, 4, -1, -2, -3, -4)
    val b = i8x16.new(-6, -7, -8, -9, 6, 7, 8, 9, -6, -7, -8, -9, 6, 7, 8, 9)
    val r = i8x16.new(-6, -7, -8, -9, -1, -2, -3, -4, -6, -7, -8, -9, -1, -2, -3, -4)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaMinSB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMinSH() {
    val a = i16x8.new(1, -2, 3, -4, 1, -2, 3, -4)
    val b = i16x8.new(-6, 7, -8, 9, -6, 7, -8, 9)
    val r = i16x8.new(-6, -2, -8, -4, -6, -2, -8, -4)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMinSH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMinSW() {
    val a = i32x4.new(1, -2, 3, -4)
    val b = i32x4.new(6, 7, 8, 9)
    val r = i32x4.new(1, -2, 3, -4)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMinSW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMinSD() {
    val a = i64x2.new(-1, 2)
    val b = i64x2.new(6, -7)
    val r = i64x2.new(-1, -7)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaMinSD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMiniSB() {
    val a = i8x16.new(1, 2, 3, 4, -1, -2, -3, -4, 1, 2, 3, 4, -1, -2, -3, -4)
    val r = i8x16.new(-10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaMiniSB(transmute(a), -10), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMiniSH() {
    val a = i16x8.new(1, -2, 3, -4, 1, -2, 3, -4)
    val r = i16x8.new(-3, -3, -3, -4, -3, -3, -3, -4)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMiniSH(transmute(a), -3), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMiniSW() {
    val a = i32x4.new(1, -2, 3, -4)
    val r = i32x4.new(-3, -3, -3, -4)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMiniSW(transmute(a), -3), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
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
    val a = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaMinUB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMinUH() {
    val a = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val r = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaMinUH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMinUW() {
    val a = u32x4.new(1, 2, 3, 4)
    val b = u32x4.new(6, 7, 8, 9)
    val r = u32x4.new(1, 2, 3, 4)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaMinUW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMinUD() {
    val a = u64x2.new(1, 2)
    val b = u64x2.new(6, 7)
    val r = u64x2.new(1, 2)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaMinUD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMiniUB() {
    val a = u8x16.new(1, 3, 6, 8, 1, 3, 6, 8, 1, 3, 6, 8, 1, 3, 6, 8)
    val r = u8x16.new(1, 3, 5, 5, 1, 3, 5, 5, 1, 3, 5, 5, 1, 3, 5, 5)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaMiniUB(transmute(a), 5), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMiniUH() {
    val a = u16x8.new(1, 3, 6, 8, 1, 3, 6, 8)
    val r = u16x8.new(1, 3, 5, 5, 1, 3, 5, 5)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaMiniUH(transmute(a), 5), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMiniUW() {
    val a = u32x4.new(1, 3, 6, 8)
    val r = u32x4.new(1, 3, 5, 5)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaMiniUW(transmute(a), 5), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMiniUD() {
    val a = u64x2.new(1, 8)
    val r = u64x2.new(1, 5)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaMiniUD(transmute(a), 5), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaModSB() {
    val a = i8x16.new(-6, -7, -8, -9, 6, 7, 8, 9, -6, -7, -8, -9, 6, 7, 8, 9)
    val b = i8x16.new(1, 2, 3, 4, -1, -2, -3, -4, 1, 2, 3, 4, -1, -2, -3, -4)
    val r = i8x16.new(0, -1, -2, -1, 0, 1, 2, 1, 0, -1, -2, -1, 0, 1, 2, 1)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaModSB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaModSH() {
    val a = i16x8.new(-6, 7, -8, 9, -6, 7, -8, 9)
    val b = i16x8.new(1, -2, 3, -4, 1, -2, 3, -4)
    val r = i16x8.new(0, 1, -2, 1, 0, 1, -2, 1)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaModSH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaModSW() {
    val a = i32x4.new(6, 7, 8, 9)
    val b = i32x4.new(1, -2, 3, -4)
    val r = i32x4.new(0, 1, 2, 1)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaModSW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaModSD() {
    val a = i64x2.new(6, -7)
    val b = i64x2.new(-1, 2)
    val r = i64x2.new(0, -1)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaModSD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaModUB() {
    val a = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val b = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val r = u8x16.new(0, 1, 2, 1, 0, 1, 2, 1, 0, 1, 2, 1, 0, 1, 2, 1)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaModUB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaModUH() {
    val a = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val b = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val r = u16x8.new(0, 1, 2, 1, 0, 1, 2, 1)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaModUH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaModUW() {
    val a = u32x4.new(6, 7, 8, 9)
    val b = u32x4.new(1, 2, 3, 4)
    val r = u32x4.new(0, 1, 2, 1)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaModUW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaModUD() {
    val a = u64x2.new(6, 7)
    val b = u64x2.new(1, 2)
    val r = u64x2.new(0, 1)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaModUD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMoveV() {
    val a = i8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8)
    val r = i8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaMoveV(transmute(a)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMsubQH() {
    val a = i16x8.new(1024, -1024, 1024, -1024, 1, 2, 3, 4)
    val b = i16x8.new(1025, 1025, 1025, 1025, 1025, 1025, 1025, 1025)
    val c = i16x8.new(1024, 2048, 3072, 4096, 1024, 2048, 3072, 4096)
    val r = i16x8.new(991, -1089, 927, -1153, -32, -63, -94, -125)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMsubQH(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMsubQW() {
    val a = i32x4.new(2147483647, -2147483647, 1, 2)
    val b = i32x4.new(10240, 10240, 10240, 10240)
    val c = i32x4.new(10240, 20480, 30720, 40960)
    val r = i32x4.new(2147483646, -2147483648, 0, 1)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMsubQW(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMsubrQH() {
    val a = i16x8.new(1024, -1024, 1024, -1024, 1, 2, 3, 4)
    val b = i16x8.new(1025, 1025, 1025, 1025, 1025, 1025, 1025, 1025)
    val c = i16x8.new(1024, 2048, 3072, 4096, 1024, 2048, 3072, 4096)
    val r = i16x8.new(992, -1088, 928, -1152, -31, -62, -93, -124)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMsubrQH(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMsubrQW() {
    val a = i32x4.new(Int.MAX_VALUE, -2147483647, 1, 2)
    val b = i32x4.new(10240, 10240, 10240, 10240)
    val c = i32x4.new(10240, 20480, 30720, 40960)
    val r = i32x4.new(2147483647, -2147483647, 1, 2)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMsubrQW(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMsubvB() {
    val a = i8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = i8x16.new(5, 6, 7, 8, 5, 6, 7, 8, 5, 6, 7, 8, 5, 6, 7, 8)
    val c = i8x16.new(9, 10, 11, 12, 9, 10, 11, 12, 9, 10, 11, 12, 9, 10, 11, 12)
    val r = i8x16.new(-44, -58, -74, -92, -44, -58, -74, -92, -44, -58, -74, -92, -44, -58, -74, -92)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaMsubvB(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMsubvH() {
    val a = i16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = i16x8.new(5, 6, 7, 8, 5, 6, 7, 8)
    val c = i16x8.new(9, 10, 11, 12, 9, 10, 11, 12)
    val r = i16x8.new(-44, -58, -74, -92, -44, -58, -74, -92)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMsubvH(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMsubvW() {
    val a = i32x4.new(1, 2, 1, 2)
    val b = i32x4.new(3, 4, 3, 4)
    val c = i32x4.new(5, 6, 5, 6)
    val r = i32x4.new(-14, -22, -14, -22)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMsubvW(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMsubvD() {
    val a = i64x2.new(1, 2)
    val b = i64x2.new(3, 4)
    val c = i64x2.new(5, 6)
    val r = i64x2.new(-14, -22)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaMsubvD(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMulQH() {
    val a = i16x8.new(12500, -20, -300, 400, 12500, 20, 300, 400)
    val b = i16x8.new(1250, 10240, -7585, 8456, 1250, 10240, -7585, 8456)
    val r = i16x8.new(476, -7, 69, 103, 476, 6, -70, 103)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMulQH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMulQW() {
    val a = i32x4.new(Int.MAX_VALUE, Int.MAX_VALUE, Int.MIN_VALUE, Int.MIN_VALUE)
    val b = i32x4.new(30, 60, 30, 60)
    val r = i32x4.new(29, 59, -30, -60)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMulQW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMulrQH() {
    val a = i16x8.new(12500, -20, -300, 400, 12500, 20, 300, 400)
    val b = i16x8.new(1250, 10240, -7585, 8456, 1250, 10240, -7585, 8456)
    val r = i16x8.new(477, -6, 69, 103, 477, 6, -69, 103)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMulrQH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMulrQW() {
    val a = i32x4.new(Int.MAX_VALUE, Int.MAX_VALUE, Int.MIN_VALUE, Int.MIN_VALUE)
    val b = i32x4.new(30, 60, 30, 60)
    val r = i32x4.new(30, 60, -30, -60)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMulrQW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMulvB() {
    val a = i8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = i8x16.new(16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    val r = i8x16.new(16, 30, 42, 52, 60, 66, 70, 72, 72, 70, 66, 60, 52, 42, 30, 16)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaMulvB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMulvH() {
    val a = i16x8.new(1, 2, 3, 4, 5, 6, 7, 8)
    val b = i16x8.new(8, 7, 6, 5, 4, 3, 2, 1)
    val r = i16x8.new(8, 14, 18, 20, 20, 18, 14, 8)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaMulvH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMulvW() {
    val a = i32x4.new(1, 2, 3, 4)
    val b = i32x4.new(4, 3, 2, 1)
    val r = i32x4.new(4, 6, 6, 4)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaMulvW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaMulvD() {
    val a = i64x2.new(1, 2)
    val b = i64x2.new(2, 1)
    val r = i64x2.new(2, 2)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaMulvD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaNlocB() {
    val a = i8x16.new(-128, -64, -32, -16, -8, -4, -2, -1, 1, 2, 4, 8, 16, 32, 64, 127)
    val r = i8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 0, 0, 0, 0, 0, 0, 0, 0)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaNlocB(transmute(a)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaNlocH() {
    val a = i16x8.new(-32768, -16384, -8192, -4096, 4096, 8192, 16384, 32767)
    val r = i16x8.new(1, 2, 3, 4, 0, 0, 0, 0)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaNlocH(transmute(a)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaNlocW() {
    val a = i32x4.new(Int.MIN_VALUE, -1073741824, 1073741824, Int.MAX_VALUE)
    val r = i32x4.new(1, 2, 0, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaNlocW(transmute(a)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaNlocD() {
    val a = i64x2.new(Long.MIN_VALUE, Long.MAX_VALUE)
    val r = i64x2.new(1, 0)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaNlocD(transmute(a)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaNlzcB() {
    val a = i8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = i8x16.new(7, 6, 6, 5, 5, 5, 5, 4, 4, 4, 4, 4, 4, 4, 4, 3)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaNlzcB(transmute(a)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaNlzcH() {
    val a = i16x8.new(1, 2, 3, 4, 5, 6, 7, 8)
    val r = i16x8.new(15, 14, 14, 13, 13, 13, 13, 12)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaNlzcH(transmute(a)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaNlzcW() {
    val a = i32x4.new(1, 2, 3, 4)
    val r = i32x4.new(31, 30, 30, 29)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaNlzcW(transmute(a)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaNlzcD() {
    val a = i64x2.new(1, 2)
    val r = i64x2.new(63, 62)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaNlzcD(transmute(a)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaNorV() {
    val a = u8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = u8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = u8x16.new(254, 253, 252, 251, 250, 249, 248, 247, 246, 245, 244, 243, 242, 241, 240, 239)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaNorV(transmute(a), transmute(b)), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaNoriB() {
    val a = u8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = u8x16.new(250, 249, 248, 251, 250, 249, 248, 243, 242, 241, 240, 243, 242, 241, 240, 235)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaNoriB(transmute(a), 4), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaOrV() {
    val a = u8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = u8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = u8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaOrV(transmute(a), transmute(b)), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaOriB() {
    val a = u8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = u8x16.new(5, 6, 7, 4, 5, 6, 7, 12, 13, 14, 15, 12, 13, 14, 15, 20)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaOriB(transmute(a), 4), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaPckevB() {
    val a = i8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = i8x16.new(4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1)
    val r = i8x16.new(4, 2, 4, 2, 4, 2, 4, 2, 1, 3, 1, 3, 1, 3, 1, 3)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaPckevB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaPckevH() {
    val a = i16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = i16x8.new(4, 3, 2, 1, 4, 3, 2, 1)
    val r = i16x8.new(4, 2, 4, 2, 1, 3, 1, 3)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaPckevH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaPckevW() {
    val a = i32x4.new(1, 2, 3, 4)
    val b = i32x4.new(4, 3, 2, 1)
    val r = i32x4.new(4, 2, 1, 3)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaPckevW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaPckevD() {
    val a = i64x2.new(1, 2)
    val b = i64x2.new(4, 3)
    val r = i64x2.new(4, 1)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaPckevD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaPckodB() {
    val a = i8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = i8x16.new(4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1)
    val r = i8x16.new(3, 1, 3, 1, 3, 1, 3, 1, 2, 4, 2, 4, 2, 4, 2, 4)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaPckodB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaPckodH() {
    val a = i16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = i16x8.new(4, 3, 2, 1, 4, 3, 2, 1)
    val r = i16x8.new(3, 1, 3, 1, 2, 4, 2, 4)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaPckodH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaPckodW() {
    val a = i32x4.new(1, 2, 3, 4)
    val b = i32x4.new(4, 3, 2, 1)
    val r = i32x4.new(3, 1, 2, 4)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaPckodW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaPckodD() {
    val a = i64x2.new(1, 2)
    val b = i64x2.new(4, 3)
    val r = i64x2.new(3, 2)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaPckodD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaPcntB() {
    val a = i8x16.new(-128, -64, -32, -16, -8, -4, -2, -1, 1, 2, 4, 8, 16, 32, 64, 127)
    val r = i8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 1, 1, 1, 1, 1, 1, 1, 7)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaPcntB(transmute(a)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaPcntH() {
    val a = i16x8.new(-32768, -16384, -8192, -4096, 4096, 8192, 16384, 32767)
    val r = i16x8.new(1, 2, 3, 4, 1, 1, 1, 15)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaPcntH(transmute(a)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaPcntW() {
    val a = i32x4.new(Int.MIN_VALUE, -1073741824, 1073741824, Int.MAX_VALUE)
    val r = i32x4.new(1, 2, 1, 31)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaPcntW(transmute(a)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaPcntD() {
    val a = i64x2.new(-2147483648, 2147483647)
    val r = i64x2.new(33, 31)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaPcntD(transmute(a)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSatSB() {
    val a = i8x16.new(Byte.MAX_VALUE, 105, 30, 1, Byte.MAX_VALUE, 105, 30, 1, Byte.MAX_VALUE, 105, 30, 1, Byte.MAX_VALUE, 105, 30, 1)
    val r = i8x16.new(3, 3, 3, 1, 3, 3, 3, 1, 3, 3, 3, 1, 3, 3, 3, 1)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSatSB(transmute(a), 2), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSatSH() {
    val a = i16x8.new(Short.MAX_VALUE, 1155, 155, 1, Short.MAX_VALUE, 1155, 155, 1)
    val r = i16x8.new(127, 127, 127, 1, 127, 127, 127, 1)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSatSH(transmute(a), 7), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSatSW() {
    val a = i32x4.new(Int.MAX_VALUE, 111111155, Int.MAX_VALUE, 1)
    val r = i32x4.new(131071, 131071, 131071, 1)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSatSW(transmute(a), 17), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSatSD() {
    val a = i64x2.new(Long.MAX_VALUE, 1)
    val r = i64x2.new(137438953471, 1)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSatSD(transmute(a), 37), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSatUB() {
    val a = u8x16.new(UByte.MAX_VALUE, 105, 30, 1, UByte.MAX_VALUE, 105, 30, 1, UByte.MAX_VALUE, 105, 30, 1, UByte.MAX_VALUE, 105, 30, 1)
    val r = u8x16.new(7, 7, 7, 1, 7, 7, 7, 1, 7, 7, 7, 1, 7, 7, 7, 1)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaSatUB(transmute(a), 2), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSatUH() {
    val a = u16x8.new(UShort.MAX_VALUE, 1155, 155, 1, UShort.MAX_VALUE, 1155, 155, 1)
    val r = u16x8.new(255, 255, 155, 1, 255, 255, 155, 1)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaSatUH(transmute(a), 7), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSatUW() {
    val a = u32x4.new(UInt.MAX_VALUE, 111111155, UInt.MAX_VALUE, 1)
    val r = u32x4.new(262143, 262143, 262143, 1)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaSatUW(transmute(a), 17), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSatUD() {
    val a = u64x2.new(ULong.MAX_VALUE, 1)
    val r = u64x2.new(274877906943, 1)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaSatUD(transmute(a), 37), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaShfB() {
    val a = i8x16.new(11, 12, 3, 4, 11, 12, 3, 4, 11, 12, 3, 4, 11, 12, 3, 4)
    val r = i8x16.new(11, 3, 4, 12, 11, 3, 4, 12, 11, 3, 4, 12, 11, 3, 4, 12)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaShfB(transmute(a), 120), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaShfH() {
    val a = i16x8.new(11, 12, 13, 14, 11, 12, 13, 14)
    val r = i16x8.new(11, 14, 12, 13, 11, 14, 12, 13)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaShfH(transmute(a), 156), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaShfW() {
    val a = i32x4.new(1, 2, 3, 4)
    val r = i32x4.new(1, 3, 2, 4)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaShfW(transmute(a), 216), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSldB() {
    val a = i8x16.new(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = i8x16.new(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = i8x16.new(21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 0, 1, 2, 3, 4)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSldB(transmute(a), transmute(b), 5), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSldH() {
    val a = i16x8.new(0, 1, 2, 3, 4, 5, 6, 7)
    val b = i16x8.new(8, 9, 10, 11, 12, 13, 14, 15)
    // let c = 5 as i32;
    val r = i16x8.new(9, 10, 11, 0, 13, 14, 15, 4)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSldH(transmute(a), transmute(b), 2), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSldW() {
    val a = i32x4.new(0, 1, 2, 3)
    val b = i32x4.new(4, 5, 6, 7)
    val r = i32x4.new(4, 5, 6, 7)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSldW(transmute(a), transmute(b), 4), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSldD() {
    val a = i64x2.new(0, 1)
    val b = i64x2.new(2, 3)
    val r = i64x2.new(2, 3)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSldD(transmute(a), transmute(b), 2), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSldiB() {
    val a = i8x16.new(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = i8x16.new(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = i8x16.new(21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 0, 1, 2, 3, 4)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSldiB(transmute(a), transmute(b), 5), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSldiH() {
    val a = i16x8.new(0, 1, 2, 3, 4, 5, 6, 7)
    val b = i16x8.new(8, 9, 10, 11, 12, 13, 14, 15)
    // let c = 5 as i32;
    val r = i16x8.new(9, 10, 11, 0, 13, 14, 15, 4)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSldiH(transmute(a), transmute(b), 2), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSldiW() {
    val a = i32x4.new(0, 1, 2, 3)
    val b = i32x4.new(4, 5, 6, 7)
    val r = i32x4.new(4, 5, 6, 7)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSldiW(transmute(a), transmute(b), 0), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSldiD() {
    val a = i64x2.new(0, 1)
    val b = i64x2.new(2, 3)
    val r = i64x2.new(2, 3)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSldiD(transmute(a), transmute(b), 0), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSllB() {
    val a = i8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = i8x16.new(4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1)
    val r = i8x16.new(16, 16, 12, 8, 16, 16, 12, 8, 16, 16, 12, 8, 16, 16, 12, 8)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSllB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSllH() {
    val a = i16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = i16x8.new(4, 3, 2, 1, 4, 3, 2, 1)
    val r = i16x8.new(16, 16, 12, 8, 16, 16, 12, 8)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSllH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSllW() {
    val a = i32x4.new(1, 2, 3, 4)
    val b = i32x4.new(4, 3, 2, 1)
    val r = i32x4.new(16, 16, 12, 8)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSllW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSllD() {
    val a = i64x2.new(1, 2)
    val b = i64x2.new(4, 3)
    val r = i64x2.new(16, 16)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSllD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSlliB() {
    val a = i8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val r = i8x16.new(4, 8, 12, 16, 4, 8, 12, 16, 4, 8, 12, 16, 4, 8, 12, 16)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSlliB(transmute(a), 2), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSlliH() {
    val a = i16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val r = i16x8.new(4, 8, 12, 16, 4, 8, 12, 16)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSlliH(transmute(a), 2), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSlliW() {
    val a = i32x4.new(1, 2, 3, 4)
    val r = i32x4.new(4, 8, 12, 16)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSlliW(transmute(a), 2), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSlliD() {
    val a = i64x2.new(1, 2)
    val r = i64x2.new(2, 4)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSlliD(transmute(a), 1), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSplatB() {
    val a = i8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val r = i8x16.new(4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSplatB(transmute(a), 3), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSplatH() {
    val a = i16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val r = i16x8.new(4, 4, 4, 4, 4, 4, 4, 4)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSplatH(transmute(a), 3), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSplatW() {
    val a = i32x4.new(1, 2, 3, 4)
    val r = i32x4.new(4, 4, 4, 4)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSplatW(transmute(a), 3), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSplatD() {
    val a = i64x2.new(1, 2)
    val r = i64x2.new(2, 2)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSplatD(transmute(a), 3), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSplatiB() {
    val a = i8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val r = i8x16.new(3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSplatiB(transmute(a), 2), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSplatiH() {
    val a = i16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val r = i16x8.new(3, 3, 3, 3, 3, 3, 3, 3)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSplatiH(transmute(a), 2), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSplatiW() {
    val a = i32x4.new(1, 2, 3, 4)
    val r = i32x4.new(3, 3, 3, 3)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSplatiW(transmute(a), 2), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSplatiD() {
    val a = i64x2.new(1, 2)
    val r = i64x2.new(2, 2)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSplatiD(transmute(a), 1), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSraB() {
    val a = i8x16.new(-128, -64, -32, -16, -8, -4, -2, -1, 1, 2, 4, 8, 16, 32, 64, 127)
    val b = i8x16.new(8, 7, 6, 5, 4, 3, 2, 1, 8, 7, 6, 5, 4, 3, 2, 1)
    val r = i8x16.new(-128, -1, -1, -1, -1, -1, -1, -1, 1, 0, 0, 0, 1, 4, 16, 63)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSraB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSraH() {
    val a = i16x8.new(-32768, -16384, -8192, -4096, 1, 2, 3, 4)
    val b = i16x8.new(15, 14, 13, 12, 12, 13, 14, 15)
    val r = i16x8.new(-1, -1, -1, -1, 0, 0, 0, 0)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSraH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSraW() {
    val a = i32x4.new(Int.MIN_VALUE, -1073741824, 1, 2)
    val b = i32x4.new(16, 15, 16, 15)
    val r = i32x4.new(-32768, -32768, 0, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSraW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSraD() {
    val a = i64x2.new(Long.MIN_VALUE, Long.MAX_VALUE)
    val b = i64x2.new(32, 31)
    val r = i64x2.new(-2147483648, 4294967295)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSraD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSraiB() {
    val a = i8x16.new(Byte.MAX_VALUE, 125, 55, 1, Byte.MAX_VALUE, 125, 55, 1, Byte.MAX_VALUE, 125, 55, 1, Byte.MAX_VALUE, 125, 55, 1)
    val r = i8x16.new(31, 31, 13, 0, 31, 31, 13, 0, 31, 31, 13, 0, 31, 31, 13, 0)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSraiB(transmute(a), 2), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSraiH() {
    val a = i16x8.new(Short.MAX_VALUE, 125, 55, 1, Short.MAX_VALUE, 125, 55, 1)
    val r = i16x8.new(8191, 31, 13, 0, 8191, 31, 13, 0)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSraiH(transmute(a), 2), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSraiW() {
    val a = i32x4.new(Int.MAX_VALUE, 125, 55, 1)
    val r = i32x4.new(536870911, 31, 13, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSraiW(transmute(a), 2), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSraiD() {
    val a = i64x2.new(Long.MAX_VALUE, 55)
    val r = i64x2.new(2305843009213693951, 13)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSraiD(transmute(a), 2), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrarB() {
    val a = i8x16.new(-128, -64, -32, -16, -8, -4, -2, -1, 1, 2, 4, 8, 16, 32, 64, 127)
    val b = i8x16.new(4, 3, 2, 1, 4, 3, 2, 1, 8, 7, 6, 5, 4, 3, 2, 1)
    val r = i8x16.new(-8, -8, -8, -8, 0, 0, 0, 0, 1, 0, 0, 0, 1, 4, 16, 64)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSrarB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrarH() {
    val a = i16x8.new(Short.MIN_VALUE, -16384, -8192, -4096, 150, 50, 25, 15)
    val b = i16x8.new(4, 3, 2, 1, 1, 2, 3, 4)
    val r = i16x8.new(-2048, -2048, -2048, -2048, 75, 13, 3, 1)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSrarH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrarW() {
    val a = i32x4.new(Int.MIN_VALUE, -1073741824, 100, 50)
    val b = i32x4.new(16, 15, 1, 2)
    val r = i32x4.new(-32768, -32768, 50, 13)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSrarW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrarD() {
    val a = i64x2.new(Long.MIN_VALUE, Long.MAX_VALUE)
    val b = i64x2.new(32, 31)
    val r = i64x2.new(-2147483648, 4294967296)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSrarD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrariB() {
    val a = i8x16.new(125, Byte.MAX_VALUE, 55, 1, 125, Byte.MAX_VALUE, 55, 1, 125, Byte.MAX_VALUE, 55, 1, 125, Byte.MAX_VALUE, 55, 1)
    val r = i8x16.new(31, 32, 14, 0, 31, 32, 14, 0, 31, 32, 14, 0, 31, 32, 14, 0)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSrariB(transmute(a), 2), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrariH() {
    val a = i16x8.new(2155, 1155, 155, 1, 2155, 1155, 155, 1)
    val r = i16x8.new(539, 289, 39, 0, 539, 289, 39, 0)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSrariH(transmute(a), 2), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrariW() {
    val a = i32x4.new(211111155, 111111155, 11111155, 1)
    val r = i32x4.new(52777789, 27777789, 2777789, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSrariW(transmute(a), 2), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrariD() {
    val a = i64x2.new(211111111155, 111111111155)
    val r = i64x2.new(52777777789, 27777777789)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSrariD(transmute(a), 2), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrlB() {
    val a = i8x16.new(-128, -64, -32, -16, -8, -4, -2, -1, 1, 2, 4, 8, 16, 32, 64, 127)
    val b = i8x16.new(8, 7, 6, 5, 4, 3, 2, 1, 8, 7, 6, 5, 4, 3, 2, 1)
    val r = i8x16.new(-128, 1, 3, 7, 15, 31, 63, 127, 1, 0, 0, 0, 1, 4, 16, 63)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSrlB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrlH() {
    val a = i16x8.new(-32768, -16384, -8192, -4096, 1, 2, 3, 4)
    val b = i16x8.new(15, 14, 13, 12, 4, 3, 2, 1)
    val r = i16x8.new(1, 3, 7, 15, 0, 0, 0, 2)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSrlH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrlW() {
    val a = i32x4.new(Int.MIN_VALUE, -1073741824, 1, 2)
    val b = i32x4.new(16, 15, 16, 15)
    val r = i32x4.new(32768, 98304, 0, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSrlW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrlD() {
    val a = i64x2.new(Long.MIN_VALUE, Long.MAX_VALUE)
    val b = i64x2.new(32, 31)
    val r = i64x2.new(2147483648, 4294967295)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSrlD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrliB() {
    val a = i8x16.new(25, 50, 100, 127, 25, 50, 100, 127, 25, 50, 100, 127, 25, 50, 100, 127)
    val r = i8x16.new(6, 12, 25, 31, 6, 12, 25, 31, 6, 12, 25, 31, 6, 12, 25, 31)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSrliB(transmute(a), 2), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrliH() {
    val a = i16x8.new(Short.MAX_VALUE, 3276, 100, 127, Short.MAX_VALUE, 3276, 100, 127)
    val r = i16x8.new(8191, 819, 25, 31, 8191, 819, 25, 31)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSrliH(transmute(a), 2), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrliW() {
    val a = i32x4.new(100, Int.MAX_VALUE, 100, Int.MAX_VALUE)
    val r = i32x4.new(25, 536870911, 25, 536870911)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSrliW(transmute(a), 2), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrliD() {
    val a = i64x2.new(100, Long.MAX_VALUE)
    val r = i64x2.new(50, 4611686018427387903)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSrliD(transmute(a), 1), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrlrB() {
    val a = i8x16.new(-128, -64, -32, -16, -8, -4, -2, -1, 1, 2, 4, 8, 16, 32, 64, 127)
    val b = i8x16.new(8, 7, 6, 5, 4, 3, 2, 1, 8, 7, 6, 5, 4, 3, 2, 1)
    val r = i8x16.new(-128, 2, 4, 8, 16, 32, 64, -128, 1, 0, 0, 0, 1, 4, 16, 64)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSrlrB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrlrH() {
    val a = i16x8.new(-32768, -16384, -8192, -4096, 1, 2, 3, 4)
    val b = i16x8.new(15, 14, 13, 12, 4, 3, 2, 1)
    val r = i16x8.new(1, 3, 7, 15, 0, 0, 1, 2)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSrlrH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrlrW() {
    val a = i32x4.new(Int.MIN_VALUE, -1073741824, 1, 2)
    val b = i32x4.new(16, 15, 16, 15)
    val r = i32x4.new(32768, 98304, 0, 0)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSrlrW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrlrD() {
    val a = i64x2.new(Long.MIN_VALUE, Long.MAX_VALUE)
    val b = i64x2.new(32, 31)
    val r = i64x2.new(2147483648, 4294967296)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSrlrD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrlriB() {
    val a = i8x16.new(25, 50, 100, Byte.MAX_VALUE, 25, 50, 100, Byte.MAX_VALUE, 25, 50, 100, Byte.MAX_VALUE, 25, 50, 100, Byte.MAX_VALUE)
    val r = i8x16.new(6, 13, 25, 32, 6, 13, 25, 32, 6, 13, 25, 32, 6, 13, 25, 32)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSrlriB(transmute(a), 2), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrlriH() {
    val a = i16x8.new(Short.MAX_VALUE, 3276, 100, 127, Short.MAX_VALUE, 3276, 100, 127)
    val r = i16x8.new(8192, 819, 25, 32, 8192, 819, 25, 32)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSrlriH(transmute(a), 2), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrlriW() {
    val a = i32x4.new(100, 150, 200, Int.MAX_VALUE)
    val r = i32x4.new(25, 38, 50, 536870912)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSrlriW(transmute(a), 2), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSrlriD() {
    val a = i64x2.new(100, Long.MAX_VALUE)
    val r = i64x2.new(50, 4611686018427387904)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSrlriD(transmute(a), 1), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaStB() {
    val a = i8x16.new(13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28)
    val localStorageL17742C8 = AddressableArrayLocal.fromByteArray(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))
    var arr by localStorageL17742C8
    val r: ByteArray = byteArrayOf(13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28)
    msaStB(transmute(a), (localStorageL17742C8.elementMutPtr(0)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0)
    check((arr).contentEquals(r))
}

public fun testMsaStH() {
    val a = i16x8.new(13, 14, 15, 16, 17, 18, 19, 20)
    val localStorageL17764C8 = AddressableArrayLocal.fromShortArray(shortArrayOf(0, 0, 0, 0, 0, 0, 0, 0))
    var arr by localStorageL17764C8
    val r: ShortArray = shortArrayOf(13, 14, 15, 16, 17, 18, 19, 20)
    msaStH(transmute(a), (localStorageL17764C8.elementMutPtr(0)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0)
    check((arr).contentEquals(r))
}

public fun testMsaStW() {
    val a = i32x4.new(13, 14, 15, 16)
    val localStorageL17775C8 = AddressableArrayLocal.fromIntArray(intArrayOf(0, 0, 0, 0))
    var arr by localStorageL17775C8
    val r: IntArray = intArrayOf(13, 14, 15, 16)
    msaStW(transmute(a), (localStorageL17775C8.elementMutPtr(0)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0)
    check((arr).contentEquals(r))
}

public fun testMsaStD() {
    val a = i64x2.new(13, 14)
    val localStorageL17786C8 = AddressableArrayLocal.fromLongArray(longArrayOf(0L, 0L))
    var arr by localStorageL17786C8
    val r: LongArray = longArrayOf(13L, 14L)
    msaStD(transmute(a), (localStorageL17786C8.elementMutPtr(0)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 0)
    check((arr).contentEquals(r))
}

public fun testMsaSubsSB() {
    val a = i8x16.new(Byte.MIN_VALUE, -2, -3, -4, Byte.MIN_VALUE, -2, -3, -4, Byte.MIN_VALUE, -2, -3, -4, Byte.MIN_VALUE, -2, -3, -4)
    val b = i8x16.new(6, -7, 8, -9, 6, -7, 8, -9, 6, -7, 8, -9, 6, -7, 8, -9)
    val r = i8x16.new(Byte.MIN_VALUE, 5, -11, 5, Byte.MIN_VALUE, 5, -11, 5, Byte.MIN_VALUE, 5, -11, 5, Byte.MIN_VALUE, 5, -11, 5)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSubsSB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsSH() {
    val a = i16x8.new(Short.MIN_VALUE, -2, -3, -4, Short.MIN_VALUE, -2, -3, -4)
    val b = i16x8.new(6, -7, 8, -9, 6, -7, 8, -9)
    val r = i16x8.new(Short.MIN_VALUE, 5, -11, 5, Short.MIN_VALUE, 5, -11, 5)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSubsSH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsSW() {
    val a = i32x4.new(Int.MIN_VALUE, -2, -3, -4)
    val b = i32x4.new(6, -7, 8, -9)
    val r = i32x4.new(Int.MIN_VALUE, 5, -11, 5)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSubsSW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsSD() {
    val a = i64x2.new(Long.MIN_VALUE, -2)
    val b = i64x2.new(6, -7)
    val r = i64x2.new(Long.MIN_VALUE, 5)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSubsSD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsUB() {
    val a = u8x16.new(UByte.MAX_VALUE, 2, 3, 4, UByte.MAX_VALUE, 2, 3, 4, UByte.MAX_VALUE, 2, 3, 4, UByte.MAX_VALUE, 2, 3, 4)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u8x16.new(249, 0, 0, 0, 249, 0, 0, 0, 249, 0, 0, 0, 249, 0, 0, 0)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaSubsUB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsUH() {
    val a = u16x8.new(UShort.MAX_VALUE, 2, 3, 4, UShort.MAX_VALUE, 2, 3, 4)
    val b = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val r = u16x8.new(65529, 0, 0, 0, 65529, 0, 0, 0)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaSubsUH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsUW() {
    val a = u32x4.new(UInt.MAX_VALUE, 2, 3, 4)
    val b = u32x4.new(6, 7, 8, 9)
    val r = u32x4.new(4294967289, 0, 0, 0)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaSubsUW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsUD() {
    val a = u64x2.new(ULong.MAX_VALUE, 2)
    val b = u64x2.new(6, 7)
    val r = u64x2.new(18446744073709551609uL, 0)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaSubsUD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsusUB() {
    val a = u8x16.new(UByte.MAX_VALUE, 2, 3, 4, UByte.MAX_VALUE, 2, 3, 4, UByte.MAX_VALUE, 2, 3, 4, UByte.MAX_VALUE, 2, 3, 4)
    val b = i8x16.new(-6, -7, -8, -9, -6, -7, -8, -9, -6, -7, -8, -9, -6, -7, -8, -9)
    val r = u8x16.new(255, 9, 11, 13, 255, 9, 11, 13, 255, 9, 11, 13, 255, 9, 11, 13)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaSubsusUB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsusUH() {
    val a = u16x8.new(UShort.MAX_VALUE, 2, 3, 4, UShort.MAX_VALUE, 2, 3, 4)
    val b = i16x8.new(-6, -7, -8, -9, -6, -7, -8, -9)
    val r = u16x8.new(65535, 9, 11, 13, 65535, 9, 11, 13)
    check(r == transmuteWithLayouts<V8u16, Unit>(msaSubsusUH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8u16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsusUW() {
    val a = u32x4.new(UInt.MAX_VALUE, 2, 3, 4)
    val b = i32x4.new(-6, -7, -8, -9)
    val r = u32x4.new(4294967295, 9, 11, 13)
    check(r == transmuteWithLayouts<V4u32, Unit>(msaSubsusUW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4u32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsusUD() {
    val a = u64x2.new(ULong.MAX_VALUE, 2)
    val b = i64x2.new(-6, -7)
    val r = u64x2.new(18446744073709551615uL, 9)
    check(r == transmuteWithLayouts<V2u64, Unit>(msaSubsusUD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2u64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsuuSB() {
    val a = u8x16.new(UByte.MAX_VALUE, 2, 3, 4, UByte.MAX_VALUE, 2, 3, 4, UByte.MAX_VALUE, 2, 3, 4, UByte.MAX_VALUE, 2, 3, 4)
    val b = u8x16.new(6, 7, 8, UByte.MAX_VALUE, 6, 7, 8, UByte.MAX_VALUE, 6, 7, 8, UByte.MAX_VALUE, 6, 7, 8, UByte.MAX_VALUE)
    val r = i8x16.new(127, -5, -5, -128, 127, -5, -5, -128, 127, -5, -5, -128, 127, -5, -5, -128)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSubsuuSB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsuuSH() {
    val a = u16x8.new(UShort.MAX_VALUE, 2, 3, 4, UShort.MAX_VALUE, 2, 3, 4)
    val b = u16x8.new(6, 7, 8, 65535, 6, 7, 8, 65535)
    val r = i16x8.new(32767, -5, -5, -32768, 32767, -5, -5, -32768)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSubsuuSH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsuuSW() {
    val a = u32x4.new(UInt.MAX_VALUE, 2, 3, 4)
    val b = u32x4.new(6, 7, 8, 4294967295)
    val r = i32x4.new(2147483647, -5, -5, -2147483648)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSubsuuSW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubsuuSD() {
    val a = u64x2.new(ULong.MAX_VALUE, 2)
    val b = u64x2.new(6, 7)
    val r = i64x2.new(Long.MAX_VALUE, -5)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSubsuuSD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubvB() {
    val a = i8x16.new(Byte.MIN_VALUE, -2, -3, -4, Byte.MIN_VALUE, -2, -3, -4, Byte.MIN_VALUE, -2, -3, -4, Byte.MIN_VALUE, -2, -3, -4)
    val b = i8x16.new(6, -7, 8, -9, 6, -7, 8, -9, 6, -7, 8, -9, 6, -7, 8, -9)
    val r = i8x16.new(122, 5, -11, 5, 122, 5, -11, 5, 122, 5, -11, 5, 122, 5, -11, 5)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSubvB(transmute(a), transmute(b)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubvH() {
    val a = i16x8.new(Short.MIN_VALUE, -2, -3, -4, Short.MIN_VALUE, -2, -3, -4)
    val b = i16x8.new(6, -7, 8, -9, 6, -7, 8, -9)
    val r = i16x8.new(32762, 5, -11, 5, 32762, 5, -11, 5)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSubvH(transmute(a), transmute(b)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubvW() {
    val a = i32x4.new(Int.MIN_VALUE, -2, -3, -4)
    val b = i32x4.new(6, -7, 8, -9)
    val r = i32x4.new(2147483642, 5, -11, 5)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSubvW(transmute(a), transmute(b)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubvD() {
    val a = i64x2.new(Long.MAX_VALUE, -2)
    val b = i64x2.new(6, -7)
    val r = i64x2.new(9223372036854775801, 5)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSubvD(transmute(a), transmute(b)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubviB() {
    val a = i8x16.new(100, Byte.MAX_VALUE, 50, Byte.MIN_VALUE, 100, Byte.MAX_VALUE, 50, Byte.MIN_VALUE, 100, Byte.MAX_VALUE, 50, Byte.MIN_VALUE, 100, Byte.MAX_VALUE, 50, Byte.MIN_VALUE)
    val r = i8x16.new(95, 122, 45, 123, 95, 122, 45, 123, 95, 122, 45, 123, 95, 122, 45, 123)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaSubviB(transmute(a), 5), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubviH() {
    val a = i16x8.new(Short.MAX_VALUE, 3276, -100, Short.MIN_VALUE, Short.MAX_VALUE, 3276, -100, Short.MIN_VALUE)
    val r = i16x8.new(32762, 3271, -105, 32763, 32762, 3271, -105, 32763)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaSubviH(transmute(a), 5), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubviW() {
    val a = i32x4.new(100, 150, 200, Int.MAX_VALUE)
    val r = i32x4.new(95, 145, 195, 2147483642)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaSubviW(transmute(a), 5), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaSubviD() {
    val a = i64x2.new(100, Long.MAX_VALUE)
    val r = i64x2.new(95, 9223372036854775802)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaSubviD(transmute(a), 5), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaVshfB() {
    val a = i8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = i8x16.new(4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1)
    val c = i8x16.new(4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1)
    val r = i8x16.new(3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4)
    check(r == transmuteWithLayouts<V16i8, Unit>(msaVshfB(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V16i8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaVshfH() {
    val a = i16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = i16x8.new(4, 3, 2, 1, 4, 3, 2, 1)
    val c = i16x8.new(4, 3, 2, 1, 4, 3, 2, 1)
    val r = i16x8.new(3, 2, 1, 4, 3, 2, 1, 4)
    check(r == transmuteWithLayouts<V8i16, Unit>(msaVshfH(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V8i16>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaVshfW() {
    val a = i32x4.new(1, 2, 3, 4)
    val b = i32x4.new(4, 3, 2, 1)
    val c = i32x4.new(4, 3, 2, 1)
    val r = i32x4.new(3, 2, 1, 4)
    check(r == transmuteWithLayouts<V4i32, Unit>(msaVshfW(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V4i32>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaVshfD() {
    val a = i64x2.new(1, 2)
    val b = i64x2.new(4, 3)
    val c = i64x2.new(4, 3)
    val r = i64x2.new(3, 4)
    check(r == transmuteWithLayouts<V2i64, Unit>(msaVshfD(transmute(a), transmute(b), transmute(c)), UnresolvedTypeLayout<V2i64>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaXorV() {
    val a = u8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = u8x16.new(16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    val r = u8x16.new(17, 13, 13, 9, 9, 13, 13, 1, 1, 13, 13, 9, 9, 13, 13, 17)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaXorV(transmute(a), transmute(b)), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}

public fun testMsaXoriB() {
    val a = u8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = u8x16.new(5, 6, 7, 0, 1, 2, 3, 12, 13, 14, 15, 8, 9, 10, 11, 20)
    check(r == transmuteWithLayouts<V16u8, Unit>(msaXoriB(transmute(a), 4), UnresolvedTypeLayout<V16u8>(), UnresolvedTypeLayout<Unit>()))
}
