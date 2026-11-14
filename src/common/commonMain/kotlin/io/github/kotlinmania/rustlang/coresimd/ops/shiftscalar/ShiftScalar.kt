// port-lint: source ops/shift_scalar.rs
package io.github.kotlinmania.rustlang.coresimd.ops.shiftscalar

import io.github.kotlinmania.rustlang.coresimd.Simd

// In the past there were inference issues when generically splatting arguments.
// Enumerate them instead.
public fun Simd<Byte, N>.shl(rhs: Byte, n: Int): Simd<Byte, N> = this.shl(Simd.splat(rhs))

public fun Simd<Byte, N>.shlByte(rhs: Byte, n: Int): Simd<Byte, N> = this.shl(Simd.splat(rhs))

public fun Simd<Byte, N>.shr(rhs: Byte, n: Int): Simd<Byte, N> = this.shr(Simd.splat(rhs))

public fun Simd<Byte, N>.shrByte(rhs: Byte, n: Int): Simd<Byte, N> = this.shr(Simd.splat(rhs))

public fun Simd<Short, N>.shl(rhs: Short, n: Int): Simd<Short, N> = this.shl(Simd.splat(rhs))

public fun Simd<Short, N>.shlShort(rhs: Short, n: Int): Simd<Short, N> = this.shl(Simd.splat(rhs))

public fun Simd<Short, N>.shr(rhs: Short, n: Int): Simd<Short, N> = this.shr(Simd.splat(rhs))

public fun Simd<Short, N>.shrShort(rhs: Short, n: Int): Simd<Short, N> = this.shr(Simd.splat(rhs))

public fun Simd<Int, N>.shl(rhs: Int, n: Int): Simd<Int, N> = this.shl(Simd.splat(rhs))

public fun Simd<Int, N>.shlInt(rhs: Int, n: Int): Simd<Int, N> = this.shl(Simd.splat(rhs))

public fun Simd<Int, N>.shr(rhs: Int, n: Int): Simd<Int, N> = this.shr(Simd.splat(rhs))

public fun Simd<Int, N>.shrInt(rhs: Int, n: Int): Simd<Int, N> = this.shr(Simd.splat(rhs))

public fun Simd<Long, N>.shl(rhs: Long, n: Int): Simd<Long, N> = this.shl(Simd.splat(rhs))

public fun Simd<Long, N>.shlLong(rhs: Long, n: Int): Simd<Long, N> = this.shl(Simd.splat(rhs))

public fun Simd<Long, N>.shr(rhs: Long, n: Int): Simd<Long, N> = this.shr(Simd.splat(rhs))

public fun Simd<Long, N>.shrLong(rhs: Long, n: Int): Simd<Long, N> = this.shr(Simd.splat(rhs))

public fun Simd<UByte, N>.shl(rhs: UByte, n: Int): Simd<UByte, N> = this.shl(Simd.splat(rhs))

public fun Simd<UByte, N>.shlUByte(rhs: UByte, n: Int): Simd<UByte, N> = this.shl(Simd.splat(rhs))

public fun Simd<UByte, N>.shr(rhs: UByte, n: Int): Simd<UByte, N> = this.shr(Simd.splat(rhs))

public fun Simd<UByte, N>.shrUByte(rhs: UByte, n: Int): Simd<UByte, N> = this.shr(Simd.splat(rhs))

public fun Simd<UShort, N>.shl(rhs: UShort, n: Int): Simd<UShort, N> = this.shl(Simd.splat(rhs))

public fun Simd<UShort, N>.shlUShort(rhs: UShort, n: Int): Simd<UShort, N> = this.shl(Simd.splat(rhs))

public fun Simd<UShort, N>.shr(rhs: UShort, n: Int): Simd<UShort, N> = this.shr(Simd.splat(rhs))

public fun Simd<UShort, N>.shrUShort(rhs: UShort, n: Int): Simd<UShort, N> = this.shr(Simd.splat(rhs))

public fun Simd<UInt, N>.shl(rhs: UInt, n: Int): Simd<UInt, N> = this.shl(Simd.splat(rhs))

public fun Simd<UInt, N>.shlUInt(rhs: UInt, n: Int): Simd<UInt, N> = this.shl(Simd.splat(rhs))

public fun Simd<UInt, N>.shr(rhs: UInt, n: Int): Simd<UInt, N> = this.shr(Simd.splat(rhs))

public fun Simd<UInt, N>.shrUInt(rhs: UInt, n: Int): Simd<UInt, N> = this.shr(Simd.splat(rhs))

public fun Simd<ULong, N>.shl(rhs: ULong, n: Int): Simd<ULong, N> = this.shl(Simd.splat(rhs))

public fun Simd<ULong, N>.shlULong(rhs: ULong, n: Int): Simd<ULong, N> = this.shl(Simd.splat(rhs))

public fun Simd<ULong, N>.shr(rhs: ULong, n: Int): Simd<ULong, N> = this.shr(Simd.splat(rhs))

public fun Simd<ULong, N>.shrULong(rhs: ULong, n: Int): Simd<ULong, N> = this.shr(Simd.splat(rhs))
