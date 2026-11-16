// port-lint: source ops/shift_scalar.rs
package io.github.kotlinmania.rustlang.coresimd.ops.shiftscalar

import io.github.kotlinmania.rustlang.coresimd.Simd

// In the past there were inference issues when generically splatting arguments.
// Enumerate them instead.
public fun Simd<Byte>.shl(rhs: Byte, n: Int): Simd<Byte> = this.shl(Simd.splat(rhs))

public fun Simd<Byte>.shlByte(rhs: Byte, n: Int): Simd<Byte> = this.shl(Simd.splat(rhs))

public fun Simd<Byte>.shr(rhs: Byte, n: Int): Simd<Byte> = this.shr(Simd.splat(rhs))

public fun Simd<Byte>.shrByte(rhs: Byte, n: Int): Simd<Byte> = this.shr(Simd.splat(rhs))

public fun Simd<Short>.shl(rhs: Short, n: Int): Simd<Short> = this.shl(Simd.splat(rhs))

public fun Simd<Short>.shlShort(rhs: Short, n: Int): Simd<Short> = this.shl(Simd.splat(rhs))

public fun Simd<Short>.shr(rhs: Short, n: Int): Simd<Short> = this.shr(Simd.splat(rhs))

public fun Simd<Short>.shrShort(rhs: Short, n: Int): Simd<Short> = this.shr(Simd.splat(rhs))

public fun Simd<Int>.shl(rhs: Int, n: Int): Simd<Int> = this.shl(Simd.splat(rhs))

public fun Simd<Int>.shlInt(rhs: Int, n: Int): Simd<Int> = this.shl(Simd.splat(rhs))

public fun Simd<Int>.shr(rhs: Int, n: Int): Simd<Int> = this.shr(Simd.splat(rhs))

public fun Simd<Int>.shrInt(rhs: Int, n: Int): Simd<Int> = this.shr(Simd.splat(rhs))

public fun Simd<Long>.shl(rhs: Long, n: Int): Simd<Long> = this.shl(Simd.splat(rhs))

public fun Simd<Long>.shlLong(rhs: Long, n: Int): Simd<Long> = this.shl(Simd.splat(rhs))

public fun Simd<Long>.shr(rhs: Long, n: Int): Simd<Long> = this.shr(Simd.splat(rhs))

public fun Simd<Long>.shrLong(rhs: Long, n: Int): Simd<Long> = this.shr(Simd.splat(rhs))

public fun Simd<UByte>.shl(rhs: UByte, n: Int): Simd<UByte> = this.shl(Simd.splat(rhs))

public fun Simd<UByte>.shlUByte(rhs: UByte, n: Int): Simd<UByte> = this.shl(Simd.splat(rhs))

public fun Simd<UByte>.shr(rhs: UByte, n: Int): Simd<UByte> = this.shr(Simd.splat(rhs))

public fun Simd<UByte>.shrUByte(rhs: UByte, n: Int): Simd<UByte> = this.shr(Simd.splat(rhs))

public fun Simd<UShort>.shl(rhs: UShort, n: Int): Simd<UShort> = this.shl(Simd.splat(rhs))

public fun Simd<UShort>.shlUShort(rhs: UShort, n: Int): Simd<UShort> = this.shl(Simd.splat(rhs))

public fun Simd<UShort>.shr(rhs: UShort, n: Int): Simd<UShort> = this.shr(Simd.splat(rhs))

public fun Simd<UShort>.shrUShort(rhs: UShort, n: Int): Simd<UShort> = this.shr(Simd.splat(rhs))

public fun Simd<UInt>.shl(rhs: UInt, n: Int): Simd<UInt> = this.shl(Simd.splat(rhs))

public fun Simd<UInt>.shlUInt(rhs: UInt, n: Int): Simd<UInt> = this.shl(Simd.splat(rhs))

public fun Simd<UInt>.shr(rhs: UInt, n: Int): Simd<UInt> = this.shr(Simd.splat(rhs))

public fun Simd<UInt>.shrUInt(rhs: UInt, n: Int): Simd<UInt> = this.shr(Simd.splat(rhs))

public fun Simd<ULong>.shl(rhs: ULong, n: Int): Simd<ULong> = this.shl(Simd.splat(rhs))

public fun Simd<ULong>.shlULong(rhs: ULong, n: Int): Simd<ULong> = this.shl(Simd.splat(rhs))

public fun Simd<ULong>.shr(rhs: ULong, n: Int): Simd<ULong> = this.shr(Simd.splat(rhs))

public fun Simd<ULong>.shrULong(rhs: ULong, n: Int): Simd<ULong> = this.shr(Simd.splat(rhs))
