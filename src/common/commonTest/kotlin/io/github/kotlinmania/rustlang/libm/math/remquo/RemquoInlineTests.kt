// port-lint: tests math/remquo.rs
package io.github.kotlinmania.rustlang.libm.math.remquo

public class RemquoInlineTests {
    @kotlin.test.Test
    public fun testQOverflow() {
        // 0xc000000000000001, 0x04c0000000000004
        remquo(-2.0000000000000004, 8.406091369059082e-286)
    }
}
