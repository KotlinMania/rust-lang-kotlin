// port-lint: source math/frexp.rs
package io.github.kotlinmania.rustlang.libm.math.frexp

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16

// Note so we can have `frexpf16` in the `Float` trait.

/**
 * Decompose a float into a normalized value within the range `[0.5, 1)`, and a power of 2.
 *
 * That is, `x * 2 ^ p` will represent the input value.
 */
public fun frexpf16(x: F16): Pair<F16, Int> = io.github.kotlinmania.rustlang.math.frexp.frexp(x.toDouble())

/**
 * Decompose a float into a normalized value within the range `[0.5, 1)`, and a power of 2.
 *
 * That is, `x * 2 ^ p` will represent the input value.
 */
public fun frexpf(x: kotlin.Float): Pair<kotlin.Float, Int> = io.github.kotlinmania.rustlang.math.frexp.frexp(x.toDouble())

/**
 * Decompose a float into a normalized value within the range `[0.5, 1)`, and a power of 2.
 *
 * That is, `x * 2 ^ p` will represent the input value.
 */
public fun frexp(x: Double): Pair<Double, Int> = io.github.kotlinmania.rustlang.math.frexp.frexp(x)

/**
 * Decompose a float into a normalized value within the range `[0.5, 1)`, and a power of 2.
 *
 * That is, `x * 2 ^ p` will represent the input value.
 */
public fun frexpf128(x: F128): Pair<F128, Int> = io.github.kotlinmania.rustlang.math.frexp.frexp(x.toDouble())
