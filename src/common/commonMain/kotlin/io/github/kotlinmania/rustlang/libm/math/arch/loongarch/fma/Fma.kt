// port-lint: source math/arch/loongarch/fma.rs
package io.github.kotlinmania.rustlang.libm.math.arch.loongarch.fma

public fun fma(x: Double, y: Double, z: Double): Double {
    // SAFETY: `fmadd.d` is available with `d and has no side effects.
    run {
        asm("fmadd.d {x}, {x}, {y}, {z}", x, inout, freg, x, y, freg, y, z, freg, z, options(nomem, nostack, pure))
    }
    return x
}

public fun fmaf(x: kotlin.Float, y: kotlin.Float, z: kotlin.Float): kotlin.Float {
    // SAFETY: `fmadd.s` is available with `f` and has no side effects.
    run {
        asm("fmadd.s {x}, {x}, {y}, {z}", x, inout, freg, x, y, freg, y, z, freg, z, options(nomem, nostack, pure))
    }
    return x
}
