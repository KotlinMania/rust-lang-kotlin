@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.num.imp.libm

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.num.complex.Complex

private val numImpLibmNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_core !== 'undefined') return globalThis.__km_napi_core; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/core-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_core = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['core_native.node', 'build/node-ffi/core_native.node', 'build/node-ffi/release/core_native.node', 'build/node-ffi/debug/core_native.node', 'src/js/node-core/build/Release/core_native.node', 'src/js/node-core/build/Debug/core_native.node', 'src/js/node-core/dist/build/Release/core_native.node', 'src/js/node-core/dist/build/Debug/core_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_core = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_core = null; return null; })()")

internal actual fun powidf2(a: Double, b: Int): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmPowidf2(a, b) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun powisf2(a: kotlin.Float, b: Int): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmPowisf2(a.toDouble(), b) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun powitf2(a: F128, b: Int): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun cbrt(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmCbrt(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun cbrtf(n: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmCbrtf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun ceil(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmCeil(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun ceilf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmCeilf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun ceilf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun ceilf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun copysign(x: Double, y: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmCopysign(x, y) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun copysignf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmCopysignf(x.toDouble(), y.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun copysignf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun copysignf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fabs(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmFabs(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fabsf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmFabsf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fabsf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fabsf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fdim(a: Double, b: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmFdim(a, b) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fdimf(a: kotlin.Float, b: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmFdimf(a.toDouble(), b.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fdimf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fdimf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun floor(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmFloor(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun floorf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmFloorf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun floorf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun floorf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fma(x: Double, y: Double, z: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmFma(x, y, z) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fmaf(x: kotlin.Float, y: kotlin.Float, z: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmFmaf(x.toDouble(), y.toDouble(), z.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fmaf128(x: F128, y: F128, z: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fmaf16(x: F16, y: F16, z: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fmax(x: Double, y: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmFmax(x, y) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fmaxf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmFmaxf(x.toDouble(), y.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fmaxf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fmaxf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fmaximum(x: Double, y: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmFmaximum(x, y) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fmaximumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmFmaximumf(x.toDouble(), y.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fmaximumf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fmaximumf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fmin(x: Double, y: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmFmin(x, y) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fminf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmFminf(x.toDouble(), y.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fminf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fminf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fminimum(x: Double, y: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmFminimum(x, y) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fminimumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmFminimumf(x.toDouble(), y.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fminimumf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fminimumf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fmod(x: Double, y: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmFmod(x, y) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fmodf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmFmodf(x.toDouble(), y.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fmodf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun fmodf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun rint(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmRint(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun rintf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmRintf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun rintf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun rintf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun round(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmRound(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun roundeven(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmRoundeven(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun roundevenf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmRoundevenf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun roundevenf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun roundevenf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun roundf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmRoundf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun roundf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun roundf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun sqrt(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmSqrt(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun sqrtf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmSqrtf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun sqrtf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun sqrtf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun trunc(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmTrunc(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun truncf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmTruncf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun truncf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun truncf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun mulsc3(a: kotlin.Float, b: kotlin.Float, c: kotlin.Float, d: kotlin.Float): Complex<kotlin.Float> =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun muldc3(a: Double, b: Double, c: Double, d: Double): Complex<Double> =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun divsc3(a: kotlin.Float, b: kotlin.Float, c: kotlin.Float, d: kotlin.Float): Complex<kotlin.Float> =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun divdc3(a: Double, b: Double, c: Double, d: Double): Complex<Double> =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun rustMulhc3(a: F16, b: F16, c: F16, d: F16): Complex<F16> =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun rustMultc3(a: F128, b: F128, c: F128, d: F128): Complex<F128> =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun rustDivhc3(a: F16, b: F16, c: F16, d: F16): Complex<F16> =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun rustDivtc3(a: F128, b: F128, c: F128, d: F128): Complex<F128> =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun acosf(n: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmAcosf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun asinf(n: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmAsinf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun atan2f(a: kotlin.Float, b: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmAtan2f(a.toDouble(), b.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun atanf(n: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmAtanf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun cosf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmCosf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun coshf(n: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmCoshf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun erfcf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmErfcf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun erff(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmErff(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun exp2f(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmExp2f(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun expf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmExpf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun expm1f(n: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmExpm1f(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun hypotf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmHypotf(x.toDouble(), y.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun ldexpf(f: kotlin.Float, n: Int): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmLdexpf(f.toDouble(), n) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun log10f(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmLog10f(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun log1pf(n: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmLog1pf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun log2f(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmLog2f(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun logf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmLogf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun powf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmPowf(x.toDouble(), y.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun sinf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmSinf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun sinhf(n: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmSinhf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun tanf(n: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmTanf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun tanhf(n: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmTanhf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun tgammaf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmTgammaf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun acos(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmAcos(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun asin(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmAsin(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun atan(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmAtan(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun atan2(x: Double, y: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmAtan2(x, y) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun cos(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmCos(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun cosh(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmCosh(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun erf(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmErf(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun erfc(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmErfc(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun exp(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmExp(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun exp2(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmExp2(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun expm1(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmExpm1(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun hypot(x: Double, y: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmHypot(x, y) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun ldexp(f: Double, n: Int): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmLdexp(f, n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun log(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmLog(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun log10(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmLog10(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun log1p(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmLog1p(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun log2(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmLog2(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun pow(x: Double, y: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmPow(x, y) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun sin(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmSin(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun sinh(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmSinh(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun tan(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmTan(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun tanh(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmTanh(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun tgamma(x: Double): Double =
    if (numImpLibmNapiAddon != null) (numImpLibmNapiAddon.coreNumImpLibmTgamma(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun acosf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun asinf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun atanf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun cbrtf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun cosf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun erff128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun expf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun exp2f128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun expm1f128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun hypotf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun ldexpf128(f: F128, n: Int): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun log10f128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun log1pf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun log2f128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun logf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun powf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun sinf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun tanf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun tanhf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

internal actual fun tgammaf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")
