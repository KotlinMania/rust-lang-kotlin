// port-lint: source math/arch/aarch64/sqrt.rs
package io.github.kotlinmania.rustlang.libm.math.arch.aarch64.sqrt

import io.github.kotlinmania.rustlang.core.F16

public fun sqrtf16(x: F16): F16 {
    // SAFETY: `fsqrt` is available for `f16` with `fp16` (implies `neon`) and has no
    // side effects.
    run {
        Unit
    }
    return x
}

public fun sqrtf(x: kotlin.Float): kotlin.Float {
    // SAFETY: `fsqrt` is available with neon and has no side effects.
    run {
        Unit
    }
    return x
}

public fun sqrt(x: Double): Double {
    // SAFETY: `fsqrt` is available with neon and has no side effects.
    run {
        Unit
    }
    return x
}
