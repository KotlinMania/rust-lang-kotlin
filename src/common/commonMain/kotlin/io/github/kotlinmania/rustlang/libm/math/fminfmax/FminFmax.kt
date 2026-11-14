// port-lint: source math/fmin_fmax.rs
package io.github.kotlinmania.rustlang.libm.math.fminfmax

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16

/**
 * Return the lesser of two arguments or, if either argument is NaN, the other argument.
 *
 * This coincides with IEEE 754-2011 `minNum`. The result disregards signed zero (meaning if
 * the inputs are -0.0 and +0.0, either may be returned).
 */
public fun fminf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.libm.math.generic.fmin(x.toDouble(), y.toDouble())

/**
 * Return the lesser of two arguments or, if either argument is NaN, the other argument.
 *
 * This coincides with IEEE 754-2011 `minNum`. The result disregards signed zero (meaning if
 * the inputs are -0.0 and +0.0, either may be returned).
 */
public fun fminf(x: kotlin.Float, y: kotlin.Float): kotlin.Float = io.github.kotlinmania.rustlang.libm.math.generic.fmin(x.toDouble(), y.toDouble())

/**
 * Return the lesser of two arguments or, if either argument is NaN, the other argument.
 *
 * This coincides with IEEE 754-2011 `minNum`. The result disregards signed zero (meaning if
 * the inputs are -0.0 and +0.0, either may be returned).
 */
public fun fmin(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.libm.math.generic.fmin(x, y)

/**
 * Return the lesser of two arguments or, if either argument is NaN, the other argument.
 *
 * This coincides with IEEE 754-2011 `minNum`. The result disregards signed zero (meaning if
 * the inputs are -0.0 and +0.0, either may be returned).
 */
public fun fminf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.libm.math.generic.fmin(x.toDouble(), y.toDouble())

/**
 * Return the greater of two arguments or, if either argument is NaN, the other argument.
 *
 * This coincides with IEEE 754-2011 `maxNum`. The result disregards signed zero (meaning if
 * the inputs are -0.0 and +0.0, either may be returned).
 */
public fun fmaxf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.libm.math.generic.fmax(x.toDouble(), y.toDouble())

/**
 * Return the greater of two arguments or, if either argument is NaN, the other argument.
 *
 * This coincides with IEEE 754-2011 `maxNum`. The result disregards signed zero (meaning if
 * the inputs are -0.0 and +0.0, either may be returned).
 */
public fun fmaxf(x: kotlin.Float, y: kotlin.Float): kotlin.Float = io.github.kotlinmania.rustlang.libm.math.generic.fmax(x.toDouble(), y.toDouble())

/**
 * Return the greater of two arguments or, if either argument is NaN, the other argument.
 *
 * This coincides with IEEE 754-2011 `maxNum`. The result disregards signed zero (meaning if
 * the inputs are -0.0 and +0.0, either may be returned).
 */
public fun fmax(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.libm.math.generic.fmax(x, y)

/**
 * Return the greater of two arguments or, if either argument is NaN, the other argument.
 *
 * This coincides with IEEE 754-2011 `maxNum`. The result disregards signed zero (meaning if
 * the inputs are -0.0 and +0.0, either may be returned).
 */
public fun fmaxf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.libm.math.generic.fmax(x.toDouble(), y.toDouble())
