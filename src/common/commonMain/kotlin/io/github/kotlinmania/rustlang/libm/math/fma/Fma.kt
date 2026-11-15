// port-lint: source math/fma.rs
package io.github.kotlinmania.rustlang.libm.math.fma

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.libm.math.Generic
import io.github.kotlinmania.rustlang.math.support.env.Round

// See `fmaf16.rs` for that implementation

/**
 * Floating multiply add (f32)
 *
 * Computes `(x * y) + z`, rounded as one ternary operation (i.e. calculated with infinite precision).
 */
public fun fmaf(x: kotlin.Float, y: kotlin.Float, z: kotlin.Float): kotlin.Float {
    if (true) {
        return io.github.kotlinmania.rustlang.math.arch.aarch64.fma.fmaf(x, y, z)
    }
    return Generic.fmaWideRound(x, y, z, Round.Nearest).value
}

/**
 * Fused multiply add (f64)
 *
 * Computes `(x * y) + z`, rounded as one ternary operation (i.e. calculated with infinite precision).
 */
public fun fma(x: Double, y: Double, z: Double): Double {
    if (true) {
        return io.github.kotlinmania.rustlang.math.arch.aarch64.fma.fma(x, y, z)
    }
    return Generic.fmaRound(x, y, z, Round.Nearest).value
}

/**
 * Fused multiply add (f128)
 *
 * Computes `(x * y) + z`, rounded as one ternary operation (i.e. calculated with infinite precision).
 */
public fun fmaf128(x: F128, y: F128, z: F128): F128 = Generic.fmaRound(x, y, z, Round.Nearest).value
