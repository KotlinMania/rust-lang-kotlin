// port-lint: source math/j1f.rs
package io.github.kotlinmania.rustlang.libm.math.j1f

import io.github.kotlinmania.rustlang.math.arch.aarch64.sqrt.sqrtf
import io.github.kotlinmania.rustlang.math.cosf.cosf
import io.github.kotlinmania.rustlang.math.fabs.fabsf
import io.github.kotlinmania.rustlang.math.logf.logf
import io.github.kotlinmania.rustlang.math.sinf.sinf

public val INVSQRTPI: kotlin.Float = 5.6418961287e-01f

// 0x3f106ebb
public val TPI: kotlin.Float = 6.3661974669e-01f

// 0x3f22f983
public fun common(ix: UInt, x: kotlin.Float, y1: Boolean, sign: Boolean): kotlin.Float {
    val z: Double
    var s: Double
    val c: Double
    var ss: Double
    var cc: Double
    s = sinf(x).toDouble()
    if (y1) {
        s = -s
    }
    c = cosf(x).toDouble()
    cc = s - c
    if (ix < 0x7f000000u) {
        ss = -s - c
        z = cosf(2.0f * x).toDouble()
        if (s * c > 0.0) {
            cc = z / ss
        } else {
            ss = z / cc
        }
        if (ix < 0x58800000u) {
            if (y1) {
                ss = -ss
            }
            cc = (ponef(x).toDouble()) * cc - (qonef(x).toDouble()) * ss
        }
    }
    if (sign) {
        cc = -cc
    }
    return (((INVSQRTPI.toDouble()) * cc) / (sqrtf(x).toDouble())).toFloat()
}

// R0/S0 on [0,2]
public val R00: kotlin.Float = -6.2500000000e-02f

// 0xbd800000
public val R01: kotlin.Float = 1.4070566976e-03f

// 0x3ab86cfd
public val R02: kotlin.Float = -1.5995563444e-05f

// 0xb7862e36
public val R03: kotlin.Float = 4.9672799207e-08f

// 0x335557d2
public val S01: kotlin.Float = 1.9153760746e-02f

// 0x3c9ce859
public val S02: kotlin.Float = 1.8594678841e-04f

// 0x3942fab6
public val S03: kotlin.Float = 1.1771846857e-06f

// 0x359dffc2
public val S04: kotlin.Float = 5.0463624390e-09f

// 0x31ad6446
public val S05: kotlin.Float = 1.2354227016e-11f

// 0x2d59567e

/**
 * First order of the [Bessel function](https://en.wikipedia.org/wiki/BesselFunction) of the first kind (f32).
 */
public fun j1f(x: kotlin.Float): kotlin.Float {
    var z: kotlin.Float
    val r: kotlin.Float
    val s: kotlin.Float
    var ix: UInt
    val sign: Boolean
    ix = x.toRawBits().toUInt()
    sign = (ix shr 31) != 0u
    ix = ix and 0x7fffffffu
    if (ix >= 0x7f800000u) {
        return 1.0f / (x * x)
    }
    if (ix >= 0x40000000u) {
        // |x| >= 2
        return common(ix, fabsf(x), false, sign)
    }
    if (ix >= 0x39000000u) {
        // |x| >= 2**-13
        z = x * x
        r = z * (R00 + z * (R01 + z * (R02 + z * R03)))
        s = 1.0f + z * (S01 + z * (S02 + z * (S03 + z * (S04 + z * S05))))
        z = 0.5f + r / s
    } else {
        z = 0.5f
    }
    return z * x
}

public val U0: FloatArray = floatArrayOf(-1.9605709612e-01f, 5.0443872809e-02f, -1.9125689287e-03f, 2.3525259166e-05f, -9.1909917899e-08f)

public val V0: FloatArray = floatArrayOf(1.9916731864e-02f, 2.0255257550e-04f, 1.3560879779e-06f, 6.2274145840e-09f, 1.6655924903e-11f)

/**
 * First order of the [Bessel function](https://en.wikipedia.org/wiki/BesselFunction) of the second kind (f32).
 */
public fun y1f(x: kotlin.Float): kotlin.Float {
    val z: kotlin.Float
    val u: kotlin.Float
    val v: kotlin.Float
    val ix: UInt
    ix = x.toRawBits().toUInt()
    if ((ix and 0x7fffffffu) == 0u) {
        return -1.0f / 0.0f
    }
    if ((ix shr 31) != 0u) {
        return 0.0f / 0.0f
    }
    if (ix >= 0x7f800000u) {
        return 1.0f / x
    }
    if (ix >= 0x40000000u) {
        // |x| >= 2.0
        return common(ix, x, true, false)
    }
    if (ix < 0x33000000u) {
        // x < 2**-25
        return -TPI / x
    }
    z = x * x
    u = U0[0] + z * (U0[1] + z * (U0[2] + z * (U0[3] + z * U0[4])))
    v = 1.0f + z * (V0[0] + z * (V0[1] + z * (V0[2] + z * (V0[3] + z * V0[4]))))
    return x * (u / v) + TPI * (j1f(x) * logf(x) - 1.0f / x)
}

// For x >= 8, the asymptotic expansions of pone is
// 1 + 15/128 s^2 - 4725/2^15 s^4 - ...,   where s = 1/x.
// We approximate pone by
// pone(x) = 1 + (R/S)
// where  R = pr0 + pr1*s^2 + pr2*s^4 + ... + pr5*s^10
// S = 1 + ps0*s^2 + ... + ps4*s^10
// and
// | pone(x)-1-R/S | <= 2  ** ( -60.06)
//
public val PR8: FloatArray = floatArrayOf(0.0000000000e00f, 1.1718750000e-01f, 1.3239480972e01f, 4.1205184937e02f, 3.8747453613e03f, 7.9144794922e03f)

public val PS8: FloatArray = floatArrayOf(1.1420736694e02f, 3.6509309082e03f, 3.6956207031e04f, 9.7602796875e04f, 3.0804271484e04f)

public val PR5: FloatArray = floatArrayOf(1.3199052094e-11f, 1.1718749255e-01f, 6.8027510643e00f, 1.0830818176e02f, 5.1763616943e02f, 5.2871520996e02f)

public val PS5: FloatArray = floatArrayOf(5.9280597687e01f, 9.9140142822e02f, 5.3532670898e03f, 7.8446904297e03f, 1.5040468750e03f)

public val PR3: FloatArray = floatArrayOf(3.0250391081e-09f, 1.1718686670e-01f, 3.9329774380e00f, 3.5119403839e01f, 9.1055007935e01f, 4.8559066772e01f)

public val PS3: FloatArray = floatArrayOf(3.4791309357e01f, 3.3676245117e02f, 1.0468714600e03f, 8.9081134033e02f, 1.0378793335e02f)

public val PR2: FloatArray = floatArrayOf(1.0771083225e-07f, 1.1717621982e-01f, 2.3685150146e00f, 1.2242610931e01f, 1.7693971634e01f, 5.0735230446e00f)

public val PS2: FloatArray = floatArrayOf(2.1436485291e01f, 1.2529022980e02f, 2.3227647400e02f, 1.1767937469e02f, 8.3646392822e00f)

public fun ponef(x: kotlin.Float): kotlin.Float {
    val p: FloatArray
    val q: FloatArray
    val z: kotlin.Float
    val r: kotlin.Float
    val s: kotlin.Float
    var ix: UInt
    ix = x.toRawBits().toUInt()
    ix = ix and 0x7fffffffu
    if (ix >= 0x41000000u) {
        p = PR8
        q = PS8
    } else if (ix >= 0x409173ebu) {
        p = PR5
        q = PS5
    } else if (ix >= 0x4036d917u) {
        p = PR3
        q = PS3
    } else {
        p = PR2
        q = PS2
    }
    z = 1.0f / (x * x)
    r = p[0] + z * (p[1] + z * (p[2] + z * (p[3] + z * (p[4] + z * p[5]))))
    s = 1.0f + z * (q[0] + z * (q[1] + z * (q[2] + z * (q[3] + z * q[4]))))
    return 1.0f + r / s
}

// For x >= 8, the asymptotic expansions of qone is
// 3/8 s - 105/1024 s^3 - ..., where s = 1/x.
// We approximate pone by
// qone(x) = s*(0.375 + (R/S))
// where  R = qr1*s^2 + qr2*s^4 + ... + qr5*s^10
// S = 1 + qs1*s^2 + ... + qs6*s^12
// and
// | qone(x)/s -0.375-R/S | <= 2  ** ( -61.13)
//
public val QR8: FloatArray = floatArrayOf(0.0000000000e00f, -1.0253906250e-01f, -1.6271753311e01f, -7.5960174561e02f, -1.1849806641e04f, -4.8438511719e04f)

public val QS8: FloatArray = floatArrayOf(1.6139537048e02f, 7.8253862305e03f, 1.3387534375e05f, 7.1965775000e05f, 6.6660125000e05f, -2.9449025000e05f)

public val QR5: FloatArray = floatArrayOf(-2.0897993405e-11f, -1.0253904760e-01f, -8.0564479828e00f, -1.8366960144e02f, -1.3731937256e03f, -2.6124443359e03f)

public val QS5: FloatArray = floatArrayOf(8.1276550293e01f, 1.9917987061e03f, 1.7468484375e04f, 4.9851425781e04f, 2.7948074219e04f, -4.7191835938e03f)

public val QR3: FloatArray = floatArrayOf(-5.0783124372e-09f, -1.0253783315e-01f, -4.6101160049e00f, -5.7847221375e01f, -2.2824453735e02f, -2.1921012878e02f)

public val QS3: FloatArray = floatArrayOf(4.7665153503e01f, 6.7386511230e02f, 3.3801528320e03f, 5.5477290039e03f, 1.9031191406e03f, -1.3520118713e02f)

public val QR2: FloatArray = floatArrayOf(-1.7838172539e-07f, -1.0251704603e-01f, -2.7522056103e00f, -1.9663616180e01f, -4.2325313568e01f, -2.1371921539e01f)

public val QS2: FloatArray = floatArrayOf(2.9533363342e01f, 2.5298155212e02f, 7.5750280762e02f, 7.3939318848e02f, 1.5594900513e02f, -4.9594988823e00f)

public fun qonef(x: kotlin.Float): kotlin.Float {
    val p: FloatArray
    val q: FloatArray
    val s: kotlin.Float
    val r: kotlin.Float
    val z: kotlin.Float
    var ix: UInt
    ix = x.toRawBits().toUInt()
    ix = ix and 0x7fffffffu
    if (ix >= 0x41000000u) {
        p = QR8
        q = QS8
    } else if (ix >= 0x409173ebu) {
        p = QR5
        q = QS5
    } else if (ix >= 0x4036d917u) {
        p = QR3
        q = QS3
    } else {
        p = QR2
        q = QS2
    }
    z = 1.0f / (x * x)
    r = p[0] + z * (p[1] + z * (p[2] + z * (p[3] + z * (p[4] + z * p[5]))))
    s = 1.0f + z * (q[0] + z * (q[1] + z * (q[2] + z * (q[3] + z * (q[4] + z * q[5])))))
    return (0.375f + r / s) / x
}
