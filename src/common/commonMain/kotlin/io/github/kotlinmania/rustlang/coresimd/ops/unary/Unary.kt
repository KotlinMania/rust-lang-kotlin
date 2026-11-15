// port-lint: source ops/unary.rs
package io.github.kotlinmania.rustlang.coresimd.ops.unary

import io.github.kotlinmania.rustlang.Simd
import io.github.kotlinmania.rustlang.core.F16

public fun <Mask> Simd<F16, N>.neg(n: Int): Simd<F16, N> = run { val _simdArg0 = this; _simdArg0 }

public fun <Mask> Simd<Byte, N>.not(n: Int): Simd<Byte, N> = this xor (Simd.splat((0.toByte()).inv()))
