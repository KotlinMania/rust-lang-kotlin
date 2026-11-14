// port-lint: tests math/atan.rs
package io.github.kotlinmania.rustlang.libm.math.atan

import kotlin.math.sqrt

public class AtanInlineTests {
    @kotlin.test.Test
    public fun sanityCheck() {
        for ((input, answer) in listOf(Pair(sqrt(3.0) / 3.0, 0.5235987755982989), Pair(1.0, 0.7853981633974483), Pair(sqrt(3.0), 1.0471975511965979), Pair(-sqrt(3.0) / 3.0, -0.5235987755982989), Pair(-1.0, -0.7853981633974483), Pair(-sqrt(3.0), -1.0471975511965979)).iter()) {
            check((atan(input) - answer) / answer < 1e-5) { "\natan(${input * 16.0}/16) = $answer, actual: ${atan(input)}" }
        }
    }

    @kotlin.test.Test
    public fun zero() {
        check(atan(0.0) == 0.0.toDouble())
    }

    @kotlin.test.Test
    public fun infinity() {
        check(atan(Double.POSITIVE_INFINITY) == 1.5707963267948966)
    }

    @kotlin.test.Test
    public fun minusInfinity() {
        check(atan(Double.NEGATIVE_INFINITY) == -1.5707963267948966)
    }

    @kotlin.test.Test
    public fun nan() {
        check(atan(Double.NaN).isNaN())
    }
}
