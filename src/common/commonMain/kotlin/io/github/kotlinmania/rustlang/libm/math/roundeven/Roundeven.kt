// port-lint: source math/roundeven.rs
package io.github.kotlinmania.rustlang.libm.math.roundeven

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.math.arch.aarch64.rounding.rint
import io.github.kotlinmania.rustlang.math.arch.aarch64.rounding.rintf
import io.github.kotlinmania.rustlang.math.arch.aarch64.rounding.rintf16
import io.github.kotlinmania.rustlang.math.rint.rintf128

// Note that we can use `rint` for these implementations since Rust expects the rounding
// mode is always ties-to-even. `roundeven` also does not raise `FE_INEXACT`.
//
// Tested in the `rint` module.

/**
 * Round `x` to the nearest integer, breaking ties toward even. This is IEEE 754
 * `roundToIntegralTiesToEven`.
 */
public fun roundevenf16(x: F16): F16 = rintf16(x)

/**
 * Round `x` to the nearest integer, breaking ties toward even. This is IEEE 754
 * `roundToIntegralTiesToEven`.
 */
public fun roundevenf(x: kotlin.Float): kotlin.Float = rintf(x)

/**
 * Round `x` to the nearest integer, breaking ties toward even. This is IEEE 754
 * `roundToIntegralTiesToEven`.
 */
public fun roundeven(x: Double): Double = rint(x)

/**
 * Round `x` to the nearest integer, breaking ties toward even. This is IEEE 754
 * `roundToIntegralTiesToEven`.
 */
public fun roundevenf128(x: F128): F128 = rintf128(x)
