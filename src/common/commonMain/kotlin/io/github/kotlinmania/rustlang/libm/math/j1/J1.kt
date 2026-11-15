// port-lint: source math/j1.rs
package io.github.kotlinmania.rustlang.libm.math.j1

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
public fun common(ix: UInt, x: Double, y1: Boolean, sign: Boolean): Double {
    val z: Double
    var s: Double
    val c: Double
    var ss: Double
    var cc: Double
    //
    // j1(x) = sqrt(2/(pi*x))*(p1(x)*cos(x-3pi/4)-q1(x)*sin(x-3pi/4))
    // y1(x) = sqrt(2/(pi*x))*(p1(x)*sin(x-3pi/4)+q1(x)*cos(x-3pi/4))
    //
    // sin(x-3pi/4) = -(sin(x) + cos(x))/sqrt(2)
    // cos(x-3pi/4) = (sin(x) - cos(x))/sqrt(2)
    // sin(x) +- cos(x) = -cos(2x)/(sin(x) -+ cos(x))
    //
    s = sin(x)
    if (y1) {
        s = -s
    }
    c = cos(x)
    cc = s - c
    if (ix < 0x7fe00000u) {
        // avoid overflow in 2*x
        ss = -s - c
        z = cos(2.0 * x)
        if (s * c > 0.0) {
            cc = z / ss
        } else {
            ss = z / cc
        }
        if (ix < 0x48000000u) {
            if (y1) {
                ss = -ss
            }
            cc = pone(x) * cc - qone(x) * ss
        }
    }
    if (sign) {
        cc = -cc
    }
    return INVSQRTPI * cc / sqrt(x)
}

// R0/S0 on [0,2]
public val R00: Double = -6.25000000000000000000e-02

// 0xBFB00000, 0x00000000
public val R01: Double = 1.40705666955189706048e-03

// 0x3F570D9F, 0x98472C61
public val R02: Double = -1.59955631084035597520e-05

// 0xBEF0C5C6, 0xBA169668
public val R03: Double = 4.96727999609584448412e-08

// 0x3E6AAAFA, 0x46CA0BD9
public val S01: Double = 1.91537599538363460805e-02

// 0x3F939D0B, 0x12637E53
public val S02: Double = 1.85946785588630915560e-04

// 0x3F285F56, 0xB9CDF664
public val S03: Double = 1.17718464042623683263e-06

// 0x3EB3BFF8, 0x333F8498
public val S04: Double = 5.04636257076217042715e-09

// 0x3E35AC88, 0xC97DFF2C
public val S05: Double = 1.23542274426137913908e-11

// 0x3DAB2ACF, 0xCFB97ED8

/**
 * First order of the [Bessel function](https://en.wikipedia.org/wiki/BesselFunction) of the first kind (f64).
 */
public fun j1(x: Double): Double {
    var z: Double
    val r: Double
    val s: Double
    var ix: UInt
    val sign: Boolean
    ix = getHighWord(x)
    sign = (ix shr 31) != 0u
    ix = ix and 0x7fffffffu
    if (ix >= 0x7ff00000u) {
        return 1.0 / (x * x)
    }
    if (ix >= 0x40000000u) {
        // |x| >= 2
        return common(ix, fabs(x), false, sign)
    }
    if (ix >= 0x38000000u) {
        // |x| >= 2**-127
        z = x * x
        r = z * (R00 + z * (R01 + z * (R02 + z * R03)))
        s = 1.0 + z * (S01 + z * (S02 + z * (S03 + z * (S04 + z * S05))))
        z = r / s
    } else {
        // avoid underflow, raise inexact if x!=0
        z = x
    }
    return (0.5 + z) * x
}

public val U0: DoubleArray = doubleArrayOf(-1.96057090646238940668e-01, 5.04438716639811282616e-02, -1.91256895875763547298e-03, 2.35252600561610495928e-05, -9.19099158039878874504e-08)

public val V0: DoubleArray = doubleArrayOf(1.99167318236649903973e-02, 2.02552581025135171496e-04, 1.35608801097516229404e-06, 6.22741452364621501295e-09, 1.66559246207992079114e-11)

/**
 * First order of the [Bessel function](https://en.wikipedia.org/wiki/BesselFunction) of the second kind (f64).
 */
public fun y1(x: Double): Double {
    val z: Double
    val u: Double
    val v: Double
    val ix: UInt
    val lx: UInt
    ix = getHighWord(x)
    lx = getLowWord(x)
    // y1(nan)=nan, y1(<0)=nan, y1(0)=-inf, y1(inf)=0
    if ((ix shl 1) or lx == 0u) {
        return -1.0 / 0.0
    }
    if (ix shr 31 != 0u) {
        return 0.0 / 0.0
    }
    if (ix >= 0x7ff00000u) {
        return 1.0 / x
    }
    if (ix >= 0x40000000u) {
        // x >= 2
        return common(ix, x, true, false)
    }
    if (ix < 0x3c900000u) {
        // x < 2**-54
        return -TPI / x
    }
    z = x * x
    u = U0[0] + z * (U0[1] + z * (U0[2] + z * (U0[3] + z * U0[4])))
    v = 1.0 + z * (V0[0] + z * (V0[1] + z * (V0[2] + z * (V0[3] + z * V0[4]))))
    return x * (u / v) + TPI * (j1(x) * log(x) - 1.0 / x)
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
public val PR8: DoubleArray = doubleArrayOf(0.00000000000000000000e00, 1.17187499999988647970e-01, 1.32394806593073575129e01, 4.12051854307378562225e02, 3.87474538913960532227e03, 7.91447954031891731574e03)

public val PS8: DoubleArray = doubleArrayOf(1.14207370375678408436e02, 3.65093083420853463394e03, 3.69562060269033463555e04, 9.76027935934950801311e04, 3.08042720627888811578e04)

public val PR5: DoubleArray = doubleArrayOf(1.31990519556243522749e-11, 1.17187493190614097638e-01, 6.80275127868432871736e00, 1.08308182990189109773e02, 5.17636139533199752805e02, 5.28715201363337541807e02)

public val PS5: DoubleArray = doubleArrayOf(5.92805987221131331921e01, 9.91401418733614377743e02, 5.35326695291487976647e03, 7.84469031749551231769e03, 1.50404688810361062679e03)

public val PR3: DoubleArray = doubleArrayOf(3.02503916137373618024e-09, 1.17186865567253592491e-01, 3.93297750033315640650e00, 3.51194035591636932736e01, 9.10550110750781271918e01, 4.85590685197364919645e01)

public val PS3: DoubleArray = doubleArrayOf(3.47913095001251519989e01, 3.36762458747825746741e02, 1.04687139975775130551e03, 8.90811346398256432622e02, 1.03787932439639277504e02)

public val PR2: DoubleArray = doubleArrayOf(1.07710830106873743082e-07, 1.17176219462683348094e-01, 2.36851496667608785174e00, 1.22426109148261232917e01, 1.76939711271687727390e01, 5.07352312588818499250e00)

public val PS2: DoubleArray = doubleArrayOf(2.14364859363821409488e01, 1.25290227168402751090e02, 2.32276469057162813669e02, 1.17679373287147100768e02, 8.36463893371618283368e00)

public fun pone(x: Double): Double {
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

// For x >= 8, the asymptotic expansions of qone is
// 3/8 s - 105/1024 s^3 - ..., where s = 1/x.
// We approximate pone by
// qone(x) = s*(0.375 + (R/S))
// where  R = qr1*s^2 + qr2*s^4 + ... + qr5*s^10
// S = 1 + qs1*s^2 + ... + qs6*s^12
// and
// | qone(x)/s -0.375-R/S | <= 2  ** ( -61.13)
//
public val QR8: DoubleArray = doubleArrayOf(0.00000000000000000000e00, -1.02539062499992714161e-01, -1.62717534544589987888e01, -7.59601722513950107896e02, -1.18498066702429587167e04, -4.84385124285750353010e04)

public val QS8: DoubleArray = doubleArrayOf(1.61395369700722909556e02, 7.82538599923348465381e03, 1.33875336287249578163e05, 7.19657723683240939863e05, 6.66601232617776375264e05, -2.94490264303834643215e05)

public val QR5: DoubleArray = doubleArrayOf(-2.08979931141764104297e-11, -1.02539050241375426231e-01, -8.05644828123936029840e00, -1.83669607474888380239e02, -1.37319376065508163265e03, -2.61244440453215656817e03)

public val QS5: DoubleArray = doubleArrayOf(8.12765501384335777857e01, 1.99179873460485964642e03, 1.74684851924908907677e04, 4.98514270910352279316e04, 2.79480751638918118260e04, -4.71918354795128470869e03)

public val QR3: DoubleArray = doubleArrayOf(-5.07831226461766561369e-09, -1.02537829820837089745e-01, -4.61011581139473403113e00, -5.78472216562783643212e01, -2.28244540737631695038e02, -2.19210128478909325622e02)

public val QS3: DoubleArray = doubleArrayOf(4.76651550323729509273e01, 6.73865112676699709482e02, 3.38015286679526343505e03, 5.54772909720722782367e03, 1.90311919338810798763e03, -1.35201191444307340817e02)

public val QR2: DoubleArray = doubleArrayOf(-1.78381727510958865572e-07, -1.02517042607985553460e-01, -2.75220568278187460720e00, -1.96636162643703720221e01, -4.23253133372830490089e01, -2.13719211703704061733e01)

public val QS2: DoubleArray = doubleArrayOf(2.95333629060523854548e01, 2.52981549982190529136e02, 7.57502834868645436472e02, 7.39393205320467245656e02, 1.55949003336666123687e02, -4.95949898822628210127e00)

public fun qone(x: Double): Double {
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
    return (0.375 + r / s) / x
}
