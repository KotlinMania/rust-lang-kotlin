package io.github.kotlinmania.rustlang.compilerbuiltins.math

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

// Weak linkage is unreliable on Windows and Apple, so we don't expose symbols that we know
// the system libc provides in order to avoid conflicts.
// Windows and MacOS do not yet expose roundeven and IEEE 754-2019 `maximum` / `minimum`,
// however, so we still provide a fallback.
// allow for windows (and other targets)
public fun ceilf16(x: F16): F16 = ceilf16(x)

public fun copysignf16(x: F16, y: F16): F16 = copysignf16(x, y)

public fun fabsf16(x: F16): F16 = fabsf16(x)

public fun fdimf16(x: F16, y: F16): F16 = fdimf16(x, y)

public fun floorf16(x: F16): F16 = floorf16(x)

public fun fmaf16(x: F16, y: F16, z: F16): F16 = fmaf16(x, y, z)

public fun fmaxf16(x: F16, y: F16): F16 = fmaxf16(x, y)

public fun fmaximumNumf16(x: F16, y: F16): F16 = fmaximumNumf16(x, y)

public fun fmaximumf16(x: F16, y: F16): F16 = fmaximumf16(x, y)

public fun fminf16(x: F16, y: F16): F16 = fminf16(x, y)

public fun fminimumNumf16(x: F16, y: F16): F16 = fminimumNumf16(x, y)

public fun fminimumf16(x: F16, y: F16): F16 = fminimumf16(x, y)

public fun fmodf16(x: F16, y: F16): F16 = fmodf16(x, y)

public fun rintf16(x: F16): F16 = rintf16(x)

public fun roundevenf16(x: F16): F16 = roundevenf16(x)

public fun roundf16(x: F16): F16 = roundf16(x)

public fun sqrtf16(x: F16): F16 = sqrtf16(x)

public fun truncf16(x: F16): F16 = truncf16(x)

public fun cbrtf(n: Float): Float = cbrtf(n)

public fun ceilf(x: Float): Float = ceilf(x)

public fun copysignf(x: Float, y: Float): Float = copysignf(x, y)

public fun fabsf(x: Float): Float = fabsf(x)

public fun fdimf(a: Float, b: Float): Float = fdimf(a, b)

public fun floorf(x: Float): Float = floorf(x)

public fun fmaf(x: Float, y: Float, z: Float): Float = fmaf(x, y, z)

public fun fmaxf(x: Float, y: Float): Float = fmaxf(x, y)

public fun fminf(x: Float, y: Float): Float = fminf(x, y)

public fun fmodf(x: Float, y: Float): Float = fmodf(x, y)

public fun rintf(x: Float): Float = rintf(x)

public fun roundf(x: Float): Float = roundf(x)

public fun sqrtf(x: Float): Float = sqrtf(x)

public fun truncf(x: Float): Float = truncf(x)

public fun cbrt(x: Double): Double = cbrt(x)

public fun ceil(x: Double): Double = ceil(x)

public fun copysign(x: Double, y: Double): Double = copysign(x, y)

public fun fabs(x: Double): Double = fabs(x)

public fun fdim(a: Double, b: Double): Double = fdim(a, b)

public fun floor(x: Double): Double = floor(x)

public fun fma(x: Double, y: Double, z: Double): Double = fma(x, y, z)

public fun fmax(x: Double, y: Double): Double = fmax(x, y)

public fun fmin(x: Double, y: Double): Double = fmin(x, y)

public fun fmod(x: Double, y: Double): Double = fmod(x, y)

public fun rint(x: Double): Double = rint(x)

public fun round(x: Double): Double = round(x)

public fun sqrt(x: Double): Double = sqrt(x)

public fun trunc(x: Double): Double = trunc(x)

public fun fmaximum(x: Double, y: Double): Double = fmaximum(x, y)

public fun fmaximumNum(x: Double, y: Double): Double = fmaximumNum(x, y)

public fun fmaximumNumf(x: Float, y: Float): Float = fmaximumNumf(x, y)

public fun fmaximumf(x: Float, y: Float): Float = fmaximumf(x, y)

public fun fminimum(x: Double, y: Double): Double = fminimum(x, y)

public fun fminimumNum(x: Double, y: Double): Double = fminimumNum(x, y)

public fun fminimumNumf(x: Float, y: Float): Float = fminimumNumf(x, y)

public fun fminimumf(x: Float, y: Float): Float = fminimumf(x, y)

public fun roundeven(x: Double): Double = roundeven(x)

public fun roundevenf(x: Float): Float = roundevenf(x)

public fun ceilf128(x: F128): F128 = ceilf128(x)

public fun copysignf128(x: F128, y: F128): F128 = copysignf128(x, y)

public fun fabsf128(x: F128): F128 = fabsf128(x)

public fun fdimf128(x: F128, y: F128): F128 = fdimf128(x, y)

public fun floorf128(x: F128): F128 = floorf128(x)

public fun fmaf128(x: F128, y: F128, z: F128): F128 = fmaf128(x, y, z)

public fun fmaxf128(x: F128, y: F128): F128 = fmaxf128(x, y)

public fun fmaximumNumf128(x: F128, y: F128): F128 = fmaximumNumf128(x, y)

public fun fmaximumf128(x: F128, y: F128): F128 = fmaximumf128(x, y)

public fun fminf128(x: F128, y: F128): F128 = fminf128(x, y)

public fun fminimumNumf128(x: F128, y: F128): F128 = fminimumNumf128(x, y)

public fun fminimumf128(x: F128, y: F128): F128 = fminimumf128(x, y)

public fun fmodf128(x: F128, y: F128): F128 = fmodf128(x, y)

public fun rintf128(x: F128): F128 = rintf128(x)

public fun roundevenf128(x: F128): F128 = roundevenf128(x)

public fun roundf128(x: F128): F128 = roundf128(x)

public fun sqrtf128(x: F128): F128 = sqrtf128(x)

public fun truncf128(x: F128): F128 = truncf128(x)

public fun acos(x: Double): Double = acos(x)

public fun acosf(n: Float): Float = acosf(n)

public fun acosh(x: Double): Double = acosh(x)

public fun acoshf(x: Float): Float = acoshf(x)

public fun asin(x: Double): Double = asin(x)

public fun asinf(n: Float): Float = asinf(n)

public fun asinh(x: Double): Double = asinh(x)

public fun asinhf(x: Float): Float = asinhf(x)

public fun atan(x: Double): Double = atan(x)

public fun atan2(x: Double, y: Double): Double = atan2(x, y)

public fun atan2f(a: Float, b: Float): Float = atan2f(a, b)

public fun atanf(n: Float): Float = atanf(n)

public fun cos(x: Double): Double = cos(x)

public fun cosf(x: Float): Float = cosf(x)

public fun cosh(x: Double): Double = cosh(x)

public fun coshf(n: Float): Float = coshf(n)

public fun erf(x: Double): Double = erf(x)

public fun erfc(x: Double): Double = erfc(x)

public fun erfcf(x: Float): Float = erfcf(x)

public fun erff(x: Float): Float = erff(x)

public fun exp(x: Double): Double = exp(x.toUInt()).toDouble()

public fun exp2(x: Double): Double = exp2(x)

public fun exp2f(x: Float): Float = exp2f(x)

public fun expf(x: Float): Float = expf(x)

public fun expm1(x: Double): Double = expm1(x)

public fun expm1f(n: Float): Float = expm1f(n)

public fun hypot(x: Double, y: Double): Double = hypot(x, y)

public fun hypotf(x: Float, y: Float): Float = hypotf(x, y)

public fun ldexp(f: Double, n: Int): Double = ldexp(f, n)

public fun ldexpf(f: Float, n: Int): Float = ldexpf(f, n)

public fun log(x: Double): Double = log(x)

public fun log10(x: Double): Double = log10(x)

public fun log10f(x: Float): Float = log10f(x)

public fun log1p(x: Double): Double = log1p(x)

public fun log1pf(n: Float): Float = log1pf(n)

public fun log2(x: Double): Double = log2(x)

public fun log2f(x: Float): Float = log2f(x)

public fun logf(x: Float): Float = logf(x)

public fun pow(x: Double, y: Double): Double = pow(x, y.toInt())

public fun powf(x: Float, y: Float): Float = powf(x, y)

public fun sin(x: Double): Double = sin(x)

public fun sinf(x: Float): Float = sinf(x)

public fun sinh(x: Double): Double = sinh(x)

public fun sinhf(n: Float): Float = sinhf(n)

public fun tan(x: Double): Double = tan(x)

public fun tanf(n: Float): Float = tanf(n)

public fun tanh(x: Double): Double = tanh(x)

public fun tanhf(n: Float): Float = tanhf(n)

public fun tgamma(x: Double): Double = tgamma(x)

public fun tgammaf(x: Float): Float = tgammaf(x)

public fun lgammaR(x: Double, s: RustMutableReference<Int>): Double {
    val s1 = s
    val r = lgammaR(x)
    (s1).write(r.second)
    return r.first
}

public fun lgammafR(x: Float, s: RustMutableReference<Int>): Float {
    val s1 = s
    val r = lgammafR(x)
    (s1).write(r.second)
    return r.first
}
