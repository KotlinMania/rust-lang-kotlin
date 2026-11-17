// port-lint: tests s390x/vector.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.s390x.vector

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.intrinsics.kmSqrtf32
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.asPtr
import io.github.kotlinmania.rustlang.core.mem.get
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.num.checkedAdd
import io.github.kotlinmania.rustlang.core.num.checkedSub
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// s390x vector intrinsics.
//
// For more info see the [Reference Summary] or the online [IBM docs].
//
// [Reference Summary]: https://www.ibm.com/support/pages/sites/default/files/2021-05/SA22-7871-10.pdf
// [IBM docs]: https://www.ibm.com/docs/en/zos/2.4.0?topic=support-vector-built-in-functions

public fun ShuffleMask.asArray(n: Int): UIntArray = transmute(this)

public fun vecAddI32x4I32x4() {
    val x = VectorSignedInt.from(i32x4.new(1, 2, 3, 4))
    val y = VectorSignedInt.from(i32x4.new(4, 3, 2, 1))
    val z = vecAdd(x, y)
    check(i32x4.splat(5) == i32x4.from(z))
}

public fun testVecSubF32x4() {
    val a: STL = f32x4.new(-1.0, 0.0, 1.0, 2.0).into()
    val b: STL = f32x4.new(2.0, 1.0, -1.0, -2.0).into()
    val d = f32x4.new(-3.0, -1.0, 2.0, 4.0)
    val r = f32x4.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubF64x2() {
    val a: STL = f64x2.new(-1.0, 0.0).into()
    val b: STL = f64x2.new(2.0, 1.0).into()
    val d = f64x2.new(-3.0, -1.0)
    val r = f64x2.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubI64x2() {
    val a: STL = i64x2.new(-1, 0).into()
    val b: STL = i64x2.new(2, 1).into()
    val d = i64x2.new(-3, -1)
    val r = i64x2.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubU64x2() {
    val a: STL = u64x2.new(0, 1).into()
    val b: STL = u64x2.new(1, 0).into()
    val d = u64x2.new(ULong.MAX_VALUE, 1)
    val r = u64x2.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubI32x4() {
    val a: STL = i32x4.new(-1, 0, 1, 2).into()
    val b: STL = i32x4.new(2, 1, -1, -2).into()
    val d = i32x4.new(-3, -1, 2, 4)
    val r = i32x4.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubU32x4() {
    val a: STL = u32x4.new(0, 0, 1, 2).into()
    val b: STL = u32x4.new(2, 1, 0, 0).into()
    val d = u32x4.new(4294967294, 4294967295, 1, 2)
    val r = u32x4.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubI16x8() {
    val a: STL = i16x8.new(-1, 0, 1, 2, -1, 0, 1, 2).into()
    val b: STL = i16x8.new(2, 1, -1, -2, 2, 1, -1, -2).into()
    val d = i16x8.new(-3, -1, 2, 4, -3, -1, 2, 4)
    val r = i16x8.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubU16x8() {
    val a: STL = u16x8.new(0, 0, 1, 2, 0, 0, 1, 2).into()
    val b: STL = u16x8.new(2, 1, 0, 0, 2, 1, 0, 0).into()
    val d = u16x8.new(65534, 65535, 1, 2, 65534, 65535, 1, 2)
    val r = u16x8.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubI8x16() {
    val a: STL = i8x16.new(-1, 0, 1, 2, -1, 0, 1, 2, -1, 0, 1, 2, -1, 0, 1, 2).into()
    val b: STL = i8x16.new(2, 1, -1, -2, 2, 1, -1, -2, 2, 1, -1, -2, 2, 1, -1, -2).into()
    val d = i8x16.new(-3, -1, 2, 4, -3, -1, 2, 4, -3, -1, 2, 4, -3, -1, 2, 4)
    val r = i8x16.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubU8x16() {
    val a: STL = u8x16.new(0, 0, 1, 2, 0, 0, 1, 2, 0, 0, 1, 2, 0, 0, 1, 2).into()
    val b: STL = u8x16.new(2, 1, 0, 0, 2, 1, 0, 0, 2, 1, 0, 0, 2, 1, 0, 0).into()
    val d = u8x16.new(254, 255, 1, 2, 254, 255, 1, 2, 254, 255, 1, 2, 254, 255, 1, 2)
    val r = u8x16.from(vecSub(a, b))
    check(d == r)
}

public fun testVecMulF32x4() {
    val a: STL = f32x4.new(-1.0, 0.0, 1.0, 2.0).into()
    val b: STL = f32x4.new(2.0, 1.0, -1.0, -2.0).into()
    val d = f32x4.new(-2.0, 0.0, -1.0, -4.0)
    val r = f32x4.from(vecMul(a, b))
    check(d == r)
}

public fun testVecMulF64x2() {
    val a: STL = f64x2.new(-1.0, 0.0).into()
    val b: STL = f64x2.new(2.0, 1.0).into()
    val d = f64x2.new(-2.0, 0.0)
    val r = f64x2.from(vecMul(a, b))
    check(d == r)
}

public fun testVecMulI64x2() {
    val a: STL = i64x2.new(Long.MAX_VALUE, -4).into()
    val b: STL = i64x2.new(2, 3).into()
    val d = i64x2.new((Long.MAX_VALUE * 2L), -12)
    val r = i64x2.from(vecMul(a, b))
    check(d == r)
}

public fun testVecMulU64x2() {
    val a: STL = u64x2.new(ULong.MAX_VALUE, 4).into()
    val b: STL = u64x2.new(2, 3).into()
    val d = u64x2.new((ULong.MAX_VALUE * 2uL), 12)
    val r = u64x2.from(vecMul(a, b))
    check(d == r)
}

public fun testVecMulI32x4() {
    val a: STL = i32x4.new(-1, 0, 1, 2).into()
    val b: STL = i32x4.new(2, 1, -1, -2).into()
    val d = i32x4.new(-2, 0, -1, -4)
    val r = i32x4.from(vecMul(a, b))
    check(d == r)
}

public fun testVecMulU32x4() {
    val a: STL = u32x4.new(0, UInt.MAX_VALUE - 1u, 1, 2).into()
    val b: STL = u32x4.new(5, 6, 7, 8).into()
    val d = u32x4.new(0, 4294967284, 7, 16)
    val r = u32x4.from(vecMul(a, b))
    check(d == r)
}

public fun testVecMulI16x8() {
    val a: STL = i16x8.new(-1, 0, 1, 2, -1, 0, 1, 2).into()
    val b: STL = i16x8.new(2, 1, -1, -2, 2, 1, -1, -2).into()
    val d = i16x8.new(-2, 0, -1, -4, -2, 0, -1, -4)
    val r = i16x8.from(vecMul(a, b))
    check(d == r)
}

public fun testVecMulU16x8() {
    val a: STL = u16x8.new(0, UShort.MAX_VALUE - 1u, 1, 2, 3, 4, 5, 6).into()
    val b: STL = u16x8.new(5, 6, 7, 8, 9, 8, 7, 6).into()
    val d = u16x8.new(0, 65524, 7, 16, 27, 32, 35, 36)
    val r = u16x8.from(vecMul(a, b))
    check(d == r)
}

public fun testVecMulI8x16() {
    val a: STL = i8x16.new(-1, 0, 1, 2, -1, 0, 1, 2, -1, 0, 1, 2, -1, 0, 1, 2).into()
    val b: STL = i8x16.new(2, 1, -1, -2, 2, 1, -1, -2, 2, 1, -1, -2, 2, 1, -1, -2).into()
    val d = i8x16.new(-2, 0, -1, -4, -2, 0, -1, -4, -2, 0, -1, -4, -2, 0, -1, -4)
    val r = i8x16.from(vecMul(a, b))
    check(d == r)
}

public fun testVecMulU8x16() {
    val a: STL = u8x16.new(0, UByte.MAX_VALUE - 1u, 1, 2, 3, 4, 5, 6, 7, 8, 9, 8, 7, 6, 5, 4).into()
    val b: STL = u8x16.new(5, 6, 7, 8, 9, 8, 7, 6, 5, 4, 0, UByte.MAX_VALUE, 1, 2, 3, 4).into()
    val d = u8x16.new(0, 244, 7, 16, 27, 32, 35, 36, 35, 32, 0, 248, 7, 12, 15, 16)
    val r = u8x16.from(vecMul(a, b))
    check(d == r)
}

public fun testVecAbsI8() {
    val a: STL = vecSplats(-42)
    val a1: STL = vecAbs(a)
    val d = i8x16.splat(42)
    check(d == i8x16.from(a1))
}

public fun testVecAbsI16() {
    val a: STL = vecSplats(-42)
    val a1: STL = vecAbs(a)
    val d = i16x8.splat(42)
    check(d == i16x8.from(a1))
}

public fun testVecAbsI32() {
    val a: STL = vecSplats(-42)
    val a1: STL = vecAbs(a)
    val d = i32x4.splat(42)
    check(d == i32x4.from(a1))
}

public fun testVecAbsI64() {
    val a: STL = vecSplats(-42L)
    val a1: STL = vecAbs(a)
    val d = i64x2.splat(42L)
    check(d == i64x2.from(a1))
}

public fun testVecAbsF32() {
    val a: STL = vecSplats(-42.0f)
    val a1: STL = vecAbs(a)
    val d = f32x4.splat(42.0f)
    check(d == f32x4.from(a1))
}

public fun testVecAbsF64() {
    val a: STL = vecSplats(-42.0)
    val a1: STL = vecAbs(a)
    val d = f64x2.splat(42.0)
    check(d == f64x2.from(a1))
}

public fun testVecNabs() {
    val a: STL = f32x4.new(3.1415927f, 1.0, 0.0, -1.0).into()
    val d = f32x4.new(-3.1415927f, -1.0, 0.0, -1.0)
    val r = f32x4.from(vecNabs(a))
    check(d == r)
}

public fun testVecAndc() {
    val a: STL = i32x4.new(0xCC, 0xCC, 0xCC, 0xCC).into()
    val b: STL = i32x4.new(0x33, 0xF3, 0xC, 0x80).into()
    val d = i32x4.new(0xCC, 0xC, 0xC0, 0x4C)
    val r = i32x4.from(vecAndc(a, b))
    check(d == r)
}

public fun testVecAnd() {
    val a: STL = i32x4.new(0xCC, 0xCC, 0xCC, 0xCC).into()
    val b: STL = i32x4.new(0x33, 0xF3, 0xC, 0x0).into()
    val d = i32x4.new(0x0, 0xC0, 0xC, 0x0)
    val r = i32x4.from(vecAnd(a, b))
    check(d == r)
}

public fun testVecNand() {
    val a: STL = i32x4.new(0xCC, 0xCC, 0xCC, 0xCC).into()
    val b: STL = i32x4.new(0x33, 0xF3, 0xC, 0x0).into()
    val d = i32x4.new(0x0.inv(), 0xC0.inv(), 0xC.inv(), 0x0.inv())
    val r = i32x4.from(vecNand(a, b))
    check(d == r)
}

public fun testVecOrc() {
    val a: STL = u32x4.new(0xCC, 0xCC, 0xCC, 0xCC).into()
    val b: STL = u32x4.new(0x33, 0xF3, 0xC, 0x0).into()
    val d = u32x4.new(0xCC or 0x33.inv(), 0xCC or 0xF3.inv(), 0xCC or 0xC.inv(), 0xCC or 0x0.inv())
    val r = u32x4.from(vecOrc(a, b))
    check(d == r)
}

public fun testVecOr() {
    val a: STL = i32x4.new(0xCC, 0xCC, 0xCC, 0xCC).into()
    val b: STL = i32x4.new(0x33, 0xF3, 0xC, 0x0).into()
    val d = i32x4.new(0xFF, 0xFF, 0xCC, 0xCC)
    val r = i32x4.from(vecOr(a, b))
    check(d == r)
}

public fun testVecNor() {
    val a: STL = i32x4.new(0xCC, 0xCC, 0xCC, 0xCC).into()
    val b: STL = i32x4.new(0x33, 0xF3, 0xC, 0x0).into()
    val d = i32x4.new(0xFF.inv(), 0xFF.inv(), 0xCC.inv(), 0xCC.inv())
    val r = i32x4.from(vecNor(a, b))
    check(d == r)
}

public fun testVecXor() {
    val a: STL = i32x4.new(0xCC, 0xCC, 0xCC, 0xCC).into()
    val b: STL = i32x4.new(0x33, 0xF3, 0xC, 0x0).into()
    val d = i32x4.new(0xFF, 0x3F, 0xC0, 0xCC)
    val r = i32x4.from(vecXor(a, b))
    check(d == r)
}

public fun testVecEqv() {
    val a: STL = i32x4.new(0xCC, 0xCC, 0xCC, 0xCC).into()
    val b: STL = i32x4.new(0x33, 0xF3, 0xC, 0x0).into()
    val d = i32x4.new(0xFF.inv(), 0x3F.inv(), 0xC0.inv(), 0xCC.inv())
    val r = i32x4.from(vecEqv(a, b))
    check(d == r)
}

public fun testVecFloorF32() {
    val a: STL = f32x4.new(1.1, 1.9, -0.5, -0.9).into()
    val d = f32x4.new(1.0, 1.0, -1.0, -1.0)
    val r = f32x4.from(vecFloor(a))
    check(d == r)
}

public fun testVecFloorF641() {
    val a: STL = f64x2.new(1.1, 1.9).into()
    val d = f64x2.new(1.0, 1.0)
    val r = f64x2.from(vecFloor(a))
    check(d == r)
}

public fun testVecFloorF642() {
    val a: STL = f64x2.new(-0.5, -0.9).into()
    val d = f64x2.new(-1.0, -1.0)
    val r = f64x2.from(vecFloor(a))
    check(d == r)
}

public fun testVecCeilF32() {
    val a: STL = f32x4.new(0.1, 0.5, 0.6, 0.9).into()
    val d = f32x4.new(1.0, 1.0, 1.0, 1.0)
    val r = f32x4.from(vecCeil(a))
    check(d == r)
}

public fun testVecCeilF641() {
    val a: STL = f64x2.new(0.1, 0.5).into()
    val d = f64x2.new(1.0, 1.0)
    val r = f64x2.from(vecCeil(a))
    check(d == r)
}

public fun testVecCeilF642() {
    val a: STL = f64x2.new(0.6, 0.9).into()
    val d = f64x2.new(1.0, 1.0)
    val r = f64x2.from(vecCeil(a))
    check(d == r)
}

public fun testVecRoundF32() {
    val a: STL = f32x4.new(0.1, 0.5, 0.6, 0.9).into()
    val d = f32x4.new(0.0, 0.0, 1.0, 1.0)
    val r = f32x4.from(vecRound(a))
    check(d == r)
}

public fun testVecRoundF32EvenOdd() {
    val a: STL = f32x4.new(0.5, 1.5, 2.5, 3.5).into()
    val d = f32x4.new(0.0, 2.0, 2.0, 4.0)
    val r = f32x4.from(vecRound(a))
    check(d == r)
}

public fun testVecRoundF641() {
    val a: STL = f64x2.new(0.1, 0.5).into()
    val d = f64x2.new(0.0, 0.0)
    val r = f64x2.from(vecRound(a))
    check(d == r)
}

public fun testVecRoundF642() {
    val a: STL = f64x2.new(0.6, 0.9).into()
    val d = f64x2.new(1.0, 1.0)
    val r = f64x2.from(vecRound(a))
    check(d == r)
}

public fun testVecRoundcF32() {
    val a: STL = f32x4.new(0.1, 0.5, 0.6, 0.9).into()
    val d = f32x4.new(0.0, 0.0, 1.0, 1.0)
    val r = f32x4.from(vecRoundc(a))
    check(d == r)
}

public fun testVecRoundcF32EvenOdd() {
    val a: STL = f32x4.new(0.5, 1.5, 2.5, 3.5).into()
    val d = f32x4.new(0.0, 2.0, 2.0, 4.0)
    val r = f32x4.from(vecRoundc(a))
    check(d == r)
}

public fun testVecRoundcF641() {
    val a: STL = f64x2.new(0.1, 0.5).into()
    val d = f64x2.new(0.0, 0.0)
    val r = f64x2.from(vecRoundc(a))
    check(d == r)
}

public fun testVecRoundcF642() {
    val a: STL = f64x2.new(0.6, 0.9).into()
    val d = f64x2.new(1.0, 1.0)
    val r = f64x2.from(vecRoundc(a))
    check(d == r)
}

public fun testVecRintF32() {
    val a: STL = f32x4.new(0.1, 0.5, 0.6, 0.9).into()
    val d = f32x4.new(0.0, 0.0, 1.0, 1.0)
    val r = f32x4.from(vecRint(a))
    check(d == r)
}

public fun testVecRintF32EvenOdd() {
    val a: STL = f32x4.new(0.5, 1.5, 2.5, 3.5).into()
    val d = f32x4.new(0.0, 2.0, 2.0, 4.0)
    val r = f32x4.from(vecRint(a))
    check(d == r)
}

public fun testVecRintF641() {
    val a: STL = f64x2.new(0.1, 0.5).into()
    val d = f64x2.new(0.0, 0.0)
    val r = f64x2.from(vecRint(a))
    check(d == r)
}

public fun testVecRintF642() {
    val a: STL = f64x2.new(0.6, 0.9).into()
    val d = f64x2.new(1.0, 1.0)
    val r = f64x2.from(vecRint(a))
    check(d == r)
}

public fun testVecSll() {
    val a: STL = i32x4.new(1, 1, 1, 1).into()
    val b: STL = u8x16.new(0, 0, 0, 2, 0, 0, 0, 3, 0, 0, 0, 4, 0, 0, 0, 8).into()
    val d = i32x4.new(1 shl 2, 1 shl 3, 1 shl 4, 1)
    val r = i32x4.from(vecSll(a, b))
    check(d == r)
}

public fun testVecSrl() {
    val a: STL = i32x4.new(0x8, 0x8, 0x8, 0x8).into()
    val b: STL = u8x16.new(0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 3, 0, 0, 0, 16).into()
    val d = i32x4.new(4, 2, 1, 8)
    val r = i32x4.from(vecSrl(a, b))
    check(d == r)
}

public fun testVecSralPos() {
    val a: STL = u32x4.new(0x8, 0x8, 0x8, 0x8).into()
    val b: STL = u8x16.new(0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 3, 0, 0, 0, 16).into()
    val d = u32x4.new(4, 2, 1, 8)
    val r = u32x4.from(vecSral(a, b))
    check(d == r)
}

public fun testVecSralNeg() {
    val a: STL = i32x4.new(-8, -8, -8, -8).into()
    val b: STL = u8x16.new(0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 3, 0, 0, 0, 16).into()
    val d = i32x4.new(-4, -2, -1, -8)
    val r = i32x4.from(vecSral(a, b))
    check(d == r)
}

public fun testVecReveF32() {
    val a: STL = f32x4.new(0.1, 0.5, 0.6, 0.9).into()
    val d = f32x4.new(0.9, 0.6, 0.5, 0.1)
    val r = f32x4.from(vecReve(a))
    check(d == r)
}

public fun testVecRevbU32() {
    val a: STL = u32x4.new(0xAABBCCDD, 0xEEFF0011, 0x22334455, 0x66778899).into()
    val d = u32x4.new(0xDDCCBBAA, 0x1100FFEE, 0x55443322, 0x99887766)
    val r = u32x4.from(vecRevb(a))
    check(d == r)
}

public fun testVecMergehU32() {
    val a: STL = u32x4.new(0xAAAAAAAA, 0xBBBBBBBB, 0xCCCCCCCC, 0xDDDDDDDD).into()
    val b: STL = u32x4.new(0x00000000, 0x11111111, 0x22222222, 0x33333333).into()
    val d = u32x4.new(0xAAAAAAAA, 0x00000000, 0xBBBBBBBB, 0x11111111)
    val r = u32x4.from(vecMergeh(a, b))
    check(d == r)
}

public fun testVecMergelU32() {
    val a: STL = u32x4.new(0xAAAAAAAA, 0xBBBBBBBB, 0xCCCCCCCC, 0xDDDDDDDD).into()
    val b: STL = u32x4.new(0x00000000, 0x11111111, 0x22222222, 0x33333333).into()
    val d = u32x4.new(0xCCCCCCCC, 0x22222222, 0xDDDDDDDD, 0x33333333)
    val r = u32x4.from(vecMergel(a, b))
    check(d == r)
}

public fun testVecPermU8x16() {
    val a = VectorUnsignedChar.from(u8x16.fromArray(listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)))
    val b = VectorUnsignedChar.from(u8x16.fromArray(listOf(100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115)))
    val c = VectorUnsignedChar.from(u8x16.fromArray(listOf(0x00, 0x01, 0x10, 0x11, 0x02, 0x03, 0x12, 0x13, 0x04, 0x05, 0x14, 0x15, 0x06, 0x07, 0x16, 0x17)))
    val d = u8x16.fromArray(listOf(0, 1, 100, 101, 2, 3, 102, 103, 4, 5, 104, 105, 6, 7, 106, 107))
    val r = u8x16.from(vecPerm(a, b, c))
    check(d == r)
}

public fun testVecPermI8x16() {
    val a = VectorSignedChar.from(i8x16.fromArray(listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)))
    val b = VectorSignedChar.from(i8x16.fromArray(listOf(100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115)))
    val c = VectorUnsignedChar.from(u8x16.fromArray(listOf(0x00, 0x01, 0x10, 0x11, 0x02, 0x03, 0x12, 0x13, 0x04, 0x05, 0x14, 0x15, 0x06, 0x07, 0x16, 0x17)))
    val d = i8x16.fromArray(listOf(0, 1, 100, 101, 2, 3, 102, 103, 4, 5, 104, 105, 6, 7, 106, 107))
    val r = i8x16.from(vecPerm(a, b, c))
    check(d == r)
}

public fun testVecPermM8x16() {
    val a = VectorBoolChar.from(m8x16.fromArray(booleanArrayOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false)))
    val b = VectorBoolChar.from(m8x16.fromArray(booleanArrayOf(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)))
    val c = VectorUnsignedChar.from(u8x16.fromArray(listOf(0x00, 0x01, 0x10, 0x11, 0x02, 0x03, 0x12, 0x13, 0x04, 0x05, 0x14, 0x15, 0x06, 0x07, 0x16, 0x17)))
    val d = m8x16.fromArray(booleanArrayOf(false, false, true, true, false, false, true, true, false, false, true, true, false, false, true, true))
    val r = m8x16.from(vecPerm(a, b, c))
    check(d == r)
}

public fun testVecPermU16x8() {
    val a = VectorUnsignedShort.from(u16x8.fromArray(listOf(0, 1, 2, 3, 4, 5, 6, 7)))
    val b = VectorUnsignedShort.from(u16x8.fromArray(listOf(10, 11, 12, 13, 14, 15, 16, 17)))
    val c = VectorUnsignedChar.from(u8x16.fromArray(listOf(0x00, 0x01, 0x10, 0x11, 0x02, 0x03, 0x12, 0x13, 0x04, 0x05, 0x14, 0x15, 0x06, 0x07, 0x16, 0x17)))
    val d = u16x8.fromArray(listOf(0, 10, 1, 11, 2, 12, 3, 13))
    val r = u16x8.from(vecPerm(a, b, c))
    check(d == r)
}

public fun testVecPermI16x8() {
    val a = VectorSignedShort.from(i16x8.fromArray(listOf(0, 1, 2, 3, 4, 5, 6, 7)))
    val b = VectorSignedShort.from(i16x8.fromArray(listOf(10, 11, 12, 13, 14, 15, 16, 17)))
    val c = VectorUnsignedChar.from(u8x16.fromArray(listOf(0x00, 0x01, 0x10, 0x11, 0x02, 0x03, 0x12, 0x13, 0x04, 0x05, 0x14, 0x15, 0x06, 0x07, 0x16, 0x17)))
    val d = i16x8.fromArray(listOf(0, 10, 1, 11, 2, 12, 3, 13))
    val r = i16x8.from(vecPerm(a, b, c))
    check(d == r)
}

public fun testVecPermM16x8() {
    val a = VectorBoolShort.from(m16x8.fromArray(booleanArrayOf(false, false, false, false, false, false, false, false)))
    val b = VectorBoolShort.from(m16x8.fromArray(booleanArrayOf(true, true, true, true, true, true, true, true)))
    val c = VectorUnsignedChar.from(u8x16.fromArray(listOf(0x00, 0x01, 0x10, 0x11, 0x02, 0x03, 0x12, 0x13, 0x04, 0x05, 0x14, 0x15, 0x06, 0x07, 0x16, 0x17)))
    val d = m16x8.fromArray(booleanArrayOf(false, true, false, true, false, true, false, true))
    val r = m16x8.from(vecPerm(a, b, c))
    check(d == r)
}

public fun testVecPermU32x4() {
    val a = VectorUnsignedInt.from(u32x4.fromArray(listOf(0, 1, 2, 3)))
    val b = VectorUnsignedInt.from(u32x4.fromArray(listOf(10, 11, 12, 13)))
    val c = VectorUnsignedChar.from(u8x16.fromArray(listOf(0x00, 0x01, 0x02, 0x03, 0x10, 0x11, 0x12, 0x13, 0x04, 0x05, 0x06, 0x07, 0x14, 0x15, 0x16, 0x17)))
    val d = u32x4.fromArray(listOf(0, 10, 1, 11))
    val r = u32x4.from(vecPerm(a, b, c))
    check(d == r)
}

public fun testVecPermI32x4() {
    val a = VectorSignedInt.from(i32x4.fromArray(listOf(0, 1, 2, 3)))
    val b = VectorSignedInt.from(i32x4.fromArray(listOf(10, 11, 12, 13)))
    val c = VectorUnsignedChar.from(u8x16.fromArray(listOf(0x00, 0x01, 0x02, 0x03, 0x10, 0x11, 0x12, 0x13, 0x04, 0x05, 0x06, 0x07, 0x14, 0x15, 0x16, 0x17)))
    val d = i32x4.fromArray(listOf(0, 10, 1, 11))
    val r = i32x4.from(vecPerm(a, b, c))
    check(d == r)
}

public fun testVecPermM32x4() {
    val a = VectorBoolInt.from(m32x4.fromArray(booleanArrayOf(false, false, false, false)))
    val b = VectorBoolInt.from(m32x4.fromArray(booleanArrayOf(true, true, true, true)))
    val c = VectorUnsignedChar.from(u8x16.fromArray(listOf(0x00, 0x01, 0x02, 0x03, 0x10, 0x11, 0x12, 0x13, 0x04, 0x05, 0x06, 0x07, 0x14, 0x15, 0x16, 0x17)))
    val d = m32x4.fromArray(booleanArrayOf(false, true, false, true))
    val r = m32x4.from(vecPerm(a, b, c))
    check(d == r)
}

public fun testVecPermF32x4() {
    val a = VectorFloat.from(f32x4.fromArray(listOf(0.0, 1.0, 2.0, 3.0)))
    val b = VectorFloat.from(f32x4.fromArray(listOf(1.0, 1.1, 1.2, 1.3)))
    val c = VectorUnsignedChar.from(u8x16.fromArray(listOf(0x00, 0x01, 0x02, 0x03, 0x10, 0x11, 0x12, 0x13, 0x04, 0x05, 0x06, 0x07, 0x14, 0x15, 0x16, 0x17)))
    val d = f32x4.fromArray(listOf(0.0, 1.0, 1.0, 1.1))
    val r = f32x4.from(vecPerm(a, b, c))
    check(d == r)
}

public fun testVecSqrt() {
    val a: STL = f32x4.new(3.1415927f, 1.0, 25.0, 2.0).into()
    val d = f32x4.new(kmSqrtf32(3.1415927f), 1.0, 5.0, 1.4142135f)
    val r = f32x4.from(vecSqrt(a))
    check(d == r)
}

public fun testVecFindAnyEq() {
    val a: STL = i32x4.new(1, -2, 3, -4).into()
    val b: STL = i32x4.new(-5, 3, -7, 8).into()
    val d = i32x4.new(0, 0, 0.inv(), 0)
    val r = i32x4.from(vecFindAnyEq(a, b))
    check(d == r)
}

public fun testVecFindAnyNe() {
    val a: STL = i32x4.new(1, -2, 3, -4).into()
    val b: STL = i32x4.new(-5, 3, -7, 8).into()
    val d = i32x4.new(0.inv(), 0.inv(), 0, 0.inv())
    val r = i32x4.from(vecFindAnyNe(a, b))
    check(d == r)
}

public fun testVecFindAnyEqIdx1() {
    val a: STL = i32x4.new(1, 2, 3, 4).into()
    val b: STL = i32x4.new(5, 3, 7, 8).into()
    val d = i32x4.new(0, 8, 0, 0)
    val r = i32x4.from(vecFindAnyEqIdx(a, b))
    check(d == r)
}

public fun testVecFindAnyEqIdx2() {
    val a: STL = i32x4.new(1, 2, 3, 4).into()
    val b: STL = i32x4.new(5, 6, 7, 8).into()
    val d = i32x4.new(0, 16, 0, 0)
    val r = i32x4.from(vecFindAnyEqIdx(a, b))
    check(d == r)
}

public fun testVecFindAnyNeIdx1() {
    val a: STL = i32x4.new(1, 2, 3, 4).into()
    val b: STL = i32x4.new(1, 5, 3, 4).into()
    val d = i32x4.new(0, 4, 0, 0)
    val r = i32x4.from(vecFindAnyNeIdx(a, b))
    check(d == r)
}

public fun testVecFindAnyNeIdx2() {
    val a: STL = i32x4.new(1, 2, 3, 4).into()
    val b: STL = i32x4.new(1, 2, 3, 4).into()
    val d = i32x4.new(0, 16, 0, 0)
    val r = i32x4.from(vecFindAnyNeIdx(a, b))
    check(d == r)
}

public fun testVecFindAnyEqOr0Idx1() {
    val a: STL = i32x4.new(1, 2, 0, 4).into()
    val b: STL = i32x4.new(5, 6, 7, 8).into()
    val d = i32x4.new(0, 8, 0, 0)
    val r = i32x4.from(vecFindAnyEqOr0Idx(a, b))
    check(d == r)
}

public fun testVecFindAnyNeOr0Idx1() {
    val a: STL = i32x4.new(1, 2, 0, 4).into()
    val b: STL = i32x4.new(1, 2, 3, 4).into()
    val d = i32x4.new(0, 8, 0, 0)
    val r = i32x4.from(vecFindAnyNeOr0Idx(a, b))
    check(d == r)
}

public fun testVecFindAnyEqCc() {
    val a = VectorUnsignedInt(listOf(1, 2, 3, 4))
    val b = VectorUnsignedInt(listOf(5, 3, 7, 8))
    val (d, c) = vecFindAnyEqCc(a, b)
    check(c == 1)
    check(d.asArray() == listOf(0, 0, -1, 0))
    val a1 = VectorUnsignedInt(listOf(1, 2, 3, 4))
    val b1 = VectorUnsignedInt(listOf(5, 6, 7, 8))
    val (d1, c1) = vecFindAnyEqCc(a1, b1)
    check(c1 == 3)
    check(d1.asArray() == listOf(0, 0, 0, 0))
}

public fun testVecFindAnyNeCc() {
    val a = VectorUnsignedInt(listOf(1, 2, 3, 4))
    val b = VectorUnsignedInt(listOf(5, 3, 7, 8))
    val (d, c) = vecFindAnyNeCc(a, b)
    check(c == 1)
    check(d.asArray() == listOf(-1, -1, 0, -1))
    val a1 = VectorUnsignedInt(listOf(1, 2, 3, 4))
    val b1 = VectorUnsignedInt(listOf(1, 2, 3, 4))
    val (d1, c1) = vecFindAnyNeCc(a1, b1)
    check(c1 == 3)
    check(d1.asArray() == listOf(0, 0, 0, 0))
}

public fun testVecFindAnyEqIdxCc() {
    val a = VectorUnsignedInt(listOf(1, 2, 3, 4))
    val b = VectorUnsignedInt(listOf(5, 3, 7, 8))
    val (d, c) = vecFindAnyEqIdxCc(a, b)
    check(c == 1)
    check(d.asArray() == listOf(0, 8, 0, 0))
    val a1 = VectorUnsignedInt(listOf(1, 2, 3, 4))
    val b1 = VectorUnsignedInt(listOf(5, 6, 7, 8))
    val (d1, c1) = vecFindAnyEqIdxCc(a1, b1)
    check(c1 == 3)
    check(d1.asArray() == listOf(0, 16, 0, 0))
}

public fun testVecFindAnyNeIdxCc() {
    val a = VectorUnsignedInt(listOf(5, 2, 3, 4))
    val b = VectorUnsignedInt(listOf(5, 3, 7, 8))
    val (d, c) = vecFindAnyNeIdxCc(a, b)
    check(c == 1)
    check(d.asArray() == listOf(0, 4, 0, 0))
    val a1 = VectorUnsignedInt(listOf(1, 2, 3, 4))
    val b1 = VectorUnsignedInt(listOf(1, 2, 3, 4))
    val (d1, c1) = vecFindAnyNeIdxCc(a1, b1)
    check(c1 == 3)
    check(d1.asArray() == listOf(0, 16, 0, 0))
}

public fun testVecFindAnyEqOr0IdxCc() {
    // if no element of a matches any element of b with an equal value, and there is at least one element from a with a value of 0
    val a = VectorUnsignedInt(listOf(0, 1, 2, 3))
    val b = VectorUnsignedInt(listOf(4, 5, 6, 7))
    val (d, c) = vecFindAnyEqOr0IdxCc(a, b)
    check(c == 0)
    check(d.asArray() == listOf(0, 0, 0, 0))
    // if at least one element of a matches any element of b with an equal value, and no elements of a with a value of 0
    val a1 = VectorUnsignedInt(listOf(1, 2, 3, 4))
    val b1 = VectorUnsignedInt(listOf(5, 2, 3, 4))
    val (d1, c1) = vecFindAnyEqOr0IdxCc(a1, b1)
    check(c1 == 1)
    check(d1.asArray() == listOf(0, 4, 0, 0))
    // if at least one element of a matches any element of b with an equal value, and there is at least one element from a has a value of 0
    val a2 = VectorUnsignedInt(listOf(1, 2, 3, 0))
    val b2 = VectorUnsignedInt(listOf(1, 2, 3, 4))
    val (d2, c2) = vecFindAnyEqOr0IdxCc(a2, b2)
    check(c2 == 2)
    check(d2.asArray() == listOf(0, 0, 0, 0))
    // if no element of a matches any element of b with an equal value, and there is no element from a with a value of 0.
    val a3 = VectorUnsignedInt(listOf(1, 2, 3, 4))
    val b3 = VectorUnsignedInt(listOf(5, 6, 7, 8))
    val (d3, c3) = vecFindAnyEqOr0IdxCc(a3, b3)
    check(c3 == 3)
    check(d3.asArray() == listOf(0, 16, 0, 0))
}

public fun testVecFindAnyNeOr0IdxCc() {
    // if no element of a matches any element of b with a not equal value, and there is at least one element from a with a value of 0.
    val a = VectorUnsignedInt(listOf(0, 1, 2, 3))
    val b = VectorUnsignedInt(listOf(4, 1, 2, 3))
    val (d, c) = vecFindAnyNeOr0IdxCc(a, b)
    check(c == 0)
    check(d.asArray() == listOf(0, 0, 0, 0))
    // if at least one element of a matches any element of b with a not equal value, and no elements of a with a value of 0.
    val a1 = VectorUnsignedInt(listOf(4, 2, 3, 4))
    val b1 = VectorUnsignedInt(listOf(4, 5, 6, 7))
    val (d1, c1) = vecFindAnyNeOr0IdxCc(a1, b1)
    check(c1 == 1)
    check(d1.asArray() == listOf(0, 4, 0, 0))
    // if at least one element of a matches any element of b with a not equal value, and there is at least one element from a has a value of 0.
    val a2 = VectorUnsignedInt(listOf(1, 0, 1, 1))
    val b2 = VectorUnsignedInt(listOf(4, 5, 6, 7))
    val (d2, c2) = vecFindAnyNeOr0IdxCc(a2, b2)
    check(c2 == 2)
    check(d2.asArray() == listOf(0, 0, 0, 0))
    // if no element of a matches any element of b with a not equal value, and there is no element from a with a value of 0.
    val a3 = VectorUnsignedInt(listOf(4, 4, 4, 4))
    val b3 = VectorUnsignedInt(listOf(4, 5, 6, 7))
    val (d3, c3) = vecFindAnyNeOr0IdxCc(a3, b3)
    check(c3 == 3)
    check(d3.asArray() == listOf(0, 16, 0, 0))
}

public fun testVectorLoad() {
    val expected = listOf(0xAAAAAAAA, 0xBBBBBBBB, 0xCCCCCCCC, 0xDDDDDDDD)
    val source: UIntArray = uintArrayOf(0xAAAAAAAAu, 0xBBBBBBBBu, 0xCCCCCCCCu, 0xDDDDDDDDu, 0u, 0u, 0u, 0u)
    check(vecXl<VectorUnsignedInt>(0, source.asPtr()).asArray() == expected)
    // offset is in bytes
    val source1: UIntArray = uintArrayOf(0x0000AAAAu, 0xAAAABBBBu, 0xBBBBCCCCu, 0xCCCCDDDDu, 0xDDDD0000u, 0u, 0u, 0u)
    check(vecXl<VectorUnsignedInt>(2, source1.asPtr()).asArray() == expected)
}

public fun testVectorStore() {
    val vec = VectorUnsignedInt(listOf(0xAAAAAAAA, 0xBBBBBBBB, 0xCCCCCCCC, 0xDDDDDDDD))
    val localStorageL6751C8 = AddressableArrayLocal.fromUIntArray(UIntArray(8) { 0u })
    var dest by localStorageL6751C8
    vecXst(vec, 0, localStorageL6751C8.elementMutPtr(0))
    check(dest == uintArrayOf(0xAAAAAAAAu, 0xBBBBBBBBu, 0xCCCCCCCCu, 0xDDDDDDDDu, 0u, 0u, 0u, 0u))
    // offset is in bytes
    val localStorageL6768C8 = AddressableArrayLocal.fromUIntArray(UIntArray(8) { 0u })
    var dest1 by localStorageL6768C8
    vecXst(vec, 2, localStorageL6768C8.elementMutPtr(0))
    check(dest1 == uintArrayOf(0x0000AAAAu, 0xAAAABBBBu, 0xBBBBCCCCu, 0xCCCCDDDDu, 0xDDDD0000u, 0u, 0u, 0u))
}

public fun testVectorLcbb() {
    open class Align64<T>(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<T> = UnresolvedTypeLayout(),
    ) {
        public var field0: T
            get() = value
            set(v) {
                value = v
            }

        public var value: T
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(value: T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, AlignedTypeLayout(fieldLayout, 64L)), AlignedTypeLayout(fieldLayout, 64L))

        public fun pointerLayout(): TypeLayout<Align64<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align64<T>(ptr, layout) }, { it.value })
    }
    val ARRAY: Align64<UByteArray>  = Align64(List(128) { 0 }, fieldLayout = UnresolvedTypeLayout<T>())
    check(lcbb(ARRAY.first[64 until ARRAY.first.size].asPtr(), 64) == 16u)
    check(lcbb(ARRAY.first[63 until ARRAY.first.size].asPtr(), 64) == 1u)
    check(lcbb(ARRAY.first[56 until ARRAY.first.size].asPtr(), 64) == 8u)
    check(lcbb(ARRAY.first[48 until ARRAY.first.size].asPtr(), 64) == 16u)
}

public fun testVecPack() {
    val a: STL = i16x8.new(0, 1, -1, 42, 32767, -32768, 30000, -30000).into()
    val b: STL = i16x8.new(32767, -32768, 12345, -12345, 0, 1, -1, 42).into()
    val d = i8x16.new(0, 1, -1, 42, -1, 0, 48, -48, -1, 0, 57, -57, 0, 1, -1, 42)
    val r = i8x16.from(vecPack(a, b))
    check(d == r)
}

public fun testVecPacks() {
    val a: STL = i16x8.new(0, 1, -1, 42, 32767, -32768, 30000, -30000).into()
    val b: STL = i16x8.new(32767, -32768, 12345, -12345, 0, 1, -1, 42).into()
    val d = i8x16.new(0, 1, -1, 42, 127, -128, 127, -128, 127, -128, 127, -128, 0, 1, -1, 42)
    val r = i8x16.from(vecPacks(a, b))
    check(d == r)
}

public fun testVecPacksuSigned() {
    val a: STL = i16x8.new(0, 1, -1, 42, 32767, -32768, 30000, -30000).into()
    val b: STL = i16x8.new(32767, -32768, 12345, -12345, 0, 1, -1, 42).into()
    val d = u8x16.new(0, 1, 0, 42, 255, 0, 255, 0, 255, 0, 255, 0, 0, 1, 0, 42)
    val r = u8x16.from(vecPacksu(a, b))
    check(d == r)
}

public fun testVecPacksuUnsigned() {
    val a: STL = u16x8.new(65535, 32768, 1234, 5678, 16, 8, 4, 2).into()
    val b: STL = u16x8.new(30000, 25000, 20000, 15000, 31, 63, 127, 255).into()
    val d = u8x16.new(255, 255, 255, 255, 16, 8, 4, 2, 255, 255, 255, 255, 31, 63, 127, 255)
    val r = u8x16.from(vecPacksu(a, b))
    check(d == r)
}

public fun testVecRl() {
    val a: STL = u32x4.new(0x12345678, 0x9ABCDEF0, 0x0F0F0F0F, 0x12345678).into()
    val b: STL = u32x4.new(4, 8, 12, 68).into()
    val d = u32x4.new(0x23456781, 0xBCDEF09A, 0xF0F0F0F0, 0x23456781)
    val r = u32x4.from(vecRl(a, b))
    check(d == r)
}

public fun testVecUnpackhI() {
    val a: STL = i16x8.new(0x1234, -2, 0x0F0F, -32768, 0, 0, 0, 0).into()
    val d = i32x4.new(0x1234, -2, 0x0F0F, -32768)
    val r = i32x4.from(vecUnpackh(a))
    check(d == r)
}

public fun testVecUnpackhU() {
    val a: STL = u16x8.new(0x1234, 0xFFFF, 0x0F0F, 0x8000, 0, 0, 0, 0).into()
    val d = u32x4.new(0x1234, 0xFFFF, 0x0F0F, 0x8000)
    val r = u32x4.from(vecUnpackh(a))
    check(d == r)
}

public fun testVecUnpacklI() {
    val a: STL = i16x8.new(0, 0, 0, 0, 0x1234, -2, 0x0F0F, -32768).into()
    val d = i32x4.new(0x1234, -2, 0x0F0F, -32768)
    val r = i32x4.from(vecUnpackl(a))
    check(d == r)
}

public fun testVecUnpacklU() {
    val a: STL = u16x8.new(0, 0, 0, 0, 0x1234, 0xFFFF, 0x0F0F, 0x8000).into()
    val d = u32x4.new(0x1234, 0xFFFF, 0x0F0F, 0x8000)
    val r = u32x4.from(vecUnpackl(a))
    check(d == r)
}

public fun testVecAvg() {
    val a: STL = u32x4.new(2, 1, UInt.MAX_VALUE, 0).into()
    val b: STL = u32x4.new(4, 2, 2, 0).into()
    val d = u32x4.new(3, (1u + 2u).divCeil(2), (UInt.MAX_VALUE.toULong() + 2uL).divCeil(2).toUInt(), 0)
    val r = u32x4.from(vecAvg(a, b))
    check(d == r)
}

public fun testVecChecksum() {
    val a: STL = u32x4.new(1, 2, 3, UInt.MAX_VALUE).into()
    val b: STL = u32x4.new(5, 6, 7, 8).into()
    val d = u32x4.new(0, 12, 0, 0)
    val r = u32x4.from(vecChecksum(a, b))
    check(d == r)
}

public fun testVecAddU128() {
    val a: STL = u8x16.new(0x01, 0x05, 0x0F, 0x1A, 0x2F, 0x3F, 0x50, 0x65, 0x7A, 0x8F, 0x9A, 0xAD, 0xB0, 0xC3, 0xD5, 0xE8).into()
    val b: STL = u8x16.new(0xF0, 0xEF, 0xC3, 0xB1, 0x92, 0x71, 0x5A, 0x43, 0x3B, 0x29, 0x13, 0x04, 0xD7, 0xA1, 0x8C, 0x76).into()
    val d = u8x16.new(0xF1, 0xF4, 0xD2, 0xCB, 0xC1, 0xB0, 0xAA, 0xA8, 0xB5, 0xB8, 0xAD, 0xB2, 0x88, 0x65, 0x62, 0x5E)
    val r = u8x16.from(vecAddU128(a, b))
    check(d == r)
}

public fun testVecAddcU128() {
    run {
        val a = U128.MAX_VALUE
        val b = U128(0x0uL, 0x1uL)
        val d: U128 = transmute(vecAddcU128(transmute(a), transmute(b)))
        check(a.checkedAdd(b) == null)
        check(d == U128(0x0uL, 0x1uL))
        val a1 = U128(0x0uL, 0x1uL)
        val b1 = U128(0x0uL, 0x1uL)
        val d1: U128 = transmute(vecAddcU128(transmute(a1), transmute(b1)))
        check(a1.checkedAdd(b1) != null)
        check(d1 == U128(0x0uL, 0x0uL))
    }
}

public fun testVecSubcU128() {
    run {
        val a = U128(0x0uL, 0x0uL)
        val b = U128(0x0uL, 0x1uL)
        val d: U128 = transmute(vecSubcU128(transmute(a), transmute(b)))
        check(a.checkedSub(b) == null)
        check(d == U128(0x0uL, 0x0uL))
        val a1 = U128(0x0uL, 0x1uL)
        val b1 = U128(0x0uL, 0x1uL)
        val d1: U128 = transmute(vecSubcU128(transmute(a1), transmute(b1)))
        check(a1.checkedSub(b1) != null)
        check(d1 == U128(0x0uL, 0x1uL))
    }
}

public fun testVecMuleU() {
    val a: STL = u16x8.new(0xFFFF, 0, 2, 0, 2, 0, 1, 0).into()
    val b: STL = u16x8.new(0xFFFF, 0, 4, 0, 0xFFFF, 0, 2, 0).into()
    val d = u32x4.new(0xFFFE0001, 8, 0x0001FFFE, 2)
    val r = u32x4.from(vecMule(a, b))
    check(d == r)
}

public fun testVecMuleI() {
    val a: STL = i16x8.new(Short.MIN_VALUE, 0, -2, 0, 2, 0, 1, 0).into()
    val b: STL = i16x8.new(Short.MIN_VALUE, 0, 4, 0, Short.MAX_VALUE, 0, 2, 0).into()
    val d = i32x4.new(0x40000000, -8, 0xFFFE, 2)
    val r = i32x4.from(vecMule(a, b))
    check(d == r)
}

public fun testVecMuloU() {
    val a: STL = u16x8.new(0, 0xFFFF, 0, 2, 0, 2, 0, 1).into()
    val b: STL = u16x8.new(0, 0xFFFF, 0, 4, 0, 0xFFFF, 0, 2).into()
    val d = u32x4.new(0xFFFE0001, 8, 0x0001FFFE, 2)
    val r = u32x4.from(vecMulo(a, b))
    check(d == r)
}

public fun testVecMuloI() {
    val a: STL = i16x8.new(0, Short.MIN_VALUE, 0, -2, 0, 2, 0, 1).into()
    val b: STL = i16x8.new(0, Short.MIN_VALUE, 0, 4, 0, Short.MAX_VALUE, 0, 2).into()
    val d = i32x4.new(0x40000000, -8, 0xFFFE, 2)
    val r = i32x4.from(vecMulo(a, b))
    check(d == r)
}

public fun testVecMulhU() {
    val a: STL = u32x4.new(UInt.MAX_VALUE, 2, 2, 1).into()
    val b: STL = u32x4.new(UInt.MAX_VALUE, 4, UInt.MAX_VALUE, 2).into()
    val d = u32x4.new(UInt.MAX_VALUE - 1u, 0, 1, 0)
    val r = u32x4.from(vecMulh(a, b))
    check(d == r)
}

public fun testVecMulhI() {
    val a: STL = i32x4.new(Int.MIN_VALUE, -2, 2, 1).into()
    val b: STL = i32x4.new(Int.MIN_VALUE, 4, Int.MAX_VALUE, 2).into()
    val d = i32x4.new(0x40000000, -1, 0, 0)
    val r = i32x4.from(vecMulh(a, b))
    check(d == r)
}

public fun testVecGfmsum1() {
    val a: STL = u16x8.new(0x1234, 0x5678, 0x9ABC, 0xDEF0, 0x1357, 0x2468, 0xACE0, 0xBDF0).into()
    val b: STL = u16x8.new(0xFFFF, 0x0001, 0x8000, 0x7FFF, 0xAAAA, 0x5555, 0x1234, 0x5678).into()
    val d = u32x4.new(0xE13A794, 0x68764A50, 0x94AA3E, 0x2C93F300)
    val r = u32x4.from(vecGfmsum(a, b))
    check(d == r)
}

public fun testVecGfmsum2() {
    val a: STL = u16x8.new(0x0000, 0xFFFF, 0xAAAA, 0x5555, 0x1234, 0x5678, 0x9ABC, 0xDEF0).into()
    val b: STL = u16x8.new(0xFFFF, 0x0000, 0x5555, 0xAAAA, 0x0001, 0x8000, 0x7FFF, 0x1357).into()
    val d = u32x4.new(0, 0, 0x2B3C1234, 0x3781D244)
    val r = u32x4.from(vecGfmsum(a, b))
    check(d == r)
}

public fun testVecGfmsum128() {
    val a = VectorUnsignedLongLong(listOf(1, 2))
    val b = VectorUnsignedLongLong(listOf(3, 4))
    val d: U128 = transmute(vecGfmsum128(a, b))
    check(d == U128(0x0uL, 0xbuL))
    val a1 = VectorUnsignedLongLong(listOf(0x0101010101010101, 0x0202020202020202))
    val b1 = VectorUnsignedLongLong(listOf(0x0404040404040404, 0x0505050505050505))
    val d1: U128 = transmute(vecGfmsum128(a1, b1))
    check(d1 == U128(0xe000e000e000euL, 0xe000e000e000euL))
}

public fun testVecBpermU128() {
    val a = VectorUnsignedChar(listOf(65, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1))
    val b = VectorUnsignedChar(listOf(0, 0, 0, 0, 1, 1, 1, 1, 128, 128, 128, 128, 255, 255, 255, 255))
    val d = vecBpermU128(a, b)
    check(d.asArray() == listOf(0xF00, 0))
}

public fun testVecSel() {
    val a = VectorSignedInt(listOf(1, 2, 3, 4))
    val b = VectorSignedInt(listOf(5, 6, 7, 8))
    val e = VectorUnsignedInt(listOf(9, 10, 11, 12))
    val f = VectorUnsignedInt(listOf(9, 9, 11, 11))
    val c: VectorBoolInt = run { val _simdArg0 = e; val _simdArg1 = f; VectorBoolInt() }
    check(c.asArray() == listOf(0.inv(), 0, 0.inv(), 0))
    val d: VectorSignedInt = vecSel(a, b, c)
    check((d.asArray()).contentEquals(listOf(5, 2, 7, 4)))
}

public fun testVecGatherElement() {
    val localStorageL6995C8 = AddressableArrayLocal.fromUIntArray(uintArrayOf(10u, 11u, 12u, 13u, 14u, 15u, 16u, 17u, 18u, 19u))
    val localStorageL6996C8 = AddressableArrayLocal.fromUIntArray(uintArrayOf(20u, 21u, 22u, 23u, 24u, 25u, 26u, 27u, 28u, 29u))
    val v1 = VectorUnsignedInt(listOf(1, 2, 3, 4))
    val v2 = VectorUnsignedInt(listOf(1, 2, 3, 4))
    val sizeofInt = sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32).toUInt()
    val v3 = VectorUnsignedInt(uintArrayOf(5u * sizeofInt, 8u * sizeofInt, 9u * sizeofInt, 6u * sizeofInt))
    run {
        val d1 = vecGatherElement(v1, v3, localStorageL6995C8.elementConstPtr(0), 0)
        check(d1.asArray() == listOf(15, 2, 3, 4))
        val d2 = vecGatherElement(v2, v3, localStorageL6996C8.elementConstPtr(0), 0)
        check(d2.asArray() == listOf(25, 2, 3, 4))
    }
}

public fun testVecFpTestDataClass() {
    val localStorageL7019C8 = AddressableLocal(42, PrimitiveLayouts.I32)
    var cc by localStorageL7019C8
    val v1 = VectorDouble(doubleArrayOf(0.0, Double.NaN))
    val v2 = VectorDouble(doubleArrayOf(Double.POSITIVE_INFINITY, 1.0))
    val v3 = VectorDouble(listOf(1.0, 2.0))
    run {
        val d = vecFpTestDataClass(v1, RustMutableReference(localStorageL7019C8.asMutPtr(), localStorageL7019C8.layout), VEC_CLASS_FP_ZERO)
        check(cc == 1)
        check(d.asArray() == listOf(0.inv(), 0))
        val d1 = vecFpTestDataClass(v1, RustMutableReference(localStorageL7019C8.asMutPtr(), localStorageL7019C8.layout), VEC_CLASS_FP_NAN)
        check(cc == 1)
        check(d1.asArray() == listOf(0, 0.inv()))
        val d2 = vecFpTestDataClass(v2, RustMutableReference(localStorageL7019C8.asMutPtr(), localStorageL7019C8.layout), VEC_CLASS_FP_INFINITY)
        check(cc == 1)
        check(d2.asArray() == listOf(0.inv(), 0))
        val d3 = vecFpTestDataClass(v2, RustMutableReference(localStorageL7019C8.asMutPtr(), localStorageL7019C8.layout), VEC_CLASS_FP_INFINITY_N)
        check(cc == 3)
        check(d3.asArray() == listOf(0, 0))
        val d4 = vecFpTestDataClass(v2, RustMutableReference(localStorageL7019C8.asMutPtr(), localStorageL7019C8.layout), VEC_CLASS_FP_NORMAL)
        check(cc == 1)
        check(d4.asArray() == listOf(0, 0.inv()))
        val d5 = vecFpTestDataClass(v3, RustMutableReference(localStorageL7019C8.asMutPtr(), localStorageL7019C8.layout), VEC_CLASS_FP_NORMAL)
        check(cc == 0)
        check(d5.asArray() == listOf(0.inv(), 0.inv()))
    }
}

public fun testVecFpAnyAllNanNumeric() {
    run {
        check(vecAllNan(VectorDouble(doubleArrayOf(Double.NaN, Double.NaN))) == Int.from(true))
        check(vecAllNan(VectorDouble(doubleArrayOf(Double.NaN, 1.0))) == Int.from(false))
        check(vecAllNan(VectorDouble(listOf(0.0, 1.0))) == Int.from(false))
        check(vecAnyNan(VectorDouble(doubleArrayOf(Double.NaN, Double.NaN))) == Int.from(true))
        check(vecAnyNan(VectorDouble(doubleArrayOf(Double.NaN, 1.0))) == Int.from(true))
        check(vecAnyNan(VectorDouble(listOf(0.0, 1.0))) == Int.from(false))
        check(vecAllNumeric(VectorDouble(doubleArrayOf(Double.NaN, Double.NaN))) == Int.from(false))
        check(vecAllNumeric(VectorDouble(doubleArrayOf(Double.NaN, 1.0))) == Int.from(false))
        check(vecAllNumeric(VectorDouble(listOf(0.0, 1.0))) == Int.from(true))
        check(vecAnyNumeric(VectorDouble(doubleArrayOf(Double.NaN, Double.NaN))) == Int.from(false))
        check(vecAnyNumeric(VectorDouble(doubleArrayOf(Double.NaN, 1.0))) == Int.from(true))
        check(vecAnyNumeric(VectorDouble(listOf(0.0, 1.0))) == Int.from(true))
        // "numeric" means "not NaN". infinities are numeric
        check(vecAllNumeric(VectorDouble(doubleArrayOf(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY))) == Int.from(true))
        check(vecAnyNumeric(VectorDouble(doubleArrayOf(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY))) == Int.from(true))
    }
}

public fun testVecTestMask() {
    run {
        val v = VectorUnsignedLongLong(List(2) { 0xFF00FF00FF00FF00uL })
        val m = VectorUnsignedLongLong(List(2) { 0x0000FF000000FF00 })
        check(vecTestMask(v, m) == 3)
        val v1 = VectorUnsignedLongLong(ULongArray(2) { ULong.MAX_VALUE })
        val m1 = VectorUnsignedLongLong(List(2) { 0 })
        check(vecTestMask(v1, m1) == 0)
        val v2 = VectorUnsignedLongLong(List(2) { 0 })
        val m2 = VectorUnsignedLongLong(ULongArray(2) { ULong.MAX_VALUE })
        check(vecTestMask(v2, m2) == 0)
        val v3 = VectorUnsignedLongLong(List(2) { 0xAAAAAAAAAAAAAAAAuL })
        val m3 = VectorUnsignedLongLong(List(2) { 0xAAAAAAAAAAAAAAAAuL })
        check(vecTestMask(v3, m3) == 3)
    }
}

public fun testVecSearchStringCc() {
    run {
        val b = VectorUnsignedChar("ABCD------------".encodeToByteArray())
        val c = VectorUnsignedChar(List(16) { 4 })
        val haystack = VectorUnsignedChar("__ABCD__________".encodeToByteArray())
        val (result, d) = vecSearchStringCc(haystack, b, c)
        check(result.asArray()[7] == 2)
        check(d == 2)
        val haystack1 = VectorUnsignedChar("___ABCD_________".encodeToByteArray())
        val (result1, d1) = vecSearchStringCc(haystack1, b, c)
        check(result1.asArray()[7] == 3)
        check(d1 == 2)
        val haystack2 = VectorUnsignedChar("________________".encodeToByteArray())
        val (result2, d2) = vecSearchStringCc(haystack2, b, c)
        check(result2.asArray()[7] == 16)
        check(d2 == 0)
        val haystack3 = VectorUnsignedChar("______\u0000_________".encodeToByteArray())
        val (result3, d3) = vecSearchStringCc(haystack3, b, c)
        check(result3.asArray()[7] == 16)
        check(d3 == 0)
        val haystack4 = VectorUnsignedChar("______\u0000__ABCD___".encodeToByteArray())
        val (result4, d4) = vecSearchStringCc(haystack4, b, c)
        check(result4.asArray()[7] == 9)
        check(d4 == 2)
    }
}

public fun testVecSearchStringUntilZeroCc() {
    run {
        val b = VectorUnsignedChar("ABCD\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000".encodeToByteArray())
        val c = VectorUnsignedChar(List(16) { 16 })
        val haystack = VectorUnsignedChar("__ABCD__________".encodeToByteArray())
        val (result, d) = vecSearchStringUntilZeroCc(haystack, b, c)
        check(result.asArray()[7] == 2)
        check(d == 2)
        val haystack1 = VectorUnsignedChar("___ABCD_________".encodeToByteArray())
        val (result1, d1) = vecSearchStringUntilZeroCc(haystack1, b, c)
        check(result1.asArray()[7] == 3)
        check(d1 == 2)
        val haystack2 = VectorUnsignedChar("________________".encodeToByteArray())
        val (result2, d2) = vecSearchStringUntilZeroCc(haystack2, b, c)
        check(result2.asArray()[7] == 16)
        check(d2 == 0)
        val haystack3 = VectorUnsignedChar("______\u0000_________".encodeToByteArray())
        val (result3, d3) = vecSearchStringUntilZeroCc(haystack3, b, c)
        check(result3.asArray()[7] == 16)
        check(d3 == 1)
        val haystack4 = VectorUnsignedChar("______\u0000__ABCD___".encodeToByteArray())
        val (result4, d4) = vecSearchStringUntilZeroCc(haystack4, b, c)
        check(result4.asArray()[7] == 16)
        check(d4 == 1)
    }
}

public fun testVecDoublee() {
    run {
        val v = VectorFloat(listOf(1.0, 2.0, 3.0, 4.0))
        check(vecDoublee(v).asArray() == listOf(1.0, 3.0))
        val v1 = VectorFloat(floatArrayOf(Float.NaN, 2.0f, Float.POSITIVE_INFINITY, 4.0f))
        val d = vecDoublee(v1)
        check(d.asArray()[0].isNaN())
        check(d.asArray()[1] == Double.POSITIVE_INFINITY)
    }
}

public fun testVecFloate() {
    // NOTE: indices 1 and 3 can have an arbitrary value. With the C version
    // these are poison values, our version initializes the memory but its
    // value still should not be relied upon by application code.
    run {
        val v = VectorDouble(listOf(1.0, 2.0))
        val d = vecFloate(v)
        check(d.asArray()[0] == 1.0f.toFloat())
        check(d.asArray()[2] == 2.0f.toFloat())
        val v1 = VectorDouble(doubleArrayOf(Double.NaN, Double.POSITIVE_INFINITY))
        val d1 = vecFloate(v1)
        check(d1.asArray()[0].isNaN())
        check(d1.asArray()[2] == Float.POSITIVE_INFINITY)
        val v2 = VectorDouble(doubleArrayOf((-Double.MAX_VALUE), Double.MAX_VALUE))
        val d2 = vecFloate(v2)
        check(d2.asArray()[0] == ((-Double.MAX_VALUE)).toFloat())
        check(d2.asArray()[2] == Double.MAX_VALUE.toFloat())
    }
}

public fun testVecExtendS64() {
    run {
        val v = VectorSignedChar(listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15))
        check(vecExtendS64(v).asArray() == listOf(7, 15))
        val v1 = VectorSignedShort(listOf(0, 1, 2, 3, 4, 5, 6, 7))
        check(vecExtendS64(v1).asArray() == listOf(3, 7))
        val v2 = VectorSignedInt(listOf(0, 1, 2, 3))
        check(vecExtendS64(v2).asArray() == listOf(1, 3))
    }
}

public fun testVecSigned() {
    run {
        val v = VectorFloat(listOf(1.0, 2.5, -2.5, -0.0))
        check(vecSigned(v).asArray() == listOf(1, 2, -2, 0))
        val v1 = VectorDouble(listOf(2.5, -2.5))
        check(vecSigned(v1).asArray() == listOf(2, -2))
    }
}

public fun testVecUnsigned() {
    // NOTE: converting a negative floating point value is UB!
    run {
        val v = VectorFloat(listOf(1.0, 2.5, 3.5, 0.0))
        check(vecUnsigned(v).asArray() == listOf(1, 2, 3, 0))
        val v1 = VectorDouble(listOf(2.5, 3.5))
        check(vecUnsigned(v1).asArray() == listOf(2, 3))
    }
}

public fun testVecCpUntilZero() {
    run {
        val v = VectorSignedInt(listOf(1, 2, 3, 4))
        val d = vecCpUntilZero(v)
        check(d.asArray() == listOf(1, 2, 3, 4))
        val v1 = VectorSignedInt(listOf(1, 2, 0, 4))
        val d1 = vecCpUntilZero(v1)
        check(d1.asArray() == listOf(1, 2, 0, 0))
    }
}

public fun testVecCpUntilZeroCc() {
    run {
        val v = VectorSignedInt(listOf(1, 2, 3, 4))
        val (d, cc) = vecCpUntilZeroCc(v)
        check(d.asArray() == listOf(1, 2, 3, 4))
        check(cc == 3)
        val v1 = VectorSignedInt(listOf(1, 2, 0, 4))
        val (d1, cc1) = vecCpUntilZeroCc(v1)
        check(d1.asArray() == listOf(1, 2, 0, 0))
        check(cc1 == 0)
    }
}

public fun testVecMsumU128() {
    val a = VectorUnsignedLongLong(listOf(1, 2))
    val b = VectorUnsignedLongLong(listOf(3, 4))
    run {
        val c: VectorUnsignedChar = transmute(U128(0x0uL, 0x64uL))
        val d: U128 = transmute(vecMsumU128(a, b, c, U128(0x0uL, 0x0uL)))
        check(d == (U128(0x0uL, 0x1uL) * U128(0x0uL, 0x3uL)) + (U128(0x0uL, 0x2uL) * U128(0x0uL, 0x4uL)) + U128(0x0uL, 0x64uL))
        val d1: U128 = transmute(vecMsumU128(a, b, c, U128(0x0uL, 0x4uL)))
        check(d1 == (U128(0x0uL, 0x1uL) * U128(0x0uL, 0x3uL)) + (U128(0x0uL, 0x2uL) * U128(0x0uL, 0x4uL)) * U128(0x0uL, 0x2uL) + U128(0x0uL, 0x64uL))
        val d2: U128 = transmute(vecMsumU128(a, b, c, U128(0x0uL, 0x8uL)))
        check(d2 == (U128(0x0uL, 0x1uL) * U128(0x0uL, 0x3uL)) * U128(0x0uL, 0x2uL) + (U128(0x0uL, 0x2uL) * U128(0x0uL, 0x4uL)) + U128(0x0uL, 0x64uL))
        val d3: U128 = transmute(vecMsumU128(a, b, c, U128(0x0uL, 0xcuL)))
        check(d3 == (U128(0x0uL, 0x1uL) * U128(0x0uL, 0x3uL)) * U128(0x0uL, 0x2uL) + (U128(0x0uL, 0x2uL) * U128(0x0uL, 0x4uL)) * U128(0x0uL, 0x2uL) + U128(0x0uL, 0x64uL))
    }
}

public fun testVecSld() {
    val a = VectorUnsignedLongLong(listOf(0xAAAAAAAAAAAAAAAAuL, 0xAAAAAAAAAAAAAAAAuL))
    val b = VectorUnsignedLongLong(listOf(0xBBBBBBBBBBBBBBBBuL, 0xBBBBBBBBBBBBBBBBuL))
    run {
        val d = vecSld(a, b, 4)
        check(d.asArray() == listOf(0xAAAAAAAAAAAAAAAAuL, 0xAAAAAAAABBBBBBBBuL))
    }
}

public fun testVecSldw() {
    val a = VectorUnsignedLongLong(listOf(0xAAAAAAAAAAAAAAAAuL, 0xAAAAAAAAAAAAAAAAuL))
    val b = VectorUnsignedLongLong(listOf(0xBBBBBBBBBBBBBBBBuL, 0xBBBBBBBBBBBBBBBBuL))
    run {
        val d = vecSldw(a, b, 1)
        check(d.asArray() == listOf(0xAAAAAAAAAAAAAAAAuL, 0xAAAAAAAABBBBBBBBuL))
    }
}

public fun testVecSldb() {
    val a = VectorUnsignedLongLong(listOf(0xAAAAAAAAAAAAAAAAuL, 0xAAAAAAAAAAAAAAAAuL))
    val b = VectorUnsignedLongLong(listOf(0xBBBBBBBBBBBBBBBBuL, 0xBBBBBBBBBBBBBBBBuL))
    run {
        val d = vecSldb(a, b, 4)
        check(d.asArray() == listOf(0xAAAAAAAAAAAAAAAAuL, 0xAAAAAAAAAAAAAAABuL))
    }
}

public fun testVecSrdb() {
    val a = VectorUnsignedLongLong(listOf(0xAAAAAAAAAAAAAAAAuL, 0xAAAAAAAAAAAAAAAAuL))
    val b = VectorUnsignedLongLong(listOf(0xBBBBBBBBBBBBBBBBuL, 0xBBBBBBBBBBBBBBBBuL))
    run {
        val d = vecSrdb(a, b, 4)
        check(d.asArray() == listOf(0xABBBBBBBBBBBBBBBuL, 0xBBBBBBBBBBBBBBBBuL))
    }
}

public val GT: UInt = 0x20000000u

public val LT: UInt = 0x40000000u

public val EQ: UInt = 0x80000000u

public fun testVecCmprg() {
    val a = VectorUnsignedInt(listOf(11, 22, 33, 44))
    val b = VectorUnsignedInt(listOf(10, 20, 30, 40))
    val c = VectorUnsignedInt(uintArrayOf(GT, LT, GT, LT))
    val d = vecCmprg(a, b, c)
    check(d.asArray() == listOf(0.inv(), 0, 0.inv(), 0))
    val c1 = VectorUnsignedInt(uintArrayOf(GT, LT, 0u, 0u))
    val d1 = vecCmprg(a, b, c1)
    check(d1.asArray() == listOf(0.inv(), 0, 0, 0))
    val a1 = VectorUnsignedInt(listOf(11, 22, 33, 30))
    val b1 = VectorUnsignedInt(listOf(10, 20, 30, 30))
    val c2 = VectorUnsignedInt(uintArrayOf(GT, LT, EQ, EQ))
    val d2 = vecCmprg(a1, b1, c2)
    check(d2.asArray() == listOf(0.inv(), 0, 0, 0.inv()))
}

public fun testVecCmpnrg() {
    val a = VectorUnsignedInt(listOf(11, 22, 33, 44))
    val b = VectorUnsignedInt(listOf(10, 20, 30, 40))
    val c = VectorUnsignedInt(uintArrayOf(GT, LT, GT, LT))
    val d = vecCmpnrg(a, b, c)
    check(d.asArray() == listOf(0, 0.inv(), 0, 0.inv()))
    val c1 = VectorUnsignedInt(uintArrayOf(GT, LT, 0u, 0u))
    val d1 = vecCmpnrg(a, b, c1)
    check(d1.asArray() == listOf(0, 0.inv(), 0.inv(), 0.inv()))
    val a1 = VectorUnsignedInt(listOf(11, 22, 33, 30))
    val b1 = VectorUnsignedInt(listOf(10, 20, 30, 30))
    val c2 = VectorUnsignedInt(uintArrayOf(GT, LT, EQ, EQ))
    val d2 = vecCmpnrg(a1, b1, c2)
    check(d2.asArray() == listOf(0, 0.inv(), 0.inv(), 0))
}

public fun testVecCmprgIdx() {
    val a = VectorUnsignedInt(listOf(1, 11, 22, 33))
    val b = VectorUnsignedInt(listOf(10, 20, 30, 40))
    val c = VectorUnsignedInt(uintArrayOf(GT, LT, GT, LT))
    val d = vecCmprgIdx(a, b, c)
    check(d.asArray() == listOf(0, 4, 0, 0))
}

public fun testVecCmpnrgIdx() {
    val a = VectorUnsignedInt(listOf(1, 11, 22, 33))
    val b = VectorUnsignedInt(listOf(10, 20, 30, 40))
    val c = VectorUnsignedInt(uintArrayOf(GT, LT, GT, LT))
    val d = vecCmpnrgIdx(a, b, c)
    check(d.asArray() == listOf(0, 0, 0, 0))
}

public fun testVecCmprgOr0Idx() {
    val a = VectorUnsignedInt(listOf(1, 0, 22, 33))
    val b = VectorUnsignedInt(listOf(10, 20, 30, 40))
    val c = VectorUnsignedInt(uintArrayOf(GT, LT, GT, LT))
    val d = vecCmprgOr0Idx(a, b, c)
    check(d.asArray() == listOf(0, 4, 0, 0))
}

public fun testVecCmpnrgOr0Idx() {
    val a = VectorUnsignedInt(listOf(11, 33, 0, 22))
    val b = VectorUnsignedInt(listOf(10, 20, 30, 40))
    val c = VectorUnsignedInt(uintArrayOf(GT, LT, GT, LT))
    val d = vecCmpnrgOr0Idx(a, b, c)
    check(d.asArray() == listOf(0, 8, 0, 0))
}

public fun testVecCmpgt() {
    val a: STL = f32x4.new(1.0, Float.NaN, Float.NaN, 3.14).into()
    val b: STL = f32x4.new(2.0, Float.NaN, 5.0, 2.0).into()
    val d = i32x4.new(0, 0, 0, 0.inv())
    val r = i32x4.from(vecCmpgt(a, b))
    check(d == r)
}

public fun testVecCmpge() {
    val a: STL = f32x4.new(1.0, Float.NaN, Float.NaN, 3.14).into()
    val b: STL = f32x4.new(1.0, Float.NaN, 5.0, 2.0).into()
    val d = i32x4.new(0.inv(), 0, 0, 0.inv())
    val r = i32x4.from(vecCmpge(a, b))
    check(d == r)
}

public fun testVecCmplt() {
    val a: STL = f32x4.new(1.0, Float.NaN, Float.NaN, 2.0).into()
    val b: STL = f32x4.new(2.0, Float.NaN, 5.0, 2.0).into()
    val d = i32x4.new(0.inv(), 0, 0, 0)
    val r = i32x4.from(vecCmplt(a, b))
    check(d == r)
}

public fun testVecCmple() {
    val a: STL = f32x4.new(1.0, Float.NaN, Float.NaN, 2.0).into()
    val b: STL = f32x4.new(1.0, Float.NaN, 5.0, 3.14).into()
    val d = i32x4.new(0.inv(), 0, 0, 0.inv())
    val r = i32x4.from(vecCmple(a, b))
    check(d == r)
}

public fun testVecCmpeq() {
    val a: STL = f32x4.new(1.0, Float.NaN, Float.NaN, 2.0).into()
    val b: STL = f32x4.new(1.0, Float.NaN, 5.0, 3.14).into()
    val d = i32x4.new(0.inv(), 0, 0, 0)
    val r = i32x4.from(vecCmpeq(a, b))
    check(d == r)
}

public fun testVecCmpne() {
    val a: STL = f32x4.new(1.0, Float.NaN, Float.NaN, 2.0).into()
    val b: STL = f32x4.new(1.0, Float.NaN, 5.0, 3.14).into()
    val d = i32x4.new(0, 0.inv(), 0.inv(), 0.inv())
    val r = i32x4.from(vecCmpne(a, b))
    check(d == r)
}

public fun testVecMaxF32() {
    val a: STL = f32x4.new(1.0, Float.NaN, Float.POSITIVE_INFINITY, 2.0).into()
    val b: STL = f32x4.new(-10.0, -10.0, 5.0, Float.NaN).into()
    val d = f32x4.new(1.0, -10.0, Float.POSITIVE_INFINITY, 2.0)
    val r = f32x4.from(vecMax(a, b))
    check(d == r)
}

public fun testVecMinF32() {
    val a: STL = f32x4.new(1.0, Float.NaN, Float.POSITIVE_INFINITY, 2.0).into()
    val b: STL = f32x4.new(-10.0, -10.0, 5.0, Float.NaN).into()
    val d = f32x4.new(-10.0, -10.0, 5.0, 2.0)
    val r = f32x4.from(vecMin(a, b))
    check(d == r)
}

public fun testVecMaxF64() {
    val a: STL = f64x2.new(Double.NaN, 2.0).into()
    val b: STL = f64x2.new(-10.0, Double.NaN).into()
    val d = f64x2.new(-10.0, 2.0)
    val r = f64x2.from(vecMax(a, b))
    check(d == r)
}

public fun testVecMinF64() {
    val a: STL = f64x2.new(Double.NaN, 2.0).into()
    val b: STL = f64x2.new(-10.0, Double.NaN).into()
    val d = f64x2.new(-10.0, 2.0)
    val r = f64x2.from(vecMin(a, b))
    check(d == r)
}

public fun testVecMeadd() {
    val a = VectorUnsignedShort(listOf(1, 0, 2, 0, 3, 0, 4, 0))
    val b = VectorUnsignedShort(listOf(5, 0, 6, 0, 7, 0, 8, 0))
    val c = VectorUnsignedInt(listOf(2, 2, 2, 2))
    val d = vecMeadd(a, b, c)
    check(d.asArray() == listOf(7, 14, 23, 34))
    val a1 = VectorSignedShort(listOf(1, 0, 2, 0, 3, 0, 4, 0))
    val b1 = VectorSignedShort(listOf(5, 0, 6, 0, 7, 0, 8, 0))
    val c1 = VectorSignedInt(listOf(2, -2, 2, -2))
    val d1 = vecMeadd(a1, b1, c1)
    check(d1.asArray() == listOf(7, 10, 23, 30))
}

public fun testVecMoadd() {
    val a = VectorUnsignedShort(listOf(0, 1, 0, 2, 0, 3, 0, 4))
    val b = VectorUnsignedShort(listOf(0, 5, 0, 6, 0, 7, 0, 8))
    val c = VectorUnsignedInt(listOf(2, 2, 2, 2))
    val d = vecMoadd(a, b, c)
    check(d.asArray() == listOf(7, 14, 23, 34))
    val a1 = VectorSignedShort(listOf(0, 1, 0, 2, 0, 3, 0, 4))
    val b1 = VectorSignedShort(listOf(0, 5, 0, 6, 0, 7, 0, 8))
    val c1 = VectorSignedInt(listOf(2, -2, 2, -2))
    val d1 = vecMoadd(a1, b1, c1)
    check(d1.asArray() == listOf(7, 10, 23, 30))
}

public fun testVecMhadd() {
    val a = VectorUnsignedInt(listOf(1, 2, 3, 4))
    val b = VectorUnsignedInt(listOf(5, 6, 7, 8))
    val c = VectorUnsignedInt(UIntArray(4) { UInt.MAX_VALUE })
    val d = vecMhadd(a, b, c)
    check(d.asArray() == listOf(1, 1, 1, 1))
    val a1 = VectorSignedInt(listOf(-1, -2, -3, -4))
    val b1 = VectorSignedInt(listOf(5, 6, 7, 8))
    val c1 = VectorSignedInt(IntArray(4) { Int.MIN_VALUE })
    val d1 = vecMhadd(a1, b1, c1)
    check(d1.asArray() == listOf(-1, -1, -1, -1))
}

public fun testVecMladd() {
    val a = VectorUnsignedInt(listOf(1, 2, 3, 4))
    val b = VectorUnsignedInt(listOf(5, 6, 7, 8))
    val c = VectorUnsignedInt(listOf(2, 2, 2, 2))
    val d = vecMladd(a, b, c)
    check(d.asArray() == listOf(7, 14, 23, 34))
    val a1 = VectorSignedInt(listOf(-1, -2, -3, -4))
    val b1 = VectorSignedInt(listOf(5, 6, 7, 8))
    val c1 = VectorSignedInt(listOf(2, 2, 2, 2))
    val d1 = vecMladd(a1, b1, c1)
    check(d1.asArray() == listOf(-3, -10, -19, -30))
}

public fun testVecExtract() {
    val v = VectorUnsignedInt(listOf(1, 2, 3, 4))
    check(vecExtract(v, 1) == 2)
    check(vecExtract(v, 4 + 2) == 3)
}

public fun testVecInsert() {
    var v = VectorUnsignedInt(listOf(1, 2, 3, 4))
    v = vecInsert(42, v, 1)
    check(v.asArray() == listOf(1, 42, 3, 4))
    v = vecInsert(64, v, 6)
    check(v.asArray() == listOf(1, 42, 64, 4))
}

public fun testVecPromote() {
    val v: VectorUnsignedInt = vecPromote(42, 1).assumeInit()
    check(v.asArray() == listOf(0, 42, 0, 0))
}

public fun testVecInsertAndZero() {
    val v = vecInsertAndZero<VectorUnsignedInt>(42u)
    check(v.asArray() == VectorUnsignedInt(listOf(0, 42, 0, 0)).asArray())
}

public class VectorInlineTests {
    @kotlin.test.Test
    public fun reverseMask() {
        check(ShuffleMask.reverse().asArray() == listOf(3, 2, 1, 0))
    }

    @kotlin.test.Test
    public fun mergelMask() {
        check(ShuffleMask.mergeLow().asArray() == listOf(2, 6, 3, 7))
    }

    @kotlin.test.Test
    public fun mergehMask() {
        check(ShuffleMask.mergeHigh().asArray() == listOf(0, 4, 1, 5))
    }

    @kotlin.test.Test
    public fun packMask() {
        check(ShuffleMask.pack().asArray() == listOf(1, 3, 5, 7))
    }

    @kotlin.test.Test
    public fun testVecMask() {
        check(genmask(0x00FF) == ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0xFFu, 0xFFu, 0xFFu, 0xFFu, 0xFFu, 0xFFu, 0xFFu, 0xFFu))
    }

    @kotlin.test.Test
    public fun testGenmasks() {
        check(genmasks(8u, 3u, 5u) == 28uL)
        check(genmasks(8u, 3u, 7u) == 31uL)
        // If a or b is greater than 8, the operation is performed as if the value gets modulo by 8.
        check(genmasks(8u, (3u + 8u).toUByte(), (7u + 8u).toUByte()) == 31uL)
        // If a is greater than b, the operation is perform as if b equals 7.
        check(genmasks(8u, 5u, 4u) == genmasks(8u, 5u, 7u))
        check(genmasks(16u, 4u, 12u).toUShort() == run { val bytesTmp0 = ubyteArrayOf(15u, (-8).toUByte()); ((bytesTmp0[0].toInt() shl 8) or bytesTmp0[1].toInt()).toUShort() })
        check(genmasks(32u, 4u, 29u).toUInt() == run { val bytesTmp1 = ubyteArrayOf(15u, 0xFFu, 0xFFu, (-4).toUByte()); ((bytesTmp1[0].toUInt() shl 24) or (bytesTmp1[1].toUInt() shl 16) or (bytesTmp1[2].toUInt() shl 8) or bytesTmp1[3].toUInt()) })
    }
}
