// port-lint: source math/remquo.rs
package io.github.kotlinmania.rustlang.libm.math.remquo

public fun remquo(x: Double, y: Double): Pair<Double, Int> {
    var x1 = x
    var y1 = y
    val ux: ULong = x1.toRawBits().toULong()
    var uy: ULong = y1.toRawBits().toULong()
    var ex = ((ux shr 52) and 0x7ffuL).toInt()
    var ey = ((uy shr 52) and 0x7ffuL).toInt()
    val sx = (ux shr 63) != 0uL
    val sy = (uy shr 63) != 0uL
    var q: UInt
    var i: ULong
    var uxi: ULong = ux
    if ((uy shl 1) == 0uL || y1.isNaN() || ex == 0x7ff) {
        return Pair((x1 * y1) / (x1 * y1), 0)
    }
    if ((ux shl 1) == 0uL) {
        return Pair(x1, 0)
    }
    // normalize x and y
    if (ex == 0) {
        i = uxi shl 12
        while ((i shr 63) == 0uL) {
            ex -= 1
            i = i shl 1
        }
        uxi = uxi shl (-ex + 1)
    } else {
        uxi = uxi and ((0uL.inv()) shr 12)
        uxi = uxi or (1uL shl 52)
    }
    if (ey == 0) {
        i = uy shl 12
        while ((i shr 63) == 0uL) {
            ey -= 1
            i = i shl 1
        }
        uy = uy shl (-ey + 1)
    } else {
        uy = uy and ((0uL.inv()) shr 12)
        uy = uy or (1uL shl 52)
    }
    q = 0u
    if (ex + 1 != ey) {
        if (ex < ey) {
            return Pair(x1, 0)
        }
        // x mod y
        while (ex > ey) {
            i = (uxi - (uy.toULong()))
            if ((i shr 63) == 0uL) {
                uxi = i
                q += 1u
            }
            uxi = uxi shl 1
            q = q shl 1
            ex -= 1
        }
        i = (uxi - (uy.toULong()))
        if ((i shr 63) == 0uL) {
            uxi = i
            q += 1u
        }
        if (uxi == 0uL) {
            ex = -60
        } else {
            while ((uxi shr 52) == 0uL) {
                uxi = uxi shl 1
                ex -= 1
            }
        }
    }
    // scale result and decide between |x| and |x|-|y|
    if (ex > 0) {
        uxi -= 1uL shl 52
        uxi = uxi or ((ex.toULong()) shl 52)
    } else {
        uxi = uxi shr (-ex + 1)
    }
    x1 = Double.fromBits(uxi.toLong())
    if (sy) {
        y1 = -y1
    }
    if (ex == ey || (ex + 1 == ey && (2.0 * x1 > y1 || (2.0 * x1 == y1 && (q % 2u) != 0u)))) {
        x1 -= y1
        // Note: this matches musl behavior, but it is incorrect
        q = (q + 1u)
    }
    q = q and 0x7fffffffu
    val quo =
        if (sx xor sy) {
            -(q.toInt())
        } else {
            q.toInt()
        }
    return if (sx) {
        Pair(-x1, quo)
    } else {
        Pair(x1, quo)
    }
}
