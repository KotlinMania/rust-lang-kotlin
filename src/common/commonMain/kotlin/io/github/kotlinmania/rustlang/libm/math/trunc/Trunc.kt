// port-lint: source math/trunc.rs
package io.github.kotlinmania.rustlang.libm.math.trunc

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.libm.math.generic.trunc.truncStatus

/**
 * Rounds the number toward 0 to the closest integral value (f16).
 *
 * This effectively removes the decimal part of the number, leaving the integral part.
 */
public fun truncf16(x: F16): F16 = truncStatus(x).value

/**
 * Rounds the number toward 0 to the closest integral value (f32).
 *
 * This effectively removes the decimal part of the number, leaving the integral part.
 */
public fun truncf(x: kotlin.Float): kotlin.Float = truncStatus(x).value

/**
 * Rounds the number toward 0 to the closest integral value (f64).
 *
 * This effectively removes the decimal part of the number, leaving the integral part.
 */
public fun trunc(x: Double): Double = truncStatus(x).value

/**
 * Rounds the number toward 0 to the closest integral value (f128).
 *
 * This effectively removes the decimal part of the number, leaving the integral part.
 */
public fun truncf128(x: F128): F128 = truncStatus(x).value
