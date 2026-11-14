// port-lint: tests math/expm1.rs
package io.github.kotlinmania.rustlang.libm.math.expm1

public class Expm1InlineTests {
    @kotlin.test.Test
    public fun sanityCheck() {
        check(expm1(1.1) == 2.0041660239464334.toDouble())
    }
}
