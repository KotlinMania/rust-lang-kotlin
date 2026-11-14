// port-lint: source math/sqrt.rs
package io.github.kotlinmania.rustlang.libm.math.sqrt

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.libm.math.generic.sqrt.sqrtRound

/**
 * The square root of `x` (f16).
 */
public fun sqrtf16(x: F16): F16 {
    if (true) {
        return io.github.kotlinmania.rustlang.libm.math.arch.sqrtf16(x)
    }
    return sqrtRound(x, Round.Nearest).value
}

/**
 * The square root of `x` (f32).
 */
public fun sqrtf(x: kotlin.Float): kotlin.Float {
    if (true) {
        return io.github.kotlinmania.rustlang.libm.math.arch.sqrtf(x)
    }
    return sqrtRound(x, Round.Nearest).value
}

/**
 * The square root of `x` (f64).
 */
public fun sqrt(x: Double): Double {
    if (true) {
        return io.github.kotlinmania.rustlang.libm.math.arch.sqrt(x)
    }
    return sqrtRound(x, Round.Nearest).value
}

/**
 * The square root of `x` (f128).
 */
public fun sqrtf128(x: F128): F128 = sqrtRound(x, Round.Nearest).value
