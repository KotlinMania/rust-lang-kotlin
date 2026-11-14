// port-lint: source num/imp/flt2dec/estimator.rs
package io.github.kotlinmania.rustlang.core.num.imp.flt2dec.estimator

import io.github.kotlinmania.rustlang.core.num.rustLeadingZeros

// The exponent estimator.

/**
 * Finds `k0` such that `10 ^(k0 - 1)<mant * 2 ^ exp <= 10 ^(k0 + 1)`.
 *
 * This is used to approximate `k = ceil(log10(mant * 2 ^ exp))`;
 * the true `k` is either `k0` or `k0 + 1`.
 */
public fun estimateScalingFactor(mant: ULong, exp: Short): Short {
    // 2^(nbits-1) < mant <= 2^nbits if mant > 0
    val nbits = 64L - rustLeadingZeros((mant - 1uL)).toLong()
    // 1292913986 = floor(2^32 * log10 2)
    // therefore this always underestimates (or is exact), but not much.
    return (((nbits + exp.toLong()) * 1292913986L) shr 32).toShort()
}
