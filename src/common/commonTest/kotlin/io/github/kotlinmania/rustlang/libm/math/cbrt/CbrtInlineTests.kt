// port-lint: tests math/cbrt.rs
package io.github.kotlinmania.rustlang.libm.math.cbrt

import io.github.kotlinmania.rustlang.math.support.floattraits.biteq
import io.github.kotlinmania.rustlang.math.support.hexfloat.Hex

public class CbrtInlineTests {
    @kotlin.test.Test
    public fun spotChecks() {
        if (!false) {
            run {
                val l = cbrt(Double.fromBits(0xf7f792b28f600000uL.toLong()))
                val r = Double.fromBits(0xd29ce68655d962f3uL.toLong())
                check(biteq(l, r)) { ("").toString() + "\nl: " + (l).toString() + " (" + (l.toRawBits().toULong()).toString() + " " + (Hex(l)).toString() + ")\nr: " + (r).toString() + " (" + (r.toRawBits().toULong()).toString() + " " + (Hex(r)).toString() + ")" }
            }
        }
    }
}
