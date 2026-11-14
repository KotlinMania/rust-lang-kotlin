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
        asm("fld dword ptr [{x}]", "fld1", "fld st(1)", "frndint", "fxch st(2)", "fucom st(2)", "fstsw ax", "test ax, 0x4000", "jnz 2f", "fsub st(0), st(2)", "f2xm1", "fadd st(1), st(0)", "2:", "fstp st(0)", "fscale", "fstp dword ptr [{x}]", "fstp st(0)", x, reg, x, out, "ax", out, "st(0)", out, "st(1)", out, "st(2)", out, "st(3)", out, "st(4)", out, "st(5)", out, "st(6)", out, "st(7)", options(nostack))
        x
    }

public fun x87Exp2(x: Double): Double =
    run {
        asm("fld qword ptr [{x}]", "fld1", "fld st(1)", "frndint", "fxch st(2)", "fucom st(2)", "fstsw ax", "test ax, 0x4000", "jnz 2f", "fsub st(0), st(2)", "f2xm1", "fadd st(1), st(0)", "2:", "fstp st(0)", "fscale", "fstp qword ptr [{x}]", "fstp st(0)", x, reg, x, out, "ax", out, "st(0)", out, "st(1)", out, "st(2)", out, "st(3)", out, "st(4)", out, "st(5)", out, "st(6)", out, "st(7)", options(nostack))
        x
    }

public fun x87Exp10f(x: kotlin.Float): kotlin.Float =
    run {
        asm("fldl2t\nfmul dword ptr [{x}]", "fld1", "fld st(1)", "frndint", "fxch st(2)", "fucom st(2)", "fstsw ax", "test ax, 0x4000", "jnz 2f", "fsub st(0), st(2)", "f2xm1", "fadd st(1), st(0)", "2:", "fstp st(0)", "fscale", "fstp dword ptr [{x}]", "fstp st(0)", x, reg, x, out, "ax", out, "st(0)", out, "st(1)", out, "st(2)", out, "st(3)", out, "st(4)", out, "st(5)", out, "st(6)", out, "st(7)", options(nostack))
        x
    }

public fun x87Exp10(x: Double): Double =
    run {
        asm("fldl2t\nfmul qword ptr [{x}]", "fld1", "fld st(1)", "frndint", "fxch st(2)", "fucom st(2)", "fstsw ax", "test ax, 0x4000", "jnz 2f", "fsub st(0), st(2)", "f2xm1", "fadd st(1), st(0)", "2:", "fstp st(0)", "fscale", "fstp qword ptr [{x}]", "fstp st(0)", x, reg, x, out, "ax", out, "st(0)", out, "st(1)", out, "st(2)", out, "st(3)", out, "st(4)", out, "st(5)", out, "st(6)", out, "st(7)", options(nostack))
        x
    }

public fun x87Expf(x: kotlin.Float): kotlin.Float =
    run {
        asm("fldl2e\nfmul dword ptr [{x}]", "fld1", "fld st(1)", "frndint", "fxch st(2)", "fucom st(2)", "fstsw ax", "test ax, 0x4000", "jnz 2f", "fsub st(0), st(2)", "f2xm1", "fadd st(1), st(0)", "2:", "fstp st(0)", "fscale", "fstp dword ptr [{x}]", "fstp st(0)", x, reg, x, out, "ax", out, "st(0)", out, "st(1)", out, "st(2)", out, "st(3)", out, "st(4)", out, "st(5)", out, "st(6)", out, "st(7)", options(nostack))
        x
    }

public fun x87Exp(x: Double): Double =
    run {
        asm("fldl2e\nfmul qword ptr [{x}]", "fld1", "fld st(1)", "frndint", "fxch st(2)", "fucom st(2)", "fstsw ax", "test ax, 0x4000", "jnz 2f", "fsub st(0), st(2)", "f2xm1", "fadd st(1), st(0)", "2:", "fstp st(0)", "fscale", "fstp qword ptr [{x}]", "fstp st(0)", x, reg, x, out, "ax", out, "st(0)", out, "st(1)", out, "st(2)", out, "st(3)", out, "st(4)", out, "st(5)", out, "st(6)", out, "st(7)", options(nostack))
        x
    }
