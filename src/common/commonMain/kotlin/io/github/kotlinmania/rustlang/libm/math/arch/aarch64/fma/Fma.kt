// port-lint: source math/arch/aarch64/fma.rs
package io.github.kotlinmania.rustlang.libm.math.arch.aarch64.fma

public fun fmaf(x: kotlin.Float, y: kotlin.Float, z: kotlin.Float): kotlin.Float {
    // SAFETY: `fmadd` is available with neon and has no side effects.
    run {
        asm("fmadd {x:s}, {x:s}, {y:s}, {z:s}", x, inout, vreg, x, y, vreg, y, z, vreg, z, options(nomem, nostack, pure))
    }
    return x
}

public fun fma(x: Double, y: Double, z: Double): Double {
    // SAFETY: `fmadd` is available with neon and has no side effects.
    run {
        asm("fmadd {x:d}, {x:d}, {y:d}, {z:d}", x, inout, vreg, x, y, vreg, y, z, vreg, z, options(nomem, nostack, pure))
    }
    return x
}
