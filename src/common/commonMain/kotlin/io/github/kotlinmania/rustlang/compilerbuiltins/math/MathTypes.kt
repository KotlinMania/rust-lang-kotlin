package io.github.kotlinmania.rustlang.compilerbuiltins.math

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

// Weak linkage is unreliable on Windows and Apple, so we don't expose symbols that we know
// the system libc provides in order to avoid conflicts.
// Windows and MacOS do not yet expose roundeven and IEEE 754-2019 `maximum` / `minimum`,
// however, so we still provide a fallback.
// allow for windows (and other targets)
public fun ceilf16(x: F16): F16 = io.github.kotlinmania.rustlang.math.ceilf16(x)

public fun copysignf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.math.copysignf16(x, y)

public fun fabsf16(x: F16): F16 = io.github.kotlinmania.rustlang.math.fabsf16(x)

public fun fdimf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.math.fdimf16(x, y)

public fun floorf16(x: F16): F16 = io.github.kotlinmania.rustlang.math.floorf16(x)

public fun fmaf16(x: F16, y: F16, z: F16): F16 = io.github.kotlinmania.rustlang.math.fmaf16(x, y, z)

public fun fmaxf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.math.fmaxf16(x, y)

public fun fmaximumNumf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.math.fmaximumNumf16(x, y)

public fun fmaximumf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.math.fmaximumf16(x, y)

public fun fminf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.math.fminf16(x, y)

public fun fminimumNumf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.math.fminimumNumf16(x, y)

public fun fminimumf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.math.fminimumf16(x, y)

public fun fmodf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.math.fmodf16(x, y)

public fun rintf16(x: F16): F16 = io.github.kotlinmania.rustlang.math.rintf16(x)

public fun roundevenf16(x: F16): F16 = io.github.kotlinmania.rustlang.math.roundevenf16(x)

public fun roundf16(x: F16): F16 = io.github.kotlinmania.rustlang.math.roundf16(x)

public fun sqrtf16(x: F16): F16 = io.github.kotlinmania.rustlang.math.sqrtf16(x)

public fun truncf16(x: F16): F16 = io.github.kotlinmania.rustlang.math.truncf16(x)

public fun cbrtf(n: Float): Float = io.github.kotlinmania.rustlang.math.cbrtf(n)

public fun ceilf(x: Float): Float = io.github.kotlinmania.rustlang.math.ceilf(x)

public fun copysignf(x: Float, y: Float): Float = io.github.kotlinmania.rustlang.math.copysignf(x, y)

public fun fabsf(x: Float): Float = io.github.kotlinmania.rustlang.math.fabsf(x)

public fun fdimf(a: Float, b: Float): Float = io.github.kotlinmania.rustlang.math.fdimf(a, b)

public fun floorf(x: Float): Float = io.github.kotlinmania.rustlang.math.floorf(x)

public fun fmaf(x: Float, y: Float, z: Float): Float = io.github.kotlinmania.rustlang.math.fmaf(x, y, z)

public fun fmaxf(x: Float, y: Float): Float = io.github.kotlinmania.rustlang.math.fmaxf(x, y)

public fun fminf(x: Float, y: Float): Float = io.github.kotlinmania.rustlang.math.fminf(x, y)

public fun fmodf(x: Float, y: Float): Float = io.github.kotlinmania.rustlang.math.fmodf(x, y)

public fun rintf(x: Float): Float = io.github.kotlinmania.rustlang.math.rintf(x)

public fun roundf(x: Float): Float = io.github.kotlinmania.rustlang.math.roundf(x)

public fun sqrtf(x: Float): Float = io.github.kotlinmania.rustlang.math.sqrtf(x)

public fun truncf(x: Float): Float = io.github.kotlinmania.rustlang.math.truncf(x)

public fun cbrt(x: Double): Double = io.github.kotlinmania.rustlang.math.cbrt(x)

public fun ceil(x: Double): Double = io.github.kotlinmania.rustlang.math.ceil(x)

public fun copysign(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.math.copysign(x, y)

public fun fabs(x: Double): Double = io.github.kotlinmania.rustlang.math.fabs(x)

public fun fdim(a: Double, b: Double): Double = io.github.kotlinmania.rustlang.math.fdim(a, b)

public fun floor(x: Double): Double = io.github.kotlinmania.rustlang.math.floor(x)

public fun fma(x: Double, y: Double, z: Double): Double = io.github.kotlinmania.rustlang.math.fma(x, y, z)

public fun fmax(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.math.fmax(x, y)

public fun fmin(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.math.fmin(x, y)

public fun fmod(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.math.fmod(x, y)

public fun rint(x: Double): Double = io.github.kotlinmania.rustlang.math.rint(x)

public fun round(x: Double): Double = io.github.kotlinmania.rustlang.math.round(x)

public fun sqrt(x: Double): Double = io.github.kotlinmania.rustlang.math.sqrt(x)

public fun trunc(x: Double): Double = io.github.kotlinmania.rustlang.math.trunc(x)

public fun fmaximum(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.math.fmaximum(x, y)

public fun fmaximumNum(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.math.fmaximumNum(x, y)

public fun fmaximumNumf(x: Float, y: Float): Float = io.github.kotlinmania.rustlang.math.fmaximumNumf(x, y)

public fun fmaximumf(x: Float, y: Float): Float = io.github.kotlinmania.rustlang.math.fmaximumf(x, y)

public fun fminimum(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.math.fminimum(x, y)

public fun fminimumNum(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.math.fminimumNum(x, y)

public fun fminimumNumf(x: Float, y: Float): Float = io.github.kotlinmania.rustlang.math.fminimumNumf(x, y)

public fun fminimumf(x: Float, y: Float): Float = io.github.kotlinmania.rustlang.math.fminimumf(x, y)

public fun roundeven(x: Double): Double = io.github.kotlinmania.rustlang.math.roundeven(x)

public fun roundevenf(x: Float): Float = io.github.kotlinmania.rustlang.math.roundevenf(x)

public fun ceilf128(x: F128): F128 = io.github.kotlinmania.rustlang.math.ceilf128(x)

public fun copysignf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.math.copysignf128(x, y)

public fun fabsf128(x: F128): F128 = io.github.kotlinmania.rustlang.math.fabsf128(x)

public fun fdimf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.math.fdimf128(x, y)

public fun floorf128(x: F128): F128 = io.github.kotlinmania.rustlang.math.floorf128(x)

public fun fmaf128(x: F128, y: F128, z: F128): F128 = io.github.kotlinmania.rustlang.math.fmaf128(x, y, z)

public fun fmaxf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.math.fmaxf128(x, y)

public fun fmaximumNumf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.math.fmaximumNumf128(x, y)

public fun fmaximumf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.math.fmaximumf128(x, y)

public fun fminf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.math.fminf128(x, y)

public fun fminimumNumf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.math.fminimumNumf128(x, y)

public fun fminimumf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.math.fminimumf128(x, y)

public fun fmodf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.math.fmodf128(x, y)

public fun rintf128(x: F128): F128 = io.github.kotlinmania.rustlang.math.rintf128(x)

public fun roundevenf128(x: F128): F128 = io.github.kotlinmania.rustlang.math.roundevenf128(x)

public fun roundf128(x: F128): F128 = io.github.kotlinmania.rustlang.math.roundf128(x)

public fun sqrtf128(x: F128): F128 = io.github.kotlinmania.rustlang.math.sqrtf128(x)

public fun truncf128(x: F128): F128 = io.github.kotlinmania.rustlang.math.truncf128(x)

public fun acos(x: Double): Double = io.github.kotlinmania.rustlang.math.acos(x)

public fun acosf(n: Float): Float = io.github.kotlinmania.rustlang.math.acosf(n)

public fun acosh(x: Double): Double = io.github.kotlinmania.rustlang.math.acosh(x)

public fun acoshf(x: Float): Float = io.github.kotlinmania.rustlang.math.acoshf(x)

public fun asin(x: Double): Double = io.github.kotlinmania.rustlang.math.asin(x)

public fun asinf(n: Float): Float = io.github.kotlinmania.rustlang.math.asinf(n)

public fun asinh(x: Double): Double = io.github.kotlinmania.rustlang.math.asinh(x)

public fun asinhf(x: Float): Float = io.github.kotlinmania.rustlang.math.asinhf(x)

public fun atan(x: Double): Double = io.github.kotlinmania.rustlang.math.atan(x)

public fun atan2(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.math.atan2(x, y)

public fun atan2f(a: Float, b: Float): Float = io.github.kotlinmania.rustlang.math.atan2f(a, b)

public fun atanf(n: Float): Float = io.github.kotlinmania.rustlang.math.atanf(n)

public fun cos(x: Double): Double = io.github.kotlinmania.rustlang.math.cos(x)

public fun cosf(x: Float): Float = io.github.kotlinmania.rustlang.math.cosf(x)

public fun cosh(x: Double): Double = io.github.kotlinmania.rustlang.math.cosh(x)

public fun coshf(n: Float): Float = io.github.kotlinmania.rustlang.math.coshf(n)

public fun erf(x: Double): Double = io.github.kotlinmania.rustlang.math.erf(x)

public fun erfc(x: Double): Double = io.github.kotlinmania.rustlang.math.erfc(x)

public fun erfcf(x: Float): Float = io.github.kotlinmania.rustlang.math.erfcf(x)

public fun erff(x: Float): Float = io.github.kotlinmania.rustlang.math.erff(x)

public fun exp(x: Double): Double = io.github.kotlinmania.rustlang.math.exp(x.toUInt()).toDouble()

public fun exp2(x: Double): Double = io.github.kotlinmania.rustlang.math.exp2(x)

public fun exp2f(x: Float): Float = io.github.kotlinmania.rustlang.math.exp2f(x)

public fun expf(x: Float): Float = io.github.kotlinmania.rustlang.math.expf(x)

public fun expm1(x: Double): Double = io.github.kotlinmania.rustlang.math.expm1(x)

public fun expm1f(n: Float): Float = io.github.kotlinmania.rustlang.math.expm1f(n)

public fun hypot(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.math.hypot(x, y)

public fun hypotf(x: Float, y: Float): Float = io.github.kotlinmania.rustlang.math.hypotf(x, y)

public fun ldexp(f: Double, n: Int): Double = io.github.kotlinmania.rustlang.math.ldexp(f, n)

public fun ldexpf(f: Float, n: Int): Float = io.github.kotlinmania.rustlang.math.ldexpf(f, n)

public fun log(x: Double): Double = io.github.kotlinmania.rustlang.math.log(x)

public fun log10(x: Double): Double = io.github.kotlinmania.rustlang.math.log10(x)

public fun log10f(x: Float): Float = io.github.kotlinmania.rustlang.math.log10f(x)

public fun log1p(x: Double): Double = io.github.kotlinmania.rustlang.math.log1p(x)

public fun log1pf(n: Float): Float = io.github.kotlinmania.rustlang.math.log1pf(n)

public fun log2(x: Double): Double = io.github.kotlinmania.rustlang.math.log2(x)

public fun log2f(x: Float): Float = io.github.kotlinmania.rustlang.math.log2f(x)

public fun logf(x: Float): Float = io.github.kotlinmania.rustlang.math.logf(x)

public fun pow(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.math.pow(x, y.toInt())

public fun powf(x: Float, y: Float): Float = io.github.kotlinmania.rustlang.math.powf(x, y)

public fun sin(x: Double): Double = io.github.kotlinmania.rustlang.math.sin(x)

public fun sinf(x: Float): Float = io.github.kotlinmania.rustlang.math.sinf(x)

public fun sinh(x: Double): Double = io.github.kotlinmania.rustlang.math.sinh(x)

public fun sinhf(n: Float): Float = io.github.kotlinmania.rustlang.math.sinhf(n)

public fun tan(x: Double): Double = io.github.kotlinmania.rustlang.math.tan(x)

public fun tanf(n: Float): Float = io.github.kotlinmania.rustlang.math.tanf(n)

public fun tanh(x: Double): Double = io.github.kotlinmania.rustlang.math.tanh(x)

public fun tanhf(n: Float): Float = io.github.kotlinmania.rustlang.math.tanhf(n)

public fun tgamma(x: Double): Double = io.github.kotlinmania.rustlang.math.tgamma(x)

public fun tgammaf(x: Float): Float = io.github.kotlinmania.rustlang.math.tgammaf(x)

public fun lgammaR(x: Double, s: RustMutableReference<Int>): Double {
    val s1 = s
    val r = io.github.kotlinmania.rustlang.math.lgammaR(x)
    (s1).write(r.second)
    return r.first
}

public fun lgammafR(x: Float, s: RustMutableReference<Int>): Float {
    val s1 = s
    val r = io.github.kotlinmania.rustlang.math.lgammafR(x)
    (s1).write(r.second)
    return r.first
}
