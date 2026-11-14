// port-lint: source loongarch64/simd.rs
package io.github.kotlinmania.rustlang.corearch.loongarch64.simd

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
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
public interface SimdExt<T, out Elem> {
    public fun splat(v: Long): T
}

public fun simdExtSplatI8x16(v: Long): I8x16 = run { val _simdArg0 = v.toByte(); _simdArg0 }

public fun simdExtSplatI8x32(v: Long): I8x32 = run { val _simdArg0 = v.toByte(); _simdArg0 }

public fun simdExtSplatU8x16(v: Long): U8x16 = run { val _simdArg0 = v.toUByte(); _simdArg0 }

public fun simdExtSplatU8x32(v: Long): U8x32 = run { val _simdArg0 = v.toUByte(); _simdArg0 }

public fun simdExtSplatI16x8(v: Long): I16x8 = run { val _simdArg0 = v.toShort(); _simdArg0 }

public fun simdExtSplatI16x16(v: Long): I16x16 = run { val _simdArg0 = v.toShort(); _simdArg0 }

public fun simdExtSplatI16x32(v: Long): I16x32 = run { val _simdArg0 = v.toShort(); _simdArg0 }

public fun simdExtSplatU16x8(v: Long): U16x8 = run { val _simdArg0 = v.toUShort(); _simdArg0 }

public fun simdExtSplatU16x16(v: Long): U16x16 = run { val _simdArg0 = v.toUShort(); _simdArg0 }

public fun simdExtSplatU16x32(v: Long): U16x32 = run { val _simdArg0 = v.toUShort(); _simdArg0 }

public fun simdExtSplatI32x4(v: Long): I32x4 = run { val _simdArg0 = v.toInt(); _simdArg0 }

public fun simdExtSplatI32x8(v: Long): I32x8 = run { val _simdArg0 = v.toInt(); _simdArg0 }

public fun simdExtSplatI32x16(v: Long): I32x16 = run { val _simdArg0 = v.toInt(); _simdArg0 }

public fun simdExtSplatU32x4(v: Long): U32x4 = run { val _simdArg0 = v.toUInt(); _simdArg0 }

public fun simdExtSplatU32x8(v: Long): U32x8 = run { val _simdArg0 = v.toUInt(); _simdArg0 }

public fun simdExtSplatU32x16(v: Long): U32x16 = run { val _simdArg0 = v.toUInt(); _simdArg0 }

public fun simdExtSplatI64x2(v: Long): I64x2 = run { val _simdArg0 = v; _simdArg0 }

public fun simdExtSplatI64x4(v: Long): I64x4 = run { val _simdArg0 = v; _simdArg0 }

public fun simdExtSplatI64x8(v: Long): I64x8 = run { val _simdArg0 = v; _simdArg0 }

public fun simdExtSplatU64x2(v: Long): U64x2 = run { val _simdArg0 = v.toULong(); _simdArg0 }

public fun simdExtSplatU64x4(v: Long): U64x4 = run { val _simdArg0 = v.toULong(); _simdArg0 }

public fun simdExtSplatU64x8(v: Long): U64x8 = run { val _simdArg0 = v.toULong(); _simdArg0 }

public fun simdExtSplatI128x2(v: Long): I128x2 = run { val _simdArg0 = I128.fromLong(v); _simdArg0 }

public fun simdExtSplatU128x2(v: Long): U128x2 = run { val _simdArg0 = U128.fromLong(v); _simdArg0 }

public fun simdExtSplatI128x4(v: Long): I128x4 = run { val _simdArg0 = I128.fromLong(v); _simdArg0 }

public fun simdExtSplatU128x4(v: Long): U128x4 = run { val _simdArg0 = U128.fromLong(v); _simdArg0 }

public fun <Elem, T : SimdExt<T, Any?>> simdExtAbs(a: T): T {
    val m: T = run { val _simdArg0 = a; val _simdArg1 = simdExtSplat(0L); _simdArg0 }
    return run { val _simdArg0 = m; val _simdArg1 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg2 = a; _simdArg1 }
}

public fun <T> simdExtAbsd(a: T, b: T): T {
    val m: T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
    return run { val _simdArg0 = m; val _simdArg1 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg2 = run { val _simdArg0 = b; val _simdArg1 = a; _simdArg0 }; _simdArg1 }
}

public fun <Elem, T : SimdExt<T, Any?>> simdExtAdda(a: T, b: T): T = run { val _simdArg0 = simdExtAbs(a); val _simdArg1 = simdExtAbs(b); _simdArg0 }

public fun <Elem, T : SimdExt<T, Any?>> simdExtAndn(a: T, b: T): T = run { val _simdArg0 = simdExtNot(a); val _simdArg1 = b; _simdArg0 }

public fun <Elem, T : SimdExt<T, Any?>> simdExtBitclr(a: T, b: T): T = simdExtAndn(simdExtShl(simdExtSplat(1L), b), a)

public fun <Elem, T : SimdExt<T, Any?>> simdExtBitrev(a: T, b: T): T = run { val _simdArg0 = simdExtShl(simdExtSplat(1L), b); val _simdArg1 = a; _simdArg0 }

public fun <Elem, T : SimdExt<T, Any?>> simdExtBitset(a: T, b: T): T = run { val _simdArg0 = simdExtShl(simdExtSplat(1L), b); val _simdArg1 = a; _simdArg0 }

public fun <T> simdExtFmsub(a: T, b: T, c: T): T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = run { val _simdArg0 = c; _simdArg0 }; _simdArg0 }

public fun <T> simdExtFnmadd(a: T, b: T, c: T): T = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = c; _simdArg0 }; _simdArg0 }

public fun <T> simdExtFnmsub(a: T, b: T, c: T): T = run { val _simdArg0 = simdExtFmsub(a, b, c); _simdArg0 }

public fun <T> simdExtFrecipS(a: T): T = run { val _simdArg0 = run { val _simdArg0 = 1.0f; _simdArg0 }; val _simdArg1 = a; _simdArg0 }

public fun <T> simdExtFrecipD(a: T): T = run { val _simdArg0 = run { val _simdArg0 = 1.0; _simdArg0 }; val _simdArg1 = a; _simdArg0 }

public fun <T> simdExtFrsqrtS(a: T): T = simdExtFrecipS(run { val _simdArg0 = a; _simdArg0 })

public fun <T> simdExtFrsqrtD(a: T): T = simdExtFrecipD(run { val _simdArg0 = a; _simdArg0 })

public inline fun <reified T> simdExtLd(a: ConstPtr, i: Int): T {
    val a1 = (ConstPtr((a).rawAddress + ((i).toLong() * (PrimitiveLayouts.I8).strideBytes))).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    return readUnaligned(a1, typeLayoutT = ObjectSlotLayout<T>())
}

public fun <Elem, T : SimdExt<T, Any?>> simdExtLdi(i: Int): T {
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
                11 -> transmute(I32x2.fromArray(intArrayOf(fp32Imm(i), 0)))
                12 -> fp64Imm(i)
                else -> error("unreachable")
            }
        }
    return simdExtSplat(r)
}

public inline fun <reified T> simdExtLdx(a: ConstPtr, b: Long): T {
    val a1 = (ConstPtr((a).rawAddress + ((b.toInt()).toLong() * (PrimitiveLayouts.I8).strideBytes))).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    return readUnaligned(a1, typeLayoutT = ObjectSlotLayout<T>())
}

public fun <T> simdExtMadd(a: T, b: T, c: T): T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

public fun <T> simdExtMsub(a: T, b: T, c: T): T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

public fun <Elem, T, W : SimdExt<W, Any?>> simdExtMuh(a: T, b: T): T {
    val a1: W = run { val _simdArg0 = a; _simdArg0 }
    val b1: W = run { val _simdArg0 = b; _simdArg0 }
    val p = run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 }
    return run { val _simdArg0 = run { val _simdArg0 = p; val _simdArg1 = simdExtSplat((sizeOf<Elem>(typeLayoutT = ObjectSlotLayout<Elem>()) * 8 / 2).toLong()); _simdArg0 }; _simdArg0 }
}

public fun <Elem, T : SimdExt<T, Any?>> simdExtNor(a: T, b: T): T = simdExtNot(run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 })

public fun <Elem, T : SimdExt<T, Any?>> simdExtNot(a: T): T = run { val _simdArg0 = a; val _simdArg1 = simdExtSplat(0L.inv()); _simdArg0 }

public fun <Elem, T : SimdExt<T, Any?>> simdExtOrn(a: T, b: T): T = run { val _simdArg0 = a; val _simdArg1 = simdExtNot(b); _simdArg0 }

public fun <Elem, T : SimdExt<T, Any?>> simdExtRotr(a: T, b: T): T {
    val m = (sizeOf<Elem>(typeLayoutT = ObjectSlotLayout<Elem>()) * 8 - 1).toLong()
    val r = run { val _simdArg0 = b; val _simdArg1 = simdExtSplat(m); _simdArg0 }
    val l = run { val _simdArg0 = run { val _simdArg0 = simdExtSplat(m + 1L); val _simdArg1 = r; _simdArg0 }; val _simdArg1 = simdExtSplat(m); _simdArg0 }
    return run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = r; _simdArg0 }; val _simdArg1 = run { val _simdArg0 = a; val _simdArg1 = l; _simdArg0 }; _simdArg0 }
}

public fun <Elem, T : SimdExt<T, Any?>> simdExtShl(a: T, b: T): T {
    val m = (sizeOf<Elem>(typeLayoutT = ObjectSlotLayout<Elem>()) * 8 - 1).toLong()
    return run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = simdExtSplat(m); _simdArg0 }; _simdArg0 }
}

public fun <Elem, T : SimdExt<T, Any?>> simdExtShr(a: T, b: T): T {
    val m = (sizeOf<Elem>(typeLayoutT = ObjectSlotLayout<Elem>()) * 8 - 1).toLong()
    return run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = simdExtSplat(m); _simdArg0 }; _simdArg0 }
}

public fun <Elem, T : SimdExt<T, Any?>> simdExtSplat(a: Long, splatTFn: (Long) -> T): T = splatTFn(a)

public fun <T> simdExtSt(a: T, b: MutPtr, i: Int) {
    val b1 = (MutPtr((b).rawAddress + ((i).toLong() * (PrimitiveLayouts.I8).strideBytes))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    writeUnaligned(b1, a, typeLayoutT = ObjectSlotLayout<T>())
}

public fun <T> simdExtStx(a: T, b: MutPtr, c: Long) {
    val b1 = (MutPtr((b).rawAddress + ((c.toInt()).toLong() * (PrimitiveLayouts.I8).strideBytes))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    writeUnaligned(b1, a, typeLayoutT = ObjectSlotLayout<T>())
}
