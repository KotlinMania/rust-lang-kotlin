// port-lint: source math/j0.rs
package io.github.kotlinmania.rustlang.libm.math.j0

import io.github.kotlinmania.rustlang.math.arch.aarch64.sqrt.sqrt
import io.github.kotlinmania.rustlang.math.cos.cos
import io.github.kotlinmania.rustlang.math.fabs.fabs
import io.github.kotlinmania.rustlang.math.getHighWord
import io.github.kotlinmania.rustlang.math.getLowWord
import io.github.kotlinmania.rustlang.math.log.log
import io.github.kotlinmania.rustlang.math.sin.sin

public val INVSQRTPI: Double = 5.64189583547756279280e-01

// 0x3FE20DD7, 0x50429B6D
public val TPI: Double = 6.36619772367581382433e-01

// 0x3FE45F30, 0x6DC9C883
// common method when |x|>=2
public fun common(ix: UInt, x: Double, y0: Boolean): Double {
    val s: Double
    var c: Double
    var ss: Double
    var cc: Double
    val z: Double
    //
    // j0(x) = sqrt(2/(pi*x))*(p0(x)*cos(x-pi/4)-q0(x)*sin(x-pi/4))
    // y0(x) = sqrt(2/(pi*x))*(p0(x)*sin(x-pi/4)+q0(x)*cos(x-pi/4))
    //
    // sin(x-pi/4) = (sin(x) - cos(x))/sqrt(2)
    // cos(x-pi/4) = (sin(x) + cos(x))/sqrt(2)
    // sin(x) +- cos(x) = -cos(2x)/(sin(x) -+ cos(x))
    //
    s = sin(x)
    c = cos(x)
    if (y0) {
        c = -c
    }
    cc = s + c
    // avoid overflow in 2*x, big ulp error when x>=0x1p1023
    if (ix < 0x7fe00000u) {
        ss = s - c
        z = -cos(2.0 * x)
        if (s * c < 0.0) {
            cc = z / ss
        } else {
            ss = z / cc
        }
        if (ix < 0x48000000u) {
            if (y0) {
                ss = -ss
            }
            cc = pzero(x) * cc - qzero(x) * ss
        }
    }
    return INVSQRTPI * cc / sqrt(x)
}

// R0/S0 on [0, 2.00]
public val R02: Double = 1.56249999999999947958e-02

// 0x3F8FFFFF, 0xFFFFFFFD
public val R03: Double = -1.89979294238854721751e-04

// 0xBF28E6A5, 0xB61AC6E9
public val R04: Double = 1.82954049532700665670e-06

// 0x3EBEB1D1, 0x0C503919
public val R05: Double = -4.61832688532103189199e-09

// 0xBE33D5E7, 0x73D63FCE
public val S01: Double = 1.56191029464890010492e-02

// 0x3F8FFCE8, 0x82C8C2A4
public val S02: Double = 1.16926784663337450260e-04

// 0x3F1EA6D2, 0xDD57DBF4
public val S03: Double = 5.13546550207318111446e-07

// 0x3EA13B54, 0xCE84D5A9
public val S04: Double = 1.16614003333790000205e-09

// 0x3E1408BC, 0xF4745D8F

/**
 * Zeroth order of the [Bessel function](https://en.wikipedia.org/wiki/BesselFunction) of the first kind (f64).
 */
public fun j0(x: Double): Double {
    var x1 = x
    val z: Double
    val r: Double
    val s: Double
    var ix: UInt
    ix = getHighWord(x1)
    ix = ix and 0x7fffffffu
    // j0(+-inf)=0, j0(nan)=nan
    if (ix >= 0x7ff00000u) {
        return 1.0 / (x1 * x1)
    }
    x1 = fabs(x1)
    if (ix >= 0x40000000u) {
        // |x| >= 2
        // large ulp error near zeros: 2.4, 5.52, 8.6537,..
        return common(ix, x1, false)
    }
    // 1 - x*x/4 + x*x*R(x^2)/S(x^2)
    if (ix >= 0x3f200000u) {
        // |x| >= 2**-13
        // up to 4ulp error close to 2
        z = x1 * x1
        r = z * (R02 + z * (R03 + z * (R04 + z * R05)))
        s = 1.0 + z * (S01 + z * (S02 + z * (S03 + z * S04)))
        return (1.0 + x1 / 2.0) * (1.0 - x1 / 2.0) + z * (r / s)
    }
    // 1 - x*x/4
    // prevent underflow
    // inexact should be raised when x!=0, this is not done correctly
    if (ix >= 0x38000000u) {
        // |x| >= 2**-127
        x1 = 0.25 * x1 * x1
    }
    return 1.0 - x1
}

public val U00: Double = -7.38042951086872317523e-02

// 0xBFB2E4D6, 0x99CBD01F
public val U01: Double = 1.76666452509181115538e-01

// 0x3FC69D01, 0x9DE9E3FC
public val U02: Double = -1.38185671945596898896e-02

// 0xBF8C4CE8, 0xB16CFA97
public val U03: Double = 3.47453432093683650238e-04

// 0x3F36C54D, 0x20B29B6B
public val U04: Double = -3.81407053724364161125e-06

// 0xBECFFEA7, 0x73D25CAD
public val U05: Double = 1.95590137035022920206e-08

// 0x3E550057, 0x3B4EABD4
public val U06: Double = -3.98205194132103398453e-11

// 0xBDC5E43D, 0x693FB3C8
public val V01: Double = 1.27304834834123699328e-02

// 0x3F8A1270, 0x91C9C71A
public val V02: Double = 7.60068627350353253702e-05

// 0x3F13ECBB, 0xF578C6C1
public val V03: Double = 2.59150851840457805467e-07

// 0x3E91642D, 0x7FF202FD
public val V04: Double = 4.41110311332675467403e-10

// 0x3DFE5018, 0x3BD6D9EF

/**
 * Zeroth order of the [Bessel function](https://en.wikipedia.org/wiki/BesselFunction) of the second kind (f64).
 */
public fun y0(x: Double): Double {
    val z: Double
    val u: Double
    val v: Double
    val ix: UInt
    val lx: UInt
    ix = getHighWord(x)
    lx = getLowWord(x)
    // y0(nan)=nan, y0(<0)=nan, y0(0)=-inf, y0(inf)=0
    if (((ix shl 1) or lx) == 0u) {
        return -1.0 / 0.0
    }
    if ((ix shr 31) != 0u) {
        return 0.0 / 0.0
    }
    if (ix >= 0x7ff00000u) {
        return 1.0 / x
    }
    if (ix >= 0x40000000u) {
        // x >= 2
        // large ulp errors near zeros: 3.958, 7.086,..
        return common(ix, x, true)
    }
    // U(x^2)/V(x^2) + (2/pi)*j0(x)*log(x)
    if (ix >= 0x3e400000u) {
        // x >= 2**-27
        // large ulp error near the first zero, x ~= 0.89
        z = x * x
        u = U00 + z * (U01 + z * (U02 + z * (U03 + z * (U04 + z * (U05 + z * U06)))))
        v = 1.0 + z * (V01 + z * (V02 + z * (V03 + z * V04)))
        return u / v + TPI * (j0(x) * log(x))
    }
    return U00 + TPI * log(x)
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
public val PR8: DoubleArray = doubleArrayOf(0.00000000000000000000e00, -7.03124999999900357484e-02, -8.08167041275349795626e00, -2.57063105679704847262e02, -2.48521641009428822144e03, -5.25304380490729545272e03)

public val PS8: DoubleArray = doubleArrayOf(1.16534364619668181717e02, 3.83374475364121826715e03, 4.05978572648472545552e04, 1.16752972564375915681e05, 4.76277284146730962675e04)

public val PR5: DoubleArray = doubleArrayOf(-1.14125464691894502584e-11, -7.03124940873599280078e-02, -4.15961064470587782438e00, -6.76747652265167261021e01, -3.31231299649172967747e02, -3.46433388365604912451e02)

public val PS5: DoubleArray = doubleArrayOf(6.07539382692300335975e01, 1.05125230595704579173e03, 5.97897094333855784498e03, 9.62544514357774460223e03, 2.40605815922939109441e03)

public val PR3: DoubleArray = doubleArrayOf(-2.54704601771951915620e-09, -7.03119616381481654654e-02, -2.40903221549529611423e00, -2.19659774734883086467e01, -5.80791704701737572236e01, -3.14479470594888503854e01)

public val PS3: DoubleArray = doubleArrayOf(3.58560338055209726349e01, 3.61513983050303863820e02, 1.19360783792111533330e03, 1.12799679856907414432e03, 1.73580930813335754692e02)

public val PR2: DoubleArray = doubleArrayOf(-8.87534333032526411254e-08, -7.03030995483624743247e-02, -1.45073846780952986357e00, -7.63569613823527770791e00, -1.11931668860356747786e01, -3.23364579351335335033e00)

public val PS2: DoubleArray = doubleArrayOf(2.22202997532088808441e01, 1.36206794218215208048e02, 2.70470278658083486789e02, 1.53875394208320329881e02, 1.46576176948256193810e01)

public fun pzero(x: Double): Double {
    val p: DoubleArray
    val q: DoubleArray
    val z: Double
    val r: Double
    val s: Double
    var ix: UInt
    ix = getHighWord(x)
    ix = ix and 0x7fffffffu
    if (ix >= 0x40200000u) {
        p = PR8
        q = PS8
    } else if (ix >= 0x40122E8Bu) {
        p = PR5
        q = PS5
    } else if (ix >= 0x4006DB6Du) {
        p = PR3
        q = PS3
    } else {
        p = PR2
        q = PS2
    }
    z = 1.0 / (x * x)
    r = p[0] + z * (p[1] + z * (p[2] + z * (p[3] + z * (p[4] + z * p[5]))))
    s = 1.0 + z * (q[0] + z * (q[1] + z * (q[2] + z * (q[3] + z * q[4]))))
    return 1.0 + r / s
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
public val QR8: DoubleArray = doubleArrayOf(0.00000000000000000000e00, 7.32421874999935051953e-02, 1.17682064682252693899e01, 5.57673380256401856059e02, 8.85919720756468632317e03, 3.70146267776887834771e04)

public val QS8: DoubleArray = doubleArrayOf(1.63776026895689824414e02, 8.09834494656449805916e03, 1.42538291419120476348e05, 8.03309257119514397345e05, 8.40501579819060512818e05, -3.43899293537866615225e05)

public val QR5: DoubleArray = doubleArrayOf(1.84085963594515531381e-11, 7.32421766612684765896e-02, 5.83563508962056953777e00, 1.35111577286449829671e02, 1.02724376596164097464e03, 1.98997785864605384631e03)

public val QS5: DoubleArray = doubleArrayOf(8.27766102236537761883e01, 2.07781416421392987104e03, 1.88472887785718085070e04, 5.67511122894947329769e04, 3.59767538425114471465e04, -5.35434275601944773371e03)

public val QR3: DoubleArray = doubleArrayOf(4.37741014089738620906e-09, 7.32411180042911447163e-02, 3.34423137516170720929e00, 4.26218440745412650017e01, 1.70808091340565596283e02, 1.66733948696651168575e02)

public val QS3: DoubleArray = doubleArrayOf(4.87588729724587182091e01, 7.09689221056606015736e02, 3.70414822620111362994e03, 6.46042516752568917582e03, 2.51633368920368957333e03, -1.49247451836156386662e02)

public val QR2: DoubleArray = doubleArrayOf(1.50444444886983272379e-07, 7.32234265963079278272e-02, 1.99819174093815998816e00, 1.44956029347885735348e01, 3.16662317504781540833e01, 1.62527075710929267416e01)

public val QS2: DoubleArray = doubleArrayOf(3.03655848355219184498e01, 2.69348118608049844624e02, 8.44783757595320139444e02, 8.82935845112488550512e02, 2.12666388511798828631e02, -5.31095493882666946917e00)

public fun qzero(x: Double): Double {
    val p: DoubleArray
    val q: DoubleArray
    val s: Double
    val r: Double
    val z: Double
    var ix: UInt
    ix = getHighWord(x)
    ix = ix and 0x7fffffffu
    if (ix >= 0x40200000u) {
        p = QR8
        q = QS8
    } else if (ix >= 0x40122E8Bu) {
        p = QR5
        q = QS5
    } else if (ix >= 0x4006DB6Du) {
        p = QR3
        q = QS3
    } else {
        p = QR2
        q = QS2
    }
    z = 1.0 / (x * x)
    r = p[0] + z * (p[1] + z * (p[2] + z * (p[3] + z * (p[4] + z * p[5]))))
    s = 1.0 + z * (q[0] + z * (q[1] + z * (q[2] + z * (q[3] + z * (q[4] + z * q[5])))))
    return (-0.125 + r / s) / x
}
