// port-lint: source math/arch/wasm32/fabs.rs
package io.github.kotlinmania.rustlang.libm.math.arch.wasm32.fabs

import io.github.kotlinmania.rustlang.core.intrinsics.kmFabsf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmFabsf64

public fun fabsf(x: kotlin.Float): kotlin.Float = kmFabsf32(x)

public fun fabs(x: Double): Double = kmFabsf64(x)
