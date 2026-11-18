// port-lint: source math/fminimum_fmaximum_num.rs
package io.github.kotlinmania.rustlang.libm.math.fminimumfmaximumnum

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16

/**
 * Return the lesser of two arguments or, if either argument is NaN, NaN.
 *
 * This coincides with IEEE 754-2019 `minimumNumber`. The result orders -0.0 < 0.0.
 */
public fun fminimumNumf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.libm.math.generic.fminimumnum.fminimumNum(x.toDouble(), y.toDouble())

/**
 * Return the lesser of two arguments or, if either argument is NaN, NaN.
 *
 * This coincides with IEEE 754-2019 `minimumNumber`. The result orders -0.0 < 0.0.
 */
public fun fminimumNumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float = io.github.kotlinmania.rustlang.libm.math.generic.fminimumnum.fminimumNum(x.toDouble(), y.toDouble())

/**
 * Return the lesser of two arguments or, if either argument is NaN, NaN.
 *
 * This coincides with IEEE 754-2019 `minimumNumber`. The result orders -0.0 < 0.0.
 */
public fun fminimumNum(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.libm.math.generic.fminimumnum.fminimumNum(x, y)

/**
 * Return the lesser of two arguments or, if either argument is NaN, NaN.
 *
 * This coincides with IEEE 754-2019 `minimumNumber`. The result orders -0.0 < 0.0.
 */
public fun fminimumNumf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.libm.math.generic.fminimumnum.fminimumNum(x.toDouble(), y.toDouble())

/**
 * Return the greater of two arguments or, if either argument is NaN, NaN.
 *
 * This coincides with IEEE 754-2019 `maximumNumber`. The result orders -0.0 < 0.0.
 */
public fun fmaximumNumf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.libm.math.generic.fmaximumnum.fmaximumNum(x.toDouble(), y.toDouble())

/**
 * Return the greater of two arguments or, if either argument is NaN, NaN.
 *
 * This coincides with IEEE 754-2019 `maximumNumber`. The result orders -0.0 < 0.0.
 */
public fun fmaximumNumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float = io.github.kotlinmania.rustlang.libm.math.generic.fmaximumnum.fmaximumNum(x.toDouble(), y.toDouble())

/**
 * Return the greater of two arguments or, if either argument is NaN, NaN.
 *
 * This coincides with IEEE 754-2019 `maximumNumber`. The result orders -0.0 < 0.0.
 */
public fun fmaximumNum(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.libm.math.generic.fmaximumnum.fmaximumNum(x, y)

/**
 * Return the greater of two arguments or, if either argument is NaN, NaN.
 *
 * This coincides with IEEE 754-2019 `maximumNumber`. The result orders -0.0 < 0.0.
 */
public fun fmaximumNumf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.libm.math.generic.fmaximumnum.fmaximumNum(x.toDouble(), y.toDouble())
