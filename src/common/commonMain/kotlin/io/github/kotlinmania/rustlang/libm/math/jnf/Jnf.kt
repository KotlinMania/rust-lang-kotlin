// port-lint: source math/jnf.rs
package io.github.kotlinmania.rustlang.libm.math.jnf

import io.github.kotlinmania.rustlang.libm.math.fabs.fabsf
import io.github.kotlinmania.rustlang.libm.math.j0f.j0f
import io.github.kotlinmania.rustlang.libm.math.j0f.y0f
import io.github.kotlinmania.rustlang.libm.math.j1f.j1f
import io.github.kotlinmania.rustlang.libm.math.j1f.y1f
import io.github.kotlinmania.rustlang.libm.math.logf.logf

/**
 * Integer order of the [Bessel function](https://en.wikipedia.org/wiki/BesselFunction) of the first kind (f32).
 */
public fun jnf(n: Int, x: kotlin.Float): kotlin.Float {
    var x1 = x
    var ix: UInt
    var nm1: Int
    var sign: Boolean
    var i: Int
    var a: kotlin.Float
    var b: kotlin.Float
    var temp: kotlin.Float
    ix = x1.toRawBits().toUInt()
    sign = (ix shr 31) != 0u
    ix = ix and 0x7fffffffu
    if (ix > 0x7f800000u) {
        // nan
        return x1
    }
    // J(-n,x) = J(n,-x), use |n|-1 to avoid overflow in -n
    if (n == 0) {
        return j0f(x1)
    }
    if (n < 0) {
        nm1 = -(n + 1)
        x1 = -x1
        sign = !sign
    } else {
        nm1 = n - 1
    }
    if (nm1 == 0) {
        return j1f(x1)
    }
    sign = sign and ((n and 1) != 0)
    // even n: 0, odd n: signbit(x)
    x1 = fabsf(x1)
    if (ix == 0u || ix == 0x7f800000u) {
        // if x is 0 or inf
        b = 0.0f
    } else if ((nm1.toFloat()) < x1) {
        // Safe to use J(n+1,x)=2n/x *J(n,x)-J(n-1,x)
        a = j0f(x1)
        b = j1f(x1)
        i = 0
        while (i < nm1) {
            i += 1
            temp = b
            b = b * (2.0f * (i.toFloat()) / x1) - a
            a = temp
        }
    } else if (ix < 0x35800000u) {
        // x < 2**-20
        // x is tiny, return the first Taylor expansion of J(n,x)
        // J(n,x) = 1/n!*(x/2)^n  - ...
        //
        if (nm1 > 8) {
            // underflow
            nm1 = 8
        }
        temp = 0.5f * x1
        b = temp
        a = 1.0f
        i = 2
        while (i <= nm1 + 1) {
            a *= i.toFloat()
            // a = n!
            b *= temp
            // b = (x/2)^n
            i += 1
        }
        b = b / a
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
        var t: kotlin.Float
        var q0: kotlin.Float
        var q1: kotlin.Float
        var w: kotlin.Float
        val h: kotlin.Float
        var z: kotlin.Float
        var tmp: kotlin.Float
        val nf: kotlin.Float
        var k: Int
        nf = (nm1.toFloat()) + 1.0f
        w = 2.0f * nf / x1
        h = 2.0f / x1
        z = w + h
        q0 = w
        q1 = w * z - 1.0f
        k = 1
        while (q1 < 1.0e4f) {
            k += 1
            z += h
            tmp = z * q1 - q0
            q0 = q1
            q1 = tmp
        }
        t = 0.0f
        i = k
        while (i >= 0) {
            t = 1.0f / (2.0f * ((i.toFloat()) + nf) / x1 - t)
            i -= 1
        }
        a = t
        b = 1.0f
        // estimate log((2/x)^n*n!) = n*log(2/x)+n*ln(n)
        // Hence, if n*(log(2n/x)) > ...
        // single 8.8722839355e+01
        // double 7.09782712893383973096e+02
        // long double 1.1356523406294143949491931077970765006170e+04
        // then recurrent value may overflow and the result is
        // likely underflow to zero
        //
        tmp = nf * logf(fabsf(w))
        if (tmp < 88.721679688f) {
            i = nm1
            while (i > 0) {
                temp = b
                b = 2.0f * (i.toFloat()) * b / x1 - a
                a = temp
                i -= 1
            }
        } else {
            i = nm1
            while (i > 0) {
                temp = b
                b = 2.0f * (i.toFloat()) * b / x1 - a
                a = temp
                // scale b to avoid spurious overflow
                val x1p60 = kotlin.Float.fromBits(0x5d800000u.toInt())
                // 0x1p60 == 2^60
                if (b > x1p60) {
                    a /= b
                    t /= b
                    b = 1.0f
                }
                i -= 1
            }
        }
        z = j0f(x1)
        w = j1f(x1)
        if (fabsf(z) >= fabsf(w)) {
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
 * Integer order of the [Bessel function](https://en.wikipedia.org/wiki/BesselFunction) of the second kind (f32).
 */
public fun ynf(n: Int, x: kotlin.Float): kotlin.Float {
    var ix: UInt
    var ib: UInt
    val nm1: Int
    var sign: Boolean
    var i: Int
    var a: kotlin.Float
    var b: kotlin.Float
    var temp: kotlin.Float
    ix = x.toRawBits().toUInt()
    sign = (ix shr 31) != 0u
    ix = ix and 0x7fffffffu
    if (ix > 0x7f800000u) {
        // nan
        return x
    }
    if (sign && ix != 0u) {
        // x < 0
        return 0.0f / 0.0f
    }
    if (ix == 0x7f800000u) {
        return 0.0f
    }
    if (n == 0) {
        return y0f(x)
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
            return -y1f(x)
        } else {
            return y1f(x)
        }
    }
    a = y0f(x)
    b = y1f(x)
    // quit if b is -inf
    ib = b.toRawBits().toUInt()
    i = 0
    while (i < nm1 && ib != 0xff800000u) {
        i += 1
        temp = b
        b = (2.0f * (i.toFloat()) / x) * b - a
        ib = b.toRawBits().toUInt()
        a = temp
    }
    return if (sign) {
        -b
    } else {
        b
    }
}
