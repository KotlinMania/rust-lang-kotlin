// port-lint: source math/rint.rs
package io.github.kotlinmania.rustlang.libm.math.rint

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.libm.math.generic.rint.rintStatus

/**
 * Round `x` to the nearest integer, breaking ties toward even.
 */
public fun rintf16(x: F16): F16 {
    if (true) {
        return io.github.kotlinmania.rustlang.libm.math.arch.rintf16(x)
    }
    return rintStatus(x).value
}

/**
 * Round `x` to the nearest integer, breaking ties toward even.
 */
public fun rintf(x: kotlin.Float): kotlin.Float {
    if (true) {
        return io.github.kotlinmania.rustlang.libm.math.arch.rintf(x)
    }
    return rintStatus(x).value
}

/**
 * Round `x` to the nearest integer, breaking ties toward even.
 */
public fun rint(x: Double): Double {
    if (true) {
        return io.github.kotlinmania.rustlang.libm.math.arch.rint(x)
    }
    if (true) {
        return io.github.kotlinmania.rustlang.libm.math.arch.rint(x)
    }
    return rintStatus(x).value
}

/**
 * Round `x` to the nearest integer, breaking ties toward even.
 */
public fun rintf128(x: F128): F128 = rintStatus(x).value
