// port-lint: source math/modff.rs
package io.github.kotlinmania.rustlang.libm.math.modff

public fun modff(x: kotlin.Float): Pair<kotlin.Float, kotlin.Float> {
    val rv2: kotlin.Float
    var u: UInt = x.toRawBits().toUInt()
    val mask: UInt
    val e = (((u shr 23) and 0xffu).toInt()) - 0x7f
    // no fractional part
    if (e >= 23) {
        rv2 = x
        if (e == 0x80 && (u shl 9) != 0u) {
            // nan
            return Pair(x, rv2)
        }
        u = u and 0x80000000u
        return Pair(kotlin.Float.fromBits(u.toInt()), rv2)
    }
    // no integral part
    if (e < 0) {
        u = u and 0x80000000u
        rv2 = kotlin.Float.fromBits(u.toInt())
        return Pair(x, rv2)
    }
    mask = 0x007fffffu shr e
    if ((u and mask) == 0u) {
        rv2 = x
        u = u and 0x80000000u
        return Pair(kotlin.Float.fromBits(u.toInt()), rv2)
    }
    u = u and mask.inv()
    rv2 = kotlin.Float.fromBits(u.toInt())
    return Pair(x - rv2, rv2)
}
