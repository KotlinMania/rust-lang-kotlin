// port-lint: source loongarch64/simd.rs
package io.github.kotlinmania.rustlang.corearch.loongarch64.simd

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.mem.transmute
import io.github.kotlinmania.rustlang.corearch.simd.I128x2
import io.github.kotlinmania.rustlang.corearch.simd.I128x4
import io.github.kotlinmania.rustlang.corearch.simd.I16x16
import io.github.kotlinmania.rustlang.corearch.simd.I16x32
import io.github.kotlinmania.rustlang.corearch.simd.I16x4
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x16
import io.github.kotlinmania.rustlang.corearch.simd.I32x2
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I32x8
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I64x4
import io.github.kotlinmania.rustlang.corearch.simd.I64x8
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.I8x32
import io.github.kotlinmania.rustlang.corearch.simd.I8x8
import io.github.kotlinmania.rustlang.corearch.simd.U128x2
import io.github.kotlinmania.rustlang.corearch.simd.U128x4
import io.github.kotlinmania.rustlang.corearch.simd.U16x16
import io.github.kotlinmania.rustlang.corearch.simd.U16x32
import io.github.kotlinmania.rustlang.corearch.simd.U16x8
import io.github.kotlinmania.rustlang.corearch.simd.U32x16
import io.github.kotlinmania.rustlang.corearch.simd.U32x4
import io.github.kotlinmania.rustlang.corearch.simd.U32x8
import io.github.kotlinmania.rustlang.corearch.simd.U64x2
import io.github.kotlinmania.rustlang.corearch.simd.U64x4
import io.github.kotlinmania.rustlang.corearch.simd.U64x8
import io.github.kotlinmania.rustlang.corearch.simd.U8x16
import io.github.kotlinmania.rustlang.corearch.simd.U8x32

// LoongArch64 SIMD helpers

// Internal extension trait for concrete `Simd<T, N>` types.
//
// Provides a small set of helper functionality (`Elem` and `splat`)
// so generic and macro-based code can operate on different SIMD
// vector types in a uniform way.
internal interface SimdExt<T, out Elem> {
    public fun splat(v: Long): T
}

public fun simdExtSplatI8x16(v: Long): I8x16 = simdSplat(v.toByte())

public fun simdExtSplatI8x32(v: Long): I8x32 = simdSplat(v.toByte())

public fun simdExtSplatU8x16(v: Long): U8x16 = simdSplat(v.toUByte())

public fun simdExtSplatU8x32(v: Long): U8x32 = simdSplat(v.toUByte())

public fun simdExtSplatI16x8(v: Long): I16x8 = simdSplat(v.toShort())

public fun simdExtSplatI16x16(v: Long): I16x16 = simdSplat(v.toShort())

public fun simdExtSplatI16x32(v: Long): I16x32 = simdSplat(v.toShort())

public fun simdExtSplatU16x8(v: Long): U16x8 = simdSplat(v.toUShort())

public fun simdExtSplatU16x16(v: Long): U16x16 = simdSplat(v.toUShort())

public fun simdExtSplatU16x32(v: Long): U16x32 = simdSplat(v.toUShort())

public fun simdExtSplatI32x4(v: Long): I32x4 = simdSplat(v.toInt())

public fun simdExtSplatI32x8(v: Long): I32x8 = simdSplat(v.toInt())

public fun simdExtSplatI32x16(v: Long): I32x16 = simdSplat(v.toInt())

public fun simdExtSplatU32x4(v: Long): U32x4 = simdSplat(v.toUInt())

public fun simdExtSplatU32x8(v: Long): U32x8 = simdSplat(v.toUInt())

public fun simdExtSplatU32x16(v: Long): U32x16 = simdSplat(v.toUInt())

public fun simdExtSplatI64x2(v: Long): I64x2 = simdSplat(v)

public fun simdExtSplatI64x4(v: Long): I64x4 = simdSplat(v)

public fun simdExtSplatI64x8(v: Long): I64x8 = simdSplat(v)

public fun simdExtSplatU64x2(v: Long): U64x2 = simdSplat(v.toULong())

public fun simdExtSplatU64x4(v: Long): U64x4 = simdSplat(v.toULong())

public fun simdExtSplatU64x8(v: Long): U64x8 = simdSplat(v.toULong())

public fun simdExtSplatI128x2(v: Long): I128x2 = simdSplat(I128.fromLong(v))

public fun simdExtSplatU128x2(v: Long): U128x2 = simdSplat(U128.fromLong(v))

public fun simdExtSplatI128x4(v: Long): I128x4 = simdSplat(I128.fromLong(v))

public fun simdExtSplatU128x4(v: Long): U128x4 = simdSplat(U128.fromLong(v))

internal fun <Elem, T : SimdExt<T, Any?>> simdExtAbs(a: T): T {
    val m: T = simdLt(a, simdExtSplat(0L))
    return simdSelect(m, simdNeg(a), a)
}

internal fun <T> simdExtAbsd(a: T, b: T): T {
    val m: T = simdGt(a, b)
    return simdSelect(m, simdSub(a, b), simdSub(b, a))
}

internal fun <Elem, T : SimdExt<T, Any?>> simdExtAdda(a: T, b: T): T = simdAdd(simdExtAbs(a), simdExtAbs(b))

internal fun <Elem, T : SimdExt<T, Any?>> simdExtAndn(a: T, b: T): T = simdAnd(simdExtNot(a), b)

internal fun <Elem, T : SimdExt<T, Any?>> simdExtBitclr(a: T, b: T): T = simdExtAndn(simdExtShl(simdExtSplat(1L), b), a)

internal fun <Elem, T : SimdExt<T, Any?>> simdExtBitrev(a: T, b: T): T = simdXor(simdExtShl(simdExtSplat(1L), b), a)

internal fun <Elem, T : SimdExt<T, Any?>> simdExtBitset(a: T, b: T): T = simdOr(simdExtShl(simdExtSplat(1L), b), a)

internal fun <T> simdExtFmsub(a: T, b: T, c: T): T = simdFma(a, b, simdNeg(c))

internal fun <T> simdExtFnmadd(a: T, b: T, c: T): T = simdNeg(simdFma(a, b, c))

internal fun <T> simdExtFnmsub(a: T, b: T, c: T): T = simdNeg(simdExtFmsub(a, b, c))

internal fun <T> simdExtFrecipS(a: T): T = simdDiv(simdSplat(1.0f), a)

internal fun <T> simdExtFrecipD(a: T): T = simdDiv(simdSplat(1.0), a)

internal fun <T> simdExtFrsqrtS(a: T): T = simdExtFrecipS(simdFsqrt(a))

internal fun <T> simdExtFrsqrtD(a: T): T = simdExtFrecipD(simdFsqrt(a))

internal inline fun <reified T> simdExtLd(a: ConstPtr, i: Int): T {
    val a1 = (ConstPtr((a).rawAddress + ((i).toLong() * (PrimitiveLayouts.I8).strideBytes))).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    return readUnaligned(a1, typeLayoutT = ObjectSlotLayout<T>())
}

internal fun <Elem, T : SimdExt<T, Any?>> simdExtLdi(i: Int): T {
    fun sext(v: Int, bits: UInt): Long = ((v.toLong()) shl (64u - bits).toInt()) shr (64u - bits).toInt()
    fun expandU8(imm: Int): ULong {
        var v = imm.toULong()
        v = (v or (v shl 28)) and 0x0000000f0000000fuL
        v = (v or (v shl 14)) and 0x0003000300030003uL
        v = (v or (v shl 7)) and 0x0101010101010101uL
        return (v * 0xffuL)
    }
    fun fp32Imm(imm: Int): Int = ((imm and 0x80) shl 24) or ((imm.inv() and 0x40) shl 24) or ((((imm and 0x40) shr 6) * 0x1f) shl 25) or ((imm and 0x3f) shl 19)
    fun fp64Imm(imm: Int): Long = ((((imm and 0x80) shl 24) or ((imm.inv() and 0x40) shl 24) or ((((imm and 0x40) shr 6) * 0xff) shl 22) or ((imm and 0x3f) shl 16)).toLong()) shl 32
    val imm8 = i and 0xff
    val imm10 = i and 0x3ff
    val r =
        if ((i and 0x1000) == 0) {
            when ((i shr 10) and 3) {
                0 -> transmute(I8x8.splat(imm8.toByte()))
                1 -> transmute(I16x4.splat(sext(imm10, 10u).toShort()))
                2 -> transmute(I32x2.splat(sext(imm10, 10u).toInt()))
                3 -> sext(imm10, 10u)
                else -> error("unreachable")
            }
        } else {
            when ((i shr 8) and 0xf) {
                in 0..3 -> { transmute(I32x2.splat(imm8 shl (8 * ((i shr 8) and 3)))) }
                in 4..5 -> transmute(I16x4.splat((imm8.toShort()) shl (8 * ((i shr 8) and 1))))
                6 -> transmute(I32x2.splat((imm8 shl 8) or 0xff))
                7 -> transmute(I32x2.splat((imm8 shl 16) or 0xffff))
                8 -> transmute(I8x8.splat(imm8.toByte()))
                9 -> expandU8(imm8).toLong()
                10 -> transmute(I32x2.splat(fp32Imm(i)))
                11 -> transmute(I32x2.fromArray(listOf(fp32Imm(i), 0)))
                12 -> fp64Imm(i)
                else -> error("unreachable")
            }
        }
    return simdExtSplat(r)
}

internal inline fun <reified T> simdExtLdx(a: ConstPtr, b: Long): T {
    val a1 = (ConstPtr((a).rawAddress + ((b.toInt()).toLong() * (PrimitiveLayouts.I8).strideBytes))).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    return readUnaligned(a1, typeLayoutT = ObjectSlotLayout<T>())
}

internal fun <T> simdExtMadd(a: T, b: T, c: T): T = simdAdd(a, simdMul(b, c))

internal fun <T> simdExtMsub(a: T, b: T, c: T): T = simdSub(a, simdMul(b, c))

internal fun <Elem, T, W : SimdExt<W, Any?>> simdExtMuh(a: T, b: T): T {
    val a1: W = simdCast(a)
    val b1: W = simdCast(b)
    val p = simdMul(a1, b1)
    return simdCast(simdShr(p, simdExtSplat((sizeOf<Elem>(typeLayoutT = ObjectSlotLayout<Elem>()) * 8 / 2).toLong())))
}

internal fun <Elem, T : SimdExt<T, Any?>> simdExtNor(a: T, b: T): T = simdExtNot(simdOr(a, b))

internal fun <Elem, T : SimdExt<T, Any?>> simdExtNot(a: T): T = simdXor(a, simdExtSplat(0L.inv()))

internal fun <Elem, T : SimdExt<T, Any?>> simdExtOrn(a: T, b: T): T = simdOr(a, simdExtNot(b))

internal fun <Elem, T : SimdExt<T, Any?>> simdExtRotr(a: T, b: T): T {
    val m = (sizeOf<Elem>(typeLayoutT = ObjectSlotLayout<Elem>()) * 8 - 1).toLong()
    val r = simdAnd(b, simdExtSplat(m))
    val l = simdAnd(simdSub(simdExtSplat(m + 1L), r), simdExtSplat(m))
    return simdOr(simdShr(a, r), simdShl(a, l))
}

internal fun <Elem, T : SimdExt<T, Any?>> simdExtShl(a: T, b: T): T {
    val m = (sizeOf<Elem>(typeLayoutT = ObjectSlotLayout<Elem>()) * 8 - 1).toLong()
    return simdShl(a, simdAnd(b, simdExtSplat(m)))
}

internal fun <Elem, T : SimdExt<T, Any?>> simdExtShr(a: T, b: T): T {
    val m = (sizeOf<Elem>(typeLayoutT = ObjectSlotLayout<Elem>()) * 8 - 1).toLong()
    return simdShr(a, simdAnd(b, simdExtSplat(m)))
}

internal fun <Elem, T : SimdExt<T, Any?>> simdExtSplat(a: Long, splatTFn: (Long) -> T): T = splatTFn(a)

internal fun <T> simdExtSt(a: T, b: MutPtr, i: Int) {
    val b1 = (MutPtr((b).rawAddress + ((i).toLong() * (PrimitiveLayouts.I8).strideBytes))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    writeUnaligned(b1, a, typeLayoutT = ObjectSlotLayout<T>())
}

internal fun <T> simdExtStx(a: T, b: MutPtr, c: Long) {
    val b1 = (MutPtr((b).rawAddress + ((c.toInt()).toLong() * (PrimitiveLayouts.I8).strideBytes))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    writeUnaligned(b1, a, typeLayoutT = ObjectSlotLayout<T>())
}
