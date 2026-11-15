// port-lint: tests math/atan2.rs
package io.github.kotlinmania.rustlang.libm.math.atan2

import io.github.kotlinmania.rustlang.libm.math.atan.atan

public class Atan2InlineTests {
    @kotlin.test.Test
    public fun sanityCheck() {
        check(atan2(0.0, 1.0) == 0.0.toDouble())
        check(atan2(0.0, -1.0) == PI)
        check(atan2(-0.0, -1.0) == -PI)
        check(atan2(3.0, 2.0) == atan(3.0 / 2.0))
        check(atan2(2.0, -1.0) == atan(2.0 / -1.0) + PI)
        check(atan2(-2.0, -1.0) == atan(-2.0 / -1.0) - PI)
    }
}
