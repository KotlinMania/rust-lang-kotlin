package io.github.kotlinmania.rustlang.corearch.armshared.neon

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.Mem
import io.github.kotlinmania.rustlang.corearch.mem.transmute
import io.github.kotlinmania.rustlang.corearch.simd.Simd

public fun fromSimdByte(simd: Int8x8T): Simd<Byte> = Mem.transmute(simd)

public fun fromSimdUByte(simd: Uint8x8T): Simd<UByte> = Mem.transmute(simd)

public fun fromSimdP8(simd: Poly8x8T): Simd<P8> = Mem.transmute(simd)

public fun fromSimdShort(simd: Int16x4T): Simd<Short> = Mem.transmute(simd)

public fun fromSimdUShort(simd: Uint16x4T): Simd<UShort> = Mem.transmute(simd)

public fun fromSimdP16(simd: Poly16x4T): Simd<P16> = Mem.transmute(simd)

public fun fromSimdInt(simd: Int32x2T): Simd<Int> = Mem.transmute(simd)

public fun fromSimdUInt(simd: Uint32x2T): Simd<UInt> = Mem.transmute(simd)

public fun fromSimdFloat(simd: Float32x2T): Simd<Float> = Mem.transmute(simd)

public fun fromSimdLong(simd: Int64x1T): Simd<Long> = Mem.transmute(simd)

public fun fromSimdULong(simd: Uint64x1T): Simd<ULong> = Mem.transmute(simd)

public fun fromSimdP64(simd: Poly64x1T): Simd<P64> = Mem.transmute(simd)

public fun fromSimdByte(simd: Int8x16T): Simd<Byte> = Mem.transmute(simd)

public fun fromSimdUByte(simd: Uint8x16T): Simd<UByte> = Mem.transmute(simd)

public fun fromSimdP8(simd: Poly8x16T): Simd<P8> = Mem.transmute(simd)

public fun fromSimdShort(simd: Int16x8T): Simd<Short> = Mem.transmute(simd)

public fun fromSimdUShort(simd: Uint16x8T): Simd<UShort> = Mem.transmute(simd)

public fun fromSimdP16(simd: Poly16x8T): Simd<P16> = Mem.transmute(simd)

public fun fromSimdInt(simd: Int32x4T): Simd<Int> = Mem.transmute(simd)

public fun fromSimdUInt(simd: Uint32x4T): Simd<UInt> = Mem.transmute(simd)

public fun fromSimdFloat(simd: Float32x4T): Simd<Float> = Mem.transmute(simd)

public fun fromSimdLong(simd: Int64x2T): Simd<Long> = Mem.transmute(simd)

public fun fromSimdULong(simd: Uint64x2T): Simd<ULong> = Mem.transmute(simd)

public fun fromSimdP64(simd: Poly64x2T): Simd<P64> = Mem.transmute(simd)

public fun fromSimdF16(simd: Float16x4T): Simd<F16> = Mem.transmute(simd)

public fun fromSimdF16(simd: Float16x8T): Simd<F16> = Mem.transmute(simd)

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

public fun MutPtr.asUnsigned(): MutPtr = transmute(this)

public fun MutPtr.asSigned(): MutPtr = transmute(this)
