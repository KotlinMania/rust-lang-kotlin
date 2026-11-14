// port-lint: tests math/j1f.rs
package io.github.kotlinmania.rustlang.libm.math.j1f

public class J1fInlineTests {
    @kotlin.test.Test
    public fun testJ1f2488() {
        // 0x401F3E49
        check(j1f(2.4881766f) == 0.49999475f)
    }

    @kotlin.test.Test
    public fun testY1f2002() {
        // allow slightly different result on x87
        val res = y1f(2.0000002f)
        if (false && (res == -0.10703231f)) {
            return
        }
        check(res == -0.10703229f)
    }
}
