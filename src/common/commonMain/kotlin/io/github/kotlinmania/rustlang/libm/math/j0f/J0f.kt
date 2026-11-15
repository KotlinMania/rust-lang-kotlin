// port-lint: source math/j0f.rs
package io.github.kotlinmania.rustlang.libm.math.j0f

import io.github.kotlinmania.rustlang.math.arch.aarch64.sqrt.sqrtf
import io.github.kotlinmania.rustlang.math.cosf.cosf
import io.github.kotlinmania.rustlang.math.fabs.fabsf
import io.github.kotlinmania.rustlang.math.logf.logf
import io.github.kotlinmania.rustlang.math.sinf.sinf

public val INVSQRTPI: kotlin.Float = 5.6418961287e-01f

// 0x3f106ebb
public val TPI: kotlin.Float = 6.3661974669e-01f

// 0x3f22f983
public fun common(ix: UInt, x: kotlin.Float, y0: Boolean): kotlin.Float {
    val z: kotlin.Float
    val s: kotlin.Float
    var c: kotlin.Float
    var ss: kotlin.Float
    var cc: kotlin.Float
    //
    // j0(x) = 1/sqrt(pi) * (P(0,x)*cc - Q(0,x)*ss) / sqrt(x)
    // y0(x) = 1/sqrt(pi) * (P(0,x)*ss + Q(0,x)*cc) / sqrt(x)
    //
    s = sinf(x)
    c = cosf(x)
    if (y0) {
        c = -c
    }
    cc = s + c
    if (ix < 0x7f000000u) {
        ss = s - c
        z = -cosf(2.0f * x)
        if (s * c < 0.0f) {
            cc = z / ss
        } else {
            ss = z / cc
        }
        if (ix < 0x58800000u) {
            if (y0) {
                ss = -ss
            }
            cc = pzerof(x) * cc - qzerof(x) * ss
        }
    }
    return INVSQRTPI * cc / sqrtf(x)
}

// R0/S0 on [0, 2.00]
public val R02: kotlin.Float = 1.5625000000e-02f

// 0x3c800000
public val R03: kotlin.Float = -1.8997929874e-04f

// 0xb947352e
public val R04: kotlin.Float = 1.8295404516e-06f

// 0x35f58e88
public val R05: kotlin.Float = -4.6183270541e-09f

// 0xb19eaf3c
public val S01: kotlin.Float = 1.5619102865e-02f

// 0x3c7fe744
public val S02: kotlin.Float = 1.1692678527e-04f

// 0x38f53697
public val S03: kotlin.Float = 5.1354652442e-07f

// 0x3509daa6
public val S04: kotlin.Float = 1.1661400734e-09f

// 0x30a045e8

/**
 * Zeroth order of the [Bessel function](https://en.wikipedia.org/wiki/BesselFunction) of the first kind (f32).
 */
public fun j0f(x: kotlin.Float): kotlin.Float {
    var x1 = x
    val z: kotlin.Float
    val r: kotlin.Float
    val s: kotlin.Float
    var ix: UInt
    ix = x1.toRawBits().toUInt()
    ix = ix and 0x7fffffffu
    if (ix >= 0x7f800000u) {
        return 1.0f / (x1 * x1)
    }
    x1 = fabsf(x1)
    if (ix >= 0x40000000u) {
        // |x| >= 2
        // large ulp error near zeros
        return common(ix, x1, false)
    }
    if (ix >= 0x3a000000u) {
        // |x| >= 2**-11
        // up to 4ulp error near 2
        z = x1 * x1
        r = z * (R02 + z * (R03 + z * (R04 + z * R05)))
        s = 1.0f + z * (S01 + z * (S02 + z * (S03 + z * S04)))
        return (1.0f + x1 / 2.0f) * (1.0f - x1 / 2.0f) + z * (r / s)
    }
    if (ix >= 0x21800000u) {
        // |x| >= 2**-60
        x1 = 0.25f * x1 * x1
    }
    return 1.0f - x1
}

public val U00: kotlin.Float = -7.3804296553e-02f

// 0xbd9726b5
public val U01: kotlin.Float = 1.7666645348e-01f

// 0x3e34e80d
public val U02: kotlin.Float = -1.3818567619e-02f

// 0xbc626746
public val U03: kotlin.Float = 3.4745343146e-04f

// 0x39b62a69
public val U04: kotlin.Float = -3.8140706238e-06f

// 0xb67ff53c
public val U05: kotlin.Float = 1.9559013964e-08f

// 0x32a802ba
public val U06: kotlin.Float = -3.9820518410e-11f

// 0xae2f21eb
public val V01: kotlin.Float = 1.2730483897e-02f

// 0x3c509385
public val V02: kotlin.Float = 7.6006865129e-05f

// 0x389f65e0
public val V03: kotlin.Float = 2.5915085189e-07f

// 0x348b216c
public val V04: kotlin.Float = 4.4111031494e-10f

// 0x2ff280c2

/**
 * Zeroth order of the [Bessel function](https://en.wikipedia.org/wiki/BesselFunction) of the second kind (f32).
 */
public fun y0f(x: kotlin.Float): kotlin.Float {
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
        // large ulp error near zeros
        return common(ix, x, true)
    }
    if (ix >= 0x39000000u) {
        // x >= 2**-13
        // large ulp error at x ~= 0.89
        z = x * x
        u = U00 + z * (U01 + z * (U02 + z * (U03 + z * (U04 + z * (U05 + z * U06)))))
        v = 1.0f + z * (V01 + z * (V02 + z * (V03 + z * V04)))
        return u / v + TPI * (j0f(x) * logf(x))
    }
    return U00 + TPI * logf(x)
}

// The asymptotic expansions of pzero is
// 1 - 9/128 s^2 + 11025/98304 s^4 - ...,  where s = 1/x.
// For x >= 2, We approximate pzero by
// pzero(x) = 1 + (R/S)
// where  R = pR0 + pR1*s^2 + pR2*s^4 + ... + pR5*s^10
// S = 1 + pS0*s^2 + ... + pS4*s^10
// and
// | pzero(x)-1-R/S | <= 2  ** ( -60.26)
//
public val PR8: FloatArray = floatArrayOf(0.0000000000e00f, -7.0312500000e-02f, -8.0816707611e00f, -2.5706311035e02f, -2.4852163086e03f, -5.2530439453e03f)

public val PS8: FloatArray = floatArrayOf(1.1653436279e02f, 3.8337448730e03f, 4.0597855469e04f, 1.1675296875e05f, 4.7627726562e04f)

public val PR5: FloatArray = floatArrayOf(-1.1412546255e-11f, -7.0312492549e-02f, -4.1596107483e00f, -6.7674766541e01f, -3.3123129272e02f, -3.4643338013e02f)

public val PS5: FloatArray = floatArrayOf(6.0753936768e01f, 1.0512523193e03f, 5.9789707031e03f, 9.6254453125e03f, 2.4060581055e03f)

public val PR3: FloatArray = floatArrayOf(-2.5470459075e-09f, -7.0311963558e-02f, -2.4090321064e00f, -2.1965976715e01f, -5.8079170227e01f, -3.1447946548e01f)

public val PS3: FloatArray = floatArrayOf(3.5856033325e01f, 3.6151397705e02f, 1.1936077881e03f, 1.1279968262e03f, 1.7358093262e02f)

public val PR2: FloatArray = floatArrayOf(-8.8753431271e-08f, -7.0303097367e-02f, -1.4507384300e00f, -7.6356959343e00f, -1.1193166733e01f, -3.2336456776e00f)

public val PS2: FloatArray = floatArrayOf(2.2220300674e01f, 1.3620678711e02f, 2.7047027588e02f, 1.5387539673e02f, 1.4657617569e01f)

public fun pzerof(x: kotlin.Float): kotlin.Float {
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

// For x >= 8, the asymptotic expansions of qzero is
// -1/8 s + 75/1024 s^3 - ..., where s = 1/x.
// We approximate pzero by
// qzero(x) = s*(-1.25 + (R/S))
// where  R = qR0 + qR1*s^2 + qR2*s^4 + ... + qR5*s^10
// S = 1 + qS0*s^2 + ... + qS5*s^12
// and
// | qzero(x)/s +1.25-R/S | <= 2  ** ( -61.22)
//
public val QR8: FloatArray = floatArrayOf(0.0000000000e00f, 7.3242187500e-02f, 1.1768206596e01f, 5.5767340088e02f, 8.8591972656e03f, 3.7014625000e04f)

public val QS8: FloatArray = floatArrayOf(1.6377603149e02f, 8.0983447266e03f, 1.4253829688e05f, 8.0330925000e05f, 8.4050156250e05f, -3.4389928125e05f)

public val QR5: FloatArray = floatArrayOf(1.8408595828e-11f, 7.3242180049e-02f, 5.8356351852e00f, 1.3511157227e02f, 1.0272437744e03f, 1.9899779053e03f)

public val QS5: FloatArray = floatArrayOf(8.2776611328e01f, 2.0778142090e03f, 1.8847289062e04f, 5.6751113281e04f, 3.5976753906e04f, -5.3543427734e03f)

public val QR3: FloatArray = floatArrayOf(4.3774099900e-09f, 7.3241114616e-02f, 3.3442313671e00f, 4.2621845245e01f, 1.7080809021e02f, 1.6673394775e02f)

public val QS3: FloatArray = floatArrayOf(4.8758872986e01f, 7.0968920898e02f, 3.7041481934e03f, 6.4604252930e03f, 2.5163337402e03f, -1.4924745178e02f)

public val QR2: FloatArray = floatArrayOf(1.5044444979e-07f, 7.3223426938e-02f, 1.9981917143e00f, 1.4495602608e01f, 3.1666231155e01f, 1.6252708435e01f)

public val QS2: FloatArray = floatArrayOf(3.0365585327e01f, 2.6934811401e02f, 8.4478375244e02f, 8.8293585205e02f, 2.1266638184e02f, -5.3109550476e00f)

public fun qzerof(x: kotlin.Float): kotlin.Float {
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
    return (-0.125f + r / s) / x
}
