// port-lint: source math/hypot.rs
package io.github.kotlinmania.rustlang.libm.math.hypot

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.num.rustLeadingZeros
import io.github.kotlinmania.rustlang.libm.math.arch.aarch64.sqrt.sqrt
import io.github.kotlinmania.rustlang.libm.math.support.coldPath
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hf64
import kotlin.math.abs

// Euclidian distance via the pythagorean theorem (`√(x2 + y2)`).
//
// Per IEEE 754-2019:
//
// - Domain: `[−∞, +∞] × [−∞, +∞]`
// - `hypot(±0, ±0)` is +0
// - `hypot(±∞, qNaN)` is +∞
// - `hypot(qNaN, ±∞)` is +∞.
// - May raise overflow or underflow

public fun hypot(x: Double, y: Double): Double = crHypot(x, y)

public fun crHypot(x: Double, y: Double): Double {
    var x1 = x
    var y1 = y
    val flag = getFlags()
    val xi = x1.toRawBits().toULong()
    val yi = y1.toRawBits().toULong()
    val emsk: ULong = 0x7ffuL shl 52
    var ex: ULong = xi and emsk
    var ey: ULong = yi and emsk
    // emsk corresponds to the upper bits of NaN and Inf (apart the sign bit)
    x1 = abs(x1)
    y1 = abs(y1)
    if (ex == emsk || ey == emsk) {
        coldPath()
        // Either x or y is NaN or Inf
        val wx: ULong = xi shl 1
        val wy: ULong = yi shl 1
        val wm: ULong = emsk shl 1
        val oneInf = (wx == wm) xor (wy == wm)
        val oneNan = x1.isNaN() xor y1.isNaN()
        // let nqnn: i32 = (((wx >> 52) == 0xfff) ^ ((wy >> 52) == 0xfff)) as i32;
        // / * ninf is 1 when only one of x and y is +/-Inf
        // nqnn is 1 when only one of x and y is qNaN
        // IEEE 754 says that hypot(+/-Inf,qNaN)=hypot(qNaN,+/-Inf)=+Inf. * /
        if (oneInf && oneNan) {
            return Double.POSITIVE_INFINITY
        }
        return x1 + y1
        // inf, sNaN
    }
    val u: Double = run { val lhsMax0 = (x1); val rhsMax0 = (y1); when { lhsMax0.isNaN() -> rhsMax0; rhsMax0.isNaN() -> lhsMax0; else -> maxOf(lhsMax0, rhsMax0) } }
    val v: Double = run { val lhsMin0 = (x1); val rhsMin0 = (y1); when { lhsMin0.isNaN() -> rhsMin0; rhsMin0.isNaN() -> lhsMin0; else -> minOf(lhsMin0, rhsMin0) } }
    var xd: ULong = u.toRawBits().toULong()
    var yd: ULong = v.toRawBits().toULong()
    ey = yd
    if (ey shr 52 == 0uL) {
        coldPath()
        if (yd == 0uL) {
            return Double.fromBits(xd.toLong())
        }
        ex = xd
        if (ex shr 52 == 0uL) {
            coldPath()
            if (ex == 0uL) {
                return 0.0
            }
            return asHypotDenorm(ex, ey)
        }
        val nz: UInt = rustLeadingZeros(ey)
        ey = ey shl ((nz - 11u).toInt())
        ey = ey and (ULong.MAX_VALUE shr 12)
        ey = (ey - (((nz.toLong() - 12L) shl 52).toULong()))
        val t = ey
        // why did they do this?
        yd = t
    }
    val de: ULong = (xd - (yd.toULong()))
    if (de > (27uL shl 52)) {
        coldPath()
        return run {
            val X: Double = hf64("0x1p-27")
            X
        }.fma(v, u)
    }
    val off: Long = (0x3ffL shl 52) - (xd and emsk).toLong()
    xd = (xd + (off.toULong()))
    yd = (yd + (off.toULong()))
    x1 = Double.fromBits(xd.toLong())
    y1 = Double.fromBits(yd.toLong())
    val x2: Double = x1 * x1
    val dx2: Double = x1.fma(x1, -x2)
    val y2: Double = y1 * y1
    val dy2: Double = y1.fma(y1, -y2)
    val r2: Double = x2 + y2
    val ir2: Double = 0.5 / r2
    val dr2: Double = ((x2 - r2) + y2) + (dx2 + dy2)
    var th: Double = sqrt(r2)
    val rsqrt: Double = th * ir2
    val dz: Double = dr2 - th.fma(th, -r2)
    val localStorageL115C4 = AddressableLocal(rsqrt * dz, PrimitiveLayouts.F64)
    val tl by localStorageL115C4
    th = fasttwosum(th, tl, RustMutableReference(localStorageL115C4.asMutPtr(), localStorageL115C4.layout))
    var thd: ULong = th.toRawBits().toULong()
    val tld = abs(tl).toRawBits().toULong()
    ex = thd
    ey = tld
    ex = ex and (0x7ffuL shl 52)
    val aidr: ULong = ((ey + ((0x3feuL shl 52).toULong())) - (ex.toULong()))
    val mid: ULong = (((aidr - 0x3c90000000000000uL) + 16uL)) shr 5
    if (mid == 0uL || !(0x39b0000000000000uL..0x3c9fffffffffff80uL).contains(aidr)) {
        coldPath()
        thd = asHypotHard(x1, y1, flag).toRawBits().toULong()
    }
    thd = (thd - (off.toULong()))
    if (thd >= (0x7ffuL shl 52)) {
        coldPath()
        return asHypotOverflow()
    }
    return Double.fromBits(thd.toLong())
}

public fun fasttwosum(x: Double, y: Double, e: RustMutableReference<Double>): Double {
    val e1 = e
    val s: Double = x + y
    val z: Double = s - x
    (e1).write(y - z)
    return s
}

public fun asHypotOverflow(): Double {
    val z: Double = run {
        val X: Double = hf64("0x1.fffffffffffffp1023")
        X
    }
    val f = z + z
    if (f > z) {
        // errno = ERANGE
    }
    return f
}

/**
 * Here the square root is refined by Newton iterations: x^2+y^2 is exact
 * and fits in a 128-bit integer, so the approximation is squared (which
 * also fits in a 128-bit integer), compared and adjusted if necessary using
 * the exact value of x^2+y^2.
 */
public fun asHypotHard(x: Double, y: Double, flag: FExcept): Double {
    val op: Double =
        1.0 +
            run {
                val X: Double = hf64("0x1p-54")
                X
            }
    val om: Double =
        1.0 -
            run {
                val X: Double = hf64("0x1p-54")
                X
            }
    var xi: ULong = x.toRawBits().toULong()
    val yi: ULong = y.toRawBits().toULong()
    var bm: ULong = (xi and (ULong.MAX_VALUE shr 12)) or 1uL shl 52
    var lm: ULong = (yi and (ULong.MAX_VALUE shr 12)) or 1uL shl 52
    val be: Int = (xi shr 52).toInt()
    val le: Int = (yi shr 52).toInt()
    val ri: ULong = sqrt(x * x + y * y).toRawBits().toULong()
    val bs: Int = 2
    var rm: ULong = ri and (ULong.MAX_VALUE shr 12)
    var re: Int = (ri shr 52).toInt() - 0x3ff
    rm = rm or (1uL shl 52)
    for (unused in 0 until 3) {
        if (rm == 1uL shl 52) {
            rm = ULong.MAX_VALUE shr 11
            re -= 1
        } else {
            coldPath()
            rm -= 1uL
        }
    }
    bm = bm shl bs
    var m2: ULong = (bm * (bm.toULong()))
    val de: Int = be - le
    var ls: Int = bs - de
    if (ls >= 0) {
        lm = lm shl ls
        m2 = (m2 + (lm * (lm.toULong())).toULong())
    } else {
        coldPath()
        val lm2: U128 = (U128.fromULong(lm)) * (U128.fromULong(lm))
        ls *= 2
        m2 = (m2 + ((lm2 shr -ls).toULong()))
        m2 = m2 or ((if ((lm2 shl (128 + ls)) != U128(0x0uL, 0x0uL)) 1uL else 0uL))
    }
    val k: Int = bs + re
    var d: Long
    while (true) {
        rm += 1uL + (if (rm >= (1uL shl 53)) 1uL else 0uL)
        val tm: ULong = rm shl k
        val rm2: ULong = (tm * (tm.toULong()))
        d = m2.toLong() - rm2.toLong()
        if (d <= 0L) {
            break
        }
    }
    if (d == 0L) {
        setFlags(flag)
    } else if (op == om) {
        val tm1: ULong = (rm shl k) - (1uL shl (k - (if (rm <= (1uL shl 53)) 1 else 0)))
        d = m2.toLong() - ((tm1 * (tm1.toULong()))).toLong()
        if (d == 0L) {
            coldPath()
            rm -= rm and 1uL
        } else {
            rm = (rm + ((d shr 63).toULong()))
        }
    } else {
        coldPath()
        rm -= ((if (op == 1.0) 1uL else 0uL)) shl ((if (rm > (1uL shl 53)) 1u else 0u)).toInt()
    }
    if (rm >= (1uL shl 53)) {
        rm = rm shr 1
        re += 1
    }
    val e: ULong = (be - 1 + re).toULong()
    xi = (e shl 52) + rm
    return Double.fromBits(xi.toLong())
}

public fun asHypotDenorm(a: ULong, b: ULong): Double {
    var a1 = a
    var b1 = b
    val op: Double =
        1.0 +
            run {
                val X: Double = hf64("0x1p-54")
                X
            }
    val om: Double =
        1.0 -
            run {
                val X: Double = hf64("0x1p-54")
                X
            }
    val af: Double = (a1.toLong()).toDouble()
    val bf: Double = (b1.toLong()).toDouble()
    a1 = a1 shl 1
    b1 = b1 shl 1
    // Is this casting right?
    var rm: ULong = sqrt(af * af + bf * bf).toULong()
    val tm: ULong = rm shl 1
    var d: Long = ((((a1 * (a1.toULong())).toLong()) - ((tm * (tm.toULong())).toLong())) + ((b1 * (b1.toULong())).toLong()))
    val sd: Long = d shr 63
    val um: Long = ((rm.toLong()) xor sd) - sd
    var drm: Long = sd + 1L
    var dd: Long = (um shl 3) + 4L
    var pD: Long
    rm -= drm.toULong()
    drm += sd
    while (true) {
        pD = d
        rm = (rm + (drm.toULong()))
        d = (d - (dd.toLong()))
        dd = (dd + 8L)
        if ((d xor pD) <= 0L) {
            coldPath()
            break
        }
    }
    pD = (sd and d) + (sd.inv() and pD)
    if (pD != 0L) {
        if (op == om) {
            val sum: Long = ((pD - ((4uL * (rm.toULong())).toLong())) - 1L)
            if (sum != 0L) {
                rm = (rm + (((sum shr 63) + 1L).toULong()))
            } else {
                coldPath()
                rm += rm and 1uL
            }
        } else {
            coldPath()
            rm += (if (op > 1.0) 1uL else 0uL)
        }
    } else {
        coldPath()
    }
    val xi: ULong = rm
    return Double.fromBits(xi.toLong())
}

public typealias FExcept = UInt

public fun setFlags(flag: FExcept) {}

public fun getFlags(): FExcept = 0u
