// port-lint: tests math/exp2.rs
package io.github.kotlinmania.rustlang.libm.math.exp2

public class Exp2InlineTests {
    @kotlin.test.Test
    public fun i0WrapTest() {
        val x = -3.0 / 256.0
        check(exp2(x) == Double.fromBits(0x3fefbdba3692d514uL.toLong()))
    }
}
