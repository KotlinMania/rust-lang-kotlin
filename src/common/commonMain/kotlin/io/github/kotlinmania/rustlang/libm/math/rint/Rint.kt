// port-lint: source math/rint.rs
package io.github.kotlinmania.rustlang.libm.math.rint

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.libm.math.Generic

/**
 * Round `x` to the nearest integer, breaking ties toward even.
 */
public fun rintf16(x: F16): F16 {
    if (true) {
        return io.github.kotlinmania.rustlang.math.rint.rintf16(x)
    }
    return Generic.rintStatus(x).value
}

/**
 * Round `x` to the nearest integer, breaking ties toward even.
 */
public fun rintf(x: kotlin.Float): kotlin.Float {
    if (true) {
        return io.github.kotlinmania.rustlang.math.rint.rintf(x)
    }
    return Generic.rintStatus(x).value
}

/**
 * Round `x` to the nearest integer, breaking ties toward even.
 */
public fun rint(x: Double): Double {
    if (true) {
        return io.github.kotlinmania.rustlang.math.rint.rint(x)
    }
    if (true) {
        return io.github.kotlinmania.rustlang.math.rint.rint(x)
    }
    return Generic.rintStatus(x).value
}

/**
 * Round `x` to the nearest integer, breaking ties toward even.
 */
public fun rintf128(x: F128): F128 = Generic.rintStatus(x).value
