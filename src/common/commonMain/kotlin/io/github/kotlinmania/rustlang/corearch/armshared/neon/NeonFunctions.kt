package io.github.kotlinmania.rustlang.corearch.armshared.neon

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.simd.Simd

public fun fromSimdByte(simd: Int8x8T): Simd<Byte> = transmuteWithLayouts<Int8x8T, Simd<Byte>>(simd, Int8x8T.typeLayout(), UnresolvedTypeLayout<Simd<Byte>>())

public fun fromSimdUByte(simd: Uint8x8T): Simd<UByte> = transmuteWithLayouts<Uint8x8T, Simd<UByte>>(simd, Uint8x8T.typeLayout(), UnresolvedTypeLayout<Simd<UByte>>())

public fun fromSimdP8(simd: Poly8x8T): Simd<P8> = transmuteWithLayouts<Poly8x8T, Simd<P8>>(simd, Poly8x8T.typeLayout(), UnresolvedTypeLayout<Simd<P8>>())

public fun fromSimdShort(simd: Int16x4T): Simd<Short> = transmuteWithLayouts<Int16x4T, Simd<Short>>(simd, Int16x4T.typeLayout(), UnresolvedTypeLayout<Simd<Short>>())

public fun fromSimdUShort(simd: Uint16x4T): Simd<UShort> = transmuteWithLayouts<Uint16x4T, Simd<UShort>>(simd, Uint16x4T.typeLayout(), UnresolvedTypeLayout<Simd<UShort>>())

public fun fromSimdP16(simd: Poly16x4T): Simd<P16> = transmuteWithLayouts<Poly16x4T, Simd<P16>>(simd, Poly16x4T.typeLayout(), UnresolvedTypeLayout<Simd<P16>>())

public fun fromSimdInt(simd: Int32x2T): Simd<Int> = transmuteWithLayouts<Int32x2T, Simd<Int>>(simd, Int32x2T.typeLayout(), UnresolvedTypeLayout<Simd<Int>>())

public fun fromSimdUInt(simd: Uint32x2T): Simd<UInt> = transmuteWithLayouts<Uint32x2T, Simd<UInt>>(simd, Uint32x2T.typeLayout(), UnresolvedTypeLayout<Simd<UInt>>())

public fun fromSimdFloat(simd: Float32x2T): Simd<Float> = transmuteWithLayouts<Float32x2T, Simd<Float>>(simd, Float32x2T.typeLayout(), UnresolvedTypeLayout<Simd<Float>>())

public fun fromSimdLong(simd: Int64x1T): Simd<Long> = transmuteWithLayouts<Int64x1T, Simd<Long>>(simd, Int64x1T.typeLayout(), UnresolvedTypeLayout<Simd<Long>>())

public fun fromSimdULong(simd: Uint64x1T): Simd<ULong> = transmuteWithLayouts<Uint64x1T, Simd<ULong>>(simd, Uint64x1T.typeLayout(), UnresolvedTypeLayout<Simd<ULong>>())

public fun fromSimdP64(simd: Poly64x1T): Simd<P64> = transmuteWithLayouts<Poly64x1T, Simd<P64>>(simd, Poly64x1T.typeLayout(), UnresolvedTypeLayout<Simd<P64>>())

public fun fromSimdByte(simd: Int8x16T): Simd<Byte> = transmuteWithLayouts<Int8x16T, Simd<Byte>>(simd, Int8x16T.typeLayout(), UnresolvedTypeLayout<Simd<Byte>>())

public fun fromSimdUByte(simd: Uint8x16T): Simd<UByte> = transmuteWithLayouts<Uint8x16T, Simd<UByte>>(simd, Uint8x16T.typeLayout(), UnresolvedTypeLayout<Simd<UByte>>())

public fun fromSimdP8(simd: Poly8x16T): Simd<P8> = transmuteWithLayouts<Poly8x16T, Simd<P8>>(simd, Poly8x16T.typeLayout(), UnresolvedTypeLayout<Simd<P8>>())

public fun fromSimdShort(simd: Int16x8T): Simd<Short> = transmuteWithLayouts<Int16x8T, Simd<Short>>(simd, Int16x8T.typeLayout(), UnresolvedTypeLayout<Simd<Short>>())

public fun fromSimdUShort(simd: Uint16x8T): Simd<UShort> = transmuteWithLayouts<Uint16x8T, Simd<UShort>>(simd, Uint16x8T.typeLayout(), UnresolvedTypeLayout<Simd<UShort>>())

public fun fromSimdP16(simd: Poly16x8T): Simd<P16> = transmuteWithLayouts<Poly16x8T, Simd<P16>>(simd, Poly16x8T.typeLayout(), UnresolvedTypeLayout<Simd<P16>>())

public fun fromSimdInt(simd: Int32x4T): Simd<Int> = transmuteWithLayouts<Int32x4T, Simd<Int>>(simd, Int32x4T.typeLayout(), UnresolvedTypeLayout<Simd<Int>>())

public fun fromSimdUInt(simd: Uint32x4T): Simd<UInt> = transmuteWithLayouts<Uint32x4T, Simd<UInt>>(simd, Uint32x4T.typeLayout(), UnresolvedTypeLayout<Simd<UInt>>())

public fun fromSimdFloat(simd: Float32x4T): Simd<Float> = transmuteWithLayouts<Float32x4T, Simd<Float>>(simd, Float32x4T.typeLayout(), UnresolvedTypeLayout<Simd<Float>>())

public fun fromSimdLong(simd: Int64x2T): Simd<Long> = transmuteWithLayouts<Int64x2T, Simd<Long>>(simd, Int64x2T.typeLayout(), UnresolvedTypeLayout<Simd<Long>>())

public fun fromSimdULong(simd: Uint64x2T): Simd<ULong> = transmuteWithLayouts<Uint64x2T, Simd<ULong>>(simd, Uint64x2T.typeLayout(), UnresolvedTypeLayout<Simd<ULong>>())

public fun fromSimdP64(simd: Poly64x2T): Simd<P64> = transmuteWithLayouts<Poly64x2T, Simd<P64>>(simd, Poly64x2T.typeLayout(), UnresolvedTypeLayout<Simd<P64>>())

public fun fromSimdF16(simd: Float16x4T): Simd<F16> = transmuteWithLayouts<Float16x4T, Simd<F16>>(simd, Float16x4T.typeLayout(), UnresolvedTypeLayout<Simd<F16>>())

public fun fromSimdF16(simd: Float16x8T): Simd<F16> = transmuteWithLayouts<Float16x8T, Simd<F16>>(simd, Float16x8T.typeLayout(), UnresolvedTypeLayout<Simd<F16>>())

public fun Byte.asUnsigned(): UByte = transmuteWithLayouts<Byte, UByte>(this, UnresolvedTypeLayout<Byte>(), PrimitiveLayouts.U8)

public fun UByte.asSigned(): Byte = transmuteWithLayouts<UByte, Byte>(this, UnresolvedTypeLayout<UByte>(), PrimitiveLayouts.I8)

public fun Short.asUnsigned(): UShort = transmuteWithLayouts<Short, UShort>(this, UnresolvedTypeLayout<Short>(), PrimitiveLayouts.U16)

public fun UShort.asSigned(): Short = transmuteWithLayouts<UShort, Short>(this, UnresolvedTypeLayout<UShort>(), PrimitiveLayouts.I16)

public fun Int.asUnsigned(): UInt = transmuteWithLayouts<Int, UInt>(this, UnresolvedTypeLayout<Int>(), PrimitiveLayouts.U32)

public fun UInt.asSigned(): Int = transmuteWithLayouts<UInt, Int>(this, UnresolvedTypeLayout<UInt>(), PrimitiveLayouts.I32)

public fun Long.asUnsigned(): ULong = transmuteWithLayouts<Long, ULong>(this, UnresolvedTypeLayout<Long>(), PrimitiveLayouts.U64)

public fun ULong.asSigned(): Long = transmuteWithLayouts<ULong, Long>(this, UnresolvedTypeLayout<ULong>(), PrimitiveLayouts.I64)

public fun ConstPtr.asUnsigned(): ConstPtr = transmuteWithLayouts<ConstPtr, ConstPtr>(this, UnresolvedTypeLayout<ConstPtr>(), PrimitiveLayouts.constPtrLayout(64L))

public fun ConstPtr.asSigned(): ConstPtr = transmuteWithLayouts<ConstPtr, ConstPtr>(this, UnresolvedTypeLayout<ConstPtr>(), PrimitiveLayouts.constPtrLayout(64L))

public fun ConstPtr.asUnsignedFromConstPtrShort(): ConstPtr = transmuteWithLayouts<ConstPtr, ConstPtr>(this, UnresolvedTypeLayout<ConstPtr>(), PrimitiveLayouts.constPtrLayout(64L))

public fun ConstPtr.asSignedFromConstPtrUShort(): ConstPtr = transmuteWithLayouts<ConstPtr, ConstPtr>(this, UnresolvedTypeLayout<ConstPtr>(), PrimitiveLayouts.constPtrLayout(64L))

public fun ConstPtr.asUnsignedFromConstPtrInt(): ConstPtr = transmuteWithLayouts<ConstPtr, ConstPtr>(this, UnresolvedTypeLayout<ConstPtr>(), PrimitiveLayouts.constPtrLayout(64L))

public fun ConstPtr.asSignedFromConstPtrUInt(): ConstPtr = transmuteWithLayouts<ConstPtr, ConstPtr>(this, UnresolvedTypeLayout<ConstPtr>(), PrimitiveLayouts.constPtrLayout(64L))

public fun ConstPtr.asUnsignedFromConstPtrLong(): ConstPtr = transmuteWithLayouts<ConstPtr, ConstPtr>(this, UnresolvedTypeLayout<ConstPtr>(), PrimitiveLayouts.constPtrLayout(64L))

public fun ConstPtr.asSignedFromConstPtrULong(): ConstPtr = transmuteWithLayouts<ConstPtr, ConstPtr>(this, UnresolvedTypeLayout<ConstPtr>(), PrimitiveLayouts.constPtrLayout(64L))

public fun MutPtr.asUnsigned(): MutPtr = transmuteWithLayouts<MutPtr, MutPtr>(this, UnresolvedTypeLayout<MutPtr>(), PrimitiveLayouts.mutPtrLayout(64L))

public fun MutPtr.asSigned(): MutPtr = transmuteWithLayouts<MutPtr, MutPtr>(this, UnresolvedTypeLayout<MutPtr>(), PrimitiveLayouts.mutPtrLayout(64L))

public fun MutPtr.asUnsignedFromMutPtrShort(): MutPtr = transmuteWithLayouts<MutPtr, MutPtr>(this, UnresolvedTypeLayout<MutPtr>(), PrimitiveLayouts.mutPtrLayout(64L))

public fun MutPtr.asSignedFromMutPtrUShort(): MutPtr = transmuteWithLayouts<MutPtr, MutPtr>(this, UnresolvedTypeLayout<MutPtr>(), PrimitiveLayouts.mutPtrLayout(64L))

public fun MutPtr.asUnsignedFromMutPtrInt(): MutPtr = transmuteWithLayouts<MutPtr, MutPtr>(this, UnresolvedTypeLayout<MutPtr>(), PrimitiveLayouts.mutPtrLayout(64L))

public fun MutPtr.asSignedFromMutPtrUInt(): MutPtr = transmuteWithLayouts<MutPtr, MutPtr>(this, UnresolvedTypeLayout<MutPtr>(), PrimitiveLayouts.mutPtrLayout(64L))

public fun MutPtr.asUnsignedFromMutPtrLong(): MutPtr = transmuteWithLayouts<MutPtr, MutPtr>(this, UnresolvedTypeLayout<MutPtr>(), PrimitiveLayouts.mutPtrLayout(64L))

public fun MutPtr.asSignedFromMutPtrULong(): MutPtr = transmuteWithLayouts<MutPtr, MutPtr>(this, UnresolvedTypeLayout<MutPtr>(), PrimitiveLayouts.mutPtrLayout(64L))
