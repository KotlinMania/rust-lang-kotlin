// port-lint: source math/arch/loongarch/rounding.rs
package io.github.kotlinmania.rustlang.libm.math.arch.loongarch.rounding

// NB: `frint.{ s.d }` is technically the correct instruction for C's `rint`.
// However, in Rust (and LLVM by default), `rint` is identical to `roundeven`
// (no fpenv interaction) so we use the side-effect-free `vfrintrne.{ s, d }`.
//
// In general, C code that calls Rust's libm should assume that fpenv is ignored.

public fun rint(x: Double): Double {
    // SAFETY: `vfrintrne.d` is available with `lsx` and has no side effects.
    //
    // `vfrintrne.d` is always round-to-nearest which does not match the
    // C specification, but Rust does not support rounding modes.
    run {
        asm("vfrintrne.d {x:w}, {x:w}", x, inout, freg, x, options(nomem, nostack, pure))
    }
    return x
}

public fun rintf(x: kotlin.Float): kotlin.Float {
    // SAFETY: `vfrintrne.s` is available with `lsx` and has no side effects.
    //
    // `vfrintrne.s` is always round-to-nearest which does not match the
    // C specification, but Rust does not support rounding modes.
    run {
        asm("vfrintrne.s {x:w}, {x:w}", x, inout, freg, x, options(nomem, nostack, pure))
    }
    return x
}
