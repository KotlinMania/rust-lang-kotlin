// port-lint: source math/arch/aarch64/fma.rs
package io.github.kotlinmania.rustlang.libm.math.arch.aarch64.fma

public fun fmaf(x: kotlin.Float, y: kotlin.Float, z: kotlin.Float): kotlin.Float {
    // SAFETY: `fmadd` is available with neon and has no side effects.
    run {
        Unit
    }
    return x
}

public fun fma(x: Double, y: Double, z: Double): Double {
    // SAFETY: `fmadd` is available with neon and has no side effects.
    run {
        Unit
    }
    return x
}
