// port-lint: source math/fminimum_fmaximum.rs
package io.github.kotlinmania.rustlang.libm.math.fminimumfmaximum

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16

/**
 * Return the lesser of two arguments or, if either argument is NaN, the other argument.
 *
 * This coincides with IEEE 754-2019 `minimum`. The result orders -0.0 < 0.0.
 */
public fun fminimumf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.libm.math.generic.fminimum(x.toDouble(), y.toDouble())

/**
 * Return the lesser of two arguments or, if either argument is NaN, the other argument.
 *
 * This coincides with IEEE 754-2019 `minimum`. The result orders -0.0 < 0.0.
 */
public fun fminimum(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.libm.math.generic.fminimum(x, y)

/**
 * Return the lesser of two arguments or, if either argument is NaN, the other argument.
 *
 * This coincides with IEEE 754-2019 `minimum`. The result orders -0.0 < 0.0.
 */
public fun fminimumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float = io.github.kotlinmania.rustlang.libm.math.generic.fminimum(x.toDouble(), y.toDouble())

/**
 * Return the lesser of two arguments or, if either argument is NaN, the other argument.
 *
 * This coincides with IEEE 754-2019 `minimum`. The result orders -0.0 < 0.0.
 */
public fun fminimumf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.libm.math.generic.fminimum(x.toDouble(), y.toDouble())

/**
 * Return the greater of two arguments or, if either argument is NaN, the other argument.
 *
 * This coincides with IEEE 754-2019 `maximum`. The result orders -0.0 < 0.0.
 */
public fun fmaximumf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.libm.math.generic.fmaximum(x.toDouble(), y.toDouble())

/**
 * Return the greater of two arguments or, if either argument is NaN, the other argument.
 *
 * This coincides with IEEE 754-2019 `maximum`. The result orders -0.0 < 0.0.
 */
public fun fmaximumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float = io.github.kotlinmania.rustlang.libm.math.generic.fmaximum(x.toDouble(), y.toDouble())

/**
 * Return the greater of two arguments or, if either argument is NaN, the other argument.
 *
 * This coincides with IEEE 754-2019 `maximum`. The result orders -0.0 < 0.0.
 */
public fun fmaximum(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.libm.math.generic.fmaximum(x, y)

/**
 * Return the greater of two arguments or, if either argument is NaN, the other argument.
 *
 * This coincides with IEEE 754-2019 `maximum`. The result orders -0.0 < 0.0.
 */
public fun fmaximumf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.libm.math.generic.fmaximum(x.toDouble(), y.toDouble())
