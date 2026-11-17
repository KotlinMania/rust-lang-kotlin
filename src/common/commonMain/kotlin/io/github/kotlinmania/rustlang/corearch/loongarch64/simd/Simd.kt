// port-lint: source loongarch64/simd.rs
package io.github.kotlinmania.rustlang.corearch.loongarch64.simd

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.intrinsics.kmAllocZeroed
import io.github.kotlinmania.rustlang.core.intrinsics.kmDealloc
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.readUnaligned
import io.github.kotlinmania.rustlang.core.ptr.writeUnaligned
import io.github.kotlinmania.rustlang.corearch.simd.I128x2
import io.github.kotlinmania.rustlang.corearch.simd.I128x4
import io.github.kotlinmania.rustlang.corearch.simd.I16x16
import io.github.kotlinmania.rustlang.corearch.simd.I16x32
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x16
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I32x8
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I64x4
import io.github.kotlinmania.rustlang.corearch.simd.I64x8
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.I8x32
import io.github.kotlinmania.rustlang.corearch.simd.Simd
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

public fun simdExtSplatI8x16(v: Long): I8x16 = run { val simdArg0 = v.toByte(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x16>()).alignBytes)); res } }

public fun simdExtSplatI8x32(v: Long): I8x32 = run { val simdArg0 = v.toByte(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x32>()).alignBytes)); val res = (UnresolvedTypeLayout<I8x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I8x32>()).alignBytes)); res } }

public fun simdExtSplatU8x16(v: Long): U8x16 = run { val simdArg0 = v.toUByte(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x16>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x16>()).alignBytes)); res } }

public fun simdExtSplatU8x32(v: Long): U8x32 = run { val simdArg0 = v.toUByte(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x32>()).alignBytes)); val res = (UnresolvedTypeLayout<U8x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U8x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U8x32>()).alignBytes)); res } }

public fun simdExtSplatI16x8(v: Long): I16x8 = run { val simdArg0 = v.toShort(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x8>()).alignBytes)); res } }

public fun simdExtSplatI16x16(v: Long): I16x16 = run { val simdArg0 = v.toShort(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x16>()).alignBytes)); res } }

public fun simdExtSplatI16x32(v: Long): I16x32 = run { val simdArg0 = v.toShort(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); val res = (UnresolvedTypeLayout<I16x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I16x32>()).alignBytes)); res } }

public fun simdExtSplatU16x8(v: Long): U16x8 = run { val simdArg0 = v.toUShort(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x8>()).alignBytes)); res } }

public fun simdExtSplatU16x16(v: Long): U16x16 = run { val simdArg0 = v.toUShort(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x16>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x16>()).alignBytes)); res } }

public fun simdExtSplatU16x32(v: Long): U16x32 = run { val simdArg0 = v.toUShort(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x32>()).alignBytes)); val res = (UnresolvedTypeLayout<U16x32>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U16x32>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U16x32>()).alignBytes)); res } }

public fun simdExtSplatI32x4(v: Long): I32x4 = run { val simdArg0 = v.toInt(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x4>()).alignBytes)); res } }

public fun simdExtSplatI32x8(v: Long): I32x8 = run { val simdArg0 = v.toInt(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x8>()).alignBytes)); res } }

public fun simdExtSplatI32x16(v: Long): I32x16 = run { val simdArg0 = v.toInt(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I32x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x16>()).alignBytes)); val res = (UnresolvedTypeLayout<I32x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I32x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I32x16>()).alignBytes)); res } }

public fun simdExtSplatU32x4(v: Long): U32x4 = run { val simdArg0 = v.toUInt(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x4>()).alignBytes)); res } }

public fun simdExtSplatU32x8(v: Long): U32x8 = run { val simdArg0 = v.toUInt(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x8>()).alignBytes)); res } }

public fun simdExtSplatU32x16(v: Long): U32x16 = run { val simdArg0 = v.toUInt(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U32x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x16>()).alignBytes)); val res = (UnresolvedTypeLayout<U32x16>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U32x16>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U32x16>()).alignBytes)); res } }

public fun simdExtSplatI64x2(v: Long): I64x2 = run { val simdArg0 = v; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }

public fun simdExtSplatI64x4(v: Long): I64x4 = run { val simdArg0 = v; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x4>()).alignBytes)); res } }

public fun simdExtSplatI64x8(v: Long): I64x8 = run { val simdArg0 = v; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x8>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x8>()).alignBytes)); res } }

public fun simdExtSplatU64x2(v: Long): U64x2 = run { val simdArg0 = v.toULong(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U64x2>()).alignBytes)); res } }

public fun simdExtSplatU64x4(v: Long): U64x4 = run { val simdArg0 = v.toULong(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U64x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U64x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U64x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U64x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U64x4>()).alignBytes)); res } }

public fun simdExtSplatU64x8(v: Long): U64x8 = run { val simdArg0 = v.toULong(); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U64x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U64x8>()).alignBytes)); val res = (UnresolvedTypeLayout<U64x8>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U64x8>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U64x8>()).alignBytes)); res } }

public fun simdExtSplatI128x2(v: Long): I128x2 = run { val simdArg0 = I128.fromLong(v); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I128x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I128x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I128x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I128x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I128x2>()).alignBytes)); res } }

public fun simdExtSplatU128x2(v: Long): U128x2 = run { val simdArg0 = U128.fromLong(v); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U128x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U128x2>()).alignBytes)); val res = (UnresolvedTypeLayout<U128x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U128x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U128x2>()).alignBytes)); res } }

public fun simdExtSplatI128x4(v: Long): I128x4 = run { val simdArg0 = I128.fromLong(v); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I128x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I128x4>()).alignBytes)); val res = (UnresolvedTypeLayout<I128x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I128x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I128x4>()).alignBytes)); res } }

public fun simdExtSplatU128x4(v: Long): U128x4 = run { val simdArg0 = U128.fromLong(v); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<U128x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U128x4>()).alignBytes)); val res = (UnresolvedTypeLayout<U128x4>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<U128x4>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<U128x4>()).alignBytes)); res } }

public fun <Elem, T : SimdExt<T, Elem>> simdExtAbs(a: T): T {
    val m: T = run { val simdArg0 = a; val simdArg1 = simdExtSplat(0L); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
    return run { val simdArg0 = m; val simdArg1 = run { val simdArg0 = a; simdArg0 }; val simdArg2 = a; simdArg1 }
}

public fun <T> simdExtAbsd(a: T, b: T): T {
    val m: T = run { val simdArg0 = a; val simdArg1 = b; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
    return run { val simdArg0 = m; val simdArg1 = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }; val simdArg2 = run { val simdArg0 = b; val simdArg1 = a; simdArg0 }; simdArg1 }
}

public fun <Elem, T : SimdExt<T, Elem>> simdExtAdda(a: T, b: T): T = run { val simdArg0 = simdExtAbs(a); val simdArg1 = simdExtAbs(b); simdArg0 }

public fun <Elem, T : SimdExt<T, Elem>> simdExtAndn(a: T, b: T): T = run { val simdArg0 = simdExtNot(a); val simdArg1 = b; simdArg0 }

public fun <Elem, T : SimdExt<T, Elem>> simdExtBitclr(a: T, b: T): T = simdExtAndn(simdExtShl(simdExtSplat(1L), b), a)

public fun <Elem, T : SimdExt<T, Elem>> simdExtBitrev(a: T, b: T): T = run { val simdArg0 = simdExtShl(simdExtSplat(1L), b); val simdArg1 = a; simdArg0 }

public fun <Elem, T : SimdExt<T, Elem>> simdExtBitset(a: T, b: T): T = run { val simdArg0 = simdExtShl(simdExtSplat(1L), b); val simdArg1 = a; simdArg0 }

public fun <T> simdExtFmsub(a: T, b: T, c: T): T = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = run { val simdArg0 = c; simdArg0 }; simdArg0 }

public fun <T> simdExtFnmadd(a: T, b: T, c: T): T = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = c; simdArg0 }; simdArg0 }

public fun <T> simdExtFnmsub(a: T, b: T, c: T): T = run { val simdArg0 = simdExtFmsub(a, b, c); simdArg0 }

public fun <T> simdExtFrecipS(a: T): T = run { val simdArg0 = run { val simdArg0 = 1.0f; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }; val simdArg1 = a; simdArg0 }

public fun <T> simdExtFrecipD(a: T): T = run { val simdArg0 = run { val simdArg0 = 1.0; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }; val simdArg1 = a; simdArg0 }

public fun <T> simdExtFrsqrtS(a: T): T = simdExtFrecipS(run { val simdArg0 = a; simdArg0 })

public fun <T> simdExtFrsqrtD(a: T): T = simdExtFrecipD(run { val simdArg0 = a; simdArg0 })

public inline fun <reified T> simdExtLd(a: ConstPtr, i: Int): T {
    val a1 = (ConstPtr((a).rawAddress + ((i).toLong() * (PrimitiveLayouts.I8).strideBytes))).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    return readUnaligned(a1, typeLayoutT = UnresolvedTypeLayout<T>())
}

public fun <Elem, T : SimdExt<T, Elem>> simdExtLdi(i: Int): T {
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
                0 -> transmute(Simd<Byte>(List(8) { imm8.toByte() }))
                1 -> transmute(Simd<Short>(List(4) { sext(imm10, 10u).toShort() }))
                2 -> transmute(Simd<Int>(List(2) { sext(imm10, 10u).toInt() }))
                3 -> sext(imm10, 10u)
                else -> error("unreachable")
            }
        } else {
            when ((i shr 8) and 0xf) {
                in 0..3 -> { transmute(Simd<Int>(List(2) { imm8 shl (8 * ((i shr 8) and 3)) })) }
                in 4..5 -> transmute(Simd<Short>(List(4) { ((imm8.toShort()).toInt() shl (8 * ((i shr 8) and 1))).toShort() }))
                6 -> transmute(Simd<Int>(List(2) { (imm8 shl 8) or 0xff }))
                7 -> transmute(Simd<Int>(List(2) { (imm8 shl 16) or 0xffff }))
                8 -> transmute(Simd<Byte>(List(8) { imm8.toByte() }))
                9 -> expandU8(imm8).toLong()
                10 -> transmute(Simd<Int>(List(2) { fp32Imm(i) }))
                11 -> transmute(Simd<Int>((intArrayOf(fp32Imm(i), 0)).toList()))
                12 -> fp64Imm(i)
                else -> error("unreachable")
            }
        }
    return simdExtSplat(r)
}

public inline fun <reified T> simdExtLdx(a: ConstPtr, b: Long): T {
    val a1 = (ConstPtr((a).rawAddress + ((b.toInt()).toLong() * (PrimitiveLayouts.I8).strideBytes))).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    return readUnaligned(a1, typeLayoutT = UnresolvedTypeLayout<T>())
}

public fun <T> simdExtMadd(a: T, b: T, c: T): T = run { val simdArg0 = a; val simdArg1 = run { val simdArg0 = b; val simdArg1 = c; simdArg0 }; simdArg0 }

public fun <T> simdExtMsub(a: T, b: T, c: T): T = run { val simdArg0 = a; val simdArg1 = run { val simdArg0 = b; val simdArg1 = c; simdArg0 }; simdArg0 }

public fun <Elem, T, W : SimdExt<W, Elem>> simdExtMuh(a: T, b: T): T {
    val a1: W = run { val simdArg0 = a; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<W>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<W>()).alignBytes)); val res = (UnresolvedTypeLayout<W>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<W>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<W>()).alignBytes)); res } }
    val b1: W = run { val simdArg0 = b; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<W>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<W>()).alignBytes)); val res = (UnresolvedTypeLayout<W>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<W>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<W>()).alignBytes)); res } }
    val p = run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }
    return run { val simdArg0 = run { val simdArg0 = p; val simdArg1 = simdExtSplat((sizeOf<Elem>(typeLayoutT = UnresolvedTypeLayout<Elem>()) * 8 / 2).toLong()); simdArg0 }; run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); val res = (UnresolvedTypeLayout<T>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<T>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<T>()).alignBytes)); res } }
}

public fun <Elem, T : SimdExt<T, Elem>> simdExtNor(a: T, b: T): T = simdExtNot(run { val simdArg0 = a; val simdArg1 = b; simdArg0 })

public fun <Elem, T : SimdExt<T, Elem>> simdExtNot(a: T): T = run { val simdArg0 = a; val simdArg1 = simdExtSplat(0L.inv()); simdArg0 }

public fun <Elem, T : SimdExt<T, Elem>> simdExtOrn(a: T, b: T): T = run { val simdArg0 = a; val simdArg1 = simdExtNot(b); simdArg0 }

public fun <Elem, T : SimdExt<T, Elem>> simdExtRotr(a: T, b: T): T {
    val m = (sizeOf<Elem>(typeLayoutT = UnresolvedTypeLayout<Elem>()) * 8 - 1).toLong()
    val r = run { val simdArg0 = b; val simdArg1 = simdExtSplat(m); simdArg0 }
    val l = run { val simdArg0 = run { val simdArg0 = simdExtSplat(m + 1L); val simdArg1 = r; simdArg0 }; val simdArg1 = simdExtSplat(m); simdArg0 }
    return run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = r; simdArg0 }; val simdArg1 = run { val simdArg0 = a; val simdArg1 = l; simdArg0 }; simdArg0 }
}

public fun <Elem, T : SimdExt<T, Elem>> simdExtShl(a: T, b: T): T {
    val m = (sizeOf<Elem>(typeLayoutT = UnresolvedTypeLayout<Elem>()) * 8 - 1).toLong()
    return run { val simdArg0 = a; val simdArg1 = run { val simdArg0 = b; val simdArg1 = simdExtSplat(m); simdArg0 }; simdArg0 }
}

public fun <Elem, T : SimdExt<T, Elem>> simdExtShr(a: T, b: T): T {
    val m = (sizeOf<Elem>(typeLayoutT = UnresolvedTypeLayout<Elem>()) * 8 - 1).toLong()
    return run { val simdArg0 = a; val simdArg1 = run { val simdArg0 = b; val simdArg1 = simdExtSplat(m); simdArg0 }; simdArg0 }
}

public fun <Elem, T : SimdExt<T, Elem>> simdExtSplat(a: Long, splatTFn: (Long) -> T): T = splatTFn(a)

public fun <T> simdExtSt(a: T, b: MutPtr, i: Int) {
    val b1 = (MutPtr((b).rawAddress + ((i).toLong() * (PrimitiveLayouts.I8).strideBytes))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    writeUnaligned(b1, a, typeLayoutT = UnresolvedTypeLayout<T>())
}

public fun <T> simdExtStx(a: T, b: MutPtr, c: Long) {
    val b1 = (MutPtr((b).rawAddress + ((c.toInt()).toLong() * (PrimitiveLayouts.I8).strideBytes))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    writeUnaligned(b1, a, typeLayoutT = UnresolvedTypeLayout<T>())
}
