// port-lint: source math/fdim.rs
package io.github.kotlinmania.rustlang.libm.math.fdim

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16

/**
 * Positive difference (f16)
 *
 * Determines the positive difference between arguments, returning:
 * * x - y if x > y, or
 * * +0 if x <= y, or
 * * NAN if either argument is NAN.
 *
 * A range error may occur.
 */
public fun fdimf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.libm.math.generic.fdim(x.toDouble(), y.toDouble())

/**
 * Positive difference (f32)
 *
 * Determines the positive difference between arguments, returning:
 * * x - y if x > y, or
 * * +0 if x <= y, or
 * * NAN if either argument is NAN.
 *
 * A range error may occur.
 */
public fun fdimf(x: kotlin.Float, y: kotlin.Float): kotlin.Float = io.github.kotlinmania.rustlang.libm.math.generic.fdim(x.toDouble(), y.toDouble())

/**
 * Positive difference (f64)
 *
 * Determines the positive difference between arguments, returning:
 * * x - y if x > y, or
 * * +0 if x <= y, or
 * * NAN if either argument is NAN.
 *
 * A range error may occur.
 */
public fun fdim(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.libm.math.generic.fdim(x, y)

/**
 * Positive difference (f128)
 *
 * Determines the positive difference between arguments, returning:
 * * x - y if x > y, or
 * * +0 if x <= y, or
 * * NAN if either argument is NAN.
 *
 * A range error may occur.
 */
public fun fdimf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.libm.math.generic.fdim(x.toDouble(), y.toDouble())
