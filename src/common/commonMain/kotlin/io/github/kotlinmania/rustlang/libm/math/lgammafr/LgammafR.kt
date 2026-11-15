// port-lint: source math/lgammaf_r.rs
package io.github.kotlinmania.rustlang.libm.math.lgammafr

import io.github.kotlinmania.rustlang.math.floor.floorf
import io.github.kotlinmania.rustlang.math.kcosf.kCosf
import io.github.kotlinmania.rustlang.math.ksinf.kSinf
import io.github.kotlinmania.rustlang.math.logf.logf
import io.github.kotlinmania.rustlang.math.support.uncheckedDivIsize

public val PI: kotlin.Float = 3.1415927410e00f

// 0x40490fdb
public val A0: kotlin.Float = 7.7215664089e-02f

// 0x3d9e233f
public val A1: kotlin.Float = 3.2246702909e-01f

// 0x3ea51a66
public val A2: kotlin.Float = 6.7352302372e-02f

// 0x3d89f001
public val A3: kotlin.Float = 2.0580807701e-02f

// 0x3ca89915
public val A4: kotlin.Float = 7.3855509982e-03f

// 0x3bf2027e
public val A5: kotlin.Float = 2.8905137442e-03f

// 0x3b3d6ec6
public val A6: kotlin.Float = 1.1927076848e-03f

// 0x3a9c54a1
public val A7: kotlin.Float = 5.1006977446e-04f

// 0x3a05b634
public val A8: kotlin.Float = 2.2086278477e-04f

// 0x39679767
public val A9: kotlin.Float = 1.0801156895e-04f

// 0x38e28445
public val A10: kotlin.Float = 2.5214456400e-05f

// 0x37d383a2
public val A11: kotlin.Float = 4.4864096708e-05f

// 0x383c2c75
public val TC: kotlin.Float = 1.4616321325e00f

// 0x3fbb16c3
public val TF: kotlin.Float = -1.2148628384e-01f

// 0xbdf8cdcd
// TT = -(tail of TF)
public val TT: kotlin.Float = 6.6971006518e-09f

// 0x31e61c52
public val T0: kotlin.Float = 4.8383611441e-01f

// 0x3ef7b95e
public val T1: kotlin.Float = -1.4758771658e-01f

// 0xbe17213c
public val T2: kotlin.Float = 6.4624942839e-02f

// 0x3d845a15
public val T3: kotlin.Float = -3.2788541168e-02f

// 0xbd064d47
public val T4: kotlin.Float = 1.7970675603e-02f

// 0x3c93373d
public val T5: kotlin.Float = -1.0314224288e-02f

// 0xbc28fcfe
public val T6: kotlin.Float = 6.1005386524e-03f

// 0x3bc7e707
public val T7: kotlin.Float = -3.6845202558e-03f

// 0xbb7177fe
public val T8: kotlin.Float = 2.2596477065e-03f

// 0x3b141699
public val T9: kotlin.Float = -1.4034647029e-03f

// 0xbab7f476
public val T10: kotlin.Float = 8.8108185446e-04f

// 0x3a66f867
public val T11: kotlin.Float = -5.3859531181e-04f

// 0xba0d3085
public val T12: kotlin.Float = 3.1563205994e-04f

// 0x39a57b6b
public val T13: kotlin.Float = -3.1275415677e-04f

// 0xb9a3f927
public val T14: kotlin.Float = 3.3552918467e-04f

// 0x39afe9f7
public val U0: kotlin.Float = -7.7215664089e-02f

// 0xbd9e233f
public val U1: kotlin.Float = 6.3282704353e-01f

// 0x3f2200f4
public val U2: kotlin.Float = 1.4549225569e00f

// 0x3fba3ae7
public val U3: kotlin.Float = 9.7771751881e-01f

// 0x3f7a4bb2
public val U4: kotlin.Float = 2.2896373272e-01f

// 0x3e6a7578
public val U5: kotlin.Float = 1.3381091878e-02f

// 0x3c5b3c5e
public val V1: kotlin.Float = 2.4559779167e00f

// 0x401d2ebe
public val V2: kotlin.Float = 2.1284897327e00f

// 0x4008392d
public val V3: kotlin.Float = 7.6928514242e-01f

// 0x3f44efdf
public val V4: kotlin.Float = 1.0422264785e-01f

// 0x3dd572af
public val V5: kotlin.Float = 3.2170924824e-03f

// 0x3b52d5db
public val S0: kotlin.Float = -7.7215664089e-02f

// 0xbd9e233f
public val S1: kotlin.Float = 2.1498242021e-01f

// 0x3e5c245a
public val S2: kotlin.Float = 3.2577878237e-01f

// 0x3ea6cc7a
public val S3: kotlin.Float = 1.4635047317e-01f

// 0x3e15dce6
public val S4: kotlin.Float = 2.6642270386e-02f

// 0x3cda40e4
public val S5: kotlin.Float = 1.8402845599e-03f

// 0x3af135b4
public val S6: kotlin.Float = 3.1947532989e-05f

// 0x3805ff67
public val R1: kotlin.Float = 1.3920053244e00f

// 0x3fb22d3b
public val R2: kotlin.Float = 7.2193557024e-01f

// 0x3f38d0c5
public val R3: kotlin.Float = 1.7193385959e-01f

// 0x3e300f6e
public val R4: kotlin.Float = 1.8645919859e-02f

// 0x3c98bf54
public val R5: kotlin.Float = 7.7794247773e-04f

// 0x3a4beed6
public val R6: kotlin.Float = 7.3266842264e-06f

// 0x36f5d7bd
public val W0: kotlin.Float = 4.1893854737e-01f

// 0x3ed67f1d
public val W1: kotlin.Float = 8.3333335817e-02f

// 0x3daaaaab
public val W2: kotlin.Float = -2.7777778450e-03f

// 0xbb360b61
public val W3: kotlin.Float = 7.9365057172e-04f

// 0x3a500cfd
public val W4: kotlin.Float = -5.9518753551e-04f

// 0xba1c065c
public val W5: kotlin.Float = 8.3633989561e-04f

// 0x3a5b3dd2
public val W6: kotlin.Float = -1.6309292987e-03f

// 0xbad5c4e8
// sin(PI*x) assuming x > 2^-100, if sin(PI*x)==0 the sign is arbitrary
public fun sinPi(x: kotlin.Float): kotlin.Float {
    var x1 = x
    var y: Double
    var n: Int
    // spurious inexact if odd int
    x1 = 2.0f * (x1 * 0.5f - floorf(x1 * 0.5f))
    // x mod 2.0
    n = (x1 * 4.0f).toInt()
    // SAFETY: nonzero divisor, nonnegative dividend (`n<8`).
    n = uncheckedDivIsize(n + 1, 2)
    y = (x1.toDouble()) - (n.toDouble()) * 0.5
    y *= 3.14159265358979323846
    return when (n) {
        1 -> kCosf(y)
        2 -> kSinf(-y)
        3 -> -kCosf(y)
        // 0
        else -> kSinf(y)
    }
}

public fun lgammafR(x: kotlin.Float): Pair<kotlin.Float, Int> {
    var x1 = x
    val u = x1.toRawBits().toUInt()
    var t: kotlin.Float
    val y: kotlin.Float
    var z: kotlin.Float
    val nadj: kotlin.Float
    val p: kotlin.Float
    val p1: kotlin.Float
    val p2: kotlin.Float
    val p3: kotlin.Float
    val q: kotlin.Float
    var r: kotlin.Float
    val w: kotlin.Float
    val ix: UInt
    val i: Int
    val sign: Boolean
    var signgam: Int
    // purge off +-inf, NaN, +-0, tiny and negative arguments
    signgam = 1
    sign = (u shr 31) != 0u
    ix = u and 0x7fffffffu
    if (ix >= 0x7f800000u) {
        return Pair(x1 * x1, signgam)
    }
    if (ix < 0x35000000u) {
        // |x| < 2**-21, return -log(|x|)
        if (sign) {
            signgam = -1
            x1 = -x1
        }
        return Pair(-logf(x1), signgam)
    }
    if (sign) {
        x1 = -x1
        t = sinPi(x1)
        if (t == 0.0f) {
            // -integer
            return Pair(1.0f / (x1 - x1), signgam)
        }
        if (t > 0.0f) {
            signgam = -1
        } else {
            t = -t
        }
        nadj = logf(PI / (t * x1))
    } else {
        nadj = 0.0f
    }
    // purge off 1 and 2
    if (ix == 0x3f800000u || ix == 0x40000000u) {
        r = 0.0f
    } else if (ix < 0x40000000u) {
        if (ix <= 0x3f666666u) {
            // lgamma(x) = lgamma(x+1)-log(x)
            r = -logf(x1)
            if (ix >= 0x3f3b4a20u) {
                y = 1.0f - x1
                i = 0
            } else if (ix >= 0x3e6d3308u) {
                y = x1 - (TC - 1.0f)
                i = 1
            } else {
                y = x1
                i = 2
            }
        } else {
            r = 0.0f
            if (ix >= 0x3fdda618u) {
                // [1.7316,2]
                y = 2.0f - x1
                i = 0
            } else if (ix >= 0x3f9da620u) {
                // [1.23,1.73]
                y = x1 - TC
                i = 1
            } else {
                y = x1 - 1.0f
                i = 2
            }
        }
        when (i) {
            0 -> {
                z = y * y
                p1 = A0 + z * (A2 + z * (A4 + z * (A6 + z * (A8 + z * A10))))
                p2 = z * (A1 + z * (A3 + z * (A5 + z * (A7 + z * (A9 + z * A11)))))
                p = y * p1 + p2
                r += p - 0.5f * y
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
                p2 = 1.0f + y * (V1 + y * (V2 + y * (V3 + y * (V4 + y * V5))))
                r += -0.5f * y + p1 / p2
            }
            else -> error("unreachable")
        }
    } else if (ix < 0x41000000u) {
        // x < 8.0
        i = x1.toInt()
        y = x1 - (i.toFloat())
        p = y * (S0 + y * (S1 + y * (S2 + y * (S3 + y * (S4 + y * (S5 + y * S6))))))
        q = 1.0f + y * (R1 + y * (R2 + y * (R3 + y * (R4 + y * (R5 + y * R6)))))
        r = 0.5f * y + p / q
        z = 1.0f
        // lgamma(1+s) = log(s) + lgamma(s)
        // Note: In C, this was implemented using switch jumps with fallthrough.
        // Does this implementation have performance problems?
        if (i >= 7) {
            z *= y + 6.0f
        }
        if (i >= 6) {
            z *= y + 5.0f
        }
        if (i >= 5) {
            z *= y + 4.0f
        }
        if (i >= 4) {
            z *= y + 3.0f
        }
        if (i >= 3) {
            z *= y + 2.0f
            r += logf(z)
        }
    } else if (ix < 0x5c800000u) {
        // 8.0 <= x < 2**58
        t = logf(x1)
        z = 1.0f / x1
        y = z * z
        w = W0 + z * (W1 + y * (W2 + y * (W3 + y * (W4 + y * (W5 + y * W6)))))
        r = (x1 - 0.5f) * (t - 1.0f) + w
    } else {
        // 2**58 <= x <= inf
        r = x1 * (logf(x1) - 1.0f)
    }
    if (sign) {
        r = nadj - r
    }
    return Pair(r, signgam)
}
