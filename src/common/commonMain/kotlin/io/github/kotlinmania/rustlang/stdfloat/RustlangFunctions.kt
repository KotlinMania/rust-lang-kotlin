package io.github.kotlinmania.rustlang.stdfloat

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.coresimd.Simd

public fun Simd<F16, N>.fract(n: Int): Simd<F16, N> = this - this.trunc()
