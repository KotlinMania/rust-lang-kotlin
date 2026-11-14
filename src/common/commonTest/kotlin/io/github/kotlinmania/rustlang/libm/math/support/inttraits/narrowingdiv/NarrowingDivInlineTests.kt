// port-lint: tests math/support/int_traits/narrowing_div.rs
package io.github.kotlinmania.rustlang.libm.math.support.inttraits.narrowingdiv

public class NarrowingDivInlineTests {
    // This test is very slow when using Miri
    @kotlin.test.Test
    public fun inverseMul() {
        for (x in 0..UByte.MAX_VALUE) {
            for (y in 1..UByte.MAX_VALUE) {
                val xy = x.widenMul(y)
                check(xy.checkedNarrowingDivRem(y) == Pair(x, 0))
                check((xy + (y - 1).toUShort()).checkedNarrowingDivRem(y) == Pair(x, y - 1))
                if (y > 1) {
                    check((xy + 1).checkedNarrowingDivRem(y) == Pair(x, 1))
                    check((xy + (y - 2).toUShort()).checkedNarrowingDivRem(y) == Pair(x, y - 2))
                }
            }
        }
    }
}
