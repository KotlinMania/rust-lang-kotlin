@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.core.num.imp.libm

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.num.complex.Complex

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initNumImpLibmNapiAddon(): Boolean

private val numImpLibmNapiAvailable: Boolean = initNumImpLibmNapiAddon()

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.coreNumImpLibmPowidf2(p0, p1)")
private external fun wasmCoreNumImpLibmPowidf2(p0: Double, p1: Int): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.coreNumImpLibmPowisf2(p0, p1)")
private external fun wasmCoreNumImpLibmPowisf2(p0: Double, p1: Int): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmCbrt(p0)")
private external fun wasmCoreNumImpLibmCbrt(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmCbrtf(p0)")
private external fun wasmCoreNumImpLibmCbrtf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmCeil(p0)")
private external fun wasmCoreNumImpLibmCeil(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmCeilf(p0)")
private external fun wasmCoreNumImpLibmCeilf(p0: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.coreNumImpLibmCopysign(p0, p1)")
private external fun wasmCoreNumImpLibmCopysign(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.coreNumImpLibmCopysignf(p0, p1)")
private external fun wasmCoreNumImpLibmCopysignf(p0: Double, p1: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmFabs(p0)")
private external fun wasmCoreNumImpLibmFabs(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmFabsf(p0)")
private external fun wasmCoreNumImpLibmFabsf(p0: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.coreNumImpLibmFdim(p0, p1)")
private external fun wasmCoreNumImpLibmFdim(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.coreNumImpLibmFdimf(p0, p1)")
private external fun wasmCoreNumImpLibmFdimf(p0: Double, p1: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmFloor(p0)")
private external fun wasmCoreNumImpLibmFloor(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmFloorf(p0)")
private external fun wasmCoreNumImpLibmFloorf(p0: Double): Double

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.coreNumImpLibmFma(p0, p1, p2)")
private external fun wasmCoreNumImpLibmFma(p0: Double, p1: Double, p2: Double): Double

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.coreNumImpLibmFmaf(p0, p1, p2)")
private external fun wasmCoreNumImpLibmFmaf(p0: Double, p1: Double, p2: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.coreNumImpLibmFmax(p0, p1)")
private external fun wasmCoreNumImpLibmFmax(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.coreNumImpLibmFmaxf(p0, p1)")
private external fun wasmCoreNumImpLibmFmaxf(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.coreNumImpLibmFmaximum(p0, p1)")
private external fun wasmCoreNumImpLibmFmaximum(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.coreNumImpLibmFmaximumf(p0, p1)")
private external fun wasmCoreNumImpLibmFmaximumf(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.coreNumImpLibmFmin(p0, p1)")
private external fun wasmCoreNumImpLibmFmin(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.coreNumImpLibmFminf(p0, p1)")
private external fun wasmCoreNumImpLibmFminf(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.coreNumImpLibmFminimum(p0, p1)")
private external fun wasmCoreNumImpLibmFminimum(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.coreNumImpLibmFminimumf(p0, p1)")
private external fun wasmCoreNumImpLibmFminimumf(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.coreNumImpLibmFmod(p0, p1)")
private external fun wasmCoreNumImpLibmFmod(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.coreNumImpLibmFmodf(p0, p1)")
private external fun wasmCoreNumImpLibmFmodf(p0: Double, p1: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmRint(p0)")
private external fun wasmCoreNumImpLibmRint(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmRintf(p0)")
private external fun wasmCoreNumImpLibmRintf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmRound(p0)")
private external fun wasmCoreNumImpLibmRound(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmRoundeven(p0)")
private external fun wasmCoreNumImpLibmRoundeven(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmRoundevenf(p0)")
private external fun wasmCoreNumImpLibmRoundevenf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmRoundf(p0)")
private external fun wasmCoreNumImpLibmRoundf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmSqrt(p0)")
private external fun wasmCoreNumImpLibmSqrt(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmSqrtf(p0)")
private external fun wasmCoreNumImpLibmSqrtf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmTrunc(p0)")
private external fun wasmCoreNumImpLibmTrunc(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmTruncf(p0)")
private external fun wasmCoreNumImpLibmTruncf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmAcosf(p0)")
private external fun wasmCoreNumImpLibmAcosf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmAsinf(p0)")
private external fun wasmCoreNumImpLibmAsinf(p0: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.coreNumImpLibmAtan2f(p0, p1)")
private external fun wasmCoreNumImpLibmAtan2f(p0: Double, p1: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmAtanf(p0)")
private external fun wasmCoreNumImpLibmAtanf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmCosf(p0)")
private external fun wasmCoreNumImpLibmCosf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmCoshf(p0)")
private external fun wasmCoreNumImpLibmCoshf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmErfcf(p0)")
private external fun wasmCoreNumImpLibmErfcf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmErff(p0)")
private external fun wasmCoreNumImpLibmErff(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmExp2f(p0)")
private external fun wasmCoreNumImpLibmExp2f(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmExpf(p0)")
private external fun wasmCoreNumImpLibmExpf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmExpm1f(p0)")
private external fun wasmCoreNumImpLibmExpm1f(p0: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.coreNumImpLibmHypotf(p0, p1)")
private external fun wasmCoreNumImpLibmHypotf(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.coreNumImpLibmLdexpf(p0, p1)")
private external fun wasmCoreNumImpLibmLdexpf(p0: Double, p1: Int): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmLog10f(p0)")
private external fun wasmCoreNumImpLibmLog10f(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmLog1pf(p0)")
private external fun wasmCoreNumImpLibmLog1pf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmLog2f(p0)")
private external fun wasmCoreNumImpLibmLog2f(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmLogf(p0)")
private external fun wasmCoreNumImpLibmLogf(p0: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.coreNumImpLibmPowf(p0, p1)")
private external fun wasmCoreNumImpLibmPowf(p0: Double, p1: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmSinf(p0)")
private external fun wasmCoreNumImpLibmSinf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmSinhf(p0)")
private external fun wasmCoreNumImpLibmSinhf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmTanf(p0)")
private external fun wasmCoreNumImpLibmTanf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmTanhf(p0)")
private external fun wasmCoreNumImpLibmTanhf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmTgammaf(p0)")
private external fun wasmCoreNumImpLibmTgammaf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmAcos(p0)")
private external fun wasmCoreNumImpLibmAcos(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmAsin(p0)")
private external fun wasmCoreNumImpLibmAsin(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmAtan(p0)")
private external fun wasmCoreNumImpLibmAtan(p0: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.coreNumImpLibmAtan2(p0, p1)")
private external fun wasmCoreNumImpLibmAtan2(p0: Double, p1: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmCos(p0)")
private external fun wasmCoreNumImpLibmCos(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmCosh(p0)")
private external fun wasmCoreNumImpLibmCosh(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmErf(p0)")
private external fun wasmCoreNumImpLibmErf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmErfc(p0)")
private external fun wasmCoreNumImpLibmErfc(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmExp(p0)")
private external fun wasmCoreNumImpLibmExp(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmExp2(p0)")
private external fun wasmCoreNumImpLibmExp2(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmExpm1(p0)")
private external fun wasmCoreNumImpLibmExpm1(p0: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.coreNumImpLibmHypot(p0, p1)")
private external fun wasmCoreNumImpLibmHypot(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.coreNumImpLibmLdexp(p0, p1)")
private external fun wasmCoreNumImpLibmLdexp(p0: Double, p1: Int): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmLog(p0)")
private external fun wasmCoreNumImpLibmLog(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmLog10(p0)")
private external fun wasmCoreNumImpLibmLog10(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmLog1p(p0)")
private external fun wasmCoreNumImpLibmLog1p(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmLog2(p0)")
private external fun wasmCoreNumImpLibmLog2(p0: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.coreNumImpLibmPow(p0, p1)")
private external fun wasmCoreNumImpLibmPow(p0: Double, p1: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmSin(p0)")
private external fun wasmCoreNumImpLibmSin(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmSinh(p0)")
private external fun wasmCoreNumImpLibmSinh(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmTan(p0)")
private external fun wasmCoreNumImpLibmTan(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmTanh(p0)")
private external fun wasmCoreNumImpLibmTanh(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.coreNumImpLibmTgamma(p0)")
private external fun wasmCoreNumImpLibmTgamma(p0: Double): Double

public actual fun powidf2(a: Double, b: Int): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmPowidf2(a, b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun powisf2(a: kotlin.Float, b: Int): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmPowisf2(a.toDouble(), b).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun powitf2(a: F128, b: Int): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cbrt(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmCbrt(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cbrtf(n: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmCbrtf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ceil(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmCeil(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ceilf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmCeilf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ceilf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ceilf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copysign(x: Double, y: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmCopysign(x, y) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copysignf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmCopysignf(x.toDouble(), y.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copysignf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copysignf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fabs(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmFabs(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fabsf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmFabsf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fabsf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fabsf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdim(a: Double, b: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmFdim(a, b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdimf(a: kotlin.Float, b: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmFdimf(a.toDouble(), b.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdimf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdimf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun floor(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmFloor(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun floorf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmFloorf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun floorf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun floorf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fma(x: Double, y: Double, z: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmFma(x, y, z) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaf(x: kotlin.Float, y: kotlin.Float, z: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmFmaf(x.toDouble(), y.toDouble(), z.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaf128(x: F128, y: F128, z: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaf16(x: F16, y: F16, z: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmax(x: Double, y: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmFmax(x, y) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaxf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmFmaxf(x.toDouble(), y.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaxf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaxf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaximum(x: Double, y: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmFmaximum(x, y) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaximumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmFmaximumf(x.toDouble(), y.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaximumf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaximumf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmin(x: Double, y: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmFmin(x, y) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fminf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmFminf(x.toDouble(), y.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fminf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fminf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fminimum(x: Double, y: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmFminimum(x, y) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fminimumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmFminimumf(x.toDouble(), y.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fminimumf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fminimumf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmod(x: Double, y: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmFmod(x, y) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmodf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmFmodf(x.toDouble(), y.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmodf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmodf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rint(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmRint(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rintf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmRintf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rintf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rintf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun round(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmRound(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun roundeven(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmRoundeven(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun roundevenf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmRoundevenf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun roundevenf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun roundevenf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun roundf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmRoundf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun roundf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun roundf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sqrt(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmSqrt(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sqrtf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmSqrtf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sqrtf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sqrtf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun trunc(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmTrunc(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun truncf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmTruncf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

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
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmAcosf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asinf(n: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmAsinf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atan2f(a: kotlin.Float, b: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmAtan2f(a.toDouble(), b.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atanf(n: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmAtanf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cosf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmCosf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun coshf(n: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmCoshf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erfcf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmErfcf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erff(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmErff(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun exp2f(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmExp2f(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmExpf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expm1f(n: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmExpm1f(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hypotf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmHypotf(x.toDouble(), y.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ldexpf(f: kotlin.Float, n: Int): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmLdexpf(f.toDouble(), n).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log10f(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmLog10f(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log1pf(n: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmLog1pf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log2f(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmLog2f(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun logf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmLogf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun powf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmPowf(x.toDouble(), y.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sinf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmSinf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sinhf(n: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmSinhf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tanf(n: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmTanf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tanhf(n: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmTanhf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tgammaf(x: kotlin.Float): kotlin.Float =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmTgammaf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun acos(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmAcos(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asin(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmAsin(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atan(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmAtan(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atan2(x: Double, y: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmAtan2(x, y) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cos(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmCos(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cosh(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmCosh(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erf(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmErf(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erfc(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmErfc(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun exp(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmExp(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun exp2(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmExp2(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expm1(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmExpm1(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hypot(x: Double, y: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmHypot(x, y) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ldexp(f: Double, n: Int): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmLdexp(f, n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmLog(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log10(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmLog10(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log1p(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmLog1p(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log2(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmLog2(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pow(x: Double, y: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmPow(x, y) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sin(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmSin(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sinh(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmSinh(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tan(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmTan(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tanh(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmTanh(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tgamma(x: Double): Double =
    if (numImpLibmNapiAvailable) wasmCoreNumImpLibmTgamma(x) else throw UnsupportedOperationException("Native FFI addon is not available")

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
