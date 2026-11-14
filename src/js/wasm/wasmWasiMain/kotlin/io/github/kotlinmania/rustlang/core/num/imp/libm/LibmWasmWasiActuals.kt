@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.core.num.imp.libm

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.num.complex.Complex

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmPowidf2")
private external fun wasiCoreNumImpLibmPowidf2(a: Double, b: Int): Double

public actual fun powidf2(a: Double, b: Int): Double =
    wasiCoreNumImpLibmPowidf2(a, b).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmPowisf2")
private external fun wasiCoreNumImpLibmPowisf2(a: Float, b: Int): Float

public actual fun powisf2(a: kotlin.Float, b: Int): kotlin.Float =
    wasiCoreNumImpLibmPowisf2(a, b).toFloat()

public actual fun powitf2(a: F128, b: Int): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmPowitf2")

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmCbrt")
private external fun wasiCoreNumImpLibmCbrt(x: Double): Double

public actual fun cbrt(x: Double): Double =
    wasiCoreNumImpLibmCbrt(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmCbrtf")
private external fun wasiCoreNumImpLibmCbrtf(n: Float): Float

public actual fun cbrtf(n: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmCbrtf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmCeil")
private external fun wasiCoreNumImpLibmCeil(x: Double): Double

public actual fun ceil(x: Double): Double =
    wasiCoreNumImpLibmCeil(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmCeilf")
private external fun wasiCoreNumImpLibmCeilf(x: Float): Float

public actual fun ceilf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmCeilf(x).toFloat()

public actual fun ceilf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmCeilf128")

public actual fun ceilf16(x: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmCeilf16")

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmCopysign")
private external fun wasiCoreNumImpLibmCopysign(x: Double, y: Double): Double

public actual fun copysign(x: Double, y: Double): Double =
    wasiCoreNumImpLibmCopysign(x, y).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmCopysignf")
private external fun wasiCoreNumImpLibmCopysignf(x: Float, y: Float): Float

public actual fun copysignf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmCopysignf(x, y).toFloat()

public actual fun copysignf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmCopysignf128")

public actual fun copysignf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmCopysignf16")

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmFabs")
private external fun wasiCoreNumImpLibmFabs(x: Double): Double

public actual fun fabs(x: Double): Double =
    wasiCoreNumImpLibmFabs(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmFabsf")
private external fun wasiCoreNumImpLibmFabsf(x: Float): Float

public actual fun fabsf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmFabsf(x).toFloat()

public actual fun fabsf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFabsf128")

public actual fun fabsf16(x: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFabsf16")

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmFdim")
private external fun wasiCoreNumImpLibmFdim(a: Double, b: Double): Double

public actual fun fdim(a: Double, b: Double): Double =
    wasiCoreNumImpLibmFdim(a, b).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmFdimf")
private external fun wasiCoreNumImpLibmFdimf(a: Float, b: Float): Float

public actual fun fdimf(a: kotlin.Float, b: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmFdimf(a, b).toFloat()

public actual fun fdimf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFdimf128")

public actual fun fdimf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFdimf16")

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmFloor")
private external fun wasiCoreNumImpLibmFloor(x: Double): Double

public actual fun floor(x: Double): Double =
    wasiCoreNumImpLibmFloor(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmFloorf")
private external fun wasiCoreNumImpLibmFloorf(x: Float): Float

public actual fun floorf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmFloorf(x).toFloat()

public actual fun floorf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFloorf128")

public actual fun floorf16(x: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFloorf16")

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmFma")
private external fun wasiCoreNumImpLibmFma(x: Double, y: Double, z: Double): Double

public actual fun fma(x: Double, y: Double, z: Double): Double =
    wasiCoreNumImpLibmFma(x, y, z).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmFmaf")
private external fun wasiCoreNumImpLibmFmaf(x: Float, y: Float, z: Float): Float

public actual fun fmaf(x: kotlin.Float, y: kotlin.Float, z: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmFmaf(x, y, z).toFloat()

public actual fun fmaf128(x: F128, y: F128, z: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFmaf128")

public actual fun fmaf16(x: F16, y: F16, z: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFmaf16")

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmFmax")
private external fun wasiCoreNumImpLibmFmax(x: Double, y: Double): Double

public actual fun fmax(x: Double, y: Double): Double =
    wasiCoreNumImpLibmFmax(x, y).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmFmaxf")
private external fun wasiCoreNumImpLibmFmaxf(x: Float, y: Float): Float

public actual fun fmaxf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmFmaxf(x, y).toFloat()

public actual fun fmaxf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFmaxf128")

public actual fun fmaxf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFmaxf16")

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmFmaximum")
private external fun wasiCoreNumImpLibmFmaximum(x: Double, y: Double): Double

public actual fun fmaximum(x: Double, y: Double): Double =
    wasiCoreNumImpLibmFmaximum(x, y).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmFmaximumf")
private external fun wasiCoreNumImpLibmFmaximumf(x: Float, y: Float): Float

public actual fun fmaximumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmFmaximumf(x, y).toFloat()

public actual fun fmaximumf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFmaximumf128")

public actual fun fmaximumf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFmaximumf16")

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmFmin")
private external fun wasiCoreNumImpLibmFmin(x: Double, y: Double): Double

public actual fun fmin(x: Double, y: Double): Double =
    wasiCoreNumImpLibmFmin(x, y).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmFminf")
private external fun wasiCoreNumImpLibmFminf(x: Float, y: Float): Float

public actual fun fminf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmFminf(x, y).toFloat()

public actual fun fminf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFminf128")

public actual fun fminf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFminf16")

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmFminimum")
private external fun wasiCoreNumImpLibmFminimum(x: Double, y: Double): Double

public actual fun fminimum(x: Double, y: Double): Double =
    wasiCoreNumImpLibmFminimum(x, y).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmFminimumf")
private external fun wasiCoreNumImpLibmFminimumf(x: Float, y: Float): Float

public actual fun fminimumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmFminimumf(x, y).toFloat()

public actual fun fminimumf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFminimumf128")

public actual fun fminimumf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFminimumf16")

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmFmod")
private external fun wasiCoreNumImpLibmFmod(x: Double, y: Double): Double

public actual fun fmod(x: Double, y: Double): Double =
    wasiCoreNumImpLibmFmod(x, y).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmFmodf")
private external fun wasiCoreNumImpLibmFmodf(x: Float, y: Float): Float

public actual fun fmodf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmFmodf(x, y).toFloat()

public actual fun fmodf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFmodf128")

public actual fun fmodf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFmodf16")

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmRint")
private external fun wasiCoreNumImpLibmRint(x: Double): Double

public actual fun rint(x: Double): Double =
    wasiCoreNumImpLibmRint(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmRintf")
private external fun wasiCoreNumImpLibmRintf(x: Float): Float

public actual fun rintf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmRintf(x).toFloat()

public actual fun rintf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmRintf128")

public actual fun rintf16(x: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmRintf16")

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmRound")
private external fun wasiCoreNumImpLibmRound(x: Double): Double

public actual fun round(x: Double): Double =
    wasiCoreNumImpLibmRound(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmRoundeven")
private external fun wasiCoreNumImpLibmRoundeven(x: Double): Double

public actual fun roundeven(x: Double): Double =
    wasiCoreNumImpLibmRoundeven(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmRoundevenf")
private external fun wasiCoreNumImpLibmRoundevenf(x: Float): Float

public actual fun roundevenf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmRoundevenf(x).toFloat()

public actual fun roundevenf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmRoundevenf128")

public actual fun roundevenf16(x: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmRoundevenf16")

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmRoundf")
private external fun wasiCoreNumImpLibmRoundf(x: Float): Float

public actual fun roundf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmRoundf(x).toFloat()

public actual fun roundf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmRoundf128")

public actual fun roundf16(x: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmRoundf16")

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmSqrt")
private external fun wasiCoreNumImpLibmSqrt(x: Double): Double

public actual fun sqrt(x: Double): Double =
    wasiCoreNumImpLibmSqrt(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmSqrtf")
private external fun wasiCoreNumImpLibmSqrtf(x: Float): Float

public actual fun sqrtf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmSqrtf(x).toFloat()

public actual fun sqrtf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmSqrtf128")

public actual fun sqrtf16(x: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmSqrtf16")

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmTrunc")
private external fun wasiCoreNumImpLibmTrunc(x: Double): Double

public actual fun trunc(x: Double): Double =
    wasiCoreNumImpLibmTrunc(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmTruncf")
private external fun wasiCoreNumImpLibmTruncf(x: Float): Float

public actual fun truncf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmTruncf(x).toFloat()

public actual fun truncf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmTruncf128")

public actual fun truncf16(x: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmTruncf16")

public actual fun mulsc3(a: kotlin.Float, b: kotlin.Float, c: kotlin.Float, d: kotlin.Float): Complex<kotlin.Float> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmMulsc3")

public actual fun muldc3(a: Double, b: Double, c: Double, d: Double): Complex<Double> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmMuldc3")

public actual fun divsc3(a: kotlin.Float, b: kotlin.Float, c: kotlin.Float, d: kotlin.Float): Complex<kotlin.Float> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmDivsc3")

public actual fun divdc3(a: Double, b: Double, c: Double, d: Double): Complex<Double> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmDivdc3")

public actual fun rustMulhc3(a: F16, b: F16, c: F16, d: F16): Complex<F16> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmRustMulhc3")

public actual fun rustMultc3(a: F128, b: F128, c: F128, d: F128): Complex<F128> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmRustMultc3")

public actual fun rustDivhc3(a: F16, b: F16, c: F16, d: F16): Complex<F16> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmRustDivhc3")

public actual fun rustDivtc3(a: F128, b: F128, c: F128, d: F128): Complex<F128> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmRustDivtc3")

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmAcosf")
private external fun wasiCoreNumImpLibmAcosf(n: Float): Float

public actual fun acosf(n: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmAcosf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmAsinf")
private external fun wasiCoreNumImpLibmAsinf(n: Float): Float

public actual fun asinf(n: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmAsinf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmAtan2f")
private external fun wasiCoreNumImpLibmAtan2f(a: Float, b: Float): Float

public actual fun atan2f(a: kotlin.Float, b: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmAtan2f(a, b).toFloat()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmAtanf")
private external fun wasiCoreNumImpLibmAtanf(n: Float): Float

public actual fun atanf(n: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmAtanf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmCosf")
private external fun wasiCoreNumImpLibmCosf(x: Float): Float

public actual fun cosf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmCosf(x).toFloat()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmCoshf")
private external fun wasiCoreNumImpLibmCoshf(n: Float): Float

public actual fun coshf(n: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmCoshf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmErfcf")
private external fun wasiCoreNumImpLibmErfcf(x: Float): Float

public actual fun erfcf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmErfcf(x).toFloat()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmErff")
private external fun wasiCoreNumImpLibmErff(x: Float): Float

public actual fun erff(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmErff(x).toFloat()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmExp2f")
private external fun wasiCoreNumImpLibmExp2f(x: Float): Float

public actual fun exp2f(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmExp2f(x).toFloat()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmExpf")
private external fun wasiCoreNumImpLibmExpf(x: Float): Float

public actual fun expf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmExpf(x).toFloat()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmExpm1f")
private external fun wasiCoreNumImpLibmExpm1f(n: Float): Float

public actual fun expm1f(n: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmExpm1f(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmHypotf")
private external fun wasiCoreNumImpLibmHypotf(x: Float, y: Float): Float

public actual fun hypotf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmHypotf(x, y).toFloat()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmLdexpf")
private external fun wasiCoreNumImpLibmLdexpf(f: Float, n: Int): Float

public actual fun ldexpf(f: kotlin.Float, n: Int): kotlin.Float =
    wasiCoreNumImpLibmLdexpf(f, n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmLog10f")
private external fun wasiCoreNumImpLibmLog10f(x: Float): Float

public actual fun log10f(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmLog10f(x).toFloat()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmLog1pf")
private external fun wasiCoreNumImpLibmLog1pf(n: Float): Float

public actual fun log1pf(n: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmLog1pf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmLog2f")
private external fun wasiCoreNumImpLibmLog2f(x: Float): Float

public actual fun log2f(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmLog2f(x).toFloat()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmLogf")
private external fun wasiCoreNumImpLibmLogf(x: Float): Float

public actual fun logf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmLogf(x).toFloat()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmPowf")
private external fun wasiCoreNumImpLibmPowf(x: Float, y: Float): Float

public actual fun powf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmPowf(x, y).toFloat()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmSinf")
private external fun wasiCoreNumImpLibmSinf(x: Float): Float

public actual fun sinf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmSinf(x).toFloat()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmSinhf")
private external fun wasiCoreNumImpLibmSinhf(n: Float): Float

public actual fun sinhf(n: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmSinhf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmTanf")
private external fun wasiCoreNumImpLibmTanf(n: Float): Float

public actual fun tanf(n: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmTanf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmTanhf")
private external fun wasiCoreNumImpLibmTanhf(n: Float): Float

public actual fun tanhf(n: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmTanhf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmTgammaf")
private external fun wasiCoreNumImpLibmTgammaf(x: Float): Float

public actual fun tgammaf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmTgammaf(x).toFloat()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmAcos")
private external fun wasiCoreNumImpLibmAcos(x: Double): Double

public actual fun acos(x: Double): Double =
    wasiCoreNumImpLibmAcos(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmAsin")
private external fun wasiCoreNumImpLibmAsin(x: Double): Double

public actual fun asin(x: Double): Double =
    wasiCoreNumImpLibmAsin(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmAtan")
private external fun wasiCoreNumImpLibmAtan(x: Double): Double

public actual fun atan(x: Double): Double =
    wasiCoreNumImpLibmAtan(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmAtan2")
private external fun wasiCoreNumImpLibmAtan2(x: Double, y: Double): Double

public actual fun atan2(x: Double, y: Double): Double =
    wasiCoreNumImpLibmAtan2(x, y).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmCos")
private external fun wasiCoreNumImpLibmCos(x: Double): Double

public actual fun cos(x: Double): Double =
    wasiCoreNumImpLibmCos(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmCosh")
private external fun wasiCoreNumImpLibmCosh(x: Double): Double

public actual fun cosh(x: Double): Double =
    wasiCoreNumImpLibmCosh(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmErf")
private external fun wasiCoreNumImpLibmErf(x: Double): Double

public actual fun erf(x: Double): Double =
    wasiCoreNumImpLibmErf(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmErfc")
private external fun wasiCoreNumImpLibmErfc(x: Double): Double

public actual fun erfc(x: Double): Double =
    wasiCoreNumImpLibmErfc(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmExp")
private external fun wasiCoreNumImpLibmExp(x: Double): Double

public actual fun exp(x: Double): Double =
    wasiCoreNumImpLibmExp(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmExp2")
private external fun wasiCoreNumImpLibmExp2(x: Double): Double

public actual fun exp2(x: Double): Double =
    wasiCoreNumImpLibmExp2(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmExpm1")
private external fun wasiCoreNumImpLibmExpm1(x: Double): Double

public actual fun expm1(x: Double): Double =
    wasiCoreNumImpLibmExpm1(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmHypot")
private external fun wasiCoreNumImpLibmHypot(x: Double, y: Double): Double

public actual fun hypot(x: Double, y: Double): Double =
    wasiCoreNumImpLibmHypot(x, y).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmLdexp")
private external fun wasiCoreNumImpLibmLdexp(f: Double, n: Int): Double

public actual fun ldexp(f: Double, n: Int): Double =
    wasiCoreNumImpLibmLdexp(f, n).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmLog")
private external fun wasiCoreNumImpLibmLog(x: Double): Double

public actual fun log(x: Double): Double =
    wasiCoreNumImpLibmLog(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmLog10")
private external fun wasiCoreNumImpLibmLog10(x: Double): Double

public actual fun log10(x: Double): Double =
    wasiCoreNumImpLibmLog10(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmLog1p")
private external fun wasiCoreNumImpLibmLog1p(x: Double): Double

public actual fun log1p(x: Double): Double =
    wasiCoreNumImpLibmLog1p(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmLog2")
private external fun wasiCoreNumImpLibmLog2(x: Double): Double

public actual fun log2(x: Double): Double =
    wasiCoreNumImpLibmLog2(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmPow")
private external fun wasiCoreNumImpLibmPow(x: Double, y: Double): Double

public actual fun pow(x: Double, y: Double): Double =
    wasiCoreNumImpLibmPow(x, y).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmSin")
private external fun wasiCoreNumImpLibmSin(x: Double): Double

public actual fun sin(x: Double): Double =
    wasiCoreNumImpLibmSin(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmSinh")
private external fun wasiCoreNumImpLibmSinh(x: Double): Double

public actual fun sinh(x: Double): Double =
    wasiCoreNumImpLibmSinh(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmTan")
private external fun wasiCoreNumImpLibmTan(x: Double): Double

public actual fun tan(x: Double): Double =
    wasiCoreNumImpLibmTan(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmTanh")
private external fun wasiCoreNumImpLibmTanh(x: Double): Double

public actual fun tanh(x: Double): Double =
    wasiCoreNumImpLibmTanh(x).toDouble()

@kotlin.wasm.WasmImport("kmcore", "coreNumImpLibmTgamma")
private external fun wasiCoreNumImpLibmTgamma(x: Double): Double

public actual fun tgamma(x: Double): Double =
    wasiCoreNumImpLibmTgamma(x).toDouble()

public actual fun acosf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmAcosf128")

public actual fun asinf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmAsinf128")

public actual fun atanf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmAtanf128")

public actual fun cbrtf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmCbrtf128")

public actual fun cosf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmCosf128")

public actual fun erff128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmErff128")

public actual fun expf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmExpf128")

public actual fun exp2f128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmExp2f128")

public actual fun expm1f128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmExpm1f128")

public actual fun hypotf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmHypotf128")

public actual fun ldexpf128(f: F128, n: Int): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmLdexpf128")

public actual fun log10f128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmLog10f128")

public actual fun log1pf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmLog1pf128")

public actual fun log2f128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmLog2f128")

public actual fun logf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmLogf128")

public actual fun powf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmPowf128")

public actual fun sinf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmSinf128")

public actual fun tanf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmTanf128")

public actual fun tanhf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmTanhf128")

public actual fun tgammaf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmTgammaf128")
