// port-lint: source math/rem_pio2.rs
package io.github.kotlinmania.rustlang.libm.math.rempio2

import io.github.kotlinmania.rustlang.libm.math.rempio2large.remPio2Large

// #if FLT_EVAL_METHOD==0 || FLT_EVAL_METHOD==1
// #define EPS DBL_EPSILON
public val EPS: Double = 2.2204460492503131e-16

// #elif FLT_EVAL_METHOD==2
// #define EPS LDBL_EPSILON
// #endif
// Note: Support FLT_EVAL_METHOD?
public val TO_INT: Double = 1.5 / EPS

/**
 * 53 bits of 2/pi
 */
public val INV_PIO2: Double = 6.36619772367581382433e-01

// 0x3FE45F30, 0x6DC9C883

/**
 * first 33 bits of pi/2
 */
public val PIO2_1: Double = 1.57079632673412561417e00

// 0x3FF921FB, 0x54400000

/**
 * pi/2 - PIO2_1
 */
public val PIO2_1T: Double = 6.07710050650619224932e-11

// 0x3DD0B461, 0x1A626331

/**
 * second 33 bits of pi/2
 */
public val PIO2_2: Double = 6.07710050630396597660e-11

// 0x3DD0B461, 0x1A600000

/**
 * pi/2 - (PIO2_1+PIO2_2)
 */
public val PIO2_2T: Double = 2.02226624879595063154e-21

// 0x3BA3198A, 0x2E037073

/**
 * third 33 bits of pi/2
 */
public val PIO2_3: Double = 2.02226624871116645580e-21

// 0x3BA3198A, 0x2E000000

/**
 * pi/2 - (PIO2_1+PIO2_2+PIO2_3)
 */
public val PIO2_3T: Double = 8.47842766036889956997e-32

// 0x397B839A, 0x252049C1
// return the remainder of x rem pi/2 in y[0]+y[1]
// use remPio2Large() for large x
//
// caller must handle the case when reduction is not needed: |x| ~<= pi/4 * /
internal fun remPio2(x: Double): Triple<Int, Double, Double> {
    fun medium(x1: Double, ix: UInt): Triple<Int, Double, Double> {
        // rint(x/(pi/2)), Assume round-to-nearest.
        val tmp = x1 * INV_PIO2 + TO_INT
        // force rounding of tmp to it's storage format on x87 to avoid
        // excess precision issues.
        val tmp1 = readVolatile(tmp)
        val fN = tmp1 - TO_INT
        val n = fN.toInt()
        var r = x1 - fN * PIO2_1
        var w = fN * PIO2_1T
        // 1st round, good to 85 bits
        var y0 = r - w
        val ui = y0.toRawBits().toULong()
        val ey = (ui shr 52).toInt() and 0x7ff
        val ex = (ix shr 20).toInt()
        if (ex - ey > 16) {
            // 2nd round, good to 118 bits
            val t = r
            w = fN * PIO2_2
            r = t - w
            w = fN * PIO2_2T - ((t - r) - w)
            y0 = r - w
            val ey1 = (y0.toRawBits().toULong() shr 52).toInt() and 0x7ff
            if (ex - ey1 > 49) {
                // 3rd round, good to 151 bits, covers all cases
                val t1 = r
                w = fN * PIO2_3
                r = t1 - w
                w = fN * PIO2_3T - ((t1 - r) - w)
                y0 = r - w
            }
        }
        val y1 = (r - y0) - w
        return Triple(n, y0, y1)
    }
    val x1p24 = Double.fromBits(0x4170000000000000uL.toLong())
    val sign = (x.toRawBits().toULong() shr 63).toInt()
    val ix = (x.toRawBits().toULong() shr 32).toUInt() and 0x7fffffffu
    if (ix <= 0x400f6a7au) {
        // |x| ~<= 5pi/4
        if ((ix and 0xfffffu) == 0x921fbu) {
            // |x| ~= pi/2 or 2pi/2
            return medium(x, ix)
            // cancellation -- use medium case
        }
        if (ix <= 0x4002d97cu) {
            // |x| ~<= 3pi/4
            if (sign == 0) {
                val z = x - PIO2_1
                // one round good to 85 bits
                val y0 = z - PIO2_1T
                val y1 = (z - y0) - PIO2_1T
                return Triple(1, y0, y1)
            } else {
                val z1 = x + PIO2_1
                val y01 = z1 + PIO2_1T
                val y11 = (z1 - y01) + PIO2_1T
                return Triple(-1, y01, y11)
            }
        } else if (sign == 0) {
            val z2 = x - 2.0 * PIO2_1
            val y02 = z2 - 2.0 * PIO2_1T
            val y12 = (z2 - y02) - 2.0 * PIO2_1T
            return Triple(2, y02, y12)
        } else {
            val z3 = x + 2.0 * PIO2_1
            val y03 = z3 + 2.0 * PIO2_1T
            val y13 = (z3 - y03) + 2.0 * PIO2_1T
            return Triple(-2, y03, y13)
        }
    }
    if (ix <= 0x401c463bu) {
        // |x| ~<= 9pi/4
        if (ix <= 0x4015fdbcu) {
            // |x| ~<= 7pi/4
            if (ix == 0x4012d97cu) {
                // |x| ~= 3pi/2
                return medium(x, ix)
            }
            if (sign == 0) {
                val z4 = x - 3.0 * PIO2_1
                val y04 = z4 - 3.0 * PIO2_1T
                val y14 = (z4 - y04) - 3.0 * PIO2_1T
                return Triple(3, y04, y14)
            } else {
                val z5 = x + 3.0 * PIO2_1
                val y05 = z5 + 3.0 * PIO2_1T
                val y15 = (z5 - y05) + 3.0 * PIO2_1T
                return Triple(-3, y05, y15)
            }
        } else {
            if (ix == 0x401921fbu) {
                // |x| ~= 4pi/2
                return medium(x, ix)
            }
            if (sign == 0) {
                val z6 = x - 4.0 * PIO2_1
                val y06 = z6 - 4.0 * PIO2_1T
                val y16 = (z6 - y06) - 4.0 * PIO2_1T
                return Triple(4, y06, y16)
            } else {
                val z7 = x + 4.0 * PIO2_1
                val y07 = z7 + 4.0 * PIO2_1T
                val y17 = (z7 - y07) + 4.0 * PIO2_1T
                return Triple(-4, y07, y17)
            }
        }
    }
    if (ix < 0x413921fbu) {
        // |x| ~< 2^20*(pi/2), medium size
        return medium(x, ix)
    }
    //
    // all other (large) arguments
    //
    if (ix >= 0x7ff00000u) {
        // x is inf or NaN
        val y08 = x - x
        val y18 = y08
        return Triple(0, y08, y18)
    }
    // set z = scalbn(|x|,-ilogb(x)+23)
    var ui = x.toRawBits().toULong()
    ui = ui and ((1.inv()) shr 12)
    ui = ui or ((0x3ff + 23) shl 52)
    var z8 = Double.fromBits(ui.toLong())
    val tx = MutableList(3) { 0.0 }
    for (i in 0 until 2) {
        run {
            tx.getUncheckedMut(i) = (z8.toInt()).toDouble()
        }
        z8 = (z8 - tx.getUnchecked(i)) * x1p24
    }
    run {
        tx.getUncheckedMut(2) = z8
    }
    // skip zero terms, first term is non-zero
    var i1 = 2
    while (i1 != 0 && tx.getUnchecked(i1) == 0.0) {
        i1 -= 1
    }
    val ty = MutableList(3) { 0.0 }
    val n = remPio2Large(tx[0..i1], ty, ((ix.toInt()) shr 20) - (0x3ff + 23), 1)
    if (sign != 0) {
        return Triple(-n, -ty.getUnchecked(0), -ty.getUnchecked(1))
    }
    return Triple(n, ty.getUnchecked(0), ty.getUnchecked(1))
}
