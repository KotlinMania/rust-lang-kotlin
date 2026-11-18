// port-lint: source iter.rs
package io.github.kotlinmania.rustlang.coresimd.iter

import io.github.kotlinmania.rustlang.core.ops.arith.Add
import io.github.kotlinmania.rustlang.core.ops.arith.Mul
import io.github.kotlinmania.rustlang.coresimd.Simd

public fun <I : Iterator<Simd<Float>>> sumSimdFloatN(iter: I, n: Int = 0): Simd<Float> = iter.fold(Simd.splat(0.toFloat(), n), Add::add)

public fun <I : Iterator<Simd<Float>>> productSimdFloatN(iter: I, n: Int = 0): Simd<Float> = iter.fold(Simd.splat(1.toFloat(), n), Mul::mul)

public fun <I : Iterator<Simd<Double>>> sumSimdDoubleN(iter: I, n: Int = 0): Simd<Double> = iter.fold(Simd.splat(0.toDouble(), n), Add::add)

public fun <I : Iterator<Simd<Double>>> productSimdDoubleN(iter: I, n: Int = 0): Simd<Double> = iter.fold(Simd.splat(1.toDouble(), n), Mul::mul)

public fun <I : Iterator<Simd<UByte>>> sumSimdUByteN(iter: I, n: Int = 0): Simd<UByte> = iter.fold(Simd.splat(0.toUByte(), n), Add::add)

public fun <I : Iterator<Simd<UByte>>> productSimdUByteN(iter: I, n: Int = 0): Simd<UByte> = iter.fold(Simd.splat(1.toUByte(), n), Mul::mul)

public fun <I : Iterator<Simd<UShort>>> sumSimdUShortN(iter: I, n: Int = 0): Simd<UShort> = iter.fold(Simd.splat(0.toUShort(), n), Add::add)

public fun <I : Iterator<Simd<UShort>>> productSimdUShortN(iter: I, n: Int = 0): Simd<UShort> = iter.fold(Simd.splat(1.toUShort(), n), Mul::mul)

public fun <I : Iterator<Simd<UInt>>> sumSimdUIntN(iter: I, n: Int = 0): Simd<UInt> = iter.fold(Simd.splat(0.toUInt(), n), Add::add)

public fun <I : Iterator<Simd<UInt>>> productSimdUIntN(iter: I, n: Int = 0): Simd<UInt> = iter.fold(Simd.splat(1.toUInt(), n), Mul::mul)

public fun <I : Iterator<Simd<ULong>>> sumSimdULongN(iter: I, n: Int = 0): Simd<ULong> = iter.fold(Simd.splat(0.toULong(), n), Add::add)

public fun <I : Iterator<Simd<ULong>>> productSimdULongN(iter: I, n: Int = 0): Simd<ULong> = iter.fold(Simd.splat(1.toULong(), n), Mul::mul)

public fun <I : Iterator<Simd<Int>>> sumSimdIntN(iter: I, n: Int = 0): Simd<Int> = iter.fold(Simd.splat(0, n), Add::add)

public fun <I : Iterator<Simd<Int>>> productSimdIntN(iter: I, n: Int = 0): Simd<Int> = iter.fold(Simd.splat(1, n), Mul::mul)

public fun <I : Iterator<Simd<Byte>>> sumSimdByteN(iter: I, n: Int = 0): Simd<Byte> = iter.fold(Simd.splat(0.toByte(), n), Add::add)

public fun <I : Iterator<Simd<Byte>>> productSimdByteN(iter: I, n: Int = 0): Simd<Byte> = iter.fold(Simd.splat(1.toByte(), n), Mul::mul)

public fun <I : Iterator<Simd<Short>>> sumSimdShortN(iter: I, n: Int = 0): Simd<Short> = iter.fold(Simd.splat(0.toShort(), n), Add::add)

public fun <I : Iterator<Simd<Short>>> productSimdShortN(iter: I, n: Int = 0): Simd<Short> = iter.fold(Simd.splat(1.toShort(), n), Mul::mul)

public fun <I : Iterator<Simd<Long>>> sumSimdLongN(iter: I, n: Int = 0): Simd<Long> = iter.fold(Simd.splat(0.toLong(), n), Add::add)

public fun <I : Iterator<Simd<Long>>> productSimdLongN(iter: I, n: Int = 0): Simd<Long> = iter.fold(Simd.splat(1.toLong(), n), Mul::mul)
