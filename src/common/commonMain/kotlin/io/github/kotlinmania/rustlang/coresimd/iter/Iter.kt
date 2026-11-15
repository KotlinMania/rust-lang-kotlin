// port-lint: source iter.rs
package io.github.kotlinmania.rustlang.coresimd.iter

import io.github.kotlinmania.rustlang.Simd
import io.github.kotlinmania.rustlang.core.ops.arith.Add
import io.github.kotlinmania.rustlang.core.ops.arith.Mul

public fun <I : Iterator<Simd<Float, N>>> sumSimdFloatN(iter: I, n: Int): Simd<Float, N> = iter.fold(Simd.splat(0.toFloat()), Add::add)

public fun <I : Iterator<Simd<Float, N>>> productSimdFloatN(iter: I, n: Int): Simd<Float, N> = iter.fold(Simd.splat(1.toFloat()), Mul::mul)

public fun <I : Iterator<Simd<Double, N>>> sumSimdDoubleN(iter: I, n: Int): Simd<Double, N> = iter.fold(Simd.splat(0.toDouble()), Add::add)

public fun <I : Iterator<Simd<Double, N>>> productSimdDoubleN(iter: I, n: Int): Simd<Double, N> = iter.fold(Simd.splat(1.toDouble()), Mul::mul)

public fun <I : Iterator<Simd<UByte, N>>> sumSimdUByteN(iter: I, n: Int): Simd<UByte, N> = iter.fold(Simd.splat(0.toUByte()), Add::add)

public fun <I : Iterator<Simd<UByte, N>>> productSimdUByteN(iter: I, n: Int): Simd<UByte, N> = iter.fold(Simd.splat(1.toUByte()), Mul::mul)

public fun <I : Iterator<Simd<UShort, N>>> sumSimdUShortN(iter: I, n: Int): Simd<UShort, N> = iter.fold(Simd.splat(0.toUShort()), Add::add)

public fun <I : Iterator<Simd<UShort, N>>> productSimdUShortN(iter: I, n: Int): Simd<UShort, N> = iter.fold(Simd.splat(1.toUShort()), Mul::mul)

public fun <I : Iterator<Simd<UInt, N>>> sumSimdUIntN(iter: I, n: Int): Simd<UInt, N> = iter.fold(Simd.splat(0.toUInt()), Add::add)

public fun <I : Iterator<Simd<UInt, N>>> productSimdUIntN(iter: I, n: Int): Simd<UInt, N> = iter.fold(Simd.splat(1.toUInt()), Mul::mul)

public fun <I : Iterator<Simd<ULong, N>>> sumSimdULongN(iter: I, n: Int): Simd<ULong, N> = iter.fold(Simd.splat(0.toULong()), Add::add)

public fun <I : Iterator<Simd<ULong, N>>> productSimdULongN(iter: I, n: Int): Simd<ULong, N> = iter.fold(Simd.splat(1.toULong()), Mul::mul)

public fun <I : Iterator<Simd<Int, N>>> sumSimdIntN(iter: I, n: Int): Simd<Int, N> = iter.fold(Simd.splat(0), Add::add)

public fun <I : Iterator<Simd<Int, N>>> productSimdIntN(iter: I, n: Int): Simd<Int, N> = iter.fold(Simd.splat(1), Mul::mul)

public fun <I : Iterator<Simd<Byte, N>>> sumSimdByteN(iter: I, n: Int): Simd<Byte, N> = iter.fold(Simd.splat(0.toByte()), Add::add)

public fun <I : Iterator<Simd<Byte, N>>> productSimdByteN(iter: I, n: Int): Simd<Byte, N> = iter.fold(Simd.splat(1.toByte()), Mul::mul)

public fun <I : Iterator<Simd<Short, N>>> sumSimdShortN(iter: I, n: Int): Simd<Short, N> = iter.fold(Simd.splat(0.toShort()), Add::add)

public fun <I : Iterator<Simd<Short, N>>> productSimdShortN(iter: I, n: Int): Simd<Short, N> = iter.fold(Simd.splat(1.toShort()), Mul::mul)

public fun <I : Iterator<Simd<Long, N>>> sumSimdLongN(iter: I, n: Int): Simd<Long, N> = iter.fold(Simd.splat(0.toLong()), Add::add)

public fun <I : Iterator<Simd<Long, N>>> productSimdLongN(iter: I, n: Int): Simd<Long, N> = iter.fold(Simd.splat(1.toLong()), Mul::mul)
