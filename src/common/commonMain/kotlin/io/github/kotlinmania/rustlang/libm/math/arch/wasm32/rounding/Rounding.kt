// port-lint: source math/arch/wasm32/rounding.rs
package io.github.kotlinmania.rustlang.libm.math.arch.wasm32.rounding

// Wasm has builtins for simple float operations. Use the unstable `core.arch` intrinsics which
// are significantly faster than soft float operations.

public fun ceilf(x: kotlin.Float): kotlin.Float = f32Ceil(x)

public fun ceil(x: Double): Double = f64Ceil(x)

public fun floorf(x: kotlin.Float): kotlin.Float = f32Floor(x)

public fun floor(x: Double): Double = f64Floor(x)

public fun rintf(x: kotlin.Float): kotlin.Float = f32Nearest(x)

public fun rint(x: Double): Double = f64Nearest(x)

public fun truncf(x: kotlin.Float): kotlin.Float = f32Trunc(x)

public fun trunc(x: Double): Double = f64Trunc(x)
