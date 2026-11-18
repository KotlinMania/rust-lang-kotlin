// port-lint: source ops/shift_scalar.rs
package io.github.kotlinmania.rustlang.coresimd.ops.shiftscalar

import io.github.kotlinmania.rustlang.coresimd.Simd

// In the past there were inference issues when generically splatting arguments.
// Enumerate them instead.
public fun Simd<Byte>.shl(rhs: Byte, n: Int = 0): Simd<Byte> = this.shl(Simd.splat(rhs, n), n)

public fun Simd<Byte>.shr(rhs: Byte, n: Int = 0): Simd<Byte> = this.shr(Simd.splat(rhs, n), n)

public fun Simd<Short>.shl(rhs: Short, n: Int = 0): Simd<Short> = this.shl(Simd.splat(rhs, n), n)

public fun Simd<Short>.shr(rhs: Short, n: Int = 0): Simd<Short> = this.shr(Simd.splat(rhs, n), n)

public fun Simd<Int>.shl(rhs: Int, n: Int = 0): Simd<Int> = this.shl(Simd.splat(rhs, n), n)

public fun Simd<Int>.shr(rhs: Int, n: Int = 0): Simd<Int> = this.shr(Simd.splat(rhs, n), n)

public fun Simd<Long>.shl(rhs: Long, n: Int = 0): Simd<Long> = this.shl(Simd.splat(rhs, n), n)

public fun Simd<Long>.shr(rhs: Long, n: Int = 0): Simd<Long> = this.shr(Simd.splat(rhs, n), n)

public fun Simd<Int>.shlInt(rhs: Int, n: Int = 0): Simd<Int> = this.shl(Simd.splat(rhs, n), n)

public fun Simd<Int>.shrInt(rhs: Int, n: Int = 0): Simd<Int> = this.shr(Simd.splat(rhs, n), n)

public fun Simd<UByte>.shl(rhs: UByte, n: Int = 0): Simd<UByte> = this.shl(Simd.splat(rhs, n), n)

public fun Simd<UByte>.shr(rhs: UByte, n: Int = 0): Simd<UByte> = this.shr(Simd.splat(rhs, n), n)

public fun Simd<UShort>.shl(rhs: UShort, n: Int = 0): Simd<UShort> = this.shl(Simd.splat(rhs, n), n)

public fun Simd<UShort>.shr(rhs: UShort, n: Int = 0): Simd<UShort> = this.shr(Simd.splat(rhs, n), n)

public fun Simd<UInt>.shl(rhs: UInt, n: Int = 0): Simd<UInt> = this.shl(Simd.splat(rhs, n), n)

public fun Simd<UInt>.shr(rhs: UInt, n: Int = 0): Simd<UInt> = this.shr(Simd.splat(rhs, n), n)

public fun Simd<ULong>.shl(rhs: ULong, n: Int = 0): Simd<ULong> = this.shl(Simd.splat(rhs, n), n)

public fun Simd<ULong>.shr(rhs: ULong, n: Int = 0): Simd<ULong> = this.shr(Simd.splat(rhs, n), n)
