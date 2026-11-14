// port-lint: source math/arch/loongarch/sqrt.rs
package io.github.kotlinmania.rustlang.libm.math.arch.loongarch.sqrt

public fun sqrt(x: Double): Double {
    // SAFETY: `fsqrt.d` is available with `d` and has no side effects.
    run {
        asm("fsqrt.d {x}, {x}", x, inout, freg, x, options(nomem, nostack, pure))
    }
    return x
}

public fun sqrtf(x: kotlin.Float): kotlin.Float {
    // SAFETY: `fsqrt.s` is available with `f` and has no side effects.
    run {
        asm("fsqrt.s {x}, {x}", x, inout, freg, x, options(nomem, nostack, pure))
    }
    return x
}
