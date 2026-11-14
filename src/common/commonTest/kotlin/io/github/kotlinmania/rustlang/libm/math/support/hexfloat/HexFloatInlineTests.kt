// port-lint: tests math/support/hex_float.rs
package io.github.kotlinmania.rustlang.libm.math.support.hexfloat

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.libm.math.support.env.Round
import io.github.kotlinmania.rustlang.libm.math.support.env.Status
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float
import kotlin.math.pow

// Utilities for working with hex float formats.

public fun roundingProperties(s: String): Result<Unit, HexFloatParseError> {
    val (xd, s0) = when (val tryResult = parseAny(s, 16u, 10u, Round.Negative)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val (xu, s1) = when (val tryResult = parseAny(s, 16u, 10u, Round.Positive)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val (xz, s2) = when (val tryResult = parseAny(s, 16u, 10u, Round.Zero)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val (xn, s3) = when (val tryResult = parseAny(s, 16u, 10u, Round.Nearest)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    // Note: A value between the least normal and largest subnormal
    // could have underflow status depend on rounding mode.
    if (s0 == Status.ok) {
        // an exact result is the same for all rounding modes
        check(s0 == s1)
        check(s0 == s2)
        check(s0 == s3)
        check(xd == xu)
        check(xd == xz)
        check(xd == xn)
    } else {
        check(doubleArrayOf(s0, s1, s2, s3).all(Status::inexact))
        val xd1 = F16.fromBits(xd.toUShort())
        val xu1 = F16.fromBits(xu.toUShort())
        val xz1 = F16.fromBits(xz.toUShort())
        val xn1 = F16.fromBits(xn.toUShort())
        run {
            val l = xd1.nextUp()
            val r = xu1
            check(l.biteq(r)) { ("s=$s, xd=$xd, xu=$xu").toString() + "\nl: " + (l).toString() + " (" + (l.toBits()).toString() + " " + (io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hexfmt.Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hexfmt.Hex(r)).toString() + ")" }
        }
        val signs = listOf(xd1, xu1, xz1, xn1).map(F16::isSignNegative)
        if (signs.contentEquals(BooleanArray(4) { true })) {
            run {
                val l1 = xz1
                val r1 = xu1
                check(l1.biteq(r1)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l1.toBits()).toString() + " " + (io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hexfmt.Hex(l1)).toString() + ")\nr: " + (r).toString() + " (" + (r1.toBits()).toString() + " " + (io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hexfmt.Hex(r1)).toString() + ")" }
            }
        } else {
            check(signs == BooleanArray(4) { false })
            run {
                val l2 = xz1
                val r2 = xd1
                check(l2.biteq(r2)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l2.toBits()).toString() + " " + (io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hexfmt.Hex(l2)).toString() + ")\nr: " + (r).toString() + " (" + (r2.toBits()).toString() + " " + (io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hexfmt.Hex(r2)).toString() + ")" }
            }
        }
        if (xn1.toBits() != xd1.toBits()) {
            run {
                val l3 = xn1
                val r3 = xu1
                check(l3.biteq(r3)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l3.toBits()).toString() + " " + (io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hexfmt.Hex(l3)).toString() + ")\nr: " + (r).toString() + " (" + (r3.toBits()).toString() + " " + (io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hexfmt.Hex(r3)).toString() + ")" }
            }
        }
    }
    return Result.Ok(Unit)
}

/**
 * Strip the qNaN/sNaN and payload since we don't parse that.
 */
public fun canonicalizeSnanStr(s: String): String {
    var s1 = s
    if (s1.contains("sNaN") || s1.contains("qNaN")) {
        s1 = s1.replace("sNaN", "NaN").replace("qNaN", "NaN")
        when (val matchVal = s1.let { s -> val d = "("; val idx = s.indexOf(d); if (idx < 0) null else Pair(s.substring(0, idx), s.substring(idx + d.length)) }) {
            null -> {}
            else -> {
                val nan = matchVal.first
                val payload = matchVal.second
                check(payload.endsWith(")"))
                s1 = nan.toString()
            }
        }
    }
    return s1
}

public class HexFloatParseTestsInlineTests {
    @kotlin.test.Test
    public fun testRounding() {
        val n = 1 shl 14
        for (i in -n until n) {
            val u = i.rotateRight(11).toUInt()
            val s = "${Hex(kotlin.Float.fromBits(u.toInt()))}"
            val s1 = canonicalizeSnanStr(s)
            run {
                val matchVal = roundingProperties(s1)
                when (matchVal) { is Result.Ok<*> -> {
                    Unit
                }; is Result.Err<*> -> {
                    val e = matchVal.error
                    error("failed rounding properties for `$s`: $e")
                } }
            }
        }
    }

    // This test is very slow when using Miri
    @kotlin.test.Test
    public fun testParseAny() {
        for (k in -149..127) {
            val s = "0x1p$k"
            val x = hf32(s)
            val y =
                if (k < 0) {
                    0.5f.pow(-k)
                } else {
                    2.0f.pow(k)
                }
            check(x == y)
        }
        val s1 = "0x.0000000p-121".encodeToByteArray()
        for (e in 0 until 40) {
            for (k1 in 0 until (1 shl 15)) {
                val expected = kotlin.Float.fromBits(k1) * 2.0f.pow(e.toInt())
                val x1 = hf32(String.fromUtf8(s1).unwrap())
                check(x1.toRawBits().toUInt() == expected.toRawBits().toUInt())
                for (i in (3 until 10).rev()) {
                    if (s1[i] == 102u.toByte()) {
                        s1[i] = 48u
                    } else if (s1[i] == 57u.toByte()) {
                        s1[i] = 97u
                        break
                    } else {
                        s1[i] += 1
                        break
                    }
                }
            }
            for (i1 in (12 until 15).rev()) {
                if (s1[i1] == 48u.toByte()) {
                    s1[i1] = 57u
                } else {
                    s1[i1] -= 1
                    break
                }
            }
            for (i2 in (3 until 10).rev()) {
                s1[i2] = 48u
            }
        }
    }

    // Note: this test is causing failures that are likely UB on various platforms
    @kotlin.test.Test
    public fun rounding() {
        val pi = F128.fromDouble(kotlin.math.PI)
        val s = "${Hex(pi)}"
        for (k in 0..111) {
            val (bits, status) = parseAny(s, 128 - k, 112 - k, Round.Nearest).unwrap()
            val scale = F128.fromDouble((U128(0x0uL, 0x1uL) shl (112 - k - 1)).toDouble())
            val expected = (pi * scale).roundTiesEven() / scale
            check(bits shl k == expected.toBits())
            check(expected != pi == status.inexact())
        }
    }

    @kotlin.test.Test
    public fun roundingExtremeUnderflow() {
        for (k in 1 until 1000) {
            val s = "0x1p${-149 - k}"
            val (bits, status) = parseAny(s, 32u, 23u, Round.Nearest) ?: error("unreachable")
            check(bits == 0)
            check(status.underflow()) { "should indicate underflow when parsing $s" }
            check(status.inexact()) { "should indicate inexact when parsing $s" }
        }
    }

    // This test is very slow when using Miri
    @kotlin.test.Test
    public fun longTail() {
        for (k in 1 until 1000) {
            val s = "0x1." + ("0".repeat(k)).toString() + "p0"
            val bits = parseHexExact(s, 32u, 23u) ?: error("parsing $s failed")
            check(kotlin.Float.fromBits((bits.toUInt()).toInt()) == 1.0f.toFloat())
            val s1 = "0x1." + ("0".repeat(k)).toString() + "1p0"
            val (bits1, status) = parseAny(s1, 32u, 23u, Round.Nearest) ?: error("unreachable")
            if (status.inexact()) {
                check(1.0f == kotlin.Float.fromBits((bits1.toUInt()).toInt()))
            } else {
                check(1.0f < kotlin.Float.fromBits((bits1.toUInt()).toInt()))
            }
        }
    }

    @kotlin.test.Test
    public fun testF16() {
        val checks = listOf(Pair("0x.1234p+16", (F16.fromDouble(0x1234.toDouble())).toBits()), Pair("0x1.234p+12", (F16.fromDouble(0x1234.toDouble())).toBits()), Pair("0x12.34p+8", (F16.fromDouble(0x1234.toDouble())).toBits()), Pair("0x123.4p+4", (F16.fromDouble(0x1234.toDouble())).toBits()), Pair("0x1234p+0", (F16.fromDouble(0x1234.toDouble())).toBits()), Pair("0x1234.p+0", (F16.fromDouble(0x1234.toDouble())).toBits()), Pair("0x1234.0p+0", (F16.fromDouble(0x1234.toDouble())).toBits()), Pair("0x1.ffcp+15", F16.MAX.toBits()), Pair("0x1.0p+1", (F16.fromFloat(2.0f)).toBits()), Pair("0x1.0p+0", (F16.fromFloat(1.0f)).toBits()), Pair("0x1.ffp+8", 0x5ffc), Pair("+0x1.ffp+8", 0x5ffc), Pair("0x1p+0", 0x3c00), Pair("0x1.998p-4", 0x2e66), Pair("0x1.9p+6", 0x5640), Pair("0x0.0p0", (F16.fromFloat(0.0f)).toBits()), Pair("-0x0.0p0", (-F16.fromFloat(0.0f)).toBits()), Pair("0x1.0p0", (F16.fromFloat(1.0f)).toBits()), Pair("0x1.998p-4", (F16.fromFloat(0.1f)).toBits()), Pair("-0x1.998p-4", (-F16.fromFloat(0.1f)).toBits()), Pair("0x0.123p-12", 0x0123), Pair("0x1p-24", 0x0001), Pair("nan", F16.NAN.toBits()), Pair("-nan", (-F16.NAN).toBits()), Pair("inf", F16.INFINITY.toBits()), Pair("-inf", F16.NEG_INFINITY.toBits()))
        for ((s, exp) in checks) {
            println("parsing $s")
            check(roundingProperties(s).isOk())
            val act = hf16(s).toBits()
            check(act == exp)
        }
    }

    @kotlin.test.Test
    public fun testF32() {
        val checks = listOf(Pair("0x.1234p+16", (0x1234.toFloat()).toRawBits().toUInt()), Pair("0x1.234p+12", (0x1234.toFloat()).toRawBits().toUInt()), Pair("0x12.34p+8", (0x1234.toFloat()).toRawBits().toUInt()), Pair("0x123.4p+4", (0x1234.toFloat()).toRawBits().toUInt()), Pair("0x1234p+0", (0x1234.toFloat()).toRawBits().toUInt()), Pair("0x1234.p+0", (0x1234.toFloat()).toRawBits().toUInt()), Pair("0x1234.0p+0", (0x1234.toFloat()).toRawBits().toUInt()), Pair("0x1.fffffep+127", kotlin.Float.MAX_VALUE.toRawBits().toUInt()), Pair("0x1.0p+1", 2.0f.toRawBits().toUInt()), Pair("0x1.0p+0", 1.0f.toRawBits().toUInt()), Pair("0x1.ffep+8", 0x43fff000), Pair("+0x1.ffep+8", 0x43fff000), Pair("0x1p+0", 0x3f800000), Pair("0x1.99999ap-4", 0x3dcccccd), Pair("0x1.9p+6", 0x42c80000), Pair("0x1.2d5ed2p+20", 0x4996af69), Pair("-0x1.348eb8p+10", 0xc49a475c), Pair("-0x1.33dcfep-33", 0xaf19ee7f), Pair("0x0.0p0", 0.0f.toRawBits().toUInt()), Pair("-0x0.0p0", (-0.0f).toRawBits().toUInt()), Pair("0x1.0p0", 1.0f.toRawBits().toUInt()), Pair("0x1.99999ap-4", (0.1f).toRawBits().toUInt()), Pair("-0x1.99999ap-4", (-0.1f).toRawBits().toUInt()), Pair("0x1.111114p-127", 0x00444445), Pair("0x1.23456p-130", 0x00091a2b), Pair("0x1p-149", 0x00000001), Pair("nan", kotlin.Float.NaN.toRawBits().toUInt()), Pair("-nan", (-kotlin.Float.NaN).toRawBits().toUInt()), Pair("inf", kotlin.Float.POSITIVE_INFINITY.toRawBits().toUInt()), Pair("-inf", kotlin.Float.NEGATIVE_INFINITY.toRawBits().toUInt()))
        for ((s, exp) in checks) {
            println("parsing $s")
            val act = hf32(s).toRawBits().toUInt()
            check(act == exp)
        }
    }

    @kotlin.test.Test
    public fun testF64() {
        val checks = listOf(Pair("0x.1234p+16", (0x1234.toDouble()).toRawBits().toULong()), Pair("0x1.234p+12", (0x1234.toDouble()).toRawBits().toULong()), Pair("0x12.34p+8", (0x1234.toDouble()).toRawBits().toULong()), Pair("0x123.4p+4", (0x1234.toDouble()).toRawBits().toULong()), Pair("0x1234p+0", (0x1234.toDouble()).toRawBits().toULong()), Pair("0x1234.p+0", (0x1234.toDouble()).toRawBits().toULong()), Pair("0x1234.0p+0", (0x1234.toDouble()).toRawBits().toULong()), Pair("0x1.ffep+8", 0x407ffe0000000000), Pair("0x1p+0", 0x3ff0000000000000), Pair("0x1.999999999999ap-4", 0x3fb999999999999a), Pair("0x1.9p+6", 0x4059000000000000), Pair("0x1.2d5ed1fe1da7bp+20", 0x4132d5ed1fe1da7b), Pair("-0x1.348eb851eb852p+10", 0xc09348eb851eb852uL), Pair("-0x1.33dcfe54a3803p-33", 0xbde33dcfe54a3803uL), Pair("0x1.0p0", 1.0.toRawBits().toULong()), Pair("0x0.0p0", 0.0.toRawBits().toULong()), Pair("-0x0.0p0", (-0.0).toRawBits().toULong()), Pair("0x1.999999999999ap-4", 0.1.toRawBits().toULong()), Pair("0x1.999999999998ap-4", (0.1 - 2.220446049250313E-16).toRawBits().toULong()), Pair("-0x1.999999999999ap-4", (-0.1).toRawBits().toULong()), Pair("-0x1.999999999998ap-4", (-0.1 + 2.220446049250313E-16).toRawBits().toULong()), Pair("0x0.8000000000001p-1022", 0x0008000000000001), Pair("0x0.123456789abcdp-1022", 0x000123456789abcd), Pair("0x0.0000000000002p-1022", 0x0000000000000002), Pair("nan", Double.NaN.toRawBits().toULong()), Pair("-nan", (-Double.NaN).toRawBits().toULong()), Pair("inf", Double.POSITIVE_INFINITY.toRawBits().toULong()), Pair("-inf", Double.NEGATIVE_INFINITY.toRawBits().toULong()))
        for ((s, exp) in checks) {
            println("parsing $s")
            val act = hf64(s).toRawBits().toULong()
            check(act == exp)
        }
    }

    @kotlin.test.Test
    public fun testF128() {
        val checks = listOf(Pair("0x.1234p+16", (F128.fromDouble(0x1234.toDouble())).toBits()), Pair("0x1.234p+12", (F128.fromDouble(0x1234.toDouble())).toBits()), Pair("0x12.34p+8", (F128.fromDouble(0x1234.toDouble())).toBits()), Pair("0x123.4p+4", (F128.fromDouble(0x1234.toDouble())).toBits()), Pair("0x1234p+0", (F128.fromDouble(0x1234.toDouble())).toBits()), Pair("0x1234.p+0", (F128.fromDouble(0x1234.toDouble())).toBits()), Pair("0x1234.0p+0", (F128.fromDouble(0x1234.toDouble())).toBits()), Pair("0x1.ffffffffffffffffffffffffffffp+16383", F128.MAX.toBits()), Pair("0x1.0p+1", (F128.fromDouble(2.0)).toBits()), Pair("0x1.0p+0", (F128.fromDouble(1.0)).toBits()), Pair("0x1.ffep+8", U128(0x4007ffe000000000uL, 0x0uL)), Pair("+0x1.ffep+8", U128(0x4007ffe000000000uL, 0x0uL)), Pair("0x1p+0", U128(0x3fff000000000000uL, 0x0uL)), Pair("0x1.999999999999999999999999999ap-4", U128(0x3ffb999999999999uL, 0x999999999999999auL)), Pair("0x1.9p+6", U128(0x4005900000000000uL, 0x0uL)), Pair("0x0.0p0", (F128.fromDouble(0.0)).toBits()), Pair("-0x0.0p0", (-F128.fromDouble(0.0)).toBits()), Pair("0x1.0p0", (F128.fromDouble(1.0)).toBits()), Pair("0x1.999999999999999999999999999ap-4", (F128.fromDouble(0.1)).toBits()), Pair("-0x1.999999999999999999999999999ap-4", (-F128.fromDouble(0.1)).toBits()), Pair("0x0.abcdef0123456789abcdef012345p-16382", U128(0xabcdef012345uL, 0x6789abcdef012345uL)), Pair("0x1p-16494", 0x00000000000000000000000000000001), Pair("nan", F128.NAN.toBits()), Pair("-nan", (-F128.NAN).toBits()), Pair("inf", F128.INFINITY.toBits()), Pair("-inf", F128.NEG_INFINITY.toBits()))
        for ((s, exp) in checks) {
            println("parsing $s")
            val act = hf128(s).toBits()
            check(act == exp)
        }
    }

    @kotlin.test.Test
    public fun testMacros() {
        check(hf16("0x1.ffp+8").toBits() == 0x5ffcu.toUShort())
        check(hf32("0x1.ffep+8").toBits() == 0x43fff000u)
        check(hf64("0x1.ffep+8").toBits() == 0x407ffe0000000000uL)
        check(hf128("0x1.ffep+8").toBits() == U128(0x4007ffe000000000uL, 0x0uL))
    }
}

public class HexFloatTestsPanickingInlineTests {
    @kotlin.test.Test
    public fun testF16AlmostExtraPrecision() {
        // Exact maximum precision allowed
        hf16("0x1.ffcp+0")
    }

    @kotlin.test.Test
    public fun testF16ExtraPrecision() {
        val panic = kotlin.test.assertFails(block = fun() {
            // One bit more than the above.
            hf16("0x1.ffdp+0")
        })
        kotlin.test.assertTrue(panic.message?.contains("the value is too precise") == true)
    }

    @kotlin.test.Test
    public fun testF16Overflow() {
        val panic = kotlin.test.assertFails(block = fun() {
            // One bit more than the above.
            hf16("0x1p+16")
        })
        kotlin.test.assertTrue(panic.message?.contains("the value is too huge") == true)
    }

    @kotlin.test.Test
    public fun testF16Tiniest() {
        val x = hf16("0x1.p-24")
        val y = hf16("0x0.001p-12")
        val z = hf16("0x0.8p-23")
        check(x == y)
        check(x == z)
    }

    @kotlin.test.Test
    public fun testF16TooTiny() {
        val panic = kotlin.test.assertFails(block = fun() {
            hf16("0x1.p-25")
        })
        kotlin.test.assertTrue(panic.message?.contains("the value is too tiny") == true)
    }

    @kotlin.test.Test
    public fun testF16AlsoTooTiny() {
        val panic = kotlin.test.assertFails(block = fun() {
            hf16("0x0.8p-24")
        })
        kotlin.test.assertTrue(panic.message?.contains("the value is too tiny") == true)
    }

    @kotlin.test.Test
    public fun testF16AgainTooTiny() {
        val panic = kotlin.test.assertFails(block = fun() {
            hf16("0x0.001p-13")
        })
        kotlin.test.assertTrue(panic.message?.contains("the value is too tiny") == true)
    }

    @kotlin.test.Test
    public fun testF32AlmostExtraPrecision() {
        // Exact maximum precision allowed
        hf32("0x1.abcdeep+0")
    }

    @kotlin.test.Test
    public fun testF32ExtraPrecision2() {
        kotlin.test.assertFails(block = fun() {
            // One bit more than the above.
            hf32("0x1.ffffffp+127")
        })
    }

    @kotlin.test.Test
    public fun testF32Overflow() {
        val panic = kotlin.test.assertFails(block = fun() {
            // One bit more than the above.
            hf32("0x1p+128")
        })
        kotlin.test.assertTrue(panic.message?.contains("the value is too huge") == true)
    }

    @kotlin.test.Test
    public fun testF32ExtraPrecision() {
        val panic = kotlin.test.assertFails(block = fun() {
            // One bit more than the above.
            hf32("0x1.abcdefp+0")
        })
        kotlin.test.assertTrue(panic.message?.contains("the value is too precise") == true)
    }

    @kotlin.test.Test
    public fun testF32Tiniest() {
        val x = hf32("0x1.p-149")
        val y = hf32("0x0.0000000000000001p-85")
        val z = hf32("0x0.8p-148")
        check(x == y)
        check(x == z)
    }

    @kotlin.test.Test
    public fun testF32TooTiny() {
        val panic = kotlin.test.assertFails(block = fun() {
            hf32("0x1.p-150")
        })
        kotlin.test.assertTrue(panic.message?.contains("the value is too tiny") == true)
    }

    @kotlin.test.Test
    public fun testF32AlsoTooTiny() {
        val panic = kotlin.test.assertFails(block = fun() {
            hf32("0x0.8p-149")
        })
        kotlin.test.assertTrue(panic.message?.contains("the value is too tiny") == true)
    }

    @kotlin.test.Test
    public fun testF32AgainTooTiny() {
        val panic = kotlin.test.assertFails(block = fun() {
            hf32("0x0.0000000000000001p-86")
        })
        kotlin.test.assertTrue(panic.message?.contains("the value is too tiny") == true)
    }

    @kotlin.test.Test
    public fun testF64AlmostExtraPrecision() {
        // Exact maximum precision allowed
        hf64("0x1.abcdabcdabcdfp+0")
    }

    @kotlin.test.Test
    public fun testF64ExtraPrecision() {
        val panic = kotlin.test.assertFails(block = fun() {
            // One bit more than the above.
            hf64("0x1.abcdabcdabcdf8p+0")
        })
        kotlin.test.assertTrue(panic.message?.contains("the value is too precise") == true)
    }

    @kotlin.test.Test
    public fun testF128AlmostExtraPrecision() {
        // Exact maximum precision allowed
        hf128("0x1.ffffffffffffffffffffffffffffp+16383")
    }

    @kotlin.test.Test
    public fun testF128ExtraPrecision() {
        val panic = kotlin.test.assertFails(block = fun() {
            // Just below the maximum finite.
            hf128("0x1.fffffffffffffffffffffffffffe8p+16383")
        })
        kotlin.test.assertTrue(panic.message?.contains("the value is too precise") == true)
    }

    @kotlin.test.Test
    public fun testF128ExtraPrecisionOverflow() {
        val panic = kotlin.test.assertFails(block = fun() {
            // One bit more than the above. Should overflow.
            hf128("0x1.ffffffffffffffffffffffffffff8p+16383")
        })
        kotlin.test.assertTrue(panic.message?.contains("the value is too huge") == true)
    }

    @kotlin.test.Test
    public fun testF128Overflow() {
        val panic = kotlin.test.assertFails(block = fun() {
            // One bit more than the above.
            hf128("0x1p+16384")
        })
        kotlin.test.assertTrue(panic.message?.contains("the value is too huge") == true)
    }

    @kotlin.test.Test
    public fun testF128Tiniest() {
        val x = hf128("0x1.p-16494")
        val y = hf128("0x0.0000000000000001p-16430")
        val z = hf128("0x0.8p-16493")
        check(x == y)
        check(x == z)
    }

    @kotlin.test.Test
    public fun testF128TooTiny() {
        val panic = kotlin.test.assertFails(block = fun() {
            hf128("0x1.p-16495")
        })
        kotlin.test.assertTrue(panic.message?.contains("the value is too tiny") == true)
    }

    @kotlin.test.Test
    public fun testF128AgainTooTiny() {
        val panic = kotlin.test.assertFails(block = fun() {
            hf128("0x0.0000000000000001p-16431")
        })
        kotlin.test.assertTrue(panic.message?.contains("the value is too tiny") == true)
    }

    @kotlin.test.Test
    public fun testF128AlsoTooTiny() {
        val panic = kotlin.test.assertFails(block = fun() {
            hf128("0x0.8p-16494")
        })
        kotlin.test.assertTrue(panic.message?.contains("the value is too tiny") == true)
    }
}

public class HexFloatPrintTestsInlineTests {
    @kotlin.test.Test
    public fun testF16() {
        // Exhaustively check that `f16` roundtrips.
        for (x in 0..UShort.MAX_VALUE) {
            val f = F16.fromBits(x.toUShort())
            val s = "${Hex(f)}"
            val s1 = canonicalizeSnanStr(s)
            val fromS = hf16(s1)
            if (f.isNaN() && fromS.isNaN()) {
                continue
            }
            check(f.toBits() == fromS.toBits())
        }
    }

    @kotlin.test.Test
    public fun testF16ToF32() {
        // Exhaustively check that these are equivalent for all `f16`:
        // - `f16 -> Float`
        // - `f16 -> String -> Float`
        // - `f16 -> Float -> String -> Float`
        // - `f16 -> Float -> String -> f16 -> Float`
        for (x in 0..UShort.MAX_VALUE) {
            val f16 = F16.fromBits(x.toUShort())
            val s16 = "${Hex(f16)}"
            val s161 = canonicalizeSnanStr(s16)
            val f32 = f16.toFloat()
            val s32 = "${Hex(f32)}"
            val s321 = canonicalizeSnanStr(s32)
            val a = hf32(s161)
            val b = hf32(s321)
            val c = hf16(s321)
            if (f32.isNaN() && a.isNaN() && b.isNaN() && c.isNaN()) {
                continue
            }
            check(f32.toRawBits().toUInt() == a.toRawBits().toUInt())
            check(f32.toRawBits().toUInt() == b.toRawBits().toUInt())
            check(f32.toRawBits().toUInt() == (c.toFloat()).toRawBits().toUInt())
        }
    }

    @kotlin.test.Test
    public fun spotChecks() {
        check(Hex(kotlin.Float.MAX_VALUE).toString() == "+0x1.fffffep+127")
        check(Hex(Double.MAX_VALUE).toString() == "+0x1.fffffffffffffp+1023")
        check(Hex((-kotlin.Float.MAX_VALUE)).toString() == "-0x1.fffffep+127")
        check(Hex((-Double.MAX_VALUE)).toString() == "-0x1.fffffffffffffp+1023")
        check(Hex(kotlin.Float.ZERO).toString() == "+0x0p+0")
        check(Hex(Double.ZERO).toString() == "+0x0p+0")
        check(Hex(kotlin.Float.NEG_ZERO).toString() == "-0x0p+0")
        check(Hex(Double.NEG_ZERO).toString() == "-0x0p+0")
        check(Hex(kotlin.Float.NaN).toString() == "+qNaN(0x000000)")
        check(Hex(Double.NaN).toString() == "+qNaN(0x0000000000000)")
        check(Hex(kotlin.Float.NEG_NAN).toString() == "-qNaN(0x000000)")
        check(Hex(Double.NEG_NAN).toString() == "-qNaN(0x0000000000000)")
        if (!false) {
            // Note(rust-lang/rust#115567): calls quiet the sNaN
            check(Hex(kotlin.Float.SNAN).toString() == "+sNaN(0x200000)")
            check(Hex(Double.SNAN).toString() == "+sNaN(0x4000000000000)")
            check(Hex(kotlin.Float.NEG_SNAN).toString() == "-sNaN(0x200000)")
            check(Hex(Double.NEG_SNAN).toString() == "-sNaN(0x4000000000000)")
            check(Hex(kotlin.Float.fromBits(UInt.MAX_VALUE.toInt())).toString() == "-qNaN(0x3fffff)")
            check(Hex(Double.fromBits(ULong.MAX_VALUE.toLong())).toString() == "-qNaN(0x7ffffffffffff)")
        }
        check(Hex(kotlin.Float.POSITIVE_INFINITY).toString() == "+inf")
        check(Hex(Double.POSITIVE_INFINITY).toString() == "+inf")
        check(Hex(kotlin.Float.NEGATIVE_INFINITY).toString() == "-inf")
        check(Hex(Double.NEGATIVE_INFINITY).toString() == "-inf")
        run {
            check(Hex(F16.MAX).toString() == "+0x1.ffcp+15")
            check(Hex(F16.MIN).toString() == "-0x1.ffcp+15")
            check(Hex(F16.ZERO).toString() == "+0x0p+0")
            check(Hex(F16.NEG_ZERO).toString() == "-0x0p+0")
            check(Hex(F16.NAN).toString() == "+qNaN(0x000)")
            check(Hex(F16.SNAN).toString() == "+sNaN(0x100)")
            check(Hex(F16.NEG_NAN).toString() == "-qNaN(0x000)")
            check(Hex(F16.NEG_SNAN).toString() == "-sNaN(0x100)")
            check(Hex(F16.fromBits(UShort.MAX_VALUE)).toString() == "-qNaN(0x1ff)")
            check(Hex(F16.INFINITY).toString() == "+inf")
            check(Hex(F16.NEG_INFINITY).toString() == "-inf")
        }
        run {
            check(Hex(F128.MAX).toString() == "+0x1.ffffffffffffffffffffffffffffp+16383")
            check(Hex(F128.MIN).toString() == "-0x1.ffffffffffffffffffffffffffffp+16383")
            check(Hex(F128.ZERO).toString() == "+0x0p+0")
            check(Hex(F128.NEG_ZERO).toString() == "-0x0p+0")
            check(Hex(F128.NAN).toString() == "+qNaN(0x0000000000000000000000000000)")
            check(Hex(F128.SNAN).toString() == "+sNaN(0x4000000000000000000000000000)")
            check(Hex(F128.NEG_NAN).toString() == "-qNaN(0x0000000000000000000000000000)")
            check(Hex(F128.NEG_SNAN).toString() == "-sNaN(0x4000000000000000000000000000)")
            check(Hex(F128.fromBits(U128.MAX_VALUE)).toString() == "-qNaN(0x7fffffffffffffffffffffffffff)")
            check(Hex(F128.INFINITY).toString() == "+inf")
            check(Hex(F128.NEG_INFINITY).toString() == "-inf")
        }
    }
}
