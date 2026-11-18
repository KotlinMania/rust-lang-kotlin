// port-lint: source math/copysign.rs
package io.github.kotlinmania.rustlang.libm.math.copysign

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16

/**
 * Sign of Y, magnitude of X (f16)
 *
 * Constructs a number with the magnitude (absolute value) of its
 * first argument, `x`, and the sign of its second argument, `y`.
 */
public fun copysignf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.libm.math.generic.copysign.copysign(x.toDouble(), y.toDouble())

/**
 * Sign of Y, magnitude of X (f32)
 *
 * Constructs a number with the magnitude (absolute value) of its
 * first argument, `x`, and the sign of its second argument, `y`.
 */
public fun copysignf(x: kotlin.Float, y: kotlin.Float): kotlin.Float = io.github.kotlinmania.rustlang.libm.math.generic.copysign.copysign(x.toDouble(), y.toDouble())

/**
 * Sign of Y, magnitude of X (f64)
 *
 * Constructs a number with the magnitude (absolute value) of its
 * first argument, `x`, and the sign of its second argument, `y`.
 */
public fun copysign(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.libm.math.generic.copysign.copysign(x, y)

/**
 * Sign of Y, magnitude of X (f128)
 *
 * Constructs a number with the magnitude (absolute value) of its
 * first argument, `x`, and the sign of its second argument, `y`.
 */
public fun copysignf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.libm.math.generic.copysign.copysign(x.toDouble(), y.toDouble())
