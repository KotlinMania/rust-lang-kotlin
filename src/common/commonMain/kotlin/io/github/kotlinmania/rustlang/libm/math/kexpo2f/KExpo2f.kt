// port-lint: source math/k_expo2f.rs
package io.github.kotlinmania.rustlang.libm.math.kexpo2f

import io.github.kotlinmania.rustlang.libm.math.expf.expf

// k is such that k*ln2 has minimal relative error and x - kln2 > log(FLT_MIN)
public val K: Int = 235

// expf(x)/2 for x >= log(FLT_MAX), slightly better than 0.5f*expf(x/2)*expf(x/2)
public fun kExpo2f(x: kotlin.Float): kotlin.Float {
    val kLn2 = kotlin.Float.fromBits(0x4322e3bcu.toInt())
    // note that k is odd and scale*scale overflows
    val scale = kotlin.Float.fromBits((((0x7f + K / 2).toUInt()) shl 23).toInt())
    // exp(x - k ln2) * 2**(k-1)
    return expf(x - kLn2) * scale * scale
}
