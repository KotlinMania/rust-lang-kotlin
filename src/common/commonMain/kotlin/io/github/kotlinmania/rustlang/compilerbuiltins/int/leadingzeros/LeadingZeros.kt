// port-lint: source int/leading_zeros.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.leadingzeros

import io.github.kotlinmania.rustlang.compilerbuiltins.int.leadingzeros.implementation.leadingZerosCondset
import io.github.kotlinmania.rustlang.compilerbuiltins.int.leadingzeros.implementation.leadingZerosDefault
import io.github.kotlinmania.rustlang.core.U128

/**
 * Returns the number of leading binary zeros in `x`
 */
public fun clzsi2(x: UInt): Int =
    if (false) {
        leadingZerosCondset(x)
    } else {
        leadingZerosDefault(x)
    }

/**
 * Returns the number of leading binary zeros in `x`
 */
public fun clzdi2(x: ULong): Int =
    if (false) {
        leadingZerosCondset(x)
    } else {
        leadingZerosDefault(x)
    }

/**
 * Returns the number of leading binary zeros in `x`
 */
public fun clzti2(x: U128): Int {
    val hi = (x shr 64).toULong()
    return if (hi == 0uL) {
        64 + clzdi2(x.toULong())
    } else {
        clzdi2(hi)
    }
}
