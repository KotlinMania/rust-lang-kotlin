// port-lint: source math/nextafter.rs
package io.github.kotlinmania.rustlang.libm.math.nextafter

public fun nextafter(x: Double, y: Double): Double {
    if (x.isNaN() || y.isNaN()) {
        return x + y
    }
    var uxI = x.toRawBits().toULong()
    val uyI = y.toRawBits().toULong()
    if (uxI == uyI) {
        return y
    }
    val ax = uxI and (1uL.inv() / 2uL)
    val ay = uyI and (1uL.inv() / 2uL)
    if (ax == 0uL) {
        if (ay == 0uL) {
            return y
        }
        uxI = (uyI and (1uL shl 63)) or 1uL
    } else if (ax > ay || ((uxI xor uyI) and (1uL shl 63)) != 0uL) {
        uxI -= 1uL
    } else {
        uxI += 1uL
    }
    val e = (uxI shr 52) and 0x7ffuL
    // raise overflow if ux.f is infinite and x is finite
    if (e == 0x7ffuL) {
        readVolatile(x + x)
    }
    val uxF = Double.fromBits(uxI.toLong())
    // raise underflow if ux.f is subnormal or zero
    if (e == 0uL) {
        readVolatile(x * x + uxF * uxF)
    }
    return uxF
}
