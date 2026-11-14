// port-lint: source math/arch/i586/rounding.rs
package io.github.kotlinmania.rustlang.libm.math.arch.i586.rounding

public fun ceil(x: Double): Double {
    run {
        Unit
    }
    return x
}

public fun floor(x: Double): Double {
    run {
        Unit
    }
    return x
}

/**
 * Note that this respects rounding mode. Because it is UB to have a non-default rounding
 * mode in Rust, this acts as roundeven.
 */
public fun rint(x: Double): Double {
    run {
        Unit
    }
    return x
}
