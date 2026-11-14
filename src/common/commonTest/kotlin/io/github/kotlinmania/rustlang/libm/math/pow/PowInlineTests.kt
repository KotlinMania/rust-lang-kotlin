// port-lint: tests math/pow.rs
package io.github.kotlinmania.rustlang.libm.math.pow

import io.github.kotlinmania.rustlang.libm.math.pow.core.f64.consts.E
import io.github.kotlinmania.rustlang.libm.math.pow.core.f64.consts.PI

public val POS_ZERO: List<Double> = listOf(0.0)

public val NEG_ZERO: List<Double> = listOf(-0.0)

public val POS_ONE: List<Double> = listOf(1.0)

public val NEG_ONE: List<Double> = listOf(-1.0)

public val POS_FLOATS: List<Double> = listOf(99.0 / 70.0, E, PI)

public val NEG_FLOATS: List<Double> = listOf(-99.0 / 70.0, -E, -PI)

public val POS_SMALL_FLOATS: List<Double> = listOf((1.0 / 2.0), 2.2250738585072014E-308, 2.220446049250313E-16)

public val NEG_SMALL_FLOATS: List<Double> = listOf(-(1.0 / 2.0), -2.2250738585072014E-308, -2.220446049250313E-16)

public val POS_EVENS: List<Double> = listOf(2.0, 6.0, 8.0, 10.0, 22.0, 100.0, Double.MAX_VALUE)

public val NEG_EVENS: List<Double> = listOf((-Double.MAX_VALUE), -100.0, -22.0, -10.0, -8.0, -6.0, -2.0)

public val POS_ODDS: List<Double> = listOf(3.0, 7.0)

public val NEG_ODDS: List<Double> = listOf(-7.0, -3.0)

public val NANS: List<Double> = listOf(Double.NaN)

public val POS_INF: List<Double> = listOf(Double.POSITIVE_INFINITY)

public val NEG_INF: List<Double> = listOf(Double.NEGATIVE_INFINITY)

public val ALL: List<List<Double>> = listOf(POS_ZERO, NEG_ZERO, NANS, NEG_SMALL_FLOATS, POS_SMALL_FLOATS, NEG_FLOATS, POS_FLOATS, NEG_EVENS, POS_EVENS, NEG_ODDS, POS_ODDS, NEG_INF, POS_INF, NEG_ONE, POS_ONE)

public val POS: List<List<Double>> = listOf(POS_ZERO, POS_ODDS, POS_ONE, POS_FLOATS, POS_EVENS, POS_INF)

public val NEG: List<List<Double>> = listOf(NEG_ZERO, NEG_ODDS, NEG_ONE, NEG_FLOATS, NEG_EVENS, NEG_INF)

public fun powTest(base: Double, exponent: Double, expected: Double) {
    val res = pow(base, exponent)
    check(if (expected.isNaN()) {
        res.isNaN()
    } else {
        pow(base, exponent) == expected
    }) { "$base ** $exponent was $res instead of $expected" }
}

public fun testSetsAsBase(sets: List<List<Double>>, exponent: Double, expected: Double) {
    sets.forEach({ s -> s.iter().forEach({ value -> powTest(value, exponent, expected) }) })
}

public fun testSetsAsExponent(base: Double, sets: List<List<Double>>, expected: Double) {
    sets.forEach({ s -> s.iter().forEach({ value -> powTest(base, value, expected) }) })
}

public fun testSets(sets: List<List<Double>>, computed: (Double) -> Double, expected: (Double) -> Double) {
    sets.forEach({ s ->
        s.iter().forEach({ value ->
            val exp = expected(value)
            val res = computed(value)
            val exp1 = readVolatile(exp)
            val res1 = readVolatile(res)
            check(if (exp1.isNan()) {
                res1.isNan()
            } else {
                exp1 == res1
            }) { "test for $value was $res instead of $exp" }
        })
    })
}

public class PowInlineTests {
    @kotlin.test.Test
    public fun zeroAsExponent() {
        testSetsAsBase(ALL, 0.0, 1.0)
        testSetsAsBase(ALL, -0.0, 1.0)
    }

    @kotlin.test.Test
    public fun oneAsBase() {
        testSetsAsExponent(1.0, ALL, 1.0)
    }

    @kotlin.test.Test
    public fun nanInputs() {
        // NAN as the base:
        // (f64.Nan ^ anything *but 0* should be f64.Nan)
        testSetsAsExponent(Double.NaN, ((ALL).slice(2 until ALL.size)), Double.NaN)
        // f64.Nan as the exponent:
        // (anything *but 1* ^ f64.Nan should be f64.Nan)
        testSetsAsBase(((ALL).slice(0 until (ALL.size - 2))), Double.NaN, Double.NaN)
    }

    @kotlin.test.Test
    public fun infinityAsBase() {
        // Positive Infinity as the base:
        // (+Infinity ^ positive anything but 0 and f64.Nan should be +Infinity)
        testSetsAsExponent(Double.POSITIVE_INFINITY, ((POS).slice(1 until POS.size)), Double.POSITIVE_INFINITY)
        // (+Infinity ^ negative anything except 0 and f64.Nan should be 0.0)
        testSetsAsExponent(Double.POSITIVE_INFINITY, ((NEG).slice(1 until NEG.size)), 0.0)
        // Negative Infinity as the base:
        // (-Infinity ^ positive odd ints should be -Infinity)
        testSetsAsExponent(Double.NEGATIVE_INFINITY, listOf(POS_ODDS), Double.NEGATIVE_INFINITY)
        // (-Infinity ^ anything but odd ints should be == -0 ^ (-anything))
        // We can lump in pos/neg odd ints here because they don't seem to
        // cause panics (div by zero) in release mode (I think).
        testSets(ALL, { v: Double -> pow(Double.NEGATIVE_INFINITY, v) }, { v: Double ->
            pow(-0.0, -v)
        })
    }

    @kotlin.test.Test
    public fun infinityAsExponent() {
        // Positive/Negative base greater than 1:
        // (pos/neg > 1 ^ Infinity should be Infinity - note this excludes f64.Nan as the base)
        testSetsAsBase(((ALL).slice(5 until (ALL.size - 2))), Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
        // (pos/neg > 1 ^ -Infinity should be 0.0)
        testSetsAsBase(((ALL).slice(5 until ALL.size - 2)), Double.NEGATIVE_INFINITY, 0.0)
        // Positive/Negative base less than 1:
        val baseBelowOne = listOf(POS_ZERO, NEG_ZERO, NEG_SMALL_FLOATS, POS_SMALL_FLOATS)
        // (pos/neg < 1 ^ Infinity should be 0.0 - this also excludes f64.Nan as the base)
        testSetsAsBase(baseBelowOne, Double.POSITIVE_INFINITY, 0.0)
        // (pos/neg < 1 ^ -Infinity should be Infinity)
        testSetsAsBase(baseBelowOne, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY)
        // Positive/Negative 1 as the base:
        // (pos/neg 1 ^ Infinity should be 1)
        testSetsAsBase(listOf(NEG_ONE, POS_ONE), Double.POSITIVE_INFINITY, 1.0)
        // (pos/neg 1 ^ -Infinity should be 1)
        testSetsAsBase(listOf(NEG_ONE, POS_ONE), Double.NEGATIVE_INFINITY, 1.0)
    }

    @kotlin.test.Test
    public fun zeroAsBase() {
        // Positive Zero as the base:
        // (+0 ^ anything positive but 0 and f64.Nan should be +0)
        testSetsAsExponent(0.0, ((POS).slice(1 until POS.size)), 0.0)
        // (+0 ^ anything negative but 0 and f64.Nan should be Infinity)
        // (this should panic because we're dividing by zero)
        testSetsAsExponent(0.0, ((NEG).slice(1 until NEG.size)), Double.POSITIVE_INFINITY)
        // Negative Zero as the base:
        // (-0 ^ anything positive but 0, f64.Nan, and odd ints should be +0)
        testSetsAsExponent(-0.0, ((POS).slice(3 until POS.size)), 0.0)
        // (-0 ^ anything negative but 0, f64.Nan, and odd ints should be Infinity)
        // (should panic because of divide by zero)
        testSetsAsExponent(-0.0, ((NEG).slice(3 until NEG.size)), Double.POSITIVE_INFINITY)
        // (-0 ^ positive odd ints should be -0)
        testSetsAsExponent(-0.0, listOf(POS_ODDS), -0.0)
        // (-0 ^ negative odd ints should be -Infinity)
        // (should panic because of divide by zero)
        testSetsAsExponent(-0.0, listOf(NEG_ODDS), Double.NEGATIVE_INFINITY)
    }

    @kotlin.test.Test
    public fun specialCases() {
        // One as the exponent:
        // (anything ^ 1 should be anything - i.e. the base)
        testSets(ALL, { v: Double -> pow(v, 1.0) }, { v: Double -> v })
        // Negative One as the exponent:
        // (anything ^ -1 should be 1/anything)
        testSets(ALL, { v: Double -> pow(v, -1.0) }, { v: Double -> 1.0 / v })
        // Factoring -1 out:
        // (negative anything ^ integer should be (-1 ^ integer) * (positive anything ^ integer))
        listOf(POS_ZERO, NEG_ZERO, POS_ONE, NEG_ONE, POS_EVENS, NEG_EVENS).forEach({ intSet ->
            intSet.iter().forEach({ int ->
                testSets(ALL, { v: Double -> pow(-v, int) }, { v: Double ->
                    pow(-1.0, int) * pow(v, int)
                })
            })
        })
        // Negative base (imaginary results):
        // (-anything except 0 and Infinity ^ non-integer should be NAN)
        ((NEG).slice(1 until (NEG.size - 1))).forEach({ set ->
            set.iter().forEach({ value ->
                testSets(((ALL).slice(3 until 7)), { v: Double -> pow(value, v) }, { Double.NaN })
            })
        })
    }

    @kotlin.test.Test
    public fun normalCases() {
        check(pow(2.0, 20.0) == (1 shl 20).toDouble())
        check(pow(-1.0, 9.0) == (-1.0).toDouble())
        check(pow(-1.0, 2.2).isNaN())
        check(pow(-1.0, -1.14).isNaN())
    }
}
