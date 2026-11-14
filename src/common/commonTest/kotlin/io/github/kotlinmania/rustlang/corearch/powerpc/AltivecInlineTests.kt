// port-lint: tests powerpc/altivec.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.powerpc

import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr

// PowerPC AltiVec intrinsics.
//
// AltiVec is a brandname trademarked by Freescale (previously Motorola) for
// the standard `Category: Vector` part of the Power ISA v.2.03 specification.
// This Category is also known as VMX (used by IBM), and "Velocity Engine" (a
// brand name previously used by Apple).
//
// The references are: [POWER ISA v2.07B (for POWER8 & POWER8 with NVIDIA
// NVlink)] and [POWER ISA v3.0B (for POWER9)].
//
// [POWER ISA v2.07B (for POWER8 & POWER8 with NVIDIA NVlink)]: https://ibm.box.com/s/jd5w15gz301s5b5dt375mshpq9c3lh4u
// [POWER ISA v3.0B (for POWER9)]: https://ibm.box.com/s/1hzcwkwf8rbju5h9iyf44wm94amnlcrv

public fun testVecLd() {
    val pat = listOf(u8x16.new(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15), u8x16.new(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31))
    for (off in 0 until 16) {
        val v = u8x16.from(vecLd(0, ConstPtr(((constPtrFromRustCast(pat.asPtr()))).rawAddress + ((off).toLong() * (PrimitiveLayouts.U8).strideBytes))))
        check(v == u8x16.new(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15))
    }
    for (off1 in 16 until 32) {
        val v1 = u8x16.from(vecLd(0, ConstPtr(((constPtrFromRustCast(pat.asPtr()))).rawAddress + ((off1).toLong() * (PrimitiveLayouts.U8).strideBytes))))
        check(v1 == u8x16.new(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31))
    }
}

public fun testVecXl() {
    val pat = listOf(u8x16.new(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15), u8x16.new(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31))
    for (off in 0 until 16) {
        val value = u8x16.from(vecXl(0, ConstPtr(((constPtrFromRustCast(pat.asPtr()))).rawAddress + ((off).toLong() * (PrimitiveLayouts.U8).strideBytes))))
        for (i in 0 until 16) {
            val v = value.extractDyn(i)
            check(off.toInt() + i == v.toInt())
        }
    }
}

public fun testVecXst() {
    val v = VectorUnsignedChar.from(u8x16.new(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15))
    for (off in 0 until 16) {
        val localStorageL4802C12 = AddressableArrayLocal.fromUByteArray(UByteArray(32) { 0u.toUByte() })
        var buf by localStorageL4802C12
        run {
            vecXst(v, 0, MutPtr(((localStorageL4802C12.elementMutPtr(0))).rawAddress + ((off).toLong() * (PrimitiveLayouts.U8).strideBytes)))
        }
        for (i in 0 until 16) {
            check(i.toUByte() == ((buf).sliceArray(off.toInt() until buf.size))[i])
        }
    }
}

public fun testVecLdl() {
    val pat = listOf(u8x16.new(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15), u8x16.new(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31))
    for (off in 0 until 16) {
        val v = u8x16.from(vecLdl(0, ConstPtr(((constPtrFromRustCast(pat.asPtr()))).rawAddress + ((off).toLong() * (PrimitiveLayouts.U8).strideBytes))))
        check(v == u8x16.new(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15))
    }
    for (off1 in 16 until 32) {
        val v1 = u8x16.from(vecLdl(0, ConstPtr(((constPtrFromRustCast(pat.asPtr()))).rawAddress + ((off1).toLong() * (PrimitiveLayouts.U8).strideBytes))))
        check(v1 == u8x16.new(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31))
    }
}

public fun testVecLdeU8() {
    val pat = listOf(u8x16.new(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15))
    for (off in 0 until 16) {
        val v = u8x16.from(vecLde(off, constPtrFromRustCast(pat.asPtr())))
        check(off.toUByte() == v.extractDyn(((off as Any?) as Unit)))
    }
}

public fun testVecLdeU16() {
    val pat = listOf(u16x8.new(0, 1, 2, 3, 4, 5, 6, 7))
    for (off in 0 until 8) {
        val v = u16x8.from(vecLde(off * 2, constPtrFromRustCast(pat.asPtr())))
        check(off.toUShort() == v.extractDyn(((off as Any?) as Unit)))
    }
}

public fun testVecLdeU32() {
    val pat = listOf(u32x4.new(0, 1, 2, 3))
    for (off in 0 until 4) {
        val v = u32x4.from(vecLde(off * 4, constPtrFromRustCast(pat.asPtr())))
        check(off.toUInt() == v.extractDyn(((off as Any?) as Unit)))
    }
}

public fun testVecFloor() {
    val a: STL = f32x4.new(1.1, 1.9, -0.5, -0.9).into()
    val d = f32x4.new(1.0, 1.0, -1.0, -1.0)
    val r = f32x4.from(vecFloor(a))
    check(d == r)
}

public fun testVecExpte() {
    val a = VectorFloat.from(f32x4.new(0.0, 2.0, 2.0, -1.0))
    val d = VectorFloat.from(f32x4.new(1.0, 4.0, 4.0, 0.5))
    val r = m32x4.from(vecCmple(vecAbs(vecSub(vecExpte(a), d)), vecSplats(1.1920929E-7f)))
    val e = m32x4.splat(true)
    check(e == r)
}

public fun testVecCmpgtI8() {
    val a: STL = i8x16.fromArray(listOf(1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i8x16.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val d = m8x16.fromArray(booleanArrayOf(true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false))
    val r = m8x16.from(vecCmpgt(a, b))
    check(d == r)
}

public fun testVecCmpgtU8() {
    val a: STL = u8x16.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u8x16.fromArray(listOf(0, 0, 255, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val d = m8x16.fromArray(booleanArrayOf(true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false))
    val r = m8x16.from(vecCmpgt(a, b))
    check(d == r)
}

public fun testVecCmpgtI16() {
    val a: STL = i16x8.fromArray(listOf(1, -1, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i16x8.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0)).into()
    val d = m16x8.fromArray(booleanArrayOf(true, false, true, false, false, false, false, false))
    val r = m16x8.from(vecCmpgt(a, b))
    check(d == r)
}

public fun testVecCmpgtU16() {
    val a: STL = u16x8.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u16x8.fromArray(listOf(0, 0, 255, 1, 0, 0, 0, 0)).into()
    val d = m16x8.fromArray(booleanArrayOf(true, true, false, false, false, false, false, false))
    val r = m16x8.from(vecCmpgt(a, b))
    check(d == r)
}

public fun testVecCmpgtI32() {
    val a: STL = i32x4.fromArray(listOf(1, -1, 0, 0)).into()
    val b: STL = i32x4.fromArray(listOf(0, -1, 0, 1)).into()
    val d = m32x4.fromArray(booleanArrayOf(true, false, false, false))
    val r = m32x4.from(vecCmpgt(a, b))
    check(d == r)
}

public fun testVecCmpgtU32() {
    val a: STL = u32x4.fromArray(listOf(1, 255, 0, 0)).into()
    val b: STL = u32x4.fromArray(listOf(0, 255, 0, 1)).into()
    val d = m32x4.fromArray(booleanArrayOf(true, false, false, false))
    val r = m32x4.from(vecCmpgt(a, b))
    check(d == r)
}

public fun testVecCmpge() {
    val a: STL = f32x4.fromArray(listOf(0.1, -0.1, 0.0, 0.99)).into()
    val b: STL = f32x4.fromArray(listOf(0.1, 0.0, 0.1, 1.0)).into()
    val d = m32x4.fromArray(booleanArrayOf(true, false, false, false))
    val r = m32x4.from(vecCmpge(a, b))
    check(d == r)
}

public fun testVecCmpeqI8() {
    val a: STL = i8x16.fromArray(listOf(1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i8x16.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val d = m8x16.fromArray(booleanArrayOf(false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true))
    val r = m8x16.from(vecCmpeq(a, b))
    check(d == r)
}

public fun testVecCmpeqU8() {
    val a: STL = u8x16.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u8x16.fromArray(listOf(0, 0, 255, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val d = m8x16.fromArray(booleanArrayOf(false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true))
    val r = m8x16.from(vecCmpeq(a, b))
    check(d == r)
}

public fun testVecCmpeqI16() {
    val a: STL = i16x8.fromArray(listOf(1, -1, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i16x8.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0)).into()
    val d = m16x8.fromArray(booleanArrayOf(false, false, false, false, true, true, true, true))
    val r = m16x8.from(vecCmpeq(a, b))
    check(d == r)
}

public fun testVecCmpeqU16() {
    val a: STL = u16x8.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u16x8.fromArray(listOf(0, 0, 255, 1, 0, 0, 0, 0)).into()
    val d = m16x8.fromArray(booleanArrayOf(false, false, false, false, true, true, true, true))
    val r = m16x8.from(vecCmpeq(a, b))
    check(d == r)
}

public fun testVecCmpeqI32() {
    val a: STL = i32x4.fromArray(listOf(1, -1, 0, 0)).into()
    val b: STL = i32x4.fromArray(listOf(0, -1, 0, 1)).into()
    val d = m32x4.fromArray(booleanArrayOf(false, true, true, false))
    val r = m32x4.from(vecCmpeq(a, b))
    check(d == r)
}

public fun testVecCmpeqU32() {
    val a: STL = u32x4.fromArray(listOf(1, 255, 0, 0)).into()
    val b: STL = u32x4.fromArray(listOf(0, 255, 0, 1)).into()
    val d = m32x4.fromArray(booleanArrayOf(false, true, true, false))
    val r = m32x4.from(vecCmpeq(a, b))
    check(d == r)
}

public fun testVecCmpneI8() {
    val a: STL = i8x16.fromArray(listOf(1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i8x16.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val d = m8x16.fromArray(booleanArrayOf(true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false))
    val r = m8x16.from(vecCmpne(a, b))
    check(d == r)
}

public fun testVecCmpneU8() {
    val a: STL = u8x16.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u8x16.fromArray(listOf(0, 0, 255, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val d = m8x16.fromArray(booleanArrayOf(true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false))
    val r = m8x16.from(vecCmpne(a, b))
    check(d == r)
}

public fun testVecCmpneI16() {
    val a: STL = i16x8.fromArray(listOf(1, -1, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i16x8.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0)).into()
    val d = m16x8.fromArray(booleanArrayOf(true, true, true, true, false, false, false, false))
    val r = m16x8.from(vecCmpne(a, b))
    check(d == r)
}

public fun testVecCmpneU16() {
    val a: STL = u16x8.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u16x8.fromArray(listOf(0, 0, 255, 1, 0, 0, 0, 0)).into()
    val d = m16x8.fromArray(booleanArrayOf(true, true, true, true, false, false, false, false))
    val r = m16x8.from(vecCmpne(a, b))
    check(d == r)
}

public fun testVecCmpneI32() {
    val a: STL = i32x4.fromArray(listOf(1, -1, 0, 0)).into()
    val b: STL = i32x4.fromArray(listOf(0, -1, 0, 1)).into()
    val d = m32x4.fromArray(booleanArrayOf(true, false, false, true))
    val r = m32x4.from(vecCmpne(a, b))
    check(d == r)
}

public fun testVecCmpneU32() {
    val a: STL = u32x4.fromArray(listOf(1, 255, 0, 0)).into()
    val b: STL = u32x4.fromArray(listOf(0, 255, 0, 1)).into()
    val d = m32x4.fromArray(booleanArrayOf(true, false, false, true))
    val r = m32x4.from(vecCmpne(a, b))
    check(d == r)
}

public fun testVecAllEqI8False() {
    val a: STL = i8x16.fromArray(listOf(1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i8x16.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllEq(a, b))
    check(false == r)
}

public fun testVecAllEqU8False() {
    val a: STL = u8x16.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u8x16.fromArray(listOf(0, 0, 255, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllEq(a, b))
    check(false == r)
}

public fun testVecAllEqI16False() {
    val a: STL = i16x8.fromArray(listOf(1, -1, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i16x8.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllEq(a, b))
    check(false == r)
}

public fun testVecAllEqU16False() {
    val a: STL = u16x8.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u16x8.fromArray(listOf(0, 0, 255, 1, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllEq(a, b))
    check(false == r)
}

public fun testVecAllEqI32False() {
    val a: STL = i32x4.fromArray(listOf(1, -1, 0, 0)).into()
    val b: STL = i32x4.fromArray(listOf(0, -1, 0, 1)).into()
    val r = Boolean.from(vecAllEq(a, b))
    check(false == r)
}

public fun testVecAllEqU32False() {
    val a: STL = u32x4.fromArray(listOf(1, 255, 0, 0)).into()
    val b: STL = u32x4.fromArray(listOf(0, 255, 0, 1)).into()
    val r = Boolean.from(vecAllEq(a, b))
    check(false == r)
}

public fun testVecAllEqI8True() {
    val a: STL = i8x16.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i8x16.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllEq(a, b))
    check(true == r)
}

public fun testVecAllEqU8True() {
    val a: STL = u8x16.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u8x16.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllEq(a, b))
    check(true == r)
}

public fun testVecAllEqI16True() {
    val a: STL = i16x8.fromArray(listOf(1, -1, 1, 0, 0, 0, 0, 0)).into()
    val b: STL = i16x8.fromArray(listOf(1, -1, 1, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllEq(a, b))
    check(true == r)
}

public fun testVecAllEqU16True() {
    val a: STL = u16x8.fromArray(listOf(1, 255, 1, 0, 0, 0, 0, 0)).into()
    val b: STL = u16x8.fromArray(listOf(1, 255, 1, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllEq(a, b))
    check(true == r)
}

public fun testVecAllEqI32True() {
    val a: STL = i32x4.fromArray(listOf(1, -1, 0, 1)).into()
    val b: STL = i32x4.fromArray(listOf(1, -1, 0, 1)).into()
    val r = Boolean.from(vecAllEq(a, b))
    check(true == r)
}

public fun testVecAllEqU32True() {
    val a: STL = u32x4.fromArray(listOf(1, 255, 0, 1)).into()
    val b: STL = u32x4.fromArray(listOf(1, 255, 0, 1)).into()
    val r = Boolean.from(vecAllEq(a, b))
    check(true == r)
}

public fun testVecAnyEqI8False() {
    val a: STL = i8x16.fromArray(listOf(1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i8x16.fromArray(listOf(0, 0, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).into()
    val r = Boolean.from(vecAnyEq(a, b))
    check(false == r)
}

public fun testVecAnyEqU8False() {
    val a: STL = u8x16.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u8x16.fromArray(listOf(0, 0, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).into()
    val r = Boolean.from(vecAnyEq(a, b))
    check(false == r)
}

public fun testVecAnyEqI16False() {
    val a: STL = i16x8.fromArray(listOf(1, -1, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i16x8.fromArray(listOf(0, 0, -1, 1, 1, 1, 1, 1)).into()
    val r = Boolean.from(vecAnyEq(a, b))
    check(false == r)
}

public fun testVecAnyEqU16False() {
    val a: STL = u16x8.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u16x8.fromArray(listOf(0, 0, 255, 1, 1, 1, 1, 1)).into()
    val r = Boolean.from(vecAnyEq(a, b))
    check(false == r)
}

public fun testVecAnyEqI32False() {
    val a: STL = i32x4.fromArray(listOf(1, -1, 0, 0)).into()
    val b: STL = i32x4.fromArray(listOf(0, -2, 1, 1)).into()
    val r = Boolean.from(vecAnyEq(a, b))
    check(false == r)
}

public fun testVecAnyEqU32False() {
    val a: STL = u32x4.fromArray(listOf(1, 2, 1, 0)).into()
    val b: STL = u32x4.fromArray(listOf(0, 255, 0, 1)).into()
    val r = Boolean.from(vecAnyEq(a, b))
    check(false == r)
}

public fun testVecAnyEqI8True() {
    val a: STL = i8x16.fromArray(listOf(1, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i8x16.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyEq(a, b))
    check(true == r)
}

public fun testVecAnyEqU8True() {
    val a: STL = u8x16.fromArray(listOf(0, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u8x16.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyEq(a, b))
    check(true == r)
}

public fun testVecAnyEqI16True() {
    val a: STL = i16x8.fromArray(listOf(0, -1, 1, 0, 0, 0, 0, 0)).into()
    val b: STL = i16x8.fromArray(listOf(1, -1, 1, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyEq(a, b))
    check(true == r)
}

public fun testVecAnyEqU16True() {
    val a: STL = u16x8.fromArray(listOf(0, 255, 1, 0, 0, 0, 0, 0)).into()
    val b: STL = u16x8.fromArray(listOf(1, 255, 1, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyEq(a, b))
    check(true == r)
}

public fun testVecAnyEqI32True() {
    val a: STL = i32x4.fromArray(listOf(0, -1, 0, 1)).into()
    val b: STL = i32x4.fromArray(listOf(1, -1, 0, 1)).into()
    val r = Boolean.from(vecAnyEq(a, b))
    check(true == r)
}

public fun testVecAnyEqU32True() {
    val a: STL = u32x4.fromArray(listOf(0, 255, 0, 1)).into()
    val b: STL = u32x4.fromArray(listOf(1, 255, 0, 1)).into()
    val r = Boolean.from(vecAnyEq(a, b))
    check(true == r)
}

public fun testVecAllGeI8False() {
    val a: STL = i8x16.fromArray(listOf(1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i8x16.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllGe(a, b))
    check(false == r)
}

public fun testVecAllGeU8False() {
    val a: STL = u8x16.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u8x16.fromArray(listOf(0, 0, 255, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllGe(a, b))
    check(false == r)
}

public fun testVecAllGeI16False() {
    val a: STL = i16x8.fromArray(listOf(1, -1, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i16x8.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllGe(a, b))
    check(false == r)
}

public fun testVecAllGeU16False() {
    val a: STL = u16x8.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u16x8.fromArray(listOf(0, 0, 255, 1, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllGe(a, b))
    check(false == r)
}

public fun testVecAllGeI32False() {
    val a: STL = i32x4.fromArray(listOf(1, -1, 0, 0)).into()
    val b: STL = i32x4.fromArray(listOf(0, -1, 0, 1)).into()
    val r = Boolean.from(vecAllGe(a, b))
    check(false == r)
}

public fun testVecAllGeU32False() {
    val a: STL = u32x4.fromArray(listOf(1, 255, 0, 0)).into()
    val b: STL = u32x4.fromArray(listOf(0, 255, 1, 1)).into()
    val r = Boolean.from(vecAllGe(a, b))
    check(false == r)
}

public fun testVecAllGeI8True() {
    val a: STL = i8x16.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i8x16.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllGe(a, b))
    check(true == r)
}

public fun testVecAllGeU8True() {
    val a: STL = u8x16.fromArray(listOf(1, 255, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u8x16.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllGe(a, b))
    check(true == r)
}

public fun testVecAllGeI16True() {
    val a: STL = i16x8.fromArray(listOf(1, -1, 42, 0, 0, 0, 0, 0)).into()
    val b: STL = i16x8.fromArray(listOf(1, -5, 2, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllGe(a, b))
    check(true == r)
}

public fun testVecAllGeU16True() {
    val a: STL = u16x8.fromArray(listOf(42, 255, 1, 0, 0, 0, 0, 0)).into()
    val b: STL = u16x8.fromArray(listOf(2, 255, 1, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllGe(a, b))
    check(true == r)
}

public fun testVecAllGeI32True() {
    val a: STL = i32x4.fromArray(listOf(1, -1, 0, 1)).into()
    val b: STL = i32x4.fromArray(listOf(0, -1, 0, 1)).into()
    val r = Boolean.from(vecAllGe(a, b))
    check(true == r)
}

public fun testVecAllGeU32True() {
    val a: STL = u32x4.fromArray(listOf(1, 255, 0, 1)).into()
    val b: STL = u32x4.fromArray(listOf(1, 254, 0, 0)).into()
    val r = Boolean.from(vecAllGe(a, b))
    check(true == r)
}

public fun testVecAnyGeI8False() {
    val a: STL = i8x16.fromArray(listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i8x16.fromArray(listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).into()
    val r = Boolean.from(vecAnyGe(a, b))
    check(false == r)
}

public fun testVecAnyGeU8False() {
    val a: STL = u8x16.fromArray(listOf(1, 254, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u8x16.fromArray(listOf(42, 255, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).into()
    val r = Boolean.from(vecAnyGe(a, b))
    check(false == r)
}

public fun testVecAnyGeI16False() {
    val a: STL = i16x8.fromArray(listOf(1, -1, -2, 0, 0, 0, 0, 0)).into()
    val b: STL = i16x8.fromArray(listOf(2, 0, -1, 1, 1, 1, 1, 1)).into()
    val r = Boolean.from(vecAnyGe(a, b))
    check(false == r)
}

public fun testVecAnyGeU16False() {
    val a: STL = u16x8.fromArray(listOf(1, 2, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u16x8.fromArray(listOf(2, 42, 255, 1, 1, 1, 1, 1)).into()
    val r = Boolean.from(vecAnyGe(a, b))
    check(false == r)
}

public fun testVecAnyGeI32False() {
    val a: STL = i32x4.fromArray(listOf(1, -1, 0, 0)).into()
    val b: STL = i32x4.fromArray(listOf(2, 0, 1, 1)).into()
    val r = Boolean.from(vecAnyGe(a, b))
    check(false == r)
}

public fun testVecAnyGeU32False() {
    val a: STL = u32x4.fromArray(listOf(1, 2, 1, 0)).into()
    val b: STL = u32x4.fromArray(listOf(4, 255, 4, 1)).into()
    val r = Boolean.from(vecAnyGe(a, b))
    check(false == r)
}

public fun testVecAnyGeI8True() {
    val a: STL = i8x16.fromArray(listOf(1, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i8x16.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyGe(a, b))
    check(true == r)
}

public fun testVecAnyGeU8True() {
    val a: STL = u8x16.fromArray(listOf(0, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u8x16.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyGe(a, b))
    check(true == r)
}

public fun testVecAnyGeI16True() {
    val a: STL = i16x8.fromArray(listOf(0, -1, 1, 0, 0, 0, 0, 0)).into()
    val b: STL = i16x8.fromArray(listOf(1, -1, 1, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyGe(a, b))
    check(true == r)
}

public fun testVecAnyGeU16True() {
    val a: STL = u16x8.fromArray(listOf(0, 255, 1, 0, 0, 0, 0, 0)).into()
    val b: STL = u16x8.fromArray(listOf(1, 255, 1, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyGe(a, b))
    check(true == r)
}

public fun testVecAnyGeI32True() {
    val a: STL = i32x4.fromArray(listOf(0, -1, 0, 1)).into()
    val b: STL = i32x4.fromArray(listOf(1, -1, 0, 1)).into()
    val r = Boolean.from(vecAnyGe(a, b))
    check(true == r)
}

public fun testVecAnyGeU32True() {
    val a: STL = u32x4.fromArray(listOf(0, 255, 0, 1)).into()
    val b: STL = u32x4.fromArray(listOf(1, 255, 0, 1)).into()
    val r = Boolean.from(vecAnyGe(a, b))
    check(true == r)
}

public fun testVecAllGtI8False() {
    val a: STL = i8x16.fromArray(listOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i8x16.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllGt(a, b))
    check(false == r)
}

public fun testVecAllGtU8False() {
    val a: STL = u8x16.fromArray(listOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u8x16.fromArray(listOf(0, 0, 255, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllGt(a, b))
    check(false == r)
}

public fun testVecAllGtI16False() {
    val a: STL = i16x8.fromArray(listOf(1, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i16x8.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllGt(a, b))
    check(false == r)
}

public fun testVecAllGtU16False() {
    val a: STL = u16x8.fromArray(listOf(1, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u16x8.fromArray(listOf(0, 0, 255, 1, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllGt(a, b))
    check(false == r)
}

public fun testVecAllGtI32False() {
    val a: STL = i32x4.fromArray(listOf(1, -1, 0, 0)).into()
    val b: STL = i32x4.fromArray(listOf(0, -1, 0, 1)).into()
    val r = Boolean.from(vecAllGt(a, b))
    check(false == r)
}

public fun testVecAllGtU32False() {
    val a: STL = u32x4.fromArray(listOf(1, 255, 0, 0)).into()
    val b: STL = u32x4.fromArray(listOf(0, 255, 1, 1)).into()
    val r = Boolean.from(vecAllGt(a, b))
    check(false == r)
}

public fun testVecAllGtI8True() {
    val a: STL = i8x16.fromArray(listOf(2, 1, -1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i8x16.fromArray(listOf(0, 0, -2, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1)).into()
    val r = Boolean.from(vecAllGt(a, b))
    check(true == r)
}

public fun testVecAllGtU8True() {
    val a: STL = u8x16.fromArray(listOf(1, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).into()
    val b: STL = u8x16.fromArray(listOf(0, 254, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllGt(a, b))
    check(true == r)
}

public fun testVecAllGtI16True() {
    val a: STL = i16x8.fromArray(listOf(1, -1, 42, 1, 1, 1, 1, 1)).into()
    val b: STL = i16x8.fromArray(listOf(0, -5, 2, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllGt(a, b))
    check(true == r)
}

public fun testVecAllGtU16True() {
    val a: STL = u16x8.fromArray(listOf(42, 255, 1, 1, 1, 1, 1, 1)).into()
    val b: STL = u16x8.fromArray(listOf(2, 254, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllGt(a, b))
    check(true == r)
}

public fun testVecAllGtI32True() {
    val a: STL = i32x4.fromArray(listOf(1, -1, 1, 1)).into()
    val b: STL = i32x4.fromArray(listOf(0, -2, 0, 0)).into()
    val r = Boolean.from(vecAllGt(a, b))
    check(true == r)
}

public fun testVecAllGtU32True() {
    val a: STL = u32x4.fromArray(listOf(1, 255, 1, 1)).into()
    val b: STL = u32x4.fromArray(listOf(0, 254, 0, 0)).into()
    val r = Boolean.from(vecAllGt(a, b))
    check(true == r)
}

public fun testVecAnyGtI8False() {
    val a: STL = i8x16.fromArray(listOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i8x16.fromArray(listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).into()
    val r = Boolean.from(vecAnyGt(a, b))
    check(false == r)
}

public fun testVecAnyGtU8False() {
    val a: STL = u8x16.fromArray(listOf(1, 254, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u8x16.fromArray(listOf(42, 255, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).into()
    val r = Boolean.from(vecAnyGt(a, b))
    check(false == r)
}

public fun testVecAnyGtI16False() {
    val a: STL = i16x8.fromArray(listOf(1, -1, -2, 0, 0, 0, 0, 0)).into()
    val b: STL = i16x8.fromArray(listOf(2, 0, -1, 1, 1, 1, 1, 1)).into()
    val r = Boolean.from(vecAnyGt(a, b))
    check(false == r)
}

public fun testVecAnyGtU16False() {
    val a: STL = u16x8.fromArray(listOf(1, 2, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u16x8.fromArray(listOf(2, 42, 255, 1, 1, 1, 1, 1)).into()
    val r = Boolean.from(vecAnyGt(a, b))
    check(false == r)
}

public fun testVecAnyGtI32False() {
    val a: STL = i32x4.fromArray(listOf(1, -1, 0, 0)).into()
    val b: STL = i32x4.fromArray(listOf(2, 0, 1, 1)).into()
    val r = Boolean.from(vecAnyGt(a, b))
    check(false == r)
}

public fun testVecAnyGtU32False() {
    val a: STL = u32x4.fromArray(listOf(1, 2, 1, 0)).into()
    val b: STL = u32x4.fromArray(listOf(4, 255, 4, 1)).into()
    val r = Boolean.from(vecAnyGt(a, b))
    check(false == r)
}

public fun testVecAnyGtI8True() {
    val a: STL = i8x16.fromArray(listOf(1, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i8x16.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyGt(a, b))
    check(true == r)
}

public fun testVecAnyGtU8True() {
    val a: STL = u8x16.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u8x16.fromArray(listOf(0, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyGt(a, b))
    check(true == r)
}

public fun testVecAnyGtI16True() {
    val a: STL = i16x8.fromArray(listOf(1, -1, 1, 0, 0, 0, 0, 0)).into()
    val b: STL = i16x8.fromArray(listOf(0, -1, 1, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyGt(a, b))
    check(true == r)
}

public fun testVecAnyGtU16True() {
    val a: STL = u16x8.fromArray(listOf(1, 255, 1, 0, 0, 0, 0, 0)).into()
    val b: STL = u16x8.fromArray(listOf(0, 255, 1, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyGt(a, b))
    check(true == r)
}

public fun testVecAnyGtI32True() {
    val a: STL = i32x4.fromArray(listOf(1, -1, 0, 1)).into()
    val b: STL = i32x4.fromArray(listOf(0, -1, 0, 1)).into()
    val r = Boolean.from(vecAnyGt(a, b))
    check(true == r)
}

public fun testVecAnyGtU32True() {
    val a: STL = u32x4.fromArray(listOf(1, 255, 0, 1)).into()
    val b: STL = u32x4.fromArray(listOf(0, 255, 0, 1)).into()
    val r = Boolean.from(vecAnyGt(a, b))
    check(true == r)
}

public fun testVecAllInTrue() {
    val a: STL = f32x4.fromArray(listOf(0.0, -0.1, 0.0, 0.0)).into()
    val b: STL = f32x4.fromArray(listOf(0.1, 0.2, 0.0, 0.0)).into()
    val r = Boolean.from(vecAllIn(a, b))
    check(true == r)
}

public fun testVecAllInFalse() {
    val a: STL = f32x4.fromArray(listOf(0.5, 0.4, -0.5, 0.8)).into()
    val b: STL = f32x4.fromArray(listOf(0.1, 0.4, -0.5, 0.8)).into()
    val r = Boolean.from(vecAllIn(a, b))
    check(false == r)
}

public fun testVecAllLeI8False() {
    val a: STL = i8x16.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i8x16.fromArray(listOf(1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllLe(a, b))
    check(false == r)
}

public fun testVecAllLeU8False() {
    val a: STL = u8x16.fromArray(listOf(0, 0, 255, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u8x16.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllLe(a, b))
    check(false == r)
}

public fun testVecAllLeI16False() {
    val a: STL = i16x8.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0)).into()
    val b: STL = i16x8.fromArray(listOf(1, -1, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllLe(a, b))
    check(false == r)
}

public fun testVecAllLeU16False() {
    val a: STL = u16x8.fromArray(listOf(0, 0, 255, 1, 0, 0, 0, 0)).into()
    val b: STL = u16x8.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllLe(a, b))
    check(false == r)
}

public fun testVecAllLeI32False() {
    val a: STL = i32x4.fromArray(listOf(0, -1, 0, 1)).into()
    val b: STL = i32x4.fromArray(listOf(1, -1, 0, 0)).into()
    val r = Boolean.from(vecAllLe(a, b))
    check(false == r)
}

public fun testVecAllLeU32False() {
    val a: STL = u32x4.fromArray(listOf(0, 255, 1, 1)).into()
    val b: STL = u32x4.fromArray(listOf(1, 255, 0, 0)).into()
    val r = Boolean.from(vecAllLe(a, b))
    check(false == r)
}

public fun testVecAllLeI8True() {
    val a: STL = i8x16.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i8x16.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllLe(a, b))
    check(true == r)
}

public fun testVecAllLeU8True() {
    val a: STL = u8x16.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u8x16.fromArray(listOf(1, 255, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllLe(a, b))
    check(true == r)
}

public fun testVecAllLeI16True() {
    val a: STL = i16x8.fromArray(listOf(1, -5, 2, 0, 0, 0, 0, 0)).into()
    val b: STL = i16x8.fromArray(listOf(1, -1, 42, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllLe(a, b))
    check(true == r)
}

public fun testVecAllLeU16True() {
    val a: STL = u16x8.fromArray(listOf(2, 255, 1, 0, 0, 0, 0, 0)).into()
    val b: STL = u16x8.fromArray(listOf(42, 255, 1, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllLe(a, b))
    check(true == r)
}

public fun testVecAllLeI32True() {
    val a: STL = i32x4.fromArray(listOf(0, -1, 0, 1)).into()
    val b: STL = i32x4.fromArray(listOf(1, -1, 0, 1)).into()
    val r = Boolean.from(vecAllLe(a, b))
    check(true == r)
}

public fun testVecAllLeU32True() {
    val a: STL = u32x4.fromArray(listOf(1, 254, 0, 0)).into()
    val b: STL = u32x4.fromArray(listOf(1, 255, 0, 1)).into()
    val r = Boolean.from(vecAllLe(a, b))
    check(true == r)
}

public fun testVecAnyLeI8False() {
    val a: STL = i8x16.fromArray(listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).into()
    val b: STL = i8x16.fromArray(listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyLe(a, b))
    check(false == r)
}

public fun testVecAnyLeU8False() {
    val a: STL = u8x16.fromArray(listOf(42, 255, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).into()
    val b: STL = u8x16.fromArray(listOf(1, 254, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyLe(a, b))
    check(false == r)
}

public fun testVecAnyLeI16False() {
    val a: STL = i16x8.fromArray(listOf(2, 0, -1, 1, 1, 1, 1, 1)).into()
    val b: STL = i16x8.fromArray(listOf(1, -1, -2, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyLe(a, b))
    check(false == r)
}

public fun testVecAnyLeU16False() {
    val a: STL = u16x8.fromArray(listOf(2, 42, 255, 1, 1, 1, 1, 1)).into()
    val b: STL = u16x8.fromArray(listOf(1, 2, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyLe(a, b))
    check(false == r)
}

public fun testVecAnyLeI32False() {
    val a: STL = i32x4.fromArray(listOf(2, 0, 1, 1)).into()
    val b: STL = i32x4.fromArray(listOf(1, -1, 0, 0)).into()
    val r = Boolean.from(vecAnyLe(a, b))
    check(false == r)
}

public fun testVecAnyLeU32False() {
    val a: STL = u32x4.fromArray(listOf(4, 255, 4, 1)).into()
    val b: STL = u32x4.fromArray(listOf(1, 2, 1, 0)).into()
    val r = Boolean.from(vecAnyLe(a, b))
    check(false == r)
}

public fun testVecAnyLeI8True() {
    val a: STL = i8x16.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i8x16.fromArray(listOf(1, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyLe(a, b))
    check(true == r)
}

public fun testVecAnyLeU8True() {
    val a: STL = u8x16.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u8x16.fromArray(listOf(0, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyLe(a, b))
    check(true == r)
}

public fun testVecAnyLeI16True() {
    val a: STL = i16x8.fromArray(listOf(1, -1, 1, 0, 0, 0, 0, 0)).into()
    val b: STL = i16x8.fromArray(listOf(0, -1, 1, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyLe(a, b))
    check(true == r)
}

public fun testVecAnyLeU16True() {
    val a: STL = u16x8.fromArray(listOf(1, 255, 1, 0, 0, 0, 0, 0)).into()
    val b: STL = u16x8.fromArray(listOf(0, 255, 1, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyLe(a, b))
    check(true == r)
}

public fun testVecAnyLeI32True() {
    val a: STL = i32x4.fromArray(listOf(1, -1, 0, 1)).into()
    val b: STL = i32x4.fromArray(listOf(0, -1, 0, 1)).into()
    val r = Boolean.from(vecAnyLe(a, b))
    check(true == r)
}

public fun testVecAnyLeU32True() {
    val a: STL = u32x4.fromArray(listOf(1, 255, 0, 1)).into()
    val b: STL = u32x4.fromArray(listOf(0, 255, 0, 1)).into()
    val r = Boolean.from(vecAnyLe(a, b))
    check(true == r)
}

public fun testVecAllLtI8False() {
    val a: STL = i8x16.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i8x16.fromArray(listOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllLt(a, b))
    check(false == r)
}

public fun testVecAllLtU8False() {
    val a: STL = u8x16.fromArray(listOf(0, 0, 255, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u8x16.fromArray(listOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllLt(a, b))
    check(false == r)
}

public fun testVecAllLtI16False() {
    val a: STL = i16x8.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0)).into()
    val b: STL = i16x8.fromArray(listOf(1, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllLt(a, b))
    check(false == r)
}

public fun testVecAllLtU16False() {
    val a: STL = u16x8.fromArray(listOf(0, 0, 255, 1, 0, 0, 0, 0)).into()
    val b: STL = u16x8.fromArray(listOf(1, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllLt(a, b))
    check(false == r)
}

public fun testVecAllLtI32False() {
    val a: STL = i32x4.fromArray(listOf(0, -1, 0, 1)).into()
    val b: STL = i32x4.fromArray(listOf(1, -1, 0, 0)).into()
    val r = Boolean.from(vecAllLt(a, b))
    check(false == r)
}

public fun testVecAllLtU32False() {
    val a: STL = u32x4.fromArray(listOf(0, 255, 1, 1)).into()
    val b: STL = u32x4.fromArray(listOf(1, 255, 0, 0)).into()
    val r = Boolean.from(vecAllLt(a, b))
    check(false == r)
}

public fun testVecAllLtI8True() {
    val a: STL = i8x16.fromArray(listOf(0, 0, -2, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1)).into()
    val b: STL = i8x16.fromArray(listOf(2, 1, -1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllLt(a, b))
    check(true == r)
}

public fun testVecAllLtU8True() {
    val a: STL = u8x16.fromArray(listOf(0, 254, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u8x16.fromArray(listOf(1, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).into()
    val r = Boolean.from(vecAllLt(a, b))
    check(true == r)
}

public fun testVecAllLtI16True() {
    val a: STL = i16x8.fromArray(listOf(0, -5, 2, 0, 0, 0, 0, 0)).into()
    val b: STL = i16x8.fromArray(listOf(1, -1, 42, 1, 1, 1, 1, 1)).into()
    val r = Boolean.from(vecAllLt(a, b))
    check(true == r)
}

public fun testVecAllLtU16True() {
    val a: STL = u16x8.fromArray(listOf(2, 254, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u16x8.fromArray(listOf(42, 255, 1, 1, 1, 1, 1, 1)).into()
    val r = Boolean.from(vecAllLt(a, b))
    check(true == r)
}

public fun testVecAllLtI32True() {
    val a: STL = i32x4.fromArray(listOf(0, -2, 0, 0)).into()
    val b: STL = i32x4.fromArray(listOf(1, -1, 1, 1)).into()
    val r = Boolean.from(vecAllLt(a, b))
    check(true == r)
}

public fun testVecAllLtU32True() {
    val a: STL = u32x4.fromArray(listOf(0, 254, 0, 0)).into()
    val b: STL = u32x4.fromArray(listOf(1, 255, 1, 1)).into()
    val r = Boolean.from(vecAllLt(a, b))
    check(true == r)
}

public fun testVecAnyLtI8False() {
    val a: STL = i8x16.fromArray(listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).into()
    val b: STL = i8x16.fromArray(listOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyLt(a, b))
    check(false == r)
}

public fun testVecAnyLtU8False() {
    val a: STL = u8x16.fromArray(listOf(42, 255, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).into()
    val b: STL = u8x16.fromArray(listOf(1, 254, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyLt(a, b))
    check(false == r)
}

public fun testVecAnyLtI16False() {
    val a: STL = i16x8.fromArray(listOf(2, 0, -1, 1, 1, 1, 1, 1)).into()
    val b: STL = i16x8.fromArray(listOf(1, -1, -2, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyLt(a, b))
    check(false == r)
}

public fun testVecAnyLtU16False() {
    val a: STL = u16x8.fromArray(listOf(2, 42, 255, 1, 1, 1, 1, 1)).into()
    val b: STL = u16x8.fromArray(listOf(1, 2, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyLt(a, b))
    check(false == r)
}

public fun testVecAnyLtI32False() {
    val a: STL = i32x4.fromArray(listOf(2, 0, 1, 1)).into()
    val b: STL = i32x4.fromArray(listOf(1, -1, 0, 0)).into()
    val r = Boolean.from(vecAnyLt(a, b))
    check(false == r)
}

public fun testVecAnyLtU32False() {
    val a: STL = u32x4.fromArray(listOf(4, 255, 4, 1)).into()
    val b: STL = u32x4.fromArray(listOf(1, 2, 1, 0)).into()
    val r = Boolean.from(vecAnyLt(a, b))
    check(false == r)
}

public fun testVecAnyLtI8True() {
    val a: STL = i8x16.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i8x16.fromArray(listOf(1, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyLt(a, b))
    check(true == r)
}

public fun testVecAnyLtU8True() {
    val a: STL = u8x16.fromArray(listOf(0, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u8x16.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyLt(a, b))
    check(true == r)
}

public fun testVecAnyLtI16True() {
    val a: STL = i16x8.fromArray(listOf(0, -1, 1, 0, 0, 0, 0, 0)).into()
    val b: STL = i16x8.fromArray(listOf(1, -1, 1, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyLt(a, b))
    check(true == r)
}

public fun testVecAnyLtU16True() {
    val a: STL = u16x8.fromArray(listOf(0, 255, 1, 0, 0, 0, 0, 0)).into()
    val b: STL = u16x8.fromArray(listOf(1, 255, 1, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyLt(a, b))
    check(true == r)
}

public fun testVecAnyLtI32True() {
    val a: STL = i32x4.fromArray(listOf(0, -1, 0, 1)).into()
    val b: STL = i32x4.fromArray(listOf(1, -1, 0, 1)).into()
    val r = Boolean.from(vecAnyLt(a, b))
    check(true == r)
}

public fun testVecAnyLtU32True() {
    val a: STL = u32x4.fromArray(listOf(0, 255, 0, 1)).into()
    val b: STL = u32x4.fromArray(listOf(1, 255, 0, 1)).into()
    val r = Boolean.from(vecAnyLt(a, b))
    check(true == r)
}

public fun testVecAllNeI8False() {
    val a: STL = i8x16.fromArray(listOf(1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i8x16.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllNe(a, b))
    check(false == r)
}

public fun testVecAllNeU8False() {
    val a: STL = u8x16.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u8x16.fromArray(listOf(0, 0, 255, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllNe(a, b))
    check(false == r)
}

public fun testVecAllNeI16False() {
    val a: STL = i16x8.fromArray(listOf(1, -1, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i16x8.fromArray(listOf(0, -1, 1, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllNe(a, b))
    check(false == r)
}

public fun testVecAllNeU16False() {
    val a: STL = u16x8.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u16x8.fromArray(listOf(0, 255, 0, 1, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllNe(a, b))
    check(false == r)
}

public fun testVecAllNeI32False() {
    val a: STL = i32x4.fromArray(listOf(1, -1, 0, 0)).into()
    val b: STL = i32x4.fromArray(listOf(0, -1, 0, 1)).into()
    val r = Boolean.from(vecAllNe(a, b))
    check(false == r)
}

public fun testVecAllNeU32False() {
    val a: STL = u32x4.fromArray(listOf(1, 255, 0, 0)).into()
    val b: STL = u32x4.fromArray(listOf(0, 255, 0, 1)).into()
    val r = Boolean.from(vecAllNe(a, b))
    check(false == r)
}

public fun testVecAllNeI8True() {
    val a: STL = i8x16.fromArray(listOf(0, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).into()
    val b: STL = i8x16.fromArray(listOf(1, 0, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllNe(a, b))
    check(true == r)
}

public fun testVecAllNeU8True() {
    val a: STL = u8x16.fromArray(listOf(0, 254, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).into()
    val b: STL = u8x16.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllNe(a, b))
    check(true == r)
}

public fun testVecAllNeI16True() {
    val a: STL = i16x8.fromArray(listOf(2, -2, 0, 1, 1, 1, 1, 1)).into()
    val b: STL = i16x8.fromArray(listOf(1, -1, 1, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAllNe(a, b))
    check(true == r)
}

public fun testVecAllNeU16True() {
    val a: STL = u16x8.fromArray(listOf(0, 254, 1, 1, 0, 0, 1, 0)).into()
    val b: STL = u16x8.fromArray(listOf(1, 255, 0, 0, 1, 1, 0, 1)).into()
    val r = Boolean.from(vecAllNe(a, b))
    check(true == r)
}

public fun testVecAllNeI32True() {
    val a: STL = i32x4.fromArray(listOf(0, -2, 0, 0)).into()
    val b: STL = i32x4.fromArray(listOf(1, -1, 1, 1)).into()
    val r = Boolean.from(vecAllNe(a, b))
    check(true == r)
}

public fun testVecAllNeU32True() {
    val a: STL = u32x4.fromArray(listOf(1, 255, 0, 0)).into()
    val b: STL = u32x4.fromArray(listOf(0, 254, 1, 1)).into()
    val r = Boolean.from(vecAllNe(a, b))
    check(true == r)
}

public fun testVecAnyNeI8False() {
    val a: STL = i8x16.fromArray(listOf(1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i8x16.fromArray(listOf(1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyNe(a, b))
    check(false == r)
}

public fun testVecAnyNeU8False() {
    val a: STL = u8x16.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u8x16.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyNe(a, b))
    check(false == r)
}

public fun testVecAnyNeI16False() {
    val a: STL = i16x8.fromArray(listOf(1, -1, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i16x8.fromArray(listOf(1, -1, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyNe(a, b))
    check(false == r)
}

public fun testVecAnyNeU16False() {
    val a: STL = u16x8.fromArray(listOf(1, 255, 1, 1, 1, 1, 1, 0)).into()
    val b: STL = u16x8.fromArray(listOf(1, 255, 1, 1, 1, 1, 1, 0)).into()
    val r = Boolean.from(vecAnyNe(a, b))
    check(false == r)
}

public fun testVecAnyNeI32False() {
    val a: STL = i32x4.fromArray(listOf(0, -1, 1, 1)).into()
    val b: STL = i32x4.fromArray(listOf(0, -1, 1, 1)).into()
    val r = Boolean.from(vecAnyNe(a, b))
    check(false == r)
}

public fun testVecAnyNeU32False() {
    val a: STL = u32x4.fromArray(listOf(1, 2, 1, 255)).into()
    val b: STL = u32x4.fromArray(listOf(1, 2, 1, 255)).into()
    val r = Boolean.from(vecAnyNe(a, b))
    check(false == r)
}

public fun testVecAnyNeI8True() {
    val a: STL = i8x16.fromArray(listOf(1, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i8x16.fromArray(listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyNe(a, b))
    check(true == r)
}

public fun testVecAnyNeU8True() {
    val a: STL = u8x16.fromArray(listOf(0, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = u8x16.fromArray(listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyNe(a, b))
    check(true == r)
}

public fun testVecAnyNeI16True() {
    val a: STL = i16x8.fromArray(listOf(0, -1, 1, 0, 0, 0, 0, 0)).into()
    val b: STL = i16x8.fromArray(listOf(1, -1, 1, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyNe(a, b))
    check(true == r)
}

public fun testVecAnyNeU16True() {
    val a: STL = u16x8.fromArray(listOf(0, 255, 1, 0, 0, 0, 0, 0)).into()
    val b: STL = u16x8.fromArray(listOf(1, 255, 1, 0, 0, 0, 0, 0)).into()
    val r = Boolean.from(vecAnyNe(a, b))
    check(true == r)
}

public fun testVecAnyNeI32True() {
    val a: STL = i32x4.fromArray(listOf(0, -1, 0, 1)).into()
    val b: STL = i32x4.fromArray(listOf(1, -1, 0, 1)).into()
    val r = Boolean.from(vecAnyNe(a, b))
    check(true == r)
}

public fun testVecAnyNeU32True() {
    val a: STL = u32x4.fromArray(listOf(0, 255, 0, 1)).into()
    val b: STL = u32x4.fromArray(listOf(1, 255, 0, 1)).into()
    val r = Boolean.from(vecAnyNe(a, b))
    check(true == r)
}

public fun testVecCmpb() {
    val a = VectorFloat.from(f32x4.new(0.1, 0.5, 0.6, 0.9))
    val b = VectorFloat.from(f32x4.new(-0.1, 0.5, -0.6, 0.9))
    val d = i32x4.new(-0x80000000, 0, -0x80000000, 0)
    check(d == i32x4.from(vecCmpb(a, b)))
}

public fun testVecCeil() {
    val a = VectorFloat.from(f32x4.new(0.1, 0.5, 0.6, 0.9))
    val d = f32x4.new(1.0, 1.0, 1.0, 1.0)
    check(d == f32x4.from(vecCeil(a)))
}

public fun testVecAndc() {
    val a: STL = i32x4.fromArray(listOf(0xCC, 0xCC, 0xCC, 0xCC)).into()
    val b: STL = i32x4.fromArray(listOf(0x33, 0xF3, 0xC, 0x80)).into()
    val d = i32x4.fromArray(listOf(0xCC, 0xC, 0xC0, 0x4C))
    val r = i32x4.from(vecAndc(a, b))
    check(d == r)
}

public fun testVecAnd() {
    val a: STL = i32x4.fromArray(listOf(0xCC, 0xCC, 0xCC, 0xCC)).into()
    val b: STL = i32x4.fromArray(listOf(0x33, 0xF3, 0xC, 0x0)).into()
    val d = i32x4.fromArray(listOf(0x0, 0xC0, 0xC, 0x0))
    val r = i32x4.from(vecAnd(a, b))
    check(d == r)
}

public fun testVecAvgI32x4() {
    val a: STL = i32x4.fromArray(intArrayOf(Int.MIN_VALUE, Int.MAX_VALUE, 1, -1)).into()
    val b: STL = i32x4.fromArray(listOf(-1, 1, 1, -1)).into()
    val d = i32x4.fromArray(listOf(-1073741824, 1073741824, 1, -1))
    val r = i32x4.from(vecAvg(a, b))
    check(d == r)
}

public fun testVecAvgU32x4() {
    val a: STL = u32x4.fromArray(uintArrayOf(UInt.MAX_VALUE, 0u, 1u, 2u)).into()
    val b: STL = u32x4.fromArray(listOf(2, 1, 0, 0)).into()
    val d = u32x4.fromArray(listOf(2147483649, 1, 1, 1))
    val r = u32x4.from(vecAvg(a, b))
    check(d == r)
}

public fun testVecAvgI16x8() {
    val a: STL = i16x8.fromArray(shortArrayOf(Short.MIN_VALUE, Short.MAX_VALUE, 1, (-1).toShort(), 0, 0, 0, 0)).into()
    val b: STL = i16x8.fromArray(listOf(-1, 1, 1, -1, 0, 0, 0, 0)).into()
    val d = i16x8.fromArray(listOf(-16384, 16384, 1, -1, 0, 0, 0, 0))
    val r = i16x8.from(vecAvg(a, b))
    check(d == r)
}

public fun testVecAvgU16x8() {
    val a: STL = u16x8.fromArray(ushortArrayOf(UShort.MAX_VALUE, 0u, 1u, 2u, 0u, 0u, 0u, 0u)).into()
    val b: STL = u16x8.fromArray(listOf(2, 1, 0, 0, 0, 0, 0, 0)).into()
    val d = u16x8.fromArray(listOf(32769, 1, 1, 1, 0, 0, 0, 0))
    val r = u16x8.from(vecAvg(a, b))
    check(d == r)
}

public fun testVecAvgI8x16() {
    val a: STL = i8x16.fromArray(byteArrayOf(Byte.MIN_VALUE, Byte.MAX_VALUE, 1, (-1).toByte(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i8x16.fromArray(listOf(-1, 1, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val d = i8x16.fromArray(listOf(-64, 64, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))
    val r = i8x16.from(vecAvg(a, b))
    check(d == r)
}

public fun testVecAvgU8x16() {
    val a: STL = u8x16.fromArray(ubyteArrayOf(UByte.MAX_VALUE, 0u, 1u, 2u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)).into()
    val b: STL = u8x16.fromArray(listOf(2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val d = u8x16.fromArray(listOf(129, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))
    val r = u8x16.from(vecAvg(a, b))
    check(d == r)
}

public fun testVecAddsI32x4() {
    val a: STL = i32x4.fromArray(intArrayOf(Int.MIN_VALUE, Int.MAX_VALUE, 1, -1)).into()
    val b: STL = i32x4.fromArray(listOf(-1, 1, 1, -1)).into()
    val d = i32x4.fromArray(intArrayOf(Int.MIN_VALUE, Int.MAX_VALUE, 2, -2))
    val r = i32x4.from(vecAdds(a, b))
    check(d == r)
}

public fun testVecAddsU32x4() {
    val a: STL = u32x4.fromArray(uintArrayOf(UInt.MAX_VALUE, 0u, 1u, 2u)).into()
    val b: STL = u32x4.fromArray(listOf(2, 1, 0, 0)).into()
    val d = u32x4.fromArray(uintArrayOf(UInt.MAX_VALUE, 1u, 1u, 2u))
    val r = u32x4.from(vecAdds(a, b))
    check(d == r)
}

public fun testVecAddsI16x8() {
    val a: STL = i16x8.fromArray(shortArrayOf(Short.MIN_VALUE, Short.MAX_VALUE, 1, (-1).toShort(), 0, 0, 0, 0)).into()
    val b: STL = i16x8.fromArray(listOf(-1, 1, 1, -1, 0, 0, 0, 0)).into()
    val d = i16x8.fromArray(shortArrayOf(Short.MIN_VALUE, Short.MAX_VALUE, 2, (-2).toShort(), 0, 0, 0, 0))
    val r = i16x8.from(vecAdds(a, b))
    check(d == r)
}

public fun testVecAddsU16x8() {
    val a: STL = u16x8.fromArray(ushortArrayOf(UShort.MAX_VALUE, 0u, 1u, 2u, 0u, 0u, 0u, 0u)).into()
    val b: STL = u16x8.fromArray(listOf(2, 1, 0, 0, 0, 0, 0, 0)).into()
    val d = u16x8.fromArray(ushortArrayOf(UShort.MAX_VALUE, 1u, 1u, 2u, 0u, 0u, 0u, 0u))
    val r = u16x8.from(vecAdds(a, b))
    check(d == r)
}

public fun testVecAddsI8x16() {
    val a: STL = i8x16.fromArray(byteArrayOf(Byte.MIN_VALUE, Byte.MAX_VALUE, 1, (-1).toByte(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val b: STL = i8x16.fromArray(listOf(-1, 1, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val d = i8x16.fromArray(byteArrayOf(Byte.MIN_VALUE, Byte.MAX_VALUE, 2, (-2).toByte(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))
    val r = i8x16.from(vecAdds(a, b))
    check(d == r)
}

public fun testVecAddsU8x16() {
    val a: STL = u8x16.fromArray(ubyteArrayOf(UByte.MAX_VALUE, 0u, 1u, 2u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)).into()
    val b: STL = u8x16.fromArray(listOf(2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).into()
    val d = u8x16.fromArray(ubyteArrayOf(UByte.MAX_VALUE, 1u, 1u, 2u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u))
    val r = u8x16.from(vecAdds(a, b))
    check(d == r)
}

public fun testVecAddc() {
    val a: STL = u32x4.fromArray(uintArrayOf(UInt.MAX_VALUE, 0u, 0u, 0u)).into()
    val b: STL = u32x4.fromArray(listOf(1, 1, 1, 1)).into()
    val d = u32x4.fromArray(listOf(1, 0, 0, 0))
    val r = u32x4.from(vecAddc(a, b))
    check(d == r)
}

public fun testVecAbsI8() {
    val a = vecSplats(-42)
    val a1: STL = vecAbs(a)
    val d = i8x16.splat(42)
    check(d == i8x16.from(a1))
}

public fun testVecAbsI16() {
    val a = vecSplats(-42)
    val a1: STL = vecAbs(a)
    val d = i16x8.splat(42)
    check(d == i16x8.from(a1))
}

public fun testVecAbsI32() {
    val a = vecSplats(-42)
    val a1: STL = vecAbs(a)
    val d = i32x4.splat(42)
    check(d == i32x4.from(a1))
}

public fun testVecAbsF32() {
    val a = vecSplats(-42.0f)
    val a1: STL = vecAbs(a)
    val d = f32x4.splat(42.0f)
    check(d == f32x4.from(a1))
}

public fun testVecAbssI8() {
    val a = vecSplats(-127)
    val a1: STL = vecAbss(a)
    val d = i8x16.splat(127)
    check(d == i8x16.from(a1))
}

public fun testVecAbssI16() {
    val a = vecSplats(-42)
    val a1: STL = vecAbss(a)
    val d = i16x8.splat(42)
    check(d == i16x8.from(a1))
}

public fun testVecAbssI32() {
    val a = vecSplats(-42)
    val a1: STL = vecAbss(a)
    val d = i32x4.splat(42)
    check(d == i32x4.from(a1))
}

public fun testVecSplatsU8() {
    val a: STL = vecSplats(42u.toUByte())
    val d = u8x16.splat(42u.toUByte())
    check(d == u8x16.from(a))
}

public fun testVecSplatsU16() {
    val a: STL = vecSplats(42u.toUShort())
    val d = u16x8.splat(42u.toUShort())
    check(d == u16x8.from(a))
}

public fun testVecSplatsU32() {
    val a: STL = vecSplats(42u)
    val d = u32x4.splat(42u)
    check(d == u32x4.from(a))
}

public fun testVecSplatsI8() {
    val a: STL = vecSplats(42)
    val d = i8x16.splat(42)
    check(d == i8x16.from(a))
}

public fun testVecSplatsI16() {
    val a: STL = vecSplats(42)
    val d = i16x8.splat(42)
    check(d == i16x8.from(a))
}

public fun testVecSplatsI32() {
    val a: STL = vecSplats(42)
    val d = i32x4.splat(42)
    check(d == i32x4.from(a))
}

public fun testVecSplatsF32() {
    val a: STL = vecSplats(42.0f)
    val d = f32x4.splat(42.0f)
    check(d == f32x4.from(a))
}

public fun testVecSplatU8() {
    val a = vecSplatU8(-1)
    val d = u8x16.splat(UByte.MAX_VALUE)
    check(d == u8x16.from(a))
}

public fun testVecSplatU16() {
    val a = vecSplatU16(-1)
    val d = u16x8.splat(UShort.MAX_VALUE)
    check(d == u16x8.from(a))
}

public fun testVecSplatU32() {
    val a = vecSplatU32(-1)
    val d = u32x4.splat(UInt.MAX_VALUE)
    check(d == u32x4.from(a))
}

public fun testVecSplatS8() {
    val a = vecSplatS8(-1)
    val d = i8x16.splat(-1)
    check(d == i8x16.from(a))
}

public fun testVecSplatS16() {
    val a = vecSplatS16(-1)
    val d = i16x8.splat(-1)
    check(d == i16x8.from(a))
}

public fun testVecSplatS32() {
    val a = vecSplatS32(-1)
    val d = i32x4.splat(-1)
    check(d == i32x4.from(a))
}

public fun testVecSubF32x4() {
    val a: STL = f32x4.fromArray(listOf(-1.0, 0.0, 1.0, 2.0)).into()
    val b: STL = f32x4.fromArray(listOf(2.0, 1.0, -1.0, -2.0)).into()
    val d = f32x4.fromArray(listOf(-3.0, -1.0, 2.0, 4.0))
    val r = f32x4.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubI32x4() {
    val a: STL = i32x4.fromArray(listOf(-1, 0, 1, 2)).into()
    val b: STL = i32x4.fromArray(listOf(2, 1, -1, -2)).into()
    val d = i32x4.fromArray(listOf(-3, -1, 2, 4))
    val r = i32x4.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubU32x4() {
    val a: STL = u32x4.fromArray(listOf(0, 0, 1, 2)).into()
    val b: STL = u32x4.fromArray(listOf(2, 1, 0, 0)).into()
    val d = u32x4.fromArray(listOf(4294967294, 4294967295, 1, 2))
    val r = u32x4.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubI16x8() {
    val a: STL = i16x8.fromArray(listOf(-1, 0, 1, 2, -1, 0, 1, 2)).into()
    val b: STL = i16x8.fromArray(listOf(2, 1, -1, -2, 2, 1, -1, -2)).into()
    val d = i16x8.fromArray(listOf(-3, -1, 2, 4, -3, -1, 2, 4))
    val r = i16x8.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubU16x8() {
    val a: STL = u16x8.fromArray(listOf(0, 0, 1, 2, 0, 0, 1, 2)).into()
    val b: STL = u16x8.fromArray(listOf(2, 1, 0, 0, 2, 1, 0, 0)).into()
    val d = u16x8.fromArray(listOf(65534, 65535, 1, 2, 65534, 65535, 1, 2))
    val r = u16x8.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubI8x16() {
    val a: STL = i8x16.fromArray(listOf(-1, 0, 1, 2, -1, 0, 1, 2, -1, 0, 1, 2, -1, 0, 1, 2)).into()
    val b: STL = i8x16.fromArray(listOf(2, 1, -1, -2, 2, 1, -1, -2, 2, 1, -1, -2, 2, 1, -1, -2)).into()
    val d = i8x16.fromArray(listOf(-3, -1, 2, 4, -3, -1, 2, 4, -3, -1, 2, 4, -3, -1, 2, 4))
    val r = i8x16.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubU8x16() {
    val a: STL = u8x16.fromArray(listOf(0, 0, 1, 2, 0, 0, 1, 2, 0, 0, 1, 2, 0, 0, 1, 2)).into()
    val b: STL = u8x16.fromArray(listOf(2, 1, 0, 0, 2, 1, 0, 0, 2, 1, 0, 0, 2, 1, 0, 0)).into()
    val d = u8x16.fromArray(listOf(254, 255, 1, 2, 254, 255, 1, 2, 254, 255, 1, 2, 254, 255, 1, 2))
    val r = u8x16.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubsI32x4() {
    val a: STL = i32x4.fromArray(listOf(-1, 0, 1, 2)).into()
    val b: STL = i32x4.fromArray(listOf(2, 1, -1, -2)).into()
    val d = i32x4.fromArray(listOf(-3, -1, 2, 4))
    val r = i32x4.from(vecSubs(a, b))
    check(d == r)
}

public fun testVecSubsU32x4() {
    val a: STL = u32x4.fromArray(listOf(0, 0, 1, 2)).into()
    val b: STL = u32x4.fromArray(listOf(2, 1, 0, 0)).into()
    val d = u32x4.fromArray(listOf(0, 0, 1, 2))
    val r = u32x4.from(vecSubs(a, b))
    check(d == r)
}

public fun testVecSubsI16x8() {
    val a: STL = i16x8.fromArray(listOf(-1, 0, 1, 2, -1, 0, 1, 2)).into()
    val b: STL = i16x8.fromArray(listOf(2, 1, -1, -2, 2, 1, -1, -2)).into()
    val d = i16x8.fromArray(listOf(-3, -1, 2, 4, -3, -1, 2, 4))
    val r = i16x8.from(vecSubs(a, b))
    check(d == r)
}

public fun testVecSubsU16x8() {
    val a: STL = u16x8.fromArray(listOf(0, 0, 1, 2, 0, 0, 1, 2)).into()
    val b: STL = u16x8.fromArray(listOf(2, 1, 0, 0, 2, 1, 0, 0)).into()
    val d = u16x8.fromArray(listOf(0, 0, 1, 2, 0, 0, 1, 2))
    val r = u16x8.from(vecSubs(a, b))
    check(d == r)
}

public fun testVecSubsI8x16() {
    val a: STL = i8x16.fromArray(listOf(-1, 0, 1, 2, -1, 0, 1, 2, -1, 0, 1, 2, -1, 0, 1, 2)).into()
    val b: STL = i8x16.fromArray(listOf(2, 1, -1, -2, 2, 1, -1, -2, 2, 1, -1, -2, 2, 1, -1, -2)).into()
    val d = i8x16.fromArray(listOf(-3, -1, 2, 4, -3, -1, 2, 4, -3, -1, 2, 4, -3, -1, 2, 4))
    val r = i8x16.from(vecSubs(a, b))
    check(d == r)
}

public fun testVecSubsU8x16() {
    val a: STL = u8x16.fromArray(listOf(0, 0, 1, 2, 0, 0, 1, 2, 0, 0, 1, 2, 0, 0, 1, 2)).into()
    val b: STL = u8x16.fromArray(listOf(2, 1, 0, 0, 2, 1, 0, 0, 2, 1, 0, 0, 2, 1, 0, 0)).into()
    val d = u8x16.fromArray(listOf(0, 0, 1, 2, 0, 0, 1, 2, 0, 0, 1, 2, 0, 0, 1, 2))
    val r = u8x16.from(vecSubs(a, b))
    check(d == r)
}

public fun testVecMinI32x4() {
    val a: STL = i32x4.new(-1, 0, 1, 2).into()
    val b: STL = i32x4.new(2, 1, -1, -2).into()
    val d = i32x4.new(-1, 0, -1, -2)
    val r = i32x4.from(vecMin(a, b))
    check(d == r)
}

public fun testVecMinU32x4() {
    val a: STL = u32x4.new(0, 0, 1, 2).into()
    val b: STL = u32x4.new(2, 1, 0, 0).into()
    val d = u32x4.new(0, 0, 0, 0)
    val r = u32x4.from(vecMin(a, b))
    check(d == r)
}

public fun testVecMinI16x8() {
    val a: STL = i16x8.new(-1, 0, 1, 2, -1, 0, 1, 2).into()
    val b: STL = i16x8.new(2, 1, -1, -2, 2, 1, -1, -2).into()
    val d = i16x8.new(-1, 0, -1, -2, -1, 0, -1, -2)
    val r = i16x8.from(vecMin(a, b))
    check(d == r)
}

public fun testVecMinU16x8() {
    val a: STL = u16x8.new(0, 0, 1, 2, 0, 0, 1, 2).into()
    val b: STL = u16x8.new(2, 1, 0, 0, 2, 1, 0, 0).into()
    val d = u16x8.new(0, 0, 0, 0, 0, 0, 0, 0)
    val r = u16x8.from(vecMin(a, b))
    check(d == r)
}

public fun testVecMinI8x16() {
    val a: STL = i8x16.new(-1, 0, 1, 2, -1, 0, 1, 2, -1, 0, 1, 2, -1, 0, 1, 2).into()
    val b: STL = i8x16.new(2, 1, -1, -2, 2, 1, -1, -2, 2, 1, -1, -2, 2, 1, -1, -2).into()
    val d = i8x16.new(-1, 0, -1, -2, -1, 0, -1, -2, -1, 0, -1, -2, -1, 0, -1, -2)
    val r = i8x16.from(vecMin(a, b))
    check(d == r)
}

public fun testVecMinU8x16() {
    val a: STL = u8x16.new(0, 0, 1, 2, 0, 0, 1, 2, 0, 0, 1, 2, 0, 0, 1, 2).into()
    val b: STL = u8x16.new(2, 1, 0, 0, 2, 1, 0, 0, 2, 1, 0, 0, 2, 1, 0, 0).into()
    val d = u8x16.new(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val r = u8x16.from(vecMin(a, b))
    check(d == r)
}

public fun testVecMaxI32x4() {
    val a: STL = i32x4.new(-1, 0, 1, 2).into()
    val b: STL = i32x4.new(2, 1, -1, -2).into()
    val d = i32x4.new(2, 1, 1, 2)
    val r = i32x4.from(vecMax(a, b))
    check(d == r)
}

public fun testVecMaxU32x4() {
    val a: STL = u32x4.new(0, 0, 1, 2).into()
    val b: STL = u32x4.new(2, 1, 0, 0).into()
    val d = u32x4.new(2, 1, 1, 2)
    val r = u32x4.from(vecMax(a, b))
    check(d == r)
}

public fun testVecMaxI16x8() {
    val a: STL = i16x8.new(-1, 0, 1, 2, -1, 0, 1, 2).into()
    val b: STL = i16x8.new(2, 1, -1, -2, 2, 1, -1, -2).into()
    val d = i16x8.new(2, 1, 1, 2, 2, 1, 1, 2)
    val r = i16x8.from(vecMax(a, b))
    check(d == r)
}

public fun testVecMaxU16x8() {
    val a: STL = u16x8.new(0, 0, 1, 2, 0, 0, 1, 2).into()
    val b: STL = u16x8.new(2, 1, 0, 0, 2, 1, 0, 0).into()
    val d = u16x8.new(2, 1, 1, 2, 2, 1, 1, 2)
    val r = u16x8.from(vecMax(a, b))
    check(d == r)
}

public fun testVecMaxI8x16() {
    val a: STL = i8x16.new(-1, 0, 1, 2, -1, 0, 1, 2, -1, 0, 1, 2, -1, 0, 1, 2).into()
    val b: STL = i8x16.new(2, 1, -1, -2, 2, 1, -1, -2, 2, 1, -1, -2, 2, 1, -1, -2).into()
    val d = i8x16.new(2, 1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 2)
    val r = i8x16.from(vecMax(a, b))
    check(d == r)
}

public fun testVecMaxU8x16() {
    val a: STL = u8x16.new(0, 0, 1, 2, 0, 0, 1, 2, 0, 0, 1, 2, 0, 0, 1, 2).into()
    val b: STL = u8x16.new(2, 1, 0, 0, 2, 1, 0, 0, 2, 1, 0, 0, 2, 1, 0, 0).into()
    val d = u8x16.new(2, 1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 2)
    val r = u8x16.from(vecMax(a, b))
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

public fun testVecMadds() {
    val a = VectorSignedShort.from(i16x8.new(0 * 256, 1 * 256, 2 * 256, 3 * 256, 4 * 256, 5 * 256, 6 * 256, 7 * 256))
    val b = VectorSignedShort.from(i16x8.new(256, 256, 256, 256, 256, 256, 256, 256))
    val c = VectorSignedShort.from(i16x8.new(0, 1, 2, 3, 4, 5, 6, 7))
    val d = i16x8.new(0, 3, 6, 9, 12, 15, 18, 21)
    check(d == i16x8.from(vecMadds(a, b, c)))
}

public fun testVecMaddFloat() {
    val a = VectorFloat.from(f32x4.new(0.1, 0.2, 0.3, 0.4))
    val b = VectorFloat.from(f32x4.new(0.1, 0.2, 0.3, 0.4))
    val c = VectorFloat.from(f32x4.new(0.1, 0.2, 0.3, 0.4))
    val d = f32x4.new(0.1 * 0.1 + 0.1, 0.2 * 0.2 + 0.2, 0.3 * 0.3 + 0.3, 0.4 * 0.4 + 0.4)
    check(d == f32x4.from(vecMadd(a, b, c)))
}

public fun testVecNmsubFloat() {
    val a = VectorFloat.from(f32x4.new(0.1, 0.2, 0.3, 0.4))
    val b = VectorFloat.from(f32x4.new(0.1, 0.2, 0.3, 0.4))
    val c = VectorFloat.from(f32x4.new(0.1, 0.2, 0.3, 0.4))
    val d = f32x4.new(-(0.1 * 0.1 - 0.1), -(0.2 * 0.2 - 0.2), -(0.3 * 0.3 - 0.3), -(0.4 * 0.4 - 0.4))
    check(d == f32x4.from(vecNmsub(a, b, c)))
}

public fun testVecMradds() {
    val a = VectorSignedShort.from(i16x8.new(0 * 256, 1 * 256, 2 * 256, 3 * 256, 4 * 256, 5 * 256, 6 * 256, 7 * 256))
    val b = VectorSignedShort.from(i16x8.new(256, 256, 256, 256, 256, 256, 256, 256))
    val c = VectorSignedShort.from(i16x8.new(0, 1, 2, 3, 4, 5, 6, Short.MAX_VALUE - 1))
    val d = i16x8.new(0, 3, 6, 9, 12, 15, 18, Short.MAX_VALUE)
    check(d == i16x8.from(vecMradds(a, b, c)))
}

public fun testVecMladdU16x8U16x8() {
    val a = VectorUnsignedShort.from(u16x8.new(0, 1, 2, 3, 4, 5, 6, 7))
    val b = VectorUnsignedShort.from(u16x8.new(0, 1, 2, 3, 4, 5, 6, 7))
    val c = u16x8.new(0, 1, 2, 3, 4, 5, 6, 7).into()
    val d = u16x8.new(0, 2, 6, 12, 20, 30, 42, 56)
    check(d == u16x8.from(vecMladd(a, b, c)))
}

public fun testVecMladdU16x8I16x8() {
    val a = VectorUnsignedShort.from(u16x8.new(0, 1, 2, 3, 4, 5, 6, 7))
    val b = VectorSignedShort.from(i16x8.new(0, 1, 2, 3, 4, 5, 6, 7))
    val c = i16x8.new(0, 1, 2, 3, 4, 5, 6, 7).into()
    val d = i16x8.new(0, 2, 6, 12, 20, 30, 42, 56)
    check(d == i16x8.from(vecMladd(a, b, c)))
}

public fun testVecMladdI16x8U16x8() {
    val a = VectorSignedShort.from(i16x8.new(0, 1, 2, 3, 4, 5, 6, 7))
    val b = VectorUnsignedShort.from(u16x8.new(0, 1, 2, 3, 4, 5, 6, 7))
    val c = u16x8.new(0, 1, 2, 3, 4, 5, 6, 7).into()
    val d = i16x8.new(0, 2, 6, 12, 20, 30, 42, 56)
    check(d == i16x8.from(vecMladd(a, b, c)))
}

public fun testVecMladdI16x8I16x8() {
    val a = VectorSignedShort.from(i16x8.new(0, 1, 2, 3, 4, 5, 6, 7))
    val b = VectorSignedShort.from(i16x8.new(0, 1, 2, 3, 4, 5, 6, 7))
    val c = i16x8.new(0, 1, 2, 3, 4, 5, 6, 7).into()
    val d = i16x8.new(0, 2, 6, 12, 20, 30, 42, 56)
    check(d == i16x8.from(vecMladd(a, b, c)))
}

public fun testVecMsumUnsignedChar() {
    val a = VectorUnsignedChar.from(u8x16.new(0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5, 6, 7))
    val b = VectorUnsignedChar.from(u8x16.new(255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255))
    val c = VectorUnsignedInt.from(u32x4.new(0, 1, 2, 3))
    val d = u32x4.new((0 + 1 + 2 + 3) * 255 + 0, (4 + 5 + 6 + 7) * 255 + 1, (0 + 1 + 2 + 3) * 255 + 2, (4 + 5 + 6 + 7) * 255 + 3)
    check(d == u32x4.from(vecMsum(a, b, c)))
}

public fun testVecMsumSignedChar() {
    val a = VectorSignedChar.from(i8x16.new(0, -1, 2, -3, 1, -1, 1, -1, 0, 1, 2, 3, 4, -5, -6, -7))
    val b = VectorUnsignedChar.from(u8x16.new(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1))
    val c = VectorSignedInt.from(i32x4.new(0, 1, 2, 3))
    val d = i32x4.new((0 - 1 + 2 - 3) + 0, (0) + 1, (0 + 1 + 2 + 3) + 2, (4 - 5 - 6 - 7) + 3)
    check(d == i32x4.from(vecMsum(a, b, c)))
}

public fun testVecMsumUnsignedShort() {
    val a = VectorUnsignedShort.from(u16x8.new(0 * 256, 1 * 256, 2 * 256, 3 * 256, 4 * 256, 5 * 256, 6 * 256, 7 * 256))
    val b = VectorUnsignedShort.from(u16x8.new(256, 256, 256, 256, 256, 256, 256, 256))
    val c = VectorUnsignedInt.from(u32x4.new(0, 1, 2, 3))
    val d = u32x4.new((0 + 1) * 256 * 256 + 0, (2 + 3) * 256 * 256 + 1, (4 + 5) * 256 * 256 + 2, (6 + 7) * 256 * 256 + 3)
    check(d == u32x4.from(vecMsum(a, b, c)))
}

public fun testVecMsumSignedShort() {
    val a = VectorSignedShort.from(i16x8.new(0 * 256, -1 * 256, 2 * 256, -3 * 256, 4 * 256, -5 * 256, 6 * 256, -7 * 256))
    val b = VectorSignedShort.from(i16x8.new(256, 256, 256, 256, 256, 256, 256, 256))
    val c = VectorSignedInt.from(i32x4.new(0, 1, 2, 3))
    val d = i32x4.new((0 - 1) * 256 * 256 + 0, (2 - 3) * 256 * 256 + 1, (4 - 5) * 256 * 256 + 2, (6 - 7) * 256 * 256 + 3)
    check(d == i32x4.from(vecMsum(a, b, c)))
}

public fun testVecMsumsUnsigned() {
    val a = VectorUnsignedShort.from(u16x8.new(0 * 256, 1 * 256, 2 * 256, 3 * 256, 4 * 256, 5 * 256, 6 * 256, 7 * 256))
    val b = VectorUnsignedShort.from(u16x8.new(256, 256, 256, 256, 256, 256, 256, 256))
    val c = VectorUnsignedInt.from(u32x4.new(0, 1, 2, 3))
    val d = u32x4.new((0 + 1) * 256 * 256 + 0, (2 + 3) * 256 * 256 + 1, (4 + 5) * 256 * 256 + 2, (6 + 7) * 256 * 256 + 3)
    check(d == u32x4.from(vecMsums(a, b, c)))
}

public fun testVecMsumsSigned() {
    val a = VectorSignedShort.from(i16x8.new(0 * 256, -1 * 256, 2 * 256, -3 * 256, 4 * 256, -5 * 256, 6 * 256, -7 * 256))
    val b = VectorSignedShort.from(i16x8.new(256, 256, 256, 256, 256, 256, 256, 256))
    val c = VectorSignedInt.from(i32x4.new(0, 1, 2, 3))
    val d = i32x4.new((0 - 1) * 256 * 256 + 0, (2 - 3) * 256 * 256 + 1, (4 - 5) * 256 * 256 + 2, (6 - 7) * 256 * 256 + 3)
    check(d == i32x4.from(vecMsums(a, b, c)))
}

public fun testVecSum2s() {
    val a = VectorSignedInt.from(i32x4.new(0, 1, 2, 3))
    val b = VectorSignedInt.from(i32x4.new(0, 1, 2, 3))
    val d = i32x4.new(0, 0 + 1 + 1, 0, 2 + 3 + 3)
    check(d == i32x4.from(vecSum2s(a, b)))
}

public fun testVecSum4sUnsignedChar() {
    val a = VectorUnsignedChar.from(u8x16.new(0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5, 6, 7))
    val b = VectorUnsignedInt.from(u32x4.new(0, 1, 2, 3))
    val d = u32x4.new(0 + 1 + 2 + 3 + 0, 4 + 5 + 6 + 7 + 1, 0 + 1 + 2 + 3 + 2, 4 + 5 + 6 + 7 + 3)
    check(d == u32x4.from(vecSum4s(a, b)))
}

public fun testVecSum4sSignedChar() {
    val a = VectorSignedChar.from(i8x16.new(0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5, 6, 7))
    val b = VectorSignedInt.from(i32x4.new(0, 1, 2, 3))
    val d = i32x4.new(0 + 1 + 2 + 3 + 0, 4 + 5 + 6 + 7 + 1, 0 + 1 + 2 + 3 + 2, 4 + 5 + 6 + 7 + 3)
    check(d == i32x4.from(vecSum4s(a, b)))
}

public fun testVecSum4sSignedShort() {
    val a = VectorSignedShort.from(i16x8.new(0, 1, 2, 3, 4, 5, 6, 7))
    val b = VectorSignedInt.from(i32x4.new(0, 1, 2, 3))
    val d = i32x4.new(0 + 1 + 0, 2 + 3 + 1, 4 + 5 + 2, 6 + 7 + 3)
    check(d == i32x4.from(vecSum4s(a, b)))
}

public fun testVecMuleUnsignedChar() {
    val a = VectorUnsignedChar.from(u8x16.new(0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5, 6, 7))
    val d = u16x8.new(0 * 0, 2 * 2, 4 * 4, 6 * 6, 0 * 0, 2 * 2, 4 * 4, 6 * 6)
    check(d == u16x8.from(vecMule(a, a)))
}

public fun testVecMuleSignedChar() {
    val a = VectorSignedChar.from(i8x16.new(0, 1, -2, 3, -4, 5, -6, 7, 0, 1, 2, 3, 4, 5, 6, 7))
    val d = i16x8.new(0 * 0, 2 * 2, 4 * 4, 6 * 6, 0 * 0, 2 * 2, 4 * 4, 6 * 6)
    check(d == i16x8.from(vecMule(a, a)))
}

public fun testVecMuleUnsignedShort() {
    val a = VectorUnsignedShort.from(u16x8.new(0, 1, 2, 3, 4, 5, 6, 7))
    val d = u32x4.new(0 * 0, 2 * 2, 4 * 4, 6 * 6)
    check(d == u32x4.from(vecMule(a, a)))
}

public fun testVecMuleSignedShort() {
    val a = VectorSignedShort.from(i16x8.new(0, 1, -2, 3, -4, 5, -6, 7))
    val d = i32x4.new(0 * 0, 2 * 2, 4 * 4, 6 * 6)
    check(d == i32x4.from(vecMule(a, a)))
}

public fun testVecMuloUnsignedChar() {
    val a = VectorUnsignedChar.from(u8x16.new(0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5, 6, 7))
    val d = u16x8.new(1 * 1, 3 * 3, 5 * 5, 7 * 7, 1 * 1, 3 * 3, 5 * 5, 7 * 7)
    check(d == u16x8.from(vecMulo(a, a)))
}

public fun testVecMuloSignedChar() {
    val a = VectorSignedChar.from(i8x16.new(0, 1, -2, 3, -4, 5, -6, 7, 0, 1, 2, 3, 4, 5, 6, 7))
    val d = i16x8.new(1 * 1, 3 * 3, 5 * 5, 7 * 7, 1 * 1, 3 * 3, 5 * 5, 7 * 7)
    check(d == i16x8.from(vecMulo(a, a)))
}

public fun testVecMuloUnsignedShort() {
    val a = VectorUnsignedShort.from(u16x8.new(0, 1, 2, 3, 4, 5, 6, 7))
    val d = u32x4.new(1 * 1, 3 * 3, 5 * 5, 7 * 7)
    check(d == u32x4.from(vecMulo(a, a)))
}

public fun testVecMuloSignedShort() {
    val a = VectorSignedShort.from(i16x8.new(0, 1, -2, 3, -4, 5, -6, 7))
    val d = i32x4.new(1 * 1, 3 * 3, 5 * 5, 7 * 7)
    check(d == i32x4.from(vecMulo(a, a)))
}

public fun vecAddI32x4I32x4() {
    val x = i32x4.new(1, 2, 3, 4)
    val y = i32x4.new(4, 3, 2, 1)
    val x1 = VectorSignedInt.from(x)
    val y1 = VectorSignedInt.from(y)
    val z = vecAdd(x1, y1)
    check(i32x4.splat(5) == i32x4.from(z))
}

public fun vecCtfU32() {
    val v = VectorUnsignedInt.from(u32x4.new(UInt.MIN_VALUE, UInt.MAX_VALUE, UInt.MAX_VALUE, 42))
    val v2 = vecCtf(v, 1)
    val r2 = VectorFloat.from(f32x4.new(0.0, 2147483600.0, 2147483600.0, 21.0))
    val v4 = vecCtf(v, 2)
    val r4 = VectorFloat.from(f32x4.new(0.0, 1073741800.0, 1073741800.0, 10.5))
    val v8 = vecCtf(v, 3)
    val r8 = VectorFloat.from(f32x4.new(0.0, 536870900.0, 536870900.0, 5.25))
    val check =
        { a, b ->
            val r = m32x4.from(vecCmple(vecAbs(vecSub(a, b)), vecSplats(1.1920929E-7f)))
            val e = m32x4.splat(true)
            check(e == r)
        }
    check(v2, r2)
    check(v4, r4)
    check(v8, r8)
}

public fun testVecCtu() {
    val v = u32x4.new(UInt.MIN_VALUE, UInt.MAX_VALUE, UInt.MAX_VALUE, 42)
    val v2 = u32x4.from(vecCtu(VectorFloat.from(f32x4.new(0.0, 2147483600.0, 2147483600.0, 21.0)), 1))
    val v4 = u32x4.from(vecCtu(VectorFloat.from(f32x4.new(0.0, 1073741800.0, 1073741800.0, 10.5)), 2))
    val v8 = u32x4.from(vecCtu(VectorFloat.from(f32x4.new(0.0, 536870900.0, 536870900.0, 5.25)), 3))
    check(v2 == v)
    check(v4 == v)
    check(v8 == v)
}

public fun vecCtfI32() {
    val v = VectorSignedInt.from(i32x4.new(Int.MIN_VALUE, Int.MAX_VALUE, Int.MAX_VALUE - 42, 42))
    val v2 = vecCtf(v, 1)
    val r2 = VectorFloat.from(f32x4.new(-1073741800.0, 1073741800.0, 1073741800.0, 21.0))
    val v4 = vecCtf(v, 2)
    val r4 = VectorFloat.from(f32x4.new(-536870900.0, 536870900.0, 536870900.0, 10.5))
    val v8 = vecCtf(v, 3)
    val r8 = VectorFloat.from(f32x4.new(-268435460.0, 268435460.0, 268435460.0, 5.25))
    val check =
        { a, b ->
            val r = m32x4.from(vecCmple(vecAbs(vecSub(a, b)), vecSplats(1.1920929E-7f)))
            println("$a $b")
            val e = m32x4.splat(true)
            check(e == r)
        }
    check(v2, r2)
    check(v4, r4)
    check(v8, r8)
}

public fun testVecCts() {
    val v = i32x4.new(Int.MIN_VALUE, Int.MAX_VALUE, Int.MAX_VALUE, 42)
    val v2 = i32x4.from(vecCts(transmute(f32x4.new(-1073741800.0, 1073741800.0, 1073741800.0, 21.0)), 1))
    val v4 = i32x4.from(vecCts(transmute(f32x4.new(-536870900.0, 536870900.0, 536870900.0, 10.5)), 2))
    val v8 = i32x4.from(vecCts(transmute(f32x4.new(-268435460.0, 268435460.0, 268435460.0, 5.25)), 3))
    check(v2 == v)
    check(v4 == v)
    check(v8 == v)
}

public fun testVecRl() {
    val a: STL = u32x4.fromArray(listOf(0x12345678, 0x9ABCDEF0, 0x0F0F0F0F, 0x12345678)).into()
    val b: STL = u32x4.fromArray(listOf(4, 8, 12, 68)).into()
    val d = u32x4.fromArray(listOf(0x23456781, 0xBCDEF09A, 0xF0F0F0F0, 0x23456781))
    val r = u32x4.from(vecRl(a, b))
    check(d == r)
}
