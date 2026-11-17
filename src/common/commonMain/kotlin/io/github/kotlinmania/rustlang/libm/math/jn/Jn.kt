// port-lint: source math/jn.rs
package io.github.kotlinmania.rustlang.libm.math.jn

import io.github.kotlinmania.rustlang.libm.math.cos.cos
import io.github.kotlinmania.rustlang.libm.math.fabs.fabs
import io.github.kotlinmania.rustlang.libm.math.getHighWord
import io.github.kotlinmania.rustlang.libm.math.getLowWord
import io.github.kotlinmania.rustlang.libm.math.j0.j0
import io.github.kotlinmania.rustlang.libm.math.j0.y0
import io.github.kotlinmania.rustlang.libm.math.j1.j1
import io.github.kotlinmania.rustlang.libm.math.j1.y1
import io.github.kotlinmania.rustlang.libm.math.log.log
import io.github.kotlinmania.rustlang.libm.math.sin.sin
import io.github.kotlinmania.rustlang.libm.math.sqrt.sqrt

public val INVSQRTPI: Double = 5.64189583547756279280e-01

// 0x3FE20DD7, 0x50429B6D

/**
 * Integer order of the [Bessel function](https://en.wikipedia.org/wiki/BesselFunction) of the first kind (f64).
 */
public fun jn(n: Int, x: Double): Double {
    var x1 = x
    var ix: UInt
    val lx: UInt
    val nm1: Int
    var i: Int
    var sign: Boolean
    var a: Double
    var b: Double
    var temp: Double
    ix = getHighWord(x1)
    lx = getLowWord(x1)
    sign = (ix shr 31) != 0u
    ix = ix and 0x7fffffffu
    // -lx == !lx + 1
    if (ix or ((lx or (!lx).wrappingAdd(1)) shr 31) > 0x7ff00000u) {
        // nan
        return x1
    }
    // J(-n,x) = (-1)^n * J(n, x), J(n, -x) = (-1)^n * J(n, x)
    // Thus, J(-n,x) = J(n,-x)
    //
    // nm1 = |n|-1 is used instead of |n| to handle n==INT_MIN
    if (n == 0) {
        return j0(x1)
    }
    if (n < 0) {
        nm1 = -(n + 1)
        x1 = -x1
        sign = !sign
    } else {
        nm1 = n - 1
    }
    if (nm1 == 0) {
        return j1(x1)
    }
    sign = sign and ((n and 1) != 0)
    // even n: 0, odd n: signbit(x)
    x1 = fabs(x1)
    if ((ix or lx) == 0u || ix == 0x7ff00000u) {
        // if x is 0 or inf
        b = 0.0
    } else if ((nm1.toDouble()) < x1) {
        // Safe to use J(n+1,x)=2n/x *J(n,x)-J(n-1,x)
        if (ix >= 0x52d00000u) {
            // x > 2**302
            // (x >> n**2)
            // Jn(x) = cos(x-(2n+1)*pi/4)*sqrt(2/x*pi)
            // Yn(x) = sin(x-(2n+1)*pi/4)*sqrt(2/x*pi)
            // Let s=sin(x), c=cos(x),
            // xn=x-(2n+1)*pi/4, sqt2 = sqrt(2),then
            //
            // n    sin(xn)*sqt2    cos(xn)*sqt2
            // ----------------------------------
            // 0     s-c             c+s
            // 1    -s-c            -c+s
            // 2    -s+c            -c-s
            // 3     s+c             c-s
            //
            temp = when (nm1 and 3) {
                0 -> -cos(x1) + sin(x1)
                1 -> -cos(x1) - sin(x1)
                2 -> cos(x1) - sin(x1)
                // 3
                else -> cos(x1) + sin(x1)
            }
            b = INVSQRTPI * temp / sqrt(x1)
        } else {
            a = j0(x1)
            b = j1(x1)
            i = 0
            while (i < nm1) {
                i += 1
                temp = b
                b = b * (2.0 * (i.toDouble()) / x1) - a
                // avoid underflow
                a = temp
            }
        }
    } else if (ix < 0x3e100000u) {
        // x < 2**-29
        // x is tiny, return the first Taylor expansion of J(n,x)
        // J(n,x) = 1/n!*(x/2)^n  - ...
        //
        if (nm1 > 32) {
            // underflow
            b = 0.0
        } else {
            temp = x1 * 0.5
            b = temp
            a = 1.0
            i = 2
            while (i <= nm1 + 1) {
                a *= i.toDouble()
                // a = n!
                b *= temp
                // b = (x/2)^n
                i += 1
            }
            b = b / a
        }
    } else {
        // use backward recurrence
        // x      x^2      x^2
        // J(n,x)/J(n-1,x) =  ----   ------   ------   .....
        // 2n  - 2(n+1) - 2(n+2)
        //
        // 1      1        1
        // (for large x)   =  ----  ------   ------   .....
        // 2n   2(n+1)   2(n+2)
        // -- - ------ - ------ -
        // x     x         x
        //
        // Let w = 2n/x and h=2/x, then the above quotient
        // is equal to the continued fraction:
        // 1
        // = -----------------------
        // 1
        // w - -----------------
        // 1
        // w+h - ---------
        // w+2h - ...
        //
        // To determine how many terms needed, let
        // Q(0) = w, Q(1) = w(w+h) - 1,
        // Q(k) = (w+k*h)*Q(k-1) - Q(k-2),
        // When Q(k) > 1e4      good for single
        // When Q(k) > 1e9      good for double
        // When Q(k) > 1e17     good for quadruple
        //
        // determine k
        var t: Double
        var q0: Double
        var q1: Double
        var w: Double
        val h: Double
        var z: Double
        var tmp: Double
        val nf: Double
        var k: Int
        nf = (nm1.toDouble()) + 1.0
        w = 2.0 * nf / x1
        h = 2.0 / x1
        z = w + h
        q0 = w
        q1 = w * z - 1.0
        k = 1
        while (q1 < 1.0e9) {
            k += 1
            z += h
            tmp = z * q1 - q0
            q0 = q1
            q1 = tmp
        }
        t = 0.0
        i = k
        while (i >= 0) {
            t = 1.0 / (2.0 * ((i.toDouble()) + nf) / x1 - t)
            i -= 1
        }
        a = t
        b = 1.0
        // estimate log((2/x)^n*n!) = n*log(2/x)+n*ln(n)
        // Hence, if n*(log(2n/x)) > ...
        // single 8.8722839355e+01
        // double 7.09782712893383973096e+02
        // long double 1.1356523406294143949491931077970765006170e+04
        // then recurrent value may overflow and the result is
        // likely underflow to zero
        //
        tmp = nf * log(fabs(w))
        if (tmp < 7.09782712893383973096e02) {
            i = nm1
            while (i > 0) {
                temp = b
                b = b * (2.0 * (i.toDouble())) / x1 - a
                a = temp
                i -= 1
            }
        } else {
            i = nm1
            while (i > 0) {
                temp = b
                b = b * (2.0 * (i.toDouble())) / x1 - a
                a = temp
                // scale b to avoid spurious overflow
                val x1p500 = Double.fromBits(0x5f30000000000000uL.toLong())
                // 0x1p500 == 2^500
                if (b > x1p500) {
                    a /= b
                    t /= b
                    b = 1.0
                }
                i -= 1
            }
        }
        z = j0(x1)
        w = j1(x1)
        if (fabs(z) >= fabs(w)) {
            b = t * z / b
        } else {
            b = t * w / a
        }
    }
    return if (sign) {
        -b
    } else {
        b
    }
}

/**
 * Integer order of the [Bessel function](https://en.wikipedia.org/wiki/BesselFunction) of the second kind (f64).
 */
public fun yn(n: Int, x: Double): Double {
    var ix: UInt
    val lx: UInt
    var ib: UInt
    val nm1: Int
    var sign: Boolean
    var i: Int
    var a: Double
    var b: Double
    var temp: Double
    ix = getHighWord(x)
    lx = getLowWord(x)
    sign = (ix shr 31) != 0u
    ix = ix and 0x7fffffffu
    // -lx == !lx + 1
    if (ix or ((lx or (!lx).wrappingAdd(1)) shr 31) > 0x7ff00000u) {
        // nan
        return x
    }
    if (sign && (ix or lx) != 0u) {
        // x < 0
        return 0.0 / 0.0
    }
    if (ix == 0x7ff00000u) {
        return 0.0
    }
    if (n == 0) {
        return y0(x)
    }
    if (n < 0) {
        nm1 = -(n + 1)
        sign = (n and 1) != 0
    } else {
        nm1 = n - 1
        sign = false
    }
    if (nm1 == 0) {
        if (sign) {
            return -y1(x)
        } else {
            return y1(x)
        }
    }
    if (ix >= 0x52d00000u) {
        // x > 2**302
        // (x >> n**2)
        // Jn(x) = cos(x-(2n+1)*pi/4)*sqrt(2/x*pi)
        // Yn(x) = sin(x-(2n+1)*pi/4)*sqrt(2/x*pi)
        // Let s=sin(x), c=cos(x),
        // xn=x-(2n+1)*pi/4, sqt2 = sqrt(2),then
        //
        // n    sin(xn)*sqt2    cos(xn)*sqt2
        // ----------------------------------
        // 0     s-c             c+s
        // 1    -s-c            -c+s
        // 2    -s+c            -c-s
        // 3     s+c             c-s
        //
        temp = when (nm1 and 3) {
            0 -> -sin(x) - cos(x)
            1 -> -sin(x) + cos(x)
            2 -> sin(x) + cos(x)
            // 3
            else -> sin(x) - cos(x)
        }
        b = INVSQRTPI * temp / sqrt(x)
    } else {
        a = y0(x)
        b = y1(x)
        // quit if b is -inf
        ib = getHighWord(b)
        i = 0
        while (i < nm1 && ib != 0xfff00000u) {
            i += 1
            temp = b
            b = (2.0 * (i.toDouble()) / x) * b - a
            ib = getHighWord(b)
            a = temp
        }
    }
    return if (sign) {
        -b
    } else {
        b
    }
}
