// port-lint: tests math/support/modular.rs
package io.github.kotlinmania.rustlang.libm.math.support.modular

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.num.pow

// This module provides accelerated modular multiplication by large powers
// of two, which is needed for computing floating point remainders in `fmod`
// and similar functions.
//
// To keep the equations somewhat concise, the following conventions are used:
//  - all integer operations are in the mathematical sense, without overflow
//  - concatenation means multiplication: `2xq = 2 * x * q`
//  - `R = (1<<U.BITS)` is the modulus of wrapping arithmetic in `U`

public class ModularInlineTests {
    // This test is very slow when using Miri
    @kotlin.test.Test
    public fun reducerOps() {
        for (n in 33..63u.toUByte()) {
            for (x in 0 until 2 * n) {
                val temp = Reducer.new(x, n)
                val n1 = n.toUInt()
                val x0 = temp.partialRemainder().toUInt()
                check(x.toUInt() == x0)
                for (k in 0..7) {
                    val red = temp.clone()
                    val u = red.shiftReduce(k).toUInt()
                    val x1 = red.partialRemainder().toUInt()
                    check(x1 == (x0 shl k) - u * n1)
                    check(x1 < 2u * n1)
                    check((red.v2xq.toUInt()).isMultipleOf(2u * x1))
                    // `wordReduce` is equivalent to
                    // `shiftReduce(U.BITS - 1)`
                    if (k == 7) {
                        val alt = temp.clone()
                        val w = alt.wordReduce()
                        check(u == w.toUInt())
                        check(alt == red)
                    }
                }
            }
        }
    }

    // This test is very slow when using Miri
    @kotlin.test.Test
    public fun reductionU8() {
        for (y in 1 until 64u.toUByte()) {
            for (x in 0 until 2 * y) {
                var r = x % y
                for (e in 0 until 100) {
                    check(r == linearMulReduction(x, e, y))
                    // maintain the correct expected remainder
                    r = r shl 1
                    if (r >= y) {
                        r -= y
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun reductionU128() {
        check(linearMulReduction<U128>(17, 100u, 123456789) == (17 shl 100) % 123456789)
        // power-of-two divisor
        check(linearMulReduction(0xdeadbeef, 100u, U128(0x0uL, 0x1uL) shl 116) == 0xbeef shl 100)
        val x = (U128(0x0uL, 0xauL)).pow(37u)
        val y = (U128(0x0uL, 0xbuL)).pow(36u)
        check(x < y)
        var r = x
        for (e in 0 until 1000) {
            check(r == linearMulReduction(x, e, y))
            // maintain the correct expected remainder
            r = r shl 1
            if (r >= y) {
                r -= y
            }
            check(r != U128(0x0uL, 0x0uL))
        }
    }
}
