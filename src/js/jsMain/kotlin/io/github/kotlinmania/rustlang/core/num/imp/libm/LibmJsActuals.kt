@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.num.imp.libm

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.num.complex.Complex

private val coreNumImpLibmNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun powidf2(a: Double, b: Int): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmPowidf2(a, b) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun powisf2(a: kotlin.Float, b: Int): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmPowisf2(a.toDouble(), b) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun powitf2(a: F128, b: Int): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cbrt(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmCbrt(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cbrtf(n: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmCbrtf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ceil(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmCeil(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ceilf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmCeilf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ceilf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ceilf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copysign(x: Double, y: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmCopysign(x, y) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copysignf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmCopysignf(x.toDouble(), y.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copysignf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copysignf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fabs(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmFabs(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fabsf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmFabsf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fabsf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fabsf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdim(a: Double, b: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmFdim(a, b) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdimf(a: kotlin.Float, b: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmFdimf(a.toDouble(), b.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdimf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdimf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun floor(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmFloor(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun floorf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmFloorf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun floorf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun floorf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fma(x: Double, y: Double, z: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmFma(x, y, z) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaf(x: kotlin.Float, y: kotlin.Float, z: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmFmaf(x.toDouble(), y.toDouble(), z.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaf128(x: F128, y: F128, z: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaf16(x: F16, y: F16, z: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmax(x: Double, y: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmFmax(x, y) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaxf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmFmaxf(x.toDouble(), y.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaxf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaxf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaximum(x: Double, y: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmFmaximum(x, y) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaximumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmFmaximumf(x.toDouble(), y.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaximumf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaximumf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmin(x: Double, y: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmFmin(x, y) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fminf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmFminf(x.toDouble(), y.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fminf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fminf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fminimum(x: Double, y: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmFminimum(x, y) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fminimumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmFminimumf(x.toDouble(), y.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fminimumf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fminimumf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmod(x: Double, y: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmFmod(x, y) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmodf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmFmodf(x.toDouble(), y.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmodf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmodf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rint(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmRint(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rintf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmRintf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rintf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rintf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun round(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmRound(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun roundeven(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmRoundeven(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun roundevenf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmRoundevenf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun roundevenf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun roundevenf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun roundf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmRoundf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun roundf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun roundf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sqrt(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmSqrt(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sqrtf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmSqrtf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sqrtf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sqrtf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun trunc(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmTrunc(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun truncf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmTruncf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun truncf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun truncf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mulsc3(a: kotlin.Float, b: kotlin.Float, c: kotlin.Float, d: kotlin.Float): Complex<kotlin.Float> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun muldc3(a: Double, b: Double, c: Double, d: Double): Complex<Double> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun divsc3(a: kotlin.Float, b: kotlin.Float, c: kotlin.Float, d: kotlin.Float): Complex<kotlin.Float> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun divdc3(a: Double, b: Double, c: Double, d: Double): Complex<Double> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rustMulhc3(a: F16, b: F16, c: F16, d: F16): Complex<F16> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rustMultc3(a: F128, b: F128, c: F128, d: F128): Complex<F128> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rustDivhc3(a: F16, b: F16, c: F16, d: F16): Complex<F16> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rustDivtc3(a: F128, b: F128, c: F128, d: F128): Complex<F128> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun acosf(n: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmAcosf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asinf(n: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmAsinf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atan2f(a: kotlin.Float, b: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmAtan2f(a.toDouble(), b.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atanf(n: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmAtanf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cosf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmCosf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun coshf(n: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmCoshf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erfcf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmErfcf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erff(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmErff(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun exp2f(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmExp2f(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmExpf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expm1f(n: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmExpm1f(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hypotf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmHypotf(x.toDouble(), y.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ldexpf(f: kotlin.Float, n: Int): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmLdexpf(f.toDouble(), n) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log10f(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmLog10f(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log1pf(n: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmLog1pf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log2f(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmLog2f(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun logf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmLogf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun powf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmPowf(x.toDouble(), y.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sinf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmSinf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sinhf(n: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmSinhf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tanf(n: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmTanf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tanhf(n: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmTanhf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tgammaf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmTgammaf(x.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun acos(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmAcos(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asin(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmAsin(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atan(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmAtan(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atan2(x: Double, y: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmAtan2(x, y) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cos(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmCos(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cosh(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmCosh(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erf(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmErf(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erfc(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmErfc(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun exp(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmExp(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun exp2(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmExp2(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expm1(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmExpm1(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hypot(x: Double, y: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmHypot(x, y) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ldexp(f: Double, n: Int): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmLdexp(f, n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmLog(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log10(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmLog10(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log1p(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmLog1p(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log2(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmLog2(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pow(x: Double, y: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmPow(x, y) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sin(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmSin(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sinh(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmSinh(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tan(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmTan(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tanh(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmTanh(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tgamma(x: Double): Double =
    if (coreNumImpLibmNapiAddon != null) (coreNumImpLibmNapiAddon.rustlangCoreNumImpLibmTgamma(x) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun acosf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asinf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atanf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cbrtf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cosf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erff128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun exp2f128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expm1f128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hypotf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ldexpf128(f: F128, n: Int): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log10f128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log1pf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log2f128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun logf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun powf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sinf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tanf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tanhf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tgammaf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")
