// port-lint: source math/lgamma_r.rs
package io.github.kotlinmania.rustlang.libm.math.lgammar

import io.github.kotlinmania.rustlang.libm.math.floor.floor
import io.github.kotlinmania.rustlang.libm.math.kcos.kCos
import io.github.kotlinmania.rustlang.libm.math.ksin.kSin
import io.github.kotlinmania.rustlang.libm.math.log.log
import io.github.kotlinmania.rustlang.libm.math.support.uncheckedDivI32

public val PI: Double = 3.14159265358979311600e00

// 0x400921FB, 0x54442D18
public val A0: Double = 7.72156649015328655494e-02

// 0x3FB3C467, 0xE37DB0C8
public val A1: Double = 3.22467033424113591611e-01

// 0x3FD4A34C, 0xC4A60FAD
public val A2: Double = 6.73523010531292681824e-02

// 0x3FB13E00, 0x1A5562A7
public val A3: Double = 2.05808084325167332806e-02

// 0x3F951322, 0xAC92547B
public val A4: Double = 7.38555086081402883957e-03

// 0x3F7E404F, 0xB68FEFE8
public val A5: Double = 2.89051383673415629091e-03

// 0x3F67ADD8, 0xCCB7926B
public val A6: Double = 1.19270763183362067845e-03

// 0x3F538A94, 0x116F3F5D
public val A7: Double = 5.10069792153511336608e-04

// 0x3F40B6C6, 0x89B99C00
public val A8: Double = 2.20862790713908385557e-04

// 0x3F2CF2EC, 0xED10E54D
public val A9: Double = 1.08011567247583939954e-04

// 0x3F1C5088, 0x987DFB07
public val A10: Double = 2.52144565451257326939e-05

// 0x3EFA7074, 0x428CFA52
public val A11: Double = 4.48640949618915160150e-05

// 0x3F07858E, 0x90A45837
public val TC: Double = 1.46163214496836224576e00

// 0x3FF762D8, 0x6356BE3F
public val TF: Double = -1.21486290535849611461e-01

// 0xBFBF19B9, 0xBCC38A42
// tt = -(tail of TF)
public val TT: Double = -3.63867699703950536541e-18

// 0xBC50C7CA, 0xA48A971F
public val T0: Double = 4.83836122723810047042e-01

// 0x3FDEF72B, 0xC8EE38A2
public val T1: Double = -1.47587722994593911752e-01

// 0xBFC2E427, 0x8DC6C509
public val T2: Double = 6.46249402391333854778e-02

// 0x3FB08B42, 0x94D5419B
public val T3: Double = -3.27885410759859649565e-02

// 0xBFA0C9A8, 0xDF35B713
public val T4: Double = 1.79706750811820387126e-02

// 0x3F9266E7, 0x970AF9EC
public val T5: Double = -1.03142241298341437450e-02

// 0xBF851F9F, 0xBA91EC6A
public val T6: Double = 6.10053870246291332635e-03

// 0x3F78FCE0, 0xE370E344
public val T7: Double = -3.68452016781138256760e-03

// 0xBF6E2EFF, 0xB3E914D7
public val T8: Double = 2.25964780900612472250e-03

// 0x3F6282D3, 0x2E15C915
public val T9: Double = -1.40346469989232843813e-03

// 0xBF56FE8E, 0xBF2D1AF1
public val T10: Double = 8.81081882437654011382e-04

// 0x3F4CDF0C, 0xEF61A8E9
public val T11: Double = -5.38595305356740546715e-04

// 0xBF41A610, 0x9C73E0EC
public val T12: Double = 3.15632070903625950361e-04

// 0x3F34AF6D, 0x6C0EBBF7
public val T13: Double = -3.12754168375120860518e-04

// 0xBF347F24, 0xECC38C38
public val T14: Double = 3.35529192635519073543e-04

// 0x3F35FD3E, 0xE8C2D3F4
public val U0: Double = -7.72156649015328655494e-02

// 0xBFB3C467, 0xE37DB0C8
public val U1: Double = 6.32827064025093366517e-01

// 0x3FE4401E, 0x8B005DFF
public val U2: Double = 1.45492250137234768737e00

// 0x3FF7475C, 0xD119BD6F
public val U3: Double = 9.77717527963372745603e-01

// 0x3FEF4976, 0x44EA8450
public val U4: Double = 2.28963728064692451092e-01

// 0x3FCD4EAE, 0xF6010924
public val U5: Double = 1.33810918536787660377e-02

// 0x3F8B678B, 0xBF2BAB09
public val V1: Double = 2.45597793713041134822e00

// 0x4003A5D7, 0xC2BD619C
public val V2: Double = 2.12848976379893395361e00

// 0x40010725, 0xA42B18F5
public val V3: Double = 7.69285150456672783825e-01

// 0x3FE89DFB, 0xE45050AF
public val V4: Double = 1.04222645593369134254e-01

// 0x3FBAAE55, 0xD6537C88
public val V5: Double = 3.21709242282423911810e-03

// 0x3F6A5ABB, 0x57D0CF61
public val S0: Double = -7.72156649015328655494e-02

// 0xBFB3C467, 0xE37DB0C8
public val S1: Double = 2.14982415960608852501e-01

// 0x3FCB848B, 0x36E20878
public val S2: Double = 3.25778796408930981787e-01

// 0x3FD4D98F, 0x4F139F59
public val S3: Double = 1.46350472652464452805e-01

// 0x3FC2BB9C, 0xBEE5F2F7
public val S4: Double = 2.66422703033638609560e-02

// 0x3F9B481C, 0x7E939961
public val S5: Double = 1.84028451407337715652e-03

// 0x3F5E26B6, 0x7368F239
public val S6: Double = 3.19475326584100867617e-05

// 0x3F00BFEC, 0xDD17E945
public val R1: Double = 1.39200533467621045958e00

// 0x3FF645A7, 0x62C4AB74
public val R2: Double = 7.21935547567138069525e-01

// 0x3FE71A18, 0x93D3DCDC
public val R3: Double = 1.71933865632803078993e-01

// 0x3FC601ED, 0xCCFBDF27
public val R4: Double = 1.86459191715652901344e-02

// 0x3F9317EA, 0x742ED475
public val R5: Double = 7.77942496381893596434e-04

// 0x3F497DDA, 0xCA41A95B
public val R6: Double = 7.32668430744625636189e-06

// 0x3EDEBAF7, 0xA5B38140
public val W0: Double = 4.18938533204672725052e-01

// 0x3FDACFE3, 0x90C97D69
public val W1: Double = 8.33333333333329678849e-02

// 0x3FB55555, 0x5555553B
public val W2: Double = -2.77777777728775536470e-03

// 0xBF66C16C, 0x16B02E5C
public val W3: Double = 7.93650558643019558500e-04

// 0x3F4A019F, 0x98CF38B6
public val W4: Double = -5.95187557450339963135e-04

// 0xBF4380CB, 0x8C0FE741
public val W5: Double = 8.36339918996282139126e-04

// 0x3F4B67BA, 0x4CDAD5D1
public val W6: Double = -1.63092934096575273989e-03

// 0xBF5AB89D, 0x0B9E43E4
// sin(PI*x) assuming x > 2^-100, if sin(PI*x)==0 the sign is arbitrary
public fun sinPi(x: Double): Double {
    var x1 = x
    var n: Int
    // spurious inexact if odd int
    x1 = 2.0 * (x1 * 0.5 - floor(x1 * 0.5))
    // x mod 2.0
    n = (x1 * 4.0).toInt()
    // SAFETY: nonzero divisor, nonnegative dividend (`n<8`).
    n = uncheckedDivI32(n + 1, 2)
    x1 -= (n.toDouble()) * 0.5
    x1 *= PI
    return when (n) {
        1 -> kCos(x1, 0.0)
        2 -> kSin(-x1, 0.0, 0)
        3 -> -kCos(x1, 0.0)
        // 0
        else -> kSin(x1, 0.0, 0)
    }
}

public fun lgammaR(x: Double): Pair<Double, Int> {
    var x1 = x
    val u: ULong = x1.toRawBits().toULong()
    var t: Double
    val y: Double
    var z: Double
    val nadj: Double
    val p: Double
    val p1: Double
    val p2: Double
    val p3: Double
    val q: Double
    var r: Double
    val w: Double
    val ix: UInt
    val sign: Boolean
    val i: Int
    var signgam: Int
    // purge off +-inf, NaN, +-0, tiny and negative arguments
    signgam = 1
    sign = (u shr 63) != 0uL
    ix = ((u shr 32).toUInt()) and 0x7fffffffu
    if (ix >= 0x7ff00000u) {
        return Pair(x1 * x1, signgam)
    }
    if (ix < (0x3ffu - 70u) shl 20) {
        // |x|<2**-70, return -log(|x|)
        if (sign) {
            x1 = -x1
            signgam = -1
        }
        return Pair(-log(x1), signgam)
    }
    if (sign) {
        x1 = -x1
        t = sinPi(x1)
        if (t == 0.0) {
            // -integer
            return Pair(1.0 / (x1 - x1), signgam)
        }
        if (t > 0.0) {
            signgam = -1
        } else {
            t = -t
        }
        nadj = log(PI / (t * x1))
    } else {
        nadj = 0.0
    }
    // purge off 1 and 2
    if ((ix == 0x3ff00000u || ix == 0x40000000u) && (u and 0xffffffffuL) == 0uL) {
        r = 0.0
    } else if (ix < 0x40000000u) {
        if (ix <= 0x3fecccccu) {
            // lgamma(x) = lgamma(x+1)-log(x)
            r = -log(x1)
            if (ix >= 0x3FE76944u) {
                y = 1.0 - x1
                i = 0
            } else if (ix >= 0x3FCDA661u) {
                y = x1 - (TC - 1.0)
                i = 1
            } else {
                y = x1
                i = 2
            }
        } else {
            r = 0.0
            if (ix >= 0x3FFBB4C3u) {
                // [1.7316,2]
                y = 2.0 - x1
                i = 0
            } else if (ix >= 0x3FF3B4C4u) {
                // [1.23,1.73]
                y = x1 - TC
                i = 1
            } else {
                y = x1 - 1.0
                i = 2
            }
        }
        when (i) {
            0 -> {
                z = y * y
                p1 = A0 + z * (A2 + z * (A4 + z * (A6 + z * (A8 + z * A10))))
                p2 = z * (A1 + z * (A3 + z * (A5 + z * (A7 + z * (A9 + z * A11)))))
                p = y * p1 + p2
                r += p - 0.5 * y
            }
            1 -> {
                z = y * y
                w = z * y
                p1 = T0 + w * (T3 + w * (T6 + w * (T9 + w * T12)))
                // parallel comp
                p2 = T1 + w * (T4 + w * (T7 + w * (T10 + w * T13)))
                p3 = T2 + w * (T5 + w * (T8 + w * (T11 + w * T14)))
                p = z * p1 - (TT - w * (p2 + y * p3))
                r += TF + p
            }
            2 -> {
                p1 = y * (U0 + y * (U1 + y * (U2 + y * (U3 + y * (U4 + y * U5)))))
                p2 = 1.0 + y * (V1 + y * (V2 + y * (V3 + y * (V4 + y * V5))))
                r += -0.5 * y + p1 / p2
            }
            else -> error("unreachable")
        }
    } else if (ix < 0x40200000u) {
        // x < 8.0
        i = x1.toInt()
        y = x1 - (i.toDouble())
        p = y * (S0 + y * (S1 + y * (S2 + y * (S3 + y * (S4 + y * (S5 + y * S6))))))
        q = 1.0 + y * (R1 + y * (R2 + y * (R3 + y * (R4 + y * (R5 + y * R6)))))
        r = 0.5 * y + p / q
        z = 1.0
        // lgamma(1+s) = log(s) + lgamma(s)
        // Note: In C, this was implemented using switch jumps with fallthrough.
        // Does this implementation have performance problems?
        if (i >= 7) {
            z *= y + 6.0
        }
        if (i >= 6) {
            z *= y + 5.0
        }
        if (i >= 5) {
            z *= y + 4.0
        }
        if (i >= 4) {
            z *= y + 3.0
        }
        if (i >= 3) {
            z *= y + 2.0
            r += log(z)
        }
    } else if (ix < 0x43900000u) {
        // 8.0 <= x < 2**58
        t = log(x1)
        z = 1.0 / x1
        y = z * z
        w = W0 + z * (W1 + y * (W2 + y * (W3 + y * (W4 + y * (W5 + y * W6)))))
        r = (x1 - 0.5) * (t - 1.0) + w
    } else {
        // 2**58 <= x <= inf
        r = x1 * (log(x1) - 1.0)
    }
    if (sign) {
        r = nadj - r
    }
    return Pair(r, signgam)
}
