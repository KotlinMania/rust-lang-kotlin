// port-lint: source math/powf.rs
package io.github.kotlinmania.rustlang.libm.math.powf

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Greater
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Less
import io.github.kotlinmania.rustlang.math.arch.aarch64.sqrt.sqrtf
import io.github.kotlinmania.rustlang.math.fabs.fabsf
import io.github.kotlinmania.rustlang.math.scalbn.scalbnf

public val BP: FloatArray = floatArrayOf(1.0f, 1.5f)

public val DP_H: FloatArray = floatArrayOf(0.0f, 5.84960938e-01f)

// 0x3f15c000
public val DP_L: FloatArray = floatArrayOf(0.0f, 1.56322085e-06f)

// 0x35d1cfdc
public val TWO24: kotlin.Float = 16777216.0f

// 0x4b800000
public val HUGE: kotlin.Float = 1.0e30f

public val TINY: kotlin.Float = 1.0e-30f

public val L1: kotlin.Float = 6.0000002384e-01f

// 0x3f19999a
public val L2: kotlin.Float = 4.2857143283e-01f

// 0x3edb6db7
public val L3: kotlin.Float = 3.3333334327e-01f

// 0x3eaaaaab
public val L4: kotlin.Float = 2.7272811532e-01f

// 0x3e8ba305
public val L5: kotlin.Float = 2.3066075146e-01f

// 0x3e6c3255
public val L6: kotlin.Float = 2.0697501302e-01f

// 0x3e53f142
public val P1: kotlin.Float = 1.6666667163e-01f

// 0x3e2aaaab
public val P2: kotlin.Float = -2.7777778450e-03f

// 0xbb360b61
public val P3: kotlin.Float = 6.6137559770e-05f

// 0x388ab355
public val P4: kotlin.Float = -1.6533901999e-06f

// 0xb5ddea0e
public val P5: kotlin.Float = 4.1381369442e-08f

// 0x3331bb4c
public val LG2: kotlin.Float = 6.9314718246e-01f

// 0x3f317218
public val LG2_H: kotlin.Float = 6.93145752e-01f

// 0x3f317200
public val LG2_L: kotlin.Float = 1.42860654e-06f

// 0x35bfbe8c
public val OVT: kotlin.Float = 4.2995665694e-08f

// -(128-log2(ovfl+.5ulp))
public val CP: kotlin.Float = 9.6179670095e-01f

// 0x3f76384f =2/(3ln2)
public val CP_H: kotlin.Float = 9.6191406250e-01f

// 0x3f764000 =12b cp
public val CP_L: kotlin.Float = -1.1736857402e-04f

// 0xb8f623c6 =tail of cpH
public val IVLN2: kotlin.Float = 1.4426950216e00f

public val IVLN2_H: kotlin.Float = 1.4426879883e00f

public val IVLN2_L: kotlin.Float = 7.0526075433e-06f

/**
 * Returns `x` to the power of `y` (f32).
 */
public fun powf(x: kotlin.Float, y: kotlin.Float): kotlin.Float {
    var z: kotlin.Float
    var ax: kotlin.Float
    val zH: kotlin.Float
    val zL: kotlin.Float
    var pH: kotlin.Float
    var pL: kotlin.Float
    val y1: kotlin.Float
    var t1: kotlin.Float
    val t2: kotlin.Float
    var r: kotlin.Float
    val s: kotlin.Float
    var sn: kotlin.Float
    var t: kotlin.Float
    var u: kotlin.Float
    var v: kotlin.Float
    var w: kotlin.Float
    val i: Int
    var j: Int
    var k: Int
    var yisint: Int
    var n: Int
    val hx: Int
    val hy: Int
    var ix: Int
    val iy: Int
    var isMatch: Int
    hx = x.toRawBits().toUInt().toInt()
    hy = y.toRawBits().toUInt().toInt()
    ix = hx and 0x7fffffff
    iy = hy and 0x7fffffff
    // x**0 = 1, even if x is NaN
    if (iy == 0) {
        return 1.0f
    }
    // 1**y = 1, even if y is NaN
    if (hx == 0x3f800000) {
        return 1.0f
    }
    // NaN if either arg is NaN
    if (ix > 0x7f800000 || iy > 0x7f800000) {
        return x + y
    }
    // determine if y is an odd int when x < 0
    // yisint = 0       ... y is not an integer
    // yisint = 1       ... y is an odd int
    // yisint = 2       ... y is an even int
    //
    yisint = 0
    if (hx < 0) {
        if (iy >= 0x4b800000) {
            yisint = 2
            // even integer y
        } else if (iy >= 0x3f800000) {
            k = (iy shr 23) - 0x7f
            // exponent
            j = iy shr (23 - k)
            if ((j shl (23 - k)) == iy) {
                yisint = 2 - (j and 1)
            }
        }
    }
    // special value of y
    if (iy == 0x7f800000) {
        // y is +-inf
        when (ix.cmp(0x3f800000)) {
            // (-1)**+-inf is 1
            Ordering.Equal -> { return 1.0f }
            // (|x|>1)**+-inf = inf,0
            Ordering.Greater -> {
                return if (hy >= 0) {
                y
            } else {
                0.0f
            }
            }
            // (|x|<1)**+-inf = 0,inf
            Ordering.Less -> {
                return if (hy >= 0) {
                0.0f
            } else {
                -y
            }
            }
        }
    }
    if (iy == 0x3f800000) {
        // y is +-1
        return if (hy >= 0) {
            x
        } else {
            1.0f / x
        }
    }
    if (hy == 0x40000000) {
        // y is 2
        return x * x
    }
    if (hy == 0x3f000000 && hx >= 0) {
        // x >= +0
        return sqrtf(x)
    }
    ax = fabsf(x)
    // special value of x
    if (ix == 0x7f800000 || ix == 0 || ix == 0x3f800000) {
        // x is +-0,+-inf,+-1
        z = ax
        if (hy < 0) {
            // z = (1/|x|)
            z = 1.0f / z
        }
        if (hx < 0) {
            if (((ix - 0x3f800000) or yisint) == 0) {
                z = (z - z) / (z - z)
                // (-1)**non-int is NaN
            } else if (yisint == 1) {
                z = -z
                // (x<0)**odd = -(|x|**odd)
            }
        }
        return z
    }
    sn = 1.0f
    // sign of result
    if (hx < 0) {
        if (yisint == 0) {
            // (x<0)**(non-int) is NaN
            return (x - x) / (x - x)
        }
        if (yisint == 1) {
            // (x<0)**(odd int)
            sn = -1.0f
        }
    }
    // |y| is HUGE
    if (iy > 0x4d000000) {
        // if |y| > 2**27
        // over/underflow if x is not close to one
        if (ix < 0x3f7ffff8) {
            return if (hy < 0) {
                sn * HUGE * HUGE
            } else {
                sn * TINY * TINY
            }
        }
        if (ix > 0x3f800007) {
            return if (hy > 0) {
                sn * HUGE * HUGE
            } else {
                sn * TINY * TINY
            }
        }
        // now |1-x| is TINY <= 2**-20, suffice to compute
        // log(x) by x-x^2/2+x^3/3-x^4/4
        t = ax - 1.0f
        // t has 20 trailing zeros
        w = (t * t) * (0.5f - t * (0.333333333333f - t * 0.25f))
        u = IVLN2_H * t
        // IVLN2_H has 16 sig. bits
        v = t * IVLN2_L - w * IVLN2
        t1 = u + v
        isMatch = t1.toRawBits().toUInt().toInt()
        t1 = kotlin.Float.fromBits((isMatch.toUInt() and 0xfffff000u).toInt())
        t2 = v - (t1 - u)
    } else {
        var s2: kotlin.Float
        var sH: kotlin.Float
        val sL: kotlin.Float
        var tH: kotlin.Float
        var tL: kotlin.Float
        n = 0
        // take care subnormal number
        if (ix < 0x00800000) {
            ax *= TWO24
            n -= 24
            ix = ax.toRawBits().toUInt().toInt()
        }
        n += ((ix) shr 23) - 0x7f
        j = ix and 0x007fffff
        // determine interval
        ix = j or 0x3f800000
        // normalize ix
        if (j <= 0x1cc471) {
            // |x|<sqrt(3/2)
            k = 0
        } else if (j < 0x5db3d7) {
            // |x|<sqrt(3)
            k = 1
        } else {
            k = 0
            n += 1
            ix -= 0x00800000
        }
        ax = kotlin.Float.fromBits((ix.toUInt()).toInt())
        // compute s = sH+sL = (x-1)/(x+1) or (x-1.5)/(x+1.5)
        u = ax - BP.getUnchecked(k)
        // bp[0]=1.0, bp[1]=1.5
        v = 1.0f / (ax + BP.getUnchecked(k))
        s = u * v
        sH = s
        isMatch = sH.toRawBits().toUInt().toInt()
        sH = kotlin.Float.fromBits((isMatch.toUInt() and 0xfffff000u).toInt())
        // tH=ax+bp[k] High
        isMatch = (((ix.toUInt() shr 1) and 0xfffff000u) or 0x20000000u).toInt()
        tH = kotlin.Float.fromBits((isMatch.toUInt() + 0x00400000u + ((k.toUInt()) shl 21)).toInt())
        tL = ax - (tH - BP.getUnchecked(k))
        sL = v * ((u - sH * tH) - sH * tL)
        // compute log(ax)
        s2 = s * s
        r = s2 * s2 * (L1 + s2 * (L2 + s2 * (L3 + s2 * (L4 + s2 * (L5 + s2 * L6)))))
        r += sL * (sH + s)
        s2 = sH * sH
        tH = 3.0f + s2 + r
        isMatch = tH.toRawBits().toUInt().toInt()
        tH = kotlin.Float.fromBits((isMatch.toUInt() and 0xfffff000u).toInt())
        tL = r - ((tH - 3.0f) - s2)
        // u+v = s*(1+...)
        u = sH * tH
        v = sL * tH + tL * s
        // 2/(3log2)*(s+...)
        pH = u + v
        isMatch = pH.toRawBits().toUInt().toInt()
        pH = kotlin.Float.fromBits((isMatch.toUInt() and 0xfffff000u).toInt())
        pL = v - (pH - u)
        zH = CP_H * pH
        // cpH+cpL = 2/(3*log2)
        zL = CP_L * pH + pL * CP + DP_L.getUnchecked(k)
        // log2(ax) = (s+..)*2/(3*log2) = n + dpH + zH + zL
        t = n.toFloat()
        t1 = ((zH + zL) + DP_H.getUnchecked(k)) + t
        isMatch = t1.toRawBits().toUInt().toInt()
        t1 = kotlin.Float.fromBits((isMatch.toUInt() and 0xfffff000u).toInt())
        t2 = zL - (((t1 - t) - DP_H.getUnchecked(k)) - zH)
    }
    // split up y into y1+y2 and compute (y1+y2)*(t1+t2)
    isMatch = y.toRawBits().toUInt().toInt()
    y1 = kotlin.Float.fromBits((isMatch.toUInt() and 0xfffff000u).toInt())
    pL = (y - y1) * t1 + y * t2
    pH = y1 * t1
    z = pL + pH
    j = z.toRawBits().toUInt().toInt()
    if (j > 0x43000000) {
        // if z > 128
        return sn * HUGE * HUGE
        // overflow
    } else if (j == 0x43000000) {
        // if z == 128
        if (pL + OVT > z - pH) {
            return sn * HUGE * HUGE
            // overflow
        }
    } else if ((j and 0x7fffffff) > 0x43160000) {
        // z < -150
        // Note: check should be  (uint32T)j > 0xc3160000
        return sn * TINY * TINY
        // underflow
    } else if (j.toUInt() == 0xc3160000u && pL <= z - pH) {
        return sn * TINY * TINY
        // underflow
    }
    //
    // compute 2**(pH+pL)
    //
    i = j and 0x7fffffff
    k = (i shr 23) - 0x7f
    n = 0
    if (i > 0x3f000000) {
        // if |z| > 0.5, set n = [z+0.5]
        n = j + (0x00800000 shr (k + 1))
        k = ((n and 0x7fffffff) shr 23) - 0x7f
        // new k for n
        t = kotlin.Float.fromBits((n.toUInt() and (0x007fffffu shr k).inv()).toInt())
        n = ((n and 0x007fffff) or 0x00800000) shr (23 - k)
        if (j < 0) {
            n = -n
        }
        pH -= t
    }
    t = pL + pH
    isMatch = t.toRawBits().toUInt().toInt()
    t = kotlin.Float.fromBits((isMatch.toUInt() and 0xffff8000u).toInt())
    u = t * LG2_H
    v = (pL - (t - pH)) * LG2 + t * LG2_L
    z = u + v
    w = v - (z - u)
    t = z * z
    t1 = z - t * (P1 + t * (P2 + t * (P3 + t * (P4 + t * P5))))
    r = (z * t1) / (t1 - 2.0f) - (w + z * w)
    z = 1.0f - (r - z)
    j = z.toRawBits().toUInt().toInt()
    j += n shl 23
    if ((j shr 23) <= 0) {
        // subnormal output
        z = scalbnf(z, n)
    } else {
        z = kotlin.Float.fromBits((j.toUInt()).toInt())
    }
    return sn * z
}
