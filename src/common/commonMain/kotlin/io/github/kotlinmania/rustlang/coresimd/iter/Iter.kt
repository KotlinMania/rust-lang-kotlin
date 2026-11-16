// port-lint: source iter.rs
package io.github.kotlinmania.rustlang.coresimd.iter

import io.github.kotlinmania.rustlang.core.ops.arith.Add
import io.github.kotlinmania.rustlang.core.ops.arith.Mul
import io.github.kotlinmania.rustlang.coresimd.Simd

public fun <I : Iterator<Simd<Float>>> sumSimdFloatN(iter: I, n: Int): Simd<Float> = iter.fold(Simd.splat(0.toFloat()), Add::add)

public fun <I : Iterator<Simd<Float>>> productSimdFloatN(iter: I, n: Int): Simd<Float> = iter.fold(Simd.splat(1.toFloat()), Mul::mul)

public fun <I : Iterator<Simd<Double>>> sumSimdDoubleN(iter: I, n: Int): Simd<Double> = iter.fold(Simd.splat(0.toDouble()), Add::add)

public fun <I : Iterator<Simd<Double>>> productSimdDoubleN(iter: I, n: Int): Simd<Double> = iter.fold(Simd.splat(1.toDouble()), Mul::mul)

public fun <I : Iterator<Simd<UByte>>> sumSimdUByteN(iter: I, n: Int): Simd<UByte> = iter.fold(Simd.splat(0.toUByte()), Add::add)

public fun <I : Iterator<Simd<UByte>>> productSimdUByteN(iter: I, n: Int): Simd<UByte> = iter.fold(Simd.splat(1.toUByte()), Mul::mul)

public fun <I : Iterator<Simd<UShort>>> sumSimdUShortN(iter: I, n: Int): Simd<UShort> = iter.fold(Simd.splat(0.toUShort()), Add::add)

public fun <I : Iterator<Simd<UShort>>> productSimdUShortN(iter: I, n: Int): Simd<UShort> = iter.fold(Simd.splat(1.toUShort()), Mul::mul)

public fun <I : Iterator<Simd<UInt>>> sumSimdUIntN(iter: I, n: Int): Simd<UInt> = iter.fold(Simd.splat(0.toUInt()), Add::add)

public fun <I : Iterator<Simd<UInt>>> productSimdUIntN(iter: I, n: Int): Simd<UInt> = iter.fold(Simd.splat(1.toUInt()), Mul::mul)

public fun <I : Iterator<Simd<ULong>>> sumSimdULongN(iter: I, n: Int): Simd<ULong> = iter.fold(Simd.splat(0.toULong()), Add::add)

public fun <I : Iterator<Simd<ULong>>> productSimdULongN(iter: I, n: Int): Simd<ULong> = iter.fold(Simd.splat(1.toULong()), Mul::mul)

public fun <I : Iterator<Simd<Int>>> sumSimdIntN(iter: I, n: Int): Simd<Int> = iter.fold(Simd.splat(0), Add::add)

public fun <I : Iterator<Simd<Int>>> productSimdIntN(iter: I, n: Int): Simd<Int> = iter.fold(Simd.splat(1), Mul::mul)

public fun <I : Iterator<Simd<Byte>>> sumSimdByteN(iter: I, n: Int): Simd<Byte> = iter.fold(Simd.splat(0.toByte()), Add::add)

public fun <I : Iterator<Simd<Byte>>> productSimdByteN(iter: I, n: Int): Simd<Byte> = iter.fold(Simd.splat(1.toByte()), Mul::mul)

public fun <I : Iterator<Simd<Short>>> sumSimdShortN(iter: I, n: Int): Simd<Short> = iter.fold(Simd.splat(0.toShort()), Add::add)

public fun <I : Iterator<Simd<Short>>> productSimdShortN(iter: I, n: Int): Simd<Short> = iter.fold(Simd.splat(1.toShort()), Mul::mul)

public fun <I : Iterator<Simd<Long>>> sumSimdLongN(iter: I, n: Int): Simd<Long> = iter.fold(Simd.splat(0.toLong()), Add::add)

public fun <I : Iterator<Simd<Long>>> productSimdLongN(iter: I, n: Int): Simd<Long> = iter.fold(Simd.splat(1.toLong()), Mul::mul)
