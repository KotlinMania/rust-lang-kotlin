// port-lint: source math/modf.rs
package io.github.kotlinmania.rustlang.libm.math.modf

public fun modf(x: Double): Pair<Double, Double> {
    val rv2: Double
    var u = x.toRawBits().toULong()
    val mask: ULong
    val e = (((u shr 52) and 0x7ffuL).toInt()) - 0x3ff
    // no fractional part
    if (e >= 52) {
        rv2 = x
        if (e == 0x400 && (u shl 12) != 0uL) {
            // nan
            return Pair(x, rv2)
        }
        u = u and (1uL shl 63)
        return Pair(Double.fromBits(u.toLong()), rv2)
    }
    // no integral part
    if (e < 0) {
        u = u and (1uL shl 63)
        rv2 = Double.fromBits(u.toLong())
        return Pair(x, rv2)
    }
    mask = ((0uL.inv()) shr 12) shr e
    if ((u and mask) == 0uL) {
        rv2 = x
        u = u and (1uL shl 63)
        return Pair(Double.fromBits(u.toLong()), rv2)
    }
    u = u and mask.inv()
    rv2 = Double.fromBits(u.toLong())
    return Pair(x - rv2, rv2)
}
