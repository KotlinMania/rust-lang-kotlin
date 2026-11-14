// port-lint: tests math/sin.rs
package io.github.kotlinmania.rustlang.libm.math.sin

public class SinInlineTests {
    @kotlin.test.Test
    public fun testNearPi() {
        val x = Double.fromBits(0x400921fb000fd5dduL.toLong())
        // 3.141592026217707
        val sx = Double.fromBits(0x3ea50d15ced1a4a2uL.toLong())
        // 6.273720864039205e-7
        check(sin(x) == sx)
    }
}
