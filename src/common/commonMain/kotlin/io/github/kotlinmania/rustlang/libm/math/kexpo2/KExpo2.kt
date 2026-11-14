// port-lint: source math/k_expo2.rs
package io.github.kotlinmania.rustlang.libm.math.kexpo2

import io.github.kotlinmania.rustlang.libm.math.exp.exp

// k is such that k*ln2 has minimal relative error and x - kln2 > log(FLT_MIN)
public val K: Int = 2043

// expf(x)/2 for x >= log(FLT_MAX), slightly better than 0.5f*expf(x/2)*expf(x/2)
internal fun kExpo2(x: Double): Double {
    val kLn2 = Double.fromBits(0x40962066151add8buL.toLong())
    // note that k is odd and scale*scale overflows
    val scale = Double.fromBits((((((0x3ff + K / 2).toUInt()) shl 20).toULong()) shl 32).toLong())
    // exp(x - k ln2) * 2**(k-1)
    return exp(x - kLn2) * scale * scale
}
