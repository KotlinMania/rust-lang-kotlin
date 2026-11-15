// port-lint: source math/expo2.rs
package io.github.kotlinmania.rustlang.libm.math.expo2

import io.github.kotlinmania.rustlang.libm.math.combineWords
import io.github.kotlinmania.rustlang.libm.math.exp.exp

// exp(x)/2 for x >= log(DBL_MAX), slightly better than 0.5*exp(x/2)*exp(x/2)
public fun expo2(x: Double): Double {
    // k is such that k*ln2 has minimal relative error and x - kln2 > log(DBL_MIN)
    val K: Int = 2043
    val kln2 = Double.fromBits(0x40962066151add8buL.toLong())
    // note that k is odd and scale*scale overflows
    val scale = combineWords(((0x3ff + K / 2).toUInt()) shl 20, 0u)
    // exp(x - k ln2) * 2**(k-1)
    return exp(x - kln2) * scale * scale
}
