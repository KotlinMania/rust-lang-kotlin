// port-lint: source math/ceil.rs
package io.github.kotlinmania.rustlang.libm.math.ceil

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.libm.math.generic.ceil.ceilStatus

/**
 * Ceil (f16)
 *
 * Finds the nearest integer greater than or equal to `x`.
 */
public fun ceilf16(x: F16): F16 = ceilStatus(x).value

/**
 * Ceil (f32)
 *
 * Finds the nearest integer greater than or equal to `x`.
 */
public fun ceilf(x: kotlin.Float): kotlin.Float = ceilStatus(x).value

/**
 * Ceil (f64)
 *
 * Finds the nearest integer greater than or equal to `x`.
 */
public fun ceil(x: Double): Double {
    if (true) {
        return io.github.kotlinmania.rustlang.libm.math.arch.i586.rounding.ceil(x)
    }
    return ceilStatus(x).value
}

/**
 * Ceil (f128)
 *
 * Finds the nearest integer greater than or equal to `x`.
 */
public fun ceilf128(x: F128): F128 = ceilStatus(x).value
