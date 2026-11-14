// port-lint: source math/arch/x86/sqrt.rs
package io.github.kotlinmania.rustlang.libm.math.arch.x86.sqrt

public fun sqrtf(x: kotlin.Float): kotlin.Float {
    // SAFETY: `sqrtss` is part of `sse2`, which this module is gated behind. It has no memory
    // access or side effects.
    asm("sqrtss {x}, {x}", x, inout, xmmReg, x, options(nostack, nomem, pure))
    return x
}

public fun sqrt(x: Double): Double {
    // SAFETY: `sqrtsd` is part of `sse2`, which this module is gated behind. It has no memory
    // access or side effects.
    asm("sqrtsd {x}, {x}", x, inout, xmmReg, x, options(nostack, nomem, pure))
    return x
}
