@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.core.num.imp.libm

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.num.complex.Complex

// The host must provide the C companion wrapper exports in import module core.
@kotlin.wasm.WasmImport("core", "coreNumImpLibmPowidf2")
private external fun wasiCoreNumImpLibmPowidf2(a: Double, b: Int): Double

internal actual fun powidf2(a: Double, b: Int): Double =
    wasiCoreNumImpLibmPowidf2(a, b).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmPowisf2")
private external fun wasiCoreNumImpLibmPowisf2(a: Float, b: Int): Float

internal actual fun powisf2(a: kotlin.Float, b: Int): kotlin.Float =
    wasiCoreNumImpLibmPowisf2(a, b).toFloat()

internal actual fun powitf2(a: F128, b: Int): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmPowitf2")

@kotlin.wasm.WasmImport("core", "coreNumImpLibmCbrt")
private external fun wasiCoreNumImpLibmCbrt(x: Double): Double

internal actual fun cbrt(x: Double): Double =
    wasiCoreNumImpLibmCbrt(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmCbrtf")
private external fun wasiCoreNumImpLibmCbrtf(n: Float): Float

internal actual fun cbrtf(n: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmCbrtf(n).toFloat()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmCeil")
private external fun wasiCoreNumImpLibmCeil(x: Double): Double

internal actual fun ceil(x: Double): Double =
    wasiCoreNumImpLibmCeil(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmCeilf")
private external fun wasiCoreNumImpLibmCeilf(x: Float): Float

internal actual fun ceilf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmCeilf(x).toFloat()

internal actual fun ceilf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmCeilf128")

internal actual fun ceilf16(x: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmCeilf16")

@kotlin.wasm.WasmImport("core", "coreNumImpLibmCopysign")
private external fun wasiCoreNumImpLibmCopysign(x: Double, y: Double): Double

internal actual fun copysign(x: Double, y: Double): Double =
    wasiCoreNumImpLibmCopysign(x, y).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmCopysignf")
private external fun wasiCoreNumImpLibmCopysignf(x: Float, y: Float): Float

internal actual fun copysignf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmCopysignf(x, y).toFloat()

internal actual fun copysignf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmCopysignf128")

internal actual fun copysignf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmCopysignf16")

@kotlin.wasm.WasmImport("core", "coreNumImpLibmFabs")
private external fun wasiCoreNumImpLibmFabs(x: Double): Double

internal actual fun fabs(x: Double): Double =
    wasiCoreNumImpLibmFabs(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmFabsf")
private external fun wasiCoreNumImpLibmFabsf(x: Float): Float

internal actual fun fabsf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmFabsf(x).toFloat()

internal actual fun fabsf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFabsf128")

internal actual fun fabsf16(x: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFabsf16")

@kotlin.wasm.WasmImport("core", "coreNumImpLibmFdim")
private external fun wasiCoreNumImpLibmFdim(a: Double, b: Double): Double

internal actual fun fdim(a: Double, b: Double): Double =
    wasiCoreNumImpLibmFdim(a, b).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmFdimf")
private external fun wasiCoreNumImpLibmFdimf(a: Float, b: Float): Float

internal actual fun fdimf(a: kotlin.Float, b: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmFdimf(a, b).toFloat()

internal actual fun fdimf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFdimf128")

internal actual fun fdimf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFdimf16")

@kotlin.wasm.WasmImport("core", "coreNumImpLibmFloor")
private external fun wasiCoreNumImpLibmFloor(x: Double): Double

internal actual fun floor(x: Double): Double =
    wasiCoreNumImpLibmFloor(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmFloorf")
private external fun wasiCoreNumImpLibmFloorf(x: Float): Float

internal actual fun floorf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmFloorf(x).toFloat()

internal actual fun floorf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFloorf128")

internal actual fun floorf16(x: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFloorf16")

@kotlin.wasm.WasmImport("core", "coreNumImpLibmFma")
private external fun wasiCoreNumImpLibmFma(x: Double, y: Double, z: Double): Double

internal actual fun fma(x: Double, y: Double, z: Double): Double =
    wasiCoreNumImpLibmFma(x, y, z).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmFmaf")
private external fun wasiCoreNumImpLibmFmaf(x: Float, y: Float, z: Float): Float

internal actual fun fmaf(x: kotlin.Float, y: kotlin.Float, z: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmFmaf(x, y, z).toFloat()

internal actual fun fmaf128(x: F128, y: F128, z: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFmaf128")

internal actual fun fmaf16(x: F16, y: F16, z: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFmaf16")

@kotlin.wasm.WasmImport("core", "coreNumImpLibmFmax")
private external fun wasiCoreNumImpLibmFmax(x: Double, y: Double): Double

internal actual fun fmax(x: Double, y: Double): Double =
    wasiCoreNumImpLibmFmax(x, y).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmFmaxf")
private external fun wasiCoreNumImpLibmFmaxf(x: Float, y: Float): Float

internal actual fun fmaxf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmFmaxf(x, y).toFloat()

internal actual fun fmaxf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFmaxf128")

internal actual fun fmaxf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFmaxf16")

@kotlin.wasm.WasmImport("core", "coreNumImpLibmFmaximum")
private external fun wasiCoreNumImpLibmFmaximum(x: Double, y: Double): Double

internal actual fun fmaximum(x: Double, y: Double): Double =
    wasiCoreNumImpLibmFmaximum(x, y).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmFmaximumf")
private external fun wasiCoreNumImpLibmFmaximumf(x: Float, y: Float): Float

internal actual fun fmaximumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmFmaximumf(x, y).toFloat()

internal actual fun fmaximumf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFmaximumf128")

internal actual fun fmaximumf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFmaximumf16")

@kotlin.wasm.WasmImport("core", "coreNumImpLibmFmin")
private external fun wasiCoreNumImpLibmFmin(x: Double, y: Double): Double

internal actual fun fmin(x: Double, y: Double): Double =
    wasiCoreNumImpLibmFmin(x, y).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmFminf")
private external fun wasiCoreNumImpLibmFminf(x: Float, y: Float): Float

internal actual fun fminf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmFminf(x, y).toFloat()

internal actual fun fminf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFminf128")

internal actual fun fminf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFminf16")

@kotlin.wasm.WasmImport("core", "coreNumImpLibmFminimum")
private external fun wasiCoreNumImpLibmFminimum(x: Double, y: Double): Double

internal actual fun fminimum(x: Double, y: Double): Double =
    wasiCoreNumImpLibmFminimum(x, y).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmFminimumf")
private external fun wasiCoreNumImpLibmFminimumf(x: Float, y: Float): Float

internal actual fun fminimumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmFminimumf(x, y).toFloat()

internal actual fun fminimumf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFminimumf128")

internal actual fun fminimumf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFminimumf16")

@kotlin.wasm.WasmImport("core", "coreNumImpLibmFmod")
private external fun wasiCoreNumImpLibmFmod(x: Double, y: Double): Double

internal actual fun fmod(x: Double, y: Double): Double =
    wasiCoreNumImpLibmFmod(x, y).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmFmodf")
private external fun wasiCoreNumImpLibmFmodf(x: Float, y: Float): Float

internal actual fun fmodf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmFmodf(x, y).toFloat()

internal actual fun fmodf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFmodf128")

internal actual fun fmodf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmFmodf16")

@kotlin.wasm.WasmImport("core", "coreNumImpLibmRint")
private external fun wasiCoreNumImpLibmRint(x: Double): Double

internal actual fun rint(x: Double): Double =
    wasiCoreNumImpLibmRint(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmRintf")
private external fun wasiCoreNumImpLibmRintf(x: Float): Float

internal actual fun rintf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmRintf(x).toFloat()

internal actual fun rintf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmRintf128")

internal actual fun rintf16(x: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmRintf16")

@kotlin.wasm.WasmImport("core", "coreNumImpLibmRound")
private external fun wasiCoreNumImpLibmRound(x: Double): Double

internal actual fun round(x: Double): Double =
    wasiCoreNumImpLibmRound(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmRoundeven")
private external fun wasiCoreNumImpLibmRoundeven(x: Double): Double

internal actual fun roundeven(x: Double): Double =
    wasiCoreNumImpLibmRoundeven(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmRoundevenf")
private external fun wasiCoreNumImpLibmRoundevenf(x: Float): Float

internal actual fun roundevenf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmRoundevenf(x).toFloat()

internal actual fun roundevenf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmRoundevenf128")

internal actual fun roundevenf16(x: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmRoundevenf16")

@kotlin.wasm.WasmImport("core", "coreNumImpLibmRoundf")
private external fun wasiCoreNumImpLibmRoundf(x: Float): Float

internal actual fun roundf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmRoundf(x).toFloat()

internal actual fun roundf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmRoundf128")

internal actual fun roundf16(x: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmRoundf16")

@kotlin.wasm.WasmImport("core", "coreNumImpLibmSqrt")
private external fun wasiCoreNumImpLibmSqrt(x: Double): Double

internal actual fun sqrt(x: Double): Double =
    wasiCoreNumImpLibmSqrt(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmSqrtf")
private external fun wasiCoreNumImpLibmSqrtf(x: Float): Float

internal actual fun sqrtf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmSqrtf(x).toFloat()

internal actual fun sqrtf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmSqrtf128")

internal actual fun sqrtf16(x: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmSqrtf16")

@kotlin.wasm.WasmImport("core", "coreNumImpLibmTrunc")
private external fun wasiCoreNumImpLibmTrunc(x: Double): Double

internal actual fun trunc(x: Double): Double =
    wasiCoreNumImpLibmTrunc(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmTruncf")
private external fun wasiCoreNumImpLibmTruncf(x: Float): Float

internal actual fun truncf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmTruncf(x).toFloat()

internal actual fun truncf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmTruncf128")

internal actual fun truncf16(x: F16): F16 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmTruncf16")

internal actual fun mulsc3(a: kotlin.Float, b: kotlin.Float, c: kotlin.Float, d: kotlin.Float): Complex<kotlin.Float> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmMulsc3")

internal actual fun muldc3(a: Double, b: Double, c: Double, d: Double): Complex<Double> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmMuldc3")

internal actual fun divsc3(a: kotlin.Float, b: kotlin.Float, c: kotlin.Float, d: kotlin.Float): Complex<kotlin.Float> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmDivsc3")

internal actual fun divdc3(a: Double, b: Double, c: Double, d: Double): Complex<Double> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmDivdc3")

internal actual fun rustMulhc3(a: F16, b: F16, c: F16, d: F16): Complex<F16> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmRustMulhc3")

internal actual fun rustMultc3(a: F128, b: F128, c: F128, d: F128): Complex<F128> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmRustMultc3")

internal actual fun rustDivhc3(a: F16, b: F16, c: F16, d: F16): Complex<F16> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmRustDivhc3")

internal actual fun rustDivtc3(a: F128, b: F128, c: F128, d: F128): Complex<F128> =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmRustDivtc3")

@kotlin.wasm.WasmImport("core", "coreNumImpLibmAcosf")
private external fun wasiCoreNumImpLibmAcosf(n: Float): Float

internal actual fun acosf(n: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmAcosf(n).toFloat()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmAsinf")
private external fun wasiCoreNumImpLibmAsinf(n: Float): Float

internal actual fun asinf(n: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmAsinf(n).toFloat()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmAtan2f")
private external fun wasiCoreNumImpLibmAtan2f(a: Float, b: Float): Float

internal actual fun atan2f(a: kotlin.Float, b: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmAtan2f(a, b).toFloat()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmAtanf")
private external fun wasiCoreNumImpLibmAtanf(n: Float): Float

internal actual fun atanf(n: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmAtanf(n).toFloat()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmCosf")
private external fun wasiCoreNumImpLibmCosf(x: Float): Float

internal actual fun cosf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmCosf(x).toFloat()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmCoshf")
private external fun wasiCoreNumImpLibmCoshf(n: Float): Float

internal actual fun coshf(n: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmCoshf(n).toFloat()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmErfcf")
private external fun wasiCoreNumImpLibmErfcf(x: Float): Float

internal actual fun erfcf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmErfcf(x).toFloat()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmErff")
private external fun wasiCoreNumImpLibmErff(x: Float): Float

internal actual fun erff(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmErff(x).toFloat()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmExp2f")
private external fun wasiCoreNumImpLibmExp2f(x: Float): Float

internal actual fun exp2f(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmExp2f(x).toFloat()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmExpf")
private external fun wasiCoreNumImpLibmExpf(x: Float): Float

internal actual fun expf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmExpf(x).toFloat()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmExpm1f")
private external fun wasiCoreNumImpLibmExpm1f(n: Float): Float

internal actual fun expm1f(n: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmExpm1f(n).toFloat()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmHypotf")
private external fun wasiCoreNumImpLibmHypotf(x: Float, y: Float): Float

internal actual fun hypotf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmHypotf(x, y).toFloat()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmLdexpf")
private external fun wasiCoreNumImpLibmLdexpf(f: Float, n: Int): Float

internal actual fun ldexpf(f: kotlin.Float, n: Int): kotlin.Float =
    wasiCoreNumImpLibmLdexpf(f, n).toFloat()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmLog10f")
private external fun wasiCoreNumImpLibmLog10f(x: Float): Float

internal actual fun log10f(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmLog10f(x).toFloat()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmLog1pf")
private external fun wasiCoreNumImpLibmLog1pf(n: Float): Float

internal actual fun log1pf(n: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmLog1pf(n).toFloat()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmLog2f")
private external fun wasiCoreNumImpLibmLog2f(x: Float): Float

internal actual fun log2f(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmLog2f(x).toFloat()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmLogf")
private external fun wasiCoreNumImpLibmLogf(x: Float): Float

internal actual fun logf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmLogf(x).toFloat()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmPowf")
private external fun wasiCoreNumImpLibmPowf(x: Float, y: Float): Float

internal actual fun powf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmPowf(x, y).toFloat()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmSinf")
private external fun wasiCoreNumImpLibmSinf(x: Float): Float

internal actual fun sinf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmSinf(x).toFloat()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmSinhf")
private external fun wasiCoreNumImpLibmSinhf(n: Float): Float

internal actual fun sinhf(n: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmSinhf(n).toFloat()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmTanf")
private external fun wasiCoreNumImpLibmTanf(n: Float): Float

internal actual fun tanf(n: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmTanf(n).toFloat()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmTanhf")
private external fun wasiCoreNumImpLibmTanhf(n: Float): Float

internal actual fun tanhf(n: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmTanhf(n).toFloat()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmTgammaf")
private external fun wasiCoreNumImpLibmTgammaf(x: Float): Float

internal actual fun tgammaf(x: kotlin.Float): kotlin.Float =
    wasiCoreNumImpLibmTgammaf(x).toFloat()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmAcos")
private external fun wasiCoreNumImpLibmAcos(x: Double): Double

internal actual fun acos(x: Double): Double =
    wasiCoreNumImpLibmAcos(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmAsin")
private external fun wasiCoreNumImpLibmAsin(x: Double): Double

internal actual fun asin(x: Double): Double =
    wasiCoreNumImpLibmAsin(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmAtan")
private external fun wasiCoreNumImpLibmAtan(x: Double): Double

internal actual fun atan(x: Double): Double =
    wasiCoreNumImpLibmAtan(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmAtan2")
private external fun wasiCoreNumImpLibmAtan2(x: Double, y: Double): Double

internal actual fun atan2(x: Double, y: Double): Double =
    wasiCoreNumImpLibmAtan2(x, y).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmCos")
private external fun wasiCoreNumImpLibmCos(x: Double): Double

internal actual fun cos(x: Double): Double =
    wasiCoreNumImpLibmCos(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmCosh")
private external fun wasiCoreNumImpLibmCosh(x: Double): Double

internal actual fun cosh(x: Double): Double =
    wasiCoreNumImpLibmCosh(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmErf")
private external fun wasiCoreNumImpLibmErf(x: Double): Double

internal actual fun erf(x: Double): Double =
    wasiCoreNumImpLibmErf(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmErfc")
private external fun wasiCoreNumImpLibmErfc(x: Double): Double

internal actual fun erfc(x: Double): Double =
    wasiCoreNumImpLibmErfc(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmExp")
private external fun wasiCoreNumImpLibmExp(x: Double): Double

internal actual fun exp(x: Double): Double =
    wasiCoreNumImpLibmExp(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmExp2")
private external fun wasiCoreNumImpLibmExp2(x: Double): Double

internal actual fun exp2(x: Double): Double =
    wasiCoreNumImpLibmExp2(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmExpm1")
private external fun wasiCoreNumImpLibmExpm1(x: Double): Double

internal actual fun expm1(x: Double): Double =
    wasiCoreNumImpLibmExpm1(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmHypot")
private external fun wasiCoreNumImpLibmHypot(x: Double, y: Double): Double

internal actual fun hypot(x: Double, y: Double): Double =
    wasiCoreNumImpLibmHypot(x, y).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmLdexp")
private external fun wasiCoreNumImpLibmLdexp(f: Double, n: Int): Double

internal actual fun ldexp(f: Double, n: Int): Double =
    wasiCoreNumImpLibmLdexp(f, n).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmLog")
private external fun wasiCoreNumImpLibmLog(x: Double): Double

internal actual fun log(x: Double): Double =
    wasiCoreNumImpLibmLog(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmLog10")
private external fun wasiCoreNumImpLibmLog10(x: Double): Double

internal actual fun log10(x: Double): Double =
    wasiCoreNumImpLibmLog10(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmLog1p")
private external fun wasiCoreNumImpLibmLog1p(x: Double): Double

internal actual fun log1p(x: Double): Double =
    wasiCoreNumImpLibmLog1p(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmLog2")
private external fun wasiCoreNumImpLibmLog2(x: Double): Double

internal actual fun log2(x: Double): Double =
    wasiCoreNumImpLibmLog2(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmPow")
private external fun wasiCoreNumImpLibmPow(x: Double, y: Double): Double

internal actual fun pow(x: Double, y: Double): Double =
    wasiCoreNumImpLibmPow(x, y).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmSin")
private external fun wasiCoreNumImpLibmSin(x: Double): Double

internal actual fun sin(x: Double): Double =
    wasiCoreNumImpLibmSin(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmSinh")
private external fun wasiCoreNumImpLibmSinh(x: Double): Double

internal actual fun sinh(x: Double): Double =
    wasiCoreNumImpLibmSinh(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmTan")
private external fun wasiCoreNumImpLibmTan(x: Double): Double

internal actual fun tan(x: Double): Double =
    wasiCoreNumImpLibmTan(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmTanh")
private external fun wasiCoreNumImpLibmTanh(x: Double): Double

internal actual fun tanh(x: Double): Double =
    wasiCoreNumImpLibmTanh(x).toDouble()

@kotlin.wasm.WasmImport("core", "coreNumImpLibmTgamma")
private external fun wasiCoreNumImpLibmTgamma(x: Double): Double

internal actual fun tgamma(x: Double): Double =
    wasiCoreNumImpLibmTgamma(x).toDouble()

internal actual fun acosf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmAcosf128")

internal actual fun asinf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmAsinf128")

internal actual fun atanf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmAtanf128")

internal actual fun cbrtf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmCbrtf128")

internal actual fun cosf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmCosf128")

internal actual fun erff128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmErff128")

internal actual fun expf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmExpf128")

internal actual fun exp2f128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmExp2f128")

internal actual fun expm1f128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmExpm1f128")

internal actual fun hypotf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmHypotf128")

internal actual fun ldexpf128(f: F128, n: Int): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmLdexpf128")

internal actual fun log10f128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmLog10f128")

internal actual fun log1pf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmLog1pf128")

internal actual fun log2f128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmLog2f128")

internal actual fun logf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmLogf128")

internal actual fun powf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmPowf128")

internal actual fun sinf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmSinf128")

internal actual fun tanf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmTanf128")

internal actual fun tanhf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmTanhf128")

internal actual fun tgammaf128(x: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for coreNumImpLibmTgammaf128")
