// port-lint: source math/remquof.rs
package io.github.kotlinmania.rustlang.libm.math.remquof

public fun remquof(x: kotlin.Float, y: kotlin.Float): Pair<kotlin.Float, Int> {
    var x1 = x
    var y1 = y
    val ux: UInt = x1.toRawBits().toUInt()
    var uy: UInt = y1.toRawBits().toUInt()
    var ex = ((ux shr 23) and 0xffu).toInt()
    var ey = ((uy shr 23) and 0xffu).toInt()
    val sx = (ux shr 31) != 0u
    val sy = (uy shr 31) != 0u
    var q: UInt
    var i: UInt
    var uxi: UInt = ux
    if ((uy shl 1) == 0u || y1.isNaN() || ex == 0xff) {
        return Pair((x1 * y1) / (x1 * y1), 0)
    }
    if ((ux shl 1) == 0u) {
        return Pair(x1, 0)
    }
    // normalize x and y
    if (ex == 0) {
        i = uxi shl 9
        while ((i shr 31) == 0u) {
            ex -= 1
            i = i shl 1
        }
        uxi = uxi shl (-ex + 1)
    } else {
        uxi = uxi and ((0u.inv()) shr 9)
        uxi = uxi or (1u shl 23)
    }
    if (ey == 0) {
        i = uy shl 9
        while ((i shr 31) == 0u) {
            ey -= 1
            i = i shl 1
        }
        uy = uy shl (-ey + 1)
    } else {
        uy = uy and ((0u.inv()) shr 9)
        uy = uy or (1u shl 23)
    }
    q = 0u
    if (ex + 1 != ey) {
        if (ex < ey) {
            return Pair(x1, 0)
        }
        // x mod y
        while (ex > ey) {
            i = (uxi - (uy.toUInt()))
            if ((i shr 31) == 0u) {
                uxi = i
                q += 1u
            }
            uxi = uxi shl 1
            q = q shl 1
            ex -= 1
        }
        i = (uxi - (uy.toUInt()))
        if ((i shr 31) == 0u) {
            uxi = i
            q += 1u
        }
        if (uxi == 0u) {
            ex = -30
        } else {
            while ((uxi shr 23) == 0u) {
                uxi = uxi shl 1
                ex -= 1
            }
        }
    }
    // scale result and decide between |x| and |x|-|y|
    if (ex > 0) {
        uxi -= 1u shl 23
        uxi = uxi or ((ex.toUInt()) shl 23)
    } else {
        uxi = uxi shr (-ex + 1)
    }
    x1 = kotlin.Float.fromBits(uxi.toInt())
    if (sy) {
        y1 = -y1
    }
    if (ex == ey || (ex + 1 == ey && (2.0f * x1 > y1 || (2.0f * x1 == y1 && (q % 2u) != 0u)))) {
        x1 -= y1
        q += 1u
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
