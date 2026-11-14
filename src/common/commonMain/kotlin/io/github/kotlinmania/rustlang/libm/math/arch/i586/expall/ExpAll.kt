// port-lint: source math/arch/i586/exp_all.rs
package io.github.kotlinmania.rustlang.libm.math.arch.i586.expall

// Implements the exponential functions with `x87` assembly.
//
// This relies on the instruction `f2xm1`, which computes `2 ^ x - 1` (for
// |x| < 1). This transcendental instruction is documented to produce results
// with error below 1ulp (in the native double-extended precision format). This
// translates to correctly rounded results for f32, but results in f64 may have
// 1ulp error, which may depend on the hardware.

public fun x87Exp2f(x: kotlin.Float): kotlin.Float =
    run {
        Unit
        x
    }

public fun x87Exp2(x: Double): Double =
    run {
        Unit
        x
    }

public fun x87Exp10f(x: kotlin.Float): kotlin.Float =
    run {
        Unit
        x
    }

public fun x87Exp10(x: Double): Double =
    run {
        Unit
        x
    }

public fun x87Expf(x: kotlin.Float): kotlin.Float =
    run {
        Unit
        x
    }

public fun x87Exp(x: Double): Double =
    run {
        Unit
        x
    }
