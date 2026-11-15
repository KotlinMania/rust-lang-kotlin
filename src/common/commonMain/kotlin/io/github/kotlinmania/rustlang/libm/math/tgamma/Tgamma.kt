// port-lint: source math/tgamma.rs
package io.github.kotlinmania.rustlang.libm.math.tgamma

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.libm.math.exp.exp
import io.github.kotlinmania.rustlang.libm.math.floor.floor
import io.github.kotlinmania.rustlang.libm.math.kcos.kCos
import io.github.kotlinmania.rustlang.libm.math.ksin.kSin
import io.github.kotlinmania.rustlang.libm.math.pow.pow
import io.github.kotlinmania.rustlang.libm.math.support.uncheckedDivIsize

public val PI: Double = 3.141592653589793238462643383279502884

// sin(pi x) with x > 0x1p-100, if sin(pi*x)==0 the sign is arbitrary
public fun sinpi(x: Double): Double {
    var x1 = x
    var n: Int
    // argument reduction: x = |x| mod 2
    // spurious inexact when x is odd int
    x1 = x1 * 0.5
    x1 = 2.0 * (x1 - floor(x1))
    // reduce x into [-.25,.25]
    n = (4.0 * x1).toInt()
    // SAFETY: nonzero divisor, nonnegative dividend (`n<8`).
    n = uncheckedDivIsize(n + 1, 2)
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

public val N: Int = 12

// static const double g = 6.024680040776729583740234375;
public val GMHALF: Double = 5.524680040776729583740234375

public val SNUM: DoubleArray = doubleArrayOf(23531376880.410759688572007674451636754734846804940, 42919803642.649098768957899047001988850926355848959, 35711959237.355668049440185451547166705960488635843, 17921034426.037209699919755754458931112671403265390, 6039542586.3520280050642916443072979210699388420708, 1439720407.3117216736632230727949123939715485786772, 248874557.86205415651146038641322942321632125127801, 31426415.585400194380614231628318205362874684987640, 2876370.6289353724412254090516208496135991145378768, 186056.26539522349504029498971604569928220784236328, 8071.6720023658162106380029022722506138218516325024, 210.82427775157934587250973392071336271166969580291, 2.5066282746310002701649081771338373386264310793408)

public val SDEN: DoubleArray = doubleArrayOf(0.0, 39916800.0, 120543840.0, 150917976.0, 105258076.0, 45995730.0, 13339535.0, 2637558.0, 357423.0, 32670.0, 1925.0, 66.0, 1.0)

// n! for small integer n
public val FACT: DoubleArray = doubleArrayOf(1.0, 1.0, 2.0, 6.0, 24.0, 120.0, 720.0, 5040.0, 40320.0, 362880.0, 3628800.0, 39916800.0, 479001600.0, 6227020800.0, 87178291200.0, 1307674368000.0, 20922789888000.0, 355687428096000.0, 6402373705728000.0, 121645100408832000.0, 2432902008176640000.0, 51090942171709440000.0, 1124000727777607680000.0)

// S(x) rational function for positive x
public fun s(x: Double): Double {
    var num: Double = 0.0
    var den: Double = 0.0
    // to avoid overflow handle large x differently
    if (x < 8.0) {
        for (i in (0..N).rev()) {
            num = num * x + SNUM.getUnchecked(i)
            den = den * x + SDEN.getUnchecked(i)
        }
    } else {
        for (i1 in 0..N) {
            num = num / x + SNUM.getUnchecked(i1)
            den = den / x + SDEN.getUnchecked(i1)
        }
    }
    return num / den
}

/**
 * The [Gamma function](https://en.wikipedia.org/wiki/GammaFunction) (f64).
 */
public fun tgamma(x: Double): Double {
    var x1 = x
    val u: ULong = x1.toRawBits().toULong()
    val absx: Double
    var y: Double
    var dy: Double
    var z: Double
    var r: Double
    val ix: UInt = ((u shr 32).toUInt()) and 0x7fffffffu
    val sign: Boolean = (u shr 63) != 0uL
    // special cases
    if (ix >= 0x7ff00000u) {
        // tgamma(nan)=nan, tgamma(inf)=inf, tgamma(-inf)=nan with invalid
        return x1 + Double.POSITIVE_INFINITY
    }
    if (ix < ((0x3ffu - 54u) shl 20)) {
        // |x| < 2^-54: tgamma(x) ~ 1/x, +-0 raises div-by-zero
        return 1.0 / x1
    }
    // integer arguments
    // raise inexact when non-integer
    if (x1 == floor(x1)) {
        if (sign) {
            return 0.0 / 0.0
        }
        if (x1 <= FACT.size.toDouble()) {
            return FACT.getUnchecked((x1.toInt()) - 1)
        }
    }
    // x >= 172: tgamma(x)=inf with overflow
    // x =< -184: tgamma(x)=+-0 with underflow
    if (ix >= 0x40670000u) {
        // |x| >= 184
        if (sign) {
            val x1p126 = Double.fromBits(0x3810000000000000uL.toLong())
            readVolatile((x1p126 / x1).toFloat(), typeLayoutT = PrimitiveLayouts.F32)
            if (floor(x1) * 0.5 == floor(x1 * 0.5)) {
                return 0.0
            } else {
                return -0.0
            }
        }
        val x1p1023 = Double.fromBits(0x7fe0000000000000uL.toLong())
        // 0x1p1023 == 2^1023
        x1 *= x1p1023
        return x1
    }
    absx = if (sign) {
        -x1
    } else {
        x1
    }
    // handle the error of x + g - 0.5
    y = absx + GMHALF
    if (absx > GMHALF) {
        dy = y - absx
        dy -= GMHALF
    } else {
        dy = y - GMHALF
        dy -= absx
    }
    z = absx - 0.5
    r = s(absx) * exp(-y)
    if (x1 < 0.0) {
        // reflection formula for negative x
        // sinpi(absx) is not 0, integers are already handled
        r = -PI / (sinpi(absx) * absx * r)
        dy = -dy
        z = -z
    }
    r += dy * (GMHALF + 0.5) * r / y
    z = pow(y, 0.5 * z)
    y = r * z * z
    return y
}
