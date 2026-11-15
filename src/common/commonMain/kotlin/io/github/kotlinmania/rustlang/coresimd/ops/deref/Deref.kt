// port-lint: source ops/deref.rs
package io.github.kotlinmania.rustlang.coresimd.ops.deref

import io.github.kotlinmania.rustlang.coresimd.Simd

// This module hacks in "implicit deref" for Simd's operators.
// Ideally, Rust would take care of this itself,
// and method calls usually handle the LHS implicitly.
// But this is not the case with arithmetic ops.

public fun <Mask, T> Simd<T, N>.add(rhs: Simd<T>, n: Int): Simd<T> = this.add(rhs)

public fun <Mask, T> Simd<T, N>.addSimdTN(rhs: Simd<T>, n: Int): Simd<T> = this.add(rhs)

public fun <Mask, T> Simd<T, N>.mul(rhs: Simd<T>, n: Int): Simd<T> = this.mul(rhs)

public fun <Mask, T> Simd<T, N>.mulSimdTN(rhs: Simd<T>, n: Int): Simd<T> = this.mul(rhs)

public fun <Mask, T> Simd<T, N>.sub(rhs: Simd<T>, n: Int): Simd<T> = this.sub(rhs)

public fun <Mask, T> Simd<T, N>.subSimdTN(rhs: Simd<T>, n: Int): Simd<T> = this.sub(rhs)

public fun <Mask, T> Simd<T, N>.div(rhs: Simd<T>, n: Int): Simd<T> = this.div(rhs)

public fun <Mask, T> Simd<T, N>.divSimdTN(rhs: Simd<T>, n: Int): Simd<T> = this.div(rhs)

public fun <Mask, T> Simd<T, N>.rem(rhs: Simd<T>, n: Int): Simd<T> = this.rem(rhs)

public fun <Mask, T> Simd<T, N>.remSimdTN(rhs: Simd<T>, n: Int): Simd<T> = this.rem(rhs)

public fun <Mask, T> Simd<T, N>.bitand(rhs: Simd<T>, n: Int): Simd<T> = this.bitand(rhs)

public fun <Mask, T> Simd<T, N>.bitandSimdTN(rhs: Simd<T>, n: Int): Simd<T> = this.bitand(rhs)

public fun <Mask, T> Simd<T, N>.bitor(rhs: Simd<T>, n: Int): Simd<T> = this.bitor(rhs)

public fun <Mask, T> Simd<T, N>.bitorSimdTN(rhs: Simd<T>, n: Int): Simd<T> = this.bitor(rhs)

public fun <Mask, T> Simd<T, N>.bitxor(rhs: Simd<T>, n: Int): Simd<T> = this.bitxor(rhs)

public fun <Mask, T> Simd<T, N>.bitxorSimdTN(rhs: Simd<T>, n: Int): Simd<T> = this.bitxor(rhs)

public fun <Mask, T> Simd<T, N>.shl(rhs: Simd<T>, n: Int): Simd<T> = this.shl(rhs)

public fun <Mask, T> Simd<T, N>.shlSimdTN(rhs: Simd<T>, n: Int): Simd<T> = this.shl(rhs)

public fun <Mask, T> Simd<T, N>.shr(rhs: Simd<T>, n: Int): Simd<T> = this.shr(rhs)

public fun <Mask, T> Simd<T, N>.shrSimdTN(rhs: Simd<T>, n: Int): Simd<T> = this.shr(rhs)
