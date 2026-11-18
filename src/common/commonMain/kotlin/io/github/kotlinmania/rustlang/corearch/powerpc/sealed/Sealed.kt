// port-lint: source powerpc/altivec.rs
package io.github.kotlinmania.rustlang.corearch.powerpc.sealed

import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.corearch.powerpc.VectorBoolChar
import io.github.kotlinmania.rustlang.corearch.powerpc.VectorBoolInt
import io.github.kotlinmania.rustlang.corearch.powerpc.VectorBoolShort
import io.github.kotlinmania.rustlang.corearch.powerpc.VectorFloat
import io.github.kotlinmania.rustlang.corearch.powerpc.VectorSignedChar
import io.github.kotlinmania.rustlang.corearch.powerpc.VectorSignedInt
import io.github.kotlinmania.rustlang.corearch.powerpc.VectorSignedShort
import io.github.kotlinmania.rustlang.corearch.powerpc.VectorUnsignedChar
import io.github.kotlinmania.rustlang.corearch.powerpc.VectorUnsignedInt
import io.github.kotlinmania.rustlang.corearch.powerpc.VectorUnsignedShort
import io.github.kotlinmania.rustlang.corearch.powerpc.lvebx
import io.github.kotlinmania.rustlang.corearch.powerpc.lvehx
import io.github.kotlinmania.rustlang.corearch.powerpc.lvewx
import io.github.kotlinmania.rustlang.corearch.powerpc.lvx
import io.github.kotlinmania.rustlang.corearch.powerpc.lvxl
import io.github.kotlinmania.rustlang.corearch.powerpc.stvebx
import io.github.kotlinmania.rustlang.corearch.powerpc.stvehx
import io.github.kotlinmania.rustlang.corearch.powerpc.stvewx
import io.github.kotlinmania.rustlang.corearch.powerpc.stvx
import io.github.kotlinmania.rustlang.corearch.powerpc.stvxl
import io.github.kotlinmania.rustlang.corearch.powerpc.vaddcuw
import io.github.kotlinmania.rustlang.corearch.powerpc.vaddsbs
import io.github.kotlinmania.rustlang.corearch.powerpc.vaddshs
import io.github.kotlinmania.rustlang.corearch.powerpc.vaddsws
import io.github.kotlinmania.rustlang.corearch.powerpc.vaddubs
import io.github.kotlinmania.rustlang.corearch.powerpc.vadduhs
import io.github.kotlinmania.rustlang.corearch.powerpc.vadduws
import io.github.kotlinmania.rustlang.corearch.powerpc.vavgsb
import io.github.kotlinmania.rustlang.corearch.powerpc.vavgsh
import io.github.kotlinmania.rustlang.corearch.powerpc.vavgsw
import io.github.kotlinmania.rustlang.corearch.powerpc.vavgub
import io.github.kotlinmania.rustlang.corearch.powerpc.vavguh
import io.github.kotlinmania.rustlang.corearch.powerpc.vavguw
import io.github.kotlinmania.rustlang.corearch.powerpc.vcfsx
import io.github.kotlinmania.rustlang.corearch.powerpc.vcfux
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpbfp
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpeqfpP
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpequb
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpequbP
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpequh
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpequhP
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpequw
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpequwP
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpgefp
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpgefpP
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpgtfpP
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpgtsb
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpgtsbP
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpgtsh
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpgtshP
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpgtsw
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpgtswP
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpgtub
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpgtubP
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpgtuh
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpgtuhP
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpgtuw
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpgtuwP
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpneb
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpneh
import io.github.kotlinmania.rustlang.corearch.powerpc.vcmpnew
import io.github.kotlinmania.rustlang.corearch.powerpc.vecAdd
import io.github.kotlinmania.rustlang.corearch.powerpc.vecAnd
import io.github.kotlinmania.rustlang.corearch.powerpc.vecCmpeq
import io.github.kotlinmania.rustlang.corearch.powerpc.vecNor
import io.github.kotlinmania.rustlang.corearch.powerpc.vecOr
import io.github.kotlinmania.rustlang.corearch.powerpc.vecPerm
import io.github.kotlinmania.rustlang.corearch.powerpc.vecSplats
import io.github.kotlinmania.rustlang.corearch.powerpc.vecXor
import io.github.kotlinmania.rustlang.corearch.powerpc.vexptefp
import io.github.kotlinmania.rustlang.corearch.powerpc.vmaxsb
import io.github.kotlinmania.rustlang.corearch.powerpc.vmaxsh
import io.github.kotlinmania.rustlang.corearch.powerpc.vmaxsw
import io.github.kotlinmania.rustlang.corearch.powerpc.vmaxub
import io.github.kotlinmania.rustlang.corearch.powerpc.vmaxuh
import io.github.kotlinmania.rustlang.corearch.powerpc.vmaxuw
import io.github.kotlinmania.rustlang.corearch.powerpc.vminsb
import io.github.kotlinmania.rustlang.corearch.powerpc.vminsh
import io.github.kotlinmania.rustlang.corearch.powerpc.vminsw
import io.github.kotlinmania.rustlang.corearch.powerpc.vminub
import io.github.kotlinmania.rustlang.corearch.powerpc.vminuh
import io.github.kotlinmania.rustlang.corearch.powerpc.vminuw
import io.github.kotlinmania.rustlang.corearch.powerpc.vmsummbm
import io.github.kotlinmania.rustlang.corearch.powerpc.vmsumshm
import io.github.kotlinmania.rustlang.corearch.powerpc.vmsumshs
import io.github.kotlinmania.rustlang.corearch.powerpc.vmsumubm
import io.github.kotlinmania.rustlang.corearch.powerpc.vmsumuhm
import io.github.kotlinmania.rustlang.corearch.powerpc.vmsumuhs
import io.github.kotlinmania.rustlang.corearch.powerpc.vmulesb
import io.github.kotlinmania.rustlang.corearch.powerpc.vmulesh
import io.github.kotlinmania.rustlang.corearch.powerpc.vmuleub
import io.github.kotlinmania.rustlang.corearch.powerpc.vmuleuh
import io.github.kotlinmania.rustlang.corearch.powerpc.vmulosb
import io.github.kotlinmania.rustlang.corearch.powerpc.vmulosh
import io.github.kotlinmania.rustlang.corearch.powerpc.vmuloub
import io.github.kotlinmania.rustlang.corearch.powerpc.vmulouh
import io.github.kotlinmania.rustlang.corearch.powerpc.vperm
import io.github.kotlinmania.rustlang.corearch.powerpc.vpkshss
import io.github.kotlinmania.rustlang.corearch.powerpc.vpkshus
import io.github.kotlinmania.rustlang.corearch.powerpc.vpkswss
import io.github.kotlinmania.rustlang.corearch.powerpc.vpkswus
import io.github.kotlinmania.rustlang.corearch.powerpc.vpkuhus
import io.github.kotlinmania.rustlang.corearch.powerpc.vpkuwus
import io.github.kotlinmania.rustlang.corearch.powerpc.vrfin
import io.github.kotlinmania.rustlang.corearch.powerpc.vsl
import io.github.kotlinmania.rustlang.corearch.powerpc.vslo
import io.github.kotlinmania.rustlang.corearch.powerpc.vsr
import io.github.kotlinmania.rustlang.corearch.powerpc.vsrab
import io.github.kotlinmania.rustlang.corearch.powerpc.vsrah
import io.github.kotlinmania.rustlang.corearch.powerpc.vsraw
import io.github.kotlinmania.rustlang.corearch.powerpc.vsro
import io.github.kotlinmania.rustlang.corearch.powerpc.vsubcuw
import io.github.kotlinmania.rustlang.corearch.powerpc.vsubsbs
import io.github.kotlinmania.rustlang.corearch.powerpc.vsubshs
import io.github.kotlinmania.rustlang.corearch.powerpc.vsubsws
import io.github.kotlinmania.rustlang.corearch.powerpc.vsububs
import io.github.kotlinmania.rustlang.corearch.powerpc.vsubuhs
import io.github.kotlinmania.rustlang.corearch.powerpc.vsubuws
import io.github.kotlinmania.rustlang.corearch.powerpc.vsum2sws
import io.github.kotlinmania.rustlang.corearch.powerpc.vsum4sbs
import io.github.kotlinmania.rustlang.corearch.powerpc.vsum4shs
import io.github.kotlinmania.rustlang.corearch.powerpc.vsum4ubs
import io.github.kotlinmania.rustlang.corearch.powerpc.vupkhsb
import io.github.kotlinmania.rustlang.corearch.powerpc.vupkhsh
import io.github.kotlinmania.rustlang.corearch.powerpc.vupklsb
import io.github.kotlinmania.rustlang.corearch.powerpc.vupklsh
import io.github.kotlinmania.rustlang.corearch.simd.F32x4
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.Simd
import io.github.kotlinmania.rustlang.corearch.simd.U16x8
import io.github.kotlinmania.rustlang.corearch.simd.U32x4
import io.github.kotlinmania.rustlang.corearch.simd.U8x16
import kotlin.experimental.and
import kotlin.experimental.inv

public interface VectorNeg<T> {
    public fun vecNeg(): T
}

public fun VectorSignedChar.vecNeg(): VectorSignedChar = run { val simdArg0 = this; simdArg0 }

public fun VectorUnsignedChar.vecNeg(): VectorUnsignedChar = run { val simdArg0 = this; simdArg0 }

public fun VectorBoolChar.vecNeg(): VectorBoolChar = run { val simdArg0 = this; simdArg0 }

public fun VectorSignedShort.vecNeg(): VectorSignedShort = run { val simdArg0 = this; simdArg0 }

public fun VectorUnsignedShort.vecNeg(): VectorUnsignedShort = run { val simdArg0 = this; simdArg0 }

public fun VectorBoolShort.vecNeg(): VectorBoolShort = run { val simdArg0 = this; simdArg0 }

public fun VectorSignedInt.vecNeg(): VectorSignedInt = run { val simdArg0 = this; simdArg0 }

public fun VectorUnsignedInt.vecNeg(): VectorUnsignedInt = run { val simdArg0 = this; simdArg0 }

public fun VectorBoolInt.vecNeg(): VectorBoolInt = run { val simdArg0 = this; simdArg0 }

public fun VectorFloat.vecNeg(): VectorFloat = run { val simdArg0 = this; simdArg0 }

public interface VectorInsert<T, Scalar> {
    public fun vecInsert(s: Scalar, idx: UInt): T
}

public fun <T> idxInVec(idx: UInt, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): UInt = idx and (16u / sizeOf<T>(typeLayoutT = typeLayoutT).toUInt())

public fun VectorSignedChar.vecInsert(s: Byte, idx: UInt): VectorSignedChar = run { val simdArg0 = this; val simdArg1 = idxInVec<Byte>(idx, typeLayoutT = UnresolvedTypeLayout<Byte>()); val simdArg2 = s; simdArg0 }

public fun VectorUnsignedChar.vecInsert(s: UByte, idx: UInt): VectorUnsignedChar = run { val simdArg0 = this; val simdArg1 = idxInVec<UByte>(idx, typeLayoutT = UnresolvedTypeLayout<UByte>()); val simdArg2 = s; simdArg0 }

public fun VectorSignedShort.vecInsert(s: Short, idx: UInt): VectorSignedShort = run { val simdArg0 = this; val simdArg1 = idxInVec<Short>(idx, typeLayoutT = UnresolvedTypeLayout<Short>()); val simdArg2 = s; simdArg0 }

public fun VectorUnsignedShort.vecInsert(s: UShort, idx: UInt): VectorUnsignedShort = run { val simdArg0 = this; val simdArg1 = idxInVec<UShort>(idx, typeLayoutT = UnresolvedTypeLayout<UShort>()); val simdArg2 = s; simdArg0 }

public fun VectorSignedInt.vecInsert(s: Int, idx: UInt): VectorSignedInt = run { val simdArg0 = this; val simdArg1 = idxInVec<Int>(idx, typeLayoutT = UnresolvedTypeLayout<Int>()); val simdArg2 = s; simdArg0 }

public fun VectorUnsignedInt.vecInsert(s: UInt, idx: UInt): VectorUnsignedInt = run { val simdArg0 = this; val simdArg1 = idxInVec<UInt>(idx, typeLayoutT = UnresolvedTypeLayout<UInt>()); val simdArg2 = s; simdArg0 }

public fun VectorFloat.vecInsert(s: Float, idx: UInt): VectorFloat = run { val simdArg0 = this; val simdArg1 = idxInVec<Float>(idx, typeLayoutT = UnresolvedTypeLayout<Float>()); val simdArg2 = s; simdArg0 }

public interface VectorExtract<T, out Scalar> {
    public fun vecExtract(idx: UInt): Scalar
}

public fun VectorSignedChar.vecExtract(idx: UInt): Byte = run { val simdArg0 = this; val simdArg1 = idxInVec<Byte>(idx, typeLayoutT = UnresolvedTypeLayout<Byte>()); 0 }

public fun VectorUnsignedChar.vecExtract(idx: UInt): UByte = run { val simdArg0 = this; val simdArg1 = idxInVec<UByte>(idx, typeLayoutT = UnresolvedTypeLayout<UByte>()); 0u }

public fun VectorSignedShort.vecExtract(idx: UInt): Short = run { val simdArg0 = this; val simdArg1 = idxInVec<Short>(idx, typeLayoutT = UnresolvedTypeLayout<Short>()); 0 }

public fun VectorUnsignedShort.vecExtract(idx: UInt): UShort = run { val simdArg0 = this; val simdArg1 = idxInVec<UShort>(idx, typeLayoutT = UnresolvedTypeLayout<UShort>()); 0u }

public fun VectorSignedInt.vecExtract(idx: UInt): Int = run { val simdArg0 = this; val simdArg1 = idxInVec<Int>(idx, typeLayoutT = UnresolvedTypeLayout<Int>()); 0 }

public fun VectorUnsignedInt.vecExtract(idx: UInt): UInt = run { val simdArg0 = this; val simdArg1 = idxInVec<UInt>(idx, typeLayoutT = UnresolvedTypeLayout<UInt>()); 0u }

public fun VectorFloat.vecExtract(idx: UInt): Float = run { val simdArg0 = this; val simdArg1 = idxInVec<Float>(idx, typeLayoutT = UnresolvedTypeLayout<Float>()); 0.0f }

public interface VectorLd<out Result> {
    public fun vecLd(off: Int): Result

    public fun vecLdl(off: Int): Result
}

public fun vecLdU8(off: Int, p: ConstPtr): VectorUnsignedChar {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmuteWithLayouts<VectorUnsignedInt, VectorUnsignedChar>(lvx(addr), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorUnsignedChar>())
}

public fun vecLdlU8(off: Int, p: ConstPtr): VectorUnsignedChar {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmuteWithLayouts<VectorUnsignedInt, VectorUnsignedChar>(lvxl(addr), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorUnsignedChar>())
}

public fun ConstPtr.vecLd(off: Int): VectorUnsignedChar = vecLdU8(off, this)

public fun ConstPtr.vecLdl(off: Int): VectorUnsignedChar = vecLdlU8(off, this)

public fun vecLdI8(off: Int, p: ConstPtr): VectorSignedChar {
    val addr = ConstPtr(((p)).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmuteWithLayouts<VectorUnsignedInt, VectorSignedChar>(lvx(addr), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedChar>())
}

public fun vecLdlI8(off: Int, p: ConstPtr): VectorSignedChar {
    val addr = ConstPtr(((p)).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmuteWithLayouts<VectorUnsignedInt, VectorSignedChar>(lvxl(addr), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedChar>())
}

public fun ConstPtr.vecLdFromConstPtrByte(off: Int): VectorSignedChar = vecLdI8(off, this)

public fun ConstPtr.vecLdlFromConstPtrByte(off: Int): VectorSignedChar = vecLdlI8(off, this)

public fun vecLdU16(off: Int, p: ConstPtr): VectorUnsignedShort {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmuteWithLayouts<VectorUnsignedInt, VectorUnsignedShort>(lvx(addr), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorUnsignedShort>())
}

public fun vecLdlU16(off: Int, p: ConstPtr): VectorUnsignedShort {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmuteWithLayouts<VectorUnsignedInt, VectorUnsignedShort>(lvxl(addr), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorUnsignedShort>())
}

public fun ConstPtr.vecLdFromConstPtrUShort(off: Int): VectorUnsignedShort = vecLdU16(off, this)

public fun ConstPtr.vecLdlFromConstPtrUShort(off: Int): VectorUnsignedShort = vecLdlU16(off, this)

public fun vecLdI16(off: Int, p: ConstPtr): VectorSignedShort {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmuteWithLayouts<VectorUnsignedInt, VectorSignedShort>(lvx(addr), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedShort>())
}

public fun vecLdlI16(off: Int, p: ConstPtr): VectorSignedShort {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmuteWithLayouts<VectorUnsignedInt, VectorSignedShort>(lvxl(addr), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedShort>())
}

public fun ConstPtr.vecLdFromConstPtrShort(off: Int): VectorSignedShort = vecLdI16(off, this)

public fun ConstPtr.vecLdlFromConstPtrShort(off: Int): VectorSignedShort = vecLdlI16(off, this)

public fun vecLdU32(off: Int, p: ConstPtr): VectorUnsignedInt {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return lvx(addr)
}

public fun vecLdlU32(off: Int, p: ConstPtr): VectorUnsignedInt {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return lvxl(addr)
}

public fun ConstPtr.vecLdFromConstPtrUInt(off: Int): VectorUnsignedInt = vecLdU32(off, this)

public fun ConstPtr.vecLdlFromConstPtrUInt(off: Int): VectorUnsignedInt = vecLdlU32(off, this)

public fun vecLdI32(off: Int, p: ConstPtr): VectorSignedInt {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(lvx(addr), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())
}

public fun vecLdlI32(off: Int, p: ConstPtr): VectorSignedInt {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(lvxl(addr), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())
}

public fun ConstPtr.vecLdFromConstPtrInt(off: Int): VectorSignedInt = vecLdI32(off, this)

public fun ConstPtr.vecLdlFromConstPtrInt(off: Int): VectorSignedInt = vecLdlI32(off, this)

public fun vecLdF32(off: Int, p: ConstPtr): VectorFloat {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmuteWithLayouts<VectorUnsignedInt, VectorFloat>(lvx(addr), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorFloat>())
}

public fun vecLdlF32(off: Int, p: ConstPtr): VectorFloat {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    return transmuteWithLayouts<VectorUnsignedInt, VectorFloat>(lvxl(addr), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorFloat>())
}

public fun ConstPtr.vecLdFromConstPtrFloat(off: Int): VectorFloat = vecLdF32(off, this)

public fun ConstPtr.vecLdlFromConstPtrFloat(off: Int): VectorFloat = vecLdlF32(off, this)

public interface VectorLde<out Result> {
    public fun vecLde(a: Int): Result
}

public fun vecLdeU8(a: Int, b: ConstPtr): VectorUnsignedChar {
    val addr = ConstPtr((ConstPtr((b).rawAddress + ((a).toLong()))).rawAddress)
    return transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(lvebx(addr), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())
}

public fun ConstPtr.vecLde(a: Int): VectorUnsignedChar = vecLdeU8(a, this)

public fun vecLdeI8(a: Int, b: ConstPtr): VectorSignedChar {
    val addr = ConstPtr((b).rawAddress + ((a).toLong()))
    return lvebx(addr)
}

public fun ConstPtr.vecLdeFromConstPtrByte(a: Int): VectorSignedChar = vecLdeI8(a, this)

public fun vecLdeU16(a: Int, b: ConstPtr): VectorUnsignedShort {
    val addr = ConstPtr((ConstPtr((b).rawAddress + ((a).toLong()))).rawAddress)
    return transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(lvehx(addr), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())
}

public fun ConstPtr.vecLdeFromConstPtrUShort(a: Int): VectorUnsignedShort = vecLdeU16(a, this)

public fun vecLdeI16(a: Int, b: ConstPtr): VectorSignedShort {
    val addr = ConstPtr((ConstPtr((b).rawAddress + ((a).toLong()))).rawAddress)
    return lvehx(addr)
}

public fun ConstPtr.vecLdeFromConstPtrShort(a: Int): VectorSignedShort = vecLdeI16(a, this)

public fun vecLdeU32(a: Int, b: ConstPtr): VectorUnsignedInt {
    val addr = ConstPtr((ConstPtr((b).rawAddress + ((a).toLong()))).rawAddress)
    return transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(lvewx(addr), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())
}

public fun ConstPtr.vecLdeFromConstPtrUInt(a: Int): VectorUnsignedInt = vecLdeU32(a, this)

public fun vecLdeI32(a: Int, b: ConstPtr): VectorSignedInt {
    val addr = ConstPtr((ConstPtr((b).rawAddress + ((a).toLong()))).rawAddress)
    return lvewx(addr)
}

public fun ConstPtr.vecLdeFromConstPtrInt(a: Int): VectorSignedInt = vecLdeI32(a, this)

public fun vecLdeF32(a: Int, b: ConstPtr): VectorFloat {
    val addr = ConstPtr((ConstPtr((b).rawAddress + ((a).toLong()))).rawAddress)
    return transmuteWithLayouts<VectorSignedInt, VectorFloat>(lvewx(addr), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorFloat>())
}

public fun ConstPtr.vecLdeFromConstPtrFloat(a: Int): VectorFloat = vecLdeF32(a, this)

public interface VectorSt<Target> {
    public fun vecSt(off: Int, p: Target)

    public fun vecStl(off: Int, p: Target)
}

public fun vecStU8(a: VectorUnsignedChar, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvx(transmute(a), addr)
}

public fun vecStlU8(a: VectorUnsignedChar, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvxl(transmute(a), addr)
}

public fun VectorUnsignedChar.vecSt(off: Int, p: ConstPtr) {
    vecStU8(this, off, p)
}

public fun VectorUnsignedChar.vecStl(off: Int, p: ConstPtr) {
    vecStU8(this, off, p)
}

public fun vecStI8(a: VectorSignedChar, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((p)).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvx(transmute(a), addr)
}

public fun vecStlI8(a: VectorSignedChar, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((p)).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvxl(transmute(a), addr)
}

public fun VectorSignedChar.vecSt(off: Int, p: ConstPtr) {
    vecStI8(this, off, p)
}

public fun VectorSignedChar.vecStl(off: Int, p: ConstPtr) {
    vecStI8(this, off, p)
}

public fun vecStU16(a: VectorUnsignedShort, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvx(transmute(a), addr)
}

public fun vecStlU16(a: VectorUnsignedShort, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvxl(transmute(a), addr)
}

public fun VectorUnsignedShort.vecSt(off: Int, p: ConstPtr) {
    vecStU16(this, off, p)
}

public fun VectorUnsignedShort.vecStl(off: Int, p: ConstPtr) {
    vecStU16(this, off, p)
}

public fun vecStI16(a: VectorSignedShort, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvx(transmute(a), addr)
}

public fun vecStlI16(a: VectorSignedShort, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvxl(transmute(a), addr)
}

public fun VectorSignedShort.vecSt(off: Int, p: ConstPtr) {
    vecStI16(this, off, p)
}

public fun VectorSignedShort.vecStl(off: Int, p: ConstPtr) {
    vecStI16(this, off, p)
}

public fun vecStU32(a: VectorUnsignedInt, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvx(transmute(a), addr)
}

public fun vecStlU32(a: VectorUnsignedInt, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvxl(transmute(a), addr)
}

public fun VectorUnsignedInt.vecSt(off: Int, p: ConstPtr) {
    vecStU32(this, off, p)
}

public fun VectorUnsignedInt.vecStl(off: Int, p: ConstPtr) {
    vecStU32(this, off, p)
}

public fun vecStI32(a: VectorSignedInt, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvx(transmute(a), addr)
}

public fun vecStlI32(a: VectorSignedInt, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvxl(transmute(a), addr)
}

public fun VectorSignedInt.vecSt(off: Int, p: ConstPtr) {
    vecStI32(this, off, p)
}

public fun VectorSignedInt.vecStl(off: Int, p: ConstPtr) {
    vecStI32(this, off, p)
}

public fun vecStF32(a: VectorFloat, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvx(transmute(a), addr)
}

public fun vecStlF32(a: VectorFloat, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvxl(transmute(a), addr)
}

public fun VectorFloat.vecSt(off: Int, p: ConstPtr) {
    vecStF32(this, off, p)
}

public fun VectorFloat.vecStl(off: Int, p: ConstPtr) {
    vecStF32(this, off, p)
}

public interface VectorSte<Target> {
    public fun vecSte(off: Int, p: Target)
}

public fun vecSteU8(a: VectorUnsignedChar, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvebx(transmute(a), addr)
}

public fun VectorUnsignedChar.vecSte(off: Int, p: ConstPtr) {
    vecSteU8(this, off, p)
}

public fun vecSteI8(a: VectorSignedChar, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((p)).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvebx(transmute(a), addr)
}

public fun VectorSignedChar.vecSte(off: Int, p: ConstPtr) {
    vecSteI8(this, off, p)
}

public fun vecSteU16(a: VectorUnsignedShort, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvehx(transmute(a), addr)
}

public fun VectorUnsignedShort.vecSte(off: Int, p: ConstPtr) {
    vecSteU16(this, off, p)
}

public fun vecSteI16(a: VectorSignedShort, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvehx(transmute(a), addr)
}

public fun VectorSignedShort.vecSte(off: Int, p: ConstPtr) {
    vecSteI16(this, off, p)
}

public fun vecSteU32(a: VectorUnsignedInt, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvewx(transmute(a), addr)
}

public fun VectorUnsignedInt.vecSte(off: Int, p: ConstPtr) {
    vecSteU32(this, off, p)
}

public fun vecSteI32(a: VectorSignedInt, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvewx(transmute(a), addr)
}

public fun VectorSignedInt.vecSte(off: Int, p: ConstPtr) {
    vecSteI32(this, off, p)
}

public fun vecSteF32(a: VectorFloat, off: Int, p: ConstPtr) {
    val addr = ConstPtr(((ConstPtr(p.rawAddress, p.rawMeta))).rawAddress + ((off).toLong() * (PrimitiveLayouts.I8).strideBytes))
    stvewx(transmute(a), addr)
}

public fun VectorFloat.vecSte(off: Int, p: ConstPtr) {
    vecSteF32(this, off, p)
}

public interface VectorXl<out Result> {
    public fun vecXl(a: Int): Result
}

public fun vecXlI8(a: Int, b: ConstPtr): VectorSignedChar {
    val addr = ConstPtr(((ConstPtr(b.rawAddress, b.rawMeta))).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    val r = MaybeUninit.uninit(UnresolvedTypeLayout<VectorSignedChar>())
    copyNonoverlapping(addr, mutPtrFromRustCast(r.asMutPtr()), sizeOf<VectorSignedChar>(typeLayoutT = UnresolvedTypeLayout<VectorSignedChar>()), typeLayoutT = PrimitiveLayouts.U8)
    return r.assumeInit()
}

public fun ConstPtr.vecXl(a: Int): VectorSignedChar = vecXlI8(a, this)

public fun vecXlU8(a: Int, b: ConstPtr): VectorUnsignedChar {
    val addr = ConstPtr(((b)).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    val r = MaybeUninit.uninit(UnresolvedTypeLayout<VectorUnsignedChar>())
    copyNonoverlapping(addr, mutPtrFromRustCast(r.asMutPtr()), sizeOf<VectorUnsignedChar>(typeLayoutT = UnresolvedTypeLayout<VectorUnsignedChar>()), typeLayoutT = PrimitiveLayouts.U8)
    return r.assumeInit()
}

public fun ConstPtr.vecXlFromConstPtrUByte(a: Int): VectorUnsignedChar = vecXlU8(a, this)

public fun vecXlI16(a: Int, b: ConstPtr): VectorSignedShort {
    val addr = ConstPtr(((ConstPtr(b.rawAddress, b.rawMeta))).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    val r = MaybeUninit.uninit(UnresolvedTypeLayout<VectorSignedShort>())
    copyNonoverlapping(addr, mutPtrFromRustCast(r.asMutPtr()), sizeOf<VectorSignedShort>(typeLayoutT = UnresolvedTypeLayout<VectorSignedShort>()), typeLayoutT = PrimitiveLayouts.U8)
    return r.assumeInit()
}

public fun ConstPtr.vecXlFromConstPtrShort(a: Int): VectorSignedShort = vecXlI16(a, this)

public fun vecXlU16(a: Int, b: ConstPtr): VectorUnsignedShort {
    val addr = ConstPtr(((ConstPtr(b.rawAddress, b.rawMeta))).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    val r = MaybeUninit.uninit(UnresolvedTypeLayout<VectorUnsignedShort>())
    copyNonoverlapping(addr, mutPtrFromRustCast(r.asMutPtr()), sizeOf<VectorUnsignedShort>(typeLayoutT = UnresolvedTypeLayout<VectorUnsignedShort>()), typeLayoutT = PrimitiveLayouts.U8)
    return r.assumeInit()
}

public fun ConstPtr.vecXlFromConstPtrUShort(a: Int): VectorUnsignedShort = vecXlU16(a, this)

public fun vecXlI32(a: Int, b: ConstPtr): VectorSignedInt {
    val addr = ConstPtr(((ConstPtr(b.rawAddress, b.rawMeta))).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    val r = MaybeUninit.uninit(UnresolvedTypeLayout<VectorSignedInt>())
    copyNonoverlapping(addr, mutPtrFromRustCast(r.asMutPtr()), sizeOf<VectorSignedInt>(typeLayoutT = UnresolvedTypeLayout<VectorSignedInt>()), typeLayoutT = PrimitiveLayouts.U8)
    return r.assumeInit()
}

public fun ConstPtr.vecXlFromConstPtrInt(a: Int): VectorSignedInt = vecXlI32(a, this)

public fun vecXlU32(a: Int, b: ConstPtr): VectorUnsignedInt {
    val addr = ConstPtr(((ConstPtr(b.rawAddress, b.rawMeta))).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    val r = MaybeUninit.uninit(UnresolvedTypeLayout<VectorUnsignedInt>())
    copyNonoverlapping(addr, mutPtrFromRustCast(r.asMutPtr()), sizeOf<VectorUnsignedInt>(typeLayoutT = UnresolvedTypeLayout<VectorUnsignedInt>()), typeLayoutT = PrimitiveLayouts.U8)
    return r.assumeInit()
}

public fun ConstPtr.vecXlFromConstPtrUInt(a: Int): VectorUnsignedInt = vecXlU32(a, this)

public fun vecXlF32(a: Int, b: ConstPtr): VectorFloat {
    val addr = ConstPtr(((ConstPtr(b.rawAddress, b.rawMeta))).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    val r = MaybeUninit.uninit(UnresolvedTypeLayout<VectorFloat>())
    copyNonoverlapping(addr, mutPtrFromRustCast(r.asMutPtr()), sizeOf<VectorFloat>(typeLayoutT = UnresolvedTypeLayout<VectorFloat>()), typeLayoutT = PrimitiveLayouts.U8)
    return r.assumeInit()
}

public fun ConstPtr.vecXlFromConstPtrFloat(a: Int): VectorFloat = vecXlF32(a, this)

public interface VectorXst<Out> {
    public fun vecXst(a: Int, p: Out)
}

public fun vecXstI8(s: VectorSignedChar, a: Int, b: MutPtr) {
    val addr = MutPtr(((MutPtr(b.rawAddress, b.rawMeta))).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    copyNonoverlapping((constPtrFromRustCast(s)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, addr, sizeOf<VectorSignedChar>(typeLayoutT = UnresolvedTypeLayout<VectorSignedChar>()), typeLayoutT = PrimitiveLayouts.U8)
}

public fun VectorSignedChar.vecXst(a: Int, b: MutPtr) {
    vecXstI8(this, a, b)
}

public fun vecXstU8(s: VectorUnsignedChar, a: Int, b: MutPtr) {
    val addr = MutPtr(((b)).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    copyNonoverlapping((constPtrFromRustCast(s)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, addr, sizeOf<VectorUnsignedChar>(typeLayoutT = UnresolvedTypeLayout<VectorUnsignedChar>()), typeLayoutT = PrimitiveLayouts.U8)
}

public fun VectorUnsignedChar.vecXst(a: Int, b: MutPtr) {
    vecXstU8(this, a, b)
}

public fun vecXstI16(s: VectorSignedShort, a: Int, b: MutPtr) {
    val addr = MutPtr(((MutPtr(b.rawAddress, b.rawMeta))).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    copyNonoverlapping((constPtrFromRustCast(s)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, addr, sizeOf<VectorSignedShort>(typeLayoutT = UnresolvedTypeLayout<VectorSignedShort>()), typeLayoutT = PrimitiveLayouts.U8)
}

public fun VectorSignedShort.vecXst(a: Int, b: MutPtr) {
    vecXstI16(this, a, b)
}

public fun vecXstU16(s: VectorUnsignedShort, a: Int, b: MutPtr) {
    val addr = MutPtr(((MutPtr(b.rawAddress, b.rawMeta))).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    copyNonoverlapping((constPtrFromRustCast(s)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, addr, sizeOf<VectorUnsignedShort>(typeLayoutT = UnresolvedTypeLayout<VectorUnsignedShort>()), typeLayoutT = PrimitiveLayouts.U8)
}

public fun VectorUnsignedShort.vecXst(a: Int, b: MutPtr) {
    vecXstU16(this, a, b)
}

public fun vecXstI32(s: VectorSignedInt, a: Int, b: MutPtr) {
    val addr = MutPtr(((MutPtr(b.rawAddress, b.rawMeta))).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    copyNonoverlapping((constPtrFromRustCast(s)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, addr, sizeOf<VectorSignedInt>(typeLayoutT = UnresolvedTypeLayout<VectorSignedInt>()), typeLayoutT = PrimitiveLayouts.U8)
}

public fun VectorSignedInt.vecXst(a: Int, b: MutPtr) {
    vecXstI32(this, a, b)
}

public fun vecXstU32(s: VectorUnsignedInt, a: Int, b: MutPtr) {
    val addr = MutPtr(((MutPtr(b.rawAddress, b.rawMeta))).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    copyNonoverlapping((constPtrFromRustCast(s)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, addr, sizeOf<VectorUnsignedInt>(typeLayoutT = UnresolvedTypeLayout<VectorUnsignedInt>()), typeLayoutT = PrimitiveLayouts.U8)
}

public fun VectorUnsignedInt.vecXst(a: Int, b: MutPtr) {
    vecXstU32(this, a, b)
}

public fun vecXstF32(s: VectorFloat, a: Int, b: MutPtr) {
    val addr = MutPtr(((MutPtr(b.rawAddress, b.rawMeta))).rawAddress + ((a).toLong() * (PrimitiveLayouts.U8).strideBytes))
    copyNonoverlapping((constPtrFromRustCast(s)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, addr, sizeOf<VectorFloat>(typeLayoutT = UnresolvedTypeLayout<VectorFloat>()), typeLayoutT = PrimitiveLayouts.U8)
}

public fun VectorFloat.vecXst(a: Int, b: MutPtr) {
    vecXstF32(this, a, b)
}

public fun vecFloor(a: VectorFloat): VectorFloat = run { val simdArg0 = a; simdArg0 }

public fun vecVexptefp(a: VectorFloat): VectorFloat = vexptefp(a)

public fun vecVcmpgtub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorBoolChar = vcmpgtub(a, b)

public fun vecVcmpgtuh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorBoolShort = vcmpgtuh(a, b)

public fun vecVcmpgtuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorBoolInt = vcmpgtuw(a, b)

public fun vecVcmpgtsb(a: VectorSignedChar, b: VectorSignedChar): VectorBoolChar = vcmpgtsb(a, b)

public fun vecVcmpgtsh(a: VectorSignedShort, b: VectorSignedShort): VectorBoolShort = vcmpgtsh(a, b)

public fun vecVcmpgtsw(a: VectorSignedInt, b: VectorSignedInt): VectorBoolInt = vcmpgtsw(a, b)

public interface VectorCmpGt<Other, out Result> {
    public fun vecCmpgt(b: Other): Result
}

public fun VectorUnsignedChar.vecCmpgt(b: VectorUnsignedChar): VectorBoolChar = vecVcmpgtub(this, b)

public fun VectorSignedChar.vecCmpgt(b: VectorSignedChar): VectorBoolChar = vecVcmpgtsb(this, b)

public fun VectorUnsignedShort.vecCmpgt(b: VectorUnsignedShort): VectorBoolShort = vecVcmpgtuh(this, b)

public fun VectorSignedShort.vecCmpgt(b: VectorSignedShort): VectorBoolShort = vecVcmpgtsh(this, b)

public fun VectorUnsignedInt.vecCmpgt(b: VectorUnsignedInt): VectorBoolInt = vecVcmpgtuw(this, b)

public fun VectorSignedInt.vecCmpgt(b: VectorSignedInt): VectorBoolInt = vecVcmpgtsw(this, b)

public fun vecVcmpgefp(a: VectorFloat, b: VectorFloat): VectorBoolInt = vcmpgefp(a, b)

public fun vecVcmpequb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorBoolChar = vcmpequb(a, b)

public fun vecVcmpequh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorBoolShort = vcmpequh(a, b)

public fun vecVcmpequw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorBoolInt = vcmpequw(a, b)

public interface VectorCmpEq<Other, out Result> {
    public fun vecCmpeq(b: Other): Result
}

public fun VectorUnsignedChar.vecCmpeq(b: VectorUnsignedChar): VectorBoolChar = vecVcmpequb(this, b)

public fun VectorSignedChar.vecCmpeq(b: VectorSignedChar): VectorBoolChar = vecVcmpequb(transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()))

public fun VectorUnsignedShort.vecCmpeq(b: VectorUnsignedShort): VectorBoolShort = vecVcmpequh(this, b)

public fun VectorSignedShort.vecCmpeq(b: VectorSignedShort): VectorBoolShort = vecVcmpequh(transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()))

public fun VectorUnsignedInt.vecCmpeq(b: VectorUnsignedInt): VectorBoolInt = vecVcmpequw(this, b)

public fun VectorSignedInt.vecCmpeq(b: VectorSignedInt): VectorBoolInt = vecVcmpequw(transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()))

public fun vecVcmpneb(a: VectorSignedChar, b: VectorSignedChar): VectorBoolChar =
    if (false) {
        vcmpneb(transmute(a), transmute(b))
    } else {
        val zero = transmuteWithLayouts<Any?, VectorBoolChar>(Simd<Int>(listOf(0, 0, 0, 0)), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolChar>())
        vecNor(vecCmpeq(a, b), zero)
    }

public fun vecVcmpneh(a: VectorSignedShort, b: VectorSignedShort): VectorBoolShort =
    if (false) {
        vcmpneh(transmute(a), transmute(b))
    } else {
        val zero = transmuteWithLayouts<Any?, VectorBoolShort>(Simd<Int>(listOf(0, 0, 0, 0)), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolShort>())
        vecNor(vecCmpeq(a, b), zero)
    }

public fun vecVcmpnew(a: VectorSignedInt, b: VectorSignedInt): VectorBoolInt =
    if (false) {
        vcmpnew(transmute(a), transmute(b))
    } else {
        val zero = transmuteWithLayouts<Any?, VectorBoolInt>(Simd<Int>(listOf(0, 0, 0, 0)), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolInt>())
        vecNor(vecCmpeq(a, b), zero)
    }

public interface VectorCmpNe<Other, out Result> {
    public fun vecCmpne(b: Other): Result
}

public fun VectorUnsignedChar.vecCmpne(b: VectorUnsignedChar): VectorBoolChar = vecVcmpneb(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()))

public fun VectorSignedChar.vecCmpne(b: VectorSignedChar): VectorBoolChar = vecVcmpneb(this, b)

public fun VectorUnsignedShort.vecCmpne(b: VectorUnsignedShort): VectorBoolShort = vecVcmpneh(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()))

public fun VectorSignedShort.vecCmpne(b: VectorSignedShort): VectorBoolShort = vecVcmpneh(this, b)

public fun VectorUnsignedInt.vecCmpne(b: VectorUnsignedInt): VectorBoolInt = vecVcmpnew(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()))

public fun VectorSignedInt.vecCmpne(b: VectorSignedInt): VectorBoolInt = vecVcmpnew(this, b)

public fun vecVcmpbfp(a: VectorFloat, b: VectorFloat): VectorSignedInt = vcmpbfp(a, b)

public fun vcmpequbAll(a: VectorUnsignedChar, b: VectorUnsignedChar): Boolean = vcmpequbP(2, a, b) != 0

public fun vcmpequbAny(a: VectorUnsignedChar, b: VectorUnsignedChar): Boolean = vcmpequbP(1, a, b) != 0

public fun vcmpequhAll(a: VectorUnsignedShort, b: VectorUnsignedShort): Boolean = vcmpequhP(2, a, b) != 0

public fun vcmpequhAny(a: VectorUnsignedShort, b: VectorUnsignedShort): Boolean = vcmpequhP(1, a, b) != 0

public fun vcmpequwAll(a: VectorUnsignedInt, b: VectorUnsignedInt): Boolean = vcmpequwP(2, a, b) != 0

public fun vcmpequwAny(a: VectorUnsignedInt, b: VectorUnsignedInt): Boolean = vcmpequwP(1, a, b) != 0

public interface VectorAllEq<Other, out Result> {
    public fun vecAllEq(b: Other): Result
}

public fun VectorUnsignedChar.vecAllEq(b: VectorUnsignedChar): Boolean = vcmpequbAll(this, b)

public fun VectorSignedChar.vecAllEq(b: VectorSignedChar): Boolean = vcmpequbAll(transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()))

public fun VectorUnsignedShort.vecAllEq(b: VectorUnsignedShort): Boolean = vcmpequhAll(this, b)

public fun VectorSignedShort.vecAllEq(b: VectorSignedShort): Boolean = vcmpequhAll(transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()))

public fun VectorUnsignedInt.vecAllEq(b: VectorUnsignedInt): Boolean = vcmpequwAll(this, b)

public fun VectorSignedInt.vecAllEq(b: VectorSignedInt): Boolean = vcmpequwAll(transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()))

// Note: vsx encoding
public fun vcmpeqfpAll(a: VectorFloat, b: VectorFloat): Boolean = vcmpeqfpP(2, a, b) != 0

public fun VectorFloat.vecAllEq(b: VectorFloat): Boolean = vcmpeqfpAll(this, b)

public interface VectorAnyEq<Other, out Result> {
    public fun vecAnyEq(b: Other): Result
}

public fun VectorUnsignedChar.vecAnyEq(b: VectorUnsignedChar): Boolean = vcmpequbAny(this, b)

public fun VectorSignedChar.vecAnyEq(b: VectorSignedChar): Boolean = vcmpequbAny(transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()))

public fun VectorUnsignedShort.vecAnyEq(b: VectorUnsignedShort): Boolean = vcmpequhAny(this, b)

public fun VectorSignedShort.vecAnyEq(b: VectorSignedShort): Boolean = vcmpequhAny(transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()))

public fun VectorUnsignedInt.vecAnyEq(b: VectorUnsignedInt): Boolean = vcmpequwAny(this, b)

public fun VectorSignedInt.vecAnyEq(b: VectorSignedInt): Boolean = vcmpequwAny(transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()))

public fun vcmpeqfpAny(a: VectorFloat, b: VectorFloat): Boolean = vcmpeqfpP(1, a, b) != 0

public fun VectorFloat.vecAnyEq(b: VectorFloat): Boolean = vcmpeqfpAny(this, b)

// All/Any GreaterEqual
public fun vcmpgesbAll(a: VectorSignedChar, b: VectorSignedChar): Boolean = vcmpgtsbP(0, b, a) != 0

public fun vcmpgesbAny(a: VectorSignedChar, b: VectorSignedChar): Boolean = vcmpgtsbP(3, b, a) != 0

public fun vcmpgeshAll(a: VectorSignedShort, b: VectorSignedShort): Boolean = vcmpgtshP(0, b, a) != 0

public fun vcmpgeshAny(a: VectorSignedShort, b: VectorSignedShort): Boolean = vcmpgtshP(3, b, a) != 0

public fun vcmpgeswAll(a: VectorSignedInt, b: VectorSignedInt): Boolean = vcmpgtswP(0, b, a) != 0

public fun vcmpgeswAny(a: VectorSignedInt, b: VectorSignedInt): Boolean = vcmpgtswP(3, b, a) != 0

public fun vcmpgeubAll(a: VectorUnsignedChar, b: VectorUnsignedChar): Boolean = vcmpgtubP(0, b, a) != 0

public fun vcmpgeubAny(a: VectorUnsignedChar, b: VectorUnsignedChar): Boolean = vcmpgtubP(3, b, a) != 0

public fun vcmpgeuhAll(a: VectorUnsignedShort, b: VectorUnsignedShort): Boolean = vcmpgtuhP(0, b, a) != 0

public fun vcmpgeuhAny(a: VectorUnsignedShort, b: VectorUnsignedShort): Boolean = vcmpgtuhP(3, b, a) != 0

public fun vcmpgeuwAll(a: VectorUnsignedInt, b: VectorUnsignedInt): Boolean = vcmpgtuwP(0, b, a) != 0

public fun vcmpgeuwAny(a: VectorUnsignedInt, b: VectorUnsignedInt): Boolean = vcmpgtuwP(3, b, a) != 0

public interface VectorAllGe<Other, out Result> {
    public fun vecAllGe(b: Other): Result
}

public fun VectorUnsignedChar.vecAllGe(b: VectorUnsignedChar): Boolean = vcmpgeubAll(this, b)

public fun VectorSignedChar.vecAllGe(b: VectorSignedChar): Boolean = vcmpgesbAll(this, b)

public fun VectorUnsignedShort.vecAllGe(b: VectorUnsignedShort): Boolean = vcmpgeuhAll(this, b)

public fun VectorSignedShort.vecAllGe(b: VectorSignedShort): Boolean = vcmpgeshAll(this, b)

public fun VectorUnsignedInt.vecAllGe(b: VectorUnsignedInt): Boolean = vcmpgeuwAll(this, b)

public fun VectorSignedInt.vecAllGe(b: VectorSignedInt): Boolean = vcmpgeswAll(this, b)

// Note: vsx encoding
public fun vcmpgefpAll(a: VectorFloat, b: VectorFloat): Boolean = vcmpgefpP(2, a, b) != 0

public fun VectorFloat.vecAllGe(b: VectorFloat): Boolean = vcmpgefpAll(this, b)

public interface VectorAnyGe<Other, out Result> {
    public fun vecAnyGe(b: Other): Result
}

public fun VectorUnsignedChar.vecAnyGe(b: VectorUnsignedChar): Boolean = vcmpgeubAny(this, b)

public fun VectorSignedChar.vecAnyGe(b: VectorSignedChar): Boolean = vcmpgesbAny(this, b)

public fun VectorUnsignedShort.vecAnyGe(b: VectorUnsignedShort): Boolean = vcmpgeuhAny(this, b)

public fun VectorSignedShort.vecAnyGe(b: VectorSignedShort): Boolean = vcmpgeshAny(this, b)

public fun VectorUnsignedInt.vecAnyGe(b: VectorUnsignedInt): Boolean = vcmpgeuwAny(this, b)

public fun VectorSignedInt.vecAnyGe(b: VectorSignedInt): Boolean = vcmpgeswAny(this, b)

public fun vcmpgefpAny(a: VectorFloat, b: VectorFloat): Boolean = vcmpgefpP(1, a, b) != 0

public fun VectorFloat.vecAnyGe(b: VectorFloat): Boolean = vcmpgefpAny(this, b)

// All/Any Greater Than
public fun vcmpgtsbAll(a: VectorSignedChar, b: VectorSignedChar): Boolean = vcmpgtsbP(2, a, b) != 0

public fun vcmpgtsbAny(a: VectorSignedChar, b: VectorSignedChar): Boolean = vcmpgtsbP(1, a, b) != 0

public fun vcmpgtshAll(a: VectorSignedShort, b: VectorSignedShort): Boolean = vcmpgtshP(2, a, b) != 0

public fun vcmpgtshAny(a: VectorSignedShort, b: VectorSignedShort): Boolean = vcmpgtshP(1, a, b) != 0

public fun vcmpgtswAll(a: VectorSignedInt, b: VectorSignedInt): Boolean = vcmpgtswP(2, a, b) != 0

public fun vcmpgtswAny(a: VectorSignedInt, b: VectorSignedInt): Boolean = vcmpgtswP(1, a, b) != 0

public fun vcmpgtubAll(a: VectorUnsignedChar, b: VectorUnsignedChar): Boolean = vcmpgtubP(2, a, b) != 0

public fun vcmpgtubAny(a: VectorUnsignedChar, b: VectorUnsignedChar): Boolean = vcmpgtubP(1, a, b) != 0

public fun vcmpgtuhAll(a: VectorUnsignedShort, b: VectorUnsignedShort): Boolean = vcmpgtuhP(2, a, b) != 0

public fun vcmpgtuhAny(a: VectorUnsignedShort, b: VectorUnsignedShort): Boolean = vcmpgtuhP(1, a, b) != 0

public fun vcmpgtuwAll(a: VectorUnsignedInt, b: VectorUnsignedInt): Boolean = vcmpgtuwP(2, a, b) != 0

public fun vcmpgtuwAny(a: VectorUnsignedInt, b: VectorUnsignedInt): Boolean = vcmpgtuwP(1, a, b) != 0

public interface VectorAllGt<Other, out Result> {
    public fun vecAllGt(b: Other): Result
}

public fun VectorUnsignedChar.vecAllGt(b: VectorUnsignedChar): Boolean = vcmpgtubAll(this, b)

public fun VectorSignedChar.vecAllGt(b: VectorSignedChar): Boolean = vcmpgtsbAll(this, b)

public fun VectorUnsignedShort.vecAllGt(b: VectorUnsignedShort): Boolean = vcmpgtuhAll(this, b)

public fun VectorSignedShort.vecAllGt(b: VectorSignedShort): Boolean = vcmpgtshAll(this, b)

public fun VectorUnsignedInt.vecAllGt(b: VectorUnsignedInt): Boolean = vcmpgtuwAll(this, b)

public fun VectorSignedInt.vecAllGt(b: VectorSignedInt): Boolean = vcmpgtswAll(this, b)

// Note: vsx encoding
public fun vcmpgtfpAll(a: VectorFloat, b: VectorFloat): Boolean = vcmpgtfpP(2, a, b) != 0

public fun VectorFloat.vecAllGt(b: VectorFloat): Boolean = vcmpgtfpAll(this, b)

public interface VectorAnyGt<Other, out Result> {
    public fun vecAnyGt(b: Other): Result
}

public fun VectorUnsignedChar.vecAnyGt(b: VectorUnsignedChar): Boolean = vcmpgtubAny(this, b)

public fun VectorSignedChar.vecAnyGt(b: VectorSignedChar): Boolean = vcmpgtsbAny(this, b)

public fun VectorUnsignedShort.vecAnyGt(b: VectorUnsignedShort): Boolean = vcmpgtuhAny(this, b)

public fun VectorSignedShort.vecAnyGt(b: VectorSignedShort): Boolean = vcmpgtshAny(this, b)

public fun VectorUnsignedInt.vecAnyGt(b: VectorUnsignedInt): Boolean = vcmpgtuwAny(this, b)

public fun VectorSignedInt.vecAnyGt(b: VectorSignedInt): Boolean = vcmpgtswAny(this, b)

public fun vcmpgtfpAny(a: VectorFloat, b: VectorFloat): Boolean = vcmpgtfpP(1, a, b) != 0

public fun VectorFloat.vecAnyGt(b: VectorFloat): Boolean = vcmpgtfpAny(this, b)

// All/Any Elements Not Equal
public fun vcmpneubAll(a: VectorUnsignedChar, b: VectorUnsignedChar): Boolean = vcmpequbP(0, a, b) != 0

public fun vcmpneubAny(a: VectorUnsignedChar, b: VectorUnsignedChar): Boolean = vcmpequbP(3, a, b) != 0

public fun vcmpneuhAll(a: VectorUnsignedShort, b: VectorUnsignedShort): Boolean = vcmpequhP(0, a, b) != 0

public fun vcmpneuhAny(a: VectorUnsignedShort, b: VectorUnsignedShort): Boolean = vcmpequhP(3, a, b) != 0

public fun vcmpneuwAll(a: VectorUnsignedInt, b: VectorUnsignedInt): Boolean = vcmpequwP(0, a, b) != 0

public fun vcmpneuwAny(a: VectorUnsignedInt, b: VectorUnsignedInt): Boolean = vcmpequwP(3, a, b) != 0

public interface VectorAllNe<Other, out Result> {
    public fun vecAllNe(b: Other): Result
}

public fun VectorUnsignedChar.vecAllNe(b: VectorUnsignedChar): Boolean = vcmpneubAll(this, b)

public fun VectorSignedChar.vecAllNe(b: VectorSignedChar): Boolean = vcmpneubAll(transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()))

public fun VectorUnsignedShort.vecAllNe(b: VectorUnsignedShort): Boolean = vcmpneuhAll(this, b)

public fun VectorSignedShort.vecAllNe(b: VectorSignedShort): Boolean = vcmpneuhAll(transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()))

public fun VectorUnsignedInt.vecAllNe(b: VectorUnsignedInt): Boolean = vcmpneuwAll(this, b)

public fun VectorSignedInt.vecAllNe(b: VectorSignedInt): Boolean = vcmpneuwAll(transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()))

// Note: vsx encoding
public fun vcmpnefpAll(a: VectorFloat, b: VectorFloat): Boolean = vcmpeqfpP(0, a, b) != 0

public fun VectorFloat.vecAllNe(b: VectorFloat): Boolean = vcmpnefpAll(this, b)

public interface VectorAnyNe<Other, out Result> {
    public fun vecAnyNe(b: Other): Result
}

public fun VectorUnsignedChar.vecAnyNe(b: VectorUnsignedChar): Boolean = vcmpneubAny(this, b)

public fun VectorSignedChar.vecAnyNe(b: VectorSignedChar): Boolean = vcmpneubAny(transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()))

public fun VectorUnsignedShort.vecAnyNe(b: VectorUnsignedShort): Boolean = vcmpneuhAny(this, b)

public fun VectorSignedShort.vecAnyNe(b: VectorSignedShort): Boolean = vcmpneuhAny(transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()))

public fun VectorUnsignedInt.vecAnyNe(b: VectorUnsignedInt): Boolean = vcmpneuwAny(this, b)

public fun VectorSignedInt.vecAnyNe(b: VectorSignedInt): Boolean = vcmpneuwAny(transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()))

public fun vcmpnefpAny(a: VectorFloat, b: VectorFloat): Boolean = vcmpeqfpP(3, a, b) != 0

public fun VectorFloat.vecAnyNe(b: VectorFloat): Boolean = vcmpnefpAny(this, b)

public fun vecVceil(a: VectorFloat): VectorFloat = run { val simdArg0 = a; simdArg0 }

public fun vecVavgsb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = vavgsb(a, b)

public fun vecVavgsh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = vavgsh(a, b)

public fun vecVavgsw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = vavgsw(a, b)

public fun vecVavgub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = vavgub(a, b)

public fun vecVavguh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = vavguh(a, b)

public fun vecVavguw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = vavguw(a, b)

public interface VectorAvg<Other, out Result> {
    public fun vecAvg(b: Other): Result
}

public fun VectorUnsignedChar.vecAvg(b: VectorUnsignedChar): VectorUnsignedChar = vecVavgub(this, b)

public fun VectorSignedChar.vecAvg(b: VectorSignedChar): VectorSignedChar = vecVavgsb(this, b)

public fun VectorUnsignedShort.vecAvg(b: VectorUnsignedShort): VectorUnsignedShort = vecVavguh(this, b)

public fun VectorSignedShort.vecAvg(b: VectorSignedShort): VectorSignedShort = vecVavgsh(this, b)

public fun VectorUnsignedInt.vecAvg(b: VectorUnsignedInt): VectorUnsignedInt = vecVavguw(this, b)

public fun VectorSignedInt.vecAvg(b: VectorSignedInt): VectorSignedInt = vecVavgsw(this, b)

public fun andc(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val a1 = a
    val b1 = b
    return transmuteWithLayouts<Any?, VectorSignedChar>(run { val simdArg0 = run { val simdArg0 = Simd<UByte>(List(16) { 0xffu.toUByte() }); val simdArg1 = b1; simdArg0 }; val simdArg1 = a1; simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>())
}

public interface VectorAndc<Other, out Result> {
    public fun vecAndc(b: Other): Result
}

public fun VectorBoolChar.vecAndc(b: VectorBoolChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(andc(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorUnsignedChar.vecAndc(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(andc(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecAndc(b: VectorSignedChar): VectorSignedChar = andc(this, b)

public fun VectorBoolShort.vecAndc(b: VectorBoolShort): VectorBoolShort = transmuteWithLayouts<VectorSignedChar, VectorBoolShort>(andc(transmuteWithLayouts<VectorBoolShort, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolShort, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorUnsignedShort.vecAndc(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(andc(transmuteWithLayouts<VectorUnsignedShort, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecAndc(b: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(andc(transmuteWithLayouts<VectorSignedShort, VectorSignedChar>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorSignedShort, VectorSignedChar>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorBoolInt.vecAndc(b: VectorBoolInt): VectorBoolInt = transmuteWithLayouts<VectorSignedChar, VectorBoolInt>(andc(transmuteWithLayouts<VectorBoolInt, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolInt, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorUnsignedInt.vecAndc(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(andc(transmuteWithLayouts<VectorUnsignedInt, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecAndc(b: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(andc(transmuteWithLayouts<VectorSignedInt, VectorSignedChar>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorSignedInt, VectorSignedChar>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun orc(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val a1 = a
    val b1 = b
    return transmuteWithLayouts<Any?, VectorSignedChar>(run { val simdArg0 = run { val simdArg0 = Simd<UByte>(List(16) { 0xffu.toUByte() }); val simdArg1 = b1; simdArg0 }; val simdArg1 = a1; simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>())
}

public interface VectorOrc<Other, out Result> {
    public fun vecOrc(b: Other): Result
}

public fun VectorBoolChar.vecOrc(b: VectorBoolChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(orc(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorUnsignedChar.vecOrc(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(orc(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecOrc(b: VectorSignedChar): VectorSignedChar = orc(this, b)

public fun VectorBoolShort.vecOrc(b: VectorBoolShort): VectorBoolShort = transmuteWithLayouts<VectorSignedChar, VectorBoolShort>(orc(transmuteWithLayouts<VectorBoolShort, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolShort, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorUnsignedShort.vecOrc(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(orc(transmuteWithLayouts<VectorUnsignedShort, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecOrc(b: VectorSignedShort): VectorSignedShort = transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(orc(transmuteWithLayouts<VectorSignedShort, VectorSignedChar>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorSignedShort, VectorSignedChar>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorBoolInt.vecOrc(b: VectorBoolInt): VectorBoolInt = transmuteWithLayouts<VectorSignedChar, VectorBoolInt>(orc(transmuteWithLayouts<VectorBoolInt, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolInt, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorUnsignedInt.vecOrc(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(orc(transmuteWithLayouts<VectorUnsignedInt, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecOrc(b: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(orc(transmuteWithLayouts<VectorSignedInt, VectorSignedChar>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorSignedInt, VectorSignedChar>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun vecVand(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public interface VectorAnd<Other, out Result> {
    public fun vecAnd(b: Other): Result
}

public fun VectorUnsignedChar.vecAnd(b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedChar.vecAnd(b: VectorBoolChar): VectorUnsignedChar = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()); simdArg0 }

public fun VectorBoolChar.vecAnd(b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()); val simdArg1 = b; simdArg0 }

public fun VectorSignedChar.vecAnd(b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorSignedChar.vecAnd(b: VectorBoolChar): VectorSignedChar = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()); simdArg0 }

public fun VectorBoolChar.vecAnd(b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()); val simdArg1 = b; simdArg0 }

public fun VectorUnsignedShort.vecAnd(b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedShort.vecAnd(b: VectorBoolShort): VectorUnsignedShort = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()); simdArg0 }

public fun VectorBoolShort.vecAnd(b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()); val simdArg1 = b; simdArg0 }

public fun VectorSignedShort.vecAnd(b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorSignedShort.vecAnd(b: VectorBoolShort): VectorSignedShort = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()); simdArg0 }

public fun VectorBoolShort.vecAnd(b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()); val simdArg1 = b; simdArg0 }

public fun VectorUnsignedInt.vecAnd(b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedInt.vecAnd(b: VectorBoolInt): VectorUnsignedInt = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()); simdArg0 }

public fun VectorBoolInt.vecAnd(b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()); val simdArg1 = b; simdArg0 }

public fun VectorSignedInt.vecAnd(b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorSignedInt.vecAnd(b: VectorBoolInt): VectorSignedInt = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()); simdArg0 }

public fun VectorBoolInt.vecAnd(b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()); val simdArg1 = b; simdArg0 }

public fun vecVaddsbs(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = vaddsbs(a, b)

public fun vecVaddshs(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = vaddshs(a, b)

public fun vecVaddsws(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = vaddsws(a, b)

public fun vecVaddubs(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = vaddubs(a, b)

public fun vecVadduhs(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = vadduhs(a, b)

public fun vecVadduws(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = vadduws(a, b)

public interface VectorAdds<Other, out Result> {
    public fun vecAdds(b: Other): Result
}

public fun VectorUnsignedChar.vecAdds(b: VectorUnsignedChar): VectorUnsignedChar = vaddubs(transmute(this), transmute(b))

public fun VectorUnsignedChar.vecAdds(b: VectorBoolChar): VectorUnsignedChar = vaddubs(transmute(this), transmute(b))

public fun VectorBoolChar.vecAdds(b: VectorUnsignedChar): VectorUnsignedChar = vaddubs(transmute(this), transmute(b))

public fun VectorSignedChar.vecAdds(b: VectorSignedChar): VectorSignedChar = vaddsbs(transmute(this), transmute(b))

public fun VectorSignedChar.vecAdds(b: VectorBoolChar): VectorSignedChar = vaddsbs(transmute(this), transmute(b))

public fun VectorBoolChar.vecAdds(b: VectorSignedChar): VectorSignedChar = vaddsbs(transmute(this), transmute(b))

public fun VectorUnsignedShort.vecAdds(b: VectorUnsignedShort): VectorUnsignedShort = vadduhs(transmute(this), transmute(b))

public fun VectorUnsignedShort.vecAdds(b: VectorBoolShort): VectorUnsignedShort = vadduhs(transmute(this), transmute(b))

public fun VectorBoolShort.vecAdds(b: VectorUnsignedShort): VectorUnsignedShort = vadduhs(transmute(this), transmute(b))

public fun VectorSignedShort.vecAdds(b: VectorSignedShort): VectorSignedShort = vaddshs(transmute(this), transmute(b))

public fun VectorSignedShort.vecAdds(b: VectorBoolShort): VectorSignedShort = vaddshs(transmute(this), transmute(b))

public fun VectorBoolShort.vecAdds(b: VectorSignedShort): VectorSignedShort = vaddshs(transmute(this), transmute(b))

public fun VectorUnsignedInt.vecAdds(b: VectorUnsignedInt): VectorUnsignedInt = vadduws(transmute(this), transmute(b))

public fun VectorUnsignedInt.vecAdds(b: VectorBoolInt): VectorUnsignedInt = vadduws(transmute(this), transmute(b))

public fun VectorBoolInt.vecAdds(b: VectorUnsignedInt): VectorUnsignedInt = vadduws(transmute(this), transmute(b))

public fun VectorSignedInt.vecAdds(b: VectorSignedInt): VectorSignedInt = vaddsws(transmute(this), transmute(b))

public fun VectorSignedInt.vecAdds(b: VectorBoolInt): VectorSignedInt = vaddsws(transmute(this), transmute(b))

public fun VectorBoolInt.vecAdds(b: VectorSignedInt): VectorSignedInt = vaddsws(transmute(this), transmute(b))

public fun vecVaddcuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = vaddcuw(a, b)

public fun vecVsubsbs(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = vsubsbs(a, b)

public fun vecVsubshs(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = vsubshs(a, b)

public fun vecVsubsws(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = vsubsws(a, b)

public fun vecVsububs(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = vsububs(a, b)

public fun vecVsubuhs(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = vsubuhs(a, b)

public fun vecVsubuws(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = vsubuws(a, b)

public interface VectorSubs<Other, out Result> {
    public fun vecSubs(b: Other): Result
}

public fun VectorUnsignedChar.vecSubs(b: VectorUnsignedChar): VectorUnsignedChar = vsububs(transmute(this), transmute(b))

public fun VectorUnsignedChar.vecSubs(b: VectorBoolChar): VectorUnsignedChar = vsububs(transmute(this), transmute(b))

public fun VectorBoolChar.vecSubs(b: VectorUnsignedChar): VectorUnsignedChar = vsububs(transmute(this), transmute(b))

public fun VectorSignedChar.vecSubs(b: VectorSignedChar): VectorSignedChar = vsubsbs(transmute(this), transmute(b))

public fun VectorSignedChar.vecSubs(b: VectorBoolChar): VectorSignedChar = vsubsbs(transmute(this), transmute(b))

public fun VectorBoolChar.vecSubs(b: VectorSignedChar): VectorSignedChar = vsubsbs(transmute(this), transmute(b))

public fun VectorUnsignedShort.vecSubs(b: VectorUnsignedShort): VectorUnsignedShort = vsubuhs(transmute(this), transmute(b))

public fun VectorUnsignedShort.vecSubs(b: VectorBoolShort): VectorUnsignedShort = vsubuhs(transmute(this), transmute(b))

public fun VectorBoolShort.vecSubs(b: VectorUnsignedShort): VectorUnsignedShort = vsubuhs(transmute(this), transmute(b))

public fun VectorSignedShort.vecSubs(b: VectorSignedShort): VectorSignedShort = vsubshs(transmute(this), transmute(b))

public fun VectorSignedShort.vecSubs(b: VectorBoolShort): VectorSignedShort = vsubshs(transmute(this), transmute(b))

public fun VectorBoolShort.vecSubs(b: VectorSignedShort): VectorSignedShort = vsubshs(transmute(this), transmute(b))

public fun VectorUnsignedInt.vecSubs(b: VectorUnsignedInt): VectorUnsignedInt = vsubuws(transmute(this), transmute(b))

public fun VectorUnsignedInt.vecSubs(b: VectorBoolInt): VectorUnsignedInt = vsubuws(transmute(this), transmute(b))

public fun VectorBoolInt.vecSubs(b: VectorUnsignedInt): VectorUnsignedInt = vsubuws(transmute(this), transmute(b))

public fun VectorSignedInt.vecSubs(b: VectorSignedInt): VectorSignedInt = vsubsws(transmute(this), transmute(b))

public fun VectorSignedInt.vecSubs(b: VectorBoolInt): VectorSignedInt = vsubsws(transmute(this), transmute(b))

public fun VectorBoolInt.vecSubs(b: VectorSignedInt): VectorSignedInt = vsubsws(transmute(this), transmute(b))

public interface VectorAbs<T> {
    public fun vecAbs(): T
}

public fun vecAbsI8(v: VectorSignedChar): VectorSignedChar = v.vecMax(run { val simdArg0 = v; simdArg0 })

public fun VectorSignedChar.vecAbs(): VectorSignedChar = vecAbsI8(this)

public fun vecAbsI16(v: VectorSignedShort): VectorSignedShort = v.vecMax(run { val simdArg0 = v; simdArg0 })

public fun VectorSignedShort.vecAbs(): VectorSignedShort = vecAbsI16(this)

public fun vecAbsI32(v: VectorSignedInt): VectorSignedInt = v.vecMax(run { val simdArg0 = v; simdArg0 })

public fun VectorSignedInt.vecAbs(): VectorSignedInt = vecAbsI32(this)

public fun vecAbsF32(v: VectorFloat): VectorFloat {
    val v1: U32x4 = transmuteWithLayouts<VectorFloat, U32x4>(v, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<U32x4>())
    return transmuteWithLayouts<U32x4, VectorFloat>(run { val simdArg0 = v1; val simdArg1 = Simd<UInt>(List(4) { 0x7FFFFFFFu }); simdArg0 }, UnresolvedTypeLayout<U32x4>(), UnresolvedTypeLayout<VectorFloat>())
}

public fun VectorFloat.vecAbs(): VectorFloat = vecAbsF32(this)

public interface VectorAbss<T> {
    public fun vecAbss(): T
}

public fun vecAbssI8(v: VectorSignedChar): VectorSignedChar {
    val zero: VectorSignedChar = transmuteWithLayouts<Any?, VectorSignedChar>(0u.toUByte().vecSplats(), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>())
    return v.vecMax(zero.vecSubs(v))
}

public fun VectorSignedChar.vecAbss(): VectorSignedChar = vecAbssI8(this)

public fun vecAbssI16(v: VectorSignedShort): VectorSignedShort {
    val zero: VectorSignedShort = transmuteWithLayouts<Any?, VectorSignedShort>(0u.toUByte().vecSplats(), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedShort>())
    return v.vecMax(zero.vecSubs(v))
}

public fun VectorSignedShort.vecAbss(): VectorSignedShort = vecAbssI16(this)

public fun vecAbssI32(v: VectorSignedInt): VectorSignedInt {
    val zero: VectorSignedInt = transmuteWithLayouts<Any?, VectorSignedInt>(0u.toUByte().vecSplats(), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>())
    return v.vecMax(zero.vecSubs(v))
}

public fun VectorSignedInt.vecAbss(): VectorSignedInt = vecAbssI32(this)

public fun vspltb(a: VectorSignedChar, imm4: UInt): VectorSignedChar {
    run {
        check(0u <= imm4 && imm4 < (1u shl 4)) { "IMM4 doesn't fit in 4 bits (unsigned)" }
    }
    return run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = Simd<UInt>(List(16) { imm4 }); VectorSignedChar() }
}

public fun vsplth(a: VectorSignedShort, imm3: UInt): VectorSignedShort {
    run {
        check(0u <= imm3 && imm3 < (1u shl 3)) { "IMM3 doesn't fit in 3 bits (unsigned)" }
    }
    return run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = Simd<UInt>(List(8) { imm3 }); VectorSignedShort() }
}

public fun vspltw(a: VectorSignedInt, imm2: UInt): VectorSignedInt {
    run {
        check(0u <= imm2 && imm2 < (1u shl 2)) { "IMM2 doesn't fit in 2 bits (unsigned)" }
    }
    return run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = Simd<UInt>(List(4) { imm2 }); VectorSignedInt() }
}

public interface VectorSplat<T> {
    public fun vecSplat(imm: UInt): T
}

public fun VectorSignedChar.vecSplat(imm: UInt): VectorSignedChar = transmuteWithLayouts<Any?, VectorSignedChar>(vspltb(this, imm), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorUnsignedChar.vecSplat(imm: UInt): VectorUnsignedChar = transmuteWithLayouts<Any?, VectorUnsignedChar>(vspltb(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), imm), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorBoolChar.vecSplat(imm: UInt): VectorBoolChar = transmuteWithLayouts<Any?, VectorBoolChar>(vspltb(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), imm), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorSignedShort.vecSplat(imm: UInt): VectorSignedShort = transmuteWithLayouts<Any?, VectorSignedShort>(vsplth(this, imm), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedShort.vecSplat(imm: UInt): VectorUnsignedShort = transmuteWithLayouts<Any?, VectorUnsignedShort>(vsplth(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), imm), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorBoolShort.vecSplat(imm: UInt): VectorBoolShort = transmuteWithLayouts<Any?, VectorBoolShort>(vsplth(transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), imm), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorSignedInt.vecSplat(imm: UInt): VectorSignedInt = transmuteWithLayouts<Any?, VectorSignedInt>(vspltw(this, imm), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedInt.vecSplat(imm: UInt): VectorUnsignedInt = transmuteWithLayouts<Any?, VectorUnsignedInt>(vspltw(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), imm), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorBoolInt.vecSplat(imm: UInt): VectorBoolInt = transmuteWithLayouts<Any?, VectorBoolInt>(vspltw(transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), imm), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun splatsU8(v: UByte): VectorUnsignedChar = transmuteWithLayouts<Any?, VectorUnsignedChar>(Simd<UByte>(List(16) { v }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun splatsU16(v: UShort): VectorUnsignedShort = transmuteWithLayouts<Any?, VectorUnsignedShort>(Simd<UShort>(List(8) { v }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun splatsU32(v: UInt): VectorUnsignedInt = transmuteWithLayouts<Any?, VectorUnsignedInt>(Simd<UInt>(List(4) { v }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun splatsI8(v: Byte): VectorSignedChar = transmuteWithLayouts<Any?, VectorSignedChar>(Simd<Byte>(List(16) { v }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun splatsI16(v: Short): VectorSignedShort = transmuteWithLayouts<Any?, VectorSignedShort>(Simd<Short>(List(8) { v }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun splatsI32(v: Int): VectorSignedInt = transmuteWithLayouts<Any?, VectorSignedInt>(Simd<Int>(List(4) { v }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun splatsF32(v: Float): VectorFloat = transmuteWithLayouts<Any?, VectorFloat>(Simd<Float>(List(4) { v }), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorFloat>())

public fun vecSplatsU8(v: UByte): VectorUnsignedChar = splatsU8(v)

public fun vecSplatsU16(v: UShort): VectorUnsignedShort = splatsU16(v)

public fun vecSplatsU32(v: UInt): VectorUnsignedInt = splatsU32(v)

public fun vecSplatsI8(v: Byte): VectorSignedChar = splatsI8(v)

public fun vecSplatsI16(v: Short): VectorSignedShort = splatsI16(v)

public fun vecSplatsI32(v: Int): VectorSignedInt = splatsI32(v)

public fun vecSplatsF32(v: Float): VectorFloat = splatsF32(v)

public interface VectorSplats<out Result> {
    public fun vecSplats(): Result
}

public fun UByte.vecSplats(): VectorUnsignedChar = vecSplatsU8(transmute(this))

public fun Byte.vecSplats(): VectorSignedChar = vecSplatsI8(transmute(this))

public fun UShort.vecSplats(): VectorUnsignedShort = vecSplatsU16(transmute(this))

public fun Short.vecSplats(): VectorSignedShort = vecSplatsI16(transmute(this))

public fun UInt.vecSplats(): VectorUnsignedInt = vecSplatsU32(transmute(this))

public fun Int.vecSplats(): VectorSignedInt = vecSplatsI32(transmute(this))

public fun Float.vecSplats(): VectorFloat = vecSplatsF32(transmute(this))

public fun vecVsububm(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun vecVsubuhm(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun vecVsubuwm(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public interface VectorSub<Other, out Result> {
    public fun vecSub(b: Other): Result
}

public fun VectorUnsignedChar.vecSub(b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedChar.vecSub(b: VectorBoolChar): VectorUnsignedChar = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()); simdArg0 }

public fun VectorBoolChar.vecSub(b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()); val simdArg1 = b; simdArg0 }

public fun VectorSignedChar.vecSub(b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorSignedChar.vecSub(b: VectorBoolChar): VectorSignedChar = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()); simdArg0 }

public fun VectorBoolChar.vecSub(b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()); val simdArg1 = b; simdArg0 }

public fun VectorUnsignedShort.vecSub(b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedShort.vecSub(b: VectorBoolShort): VectorUnsignedShort = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()); simdArg0 }

public fun VectorBoolShort.vecSub(b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()); val simdArg1 = b; simdArg0 }

public fun VectorSignedShort.vecSub(b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorSignedShort.vecSub(b: VectorBoolShort): VectorSignedShort = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()); simdArg0 }

public fun VectorBoolShort.vecSub(b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()); val simdArg1 = b; simdArg0 }

public fun VectorUnsignedInt.vecSub(b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedInt.vecSub(b: VectorBoolInt): VectorUnsignedInt = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()); simdArg0 }

public fun VectorBoolInt.vecSub(b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()); val simdArg1 = b; simdArg0 }

public fun VectorSignedInt.vecSub(b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorSignedInt.vecSub(b: VectorBoolInt): VectorSignedInt = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()); simdArg0 }

public fun VectorBoolInt.vecSub(b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()); val simdArg1 = b; simdArg0 }

public fun VectorFloat.vecSub(b: VectorFloat): VectorFloat = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun vecVsubcuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = vsubcuw(a, b)

public interface VectorSubc<Other, out Result> {
    public fun vecSubc(b: Other): Result
}

public fun VectorUnsignedInt.vecSubc(b: VectorUnsignedInt): VectorUnsignedInt = vecVsubcuw(this, b)

public fun VectorSignedInt.vecSubc(b: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(vecVsubcuw(transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun vecVminsb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = vminsb(a, b)

public fun vecVminsh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = vminsh(a, b)

public fun vecVminsw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = vminsw(a, b)

public fun vecVminub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = vminub(a, b)

public fun vecVminuh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = vminuh(a, b)

public fun vecVminuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = vminuw(a, b)

public interface VectorMin<Other, out Result> {
    public fun vecMin(b: Other): Result
}

public fun VectorUnsignedChar.vecMin(b: VectorUnsignedChar): VectorUnsignedChar = vminub(transmute(this), transmute(b))

public fun VectorUnsignedChar.vecMin(b: VectorBoolChar): VectorUnsignedChar = vminub(transmute(this), transmute(b))

public fun VectorBoolChar.vecMin(b: VectorUnsignedChar): VectorUnsignedChar = vminub(transmute(this), transmute(b))

public fun VectorSignedChar.vecMin(b: VectorSignedChar): VectorSignedChar = vminsb(transmute(this), transmute(b))

public fun VectorSignedChar.vecMin(b: VectorBoolChar): VectorSignedChar = vminsb(transmute(this), transmute(b))

public fun VectorBoolChar.vecMin(b: VectorSignedChar): VectorSignedChar = vminsb(transmute(this), transmute(b))

public fun VectorUnsignedShort.vecMin(b: VectorUnsignedShort): VectorUnsignedShort = vminuh(transmute(this), transmute(b))

public fun VectorUnsignedShort.vecMin(b: VectorBoolShort): VectorUnsignedShort = vminuh(transmute(this), transmute(b))

public fun VectorBoolShort.vecMin(b: VectorUnsignedShort): VectorUnsignedShort = vminuh(transmute(this), transmute(b))

public fun VectorSignedShort.vecMin(b: VectorSignedShort): VectorSignedShort = vminsh(transmute(this), transmute(b))

public fun VectorSignedShort.vecMin(b: VectorBoolShort): VectorSignedShort = vminsh(transmute(this), transmute(b))

public fun VectorBoolShort.vecMin(b: VectorSignedShort): VectorSignedShort = vminsh(transmute(this), transmute(b))

public fun VectorUnsignedInt.vecMin(b: VectorUnsignedInt): VectorUnsignedInt = vminuw(transmute(this), transmute(b))

public fun VectorUnsignedInt.vecMin(b: VectorBoolInt): VectorUnsignedInt = vminuw(transmute(this), transmute(b))

public fun VectorBoolInt.vecMin(b: VectorUnsignedInt): VectorUnsignedInt = vminuw(transmute(this), transmute(b))

public fun VectorSignedInt.vecMin(b: VectorSignedInt): VectorSignedInt = vminsw(transmute(this), transmute(b))

public fun VectorSignedInt.vecMin(b: VectorBoolInt): VectorSignedInt = vminsw(transmute(this), transmute(b))

public fun VectorBoolInt.vecMin(b: VectorSignedInt): VectorSignedInt = vminsw(transmute(this), transmute(b))

public fun vecVmaxsb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = vmaxsb(a, b)

public fun vecVmaxsh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = vmaxsh(a, b)

public fun vecVmaxsw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = vmaxsw(a, b)

public fun vecVmaxub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = vmaxub(a, b)

public fun vecVmaxuh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = vmaxuh(a, b)

public fun vecVmaxuw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = vmaxuw(a, b)

public interface VectorMax<Other, out Result> {
    public fun vecMax(b: Other): Result
}

public fun VectorUnsignedChar.vecMax(b: VectorUnsignedChar): VectorUnsignedChar = vmaxub(transmute(this), transmute(b))

public fun VectorUnsignedChar.vecMax(b: VectorBoolChar): VectorUnsignedChar = vmaxub(transmute(this), transmute(b))

public fun VectorBoolChar.vecMax(b: VectorUnsignedChar): VectorUnsignedChar = vmaxub(transmute(this), transmute(b))

public fun VectorSignedChar.vecMax(b: VectorSignedChar): VectorSignedChar = vmaxsb(transmute(this), transmute(b))

public fun VectorSignedChar.vecMax(b: VectorBoolChar): VectorSignedChar = vmaxsb(transmute(this), transmute(b))

public fun VectorBoolChar.vecMax(b: VectorSignedChar): VectorSignedChar = vmaxsb(transmute(this), transmute(b))

public fun VectorUnsignedShort.vecMax(b: VectorUnsignedShort): VectorUnsignedShort = vmaxuh(transmute(this), transmute(b))

public fun VectorUnsignedShort.vecMax(b: VectorBoolShort): VectorUnsignedShort = vmaxuh(transmute(this), transmute(b))

public fun VectorBoolShort.vecMax(b: VectorUnsignedShort): VectorUnsignedShort = vmaxuh(transmute(this), transmute(b))

public fun VectorSignedShort.vecMax(b: VectorSignedShort): VectorSignedShort = vmaxsh(transmute(this), transmute(b))

public fun VectorSignedShort.vecMax(b: VectorBoolShort): VectorSignedShort = vmaxsh(transmute(this), transmute(b))

public fun VectorBoolShort.vecMax(b: VectorSignedShort): VectorSignedShort = vmaxsh(transmute(this), transmute(b))

public fun VectorUnsignedInt.vecMax(b: VectorUnsignedInt): VectorUnsignedInt = vmaxuw(transmute(this), transmute(b))

public fun VectorUnsignedInt.vecMax(b: VectorBoolInt): VectorUnsignedInt = vmaxuw(transmute(this), transmute(b))

public fun VectorBoolInt.vecMax(b: VectorUnsignedInt): VectorUnsignedInt = vmaxuw(transmute(this), transmute(b))

public fun VectorSignedInt.vecMax(b: VectorSignedInt): VectorSignedInt = vmaxsw(transmute(this), transmute(b))

public fun VectorSignedInt.vecMax(b: VectorBoolInt): VectorSignedInt = vmaxsw(transmute(this), transmute(b))

public fun VectorBoolInt.vecMax(b: VectorSignedInt): VectorSignedInt = vmaxsw(transmute(this), transmute(b))

public fun vecVmuleub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedShort = vmuleub(a, b)

public fun vecVmulesb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedShort = vmulesb(a, b)

public fun vecVmuleuh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt = vmuleuh(a, b)

public fun vecVmulesh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedInt = vmulesh(a, b)

public interface VectorMul<T> {
    public fun vecMul(b: T): T
}

public fun vecVmuluwm(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = transmuteWithLayouts<I32x4, VectorSignedInt>(run { val simdArg0 = transmuteWithLayouts<VectorSignedInt, I32x4>(a, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<I32x4>()); val simdArg1 = transmuteWithLayouts<VectorSignedInt, I32x4>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<I32x4>()); simdArg0 }, UnresolvedTypeLayout<I32x4>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun vecXvmulsp(a: VectorFloat, b: VectorFloat): VectorFloat = transmuteWithLayouts<F32x4, VectorFloat>(run { val simdArg0 = transmuteWithLayouts<VectorFloat, F32x4>(a, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<F32x4>()); val simdArg1 = transmuteWithLayouts<VectorFloat, F32x4>(b, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<F32x4>()); simdArg0 }, UnresolvedTypeLayout<F32x4>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorSignedInt.vecMul(b: VectorSignedInt): VectorSignedInt = vecVmuluwm(this, b)

public fun VectorUnsignedInt.vecMul(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<U32x4, VectorUnsignedInt>(run { val simdArg0 = transmuteWithLayouts<VectorUnsignedInt, U32x4>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<U32x4>()); val simdArg1 = transmuteWithLayouts<VectorUnsignedInt, U32x4>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<U32x4>()); simdArg0 }, UnresolvedTypeLayout<U32x4>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorFloat.vecMul(b: VectorFloat): VectorFloat = vecXvmulsp(this, b)

public interface VectorMule<Result, T> {
    public fun vecMule(b: T): Result
}

public fun VectorUnsignedChar.vecMule(b: VectorUnsignedChar): VectorUnsignedShort = vmuleub(this, b)

public fun VectorSignedChar.vecMule(b: VectorSignedChar): VectorSignedShort = vmulesb(this, b)

public fun VectorUnsignedShort.vecMule(b: VectorUnsignedShort): VectorUnsignedInt = vmuleuh(this, b)

public fun VectorSignedShort.vecMule(b: VectorSignedShort): VectorSignedInt = vmulesh(this, b)

public fun vecVmuloub(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedShort = vmuloub(a, b)

public fun vecVmulosb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedShort = vmulosb(a, b)

public fun vecVmulouh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt = vmulouh(a, b)

public fun vecVmulosh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedInt = vmulosh(a, b)

public interface VectorMulo<Result, T> {
    public fun vecMulo(b: T): Result
}

public fun VectorUnsignedChar.vecMulo(b: VectorUnsignedChar): VectorUnsignedShort = vmuloub(this, b)

public fun VectorSignedChar.vecMulo(b: VectorSignedChar): VectorSignedShort = vmulosb(this, b)

public fun VectorUnsignedShort.vecMulo(b: VectorUnsignedShort): VectorUnsignedInt = vmulouh(this, b)

public fun VectorSignedShort.vecMulo(b: VectorSignedShort): VectorSignedInt = vmulosh(this, b)

public fun vecVsum4ubs(a: VectorUnsignedChar, b: VectorUnsignedInt): VectorUnsignedInt = vsum4ubs(a, b)

public fun vecVsum4sbs(a: VectorSignedChar, b: VectorSignedInt): VectorSignedInt = vsum4sbs(a, b)

public fun vecVsum4shs(a: VectorSignedShort, b: VectorSignedInt): VectorSignedInt = vsum4shs(a, b)

public interface VectorSum4s<Other> {
    public fun vecSum4s(b: Other): Other
}

public fun VectorUnsignedChar.vecSum4s(b: VectorUnsignedInt): VectorUnsignedInt = vsum4ubs(this, b)

public fun VectorSignedChar.vecSum4s(b: VectorSignedInt): VectorSignedInt = vsum4sbs(this, b)

public fun VectorSignedShort.vecSum4s(b: VectorSignedInt): VectorSignedInt = vsum4shs(this, b)

public fun vecVsum2sws(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = vsum2sws(a, b)

public fun vecVnmsubfp(a: VectorFloat, b: VectorFloat, c: VectorFloat): VectorFloat = run { val simdArg0 = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = run { val simdArg0 = c; simdArg0 }; simdArg0 }; simdArg0 }

public fun vecVmaddfp(a: VectorFloat, b: VectorFloat, c: VectorFloat): VectorFloat = run { val simdArg0 = a; val simdArg1 = b; val simdArg2 = c; simdArg0 }

public fun vecVmsumubm(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedInt): VectorUnsignedInt = vmsumubm(a, b, c)

public fun vecVmsummbm(a: VectorSignedChar, b: VectorUnsignedChar, c: VectorSignedInt): VectorSignedInt = vmsummbm(a, b, c)

public fun vecVmsumuhm(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt = vmsumuhm(a, b, c)

public fun vecVmsumshm(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt = vmsumshm(a, b, c)

public interface VectorMsum<B, Other> {
    public fun vecMsum(b: B, c: Other): Other
}

public fun VectorUnsignedChar.vecMsum(b: VectorUnsignedChar, c: VectorUnsignedInt): VectorUnsignedInt = vmsumubm(this, b, c)

public fun VectorSignedChar.vecMsum(b: VectorUnsignedChar, c: VectorSignedInt): VectorSignedInt = vmsummbm(this, b, c)

public fun VectorUnsignedShort.vecMsum(b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt = vmsumuhm(this, b, c)

public fun VectorSignedShort.vecMsum(b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt = vmsumshm(this, b, c)

public fun vecVmsumuhs(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt = vmsumuhs(a, b, c)

public fun vecVmsumshs(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt = vmsumshs(a, b, c)

public interface VectorMsums<Other, T> {
    public fun vecMsums(b: T, c: Other): Other
}

public fun VectorUnsignedShort.vecMsums(b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt = vmsumuhs(this, b, c)

public fun VectorSignedShort.vecMsums(b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt = vmsumshs(this, b, c)

public fun vecVperm(a: VectorSignedInt, b: VectorSignedInt, c: VectorUnsignedChar): VectorSignedInt = vperm(a, b, c)

public interface VectorPerm<T> {
    public fun vecVperm(b: T, c: VectorUnsignedChar): T
}

public fun VectorSignedChar.vecVperm(b: VectorSignedChar, c: VectorUnsignedChar): VectorSignedChar = transmuteWithLayouts<VectorSignedInt, VectorSignedChar>(vecVperm(transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>()), c), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorUnsignedChar.vecVperm(b: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedInt, VectorUnsignedChar>(vecVperm(transmuteWithLayouts<VectorUnsignedChar, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedInt>()), c), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorBoolChar.vecVperm(b: VectorBoolChar, c: VectorUnsignedChar): VectorBoolChar = transmuteWithLayouts<VectorSignedInt, VectorBoolChar>(vecVperm(transmuteWithLayouts<VectorBoolChar, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorBoolChar, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedInt>()), c), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorSignedShort.vecVperm(b: VectorSignedShort, c: VectorUnsignedChar): VectorSignedShort = transmuteWithLayouts<VectorSignedInt, VectorSignedShort>(vecVperm(transmuteWithLayouts<VectorSignedShort, VectorSignedInt>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorSignedShort, VectorSignedInt>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorSignedInt>()), c), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedShort.vecVperm(b: VectorUnsignedShort, c: VectorUnsignedChar): VectorUnsignedShort = transmuteWithLayouts<VectorSignedInt, VectorUnsignedShort>(vecVperm(transmuteWithLayouts<VectorUnsignedShort, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedInt>()), c), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorBoolShort.vecVperm(b: VectorBoolShort, c: VectorUnsignedChar): VectorBoolShort = transmuteWithLayouts<VectorSignedInt, VectorBoolShort>(vecVperm(transmuteWithLayouts<VectorBoolShort, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorBoolShort, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedInt>()), c), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorSignedInt.vecVperm(b: VectorSignedInt, c: VectorUnsignedChar): VectorSignedInt = vecVperm(this, b, c)

public fun VectorUnsignedInt.vecVperm(b: VectorUnsignedInt, c: VectorUnsignedChar): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vecVperm(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), c), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorBoolInt.vecVperm(b: VectorBoolInt, c: VectorUnsignedChar): VectorBoolInt = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(vecVperm(transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), c), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorFloat.vecVperm(b: VectorFloat, c: VectorUnsignedChar): VectorFloat = transmuteWithLayouts<VectorSignedInt, VectorFloat>(vecVperm(transmuteWithLayouts<VectorFloat, VectorSignedInt>(this, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorFloat, VectorSignedInt>(b, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorSignedInt>()), c), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorFloat>())

public interface VectorAdd<Other, out Result> {
    public fun vecAdd(other: Other): Result
}

public fun vecAddBcSc(a: VectorBoolChar, b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(a, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()); val simdArg1 = b; simdArg0 }

public fun VectorBoolChar.vecAdd(other: VectorSignedChar): VectorSignedChar = vecAddBcSc(this, other)

public fun VectorSignedChar.vecAdd(other: VectorBoolChar): VectorSignedChar = other.vecAdd(this)

public fun vecAddScSc(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorSignedChar.vecAdd(other: VectorSignedChar): VectorSignedChar = vecAddScSc(this, other)

public fun vecAddBcUc(a: VectorBoolChar, b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(a, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()); val simdArg1 = b; simdArg0 }

public fun VectorBoolChar.vecAdd(other: VectorUnsignedChar): VectorUnsignedChar = vecAddBcUc(this, other)

public fun VectorUnsignedChar.vecAdd(other: VectorBoolChar): VectorUnsignedChar = other.vecAdd(this)

public fun vecAddUcUc(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedChar.vecAdd(other: VectorUnsignedChar): VectorUnsignedChar = vecAddUcUc(this, other)

public fun vecAddBsSs(a: VectorBoolShort, b: VectorSignedShort): VectorSignedShort {
    val a1: I16x8 = transmuteWithLayouts<VectorBoolShort, I16x8>(a, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<I16x8>())
    val a2: VectorSignedShort = run { val simdArg0 = a1; VectorSignedShort() }
    return run { val simdArg0 = a2; val simdArg1 = b; simdArg0 }
}

public fun VectorBoolShort.vecAdd(other: VectorSignedShort): VectorSignedShort = vecAddBsSs(this, other)

public fun VectorSignedShort.vecAdd(other: VectorBoolShort): VectorSignedShort = other.vecAdd(this)

public fun vecAddSsSs(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorSignedShort.vecAdd(other: VectorSignedShort): VectorSignedShort = vecAddSsSs(this, other)

public fun vecAddBsUs(a: VectorBoolShort, b: VectorUnsignedShort): VectorUnsignedShort {
    val a1: I16x8 = transmuteWithLayouts<VectorBoolShort, I16x8>(a, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<I16x8>())
    val a2: VectorUnsignedShort = run { val simdArg0 = a1; VectorUnsignedShort() }
    return run { val simdArg0 = a2; val simdArg1 = b; simdArg0 }
}

public fun VectorBoolShort.vecAdd(other: VectorUnsignedShort): VectorUnsignedShort = vecAddBsUs(this, other)

public fun VectorUnsignedShort.vecAdd(other: VectorBoolShort): VectorUnsignedShort = other.vecAdd(this)

public fun vecAddUsUs(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedShort.vecAdd(other: VectorUnsignedShort): VectorUnsignedShort = vecAddUsUs(this, other)

public fun vecAddBiSi(a: VectorBoolInt, b: VectorSignedInt): VectorSignedInt {
    val a1: I32x4 = transmuteWithLayouts<VectorBoolInt, I32x4>(a, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<I32x4>())
    val a2: VectorSignedInt = run { val simdArg0 = a1; VectorSignedInt() }
    return run { val simdArg0 = a2; val simdArg1 = b; simdArg0 }
}

public fun VectorBoolInt.vecAdd(other: VectorSignedInt): VectorSignedInt = vecAddBiSi(this, other)

public fun VectorSignedInt.vecAdd(other: VectorBoolInt): VectorSignedInt = other.vecAdd(this)

public fun vecAddSiSi(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorSignedInt.vecAdd(other: VectorSignedInt): VectorSignedInt = vecAddSiSi(this, other)

public fun vecAddBiUi(a: VectorBoolInt, b: VectorUnsignedInt): VectorUnsignedInt {
    val a1: I32x4 = transmuteWithLayouts<VectorBoolInt, I32x4>(a, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<I32x4>())
    val a2: VectorUnsignedInt = run { val simdArg0 = a1; VectorUnsignedInt() }
    return run { val simdArg0 = a2; val simdArg1 = b; simdArg0 }
}

public fun VectorBoolInt.vecAdd(other: VectorUnsignedInt): VectorUnsignedInt = vecAddBiUi(this, other)

public fun VectorUnsignedInt.vecAdd(other: VectorBoolInt): VectorUnsignedInt = other.vecAdd(this)

public fun vecAddUiUi(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedInt.vecAdd(other: VectorUnsignedInt): VectorUnsignedInt = vecAddUiUi(this, other)

public fun vecAddFloatFloat(a: VectorFloat, b: VectorFloat): VectorFloat = run { val simdArg0 = a; val simdArg1 = b; simdArg0 }

public fun VectorFloat.vecAdd(other: VectorFloat): VectorFloat = vecAddFloatFloat(this, other)

public interface VectorAdde<T> {
    public fun vecAdde(b: T, c: T): T
}

public fun VectorUnsignedInt.vecAdde(b: VectorUnsignedInt, c: VectorUnsignedInt): VectorUnsignedInt {
    val mask: VectorUnsignedInt = transmuteWithLayouts<Any?, VectorUnsignedInt>(Simd<UInt>(listOf(1u, 1u, 1u, 1u)), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>())
    val carry = vecAnd(c, mask)
    return vecAdd(vecAdd(this, b), carry)
}

public fun VectorSignedInt.vecAdde(b: VectorSignedInt, c: VectorSignedInt): VectorSignedInt {
    val mask: VectorSignedInt = transmuteWithLayouts<Any?, VectorSignedInt>(Simd<Int>(listOf(1, 1, 1, 1)), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>())
    val carry = vecAnd(c, mask)
    return vecAdd(vecAdd(this, b), carry)
}

public interface VectorMladd<Other, T, out Result> {
    public fun vecMladd(b: Other, c: Other): Result
}

public fun mladd(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort {
    val a1: I16x8 = transmuteWithLayouts<VectorSignedShort, I16x8>(a, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<I16x8>())
    val b1: I16x8 = transmuteWithLayouts<VectorSignedShort, I16x8>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<I16x8>())
    val c1: I16x8 = transmuteWithLayouts<VectorSignedShort, I16x8>(c, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<I16x8>())
    return transmuteWithLayouts<I16x8, VectorSignedShort>(run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }; val simdArg1 = c1; simdArg0 }, UnresolvedTypeLayout<I16x8>(), UnresolvedTypeLayout<VectorSignedShort>())
}

public fun VectorUnsignedShort.vecMladd(b: VectorUnsignedShort, c: VectorUnsignedShort): VectorUnsignedShort {
    val a = this
    val b1 = b
    val c1 = c
    return transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(mladd(a, b1, c1), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())
}

public fun VectorUnsignedShort.vecMladd(b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort {
    val a = transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())
    val b1 = b
    val c1 = c
    return mladd(a, b1, c1)
}

public fun VectorSignedShort.vecMladd(b: VectorUnsignedShort, c: VectorUnsignedShort): VectorSignedShort {
    val a = this
    val b1 = transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())
    val c1 = transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(c, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())
    return mladd(a, b1, c1)
}

public fun VectorSignedShort.vecMladd(b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort {
    val a = this
    val b1 = b
    val c1 = c
    return mladd(a, b1, c1)
}

public interface VectorOr<Other, out Result> {
    public fun vecOr(b: Other): Result
}

public fun VectorUnsignedChar.vecOr(b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedChar.vecOr(b: VectorBoolChar): VectorUnsignedChar = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()); simdArg0 }

public fun VectorBoolChar.vecOr(b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()); val simdArg1 = b; simdArg0 }

public fun VectorSignedChar.vecOr(b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorSignedChar.vecOr(b: VectorBoolChar): VectorSignedChar = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()); simdArg0 }

public fun VectorBoolChar.vecOr(b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()); val simdArg1 = b; simdArg0 }

public fun VectorUnsignedShort.vecOr(b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedShort.vecOr(b: VectorBoolShort): VectorUnsignedShort = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()); simdArg0 }

public fun VectorBoolShort.vecOr(b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()); val simdArg1 = b; simdArg0 }

public fun VectorSignedShort.vecOr(b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorSignedShort.vecOr(b: VectorBoolShort): VectorSignedShort = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()); simdArg0 }

public fun VectorBoolShort.vecOr(b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()); val simdArg1 = b; simdArg0 }

public fun VectorUnsignedInt.vecOr(b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedInt.vecOr(b: VectorBoolInt): VectorUnsignedInt = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()); simdArg0 }

public fun VectorBoolInt.vecOr(b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()); val simdArg1 = b; simdArg0 }

public fun VectorSignedInt.vecOr(b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorSignedInt.vecOr(b: VectorBoolInt): VectorSignedInt = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()); simdArg0 }

public fun VectorBoolInt.vecOr(b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()); val simdArg1 = b; simdArg0 }

public interface VectorXor<Other, out Result> {
    public fun vecXor(b: Other): Result
}

public fun VectorUnsignedChar.vecXor(b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedChar.vecXor(b: VectorBoolChar): VectorUnsignedChar = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()); simdArg0 }

public fun VectorBoolChar.vecXor(b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()); val simdArg1 = b; simdArg0 }

public fun VectorSignedChar.vecXor(b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorSignedChar.vecXor(b: VectorBoolChar): VectorSignedChar = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()); simdArg0 }

public fun VectorBoolChar.vecXor(b: VectorSignedChar): VectorSignedChar = run { val simdArg0 = transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()); val simdArg1 = b; simdArg0 }

public fun VectorUnsignedShort.vecXor(b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedShort.vecXor(b: VectorBoolShort): VectorUnsignedShort = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()); simdArg0 }

public fun VectorBoolShort.vecXor(b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()); val simdArg1 = b; simdArg0 }

public fun VectorSignedShort.vecXor(b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorSignedShort.vecXor(b: VectorBoolShort): VectorSignedShort = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()); simdArg0 }

public fun VectorBoolShort.vecXor(b: VectorSignedShort): VectorSignedShort = run { val simdArg0 = transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()); val simdArg1 = b; simdArg0 }

public fun VectorUnsignedInt.vecXor(b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorUnsignedInt.vecXor(b: VectorBoolInt): VectorUnsignedInt = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()); simdArg0 }

public fun VectorBoolInt.vecXor(b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()); val simdArg1 = b; simdArg0 }

public fun VectorSignedInt.vecXor(b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = this; val simdArg1 = b; simdArg0 }

public fun VectorSignedInt.vecXor(b: VectorBoolInt): VectorSignedInt = run { val simdArg0 = this; val simdArg1 = transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()); simdArg0 }

public fun VectorBoolInt.vecXor(b: VectorSignedInt): VectorSignedInt = run { val simdArg0 = transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()); val simdArg1 = b; simdArg0 }

public fun vecVnorsb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val o = vecSplats((0.inv()).toByte())
    return vecXor(vecOr(a, b), o)
}

public fun vecVnorsh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort {
    val o = vecSplats((0.inv()).toShort())
    return vecXor(vecOr(a, b), o)
}

public fun vecVnorsw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt {
    val o = vecSplats(0.inv())
    return vecXor(vecOr(a, b), o)
}

public interface VectorNor<Other, out Result> {
    public fun vecNor(b: Other): Result
}

public fun VectorBoolChar.vecNor(b: VectorBoolChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(vecVnorsb(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorUnsignedChar.vecNor(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vecVnorsb(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecNor(b: VectorSignedChar): VectorSignedChar = vecVnorsb(this, b)

public fun VectorBoolShort.vecNor(b: VectorBoolShort): VectorBoolShort = transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(vecVnorsh(transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorUnsignedShort.vecNor(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vecVnorsh(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecNor(b: VectorSignedShort): VectorSignedShort = vecVnorsh(this, b)

public fun VectorBoolInt.vecNor(b: VectorBoolInt): VectorBoolInt = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(vecVnorsw(transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorUnsignedInt.vecNor(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vecVnorsw(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecNor(b: VectorSignedInt): VectorSignedInt = vecVnorsw(this, b)

public fun vecVnandsb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val o = vecSplats((0.inv()).toByte())
    return vecXor(vecAnd(a, b), o)
}

public fun vecVnandsh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort {
    val o = vecSplats((0.inv()).toShort())
    return vecXor(vecAnd(a, b), o)
}

public fun vecVnandsw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt {
    val o = vecSplats(0.inv())
    return vecXor(vecAnd(a, b), o)
}

public interface VectorNand<Other, out Result> {
    public fun vecNand(b: Other): Result
}

public fun VectorBoolChar.vecNand(b: VectorBoolChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(vecVnandsb(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorUnsignedChar.vecNand(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vecVnandsb(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecNand(b: VectorSignedChar): VectorSignedChar = vecVnandsb(this, b)

public fun VectorBoolShort.vecNand(b: VectorBoolShort): VectorBoolShort = transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(vecVnandsh(transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorUnsignedShort.vecNand(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vecVnandsh(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecNand(b: VectorSignedShort): VectorSignedShort = vecVnandsh(this, b)

public fun VectorBoolInt.vecNand(b: VectorBoolInt): VectorBoolInt = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(vecVnandsw(transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorUnsignedInt.vecNand(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vecVnandsw(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecNand(b: VectorSignedInt): VectorSignedInt = vecVnandsw(this, b)

public fun vecVsel(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedChar): VectorSignedChar {
    val a1: I8x16 = transmuteWithLayouts<VectorSignedChar, I8x16>(a, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<I8x16>())
    val b1: I8x16 = transmuteWithLayouts<VectorSignedChar, I8x16>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<I8x16>())
    val c1: I8x16 = transmuteWithLayouts<VectorSignedChar, I8x16>(c, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<I8x16>())
    val notC = run { val simdArg0 = c1; val simdArg1 = Simd<Byte>(List(16) { (0.inv()).toByte() }); simdArg0 }
    return transmuteWithLayouts<I8x16, VectorSignedChar>(run { val simdArg0 = run { val simdArg0 = a1; val simdArg1 = notC; simdArg0 }; val simdArg1 = run { val simdArg0 = b1; val simdArg1 = c1; simdArg0 }; simdArg0 }, UnresolvedTypeLayout<I8x16>(), UnresolvedTypeLayout<VectorSignedChar>())
}

public interface VectorSel<Mask, T> {
    public fun vecSel(b: T, c: Mask): T
}

public fun VectorSignedChar.vecSel(b: VectorSignedChar, c: VectorBoolChar): VectorSignedChar {
    val a = this
    val b1 = b
    val c1 = transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(c, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>())
    return vecVsel(a, b1, c1)
}

public fun VectorSignedChar.vecSel(b: VectorSignedChar, c: VectorUnsignedChar): VectorSignedChar {
    val a = this
    val b1 = b
    val c1 = transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(c, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())
    return vecVsel(a, b1, c1)
}

public fun VectorUnsignedChar.vecSel(b: VectorUnsignedChar, c: VectorBoolChar): VectorUnsignedChar {
    val a = this
    val b1 = b
    val c1 = transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(c, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())
    return transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vecVsel(a, b1, c1), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())
}

public fun VectorUnsignedChar.vecSel(b: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar {
    val a = this
    val b1 = b
    val c1 = c
    return transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vecVsel(a, b1, c1), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())
}

public fun VectorBoolChar.vecSel(b: VectorBoolChar, c: VectorBoolChar): VectorBoolChar {
    val a = this
    val b1 = b
    val c1 = c
    return transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(vecVsel(a, b1, c1), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())
}

public fun VectorBoolChar.vecSel(b: VectorBoolChar, c: VectorUnsignedChar): VectorBoolChar {
    val a = this
    val b1 = b
    val c1 = transmuteWithLayouts<VectorUnsignedChar, VectorBoolChar>(c, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())
    return transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(vecVsel(a, b1, c1), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())
}

public fun VectorSignedShort.vecSel(b: VectorSignedShort, c: VectorBoolShort): VectorSignedShort {
    val a = this
    val b1 = b
    val c1 = transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(c, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>())
    return transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(vecVsel(a, b1, c1), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())
}

public fun VectorSignedShort.vecSel(b: VectorSignedShort, c: VectorUnsignedShort): VectorSignedShort {
    val a = this
    val b1 = b
    val c1 = transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(c, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())
    return transmuteWithLayouts<VectorSignedChar, VectorSignedShort>(vecVsel(a, b1, c1), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedShort>())
}

public fun VectorUnsignedShort.vecSel(b: VectorUnsignedShort, c: VectorBoolShort): VectorUnsignedShort {
    val a = this
    val b1 = b
    val c1 = transmuteWithLayouts<VectorBoolShort, VectorUnsignedShort>(c, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())
    return transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(vecVsel(a, b1, c1), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())
}

public fun VectorUnsignedShort.vecSel(b: VectorUnsignedShort, c: VectorUnsignedShort): VectorUnsignedShort {
    val a = this
    val b1 = b
    val c1 = c
    return transmuteWithLayouts<VectorSignedChar, VectorUnsignedShort>(vecVsel(a, b1, c1), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedShort>())
}

public fun VectorBoolShort.vecSel(b: VectorBoolShort, c: VectorBoolShort): VectorBoolShort {
    val a = this
    val b1 = b
    val c1 = c
    return transmuteWithLayouts<VectorSignedChar, VectorBoolShort>(vecVsel(a, b1, c1), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolShort>())
}

public fun VectorBoolShort.vecSel(b: VectorBoolShort, c: VectorUnsignedShort): VectorBoolShort {
    val a = this
    val b1 = b
    val c1 = transmuteWithLayouts<VectorUnsignedShort, VectorBoolShort>(c, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())
    return transmuteWithLayouts<VectorSignedChar, VectorBoolShort>(vecVsel(a, b1, c1), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolShort>())
}

public fun VectorSignedInt.vecSel(b: VectorSignedInt, c: VectorBoolInt): VectorSignedInt {
    val a = this
    val b1 = b
    val c1 = transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(c, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>())
    return transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(vecVsel(a, b1, c1), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())
}

public fun VectorSignedInt.vecSel(b: VectorSignedInt, c: VectorUnsignedInt): VectorSignedInt {
    val a = this
    val b1 = b
    val c1 = transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(c, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())
    return transmuteWithLayouts<VectorSignedChar, VectorSignedInt>(vecVsel(a, b1, c1), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorSignedInt>())
}

public fun VectorUnsignedInt.vecSel(b: VectorUnsignedInt, c: VectorBoolInt): VectorUnsignedInt {
    val a = this
    val b1 = b
    val c1 = transmuteWithLayouts<VectorBoolInt, VectorUnsignedInt>(c, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())
    return transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(vecVsel(a, b1, c1), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())
}

public fun VectorUnsignedInt.vecSel(b: VectorUnsignedInt, c: VectorUnsignedInt): VectorUnsignedInt {
    val a = this
    val b1 = b
    val c1 = c
    return transmuteWithLayouts<VectorSignedChar, VectorUnsignedInt>(vecVsel(a, b1, c1), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedInt>())
}

public fun VectorBoolInt.vecSel(b: VectorBoolInt, c: VectorBoolInt): VectorBoolInt {
    val a = this
    val b1 = b
    val c1 = c
    return transmuteWithLayouts<VectorSignedChar, VectorBoolInt>(vecVsel(a, b1, c1), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolInt>())
}

public fun VectorBoolInt.vecSel(b: VectorBoolInt, c: VectorUnsignedInt): VectorBoolInt {
    val a = this
    val b1 = b
    val c1 = transmuteWithLayouts<VectorUnsignedInt, VectorBoolInt>(c, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())
    return transmuteWithLayouts<VectorSignedChar, VectorBoolInt>(vecVsel(a, b1, c1), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolInt>())
}

public fun VectorFloat.vecSel(b: VectorFloat, c: VectorBoolInt): VectorFloat {
    val a = this
    val b1 = b
    val c1 = transmuteWithLayouts<VectorBoolInt, VectorFloat>(c, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorFloat>())
    return transmuteWithLayouts<VectorSignedChar, VectorFloat>(vecVsel(a, b1, c1), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())
}

public fun VectorFloat.vecSel(b: VectorFloat, c: VectorUnsignedInt): VectorFloat {
    val a = this
    val b1 = b
    val c1 = transmuteWithLayouts<VectorUnsignedInt, VectorFloat>(c, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorFloat>())
    return transmuteWithLayouts<VectorSignedChar, VectorFloat>(vecVsel(a, b1, c1), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorFloat>())
}

public fun vecCtfI32(a: VectorSignedInt, imm5: Int): VectorFloat {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return vcfsx(a, imm5)
}

public fun vecCtfU32(a: VectorUnsignedInt, imm5: Int): VectorFloat {
    run {
        check(0 <= imm5 && imm5 < (1 shl 5)) { "IMM5 doesn't fit in 5 bits (unsigned)" }
    }
    return vcfux(a, imm5)
}

public interface VectorCtf {
    public fun vecCtf(imm5: Int): VectorFloat
}

public fun VectorSignedInt.vecCtf(imm5: Int): VectorFloat = vecCtfI32(this, imm5)

public fun VectorUnsignedInt.vecCtf(imm5: Int): VectorFloat = vecCtfU32(this, imm5)

public fun vecVmrglb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val mergelPerm = transmuteWithLayouts<Any?, VectorSignedChar>(Simd<UByte>(listOf(0x08u.toUByte(), 0x18u.toUByte(), 0x09u.toUByte(), 0x19u.toUByte(), 0x0Au.toUByte(), 0x1Au.toUByte(), 0x0Bu.toUByte(), 0x1Bu.toUByte(), 0x0Cu.toUByte(), 0x1Cu.toUByte(), 0x0Du.toUByte(), 0x1Du.toUByte(), 0x0Eu.toUByte(), 0x1Eu.toUByte(), 0x0Fu.toUByte(), 0x1Fu.toUByte())), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>())
    return vecPerm(a, b, mergelPerm)
}

public fun vecVmrglh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort {
    val mergelPerm = transmuteWithLayouts<Any?, VectorSignedShort>(Simd<UByte>(listOf(0x08u.toUByte(), 0x09u.toUByte(), 0x18u.toUByte(), 0x19u.toUByte(), 0x0Au.toUByte(), 0x0Bu.toUByte(), 0x1Au.toUByte(), 0x1Bu.toUByte(), 0x0Cu.toUByte(), 0x0Du.toUByte(), 0x1Cu.toUByte(), 0x1Du.toUByte(), 0x0Eu.toUByte(), 0x0Fu.toUByte(), 0x1Eu.toUByte(), 0x1Fu.toUByte())), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedShort>())
    return vecPerm(a, b, mergelPerm)
}

public fun vecVmrglw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt {
    val mergelPerm = transmuteWithLayouts<Any?, VectorSignedInt>(Simd<UByte>(listOf(0x08u.toUByte(), 0x09u.toUByte(), 0x0Au.toUByte(), 0x0Bu.toUByte(), 0x18u.toUByte(), 0x19u.toUByte(), 0x1Au.toUByte(), 0x1Bu.toUByte(), 0x0Cu.toUByte(), 0x0Du.toUByte(), 0x0Eu.toUByte(), 0x0Fu.toUByte(), 0x1Cu.toUByte(), 0x1Du.toUByte(), 0x1Eu.toUByte(), 0x1Fu.toUByte())), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>())
    return vecPerm(a, b, mergelPerm)
}

public fun vecVmrghb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar {
    val mergelPerm = transmuteWithLayouts<Any?, VectorSignedChar>(Simd<UByte>(listOf(0x00u.toUByte(), 0x10u.toUByte(), 0x01u.toUByte(), 0x11u.toUByte(), 0x02u.toUByte(), 0x12u.toUByte(), 0x03u.toUByte(), 0x13u.toUByte(), 0x04u.toUByte(), 0x14u.toUByte(), 0x05u.toUByte(), 0x15u.toUByte(), 0x06u.toUByte(), 0x16u.toUByte(), 0x07u.toUByte(), 0x17u.toUByte())), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>())
    return vecPerm(a, b, mergelPerm)
}

public fun vecVmrghh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort {
    val mergelPerm = transmuteWithLayouts<Any?, VectorSignedShort>(Simd<UByte>(listOf(0x00u.toUByte(), 0x01u.toUByte(), 0x10u.toUByte(), 0x11u.toUByte(), 0x02u.toUByte(), 0x03u.toUByte(), 0x12u.toUByte(), 0x13u.toUByte(), 0x04u.toUByte(), 0x05u.toUByte(), 0x14u.toUByte(), 0x15u.toUByte(), 0x06u.toUByte(), 0x07u.toUByte(), 0x16u.toUByte(), 0x17u.toUByte())), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedShort>())
    return vecPerm(a, b, mergelPerm)
}

public fun vecVmrghw(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt {
    val mergelPerm = transmuteWithLayouts<Any?, VectorSignedInt>(Simd<UByte>(listOf(0x00u.toUByte(), 0x01u.toUByte(), 0x02u.toUByte(), 0x03u.toUByte(), 0x10u.toUByte(), 0x11u.toUByte(), 0x12u.toUByte(), 0x13u.toUByte(), 0x04u.toUByte(), 0x05u.toUByte(), 0x06u.toUByte(), 0x07u.toUByte(), 0x14u.toUByte(), 0x15u.toUByte(), 0x16u.toUByte(), 0x17u.toUByte())), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>())
    return vecPerm(a, b, mergelPerm)
}

public interface VectorMergeh<Other, T, out Result> {
    public fun vecMergeh(b: Other): Result
}

public fun VectorBoolChar.vecMergeh(b: VectorBoolChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(vecVmrghb(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorUnsignedChar.vecMergeh(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vecVmrghb(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecMergeh(b: VectorSignedChar): VectorSignedChar = vecVmrghb(this, b)

public fun VectorBoolShort.vecMergeh(b: VectorBoolShort): VectorBoolShort = transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(vecVmrghh(transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorUnsignedShort.vecMergeh(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vecVmrghh(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecMergeh(b: VectorSignedShort): VectorSignedShort = vecVmrghh(this, b)

public fun VectorBoolInt.vecMergeh(b: VectorBoolInt): VectorBoolInt = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(vecVmrghw(transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorUnsignedInt.vecMergeh(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vecVmrghw(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecMergeh(b: VectorSignedInt): VectorSignedInt = vecVmrghw(this, b)

public fun VectorFloat.vecMergeh(b: VectorFloat): VectorFloat = transmuteWithLayouts<VectorSignedInt, VectorFloat>(vecVmrghw(transmuteWithLayouts<VectorFloat, VectorSignedInt>(this, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorFloat, VectorSignedInt>(b, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorFloat>())

public interface VectorMergel<Other, T, out Result> {
    public fun vecMergel(b: Other): Result
}

public fun VectorBoolChar.vecMergel(b: VectorBoolChar): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(vecVmrglb(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorUnsignedChar.vecMergel(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vecVmrglb(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>()), transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(b, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecMergel(b: VectorSignedChar): VectorSignedChar = vecVmrglb(this, b)

public fun VectorBoolShort.vecMergel(b: VectorBoolShort): VectorBoolShort = transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(vecVmrglh(transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorUnsignedShort.vecMergel(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vecVmrglh(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecMergel(b: VectorSignedShort): VectorSignedShort = vecVmrglh(this, b)

public fun VectorBoolInt.vecMergel(b: VectorBoolInt): VectorBoolInt = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(vecVmrglw(transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorUnsignedInt.vecMergel(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vecVmrglw(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecMergel(b: VectorSignedInt): VectorSignedInt = vecVmrglw(this, b)

public fun VectorFloat.vecMergel(b: VectorFloat): VectorFloat = transmuteWithLayouts<VectorSignedInt, VectorFloat>(vecVmrglw(transmuteWithLayouts<VectorFloat, VectorSignedInt>(this, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorFloat, VectorSignedInt>(b, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorFloat>())

public fun vecVpkuhum(a: VectorSignedShort, b: VectorSignedShort): VectorSignedChar {
    val packPerm =
        if (true) {
            transmuteWithLayouts<Any?, VectorSignedChar>(Simd<UByte>(listOf(0x00u.toUByte(), 0x02u.toUByte(), 0x04u.toUByte(), 0x06u.toUByte(), 0x08u.toUByte(), 0x0Au.toUByte(), 0x0Cu.toUByte(), 0x0Eu.toUByte(), 0x10u.toUByte(), 0x12u.toUByte(), 0x14u.toUByte(), 0x16u.toUByte(), 0x18u.toUByte(), 0x1Au.toUByte(), 0x1Cu.toUByte(), 0x1Eu.toUByte())), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>())
        } else {
            transmuteWithLayouts<Any?, VectorSignedChar>(Simd<UByte>(listOf(0x01u.toUByte(), 0x03u.toUByte(), 0x05u.toUByte(), 0x07u.toUByte(), 0x09u.toUByte(), 0x0Bu.toUByte(), 0x0Du.toUByte(), 0x0Fu.toUByte(), 0x11u.toUByte(), 0x13u.toUByte(), 0x15u.toUByte(), 0x17u.toUByte(), 0x19u.toUByte(), 0x1Bu.toUByte(), 0x1Du.toUByte(), 0x1Fu.toUByte())), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>())
        }
    return transmuteWithLayouts<T, VectorSignedChar>(vecPerm(a, b, packPerm), UnresolvedTypeLayout<T>(), UnresolvedTypeLayout<VectorSignedChar>())
}

public fun vecVpkuwum(a: VectorSignedInt, b: VectorSignedInt): VectorSignedShort {
    val packPerm =
        if (true) {
            transmuteWithLayouts<Any?, VectorSignedShort>(Simd<UByte>(listOf(0x00u.toUByte(), 0x01u.toUByte(), 0x04u.toUByte(), 0x05u.toUByte(), 0x08u.toUByte(), 0x09u.toUByte(), 0x0Cu.toUByte(), 0x0Du.toUByte(), 0x10u.toUByte(), 0x11u.toUByte(), 0x14u.toUByte(), 0x15u.toUByte(), 0x18u.toUByte(), 0x19u.toUByte(), 0x1Cu.toUByte(), 0x1Du.toUByte())), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedShort>())
        } else {
            transmuteWithLayouts<Any?, VectorSignedShort>(Simd<UByte>(listOf(0x02u.toUByte(), 0x03u.toUByte(), 0x06u.toUByte(), 0x07u.toUByte(), 0x0Au.toUByte(), 0x0Bu.toUByte(), 0x0Eu.toUByte(), 0x0Fu.toUByte(), 0x12u.toUByte(), 0x13u.toUByte(), 0x16u.toUByte(), 0x17u.toUByte(), 0x1Au.toUByte(), 0x1Bu.toUByte(), 0x1Eu.toUByte(), 0x1Fu.toUByte())), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedShort>())
        }
    return transmuteWithLayouts<T, VectorSignedShort>(vecPerm(a, b, packPerm), UnresolvedTypeLayout<T>(), UnresolvedTypeLayout<VectorSignedShort>())
}

public interface VectorPack<Other, out Result> {
    public fun vecPack(b: Other): Result
}

public fun VectorSignedShort.vecPack(b: VectorSignedShort): VectorSignedChar = vecVpkuhum(this, b)

public fun VectorUnsignedShort.vecPack(b: VectorUnsignedShort): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vecVpkuhum(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorBoolShort.vecPack(b: VectorBoolShort): VectorBoolChar = transmuteWithLayouts<VectorSignedChar, VectorBoolChar>(vecVpkuhum(transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>()), transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorSignedInt.vecPack(b: VectorSignedInt): VectorSignedShort = vecVpkuwum(this, b)

public fun VectorUnsignedInt.vecPack(b: VectorUnsignedInt): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vecVpkuwum(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorBoolInt.vecPack(b: VectorBoolInt): VectorBoolShort = transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(vecVpkuwum(transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>()), transmuteWithLayouts<VectorBoolInt, VectorSignedInt>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun vecVpkshss(a: VectorSignedShort, b: VectorSignedShort): VectorSignedChar =
    if (true) {
        vpkshss(b, a)
    } else {
        vpkshss(a, b)
    }

public fun vecVpkshus(a: VectorSignedShort, b: VectorSignedShort): VectorUnsignedChar =
    if (true) {
        vpkshus(b, a)
    } else {
        vpkshus(a, b)
    }

public fun vecVpkuhus(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedChar =
    if (true) {
        vpkuhus(b, a)
    } else {
        vpkuhus(a, b)
    }

public fun vecVpkswss(a: VectorSignedInt, b: VectorSignedInt): VectorSignedShort =
    if (true) {
        vpkswss(b, a)
    } else {
        vpkswss(a, b)
    }

public fun vecVpkswus(a: VectorSignedInt, b: VectorSignedInt): VectorUnsignedShort =
    if (true) {
        vpkswus(b, a)
    } else {
        vpkswus(a, b)
    }

public fun vecVpkuwus(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedShort =
    if (true) {
        vpkuwus(b, a)
    } else {
        vpkuwus(a, b)
    }

public interface VectorPacks<Other, out Result> {
    public fun vecPacks(b: Other): Result
}

public fun VectorSignedShort.vecPacks(b: VectorSignedShort): VectorSignedChar = vecVpkshss(this, b)

public fun VectorUnsignedShort.vecPacks(b: VectorUnsignedShort): VectorUnsignedChar = vecVpkuhus(this, b)

public fun VectorSignedInt.vecPacks(b: VectorSignedInt): VectorSignedShort = vecVpkswss(this, b)

public fun VectorUnsignedInt.vecPacks(b: VectorUnsignedInt): VectorUnsignedShort = vecVpkuwus(this, b)

public interface VectorPacksu<Other, out Result> {
    public fun vecPacksu(b: Other): Result
}

public fun VectorSignedShort.vecPacksu(b: VectorSignedShort): VectorUnsignedChar = vecVpkshus(this, b)

public fun VectorUnsignedShort.vecPacksu(b: VectorUnsignedShort): VectorUnsignedChar = vecVpkuhus(this, b)

public fun VectorSignedInt.vecPacksu(b: VectorSignedInt): VectorUnsignedShort = vecVpkswus(this, b)

public fun VectorUnsignedInt.vecPacksu(b: VectorUnsignedInt): VectorUnsignedShort = vecVpkuwus(this, b)

public fun vecVupkhsb(a: VectorSignedChar): VectorSignedShort =
    if (true) {
        vupklsb(a)
    } else {
        vupkhsb(a)
    }

public fun vecVupklsb(a: VectorSignedChar): VectorSignedShort =
    if (true) {
        vupkhsb(a)
    } else {
        vupklsb(a)
    }

public fun vecVupkhsh(a: VectorSignedShort): VectorSignedInt =
    if (true) {
        vupklsh(a)
    } else {
        vupkhsh(a)
    }

public fun vecVupklsh(a: VectorSignedShort): VectorSignedInt =
    if (true) {
        vupkhsh(a)
    } else {
        vupklsh(a)
    }

public interface VectorUnpackh<out Result> {
    public fun vecUnpackh(): Result
}

public fun VectorSignedChar.vecUnpackh(): VectorSignedShort = vecVupkhsb(this)

public fun VectorBoolChar.vecUnpackh(): VectorBoolShort = transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(vecVupkhsb(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorSignedShort.vecUnpackh(): VectorSignedInt = vecVupkhsh(this)

public fun VectorBoolShort.vecUnpackh(): VectorBoolInt = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(vecVupkhsh(transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public interface VectorUnpackl<out Result> {
    public fun vecUnpackl(): Result
}

public fun VectorSignedChar.vecUnpackl(): VectorSignedShort = vecVupklsb(this)

public fun VectorBoolChar.vecUnpackl(): VectorBoolShort = transmuteWithLayouts<VectorSignedShort, VectorBoolShort>(vecVupklsb(transmuteWithLayouts<VectorBoolChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorSignedShort.vecUnpackl(): VectorSignedInt = vecVupklsh(this)

public fun VectorBoolShort.vecUnpackl(): VectorBoolInt = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(vecVupklsh(transmuteWithLayouts<VectorBoolShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public interface VectorSl<Other, out Result> {
    public fun vecSl(b: Other): Result
}

public fun vslb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = U8x16.splat(sizeOf<UByte>(typeLayoutT = PrimitiveLayouts.U8).toUByte() * 8u.toUByte()); simdArg0 }
    return transmuteWithLayouts<Any?, VectorUnsignedChar>(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>())
}

public fun vslh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = U16x8.splat(sizeOf<UShort>(typeLayoutT = PrimitiveLayouts.U16).toUShort() * 16u.toUShort()); simdArg0 }
    return transmuteWithLayouts<Any?, VectorUnsignedShort>(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>())
}

public fun vslw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = U32x4.splat(sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32).toUInt() * 32u); simdArg0 }
    return transmuteWithLayouts<Any?, VectorUnsignedInt>(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>())
}

public fun VectorUnsignedChar.vecSl(b: VectorUnsignedChar): VectorUnsignedChar = vslb(this, b)

public fun VectorSignedChar.vecSl(b: VectorUnsignedChar): VectorSignedChar = transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(vslb(transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), b), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorUnsignedShort.vecSl(b: VectorUnsignedShort): VectorUnsignedShort = vslh(this, b)

public fun VectorSignedShort.vecSl(b: VectorUnsignedShort): VectorSignedShort = transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(vslh(transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), b), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecSl(b: VectorUnsignedInt): VectorUnsignedInt = vslw(this, b)

public fun VectorSignedInt.vecSl(b: VectorUnsignedInt): VectorSignedInt = transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(vslw(transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), b), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())

public interface VectorSr<Other, out Result> {
    public fun vecSr(b: Other): Result
}

public fun vsrb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = U8x16.splat(sizeOf<UByte>(typeLayoutT = PrimitiveLayouts.U8).toUByte() * 8u.toUByte()); simdArg0 }
    return transmuteWithLayouts<Any?, VectorUnsignedChar>(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>())
}

public fun vsrh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = U16x8.splat(sizeOf<UShort>(typeLayoutT = PrimitiveLayouts.U16).toUShort() * 16u.toUShort()); simdArg0 }
    return transmuteWithLayouts<Any?, VectorUnsignedShort>(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>())
}

public fun vsrw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt {
    val a1 = a
    val b1 = run { val simdArg0 = b; val simdArg1 = U32x4.splat(sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32).toUInt() * 32u); simdArg0 }
    return transmuteWithLayouts<Any?, VectorUnsignedInt>(run { val simdArg0 = a1; val simdArg1 = b1; simdArg0 }, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>())
}

public fun VectorUnsignedChar.vecSr(b: VectorUnsignedChar): VectorUnsignedChar = vsrb(this, b)

public fun VectorSignedChar.vecSr(b: VectorUnsignedChar): VectorSignedChar = transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(vsrb(transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), b), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorUnsignedShort.vecSr(b: VectorUnsignedShort): VectorUnsignedShort = vsrh(this, b)

public fun VectorSignedShort.vecSr(b: VectorUnsignedShort): VectorSignedShort = transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(vsrh(transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), b), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecSr(b: VectorUnsignedInt): VectorUnsignedInt = vsrw(this, b)

public fun VectorSignedInt.vecSr(b: VectorUnsignedInt): VectorSignedInt = transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(vsrw(transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), b), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())

public interface VectorSra<Other, out Result> {
    public fun vecSra(b: Other): Result
}

public fun VectorUnsignedChar.vecSra(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vsrab(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecSra(b: VectorUnsignedChar): VectorSignedChar = vsrab(transmute(this), transmute(b))

public fun VectorUnsignedShort.vecSra(b: VectorUnsignedShort): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vsrah(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecSra(b: VectorUnsignedShort): VectorSignedShort = vsrah(transmute(this), transmute(b))

public fun VectorUnsignedInt.vecSra(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vsraw(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecSra(b: VectorUnsignedInt): VectorSignedInt = vsraw(transmute(this), transmute(b))

public interface VectorSld<T> {
    public fun vecSld(b: T, uimm4: Int): T

    public fun vecSldw(b: T, uimm2: Int): T
}

public fun vsldoi(a: VectorUnsignedChar, b: VectorUnsignedChar, uimm4: Int): VectorUnsignedChar {
    run {
        check(0 <= uimm4 && uimm4 < (1 shl 4)) { "UIMM4 doesn't fit in 4 bits (unsigned)" }
    }
    val d = uimm4.toUByte()
    return if (true) {
        val perm = Simd<UByte>(listOf((16u.toUByte() - d).toUByte(), (17u.toUByte() - d).toUByte(), (18u.toUByte() - d).toUByte(), (19u.toUByte() - d).toUByte(), (20u.toUByte() - d).toUByte(), (21u.toUByte() - d).toUByte(), (22u.toUByte() - d).toUByte(), (23u.toUByte() - d).toUByte(), (24u.toUByte() - d).toUByte(), (25u.toUByte() - d).toUByte(), (26u.toUByte() - d).toUByte(), (27u.toUByte() - d).toUByte(), (28u.toUByte() - d).toUByte(), (29u.toUByte() - d).toUByte(), (30u.toUByte() - d).toUByte(), (31u.toUByte() - d).toUByte()))
        vecPerm(b, a, transmute(perm))
    } else {
        val perm1 = Simd<UByte>(listOf(d, d + 1u.toUByte(), d + 2u.toUByte(), d + 3u.toUByte(), d + 4u.toUByte(), d + 5u.toUByte(), d + 6u.toUByte(), d + 7u.toUByte(), d + 8u.toUByte(), d + 9u.toUByte(), d + 10u.toUByte(), d + 11u.toUByte(), d + 12u.toUByte(), d + 13u.toUByte(), d + 14u.toUByte(), d + 15u.toUByte()))
        vecPerm(a, b, transmute(perm1))
    }
}

// Note: collapse the two once genericConstExprs are usable.
public fun xxsldwi(a: VectorUnsignedChar, b: VectorUnsignedChar, uimm2: Int): VectorUnsignedChar {
    run {
        check(0 <= uimm2 && uimm2 < (1 shl 2)) { "UIMM2 doesn't fit in 2 bits (unsigned)" }
    }
    val d = (uimm2 shl 2).toUByte()
    return if (true) {
        val perm = Simd<UByte>(listOf((16u.toUByte() - d).toUByte(), (17u.toUByte() - d).toUByte(), (18u.toUByte() - d).toUByte(), (19u.toUByte() - d).toUByte(), (20u.toUByte() - d).toUByte(), (21u.toUByte() - d).toUByte(), (22u.toUByte() - d).toUByte(), (23u.toUByte() - d).toUByte(), (24u.toUByte() - d).toUByte(), (25u.toUByte() - d).toUByte(), (26u.toUByte() - d).toUByte(), (27u.toUByte() - d).toUByte(), (28u.toUByte() - d).toUByte(), (29u.toUByte() - d).toUByte(), (30u.toUByte() - d).toUByte(), (31u.toUByte() - d).toUByte()))
        vecPerm(b, a, transmute(perm))
    } else {
        val perm1 = Simd<UByte>(listOf(d, d + 1u.toUByte(), d + 2u.toUByte(), d + 3u.toUByte(), d + 4u.toUByte(), d + 5u.toUByte(), d + 6u.toUByte(), d + 7u.toUByte(), d + 8u.toUByte(), d + 9u.toUByte(), d + 10u.toUByte(), d + 11u.toUByte(), d + 12u.toUByte(), d + 13u.toUByte(), d + 14u.toUByte(), d + 15u.toUByte()))
        vecPerm(a, b, transmute(perm1))
    }
}

public fun VectorBoolChar.vecSld(b: VectorBoolChar, uimm4: Int): VectorBoolChar = transmuteWithLayouts<Any?, VectorBoolChar>(vsldoi(transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), uimm4), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorBoolChar.vecSldw(b: VectorBoolChar, uimm2: Int): VectorBoolChar = transmuteWithLayouts<Any?, VectorBoolChar>(xxsldwi(transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorBoolChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorBoolChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), uimm2), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolChar>())

public fun VectorSignedChar.vecSld(b: VectorSignedChar, uimm4: Int): VectorSignedChar = transmuteWithLayouts<Any?, VectorSignedChar>(vsldoi(transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), uimm4), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorSignedChar.vecSldw(b: VectorSignedChar, uimm2: Int): VectorSignedChar = transmuteWithLayouts<Any?, VectorSignedChar>(xxsldwi(transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), uimm2), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorUnsignedChar.vecSld(b: VectorUnsignedChar, uimm4: Int): VectorUnsignedChar = transmuteWithLayouts<Any?, VectorUnsignedChar>(vsldoi(this, b, uimm4), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorUnsignedChar.vecSldw(b: VectorUnsignedChar, uimm2: Int): VectorUnsignedChar = transmuteWithLayouts<Any?, VectorUnsignedChar>(xxsldwi(this, b, uimm2), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorBoolShort.vecSld(b: VectorBoolShort, uimm4: Int): VectorBoolShort = transmuteWithLayouts<Any?, VectorBoolShort>(vsldoi(transmuteWithLayouts<VectorBoolShort, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorBoolShort, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedChar>()), uimm4), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorBoolShort.vecSldw(b: VectorBoolShort, uimm2: Int): VectorBoolShort = transmuteWithLayouts<Any?, VectorBoolShort>(xxsldwi(transmuteWithLayouts<VectorBoolShort, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorBoolShort, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorBoolShort>(), UnresolvedTypeLayout<VectorUnsignedChar>()), uimm2), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolShort>())

public fun VectorSignedShort.vecSld(b: VectorSignedShort, uimm4: Int): VectorSignedShort = transmuteWithLayouts<Any?, VectorSignedShort>(vsldoi(transmuteWithLayouts<VectorSignedShort, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorSignedShort, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedChar>()), uimm4), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorSignedShort.vecSldw(b: VectorSignedShort, uimm2: Int): VectorSignedShort = transmuteWithLayouts<Any?, VectorSignedShort>(xxsldwi(transmuteWithLayouts<VectorSignedShort, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorSignedShort, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedChar>()), uimm2), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedShort.vecSld(b: VectorUnsignedShort, uimm4: Int): VectorUnsignedShort = transmuteWithLayouts<Any?, VectorUnsignedShort>(vsldoi(transmuteWithLayouts<VectorUnsignedShort, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorUnsignedShort, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorUnsignedChar>()), uimm4), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorUnsignedShort.vecSldw(b: VectorUnsignedShort, uimm2: Int): VectorUnsignedShort = transmuteWithLayouts<Any?, VectorUnsignedShort>(xxsldwi(transmuteWithLayouts<VectorUnsignedShort, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorUnsignedShort, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorUnsignedChar>()), uimm2), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorBoolInt.vecSld(b: VectorBoolInt, uimm4: Int): VectorBoolInt = transmuteWithLayouts<Any?, VectorBoolInt>(vsldoi(transmuteWithLayouts<VectorBoolInt, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorBoolInt, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedChar>()), uimm4), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorBoolInt.vecSldw(b: VectorBoolInt, uimm2: Int): VectorBoolInt = transmuteWithLayouts<Any?, VectorBoolInt>(xxsldwi(transmuteWithLayouts<VectorBoolInt, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorBoolInt, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorBoolInt>(), UnresolvedTypeLayout<VectorUnsignedChar>()), uimm2), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorSignedInt.vecSld(b: VectorSignedInt, uimm4: Int): VectorSignedInt = transmuteWithLayouts<Any?, VectorSignedInt>(vsldoi(transmuteWithLayouts<VectorSignedInt, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorSignedInt, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedChar>()), uimm4), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorSignedInt.vecSldw(b: VectorSignedInt, uimm2: Int): VectorSignedInt = transmuteWithLayouts<Any?, VectorSignedInt>(xxsldwi(transmuteWithLayouts<VectorSignedInt, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorSignedInt, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedChar>()), uimm2), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedInt.vecSld(b: VectorUnsignedInt, uimm4: Int): VectorUnsignedInt = transmuteWithLayouts<Any?, VectorUnsignedInt>(vsldoi(transmuteWithLayouts<VectorUnsignedInt, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorUnsignedInt, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorUnsignedChar>()), uimm4), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorUnsignedInt.vecSldw(b: VectorUnsignedInt, uimm2: Int): VectorUnsignedInt = transmuteWithLayouts<Any?, VectorUnsignedInt>(xxsldwi(transmuteWithLayouts<VectorUnsignedInt, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorUnsignedInt, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorUnsignedChar>()), uimm2), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorFloat.vecSld(b: VectorFloat, uimm4: Int): VectorFloat = transmuteWithLayouts<Any?, VectorFloat>(vsldoi(transmuteWithLayouts<VectorFloat, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorFloat, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorUnsignedChar>()), uimm4), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorFloat.vecSldw(b: VectorFloat, uimm2: Int): VectorFloat = transmuteWithLayouts<Any?, VectorFloat>(xxsldwi(transmuteWithLayouts<VectorFloat, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorUnsignedChar>()), transmuteWithLayouts<VectorFloat, VectorUnsignedChar>(b, UnresolvedTypeLayout<VectorFloat>(), UnresolvedTypeLayout<VectorUnsignedChar>()), uimm2), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorFloat>())

public interface VectorSll<Other, out Result> {
    public fun vecSll(b: Other): Result
}

public fun VectorUnsignedChar.vecSll(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedInt, VectorUnsignedChar>(vsl(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecSll(b: VectorUnsignedChar): VectorSignedChar = transmuteWithLayouts<VectorSignedInt, VectorSignedChar>(vsl(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorUnsignedShort.vecSll(b: VectorUnsignedChar): VectorUnsignedShort = transmuteWithLayouts<VectorSignedInt, VectorUnsignedShort>(vsl(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecSll(b: VectorUnsignedChar): VectorSignedShort = transmuteWithLayouts<VectorSignedInt, VectorSignedShort>(vsl(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecSll(b: VectorUnsignedChar): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vsl(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecSll(b: VectorUnsignedChar): VectorSignedInt = vsl(transmute(this), transmute(b))

public interface VectorSrl<Other, out Result> {
    public fun vecSrl(b: Other): Result
}

public fun VectorUnsignedChar.vecSrl(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedInt, VectorUnsignedChar>(vsr(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecSrl(b: VectorUnsignedChar): VectorSignedChar = transmuteWithLayouts<VectorSignedInt, VectorSignedChar>(vsr(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorUnsignedShort.vecSrl(b: VectorUnsignedChar): VectorUnsignedShort = transmuteWithLayouts<VectorSignedInt, VectorUnsignedShort>(vsr(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecSrl(b: VectorUnsignedChar): VectorSignedShort = transmuteWithLayouts<VectorSignedInt, VectorSignedShort>(vsr(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecSrl(b: VectorUnsignedChar): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vsr(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecSrl(b: VectorUnsignedChar): VectorSignedInt = vsr(transmute(this), transmute(b))

public interface VectorSlo<Other, out Result> {
    public fun vecSlo(b: Other): Result
}

public fun VectorUnsignedChar.vecSlo(b: VectorSignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedInt, VectorUnsignedChar>(vslo(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecSlo(b: VectorSignedChar): VectorSignedChar = transmuteWithLayouts<VectorSignedInt, VectorSignedChar>(vslo(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorUnsignedShort.vecSlo(b: VectorSignedChar): VectorUnsignedShort = transmuteWithLayouts<VectorSignedInt, VectorUnsignedShort>(vslo(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecSlo(b: VectorSignedChar): VectorSignedShort = transmuteWithLayouts<VectorSignedInt, VectorSignedShort>(vslo(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecSlo(b: VectorSignedChar): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vslo(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecSlo(b: VectorSignedChar): VectorSignedInt = vslo(transmute(this), transmute(b))

public fun VectorFloat.vecSlo(b: VectorSignedChar): VectorFloat = transmuteWithLayouts<VectorSignedInt, VectorFloat>(vslo(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorUnsignedChar.vecSlo(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedInt, VectorUnsignedChar>(vslo(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecSlo(b: VectorUnsignedChar): VectorSignedChar = transmuteWithLayouts<VectorSignedInt, VectorSignedChar>(vslo(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorUnsignedShort.vecSlo(b: VectorUnsignedChar): VectorUnsignedShort = transmuteWithLayouts<VectorSignedInt, VectorUnsignedShort>(vslo(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecSlo(b: VectorUnsignedChar): VectorSignedShort = transmuteWithLayouts<VectorSignedInt, VectorSignedShort>(vslo(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecSlo(b: VectorUnsignedChar): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vslo(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecSlo(b: VectorUnsignedChar): VectorSignedInt = vslo(transmute(this), transmute(b))

public fun VectorFloat.vecSlo(b: VectorUnsignedChar): VectorFloat = transmuteWithLayouts<VectorSignedInt, VectorFloat>(vslo(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorFloat>())

public interface VectorSro<Other, out Result> {
    public fun vecSro(b: Other): Result
}

public fun VectorUnsignedChar.vecSro(b: VectorSignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedInt, VectorUnsignedChar>(vsro(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecSro(b: VectorSignedChar): VectorSignedChar = transmuteWithLayouts<VectorSignedInt, VectorSignedChar>(vsro(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorUnsignedShort.vecSro(b: VectorSignedChar): VectorUnsignedShort = transmuteWithLayouts<VectorSignedInt, VectorUnsignedShort>(vsro(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecSro(b: VectorSignedChar): VectorSignedShort = transmuteWithLayouts<VectorSignedInt, VectorSignedShort>(vsro(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecSro(b: VectorSignedChar): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vsro(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecSro(b: VectorSignedChar): VectorSignedInt = vsro(transmute(this), transmute(b))

public fun VectorFloat.vecSro(b: VectorSignedChar): VectorFloat = transmuteWithLayouts<VectorSignedInt, VectorFloat>(vsro(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorUnsignedChar.vecSro(b: VectorUnsignedChar): VectorUnsignedChar = transmuteWithLayouts<VectorSignedInt, VectorUnsignedChar>(vsro(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedChar.vecSro(b: VectorUnsignedChar): VectorSignedChar = transmuteWithLayouts<VectorSignedInt, VectorSignedChar>(vsro(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorUnsignedShort.vecSro(b: VectorUnsignedChar): VectorUnsignedShort = transmuteWithLayouts<VectorSignedInt, VectorUnsignedShort>(vsro(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedShort.vecSro(b: VectorUnsignedChar): VectorSignedShort = transmuteWithLayouts<VectorSignedInt, VectorSignedShort>(vsro(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorUnsignedInt.vecSro(b: VectorUnsignedChar): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vsro(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorSignedInt.vecSro(b: VectorUnsignedChar): VectorSignedInt = vsro(transmute(this), transmute(b))

public fun VectorFloat.vecSro(b: VectorUnsignedChar): VectorFloat = transmuteWithLayouts<VectorSignedInt, VectorFloat>(vsro(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorFloat>())

public fun vecVcntlzb(a: VectorSignedChar): VectorSignedChar = run { val simdArg0 = a; simdArg0 }

public fun vecVcntlzh(a: VectorSignedShort): VectorSignedShort = run { val simdArg0 = a; simdArg0 }

public fun vecVcntlzw(a: VectorSignedInt): VectorSignedInt = run { val simdArg0 = a; simdArg0 }

public interface VectorCntlz<T> {
    public fun vecCntlz(): T
}

public fun VectorSignedChar.vecCntlz(): VectorSignedChar = vecVcntlzb(this)

public fun VectorUnsignedChar.vecCntlz(): VectorUnsignedChar = transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(vecVcntlzb(transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(this, UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())), UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>())

public fun VectorSignedShort.vecCntlz(): VectorSignedShort = vecVcntlzh(this)

public fun VectorUnsignedShort.vecCntlz(): VectorUnsignedShort = transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(vecVcntlzh(transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(this, UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())), UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>())

public fun VectorSignedInt.vecCntlz(): VectorSignedInt = vecVcntlzw(this)

public fun VectorUnsignedInt.vecCntlz(): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(vecVcntlzw(transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(this, UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun vrlb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = b; simdArg0 }

public fun vrlh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = b; simdArg0 }

public fun vrlw(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt = run { val simdArg0 = a; val simdArg1 = a; val simdArg2 = b; simdArg0 }

public interface VectorRl<T, Shift> {
    public fun vecRl(b: Shift): T
}

public fun VectorSignedChar.vecRl(b: VectorUnsignedChar): VectorSignedChar = transmuteWithLayouts<VectorUnsignedChar, VectorSignedChar>(vrlb(transmuteWithLayouts<VectorSignedChar, VectorUnsignedChar>(this, UnresolvedTypeLayout<VectorSignedChar>(), UnresolvedTypeLayout<VectorUnsignedChar>()), b), UnresolvedTypeLayout<VectorUnsignedChar>(), UnresolvedTypeLayout<VectorSignedChar>())

public fun VectorSignedShort.vecRl(b: VectorUnsignedShort): VectorSignedShort = transmuteWithLayouts<VectorUnsignedShort, VectorSignedShort>(vrlh(transmuteWithLayouts<VectorSignedShort, VectorUnsignedShort>(this, UnresolvedTypeLayout<VectorSignedShort>(), UnresolvedTypeLayout<VectorUnsignedShort>()), b), UnresolvedTypeLayout<VectorUnsignedShort>(), UnresolvedTypeLayout<VectorSignedShort>())

public fun VectorSignedInt.vecRl(b: VectorUnsignedInt): VectorSignedInt = transmuteWithLayouts<VectorUnsignedInt, VectorSignedInt>(vrlw(transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(this, UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>()), b), UnresolvedTypeLayout<VectorUnsignedInt>(), UnresolvedTypeLayout<VectorSignedInt>())

public fun VectorUnsignedChar.vecRl(b: VectorUnsignedChar): VectorUnsignedChar = vrlb(this, b)

public fun VectorUnsignedShort.vecRl(b: VectorUnsignedShort): VectorUnsignedShort = vrlh(this, b)

public fun VectorUnsignedInt.vecRl(b: VectorUnsignedInt): VectorUnsignedInt = vrlw(this, b)

public interface VectorRound<T> {
    public fun vecRound(): T
}

public fun vecVrfin(a: VectorFloat): VectorFloat = vrfin(a)

public fun VectorFloat.vecRound(): VectorFloat = vecVrfin(this)
