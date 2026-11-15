// port-lint: source float/pow.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.float.pow

import io.github.kotlinmania.rustlang.compilerbuiltins.support.Float
import io.github.kotlinmania.rustlang.compilerbuiltins.support.Int
import io.github.kotlinmania.rustlang.core.F128

/**
 * Returns `a` raised to the power `b`
 */
public fun <F : Float> pow(a: F, b: Int): F {
    val a1 = a
    var a2 = a1
    val recip = b < 0
    var pow = Int.absDiff(b, 0)
    var mul = F.ONE
    while (true) {
        if ((pow and 1) != 0) {
            mul *= a2
        }
        pow = pow shr 1
        if (pow == 0) {
            break
        }
        a2 *= a2
    }
    return if (recip) {
        F.ONE / mul
    } else {
        mul
    }
}

public fun powisf2(a: Float, b: Int): Float = pow(a, b)

public fun powidf2(a: Double, b: Int): Double = pow(a, b)

public fun powitf2(a: F128, b: Int): F128 = pow(a, b)

public fun powikf2(a: F128, b: Int): F128 = pow(a, b)
