// port-lint: tests powerpc/altivec.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.powerpc

import io.github.kotlinmania.rustlang.core.convert.num.from
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.corearch.simd.F32x4
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.M16x8
import io.github.kotlinmania.rustlang.corearch.simd.M32x4
import io.github.kotlinmania.rustlang.corearch.simd.M8x16
import io.github.kotlinmania.rustlang.corearch.simd.Simd
import io.github.kotlinmania.rustlang.corearch.simd.SimdM
import io.github.kotlinmania.rustlang.corearch.simd.U16x8
import io.github.kotlinmania.rustlang.corearch.simd.U32x4
import io.github.kotlinmania.rustlang.corearch.simd.U8x16

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
    val pat = listOf(Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte())), Simd<UByte>(listOf(16u.toUByte(), 17u.toUByte(), 18u.toUByte(), 19u.toUByte(), 20u.toUByte(), 21u.toUByte(), 22u.toUByte(), 23u.toUByte(), 24u.toUByte(), 25u.toUByte(), 26u.toUByte(), 27u.toUByte(), 28u.toUByte(), 29u.toUByte(), 30u.toUByte(), 31u.toUByte())))
    for (off in 0 until 16) {
        val v = U8x16.from(vecLd(0, ConstPtr(((constPtrFromRustCast(pat.asPtr()))).rawAddress + ((off).toLong() * (PrimitiveLayouts.U8).strideBytes))))
        check(v == Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte())))
    }
    for (off1 in 16 until 32) {
        val v1 = U8x16.from(vecLd(0, ConstPtr(((constPtrFromRustCast(pat.asPtr()))).rawAddress + ((off1).toLong() * (PrimitiveLayouts.U8).strideBytes))))
        check(v1 == Simd<UByte>(listOf(16u.toUByte(), 17u.toUByte(), 18u.toUByte(), 19u.toUByte(), 20u.toUByte(), 21u.toUByte(), 22u.toUByte(), 23u.toUByte(), 24u.toUByte(), 25u.toUByte(), 26u.toUByte(), 27u.toUByte(), 28u.toUByte(), 29u.toUByte(), 30u.toUByte(), 31u.toUByte())))
    }
}

public fun testVecXl() {
    val pat = listOf(Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte())), Simd<UByte>(listOf(16u.toUByte(), 17u.toUByte(), 18u.toUByte(), 19u.toUByte(), 20u.toUByte(), 21u.toUByte(), 22u.toUByte(), 23u.toUByte(), 24u.toUByte(), 25u.toUByte(), 26u.toUByte(), 27u.toUByte(), 28u.toUByte(), 29u.toUByte(), 30u.toUByte(), 31u.toUByte())))
    for (off in 0 until 16) {
        val value = U8x16.from(vecXl(0, ConstPtr(((constPtrFromRustCast(pat.asPtr()))).rawAddress + ((off).toLong() * (PrimitiveLayouts.U8).strideBytes))))
        for (i in 0 until 16) {
            val v = value.extractDyn(i)
            check(off.toInt() + i == v.toInt())
        }
    }
}

public fun testVecXst() {
    val v = VectorUnsignedChar.from(Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte())))
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
    val pat = listOf(Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte())), Simd<UByte>(listOf(16u.toUByte(), 17u.toUByte(), 18u.toUByte(), 19u.toUByte(), 20u.toUByte(), 21u.toUByte(), 22u.toUByte(), 23u.toUByte(), 24u.toUByte(), 25u.toUByte(), 26u.toUByte(), 27u.toUByte(), 28u.toUByte(), 29u.toUByte(), 30u.toUByte(), 31u.toUByte())))
    for (off in 0 until 16) {
        val v = U8x16.from(vecLdl(0, ConstPtr(((constPtrFromRustCast(pat.asPtr()))).rawAddress + ((off).toLong() * (PrimitiveLayouts.U8).strideBytes))))
        check(v == Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte())))
    }
    for (off1 in 16 until 32) {
        val v1 = U8x16.from(vecLdl(0, ConstPtr(((constPtrFromRustCast(pat.asPtr()))).rawAddress + ((off1).toLong() * (PrimitiveLayouts.U8).strideBytes))))
        check(v1 == Simd<UByte>(listOf(16u.toUByte(), 17u.toUByte(), 18u.toUByte(), 19u.toUByte(), 20u.toUByte(), 21u.toUByte(), 22u.toUByte(), 23u.toUByte(), 24u.toUByte(), 25u.toUByte(), 26u.toUByte(), 27u.toUByte(), 28u.toUByte(), 29u.toUByte(), 30u.toUByte(), 31u.toUByte())))
    }
}

public fun testVecLdeU8() {
    val pat = listOf(Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte())))
    for (off in 0 until 16) {
        val v = U8x16.from(vecLde(off, constPtrFromRustCast(pat.asPtr())))
        check(off.toUByte() == v.extractDyn(off))
    }
}

public fun testVecLdeU16() {
    val pat = listOf(Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 7u.toUShort())))
    for (off in 0 until 8) {
        val v = U16x8.from(vecLde(off * 2, constPtrFromRustCast(pat.asPtr())))
        check(off.toUShort() == v.extractDyn(off))
    }
}

public fun testVecLdeU32() {
    val pat = listOf(Simd<UInt>(listOf(0u, 1u, 2u, 3u)))
    for (off in 0 until 4) {
        val v = U32x4.from(vecLde(off * 4, constPtrFromRustCast(pat.asPtr())))
        check(off.toUInt() == v.extractDyn(off))
    }
}

public fun testVecFloor() {
    val a: VectorFloat = Simd<Float>(listOf(1.1f.toFloat(), 1.9f.toFloat(), (-0.5f).toFloat(), (-0.9f).toFloat())).into()
    val d = Simd<Float>(listOf(1.0f.toFloat(), 1.0f.toFloat(), (-1.0f).toFloat(), (-1.0f).toFloat()))
    val r = F32x4.from(vecFloor(a))
    check(d == r)
}

public fun testVecExpte() {
    val a = VectorFloat.from(Simd<Float>(listOf(0.0f.toFloat(), 2.0f.toFloat(), 2.0f.toFloat(), (-1.0f).toFloat())))
    val d = VectorFloat.from(Simd<Float>(listOf(1.0f.toFloat(), 4.0f.toFloat(), 4.0f.toFloat(), 0.5f.toFloat())))
    val r = M32x4.from(vecCmple(vecAbs(vecSub(vecExpte(a), d)), vecSplats(1.1920929E-7f)))
    val e = SimdM<Int>(List(4) { if (true) -1 else 0 })
    check(e == r)
}

public fun testVecCmpgtI8() {
    val a: VectorSignedChar = Simd<Byte>((listOf(1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val d = SimdM<Byte>((booleanArrayOf(true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false)).toList())
    val r = M8x16.from(vecCmpgt(a, b))
    check(d == r)
}

public fun testVecCmpgtU8() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(0, 0, 255, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val d = SimdM<Byte>((booleanArrayOf(true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false)).toList())
    val r = M8x16.from(vecCmpgt(a, b))
    check(d == r)
}

public fun testVecCmpgtI16() {
    val a: VectorSignedShort = Simd<Short>((listOf(1, -1, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(0, 0, -1, 1, 0, 0, 0, 0)).toList()).into()
    val d = SimdM<Short>((booleanArrayOf(true, false, true, false, false, false, false, false)).toList())
    val r = M16x8.from(vecCmpgt(a, b))
    check(d == r)
}

public fun testVecCmpgtU16() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(1, 255, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(0, 0, 255, 1, 0, 0, 0, 0)).toList()).into()
    val d = SimdM<Short>((booleanArrayOf(true, true, false, false, false, false, false, false)).toList())
    val r = M16x8.from(vecCmpgt(a, b))
    check(d == r)
}

public fun testVecCmpgtI32() {
    val a: VectorSignedInt = Simd<Int>((listOf(1, -1, 0, 0)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(0, -1, 0, 1)).toList()).into()
    val d = SimdM<Int>((booleanArrayOf(true, false, false, false)).toList())
    val r = M32x4.from(vecCmpgt(a, b))
    check(d == r)
}

public fun testVecCmpgtU32() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(1, 255, 0, 0)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(0, 255, 0, 1)).toList()).into()
    val d = SimdM<Int>((booleanArrayOf(true, false, false, false)).toList())
    val r = M32x4.from(vecCmpgt(a, b))
    check(d == r)
}

public fun testVecCmpge() {
    val a: VectorFloat = Simd<Float>((listOf(0.1, -0.1, 0.0, 0.99)).toList()).into()
    val b: VectorFloat = Simd<Float>((listOf(0.1, 0.0, 0.1, 1.0)).toList()).into()
    val d = SimdM<Int>((booleanArrayOf(true, false, false, false)).toList())
    val r = M32x4.from(vecCmpge(a, b))
    check(d == r)
}

public fun testVecCmpeqI8() {
    val a: VectorSignedChar = Simd<Byte>((listOf(1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val d = SimdM<Byte>((booleanArrayOf(false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true)).toList())
    val r = M8x16.from(vecCmpeq(a, b))
    check(d == r)
}

public fun testVecCmpeqU8() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(0, 0, 255, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val d = SimdM<Byte>((booleanArrayOf(false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true)).toList())
    val r = M8x16.from(vecCmpeq(a, b))
    check(d == r)
}

public fun testVecCmpeqI16() {
    val a: VectorSignedShort = Simd<Short>((listOf(1, -1, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(0, 0, -1, 1, 0, 0, 0, 0)).toList()).into()
    val d = SimdM<Short>((booleanArrayOf(false, false, false, false, true, true, true, true)).toList())
    val r = M16x8.from(vecCmpeq(a, b))
    check(d == r)
}

public fun testVecCmpeqU16() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(1, 255, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(0, 0, 255, 1, 0, 0, 0, 0)).toList()).into()
    val d = SimdM<Short>((booleanArrayOf(false, false, false, false, true, true, true, true)).toList())
    val r = M16x8.from(vecCmpeq(a, b))
    check(d == r)
}

public fun testVecCmpeqI32() {
    val a: VectorSignedInt = Simd<Int>((listOf(1, -1, 0, 0)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(0, -1, 0, 1)).toList()).into()
    val d = SimdM<Int>((booleanArrayOf(false, true, true, false)).toList())
    val r = M32x4.from(vecCmpeq(a, b))
    check(d == r)
}

public fun testVecCmpeqU32() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(1, 255, 0, 0)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(0, 255, 0, 1)).toList()).into()
    val d = SimdM<Int>((booleanArrayOf(false, true, true, false)).toList())
    val r = M32x4.from(vecCmpeq(a, b))
    check(d == r)
}

public fun testVecCmpneI8() {
    val a: VectorSignedChar = Simd<Byte>((listOf(1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val d = SimdM<Byte>((booleanArrayOf(true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false)).toList())
    val r = M8x16.from(vecCmpne(a, b))
    check(d == r)
}

public fun testVecCmpneU8() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(0, 0, 255, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val d = SimdM<Byte>((booleanArrayOf(true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false)).toList())
    val r = M8x16.from(vecCmpne(a, b))
    check(d == r)
}

public fun testVecCmpneI16() {
    val a: VectorSignedShort = Simd<Short>((listOf(1, -1, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(0, 0, -1, 1, 0, 0, 0, 0)).toList()).into()
    val d = SimdM<Short>((booleanArrayOf(true, true, true, true, false, false, false, false)).toList())
    val r = M16x8.from(vecCmpne(a, b))
    check(d == r)
}

public fun testVecCmpneU16() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(1, 255, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(0, 0, 255, 1, 0, 0, 0, 0)).toList()).into()
    val d = SimdM<Short>((booleanArrayOf(true, true, true, true, false, false, false, false)).toList())
    val r = M16x8.from(vecCmpne(a, b))
    check(d == r)
}

public fun testVecCmpneI32() {
    val a: VectorSignedInt = Simd<Int>((listOf(1, -1, 0, 0)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(0, -1, 0, 1)).toList()).into()
    val d = SimdM<Int>((booleanArrayOf(true, false, false, true)).toList())
    val r = M32x4.from(vecCmpne(a, b))
    check(d == r)
}

public fun testVecCmpneU32() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(1, 255, 0, 0)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(0, 255, 0, 1)).toList()).into()
    val d = SimdM<Int>((booleanArrayOf(true, false, false, true)).toList())
    val r = M32x4.from(vecCmpne(a, b))
    check(d == r)
}

public fun testVecAllEqI8False() {
    val a: VectorSignedChar = Simd<Byte>((listOf(1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllEq(a, b))
    check(false == r)
}

public fun testVecAllEqU8False() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(0, 0, 255, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllEq(a, b))
    check(false == r)
}

public fun testVecAllEqI16False() {
    val a: VectorSignedShort = Simd<Short>((listOf(1, -1, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(0, 0, -1, 1, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllEq(a, b))
    check(false == r)
}

public fun testVecAllEqU16False() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(1, 255, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(0, 0, 255, 1, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllEq(a, b))
    check(false == r)
}

public fun testVecAllEqI32False() {
    val a: VectorSignedInt = Simd<Int>((listOf(1, -1, 0, 0)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(0, -1, 0, 1)).toList()).into()
    val r = Boolean.from(vecAllEq(a, b))
    check(false == r)
}

public fun testVecAllEqU32False() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(1, 255, 0, 0)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(0, 255, 0, 1)).toList()).into()
    val r = Boolean.from(vecAllEq(a, b))
    check(false == r)
}

public fun testVecAllEqI8True() {
    val a: VectorSignedChar = Simd<Byte>((listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllEq(a, b))
    check(true == r)
}

public fun testVecAllEqU8True() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllEq(a, b))
    check(true == r)
}

public fun testVecAllEqI16True() {
    val a: VectorSignedShort = Simd<Short>((listOf(1, -1, 1, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(1, -1, 1, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllEq(a, b))
    check(true == r)
}

public fun testVecAllEqU16True() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(1, 255, 1, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(1, 255, 1, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllEq(a, b))
    check(true == r)
}

public fun testVecAllEqI32True() {
    val a: VectorSignedInt = Simd<Int>((listOf(1, -1, 0, 1)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(1, -1, 0, 1)).toList()).into()
    val r = Boolean.from(vecAllEq(a, b))
    check(true == r)
}

public fun testVecAllEqU32True() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(1, 255, 0, 1)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(1, 255, 0, 1)).toList()).into()
    val r = Boolean.from(vecAllEq(a, b))
    check(true == r)
}

public fun testVecAnyEqI8False() {
    val a: VectorSignedChar = Simd<Byte>((listOf(1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(0, 0, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).toList()).into()
    val r = Boolean.from(vecAnyEq(a, b))
    check(false == r)
}

public fun testVecAnyEqU8False() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(0, 0, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).toList()).into()
    val r = Boolean.from(vecAnyEq(a, b))
    check(false == r)
}

public fun testVecAnyEqI16False() {
    val a: VectorSignedShort = Simd<Short>((listOf(1, -1, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(0, 0, -1, 1, 1, 1, 1, 1)).toList()).into()
    val r = Boolean.from(vecAnyEq(a, b))
    check(false == r)
}

public fun testVecAnyEqU16False() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(1, 255, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(0, 0, 255, 1, 1, 1, 1, 1)).toList()).into()
    val r = Boolean.from(vecAnyEq(a, b))
    check(false == r)
}

public fun testVecAnyEqI32False() {
    val a: VectorSignedInt = Simd<Int>((listOf(1, -1, 0, 0)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(0, -2, 1, 1)).toList()).into()
    val r = Boolean.from(vecAnyEq(a, b))
    check(false == r)
}

public fun testVecAnyEqU32False() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(1, 2, 1, 0)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(0, 255, 0, 1)).toList()).into()
    val r = Boolean.from(vecAnyEq(a, b))
    check(false == r)
}

public fun testVecAnyEqI8True() {
    val a: VectorSignedChar = Simd<Byte>((listOf(1, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyEq(a, b))
    check(true == r)
}

public fun testVecAnyEqU8True() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(0, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyEq(a, b))
    check(true == r)
}

public fun testVecAnyEqI16True() {
    val a: VectorSignedShort = Simd<Short>((listOf(0, -1, 1, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(1, -1, 1, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyEq(a, b))
    check(true == r)
}

public fun testVecAnyEqU16True() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(0, 255, 1, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(1, 255, 1, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyEq(a, b))
    check(true == r)
}

public fun testVecAnyEqI32True() {
    val a: VectorSignedInt = Simd<Int>((listOf(0, -1, 0, 1)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(1, -1, 0, 1)).toList()).into()
    val r = Boolean.from(vecAnyEq(a, b))
    check(true == r)
}

public fun testVecAnyEqU32True() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(0, 255, 0, 1)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(1, 255, 0, 1)).toList()).into()
    val r = Boolean.from(vecAnyEq(a, b))
    check(true == r)
}

public fun testVecAllGeI8False() {
    val a: VectorSignedChar = Simd<Byte>((listOf(1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllGe(a, b))
    check(false == r)
}

public fun testVecAllGeU8False() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(0, 0, 255, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllGe(a, b))
    check(false == r)
}

public fun testVecAllGeI16False() {
    val a: VectorSignedShort = Simd<Short>((listOf(1, -1, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(0, 0, -1, 1, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllGe(a, b))
    check(false == r)
}

public fun testVecAllGeU16False() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(1, 255, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(0, 0, 255, 1, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllGe(a, b))
    check(false == r)
}

public fun testVecAllGeI32False() {
    val a: VectorSignedInt = Simd<Int>((listOf(1, -1, 0, 0)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(0, -1, 0, 1)).toList()).into()
    val r = Boolean.from(vecAllGe(a, b))
    check(false == r)
}

public fun testVecAllGeU32False() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(1, 255, 0, 0)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(0, 255, 1, 1)).toList()).into()
    val r = Boolean.from(vecAllGe(a, b))
    check(false == r)
}

public fun testVecAllGeI8True() {
    val a: VectorSignedChar = Simd<Byte>((listOf(0, 0, -1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllGe(a, b))
    check(true == r)
}

public fun testVecAllGeU8True() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(1, 255, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllGe(a, b))
    check(true == r)
}

public fun testVecAllGeI16True() {
    val a: VectorSignedShort = Simd<Short>((listOf(1, -1, 42, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(1, -5, 2, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllGe(a, b))
    check(true == r)
}

public fun testVecAllGeU16True() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(42, 255, 1, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(2, 255, 1, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllGe(a, b))
    check(true == r)
}

public fun testVecAllGeI32True() {
    val a: VectorSignedInt = Simd<Int>((listOf(1, -1, 0, 1)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(0, -1, 0, 1)).toList()).into()
    val r = Boolean.from(vecAllGe(a, b))
    check(true == r)
}

public fun testVecAllGeU32True() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(1, 255, 0, 1)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(1, 254, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllGe(a, b))
    check(true == r)
}

public fun testVecAnyGeI8False() {
    val a: VectorSignedChar = Simd<Byte>((listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).toList()).into()
    val r = Boolean.from(vecAnyGe(a, b))
    check(false == r)
}

public fun testVecAnyGeU8False() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(1, 254, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(42, 255, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).toList()).into()
    val r = Boolean.from(vecAnyGe(a, b))
    check(false == r)
}

public fun testVecAnyGeI16False() {
    val a: VectorSignedShort = Simd<Short>((listOf(1, -1, -2, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(2, 0, -1, 1, 1, 1, 1, 1)).toList()).into()
    val r = Boolean.from(vecAnyGe(a, b))
    check(false == r)
}

public fun testVecAnyGeU16False() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(1, 2, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(2, 42, 255, 1, 1, 1, 1, 1)).toList()).into()
    val r = Boolean.from(vecAnyGe(a, b))
    check(false == r)
}

public fun testVecAnyGeI32False() {
    val a: VectorSignedInt = Simd<Int>((listOf(1, -1, 0, 0)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(2, 0, 1, 1)).toList()).into()
    val r = Boolean.from(vecAnyGe(a, b))
    check(false == r)
}

public fun testVecAnyGeU32False() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(1, 2, 1, 0)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(4, 255, 4, 1)).toList()).into()
    val r = Boolean.from(vecAnyGe(a, b))
    check(false == r)
}

public fun testVecAnyGeI8True() {
    val a: VectorSignedChar = Simd<Byte>((listOf(1, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyGe(a, b))
    check(true == r)
}

public fun testVecAnyGeU8True() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(0, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyGe(a, b))
    check(true == r)
}

public fun testVecAnyGeI16True() {
    val a: VectorSignedShort = Simd<Short>((listOf(0, -1, 1, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(1, -1, 1, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyGe(a, b))
    check(true == r)
}

public fun testVecAnyGeU16True() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(0, 255, 1, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(1, 255, 1, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyGe(a, b))
    check(true == r)
}

public fun testVecAnyGeI32True() {
    val a: VectorSignedInt = Simd<Int>((listOf(0, -1, 0, 1)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(1, -1, 0, 1)).toList()).into()
    val r = Boolean.from(vecAnyGe(a, b))
    check(true == r)
}

public fun testVecAnyGeU32True() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(0, 255, 0, 1)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(1, 255, 0, 1)).toList()).into()
    val r = Boolean.from(vecAnyGe(a, b))
    check(true == r)
}

public fun testVecAllGtI8False() {
    val a: VectorSignedChar = Simd<Byte>((listOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllGt(a, b))
    check(false == r)
}

public fun testVecAllGtU8False() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(0, 0, 255, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllGt(a, b))
    check(false == r)
}

public fun testVecAllGtI16False() {
    val a: VectorSignedShort = Simd<Short>((listOf(1, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(0, 0, -1, 1, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllGt(a, b))
    check(false == r)
}

public fun testVecAllGtU16False() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(1, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(0, 0, 255, 1, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllGt(a, b))
    check(false == r)
}

public fun testVecAllGtI32False() {
    val a: VectorSignedInt = Simd<Int>((listOf(1, -1, 0, 0)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(0, -1, 0, 1)).toList()).into()
    val r = Boolean.from(vecAllGt(a, b))
    check(false == r)
}

public fun testVecAllGtU32False() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(1, 255, 0, 0)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(0, 255, 1, 1)).toList()).into()
    val r = Boolean.from(vecAllGt(a, b))
    check(false == r)
}

public fun testVecAllGtI8True() {
    val a: VectorSignedChar = Simd<Byte>((listOf(2, 1, -1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(0, 0, -2, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1)).toList()).into()
    val r = Boolean.from(vecAllGt(a, b))
    check(true == r)
}

public fun testVecAllGtU8True() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(1, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(0, 254, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllGt(a, b))
    check(true == r)
}

public fun testVecAllGtI16True() {
    val a: VectorSignedShort = Simd<Short>((listOf(1, -1, 42, 1, 1, 1, 1, 1)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(0, -5, 2, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllGt(a, b))
    check(true == r)
}

public fun testVecAllGtU16True() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(42, 255, 1, 1, 1, 1, 1, 1)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(2, 254, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllGt(a, b))
    check(true == r)
}

public fun testVecAllGtI32True() {
    val a: VectorSignedInt = Simd<Int>((listOf(1, -1, 1, 1)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(0, -2, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllGt(a, b))
    check(true == r)
}

public fun testVecAllGtU32True() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(1, 255, 1, 1)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(0, 254, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllGt(a, b))
    check(true == r)
}

public fun testVecAnyGtI8False() {
    val a: VectorSignedChar = Simd<Byte>((listOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).toList()).into()
    val r = Boolean.from(vecAnyGt(a, b))
    check(false == r)
}

public fun testVecAnyGtU8False() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(1, 254, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(42, 255, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).toList()).into()
    val r = Boolean.from(vecAnyGt(a, b))
    check(false == r)
}

public fun testVecAnyGtI16False() {
    val a: VectorSignedShort = Simd<Short>((listOf(1, -1, -2, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(2, 0, -1, 1, 1, 1, 1, 1)).toList()).into()
    val r = Boolean.from(vecAnyGt(a, b))
    check(false == r)
}

public fun testVecAnyGtU16False() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(1, 2, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(2, 42, 255, 1, 1, 1, 1, 1)).toList()).into()
    val r = Boolean.from(vecAnyGt(a, b))
    check(false == r)
}

public fun testVecAnyGtI32False() {
    val a: VectorSignedInt = Simd<Int>((listOf(1, -1, 0, 0)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(2, 0, 1, 1)).toList()).into()
    val r = Boolean.from(vecAnyGt(a, b))
    check(false == r)
}

public fun testVecAnyGtU32False() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(1, 2, 1, 0)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(4, 255, 4, 1)).toList()).into()
    val r = Boolean.from(vecAnyGt(a, b))
    check(false == r)
}

public fun testVecAnyGtI8True() {
    val a: VectorSignedChar = Simd<Byte>((listOf(1, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyGt(a, b))
    check(true == r)
}

public fun testVecAnyGtU8True() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(0, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyGt(a, b))
    check(true == r)
}

public fun testVecAnyGtI16True() {
    val a: VectorSignedShort = Simd<Short>((listOf(1, -1, 1, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(0, -1, 1, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyGt(a, b))
    check(true == r)
}

public fun testVecAnyGtU16True() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(1, 255, 1, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(0, 255, 1, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyGt(a, b))
    check(true == r)
}

public fun testVecAnyGtI32True() {
    val a: VectorSignedInt = Simd<Int>((listOf(1, -1, 0, 1)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(0, -1, 0, 1)).toList()).into()
    val r = Boolean.from(vecAnyGt(a, b))
    check(true == r)
}

public fun testVecAnyGtU32True() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(1, 255, 0, 1)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(0, 255, 0, 1)).toList()).into()
    val r = Boolean.from(vecAnyGt(a, b))
    check(true == r)
}

public fun testVecAllInTrue() {
    val a: VectorFloat = Simd<Float>((listOf(0.0, -0.1, 0.0, 0.0)).toList()).into()
    val b: VectorFloat = Simd<Float>((listOf(0.1, 0.2, 0.0, 0.0)).toList()).into()
    val r = Boolean.from(vecAllIn(a, b))
    check(true == r)
}

public fun testVecAllInFalse() {
    val a: VectorFloat = Simd<Float>((listOf(0.5, 0.4, -0.5, 0.8)).toList()).into()
    val b: VectorFloat = Simd<Float>((listOf(0.1, 0.4, -0.5, 0.8)).toList()).into()
    val r = Boolean.from(vecAllIn(a, b))
    check(false == r)
}

public fun testVecAllLeI8False() {
    val a: VectorSignedChar = Simd<Byte>((listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllLe(a, b))
    check(false == r)
}

public fun testVecAllLeU8False() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(0, 0, 255, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllLe(a, b))
    check(false == r)
}

public fun testVecAllLeI16False() {
    val a: VectorSignedShort = Simd<Short>((listOf(0, 0, -1, 1, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(1, -1, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllLe(a, b))
    check(false == r)
}

public fun testVecAllLeU16False() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(0, 0, 255, 1, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(1, 255, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllLe(a, b))
    check(false == r)
}

public fun testVecAllLeI32False() {
    val a: VectorSignedInt = Simd<Int>((listOf(0, -1, 0, 1)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(1, -1, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllLe(a, b))
    check(false == r)
}

public fun testVecAllLeU32False() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(0, 255, 1, 1)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(1, 255, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllLe(a, b))
    check(false == r)
}

public fun testVecAllLeI8True() {
    val a: VectorSignedChar = Simd<Byte>((listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(0, 0, -1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllLe(a, b))
    check(true == r)
}

public fun testVecAllLeU8True() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(1, 255, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllLe(a, b))
    check(true == r)
}

public fun testVecAllLeI16True() {
    val a: VectorSignedShort = Simd<Short>((listOf(1, -5, 2, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(1, -1, 42, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllLe(a, b))
    check(true == r)
}

public fun testVecAllLeU16True() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(2, 255, 1, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(42, 255, 1, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllLe(a, b))
    check(true == r)
}

public fun testVecAllLeI32True() {
    val a: VectorSignedInt = Simd<Int>((listOf(0, -1, 0, 1)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(1, -1, 0, 1)).toList()).into()
    val r = Boolean.from(vecAllLe(a, b))
    check(true == r)
}

public fun testVecAllLeU32True() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(1, 254, 0, 0)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(1, 255, 0, 1)).toList()).into()
    val r = Boolean.from(vecAllLe(a, b))
    check(true == r)
}

public fun testVecAnyLeI8False() {
    val a: VectorSignedChar = Simd<Byte>((listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyLe(a, b))
    check(false == r)
}

public fun testVecAnyLeU8False() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(42, 255, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(1, 254, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyLe(a, b))
    check(false == r)
}

public fun testVecAnyLeI16False() {
    val a: VectorSignedShort = Simd<Short>((listOf(2, 0, -1, 1, 1, 1, 1, 1)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(1, -1, -2, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyLe(a, b))
    check(false == r)
}

public fun testVecAnyLeU16False() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(2, 42, 255, 1, 1, 1, 1, 1)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(1, 2, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyLe(a, b))
    check(false == r)
}

public fun testVecAnyLeI32False() {
    val a: VectorSignedInt = Simd<Int>((listOf(2, 0, 1, 1)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(1, -1, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyLe(a, b))
    check(false == r)
}

public fun testVecAnyLeU32False() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(4, 255, 4, 1)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(1, 2, 1, 0)).toList()).into()
    val r = Boolean.from(vecAnyLe(a, b))
    check(false == r)
}

public fun testVecAnyLeI8True() {
    val a: VectorSignedChar = Simd<Byte>((listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(1, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyLe(a, b))
    check(true == r)
}

public fun testVecAnyLeU8True() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(0, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyLe(a, b))
    check(true == r)
}

public fun testVecAnyLeI16True() {
    val a: VectorSignedShort = Simd<Short>((listOf(1, -1, 1, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(0, -1, 1, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyLe(a, b))
    check(true == r)
}

public fun testVecAnyLeU16True() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(1, 255, 1, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(0, 255, 1, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyLe(a, b))
    check(true == r)
}

public fun testVecAnyLeI32True() {
    val a: VectorSignedInt = Simd<Int>((listOf(1, -1, 0, 1)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(0, -1, 0, 1)).toList()).into()
    val r = Boolean.from(vecAnyLe(a, b))
    check(true == r)
}

public fun testVecAnyLeU32True() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(1, 255, 0, 1)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(0, 255, 0, 1)).toList()).into()
    val r = Boolean.from(vecAnyLe(a, b))
    check(true == r)
}

public fun testVecAllLtI8False() {
    val a: VectorSignedChar = Simd<Byte>((listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllLt(a, b))
    check(false == r)
}

public fun testVecAllLtU8False() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(0, 0, 255, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllLt(a, b))
    check(false == r)
}

public fun testVecAllLtI16False() {
    val a: VectorSignedShort = Simd<Short>((listOf(0, 0, -1, 1, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(1, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllLt(a, b))
    check(false == r)
}

public fun testVecAllLtU16False() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(0, 0, 255, 1, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(1, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllLt(a, b))
    check(false == r)
}

public fun testVecAllLtI32False() {
    val a: VectorSignedInt = Simd<Int>((listOf(0, -1, 0, 1)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(1, -1, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllLt(a, b))
    check(false == r)
}

public fun testVecAllLtU32False() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(0, 255, 1, 1)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(1, 255, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllLt(a, b))
    check(false == r)
}

public fun testVecAllLtI8True() {
    val a: VectorSignedChar = Simd<Byte>((listOf(0, 0, -2, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(2, 1, -1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllLt(a, b))
    check(true == r)
}

public fun testVecAllLtU8True() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(0, 254, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(1, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).toList()).into()
    val r = Boolean.from(vecAllLt(a, b))
    check(true == r)
}

public fun testVecAllLtI16True() {
    val a: VectorSignedShort = Simd<Short>((listOf(0, -5, 2, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(1, -1, 42, 1, 1, 1, 1, 1)).toList()).into()
    val r = Boolean.from(vecAllLt(a, b))
    check(true == r)
}

public fun testVecAllLtU16True() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(2, 254, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(42, 255, 1, 1, 1, 1, 1, 1)).toList()).into()
    val r = Boolean.from(vecAllLt(a, b))
    check(true == r)
}

public fun testVecAllLtI32True() {
    val a: VectorSignedInt = Simd<Int>((listOf(0, -2, 0, 0)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(1, -1, 1, 1)).toList()).into()
    val r = Boolean.from(vecAllLt(a, b))
    check(true == r)
}

public fun testVecAllLtU32True() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(0, 254, 0, 0)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(1, 255, 1, 1)).toList()).into()
    val r = Boolean.from(vecAllLt(a, b))
    check(true == r)
}

public fun testVecAnyLtI8False() {
    val a: VectorSignedChar = Simd<Byte>((listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyLt(a, b))
    check(false == r)
}

public fun testVecAnyLtU8False() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(42, 255, 255, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(1, 254, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyLt(a, b))
    check(false == r)
}

public fun testVecAnyLtI16False() {
    val a: VectorSignedShort = Simd<Short>((listOf(2, 0, -1, 1, 1, 1, 1, 1)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(1, -1, -2, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyLt(a, b))
    check(false == r)
}

public fun testVecAnyLtU16False() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(2, 42, 255, 1, 1, 1, 1, 1)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(1, 2, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyLt(a, b))
    check(false == r)
}

public fun testVecAnyLtI32False() {
    val a: VectorSignedInt = Simd<Int>((listOf(2, 0, 1, 1)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(1, -1, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyLt(a, b))
    check(false == r)
}

public fun testVecAnyLtU32False() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(4, 255, 4, 1)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(1, 2, 1, 0)).toList()).into()
    val r = Boolean.from(vecAnyLt(a, b))
    check(false == r)
}

public fun testVecAnyLtI8True() {
    val a: VectorSignedChar = Simd<Byte>((listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(1, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyLt(a, b))
    check(true == r)
}

public fun testVecAnyLtU8True() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(0, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyLt(a, b))
    check(true == r)
}

public fun testVecAnyLtI16True() {
    val a: VectorSignedShort = Simd<Short>((listOf(0, -1, 1, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(1, -1, 1, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyLt(a, b))
    check(true == r)
}

public fun testVecAnyLtU16True() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(0, 255, 1, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(1, 255, 1, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyLt(a, b))
    check(true == r)
}

public fun testVecAnyLtI32True() {
    val a: VectorSignedInt = Simd<Int>((listOf(0, -1, 0, 1)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(1, -1, 0, 1)).toList()).into()
    val r = Boolean.from(vecAnyLt(a, b))
    check(true == r)
}

public fun testVecAnyLtU32True() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(0, 255, 0, 1)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(1, 255, 0, 1)).toList()).into()
    val r = Boolean.from(vecAnyLt(a, b))
    check(true == r)
}

public fun testVecAllNeI8False() {
    val a: VectorSignedChar = Simd<Byte>((listOf(1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllNe(a, b))
    check(false == r)
}

public fun testVecAllNeU8False() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(0, 0, 255, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllNe(a, b))
    check(false == r)
}

public fun testVecAllNeI16False() {
    val a: VectorSignedShort = Simd<Short>((listOf(1, -1, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(0, -1, 1, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllNe(a, b))
    check(false == r)
}

public fun testVecAllNeU16False() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(1, 255, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(0, 255, 0, 1, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllNe(a, b))
    check(false == r)
}

public fun testVecAllNeI32False() {
    val a: VectorSignedInt = Simd<Int>((listOf(1, -1, 0, 0)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(0, -1, 0, 1)).toList()).into()
    val r = Boolean.from(vecAllNe(a, b))
    check(false == r)
}

public fun testVecAllNeU32False() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(1, 255, 0, 0)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(0, 255, 0, 1)).toList()).into()
    val r = Boolean.from(vecAllNe(a, b))
    check(false == r)
}

public fun testVecAllNeI8True() {
    val a: VectorSignedChar = Simd<Byte>((listOf(0, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(1, 0, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllNe(a, b))
    check(true == r)
}

public fun testVecAllNeU8True() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(0, 254, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllNe(a, b))
    check(true == r)
}

public fun testVecAllNeI16True() {
    val a: VectorSignedShort = Simd<Short>((listOf(2, -2, 0, 1, 1, 1, 1, 1)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(1, -1, 1, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAllNe(a, b))
    check(true == r)
}

public fun testVecAllNeU16True() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(0, 254, 1, 1, 0, 0, 1, 0)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(1, 255, 0, 0, 1, 1, 0, 1)).toList()).into()
    val r = Boolean.from(vecAllNe(a, b))
    check(true == r)
}

public fun testVecAllNeI32True() {
    val a: VectorSignedInt = Simd<Int>((listOf(0, -2, 0, 0)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(1, -1, 1, 1)).toList()).into()
    val r = Boolean.from(vecAllNe(a, b))
    check(true == r)
}

public fun testVecAllNeU32True() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(1, 255, 0, 0)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(0, 254, 1, 1)).toList()).into()
    val r = Boolean.from(vecAllNe(a, b))
    check(true == r)
}

public fun testVecAnyNeI8False() {
    val a: VectorSignedChar = Simd<Byte>((listOf(1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyNe(a, b))
    check(false == r)
}

public fun testVecAnyNeU8False() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyNe(a, b))
    check(false == r)
}

public fun testVecAnyNeI16False() {
    val a: VectorSignedShort = Simd<Short>((listOf(1, -1, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(1, -1, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyNe(a, b))
    check(false == r)
}

public fun testVecAnyNeU16False() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(1, 255, 1, 1, 1, 1, 1, 0)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(1, 255, 1, 1, 1, 1, 1, 0)).toList()).into()
    val r = Boolean.from(vecAnyNe(a, b))
    check(false == r)
}

public fun testVecAnyNeI32False() {
    val a: VectorSignedInt = Simd<Int>((listOf(0, -1, 1, 1)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(0, -1, 1, 1)).toList()).into()
    val r = Boolean.from(vecAnyNe(a, b))
    check(false == r)
}

public fun testVecAnyNeU32False() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(1, 2, 1, 255)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(1, 2, 1, 255)).toList()).into()
    val r = Boolean.from(vecAnyNe(a, b))
    check(false == r)
}

public fun testVecAnyNeI8True() {
    val a: VectorSignedChar = Simd<Byte>((listOf(1, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyNe(a, b))
    check(true == r)
}

public fun testVecAnyNeU8True() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(0, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(1, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyNe(a, b))
    check(true == r)
}

public fun testVecAnyNeI16True() {
    val a: VectorSignedShort = Simd<Short>((listOf(0, -1, 1, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(1, -1, 1, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyNe(a, b))
    check(true == r)
}

public fun testVecAnyNeU16True() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(0, 255, 1, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(1, 255, 1, 0, 0, 0, 0, 0)).toList()).into()
    val r = Boolean.from(vecAnyNe(a, b))
    check(true == r)
}

public fun testVecAnyNeI32True() {
    val a: VectorSignedInt = Simd<Int>((listOf(0, -1, 0, 1)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(1, -1, 0, 1)).toList()).into()
    val r = Boolean.from(vecAnyNe(a, b))
    check(true == r)
}

public fun testVecAnyNeU32True() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(0, 255, 0, 1)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(1, 255, 0, 1)).toList()).into()
    val r = Boolean.from(vecAnyNe(a, b))
    check(true == r)
}

public fun testVecCmpb() {
    val a = VectorFloat.from(Simd<Float>(listOf(0.1f.toFloat(), 0.5f.toFloat(), 0.6f.toFloat(), 0.9f.toFloat())))
    val b = VectorFloat.from(Simd<Float>(listOf((-0.1f).toFloat(), 0.5f.toFloat(), (-0.6f).toFloat(), 0.9f.toFloat())))
    val d = Simd<Int>(listOf(-0x80000000.toInt(), 0, -0x80000000.toInt(), 0))
    check(d == I32x4.from(vecCmpb(a, b)))
}

public fun testVecCeil() {
    val a = VectorFloat.from(Simd<Float>(listOf(0.1f.toFloat(), 0.5f.toFloat(), 0.6f.toFloat(), 0.9f.toFloat())))
    val d = Simd<Float>(listOf(1.0f.toFloat(), 1.0f.toFloat(), 1.0f.toFloat(), 1.0f.toFloat()))
    check(d == F32x4.from(vecCeil(a)))
}

public fun testVecAndc() {
    val a: VectorSignedInt = Simd<Int>((listOf(0xCC, 0xCC, 0xCC, 0xCC)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(0x33, 0xF3, 0xC, 0x80)).toList()).into()
    val d = Simd<Int>((listOf(0xCC, 0xC, 0xC0, 0x4C)).toList())
    val r = I32x4.from(vecAndc(a, b))
    check(d == r)
}

public fun testVecAnd() {
    val a: VectorSignedInt = Simd<Int>((listOf(0xCC, 0xCC, 0xCC, 0xCC)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(0x33, 0xF3, 0xC, 0x0)).toList()).into()
    val d = Simd<Int>((listOf(0x0, 0xC0, 0xC, 0x0)).toList())
    val r = I32x4.from(vecAnd(a, b))
    check(d == r)
}

public fun testVecAvgI32x4() {
    val a: VectorSignedInt = Simd<Int>((intArrayOf(Int.MIN_VALUE, Int.MAX_VALUE, 1, -1)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(-1, 1, 1, -1)).toList()).into()
    val d = Simd<Int>((listOf(-1073741824, 1073741824, 1, -1)).toList())
    val r = I32x4.from(vecAvg(a, b))
    check(d == r)
}

public fun testVecAvgU32x4() {
    val a: VectorUnsignedInt = Simd<UInt>((uintArrayOf(UInt.MAX_VALUE, 0u, 1u, 2u)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(2, 1, 0, 0)).toList()).into()
    val d = Simd<UInt>((listOf(2147483649, 1, 1, 1)).toList())
    val r = U32x4.from(vecAvg(a, b))
    check(d == r)
}

public fun testVecAvgI16x8() {
    val a: VectorSignedShort = Simd<Short>((shortArrayOf(Short.MIN_VALUE, Short.MAX_VALUE, 1, (-1).toShort(), 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(-1, 1, 1, -1, 0, 0, 0, 0)).toList()).into()
    val d = Simd<Short>((listOf(-16384, 16384, 1, -1, 0, 0, 0, 0)).toList())
    val r = I16x8.from(vecAvg(a, b))
    check(d == r)
}

public fun testVecAvgU16x8() {
    val a: VectorUnsignedShort = Simd<UShort>((ushortArrayOf(UShort.MAX_VALUE, 0u, 1u, 2u, 0u, 0u, 0u, 0u)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(2, 1, 0, 0, 0, 0, 0, 0)).toList()).into()
    val d = Simd<UShort>((listOf(32769, 1, 1, 1, 0, 0, 0, 0)).toList())
    val r = U16x8.from(vecAvg(a, b))
    check(d == r)
}

public fun testVecAvgI8x16() {
    val a: VectorSignedChar = Simd<Byte>((byteArrayOf(Byte.MIN_VALUE, Byte.MAX_VALUE, 1, (-1).toByte(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(-1, 1, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val d = Simd<Byte>((listOf(-64, 64, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList())
    val r = I8x16.from(vecAvg(a, b))
    check(d == r)
}

public fun testVecAvgU8x16() {
    val a: VectorUnsignedChar = Simd<UByte>((ubyteArrayOf(UByte.MAX_VALUE, 0u, 1u, 2u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val d = Simd<UByte>((listOf(129, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList())
    val r = U8x16.from(vecAvg(a, b))
    check(d == r)
}

public fun testVecAddsI32x4() {
    val a: VectorSignedInt = Simd<Int>((intArrayOf(Int.MIN_VALUE, Int.MAX_VALUE, 1, -1)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(-1, 1, 1, -1)).toList()).into()
    val d = Simd<Int>((intArrayOf(Int.MIN_VALUE, Int.MAX_VALUE, 2, -2)).toList())
    val r = I32x4.from(vecAdds(a, b))
    check(d == r)
}

public fun testVecAddsU32x4() {
    val a: VectorUnsignedInt = Simd<UInt>((uintArrayOf(UInt.MAX_VALUE, 0u, 1u, 2u)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(2, 1, 0, 0)).toList()).into()
    val d = Simd<UInt>((uintArrayOf(UInt.MAX_VALUE, 1u, 1u, 2u)).toList())
    val r = U32x4.from(vecAdds(a, b))
    check(d == r)
}

public fun testVecAddsI16x8() {
    val a: VectorSignedShort = Simd<Short>((shortArrayOf(Short.MIN_VALUE, Short.MAX_VALUE, 1, (-1).toShort(), 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(-1, 1, 1, -1, 0, 0, 0, 0)).toList()).into()
    val d = Simd<Short>((shortArrayOf(Short.MIN_VALUE, Short.MAX_VALUE, 2, (-2).toShort(), 0, 0, 0, 0)).toList())
    val r = I16x8.from(vecAdds(a, b))
    check(d == r)
}

public fun testVecAddsU16x8() {
    val a: VectorUnsignedShort = Simd<UShort>((ushortArrayOf(UShort.MAX_VALUE, 0u, 1u, 2u, 0u, 0u, 0u, 0u)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(2, 1, 0, 0, 0, 0, 0, 0)).toList()).into()
    val d = Simd<UShort>((ushortArrayOf(UShort.MAX_VALUE, 1u, 1u, 2u, 0u, 0u, 0u, 0u)).toList())
    val r = U16x8.from(vecAdds(a, b))
    check(d == r)
}

public fun testVecAddsI8x16() {
    val a: VectorSignedChar = Simd<Byte>((byteArrayOf(Byte.MIN_VALUE, Byte.MAX_VALUE, 1, (-1).toByte(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(-1, 1, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val d = Simd<Byte>((byteArrayOf(Byte.MIN_VALUE, Byte.MAX_VALUE, 2, (-2).toByte(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList())
    val r = I8x16.from(vecAdds(a, b))
    check(d == r)
}

public fun testVecAddsU8x16() {
    val a: VectorUnsignedChar = Simd<UByte>((ubyteArrayOf(UByte.MAX_VALUE, 0u, 1u, 2u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)).toList()).into()
    val d = Simd<UByte>((ubyteArrayOf(UByte.MAX_VALUE, 1u, 1u, 2u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)).toList())
    val r = U8x16.from(vecAdds(a, b))
    check(d == r)
}

public fun testVecAddc() {
    val a: VectorUnsignedInt = Simd<UInt>((uintArrayOf(UInt.MAX_VALUE, 0u, 0u, 0u)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(1, 1, 1, 1)).toList()).into()
    val d = Simd<UInt>((listOf(1, 0, 0, 0)).toList())
    val r = U32x4.from(vecAddc(a, b))
    check(d == r)
}

public fun testVecAbsI8() {
    val a = vecSplats(-42)
    val a1: VectorSignedChar = vecAbs(a)
    val d = Simd<Byte>(List(16) { 42 })
    check(d == I8x16.from(a1))
}

public fun testVecAbsI16() {
    val a = vecSplats(-42)
    val a1: VectorSignedShort = vecAbs(a)
    val d = Simd<Short>(List(8) { 42 })
    check(d == I16x8.from(a1))
}

public fun testVecAbsI32() {
    val a = vecSplats(-42)
    val a1: VectorSignedInt = vecAbs(a)
    val d = Simd<Int>(List(4) { 42 })
    check(d == I32x4.from(a1))
}

public fun testVecAbsF32() {
    val a = vecSplats(-42.0f)
    val a1: VectorFloat = vecAbs(a)
    val d = Simd<Float>(List(4) { 42.0f.toFloat() })
    check(d == F32x4.from(a1))
}

public fun testVecAbssI8() {
    val a = vecSplats(-127)
    val a1: VectorSignedChar = vecAbss(a)
    val d = Simd<Byte>(List(16) { 127 })
    check(d == I8x16.from(a1))
}

public fun testVecAbssI16() {
    val a = vecSplats(-42)
    val a1: VectorSignedShort = vecAbss(a)
    val d = Simd<Short>(List(8) { 42 })
    check(d == I16x8.from(a1))
}

public fun testVecAbssI32() {
    val a = vecSplats(-42)
    val a1: VectorSignedInt = vecAbss(a)
    val d = Simd<Int>(List(4) { 42 })
    check(d == I32x4.from(a1))
}

public fun testVecSplatsU8() {
    val a: VectorUnsignedChar = vecSplats(42u.toUByte())
    val d = Simd<UByte>(List(16) { 42u.toUByte() })
    check(d == U8x16.from(a))
}

public fun testVecSplatsU16() {
    val a: VectorUnsignedShort = vecSplats(42u.toUShort())
    val d = Simd<UShort>(List(8) { 42u.toUShort() })
    check(d == U16x8.from(a))
}

public fun testVecSplatsU32() {
    val a: VectorUnsignedInt = vecSplats(42u)
    val d = Simd<UInt>(List(4) { 42u })
    check(d == U32x4.from(a))
}

public fun testVecSplatsI8() {
    val a: VectorSignedChar = vecSplats(42)
    val d = Simd<Byte>(List(16) { 42 })
    check(d == I8x16.from(a))
}

public fun testVecSplatsI16() {
    val a: VectorSignedShort = vecSplats(42)
    val d = Simd<Short>(List(8) { 42 })
    check(d == I16x8.from(a))
}

public fun testVecSplatsI32() {
    val a: VectorSignedInt = vecSplats(42)
    val d = Simd<Int>(List(4) { 42 })
    check(d == I32x4.from(a))
}

public fun testVecSplatsF32() {
    val a: VectorFloat = vecSplats(42.0f)
    val d = Simd<Float>(List(4) { 42.0f.toFloat() })
    check(d == F32x4.from(a))
}

public fun testVecSplatU8() {
    val a = vecSplatU8(-1)
    val d = Simd<UByte>(List(16) { UByte.MAX_VALUE })
    check(d == U8x16.from(a))
}

public fun testVecSplatU16() {
    val a = vecSplatU16(-1)
    val d = Simd<UShort>(List(8) { UShort.MAX_VALUE })
    check(d == U16x8.from(a))
}

public fun testVecSplatU32() {
    val a = vecSplatU32(-1)
    val d = Simd<UInt>(List(4) { UInt.MAX_VALUE })
    check(d == U32x4.from(a))
}

public fun testVecSplatS8() {
    val a = vecSplatS8(-1)
    val d = Simd<Byte>(List(16) { (-1).toByte() })
    check(d == I8x16.from(a))
}

public fun testVecSplatS16() {
    val a = vecSplatS16(-1)
    val d = Simd<Short>(List(8) { (-1).toShort() })
    check(d == I16x8.from(a))
}

public fun testVecSplatS32() {
    val a = vecSplatS32(-1)
    val d = Simd<Int>(List(4) { -1 })
    check(d == I32x4.from(a))
}

public fun testVecSubF32x4() {
    val a: VectorFloat = Simd<Float>((listOf(-1.0, 0.0, 1.0, 2.0)).toList()).into()
    val b: VectorFloat = Simd<Float>((listOf(2.0, 1.0, -1.0, -2.0)).toList()).into()
    val d = Simd<Float>((listOf(-3.0, -1.0, 2.0, 4.0)).toList())
    val r = F32x4.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubI32x4() {
    val a: VectorSignedInt = Simd<Int>((listOf(-1, 0, 1, 2)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(2, 1, -1, -2)).toList()).into()
    val d = Simd<Int>((listOf(-3, -1, 2, 4)).toList())
    val r = I32x4.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubU32x4() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(0, 0, 1, 2)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(2, 1, 0, 0)).toList()).into()
    val d = Simd<UInt>((listOf(4294967294, 4294967295, 1, 2)).toList())
    val r = U32x4.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubI16x8() {
    val a: VectorSignedShort = Simd<Short>((listOf(-1, 0, 1, 2, -1, 0, 1, 2)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(2, 1, -1, -2, 2, 1, -1, -2)).toList()).into()
    val d = Simd<Short>((listOf(-3, -1, 2, 4, -3, -1, 2, 4)).toList())
    val r = I16x8.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubU16x8() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(0, 0, 1, 2, 0, 0, 1, 2)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(2, 1, 0, 0, 2, 1, 0, 0)).toList()).into()
    val d = Simd<UShort>((listOf(65534, 65535, 1, 2, 65534, 65535, 1, 2)).toList())
    val r = U16x8.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubI8x16() {
    val a: VectorSignedChar = Simd<Byte>((listOf(-1, 0, 1, 2, -1, 0, 1, 2, -1, 0, 1, 2, -1, 0, 1, 2)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(2, 1, -1, -2, 2, 1, -1, -2, 2, 1, -1, -2, 2, 1, -1, -2)).toList()).into()
    val d = Simd<Byte>((listOf(-3, -1, 2, 4, -3, -1, 2, 4, -3, -1, 2, 4, -3, -1, 2, 4)).toList())
    val r = I8x16.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubU8x16() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(0, 0, 1, 2, 0, 0, 1, 2, 0, 0, 1, 2, 0, 0, 1, 2)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(2, 1, 0, 0, 2, 1, 0, 0, 2, 1, 0, 0, 2, 1, 0, 0)).toList()).into()
    val d = Simd<UByte>((listOf(254, 255, 1, 2, 254, 255, 1, 2, 254, 255, 1, 2, 254, 255, 1, 2)).toList())
    val r = U8x16.from(vecSub(a, b))
    check(d == r)
}

public fun testVecSubsI32x4() {
    val a: VectorSignedInt = Simd<Int>((listOf(-1, 0, 1, 2)).toList()).into()
    val b: VectorSignedInt = Simd<Int>((listOf(2, 1, -1, -2)).toList()).into()
    val d = Simd<Int>((listOf(-3, -1, 2, 4)).toList())
    val r = I32x4.from(vecSubs(a, b))
    check(d == r)
}

public fun testVecSubsU32x4() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(0, 0, 1, 2)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(2, 1, 0, 0)).toList()).into()
    val d = Simd<UInt>((listOf(0, 0, 1, 2)).toList())
    val r = U32x4.from(vecSubs(a, b))
    check(d == r)
}

public fun testVecSubsI16x8() {
    val a: VectorSignedShort = Simd<Short>((listOf(-1, 0, 1, 2, -1, 0, 1, 2)).toList()).into()
    val b: VectorSignedShort = Simd<Short>((listOf(2, 1, -1, -2, 2, 1, -1, -2)).toList()).into()
    val d = Simd<Short>((listOf(-3, -1, 2, 4, -3, -1, 2, 4)).toList())
    val r = I16x8.from(vecSubs(a, b))
    check(d == r)
}

public fun testVecSubsU16x8() {
    val a: VectorUnsignedShort = Simd<UShort>((listOf(0, 0, 1, 2, 0, 0, 1, 2)).toList()).into()
    val b: VectorUnsignedShort = Simd<UShort>((listOf(2, 1, 0, 0, 2, 1, 0, 0)).toList()).into()
    val d = Simd<UShort>((listOf(0, 0, 1, 2, 0, 0, 1, 2)).toList())
    val r = U16x8.from(vecSubs(a, b))
    check(d == r)
}

public fun testVecSubsI8x16() {
    val a: VectorSignedChar = Simd<Byte>((listOf(-1, 0, 1, 2, -1, 0, 1, 2, -1, 0, 1, 2, -1, 0, 1, 2)).toList()).into()
    val b: VectorSignedChar = Simd<Byte>((listOf(2, 1, -1, -2, 2, 1, -1, -2, 2, 1, -1, -2, 2, 1, -1, -2)).toList()).into()
    val d = Simd<Byte>((listOf(-3, -1, 2, 4, -3, -1, 2, 4, -3, -1, 2, 4, -3, -1, 2, 4)).toList())
    val r = I8x16.from(vecSubs(a, b))
    check(d == r)
}

public fun testVecSubsU8x16() {
    val a: VectorUnsignedChar = Simd<UByte>((listOf(0, 0, 1, 2, 0, 0, 1, 2, 0, 0, 1, 2, 0, 0, 1, 2)).toList()).into()
    val b: VectorUnsignedChar = Simd<UByte>((listOf(2, 1, 0, 0, 2, 1, 0, 0, 2, 1, 0, 0, 2, 1, 0, 0)).toList()).into()
    val d = Simd<UByte>((listOf(0, 0, 1, 2, 0, 0, 1, 2, 0, 0, 1, 2, 0, 0, 1, 2)).toList())
    val r = U8x16.from(vecSubs(a, b))
    check(d == r)
}

public fun testVecMinI32x4() {
    val a: VectorSignedInt = Simd<Int>(listOf(-1, 0, 1, 2)).into()
    val b: VectorSignedInt = Simd<Int>(listOf(2, 1, -1, -2)).into()
    val d = Simd<Int>(listOf(-1, 0, -1, -2))
    val r = I32x4.from(vecMin(a, b))
    check(d == r)
}

public fun testVecMinU32x4() {
    val a: VectorUnsignedInt = Simd<UInt>(listOf(0u, 0u, 1u, 2u)).into()
    val b: VectorUnsignedInt = Simd<UInt>(listOf(2u, 1u, 0u, 0u)).into()
    val d = Simd<UInt>(listOf(0u, 0u, 0u, 0u))
    val r = U32x4.from(vecMin(a, b))
    check(d == r)
}

public fun testVecMinI16x8() {
    val a: VectorSignedShort = Simd<Short>(listOf((-1).toShort(), 0.toShort(), 1.toShort(), 2.toShort(), (-1).toShort(), 0.toShort(), 1.toShort(), 2.toShort())).into()
    val b: VectorSignedShort = Simd<Short>(listOf(2.toShort(), 1.toShort(), (-1).toShort(), (-2).toShort(), 2.toShort(), 1.toShort(), (-1).toShort(), (-2).toShort())).into()
    val d = Simd<Short>(listOf((-1).toShort(), 0.toShort(), (-1).toShort(), (-2).toShort(), (-1).toShort(), 0.toShort(), (-1).toShort(), (-2).toShort()))
    val r = I16x8.from(vecMin(a, b))
    check(d == r)
}

public fun testVecMinU16x8() {
    val a: VectorUnsignedShort = Simd<UShort>(listOf(0u.toUShort(), 0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 0u.toUShort(), 0u.toUShort(), 1u.toUShort(), 2u.toUShort())).into()
    val b: VectorUnsignedShort = Simd<UShort>(listOf(2u.toUShort(), 1u.toUShort(), 0u.toUShort(), 0u.toUShort(), 2u.toUShort(), 1u.toUShort(), 0u.toUShort(), 0u.toUShort())).into()
    val d = Simd<UShort>(listOf(0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort()))
    val r = U16x8.from(vecMin(a, b))
    check(d == r)
}

public fun testVecMinI8x16() {
    val a: VectorSignedChar = Simd<Byte>(listOf((-1).toByte(), 0.toByte(), 1.toByte(), 2.toByte(), (-1).toByte(), 0.toByte(), 1.toByte(), 2.toByte(), (-1).toByte(), 0.toByte(), 1.toByte(), 2.toByte(), (-1).toByte(), 0.toByte(), 1.toByte(), 2.toByte())).into()
    val b: VectorSignedChar = Simd<Byte>(listOf(2.toByte(), 1.toByte(), (-1).toByte(), (-2).toByte(), 2.toByte(), 1.toByte(), (-1).toByte(), (-2).toByte(), 2.toByte(), 1.toByte(), (-1).toByte(), (-2).toByte(), 2.toByte(), 1.toByte(), (-1).toByte(), (-2).toByte())).into()
    val d = Simd<Byte>(listOf((-1).toByte(), 0.toByte(), (-1).toByte(), (-2).toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), (-2).toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), (-2).toByte(), (-1).toByte(), 0.toByte(), (-1).toByte(), (-2).toByte()))
    val r = I8x16.from(vecMin(a, b))
    check(d == r)
}

public fun testVecMinU8x16() {
    val a: VectorUnsignedChar = Simd<UByte>(listOf(0u.toUByte(), 0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 0u.toUByte(), 0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 0u.toUByte(), 0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 0u.toUByte(), 0u.toUByte(), 1u.toUByte(), 2u.toUByte())).into()
    val b: VectorUnsignedChar = Simd<UByte>(listOf(2u.toUByte(), 1u.toUByte(), 0u.toUByte(), 0u.toUByte(), 2u.toUByte(), 1u.toUByte(), 0u.toUByte(), 0u.toUByte(), 2u.toUByte(), 1u.toUByte(), 0u.toUByte(), 0u.toUByte(), 2u.toUByte(), 1u.toUByte(), 0u.toUByte(), 0u.toUByte())).into()
    val d = Simd<UByte>(listOf(0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte(), 0u.toUByte()))
    val r = U8x16.from(vecMin(a, b))
    check(d == r)
}

public fun testVecMaxI32x4() {
    val a: VectorSignedInt = Simd<Int>(listOf(-1, 0, 1, 2)).into()
    val b: VectorSignedInt = Simd<Int>(listOf(2, 1, -1, -2)).into()
    val d = Simd<Int>(listOf(2, 1, 1, 2))
    val r = I32x4.from(vecMax(a, b))
    check(d == r)
}

public fun testVecMaxU32x4() {
    val a: VectorUnsignedInt = Simd<UInt>(listOf(0u, 0u, 1u, 2u)).into()
    val b: VectorUnsignedInt = Simd<UInt>(listOf(2u, 1u, 0u, 0u)).into()
    val d = Simd<UInt>(listOf(2u, 1u, 1u, 2u))
    val r = U32x4.from(vecMax(a, b))
    check(d == r)
}

public fun testVecMaxI16x8() {
    val a: VectorSignedShort = Simd<Short>(listOf((-1).toShort(), 0.toShort(), 1.toShort(), 2.toShort(), (-1).toShort(), 0.toShort(), 1.toShort(), 2.toShort())).into()
    val b: VectorSignedShort = Simd<Short>(listOf(2.toShort(), 1.toShort(), (-1).toShort(), (-2).toShort(), 2.toShort(), 1.toShort(), (-1).toShort(), (-2).toShort())).into()
    val d = Simd<Short>(listOf(2.toShort(), 1.toShort(), 1.toShort(), 2.toShort(), 2.toShort(), 1.toShort(), 1.toShort(), 2.toShort()))
    val r = I16x8.from(vecMax(a, b))
    check(d == r)
}

public fun testVecMaxU16x8() {
    val a: VectorUnsignedShort = Simd<UShort>(listOf(0u.toUShort(), 0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 0u.toUShort(), 0u.toUShort(), 1u.toUShort(), 2u.toUShort())).into()
    val b: VectorUnsignedShort = Simd<UShort>(listOf(2u.toUShort(), 1u.toUShort(), 0u.toUShort(), 0u.toUShort(), 2u.toUShort(), 1u.toUShort(), 0u.toUShort(), 0u.toUShort())).into()
    val d = Simd<UShort>(listOf(2u.toUShort(), 1u.toUShort(), 1u.toUShort(), 2u.toUShort(), 2u.toUShort(), 1u.toUShort(), 1u.toUShort(), 2u.toUShort()))
    val r = U16x8.from(vecMax(a, b))
    check(d == r)
}

public fun testVecMaxI8x16() {
    val a: VectorSignedChar = Simd<Byte>(listOf((-1).toByte(), 0.toByte(), 1.toByte(), 2.toByte(), (-1).toByte(), 0.toByte(), 1.toByte(), 2.toByte(), (-1).toByte(), 0.toByte(), 1.toByte(), 2.toByte(), (-1).toByte(), 0.toByte(), 1.toByte(), 2.toByte())).into()
    val b: VectorSignedChar = Simd<Byte>(listOf(2.toByte(), 1.toByte(), (-1).toByte(), (-2).toByte(), 2.toByte(), 1.toByte(), (-1).toByte(), (-2).toByte(), 2.toByte(), 1.toByte(), (-1).toByte(), (-2).toByte(), 2.toByte(), 1.toByte(), (-1).toByte(), (-2).toByte())).into()
    val d = Simd<Byte>(listOf(2.toByte(), 1.toByte(), 1.toByte(), 2.toByte(), 2.toByte(), 1.toByte(), 1.toByte(), 2.toByte(), 2.toByte(), 1.toByte(), 1.toByte(), 2.toByte(), 2.toByte(), 1.toByte(), 1.toByte(), 2.toByte()))
    val r = I8x16.from(vecMax(a, b))
    check(d == r)
}

public fun testVecMaxU8x16() {
    val a: VectorUnsignedChar = Simd<UByte>(listOf(0u.toUByte(), 0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 0u.toUByte(), 0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 0u.toUByte(), 0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 0u.toUByte(), 0u.toUByte(), 1u.toUByte(), 2u.toUByte())).into()
    val b: VectorUnsignedChar = Simd<UByte>(listOf(2u.toUByte(), 1u.toUByte(), 0u.toUByte(), 0u.toUByte(), 2u.toUByte(), 1u.toUByte(), 0u.toUByte(), 0u.toUByte(), 2u.toUByte(), 1u.toUByte(), 0u.toUByte(), 0u.toUByte(), 2u.toUByte(), 1u.toUByte(), 0u.toUByte(), 0u.toUByte())).into()
    val d = Simd<UByte>(listOf(2u.toUByte(), 1u.toUByte(), 1u.toUByte(), 2u.toUByte(), 2u.toUByte(), 1u.toUByte(), 1u.toUByte(), 2u.toUByte(), 2u.toUByte(), 1u.toUByte(), 1u.toUByte(), 2u.toUByte(), 2u.toUByte(), 1u.toUByte(), 1u.toUByte(), 2u.toUByte()))
    val r = U8x16.from(vecMax(a, b))
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

public fun testVecMadds() {
    val a = VectorSignedShort.from(Simd<Short>(listOf((0 * 256).toShort(), (1 * 256).toShort(), (2 * 256).toShort(), (3 * 256).toShort(), (4 * 256).toShort(), (5 * 256).toShort(), (6 * 256).toShort(), (7 * 256).toShort())))
    val b = VectorSignedShort.from(Simd<Short>(listOf(256.toShort(), 256.toShort(), 256.toShort(), 256.toShort(), 256.toShort(), 256.toShort(), 256.toShort(), 256.toShort())))
    val c = VectorSignedShort.from(Simd<Short>(listOf(0.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 7.toShort())))
    val d = Simd<Short>(listOf(0.toShort(), 3.toShort(), 6.toShort(), 9.toShort(), 12.toShort(), 15.toShort(), 18.toShort(), 21.toShort()))
    check(d == I16x8.from(vecMadds(a, b, c)))
}

public fun testVecMaddFloat() {
    val a = VectorFloat.from(Simd<Float>(listOf(0.1f.toFloat(), 0.2f.toFloat(), 0.3f.toFloat(), 0.4f.toFloat())))
    val b = VectorFloat.from(Simd<Float>(listOf(0.1f.toFloat(), 0.2f.toFloat(), 0.3f.toFloat(), 0.4f.toFloat())))
    val c = VectorFloat.from(Simd<Float>(listOf(0.1f.toFloat(), 0.2f.toFloat(), 0.3f.toFloat(), 0.4f.toFloat())))
    val d = Simd<Float>(listOf((0.1f * 0.1f + 0.1f).toFloat(), (0.2f * 0.2f + 0.2f).toFloat(), (0.3f * 0.3f + 0.3f).toFloat(), (0.4f * 0.4f + 0.4f).toFloat()))
    check(d == F32x4.from(vecMadd(a, b, c)))
}

public fun testVecNmsubFloat() {
    val a = VectorFloat.from(Simd<Float>(listOf(0.1f.toFloat(), 0.2f.toFloat(), 0.3f.toFloat(), 0.4f.toFloat())))
    val b = VectorFloat.from(Simd<Float>(listOf(0.1f.toFloat(), 0.2f.toFloat(), 0.3f.toFloat(), 0.4f.toFloat())))
    val c = VectorFloat.from(Simd<Float>(listOf(0.1f.toFloat(), 0.2f.toFloat(), 0.3f.toFloat(), 0.4f.toFloat())))
    val d = Simd<Float>(listOf((-(0.1f * 0.1f - 0.1f)).toFloat(), (-(0.2f * 0.2f - 0.2f)).toFloat(), (-(0.3f * 0.3f - 0.3f)).toFloat(), (-(0.4f * 0.4f - 0.4f)).toFloat()))
    check(d == F32x4.from(vecNmsub(a, b, c)))
}

public fun testVecMradds() {
    val a = VectorSignedShort.from(Simd<Short>(listOf((0 * 256).toShort(), (1 * 256).toShort(), (2 * 256).toShort(), (3 * 256).toShort(), (4 * 256).toShort(), (5 * 256).toShort(), (6 * 256).toShort(), (7 * 256).toShort())))
    val b = VectorSignedShort.from(Simd<Short>(listOf(256.toShort(), 256.toShort(), 256.toShort(), 256.toShort(), 256.toShort(), 256.toShort(), 256.toShort(), 256.toShort())))
    val c = VectorSignedShort.from(Simd<Short>(listOf(0.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), (Short.MAX_VALUE - 1).toShort())))
    val d = Simd<Short>(listOf(0.toShort(), 3.toShort(), 6.toShort(), 9.toShort(), 12.toShort(), 15.toShort(), 18.toShort(), Short.MAX_VALUE))
    check(d == I16x8.from(vecMradds(a, b, c)))
}

public fun testVecMladdU16x8U16x8() {
    val a = VectorUnsignedShort.from(Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 7u.toUShort())))
    val b = VectorUnsignedShort.from(Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 7u.toUShort())))
    val c = Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 7u.toUShort())).into()
    val d = Simd<UShort>(listOf(0u.toUShort(), 2u.toUShort(), 6u.toUShort(), 12u.toUShort(), 20u.toUShort(), 30u.toUShort(), 42u.toUShort(), 56u.toUShort()))
    check(d == U16x8.from(vecMladd(a, b, c)))
}

public fun testVecMladdU16x8I16x8() {
    val a = VectorUnsignedShort.from(Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 7u.toUShort())))
    val b = VectorSignedShort.from(Simd<Short>(listOf(0.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 7.toShort())))
    val c = Simd<Short>(listOf(0.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 7.toShort())).into()
    val d = Simd<Short>(listOf(0.toShort(), 2.toShort(), 6.toShort(), 12.toShort(), 20.toShort(), 30.toShort(), 42.toShort(), 56.toShort()))
    check(d == I16x8.from(vecMladd(a, b, c)))
}

public fun testVecMladdI16x8U16x8() {
    val a = VectorSignedShort.from(Simd<Short>(listOf(0.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 7.toShort())))
    val b = VectorUnsignedShort.from(Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 7u.toUShort())))
    val c = Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 7u.toUShort())).into()
    val d = Simd<Short>(listOf(0.toShort(), 2.toShort(), 6.toShort(), 12.toShort(), 20.toShort(), 30.toShort(), 42.toShort(), 56.toShort()))
    check(d == I16x8.from(vecMladd(a, b, c)))
}

public fun testVecMladdI16x8I16x8() {
    val a = VectorSignedShort.from(Simd<Short>(listOf(0.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 7.toShort())))
    val b = VectorSignedShort.from(Simd<Short>(listOf(0.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 7.toShort())))
    val c = Simd<Short>(listOf(0.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 7.toShort())).into()
    val d = Simd<Short>(listOf(0.toShort(), 2.toShort(), 6.toShort(), 12.toShort(), 20.toShort(), 30.toShort(), 42.toShort(), 56.toShort()))
    check(d == I16x8.from(vecMladd(a, b, c)))
}

public fun testVecMsumUnsignedChar() {
    val a = VectorUnsignedChar.from(Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte())))
    val b = VectorUnsignedChar.from(Simd<UByte>(listOf(255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte(), 255u.toUByte())))
    val c = VectorUnsignedInt.from(Simd<UInt>(listOf(0u, 1u, 2u, 3u)))
    val d = Simd<UInt>(listOf((0u + 1u + 2u + 3u) * 255u + 0u, (4u + 5u + 6u + 7u) * 255u + 1u, (0u + 1u + 2u + 3u) * 255u + 2u, (4u + 5u + 6u + 7u) * 255u + 3u))
    check(d == U32x4.from(vecMsum(a, b, c)))
}

public fun testVecMsumSignedChar() {
    val a = VectorSignedChar.from(Simd<Byte>(listOf(0.toByte(), (-1).toByte(), 2.toByte(), (-3).toByte(), 1.toByte(), (-1).toByte(), 1.toByte(), (-1).toByte(), 0.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), (-5).toByte(), (-6).toByte(), (-7).toByte())))
    val b = VectorUnsignedChar.from(Simd<UByte>(listOf(1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte())))
    val c = VectorSignedInt.from(Simd<Int>(listOf(0, 1, 2, 3)))
    val d = Simd<Int>(listOf(((0 - 1 + 2 - 3) + 0).toInt(), ((0) + 1).toInt(), ((0 + 1 + 2 + 3) + 2).toInt(), ((4 - 5 - 6 - 7) + 3).toInt()))
    check(d == I32x4.from(vecMsum(a, b, c)))
}

public fun testVecMsumUnsignedShort() {
    val a = VectorUnsignedShort.from(Simd<UShort>(listOf((0u * 256u).toUShort(), (1u * 256u).toUShort(), (2u * 256u).toUShort(), (3u * 256u).toUShort(), (4u * 256u).toUShort(), (5u * 256u).toUShort(), (6u * 256u).toUShort(), (7u * 256u).toUShort())))
    val b = VectorUnsignedShort.from(Simd<UShort>(listOf(256u.toUShort(), 256u.toUShort(), 256u.toUShort(), 256u.toUShort(), 256u.toUShort(), 256u.toUShort(), 256u.toUShort(), 256u.toUShort())))
    val c = VectorUnsignedInt.from(Simd<UInt>(listOf(0u, 1u, 2u, 3u)))
    val d = Simd<UInt>(listOf((0u + 1u) * 256u * 256u + 0u, (2u + 3u) * 256u * 256u + 1u, (4u + 5u) * 256u * 256u + 2u, (6u + 7u) * 256u * 256u + 3u))
    check(d == U32x4.from(vecMsum(a, b, c)))
}

public fun testVecMsumSignedShort() {
    val a = VectorSignedShort.from(Simd<Short>(listOf((0 * 256).toShort(), (-1 * 256).toShort(), (2 * 256).toShort(), (-3 * 256).toShort(), (4 * 256).toShort(), (-5 * 256).toShort(), (6 * 256).toShort(), (-7 * 256).toShort())))
    val b = VectorSignedShort.from(Simd<Short>(listOf(256.toShort(), 256.toShort(), 256.toShort(), 256.toShort(), 256.toShort(), 256.toShort(), 256.toShort(), 256.toShort())))
    val c = VectorSignedInt.from(Simd<Int>(listOf(0, 1, 2, 3)))
    val d = Simd<Int>(listOf(((0 - 1) * 256 * 256 + 0).toInt(), ((2 - 3) * 256 * 256 + 1).toInt(), ((4 - 5) * 256 * 256 + 2).toInt(), ((6 - 7) * 256 * 256 + 3).toInt()))
    check(d == I32x4.from(vecMsum(a, b, c)))
}

public fun testVecMsumsUnsigned() {
    val a = VectorUnsignedShort.from(Simd<UShort>(listOf((0u * 256u).toUShort(), (1u * 256u).toUShort(), (2u * 256u).toUShort(), (3u * 256u).toUShort(), (4u * 256u).toUShort(), (5u * 256u).toUShort(), (6u * 256u).toUShort(), (7u * 256u).toUShort())))
    val b = VectorUnsignedShort.from(Simd<UShort>(listOf(256u.toUShort(), 256u.toUShort(), 256u.toUShort(), 256u.toUShort(), 256u.toUShort(), 256u.toUShort(), 256u.toUShort(), 256u.toUShort())))
    val c = VectorUnsignedInt.from(Simd<UInt>(listOf(0u, 1u, 2u, 3u)))
    val d = Simd<UInt>(listOf((0u + 1u) * 256u * 256u + 0u, (2u + 3u) * 256u * 256u + 1u, (4u + 5u) * 256u * 256u + 2u, (6u + 7u) * 256u * 256u + 3u))
    check(d == U32x4.from(vecMsums(a, b, c)))
}

public fun testVecMsumsSigned() {
    val a = VectorSignedShort.from(Simd<Short>(listOf((0 * 256).toShort(), (-1 * 256).toShort(), (2 * 256).toShort(), (-3 * 256).toShort(), (4 * 256).toShort(), (-5 * 256).toShort(), (6 * 256).toShort(), (-7 * 256).toShort())))
    val b = VectorSignedShort.from(Simd<Short>(listOf(256.toShort(), 256.toShort(), 256.toShort(), 256.toShort(), 256.toShort(), 256.toShort(), 256.toShort(), 256.toShort())))
    val c = VectorSignedInt.from(Simd<Int>(listOf(0, 1, 2, 3)))
    val d = Simd<Int>(listOf(((0 - 1) * 256 * 256 + 0).toInt(), ((2 - 3) * 256 * 256 + 1).toInt(), ((4 - 5) * 256 * 256 + 2).toInt(), ((6 - 7) * 256 * 256 + 3).toInt()))
    check(d == I32x4.from(vecMsums(a, b, c)))
}

public fun testVecSum2s() {
    val a = VectorSignedInt.from(Simd<Int>(listOf(0, 1, 2, 3)))
    val b = VectorSignedInt.from(Simd<Int>(listOf(0, 1, 2, 3)))
    val d = Simd<Int>(listOf(0, (0 + 1 + 1).toInt(), 0, (2 + 3 + 3).toInt()))
    check(d == I32x4.from(vecSum2s(a, b)))
}

public fun testVecSum4sUnsignedChar() {
    val a = VectorUnsignedChar.from(Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte())))
    val b = VectorUnsignedInt.from(Simd<UInt>(listOf(0u, 1u, 2u, 3u)))
    val d = Simd<UInt>(listOf(0u + 1u + 2u + 3u + 0u, 4u + 5u + 6u + 7u + 1u, 0u + 1u + 2u + 3u + 2u, 4u + 5u + 6u + 7u + 3u))
    check(d == U32x4.from(vecSum4s(a, b)))
}

public fun testVecSum4sSignedChar() {
    val a = VectorSignedChar.from(Simd<Byte>(listOf(0.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 0.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte())))
    val b = VectorSignedInt.from(Simd<Int>(listOf(0, 1, 2, 3)))
    val d = Simd<Int>(listOf((0 + 1 + 2 + 3 + 0).toInt(), (4 + 5 + 6 + 7 + 1).toInt(), (0 + 1 + 2 + 3 + 2).toInt(), (4 + 5 + 6 + 7 + 3).toInt()))
    check(d == I32x4.from(vecSum4s(a, b)))
}

public fun testVecSum4sSignedShort() {
    val a = VectorSignedShort.from(Simd<Short>(listOf(0.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 7.toShort())))
    val b = VectorSignedInt.from(Simd<Int>(listOf(0, 1, 2, 3)))
    val d = Simd<Int>(listOf((0 + 1 + 0).toInt(), (2 + 3 + 1).toInt(), (4 + 5 + 2).toInt(), (6 + 7 + 3).toInt()))
    check(d == I32x4.from(vecSum4s(a, b)))
}

public fun testVecMuleUnsignedChar() {
    val a = VectorUnsignedChar.from(Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte())))
    val d = Simd<UShort>(listOf((0u * 0u).toUShort(), (2u * 2u).toUShort(), (4u * 4u).toUShort(), (6u * 6u).toUShort(), (0u * 0u).toUShort(), (2u * 2u).toUShort(), (4u * 4u).toUShort(), (6u * 6u).toUShort()))
    check(d == U16x8.from(vecMule(a, a)))
}

public fun testVecMuleSignedChar() {
    val a = VectorSignedChar.from(Simd<Byte>(listOf(0.toByte(), 1.toByte(), (-2).toByte(), 3.toByte(), (-4).toByte(), 5.toByte(), (-6).toByte(), 7.toByte(), 0.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte())))
    val d = Simd<Short>(listOf((0 * 0).toShort(), (2 * 2).toShort(), (4 * 4).toShort(), (6 * 6).toShort(), (0 * 0).toShort(), (2 * 2).toShort(), (4 * 4).toShort(), (6 * 6).toShort()))
    check(d == I16x8.from(vecMule(a, a)))
}

public fun testVecMuleUnsignedShort() {
    val a = VectorUnsignedShort.from(Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 7u.toUShort())))
    val d = Simd<UInt>(listOf(0u * 0u, 2u * 2u, 4u * 4u, 6u * 6u))
    check(d == U32x4.from(vecMule(a, a)))
}

public fun testVecMuleSignedShort() {
    val a = VectorSignedShort.from(Simd<Short>(listOf(0.toShort(), 1.toShort(), (-2).toShort(), 3.toShort(), (-4).toShort(), 5.toShort(), (-6).toShort(), 7.toShort())))
    val d = Simd<Int>(listOf((0 * 0).toInt(), (2 * 2).toInt(), (4 * 4).toInt(), (6 * 6).toInt()))
    check(d == I32x4.from(vecMule(a, a)))
}

public fun testVecMuloUnsignedChar() {
    val a = VectorUnsignedChar.from(Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte())))
    val d = Simd<UShort>(listOf((1u * 1u).toUShort(), (3u * 3u).toUShort(), (5u * 5u).toUShort(), (7u * 7u).toUShort(), (1u * 1u).toUShort(), (3u * 3u).toUShort(), (5u * 5u).toUShort(), (7u * 7u).toUShort()))
    check(d == U16x8.from(vecMulo(a, a)))
}

public fun testVecMuloSignedChar() {
    val a = VectorSignedChar.from(Simd<Byte>(listOf(0.toByte(), 1.toByte(), (-2).toByte(), 3.toByte(), (-4).toByte(), 5.toByte(), (-6).toByte(), 7.toByte(), 0.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte())))
    val d = Simd<Short>(listOf((1 * 1).toShort(), (3 * 3).toShort(), (5 * 5).toShort(), (7 * 7).toShort(), (1 * 1).toShort(), (3 * 3).toShort(), (5 * 5).toShort(), (7 * 7).toShort()))
    check(d == I16x8.from(vecMulo(a, a)))
}

public fun testVecMuloUnsignedShort() {
    val a = VectorUnsignedShort.from(Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 7u.toUShort())))
    val d = Simd<UInt>(listOf(1u * 1u, 3u * 3u, 5u * 5u, 7u * 7u))
    check(d == U32x4.from(vecMulo(a, a)))
}

public fun testVecMuloSignedShort() {
    val a = VectorSignedShort.from(Simd<Short>(listOf(0.toShort(), 1.toShort(), (-2).toShort(), 3.toShort(), (-4).toShort(), 5.toShort(), (-6).toShort(), 7.toShort())))
    val d = Simd<Int>(listOf((1 * 1).toInt(), (3 * 3).toInt(), (5 * 5).toInt(), (7 * 7).toInt()))
    check(d == I32x4.from(vecMulo(a, a)))
}

public fun vecAddI32x4I32x4() {
    val x = Simd<Int>(listOf(1, 2, 3, 4))
    val y = Simd<Int>(listOf(4, 3, 2, 1))
    val x1 = VectorSignedInt.from(x)
    val y1 = VectorSignedInt.from(y)
    val z = vecAdd(x1, y1)
    check(Simd<Int>(List(4) { 5 }) == I32x4.from(z))
}

public fun vecCtfU32() {
    val v = VectorUnsignedInt.from(Simd<UInt>(listOf(UInt.MIN_VALUE, UInt.MAX_VALUE, UInt.MAX_VALUE, 42u)))
    val v2 = vecCtf(v, 1)
    val r2 = VectorFloat.from(Simd<Float>(listOf(0.0f.toFloat(), 2147483600.0f.toFloat(), 2147483600.0f.toFloat(), 21.0f.toFloat())))
    val v4 = vecCtf(v, 2)
    val r4 = VectorFloat.from(Simd<Float>(listOf(0.0f.toFloat(), 1073741800.0f.toFloat(), 1073741800.0f.toFloat(), 10.5f.toFloat())))
    val v8 = vecCtf(v, 3)
    val r8 = VectorFloat.from(Simd<Float>(listOf(0.0f.toFloat(), 536870900.0f.toFloat(), 536870900.0f.toFloat(), 5.25f.toFloat())))
    val check =
        { a, b ->
            val r = M32x4.from(vecCmple(vecAbs(vecSub(a, b)), vecSplats(1.1920929E-7f)))
            val e = SimdM<Int>(List(4) { if (true) -1 else 0 })
            check(e == r)
        }
    check(v2, r2)
    check(v4, r4)
    check(v8, r8)
}

public fun testVecCtu() {
    val v = Simd<UInt>(listOf(UInt.MIN_VALUE, UInt.MAX_VALUE, UInt.MAX_VALUE, 42u))
    val v2 = U32x4.from(vecCtu(VectorFloat.from(Simd<Float>(listOf(0.0f.toFloat(), 2147483600.0f.toFloat(), 2147483600.0f.toFloat(), 21.0f.toFloat()))), 1))
    val v4 = U32x4.from(vecCtu(VectorFloat.from(Simd<Float>(listOf(0.0f.toFloat(), 1073741800.0f.toFloat(), 1073741800.0f.toFloat(), 10.5f.toFloat()))), 2))
    val v8 = U32x4.from(vecCtu(VectorFloat.from(Simd<Float>(listOf(0.0f.toFloat(), 536870900.0f.toFloat(), 536870900.0f.toFloat(), 5.25f.toFloat()))), 3))
    check(v2 == v)
    check(v4 == v)
    check(v8 == v)
}

public fun vecCtfI32() {
    val v = VectorSignedInt.from(Simd<Int>(listOf(Int.MIN_VALUE, Int.MAX_VALUE, Int.MAX_VALUE - 42, 42)))
    val v2 = vecCtf(v, 1)
    val r2 = VectorFloat.from(Simd<Float>(listOf((-1073741800.0f).toFloat(), 1073741800.0f.toFloat(), 1073741800.0f.toFloat(), 21.0f.toFloat())))
    val v4 = vecCtf(v, 2)
    val r4 = VectorFloat.from(Simd<Float>(listOf((-536870900.0f).toFloat(), 536870900.0f.toFloat(), 536870900.0f.toFloat(), 10.5f.toFloat())))
    val v8 = vecCtf(v, 3)
    val r8 = VectorFloat.from(Simd<Float>(listOf((-268435460.0f).toFloat(), 268435460.0f.toFloat(), 268435460.0f.toFloat(), 5.25f.toFloat())))
    val check =
        { a, b ->
            val r = M32x4.from(vecCmple(vecAbs(vecSub(a, b)), vecSplats(1.1920929E-7f)))
            println("$a $b")
            val e = SimdM<Int>(List(4) { if (true) -1 else 0 })
            check(e == r)
        }
    check(v2, r2)
    check(v4, r4)
    check(v8, r8)
}

public fun testVecCts() {
    val v = Simd<Int>(listOf(Int.MIN_VALUE, Int.MAX_VALUE, Int.MAX_VALUE, 42))
    val v2 = I32x4.from(vecCts(transmuteWithLayouts<Any?, VectorFloat>(Simd<Float>(listOf((-1073741800.0f).toFloat(), 1073741800.0f.toFloat(), 1073741800.0f.toFloat(), 21.0f.toFloat())), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorFloat>()), 1))
    val v4 = I32x4.from(vecCts(transmuteWithLayouts<Any?, VectorFloat>(Simd<Float>(listOf((-536870900.0f).toFloat(), 536870900.0f.toFloat(), 536870900.0f.toFloat(), 10.5f.toFloat())), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorFloat>()), 2))
    val v8 = I32x4.from(vecCts(transmuteWithLayouts<Any?, VectorFloat>(Simd<Float>(listOf((-268435460.0f).toFloat(), 268435460.0f.toFloat(), 268435460.0f.toFloat(), 5.25f.toFloat())), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorFloat>()), 3))
    check(v2 == v)
    check(v4 == v)
    check(v8 == v)
}

public fun testVecRl() {
    val a: VectorUnsignedInt = Simd<UInt>((listOf(0x12345678, 0x9ABCDEF0, 0x0F0F0F0F, 0x12345678)).toList()).into()
    val b: VectorUnsignedInt = Simd<UInt>((listOf(4, 8, 12, 68)).toList()).into()
    val d = Simd<UInt>((listOf(0x23456781, 0xBCDEF09A, 0xF0F0F0F0, 0x23456781)).toList())
    val r = U32x4.from(vecRl(a, b))
    check(d == r)
}
