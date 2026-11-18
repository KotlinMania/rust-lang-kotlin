// port-lint: source math/fmod.rs
package io.github.kotlinmania.rustlang.libm.math.fmod

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16

/**
 * Calculate the remainder of `x / y`, the precise result of `x - trunc(x / y) * y`.
 */
public fun fmodf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.libm.math.generic.fmod.fmod(x.toDouble(), y.toDouble())

/**
 * Calculate the remainder of `x / y`, the precise result of `x - trunc(x / y) * y`.
 */
public fun fmodf(x: kotlin.Float, y: kotlin.Float): kotlin.Float = io.github.kotlinmania.rustlang.libm.math.generic.fmod.fmod(x.toDouble(), y.toDouble())

/**
 * Calculate the remainder of `x / y`, the precise result of `x - trunc(x / y) * y`.
 */
public fun fmod(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.libm.math.generic.fmod.fmod(x, y)

/**
 * Calculate the remainder of `x / y`, the precise result of `x - trunc(x / y) * y`.
 */
public fun fmodf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.libm.math.generic.fmod.fmod(x.toDouble(), y.toDouble())
