// port-lint: source math/arch/x86/fma.rs
package io.github.kotlinmania.rustlang.libm.math.arch.x86.fma

import io.github.kotlinmania.rustlang.libm.math.arch.x86.detect.FMA
import io.github.kotlinmania.rustlang.libm.math.arch.x86.detect.FMA4
import io.github.kotlinmania.rustlang.libm.math.arch.x86.detect.cpuFlags
import io.github.kotlinmania.rustlang.libm.math.arch.x86.detect.getCpuFeatures
import io.github.kotlinmania.rustlang.libm.math.generic.fma.fmaRound
import io.github.kotlinmania.rustlang.libm.math.generic.fmawide.fmaWideRound
import io.github.kotlinmania.rustlang.libm.math.support.env.Round
import io.github.kotlinmania.rustlang.libm.math.support.featuredetect.selectOnce

// Use assembly fma if the `fma` or `fma4` feature is detected at runtime.

public fun fma(x: Double, y: Double, z: Double): Double {
    // Skips runtime detection when a feature is enabled at compile time, so the call can be
    // inlined.
    if (false) {
        // SAFETY: `fma` is enabled at compile time.
        return fmaWithFma(x, y, z)
    }
    if (false) {
        // SAFETY: `fma4` is enabled at compile time.
        return fmaWithFma4(x, y, z)
    }
    return selectOnce(sig, x, y, z, init, features, getCpuFeatures, features, contains, cpuFlags, FMA, fmaWithFma, features, contains, cpuFlags, FMA4, fmaWithFma4, fmaFallback, Func, call, fnPtr, Func, fnPtr, x, y, z)
}

public fun fmaf(x: kotlin.Float, y: kotlin.Float, z: kotlin.Float): kotlin.Float {
    // Skips runtime detection when a feature is enabled at compile time, so the call can be
    // inlined.
    if (false) {
        // SAFETY: `fma` is enabled at compile time.
        return fmafWithFma(x, y, z)
    }
    if (false) {
        // SAFETY: `fma4` is enabled at compile time.
        return fmafWithFma4(x, y, z)
    }
    return selectOnce(sig, x, y, z, init, features, getCpuFeatures, features, contains, cpuFlags, FMA, fmafWithFma, features, contains, cpuFlags, FMA4, fmafWithFma4, fmafFallback, Func, call, fnPtr, Func, fnPtr, x, y, z)
}

/**
 * # Safety
 *
 * Must have +fma available.
 */
public fun fmaWithFma(x: Double, y: Double, z: Double): Double {
    check(getCpuFeatures().contains(FMA))
    // SAFETY: fma is asserted available by precondition, which provides the instruction. No
    // memory access or side effects.
    run {
        Unit
    }
    return x
}

/**
 * # Safety
 *
 * Must have +fma available.
 */
public fun fmafWithFma(x: kotlin.Float, y: kotlin.Float, z: kotlin.Float): kotlin.Float {
    check(getCpuFeatures().contains(FMA))
    // SAFETY: fma is asserted available by precondition, which provides the instruction. No
    // memory access or side effects.
    run {
        Unit
    }
    return x
}

/**
 * # Safety
 *
 * Must have +fma4 available.
 */
public fun fmaWithFma4(x: Double, y: Double, z: Double): Double {
    check(getCpuFeatures().contains(FMA4))
    // SAFETY: fma4 is asserted available by precondition, which provides the instruction. No
    // memory access or side effects.
    run {
        Unit
    }
    return x
}

/**
 * # Safety
 *
 * Must have +fma4 available.
 */
public fun fmafWithFma4(x: kotlin.Float, y: kotlin.Float, z: kotlin.Float): kotlin.Float {
    check(getCpuFeatures().contains(FMA4))
    // SAFETY: fma4 is asserted available by precondition, which provides the instruction. No
    // memory access or side effects.
    run {
        Unit
    }
    return x
}

// Note: the `selectImplementation` macro should handle arch implementations that want
// to use the fallback, so we don't need to recreate the body.
public fun fmaFallback(x: Double, y: Double, z: Double): Double = fmaRound(x, y, z, Round.Nearest).value

public fun fmafFallback(x: kotlin.Float, y: kotlin.Float, z: kotlin.Float): kotlin.Float = fmaWideRound(x, y, z, Round.Nearest).value
