// port-lint: source math/cbrt.rs
package io.github.kotlinmania.rustlang.libm.math.cbrt

import io.github.kotlinmania.rustlang.core.intrinsics.kmCopysignf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmFabsf64
import io.github.kotlinmania.rustlang.core.num.leadingZeros
import io.github.kotlinmania.rustlang.libm.math.support.FpResult
import io.github.kotlinmania.rustlang.libm.math.support.Round
import io.github.kotlinmania.rustlang.libm.math.support.coldPath
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hf64

/**
 * Compute the cube root of the argument.
 */
public fun cbrt(x: Double): Double = cbrtRound(x, Round.Nearest).value

public fun cbrtRound(x: Double, round: Round): FpResult<Double> {
    val ESCALE: DoubleArray =
        doubleArrayOf(1.0, run {
            val X: Double = hf64("0x1.428a2f98d728bp+0")
            X
        }, run {
            val X: Double = hf64("0x1.965fea53d6e3dp+0")
            X
        })
    // the polynomial c0+c1*x+c2*x^2+c3*x^3 approximates x^(1/3) on [1,2]
    // with maximal error < 9.2e-5 (attained at x=2)
    val C: DoubleArray =
        doubleArrayOf(run {
            val X: Double = hf64("0x1.1b0babccfef9cp-1")
            X
        }, run {
            val X: Double = hf64("0x1.2c9a3e94d1da5p-1")
            X
        }, run {
            val X: Double = hf64("-0x1.4dc30b1a1ddbap-3")
            X
        }, run {
            val X: Double = hf64("0x1.7a8d3e4ec9b07p-6")
            X
        })
    val u0: Double = run {
        val X: Double = hf64("0x1.5555555555555p-2")
        X
    }
    val u1: Double = run {
        val X: Double = hf64("0x1.c71c71c71c71cp-3")
        X
    }
    val rsc = listOf(1.0, -1.0, 0.5, -0.5, 0.25, -0.25)
    val off =
        doubleArrayOf(run {
            val X: Double = hf64("0x1p-53")
            X
        }, 0.0, 0.0, 0.0)
    // rm=0 for rounding to nearest, and other values for directed roundings
    val hx: ULong = x.toRawBits().toULong()
    var mant: ULong = hx and Double.SIG_MASK.toULong()
    val sign: ULong = hx shr 63
    var e: UInt = (hx shr Double.SIG_BITS.toInt()).toUInt() and Double.EXP_SAT.toUInt()
    if (((e + 1u) and Double.EXP_SAT.toUInt()) < 2u) {
        coldPath()
        val ix: ULong = hx and Double.SIGN_MASK.inv()
        // 0, inf, nan: we return x + x instead of simply x,
        // to that for x a signaling NaN, it correctly triggers
        // the invalid exception.
        if (e == Double.EXP_SAT.toUInt() || ix == 0uL) {
            return FpResult.ok(x + x)
        }
        val nz = ix.leadingZeros() - 11u
        // subnormal
        mant = mant shl nz.toInt()
        mant = mant and Double.SIG_MASK.toULong()
        e = (e - (nz - 1u))
    }
    e = (e + 3072u)
    val cvt1: ULong = mant or (0x3ffuL shl 52)
    var cvt5: ULong = cvt1
    val et: UInt = e / 3u
    val it: UInt = e % 3u
    // 2^(3k+it) <= x < 2^(3k+it+1), with 0 <= it <= 3
    cvt5 += ULong.from(it) shl Double.SIG_BITS.toInt()
    cvt5 = cvt5 or (sign shl 63)
    val zz: Double = Double.fromBits(cvt5.toLong())
    // cbrt(x) = cbrt(zz)*2^(et-1365) where 1 <= zz < 8
    var isc: ULong = ESCALE[it.toInt()].toBits()
    // Note: index
    isc = isc or (sign shl 63)
    val cvt2: ULong = isc
    val z: Double = Double.fromBits(cvt1.toLong())
    // cbrt(zz) = cbrt(z)*isc, where isc encodes 1, 2^(1/3) or 2^(2/3),
    // and 1 <= z < 2
    val r: Double = 1.0 / z
    val rr: Double = r * rsc[((it.toInt()) shl 1) or sign.toInt()]
    val z2: Double = z * z
    val c0: Double = C[0] + z * C[1]
    val c2: Double = C[2] + z * C[3]
    var y: Double = c0 + z2 * c2
    var y2: Double = y * y
    // y is an approximation of z^(1/3)
    var h: Double = y2 * (y * r) - 1.0
    // h determines the error between y and z^(1/3)
    y -= (h * y) * (u0 - u1 * h)
    // The correction y -= (h*y)*(u0 - u1*h) corresponds to a cubic variant
    // of Newton's method, with the function f(y) = 1-z/y^3.
    y *= Double.fromBits(cvt2.toLong())
    // Now y is an approximation of zz^(1/3),
    // and rr an approximation of 1/zz. We now perform another iteration of
    // Newton-Raphson, this time with a linear approximation only.
    y2 = y * y
    var y2l: Double = y.fma(y, -y2)
    // y2 + y2l = y^2 exactly
    var y3: Double = y2 * y
    var y3l: Double = y.fma(y2, -y3) + y * y2l
    // y3 + y3l approximates y^3 with about 106 bits of accuracy
    h = ((y3 - zz) + y3l) * rr
    var dy: Double = h * (y * u0)
    // the approximation of zz^(1/3) is y - dy
    var y1: Double = y - dy
    dy = (y - y1) - dy
    // the approximation of zz^(1/3) is now y1 + dy, where |dy| < 1/2 ulp(y)
    // (for rounding to nearest)
    var ady: Double = kmFabsf64(dy)
    // For directed roundings, ady0 is tiny when dy is tiny, or ady0 is near
    // from ulp(1);
    // for rounding to nearest, ady0 is tiny when dy is near from 1/2 ulp(1),
    // or from 3/2 ulp(1).
    var ady0: Double = kmFabsf64((ady - off[round.toInt()]))
    var ady1: Double =
        kmFabsf64((ady -
            (run {
                val X: Double = hf64("0x1p-52")
                X
            } + off[round.toInt()])))
    if (ady0 <
        run {
            val X: Double = hf64("0x1p-75")
            X
        } ||
        ady1 <
            run {
                val X: Double = hf64("0x1p-75")
                X
            }) {
        coldPath()
        y2 = y1 * y1
        y2l = y1.fma(y1, -y2)
        y3 = y2 * y1
        y3l = y1.fma(y2, -y3) + y1 * y2l
        h = ((y3 - zz) + y3l) * rr
        dy = h * (y1 * u0)
        y = y1 - dy
        dy = (y1 - y) - dy
        y1 = y
        ady = kmFabsf64(dy)
        ady0 = kmFabsf64((ady - off[round.toInt()]))
        ady1 = kmFabsf64((ady -
            (run {
                val X: Double = hf64("0x1p-52")
                X
            } + off[round.toInt()])))
        if (ady0 <
            run {
                val X: Double = hf64("0x1p-98")
                X
            } ||
            ady1 <
                run {
                    val X: Double = hf64("0x1p-98")
                    X
                }) {
            coldPath()
            val azz: Double = kmFabsf64(zz)
            // ~ 0x1.79d15d0e8d59b80000000000000ffc3dp+0
            if (azz ==
                run {
                    val X: Double = hf64("0x1.9b78223aa307cp+1")
                    X
                }) {
                y1 = kmCopysignf64(run {
                    val X: Double = hf64("0x1.79d15d0e8d59cp+0")
                    X
                }, zz)
            }
            // ~ 0x1.de87aa837820e80000000000001c0f08p+0
            if (azz ==
                run {
                    val X: Double = hf64("0x1.a202bfc89ddffp+2")
                    X
                }) {
                y1 = kmCopysignf64(run {
                    val X: Double = hf64("0x1.de87aa837820fp+0")
                    X
                }, zz)
            }
            if (round != Round.Nearest) {
                val wlist =
                    listOf(Pair(run {
                        val X: Double = hf64("0x1.3a9ccd7f022dbp+0")
                        X
                    }, run {
                        val X: Double = hf64("0x1.1236160ba9b93p+0")
                        X
                    }), Pair(run {
                        val X: Double = hf64("0x1.7845d2faac6fep+0")
                        X
                    }, run {
                        val X: Double = hf64("0x1.23115e657e49cp+0")
                        X
                    }), Pair(run {
                        val X: Double = hf64("0x1.d1ef81cbbbe71p+0")
                        X
                    }, run {
                        val X: Double = hf64("0x1.388fb44cdcf5ap+0")
                        X
                    }), Pair(run {
                        val X: Double = hf64("0x1.0a2014f62987cp+1")
                        X
                    }, run {
                        val X: Double = hf64("0x1.46bcbf47dc1e8p+0")
                        X
                    }), Pair(run {
                        val X: Double = hf64("0x1.fe18a044a5501p+1")
                        X
                    }, run {
                        val X: Double = hf64("0x1.95decfec9c904p+0")
                        X
                    }), Pair(run {
                        val X: Double = hf64("0x1.a6bb8c803147bp+2")
                        X
                    }, run {
                        val X: Double = hf64("0x1.e05335a6401dep+0")
                        X
                    }), Pair(run {
                        val X: Double = hf64("0x1.ac8538a031cbdp+2")
                        X
                    }, run {
                        val X: Double = hf64("0x1.e281d87098de8p+0")
                        X
                    }))
                for ((a, b) in wlist) {
                    if (azz == a) {
                        val tmp =
                            if (round.toULong() + sign == 2uL) {
                                run {
                                    val X: Double = hf64("0x1p-52")
                                    X
                                }
                            } else {
                                0.0
                            }
                        y1 = kmCopysignf64((b + tmp), zz)
                    }
                }
            }
        }
    }
    var cvt3: ULong = y1.toRawBits().toULong()
    cvt3 = (cvt3 + ((((((et - 342u) - 1023u)).toULong()) shl 52).toULong()))
    val m0: ULong = cvt3 shl 30
    val m1 = m0 shr 63
    if ((m0 xor m1) <= (1uL shl 30)) {
        coldPath()
        var cvt4: ULong = y1.toRawBits().toULong()
        cvt4 = (cvt4 + (164uL shl 15)) and 0xffffffffffff0000uL
        if (kmFabsf64(((Double.fromBits(cvt4.toLong()) - y1) - dy)) <
            run {
                val X: Double = hf64("0x1p-60")
                X
            } || kmFabsf64((zz)) == 1.0) {
            cvt3 = (cvt3 + (1uL shl 15)) and 0xffffffffffff0000uL
        }
    }
    return FpResult.ok(Double.fromBits(cvt3.toLong()))
}
