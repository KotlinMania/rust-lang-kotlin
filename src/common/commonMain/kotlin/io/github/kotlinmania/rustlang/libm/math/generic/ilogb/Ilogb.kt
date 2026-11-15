// port-lint: source math/generic/ilogb.rs
package io.github.kotlinmania.rustlang.libm.math.generic.ilogb

import io.github.kotlinmania.rustlang.math.support.floattraits.Float

public val FP_ILOGBNAN: Int = Int.MIN_VALUE

public val FP_ILOGB0: Int = FP_ILOGBNAN

public fun <Int, SignedInt, F : Float<F, Any?, Any?>> ilogb(x: F): Int {
    val zero = F.Int.ZERO
    var i = x.toBits()
    val e = x.ex().toInt()
    return if (e == 0) {
        i = i shl ((F.EXP_BITS + 1).toInt())
        if (i == F.Int.ZERO) {
            readVolatile(0.0 / 0.0)
            return FP_ILOGB0
        }
        // subnormal x
        var e1 = -(F.EXP_BIAS.toInt())
        while (i shr (F.BITS - 1).toInt() == zero) {
            e1 -= 1
            i = i shl 1
        }
        e1
    } else if (e == F.EXP_SAT.toInt()) {
        readVolatile(0.0 / 0.0)
        if (i shl (F.EXP_BITS + 1).toInt() != zero) {
            FP_ILOGBNAN
        } else {
            Int.MAX_VALUE
        }
    } else {
        e - F.EXP_BIAS.toInt()
    }
}
