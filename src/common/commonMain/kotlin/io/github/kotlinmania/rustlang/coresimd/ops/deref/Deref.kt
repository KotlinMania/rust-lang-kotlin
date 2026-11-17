// port-lint: source ops/deref.rs
package io.github.kotlinmania.rustlang.coresimd.ops.deref

import io.github.kotlinmania.rustlang.coresimd.Simd

// This module hacks in "implicit deref" for Simd's operators.
// Ideally, Rust would take care of this itself,
// and method calls usually handle the LHS implicitly.
// But this is not the case with arithmetic ops.

public fun <Mask, T> Simd<T>.add(rhs: Simd<T>, n: Int): Simd<T> = this.add(rhs, n)

public fun <Mask, T> Simd<T>.mul(rhs: Simd<T>, n: Int): Simd<T> = this.mul(rhs, n)

public fun <Mask, T> Simd<T>.sub(rhs: Simd<T>, n: Int): Simd<T> = this.sub(rhs, n)

public fun <Mask, T> Simd<T>.div(rhs: Simd<T>, n: Int): Simd<T> = this.div(rhs, n)

public fun <Mask, T> Simd<T>.rem(rhs: Simd<T>, n: Int): Simd<T> = this.rem(rhs, n)

public fun <Mask, T> Simd<T>.bitand(rhs: Simd<T>, n: Int): Simd<T> = this.bitand(rhs, n)

public fun <Mask, T> Simd<T>.bitor(rhs: Simd<T>, n: Int): Simd<T> = this.bitor(rhs, n)

public fun <Mask, T> Simd<T>.bitxor(rhs: Simd<T>, n: Int): Simd<T> = this.bitxor(rhs, n)

public fun <Mask, T> Simd<T>.shl(rhs: Simd<T>, n: Int): Simd<T> = this.shl(rhs, n)

public fun <Mask, T> Simd<T>.shr(rhs: Simd<T>, n: Int): Simd<T> = this.shr(rhs, n)
