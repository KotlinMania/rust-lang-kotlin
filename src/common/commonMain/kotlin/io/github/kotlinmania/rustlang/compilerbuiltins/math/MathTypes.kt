package io.github.kotlinmania.rustlang.compilerbuiltins.math

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

// Weak linkage is unreliable on Windows and Apple, so we don't expose symbols that we know
// the system libc provides in order to avoid conflicts.
// Windows and MacOS do not yet expose roundeven and IEEE 754-2019 `maximum` / `minimum`,
// however, so we still provide a fallback.
// allow for windows (and other targets)
public fun ceilf16(x: F16): F16 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.ceilf16(x)

public fun copysignf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.copysignf16(x, y)

public fun fabsf16(x: F16): F16 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fabsf16(x)

public fun fdimf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fdimf16(x, y)

public fun floorf16(x: F16): F16 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.floorf16(x)

public fun fmaf16(x: F16, y: F16, z: F16): F16 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fmaf16(x, y, z)

public fun fmaxf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fmaxf16(x, y)

public fun fmaximumNumf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fmaximumNumf16(x, y)

public fun fmaximumf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fmaximumf16(x, y)

public fun fminf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fminf16(x, y)

public fun fminimumNumf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fminimumNumf16(x, y)

public fun fminimumf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fminimumf16(x, y)

public fun fmodf16(x: F16, y: F16): F16 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fmodf16(x, y)

public fun rintf16(x: F16): F16 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.rintf16(x)

public fun roundevenf16(x: F16): F16 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.roundevenf16(x)

public fun roundf16(x: F16): F16 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.roundf16(x)

public fun sqrtf16(x: F16): F16 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.sqrtf16(x)

public fun truncf16(x: F16): F16 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.truncf16(x)

public fun cbrtf(n: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.cbrtf(n)

public fun ceilf(x: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.ceilf(x)

public fun copysignf(x: Float, y: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.copysignf(x, y)

public fun fabsf(x: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fabsf(x)

public fun fdimf(a: Float, b: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fdimf(a, b)

public fun floorf(x: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.floorf(x)

public fun fmaf(x: Float, y: Float, z: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fmaf(x, y, z)

public fun fmaxf(x: Float, y: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fmaxf(x, y)

public fun fminf(x: Float, y: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fminf(x, y)

public fun fmodf(x: Float, y: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fmodf(x, y)

public fun rintf(x: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.rintf(x)

public fun roundf(x: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.roundf(x)

public fun sqrtf(x: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.sqrtf(x)

public fun truncf(x: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.truncf(x)

public fun cbrt(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.cbrt(x)

public fun ceil(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.ceil(x)

public fun copysign(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.copysign(x, y)

public fun fabs(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fabs(x)

public fun fdim(a: Double, b: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fdim(a, b)

public fun floor(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.floor(x)

public fun fma(x: Double, y: Double, z: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fma(x, y, z)

public fun fmax(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fmax(x, y)

public fun fmin(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fmin(x, y)

public fun fmod(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fmod(x, y)

public fun rint(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.rint(x)

public fun round(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.round(x)

public fun sqrt(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.sqrt(x)

public fun trunc(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.float.trunc.trunc(x)

public fun fmaximum(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fmaximum(x, y)

public fun fmaximumNum(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fmaximumNum(x, y)

public fun fmaximumNumf(x: Float, y: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fmaximumNumf(x, y)

public fun fmaximumf(x: Float, y: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fmaximumf(x, y)

public fun fminimum(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fminimum(x, y)

public fun fminimumNum(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fminimumNum(x, y)

public fun fminimumNumf(x: Float, y: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fminimumNumf(x, y)

public fun fminimumf(x: Float, y: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fminimumf(x, y)

public fun roundeven(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.roundeven(x)

public fun roundevenf(x: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.roundevenf(x)

public fun ceilf128(x: F128): F128 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.ceilf128(x)

public fun copysignf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.copysignf128(x, y)

public fun fabsf128(x: F128): F128 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fabsf128(x)

public fun fdimf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fdimf128(x, y)

public fun floorf128(x: F128): F128 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.floorf128(x)

public fun fmaf128(x: F128, y: F128, z: F128): F128 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fmaf128(x, y, z)

public fun fmaxf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fmaxf128(x, y)

public fun fmaximumNumf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fmaximumNumf128(x, y)

public fun fmaximumf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fmaximumf128(x, y)

public fun fminf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fminf128(x, y)

public fun fminimumNumf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fminimumNumf128(x, y)

public fun fminimumf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fminimumf128(x, y)

public fun fmodf128(x: F128, y: F128): F128 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.fmodf128(x, y)

public fun rintf128(x: F128): F128 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.rintf128(x)

public fun roundevenf128(x: F128): F128 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.roundevenf128(x)

public fun roundf128(x: F128): F128 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.roundf128(x)

public fun sqrtf128(x: F128): F128 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.sqrtf128(x)

public fun truncf128(x: F128): F128 = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.truncf128(x)

public fun acos(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.acos(x)

public fun acosf(n: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.acosf(n)

public fun acosh(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.acosh(x)

public fun acoshf(x: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.acoshf(x)

public fun asin(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.asin(x)

public fun asinf(n: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.asinf(n)

public fun asinh(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.asinh(x)

public fun asinhf(x: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.asinhf(x)

public fun atan(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.atan(x)

public fun atan2(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.atan2(x, y)

public fun atan2f(a: Float, b: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.atan2f(a, b)

public fun atanf(n: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.atanf(n)

public fun cos(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.cos(x)

public fun cosf(x: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.cosf(x)

public fun cosh(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.cosh(x)

public fun coshf(n: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.coshf(n)

public fun erf(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.erf(x)

public fun erfc(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.erfc(x)

public fun erfcf(x: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.erfcf(x)

public fun erff(x: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.erff(x)

public fun exp(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.float.conv.inttofloat.exp(x.toUInt()).toDouble()

public fun exp2(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.exp2(x)

public fun exp2f(x: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.exp2f(x)

public fun expf(x: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.expf(x)

public fun expm1(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.expm1(x)

public fun expm1f(n: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.expm1f(n)

public fun hypot(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.hypot(x, y)

public fun hypotf(x: Float, y: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.hypotf(x, y)

public fun ldexp(f: Double, n: Int): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.ldexp(f, n)

public fun ldexpf(f: Float, n: Int): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.ldexpf(f, n)

public fun log(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.log(x)

public fun log10(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.log10(x)

public fun log10f(x: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.log10f(x)

public fun log1p(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.log1p(x)

public fun log1pf(n: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.log1pf(n)

public fun log2(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.log2(x)

public fun log2f(x: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.log2f(x)

public fun logf(x: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.logf(x)

public fun pow(x: Double, y: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.float.pow.pow(x, y.toInt())

public fun powf(x: Float, y: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.powf(x, y)

public fun sin(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.sin(x)

public fun sinf(x: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.sinf(x)

public fun sinh(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.sinh(x)

public fun sinhf(n: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.sinhf(n)

public fun tan(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.tan(x)

public fun tanf(n: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.tanf(n)

public fun tanh(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.tanh(x)

public fun tanhf(n: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.tanhf(n)

public fun tgamma(x: Double): Double = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.tgamma(x)

public fun tgammaf(x: Float): Float = io.github.kotlinmania.rustlang.compilerbuiltins.math.libmmath.tgammaf(x)

public fun lgammaR(x: Double, s: RustMutableReference<Int>): Double {
    val s1 = s
    val r = io.github.kotlinmania.rustlang.compilerbuiltins.math.partialavailability.lgammaR(x)
    (s1).write(r.second)
    return r.first
}

public fun lgammafR(x: Float, s: RustMutableReference<Int>): Float {
    val s1 = s
    val r = io.github.kotlinmania.rustlang.compilerbuiltins.math.partialavailability.lgammafR(x)
    (s1).write(r.second)
    return r.first
}
