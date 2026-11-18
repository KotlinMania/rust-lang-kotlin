// port-lint: source math/floor.rs
package io.github.kotlinmania.rustlang.libm.math.floor

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.libm.math.generic.floor.floorStatus

/**
 * Floor (f16)
 *
 * Finds the nearest integer less than or equal to `x`.
 */
public fun floorf16(x: F16): F16 = floorStatus(x).value

/**
 * Floor (f64)
 *
 * Finds the nearest integer less than or equal to `x`.
 */
public fun floor(x: Double): Double {
    if (true) {
        return io.github.kotlinmania.rustlang.libm.math.arch.i586.rounding.floor(x)
    }
    return floorStatus(x).value
}

/**
 * Floor (f32)
 *
 * Finds the nearest integer less than or equal to `x`.
 */
public fun floorf(x: kotlin.Float): kotlin.Float = floorStatus(x).value

/**
 * Floor (f128)
 *
 * Finds the nearest integer less than or equal to `x`.
 */
public fun floorf128(x: F128): F128 = floorStatus(x).value
