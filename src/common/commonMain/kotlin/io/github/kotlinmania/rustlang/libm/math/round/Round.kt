// port-lint: source math/round.rs
package io.github.kotlinmania.rustlang.libm.math.round

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16

/**
 * Round `x` to the nearest integer, breaking ties away from zero.
 */
public fun roundf16(x: F16): F16 = io.github.kotlinmania.rustlang.libm.math.generic.round.round(x.toDouble())

/**
 * Round `x` to the nearest integer, breaking ties away from zero.
 */
public fun roundf(x: kotlin.Float): kotlin.Float = io.github.kotlinmania.rustlang.libm.math.generic.round.round(x.toDouble())

/**
 * Round `x` to the nearest integer, breaking ties away from zero.
 */
public fun round(x: Double): Double = io.github.kotlinmania.rustlang.libm.math.generic.round.round(x)

/**
 * Round `x` to the nearest integer, breaking ties away from zero.
 */
public fun roundf128(x: F128): F128 = io.github.kotlinmania.rustlang.libm.math.generic.round.round(x.toDouble())
