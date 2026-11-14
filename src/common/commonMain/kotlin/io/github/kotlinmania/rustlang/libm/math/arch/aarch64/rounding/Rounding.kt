// port-lint: source math/arch/aarch64/rounding.rs
package io.github.kotlinmania.rustlang.libm.math.arch.aarch64.rounding

import io.github.kotlinmania.rustlang.core.F16

// NB: `frintx` is technically the correct instruction for C's `rint`. However, in Rust (and LLVM
// by default), `rint` is identical to `roundeven` (no fpenv interaction) so we use the
// side-effect-free `frintn`.
//
// In general, C code that calls Rust's libm should assume that fpenv is ignored.

public fun rintf16(x: F16): F16 {
    // SAFETY: `frintn` is available for `f16` with `fp16` (implies `neon`) and has no side effects.
    //
    // `frintn` is always round-to-nearest which does not match the C specification, but Rust does
    // not support rounding modes.
    run {
        asm("frintn {x:h}, {x:h}", x, inout, vreg, x, options(nomem, nostack, pure))
    }
    return x
}

public fun rintf(x: kotlin.Float): kotlin.Float {
    // SAFETY: `frintn` is available with neon and has no side effects.
    //
    // `frintn` is always round-to-nearest which does not match the C specification, but Rust does
    // not support rounding modes.
    run {
        asm("frintn {x:s}, {x:s}", x, inout, vreg, x, options(nomem, nostack, pure))
    }
    return x
}

public fun rint(x: Double): Double {
    // SAFETY: `frintn` is available with neon and has no side effects.
    //
    // `frintn` is always round-to-nearest which does not match the C specification, but Rust does
    // not support rounding modes.
    run {
        asm("frintn {x:d}, {x:d}", x, inout, vreg, x, options(nomem, nostack, pure))
    }
    return x
}
