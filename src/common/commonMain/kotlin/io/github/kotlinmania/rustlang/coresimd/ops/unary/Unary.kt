// port-lint: source ops/unary.rs
package io.github.kotlinmania.rustlang.coresimd.ops.unary

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.coresimd.Simd

public fun <Mask> Simd<F16>.neg(n: Int): Simd<F16> = run { val _simdArg0 = this; _simdArg0 }

public fun <Mask> Simd<Byte>.not(n: Int): Simd<Byte> = this xor (Simd.splat((0.toByte()).inv()))
