// port-lint: tests s390x/vector.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.s390x.vector

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.convert.num.from
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
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.num.checkedAdd
import io.github.kotlinmania.rustlang.core.num.checkedSub
import io.github.kotlinmania.rustlang.core.num.divCeil
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.simd.F32x4
import io.github.kotlinmania.rustlang.corearch.simd.F64x2
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.M16x8
import io.github.kotlinmania.rustlang.corearch.simd.M32x4
import io.github.kotlinmania.rustlang.corearch.simd.M8x16
import io.github.kotlinmania.rustlang.corearch.simd.Simd
import io.github.kotlinmania.rustlang.corearch.simd.SimdM
import io.github.kotlinmania.rustlang.corearch.simd.U16x8
import io.github.kotlinmania.rustlang.corearch.simd.U32x4
import io.github.kotlinmania.rustlang.corearch.simd.U64x2
import io.github.kotlinmania.rustlang.corearch.simd.U8x16
import kotlin.experimental.inv
import kotlin.experimental.or

// s390x vector intrinsics.
//
// For more info see the [Reference Summary] or the online [IBM docs].
//
// [Reference Summary]: https://www.ibm.com/support/pages/sites/default/files/2021-05/SA22-7871-10.pdf
// [IBM docs]: https://www.ibm.com/docs/en/zos/2.4.0?topic=support-vector-built-in-functions

public fun ShuffleMask.asArray(n: Int = 0): UIntArray = transmuteWithLayouts<ShuffleMask, UIntArray>(this, UnresolvedTypeLayout<ShuffleMask>(), UnresolvedTypeLayout<UIntArray>())

public fun vecAddI32x4I32x4() {
    val x = VectorSignedInt.from(Simd<Int>(listOf(1, 2, 3, 4)))
    val y = VectorSignedInt.from(Simd<Int>(listOf(4, 3, 2, 1)))
    val z = vecAdd(x, y)
    check(Simd<Int>(List(4) { 5 }) == I32x4.from(z))
}

public fun testVecSubF32x4() {
    val a: VectorFloat = Simd<Float>(listOf((-1.0f).toFloat(), 0.0f.toFloat(), 1.0f.toFloat(), 2.0f.toFloat())).into()
    val b: VectorFloat = Simd<Float>(listOf(2.0f.toFloat(), 1.0f.toFloat(), (-1.0f).toFloat(), (-2.0f).toFloat())).into()
    val d = Simd<Float>(listOf((-3.0f).toFloat(), (-1.0f).toFloat(), 2.0f.toFloat(), 4.0f.toFloat()))
    val r = F32x4.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubF64x2() {
    val a: VectorDouble = Simd<Double>(listOf((-1.0).toDouble(), 0.0.toDouble())).into()
    val b: VectorDouble = Simd<Double>(listOf(2.0.toDouble(), 1.0.toDouble())).into()
    val d = Simd<Double>(listOf((-3.0).toDouble(), (-1.0).toDouble()))
    val r = F64x2.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubI64x2() {
    val a: VectorSignedLongLong = Simd<Long>(listOf(-1L, 0L)).into()
    val b: VectorSignedLongLong = Simd<Long>(listOf(2L, 1L)).into()
    val d = Simd<Long>(listOf(-3L, -1L))
    val r = I64x2.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubU64x2() {
    val a: VectorUnsignedLongLong = Simd<ULong>(listOf(0uL, 1uL)).into()
    val b: VectorUnsignedLongLong = Simd<ULong>(listOf(1uL, 0uL)).into()
    val d = Simd<ULong>(listOf(ULong.MAX_VALUE, 1uL))
    val r = U64x2.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubI32x4() {
    val a: VectorSignedInt = Simd<Int>(listOf(-1, 0, 1, 2)).into()
    val b: VectorSignedInt = Simd<Int>(listOf(2, 1, -1, -2)).into()
    val d = Simd<Int>(listOf(-3, -1, 2, 4))
    val r = I32x4.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubU32x4() {
    val a: VectorUnsignedInt = Simd<UInt>(listOf(0u, 0u, 1u, 2u)).into()
    val b: VectorUnsignedInt = Simd<UInt>(listOf(2u, 1u, 0u, 0u)).into()
    val d = Simd<UInt>(listOf(4294967294u, 4294967295u, 1u, 2u))
    val r = U32x4.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubI16x8() {
    val a: VectorSignedShort = Simd<Short>(listOf((-1).toShort(), 0.toShort(), 1.toShort(), 2.toShort(), (-1).toShort(), 0.toShort(), 1.toShort(), 2.toShort())).into()
    val b: VectorSignedShort = Simd<Short>(listOf(2.toShort(), 1.toShort(), (-1).toShort(), (-2).toShort(), 2.toShort(), 1.toShort(), (-1).toShort(), (-2).toShort())).into()
    val d = Simd<Short>(listOf((-3).toShort(), (-1).toShort(), 2.toShort(), 4.toShort(), (-3).toShort(), (-1).toShort(), 2.toShort(), 4.toShort()))
    val r = I16x8.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubU16x8() {
    val a: VectorUnsignedShort = Simd<UShort>(listOf(0u.toUShort(), 0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 0u.toUShort(), 0u.toUShort(), 1u.toUShort(), 2u.toUShort())).into()
    val b: VectorUnsignedShort = Simd<UShort>(listOf(2u.toUShort(), 1u.toUShort(), 0u.toUShort(), 0u.toUShort(), 2u.toUShort(), 1u.toUShort(), 0u.toUShort(), 0u.toUShort())).into()
    val d = Simd<UShort>(listOf(65534u.toUShort(), 65535u.toUShort(), 1u.toUShort(), 2u.toUShort(), 65534u.toUShort(), 65535u.toUShort(), 1u.toUShort(), 2u.toUShort()))
    val r = U16x8.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubI8x16() {
    val a: VectorSignedChar = Simd<Byte>(listOf((-1).toByte(), 0.toByte(), 1.toByte(), 2.toByte(), (-1).toByte(), 0.toByte(), 1.toByte(), 2.toByte(), (-1).toByte(), 0.toByte(), 1.toByte(), 2.toByte(), (-1).toByte(), 0.toByte(), 1.toByte(), 2.toByte())).into()
    val b: VectorSignedChar = Simd<Byte>(listOf(2.toByte(), 1.toByte(), (-1).toByte(), (-2).toByte(), 2.toByte(), 1.toByte(), (-1).toByte(), (-2).toByte(), 2.toByte(), 1.toByte(), (-1).toByte(), (-2).toByte(), 2.toByte(), 1.toByte(), (-1).toByte(), (-2).toByte())).into()
    val d = Simd<Byte>(listOf((-3).toByte(), (-1).toByte(), 2.toByte(), 4.toByte(), (-3).toByte(), (-1).toByte(), 2.toByte(), 4.toByte(), (-3).toByte(), (-1).toByte(), 2.toByte(), 4.toByte(), (-3).toByte(), (-1).toByte(), 2.toByte(), 4.toByte()))
    val r = I8x16.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubU8x16() {
    val a: VectorUnsignedChar = Simd<UByte>(listOf(0u.toUByte(), 0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 0u.toUByte(), 0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 0u.toUByte(), 0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 0u.toUByte(), 0u.toUByte(), 1u.toUByte(), 2u.toUByte())).into()
    val b: VectorUnsignedChar = Simd<UByte>(listOf(2u.toUByte(), 1u.toUByte(), 0u.toUByte(), 0u.toUByte(), 2u.toUByte(), 1u.toUByte(), 0u.toUByte(), 0u.toUByte(), 2u.toUByte(), 1u.toUByte(), 0u.toUByte(), 0u.toUByte(), 2u.toUByte(), 1u.toUByte(), 0u.toUByte(), 0u.toUByte())).into()
    val d = Simd<UByte>(listOf(254u.toUByte(), 255u.toUByte(), 1u.toUByte(), 2u.toUByte(), 254u.toUByte(), 255u.toUByte(), 1u.toUByte(), 2u.toUByte(), 254u.toUByte(), 255u.toUByte(), 1u.toUByte(), 2u.toUByte(), 254u.toUByte(), 255u.toUByte(), 1u.toUByte(), 2u.toUByte()))
    val r = U8x16.from(vecSub(a, b))
    check(d == r)
}

public fun testVecMulF32x4() {
    val a: VectorFloat = Simd<Float>(listOf((-1.0f).toFloat(), 0.0f.toFloat(), 1.0f.toFloat(), 2.0f.toFloat())).into()
    val b: VectorFloat = Simd<Float>(listOf(2.0f.toFloat(), 1.0f.toFloat(), (-1.0f).toFloat(), (-2.0f).toFloat())).into()
    val d = Simd<Float>(listOf((-2.0f).toFloat(), 0.0f.toFloat(), (-1.0f).toFloat(), (-4.0f).toFloat()))
    val r = F32x4.from(vecMul(a, b))
    check(d == r)
}

public fun testVecMulF64x2() {
    val a: VectorDouble = Simd<Double>(listOf((-1.0).toDouble(), 0.0.toDouble())).into()
    val b: VectorDouble = Simd<Double>(listOf(2.0.toDouble(), 1.0.toDouble())).into()
    val d = Simd<Double>(listOf((-2.0).toDouble(), 0.0.toDouble()))
    val r = F64x2.from(vecMul(a, b))
    check(d == r)
}

public fun testVecMulI64x2() {
    val a: VectorSignedLongLong = Simd<Long>(listOf(Long.MAX_VALUE, -4L)).into()
    val b: VectorSignedLongLong = Simd<Long>(listOf(2L, 3L)).into()
    val d = Simd<Long>(listOf((Long.MAX_VALUE * 2L), -12L))
    val r = I64x2.from(vecMul(a, b))
    check(d == r)
}

public fun testVecMulU64x2() {
    val a: VectorUnsignedLongLong = Simd<ULong>(listOf(ULong.MAX_VALUE, 4uL)).into()
    val b: VectorUnsignedLongLong = Simd<ULong>(listOf(2uL, 3uL)).into()
    val d = Simd<ULong>(listOf((ULong.MAX_VALUE * 2uL), 12uL))
    val r = U64x2.from(vecMul(a, b))
    check(d == r)
}

public fun testVecMulI32x4() {
    val a: VectorSignedInt = Simd<Int>(listOf(-1, 0, 1, 2)).into()
    val b: VectorSignedInt = Simd<Int>(listOf(2, 1, -1, -2)).into()
    val d = Simd<Int>(listOf(-2, 0, -1, -4))
    val r = I32x4.from(vecMul(a, b))
    check(d == r)
}

public fun testVecMulU32x4() {
    val a: VectorUnsignedInt = Simd<UInt>(listOf(0u, UInt.MAX_VALUE - 1u, 1u, 2u)).into()
    val b: VectorUnsignedInt = Simd<UInt>(listOf(5u, 6u, 7u, 8u)).into()
    val d = Simd<UInt>(listOf(0u, 4294967284u, 7u, 16u))
    val r = U32x4.from(vecMul(a, b))
    check(d == r)
}

public fun testVecMulI16x8() {
    val a: VectorSignedShort = Simd<Short>(listOf((-1).toShort(), 0.toShort(), 1.toShort(), 2.toShort(), (-1).toShort(), 0.toShort(), 1.toShort(), 2.toShort())).into()
    val b: VectorSignedShort = Simd<Short>(listOf(2.toShort(), 1.toShort(), (-1).toShort(), (-2).toShort(), 2.toShort(), 1.toShort(), (-1).toShort(), (-2).toShort())).into()
    val d = Simd<Short>(listOf((-2).toShort(), 0.toShort(), (-1).toShort(), (-4).toShort(), (-2).toShort(), 0.toShort(), (-1).toShort(), (-4).toShort()))
    val r = I16x8.from(vecMul(a, b))
    check(d == r)
}

public fun testVecMulU16x8() {
    val a: VectorUnsignedShort = Simd<UShort>(listOf(0u.toUShort(), (UShort.MAX_VALUE - 1u).toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort())).into()
    val b: VectorUnsignedShort = Simd<UShort>(listOf(5u.toUShort(), 6u.toUShort(), 7u.toUShort(), 8u.toUShort(), 9u.toUShort(), 8u.toUShort(), 7u.toUShort(), 6u.toUShort())).into()
    val d = Simd<UShort>(listOf(0u.toUShort(), 65524u.toUShort(), 7u.toUShort(), 16u.toUShort(), 27u.toUShort(), 32u.toUShort(), 35u.toUShort(), 36u.toUShort()))
    val r = U16x8.from(vecMul(a, b))
    check(d == r)
}

public fun testVecMulI8x16() {
    val a: VectorSignedChar = Simd<Byte>(listOf((-1).toByte(), 0.toByte(), 1.toByte(), 2.toByte(), (-1).toByte(), 0.toByte(), 1.toByte(), 2.toByte(), (-1).toByte(), 0.toByte(), 1.toByte(), 2.toByte(), (-1).toByte(), 0.toByte(), 1.toByte(), 2.toByte())).into()
    val b: VectorSignedChar = Simd<Byte>(listOf(2.toByte(), 1.toByte(), (-1).toByte(), (-2).toByte(), 2.toByte(), 1.toByte(), (-1).toByte(), (-2).toByte(), 2.toByte(), 1.toByte(), (-1).toByte(), (-2).toByte(), 2.toByte(), 1.toByte(), (-1).toByte(), (-2).toByte())).into()
    val d = Simd<Byte>(listOf((-2).toByte(), 0.toByte(), (-1).toByte(), (-4).toByte(), (-2).toByte(), 0.toByte(), (-1).toByte(), (-4).toByte(), (-2).toByte(), 0.toByte(), (-1).toByte(), (-4).toByte(), (-2).toByte(), 0.toByte(), (-1).toByte(), (-4).toByte()))
    val r = I8x16.from(vecMul(a, b))
    check(d == r)
}

public fun testVecMulU8x16() {
    val a: VectorUnsignedChar = Simd<UByte>(listOf(0u.toUByte(), (UByte.MAX_VALUE - 1u).toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 8u.toUByte(), 7u.toUByte(), 6u.toUByte(), 5u.toUByte(), 4u.toUByte())).into()
    val b: VectorUnsignedChar = Simd<UByte>(listOf(5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 8u.toUByte(), 7u.toUByte(), 6u.toUByte(), 5u.toUByte(), 4u.toUByte(), 0u.toUByte(), UByte.MAX_VALUE, 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte())).into()
    val d = Simd<UByte>(listOf(0u.toUByte(), 244u.toUByte(), 7u.toUByte(), 16u.toUByte(), 27u.toUByte(), 32u.toUByte(), 35u.toUByte(), 36u.toUByte(), 35u.toUByte(), 32u.toUByte(), 0u.toUByte(), 248u.toUByte(), 7u.toUByte(), 12u.toUByte(), 15u.toUByte(), 16u.toUByte()))
    val r = U8x16.from(vecMul(a, b))
    check(d == r)
}

public fun testVecAbsI8() {
    val a: VectorSignedChar = vecSplats(-42)
    val a1: VectorSignedChar = vecAbs(a)
    val d = Simd<Byte>(List(16) { 42 })
    check(d == I8x16.from(a1))
}

public fun testVecAbsI16() {
    val a: VectorSignedShort = vecSplats(-42)
    val a1: VectorSignedShort = vecAbs(a)
    val d = Simd<Short>(List(8) { 42 })
    check(d == I16x8.from(a1))
}

public fun testVecAbsI32() {
    val a: VectorSignedInt = vecSplats(-42)
    val a1: VectorSignedInt = vecAbs(a)
    val d = Simd<Int>(List(4) { 42 })
    check(d == I32x4.from(a1))
}

public fun testVecAbsI64() {
    val a: VectorSignedLongLong = vecSplats(-42L)
    val a1: VectorSignedLongLong = vecAbs(a)
    val d = Simd<Long>(List(2) { 42L })
    check(d == I64x2.from(a1))
}

public fun testVecAbsF32() {
    val a: VectorFloat = vecSplats(-42.0f)
    val a1: VectorFloat = vecAbs(a)
    val d = Simd<Float>(List(4) { 42.0f.toFloat() })
    check(d == F32x4.from(a1))
}

public fun testVecAbsF64() {
    val a: VectorDouble = vecSplats(-42.0)
    val a1: VectorDouble = vecAbs(a)
    val d = Simd<Double>(List(2) { 42.0.toDouble() })
    check(d == F64x2.from(a1))
}

public fun testVecNabs() {
    val a: VectorFloat = Simd<Float>(listOf(3.1415927f, 1.0f.toFloat(), 0.0f.toFloat(), (-1.0f).toFloat())).into()
    val d = Simd<Float>(listOf(-3.1415927f, (-1.0f).toFloat(), 0.0f.toFloat(), (-1.0f).toFloat()))
    val r = F32x4.from(vecNabs(a))
    check(d == r)
}

public fun testVecAndc() {
    val a: VectorSignedInt = Simd<Int>(listOf(0xCC, 0xCC, 0xCC, 0xCC)).into()
    val b: VectorSignedInt = Simd<Int>(listOf(0x33, 0xF3, 0xC, 0x80)).into()
    val d = Simd<Int>(listOf(0xCC, 0xC, 0xC0, 0x4C))
    val r = I32x4.from(vecAndc(a, b))
    check(d == r)
}

public fun testVecAnd() {
    val a: VectorSignedInt = Simd<Int>(listOf(0xCC, 0xCC, 0xCC, 0xCC)).into()
    val b: VectorSignedInt = Simd<Int>(listOf(0x33, 0xF3, 0xC, 0x0)).into()
    val d = Simd<Int>(listOf(0x0, 0xC0, 0xC, 0x0))
    val r = I32x4.from(vecAnd(a, b))
    check(d == r)
}

public fun testVecNand() {
    val a: VectorSignedInt = Simd<Int>(listOf(0xCC, 0xCC, 0xCC, 0xCC)).into()
    val b: VectorSignedInt = Simd<Int>(listOf(0x33, 0xF3, 0xC, 0x0)).into()
    val d = Simd<Int>(listOf(0x0.inv(), 0xC0.inv(), 0xC.inv(), 0x0.inv()))
    val r = I32x4.from(vecNand(a, b))
    check(d == r)
}

public fun testVecOrc() {
    val a: VectorUnsignedInt = Simd<UInt>(listOf(0xCCu, 0xCCu, 0xCCu, 0xCCu)).into()
    val b: VectorUnsignedInt = Simd<UInt>(listOf(0x33u, 0xF3u, 0xCu, 0x0u)).into()
    val d = Simd<UInt>(listOf(0xCCu or 0x33u.inv(), 0xCCu or 0xF3u.inv(), 0xCCu or 0xCu.inv(), 0xCCu or 0x0u.inv()))
    val r = U32x4.from(vecOrc(a, b))
    check(d == r)
}

public fun testVecOr() {
    val a: VectorSignedInt = Simd<Int>(listOf(0xCC, 0xCC, 0xCC, 0xCC)).into()
    val b: VectorSignedInt = Simd<Int>(listOf(0x33, 0xF3, 0xC, 0x0)).into()
    val d = Simd<Int>(listOf(0xFF, 0xFF, 0xCC, 0xCC))
    val r = I32x4.from(vecOr(a, b))
    check(d == r)
}

public fun testVecNor() {
    val a: VectorSignedInt = Simd<Int>(listOf(0xCC, 0xCC, 0xCC, 0xCC)).into()
    val b: VectorSignedInt = Simd<Int>(listOf(0x33, 0xF3, 0xC, 0x0)).into()
    val d = Simd<Int>(listOf(0xFF.inv(), 0xFF.inv(), 0xCC.inv(), 0xCC.inv()))
    val r = I32x4.from(vecNor(a, b))
    check(d == r)
}

public fun testVecXor() {
    val a: VectorSignedInt = Simd<Int>(listOf(0xCC, 0xCC, 0xCC, 0xCC)).into()
    val b: VectorSignedInt = Simd<Int>(listOf(0x33, 0xF3, 0xC, 0x0)).into()
    val d = Simd<Int>(listOf(0xFF, 0x3F, 0xC0, 0xCC))
    val r = I32x4.from(vecXor(a, b))
    check(d == r)
}

public fun testVecEqv() {
    val a: VectorSignedInt = Simd<Int>(listOf(0xCC, 0xCC, 0xCC, 0xCC)).into()
    val b: VectorSignedInt = Simd<Int>(listOf(0x33, 0xF3, 0xC, 0x0)).into()
    val d = Simd<Int>(listOf(0xFF.inv(), 0x3F.inv(), 0xC0.inv(), 0xCC.inv()))
    val r = I32x4.from(vecEqv(a, b))
    check(d == r)
}

public fun testVecFloorF32() {
    val a: VectorFloat = Simd<Float>(listOf(1.1f.toFloat(), 1.9f.toFloat(), (-0.5f).toFloat(), (-0.9f).toFloat())).into()
    val d = Simd<Float>(listOf(1.0f.toFloat(), 1.0f.toFloat(), (-1.0f).toFloat(), (-1.0f).toFloat()))
    val r = F32x4.from(vecFloor(a))
    check(d == r)
}

public fun testVecFloorF641() {
    val a: VectorDouble = Simd<Double>(listOf(1.1.toDouble(), 1.9.toDouble())).into()
    val d = Simd<Double>(listOf(1.0.toDouble(), 1.0.toDouble()))
    val r = F64x2.from(vecFloor(a))
    check(d == r)
}

public fun testVecFloorF642() {
    val a: VectorDouble = Simd<Double>(listOf((-0.5).toDouble(), (-0.9).toDouble())).into()
    val d = Simd<Double>(listOf((-1.0).toDouble(), (-1.0).toDouble()))
    val r = F64x2.from(vecFloor(a))
    check(d == r)
}

public fun testVecCeilF32() {
    val a: VectorFloat = Simd<Float>(listOf(0.1f.toFloat(), 0.5f.toFloat(), 0.6f.toFloat(), 0.9f.toFloat())).into()
    val d = Simd<Float>(listOf(1.0f.toFloat(), 1.0f.toFloat(), 1.0f.toFloat(), 1.0f.toFloat()))
    val r = F32x4.from(vecCeil(a))
    check(d == r)
}

public fun testVecCeilF641() {
    val a: VectorDouble = Simd<Double>(listOf(0.1.toDouble(), 0.5.toDouble())).into()
    val d = Simd<Double>(listOf(1.0.toDouble(), 1.0.toDouble()))
    val r = F64x2.from(vecCeil(a))
    check(d == r)
}

public fun testVecCeilF642() {
    val a: VectorDouble = Simd<Double>(listOf(0.6.toDouble(), 0.9.toDouble())).into()
    val d = Simd<Double>(listOf(1.0.toDouble(), 1.0.toDouble()))
    val r = F64x2.from(vecCeil(a))
    check(d == r)
}

public fun testVecRoundF32() {
    val a: VectorFloat = Simd<Float>(listOf(0.1f.toFloat(), 0.5f.toFloat(), 0.6f.toFloat(), 0.9f.toFloat())).into()
    val d = Simd<Float>(listOf(0.0f.toFloat(), 0.0f.toFloat(), 1.0f.toFloat(), 1.0f.toFloat()))
    val r = F32x4.from(vecRound(a))
    check(d == r)
}

public fun testVecRoundF32EvenOdd() {
    val a: VectorFloat = Simd<Float>(listOf(0.5f.toFloat(), 1.5f.toFloat(), 2.5f.toFloat(), 3.5f.toFloat())).into()
    val d = Simd<Float>(listOf(0.0f.toFloat(), 2.0f.toFloat(), 2.0f.toFloat(), 4.0f.toFloat()))
    val r = F32x4.from(vecRound(a))
    check(d == r)
}

public fun testVecRoundF641() {
    val a: VectorDouble = Simd<Double>(listOf(0.1.toDouble(), 0.5.toDouble())).into()
    val d = Simd<Double>(listOf(0.0.toDouble(), 0.0.toDouble()))
    val r = F64x2.from(vecRound(a))
    check(d == r)
}

public fun testVecRoundF642() {
    val a: VectorDouble = Simd<Double>(listOf(0.6.toDouble(), 0.9.toDouble())).into()
    val d = Simd<Double>(listOf(1.0.toDouble(), 1.0.toDouble()))
    val r = F64x2.from(vecRound(a))
    check(d == r)
}

public fun testVecRoundcF32() {
    val a: VectorFloat = Simd<Float>(listOf(0.1f.toFloat(), 0.5f.toFloat(), 0.6f.toFloat(), 0.9f.toFloat())).into()
    val d = Simd<Float>(listOf(0.0f.toFloat(), 0.0f.toFloat(), 1.0f.toFloat(), 1.0f.toFloat()))
    val r = F32x4.from(vecRoundc(a))
    check(d == r)
}

public fun testVecRoundcF32EvenOdd() {
    val a: VectorFloat = Simd<Float>(listOf(0.5f.toFloat(), 1.5f.toFloat(), 2.5f.toFloat(), 3.5f.toFloat())).into()
    val d = Simd<Float>(listOf(0.0f.toFloat(), 2.0f.toFloat(), 2.0f.toFloat(), 4.0f.toFloat()))
    val r = F32x4.from(vecRoundc(a))
    check(d == r)
}

public fun testVecRoundcF641() {
    val a: VectorDouble = Simd<Double>(listOf(0.1.toDouble(), 0.5.toDouble())).into()
    val d = Simd<Double>(listOf(0.0.toDouble(), 0.0.toDouble()))
    val r = F64x2.from(vecRoundc(a))
    check(d == r)
}

public fun testVecRoundcF642() {
    val a: VectorDouble = Simd<Double>(listOf(0.6.toDouble(), 0.9.toDouble())).into()
    val d = Simd<Double>(listOf(1.0.toDouble(), 1.0.toDouble()))
    val r = F64x2.from(vecRoundc(a))
    check(d == r)
}

public fun testVecRintF32() {
    val a: VectorFloat = Simd<Float>(listOf(0.1f.toFloat(), 0.5f.toFloat(), 0.6f.toFloat(), 0.9f.toFloat())).into()
    val d = Simd<Float>(listOf(0.0f.toFloat(), 0.0f.toFloat(), 1.0f.toFloat(), 1.0f.toFloat()))
    val r = F32x4.from(vecRint(a))
    check(d == r)
}

public fun testVecRintF32EvenOdd() {
    val a: VectorFloat = Simd<Float>(listOf(0.5f.toFloat(), 1.5f.toFloat(), 2.5f.toFloat(), 3.5f.toFloat())).into()
    val d = Simd<Float>(listOf(0.0f.toFloat(), 2.0f.toFloat(), 2.0f.toFloat(), 4.0f.toFloat()))
    val r = F32x4.from(vecRint(a))
    check(d == r)
}

public fun testVecRintF641() {
    val a: VectorDouble = Simd<Double>(listOf(0.1.toDouble(), 0.5.toDouble())).into()
    val d = Simd<Double>(listOf(0.0.toDouble(), 0.0.toDouble()))
    val r = F64x2.from(vecRint(a))
    check(d == r)
}

public fun testVecRintF642() {
    val a: VectorDouble = Simd<Double>(listOf(0.6.toDouble(), 0.9.toDouble())).into()
    val d = Simd<Double>(listOf(1.0.toDouble(), 1.0.toDouble()))
    val r = F64x2.from(vecRint(a))
    check(d == r)
}

public fun testVecSll() {
    val a: VectorSignedInt = Simd<Int>(listOf(1, 1, 1, 1)).into()
    val b: VectorUnsignedChar = Simd<UByte>(listOf(0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 2u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 3u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 4u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 8u.toUByte())).into()
    val d = Simd<Int>(listOf((1 shl 2).toInt(), (1 shl 3).toInt(), (1 shl 4).toInt(), 1))
    val r = I32x4.from(vecSll(a, b))
    check(d == r)
}

public fun testVecSrl() {
    val a: VectorSignedInt = Simd<Int>(listOf(0x8, 0x8, 0x8, 0x8)).into()
    val b: VectorUnsignedChar = Simd<UByte>(listOf(0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 1u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 2u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 3u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 16u.toUByte())).into()
    val d = Simd<Int>(listOf(4, 2, 1, 8))
    val r = I32x4.from(vecSrl(a, b))
    check(d == r)
}

public fun testVecSralPos() {
    val a: VectorUnsignedInt = Simd<UInt>(listOf(0x8u, 0x8u, 0x8u, 0x8u)).into()
    val b: VectorUnsignedChar = Simd<UByte>(listOf(0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 1u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 2u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 3u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 16u.toUByte())).into()
    val d = Simd<UInt>(listOf(4u, 2u, 1u, 8u))
    val r = U32x4.from(vecSral(a, b))
    check(d == r)
}

public fun testVecSralNeg() {
    val a: VectorSignedInt = Simd<Int>(listOf(-8, -8, -8, -8)).into()
    val b: VectorUnsignedChar = Simd<UByte>(listOf(0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 1u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 2u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 3u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 16u.toUByte())).into()
    val d = Simd<Int>(listOf(-4, -2, -1, -8))
    val r = I32x4.from(vecSral(a, b))
    check(d == r)
}

public fun testVecReveF32() {
    val a: VectorFloat = Simd<Float>(listOf(0.1f.toFloat(), 0.5f.toFloat(), 0.6f.toFloat(), 0.9f.toFloat())).into()
    val d = Simd<Float>(listOf(0.9f.toFloat(), 0.6f.toFloat(), 0.5f.toFloat(), 0.1f.toFloat()))
    val r = F32x4.from(vecReve(a))
    check(d == r)
}

public fun testVecRevbU32() {
    val a: VectorUnsignedInt = Simd<UInt>(listOf(0xAABBCCDDu, 0xEEFF0011u, 0x22334455u, 0x66778899u)).into()
    val d = Simd<UInt>(listOf(0xDDCCBBAAu, 0x1100FFEEu, 0x55443322u, 0x99887766u))
    val r = U32x4.from(vecRevb(a))
    check(d == r)
}

public fun testVecMergehU32() {
    val a: VectorUnsignedInt = Simd<UInt>(listOf(0xAAAAAAAAu, 0xBBBBBBBBu, 0xCCCCCCCCu, 0xDDDDDDDDu)).into()
    val b: VectorUnsignedInt = Simd<UInt>(listOf(0x00000000u, 0x11111111u, 0x22222222u, 0x33333333u)).into()
    val d = Simd<UInt>(listOf(0xAAAAAAAAu, 0x00000000u, 0xBBBBBBBBu, 0x11111111u))
    val r = U32x4.from(vecMergeh(a, b))
    check(d == r)
}

public fun testVecMergelU32() {
    val a: VectorUnsignedInt = Simd<UInt>(listOf(0xAAAAAAAAu, 0xBBBBBBBBu, 0xCCCCCCCCu, 0xDDDDDDDDu)).into()
    val b: VectorUnsignedInt = Simd<UInt>(listOf(0x00000000u, 0x11111111u, 0x22222222u, 0x33333333u)).into()
    val d = Simd<UInt>(listOf(0xCCCCCCCCu, 0x22222222u, 0xDDDDDDDDu, 0x33333333u))
    val r = U32x4.from(vecMergel(a, b))
    check(d == r)
}

public fun testVecPermU8x16() {
    val a = VectorUnsignedChar.from(Simd<UByte>((listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)).toList()))
    val b = VectorUnsignedChar.from(Simd<UByte>((listOf(100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115)).toList()))
    val c = VectorUnsignedChar.from(Simd<UByte>((listOf(0x00, 0x01, 0x10, 0x11, 0x02, 0x03, 0x12, 0x13, 0x04, 0x05, 0x14, 0x15, 0x06, 0x07, 0x16, 0x17)).toList()))
    val d = Simd<UByte>((listOf(0, 1, 100, 101, 2, 3, 102, 103, 4, 5, 104, 105, 6, 7, 106, 107)).toList())
    val r = U8x16.from(vecPerm(a, b, c))
    check(d == r)
}

public fun testVecPermI8x16() {
    val a = VectorSignedChar.from(Simd<Byte>((listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)).toList()))
    val b = VectorSignedChar.from(Simd<Byte>((listOf(100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115)).toList()))
    val c = VectorUnsignedChar.from(Simd<UByte>((listOf(0x00, 0x01, 0x10, 0x11, 0x02, 0x03, 0x12, 0x13, 0x04, 0x05, 0x14, 0x15, 0x06, 0x07, 0x16, 0x17)).toList()))
    val d = Simd<Byte>((listOf(0, 1, 100, 101, 2, 3, 102, 103, 4, 5, 104, 105, 6, 7, 106, 107)).toList())
    val r = I8x16.from(vecPerm(a, b, c))
    check(d == r)
}

public fun testVecPermM8x16() {
    val a = VectorBoolChar.from(SimdM<Byte>((booleanArrayOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false)).toList()))
    val b = VectorBoolChar.from(SimdM<Byte>((booleanArrayOf(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true)).toList()))
    val c = VectorUnsignedChar.from(Simd<UByte>((listOf(0x00, 0x01, 0x10, 0x11, 0x02, 0x03, 0x12, 0x13, 0x04, 0x05, 0x14, 0x15, 0x06, 0x07, 0x16, 0x17)).toList()))
    val d = SimdM<Byte>((booleanArrayOf(false, false, true, true, false, false, true, true, false, false, true, true, false, false, true, true)).toList())
    val r = M8x16.from(vecPerm(a, b, c))
    check(d == r)
}

public fun testVecPermU16x8() {
    val a = VectorUnsignedShort.from(Simd<UShort>((listOf(0, 1, 2, 3, 4, 5, 6, 7)).toList()))
    val b = VectorUnsignedShort.from(Simd<UShort>((listOf(10, 11, 12, 13, 14, 15, 16, 17)).toList()))
    val c = VectorUnsignedChar.from(Simd<UByte>((listOf(0x00, 0x01, 0x10, 0x11, 0x02, 0x03, 0x12, 0x13, 0x04, 0x05, 0x14, 0x15, 0x06, 0x07, 0x16, 0x17)).toList()))
    val d = Simd<UShort>((listOf(0, 10, 1, 11, 2, 12, 3, 13)).toList())
    val r = U16x8.from(vecPerm(a, b, c))
    check(d == r)
}

public fun testVecPermI16x8() {
    val a = VectorSignedShort.from(Simd<Short>((listOf(0, 1, 2, 3, 4, 5, 6, 7)).toList()))
    val b = VectorSignedShort.from(Simd<Short>((listOf(10, 11, 12, 13, 14, 15, 16, 17)).toList()))
    val c = VectorUnsignedChar.from(Simd<UByte>((listOf(0x00, 0x01, 0x10, 0x11, 0x02, 0x03, 0x12, 0x13, 0x04, 0x05, 0x14, 0x15, 0x06, 0x07, 0x16, 0x17)).toList()))
    val d = Simd<Short>((listOf(0, 10, 1, 11, 2, 12, 3, 13)).toList())
    val r = I16x8.from(vecPerm(a, b, c))
    check(d == r)
}

public fun testVecPermM16x8() {
    val a = VectorBoolShort.from(SimdM<Short>((booleanArrayOf(false, false, false, false, false, false, false, false)).toList()))
    val b = VectorBoolShort.from(SimdM<Short>((booleanArrayOf(true, true, true, true, true, true, true, true)).toList()))
    val c = VectorUnsignedChar.from(Simd<UByte>((listOf(0x00, 0x01, 0x10, 0x11, 0x02, 0x03, 0x12, 0x13, 0x04, 0x05, 0x14, 0x15, 0x06, 0x07, 0x16, 0x17)).toList()))
    val d = SimdM<Short>((booleanArrayOf(false, true, false, true, false, true, false, true)).toList())
    val r = M16x8.from(vecPerm(a, b, c))
    check(d == r)
}

public fun testVecPermU32x4() {
    val a = VectorUnsignedInt.from(Simd<UInt>((listOf(0, 1, 2, 3)).toList()))
    val b = VectorUnsignedInt.from(Simd<UInt>((listOf(10, 11, 12, 13)).toList()))
    val c = VectorUnsignedChar.from(Simd<UByte>((listOf(0x00, 0x01, 0x02, 0x03, 0x10, 0x11, 0x12, 0x13, 0x04, 0x05, 0x06, 0x07, 0x14, 0x15, 0x16, 0x17)).toList()))
    val d = Simd<UInt>((listOf(0, 10, 1, 11)).toList())
    val r = U32x4.from(vecPerm(a, b, c))
    check(d == r)
}

public fun testVecPermI32x4() {
    val a = VectorSignedInt.from(Simd<Int>((listOf(0, 1, 2, 3)).toList()))
    val b = VectorSignedInt.from(Simd<Int>((listOf(10, 11, 12, 13)).toList()))
    val c = VectorUnsignedChar.from(Simd<UByte>((listOf(0x00, 0x01, 0x02, 0x03, 0x10, 0x11, 0x12, 0x13, 0x04, 0x05, 0x06, 0x07, 0x14, 0x15, 0x16, 0x17)).toList()))
    val d = Simd<Int>((listOf(0, 10, 1, 11)).toList())
    val r = I32x4.from(vecPerm(a, b, c))
    check(d == r)
}

public fun testVecPermM32x4() {
    val a = VectorBoolInt.from(SimdM<Int>((booleanArrayOf(false, false, false, false)).toList()))
    val b = VectorBoolInt.from(SimdM<Int>((booleanArrayOf(true, true, true, true)).toList()))
    val c = VectorUnsignedChar.from(Simd<UByte>((listOf(0x00, 0x01, 0x02, 0x03, 0x10, 0x11, 0x12, 0x13, 0x04, 0x05, 0x06, 0x07, 0x14, 0x15, 0x16, 0x17)).toList()))
    val d = SimdM<Int>((booleanArrayOf(false, true, false, true)).toList())
    val r = M32x4.from(vecPerm(a, b, c))
    check(d == r)
}

public fun testVecPermF32x4() {
    val a = VectorFloat.from(Simd<Float>((listOf(0.0, 1.0, 2.0, 3.0)).toList()))
    val b = VectorFloat.from(Simd<Float>((listOf(1.0, 1.1, 1.2, 1.3)).toList()))
    val c = VectorUnsignedChar.from(Simd<UByte>((listOf(0x00, 0x01, 0x02, 0x03, 0x10, 0x11, 0x12, 0x13, 0x04, 0x05, 0x06, 0x07, 0x14, 0x15, 0x16, 0x17)).toList()))
    val d = Simd<Float>((listOf(0.0, 1.0, 1.0, 1.1)).toList())
    val r = F32x4.from(vecPerm(a, b, c))
    check(d == r)
}

public fun testVecSqrt() {
    val a: VectorFloat = Simd<Float>(listOf(3.1415927f, 1.0f.toFloat(), 25.0f.toFloat(), 2.0f.toFloat())).into()
    val d = Simd<Float>(listOf(kmSqrtf32(3.1415927f), 1.0f.toFloat(), 5.0f.toFloat(), 1.4142135f))
    val r = F32x4.from(vecSqrt(a))
    check(d == r)
}

public fun testVecFindAnyEq() {
    val a: VectorSignedInt = Simd<Int>(listOf(1, -2, 3, -4)).into()
    val b: VectorSignedInt = Simd<Int>(listOf(-5, 3, -7, 8)).into()
    val d = Simd<Int>(listOf(0, 0, 0.inv(), 0))
    val r = I32x4.from(vecFindAnyEq(a, b))
    check(d == r)
}

public fun testVecFindAnyNe() {
    val a: VectorSignedInt = Simd<Int>(listOf(1, -2, 3, -4)).into()
    val b: VectorSignedInt = Simd<Int>(listOf(-5, 3, -7, 8)).into()
    val d = Simd<Int>(listOf(0.inv(), 0.inv(), 0, 0.inv()))
    val r = I32x4.from(vecFindAnyNe(a, b))
    check(d == r)
}

public fun testVecFindAnyEqIdx1() {
    val a: VectorSignedInt = Simd<Int>(listOf(1, 2, 3, 4)).into()
    val b: VectorSignedInt = Simd<Int>(listOf(5, 3, 7, 8)).into()
    val d = Simd<Int>(listOf(0, 8, 0, 0))
    val r = I32x4.from(vecFindAnyEqIdx(a, b))
    check(d == r)
}

public fun testVecFindAnyEqIdx2() {
    val a: VectorSignedInt = Simd<Int>(listOf(1, 2, 3, 4)).into()
    val b: VectorSignedInt = Simd<Int>(listOf(5, 6, 7, 8)).into()
    val d = Simd<Int>(listOf(0, 16, 0, 0))
    val r = I32x4.from(vecFindAnyEqIdx(a, b))
    check(d == r)
}

public fun testVecFindAnyNeIdx1() {
    val a: VectorSignedInt = Simd<Int>(listOf(1, 2, 3, 4)).into()
    val b: VectorSignedInt = Simd<Int>(listOf(1, 5, 3, 4)).into()
    val d = Simd<Int>(listOf(0, 4, 0, 0))
    val r = I32x4.from(vecFindAnyNeIdx(a, b))
    check(d == r)
}

public fun testVecFindAnyNeIdx2() {
    val a: VectorSignedInt = Simd<Int>(listOf(1, 2, 3, 4)).into()
    val b: VectorSignedInt = Simd<Int>(listOf(1, 2, 3, 4)).into()
    val d = Simd<Int>(listOf(0, 16, 0, 0))
    val r = I32x4.from(vecFindAnyNeIdx(a, b))
    check(d == r)
}

public fun testVecFindAnyEqOr0Idx1() {
    val a: VectorSignedInt = Simd<Int>(listOf(1, 2, 0, 4)).into()
    val b: VectorSignedInt = Simd<Int>(listOf(5, 6, 7, 8)).into()
    val d = Simd<Int>(listOf(0, 8, 0, 0))
    val r = I32x4.from(vecFindAnyEqOr0Idx(a, b))
    check(d == r)
}

public fun testVecFindAnyNeOr0Idx1() {
    val a: VectorSignedInt = Simd<Int>(listOf(1, 2, 0, 4)).into()
    val b: VectorSignedInt = Simd<Int>(listOf(1, 2, 3, 4)).into()
    val d = Simd<Int>(listOf(0, 8, 0, 0))
    val r = I32x4.from(vecFindAnyNeOr0Idx(a, b))
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
    val a: VectorSignedShort = Simd<Short>(listOf(0.toShort(), 1.toShort(), (-1).toShort(), 42.toShort(), 32767.toShort(), (-32768).toShort(), 30000.toShort(), (-30000).toShort())).into()
    val b: VectorSignedShort = Simd<Short>(listOf(32767.toShort(), (-32768).toShort(), 12345.toShort(), (-12345).toShort(), 0.toShort(), 1.toShort(), (-1).toShort(), 42.toShort())).into()
    val d = Simd<Byte>(listOf(0.toByte(), 1.toByte(), (-1).toByte(), 42.toByte(), (-1).toByte(), 0.toByte(), 48.toByte(), (-48).toByte(), (-1).toByte(), 0.toByte(), 57.toByte(), (-57).toByte(), 0.toByte(), 1.toByte(), (-1).toByte(), 42.toByte()))
    val r = I8x16.from(vecPack(a, b))
    check(d == r)
}

public fun testVecPacks() {
    val a: VectorSignedShort = Simd<Short>(listOf(0.toShort(), 1.toShort(), (-1).toShort(), 42.toShort(), 32767.toShort(), (-32768).toShort(), 30000.toShort(), (-30000).toShort())).into()
    val b: VectorSignedShort = Simd<Short>(listOf(32767.toShort(), (-32768).toShort(), 12345.toShort(), (-12345).toShort(), 0.toShort(), 1.toShort(), (-1).toShort(), 42.toShort())).into()
    val d = Simd<Byte>(listOf(0.toByte(), 1.toByte(), (-1).toByte(), 42.toByte(), 127.toByte(), (-128).toByte(), 127.toByte(), (-128).toByte(), 127.toByte(), (-128).toByte(), 127.toByte(), (-128).toByte(), 0.toByte(), 1.toByte(), (-1).toByte(), 42.toByte()))
    val r = I8x16.from(vecPacks(a, b))
    check(d == r)
}

public fun testVecPacksuSigned() {
    val a: VectorSignedShort = Simd<Short>(listOf(0.toShort(), 1.toShort(), (-1).toShort(), 42.toShort(), 32767.toShort(), (-32768).toShort(), 30000.toShort(), (-30000).toShort())).into()
    val b: VectorSignedShort = Simd<Short>(listOf(32767.toShort(), (-32768).toShort(), 12345.toShort(), (-12345).toShort(), 0.toShort(), 1.toShort(), (-1).toShort(), 42.toShort())).into()
    val d = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 0u.toUByte(), 42u.toUByte(), 255u.toUByte(), 0u.toUByte(), 255u.toUByte(), 0u.toUByte(), 255u.toUByte(), 0u.toUByte(), 255u.toUByte(), 0u.toUByte(), 0u.toUByte(), 1u.toUByte(), 0u.toUByte(), 42u.toUByte()))
    val r = U8x16.from(vecPacksu(a, b))
    check(d == r)
}

public fun testVecPacksuUnsigned() {
    val a: VectorUnsignedShort = Simd<UShort>(listOf(65535u.toUShort(), 32768u.toUShort(), 1234u.toUShort(), 5678u.toUShort(), 16u.toUShort(), 8u.toUShort(), 4u.toUShort(), 2u.toUShort())).into()
    val b: VectorUnsignedShort = Simd<UShort>(listOf(30000u.toUShort(), 25000u.toUShort(), 20000u.toUShort(), 15000u.toUShort(), 31u.toUShort(), 63u.toUShort(), 127u.toUShort(), 255u.toUShort())).into()
    val d = Simd<UByte>(listOf(255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 16u.toUByte(), 8u.toUByte(), 4u.toUByte(), 2u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 31u.toUByte(), 63u.toUByte(), 127u.toUByte(), 255u.toUByte()))
    val r = U8x16.from(vecPacksu(a, b))
    check(d == r)
}

public fun testVecRl() {
    val a: VectorUnsignedInt = Simd<UInt>(listOf(0x12345678u, 0x9ABCDEF0u, 0x0F0F0F0Fu, 0x12345678u)).into()
    val b: VectorUnsignedInt = Simd<UInt>(listOf(4u, 8u, 12u, 68u)).into()
    val d = Simd<UInt>(listOf(0x23456781u, 0xBCDEF09Au, 0xF0F0F0F0u, 0x23456781u))
    val r = U32x4.from(vecRl(a, b))
    check(d == r)
}

public fun testVecUnpackhI() {
    val a: VectorSignedShort = Simd<Short>(listOf(0x1234.toShort(), (-2).toShort(), 0x0F0F.toShort(), (-32768).toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0.toShort())).into()
    val d = Simd<Int>(listOf(0x1234, -2, 0x0F0F, -32768))
    val r = I32x4.from(vecUnpackh(a))
    check(d == r)
}

public fun testVecUnpackhU() {
    val a: VectorUnsignedShort = Simd<UShort>(listOf(0x1234u.toUShort(), 0xFFFFu.toUShort(), 0x0F0Fu.toUShort(), 0x8000u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort())).into()
    val d = Simd<UInt>(listOf(0x1234u, 0xFFFFu, 0x0F0Fu, 0x8000u))
    val r = U32x4.from(vecUnpackh(a))
    check(d == r)
}

public fun testVecUnpacklI() {
    val a: VectorSignedShort = Simd<Short>(listOf(0.toShort(), 0.toShort(), 0.toShort(), 0.toShort(), 0x1234.toShort(), (-2).toShort(), 0x0F0F.toShort(), (-32768).toShort())).into()
    val d = Simd<Int>(listOf(0x1234, -2, 0x0F0F, -32768))
    val r = I32x4.from(vecUnpackl(a))
    check(d == r)
}

public fun testVecUnpacklU() {
    val a: VectorUnsignedShort = Simd<UShort>(listOf(0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0x1234u.toUShort(), 0xFFFFu.toUShort(), 0x0F0Fu.toUShort(), 0x8000u.toUShort())).into()
    val d = Simd<UInt>(listOf(0x1234u, 0xFFFFu, 0x0F0Fu, 0x8000u))
    val r = U32x4.from(vecUnpackl(a))
    check(d == r)
}

public fun testVecAvg() {
    val a: VectorUnsignedInt = Simd<UInt>(listOf(2u, 1u, UInt.MAX_VALUE, 0u)).into()
    val b: VectorUnsignedInt = Simd<UInt>(listOf(4u, 2u, 2u, 0u)).into()
    val d = Simd<UInt>(listOf(3u, (1u + 2u).divCeil(2).toUInt(), (UInt.MAX_VALUE.toULong() + 2uL).divCeil(2).toUInt(), 0u))
    val r = U32x4.from(vecAvg(a, b))
    check(d == r)
}

public fun testVecChecksum() {
    val a: VectorUnsignedInt = Simd<UInt>(listOf(1u, 2u, 3u, UInt.MAX_VALUE)).into()
    val b: VectorUnsignedInt = Simd<UInt>(listOf(5u, 6u, 7u, 8u)).into()
    val d = Simd<UInt>(listOf(0u, 12u, 0u, 0u))
    val r = U32x4.from(vecChecksum(a, b))
    check(d == r)
}

public fun testVecAddU128() {
    val a: VectorUnsignedChar = Simd<UByte>(listOf(0x01u.toUByte(), 0x05u.toUByte(), 0x0Fu.toUByte(), 0x1Au.toUByte(), 0x2Fu.toUByte(), 0x3Fu.toUByte(), 0x50u.toUByte(), 0x65u.toUByte(), 0x7Au.toUByte(), 0x8Fu.toUByte(), 0x9Au.toUByte(), 0xADu.toUByte(), 0xB0u.toUByte(), 0xC3u.toUByte(), 0xD5u.toUByte(), 0xE8u.toUByte())).into()
    val b: VectorUnsignedChar = Simd<UByte>(listOf(0xF0u.toUByte(), 0xEFu.toUByte(), 0xC3u.toUByte(), 0xB1u.toUByte(), 0x92u.toUByte(), 0x71u.toUByte(), 0x5Au.toUByte(), 0x43u.toUByte(), 0x3Bu.toUByte(), 0x29u.toUByte(), 0x13u.toUByte(), 0x04u.toUByte(), 0xD7u.toUByte(), 0xA1u.toUByte(), 0x8Cu.toUByte(), 0x76u.toUByte())).into()
    val d = Simd<UByte>(listOf(0xF1u.toUByte(), 0xF4u.toUByte(), 0xD2u.toUByte(), 0xCBu.toUByte(), 0xC1u.toUByte(), 0xB0u.toUByte(), 0xAAu.toUByte(), 0xA8u.toUByte(), 0xB5u.toUByte(), 0xB8u.toUByte(), 0xADu.toUByte(), 0xB2u.toUByte(), 0x88u.toUByte(), 0x65u.toUByte(), 0x62u.toUByte(), 0x5Eu.toUByte()))
    val r = U8x16.from(vecAddU128(a, b))
    check(d == r)
}

public fun testVecAddcU128() {
    run {
        val a = U128.MAX_VALUE
        val b = U128(0x0uL, 0x1uL)
        val d: U128 = transmuteWithLayouts<VectorUnsignedChar, U128>(vecAddcU128(transmuteWithLayouts<U128, VectorUnsignedChar>(a, UnresolvedTypeLayout<U128>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<U128, VectorUnsignedChar>(b, PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), UnresolvedTypeLayout<VectorUnsignedChar>())), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<U128>())
        check(a.checkedAdd(b) == null)
        check(d == U128(0x0uL, 0x1uL))
        val a1 = U128(0x0uL, 0x1uL)
        val b1 = U128(0x0uL, 0x1uL)
        val d1: U128 = transmuteWithLayouts<VectorUnsignedChar, U128>(vecAddcU128(transmuteWithLayouts<U128, VectorUnsignedChar>(a1, PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<U128, VectorUnsignedChar>(b1, PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), UnresolvedTypeLayout<VectorUnsignedChar>())), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<U128>())
        check(a1.checkedAdd(b1) != null)
        check(d1 == U128(0x0uL, 0x0uL))
    }
}

public fun testVecSubcU128() {
    run {
        val a = U128(0x0uL, 0x0uL)
        val b = U128(0x0uL, 0x1uL)
        val d: U128 = transmuteWithLayouts<VectorUnsignedChar, U128>(vecSubcU128(transmuteWithLayouts<U128, VectorUnsignedChar>(a, PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<U128, VectorUnsignedChar>(b, PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), UnresolvedTypeLayout<VectorUnsignedChar>())), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<U128>())
        check(a.checkedSub(b) == null)
        check(d == U128(0x0uL, 0x0uL))
        val a1 = U128(0x0uL, 0x1uL)
        val b1 = U128(0x0uL, 0x1uL)
        val d1: U128 = transmuteWithLayouts<VectorUnsignedChar, U128>(vecSubcU128(transmuteWithLayouts<U128, VectorUnsignedChar>(a1, PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<U128, VectorUnsignedChar>(b1, PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), UnresolvedTypeLayout<VectorUnsignedChar>())), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<U128>())
        check(a1.checkedSub(b1) != null)
        check(d1 == U128(0x0uL, 0x1uL))
    }
}

public fun testVecMuleU() {
    val a: VectorUnsignedShort = Simd<UShort>(listOf(0xFFFFu.toUShort(), 0u.toUShort(), 2u.toUShort(), 0u.toUShort(), 2u.toUShort(), 0u.toUShort(), 1u.toUShort(), 0u.toUShort())).into()
    val b: VectorUnsignedShort = Simd<UShort>(listOf(0xFFFFu.toUShort(), 0u.toUShort(), 4u.toUShort(), 0u.toUShort(), 0xFFFFu.toUShort(), 0u.toUShort(), 2u.toUShort(), 0u.toUShort())).into()
    val d = Simd<UInt>(listOf(0xFFFE0001u, 8u, 0x0001FFFEu, 2u))
    val r = U32x4.from(vecMule(a, b))
    check(d == r)
}

public fun testVecMuleI() {
    val a: VectorSignedShort = Simd<Short>(listOf(Short.MIN_VALUE, 0.toShort(), (-2).toShort(), 0.toShort(), 2.toShort(), 0.toShort(), 1.toShort(), 0.toShort())).into()
    val b: VectorSignedShort = Simd<Short>(listOf(Short.MIN_VALUE, 0.toShort(), 4.toShort(), 0.toShort(), Short.MAX_VALUE, 0.toShort(), 2.toShort(), 0.toShort())).into()
    val d = Simd<Int>(listOf(0x40000000, -8, 0xFFFE, 2))
    val r = I32x4.from(vecMule(a, b))
    check(d == r)
}

public fun testVecMuloU() {
    val a: VectorUnsignedShort = Simd<UShort>(listOf(0u.toUShort(), 0xFFFFu.toUShort(), 0u.toUShort(), 2u.toUShort(), 0u.toUShort(), 2u.toUShort(), 0u.toUShort(), 1u.toUShort())).into()
    val b: VectorUnsignedShort = Simd<UShort>(listOf(0u.toUShort(), 0xFFFFu.toUShort(), 0u.toUShort(), 4u.toUShort(), 0u.toUShort(), 0xFFFFu.toUShort(), 0u.toUShort(), 2u.toUShort())).into()
    val d = Simd<UInt>(listOf(0xFFFE0001u, 8u, 0x0001FFFEu, 2u))
    val r = U32x4.from(vecMulo(a, b))
    check(d == r)
}

public fun testVecMuloI() {
    val a: VectorSignedShort = Simd<Short>(listOf(0.toShort(), Short.MIN_VALUE, 0.toShort(), (-2).toShort(), 0.toShort(), 2.toShort(), 0.toShort(), 1.toShort())).into()
    val b: VectorSignedShort = Simd<Short>(listOf(0.toShort(), Short.MIN_VALUE, 0.toShort(), 4.toShort(), 0.toShort(), Short.MAX_VALUE, 0.toShort(), 2.toShort())).into()
    val d = Simd<Int>(listOf(0x40000000, -8, 0xFFFE, 2))
    val r = I32x4.from(vecMulo(a, b))
    check(d == r)
}

public fun testVecMulhU() {
    val a: VectorUnsignedInt = Simd<UInt>(listOf(UInt.MAX_VALUE, 2u, 2u, 1u)).into()
    val b: VectorUnsignedInt = Simd<UInt>(listOf(UInt.MAX_VALUE, 4u, UInt.MAX_VALUE, 2u)).into()
    val d = Simd<UInt>(listOf(UInt.MAX_VALUE - 1u, 0u, 1u, 0u))
    val r = U32x4.from(vecMulh(a, b))
    check(d == r)
}

public fun testVecMulhI() {
    val a: VectorSignedInt = Simd<Int>(listOf(Int.MIN_VALUE, -2, 2, 1)).into()
    val b: VectorSignedInt = Simd<Int>(listOf(Int.MIN_VALUE, 4, Int.MAX_VALUE, 2)).into()
    val d = Simd<Int>(listOf(0x40000000, -1, 0, 0))
    val r = I32x4.from(vecMulh(a, b))
    check(d == r)
}

public fun testVecGfmsum1() {
    val a: VectorUnsignedShort = Simd<UShort>(listOf(0x1234u.toUShort(), 0x5678u.toUShort(), 0x9ABCu.toUShort(), 0xDEF0u.toUShort(), 0x1357u.toUShort(), 0x2468u.toUShort(), 0xACE0u.toUShort(), 0xBDF0u.toUShort())).into()
    val b: VectorUnsignedShort = Simd<UShort>(listOf(0xFFFFu.toUShort(), 0x0001u.toUShort(), 0x8000u.toUShort(), 0x7FFFu.toUShort(), 0xAAAAu.toUShort(), 0x5555u.toUShort(), 0x1234u.toUShort(), 0x5678u.toUShort())).into()
    val d = Simd<UInt>(listOf(0xE13A794u, 0x68764A50u, 0x94AA3Eu, 0x2C93F300u))
    val r = U32x4.from(vecGfmsum(a, b))
    check(d == r)
}

public fun testVecGfmsum2() {
    val a: VectorUnsignedShort = Simd<UShort>(listOf(0x0000u.toUShort(), 0xFFFFu.toUShort(), 0xAAAAu.toUShort(), 0x5555u.toUShort(), 0x1234u.toUShort(), 0x5678u.toUShort(), 0x9ABCu.toUShort(), 0xDEF0u.toUShort())).into()
    val b: VectorUnsignedShort = Simd<UShort>(listOf(0xFFFFu.toUShort(), 0x0000u.toUShort(), 0x5555u.toUShort(), 0xAAAAu.toUShort(), 0x0001u.toUShort(), 0x8000u.toUShort(), 0x7FFFu.toUShort(), 0x1357u.toUShort())).into()
    val d = Simd<UInt>(listOf(0u, 0u, 0x2B3C1234u, 0x3781D244u))
    val r = U32x4.from(vecGfmsum(a, b))
    check(d == r)
}

public fun testVecGfmsum128() {
    val a = VectorUnsignedLongLong(listOf(1, 2))
    val b = VectorUnsignedLongLong(listOf(3, 4))
    val d: U128 = transmuteWithLayouts<VectorUnsignedChar, U128>(vecGfmsum128(a, b), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<U128>())
    check(d == U128(0x0uL, 0xbuL))
    val a1 = VectorUnsignedLongLong(listOf(0x0101010101010101, 0x0202020202020202))
    val b1 = VectorUnsignedLongLong(listOf(0x0404040404040404, 0x0505050505050505))
    val d1: U128 = transmuteWithLayouts<VectorUnsignedChar, U128>(vecGfmsum128(a1, b1), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<U128>())
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
    val c: VectorBoolInt = run { val simdArg0 = e; val simdArg1 = f; VectorBoolInt() }
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
        val c: VectorUnsignedChar = transmuteWithLayouts<U128, VectorUnsignedChar>(U128(0x0uL, 0x64uL), PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }), UnresolvedTypeLayout<VectorUnsignedChar>())
        val d: U128 = transmuteWithLayouts<VectorUnsignedChar, U128>(vecMsumU128(a, b, c, U128(0x0uL, 0x0uL)), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<U128>())
        check(d == (U128(0x0uL, 0x1uL) * U128(0x0uL, 0x3uL)) + (U128(0x0uL, 0x2uL) * U128(0x0uL, 0x4uL)) + U128(0x0uL, 0x64uL))
        val d1: U128 = transmuteWithLayouts<VectorUnsignedChar, U128>(vecMsumU128(a, b, c, U128(0x0uL, 0x4uL)), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<U128>())
        check(d1 == (U128(0x0uL, 0x1uL) * U128(0x0uL, 0x3uL)) + (U128(0x0uL, 0x2uL) * U128(0x0uL, 0x4uL)) * U128(0x0uL, 0x2uL) + U128(0x0uL, 0x64uL))
        val d2: U128 = transmuteWithLayouts<VectorUnsignedChar, U128>(vecMsumU128(a, b, c, U128(0x0uL, 0x8uL)), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<U128>())
        check(d2 == (U128(0x0uL, 0x1uL) * U128(0x0uL, 0x3uL)) * U128(0x0uL, 0x2uL) + (U128(0x0uL, 0x2uL) * U128(0x0uL, 0x4uL)) + U128(0x0uL, 0x64uL))
        val d3: U128 = transmuteWithLayouts<VectorUnsignedChar, U128>(vecMsumU128(a, b, c, U128(0x0uL, 0xcuL)), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<U128>())
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
    val a: VectorFloat = Simd<Float>(listOf(1.0f.toFloat(), Float.NaN, Float.NaN, 3.14f.toFloat())).into()
    val b: VectorFloat = Simd<Float>(listOf(2.0f.toFloat(), Float.NaN, 5.0f.toFloat(), 2.0f.toFloat())).into()
    val d = Simd<Int>(listOf(0, 0, 0, 0.inv()))
    val r = I32x4.from(vecCmpgt(a, b))
    check(d == r)
}

public fun testVecCmpge() {
    val a: VectorFloat = Simd<Float>(listOf(1.0f.toFloat(), Float.NaN, Float.NaN, 3.14f.toFloat())).into()
    val b: VectorFloat = Simd<Float>(listOf(1.0f.toFloat(), Float.NaN, 5.0f.toFloat(), 2.0f.toFloat())).into()
    val d = Simd<Int>(listOf(0.inv(), 0, 0, 0.inv()))
    val r = I32x4.from(vecCmpge(a, b))
    check(d == r)
}

public fun testVecCmplt() {
    val a: VectorFloat = Simd<Float>(listOf(1.0f.toFloat(), Float.NaN, Float.NaN, 2.0f.toFloat())).into()
    val b: VectorFloat = Simd<Float>(listOf(2.0f.toFloat(), Float.NaN, 5.0f.toFloat(), 2.0f.toFloat())).into()
    val d = Simd<Int>(listOf(0.inv(), 0, 0, 0))
    val r = I32x4.from(vecCmplt(a, b))
    check(d == r)
}

public fun testVecCmple() {
    val a: VectorFloat = Simd<Float>(listOf(1.0f.toFloat(), Float.NaN, Float.NaN, 2.0f.toFloat())).into()
    val b: VectorFloat = Simd<Float>(listOf(1.0f.toFloat(), Float.NaN, 5.0f.toFloat(), 3.14f.toFloat())).into()
    val d = Simd<Int>(listOf(0.inv(), 0, 0, 0.inv()))
    val r = I32x4.from(vecCmple(a, b))
    check(d == r)
}

public fun testVecCmpeq() {
    val a: VectorFloat = Simd<Float>(listOf(1.0f.toFloat(), Float.NaN, Float.NaN, 2.0f.toFloat())).into()
    val b: VectorFloat = Simd<Float>(listOf(1.0f.toFloat(), Float.NaN, 5.0f.toFloat(), 3.14f.toFloat())).into()
    val d = Simd<Int>(listOf(0.inv(), 0, 0, 0))
    val r = I32x4.from(vecCmpeq(a, b))
    check(d == r)
}

public fun testVecCmpne() {
    val a: VectorFloat = Simd<Float>(listOf(1.0f.toFloat(), Float.NaN, Float.NaN, 2.0f.toFloat())).into()
    val b: VectorFloat = Simd<Float>(listOf(1.0f.toFloat(), Float.NaN, 5.0f.toFloat(), 3.14f.toFloat())).into()
    val d = Simd<Int>(listOf(0, 0.inv(), 0.inv(), 0.inv()))
    val r = I32x4.from(vecCmpne(a, b))
    check(d == r)
}

public fun testVecMaxF32() {
    val a: VectorFloat = Simd<Float>(listOf(1.0f.toFloat(), Float.NaN, Float.POSITIVE_INFINITY, 2.0f.toFloat())).into()
    val b: VectorFloat = Simd<Float>(listOf((-10.0f).toFloat(), (-10.0f).toFloat(), 5.0f.toFloat(), Float.NaN)).into()
    val d = Simd<Float>(listOf(1.0f.toFloat(), (-10.0f).toFloat(), Float.POSITIVE_INFINITY, 2.0f.toFloat()))
    val r = F32x4.from(vecMax(a, b))
    check(d == r)
}

public fun testVecMinF32() {
    val a: VectorFloat = Simd<Float>(listOf(1.0f.toFloat(), Float.NaN, Float.POSITIVE_INFINITY, 2.0f.toFloat())).into()
    val b: VectorFloat = Simd<Float>(listOf((-10.0f).toFloat(), (-10.0f).toFloat(), 5.0f.toFloat(), Float.NaN)).into()
    val d = Simd<Float>(listOf((-10.0f).toFloat(), (-10.0f).toFloat(), 5.0f.toFloat(), 2.0f.toFloat()))
    val r = F32x4.from(vecMin(a, b))
    check(d == r)
}

public fun testVecMaxF64() {
    val a: VectorDouble = Simd<Double>(listOf(Double.NaN, 2.0.toDouble())).into()
    val b: VectorDouble = Simd<Double>(listOf((-10.0).toDouble(), Double.NaN)).into()
    val d = Simd<Double>(listOf((-10.0).toDouble(), 2.0.toDouble()))
    val r = F64x2.from(vecMax(a, b))
    check(d == r)
}

public fun testVecMinF64() {
    val a: VectorDouble = Simd<Double>(listOf(Double.NaN, 2.0.toDouble())).into()
    val b: VectorDouble = Simd<Double>(listOf((-10.0).toDouble(), Double.NaN)).into()
    val d = Simd<Double>(listOf((-10.0).toDouble(), 2.0.toDouble()))
    val r = F64x2.from(vecMin(a, b))
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
