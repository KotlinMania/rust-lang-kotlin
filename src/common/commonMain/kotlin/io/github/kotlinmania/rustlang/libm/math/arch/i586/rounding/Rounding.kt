// port-lint: source math/arch/i586/rounding.rs
package io.github.kotlinmania.rustlang.libm.math.arch.i586.rounding

public fun ceil(x: Double): Double {
    run {
        asm("fld qword ptr [{x}]", "fstcw [{x}]", "mov word ptr [{x} + 2], 0x0b7f", "fldcw [{x} + 2]", "frndint", "fldcw [{x}]", "fstp qword ptr [{x}]", x, reg, x, out, "st(0)", out, "st(1)", out, "st(2)", out, "st(3)", out, "st(4)", out, "st(5)", out, "st(6)", out, "st(7)", options(nostack))
    }
    return x
}

public fun floor(x: Double): Double {
    run {
        asm("fld qword ptr [{x}]", "fstcw [{x}]", "mov word ptr [{x} + 2], 0x077f", "fldcw [{x} + 2]", "frndint", "fldcw [{x}]", "fstp qword ptr [{x}]", x, reg, x, out, "st(0)", out, "st(1)", out, "st(2)", out, "st(3)", out, "st(4)", out, "st(5)", out, "st(6)", out, "st(7)", options(nostack))
    }
    return x
}

/**
 * Note that this respects rounding mode. Because it is UB to have a non-default rounding
 * mode in Rust, this acts as roundeven.
 */
public fun rint(x: Double): Double {
    run {
        asm("fld qword ptr [{x}]", "frndint", "fstp qword ptr [{x}]", x, reg, x, out, "st(0)", out, "st(1)", out, "st(2)", out, "st(3)", out, "st(4)", out, "st(5)", out, "st(6)", out, "st(7)", options(nostack))
    }
    return x
}
