// port-lint: tests math/sqrt.rs
package io.github.kotlinmania.rustlang.libm.math.sqrt

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.libm.math.Generic
import io.github.kotlinmania.rustlang.math.generic.sqrt.SqrtHelper
import io.github.kotlinmania.rustlang.math.support.env.Round
import io.github.kotlinmania.rustlang.math.support.env.Status
import io.github.kotlinmania.rustlang.math.support.floattraits.Float
import io.github.kotlinmania.rustlang.math.support.floattraits.biteq
import io.github.kotlinmania.rustlang.math.support.hexfloat.Hex

/**
 * Test behavior specified in IEEE 754 `squareRoot`.
 */
public fun <Int, SignedInt, ISet1, ISet2, D, F> specTest() where F : Float<F, Any?, Any?>, F : SqrtHelper<F, Any?, Any?> {
    // Values that should return a NaN and raise invalid
    val nan = listOf(F.NEG_INFINITY, F.NEG_ONE, F.NAN, F.MIN)
    // Values that return unaltered
    val roundtrip = listOf(F.ZERO, F.NEG_ZERO, F.INFINITY)
    for (x in nan) {
        val structVal = Generic.sqrtRound(x, Round.Nearest)
        val value = structVal.value
        val status = structVal.status
        check(value.isNan())
        check(status == Status.INVALID)
    }
    for (x1 in roundtrip) {
        val structVal1 = Generic.sqrtRound(x1, Round.Nearest)
        val value1 = structVal1.value
        val status1 = structVal1.status
        run {
            val l = value1
            val r = x1
            check(biteq(l, r)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l.toBits()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
        }
        check(status1 == Status.OK)
    }
}

public class SqrtInlineTests {
    @kotlin.test.Test
    public fun sanityCheckF16() {
        run {
            val l = sqrtf16(F16.fromFloat(100.0f))
            val r = 10.0
            check(biteq(l, r)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l.toBits()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
        }
        run {
            val l1 = sqrtf16(F16.fromFloat(4.0f))
            val r1 = 2.0
            check(biteq(l1, r1)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l1.toBits()).toString() + " " + (Hex(l1)).toString() + ")\nr: " + (r).toString() + " (" + (r1.toBits()).toString() + " " + (Hex(r1)).toString() + ")" }
        }
    }

    @kotlin.test.Test
    public fun specTestsF16() {
        specTest<F16>()
    }

    @kotlin.test.Test
    public fun conformanceTestsF16() {
        val cases = listOf(Pair(F16.PI, 0x3f17u.toUShort()), Pair(F16.fromFloat(10000.0f), 0x5640u.toUShort()), Pair(F16.fromBits(0x0000000fu.toUShort()), 0x13bfu.toUShort()), Pair(F16.INFINITY, F16.INFINITY.toBits()))
        for ((input, output) in cases) {
            run {
                val l = sqrtf16(input)
                val r = F16.fromBits(output.toUShort())
                check(biteq(l, r)) { ("input: $input (${input.toBits()})").toString() + "\nl: " + (l).toString() + " (" + (l.toBits()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
            }
        }
    }

    @kotlin.test.Test
    public fun sanityCheckF32() {
        run {
            val l = sqrtf(100.0f)
            val r = 10.0
            check(biteq(l, r)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l.toRawBits().toUInt()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
        }
        run {
            val l1 = sqrtf(4.0f)
            val r1 = 2.0
            check(biteq(l1, r1)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l1.toRawBits().toUInt()).toString() + " " + (Hex(l1)).toString() + ")\nr: " + (r).toString() + " (" + (r1.toBits()).toString() + " " + (Hex(r1)).toString() + ")" }
        }
    }

    @kotlin.test.Test
    public fun specTestsF32() {
        specTest<kotlin.Float>()
    }

    @kotlin.test.Test
    public fun conformanceTestsF32() {
        val cases = listOf(Pair(kotlin.Float.PI, 0x3fe2dfc5u), Pair(10000.0f, 0x42c80000u), Pair(kotlin.Float.fromBits(0x0000000fu.toInt()), 0x1b2f456fu), Pair(kotlin.Float.POSITIVE_INFINITY, kotlin.Float.POSITIVE_INFINITY.toRawBits().toUInt()))
        for ((input, output) in cases) {
            run {
                val l = sqrtf(input)
                val r = kotlin.Float.fromBits(output.toInt())
                check(biteq(l, r)) { ("input: $input (${input.toBits()})").toString() + "\nl: " + (l).toString() + " (" + (l.toRawBits().toUInt()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toRawBits().toUInt()).toString() + " " + (Hex(r)).toString() + ")" }
            }
        }
    }

    @kotlin.test.Test
    public fun sanityCheckF64() {
        run {
            val l = sqrt(100.0)
            val r = 10.0
            check(biteq(l, r)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l.toRawBits().toULong()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
        }
        run {
            val l1 = sqrt(4.0)
            val r1 = 2.0
            check(biteq(l1, r1)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l1.toRawBits().toULong()).toString() + " " + (Hex(l1)).toString() + ")\nr: " + (r).toString() + " (" + (r1.toBits()).toString() + " " + (Hex(r1)).toString() + ")" }
        }
    }

    @kotlin.test.Test
    public fun specTestsF64() {
        specTest<Double>()
    }

    @kotlin.test.Test
    public fun conformanceTestsF64() {
        val cases = listOf(Pair(Double.PI, 0x3ffc5bf891b4ef6auL), Pair(10000.0, 0x4059000000000000uL), Pair(Double.fromBits(0x0000000fuL.toLong()), 0x1e7efbdeb14f4edauL), Pair(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY.toRawBits().toULong()))
        for ((input, output) in cases) {
            run {
                val l = sqrt(input)
                val r = Double.fromBits(output.toLong())
                check(biteq(l, r)) { ("input: $input (${input.toBits()})").toString() + "\nl: " + (l).toString() + " (" + (l.toRawBits().toULong()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toRawBits().toULong()).toString() + " " + (Hex(r)).toString() + ")" }
            }
        }
    }

    @kotlin.test.Test
    public fun sanityCheckF128() {
        run {
            val l = sqrtf128(F128.fromDouble(100.0))
            val r = 10.0
            check(biteq(l, r)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l.toBits()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
        }
        run {
            val l1 = sqrtf128(F128.fromDouble(4.0))
            val r1 = 2.0
            check(biteq(l1, r1)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l1.toBits()).toString() + " " + (Hex(l1)).toString() + ")\nr: " + (r).toString() + " (" + (r1.toBits()).toString() + " " + (Hex(r1)).toString() + ")" }
        }
    }

    @kotlin.test.Test
    public fun specTestsF128() {
        specTest<F128>()
    }

    @kotlin.test.Test
    public fun conformanceTestsF128() {
        val cases = listOf(Pair(F128.PI, U128(0x3fffc5bf891b4ef6uL, 0xaa79c3b0520d5db9uL)), Pair(F128.fromBits(U128(0x400c388000000000uL, 0x0uL)), U128(0x4005900000000000uL, 0x0uL)), Pair(F128.fromBits(U128(0x0uL, 0xfuL)), U128(0x1fc9efbdeb14f4eduL, 0x9b17ae807907e1e9uL)), Pair(F128.INFINITY, F128.INFINITY.toBits()))
        for ((input, output) in cases) {
            run {
                val l = sqrtf128(input)
                val r = F128.fromBits(output)
                check(biteq(l, r)) { ("input: $input (${input.toBits()})").toString() + "\nl: " + (l).toString() + " (" + (l.toBits()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toBits()).toString() + " " + (Hex(r)).toString() + ")" }
            }
        }
    }
}
