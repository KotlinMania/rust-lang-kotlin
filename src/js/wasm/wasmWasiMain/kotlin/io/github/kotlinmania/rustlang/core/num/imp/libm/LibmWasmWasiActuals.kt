@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.core.num.imp.libm

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.num.complex.Complex

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmPowidf2")
private external fun wasiRustlangCoreNumImpLibmPowidf2(a: Double, b: Int): Double

public actual fun powidf2(a: Double, b: Int): Double =
    wasiRustlangCoreNumImpLibmPowidf2(a, b).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmPowisf2")
private external fun wasiRustlangCoreNumImpLibmPowisf2(a: Float, b: Int): Float

public actual fun powisf2(a: kotlin.Float, b: Int): kotlin.Float =
    wasiRustlangCoreNumImpLibmPowisf2(a, b).toFloat()

public actual fun powitf2(a: F128, b: Int): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmPowitf2")

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmCbrt")
private external fun wasiRustlangCoreNumImpLibmCbrt(x: Double): Double

public actual fun cbrt(x: Double): Double =
    wasiRustlangCoreNumImpLibmCbrt(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmCbrtf")
private external fun wasiRustlangCoreNumImpLibmCbrtf(n: Float): Float

public actual fun cbrtf(n: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmCbrtf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmCeil")
private external fun wasiRustlangCoreNumImpLibmCeil(x: Double): Double

public actual fun ceil(x: Double): Double =
    wasiRustlangCoreNumImpLibmCeil(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmCeilf")
private external fun wasiRustlangCoreNumImpLibmCeilf(x: Float): Float

public actual fun ceilf(x: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmCeilf(x).toFloat()

public actual fun ceilf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmCeilf128")

public actual fun ceilf16(x: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmCeilf16")

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmCopysign")
private external fun wasiRustlangCoreNumImpLibmCopysign(x: Double, y: Double): Double

public actual fun copysign(x: Double, y: Double): Double =
    wasiRustlangCoreNumImpLibmCopysign(x, y).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmCopysignf")
private external fun wasiRustlangCoreNumImpLibmCopysignf(x: Float, y: Float): Float

public actual fun copysignf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmCopysignf(x, y).toFloat()

public actual fun copysignf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmCopysignf128")

public actual fun copysignf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmCopysignf16")

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmFabs")
private external fun wasiRustlangCoreNumImpLibmFabs(x: Double): Double

public actual fun fabs(x: Double): Double =
    wasiRustlangCoreNumImpLibmFabs(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmFabsf")
private external fun wasiRustlangCoreNumImpLibmFabsf(x: Float): Float

public actual fun fabsf(x: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmFabsf(x).toFloat()

public actual fun fabsf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmFabsf128")

public actual fun fabsf16(x: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmFabsf16")

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmFdim")
private external fun wasiRustlangCoreNumImpLibmFdim(a: Double, b: Double): Double

public actual fun fdim(a: Double, b: Double): Double =
    wasiRustlangCoreNumImpLibmFdim(a, b).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmFdimf")
private external fun wasiRustlangCoreNumImpLibmFdimf(a: Float, b: Float): Float

public actual fun fdimf(a: kotlin.Float, b: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmFdimf(a, b).toFloat()

public actual fun fdimf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmFdimf128")

public actual fun fdimf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmFdimf16")

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmFloor")
private external fun wasiRustlangCoreNumImpLibmFloor(x: Double): Double

public actual fun floor(x: Double): Double =
    wasiRustlangCoreNumImpLibmFloor(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmFloorf")
private external fun wasiRustlangCoreNumImpLibmFloorf(x: Float): Float

public actual fun floorf(x: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmFloorf(x).toFloat()

public actual fun floorf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmFloorf128")

public actual fun floorf16(x: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmFloorf16")

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmFma")
private external fun wasiRustlangCoreNumImpLibmFma(x: Double, y: Double, z: Double): Double

public actual fun fma(x: Double, y: Double, z: Double): Double =
    wasiRustlangCoreNumImpLibmFma(x, y, z).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmFmaf")
private external fun wasiRustlangCoreNumImpLibmFmaf(x: Float, y: Float, z: Float): Float

public actual fun fmaf(x: kotlin.Float, y: kotlin.Float, z: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmFmaf(x, y, z).toFloat()

public actual fun fmaf128(x: F128, y: F128, z: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmFmaf128")

public actual fun fmaf16(x: F16, y: F16, z: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmFmaf16")

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmFmax")
private external fun wasiRustlangCoreNumImpLibmFmax(x: Double, y: Double): Double

public actual fun fmax(x: Double, y: Double): Double =
    wasiRustlangCoreNumImpLibmFmax(x, y).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmFmaxf")
private external fun wasiRustlangCoreNumImpLibmFmaxf(x: Float, y: Float): Float

public actual fun fmaxf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmFmaxf(x, y).toFloat()

public actual fun fmaxf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmFmaxf128")

public actual fun fmaxf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmFmaxf16")

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmFmaximum")
private external fun wasiRustlangCoreNumImpLibmFmaximum(x: Double, y: Double): Double

public actual fun fmaximum(x: Double, y: Double): Double =
    wasiRustlangCoreNumImpLibmFmaximum(x, y).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmFmaximumf")
private external fun wasiRustlangCoreNumImpLibmFmaximumf(x: Float, y: Float): Float

public actual fun fmaximumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmFmaximumf(x, y).toFloat()

public actual fun fmaximumf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmFmaximumf128")

public actual fun fmaximumf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmFmaximumf16")

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmFmin")
private external fun wasiRustlangCoreNumImpLibmFmin(x: Double, y: Double): Double

public actual fun fmin(x: Double, y: Double): Double =
    wasiRustlangCoreNumImpLibmFmin(x, y).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmFminf")
private external fun wasiRustlangCoreNumImpLibmFminf(x: Float, y: Float): Float

public actual fun fminf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmFminf(x, y).toFloat()

public actual fun fminf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmFminf128")

public actual fun fminf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmFminf16")

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmFminimum")
private external fun wasiRustlangCoreNumImpLibmFminimum(x: Double, y: Double): Double

public actual fun fminimum(x: Double, y: Double): Double =
    wasiRustlangCoreNumImpLibmFminimum(x, y).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmFminimumf")
private external fun wasiRustlangCoreNumImpLibmFminimumf(x: Float, y: Float): Float

public actual fun fminimumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmFminimumf(x, y).toFloat()

public actual fun fminimumf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmFminimumf128")

public actual fun fminimumf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmFminimumf16")

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmFmod")
private external fun wasiRustlangCoreNumImpLibmFmod(x: Double, y: Double): Double

public actual fun fmod(x: Double, y: Double): Double =
    wasiRustlangCoreNumImpLibmFmod(x, y).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmFmodf")
private external fun wasiRustlangCoreNumImpLibmFmodf(x: Float, y: Float): Float

public actual fun fmodf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmFmodf(x, y).toFloat()

public actual fun fmodf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmFmodf128")

public actual fun fmodf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmFmodf16")

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmRint")
private external fun wasiRustlangCoreNumImpLibmRint(x: Double): Double

public actual fun rint(x: Double): Double =
    wasiRustlangCoreNumImpLibmRint(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmRintf")
private external fun wasiRustlangCoreNumImpLibmRintf(x: Float): Float

public actual fun rintf(x: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmRintf(x).toFloat()

public actual fun rintf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmRintf128")

public actual fun rintf16(x: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmRintf16")

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmRound")
private external fun wasiRustlangCoreNumImpLibmRound(x: Double): Double

public actual fun round(x: Double): Double =
    wasiRustlangCoreNumImpLibmRound(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmRoundeven")
private external fun wasiRustlangCoreNumImpLibmRoundeven(x: Double): Double

public actual fun roundeven(x: Double): Double =
    wasiRustlangCoreNumImpLibmRoundeven(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmRoundevenf")
private external fun wasiRustlangCoreNumImpLibmRoundevenf(x: Float): Float

public actual fun roundevenf(x: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmRoundevenf(x).toFloat()

public actual fun roundevenf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmRoundevenf128")

public actual fun roundevenf16(x: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmRoundevenf16")

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmRoundf")
private external fun wasiRustlangCoreNumImpLibmRoundf(x: Float): Float

public actual fun roundf(x: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmRoundf(x).toFloat()

public actual fun roundf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmRoundf128")

public actual fun roundf16(x: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmRoundf16")

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmSqrt")
private external fun wasiRustlangCoreNumImpLibmSqrt(x: Double): Double

public actual fun sqrt(x: Double): Double =
    wasiRustlangCoreNumImpLibmSqrt(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmSqrtf")
private external fun wasiRustlangCoreNumImpLibmSqrtf(x: Float): Float

public actual fun sqrtf(x: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmSqrtf(x).toFloat()

public actual fun sqrtf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmSqrtf128")

public actual fun sqrtf16(x: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmSqrtf16")

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmTrunc")
private external fun wasiRustlangCoreNumImpLibmTrunc(x: Double): Double

public actual fun trunc(x: Double): Double =
    wasiRustlangCoreNumImpLibmTrunc(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmTruncf")
private external fun wasiRustlangCoreNumImpLibmTruncf(x: Float): Float

public actual fun truncf(x: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmTruncf(x).toFloat()

public actual fun truncf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmTruncf128")

public actual fun truncf16(x: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmTruncf16")

public actual fun mulsc3(a: kotlin.Float, b: kotlin.Float, c: kotlin.Float, d: kotlin.Float): Complex<kotlin.Float> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmMulsc3")

public actual fun muldc3(a: Double, b: Double, c: Double, d: Double): Complex<Double> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmMuldc3")

public actual fun divsc3(a: kotlin.Float, b: kotlin.Float, c: kotlin.Float, d: kotlin.Float): Complex<kotlin.Float> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmDivsc3")

public actual fun divdc3(a: Double, b: Double, c: Double, d: Double): Complex<Double> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmDivdc3")

public actual fun rustMulhc3(a: F16, b: F16, c: F16, d: F16): Complex<F16> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmRustMulhc3")

public actual fun rustMultc3(a: F128, b: F128, c: F128, d: F128): Complex<F128> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmRustMultc3")

public actual fun rustDivhc3(a: F16, b: F16, c: F16, d: F16): Complex<F16> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmRustDivhc3")

public actual fun rustDivtc3(a: F128, b: F128, c: F128, d: F128): Complex<F128> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmRustDivtc3")

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmAcosf")
private external fun wasiRustlangCoreNumImpLibmAcosf(n: Float): Float

public actual fun acosf(n: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmAcosf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmAsinf")
private external fun wasiRustlangCoreNumImpLibmAsinf(n: Float): Float

public actual fun asinf(n: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmAsinf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmAtan2f")
private external fun wasiRustlangCoreNumImpLibmAtan2f(a: Float, b: Float): Float

public actual fun atan2f(a: kotlin.Float, b: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmAtan2f(a, b).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmAtanf")
private external fun wasiRustlangCoreNumImpLibmAtanf(n: Float): Float

public actual fun atanf(n: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmAtanf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmCosf")
private external fun wasiRustlangCoreNumImpLibmCosf(x: Float): Float

public actual fun cosf(x: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmCosf(x).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmCoshf")
private external fun wasiRustlangCoreNumImpLibmCoshf(n: Float): Float

public actual fun coshf(n: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmCoshf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmErfcf")
private external fun wasiRustlangCoreNumImpLibmErfcf(x: Float): Float

public actual fun erfcf(x: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmErfcf(x).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmErff")
private external fun wasiRustlangCoreNumImpLibmErff(x: Float): Float

public actual fun erff(x: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmErff(x).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmExp2f")
private external fun wasiRustlangCoreNumImpLibmExp2f(x: Float): Float

public actual fun exp2f(x: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmExp2f(x).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmExpf")
private external fun wasiRustlangCoreNumImpLibmExpf(x: Float): Float

public actual fun expf(x: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmExpf(x).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmExpm1f")
private external fun wasiRustlangCoreNumImpLibmExpm1f(n: Float): Float

public actual fun expm1f(n: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmExpm1f(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmHypotf")
private external fun wasiRustlangCoreNumImpLibmHypotf(x: Float, y: Float): Float

public actual fun hypotf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmHypotf(x, y).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmLdexpf")
private external fun wasiRustlangCoreNumImpLibmLdexpf(f: Float, n: Int): Float

public actual fun ldexpf(f: kotlin.Float, n: Int): kotlin.Float =
    wasiRustlangCoreNumImpLibmLdexpf(f, n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmLog10f")
private external fun wasiRustlangCoreNumImpLibmLog10f(x: Float): Float

public actual fun log10f(x: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmLog10f(x).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmLog1pf")
private external fun wasiRustlangCoreNumImpLibmLog1pf(n: Float): Float

public actual fun log1pf(n: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmLog1pf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmLog2f")
private external fun wasiRustlangCoreNumImpLibmLog2f(x: Float): Float

public actual fun log2f(x: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmLog2f(x).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmLogf")
private external fun wasiRustlangCoreNumImpLibmLogf(x: Float): Float

public actual fun logf(x: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmLogf(x).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmPowf")
private external fun wasiRustlangCoreNumImpLibmPowf(x: Float, y: Float): Float

public actual fun powf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmPowf(x, y).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmSinf")
private external fun wasiRustlangCoreNumImpLibmSinf(x: Float): Float

public actual fun sinf(x: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmSinf(x).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmSinhf")
private external fun wasiRustlangCoreNumImpLibmSinhf(n: Float): Float

public actual fun sinhf(n: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmSinhf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmTanf")
private external fun wasiRustlangCoreNumImpLibmTanf(n: Float): Float

public actual fun tanf(n: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmTanf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmTanhf")
private external fun wasiRustlangCoreNumImpLibmTanhf(n: Float): Float

public actual fun tanhf(n: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmTanhf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmTgammaf")
private external fun wasiRustlangCoreNumImpLibmTgammaf(x: Float): Float

public actual fun tgammaf(x: kotlin.Float): kotlin.Float =
    wasiRustlangCoreNumImpLibmTgammaf(x).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmAcos")
private external fun wasiRustlangCoreNumImpLibmAcos(x: Double): Double

public actual fun acos(x: Double): Double =
    wasiRustlangCoreNumImpLibmAcos(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmAsin")
private external fun wasiRustlangCoreNumImpLibmAsin(x: Double): Double

public actual fun asin(x: Double): Double =
    wasiRustlangCoreNumImpLibmAsin(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmAtan")
private external fun wasiRustlangCoreNumImpLibmAtan(x: Double): Double

public actual fun atan(x: Double): Double =
    wasiRustlangCoreNumImpLibmAtan(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmAtan2")
private external fun wasiRustlangCoreNumImpLibmAtan2(x: Double, y: Double): Double

public actual fun atan2(x: Double, y: Double): Double =
    wasiRustlangCoreNumImpLibmAtan2(x, y).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmCos")
private external fun wasiRustlangCoreNumImpLibmCos(x: Double): Double

public actual fun cos(x: Double): Double =
    wasiRustlangCoreNumImpLibmCos(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmCosh")
private external fun wasiRustlangCoreNumImpLibmCosh(x: Double): Double

public actual fun cosh(x: Double): Double =
    wasiRustlangCoreNumImpLibmCosh(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmErf")
private external fun wasiRustlangCoreNumImpLibmErf(x: Double): Double

public actual fun erf(x: Double): Double =
    wasiRustlangCoreNumImpLibmErf(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmErfc")
private external fun wasiRustlangCoreNumImpLibmErfc(x: Double): Double

public actual fun erfc(x: Double): Double =
    wasiRustlangCoreNumImpLibmErfc(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmExp")
private external fun wasiRustlangCoreNumImpLibmExp(x: Double): Double

public actual fun exp(x: Double): Double =
    wasiRustlangCoreNumImpLibmExp(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmExp2")
private external fun wasiRustlangCoreNumImpLibmExp2(x: Double): Double

public actual fun exp2(x: Double): Double =
    wasiRustlangCoreNumImpLibmExp2(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmExpm1")
private external fun wasiRustlangCoreNumImpLibmExpm1(x: Double): Double

public actual fun expm1(x: Double): Double =
    wasiRustlangCoreNumImpLibmExpm1(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmHypot")
private external fun wasiRustlangCoreNumImpLibmHypot(x: Double, y: Double): Double

public actual fun hypot(x: Double, y: Double): Double =
    wasiRustlangCoreNumImpLibmHypot(x, y).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmLdexp")
private external fun wasiRustlangCoreNumImpLibmLdexp(f: Double, n: Int): Double

public actual fun ldexp(f: Double, n: Int): Double =
    wasiRustlangCoreNumImpLibmLdexp(f, n).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmLog")
private external fun wasiRustlangCoreNumImpLibmLog(x: Double): Double

public actual fun log(x: Double): Double =
    wasiRustlangCoreNumImpLibmLog(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmLog10")
private external fun wasiRustlangCoreNumImpLibmLog10(x: Double): Double

public actual fun log10(x: Double): Double =
    wasiRustlangCoreNumImpLibmLog10(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmLog1p")
private external fun wasiRustlangCoreNumImpLibmLog1p(x: Double): Double

public actual fun log1p(x: Double): Double =
    wasiRustlangCoreNumImpLibmLog1p(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmLog2")
private external fun wasiRustlangCoreNumImpLibmLog2(x: Double): Double

public actual fun log2(x: Double): Double =
    wasiRustlangCoreNumImpLibmLog2(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmPow")
private external fun wasiRustlangCoreNumImpLibmPow(x: Double, y: Double): Double

public actual fun pow(x: Double, y: Double): Double =
    wasiRustlangCoreNumImpLibmPow(x, y).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmSin")
private external fun wasiRustlangCoreNumImpLibmSin(x: Double): Double

public actual fun sin(x: Double): Double =
    wasiRustlangCoreNumImpLibmSin(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmSinh")
private external fun wasiRustlangCoreNumImpLibmSinh(x: Double): Double

public actual fun sinh(x: Double): Double =
    wasiRustlangCoreNumImpLibmSinh(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmTan")
private external fun wasiRustlangCoreNumImpLibmTan(x: Double): Double

public actual fun tan(x: Double): Double =
    wasiRustlangCoreNumImpLibmTan(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmTanh")
private external fun wasiRustlangCoreNumImpLibmTanh(x: Double): Double

public actual fun tanh(x: Double): Double =
    wasiRustlangCoreNumImpLibmTanh(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangCoreNumImpLibmTgamma")
private external fun wasiRustlangCoreNumImpLibmTgamma(x: Double): Double

public actual fun tgamma(x: Double): Double =
    wasiRustlangCoreNumImpLibmTgamma(x).toDouble()

public actual fun acosf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmAcosf128")

public actual fun asinf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmAsinf128")

public actual fun atanf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmAtanf128")

public actual fun cbrtf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmCbrtf128")

public actual fun cosf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmCosf128")

public actual fun erff128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmErff128")

public actual fun expf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmExpf128")

public actual fun exp2f128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmExp2f128")

public actual fun expm1f128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmExpm1f128")

public actual fun hypotf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmHypotf128")

public actual fun ldexpf128(f: F128, n: Int): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmLdexpf128")

public actual fun log10f128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmLog10f128")

public actual fun log1pf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmLog1pf128")

public actual fun log2f128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmLog2f128")

public actual fun logf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmLogf128")

public actual fun powf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmPowf128")

public actual fun sinf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmSinf128")

public actual fun tanf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmTanf128")

public actual fun tanhf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmTanhf128")

public actual fun tgammaf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangCoreNumImpLibmTgammaf128")
