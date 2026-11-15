package io.github.kotlinmania.rustlang.corearch.armshared.neon

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.simd.Simd

public fun fromSimdByte(simd: Int8x8T): Simd<Byte> = transmute(simd)

public fun fromSimdUByte(simd: Uint8x8T): Simd<UByte> = transmute(simd)

public fun fromSimdP8(simd: Poly8x8T): Simd<P8> = transmute(simd)

public fun fromSimdShort(simd: Int16x4T): Simd<Short> = transmute(simd)

public fun fromSimdUShort(simd: Uint16x4T): Simd<UShort> = transmute(simd)

public fun fromSimdP16(simd: Poly16x4T): Simd<P16> = transmute(simd)

public fun fromSimdInt(simd: Int32x2T): Simd<Int> = transmute(simd)

public fun fromSimdUInt(simd: Uint32x2T): Simd<UInt> = transmute(simd)

public fun fromSimdFloat(simd: Float32x2T): Simd<Float> = transmute(simd)

public fun fromSimdLong(simd: Int64x1T): Simd<Long> = transmute(simd)

public fun fromSimdULong(simd: Uint64x1T): Simd<ULong> = transmute(simd)

public fun fromSimdP64(simd: Poly64x1T): Simd<P64> = transmute(simd)

public fun fromSimdByte(simd: Int8x16T): Simd<Byte> = transmute(simd)

public fun fromSimdUByte(simd: Uint8x16T): Simd<UByte> = transmute(simd)

public fun fromSimdP8(simd: Poly8x16T): Simd<P8> = transmute(simd)

public fun fromSimdShort(simd: Int16x8T): Simd<Short> = transmute(simd)

public fun fromSimdUShort(simd: Uint16x8T): Simd<UShort> = transmute(simd)

public fun fromSimdP16(simd: Poly16x8T): Simd<P16> = transmute(simd)

public fun fromSimdInt(simd: Int32x4T): Simd<Int> = transmute(simd)

public fun fromSimdUInt(simd: Uint32x4T): Simd<UInt> = transmute(simd)

public fun fromSimdFloat(simd: Float32x4T): Simd<Float> = transmute(simd)

public fun fromSimdLong(simd: Int64x2T): Simd<Long> = transmute(simd)

public fun fromSimdULong(simd: Uint64x2T): Simd<ULong> = transmute(simd)

public fun fromSimdP64(simd: Poly64x2T): Simd<P64> = transmute(simd)

public fun fromSimdF16(simd: Float16x4T): Simd<F16> = transmute(simd)

public fun fromSimdF16(simd: Float16x8T): Simd<F16> = transmute(simd)

public fun Byte.asUnsigned(): UByte = transmute(this)

public fun UByte.asSigned(): Byte = transmute(this)

public fun Short.asUnsigned(): UShort = transmute(this)

public fun UShort.asSigned(): Short = transmute(this)

public fun Int.asUnsigned(): UInt = transmute(this)

public fun UInt.asSigned(): Int = transmute(this)

public fun Long.asUnsigned(): ULong = transmute(this)

public fun ULong.asSigned(): Long = transmute(this)

public fun ConstPtr.asUnsigned(): ConstPtr = transmute(this)

public fun ConstPtr.asSigned(): ConstPtr = transmute(this)

public fun ConstPtr.asUnsignedFromConstPtrShort(): ConstPtr = transmute(this)

public fun ConstPtr.asSignedFromConstPtrUShort(): ConstPtr = transmute(this)

public fun ConstPtr.asUnsignedFromConstPtrInt(): ConstPtr = transmute(this)

public fun ConstPtr.asSignedFromConstPtrUInt(): ConstPtr = transmute(this)

public fun ConstPtr.asUnsignedFromConstPtrLong(): ConstPtr = transmute(this)

public fun ConstPtr.asSignedFromConstPtrULong(): ConstPtr = transmute(this)

public fun MutPtr.asUnsigned(): MutPtr = transmute(this)

public fun MutPtr.asSigned(): MutPtr = transmute(this)

public fun MutPtr.asUnsignedFromMutPtrShort(): MutPtr = transmute(this)

public fun MutPtr.asSignedFromMutPtrUShort(): MutPtr = transmute(this)

public fun MutPtr.asUnsignedFromMutPtrInt(): MutPtr = transmute(this)

public fun MutPtr.asSignedFromMutPtrUInt(): MutPtr = transmute(this)

public fun MutPtr.asUnsignedFromMutPtrLong(): MutPtr = transmute(this)

public fun MutPtr.asSignedFromMutPtrULong(): MutPtr = transmute(this)
