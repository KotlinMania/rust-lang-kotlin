// port-lint: source math/pow.rs
package io.github.kotlinmania.rustlang.libm.math.pow

import io.github.kotlinmania.rustlang.libm.math.fabs.fabs
import io.github.kotlinmania.rustlang.libm.math.getHighWord
import io.github.kotlinmania.rustlang.libm.math.scalbn.scalbn
import io.github.kotlinmania.rustlang.libm.math.sqrt.sqrt
import io.github.kotlinmania.rustlang.libm.math.withSetHighWord
import io.github.kotlinmania.rustlang.libm.math.withSetLowWord

public val BP: DoubleArray = doubleArrayOf(1.0, 1.5)

public val DP_H: DoubleArray = doubleArrayOf(0.0, 5.84962487220764160156e-01)

// 0x3fe2b80340000000
public val DP_L: DoubleArray = doubleArrayOf(0.0, 1.35003920212974897128e-08)

// 0x3E4CFDEB, 0x43CFD006
public val TWO53: Double = 9007199254740992.0

// 0x4340000000000000
public val HUGE: Double = 1.0e300

public val TINY: Double = 1.0e-300

// poly coefs for (3/2)*(log(x)-2s-2/3*s**3:
public val L1: Double = 5.99999999999994648725e-01

// 0x3fe3333333333303
public val L2: Double = 4.28571428578550184252e-01

// 0x3fdb6db6Db6fabff
public val L3: Double = 3.33333329818377432918e-01

// 0x3fd55555518f264d
public val L4: Double = 2.72728123808534006489e-01

// 0x3fd17460A91d4101
public val L5: Double = 2.30660745775561754067e-01

// 0x3fcd864a93c9db65
public val L6: Double = 2.06975017800338417784e-01

// 0x3fca7e284a454eef
public val P1: Double = 1.66666666666666019037e-01

// 0x3fc555555555553e
public val P2: Double = -2.77777777770155933842e-03

// 0xbf66c16c16bebd93
public val P3: Double = 6.61375632143793436117e-05

// 0x3f11566aAf25de2c
public val P4: Double = -1.65339022054652515390e-06

// 0xbebbbd41C5d26bf1
public val P5: Double = 4.13813679705723846039e-08

// 0x3e66376972bea4d0
public val LG2: Double = 6.93147180559945286227e-01

// 0x3fe62e42Fefa39ef
public val LG2_H: Double = 6.93147182464599609375e-01

// 0x3fe62e4300000000
public val LG2_L: Double = -1.90465429995776804525e-09

// 0xbe205c610ca86c39
public val OVT: Double = 8.0085662595372944372e-017

// -(1024-log2(ovfl+.5ulp))
public val CP: Double = 9.61796693925975554329e-01

// 0x3feec709Dc3a03fd =2/(3ln2)
public val CP_H: Double = 9.61796700954437255859e-01

// 0x3feec709E0000000 =(float)cp
public val CP_L: Double = -7.02846165095275826516e-09

// 0xbe3e2fe0145b01f5 =tail of cpH
public val IVLN2: Double = 1.44269504088896338700e00

// 0x3ff71547652b82fe =1/ln2
public val IVLN2_H: Double = 1.44269502162933349609e00

// 0x3ff7154760000000 =24b 1/ln2
public val IVLN2_L: Double = 1.92596299112661746887e-08

// 0x3e54ae0bF85ddf44 =1/ln2 tail

/**
 * Returns `x` to the power of `y` (f64).
 */
public fun pow(x: Double, y: Double): Double {
    val t1: Double
    val t2: Double
    val (hx: Int, lx: UInt) = Pair((x.toRawBits().toULong() shr 32).toInt(), x.toRawBits().toULong().toUInt())
    val (hy: Int, ly: UInt) = Pair((y.toRawBits().toULong() shr 32).toInt(), y.toRawBits().toULong().toUInt())
    var ix: Int = hx and 0x7fffffff
    val iy: Int = hy and 0x7fffffff
    // x**0 = 1, even if x is NaN
    if (((iy.toUInt()) or ly) == 0u) {
        return 1.0
    }
    // 1**y = 1, even if y is NaN
    if (hx == 0x3ff00000 && lx == 0u) {
        return 1.0
    }
    // NaN if either arg is NaN
    if (ix > 0x7ff00000 || (ix == 0x7ff00000 && lx != 0u) || iy > 0x7ff00000 || (iy == 0x7ff00000 && ly != 0u)) {
        return x + y
    }
    // determine if y is an odd int when x < 0
    // yisint = 0       ... y is not an integer
    // yisint = 1       ... y is an odd int
    // yisint = 2       ... y is an even int
    //
    var yisint: Int = 0
    var k: Int
    var j: Int
    if (hx < 0) {
        if (iy >= 0x43400000) {
            yisint = 2
            // even integer y
        } else if (iy >= 0x3ff00000) {
            k = (iy shr 20) - 0x3ff
            // exponent
            if (k > 20) {
                j = (ly shr (52 - k)).toInt()
                if ((j shl (52 - k)) == (ly.toInt())) {
                    yisint = 2 - (j and 1)
                }
            } else if (ly == 0u) {
                j = iy shr (20 - k)
                if ((j shl (20 - k)) == iy) {
                    yisint = 2 - (j and 1)
                }
            }
        }
    }
    if (ly == 0u) {
        // special value of y
        if (iy == 0x7ff00000) {
            // y is +-inf
            return if (((ix - 0x3ff00000) or (lx.toInt())) == 0) {
                // (-1)**+-inf is 1
                1.0
            } else if (ix >= 0x3ff00000) {
                // (|x|>1)**+-inf = inf,0
                if (hy >= 0) {
                    y
                } else {
                    0.0
                }
            } else {
                // (|x|<1)**+-inf = 0,inf
                if (hy >= 0) {
                    0.0
                } else {
                    -y
                }
            }
        }
        if (iy == 0x3ff00000) {
            // y is +-1
            return if (hy >= 0) {
                x
            } else {
                1.0 / x
            }
        }
        if (hy == 0x40000000) {
            // y is 2
            return x * x
        }
        if (hy == 0x3fe00000) {
            // y is 0.5
            if (hx >= 0) {
                // x >= +0
                return sqrt(x)
            }
        }
    }
    var ax: Double = fabs(x)
    if (lx == 0u) {
        // special value of x
        if (ix == 0x7ff00000 || ix == 0 || ix == 0x3ff00000) {
            // x is +-0,+-inf,+-1
            var z: Double = ax
            if (hy < 0) {
                // z = (1/|x|)
                z = 1.0 / z
            }
            if (hx < 0) {
                if (((ix - 0x3ff00000) or yisint) == 0) {
                    z = (z - z) / (z - z)
                    // (-1)**non-int is NaN
                } else if (yisint == 1) {
                    z = -z
                    // (x<0)**odd = -(|x|**odd)
                }
            }
            return z
        }
    }
    var s: Double = 1.0
    // sign of result
    if (hx < 0) {
        if (yisint == 0) {
            // (x<0)**(non-int) is NaN
            return (x - x) / (x - x)
        }
        if (yisint == 1) {
            // (x<0)**(odd int)
            s = -1.0
        }
    }
    // |y| is HUGE
    if (iy > 0x41e00000) {
        // if |y| > 2**31
        if (iy > 0x43f00000) {
            // if |y| > 2**64, must o/uflow
            if (ix <= 0x3fefffff) {
                return if (hy < 0) {
                    HUGE * HUGE
                } else {
                    TINY * TINY
                }
            }
            if (ix >= 0x3ff00000) {
                return if (hy > 0) {
                    HUGE * HUGE
                } else {
                    TINY * TINY
                }
            }
        }
        // over/underflow if x is not close to one
        if (ix < 0x3fefffff) {
            return if (hy < 0) {
                s * HUGE * HUGE
            } else {
                s * TINY * TINY
            }
        }
        if (ix > 0x3ff00000) {
            return if (hy > 0) {
                s * HUGE * HUGE
            } else {
                s * TINY * TINY
            }
        }
        // now |1-x| is TINY <= 2**-20, suffice to compute
        // log(x) by x-x^2/2+x^3/3-x^4/4
        val t: Double = ax - 1.0
        // t has 20 trailing zeros
        val w: Double = (t * t) * (0.5 - t * (0.3333333333333333333333 - t * 0.25))
        val u: Double = IVLN2_H * t
        // ivln2H has 21 sig. bits
        val v: Double = t * IVLN2_L - w * IVLN2
        t1 = withSetLowWord(u + v, 0u)
        t2 = v - (t1 - u)
    } else {
        // double ss,s2,sH,sL,tH,tL;
        var n: Int = 0
        if (ix < 0x00100000) {
            // take care subnormal number
            ax *= TWO53
            n -= 53
            ix = getHighWord(ax).toInt()
        }
        n += (ix shr 20) - 0x3ff
        j = ix and 0x000fffff
        // determine interval
        val k1: Int
        ix = j or 0x3ff00000
        // normalize ix
        if (j <= 0x3988E) {
            // |x|<sqrt(3/2)
            k1 = 0
        } else if (j < 0xBB67A) {
            // |x|<sqrt(3)
            k1 = 1
        } else {
            k1 = 0
            n += 1
            ix -= 0x00100000
        }
        ax = withSetHighWord(ax, ix.toUInt())
        // compute ss = sH+sL = (x-1)/(x+1) or (x-1.5)/(x+1.5)
        val u1: Double = ax - BP.getUnchecked(k1)
        // bp[0]=1.0, bp[1]=1.5
        val v1: Double = 1.0 / (ax + BP.getUnchecked(k1))
        val ss: Double = u1 * v1
        val sH = withSetLowWord(ss, 0u)
        // tH=ax+bp[k] High
        val tH: Double = withSetHighWord(0.0, ((ix.toUInt() shr 1) or 0x20000000u) + 0x00080000u + ((k1.toUInt()) shl 18))
        val tL: Double = ax - (tH - BP.getUnchecked(k1))
        val sL: Double = v1 * ((u1 - sH * tH) - sH * tL)
        // compute log(ax)
        val s2: Double = ss * ss
        var r: Double = s2 * s2 * (L1 + s2 * (L2 + s2 * (L3 + s2 * (L4 + s2 * (L5 + s2 * L6)))))
        r += sL * (sH + ss)
        val s21: Double = sH * sH
        val tH1: Double = withSetLowWord(3.0 + s21 + r, 0u)
        val tL1: Double = r - ((tH1 - 3.0) - s21)
        // u+v = ss*(1+...)
        val u2: Double = sH * tH1
        val v2: Double = sL * tH1 + tL1 * ss
        // 2/(3log2)*(ss+...)
        val pH: Double = withSetLowWord(u2 + v2, 0u)
        val pL = v2 - (pH - u2)
        val zH: Double = CP_H * pH
        // cpH+cpL = 2/(3*log2)
        val zL: Double = CP_L * pH + pL * CP + DP_L.getUnchecked(k1)
        // log2(ax) = (ss+..)*2/(3*log2) = n + dpH + zH + zL
        val t1: Double = n.toDouble()
        t1 = withSetLowWord(((zH + zL) + DP_H.getUnchecked(k1)) + t1, 0u)
        t2 = zL - (((t1 - t1) - DP_H.getUnchecked(k1)) - zH)
    }
    // split up y into y1+y2 and compute (y1+y2)*(t1+t2)
    val y1: Double = withSetLowWord(y, 0u)
    val pL1: Double = (y - y1) * t1 + y * t2
    var pH1: Double = y1 * t1
    val z1: Double = pL1 + pH1
    var j1: Int = (z1.toRawBits().toULong() shr 32).toInt()
    val i: Int = z1.toRawBits().toULong().toInt()
    // let (j, i): (i32, i32) = ((z.toBits() >> 32) as i32, z.toBits() as i32);
    if (j1 >= 0x40900000) {
        // z >= 1024
        if ((j1 - 0x40900000) or i != 0) {
            // if z > 1024
            return s * HUGE * HUGE
            // overflow
        }
        if (pL1 + OVT > z1 - pH1) {
            return s * HUGE * HUGE
            // overflow
        }
    } else if ((j1 and 0x7fffffff) >= 0x4090cc00) {
        // z <= -1075
        // Note: instead of abs(j) use unsigned j
        if ((((j1.toUInt()) - 0xc090cc00u) or (i.toUInt())) != 0u) {
            // z < -1075
            return s * TINY * TINY
            // underflow
        }
        if (pL1 <= z1 - pH1) {
            return s * TINY * TINY
            // underflow
        }
    }
    // compute 2**(pH+pL)
    val i1: Int = j1 and 0x7fffffff
    k = (i1 shr 20) - 0x3ff
    var n1: Int = 0
    if (i1 > 0x3fe00000) {
        // if |z| > 0.5, set n = [z+0.5]
        n1 = j1 + (0x00100000 shr (k + 1))
        k = ((n1 and 0x7fffffff) shr 20) - 0x3ff
        // new k for n
        val t2: Double = withSetHighWord(0.0, (n1 and (0x000fffff shr k).inv()).toUInt())
        n1 = ((n1 and 0x000fffff) or 0x00100000) shr (20 - k)
        if (j1 < 0) {
            n1 = -n1
        }
        pH1 -= t2
    }
    val t3: Double = withSetLowWord(pL1 + pH1, 0u)
    val u3: Double = t3 * LG2_H
    val v3: Double = (pL1 - (t3 - pH1)) * LG2 + t3 * LG2_L
    var z2: Double = u3 + v3
    val w1: Double = v3 - (z2 - u3)
    val t4: Double = z2 * z2
    val t11: Double = z2 - t4 * (P1 + t4 * (P2 + t4 * (P3 + t4 * (P4 + t4 * P5))))
    val r1: Double = (z2 * t11) / (t11 - 2.0) - (w1 + z2 * w1)
    z2 = 1.0 - (r1 - z2)
    j1 = getHighWord(z2).toInt()
    j1 += n1 shl 20
    if ((j1 shr 20) <= 0) {
        // subnormal output
        z2 = scalbn(z2, n1)
    } else {
        z2 = withSetHighWord(z2, j1.toUInt())
    }
    return s * z2
}
