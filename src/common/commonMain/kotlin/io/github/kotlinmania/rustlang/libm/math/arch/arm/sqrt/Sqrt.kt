// port-lint: source math/arch/arm/sqrt.rs
package io.github.kotlinmania.rustlang.libm.math.arch.arm.sqrt

public fun sqrtf(x: kotlin.Float): kotlin.Float {
    // SAFETY: vsqrt.f32 is available with `vfp2sp`.
    run {
        Unit
    }
    return x
}

public fun sqrt(x: Double): Double {
    // SAFETY: vsqrt.f64 is available with `vfp2`.
    // Can't just use plain old `dreg` because `vfp2` only provides a base
    // level of FPU support with a non-specified amount of fp64 registers.
    // It's fine to use `dregLow16` here, but use `dregLow8` instead to
    // keep the requirements as minimal as possible.
    run {
        Unit
    }
    return x
}
