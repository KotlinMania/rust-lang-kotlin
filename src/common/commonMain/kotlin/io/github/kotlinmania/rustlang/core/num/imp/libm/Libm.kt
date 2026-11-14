// port-lint: source num/imp/libm.rs
package io.github.kotlinmania.rustlang.core.num.imp.libm

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.num.complex.Complex

// Bindings to math functions provided by the system `libm` or by the `libm` crate, exposed
// via `compiler - builtins`.
//
// The functions in the root of this module are "guaranteed" to be available; see the
// `fullAvailability` module in compiler-builtins for details.

// SAFETY: These symbols have standard interfaces in C and are defined by `libm`, or are
// provided by `compiler - builtins` on unsupported platforms.
// This list reflects what is available rather than what is consumed.
internal expect fun powidf2(a: Double, b: Int): Double

internal expect fun powisf2(a: kotlin.Float, b: Int): kotlin.Float

// PowerPC uses `kf` rather than `tf` for `f128`.
internal expect fun powitf2(a: F128, b: Int): F128

internal expect fun cbrt(x: Double): Double

internal expect fun cbrtf(n: kotlin.Float): kotlin.Float

internal expect fun ceil(x: Double): Double

internal expect fun ceilf(x: kotlin.Float): kotlin.Float

internal expect fun ceilf128(x: F128): F128

internal expect fun ceilf16(x: F16): F16

internal expect fun copysign(x: Double, y: Double): Double

internal expect fun copysignf(x: kotlin.Float, y: kotlin.Float): kotlin.Float

internal expect fun copysignf128(x: F128, y: F128): F128

internal expect fun copysignf16(x: F16, y: F16): F16

internal expect fun fabs(x: Double): Double

internal expect fun fabsf(x: kotlin.Float): kotlin.Float

internal expect fun fabsf128(x: F128): F128

internal expect fun fabsf16(x: F16): F16

internal expect fun fdim(a: Double, b: Double): Double

internal expect fun fdimf(a: kotlin.Float, b: kotlin.Float): kotlin.Float

internal expect fun fdimf128(x: F128, y: F128): F128

internal expect fun fdimf16(x: F16, y: F16): F16

internal expect fun floor(x: Double): Double

internal expect fun floorf(x: kotlin.Float): kotlin.Float

internal expect fun floorf128(x: F128): F128

internal expect fun floorf16(x: F16): F16

internal expect fun fma(x: Double, y: Double, z: Double): Double

internal expect fun fmaf(x: kotlin.Float, y: kotlin.Float, z: kotlin.Float): kotlin.Float

internal expect fun fmaf128(x: F128, y: F128, z: F128): F128

internal expect fun fmaf16(x: F16, y: F16, z: F16): F16

internal expect fun fmax(x: Double, y: Double): Double

internal expect fun fmaxf(x: kotlin.Float, y: kotlin.Float): kotlin.Float

internal expect fun fmaxf128(x: F128, y: F128): F128

internal expect fun fmaxf16(x: F16, y: F16): F16

internal expect fun fmaximum(x: Double, y: Double): Double

internal expect fun fmaximumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float

internal expect fun fmaximumf128(x: F128, y: F128): F128

internal expect fun fmaximumf16(x: F16, y: F16): F16

internal expect fun fmin(x: Double, y: Double): Double

internal expect fun fminf(x: kotlin.Float, y: kotlin.Float): kotlin.Float

internal expect fun fminf128(x: F128, y: F128): F128

internal expect fun fminf16(x: F16, y: F16): F16

internal expect fun fminimum(x: Double, y: Double): Double

internal expect fun fminimumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float

internal expect fun fminimumf128(x: F128, y: F128): F128

internal expect fun fminimumf16(x: F16, y: F16): F16

internal expect fun fmod(x: Double, y: Double): Double

internal expect fun fmodf(x: kotlin.Float, y: kotlin.Float): kotlin.Float

internal expect fun fmodf128(x: F128, y: F128): F128

internal expect fun fmodf16(x: F16, y: F16): F16

internal expect fun rint(x: Double): Double

internal expect fun rintf(x: kotlin.Float): kotlin.Float

internal expect fun rintf128(x: F128): F128

internal expect fun rintf16(x: F16): F16

internal expect fun round(x: Double): Double

internal expect fun roundeven(x: Double): Double

internal expect fun roundevenf(x: kotlin.Float): kotlin.Float

internal expect fun roundevenf128(x: F128): F128

internal expect fun roundevenf16(x: F16): F16

internal expect fun roundf(x: kotlin.Float): kotlin.Float

internal expect fun roundf128(x: F128): F128

internal expect fun roundf16(x: F16): F16

internal expect fun sqrt(x: Double): Double

internal expect fun sqrtf(x: kotlin.Float): kotlin.Float

internal expect fun sqrtf128(x: F128): F128

internal expect fun sqrtf16(x: F16): F16

internal expect fun trunc(x: Double): Double

internal expect fun truncf(x: kotlin.Float): kotlin.Float

internal expect fun truncf128(x: F128): F128

internal expect fun truncf16(x: F16): F16

internal expect fun mulsc3(a: kotlin.Float, b: kotlin.Float, c: kotlin.Float, d: kotlin.Float): Complex<kotlin.Float>

internal expect fun muldc3(a: Double, b: Double, c: Double, d: Double): Complex<Double>

internal expect fun divsc3(a: kotlin.Float, b: kotlin.Float, c: kotlin.Float, d: kotlin.Float): Complex<kotlin.Float>

internal expect fun divdc3(a: Double, b: Double, c: Double, d: Double): Complex<Double>

internal expect fun rustMulhc3(a: F16, b: F16, c: F16, d: F16): Complex<F16>

internal expect fun rustMultc3(a: F128, b: F128, c: F128, d: F128): Complex<F128>

internal expect fun rustDivhc3(a: F16, b: F16, c: F16, d: F16): Complex<F16>

internal expect fun rustDivtc3(a: F128, b: F128, c: F128, d: F128): Complex<F128>

internal expect fun acos(x: Double): Double

internal expect fun asin(x: Double): Double

internal expect fun atan(x: Double): Double

internal expect fun atan2(x: Double, y: Double): Double

internal expect fun cos(x: Double): Double

internal expect fun cosh(x: Double): Double

internal expect fun erf(x: Double): Double

internal expect fun erfc(x: Double): Double

internal expect fun exp(x: Double): Double

internal expect fun exp2(x: Double): Double

internal expect fun expm1(x: Double): Double

internal expect fun hypot(x: Double, y: Double): Double

internal expect fun ldexp(f: Double, n: Int): Double

internal expect fun log(x: Double): Double

internal expect fun log10(x: Double): Double

internal expect fun log1p(x: Double): Double

internal expect fun log2(x: Double): Double

internal expect fun pow(x: Double, y: Double): Double

internal expect fun sin(x: Double): Double

internal expect fun sinh(x: Double): Double

internal expect fun tan(x: Double): Double

internal expect fun tanh(x: Double): Double

internal expect fun tgamma(x: Double): Double

internal expect fun acosf128(x: F128): F128

internal expect fun asinf128(x: F128): F128

internal expect fun atanf128(x: F128): F128

internal expect fun cbrtf128(x: F128): F128

internal expect fun cosf128(x: F128): F128

internal expect fun erff128(x: F128): F128

internal expect fun expf128(x: F128): F128

internal expect fun exp2f128(x: F128): F128

internal expect fun expm1f128(x: F128): F128

internal expect fun hypotf128(x: F128, y: F128): F128

internal expect fun ldexpf128(f: F128, n: Int): F128

internal expect fun log10f128(x: F128): F128

internal expect fun log1pf128(x: F128): F128

internal expect fun log2f128(x: F128): F128

internal expect fun logf128(x: F128): F128

internal expect fun powf128(x: F128, y: F128): F128

internal expect fun sinf128(x: F128): F128

internal expect fun tanf128(x: F128): F128

internal expect fun tanhf128(x: F128): F128

internal expect fun tgammaf128(x: F128): F128
