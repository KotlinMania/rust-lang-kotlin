// port-lint: tests math/support/float_traits.rs
package io.github.kotlinmania.rustlang.libm.math.support.floattraits

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.Hex
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hf32
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hf64

public class FloatTraitsInlineTests {
    @kotlin.test.Test
    public fun checkF16() {
        // Constants
        check(F16.EXP_SAT == F16.fromDouble((0x1F).toDouble()))
        check(F16.EXP_BIAS == F16.fromFloat(15.0f))
        check(F16.EXP_MAX == F16.fromFloat(15.0f))
        check(F16.EXP_MIN == F16.fromDouble((-F16.fromFloat(14.0f)).toDouble()))
        check(F16.EXP_MIN_SUBNORM == F16.fromDouble((-F16.fromFloat(24.0f)).toDouble()))
        run {
            val l = F16.NAN
            val r = F16.NAN
            check(biteq(l, r)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l.toBits()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
        }
        // Value of NAN and FLT16_SNAN in C. We don't strictly need to match up, but it is good to
        // be aware if there are platforms where we don't.
        if (MIPS_NAN) {
            run {
                val l1 = F16.NAN
                val r1 = F16.fromBits(0x7dffu.toUShort())
                check(biteq(l1, r1)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l1.toBits()).toString() + " " + (Hex(l1)).toString() + ")\nr: " + (r).toString() + " (" + (r1.toBits()).toString() + " " + (Hex(r1)).toString() + ")" }
            }
            run {
                val l2 = F16.SNAN
                val r2 = F16.fromBits(0x7fffu.toUShort())
                check(biteq(l2, r2)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l2.toBits()).toString() + " " + (Hex(l2)).toString() + ")\nr: " + (r).toString() + " (" + (r2.toBits()).toString() + " " + (Hex(r2)).toString() + ")" }
            }
            run {
                val l3 = F16.QSNAN
                val r3 = F16.fromBits(0x7dffu.toUShort())
                check(biteq(l3, r3)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l3.toBits()).toString() + " " + (Hex(l3)).toString() + ")\nr: " + (r).toString() + " (" + (r3.toBits()).toString() + " " + (Hex(r3)).toString() + ")" }
            }
        } else {
            run {
                val l4 = F16.NAN
                val r4 = F16.fromBits(0x7e00u.toUShort())
                check(biteq(l4, r4)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l4.toBits()).toString() + " " + (Hex(l4)).toString() + ")\nr: " + (r).toString() + " (" + (r4.toBits()).toString() + " " + (Hex(r4)).toString() + ")" }
            }
            run {
                val l5 = F16.SNAN
                val r5 = F16.fromBits(0x7d00u.toUShort())
                check(biteq(l5, r5)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l5.toBits()).toString() + " " + (Hex(l5)).toString() + ")\nr: " + (r).toString() + " (" + (r5.toBits()).toString() + " " + (Hex(r5)).toString() + ")" }
            }
            run {
                val l6 = F16.QSNAN
                val r6 = F16.fromBits(0x7f00u.toUShort())
                check(biteq(l6, r6)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l6.toBits()).toString() + " " + (Hex(l6)).toString() + ")\nr: " + (r).toString() + " (" + (r6.toBits()).toString() + " " + (Hex(r6)).toString() + ")" }
            }
        }
        check(F16.NAN.isQnan())
        check(F16.SNAN.isSnan())
        // `expUnbiased`
        check(F16.FRAC_PI_2.expUnbiased() == 0)
        check((F16.fromFloat(1.0f) / F16.fromFloat(2.0f)).expUnbiased() == -1)
        check(F16.MAX.expUnbiased() == 15)
        check(F16.MIN.expUnbiased() == 15)
        check(F16.MIN_POSITIVE.expUnbiased() == -14)
        // This is a convenience method and not ldexp, `expUnbiased` does not return correct
        // results for zero and subnormals.
        check(F16.ZERO.expUnbiased() == -15)
        check(F16.fromBits(0x1u.toUShort()).expUnbiased() == -15)
        check(F16.MIN_POSITIVE == F16.MIN_POSITIVE_NORMAL)
        run {
            val l7 = F16.fromParts(true, F16.EXP_BIAS, 0)
            val r7 = -F16.fromFloat(1.0f)
            check(biteq(l7, r7)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l7.toBits()).toString() + " " + (Hex(l7)).toString() + ")\nr: " + (r).toString() + " (" + (r7.toBits()).toString() + " " + (Hex(r7)).toString() + ")" }
        }
        run {
            val l8 = F16.fromParts(false, 0, 1)
            val r8 = F16.fromBits(0x1u.toUShort())
            check(biteq(l8, r8)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l8.toBits()).toString() + " " + (Hex(l8)).toString() + ")\nr: " + (r).toString() + " (" + (r8.toBits()).toString() + " " + (Hex(r8)).toString() + ")" }
        }
    }

    @kotlin.test.Test
    public fun checkF32() {
        // Constants
        check(kotlin.Float.EXP_SAT == (0xFF).toFloat())
        check(kotlin.Float.EXP_BIAS == 127.0f.toFloat())
        check(kotlin.Float.EXP_MAX == 127.0f.toFloat())
        check(kotlin.Float.EXP_MIN == (-126.0f).toFloat())
        check(kotlin.Float.EXP_MIN_SUBNORM == (-149.0f).toFloat())
        run {
            val l = kotlin.Float.NaN
            val r = kotlin.Float.NAN
            check(biteq(l, r)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l.toRawBits().toUInt()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
        }
        // Value of NAN and FLT_SNAN in C. We don't strictly need to match up, but it is good to
        // be aware if there are platforms where we don't.
        if (MIPS_NAN) {
            run {
                val l1 = kotlin.Float.NAN
                val r1 = kotlin.Float.fromBits(0x7fbfffffu.toInt())
                check(biteq(l1, r1)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l1.toBits()).toString() + " " + (Hex(l1)).toString() + ")\nr: " + (r).toString() + " (" + (r1.toRawBits().toUInt()).toString() + " " + (Hex(r1)).toString() + ")" }
            }
            run {
                val l2 = kotlin.Float.SNAN
                val r2 = kotlin.Float.fromBits(0x7fffffffu.toInt())
                check(biteq(l2, r2)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l2.toRawBits().toUInt()).toString() + " " + (Hex(l2)).toString() + ")\nr: " + (r).toString() + " (" + (r2.toRawBits().toUInt()).toString() + " " + (Hex(r2)).toString() + ")" }
            }
            run {
                val l3 = kotlin.Float.QSNAN
                val r3 = kotlin.Float.fromBits(0x7fbfffffu.toInt())
                check(biteq(l3, r3)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l3.toRawBits().toUInt()).toString() + " " + (Hex(l3)).toString() + ")\nr: " + (r).toString() + " (" + (r3.toRawBits().toUInt()).toString() + " " + (Hex(r3)).toString() + ")" }
            }
        } else {
            run {
                val l4 = kotlin.Float.NaN
                val r4 = kotlin.Float.fromBits(0x7fc00000u.toInt())
                check(biteq(l4, r4)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l4.toRawBits().toUInt()).toString() + " " + (Hex(l4)).toString() + ")\nr: " + (r).toString() + " (" + (r4.toRawBits().toUInt()).toString() + " " + (Hex(r4)).toString() + ")" }
            }
            run {
                val l5 = kotlin.Float.SNAN
                val r5 = kotlin.Float.fromBits(0x7fa00000u.toInt())
                check(biteq(l5, r5)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l5.toRawBits().toUInt()).toString() + " " + (Hex(l5)).toString() + ")\nr: " + (r).toString() + " (" + (r5.toRawBits().toUInt()).toString() + " " + (Hex(r5)).toString() + ")" }
            }
            run {
                val l6 = kotlin.Float.QSNAN
                val r6 = kotlin.Float.fromBits(0x7fe00000u.toInt())
                check(biteq(l6, r6)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l6.toRawBits().toUInt()).toString() + " " + (Hex(l6)).toString() + ")\nr: " + (r).toString() + " (" + (r6.toRawBits().toUInt()).toString() + " " + (Hex(r6)).toString() + ")" }
            }
        }
        check(kotlin.Float.NAN.isQnan())
        // Note(rust-lang/rust#115567): x87 use in `isSnan` quiets the sNaN
        if (!false) {
            check(kotlin.Float.SNAN.isSnan())
        }
        // `expUnbiased`
        check(kotlin.Float.FRAC_PI_2.expUnbiased() == 0)
        check((1.0f / 2.0f).expUnbiased() == -1)
        check(kotlin.Float.MAX_VALUE.expUnbiased() == 127)
        check((-kotlin.Float.MAX_VALUE).expUnbiased() == 127)
        check(1.17549435E-38f.expUnbiased() == -126)
        // This is a convenience method and not ldexp, `expUnbiased` does not return correct
        // results for zero and subnormals.
        check(kotlin.Float.ZERO.expUnbiased() == -127)
        check(kotlin.Float.fromBits(0x1u.toInt()).expUnbiased() == -127)
        check(1.17549435E-38f == kotlin.Float.MIN_POSITIVE_NORMAL)
        run {
            val l7 = kotlin.Float.fromParts(true, kotlin.Float.EXP_BIAS, 0)
            val r7 = -1.0f
            check(biteq(l7, r7)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l7.toBits()).toString() + " " + (Hex(l7)).toString() + ")\nr: " + (r).toString() + " (" + (r7.toRawBits().toUInt()).toString() + " " + (Hex(r7)).toString() + ")" }
        }
        run {
            val l8 = kotlin.Float.fromParts(false, 10.0f + kotlin.Float.EXP_BIAS, 0)
            val r8 = run {
                val X: kotlin.Float = hf32("0x1p10")
                X
            }
            check(biteq(l8, r8)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l8.toBits()).toString() + " " + (Hex(l8)).toString() + ")\nr: " + (r).toString() + " (" + (r8.toBits()).toString() + " " + (Hex(r8)).toString() + ")" }
        }
        run {
            val l9 = kotlin.Float.fromParts(false, 0, 1)
            val r9 = kotlin.Float.fromBits(0x1u.toInt())
            check(biteq(l9, r9)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l9.toBits()).toString() + " " + (Hex(l9)).toString() + ")\nr: " + (r).toString() + " (" + (r9.toRawBits().toUInt()).toString() + " " + (Hex(r9)).toString() + ")" }
        }
    }

    @kotlin.test.Test
    public fun checkF64() {
        // Constants
        check(Double.EXP_SAT == (0x7FF).toDouble())
        check(Double.EXP_BIAS == 1023.0.toDouble())
        check(Double.EXP_MAX == 1023.0.toDouble())
        check(Double.EXP_MIN == (-1022.0).toDouble())
        check(Double.EXP_MIN_SUBNORM == (-1074.0).toDouble())
        run {
            val l = Double.NaN
            val r = Double.NAN
            check(biteq(l, r)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l.toRawBits().toULong()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
        }
        // Value of NAN and DBL_SNAN in C. We don't strictly need to match up, but it is good to
        // be aware if there are platforms where we don't.
        if (MIPS_NAN) {
            run {
                val l1 = Double.NAN
                val r1 = Double.fromBits(0x7ff7ffffffffffffuL.toLong())
                check(biteq(l1, r1)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l1.toBits()).toString() + " " + (Hex(l1)).toString() + ")\nr: " + (r).toString() + " (" + (r1.toRawBits().toULong()).toString() + " " + (Hex(r1)).toString() + ")" }
            }
            run {
                val l2 = Double.SNAN
                val r2 = Double.fromBits(0x7fffffffffffffffuL.toLong())
                check(biteq(l2, r2)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l2.toRawBits().toULong()).toString() + " " + (Hex(l2)).toString() + ")\nr: " + (r).toString() + " (" + (r2.toRawBits().toULong()).toString() + " " + (Hex(r2)).toString() + ")" }
            }
            run {
                val l3 = Double.QSNAN
                val r3 = Double.fromBits(0x7ff7ffffffffffffuL.toLong())
                check(biteq(l3, r3)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l3.toRawBits().toULong()).toString() + " " + (Hex(l3)).toString() + ")\nr: " + (r).toString() + " (" + (r3.toRawBits().toULong()).toString() + " " + (Hex(r3)).toString() + ")" }
            }
        } else {
            run {
                val l4 = Double.NaN
                val r4 = Double.fromBits(0x7ff8000000000000uL.toLong())
                check(biteq(l4, r4)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l4.toRawBits().toULong()).toString() + " " + (Hex(l4)).toString() + ")\nr: " + (r).toString() + " (" + (r4.toRawBits().toULong()).toString() + " " + (Hex(r4)).toString() + ")" }
            }
            run {
                val l5 = Double.SNAN
                val r5 = Double.fromBits(0x7ff4000000000000uL.toLong())
                check(biteq(l5, r5)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l5.toRawBits().toULong()).toString() + " " + (Hex(l5)).toString() + ")\nr: " + (r).toString() + " (" + (r5.toRawBits().toULong()).toString() + " " + (Hex(r5)).toString() + ")" }
            }
            run {
                val l6 = Double.QSNAN
                val r6 = Double.fromBits(0x7ffc000000000000uL.toLong())
                check(biteq(l6, r6)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l6.toRawBits().toULong()).toString() + " " + (Hex(l6)).toString() + ")\nr: " + (r).toString() + " (" + (r6.toRawBits().toULong()).toString() + " " + (Hex(r6)).toString() + ")" }
            }
        }
        check(Double.NAN.isQnan())
        // Note(rust-lang/rust#115567): x87 use in `isSnan` quiets the sNaN
        if (!false) {
            check(Double.SNAN.isSnan())
        }
        // `expUnbiased`
        check(Double.FRAC_PI_2.expUnbiased() == 0)
        check((1.0 / 2.0).expUnbiased() == -1)
        check(Double.MAX_VALUE.expUnbiased() == 1023)
        check((-Double.MAX_VALUE).expUnbiased() == 1023)
        check(2.2250738585072014E-308.expUnbiased() == -1022)
        // This is a convenience method and not ldexp, `expUnbiased` does not return correct
        // results for zero and subnormals.
        check(Double.ZERO.expUnbiased() == -1023)
        check(Double.fromBits(0x1uL.toLong()).expUnbiased() == -1023)
        check(2.2250738585072014E-308 == Double.MIN_POSITIVE_NORMAL)
        run {
            val l7 = Double.fromParts(true, Double.EXP_BIAS, 0)
            val r7 = -1.0
            check(biteq(l7, r7)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l7.toBits()).toString() + " " + (Hex(l7)).toString() + ")\nr: " + (r).toString() + " (" + (r7.toRawBits().toULong()).toString() + " " + (Hex(r7)).toString() + ")" }
        }
        run {
            val l8 = Double.fromParts(false, 10.0 + Double.EXP_BIAS, 0)
            val r8 = run {
                val X: Double = hf64("0x1p10")
                X
            }
            check(biteq(l8, r8)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l8.toBits()).toString() + " " + (Hex(l8)).toString() + ")\nr: " + (r).toString() + " (" + (r8.toBits()).toString() + " " + (Hex(r8)).toString() + ")" }
        }
        run {
            val l9 = Double.fromParts(false, 0, 1)
            val r9 = Double.fromBits(0x1uL.toLong())
            check(biteq(l9, r9)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l9.toBits()).toString() + " " + (Hex(l9)).toString() + ")\nr: " + (r).toString() + " (" + (r9.toRawBits().toULong()).toString() + " " + (Hex(r9)).toString() + ")" }
        }
    }

    @kotlin.test.Test
    public fun checkF128() {
        // Constants
        check(F128.EXP_SAT == F128.fromDouble((0x7FFF).toDouble()))
        check(F128.EXP_BIAS == F128.fromDouble(16383.0))
        check(F128.EXP_MAX == F128.fromDouble(16383.0))
        check(F128.EXP_MIN == F128.fromDouble((-F128.fromDouble(16382.0)).toDouble()))
        check(F128.EXP_MIN_SUBNORM == F128.fromDouble((-F128.fromDouble(16494.0)).toDouble()))
        run {
            val l = F128.NAN
            val r = F128.NAN
            check(biteq(l, r)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l.toBits()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
        }
        // Value of NAN and FLT128_SNAN in C. We don't strictly need to match up, but it is good to
        // be aware if there are platforms where we don't.
        if (MIPS_NAN) {
            run {
                val l1 = F128.NAN
                val r1 = F128.fromBits(U128(0x7fff7fffffffffffuL, 0xffffffffffffffffuL))
                check(biteq(l1, r1)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l1.toBits()).toString() + " " + (Hex(l1)).toString() + ")\nr: " + (r).toString() + " (" + (r1.toBits()).toString() + " " + (Hex(r1)).toString() + ")" }
            }
            run {
                val l2 = F128.SNAN
                val r2 = F128.fromBits(U128(0x7fffffffffffffffuL, 0xffffffffffffffffuL))
                check(biteq(l2, r2)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l2.toBits()).toString() + " " + (Hex(l2)).toString() + ")\nr: " + (r).toString() + " (" + (r2.toBits()).toString() + " " + (Hex(r2)).toString() + ")" }
            }
            run {
                val l3 = F128.QSNAN
                val r3 = F128.fromBits(U128(0x7fff7fffffffffffuL, 0xffffffffffffffffuL))
                check(biteq(l3, r3)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l3.toBits()).toString() + " " + (Hex(l3)).toString() + ")\nr: " + (r).toString() + " (" + (r3.toBits()).toString() + " " + (Hex(r3)).toString() + ")" }
            }
        } else {
            run {
                val l4 = F128.NAN
                val r4 = F128.fromBits(U128(0x7fff800000000000uL, 0x0uL))
                check(biteq(l4, r4)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l4.toBits()).toString() + " " + (Hex(l4)).toString() + ")\nr: " + (r).toString() + " (" + (r4.toBits()).toString() + " " + (Hex(r4)).toString() + ")" }
            }
            run {
                val l5 = F128.SNAN
                val r5 = F128.fromBits(U128(0x7fff400000000000uL, 0x0uL))
                check(biteq(l5, r5)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l5.toBits()).toString() + " " + (Hex(l5)).toString() + ")\nr: " + (r).toString() + " (" + (r5.toBits()).toString() + " " + (Hex(r5)).toString() + ")" }
            }
            run {
                val l6 = F128.QSNAN
                val r6 = F128.fromBits(U128(0x7fffc00000000000uL, 0x0uL))
                check(biteq(l6, r6)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l6.toBits()).toString() + " " + (Hex(l6)).toString() + ")\nr: " + (r).toString() + " (" + (r6.toBits()).toString() + " " + (Hex(r6)).toString() + ")" }
            }
        }
        check(F128.NAN.isQnan())
        check(F128.SNAN.isSnan())
        // `expUnbiased`
        check(F128.FRAC_PI_2.expUnbiased() == 0)
        check((F128.fromDouble(1.0) / F128.fromDouble(2.0)).expUnbiased() == -1)
        check(F128.MAX.expUnbiased() == 16383)
        check(F128.MIN.expUnbiased() == 16383)
        check(F128.MIN_POSITIVE.expUnbiased() == -16382)
        // This is a convenience method and not ldexp, `expUnbiased` does not return correct
        // results for zero and subnormals.
        check(F128.ZERO.expUnbiased() == -16383)
        check(F128.fromBits(U128(0x0uL, 0x1uL)).expUnbiased() == -16383)
        check(F128.MIN_POSITIVE == F128.MIN_POSITIVE_NORMAL)
        run {
            val l7 = F128.fromParts(true, F128.EXP_BIAS, 0)
            val r7 = -F128.fromDouble(1.0)
            check(biteq(l7, r7)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l7.toBits()).toString() + " " + (Hex(l7)).toString() + ")\nr: " + (r).toString() + " (" + (r7.toBits()).toString() + " " + (Hex(r7)).toString() + ")" }
        }
        run {
            val l8 = F128.fromParts(false, 0, 1)
            val r8 = F128.fromBits(U128(0x0uL, 0x1uL))
            check(biteq(l8, r8)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l8.toBits()).toString() + " " + (Hex(l8)).toString() + ")\nr: " + (r).toString() + " (" + (r8.toBits()).toString() + " " + (Hex(r8)).toString() + ")" }
        }
    }
}
