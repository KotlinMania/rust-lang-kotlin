// port-lint: source ops/unary.rs
package io.github.kotlinmania.rustlang.coresimd.ops.unary

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.coresimd.Simd

public fun <Mask> Simd<F16, N>.neg(n: Int): Simd<F16, N> = simd.simdNeg(this)

public fun <Mask> Simd<Byte, N>.not(n: Int): Simd<Byte, N> = this xor (Simd.splat((0.toByte()).inv()))
