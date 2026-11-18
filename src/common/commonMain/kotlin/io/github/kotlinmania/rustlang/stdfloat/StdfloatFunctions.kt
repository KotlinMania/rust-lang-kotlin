package io.github.kotlinmania.rustlang.stdfloat

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.coresimd.Simd

public fun Simd<F16>.fract(n: Int = 0): Simd<F16> = this - this.trunc()
