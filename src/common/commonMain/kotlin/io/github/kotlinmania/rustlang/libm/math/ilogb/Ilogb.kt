// port-lint: source math/ilogb.rs
package io.github.kotlinmania.rustlang.libm.math.ilogb

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16

/**
 * Extract the binary exponent of `x`.
 */
public fun ilogbf16(x: F16): Int = io.github.kotlinmania.rustlang.libm.math.generic.ilogb(x)

/**
 * Extract the binary exponent of `x`.
 */
public fun ilogbf(x: kotlin.Float): Int = io.github.kotlinmania.rustlang.libm.math.generic.ilogb(x.toDouble())

/**
 * Extract the binary exponent of `x`.
 */
public fun ilogb(x: Double): Int = io.github.kotlinmania.rustlang.libm.math.generic.ilogb(x)

/**
 * Extract the binary exponent of `x`.
 */
public fun ilogbf128(x: F128): Int = io.github.kotlinmania.rustlang.libm.math.generic.ilogb(x)
