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
public expect fun powidf2(a: Double, b: Int): Double

public expect fun powisf2(a: kotlin.Float, b: Int): kotlin.Float

// PowerPC uses `kf` rather than `tf` for `f128`.
public expect fun powitf2(a: F128, b: Int): F128

public expect fun cbrt(x: Double): Double

public expect fun cbrtf(n: kotlin.Float): kotlin.Float

public expect fun ceil(x: Double): Double

public expect fun ceilf(x: kotlin.Float): kotlin.Float

public expect fun ceilf128(x: F128): F128

public expect fun ceilf16(x: F16): F16

public expect fun copysign(x: Double, y: Double): Double

public expect fun copysignf(x: kotlin.Float, y: kotlin.Float): kotlin.Float

public expect fun copysignf128(x: F128, y: F128): F128

public expect fun copysignf16(x: F16, y: F16): F16

public expect fun fabs(x: Double): Double

public expect fun fabsf(x: kotlin.Float): kotlin.Float

public expect fun fabsf128(x: F128): F128

public expect fun fabsf16(x: F16): F16

public expect fun fdim(a: Double, b: Double): Double

public expect fun fdimf(a: kotlin.Float, b: kotlin.Float): kotlin.Float

public expect fun fdimf128(x: F128, y: F128): F128

public expect fun fdimf16(x: F16, y: F16): F16

public expect fun floor(x: Double): Double

public expect fun floorf(x: kotlin.Float): kotlin.Float

public expect fun floorf128(x: F128): F128

public expect fun floorf16(x: F16): F16

public expect fun fma(x: Double, y: Double, z: Double): Double

public expect fun fmaf(x: kotlin.Float, y: kotlin.Float, z: kotlin.Float): kotlin.Float

public expect fun fmaf128(x: F128, y: F128, z: F128): F128

public expect fun fmaf16(x: F16, y: F16, z: F16): F16

public expect fun fmax(x: Double, y: Double): Double

public expect fun fmaxf(x: kotlin.Float, y: kotlin.Float): kotlin.Float

public expect fun fmaxf128(x: F128, y: F128): F128

public expect fun fmaxf16(x: F16, y: F16): F16

public expect fun fmaximum(x: Double, y: Double): Double

public expect fun fmaximumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float

public expect fun fmaximumf128(x: F128, y: F128): F128

public expect fun fmaximumf16(x: F16, y: F16): F16

public expect fun fmin(x: Double, y: Double): Double

public expect fun fminf(x: kotlin.Float, y: kotlin.Float): kotlin.Float

public expect fun fminf128(x: F128, y: F128): F128

public expect fun fminf16(x: F16, y: F16): F16

public expect fun fminimum(x: Double, y: Double): Double

public expect fun fminimumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float

public expect fun fminimumf128(x: F128, y: F128): F128

public expect fun fminimumf16(x: F16, y: F16): F16

public expect fun fmod(x: Double, y: Double): Double

public expect fun fmodf(x: kotlin.Float, y: kotlin.Float): kotlin.Float

public expect fun fmodf128(x: F128, y: F128): F128

public expect fun fmodf16(x: F16, y: F16): F16

public expect fun rint(x: Double): Double

public expect fun rintf(x: kotlin.Float): kotlin.Float

public expect fun rintf128(x: F128): F128

public expect fun rintf16(x: F16): F16

public expect fun round(x: Double): Double

public expect fun roundeven(x: Double): Double

public expect fun roundevenf(x: kotlin.Float): kotlin.Float

public expect fun roundevenf128(x: F128): F128

public expect fun roundevenf16(x: F16): F16

public expect fun roundf(x: kotlin.Float): kotlin.Float

public expect fun roundf128(x: F128): F128

public expect fun roundf16(x: F16): F16

public expect fun sqrt(x: Double): Double

public expect fun sqrtf(x: kotlin.Float): kotlin.Float

public expect fun sqrtf128(x: F128): F128

public expect fun sqrtf16(x: F16): F16

public expect fun trunc(x: Double): Double

public expect fun truncf(x: kotlin.Float): kotlin.Float

public expect fun truncf128(x: F128): F128

public expect fun truncf16(x: F16): F16

public expect fun mulsc3(a: kotlin.Float, b: kotlin.Float, c: kotlin.Float, d: kotlin.Float): Complex<kotlin.Float>

public expect fun muldc3(a: Double, b: Double, c: Double, d: Double): Complex<Double>

public expect fun divsc3(a: kotlin.Float, b: kotlin.Float, c: kotlin.Float, d: kotlin.Float): Complex<kotlin.Float>

public expect fun divdc3(a: Double, b: Double, c: Double, d: Double): Complex<Double>

public expect fun rustMulhc3(a: F16, b: F16, c: F16, d: F16): Complex<F16>

public expect fun rustMultc3(a: F128, b: F128, c: F128, d: F128): Complex<F128>

public expect fun rustDivhc3(a: F16, b: F16, c: F16, d: F16): Complex<F16>

public expect fun rustDivtc3(a: F128, b: F128, c: F128, d: F128): Complex<F128>

public expect fun acosf(n: kotlin.Float): kotlin.Float

public expect fun asinf(n: kotlin.Float): kotlin.Float

public expect fun atan2f(a: kotlin.Float, b: kotlin.Float): kotlin.Float

public expect fun atanf(n: kotlin.Float): kotlin.Float

public expect fun cosf(x: kotlin.Float): kotlin.Float

public expect fun coshf(n: kotlin.Float): kotlin.Float

public expect fun erfcf(x: kotlin.Float): kotlin.Float

public expect fun erff(x: kotlin.Float): kotlin.Float

public expect fun exp2f(x: kotlin.Float): kotlin.Float

public expect fun expf(x: kotlin.Float): kotlin.Float

public expect fun expm1f(n: kotlin.Float): kotlin.Float

public expect fun hypotf(x: kotlin.Float, y: kotlin.Float): kotlin.Float

public expect fun ldexpf(f: kotlin.Float, n: Int): kotlin.Float

public expect fun log10f(x: kotlin.Float): kotlin.Float

public expect fun log1pf(n: kotlin.Float): kotlin.Float

public expect fun log2f(x: kotlin.Float): kotlin.Float

public expect fun logf(x: kotlin.Float): kotlin.Float

public expect fun powf(x: kotlin.Float, y: kotlin.Float): kotlin.Float

public expect fun sinf(x: kotlin.Float): kotlin.Float

public expect fun sinhf(n: kotlin.Float): kotlin.Float

public expect fun tanf(n: kotlin.Float): kotlin.Float

public expect fun tanhf(n: kotlin.Float): kotlin.Float

public expect fun tgammaf(x: kotlin.Float): kotlin.Float

public expect fun acos(x: Double): Double

public expect fun asin(x: Double): Double

public expect fun atan(x: Double): Double

public expect fun atan2(x: Double, y: Double): Double

public expect fun cos(x: Double): Double

public expect fun cosh(x: Double): Double

public expect fun erf(x: Double): Double

public expect fun erfc(x: Double): Double

public expect fun exp(x: Double): Double

public expect fun exp2(x: Double): Double

public expect fun expm1(x: Double): Double

public expect fun hypot(x: Double, y: Double): Double

public expect fun ldexp(f: Double, n: Int): Double

public expect fun log(x: Double): Double

public expect fun log10(x: Double): Double

public expect fun log1p(x: Double): Double

public expect fun log2(x: Double): Double

public expect fun pow(x: Double, y: Double): Double

public expect fun sin(x: Double): Double

public expect fun sinh(x: Double): Double

public expect fun tan(x: Double): Double

public expect fun tanh(x: Double): Double

public expect fun tgamma(x: Double): Double

public expect fun acosf128(x: F128): F128

public expect fun asinf128(x: F128): F128

public expect fun atanf128(x: F128): F128

public expect fun cbrtf128(x: F128): F128

public expect fun cosf128(x: F128): F128

public expect fun erff128(x: F128): F128

public expect fun expf128(x: F128): F128

public expect fun exp2f128(x: F128): F128

public expect fun expm1f128(x: F128): F128

public expect fun hypotf128(x: F128, y: F128): F128

public expect fun ldexpf128(f: F128, n: Int): F128

public expect fun log10f128(x: F128): F128

public expect fun log1pf128(x: F128): F128

public expect fun log2f128(x: F128): F128

public expect fun logf128(x: F128): F128

public expect fun powf128(x: F128, y: F128): F128

public expect fun sinf128(x: F128): F128

public expect fun tanf128(x: F128): F128

public expect fun tanhf128(x: F128): F128

public expect fun tgammaf128(x: F128): F128
