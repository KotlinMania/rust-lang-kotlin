// port-lint: source math/nextafterf.rs
package io.github.kotlinmania.rustlang.libm.math.nextafterf

public fun nextafterf(x: kotlin.Float, y: kotlin.Float): kotlin.Float {
    if (x.isNaN() || y.isNaN()) {
        return x + y
    }
    var uxI = x.toRawBits().toUInt()
    val uyI = y.toRawBits().toUInt()
    if (uxI == uyI) {
        return y
    }
    val ax = uxI and 0x7fffffffu
    val ay = uyI and 0x7fffffffu
    if (ax == 0u) {
        if (ay == 0u) {
            return y
        }
        uxI = (uyI and 0x80000000u) or 1u
    } else if (ax > ay || ((uxI xor uyI) and 0x80000000u) != 0u) {
        uxI -= 1u
    } else {
        uxI += 1u
    }
    val e = uxI and 0x7f800000u
    // raise overflow if uxF is infinite and x is finite
    if (e == 0x7f800000u) {
        readVolatile(x + x)
    }
    val uxF = kotlin.Float.fromBits(uxI.toInt())
    // raise underflow if uxF is subnormal or zero
    if (e == 0u) {
        readVolatile(x * x + uxF * uxF)
    }
    return uxF
}
