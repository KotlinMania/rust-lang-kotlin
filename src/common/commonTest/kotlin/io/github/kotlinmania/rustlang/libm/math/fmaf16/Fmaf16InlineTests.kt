// port-lint: tests math/fmaf16.rs
package io.github.kotlinmania.rustlang.libm.math.fmaf16

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.hexfmt.Hex
import io.github.kotlinmania.rustlang.libm.math.support.inttraits.Int

public class Fmaf16InlineTests {
    @kotlin.test.Test
    public fun testFromFixed() {
        // Move 1.xx... floating point to 1.xx... fixed point
        val shiftToOne = { x: UShort -> ULong.from(x) shl (FIXED_FRAC_BITS.toInt() - F16.SIG_BITS.toInt()).toInt() }
        val topSig = F16.IMPLICIT_BIT
        val maxSig = F16.IMPLICIT_BIT or F16.SIG_MASK
        // Basic values
        val one = shiftToOne(topSig)
        val max = shiftToOne(maxSig) shl 15
        val inf = shiftToOne(maxSig + F16.fromFloat(1.0f)) shl 15
        val minNorm = one shr 14
        val maxSub = shiftToOne(F16.SIG_MASK) shr 14
        val minSub = shiftToOne(1) shr 14
        run {
            val l = f16FromU2143(0uL)
            val r = F16.fromFloat(0.0f)
            check(l.biteq(r)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l.toBits()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
        }
        run {
            val l1 = f16FromU2143(one)
            val r1 = F16.fromFloat(1.0f)
            check(l1.biteq(r1)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l1.toBits()).toString() + " " + (Hex(l1)).toString() + ")\nr: " + (r).toString() + " (" + (r1.toBits()).toString() + " " + (Hex(r1)).toString() + ")" }
        }
        run {
            val l2 = f16FromU2143(max)
            val r2 = F16.MAX
            check(l2.biteq(r2)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l2.toBits()).toString() + " " + (Hex(l2)).toString() + ")\nr: " + (r).toString() + " (" + (r2.toBits()).toString() + " " + (Hex(r2)).toString() + ")" }
        }
        run {
            val l3 = f16FromU2143(inf)
            val r3 = F16.INFINITY
            check(l3.biteq(r3)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l3.toBits()).toString() + " " + (Hex(l3)).toString() + ")\nr: " + (r).toString() + " (" + (r3.toBits()).toString() + " " + (Hex(r3)).toString() + ")" }
        }
        run {
            val l4 = f16FromU2143(minNorm)
            val r4 = F16.MIN_POSITIVE_NORMAL
            check(l4.biteq(r4)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l4.toBits()).toString() + " " + (Hex(l4)).toString() + ")\nr: " + (r).toString() + " (" + (r4.toBits()).toString() + " " + (Hex(r4)).toString() + ")" }
        }
        run {
            val l5 = f16FromU2143(maxSub)
            val r5 = F16.fromBits(F16.SIG_MASK.toUShort())
            check(l5.biteq(r5)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l5.toBits()).toString() + " " + (Hex(l5)).toString() + ")\nr: " + (r).toString() + " (" + (r5.toBits()).toString() + " " + (Hex(r5)).toString() + ")" }
        }
        run {
            val l6 = f16FromU2143(minSub)
            val r6 = F16.MIN_POSITIVE_SUBNORMAL
            check(l6.biteq(r6)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l6.toBits()).toString() + " " + (Hex(l6)).toString() + ")\nr: " + (r).toString() + " (" + (r6.toBits()).toString() + " " + (Hex(r6)).toString() + ")" }
        }
        // Masks centered around 1 to add a rounding
        val maskR = shiftToOne(0x1) shr 1
        // round bit
        val maskRg = shiftToOne(0x3) shr 2
        // round + guard
        val maskRgs = shiftToOne(0x7) shr 3
        // round + guard + sticky
        val maskRs = shiftToOne(0x5) shr 3
        // round + sticky
        val maskRs2 = shiftToOne(0x81) shr 8
        // round + part of sticky
        val signedShift =
            { value: ULong, shift: Int ->
                if (shift >= 0) {
                    value shl shift.toInt()
                } else {
                    value shr (-shift).toInt()
                }
            }
        val checkRound =
            { fixed: ULong, shift: Int, lsbSet: Boolean, down: F16, up: F16 ->
                // Masks that will cause rounding down
                val mdown =
                    if (lsbSet) {
                        listOf(0)
                    } else {
                        listOf(0, maskR)
                    }
                // Masks that will cause rounding up
                val mup =
                    if (lsbSet) {
                        listOf(maskR, maskRg, maskRgs, maskRs, maskRs2)
                    } else {
                        listOf(maskRg, maskRgs, maskRs, maskRs2)
                    }
                for ((i, mask) in mdown.iter().mapIndexed { index, item -> Pair(index, item) }) {
                    val bits7 = fixed or signedShift(mask, shift)
                    run {
                        val l7 = f16FromU2143(bits7)
                        val r7 = down
                        check(l7.biteq(r7)) { ("$bits $i").toString() + "\nl: " + (l).toString() + " (" + (l7.toBits()).toString() + " " + (Hex(l7)).toString() + ")\nr: " + (r).toString() + " (" + (r7.toBits()).toString() + " " + (Hex(r7)).toString() + ")" }
                    }
                }
                for ((i1, mask1) in mup.iter().mapIndexed { index, item -> Pair(index, item) }) {
                    val bits9 = fixed or signedShift(mask1, shift)
                    run {
                        val l8 = f16FromU2143(bits9)
                        val r8 = up
                        check(l8.biteq(r8)) { ("$bits $i").toString() + "\nl: " + (l).toString() + " (" + (l8.toBits()).toString() + " " + (Hex(l8)).toString() + ")\nr: " + (r).toString() + " (" + (r8.toBits()).toString() + " " + (Hex(r8)).toString() + ")" }
                    }
                }
            }
        checkRound(one, 0, false, 1.0, F16.fromFloat(1.0f).nextUp())
        checkRound(max, 15, true, F16.MAX, F16.INFINITY)
        checkRound(minNorm, -14, false, F16.MIN_POSITIVE_NORMAL, F16.MIN_POSITIVE_NORMAL.nextUp())
        checkRound(maxSub, -14, true, F16.MIN_POSITIVE_NORMAL.nextDown(), F16.MIN_POSITIVE_NORMAL)
        checkRound(minSub, -14, true, F16.MIN_POSITIVE_SUBNORMAL, F16.MIN_POSITIVE_SUBNORMAL.nextUp())
        checkRound(0, -14, false, 0.0, F16.MIN_POSITIVE_SUBNORMAL)
    }
}
