// port-lint: source math/fabs.rs
package io.github.kotlinmania.rustlang.libm.math.fabs

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16

/**
 * Absolute value (magnitude) (f16)
 *
 * Calculates the absolute value (magnitude) of the argument `x`,
 * by direct manipulation of the bit representation of `x`.
 */
public fun fabsf16(x: F16): F16 = io.github.kotlinmania.rustlang.libm.math.generic.fabs(x)

/**
 * Absolute value (magnitude) (f32)
 *
 * Calculates the absolute value (magnitude) of the argument `x`,
 * by direct manipulation of the bit representation of `x`.
 */
public fun fabsf(x: kotlin.Float): kotlin.Float = io.github.kotlinmania.rustlang.libm.math.generic.fabs(x.toDouble())

/**
 * Absolute value (magnitude) (f64)
 *
 * Calculates the absolute value (magnitude) of the argument `x`,
 * by direct manipulation of the bit representation of `x`.
 */
public fun fabs(x: Double): Double = io.github.kotlinmania.rustlang.libm.math.generic.fabs(x)

/**
 * Absolute value (magnitude) (f128)
 *
 * Calculates the absolute value (magnitude) of the argument `x`,
 * by direct manipulation of the bit representation of `x`.
 */
public fun fabsf128(x: F128): F128 = io.github.kotlinmania.rustlang.libm.math.generic.fabs(x)
