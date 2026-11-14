// port-lint: source math/fma.rs
package io.github.kotlinmania.rustlang.libm.math.fma

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.libm.math.generic.fma.fmaRound
import io.github.kotlinmania.rustlang.libm.math.generic.fmawide.fmaWideRound

// See `fmaf16.rs` for that implementation

/**
 * Floating multiply add (f32)
 *
 * Computes `(x * y) + z`, rounded as one ternary operation (i.e. calculated with infinite precision).
 */
public fun fmaf(x: kotlin.Float, y: kotlin.Float, z: kotlin.Float): kotlin.Float {
    if (true) {
        return io.github.kotlinmania.rustlang.libm.math.arch.fmaf(x, y, z)
    }
    return fmaWideRound(x, y, z, Round.Nearest).value
}

/**
 * Fused multiply add (f64)
 *
 * Computes `(x * y) + z`, rounded as one ternary operation (i.e. calculated with infinite precision).
 */
public fun fma(x: Double, y: Double, z: Double): Double {
    if (true) {
        return io.github.kotlinmania.rustlang.libm.math.arch.fma(x, y, z)
    }
    return fmaRound(x, y, z, Round.Nearest).value
}

/**
 * Fused multiply add (f128)
 *
 * Computes `(x * y) + z`, rounded as one ternary operation (i.e. calculated with infinite precision).
 */
public fun fmaf128(x: F128, y: F128, z: F128): F128 = fmaRound(x, y, z, Round.Nearest).value
