// port-lint: tests mips/msa.rs
package io.github.kotlinmania.rustlang.corearch.mips.msa

import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmute
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
    check(r == transmute(msaAddAB(transmute(a), transmute(b))))
}

public fun testMsaAddAH() {
    val a = i16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = i16x8.new(-4, -3, -2, -1, -4, -3, -2, -1)
    val r = i16x8.new(5, 5, 5, 5, 5, 5, 5, 5)
    check(r == transmute(msaAddAH(transmute(a), transmute(b))))
}

public fun testMsaAddAW() {
    val a = i32x4.new(1, 2, 3, 4)
    val b = i32x4.new(-4, -3, -2, -1)
    val r = i32x4.new(5, 5, 5, 5)
    check(r == transmute(msaAddAW(transmute(a), transmute(b))))
}

public fun testMsaAddAD() {
    val a = i64x2.new(1, 2)
    val b = i64x2.new(-4, -3)
    val r = i64x2.new(5, 5)
    check(r == transmute(msaAddAD(transmute(a), transmute(b))))
}

public fun testMsaAddsAB() {
    val a = i8x16.new(100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE)
    val b = i8x16.new(-4, -3, -2, -100, -4, -3, -2, -100, -4, -3, -2, -100, -4, -3, -2, -100)
    val r = i8x16.new(104, 127, 102, 127, 104, 127, 102, 127, 104, 127, 102, 127, 104, 127, 102, 127)
    check(r == transmute(msaAddsAB(transmute(a), transmute(b))))
}

public fun testMsaAddsAH() {
    val a = i16x8.new(100, Short.MAX_VALUE, 100, Short.MAX_VALUE, 100, Short.MAX_VALUE, 100, Short.MAX_VALUE)
    val b = i16x8.new(-4, -3, -2, -1, -4, -3, -2, -1)
    val r = i16x8.new(104, Short.MAX_VALUE, 102, Short.MAX_VALUE, 104, Short.MAX_VALUE, 102, Short.MAX_VALUE)
    check(r == transmute(msaAddsAH(transmute(a), transmute(b))))
}

public fun testMsaAddsAW() {
    val a = i32x4.new(100, Int.MAX_VALUE, 100, Int.MAX_VALUE)
    val b = i32x4.new(-4, -3, -2, -1)
    val r = i32x4.new(104, Int.MAX_VALUE, 102, Int.MAX_VALUE)
    check(r == transmute(msaAddsAW(transmute(a), transmute(b))))
}

public fun testMsaAddsAD() {
    val a = i64x2.new(100, Long.MAX_VALUE)
    val b = i64x2.new(-4, -3)
    val r = i64x2.new(104, Long.MAX_VALUE)
    check(r == transmute(msaAddsAD(transmute(a), transmute(b))))
}

public fun testMsaAddsSB() {
    val a = i8x16.new(100, Byte.MIN_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MIN_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MIN_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MIN_VALUE, 100, Byte.MAX_VALUE)
    val b = i8x16.new(-4, -3, -2, 100, -4, -3, -2, 100, -4, -3, -2, 100, -4, -3, -2, 100)
    val r = i8x16.new(96, Byte.MIN_VALUE, 98, Byte.MAX_VALUE, 96, Byte.MIN_VALUE, 98, Byte.MAX_VALUE, 96, Byte.MIN_VALUE, 98, Byte.MAX_VALUE, 96, Byte.MIN_VALUE, 98, Byte.MAX_VALUE)
    check(r == transmute(msaAddsSB(transmute(a), transmute(b))))
}

public fun testMsaAddsSH() {
    val a = i16x8.new(100, Short.MIN_VALUE, 100, Short.MAX_VALUE, 100, Short.MIN_VALUE, 100, Short.MAX_VALUE)
    val b = i16x8.new(-4, -3, -2, 1, -4, -3, -2, 1)
    val r = i16x8.new(96, Short.MIN_VALUE, 98, Short.MAX_VALUE, 96, Short.MIN_VALUE, 98, Short.MAX_VALUE)
    check(r == transmute(msaAddsSH(transmute(a), transmute(b))))
}

public fun testMsaAddsSW() {
    val a = i32x4.new(100, Int.MAX_VALUE, 100, Int.MIN_VALUE)
    val b = i32x4.new(-4, 3, -2, -1)
    val r = i32x4.new(96, Int.MAX_VALUE, 98, Int.MIN_VALUE)
    check(r == transmute(msaAddsSW(transmute(a), transmute(b))))
}

public fun testMsaAddsSD() {
    val a = i64x2.new(100, Long.MIN_VALUE)
    val b = i64x2.new(-4, -3)
    val r = i64x2.new(96, Long.MIN_VALUE)
    check(r == transmute(msaAddsSD(transmute(a), transmute(b))))
}

public fun testMsaAddsUB() {
    val a = u8x16.new(100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE)
    val b = u8x16.new(4, 3, 2, 100, 4, 3, 2, 100, 4, 3, 2, 100, 4, 3, 2, 100)
    val r = u8x16.new(104, UByte.MAX_VALUE, 102, UByte.MAX_VALUE, 104, UByte.MAX_VALUE, 102, UByte.MAX_VALUE, 104, UByte.MAX_VALUE, 102, UByte.MAX_VALUE, 104, UByte.MAX_VALUE, 102, UByte.MAX_VALUE)
    check(r == transmute(msaAddsUB(transmute(a), transmute(b))))
}

public fun testMsaAddsUH() {
    val a = u16x8.new(100, UShort.MAX_VALUE, 100, UShort.MAX_VALUE, 100, UShort.MAX_VALUE, 100, UShort.MAX_VALUE)
    val b = u16x8.new(4, 3, 2, 1, 4, 3, 2, 1)
    val r = u16x8.new(104, UShort.MAX_VALUE, 102, UShort.MAX_VALUE, 104, UShort.MAX_VALUE, 102, UShort.MAX_VALUE)
    check(r == transmute(msaAddsUH(transmute(a), transmute(b))))
}

public fun testMsaAddsUW() {
    val a = u32x4.new(100, UInt.MAX_VALUE, 100, UInt.MAX_VALUE)
    val b = u32x4.new(4, 3, 2, 1)
    val r = u32x4.new(104, UInt.MAX_VALUE, 102, UInt.MAX_VALUE)
    check(r == transmute(msaAddsUW(transmute(a), transmute(b))))
}

public fun testMsaAddsUD() {
    val a = u64x2.new(100, ULong.MAX_VALUE)
    val b = u64x2.new(4, 3)
    val r = u64x2.new(104, ULong.MAX_VALUE)
    check(r == transmute(msaAddsUD(transmute(a), transmute(b))))
}

public fun testMsaAddvB() {
    val a = i8x16.new(100, Byte.MIN_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MIN_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MIN_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MIN_VALUE, 100, Byte.MAX_VALUE)
    val b = i8x16.new(-4, -3, -2, 100, -4, -3, -2, 100, -4, -3, -2, 100, -4, -3, -2, 100)
    val r = i8x16.new(96, 125, 98, -29, 96, 125, 98, -29, 96, 125, 98, -29, 96, 125, 98, -29)
    check(r == transmute(msaAddvB(transmute(a), transmute(b))))
}

public fun testMsaAddvH() {
    val a = i16x8.new(100, Short.MIN_VALUE, 100, Short.MAX_VALUE, 100, Short.MIN_VALUE, 100, Short.MAX_VALUE)
    val b = i16x8.new(-4, -3, -2, 1, -4, -3, -2, 1)
    val r = i16x8.new(96, 32765, 98, -32768, 96, 32765, 98, -32768)
    check(r == transmute(msaAddvH(transmute(a), transmute(b))))
}

public fun testMsaAddvW() {
    val a = i32x4.new(100, Int.MAX_VALUE, 100, Int.MIN_VALUE)
    val b = i32x4.new(-4, 3, -2, -1)
    val r = i32x4.new(96, -2147483646, 98, 2147483647)
    check(r == transmute(msaAddvW(transmute(a), transmute(b))))
}

public fun testMsaAddvD() {
    val a = i64x2.new(100, Long.MIN_VALUE)
    val b = i64x2.new(-4, -3)
    val r = i64x2.new(96, 9223372036854775805)
    check(r == transmute(msaAddvD(transmute(a), transmute(b))))
}

public fun testMsaAddviB() {
    val a = i8x16.new(100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE, 100, Byte.MAX_VALUE)
    val r = i8x16.new(103, -126, 103, -126, 103, -126, 103, -126, 103, -126, 103, -126, 103, -126, 103, -126)
    check(r == transmute(msaAddviB(transmute(a), 3)))
}

public fun testMsaAddviH() {
    val a = i16x8.new(Short.MAX_VALUE, 3276, -100, -127, Short.MAX_VALUE, 3276, -100, -127)
    val r = i16x8.new(-32766, 3279, -97, -124, -32766, 3279, -97, -124)
    check(r == transmute(msaAddviH(transmute(a), 3)))
}

public fun testMsaAddviW() {
    val a = i32x4.new(100, Int.MAX_VALUE, 100, Int.MIN_VALUE)
    val r = i32x4.new(103, -2147483646, 103, -2147483645)
    check(r == transmute(msaAddviW(transmute(a), 3)))
}

public fun testMsaAddviD() {
    val a = i64x2.new(100, Long.MIN_VALUE)
    val r = i64x2.new(117, -9223372036854775791)
    check(r == transmute(msaAddviD(transmute(a), 17)))
}

public fun testMsaAndV() {
    val a = u8x16.new(100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE)
    val b = u8x16.new(4, 3, 2, 100, 4, 3, 2, 100, 4, 3, 2, 100, 4, 3, 2, 100)
    val r = u8x16.new(4, 3, 0, 100, 4, 3, 0, 100, 4, 3, 0, 100, 4, 3, 0, 100)
    check(r == transmute(msaAndV(transmute(a), transmute(b))))
}

public fun testMsaAndiB() {
    val a = u8x16.new(100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE, 100, UByte.MAX_VALUE)
    val r = u8x16.new(4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5)
    check(r == transmute(msaAndiB(transmute(a), 5)))
}

public fun testMsaAsubSB() {
    val a = i8x16.new(-1, -2, -3, -4, -1, -2, -3, -4, -1, -2, -3, -4, -1, -2, -3, -4)
    val b = i8x16.new(-6, -7, -8, -9, -6, -7, -8, -9, -6, -7, -8, -9, -6, -7, -8, -9)
    val r = i8x16.new(5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5)
    check(r == transmute(msaAsubSB(transmute(a), transmute(b))))
}

public fun testMsaAsubSH() {
    val a = i16x8.new(-1, -2, -3, -4, -1, -2, -3, -4)
    val b = i16x8.new(-6, -7, -8, -9, -6, -7, -8, -9)
    val r = i16x8.new(5, 5, 5, 5, 5, 5, 5, 5)
    check(r == transmute(msaAsubSH(transmute(a), transmute(b))))
}

public fun testMsaAsubSW() {
    val a = i32x4.new(-1, -2, -3, -4)
    val b = i32x4.new(-6, -7, -8, -9)
    val r = i32x4.new(5, 5, 5, 5)
    check(r == transmute(msaAsubSW(transmute(a), transmute(b))))
}

public fun testMsaAsubSD() {
    val a = i64x2.new(-1, -2)
    val b = i64x2.new(-6, -7)
    val r = i64x2.new(5, 5)
    check(r == transmute(msaAsubSD(transmute(a), transmute(b))))
}

public fun testMsaAsubUB() {
    val a = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u8x16.new(5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5)
    check(r == transmute(msaAsubUB(transmute(a), transmute(b))))
}

public fun testMsaAsubUH() {
    val a = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val r = u16x8.new(5, 5, 5, 5, 5, 5, 5, 5)
    check(r == transmute(msaAsubUH(transmute(a), transmute(b))))
}

public fun testMsaAsubUW() {
    val a = u32x4.new(1, 2, 3, 4)
    val b = u32x4.new(6, 7, 8, 9)
    val r = u32x4.new(5, 5, 5, 5)
    check(r == transmute(msaAsubUW(transmute(a), transmute(b))))
}

public fun testMsaAsubUD() {
    val a = u64x2.new(1, 2)
    val b = u64x2.new(6, 7)
    val r = u64x2.new(5, 5)
    check(r == transmute(msaAsubUD(transmute(a), transmute(b))))
}

public fun testMsaAveSB() {
    val a = i8x16.new(-1, -2, -3, -4, -1, -2, -3, -4, -1, -2, -3, -4, -1, -2, -3, -4)
    val b = i8x16.new(6, -7, 8, -9, 6, -7, 8, -9, 6, -7, 8, -9, 6, -7, 8, -9)
    val r = i8x16.new(2, -5, 2, -7, 2, -5, 2, -7, 2, -5, 2, -7, 2, -5, 2, -7)
    check(r == transmute(msaAveSB(transmute(a), transmute(b))))
}

public fun testMsaAveSH() {
    val a = i16x8.new(-1, -2, -3, -4, -1, -2, -3, -4)
    val b = i16x8.new(6, -7, 8, -9, 6, -7, 8, -9)
    val r = i16x8.new(2, -5, 2, -7, 2, -5, 2, -7)
    check(r == transmute(msaAveSH(transmute(a), transmute(b))))
}

public fun testMsaAveSW() {
    val a = i32x4.new(-1, -2, -3, -4)
    val b = i32x4.new(6, -7, 8, -9)
    val r = i32x4.new(2, -5, 2, -7)
    check(r == transmute(msaAveSW(transmute(a), transmute(b))))
}

public fun testMsaAveSD() {
    val a = i64x2.new(-1, -2)
    val b = i64x2.new(-6, -7)
    val r = i64x2.new(-4, -5)
    check(r == transmute(msaAveSD(transmute(a), transmute(b))))
}

public fun testMsaAveUB() {
    val a = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u8x16.new(3, 4, 5, 6, 3, 4, 5, 6, 3, 4, 5, 6, 3, 4, 5, 6)
    check(r == transmute(msaAveUB(transmute(a), transmute(b))))
}

public fun testMsaAveUH() {
    val a = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val r = u16x8.new(3, 4, 5, 6, 3, 4, 5, 6)
    check(r == transmute(msaAveUH(transmute(a), transmute(b))))
}

public fun testMsaAveUW() {
    val a = u32x4.new(1, 2, 3, 4)
    val b = u32x4.new(6, 7, 8, 9)
    val r = u32x4.new(3, 4, 5, 6)
    check(r == transmute(msaAveUW(transmute(a), transmute(b))))
}

public fun testMsaAveUD() {
    val a = u64x2.new(1, 2)
    val b = u64x2.new(6, 7)
    val r = u64x2.new(3, 4)
    check(r == transmute(msaAveUD(transmute(a), transmute(b))))
}

public fun testMsaAverSB() {
    val a = i8x16.new(-1, -2, 3, -4, -1, -2, 3, -4, -1, -2, 3, -4, -1, -2, 3, -4)
    val b = i8x16.new(-6, 7, -8, -9, -6, 7, -8, -9, -6, 7, -8, -9, -6, 7, -8, -9)
    val r = i8x16.new(-3, 3, -2, -6, -3, 3, -2, -6, -3, 3, -2, -6, -3, 3, -2, -6)
    check(r == transmute(msaAverSB(transmute(a), transmute(b))))
}

public fun testMsaAverSH() {
    val a = i16x8.new(-1, -2, 3, -4, -1, -2, 3, -4)
    val b = i16x8.new(-6, 7, -8, -9, -6, 7, -8, -9)
    val r = i16x8.new(-3, 3, -2, -6, -3, 3, -2, -6)
    check(r == transmute(msaAverSH(transmute(a), transmute(b))))
}

public fun testMsaAverSW() {
    val a = i32x4.new(-1, -2, 3, -4)
    val b = i32x4.new(-6, 7, -8, -9)
    val r = i32x4.new(-3, 3, -2, -6)
    check(r == transmute(msaAverSW(transmute(a), transmute(b))))
}

public fun testMsaAverSD() {
    val a = i64x2.new(-1, -2)
    val b = i64x2.new(-6, -7)
    val r = i64x2.new(-3, -4)
    check(r == transmute(msaAverSD(transmute(a), transmute(b))))
}

public fun testMsaAverUB() {
    val a = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u8x16.new(4, 5, 6, 7, 4, 5, 6, 7, 4, 5, 6, 7, 4, 5, 6, 7)
    check(r == transmute(msaAverUB(transmute(a), transmute(b))))
}

public fun testMsaAverUH() {
    val a = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val r = u16x8.new(4, 5, 6, 7, 4, 5, 6, 7)
    check(r == transmute(msaAverUH(transmute(a), transmute(b))))
}

public fun testMsaAverUW() {
    val a = u32x4.new(1, 2, 3, 4)
    val b = u32x4.new(6, 7, 8, 9)
    val r = u32x4.new(4, 5, 6, 7)
    check(r == transmute(msaAverUW(transmute(a), transmute(b))))
}

public fun testMsaAverUD() {
    val a = u64x2.new(1, 2)
    val b = u64x2.new(6, 7)
    val r = u64x2.new(4, 5)
    check(r == transmute(msaAverUD(transmute(a), transmute(b))))
}

public fun testMsaBclrB() {
    val a = u8x16.new(255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u8x16.new(191, 27, 54, 1, 191, 27, 54, 1, 191, 27, 54, 1, 191, 27, 54, 1)
    check(r == transmute(msaBclrB(transmute(a), transmute(b))))
}

public fun testMsaBclrH() {
    val a = u16x8.new(255, 155, 55, 1, 255, 155, 55, 1)
    val b = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val r = u16x8.new(191, 27, 55, 1, 191, 27, 55, 1)
    check(r == transmute(msaBclrH(transmute(a), transmute(b))))
}

public fun testMsaBclrW() {
    val a = u32x4.new(255, 155, 55, 1)
    val b = u32x4.new(6, 7, 8, 9)
    val r = u32x4.new(191, 27, 55, 1)
    check(r == transmute(msaBclrW(transmute(a), transmute(b))))
}

public fun testMsaBclrD() {
    val a = u64x2.new(255, 155)
    val b = u64x2.new(6, 7)
    val r = u64x2.new(191, 27)
    check(r == transmute(msaBclrD(transmute(a), transmute(b))))
}

public fun testMsaBclriB() {
    val a = u8x16.new(255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1)
    val r = u8x16.new(247, 147, 55, 1, 247, 147, 55, 1, 247, 147, 55, 1, 247, 147, 55, 1)
    check(r == transmute(msaBclriB(transmute(a), 3)))
}

public fun testMsaBclriH() {
    val a = u16x8.new(2155, 1155, 155, 1, 2155, 1155, 155, 1)
    val r = u16x8.new(107, 1155, 155, 1, 107, 1155, 155, 1)
    check(r == transmute(msaBclriH(transmute(a), 11)))
}

public fun testMsaBclriW() {
    val a = u32x4.new(211111155, 111111155, 11111155, 1)
    val r = u32x4.new(202722547, 102722547, 2722547, 1)
    check(r == transmute(msaBclriW(transmute(a), 23)))
}

public fun testMsaBclriD() {
    val a = u64x2.new(211111111155, 11111111111111155)
    val r = u64x2.new(73672157683, 11110973672157683)
    check(r == transmute(msaBclriD(transmute(a), 37)))
}

public fun testMsaBinslB() {
    val a = u8x16.new(255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val c = u8x16.new(1, 3, 5, 9, 1, 3, 5, 9, 1, 3, 5, 9, 1, 3, 5, 9)
    val r = u8x16.new(63, 11, 11, 1, 63, 11, 11, 1, 63, 11, 11, 1, 63, 11, 11, 1)
    check(r == transmute(msaBinslB(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaBinslH() {
    val a = u16x8.new(32767, 16384, 8192, 4096, 32767, 16384, 8192, 4096)
    val b = u16x8.new(21656, 5273, 7081, 2985, 21656, 5273, 7081, 2985)
    val c = u16x8.new(3, 7, 9, 13, 15, 17, 21, 23)
    val r = u16x8.new(24575, 5120, 7040, 2984, 21656, 0, 6144, 2816)
    check(r == transmute(msaBinslH(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaBinslW() {
    val a = u32x4.new(2147483647, 536870912, 67108864, 8388608)
    val b = u32x4.new(1036372536, 259093134, 78219975, 1119499719)
    val c = u32x4.new(11, 15, 31, 37)
    val r = u32x4.new(1037041663, 259063808, 78219975, 1082130432)
    check(r == transmute(msaBinslW(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaBinslD() {
    val a = u64x2.new(8006399338, 2882303762)
    val b = u64x2.new(9223372036854775805, 536870912)
    val c = u64x2.new(12, 48)
    val r = u64x2.new(9221120245047489898, 536901394)
    check(r == transmute(msaBinslD(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaBinsliB() {
    val a = u8x16.new(UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u8x16.new(7, 7, 11, 9, 7, 7, 11, 9, 7, 7, 11, 9, 7, 7, 11, 9)
    check(r == transmute(msaBinsliB(transmute(a), transmute(b), 5)))
}

public fun testMsaBinsliH() {
    val a = u16x8.new(32767, 16384, 8192, 4096, 32767, 16384, 8192, 4096)
    val b = u16x8.new(21656, 5273, 7081, 2985, 21656, 5273, 7081, 2985)
    val r = u16x8.new(21659, 5272, 7080, 2984, 21659, 5272, 7080, 2984)
    check(r == transmute(msaBinsliH(transmute(a), transmute(b), 13)))
}

public fun testMsaBinsliW() {
    val a = u32x4.new(2147483647, 536870912, 67108864, 8388608)
    val b = u32x4.new(1036372536, 259093134, 78219975, 1119499719)
    val r = u32x4.new(1036386303, 259080192, 78217216, 1119485952)
    check(r == transmute(msaBinsliW(transmute(a), transmute(b), 17)))
}

public fun testMsaBinsliD() {
    val a = u64x2.new(8006399338, 2882303762)
    val b = u64x2.new(9223372036854775805, 536870912)
    val r = u64x2.new(9223372036854773098, 536901394)
    check(r == transmute(msaBinsliD(transmute(a), transmute(b), 48)))
}

public fun testMsaBinsrB() {
    val a = u8x16.new(255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val c = u8x16.new(1, 3, 5, 9, 1, 3, 5, 9, 1, 3, 5, 9, 1, 3, 5, 9)
    val r = u8x16.new(254, 151, 8, 1, 254, 151, 8, 1, 254, 151, 8, 1, 254, 151, 8, 1)
    check(r == transmute(msaBinsrB(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaBinsrH() {
    val a = u16x8.new(32767, 16384, 8192, 4096, 32767, 16384, 8192, 4096)
    val b = u16x8.new(21656, 5273, 7081, 2985, 21656, 5273, 7081, 2985)
    val c = u16x8.new(3, 7, 9, 13, 15, 17, 21, 23)
    val r = u16x8.new(32760, 16537, 9129, 2985, 21656, 16385, 8233, 4265)
    check(r == transmute(msaBinsrH(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaBinsrW() {
    val a = u32x4.new(2147483647, 536870912, 67108864, 8388608)
    val b = u32x4.new(1036372536, 259093134, 78219975, 1119499719)
    val c = u32x4.new(11, 15, 31, 37)
    val r = u32x4.new(2147482168, 536900238, 78219975, 8388615)
    check(r == transmute(msaBinsrW(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaBinsrD() {
    val a = u64x2.new(8006399338, 2882303762)
    val b = u64x2.new(9223372036854775805, 536870912)
    val c = u64x2.new(12, 48)
    val r = u64x2.new(8006402045, 536870912)
    check(r == transmute(msaBinsrD(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaBinsriB() {
    val a = u8x16.new(255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u8x16.new(198, 135, 8, 9, 198, 135, 8, 9, 198, 135, 8, 9, 198, 135, 8, 9)
    check(r == transmute(msaBinsriB(transmute(a), transmute(b), 5)))
}

public fun testMsaBinsriH() {
    val a = u16x8.new(32767, 16384, 8192, 4096, 32767, 16384, 8192, 4096)
    val b = u16x8.new(21656, 5273, 7081, 2985, 21656, 5273, 7081, 2985)
    val r = u16x8.new(21656, 21657, 7081, 2985, 21656, 21657, 7081, 2985)
    check(r == transmute(msaBinsriH(transmute(a), transmute(b), 13)))
}

public fun testMsaBinsriW() {
    val a = u32x4.new(2147483647, 536870912, 67108864, 8388608)
    val b = u32x4.new(1036372536, 259093134, 78219975, 1119499719)
    val r = u32x4.new(2147338808, 536965774, 67209927, 8533447)
    check(r == transmute(msaBinsriW(transmute(a), transmute(b), 17)))
}

public fun testMsaBinsriD() {
    val a = u64x2.new(8006399338, 2882303762)
    val b = u64x2.new(9223372036854775805, 536870912)
    val r = u64x2.new(562949953421309, 536870912)
    check(r == transmute(msaBinsriD(transmute(a), transmute(b), 48)))
}

public fun testMsaBmnzV() {
    val a = u8x16.new(UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val c = u8x16.new(3, 5, 7, 1, 3, 5, 7, 1, 3, 5, 7, 1, 3, 5, 7, 1)
    val r = u8x16.new(254, 159, 48, 1, 254, 159, 48, 1, 254, 159, 48, 1, 254, 159, 48, 1)
    check(r == transmute(msaBmnzV(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaBmnziB() {
    val a = u8x16.new(UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1)
    val b = u8x16.new(1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55)
    val r = u8x16.new(249, 159, 51, 7, 249, 159, 51, 7, 249, 159, 51, 7, 249, 159, 51, 7)
    check(r == transmute(msaBmnziB(transmute(a), transmute(b), 7)))
}

public fun testMsaBmzV() {
    val a = u8x16.new(UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val c = u8x16.new(3, 5, 7, 1, 3, 5, 7, 1, 3, 5, 7, 1, 3, 5, 7, 1)
    val r = u8x16.new(7, 3, 15, 9, 7, 3, 15, 9, 7, 3, 15, 9, 7, 3, 15, 9)
    check(r == transmute(msaBmzV(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaBmziB() {
    val a = u8x16.new(UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1, UByte.MAX_VALUE, 155, 55, 1)
    val b = u8x16.new(1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55)
    val r = u8x16.new(7, 251, 159, 49, 7, 251, 159, 49, 7, 251, 159, 49, 7, 251, 159, 49)
    check(r == transmute(msaBmziB(transmute(a), transmute(b), 7)))
}

public fun testMsaBnegB() {
    val a = u8x16.new(255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u8x16.new(191, 27, 54, 3, 191, 27, 54, 3, 191, 27, 54, 3, 191, 27, 54, 3)
    check(r == transmute(msaBnegB(transmute(a), transmute(b))))
}

public fun testMsaBnegH() {
    val a = u16x8.new(255, 155, 55, 1, 255, 155, 55, 1)
    val b = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val r = u16x8.new(191, 27, 311, 513, 191, 27, 311, 513)
    check(r == transmute(msaBnegH(transmute(a), transmute(b))))
}

public fun testMsaBnegW() {
    val a = u32x4.new(255, 155, 55, 1)
    val b = u32x4.new(6, 7, 8, 9)
    val r = u32x4.new(191, 27, 311, 513)
    check(r == transmute(msaBnegW(transmute(a), transmute(b))))
}

public fun testMsaBnegD() {
    val a = u64x2.new(255, 155)
    val b = u64x2.new(6, 7)
    val r = u64x2.new(191, 27)
    check(r == transmute(msaBnegD(transmute(a), transmute(b))))
}

public fun testMsaBnegiB() {
    val a = u8x16.new(50, 100, 127, UByte.MAX_VALUE, 50, 100, 127, UByte.MAX_VALUE, 50, 100, 127, UByte.MAX_VALUE, 50, 100, 127, UByte.MAX_VALUE)
    val r = u8x16.new(34, 116, 111, 239, 34, 116, 111, 239, 34, 116, 111, 239, 34, 116, 111, 239)
    check(r == transmute(msaBnegiB(transmute(a), 4)))
}

public fun testMsaBnegiH() {
    val a = u16x8.new(32767, 3276, 100, 127, 32767, 3276, 100, 127)
    val r = u16x8.new(30719, 1228, 2148, 2175, 30719, 1228, 2148, 2175)
    check(r == transmute(msaBnegiH(transmute(a), 11)))
}

public fun testMsaBnegiW() {
    val a = u32x4.new(100, 2147483647, 100, 2147483648)
    val r = u32x4.new(16777316, 2130706431, 16777316, 2164260864)
    check(r == transmute(msaBnegiW(transmute(a), 24)))
}

public fun testMsaBnegiD() {
    val a = u64x2.new(100, 9223372036854775808uL)
    val r = u64x2.new(4398046511204, 9223376434901286912uL)
    check(r == transmute(msaBnegiD(transmute(a), 42)))
}

public fun testMsaBnzB() {
    val a = u8x16.new(1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 4, 4, 0, 4)
    val r = 0
    check(r == transmute(msaBnzB(transmute(a))))
}

public fun testMsaBnzH() {
    val a = u16x8.new(32767, 3276, 100, 127, 32767, 0, 100, 127)
    val r = 0
    check(r == transmute(msaBnzH(transmute(a))))
}

public fun testMsaBnzW() {
    val a = u32x4.new(100, 2147483647, 0, 2147483648)
    val r = 0
    check(r == transmute(msaBnzW(transmute(a))))
}

public fun testMsaBnzD() {
    val a = u64x2.new(100, 9223372036854775808uL)
    val r = 1
    check(r == transmute(msaBnzD(transmute(a))))
}

public fun testMsaBnzV() {
    val a = u8x16.new(0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val r = 1
    check(r == transmute(msaBnzV(transmute(a))))
}

public fun testMsaBselV() {
    val a = u8x16.new(3, 5, 7, 1, 3, 5, 7, 1, 3, 5, 7, 1, 3, 5, 7, 1)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val c = u8x16.new(255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1)
    val r = u8x16.new(7, 3, 15, 9, 7, 3, 15, 9, 7, 3, 15, 9, 7, 3, 15, 9)
    check(r == transmute(msaBselV(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaBseliB() {
    val a = u8x16.new(255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u8x16.new(121, 29, 57, 9, 121, 29, 57, 9, 121, 29, 57, 9, 121, 29, 57, 9)
    check(r == transmute(msaBseliB(transmute(a), transmute(b), 121)))
}

public fun testMsaBsetB() {
    val a = u8x16.new(255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u8x16.new(255, 155, 55, 3, 255, 155, 55, 3, 255, 155, 55, 3, 255, 155, 55, 3)
    check(r == transmute(msaBsetB(transmute(a), transmute(b))))
}

public fun testMsaBsetH() {
    val a = u16x8.new(255, 155, 55, 1, 255, 155, 55, 1)
    val b = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val r = u16x8.new(255, 155, 311, 513, 255, 155, 311, 513)
    check(r == transmute(msaBsetH(transmute(a), transmute(b))))
}

public fun testMsaBsetW() {
    val a = u32x4.new(255, 155, 55, 1)
    val b = u32x4.new(6, 7, 8, 9)
    val r = u32x4.new(255, 155, 311, 513)
    check(r == transmute(msaBsetW(transmute(a), transmute(b))))
}

public fun testMsaBsetD() {
    val a = u64x2.new(255, 155)
    val b = u64x2.new(6, 7)
    val r = u64x2.new(255, 155)
    check(r == transmute(msaBsetD(transmute(a), transmute(b))))
}

public fun testMsaBsetiB() {
    val a = u8x16.new(255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1)
    val r = u8x16.new(255, 159, 55, 5, 255, 159, 55, 5, 255, 159, 55, 5, 255, 159, 55, 5)
    check(r == transmute(msaBsetiB(transmute(a), 2)))
}

public fun testMsaBsetiH() {
    val a = u16x8.new(255, 155, 55, 1, 255, 155, 55, 1)
    val r = u16x8.new(255, 159, 55, 5, 255, 159, 55, 5)
    check(r == transmute(msaBsetiH(transmute(a), 2)))
}

public fun testMsaBsetiW() {
    val a = u32x4.new(255, 155, 55, 1)
    val r = u32x4.new(255, 159, 55, 5)
    check(r == transmute(msaBsetiW(transmute(a), 2)))
}

public fun testMsaBsetiD() {
    val a = u64x2.new(255, 155)
    val r = u64x2.new(255, 159)
    check(r == transmute(msaBsetiD(transmute(a), 2)))
}

public fun testMsaBzB() {
    val a = u8x16.new(255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1, 255, 155, 55, 1)
    val r = 0
    check(r == transmute(msaBzB(transmute(a))))
}

public fun testMsaBzH() {
    val a = u16x8.new(0, 0, 0, 0, 0, 0, 0, 0)
    val r = 1
    check(r == transmute(msaBzH(transmute(a))))
}

public fun testMsaBzW() {
    val a = u32x4.new(255, 0, 55, 1)
    val r = 1
    check(r == transmute(msaBzW(transmute(a))))
}

public fun testMsaBzD() {
    val a = u64x2.new(255, 0)
    val r = 1
    check(r == transmute(msaBzD(transmute(a))))
}

public fun testMsaBzV() {
    val a = u8x16.new(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val r = 1
    check(r == transmute(msaBzV(transmute(a))))
}

public fun testMsaCeqB() {
    val a = i8x16.new(-128, 127, 55, 1, -128, 127, 55, 1, -128, 127, 55, 1, -128, 127, 55, 1)
    val b = i8x16.new(-128, 126, 55, 1, -128, 126, 55, 1, -128, 126, 55, 1, -128, 126, 55, 1)
    val r = i8x16.new(-1, 0, -1, -1, -1, 0, -1, -1, -1, 0, -1, -1, -1, 0, -1, -1)
    check(r == transmute(msaCeqB(transmute(a), transmute(b))))
}

public fun testMsaCeqH() {
    val a = i16x8.new(255, 155, 55, 1, 255, 155, 55, 1)
    val b = i16x8.new(255, 155, 56, 1, 255, 155, 56, 1)
    val r = i16x8.new(-1, -1, 0, -1, -1, -1, 0, -1)
    check(r == transmute(msaCeqH(transmute(a), transmute(b))))
}

public fun testMsaCeqW() {
    val a = i32x4.new(255, 155, 55, 1)
    val b = i32x4.new(255, 156, 55, 1)
    val r = i32x4.new(-1, 0, -1, -1)
    check(r == transmute(msaCeqW(transmute(a), transmute(b))))
}

public fun testMsaCeqD() {
    val a = i64x2.new(255, 155)
    val b = i64x2.new(255, 156)
    val r = i64x2.new(-1, 0)
    check(r == transmute(msaCeqD(transmute(a), transmute(b))))
}

public fun testMsaCeqiB() {
    val a = i8x16.new(100, -1, -4, 15, 100, -1, -4, 15, 100, -1, -4, 15, 100, -1, -4, 15)
    val r = i8x16.new(0, 0, -1, 0, 0, 0, -1, 0, 0, 0, -1, 0, 0, 0, -1, 0)
    check(r == transmute(msaCeqiB(transmute(a), -4)))
}

public fun testMsaCeqiH() {
    val a = i16x8.new(32767, 3276, 100, -11, 32767, 3276, 100, -11)
    val r = i16x8.new(0, 0, 0, -1, 0, 0, 0, -1)
    check(r == transmute(msaCeqiH(transmute(a), -11)))
}

public fun testMsaCeqiW() {
    val a = i32x4.new(1, 3, 5, -3)
    val r = i32x4.new(0, 0, -1, 0)
    check(r == transmute(msaCeqiW(transmute(a), 5)))
}

// Note: https://reviews.llvm.org/D59884
// If target type is i64, negative immediate loses the sign
// Test passes if 4294967293 is used instead -3 in vector `a`
// @simdTest(enable = "msa")
// unchecked fn testMsaCeqiD() {
// #[rustfmt.skip]
// let a = i64x2.new(-3, 2);
// #[rustfmt.skip]
// let r = i64x2.new(-1, 0);
// assertEq(r, mem.transmute(__msa_ceqi_d(mem.transmute(a), -3)));
// }
// Can not be tested in user mode
// @simdTest(enable = "msa")
// unchecked fn testMsaCfcmsa() {
// let r = 5;
// assertEq(r, mem.transmute(__msa_cfcmsa(5));
// }
public fun testMsaCleSB() {
    val a = i8x16.new(-128, 127, 55, 2, -128, 127, 55, 2, -128, 127, 55, 2, -128, 127, 55, 2)
    val b = i8x16.new(-128, 126, 55, 1, -128, 126, 55, 1, -128, 126, 55, 1, -128, 126, 55, 1)
    val r = i8x16.new(-1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0)
    check(r == transmute(msaCleSB(transmute(a), transmute(b))))
}

public fun testMsaCleSH() {
    val a = i16x8.new(255, 155, 55, 2, 255, 155, 55, 2)
    val b = i16x8.new(255, 155, 56, 1, 255, 155, 56, 1)
    val r = i16x8.new(-1, -1, -1, 0, -1, -1, -1, 0)
    check(r == transmute(msaCleSH(transmute(a), transmute(b))))
}

public fun testMsaCleSW() {
    val a = i32x4.new(255, 155, 55, 2)
    val b = i32x4.new(255, 156, 55, 1)
    val r = i32x4.new(-1, -1, -1, 0)
    check(r == transmute(msaCleSW(transmute(a), transmute(b))))
}

public fun testMsaCleSD() {
    val a = i64x2.new(255, 155)
    val b = i64x2.new(255, 156)
    val r = i64x2.new(-1, -1)
    check(r == transmute(msaCleSD(transmute(a), transmute(b))))
}

public fun testMsaCleUB() {
    val a = u8x16.new(UByte.MAX_VALUE, 127, 55, 2, UByte.MAX_VALUE, 127, 55, 2, UByte.MAX_VALUE, 127, 55, 2, UByte.MAX_VALUE, 127, 55, 2)
    val b = u8x16.new(UByte.MAX_VALUE, 126, 55, 1, UByte.MAX_VALUE, 126, 55, 1, UByte.MAX_VALUE, 126, 55, 1, UByte.MAX_VALUE, 126, 55, 1)
    val r = i8x16.new(-1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0)
    check(r == transmute(msaCleUB(transmute(a), transmute(b))))
}

public fun testMsaCleUH() {
    val a = u16x8.new(UShort.MAX_VALUE, 155, 55, 2, UShort.MAX_VALUE, 155, 55, 2)
    val b = u16x8.new(UShort.MAX_VALUE, 155, 56, 1, UShort.MAX_VALUE, 155, 56, 1)
    val r = i16x8.new(-1, -1, -1, 0, -1, -1, -1, 0)
    check(r == transmute(msaCleUH(transmute(a), transmute(b))))
}

public fun testMsaCleUW() {
    val a = u32x4.new(UInt.MAX_VALUE, 155, 55, 2)
    val b = u32x4.new(UInt.MAX_VALUE, 156, 55, 1)
    val r = i32x4.new(-1, -1, -1, 0)
    check(r == transmute(msaCleUW(transmute(a), transmute(b))))
}

public fun testMsaCleUD() {
    val a = u64x2.new(ULong.MAX_VALUE, 155)
    val b = u64x2.new(ULong.MAX_VALUE, 156)
    val r = i64x2.new(-1, -1)
    check(r == transmute(msaCleUD(transmute(a), transmute(b))))
}

public fun testMsaCleiSB() {
    val a = i8x16.new(-2, -127, 100, -127, -2, -127, 100, -127, -2, -127, 100, -127, -2, -127, 100, -127)
    val r = i8x16.new(-1, -1, 0, -1, -1, -1, 0, -1, -1, -1, 0, -1, -1, -1, 0, -1)
    check(r == transmute(msaCleiSB(transmute(a), -2)))
}

public fun testMsaCleiSH() {
    val a = i16x8.new(32767, 3276, 10, -1, 32767, 3276, 10, -1)
    val r = i16x8.new(0, 0, 0, -1, 0, 0, 0, -1)
    check(r == transmute(msaCleiSH(transmute(a), -1)))
}

public fun testMsaCleiSW() {
    val a = i32x4.new(100, 2147483647, 6, 2147483647)
    val r = i32x4.new(0, 0, -1, 0)
    check(r == transmute(msaCleiSW(transmute(a), 6)))
}

// Note: https://reviews.llvm.org/D59884
// If target type is i64, negative immediate loses the sign
// -3 is represented as 4294967293
// @simdTest(enable = "msa")
// unchecked fn testMsaCleiSD() {
// #[rustfmt.skip]
// let a = i64x2.new(-3, 11);
// #[rustfmt.skip]
// let r = i64x2.new(-1, 0);
// assertEq(r, mem.transmute(__msa_clei_s_d(mem.transmute(a), -3)));
// }
public fun testMsaCleiUB() {
    val a = u8x16.new(2, 127, 100, 127, 2, 127, 100, 127, 2, 127, 100, 127, 2, 127, 100, 127)
    val r = i8x16.new(-1, 0, 0, 0, -1, 0, 0, 0, -1, 0, 0, 0, -1, 0, 0, 0)
    check(r == transmute(msaCleiUB(transmute(a), 25)))
}

public fun testMsaCleiUH() {
    val a = u16x8.new(1, 26, 15, 36, 1, 26, 15, 36)
    val r = i16x8.new(-1, 0, -1, 0, -1, 0, -1, 0)
    check(r == transmute(msaCleiUH(transmute(a), 25)))
}

public fun testMsaCleiUW() {
    val a = u32x4.new(25, 32, 25, 32)
    val r = i32x4.new(-1, 0, -1, 0)
    check(r == transmute(msaCleiUW(transmute(a), 31)))
}

public fun testMsaCleiUD() {
    val a = u64x2.new(10, 26)
    val r = i64x2.new(-1, 0)
    check(r == transmute(msaCleiUD(transmute(a), 25)))
}

public fun testMsaCltSB() {
    val a = i8x16.new(-128, 127, 55, 2, -128, 127, 55, 2, -128, 127, 55, 2, -128, 127, 55, 2)
    val b = i8x16.new(-127, 126, 56, 1, -127, 126, 56, 1, -127, 126, 56, 1, -127, 126, 56, 1)
    val r = i8x16.new(-1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0, -1, 0)
    check(r == transmute(msaCltSB(transmute(a), transmute(b))))
}

public fun testMsaCltSH() {
    val a = i16x8.new(-255, 155, 55, 2, -255, 155, 55, 2)
    val b = i16x8.new(255, 156, 56, 1, 255, 156, 56, 1)
    val r = i16x8.new(-1, -1, -1, 0, -1, -1, -1, 0)
    check(r == transmute(msaCltSH(transmute(a), transmute(b))))
}

public fun testMsaCltSW() {
    val a = i32x4.new(-255, 155, 55, 2)
    val b = i32x4.new(255, 156, 55, 1)
    val r = i32x4.new(-1, -1, 0, 0)
    check(r == transmute(msaCltSW(transmute(a), transmute(b))))
}

public fun testMsaCltSD() {
    val a = i64x2.new(-255, 155)
    val b = i64x2.new(255, 156)
    val r = i64x2.new(-1, -1)
    check(r == transmute(msaCltSD(transmute(a), transmute(b))))
}

public fun testMsaCltUB() {
    val a = u8x16.new(128, 127, 55, 2, 128, 127, 55, 2, 128, 127, 55, 2, 128, 127, 55, 2)
    val b = u8x16.new(127, 126, 56, 1, 127, 126, 56, 1, 127, 126, 56, 1, 127, 126, 56, 1)
    val r = i8x16.new(0, 0, -1, 0, 0, 0, -1, 0, 0, 0, -1, 0, 0, 0, -1, 0)
    check(r == transmute(msaCltUB(transmute(a), transmute(b))))
}

public fun testMsaCltUH() {
    val a = u16x8.new(255, 155, 55, 2, 255, 155, 55, 2)
    val b = u16x8.new(255, 156, 56, 1, 255, 156, 56, 1)
    val r = i16x8.new(0, -1, -1, 0, 0, -1, -1, 0)
    check(r == transmute(msaCltUH(transmute(a), transmute(b))))
}

public fun testMsaCltUW() {
    val a = u32x4.new(255, 155, 55, 2)
    val b = u32x4.new(255, 156, 55, 1)
    val r = i32x4.new(0, -1, 0, 0)
    check(r == transmute(msaCltUW(transmute(a), transmute(b))))
}

public fun testMsaCltUD() {
    val a = u64x2.new(255, 155)
    val b = u64x2.new(255, 156)
    val r = i64x2.new(0, -1)
    check(r == transmute(msaCltUD(transmute(a), transmute(b))))
}

public fun testMsaCltiSB() {
    val a = i8x16.new(2, -127, -5, 127, 2, -127, -5, 127, 2, -127, -5, 127, 2, -127, -5, 127)
    val r = i8x16.new(0, -1, 0, 0, 0, -1, 0, 0, 0, -1, 0, 0, 0, -1, 0, 0)
    check(r == transmute(msaCltiSB(transmute(a), -5)))
}

public fun testMsaCltiSH() {
    val a = i16x8.new(-1024, 3276, 15, 127, -1024, 3276, 15, 127)
    val r = i16x8.new(-1, 0, 0, 0, -1, 0, 0, 0)
    check(r == transmute(msaCltiSH(transmute(a), 15)))
}

public fun testMsaCltiSW() {
    val a = i32x4.new(-15, 2147483647, -15, 2147483647)
    val r = i32x4.new(-1, 0, -1, 0)
    check(r == transmute(msaCltiSW(transmute(a), -10)))
}

// Note: https://reviews.llvm.org/D59884
// If target type is i64, negative immediate loses the sign
// -3 is represented as 4294967293
// @simdTest(enable = "msa")
// unchecked fn testMsaCltiSD() {
// #[rustfmt.skip]
// let a = i64x2.new(-5, -2);
// #[rustfmt.skip]
// let r = i64x2.new(-1, 0);
// assertEq(r, mem.transmute(__msa_clti_s_d(mem.transmute(a), -3)));
// }
public fun testMsaCltiUB() {
    val a = u8x16.new(2, 127, 49, 127, 2, 127, 49, 127, 2, 127, 49, 127, 2, 127, 49, 127)
    val r = i8x16.new(-1, 0, 0, 0, -1, 0, 0, 0, -1, 0, 0, 0, -1, 0, 0, 0)
    check(r == transmute(msaCltiUB(transmute(a), 3)))
}

public fun testMsaCltiUH() {
    val a = u16x8.new(327, 3276, 100, 127, 327, 3276, 100, 127)
    val r = i16x8.new(0, 0, 0, 0, 0, 0, 0, 0)
    check(r == transmute(msaCltiUH(transmute(a), 30)))
}

public fun testMsaCltiUW() {
    val a = u32x4.new(100, 2147483647, 100, 2147483647)
    val r = i32x4.new(0, 0, 0, 0)
    check(r == transmute(msaCltiUW(transmute(a), 10)))
}

public fun testMsaCltiUD() {
    val a = u64x2.new(1, 9223372036854775807)
    val r = i64x2.new(-1, 0)
    check(r == transmute(msaCltiUD(transmute(a), 10)))
}

public fun testMsaCopySB() {
    val a = i8x16.new(-100, 127, 4, 127, -100, 127, 4, 127, -100, 127, 4, 127, -100, 127, 4, 127)
    val r = -100
    check(r == transmute(msaCopySB(transmute(a), 12)))
}

public fun testMsaCopySH() {
    val a = i16x8.new(32767, 3276, 100, 11, 32767, 3276, 100, 11)
    val r = 32767
    check(r == transmute(msaCopySH(transmute(a), 4)))
}

public fun testMsaCopySW() {
    val a = i32x4.new(100, 2147483647, 5, -2147483647)
    val r = 2147483647
    check(r == transmute(msaCopySW(transmute(a), 1)))
}

public fun testMsaCopySD() {
    val a = i64x2.new(3, 9223372036854775807)
    val r = 9223372036854775807.toLong()
    check(r == transmute(msaCopySD(transmute(a), 1L)))
}

public fun testMsaCopyUB() {
    val a = i8x16.new(100, 127, 4, 127, 100, 127, 4, 127, 100, 127, 4, 127, 100, 127, 4, 127)
    val r = 100.toUInt()
    check(r == transmute(msaCopyUB(transmute(a), 12u)))
}

public fun testMsaCopyUH() {
    val a = i16x8.new(32767, 3276, 100, 11, 32767, 3276, 100, 11)
    val r = 32767.toUInt()
    check(r == transmute(msaCopyUH(transmute(a), 4u)))
}

public fun testMsaCopyUW() {
    val a = i32x4.new(100, 2147483647, 5, 2147483647)
    val r = 2147483647.toUInt()
    check(r == transmute(msaCopyUW(transmute(a), 1u)))
}

public fun testMsaCopyUD() {
    val a = i64x2.new(3, Long.MAX_VALUE)
    val r = 9223372036854775807.toULong()
    check(r == transmute(msaCopyUD(transmute(a), 1uL)))
}

// Can not be tested in user mode
// @simdTest(enable = "msa")
// unchecked fn testMsaCtcmsa() {
// }
public fun testMsaDivSB() {
    val a = i8x16.new(-6, -7, -8, -9, -6, -7, -8, -9, -6, -7, -8, -9, -6, -7, -8, -9)
    val b = i8x16.new(-1, -2, -3, -4, -1, -2, -3, -4, -1, -2, -3, -4, -1, -2, -3, -4)
    val r = i8x16.new(6, 3, 2, 2, 6, 3, 2, 2, 6, 3, 2, 2, 6, 3, 2, 2)
    check(r == transmute(msaDivSB(transmute(a), transmute(b))))
}

public fun testMsaDivSH() {
    val a = i16x8.new(-6, -7, -8, -9, 6, 7, 8, 9)
    val b = i16x8.new(-1, -2, -3, -4, -1, -2, -3, -4)
    val r = i16x8.new(6, 3, 2, 2, -6, -3, -2, -2)
    check(r == transmute(msaDivSH(transmute(a), transmute(b))))
}

public fun testMsaDivSW() {
    val a = i32x4.new(-6, -7, 8, 9)
    val b = i32x4.new(-1, -2, -3, -4)
    val r = i32x4.new(6, 3, -2, -2)
    check(r == transmute(msaDivSW(transmute(a), transmute(b))))
}

public fun testMsaDivSD() {
    val a = i64x2.new(-6, 7)
    val b = i64x2.new(-1, -2)
    val r = i64x2.new(6, -3)
    check(r == transmute(msaDivSD(transmute(a), transmute(b))))
}

public fun testMsaDivUB() {
    val a = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val b = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val r = u8x16.new(6, 3, 2, 2, 6, 3, 2, 2, 6, 3, 2, 2, 6, 3, 2, 2)
    check(r == transmute(msaDivUB(transmute(a), transmute(b))))
}

public fun testMsaDivUH() {
    val a = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val b = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val r = u16x8.new(6, 3, 2, 2, 6, 3, 2, 2)
    check(r == transmute(msaDivUH(transmute(a), transmute(b))))
}

public fun testMsaDivUW() {
    val a = u32x4.new(6, 7, 8, 9)
    val b = u32x4.new(1, 2, 3, 4)
    val r = u32x4.new(6, 3, 2, 2)
    check(r == transmute(msaDivUW(transmute(a), transmute(b))))
}

public fun testMsaDivUD() {
    val a = u64x2.new(6, 7)
    val b = u64x2.new(1, 2)
    val r = u64x2.new(6, 3)
    check(r == transmute(msaDivUD(transmute(a), transmute(b))))
}

public fun testMsaDotpSH() {
    val a = i8x16.new(-1, -2, -3, 4, -1, -2, -3, -4, -1, -2, -3, 4, -1, -2, -3, -4)
    val b = i8x16.new(-6, -7, -8, -9, -6, -7, -8, -9, -6, -7, -8, -9, -6, -7, -8, -9)
    val r = i16x8.new(20, -12, 20, 60, 20, -12, 20, 60)
    check(r == transmute(msaDotpSH(transmute(a), transmute(b))))
}

public fun testMsaDotpSW() {
    val a = i16x8.new(-1, -2, -3, -4, -1, -2, -3, 4)
    val b = i16x8.new(-6, -7, -8, -9, -6, -7, -8, -9)
    val r = i32x4.new(20, 60, 20, -12)
    check(r == transmute(msaDotpSW(transmute(a), transmute(b))))
}

public fun testMsaDotpSD() {
    val a = i32x4.new(-1, -2, -3, 4)
    val b = i32x4.new(-6, -7, -8, -9)
    val r = i64x2.new(20, -12)
    check(r == transmute(msaDotpSD(transmute(a), transmute(b))))
}

public fun testMsaDotpUH() {
    val a = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u16x8.new(20, 60, 20, 60, 20, 60, 20, 60)
    check(r == transmute(msaDotpUH(transmute(a), transmute(b))))
}

public fun testMsaDotpUW() {
    val a = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val r = u32x4.new(20, 60, 20, 60)
    check(r == transmute(msaDotpUW(transmute(a), transmute(b))))
}

public fun testMsaDotpUD() {
    val a = u32x4.new(1, 2, 3, 4)
    val b = u32x4.new(6, 7, 8, 9)
    val r = u64x2.new(20, 60)
    check(r == transmute(msaDotpUD(transmute(a), transmute(b))))
}

public fun testMsaDpaddSH() {
    val a = i16x8.new(-1, -2, -3, -4, -1, -2, -3, 4)
    val b = i8x16.new(-1, -2, -3, 4, -1, -2, -3, -4, -1, -2, -3, 4, -1, -2, -3, -4)
    val c = i8x16.new(-6, -7, -8, -9, -6, -7, -8, -9, -6, -7, -8, -9, -6, -7, -8, -9)
    val r = i16x8.new(19, -14, 17, 56, 19, -14, 17, 64)
    check(r == transmute(msaDpaddSH(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaDpaddSW() {
    val a = i32x4.new(-1, -2, -3, -4)
    val b = i16x8.new(-1, -2, -3, 4, -1, -2, -3, -4)
    val c = i16x8.new(-6, -7, -8, -9, -6, -7, -8, -9)
    val r = i32x4.new(19, -14, 17, 56)
    check(r == transmute(msaDpaddSW(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaDpaddSD() {
    val a = i64x2.new(-1, -2)
    val b = i32x4.new(-1, -2, -3, 4)
    val c = i32x4.new(-6, -7, -8, -9)
    val r = i64x2.new(19, -14)
    check(r == transmute(msaDpaddSD(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaDpaddUH() {
    val a = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val c = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u16x8.new(21, 62, 23, 64, 21, 62, 23, 64)
    check(r == transmute(msaDpaddUH(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaDpaddUW() {
    val a = u32x4.new(1, 2, 3, 4)
    val b = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val c = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val r = u32x4.new(21, 62, 23, 64)
    check(r == transmute(msaDpaddUW(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaDpaddUD() {
    val a = u64x2.new(1, 2)
    val b = u32x4.new(1, 2, 3, 4)
    val c = u32x4.new(6, 7, 8, 9)
    val r = u64x2.new(21, 62)
    check(r == transmute(msaDpaddUD(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaDpsubSH() {
    val a = i16x8.new(-1, -2, -3, -4, -1, -2, -3, 4)
    val b = i8x16.new(-1, -2, -3, 4, -1, -2, -3, -4, -1, -2, -3, 4, -1, -2, -3, -4)
    val c = i8x16.new(-6, -7, -8, -9, -6, -7, -8, -9, -6, -7, -8, -9, -6, -7, -8, -9)
    val r = i16x8.new(-21, 10, -23, -64, -21, 10, -23, -56)
    check(r == transmute(msaDpsubSH(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaDpsubSW() {
    val a = i32x4.new(-1, -2, -3, -4)
    val b = i16x8.new(-1, -2, -3, 4, -1, -2, -3, -4)
    val c = i16x8.new(-6, -7, -8, -9, -6, -7, -8, -9)
    val r = i32x4.new(-21, 10, -23, -64)
    check(r == transmute(msaDpsubSW(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaDpsubSD() {
    val a = i64x2.new(-1, -2)
    val b = i32x4.new(-1, -2, -3, 4)
    val c = i32x4.new(-6, -7, -8, -9)
    val r = i64x2.new(-21, 10)
    check(r == transmute(msaDpsubSD(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaDpsubUH() {
    val a = i16x8.new(1, -2, 3, -4, -1, 2, -3, 4)
    val b = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val c = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = i16x8.new(-19, -62, -17, -64, -21, -58, -23, -56)
    check(r == transmute(msaDpsubUH(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaDpsubUW() {
    val a = i32x4.new(1, -2, 3, -4)
    val b = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val c = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val r = i32x4.new(-19, -62, -17, -64)
    check(r == transmute(msaDpsubUW(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaDpsubUD() {
    val a = i64x2.new(1, -2)
    val b = u32x4.new(1, 2, 3, 4)
    val c = u32x4.new(6, 7, 8, 9)
    val r = i64x2.new(-19, -62)
    check(r == transmute(msaDpsubUD(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaFaddW() {
    val a = f32x4.new(1.1, -2.2, 3.3, -4.4)
    val b = f32x4.new(4.4, -3.3, 2.2, -1.1)
    val r = f32x4.new(5.5, -5.5, 5.5, -5.5)
    check(r == transmute(msaFaddW(transmute(a), transmute(b))))
}

public fun testMsaFaddD() {
    val a = f64x2.new(1.1, -2.2)
    val b = f64x2.new(4.4, -3.3)
    val r = f64x2.new(5.5, -5.5)
    check(r == transmute(msaFaddD(transmute(a), transmute(b))))
}

// Only observed beahiour should be SIGFPE signal
// Can not be tested
public fun testMsaFcafW() {
    val a = f32x4.new(1.1, -2.2, 3.3, -4.4)
    val b = f32x4.new(0.0, -1.2, 3.3, Float.NaN)
    val r = i32x4.new(0, 0, 0, 0)
    check(r == transmute(msaFcafW(transmute(a), transmute(b))))
}

// Only observed beahiour should be SIGFPE signal
// Can not be tested
public fun testMsaFcafD() {
    val a = f64x2.new(1.1, -2.2)
    val b = f64x2.new(-2.2, 1.1)
    val r = i64x2.new(0, 0)
    check(r == transmute(msaFcafD(transmute(a), transmute(b))))
}

public fun testMsaFceqW() {
    val a = f32x4.new(1.1, -2.2, 3.3, Float.NaN)
    val b = f32x4.new(-4.4, -2.2, 3.3, Float.NaN)
    val r = i32x4.new(0, -1, -1, 0)
    check(r == transmute(msaFceqW(transmute(a), transmute(b))))
}

public fun testMsaFceqD() {
    val a = f64x2.new(1.1, -2.2)
    val b = f64x2.new(1.1, 1.1)
    val r = i64x2.new(-1, 0)
    check(r == transmute(msaFceqD(transmute(a), transmute(b))))
}

public fun testMsaFclassW() {
    val a = f32x4.new(1.1, -2.2, 3.3, Float.NaN)
    val r = i32x4.new(128, 8, 128, 2)
    check(r == transmute(msaFclassW(transmute(a))))
}

public fun testMsaFclassD() {
    val a = f64x2.new(1.1, -2.2)
    val r = i64x2.new(128, 8)
    check(r == transmute(msaFclassD(transmute(a))))
}

public fun testMsaFcleW() {
    val a = f32x4.new(1.1, -2.2, 3.3, Float.NaN)
    val b = f32x4.new(-4.4, -1.2, 3.3, Float.NaN)
    val r = i32x4.new(0, -1, -1, 0)
    check(r == transmute(msaFcleW(transmute(a), transmute(b))))
}

public fun testMsaFcleD() {
    val a = f64x2.new(1.1, -2.2)
    val b = f64x2.new(1.1, 1.1)
    val r = i64x2.new(-1, -1)
    check(r == transmute(msaFcleD(transmute(a), transmute(b))))
}

public fun testMsaFcltW() {
    val a = f32x4.new(1.1, -2.2, 3.3, Float.NaN)
    val b = f32x4.new(-4.4, -1.2, 3.3, Float.NaN)
    val r = i32x4.new(0, -1, 0, 0)
    check(r == transmute(msaFcltW(transmute(a), transmute(b))))
}

public fun testMsaFcltD() {
    val a = f64x2.new(1.1, -2.2)
    val b = f64x2.new(1.1, 1.1)
    val r = i64x2.new(0, -1)
    check(r == transmute(msaFcltD(transmute(a), transmute(b))))
}

public fun testMsaFcneW() {
    val a = f32x4.new(1.1, -2.2, 3.3, Float.NaN)
    val b = f32x4.new(-4.4, -1.2, 3.3, Float.NaN)
    val r = i32x4.new(-1, -1, 0, 0)
    check(r == transmute(msaFcneW(transmute(a), transmute(b))))
}

public fun testMsaFcneD() {
    val a = f64x2.new(1.1, -2.2)
    val b = f64x2.new(1.1, 1.1)
    val r = i64x2.new(0, -1)
    check(r == transmute(msaFcneD(transmute(a), transmute(b))))
}

public fun testMsaFcorW() {
    val a = f32x4.new(1.1, -2.2, 3.3, Float.NaN)
    val b = f32x4.new(Float.NaN, -1.2, 3.3, Float.NaN)
    val r = i32x4.new(0, -1, -1, 0)
    check(r == transmute(msaFcorW(transmute(a), transmute(b))))
}

public fun testMsaFcorD() {
    val a = f64x2.new(1.1, Double.NaN)
    val b = f64x2.new(1.1, 1.1)
    val r = i64x2.new(-1, 0)
    check(r == transmute(msaFcorD(transmute(a), transmute(b))))
}

public fun testMsaFcueqW() {
    val a = f32x4.new(1.1, -2.2, 3.3, Float.NaN)
    val b = f32x4.new(Float.NaN, -1.2, 3.3, Float.NaN)
    val r = i32x4.new(-1, 0, -1, -1)
    check(r == transmute(msaFcueqW(transmute(a), transmute(b))))
}

public fun testMsaFcueqD() {
    val a = f64x2.new(1.1, Double.NaN)
    val b = f64x2.new(1.1, 1.1)
    val r = i64x2.new(-1, -1)
    check(r == transmute(msaFcueqD(transmute(a), transmute(b))))
}

public fun testMsaFculeW() {
    val a = f32x4.new(1.1, -2.2, 3.3, Float.NaN)
    val b = f32x4.new(Float.NaN, -1.2, 3.3, Float.NaN)
    val r = i32x4.new(-1, -1, -1, -1)
    check(r == transmute(msaFculeW(transmute(a), transmute(b))))
}

public fun testMsaFculeD() {
    val a = f64x2.new(1.1, Double.NaN)
    val b = f64x2.new(1.1, 1.1)
    val r = i64x2.new(-1, -1)
    check(r == transmute(msaFculeD(transmute(a), transmute(b))))
}

public fun testMsaFcultW() {
    val a = f32x4.new(1.1, -2.2, 3.3, Float.NaN)
    val b = f32x4.new(Float.NaN, -1.2, 3.3, Float.NaN)
    val r = i32x4.new(-1, -1, 0, -1)
    check(r == transmute(msaFcultW(transmute(a), transmute(b))))
}

public fun testMsaFcultD() {
    val a = f64x2.new(1.1, Double.NaN)
    val b = f64x2.new(1.1, 1.1)
    val r = i64x2.new(0, -1)
    check(r == transmute(msaFcultD(transmute(a), transmute(b))))
}

public fun testMsaFcunW() {
    val a = f32x4.new(1.1, -2.2, 3.3, Float.NaN)
    val b = f32x4.new(Float.NaN, -1.2, 3.3, Float.NaN)
    val r = i32x4.new(-1, 0, 0, -1)
    check(r == transmute(msaFcunW(transmute(a), transmute(b))))
}

public fun testMsaFcunD() {
    val a = f64x2.new(1.1, Double.NaN)
    val b = f64x2.new(1.1, 1.1)
    val r = i64x2.new(0, -1)
    check(r == transmute(msaFcunD(transmute(a), transmute(b))))
}

public fun testMsaFcuneW() {
    val a = f32x4.new(1.1, -2.2, 3.3, Float.NaN)
    val b = f32x4.new(Float.NaN, -1.2, 3.3, Float.NaN)
    val r = i32x4.new(-1, -1, 0, -1)
    check(r == transmute(msaFcuneW(transmute(a), transmute(b))))
}

public fun testMsaFcuneD() {
    val a = f64x2.new(1.1, Double.NaN)
    val b = f64x2.new(1.1, 1.1)
    val r = i64x2.new(0, -1)
    check(r == transmute(msaFcuneD(transmute(a), transmute(b))))
}

public fun testMsaFdivW() {
    val a = f32x4.new(5.25, -20.2, 333.333, -425.0)
    val b = f32x4.new(4.0, -2.1, 11.11, 8.2)
    val r = f32x4.new(1.3125, 9.619048, 30.002972, -51.82927)
    check(r == transmute(msaFdivW(transmute(a), transmute(b))))
}

public fun testMsaFdivD() {
    val a = f64x2.new(1111.11, -222222.2)
    val b = f64x2.new(-4.85, 3.33)
    val r = f64x2.new(-229.09484536082473, -66733.3933933934)
    check(r == transmute(msaFdivD(transmute(a), transmute(b))))
}

// // Note: 16-bit floats
// @simdTest(enable = "msa")
// unchecked fn testMsaFexdoH() {
// #[rustfmt.skip]
// let a = f32x4.new(20.5, 2.3, 4.5, 5.4);
// #[rustfmt.skip]
// let b = f32x4.new(1.1, 1.0, 1.0, 1.0);
// let r = i16x8.new(1, 9, 30, 51, 1, 9, 30, 51);
//
// assertEq(r, mem.transmute(__msa_fexdo_h(mem.transmute(a), mem.transmute(b))));
// }
public fun testMsaFexdoW() {
    val a = f64x2.new(2000005.5, 2.3)
    val b = f64x2.new(1235689784512.1, 2147483649998.5)
    val r = f32x4.new(1235689800000.0, 2147483600000.0, 2000005.5, 2.3)
    check(r == transmute(msaFexdoW(transmute(a), transmute(b))))
}

public fun testMsaFexp2W() {
    val a = f32x4.new(1.1, -2.2, 3.3, -4.4)
    val b = i32x4.new(4, -3, 2, 1)
    val r = f32x4.new(17.6, -0.275, 13.2, -8.8)
    check(r == transmute(msaFexp2W(transmute(a), transmute(b))))
}

public fun testMsaFexp2D() {
    val a = f64x2.new(1.1, -2.2)
    val b = i64x2.new(-4, 3)
    val r = f64x2.new(0.06875, -17.6)
    check(r == transmute(msaFexp2D(transmute(a), transmute(b))))
}

// Note: 16-bit floats
// @simdTest(enable = "msa")
// unchecked fn testMsaFexuplW() {
// #[rustfmt.skip]
// let a = f16x8(1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5);
// #[rustfmt.skip]
// let r = f32x4.new(5.5, 6.5, 7.5, 8.5);
// assertEq(r, mem.transmute(__msa_fexupl_w(mem.transmute(a))));
// }
public fun testMsaFexuplD() {
    val a = f32x4.new(5.5, 6.5, 7.5, 8.5)
    val r = f64x2.new(7.5, 8.5)
    check(r == transmute(msaFexuplD(transmute(a))))
}

// Note: 16-bit floats
// @simdTest(enable = "msa")
// unchecked fn testMsaFexuprW() {
// #[rustfmt.skip]
// let a = f16x8(1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5);
// #[rustfmt.skip]
// let r = f32x4.new(1.5, 2.5, 3.5, 4.5);
// assertEq(r, mem.transmute(__msa_fexupr_w(mem.transmute(a))));
// }
public fun testMsaFexuprD() {
    val a = f32x4.new(5.5, 6.5, 7.5, 8.5)
    val r = f64x2.new(5.5, 6.5)
    check(r == transmute(msaFexuprD(transmute(a))))
}

public fun testMsaFfintSW() {
    val a = i32x4.new(-1, 2, -3, 4)
    val r = f32x4.new(-1.0, 2.0, -3.0, 4.0)
    check(r == transmute(msaFfintSW(transmute(a))))
}

public fun testMsaFfintSD() {
    val a = i64x2.new(-1, 2)
    val r = f64x2.new(-1.0, 2.0)
    check(r == transmute(msaFfintSD(transmute(a))))
}

public fun testMsaFfintUW() {
    val a = u32x4.new(1, 2, 3, 4)
    val r = f32x4.new(1.0, 2.0, 3.0, 4.0)
    check(r == transmute(msaFfintUW(transmute(a))))
}

public fun testMsaFfintUD() {
    val a = u64x2.new(1, 2)
    val r = f64x2.new(1.0, 2.0)
    check(r == transmute(msaFfintUD(transmute(a))))
}

public fun testMsaFfqlW() {
    val a = i16x8.new(11, 25, 33, 47, 11, 25, 33, 47)
    val r = f32x4.new(0.00033569336, 0.00076293945, 0.0010070801, 0.0014343262)
    check(r == transmute(msaFfqlW(transmute(a))))
}

public fun testMsaFfqlD() {
    val a = i32x4.new(1111, 2222, 3333, 4444)
    val r = f64x2.new(0.000001552049070596695, 0.0000020693987607955933)
    check(r == transmute(msaFfqlD(transmute(a))))
}

public fun testMsaFfqrW() {
    val a = i16x8.new(12, 26, 34, 48, 11, 25, 33, 47)
    val r = f32x4.new(0.00036621094, 0.00079345703, 0.0010375977, 0.0014648438)
    check(r == transmute(msaFfqrW(transmute(a))))
}

public fun testMsaFfqrD() {
    val a = i32x4.new(1111, 2555, 3333, 475)
    val r = f64x2.new(0.0000005173496901988983, 0.0000011897645890712738)
    check(r == transmute(msaFfqrD(transmute(a))))
}

public fun testMsaFillB() {
    val r = i8x16.new(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)
    check(r == transmute(msaFillB(2)))
}

public fun testMsaFillH() {
    val r = i16x8.new(2, 2, 2, 2, 2, 2, 2, 2)
    check(r == transmute(msaFillH(2)))
}

public fun testMsaFillW() {
    val r = i32x4.new(2, 2, 2, 2)
    check(r == transmute(msaFillW(2)))
}

public fun testMsaFillD() {
    val r = i64x2.new(2, 2)
    check(r == transmute(msaFillD(2L)))
}

public fun testMsaFlog2W() {
    val a = f32x4.new(8.0, 16.0, 32.0, 64.0)
    val r = f32x4.new(3.0, 4.0, 5.0, 6.0)
    check(r == transmute(msaFlog2W(transmute(a))))
}

public fun testMsaFlog2D() {
    val a = f64x2.new(8.0, 16.0)
    val r = f64x2.new(3.0, 4.0)
    check(r == transmute(msaFlog2D(transmute(a))))
}

public fun testMsaFmaddW() {
    val a = f32x4.new(1.0, 2.0, 3.0, 4.0)
    val b = f32x4.new(5.0, 6.0, 7.0, 8.0)
    val c = f32x4.new(9.0, 10.0, 11.0, 12.0)
    val r = f32x4.new(46.0, 62.0, 80.0, 100.0)
    check(r == transmute(msaFmaddW(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaFmaddD() {
    val a = f64x2.new(1.0, 2.0)
    val b = f64x2.new(3.0, 4.0)
    val c = f64x2.new(5.0, 6.0)
    val r = f64x2.new(16.0, 26.0)
    check(r == transmute(msaFmaddD(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaFmaxW() {
    val a = f32x4.new(1.0, -6.0, 7.0, 8.0)
    val b = f32x4.new(5.0, -2.0, 3.0, 4.0)
    val r = f32x4.new(5.0, -2.0, 7.0, 8.0)
    check(r == transmute(msaFmaxW(transmute(a), transmute(b))))
}

public fun testMsaFmaxD() {
    val a = f64x2.new(1.0, 4.0)
    val b = f64x2.new(3.0, 2.0)
    val r = f64x2.new(3.0, 4.0)
    check(r == transmute(msaFmaxD(transmute(a), transmute(b))))
}

public fun testMsaFmaxAW() {
    val a = f32x4.new(1.0, -6.0, -7.0, -8.0)
    val b = f32x4.new(5.0, -2.0, 3.0, 4.0)
    val r = f32x4.new(5.0, -6.0, -7.0, -8.0)
    check(r == transmute(msaFmaxAW(transmute(a), transmute(b))))
}

public fun testMsaFmaxAD() {
    val a = f64x2.new(1.0, -4.0)
    val b = f64x2.new(3.0, 2.0)
    val r = f64x2.new(3.0, -4.0)
    check(r == transmute(msaFmaxAD(transmute(a), transmute(b))))
}

public fun testMsaFminW() {
    val a = f32x4.new(1.0, -6.0, 7.0, 8.0)
    val b = f32x4.new(5.0, -2.0, 3.0, 4.0)
    val r = f32x4.new(1.0, -6.0, 3.0, 4.0)
    check(r == transmute(msaFminW(transmute(a), transmute(b))))
}

public fun testMsaFminD() {
    val a = f64x2.new(1.0, 4.0)
    val b = f64x2.new(3.0, 2.0)
    val r = f64x2.new(1.0, 2.0)
    check(r == transmute(msaFminD(transmute(a), transmute(b))))
}

public fun testMsaFminAW() {
    val a = f32x4.new(1.0, -6.0, -7.0, -8.0)
    val b = f32x4.new(5.0, -2.0, 3.0, 4.0)
    val r = f32x4.new(1.0, -2.0, 3.0, 4.0)
    check(r == transmute(msaFminAW(transmute(a), transmute(b))))
}

public fun testMsaFminAD() {
    val a = f64x2.new(1.0, -4.0)
    val b = f64x2.new(3.0, 2.0)
    val r = f64x2.new(1.0, 2.0)
    check(r == transmute(msaFminAD(transmute(a), transmute(b))))
}

public fun testMsaFmsubW() {
    val a = f32x4.new(1.0, 2.0, 3.0, 4.0)
    val b = f32x4.new(5.0, 6.0, 7.0, 8.0)
    val c = f32x4.new(9.0, 10.0, 11.0, 12.0)
    val r = f32x4.new(-44.0, -58.0, -74.0, -92.0)
    check(r == transmute(msaFmsubW(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaFmsubD() {
    val a = f64x2.new(1.0, 2.0)
    val b = f64x2.new(3.0, 4.0)
    val c = f64x2.new(5.0, 6.0)
    val r = f64x2.new(-14.0, -22.0)
    check(r == transmute(msaFmsubD(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaFmulW() {
    val a = f32x4.new(1.1, -2.2, 3.3, 4.4)
    val b = f32x4.new(4.4, 3.3, 2.2, -1.1)
    val r = f32x4.new(4.84, -7.26, 7.26, -4.84)
    check(r == transmute(msaFmulW(transmute(a), transmute(b))))
}

public fun testMsaFmulD() {
    val a = f64x2.new(1.1, -2.2)
    val b = f64x2.new(4.0, -3.3)
    val r = f64x2.new(4.4, 7.26)
    check(r == transmute(msaFmulD(transmute(a), transmute(b))))
}

public fun testMsaFrintW() {
    val a = f32x4.new(2.6, -2.7, 1.3, -1.7)
    val r = f32x4.new(3.0, -3.0, 1.0, -2.0)
    check(r == transmute(msaFrintW(transmute(a))))
}

public fun testMsaFrintD() {
    val a = f64x2.new(2.6, 1.3)
    val r = f64x2.new(3.0, 1.0)
    check(r == transmute(msaFrintD(transmute(a))))
}

public fun testMsaFrcpW() {
    val a = f32x4.new(2.6, -2.7, 1.3, -1.7)
    val r = f32x4.new(0.3846154, -0.37037036, 0.7692308, -0.58823526)
    check(r == transmute(msaFrcpW(transmute(a))))
}

public fun testMsaFrcpD() {
    val a = f64x2.new(2.6, 1.3)
    val r = f64x2.new(0.3846153846153846, 0.7692307692307692)
    check(r == transmute(msaFrcpD(transmute(a))))
}

public fun testMsaFrsqrtW() {
    val a = f32x4.new(2.6, 2.7, 1.3, 1.7)
    val r = f32x4.new(0.6201737, 0.6085806, 0.87705797, 0.766965)
    check(r == transmute(msaFrsqrtW(transmute(a))))
}

public fun testMsaFrsqrtD() {
    val a = f64x2.new(2.6, 1.3)
    val r = f64x2.new(0.6201736729460422, 0.8770580193070292)
    check(r == transmute(msaFrsqrtD(transmute(a))))
}

public fun testMsaFsafW() {
    val a = f32x4.new(-5.5, 5.5, 5.5, 5.5)
    val b = f32x4.new(-5.5, 5.5, 5.5, 5.5)
    val r = i32x4.new(0, 0, 0, 0)
    check(r == transmute(msaFsafW(transmute(a), transmute(b))))
}

public fun testMsaFsafD() {
    val a = f64x2.new(-125.5, 5.5)
    val b = f64x2.new(125.5, 3.3)
    val r = i64x2.new(0, 0)
    check(r == transmute(msaFsafD(transmute(a), transmute(b))))
}

public fun testMsaFseqW() {
    val a = f32x4.new(-5.5, -3.3, Float.NaN, Float.NaN)
    val b = f32x4.new(5.5, -3.3, Float.NaN, 1.1)
    val r = i32x4.new(0, -1, 0, 0)
    check(r == transmute(msaFseqW(transmute(a), transmute(b))))
}

public fun testMsaFseqD() {
    val a = f64x2.new(-125.5, 5.5)
    val b = f64x2.new(125.5, 5.5)
    val r = i64x2.new(0, -1)
    check(r == transmute(msaFseqD(transmute(a), transmute(b))))
}

public fun testMsaFsleW() {
    val a = f32x4.new(5.5, 5.5, 5.5, Float.NaN)
    val b = f32x4.new(-5.5, 3.3, 5.5, Float.NaN)
    val r = i32x4.new(0, 0, -1, 0)
    check(r == transmute(msaFsleW(transmute(a), transmute(b))))
}

public fun testMsaFsleD() {
    val a = f64x2.new(-125.5, 5.5)
    val b = f64x2.new(125.5, 3.3)
    val r = i64x2.new(-1, 0)
    check(r == transmute(msaFsleD(transmute(a), transmute(b))))
}

public fun testMsaFsltW() {
    val a = f32x4.new(-5.5, 5.5, 5.5, 5.5)
    val b = f32x4.new(5.5, 3.3, 5.5, 1.1)
    val r = i32x4.new(-1, 0, 0, 0)
    check(r == transmute(msaFsltW(transmute(a), transmute(b))))
}

public fun testMsaFsltD() {
    val a = f64x2.new(-125.5, 5.5)
    val b = f64x2.new(125.5, 3.3)
    val r = i64x2.new(-1, 0)
    check(r == transmute(msaFsltD(transmute(a), transmute(b))))
}

public fun testMsaFsneW() {
    val a = f32x4.new(-5.5, 5.5, 5.5, 5.5)
    val b = f32x4.new(5.5, 3.3, 5.5, 1.1)
    val r = i32x4.new(-1, -1, 0, -1)
    check(r == transmute(msaFsneW(transmute(a), transmute(b))))
}

public fun testMsaFsneD() {
    val a = f64x2.new(-125.5, 5.5)
    val b = f64x2.new(125.5, 5.5)
    val r = i64x2.new(-1, 0)
    check(r == transmute(msaFsneD(transmute(a), transmute(b))))
}

public fun testMsaFsorW() {
    val a = f32x4.new(-5.5, Float.NaN, 5.5, 5.5)
    val b = f32x4.new(5.5, 3.3, 5.5, 1.1)
    val r = i32x4.new(-1, 0, -1, -1)
    check(r == transmute(msaFsorW(transmute(a), transmute(b))))
}

public fun testMsaFsorD() {
    val a = f64x2.new(-125.5, 5.5)
    val b = f64x2.new(125.5, Double.NaN)
    val r = i64x2.new(-1, 0)
    check(r == transmute(msaFsorD(transmute(a), transmute(b))))
}

public fun testMsaFsqrtW() {
    val a = f32x4.new(9.0, 81.0, 1089.0, 10000.0)
    val r = f32x4.new(3.0, 9.0, 33.0, 100.0)
    check(r == transmute(msaFsqrtW(transmute(a))))
}

public fun testMsaFsqrtD() {
    val a = f64x2.new(81.0, 10000.0)
    val r = f64x2.new(9.0, 100.0)
    check(r == transmute(msaFsqrtD(transmute(a))))
}

public fun testMsaFsubW() {
    val a = f32x4.new(5.5, 6.5, 7.5, 8.5)
    val b = f32x4.new(1.25, 1.75, 2.25, 2.75)
    val r = f32x4.new(4.25, 4.75, 5.25, 5.75)
    check(r == transmute(msaFsubW(transmute(a), transmute(b))))
}

public fun testMsaFsubD() {
    val a = f64x2.new(555.5, 55.5)
    val b = f64x2.new(4.25, 3.25)
    val r = f64x2.new(551.25, 52.25)
    check(r == transmute(msaFsubD(transmute(a), transmute(b))))
}

public fun testMsaFsueqW() {
    val a = f32x4.new(5.5, Float.NaN, 5.5, 5.5)
    val b = f32x4.new(5.5, 5.5, -5.5, 5.5)
    val r = i32x4.new(-1, -1, 0, -1)
    check(r == transmute(msaFsueqW(transmute(a), transmute(b))))
}

public fun testMsaFsueqD() {
    val a = f64x2.new(-5.5, 5.5)
    val b = f64x2.new(5.5, Double.NaN)
    val r = i64x2.new(0, -1)
    check(r == transmute(msaFsueqD(transmute(a), transmute(b))))
}

public fun testMsaFsuleW() {
    val a = f32x4.new(5.7, 5.8, 5.9, Float.NaN)
    val b = f32x4.new(5.6, 5.9, 5.9, Float.NaN)
    val r = i32x4.new(0, -1, -1, -1)
    check(r == transmute(msaFsuleW(transmute(a), transmute(b))))
}

public fun testMsaFsuleD() {
    val a = f64x2.new(5.5, 5.5)
    val b = f64x2.new(5.5, 5.5)
    val r = i64x2.new(-1, -1)
    check(r == transmute(msaFsuleD(transmute(a), transmute(b))))
}

public fun testMsaFsultW() {
    val a = f32x4.new(5.5, 5.5, 5.5, 5.5)
    val b = f32x4.new(5.6, Float.NaN, 2.2, 1.1)
    val r = i32x4.new(-1, -1, 0, 0)
    check(r == transmute(msaFsultW(transmute(a), transmute(b))))
}

public fun testMsaFsultD() {
    val a = f64x2.new(5.5, Double.NaN)
    val b = f64x2.new(4.4, 3.3)
    val r = i64x2.new(0, -1)
    check(r == transmute(msaFsultD(transmute(a), transmute(b))))
}

public fun testMsaFsunW() {
    val a = f32x4.new(5.5, 5.5, Float.NaN, 5.5)
    val b = f32x4.new(4.4, 3.3, 2.2, Float.NaN)
    val r = i32x4.new(0, 0, -1, -1)
    check(r == transmute(msaFsunW(transmute(a), transmute(b))))
}

public fun testMsaFsunD() {
    val a = f64x2.new(5.5, Double.NaN)
    val b = f64x2.new(4.4, 3.3)
    val r = i64x2.new(0, -1)
    check(r == transmute(msaFsunD(transmute(a), transmute(b))))
}

public fun testMsaFsuneW() {
    val a = f32x4.new(5.5, 5.5, Float.NaN, 5.5)
    val b = f32x4.new(4.4, 3.3, 2.2, 5.5)
    val r = i32x4.new(-1, -1, -1, 0)
    check(r == transmute(msaFsuneW(transmute(a), transmute(b))))
}

public fun testMsaFsuneD() {
    val a = f64x2.new(5.5, Double.NaN)
    val b = f64x2.new(5.5, 3.3)
    val r = i64x2.new(0, -1)
    check(r == transmute(msaFsuneD(transmute(a), transmute(b))))
}

public fun testMsaFtintSW() {
    val a = f32x4.new(-5.5, 75.6, -1000.7, 1219.3)
    val r = i32x4.new(-6, 76, -1001, 1219)
    check(r == transmute(msaFtintSW(transmute(a))))
}

public fun testMsaFtintSD() {
    val a = f64x2.new(-5.5, 25656.4)
    val r = i64x2.new(-6, 25656)
    check(r == transmute(msaFtintSD(transmute(a))))
}

public fun testMsaFtintUW() {
    val a = f32x4.new(-5.5, 75.6, -1000.7, 1219.3)
    val r = u32x4.new(0, 76, 0, 1219)
    check(r == transmute(msaFtintUW(transmute(a))))
}

public fun testMsaFtintUD() {
    val a = f64x2.new(5.5, -25656.4)
    val r = u64x2.new(6, 0)
    check(r == transmute(msaFtintUD(transmute(a))))
}

public fun testMsaFtqH() {
    val a = f32x4.new(0.00001, 0.0002, 0.00001, -0.0002)
    val b = f32x4.new(0.0001, -0.002, 0.0001, 0.002)
    val r = i16x8.new(3, -66, 3, 66, 0, 7, 0, -7)
    check(r == transmute(msaFtqH(transmute(a), transmute(b))))
}

public fun testMsaFtqW() {
    val a = f64x2.new(0.00001, -0.0002)
    val b = f64x2.new(0.00000045, 0.000015)
    val r = i32x4.new(966, 32212, 21475, -429497)
    check(r == transmute(msaFtqW(transmute(a), transmute(b))))
}

public fun testMsaFtruncSW() {
    val a = f32x4.new(-5.5, 75.6, -1000.7, 1219.3)
    val r = i32x4.new(-5, 75, -1000, 1219)
    check(r == transmute(msaFtruncSW(transmute(a))))
}

public fun testMsaFtruncSD() {
    val a = f64x2.new(-5.5, 25656.4)
    val r = i64x2.new(-5, 25656)
    check(r == transmute(msaFtruncSD(transmute(a))))
}

public fun testMsaFtruncUW() {
    val a = f32x4.new(-5.5, 75.6, -1000.7, 1219.3)
    val r = u32x4.new(0, 75, 0, 1219)
    check(r == transmute(msaFtruncUW(transmute(a))))
}

public fun testMsaFtruncUD() {
    val a = f64x2.new(5.5, -25656.4)
    val r = u64x2.new(5, 0)
    check(r == transmute(msaFtruncUD(transmute(a))))
}

public fun testMsaHaddSH() {
    val a = i8x16.new(1, 2, 3, 4, -1, -2, -3, -4, 1, 2, 3, 4, -1, -2, -3, -4)
    val b = i8x16.new(4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1)
    val r = i16x8.new(6, 6, 2, -2, 6, 6, 2, -2)
    check(r == transmute(msaHaddSH(transmute(a), transmute(b))))
}

public fun testMsaHaddSW() {
    val a = i16x8.new(1, 2, 3, 4, -1, -2, -3, -4)
    val b = i16x8.new(4, 3, 2, 1, 4, 3, 2, 1)
    val r = i32x4.new(6, 6, 2, -2)
    check(r == transmute(msaHaddSW(transmute(a), transmute(b))))
}

public fun testMsaHaddSD() {
    val a = i32x4.new(1, -2, 3, -4)
    val b = i32x4.new(4, 3, 2, 1)
    val r = i64x2.new(2, -2)
    check(r == transmute(msaHaddSD(transmute(a), transmute(b))))
}

public fun testMsaHaddUH() {
    val a = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = u8x16.new(4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1)
    val r = u16x8.new(6, 6, 6, 6, 6, 6, 6, 6)
    check(r == transmute(msaHaddUH(transmute(a), transmute(b))))
}

public fun testMsaHaddUW() {
    val a = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = u16x8.new(4, 3, 2, 1, 4, 3, 2, 1)
    val r = u32x4.new(6, 6, 6, 6)
    check(r == transmute(msaHaddUW(transmute(a), transmute(b))))
}

public fun testMsaHaddUD() {
    val a = u32x4.new(1, 2, 3, 4)
    val b = u32x4.new(4, 3, 2, 1)
    val r = u64x2.new(6, 6)
    check(r == transmute(msaHaddUD(transmute(a), transmute(b))))
}

public fun testMsaHsubSH() {
    val a = i8x16.new(1, 2, 3, 4, -1, -2, -3, -4, 1, 2, 3, 4, -1, -2, -3, -4)
    val b = i8x16.new(4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1)
    val r = i16x8.new(-2, 2, -6, -6, -2, 2, -6, -6)
    check(r == transmute(msaHsubSH(transmute(a), transmute(b))))
}

public fun testMsaHsubSW() {
    val a = i16x8.new(1, 2, 3, 4, -1, -2, -3, -4)
    val b = i16x8.new(4, 3, 2, 1, 4, 3, 2, 1)
    val r = i32x4.new(-2, 2, -6, -6)
    check(r == transmute(msaHsubSW(transmute(a), transmute(b))))
}

public fun testMsaHsubSD() {
    val a = i32x4.new(1, -2, 3, -4)
    val b = i32x4.new(4, 3, 2, 1)
    val r = i64x2.new(-6, -6)
    check(r == transmute(msaHsubSD(transmute(a), transmute(b))))
}

public fun testMsaHsubUH() {
    val a = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = u8x16.new(4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1)
    val r = i16x8.new(-2, 2, -2, 2, -2, 2, -2, 2)
    check(r == transmute(msaHsubUH(transmute(a), transmute(b))))
}

public fun testMsaHsubUW() {
    val a = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = u16x8.new(4, 3, 2, 1, 4, 3, 2, 1)
    val r = i32x4.new(-2, 2, -2, 2)
    check(r == transmute(msaHsubUW(transmute(a), transmute(b))))
}

public fun testMsaHsubUD() {
    val a = u32x4.new(1, 2, 3, 4)
    val b = u32x4.new(4, 3, 2, 1)
    val r = i64x2.new(-2, 2)
    check(r == transmute(msaHsubUD(transmute(a), transmute(b))))
}

public fun testMsaIlvevB() {
    val a = i8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = i8x16.new(4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1)
    val r = i8x16.new(4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3)
    check(r == transmute(msaIlvevB(transmute(a), transmute(b))))
}

public fun testMsaIlvevH() {
    val a = i16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = i16x8.new(4, 3, 2, 1, 4, 3, 2, 1)
    val r = i16x8.new(4, 1, 2, 3, 4, 1, 2, 3)
    check(r == transmute(msaIlvevH(transmute(a), transmute(b))))
}

public fun testMsaIlvevW() {
    val a = i32x4.new(1, 2, 3, 4)
    val b = i32x4.new(4, 3, 2, 1)
    val r = i32x4.new(4, 1, 2, 3)
    check(r == transmute(msaIlvevW(transmute(a), transmute(b))))
}

public fun testMsaIlvevD() {
    val a = i64x2.new(1, 2)
    val b = i64x2.new(4, 3)
    val r = i64x2.new(4, 1)
    check(r == transmute(msaIlvevD(transmute(a), transmute(b))))
}

public fun testMsaIlvlB() {
    val a = i8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = i8x16.new(16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    val r = i8x16.new(8, 9, 7, 10, 6, 11, 5, 12, 4, 13, 3, 14, 2, 15, 1, 16)
    check(r == transmute(msaIlvlB(transmute(a), transmute(b))))
}

public fun testMsaIlvlH() {
    val a = i16x8.new(1, 2, 3, 4, 5, 6, 7, 8)
    val b = i16x8.new(8, 7, 6, 5, 4, 3, 2, 1)
    val r = i16x8.new(4, 5, 3, 6, 2, 7, 1, 8)
    check(r == transmute(msaIlvlH(transmute(a), transmute(b))))
}

public fun testMsaIlvlW() {
    val a = i32x4.new(1, 2, 3, 4)
    val b = i32x4.new(4, 3, 2, 1)
    val r = i32x4.new(2, 3, 1, 4)
    check(r == transmute(msaIlvlW(transmute(a), transmute(b))))
}

public fun testMsaIlvlD() {
    val a = i64x2.new(1, 2)
    val b = i64x2.new(2, 1)
    val r = i64x2.new(1, 2)
    check(r == transmute(msaIlvlD(transmute(a), transmute(b))))
}

public fun testMsaIlvodB() {
    val a = i8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = i8x16.new(16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    val r = i8x16.new(15, 2, 13, 4, 11, 6, 9, 8, 7, 10, 5, 12, 3, 14, 1, 16)
    check(r == transmute(msaIlvodB(transmute(a), transmute(b))))
}

public fun testMsaIlvodH() {
    val a = i16x8.new(1, 2, 3, 4, 5, 6, 7, 8)
    val b = i16x8.new(8, 7, 6, 5, 4, 3, 2, 1)
    val r = i16x8.new(7, 2, 5, 4, 3, 6, 1, 8)
    check(r == transmute(msaIlvodH(transmute(a), transmute(b))))
}

public fun testMsaIlvodW() {
    val a = i32x4.new(1, 2, 3, 4)
    val b = i32x4.new(4, 3, 2, 1)
    val r = i32x4.new(3, 2, 1, 4)
    check(r == transmute(msaIlvodW(transmute(a), transmute(b))))
}

public fun testMsaIlvodD() {
    val a = i64x2.new(1, 2)
    val b = i64x2.new(2, 1)
    val r = i64x2.new(1, 2)
    check(r == transmute(msaIlvodD(transmute(a), transmute(b))))
}

public fun testMsaIlvrB() {
    val a = i8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = i8x16.new(16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    val r = i8x16.new(16, 1, 15, 2, 14, 3, 13, 4, 12, 5, 11, 6, 10, 7, 9, 8)
    check(r == transmute(msaIlvrB(transmute(a), transmute(b))))
}

public fun testMsaIlvrH() {
    val a = i16x8.new(1, 2, 3, 4, 5, 6, 7, 8)
    val b = i16x8.new(8, 7, 6, 5, 4, 3, 2, 1)
    val r = i16x8.new(8, 1, 7, 2, 6, 3, 5, 4)
    check(r == transmute(msaIlvrH(transmute(a), transmute(b))))
}

public fun testMsaIlvrW() {
    val a = i32x4.new(1, 2, 3, 4)
    val b = i32x4.new(4, 3, 2, 1)
    val r = i32x4.new(4, 1, 3, 2)
    check(r == transmute(msaIlvrW(transmute(a), transmute(b))))
}

public fun testMsaIlvrD() {
    val a = i64x2.new(1, 2)
    val b = i64x2.new(2, 1)
    val r = i64x2.new(2, 1)
    check(r == transmute(msaIlvrD(transmute(a), transmute(b))))
}

public fun testMsaInsertB() {
    val a = i8x16.new(-100, 127, 4, 127, -100, 127, 4, 127, -100, 127, 4, 127, -100, 127, 4, 127)
    val r = i8x16.new(-100, 127, 4, 127, -100, 127, 4, 127, -100, 127, 4, 127, 5, 127, 4, 127)
    check(r == transmute(msaInsertB(transmute(a), 5, 12)))
}

public fun testMsaInsertH() {
    val a = i16x8.new(32767, 3276, 100, 11, 32767, 3276, 100, 11)
    val r = i16x8.new(32767, 3276, 100, 11, 5, 3276, 100, 11)
    check(r == transmute(msaInsertH(transmute(a), 5, 4)))
}

public fun testMsaInsertW() {
    val a = i32x4.new(100, 2147483647, 5, -2147483647)
    val r = i32x4.new(100, 7, 5, -2147483647)
    check(r == transmute(msaInsertW(transmute(a), 7, 1)))
}

public fun testMsaInsertD() {
    val a = i64x2.new(3, Long.MAX_VALUE)
    val r = i64x2.new(3, 100)
    check(r == transmute(msaInsertD(transmute(a), 100L, 1)))
}

public fun testMsaInsveB() {
    val a = i8x16.new(-100, Byte.MAX_VALUE, 4, Byte.MAX_VALUE, -100, Byte.MAX_VALUE, 4, Byte.MAX_VALUE, -100, Byte.MAX_VALUE, 4, Byte.MAX_VALUE, -100, Byte.MAX_VALUE, 4, Byte.MAX_VALUE)
    val b = i8x16.new(5, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val r = i8x16.new(-100, 127, 4, 127, -100, 127, 4, 127, -100, 127, 4, 127, 5, 127, 4, 127)
    check(r == transmute(msaInsveB(transmute(a), transmute(b), 12)))
}

public fun testMsaInsveH() {
    val a = i16x8.new(Short.MAX_VALUE, 3276, 100, 11, Short.MAX_VALUE, 3276, 100, 11)
    val b = i16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val r = i16x8.new(32767, 3276, 100, 11, 1, 3276, 100, 11)
    check(r == transmute(msaInsveH(transmute(a), transmute(b), 4)))
}

public fun testMsaInsveW() {
    val a = i32x4.new(100, 2147483647, 5, -2147483647)
    val b = i32x4.new(1, 2, 3, 4)
    val r = i32x4.new(100, 2147483647, 5, 1)
    check(r == transmute(msaInsveW(transmute(a), transmute(b), 3)))
}

public fun testMsaInsveD() {
    val a = i64x2.new(3, Long.MAX_VALUE)
    val b = i64x2.new(1, 2)
    val r = i64x2.new(3, 1)
    check(r == transmute(msaInsveD(transmute(a), transmute(b), 1)))
}

public fun testMsaLdB() {
    val a: ByteArray = byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val p = (mutPtrFromRustCast(a[4])).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    val r = i8x16.new(13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28)
    check(r == transmute(msaLdB(p, 9)))
}

public fun testMsaLdH() {
    val a: ShortArray = shortArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val p = (mutPtrFromRustCast(a[4])).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    val r = i16x8.new(3, 4, 5, 6, 7, 8, 9, 10)
    check(r == transmute(msaLdH(p, -2)))
}

public fun testMsaLdW() {
    val a: IntArray = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7)
    val p = (mutPtrFromRustCast(a[3])).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    val r = i32x4.new(2, 3, 4, 5)
    check(r == transmute(msaLdW(p, -4)))
}

public fun testMsaLdD() {
    val a: LongArray = longArrayOf(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L)
    val p = (mutPtrFromRustCast(a[4])).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    val r = i64x2.new(0, 1)
    check(r == transmute(msaLdD(p, -32)))
}

public fun testMsaLdiB() {
    val r = i8x16.new(-20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20)
    check(r == transmute(msaLdiB(-20)))
}

public fun testMsaLdiH() {
    val r = i16x8.new(255, 255, 255, 255, 255, 255, 255, 255)
    check(r == transmute(msaLdiH(255)))
}

public fun testMsaLdiW() {
    val r = i32x4.new(-509, -509, -509, -509)
    check(r == transmute(msaLdiW(-509)))
}

// Note: https://reviews.llvm.org/D59884
// If target type is i64, negative immediate loses the sign
// Test passes if 4294967185 is used instead -111 in vector `r`
// @simdTest(enable = "msa")
// unchecked fn testMsaLdiD() {
// let r = i64x2.new(-111, -111);
// assertEq(r, mem.transmute(__msa_ldi_d(-111)));
// }
public fun testMsaMaddQH() {
    val a = i16x8.new(Short.MAX_VALUE, 1024, Short.MIN_VALUE, -1024, 1, 2, 3, 4)
    val b = i16x8.new(1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024)
    val c = i16x8.new(Short.MAX_VALUE, Short.MAX_VALUE, 1, -1, 33, 66, 99, 132)
    val r = i16x8.new(32767, 2047, -32768, -1025, 2, 4, 6, 8)
    check(r == transmute(msaMaddQH(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaMaddQW() {
    val a = i32x4.new(Int.MAX_VALUE, Int.MIN_VALUE, 1, 2)
    val b = i32x4.new(102401, 102401, 102401, 102401)
    val c = i32x4.new(10240, 20480, 30720, 40960)
    val r = i32x4.new(2147483647, -2147483648, 2, 3)
    check(r == transmute(msaMaddQW(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaMaddrQH() {
    val a = i16x8.new(32767, 1024, -32768, -1024, 1, 2, 3, 4)
    val b = i16x8.new(1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024)
    val c = i16x8.new(32767, 32767, 32767, 32767, 33, 66, 99, 132)
    val r = i16x8.new(32767, 2048, -31744, 0, 2, 4, 6, 8)
    check(r == transmute(msaMaddrQH(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaMaddrQW() {
    val a = i32x4.new(Int.MAX_VALUE, Int.MIN_VALUE, 1, 2)
    val b = i32x4.new(102401, 102401, 102401, 102401)
    val c = i32x4.new(10240, 20480, 30720, 40960)
    val r = i32x4.new(2147483647, -2147483647, 2, 4)
    check(r == transmute(msaMaddrQW(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaMaddvB() {
    val a = i8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = i8x16.new(5, 6, 7, 8, 5, 6, 7, 8, 5, 6, 7, 8, 5, 6, 7, 8)
    val c = i8x16.new(9, 10, 11, 12, 9, 10, 11, 12, 9, 10, 11, 12, 9, 10, 11, 12)
    val r = i8x16.new(46, 62, 80, 100, 46, 62, 80, 100, 46, 62, 80, 100, 46, 62, 80, 100)
    check(r == transmute(msaMaddvB(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaMaddvH() {
    val a = i16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = i16x8.new(5, 6, 7, 8, 5, 6, 7, 8)
    val c = i16x8.new(9, 10, 11, 12, 9, 10, 11, 12)
    val r = i16x8.new(46, 62, 80, 100, 46, 62, 80, 100)
    check(r == transmute(msaMaddvH(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaMaddvW() {
    val a = i32x4.new(1, 2, 1, 2)
    val b = i32x4.new(3, 4, 3, 4)
    val c = i32x4.new(5, 6, 5, 6)
    val r = i32x4.new(16, 26, 16, 26)
    check(r == transmute(msaMaddvW(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaMaddvD() {
    val a = i64x2.new(1, 2)
    val b = i64x2.new(3, 4)
    val c = i64x2.new(5, 6)
    val r = i64x2.new(16, 26)
    check(r == transmute(msaMaddvD(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaMaxAB() {
    val a = i8x16.new(1, 2, 3, 4, -1, -2, -3, -4, 1, 2, 3, 4, -1, -2, -3, -4)
    val b = i8x16.new(-6, -7, -8, -9, 6, 7, 8, 9, -6, -7, -8, -9, 6, 7, 8, 9)
    val r = i8x16.new(-6, -7, -8, -9, 6, 7, 8, 9, -6, -7, -8, -9, 6, 7, 8, 9)
    check(r == transmute(msaMaxAB(transmute(a), transmute(b))))
}

public fun testMsaMaxAH() {
    val a = i16x8.new(1, -2, 3, -4, 1, -2, 3, -4)
    val b = i16x8.new(-6, 7, -8, 9, -6, 7, -8, 9)
    val r = i16x8.new(-6, 7, -8, 9, -6, 7, -8, 9)
    check(r == transmute(msaMaxAH(transmute(a), transmute(b))))
}

public fun testMsaMaxAW() {
    val a = i32x4.new(1, -2, 3, -4)
    val b = i32x4.new(6, 7, 8, 9)
    val r = i32x4.new(6, 7, 8, 9)
    check(r == transmute(msaMaxAW(transmute(a), transmute(b))))
}

public fun testMsaMaxAD() {
    val a = i64x2.new(-1, 2)
    val b = i64x2.new(6, -7)
    val r = i64x2.new(6, -7)
    check(r == transmute(msaMaxAD(transmute(a), transmute(b))))
}

public fun testMsaMaxSB() {
    val a = i8x16.new(1, 2, 3, 4, -1, -2, -3, -4, 1, 2, 3, 4, -1, -2, -3, -4)
    val b = i8x16.new(-6, -7, -8, -9, 6, 7, 8, 9, -6, -7, -8, -9, 6, 7, 8, 9)
    val r = i8x16.new(1, 2, 3, 4, 6, 7, 8, 9, 1, 2, 3, 4, 6, 7, 8, 9)
    check(r == transmute(msaMaxSB(transmute(a), transmute(b))))
}

public fun testMsaMaxSH() {
    val a = i16x8.new(1, -2, 3, -4, 1, -2, 3, -4)
    val b = i16x8.new(-6, 7, -8, 9, -6, 7, -8, 9)
    val r = i16x8.new(1, 7, 3, 9, 1, 7, 3, 9)
    check(r == transmute(msaMaxSH(transmute(a), transmute(b))))
}

public fun testMsaMaxSW() {
    val a = i32x4.new(1, -2, 3, -4)
    val b = i32x4.new(6, 7, 8, 9)
    val r = i32x4.new(6, 7, 8, 9)
    check(r == transmute(msaMaxSW(transmute(a), transmute(b))))
}

public fun testMsaMaxSD() {
    val a = i64x2.new(-1, 2)
    val b = i64x2.new(6, -7)
    val r = i64x2.new(6, 2)
    check(r == transmute(msaMaxSD(transmute(a), transmute(b))))
}

public fun testMsaMaxUB() {
    val a = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    check(r == transmute(msaMaxUB(transmute(a), transmute(b))))
}

public fun testMsaMaxUH() {
    val a = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val r = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    check(r == transmute(msaMaxUH(transmute(a), transmute(b))))
}

public fun testMsaMaxUW() {
    val a = u32x4.new(1, 2, 3, 4)
    val b = u32x4.new(6, 7, 8, 9)
    val r = u32x4.new(6, 7, 8, 9)
    check(r == transmute(msaMaxUW(transmute(a), transmute(b))))
}

public fun testMsaMaxUD() {
    val a = u64x2.new(1, 2)
    val b = u64x2.new(6, 7)
    val r = u64x2.new(6, 7)
    check(r == transmute(msaMaxUD(transmute(a), transmute(b))))
}

public fun testMsaMaxiSB() {
    val a = i8x16.new(1, -20, -6, 8, 1, -20, -6, 8, 1, -20, -6, 8, 1, -20, -6, 8)
    val r = i8x16.new(1, -16, -6, 8, 1, -16, -6, 8, 1, -16, -6, 8, 1, -16, -6, 8)
    check(r == transmute(msaMaxiSB(transmute(a), -16)))
}

public fun testMsaMaxiSH() {
    val a = i16x8.new(1, 3, -60, -8, 1, 3, -6, -8)
    val r = i16x8.new(15, 15, 15, 15, 15, 15, 15, 15)
    check(r == transmute(msaMaxiSH(transmute(a), 15)))
}

public fun testMsaMaxiSW() {
    val a = i32x4.new(1, 3, -6, -8)
    val r = i32x4.new(1, 3, -5, -5)
    check(r == transmute(msaMaxiSW(transmute(a), -5)))
}

// Note: https://reviews.llvm.org/D59884
// If target type is i64, negative immediate loses the sign
// Test passes if 4294967293 is used instead -3 in vector `r`
// @simdTest(enable = "msa")
// unchecked fn testMsaMaxiSD() {
// #[rustfmt.skip]
// let a = i64x2.new(1, -8);
// #[rustfmt.skip]
// let r = i64x2.new(-3, -3);
// assertEq(r, mem.transmute(__msa_maxi_s_d(mem.transmute(a), -3)));
// }
public fun testMsaMaxiUB() {
    val a = u8x16.new(1, 3, 6, 8, 1, 3, 6, 8, 1, 3, 6, 8, 1, 3, 6, 8)
    val r = u8x16.new(5, 5, 6, 8, 5, 5, 6, 8, 5, 5, 6, 8, 5, 5, 6, 8)
    check(r == transmute(msaMaxiUB(transmute(a), 5)))
}

public fun testMsaMaxiUH() {
    val a = u16x8.new(1, 3, 6, 8, 1, 3, 6, 8)
    val r = u16x8.new(5, 5, 6, 8, 5, 5, 6, 8)
    check(r == transmute(msaMaxiUH(transmute(a), 5)))
}

public fun testMsaMaxiUW() {
    val a = u32x4.new(1, 3, 6, 8)
    val r = u32x4.new(5, 5, 6, 8)
    check(r == transmute(msaMaxiUW(transmute(a), 5)))
}

public fun testMsaMaxiUD() {
    val a = u64x2.new(1, 8)
    val r = u64x2.new(5, 8)
    check(r == transmute(msaMaxiUD(transmute(a), 5)))
}

public fun testMsaMinAB() {
    val a = i8x16.new(1, 2, 3, 4, -1, -2, -3, -4, 1, 2, 3, 4, -1, -2, -3, -4)
    val b = i8x16.new(-6, -7, -8, -9, 6, 7, 8, 9, -6, -7, -8, -9, 6, 7, 8, 9)
    val r = i8x16.new(1, 2, 3, 4, -1, -2, -3, -4, 1, 2, 3, 4, -1, -2, -3, -4)
    check(r == transmute(msaMinAB(transmute(a), transmute(b))))
}

public fun testMsaMinAH() {
    val a = i16x8.new(1, -2, 3, -4, 1, -2, 3, -4)
    val b = i16x8.new(-6, 7, -8, 9, -6, 7, -8, 9)
    val r = i16x8.new(1, -2, 3, -4, 1, -2, 3, -4)
    check(r == transmute(msaMinAH(transmute(a), transmute(b))))
}

public fun testMsaMinAW() {
    val a = i32x4.new(1, -2, 3, -4)
    val b = i32x4.new(6, 7, 8, 9)
    val r = i32x4.new(1, -2, 3, -4)
    check(r == transmute(msaMinAW(transmute(a), transmute(b))))
}

public fun testMsaMinAD() {
    val a = i64x2.new(-1, 2)
    val b = i64x2.new(6, -7)
    val r = i64x2.new(-1, 2)
    check(r == transmute(msaMinAD(transmute(a), transmute(b))))
}

public fun testMsaMinSB() {
    val a = i8x16.new(1, 2, 3, 4, -1, -2, -3, -4, 1, 2, 3, 4, -1, -2, -3, -4)
    val b = i8x16.new(-6, -7, -8, -9, 6, 7, 8, 9, -6, -7, -8, -9, 6, 7, 8, 9)
    val r = i8x16.new(-6, -7, -8, -9, -1, -2, -3, -4, -6, -7, -8, -9, -1, -2, -3, -4)
    check(r == transmute(msaMinSB(transmute(a), transmute(b))))
}

public fun testMsaMinSH() {
    val a = i16x8.new(1, -2, 3, -4, 1, -2, 3, -4)
    val b = i16x8.new(-6, 7, -8, 9, -6, 7, -8, 9)
    val r = i16x8.new(-6, -2, -8, -4, -6, -2, -8, -4)
    check(r == transmute(msaMinSH(transmute(a), transmute(b))))
}

public fun testMsaMinSW() {
    val a = i32x4.new(1, -2, 3, -4)
    val b = i32x4.new(6, 7, 8, 9)
    val r = i32x4.new(1, -2, 3, -4)
    check(r == transmute(msaMinSW(transmute(a), transmute(b))))
}

public fun testMsaMinSD() {
    val a = i64x2.new(-1, 2)
    val b = i64x2.new(6, -7)
    val r = i64x2.new(-1, -7)
    check(r == transmute(msaMinSD(transmute(a), transmute(b))))
}

public fun testMsaMiniSB() {
    val a = i8x16.new(1, 2, 3, 4, -1, -2, -3, -4, 1, 2, 3, 4, -1, -2, -3, -4)
    val r = i8x16.new(-10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10)
    check(r == transmute(msaMiniSB(transmute(a), -10)))
}

public fun testMsaMiniSH() {
    val a = i16x8.new(1, -2, 3, -4, 1, -2, 3, -4)
    val r = i16x8.new(-3, -3, -3, -4, -3, -3, -3, -4)
    check(r == transmute(msaMiniSH(transmute(a), -3)))
}

public fun testMsaMiniSW() {
    val a = i32x4.new(1, -2, 3, -4)
    val r = i32x4.new(-3, -3, -3, -4)
    check(r == transmute(msaMiniSW(transmute(a), -3)))
}

// Note: https://reviews.llvm.org/D59884
// If target type is i64, negative immediate loses the sign
// -3 is represented as 4294967293
// @simdTest(enable = "msa")
// unchecked fn testMsaMiniSD() {
// #[rustfmt.skip]
// let a = i64x2.new(-3, 2);
// #[rustfmt.skip]
// let r = i64x2.new(-1, -3);
// assertEq(r, mem.transmute(__msa_mini_s_d(mem.transmute(a), -3)));
// }
public fun testMsaMinUB() {
    val a = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    check(r == transmute(msaMinUB(transmute(a), transmute(b))))
}

public fun testMsaMinUH() {
    val a = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val r = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    check(r == transmute(msaMinUH(transmute(a), transmute(b))))
}

public fun testMsaMinUW() {
    val a = u32x4.new(1, 2, 3, 4)
    val b = u32x4.new(6, 7, 8, 9)
    val r = u32x4.new(1, 2, 3, 4)
    check(r == transmute(msaMinUW(transmute(a), transmute(b))))
}

public fun testMsaMinUD() {
    val a = u64x2.new(1, 2)
    val b = u64x2.new(6, 7)
    val r = u64x2.new(1, 2)
    check(r == transmute(msaMinUD(transmute(a), transmute(b))))
}

public fun testMsaMiniUB() {
    val a = u8x16.new(1, 3, 6, 8, 1, 3, 6, 8, 1, 3, 6, 8, 1, 3, 6, 8)
    val r = u8x16.new(1, 3, 5, 5, 1, 3, 5, 5, 1, 3, 5, 5, 1, 3, 5, 5)
    check(r == transmute(msaMiniUB(transmute(a), 5)))
}

public fun testMsaMiniUH() {
    val a = u16x8.new(1, 3, 6, 8, 1, 3, 6, 8)
    val r = u16x8.new(1, 3, 5, 5, 1, 3, 5, 5)
    check(r == transmute(msaMiniUH(transmute(a), 5)))
}

public fun testMsaMiniUW() {
    val a = u32x4.new(1, 3, 6, 8)
    val r = u32x4.new(1, 3, 5, 5)
    check(r == transmute(msaMiniUW(transmute(a), 5)))
}

public fun testMsaMiniUD() {
    val a = u64x2.new(1, 8)
    val r = u64x2.new(1, 5)
    check(r == transmute(msaMiniUD(transmute(a), 5)))
}

public fun testMsaModSB() {
    val a = i8x16.new(-6, -7, -8, -9, 6, 7, 8, 9, -6, -7, -8, -9, 6, 7, 8, 9)
    val b = i8x16.new(1, 2, 3, 4, -1, -2, -3, -4, 1, 2, 3, 4, -1, -2, -3, -4)
    val r = i8x16.new(0, -1, -2, -1, 0, 1, 2, 1, 0, -1, -2, -1, 0, 1, 2, 1)
    check(r == transmute(msaModSB(transmute(a), transmute(b))))
}

public fun testMsaModSH() {
    val a = i16x8.new(-6, 7, -8, 9, -6, 7, -8, 9)
    val b = i16x8.new(1, -2, 3, -4, 1, -2, 3, -4)
    val r = i16x8.new(0, 1, -2, 1, 0, 1, -2, 1)
    check(r == transmute(msaModSH(transmute(a), transmute(b))))
}

public fun testMsaModSW() {
    val a = i32x4.new(6, 7, 8, 9)
    val b = i32x4.new(1, -2, 3, -4)
    val r = i32x4.new(0, 1, 2, 1)
    check(r == transmute(msaModSW(transmute(a), transmute(b))))
}

public fun testMsaModSD() {
    val a = i64x2.new(6, -7)
    val b = i64x2.new(-1, 2)
    val r = i64x2.new(0, -1)
    check(r == transmute(msaModSD(transmute(a), transmute(b))))
}

public fun testMsaModUB() {
    val a = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val b = u8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val r = u8x16.new(0, 1, 2, 1, 0, 1, 2, 1, 0, 1, 2, 1, 0, 1, 2, 1)
    check(r == transmute(msaModUB(transmute(a), transmute(b))))
}

public fun testMsaModUH() {
    val a = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val b = u16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val r = u16x8.new(0, 1, 2, 1, 0, 1, 2, 1)
    check(r == transmute(msaModUH(transmute(a), transmute(b))))
}

public fun testMsaModUW() {
    val a = u32x4.new(6, 7, 8, 9)
    val b = u32x4.new(1, 2, 3, 4)
    val r = u32x4.new(0, 1, 2, 1)
    check(r == transmute(msaModUW(transmute(a), transmute(b))))
}

public fun testMsaModUD() {
    val a = u64x2.new(6, 7)
    val b = u64x2.new(1, 2)
    val r = u64x2.new(0, 1)
    check(r == transmute(msaModUD(transmute(a), transmute(b))))
}

public fun testMsaMoveV() {
    val a = i8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8)
    val r = i8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8)
    check(r == transmute(msaMoveV(transmute(a))))
}

public fun testMsaMsubQH() {
    val a = i16x8.new(1024, -1024, 1024, -1024, 1, 2, 3, 4)
    val b = i16x8.new(1025, 1025, 1025, 1025, 1025, 1025, 1025, 1025)
    val c = i16x8.new(1024, 2048, 3072, 4096, 1024, 2048, 3072, 4096)
    val r = i16x8.new(991, -1089, 927, -1153, -32, -63, -94, -125)
    check(r == transmute(msaMsubQH(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaMsubQW() {
    val a = i32x4.new(2147483647, -2147483647, 1, 2)
    val b = i32x4.new(10240, 10240, 10240, 10240)
    val c = i32x4.new(10240, 20480, 30720, 40960)
    val r = i32x4.new(2147483646, -2147483648, 0, 1)
    check(r == transmute(msaMsubQW(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaMsubrQH() {
    val a = i16x8.new(1024, -1024, 1024, -1024, 1, 2, 3, 4)
    val b = i16x8.new(1025, 1025, 1025, 1025, 1025, 1025, 1025, 1025)
    val c = i16x8.new(1024, 2048, 3072, 4096, 1024, 2048, 3072, 4096)
    val r = i16x8.new(992, -1088, 928, -1152, -31, -62, -93, -124)
    check(r == transmute(msaMsubrQH(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaMsubrQW() {
    val a = i32x4.new(Int.MAX_VALUE, -2147483647, 1, 2)
    val b = i32x4.new(10240, 10240, 10240, 10240)
    val c = i32x4.new(10240, 20480, 30720, 40960)
    val r = i32x4.new(2147483647, -2147483647, 1, 2)
    check(r == transmute(msaMsubrQW(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaMsubvB() {
    val a = i8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = i8x16.new(5, 6, 7, 8, 5, 6, 7, 8, 5, 6, 7, 8, 5, 6, 7, 8)
    val c = i8x16.new(9, 10, 11, 12, 9, 10, 11, 12, 9, 10, 11, 12, 9, 10, 11, 12)
    val r = i8x16.new(-44, -58, -74, -92, -44, -58, -74, -92, -44, -58, -74, -92, -44, -58, -74, -92)
    check(r == transmute(msaMsubvB(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaMsubvH() {
    val a = i16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = i16x8.new(5, 6, 7, 8, 5, 6, 7, 8)
    val c = i16x8.new(9, 10, 11, 12, 9, 10, 11, 12)
    val r = i16x8.new(-44, -58, -74, -92, -44, -58, -74, -92)
    check(r == transmute(msaMsubvH(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaMsubvW() {
    val a = i32x4.new(1, 2, 1, 2)
    val b = i32x4.new(3, 4, 3, 4)
    val c = i32x4.new(5, 6, 5, 6)
    val r = i32x4.new(-14, -22, -14, -22)
    check(r == transmute(msaMsubvW(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaMsubvD() {
    val a = i64x2.new(1, 2)
    val b = i64x2.new(3, 4)
    val c = i64x2.new(5, 6)
    val r = i64x2.new(-14, -22)
    check(r == transmute(msaMsubvD(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaMulQH() {
    val a = i16x8.new(12500, -20, -300, 400, 12500, 20, 300, 400)
    val b = i16x8.new(1250, 10240, -7585, 8456, 1250, 10240, -7585, 8456)
    val r = i16x8.new(476, -7, 69, 103, 476, 6, -70, 103)
    check(r == transmute(msaMulQH(transmute(a), transmute(b))))
}

public fun testMsaMulQW() {
    val a = i32x4.new(Int.MAX_VALUE, Int.MAX_VALUE, Int.MIN_VALUE, Int.MIN_VALUE)
    val b = i32x4.new(30, 60, 30, 60)
    val r = i32x4.new(29, 59, -30, -60)
    check(r == transmute(msaMulQW(transmute(a), transmute(b))))
}

public fun testMsaMulrQH() {
    val a = i16x8.new(12500, -20, -300, 400, 12500, 20, 300, 400)
    val b = i16x8.new(1250, 10240, -7585, 8456, 1250, 10240, -7585, 8456)
    val r = i16x8.new(477, -6, 69, 103, 477, 6, -69, 103)
    check(r == transmute(msaMulrQH(transmute(a), transmute(b))))
}

public fun testMsaMulrQW() {
    val a = i32x4.new(Int.MAX_VALUE, Int.MAX_VALUE, Int.MIN_VALUE, Int.MIN_VALUE)
    val b = i32x4.new(30, 60, 30, 60)
    val r = i32x4.new(30, 60, -30, -60)
    check(r == transmute(msaMulrQW(transmute(a), transmute(b))))
}

public fun testMsaMulvB() {
    val a = i8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = i8x16.new(16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    val r = i8x16.new(16, 30, 42, 52, 60, 66, 70, 72, 72, 70, 66, 60, 52, 42, 30, 16)
    check(r == transmute(msaMulvB(transmute(a), transmute(b))))
}

public fun testMsaMulvH() {
    val a = i16x8.new(1, 2, 3, 4, 5, 6, 7, 8)
    val b = i16x8.new(8, 7, 6, 5, 4, 3, 2, 1)
    val r = i16x8.new(8, 14, 18, 20, 20, 18, 14, 8)
    check(r == transmute(msaMulvH(transmute(a), transmute(b))))
}

public fun testMsaMulvW() {
    val a = i32x4.new(1, 2, 3, 4)
    val b = i32x4.new(4, 3, 2, 1)
    val r = i32x4.new(4, 6, 6, 4)
    check(r == transmute(msaMulvW(transmute(a), transmute(b))))
}

public fun testMsaMulvD() {
    val a = i64x2.new(1, 2)
    val b = i64x2.new(2, 1)
    val r = i64x2.new(2, 2)
    check(r == transmute(msaMulvD(transmute(a), transmute(b))))
}

public fun testMsaNlocB() {
    val a = i8x16.new(-128, -64, -32, -16, -8, -4, -2, -1, 1, 2, 4, 8, 16, 32, 64, 127)
    val r = i8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 0, 0, 0, 0, 0, 0, 0, 0)
    check(r == transmute(msaNlocB(transmute(a))))
}

public fun testMsaNlocH() {
    val a = i16x8.new(-32768, -16384, -8192, -4096, 4096, 8192, 16384, 32767)
    val r = i16x8.new(1, 2, 3, 4, 0, 0, 0, 0)
    check(r == transmute(msaNlocH(transmute(a))))
}

public fun testMsaNlocW() {
    val a = i32x4.new(Int.MIN_VALUE, -1073741824, 1073741824, Int.MAX_VALUE)
    val r = i32x4.new(1, 2, 0, 0)
    check(r == transmute(msaNlocW(transmute(a))))
}

public fun testMsaNlocD() {
    val a = i64x2.new(Long.MIN_VALUE, Long.MAX_VALUE)
    val r = i64x2.new(1, 0)
    check(r == transmute(msaNlocD(transmute(a))))
}

public fun testMsaNlzcB() {
    val a = i8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = i8x16.new(7, 6, 6, 5, 5, 5, 5, 4, 4, 4, 4, 4, 4, 4, 4, 3)
    check(r == transmute(msaNlzcB(transmute(a))))
}

public fun testMsaNlzcH() {
    val a = i16x8.new(1, 2, 3, 4, 5, 6, 7, 8)
    val r = i16x8.new(15, 14, 14, 13, 13, 13, 13, 12)
    check(r == transmute(msaNlzcH(transmute(a))))
}

public fun testMsaNlzcW() {
    val a = i32x4.new(1, 2, 3, 4)
    val r = i32x4.new(31, 30, 30, 29)
    check(r == transmute(msaNlzcW(transmute(a))))
}

public fun testMsaNlzcD() {
    val a = i64x2.new(1, 2)
    val r = i64x2.new(63, 62)
    check(r == transmute(msaNlzcD(transmute(a))))
}

public fun testMsaNorV() {
    val a = u8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = u8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = u8x16.new(254, 253, 252, 251, 250, 249, 248, 247, 246, 245, 244, 243, 242, 241, 240, 239)
    check(r == transmute(msaNorV(transmute(a), transmute(b))))
}

public fun testMsaNoriB() {
    val a = u8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = u8x16.new(250, 249, 248, 251, 250, 249, 248, 243, 242, 241, 240, 243, 242, 241, 240, 235)
    check(r == transmute(msaNoriB(transmute(a), 4)))
}

public fun testMsaOrV() {
    val a = u8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = u8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = u8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    check(r == transmute(msaOrV(transmute(a), transmute(b))))
}

public fun testMsaOriB() {
    val a = u8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = u8x16.new(5, 6, 7, 4, 5, 6, 7, 12, 13, 14, 15, 12, 13, 14, 15, 20)
    check(r == transmute(msaOriB(transmute(a), 4)))
}

public fun testMsaPckevB() {
    val a = i8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = i8x16.new(4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1)
    val r = i8x16.new(4, 2, 4, 2, 4, 2, 4, 2, 1, 3, 1, 3, 1, 3, 1, 3)
    check(r == transmute(msaPckevB(transmute(a), transmute(b))))
}

public fun testMsaPckevH() {
    val a = i16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = i16x8.new(4, 3, 2, 1, 4, 3, 2, 1)
    val r = i16x8.new(4, 2, 4, 2, 1, 3, 1, 3)
    check(r == transmute(msaPckevH(transmute(a), transmute(b))))
}

public fun testMsaPckevW() {
    val a = i32x4.new(1, 2, 3, 4)
    val b = i32x4.new(4, 3, 2, 1)
    val r = i32x4.new(4, 2, 1, 3)
    check(r == transmute(msaPckevW(transmute(a), transmute(b))))
}

public fun testMsaPckevD() {
    val a = i64x2.new(1, 2)
    val b = i64x2.new(4, 3)
    val r = i64x2.new(4, 1)
    check(r == transmute(msaPckevD(transmute(a), transmute(b))))
}

public fun testMsaPckodB() {
    val a = i8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = i8x16.new(4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1)
    val r = i8x16.new(3, 1, 3, 1, 3, 1, 3, 1, 2, 4, 2, 4, 2, 4, 2, 4)
    check(r == transmute(msaPckodB(transmute(a), transmute(b))))
}

public fun testMsaPckodH() {
    val a = i16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = i16x8.new(4, 3, 2, 1, 4, 3, 2, 1)
    val r = i16x8.new(3, 1, 3, 1, 2, 4, 2, 4)
    check(r == transmute(msaPckodH(transmute(a), transmute(b))))
}

public fun testMsaPckodW() {
    val a = i32x4.new(1, 2, 3, 4)
    val b = i32x4.new(4, 3, 2, 1)
    val r = i32x4.new(3, 1, 2, 4)
    check(r == transmute(msaPckodW(transmute(a), transmute(b))))
}

public fun testMsaPckodD() {
    val a = i64x2.new(1, 2)
    val b = i64x2.new(4, 3)
    val r = i64x2.new(3, 2)
    check(r == transmute(msaPckodD(transmute(a), transmute(b))))
}

public fun testMsaPcntB() {
    val a = i8x16.new(-128, -64, -32, -16, -8, -4, -2, -1, 1, 2, 4, 8, 16, 32, 64, 127)
    val r = i8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 1, 1, 1, 1, 1, 1, 1, 7)
    check(r == transmute(msaPcntB(transmute(a))))
}

public fun testMsaPcntH() {
    val a = i16x8.new(-32768, -16384, -8192, -4096, 4096, 8192, 16384, 32767)
    val r = i16x8.new(1, 2, 3, 4, 1, 1, 1, 15)
    check(r == transmute(msaPcntH(transmute(a))))
}

public fun testMsaPcntW() {
    val a = i32x4.new(Int.MIN_VALUE, -1073741824, 1073741824, Int.MAX_VALUE)
    val r = i32x4.new(1, 2, 1, 31)
    check(r == transmute(msaPcntW(transmute(a))))
}

public fun testMsaPcntD() {
    val a = i64x2.new(-2147483648, 2147483647)
    val r = i64x2.new(33, 31)
    check(r == transmute(msaPcntD(transmute(a))))
}

public fun testMsaSatSB() {
    val a = i8x16.new(Byte.MAX_VALUE, 105, 30, 1, Byte.MAX_VALUE, 105, 30, 1, Byte.MAX_VALUE, 105, 30, 1, Byte.MAX_VALUE, 105, 30, 1)
    val r = i8x16.new(3, 3, 3, 1, 3, 3, 3, 1, 3, 3, 3, 1, 3, 3, 3, 1)
    check(r == transmute(msaSatSB(transmute(a), 2)))
}

public fun testMsaSatSH() {
    val a = i16x8.new(Short.MAX_VALUE, 1155, 155, 1, Short.MAX_VALUE, 1155, 155, 1)
    val r = i16x8.new(127, 127, 127, 1, 127, 127, 127, 1)
    check(r == transmute(msaSatSH(transmute(a), 7)))
}

public fun testMsaSatSW() {
    val a = i32x4.new(Int.MAX_VALUE, 111111155, Int.MAX_VALUE, 1)
    val r = i32x4.new(131071, 131071, 131071, 1)
    check(r == transmute(msaSatSW(transmute(a), 17)))
}

public fun testMsaSatSD() {
    val a = i64x2.new(Long.MAX_VALUE, 1)
    val r = i64x2.new(137438953471, 1)
    check(r == transmute(msaSatSD(transmute(a), 37)))
}

public fun testMsaSatUB() {
    val a = u8x16.new(UByte.MAX_VALUE, 105, 30, 1, UByte.MAX_VALUE, 105, 30, 1, UByte.MAX_VALUE, 105, 30, 1, UByte.MAX_VALUE, 105, 30, 1)
    val r = u8x16.new(7, 7, 7, 1, 7, 7, 7, 1, 7, 7, 7, 1, 7, 7, 7, 1)
    check(r == transmute(msaSatUB(transmute(a), 2)))
}

public fun testMsaSatUH() {
    val a = u16x8.new(UShort.MAX_VALUE, 1155, 155, 1, UShort.MAX_VALUE, 1155, 155, 1)
    val r = u16x8.new(255, 255, 155, 1, 255, 255, 155, 1)
    check(r == transmute(msaSatUH(transmute(a), 7)))
}

public fun testMsaSatUW() {
    val a = u32x4.new(UInt.MAX_VALUE, 111111155, UInt.MAX_VALUE, 1)
    val r = u32x4.new(262143, 262143, 262143, 1)
    check(r == transmute(msaSatUW(transmute(a), 17)))
}

public fun testMsaSatUD() {
    val a = u64x2.new(ULong.MAX_VALUE, 1)
    val r = u64x2.new(274877906943, 1)
    check(r == transmute(msaSatUD(transmute(a), 37)))
}

public fun testMsaShfB() {
    val a = i8x16.new(11, 12, 3, 4, 11, 12, 3, 4, 11, 12, 3, 4, 11, 12, 3, 4)
    val r = i8x16.new(11, 3, 4, 12, 11, 3, 4, 12, 11, 3, 4, 12, 11, 3, 4, 12)
    check(r == transmute(msaShfB(transmute(a), 120)))
}

public fun testMsaShfH() {
    val a = i16x8.new(11, 12, 13, 14, 11, 12, 13, 14)
    val r = i16x8.new(11, 14, 12, 13, 11, 14, 12, 13)
    check(r == transmute(msaShfH(transmute(a), 156)))
}

public fun testMsaShfW() {
    val a = i32x4.new(1, 2, 3, 4)
    val r = i32x4.new(1, 3, 2, 4)
    check(r == transmute(msaShfW(transmute(a), 216)))
}

public fun testMsaSldB() {
    val a = i8x16.new(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = i8x16.new(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = i8x16.new(21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 0, 1, 2, 3, 4)
    check(r == transmute(msaSldB(transmute(a), transmute(b), 5)))
}

public fun testMsaSldH() {
    val a = i16x8.new(0, 1, 2, 3, 4, 5, 6, 7)
    val b = i16x8.new(8, 9, 10, 11, 12, 13, 14, 15)
    // let c = 5 as i32;
    val r = i16x8.new(9, 10, 11, 0, 13, 14, 15, 4)
    check(r == transmute(msaSldH(transmute(a), transmute(b), 2)))
}

public fun testMsaSldW() {
    val a = i32x4.new(0, 1, 2, 3)
    val b = i32x4.new(4, 5, 6, 7)
    val r = i32x4.new(4, 5, 6, 7)
    check(r == transmute(msaSldW(transmute(a), transmute(b), 4)))
}

public fun testMsaSldD() {
    val a = i64x2.new(0, 1)
    val b = i64x2.new(2, 3)
    val r = i64x2.new(2, 3)
    check(r == transmute(msaSldD(transmute(a), transmute(b), 2)))
}

public fun testMsaSldiB() {
    val a = i8x16.new(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val b = i8x16.new(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
    val r = i8x16.new(21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 0, 1, 2, 3, 4)
    check(r == transmute(msaSldiB(transmute(a), transmute(b), 5)))
}

public fun testMsaSldiH() {
    val a = i16x8.new(0, 1, 2, 3, 4, 5, 6, 7)
    val b = i16x8.new(8, 9, 10, 11, 12, 13, 14, 15)
    // let c = 5 as i32;
    val r = i16x8.new(9, 10, 11, 0, 13, 14, 15, 4)
    check(r == transmute(msaSldiH(transmute(a), transmute(b), 2)))
}

public fun testMsaSldiW() {
    val a = i32x4.new(0, 1, 2, 3)
    val b = i32x4.new(4, 5, 6, 7)
    val r = i32x4.new(4, 5, 6, 7)
    check(r == transmute(msaSldiW(transmute(a), transmute(b), 0)))
}

public fun testMsaSldiD() {
    val a = i64x2.new(0, 1)
    val b = i64x2.new(2, 3)
    val r = i64x2.new(2, 3)
    check(r == transmute(msaSldiD(transmute(a), transmute(b), 0)))
}

public fun testMsaSllB() {
    val a = i8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = i8x16.new(4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1)
    val r = i8x16.new(16, 16, 12, 8, 16, 16, 12, 8, 16, 16, 12, 8, 16, 16, 12, 8)
    check(r == transmute(msaSllB(transmute(a), transmute(b))))
}

public fun testMsaSllH() {
    val a = i16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = i16x8.new(4, 3, 2, 1, 4, 3, 2, 1)
    val r = i16x8.new(16, 16, 12, 8, 16, 16, 12, 8)
    check(r == transmute(msaSllH(transmute(a), transmute(b))))
}

public fun testMsaSllW() {
    val a = i32x4.new(1, 2, 3, 4)
    val b = i32x4.new(4, 3, 2, 1)
    val r = i32x4.new(16, 16, 12, 8)
    check(r == transmute(msaSllW(transmute(a), transmute(b))))
}

public fun testMsaSllD() {
    val a = i64x2.new(1, 2)
    val b = i64x2.new(4, 3)
    val r = i64x2.new(16, 16)
    check(r == transmute(msaSllD(transmute(a), transmute(b))))
}

public fun testMsaSlliB() {
    val a = i8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val r = i8x16.new(4, 8, 12, 16, 4, 8, 12, 16, 4, 8, 12, 16, 4, 8, 12, 16)
    check(r == transmute(msaSlliB(transmute(a), 2)))
}

public fun testMsaSlliH() {
    val a = i16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val r = i16x8.new(4, 8, 12, 16, 4, 8, 12, 16)
    check(r == transmute(msaSlliH(transmute(a), 2)))
}

public fun testMsaSlliW() {
    val a = i32x4.new(1, 2, 3, 4)
    val r = i32x4.new(4, 8, 12, 16)
    check(r == transmute(msaSlliW(transmute(a), 2)))
}

public fun testMsaSlliD() {
    val a = i64x2.new(1, 2)
    val r = i64x2.new(2, 4)
    check(r == transmute(msaSlliD(transmute(a), 1)))
}

public fun testMsaSplatB() {
    val a = i8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val r = i8x16.new(4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4)
    check(r == transmute(msaSplatB(transmute(a), 3)))
}

public fun testMsaSplatH() {
    val a = i16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val r = i16x8.new(4, 4, 4, 4, 4, 4, 4, 4)
    check(r == transmute(msaSplatH(transmute(a), 3)))
}

public fun testMsaSplatW() {
    val a = i32x4.new(1, 2, 3, 4)
    val r = i32x4.new(4, 4, 4, 4)
    check(r == transmute(msaSplatW(transmute(a), 3)))
}

public fun testMsaSplatD() {
    val a = i64x2.new(1, 2)
    val r = i64x2.new(2, 2)
    check(r == transmute(msaSplatD(transmute(a), 3)))
}

public fun testMsaSplatiB() {
    val a = i8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val r = i8x16.new(3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3)
    check(r == transmute(msaSplatiB(transmute(a), 2)))
}

public fun testMsaSplatiH() {
    val a = i16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val r = i16x8.new(3, 3, 3, 3, 3, 3, 3, 3)
    check(r == transmute(msaSplatiH(transmute(a), 2)))
}

public fun testMsaSplatiW() {
    val a = i32x4.new(1, 2, 3, 4)
    val r = i32x4.new(3, 3, 3, 3)
    check(r == transmute(msaSplatiW(transmute(a), 2)))
}

public fun testMsaSplatiD() {
    val a = i64x2.new(1, 2)
    val r = i64x2.new(2, 2)
    check(r == transmute(msaSplatiD(transmute(a), 1)))
}

public fun testMsaSraB() {
    val a = i8x16.new(-128, -64, -32, -16, -8, -4, -2, -1, 1, 2, 4, 8, 16, 32, 64, 127)
    val b = i8x16.new(8, 7, 6, 5, 4, 3, 2, 1, 8, 7, 6, 5, 4, 3, 2, 1)
    val r = i8x16.new(-128, -1, -1, -1, -1, -1, -1, -1, 1, 0, 0, 0, 1, 4, 16, 63)
    check(r == transmute(msaSraB(transmute(a), transmute(b))))
}

public fun testMsaSraH() {
    val a = i16x8.new(-32768, -16384, -8192, -4096, 1, 2, 3, 4)
    val b = i16x8.new(15, 14, 13, 12, 12, 13, 14, 15)
    val r = i16x8.new(-1, -1, -1, -1, 0, 0, 0, 0)
    check(r == transmute(msaSraH(transmute(a), transmute(b))))
}

public fun testMsaSraW() {
    val a = i32x4.new(Int.MIN_VALUE, -1073741824, 1, 2)
    val b = i32x4.new(16, 15, 16, 15)
    val r = i32x4.new(-32768, -32768, 0, 0)
    check(r == transmute(msaSraW(transmute(a), transmute(b))))
}

public fun testMsaSraD() {
    val a = i64x2.new(Long.MIN_VALUE, Long.MAX_VALUE)
    val b = i64x2.new(32, 31)
    val r = i64x2.new(-2147483648, 4294967295)
    check(r == transmute(msaSraD(transmute(a), transmute(b))))
}

public fun testMsaSraiB() {
    val a = i8x16.new(Byte.MAX_VALUE, 125, 55, 1, Byte.MAX_VALUE, 125, 55, 1, Byte.MAX_VALUE, 125, 55, 1, Byte.MAX_VALUE, 125, 55, 1)
    val r = i8x16.new(31, 31, 13, 0, 31, 31, 13, 0, 31, 31, 13, 0, 31, 31, 13, 0)
    check(r == transmute(msaSraiB(transmute(a), 2)))
}

public fun testMsaSraiH() {
    val a = i16x8.new(Short.MAX_VALUE, 125, 55, 1, Short.MAX_VALUE, 125, 55, 1)
    val r = i16x8.new(8191, 31, 13, 0, 8191, 31, 13, 0)
    check(r == transmute(msaSraiH(transmute(a), 2)))
}

public fun testMsaSraiW() {
    val a = i32x4.new(Int.MAX_VALUE, 125, 55, 1)
    val r = i32x4.new(536870911, 31, 13, 0)
    check(r == transmute(msaSraiW(transmute(a), 2)))
}

public fun testMsaSraiD() {
    val a = i64x2.new(Long.MAX_VALUE, 55)
    val r = i64x2.new(2305843009213693951, 13)
    check(r == transmute(msaSraiD(transmute(a), 2)))
}

public fun testMsaSrarB() {
    val a = i8x16.new(-128, -64, -32, -16, -8, -4, -2, -1, 1, 2, 4, 8, 16, 32, 64, 127)
    val b = i8x16.new(4, 3, 2, 1, 4, 3, 2, 1, 8, 7, 6, 5, 4, 3, 2, 1)
    val r = i8x16.new(-8, -8, -8, -8, 0, 0, 0, 0, 1, 0, 0, 0, 1, 4, 16, 64)
    check(r == transmute(msaSrarB(transmute(a), transmute(b))))
}

public fun testMsaSrarH() {
    val a = i16x8.new(Short.MIN_VALUE, -16384, -8192, -4096, 150, 50, 25, 15)
    val b = i16x8.new(4, 3, 2, 1, 1, 2, 3, 4)
    val r = i16x8.new(-2048, -2048, -2048, -2048, 75, 13, 3, 1)
    check(r == transmute(msaSrarH(transmute(a), transmute(b))))
}

public fun testMsaSrarW() {
    val a = i32x4.new(Int.MIN_VALUE, -1073741824, 100, 50)
    val b = i32x4.new(16, 15, 1, 2)
    val r = i32x4.new(-32768, -32768, 50, 13)
    check(r == transmute(msaSrarW(transmute(a), transmute(b))))
}

public fun testMsaSrarD() {
    val a = i64x2.new(Long.MIN_VALUE, Long.MAX_VALUE)
    val b = i64x2.new(32, 31)
    val r = i64x2.new(-2147483648, 4294967296)
    check(r == transmute(msaSrarD(transmute(a), transmute(b))))
}

public fun testMsaSrariB() {
    val a = i8x16.new(125, Byte.MAX_VALUE, 55, 1, 125, Byte.MAX_VALUE, 55, 1, 125, Byte.MAX_VALUE, 55, 1, 125, Byte.MAX_VALUE, 55, 1)
    val r = i8x16.new(31, 32, 14, 0, 31, 32, 14, 0, 31, 32, 14, 0, 31, 32, 14, 0)
    check(r == transmute(msaSrariB(transmute(a), 2)))
}

public fun testMsaSrariH() {
    val a = i16x8.new(2155, 1155, 155, 1, 2155, 1155, 155, 1)
    val r = i16x8.new(539, 289, 39, 0, 539, 289, 39, 0)
    check(r == transmute(msaSrariH(transmute(a), 2)))
}

public fun testMsaSrariW() {
    val a = i32x4.new(211111155, 111111155, 11111155, 1)
    val r = i32x4.new(52777789, 27777789, 2777789, 0)
    check(r == transmute(msaSrariW(transmute(a), 2)))
}

public fun testMsaSrariD() {
    val a = i64x2.new(211111111155, 111111111155)
    val r = i64x2.new(52777777789, 27777777789)
    check(r == transmute(msaSrariD(transmute(a), 2)))
}

public fun testMsaSrlB() {
    val a = i8x16.new(-128, -64, -32, -16, -8, -4, -2, -1, 1, 2, 4, 8, 16, 32, 64, 127)
    val b = i8x16.new(8, 7, 6, 5, 4, 3, 2, 1, 8, 7, 6, 5, 4, 3, 2, 1)
    val r = i8x16.new(-128, 1, 3, 7, 15, 31, 63, 127, 1, 0, 0, 0, 1, 4, 16, 63)
    check(r == transmute(msaSrlB(transmute(a), transmute(b))))
}

public fun testMsaSrlH() {
    val a = i16x8.new(-32768, -16384, -8192, -4096, 1, 2, 3, 4)
    val b = i16x8.new(15, 14, 13, 12, 4, 3, 2, 1)
    val r = i16x8.new(1, 3, 7, 15, 0, 0, 0, 2)
    check(r == transmute(msaSrlH(transmute(a), transmute(b))))
}

public fun testMsaSrlW() {
    val a = i32x4.new(Int.MIN_VALUE, -1073741824, 1, 2)
    val b = i32x4.new(16, 15, 16, 15)
    val r = i32x4.new(32768, 98304, 0, 0)
    check(r == transmute(msaSrlW(transmute(a), transmute(b))))
}

public fun testMsaSrlD() {
    val a = i64x2.new(Long.MIN_VALUE, Long.MAX_VALUE)
    val b = i64x2.new(32, 31)
    val r = i64x2.new(2147483648, 4294967295)
    check(r == transmute(msaSrlD(transmute(a), transmute(b))))
}

public fun testMsaSrliB() {
    val a = i8x16.new(25, 50, 100, 127, 25, 50, 100, 127, 25, 50, 100, 127, 25, 50, 100, 127)
    val r = i8x16.new(6, 12, 25, 31, 6, 12, 25, 31, 6, 12, 25, 31, 6, 12, 25, 31)
    check(r == transmute(msaSrliB(transmute(a), 2)))
}

public fun testMsaSrliH() {
    val a = i16x8.new(Short.MAX_VALUE, 3276, 100, 127, Short.MAX_VALUE, 3276, 100, 127)
    val r = i16x8.new(8191, 819, 25, 31, 8191, 819, 25, 31)
    check(r == transmute(msaSrliH(transmute(a), 2)))
}

public fun testMsaSrliW() {
    val a = i32x4.new(100, Int.MAX_VALUE, 100, Int.MAX_VALUE)
    val r = i32x4.new(25, 536870911, 25, 536870911)
    check(r == transmute(msaSrliW(transmute(a), 2)))
}

public fun testMsaSrliD() {
    val a = i64x2.new(100, Long.MAX_VALUE)
    val r = i64x2.new(50, 4611686018427387903)
    check(r == transmute(msaSrliD(transmute(a), 1)))
}

public fun testMsaSrlrB() {
    val a = i8x16.new(-128, -64, -32, -16, -8, -4, -2, -1, 1, 2, 4, 8, 16, 32, 64, 127)
    val b = i8x16.new(8, 7, 6, 5, 4, 3, 2, 1, 8, 7, 6, 5, 4, 3, 2, 1)
    val r = i8x16.new(-128, 2, 4, 8, 16, 32, 64, -128, 1, 0, 0, 0, 1, 4, 16, 64)
    check(r == transmute(msaSrlrB(transmute(a), transmute(b))))
}

public fun testMsaSrlrH() {
    val a = i16x8.new(-32768, -16384, -8192, -4096, 1, 2, 3, 4)
    val b = i16x8.new(15, 14, 13, 12, 4, 3, 2, 1)
    val r = i16x8.new(1, 3, 7, 15, 0, 0, 1, 2)
    check(r == transmute(msaSrlrH(transmute(a), transmute(b))))
}

public fun testMsaSrlrW() {
    val a = i32x4.new(Int.MIN_VALUE, -1073741824, 1, 2)
    val b = i32x4.new(16, 15, 16, 15)
    val r = i32x4.new(32768, 98304, 0, 0)
    check(r == transmute(msaSrlrW(transmute(a), transmute(b))))
}

public fun testMsaSrlrD() {
    val a = i64x2.new(Long.MIN_VALUE, Long.MAX_VALUE)
    val b = i64x2.new(32, 31)
    val r = i64x2.new(2147483648, 4294967296)
    check(r == transmute(msaSrlrD(transmute(a), transmute(b))))
}

public fun testMsaSrlriB() {
    val a = i8x16.new(25, 50, 100, Byte.MAX_VALUE, 25, 50, 100, Byte.MAX_VALUE, 25, 50, 100, Byte.MAX_VALUE, 25, 50, 100, Byte.MAX_VALUE)
    val r = i8x16.new(6, 13, 25, 32, 6, 13, 25, 32, 6, 13, 25, 32, 6, 13, 25, 32)
    check(r == transmute(msaSrlriB(transmute(a), 2)))
}

public fun testMsaSrlriH() {
    val a = i16x8.new(Short.MAX_VALUE, 3276, 100, 127, Short.MAX_VALUE, 3276, 100, 127)
    val r = i16x8.new(8192, 819, 25, 32, 8192, 819, 25, 32)
    check(r == transmute(msaSrlriH(transmute(a), 2)))
}

public fun testMsaSrlriW() {
    val a = i32x4.new(100, 150, 200, Int.MAX_VALUE)
    val r = i32x4.new(25, 38, 50, 536870912)
    check(r == transmute(msaSrlriW(transmute(a), 2)))
}

public fun testMsaSrlriD() {
    val a = i64x2.new(100, Long.MAX_VALUE)
    val r = i64x2.new(50, 4611686018427387904)
    check(r == transmute(msaSrlriD(transmute(a), 1)))
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
    check(r == transmute(msaSubsSB(transmute(a), transmute(b))))
}

public fun testMsaSubsSH() {
    val a = i16x8.new(Short.MIN_VALUE, -2, -3, -4, Short.MIN_VALUE, -2, -3, -4)
    val b = i16x8.new(6, -7, 8, -9, 6, -7, 8, -9)
    val r = i16x8.new(Short.MIN_VALUE, 5, -11, 5, Short.MIN_VALUE, 5, -11, 5)
    check(r == transmute(msaSubsSH(transmute(a), transmute(b))))
}

public fun testMsaSubsSW() {
    val a = i32x4.new(Int.MIN_VALUE, -2, -3, -4)
    val b = i32x4.new(6, -7, 8, -9)
    val r = i32x4.new(Int.MIN_VALUE, 5, -11, 5)
    check(r == transmute(msaSubsSW(transmute(a), transmute(b))))
}

public fun testMsaSubsSD() {
    val a = i64x2.new(Long.MIN_VALUE, -2)
    val b = i64x2.new(6, -7)
    val r = i64x2.new(Long.MIN_VALUE, 5)
    check(r == transmute(msaSubsSD(transmute(a), transmute(b))))
}

public fun testMsaSubsUB() {
    val a = u8x16.new(UByte.MAX_VALUE, 2, 3, 4, UByte.MAX_VALUE, 2, 3, 4, UByte.MAX_VALUE, 2, 3, 4, UByte.MAX_VALUE, 2, 3, 4)
    val b = u8x16.new(6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9, 6, 7, 8, 9)
    val r = u8x16.new(249, 0, 0, 0, 249, 0, 0, 0, 249, 0, 0, 0, 249, 0, 0, 0)
    check(r == transmute(msaSubsUB(transmute(a), transmute(b))))
}

public fun testMsaSubsUH() {
    val a = u16x8.new(UShort.MAX_VALUE, 2, 3, 4, UShort.MAX_VALUE, 2, 3, 4)
    val b = u16x8.new(6, 7, 8, 9, 6, 7, 8, 9)
    val r = u16x8.new(65529, 0, 0, 0, 65529, 0, 0, 0)
    check(r == transmute(msaSubsUH(transmute(a), transmute(b))))
}

public fun testMsaSubsUW() {
    val a = u32x4.new(UInt.MAX_VALUE, 2, 3, 4)
    val b = u32x4.new(6, 7, 8, 9)
    val r = u32x4.new(4294967289, 0, 0, 0)
    check(r == transmute(msaSubsUW(transmute(a), transmute(b))))
}

public fun testMsaSubsUD() {
    val a = u64x2.new(ULong.MAX_VALUE, 2)
    val b = u64x2.new(6, 7)
    val r = u64x2.new(18446744073709551609uL, 0)
    check(r == transmute(msaSubsUD(transmute(a), transmute(b))))
}

public fun testMsaSubsusUB() {
    val a = u8x16.new(UByte.MAX_VALUE, 2, 3, 4, UByte.MAX_VALUE, 2, 3, 4, UByte.MAX_VALUE, 2, 3, 4, UByte.MAX_VALUE, 2, 3, 4)
    val b = i8x16.new(-6, -7, -8, -9, -6, -7, -8, -9, -6, -7, -8, -9, -6, -7, -8, -9)
    val r = u8x16.new(255, 9, 11, 13, 255, 9, 11, 13, 255, 9, 11, 13, 255, 9, 11, 13)
    check(r == transmute(msaSubsusUB(transmute(a), transmute(b))))
}

public fun testMsaSubsusUH() {
    val a = u16x8.new(UShort.MAX_VALUE, 2, 3, 4, UShort.MAX_VALUE, 2, 3, 4)
    val b = i16x8.new(-6, -7, -8, -9, -6, -7, -8, -9)
    val r = u16x8.new(65535, 9, 11, 13, 65535, 9, 11, 13)
    check(r == transmute(msaSubsusUH(transmute(a), transmute(b))))
}

public fun testMsaSubsusUW() {
    val a = u32x4.new(UInt.MAX_VALUE, 2, 3, 4)
    val b = i32x4.new(-6, -7, -8, -9)
    val r = u32x4.new(4294967295, 9, 11, 13)
    check(r == transmute(msaSubsusUW(transmute(a), transmute(b))))
}

public fun testMsaSubsusUD() {
    val a = u64x2.new(ULong.MAX_VALUE, 2)
    val b = i64x2.new(-6, -7)
    val r = u64x2.new(18446744073709551615uL, 9)
    check(r == transmute(msaSubsusUD(transmute(a), transmute(b))))
}

public fun testMsaSubsuuSB() {
    val a = u8x16.new(UByte.MAX_VALUE, 2, 3, 4, UByte.MAX_VALUE, 2, 3, 4, UByte.MAX_VALUE, 2, 3, 4, UByte.MAX_VALUE, 2, 3, 4)
    val b = u8x16.new(6, 7, 8, UByte.MAX_VALUE, 6, 7, 8, UByte.MAX_VALUE, 6, 7, 8, UByte.MAX_VALUE, 6, 7, 8, UByte.MAX_VALUE)
    val r = i8x16.new(127, -5, -5, -128, 127, -5, -5, -128, 127, -5, -5, -128, 127, -5, -5, -128)
    check(r == transmute(msaSubsuuSB(transmute(a), transmute(b))))
}

public fun testMsaSubsuuSH() {
    val a = u16x8.new(UShort.MAX_VALUE, 2, 3, 4, UShort.MAX_VALUE, 2, 3, 4)
    val b = u16x8.new(6, 7, 8, 65535, 6, 7, 8, 65535)
    val r = i16x8.new(32767, -5, -5, -32768, 32767, -5, -5, -32768)
    check(r == transmute(msaSubsuuSH(transmute(a), transmute(b))))
}

public fun testMsaSubsuuSW() {
    val a = u32x4.new(UInt.MAX_VALUE, 2, 3, 4)
    val b = u32x4.new(6, 7, 8, 4294967295)
    val r = i32x4.new(2147483647, -5, -5, -2147483648)
    check(r == transmute(msaSubsuuSW(transmute(a), transmute(b))))
}

public fun testMsaSubsuuSD() {
    val a = u64x2.new(ULong.MAX_VALUE, 2)
    val b = u64x2.new(6, 7)
    val r = i64x2.new(Long.MAX_VALUE, -5)
    check(r == transmute(msaSubsuuSD(transmute(a), transmute(b))))
}

public fun testMsaSubvB() {
    val a = i8x16.new(Byte.MIN_VALUE, -2, -3, -4, Byte.MIN_VALUE, -2, -3, -4, Byte.MIN_VALUE, -2, -3, -4, Byte.MIN_VALUE, -2, -3, -4)
    val b = i8x16.new(6, -7, 8, -9, 6, -7, 8, -9, 6, -7, 8, -9, 6, -7, 8, -9)
    val r = i8x16.new(122, 5, -11, 5, 122, 5, -11, 5, 122, 5, -11, 5, 122, 5, -11, 5)
    check(r == transmute(msaSubvB(transmute(a), transmute(b))))
}

public fun testMsaSubvH() {
    val a = i16x8.new(Short.MIN_VALUE, -2, -3, -4, Short.MIN_VALUE, -2, -3, -4)
    val b = i16x8.new(6, -7, 8, -9, 6, -7, 8, -9)
    val r = i16x8.new(32762, 5, -11, 5, 32762, 5, -11, 5)
    check(r == transmute(msaSubvH(transmute(a), transmute(b))))
}

public fun testMsaSubvW() {
    val a = i32x4.new(Int.MIN_VALUE, -2, -3, -4)
    val b = i32x4.new(6, -7, 8, -9)
    val r = i32x4.new(2147483642, 5, -11, 5)
    check(r == transmute(msaSubvW(transmute(a), transmute(b))))
}

public fun testMsaSubvD() {
    val a = i64x2.new(Long.MAX_VALUE, -2)
    val b = i64x2.new(6, -7)
    val r = i64x2.new(9223372036854775801, 5)
    check(r == transmute(msaSubvD(transmute(a), transmute(b))))
}

public fun testMsaSubviB() {
    val a = i8x16.new(100, Byte.MAX_VALUE, 50, Byte.MIN_VALUE, 100, Byte.MAX_VALUE, 50, Byte.MIN_VALUE, 100, Byte.MAX_VALUE, 50, Byte.MIN_VALUE, 100, Byte.MAX_VALUE, 50, Byte.MIN_VALUE)
    val r = i8x16.new(95, 122, 45, 123, 95, 122, 45, 123, 95, 122, 45, 123, 95, 122, 45, 123)
    check(r == transmute(msaSubviB(transmute(a), 5)))
}

public fun testMsaSubviH() {
    val a = i16x8.new(Short.MAX_VALUE, 3276, -100, Short.MIN_VALUE, Short.MAX_VALUE, 3276, -100, Short.MIN_VALUE)
    val r = i16x8.new(32762, 3271, -105, 32763, 32762, 3271, -105, 32763)
    check(r == transmute(msaSubviH(transmute(a), 5)))
}

public fun testMsaSubviW() {
    val a = i32x4.new(100, 150, 200, Int.MAX_VALUE)
    val r = i32x4.new(95, 145, 195, 2147483642)
    check(r == transmute(msaSubviW(transmute(a), 5)))
}

public fun testMsaSubviD() {
    val a = i64x2.new(100, Long.MAX_VALUE)
    val r = i64x2.new(95, 9223372036854775802)
    check(r == transmute(msaSubviD(transmute(a), 5)))
}

public fun testMsaVshfB() {
    val a = i8x16.new(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4)
    val b = i8x16.new(4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1)
    val c = i8x16.new(4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1)
    val r = i8x16.new(3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4, 3, 2, 1, 4)
    check(r == transmute(msaVshfB(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaVshfH() {
    val a = i16x8.new(1, 2, 3, 4, 1, 2, 3, 4)
    val b = i16x8.new(4, 3, 2, 1, 4, 3, 2, 1)
    val c = i16x8.new(4, 3, 2, 1, 4, 3, 2, 1)
    val r = i16x8.new(3, 2, 1, 4, 3, 2, 1, 4)
    check(r == transmute(msaVshfH(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaVshfW() {
    val a = i32x4.new(1, 2, 3, 4)
    val b = i32x4.new(4, 3, 2, 1)
    val c = i32x4.new(4, 3, 2, 1)
    val r = i32x4.new(3, 2, 1, 4)
    check(r == transmute(msaVshfW(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaVshfD() {
    val a = i64x2.new(1, 2)
    val b = i64x2.new(4, 3)
    val c = i64x2.new(4, 3)
    val r = i64x2.new(3, 4)
    check(r == transmute(msaVshfD(transmute(a), transmute(b), transmute(c))))
}

public fun testMsaXorV() {
    val a = u8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val b = u8x16.new(16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    val r = u8x16.new(17, 13, 13, 9, 9, 13, 13, 1, 1, 13, 13, 9, 9, 13, 13, 17)
    check(r == transmute(msaXorV(transmute(a), transmute(b))))
}

public fun testMsaXoriB() {
    val a = u8x16.new(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = u8x16.new(5, 6, 7, 0, 1, 2, 3, 12, 13, 14, 15, 8, 9, 10, 11, 20)
    check(r == transmute(msaXoriB(transmute(a), 4)))
}
