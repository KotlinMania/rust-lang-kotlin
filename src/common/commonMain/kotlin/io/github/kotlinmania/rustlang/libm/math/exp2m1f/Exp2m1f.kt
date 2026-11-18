// port-lint: source math/exp2m1f.rs
package io.github.kotlinmania.rustlang.libm.math.exp2m1f

import io.github.kotlinmania.rustlang.core.convert.num.from
import io.github.kotlinmania.rustlang.libm.math.support.coldPath
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hf32
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hf64
import io.github.kotlinmania.rustlang.libm.math.support.inttraits.Int

/**
 * Base-2 exponential minus one (f32).
 *
 * Calculate `2 ^ x - 1`, that is, 2 raised to the power `x` minus `1`.
 */
public fun exp2m1f(x: kotlin.Float): kotlin.Float = crExp2m1f(x)

public fun crExp2m1f(x: kotlin.Float): kotlin.Float {
    val Q: List<Pair<kotlin.Float, kotlin.Float>> =
        listOf(Pair(run {
            val X: kotlin.Float = hf32("0x1.fffffep127")
            X
        }, run {
            val X: kotlin.Float = hf32("0x1.fffffep127")
            X
        }), Pair(run {
            val X: kotlin.Float = hf32("0x1.fffffep127")
            X
        }, run {
            val X: kotlin.Float = hf32("0x1p+103")
            X
        }), Pair(-1.0, run {
            val X: kotlin.Float = hf32("0x1p-26")
            X
        }))
    val z = Double.from(x)
    val ux = x.toRawBits().toUInt()
    val ax = ux and UInt.MAX_VALUE shr 1
    // x <= -25
    if (ux >= 0xc1c80000u) {
        coldPath()
        if (ax > (0xffu shl 23)) {
            return x + x
            // NaN
        }
        // avoid spurious inexact exception for -Inf
        if (ux == 0xff800000u) {
            return Q[2].first
        } else {
            return Q[2].first + Q[2].second
        }
    }
    // x >= 128, result is infinite (with default rounding)
    if (ax >= 0x43000000u) {
        coldPath()
        if (ax > (0xffu shl 23)) {
            return x + x
            // nan
        }
        // for x=128 and rounding downward or to zero, there is no overflow
        val special = Int.from(x == 128.0f && (Q[1].first + Q[1].second == Q[1].first))
        // avoid spurious inexact exception for +Inf
        if (ux == 0x7f800000u) {
            return x
        } else {
            return Q[special].first + Q[special].second
        }
    }
    return if (ax < 0x3df95f1fu) {
        // |x| < 8.44e-2/log(2)
        coldPath()
        val z2 = z * z
        var r: Double
        if (ax < 0x3d67a4ccu) {
            // |x| < 3.92e-2/log(2)
            coldPath()
            if (ax < 0x3caa2feeu) {
                // |x| < 1.44e-2/log(2)
                coldPath()
                if (ax < 0x3bac1405u) {
                    // |x| < 3.64e-3/log(2)
                    coldPath()
                    if (ax < 0x3a358876u) {
                        // |x| < 4.8e-4/log(2)
                        coldPath()
                        if (ax < 0x37d32ef6u) {
                            // |x| < 1.745e-5/log(2)
                            coldPath()
                            if (ax < 0x331fdd82u) {
                                // |x| < 2.58e-8/log(2)
                                coldPath()
                                if (ax < 0x2538aa3bu) {
                                    // |x| < 0x1.715476p-53
                                    coldPath()
                                    r = (run {
                                        val X: Double = hf64("0x1.62e42fefa39efp-1")
                                        X
                                    }).toDouble()
                                } else {
                                    r = run {
                                        val X: Double = hf64("0x1.62e42fefa39fp-1")
                                        X
                                    } +
                                        z *
                                            run {
                                                val X: Double = hf64("0x1.ebfbdff82c58fp-3")
                                                X
                                            }
                                }
                            } else {
                                val C: DoubleArray =
                                    doubleArrayOf((run {
                                        val X: Double = hf64("0x1.62e42fefa39efp-1")
                                        X
                                    }).toDouble(), (run {
                                        val X: Double = hf64("0x1.ebfbdff8548fdp-3")
                                        X
                                    }).toDouble(), (run {
                                        val X: Double = hf64("0x1.c6b08d704a06dp-5")
                                        X
                                    }).toDouble())
                                if (ux == 0xb3d85005u) {
                                    coldPath()
                                    return (-run {
                                        val X: Double = hf64("0x1.2bdf76p-24")
                                        X
                                    } -
                                        run {
                                            val X: Double = hf64("0x1.8p-77")
                                            X
                                        }).castLossy()
                                }
                                if (ux == 0x3338428du) {
                                    coldPath()
                                    return (run {
                                        val X: Double = hf64("0x1.fee08ap-26")
                                        X
                                    } +
                                        run {
                                            val X: Double = hf64("0x1p-80")
                                            X
                                        }).castLossy()
                                }
                                r = C[0] + z * (C[1] + z * C[2])
                            }
                        } else {
                            val C: DoubleArray =
                                doubleArrayOf((run {
                                    val X: Double = hf64("0x1.62e42fefa39efp-1")
                                    X
                                }).toDouble(), (run {
                                    val X: Double = hf64("0x1.ebfbdff82c58fp-3")
                                    X
                                }).toDouble(), (run {
                                    val X: Double = hf64("0x1.c6b08dc82b347p-5")
                                    X
                                }).toDouble(), (run {
                                    val X: Double = hf64("0x1.3b2ab6fbad172p-7")
                                    X
                                }).toDouble())
                            if (ux == 0x388bca4fu) {
                                coldPath()
                                return (run {
                                    val X: Double = hf64("0x1.839702p-15")
                                    X
                                } -
                                    run {
                                        val X: Double = hf64("0x1.8p-68")
                                        X
                                    }).castLossy()
                            }
                            r = (C[0] + z * C[1]) + z2 * (C[2] + z * C[3])
                        }
                    } else {
                        val C: DoubleArray =
                            doubleArrayOf((run {
                                val X: Double = hf64("0x1.62e42fefa39efp-1")
                                X
                            }).toDouble(), (run {
                                val X: Double = hf64("0x1.ebfbdff82c068p-3")
                                X
                            }).toDouble(), (run {
                                val X: Double = hf64("0x1.c6b08d704a6dcp-5")
                                X
                            }).toDouble(), (run {
                                val X: Double = hf64("0x1.3b2ac262c3eedp-7")
                                X
                            }).toDouble(), (run {
                                val X: Double = hf64("0x1.5d87fe7af779ap-10")
                                X
                            }).toDouble())
                        r = (C[0] + z * C[1]) + z2 * (C[2] + z * (C[3] + z * C[4]))
                    }
                } else {
                    val C: DoubleArray =
                        doubleArrayOf((run {
                            val X: Double = hf64("0x1.62e42fefa39fp-1")
                            X
                        }).toDouble(), (run {
                            val X: Double = hf64("0x1.ebfbdff82c58dp-3")
                            X
                        }).toDouble(), (run {
                            val X: Double = hf64("0x1.c6b08d7011d13p-5")
                            X
                        }).toDouble(), (run {
                            val X: Double = hf64("0x1.3b2ab6fbd267dp-7")
                            X
                        }).toDouble(), (run {
                            val X: Double = hf64("0x1.5d88a81cea49ep-10")
                            X
                        }).toDouble(), (run {
                            val X: Double = hf64("0x1.430912ea9b963p-13")
                            X
                        }).toDouble())
                    r = (C[0] + z * C[1]) + z2 * ((C[2] + z * C[3]) + z2 * (C[4] + z * C[5]))
                }
            } else {
                val C: DoubleArray =
                    doubleArrayOf((run {
                        val X: Double = hf64("0x1.62e42fefa39efp-1")
                        X
                    }).toDouble(), (run {
                        val X: Double = hf64("0x1.ebfbdff82c639p-3")
                        X
                    }).toDouble(), (run {
                        val X: Double = hf64("0x1.c6b08d7049f1cp-5")
                        X
                    }).toDouble(), (run {
                        val X: Double = hf64("0x1.3b2ab6f5243bdp-7")
                        X
                    }).toDouble(), (run {
                        val X: Double = hf64("0x1.5d87fe80a9e6cp-10")
                        X
                    }).toDouble(), (run {
                        val X: Double = hf64("0x1.430d0b9257fa8p-13")
                        X
                    }).toDouble(), (run {
                        val X: Double = hf64("0x1.ffcbfc4cf0952p-17")
                        X
                    }).toDouble())
                r = (C[0] + z * C[1]) + z2 * ((C[2] + z * C[3]) + z2 * (C[4] + z * (C[5] + z * C[6])))
            }
        } else {
            val C: DoubleArray =
                doubleArrayOf((run {
                    val X: Double = hf64("0x1.62e42fefa39efp-1")
                    X
                }).toDouble(), (run {
                    val X: Double = hf64("0x1.ebfbdff82c591p-3")
                    X
                }).toDouble(), (run {
                    val X: Double = hf64("0x1.c6b08d704cf6bp-5")
                    X
                }).toDouble(), (run {
                    val X: Double = hf64("0x1.3b2ab6fba00cep-7")
                    X
                }).toDouble(), (run {
                    val X: Double = hf64("0x1.5d87fdfdaadb4p-10")
                    X
                }).toDouble(), (run {
                    val X: Double = hf64("0x1.4309137333066p-13")
                    X
                }).toDouble(), (run {
                    val X: Double = hf64("0x1.ffe5e90daf7ddp-17")
                    X
                }).toDouble(), (run {
                    val X: Double = hf64("0x1.62c0220eed731p-20")
                    X
                }).toDouble())
            r = ((C[0] + z * C[1]) + z2 * (C[2] + z * C[3])) + (z2 * z2) * ((C[4] + z * C[5]) + z2 * (C[6] + z * C[7]))
        }
        r *= z
        r.castLossy()
    } else {
        val C: DoubleArray =
            doubleArrayOf((run {
                val X: Double = hf64("0x1.62e42fefa398bp-5")
                X
            }).toDouble(), (run {
                val X: Double = hf64("0x1.ebfbdff84555ap-11")
                X
            }).toDouble(), (run {
                val X: Double = hf64("0x1.c6b08d4ad86d3p-17")
                X
            }).toDouble(), (run {
                val X: Double = hf64("0x1.3b2ad1b1716a2p-23")
                X
            }).toDouble(), (run {
                val X: Double = hf64("0x1.5d7472718ce9dp-30")
                X
            }).toDouble(), (run {
                val X: Double = hf64("0x1.4a1d7f457ac56p-37")
                X
            }).toDouble())
        val TB: DoubleArray =
            doubleArrayOf((run {
                val X: Double = hf64("0x1p+0")
                X
            }).toDouble(), (run {
                val X: Double = hf64("0x1.0b5586cf9890fp+0")
                X
            }).toDouble(), (run {
                val X: Double = hf64("0x1.172b83c7d517bp+0")
                X
            }).toDouble(), (run {
                val X: Double = hf64("0x1.2387a6e756238p+0")
                X
            }).toDouble(), (run {
                val X: Double = hf64("0x1.306fe0a31b715p+0")
                X
            }).toDouble(), (run {
                val X: Double = hf64("0x1.3dea64c123422p+0")
                X
            }).toDouble(), (run {
                val X: Double = hf64("0x1.4bfdad5362a27p+0")
                X
            }).toDouble(), (run {
                val X: Double = hf64("0x1.5ab07dd485429p+0")
                X
            }).toDouble(), (run {
                val X: Double = hf64("0x1.6a09e667f3bcdp+0")
                X
            }).toDouble(), (run {
                val X: Double = hf64("0x1.7a11473eb0187p+0")
                X
            }).toDouble(), (run {
                val X: Double = hf64("0x1.8ace5422aa0dap+0")
                X
            }).toDouble(), (run {
                val X: Double = hf64("0x1.9c49182a3f09p+0")
                X
            }).toDouble(), (run {
                val X: Double = hf64("0x1.ae89f995ad3adp+0")
                X
            }).toDouble(), (run {
                val X: Double = hf64("0x1.c199bdd85529cp+0")
                X
            }).toDouble(), (run {
                val X: Double = hf64("0x1.d5818dcfba487p+0")
                X
            }).toDouble(), (run {
                val X: Double = hf64("0x1.ea4afa2a490dap+0")
                X
            }).toDouble())
        val a = 16.0 * z
        val ia = a.floor()
        val h = a - ia
        val h2 = h * h
        val i: Long = ia.cast()
        val j = i and 0xfL
        var e = i - j
        e = e shr 4
        var s = TB[Int.castFrom(j)]
        val su = Double.fromBits(((e + 0x3ffL).unsigned() shl 52).toLong())
        s *= su
        var c0 = C[0] + h * C[1]
        val c2 = C[2] + h * C[3]
        val c4 = C[4] + h * C[5]
        c0 += h2 * (c2 + h2 * c4)
        val w = s * h
        ((s - 1.0) + w * c0).castLossy()
    }
}
