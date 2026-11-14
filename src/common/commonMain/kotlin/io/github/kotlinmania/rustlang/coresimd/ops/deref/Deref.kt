// port-lint: source ops/deref.rs
package io.github.kotlinmania.rustlang.coresimd.ops.deref

import io.github.kotlinmania.rustlang.coresimd.Simd

// This module hacks in "implicit deref" for Simd's operators.
// Ideally, Rust would take care of this itself,
// and method calls usually handle the LHS implicitly.
// But this is not the case with arithmetic ops.

public fun <Mask, T> Simd<T, N>.add(rhs: Simd<T, N>, n: Int): Simd<T> where T : SimdElement<Any?> = this.add(rhs)

public fun <Mask, T> Simd<T, N>.addSimdTN(rhs: Simd<T, N>, n: Int): Simd<T> where T : SimdElement<Any?> = this.add(rhs)

public fun <Mask, T> Simd<T, N>.mul(rhs: Simd<T, N>, n: Int): Simd<T> where T : SimdElement<Any?> = this.mul(rhs)

public fun <Mask, T> Simd<T, N>.mulSimdTN(rhs: Simd<T, N>, n: Int): Simd<T> where T : SimdElement<Any?> = this.mul(rhs)

public fun <Mask, T> Simd<T, N>.sub(rhs: Simd<T, N>, n: Int): Simd<T> where T : SimdElement<Any?> = this.sub(rhs)

public fun <Mask, T> Simd<T, N>.subSimdTN(rhs: Simd<T, N>, n: Int): Simd<T> where T : SimdElement<Any?> = this.sub(rhs)

public fun <Mask, T> Simd<T, N>.div(rhs: Simd<T, N>, n: Int): Simd<T> where T : SimdElement<Any?> = this.div(rhs)

public fun <Mask, T> Simd<T, N>.divSimdTN(rhs: Simd<T, N>, n: Int): Simd<T> where T : SimdElement<Any?> = this.div(rhs)

public fun <Mask, T> Simd<T, N>.rem(rhs: Simd<T, N>, n: Int): Simd<T> where T : SimdElement<Any?> = this.rem(rhs)

public fun <Mask, T> Simd<T, N>.remSimdTN(rhs: Simd<T, N>, n: Int): Simd<T> where T : SimdElement<Any?> = this.rem(rhs)

public fun <Mask, T> Simd<T, N>.bitand(rhs: Simd<T, N>, n: Int): Simd<T> where T : SimdElement<Any?> = this.bitand(rhs)

public fun <Mask, T> Simd<T, N>.bitandSimdTN(rhs: Simd<T, N>, n: Int): Simd<T> where T : SimdElement<Any?> = this.bitand(rhs)

public fun <Mask, T> Simd<T, N>.bitor(rhs: Simd<T, N>, n: Int): Simd<T> where T : SimdElement<Any?> = this.bitor(rhs)

public fun <Mask, T> Simd<T, N>.bitorSimdTN(rhs: Simd<T, N>, n: Int): Simd<T> where T : SimdElement<Any?> = this.bitor(rhs)

public fun <Mask, T> Simd<T, N>.bitxor(rhs: Simd<T, N>, n: Int): Simd<T> where T : SimdElement<Any?> = this.bitxor(rhs)

public fun <Mask, T> Simd<T, N>.bitxorSimdTN(rhs: Simd<T, N>, n: Int): Simd<T> where T : SimdElement<Any?> = this.bitxor(rhs)

public fun <Mask, T> Simd<T, N>.shl(rhs: Simd<T, N>, n: Int): Simd<T> where T : SimdElement<Any?> = this.shl(rhs)

public fun <Mask, T> Simd<T, N>.shlSimdTN(rhs: Simd<T, N>, n: Int): Simd<T> where T : SimdElement<Any?> = this.shl(rhs)

public fun <Mask, T> Simd<T, N>.shr(rhs: Simd<T, N>, n: Int): Simd<T> where T : SimdElement<Any?> = this.shr(rhs)

public fun <Mask, T> Simd<T, N>.shrSimdTN(rhs: Simd<T, N>, n: Int): Simd<T> where T : SimdElement<Any?> = this.shr(rhs)
