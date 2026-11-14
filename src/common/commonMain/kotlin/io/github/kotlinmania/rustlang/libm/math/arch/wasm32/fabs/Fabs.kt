// port-lint: source math/arch/wasm32/fabs.rs
package io.github.kotlinmania.rustlang.libm.math.arch.wasm32.fabs

import kotlin.math.abs

public fun fabsf(x: kotlin.Float): kotlin.Float = abs(x)

public fun fabs(x: Double): Double = abs(x)
