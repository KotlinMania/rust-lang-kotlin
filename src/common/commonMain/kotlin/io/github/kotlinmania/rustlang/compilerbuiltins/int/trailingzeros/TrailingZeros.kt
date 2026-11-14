// port-lint: source int/trailing_zeros.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.trailingzeros

import io.github.kotlinmania.rustlang.compilerbuiltins.int.trailingzeros.implementation.trailingZeros
import io.github.kotlinmania.rustlang.core.U128

/**
 * Returns the number of trailing binary zeros in `x` (32 bit version).
 */
public fun ctzsi2(x: UInt): ULong = trailingZeros(x).toULong()

/**
 * Returns the number of trailing binary zeros in `x` (64 bit version).
 */
public fun ctzdi2(x: ULong): ULong = trailingZeros(x).toULong()

/**
 * Returns the number of trailing binary zeros in `x` (128 bit version).
 */
public fun ctzti2(x: U128): Int {
    val lo = x.toULong()
    return if (lo == 0uL) {
        64 + ctzdi2((x shr 64).toULong())
    } else {
        ctzdi2(lo)
    }
}
