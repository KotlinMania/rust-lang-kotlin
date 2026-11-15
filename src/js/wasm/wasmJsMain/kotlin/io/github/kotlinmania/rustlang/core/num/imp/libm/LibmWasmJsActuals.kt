@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.core.num.imp.libm

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.num.complex.Complex

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initCoreNumImpLibmNapiAddon(): Boolean

private val coreNumImpLibmNapiAvailable: Boolean = initCoreNumImpLibmNapiAddon()

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmPowidf2(p0, p1)")
private external fun wasmRustlangCoreNumImpLibmPowidf2(p0: Double, p1: Int): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmPowisf2(p0, p1)")
private external fun wasmRustlangCoreNumImpLibmPowisf2(p0: Double, p1: Int): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmCbrt(p0)")
private external fun wasmRustlangCoreNumImpLibmCbrt(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmCbrtf(p0)")
private external fun wasmRustlangCoreNumImpLibmCbrtf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmCeil(p0)")
private external fun wasmRustlangCoreNumImpLibmCeil(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmCeilf(p0)")
private external fun wasmRustlangCoreNumImpLibmCeilf(p0: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmCopysign(p0, p1)")
private external fun wasmRustlangCoreNumImpLibmCopysign(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmCopysignf(p0, p1)")
private external fun wasmRustlangCoreNumImpLibmCopysignf(p0: Double, p1: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmFabs(p0)")
private external fun wasmRustlangCoreNumImpLibmFabs(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmFabsf(p0)")
private external fun wasmRustlangCoreNumImpLibmFabsf(p0: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmFdim(p0, p1)")
private external fun wasmRustlangCoreNumImpLibmFdim(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmFdimf(p0, p1)")
private external fun wasmRustlangCoreNumImpLibmFdimf(p0: Double, p1: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmFloor(p0)")
private external fun wasmRustlangCoreNumImpLibmFloor(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmFloorf(p0)")
private external fun wasmRustlangCoreNumImpLibmFloorf(p0: Double): Double

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmFma(p0, p1, p2)")
private external fun wasmRustlangCoreNumImpLibmFma(p0: Double, p1: Double, p2: Double): Double

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmFmaf(p0, p1, p2)")
private external fun wasmRustlangCoreNumImpLibmFmaf(p0: Double, p1: Double, p2: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmFmax(p0, p1)")
private external fun wasmRustlangCoreNumImpLibmFmax(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmFmaxf(p0, p1)")
private external fun wasmRustlangCoreNumImpLibmFmaxf(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmFmaximum(p0, p1)")
private external fun wasmRustlangCoreNumImpLibmFmaximum(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmFmaximumf(p0, p1)")
private external fun wasmRustlangCoreNumImpLibmFmaximumf(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmFmin(p0, p1)")
private external fun wasmRustlangCoreNumImpLibmFmin(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmFminf(p0, p1)")
private external fun wasmRustlangCoreNumImpLibmFminf(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmFminimum(p0, p1)")
private external fun wasmRustlangCoreNumImpLibmFminimum(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmFminimumf(p0, p1)")
private external fun wasmRustlangCoreNumImpLibmFminimumf(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmFmod(p0, p1)")
private external fun wasmRustlangCoreNumImpLibmFmod(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmFmodf(p0, p1)")
private external fun wasmRustlangCoreNumImpLibmFmodf(p0: Double, p1: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmRint(p0)")
private external fun wasmRustlangCoreNumImpLibmRint(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmRintf(p0)")
private external fun wasmRustlangCoreNumImpLibmRintf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmRound(p0)")
private external fun wasmRustlangCoreNumImpLibmRound(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmRoundeven(p0)")
private external fun wasmRustlangCoreNumImpLibmRoundeven(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmRoundevenf(p0)")
private external fun wasmRustlangCoreNumImpLibmRoundevenf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmRoundf(p0)")
private external fun wasmRustlangCoreNumImpLibmRoundf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmSqrt(p0)")
private external fun wasmRustlangCoreNumImpLibmSqrt(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmSqrtf(p0)")
private external fun wasmRustlangCoreNumImpLibmSqrtf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmTrunc(p0)")
private external fun wasmRustlangCoreNumImpLibmTrunc(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmTruncf(p0)")
private external fun wasmRustlangCoreNumImpLibmTruncf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmAcosf(p0)")
private external fun wasmRustlangCoreNumImpLibmAcosf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmAsinf(p0)")
private external fun wasmRustlangCoreNumImpLibmAsinf(p0: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmAtan2f(p0, p1)")
private external fun wasmRustlangCoreNumImpLibmAtan2f(p0: Double, p1: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmAtanf(p0)")
private external fun wasmRustlangCoreNumImpLibmAtanf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmCosf(p0)")
private external fun wasmRustlangCoreNumImpLibmCosf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmCoshf(p0)")
private external fun wasmRustlangCoreNumImpLibmCoshf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmErfcf(p0)")
private external fun wasmRustlangCoreNumImpLibmErfcf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmErff(p0)")
private external fun wasmRustlangCoreNumImpLibmErff(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmExp2f(p0)")
private external fun wasmRustlangCoreNumImpLibmExp2f(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmExpf(p0)")
private external fun wasmRustlangCoreNumImpLibmExpf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmExpm1f(p0)")
private external fun wasmRustlangCoreNumImpLibmExpm1f(p0: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmHypotf(p0, p1)")
private external fun wasmRustlangCoreNumImpLibmHypotf(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmLdexpf(p0, p1)")
private external fun wasmRustlangCoreNumImpLibmLdexpf(p0: Double, p1: Int): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmLog10f(p0)")
private external fun wasmRustlangCoreNumImpLibmLog10f(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmLog1pf(p0)")
private external fun wasmRustlangCoreNumImpLibmLog1pf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmLog2f(p0)")
private external fun wasmRustlangCoreNumImpLibmLog2f(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmLogf(p0)")
private external fun wasmRustlangCoreNumImpLibmLogf(p0: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmPowf(p0, p1)")
private external fun wasmRustlangCoreNumImpLibmPowf(p0: Double, p1: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmSinf(p0)")
private external fun wasmRustlangCoreNumImpLibmSinf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmSinhf(p0)")
private external fun wasmRustlangCoreNumImpLibmSinhf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmTanf(p0)")
private external fun wasmRustlangCoreNumImpLibmTanf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmTanhf(p0)")
private external fun wasmRustlangCoreNumImpLibmTanhf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmTgammaf(p0)")
private external fun wasmRustlangCoreNumImpLibmTgammaf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmAcos(p0)")
private external fun wasmRustlangCoreNumImpLibmAcos(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmAsin(p0)")
private external fun wasmRustlangCoreNumImpLibmAsin(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmAtan(p0)")
private external fun wasmRustlangCoreNumImpLibmAtan(p0: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmAtan2(p0, p1)")
private external fun wasmRustlangCoreNumImpLibmAtan2(p0: Double, p1: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmCos(p0)")
private external fun wasmRustlangCoreNumImpLibmCos(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmCosh(p0)")
private external fun wasmRustlangCoreNumImpLibmCosh(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmErf(p0)")
private external fun wasmRustlangCoreNumImpLibmErf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmErfc(p0)")
private external fun wasmRustlangCoreNumImpLibmErfc(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmExp(p0)")
private external fun wasmRustlangCoreNumImpLibmExp(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmExp2(p0)")
private external fun wasmRustlangCoreNumImpLibmExp2(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmExpm1(p0)")
private external fun wasmRustlangCoreNumImpLibmExpm1(p0: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmHypot(p0, p1)")
private external fun wasmRustlangCoreNumImpLibmHypot(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmLdexp(p0, p1)")
private external fun wasmRustlangCoreNumImpLibmLdexp(p0: Double, p1: Int): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmLog(p0)")
private external fun wasmRustlangCoreNumImpLibmLog(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmLog10(p0)")
private external fun wasmRustlangCoreNumImpLibmLog10(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmLog1p(p0)")
private external fun wasmRustlangCoreNumImpLibmLog1p(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmLog2(p0)")
private external fun wasmRustlangCoreNumImpLibmLog2(p0: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmPow(p0, p1)")
private external fun wasmRustlangCoreNumImpLibmPow(p0: Double, p1: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmSin(p0)")
private external fun wasmRustlangCoreNumImpLibmSin(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmSinh(p0)")
private external fun wasmRustlangCoreNumImpLibmSinh(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmTan(p0)")
private external fun wasmRustlangCoreNumImpLibmTan(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmTanh(p0)")
private external fun wasmRustlangCoreNumImpLibmTanh(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCoreNumImpLibmTgamma(p0)")
private external fun wasmRustlangCoreNumImpLibmTgamma(p0: Double): Double

public actual fun powidf2(a: Double, b: Int): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmPowidf2(a, b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun powisf2(a: kotlin.Float, b: Int): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmPowisf2(a.toDouble(), b).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun powitf2(a: F128, b: Int): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cbrt(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmCbrt(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cbrtf(n: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmCbrtf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ceil(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmCeil(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ceilf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmCeilf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ceilf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ceilf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copysign(x: Double, y: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmCopysign(x, y) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copysignf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmCopysignf(x.toDouble(), y.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copysignf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copysignf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fabs(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmFabs(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fabsf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmFabsf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fabsf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fabsf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdim(a: Double, b: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmFdim(a, b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdimf(a: kotlin.Float, b: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmFdimf(a.toDouble(), b.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdimf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdimf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun floor(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmFloor(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun floorf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmFloorf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun floorf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun floorf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fma(x: Double, y: Double, z: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmFma(x, y, z) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaf(x: kotlin.Float, y: kotlin.Float, z: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmFmaf(x.toDouble(), y.toDouble(), z.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaf128(x: F128, y: F128, z: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaf16(x: F16, y: F16, z: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmax(x: Double, y: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmFmax(x, y) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaxf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmFmaxf(x.toDouble(), y.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaxf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaxf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaximum(x: Double, y: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmFmaximum(x, y) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaximumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmFmaximumf(x.toDouble(), y.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaximumf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmaximumf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmin(x: Double, y: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmFmin(x, y) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fminf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmFminf(x.toDouble(), y.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fminf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fminf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fminimum(x: Double, y: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmFminimum(x, y) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fminimumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmFminimumf(x.toDouble(), y.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fminimumf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fminimumf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmod(x: Double, y: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmFmod(x, y) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmodf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmFmodf(x.toDouble(), y.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmodf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmodf16(x: F16, y: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rint(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmRint(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rintf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmRintf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rintf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rintf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun round(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmRound(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun roundeven(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmRoundeven(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun roundevenf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmRoundevenf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun roundevenf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun roundevenf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun roundf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmRoundf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun roundf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun roundf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sqrt(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmSqrt(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sqrtf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmSqrtf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sqrtf128(x: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sqrtf16(x: F16): F16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun trunc(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmTrunc(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun truncf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmTruncf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

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
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmAcosf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asinf(n: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmAsinf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atan2f(a: kotlin.Float, b: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmAtan2f(a.toDouble(), b.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atanf(n: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmAtanf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cosf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmCosf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun coshf(n: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmCoshf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erfcf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmErfcf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erff(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmErff(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun exp2f(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmExp2f(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmExpf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expm1f(n: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmExpm1f(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hypotf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmHypotf(x.toDouble(), y.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ldexpf(f: kotlin.Float, n: Int): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmLdexpf(f.toDouble(), n).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log10f(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmLog10f(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log1pf(n: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmLog1pf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log2f(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmLog2f(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun logf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmLogf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun powf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmPowf(x.toDouble(), y.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sinf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmSinf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sinhf(n: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmSinhf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tanf(n: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmTanf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tanhf(n: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmTanhf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tgammaf(x: kotlin.Float): kotlin.Float =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmTgammaf(x.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun acos(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmAcos(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asin(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmAsin(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atan(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmAtan(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atan2(x: Double, y: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmAtan2(x, y) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cos(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmCos(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cosh(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmCosh(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erf(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmErf(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erfc(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmErfc(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun exp(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmExp(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun exp2(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmExp2(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expm1(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmExpm1(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hypot(x: Double, y: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmHypot(x, y) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ldexp(f: Double, n: Int): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmLdexp(f, n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmLog(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log10(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmLog10(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log1p(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmLog1p(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log2(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmLog2(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pow(x: Double, y: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmPow(x, y) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sin(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmSin(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sinh(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmSinh(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tan(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmTan(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tanh(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmTanh(x) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tgamma(x: Double): Double =
    if (coreNumImpLibmNapiAvailable) wasmRustlangCoreNumImpLibmTgamma(x) else throw UnsupportedOperationException("Native FFI addon is not available")

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
