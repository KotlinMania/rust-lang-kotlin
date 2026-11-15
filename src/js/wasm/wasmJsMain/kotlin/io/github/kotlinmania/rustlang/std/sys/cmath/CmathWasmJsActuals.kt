@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.std.sys.cmath

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initStdSysCmathNapiAddon(): Boolean

private val stdSysCmathNapiAvailable: Boolean = initStdSysCmathNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathAcos(p0)")
private external fun wasmRustlangStdSysCmathAcos(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathAcosh(p0)")
private external fun wasmRustlangStdSysCmathAcosh(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathAsin(p0)")
private external fun wasmRustlangStdSysCmathAsin(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathAsinh(p0)")
private external fun wasmRustlangStdSysCmathAsinh(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathAtan(p0)")
private external fun wasmRustlangStdSysCmathAtan(p0: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysCmathAtan2(p0, p1)")
private external fun wasmRustlangStdSysCmathAtan2(p0: Double, p1: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathCosh(p0)")
private external fun wasmRustlangStdSysCmathCosh(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathExpm1(p0)")
private external fun wasmRustlangStdSysCmathExpm1(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathExpm1f(p0)")
private external fun wasmRustlangStdSysCmathExpm1f(p0: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysCmathHypot(p0, p1)")
private external fun wasmRustlangStdSysCmathHypot(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysCmathHypotf(p0, p1)")
private external fun wasmRustlangStdSysCmathHypotf(p0: Double, p1: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathLog1p(p0)")
private external fun wasmRustlangStdSysCmathLog1p(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathLog1pf(p0)")
private external fun wasmRustlangStdSysCmathLog1pf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathSinh(p0)")
private external fun wasmRustlangStdSysCmathSinh(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathTan(p0)")
private external fun wasmRustlangStdSysCmathTan(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathTanh(p0)")
private external fun wasmRustlangStdSysCmathTanh(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathTgamma(p0)")
private external fun wasmRustlangStdSysCmathTgamma(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathTgammaf(p0)")
private external fun wasmRustlangStdSysCmathTgammaf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathErf(p0)")
private external fun wasmRustlangStdSysCmathErf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathErff(p0)")
private external fun wasmRustlangStdSysCmathErff(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathErfc(p0)")
private external fun wasmRustlangStdSysCmathErfc(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathErfcf(p0)")
private external fun wasmRustlangStdSysCmathErfcf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathAcosf(p0)")
private external fun wasmRustlangStdSysCmathAcosf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathAcoshf(p0)")
private external fun wasmRustlangStdSysCmathAcoshf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathAsinf(p0)")
private external fun wasmRustlangStdSysCmathAsinf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathAsinhf(p0)")
private external fun wasmRustlangStdSysCmathAsinhf(p0: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysCmathAtan2f(p0, p1)")
private external fun wasmRustlangStdSysCmathAtan2f(p0: Double, p1: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathAtanf(p0)")
private external fun wasmRustlangStdSysCmathAtanf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathCoshf(p0)")
private external fun wasmRustlangStdSysCmathCoshf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathSinhf(p0)")
private external fun wasmRustlangStdSysCmathSinhf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathTanf(p0)")
private external fun wasmRustlangStdSysCmathTanf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysCmathTanhf(p0)")
private external fun wasmRustlangStdSysCmathTanhf(p0: Double): Double

public actual fun acos(n: Double): Double =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathAcos(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun acosh(n: Double): Double =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathAcosh(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asin(n: Double): Double =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathAsin(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asinh(n: Double): Double =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathAsinh(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atan(n: Double): Double =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathAtan(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atan2(a: Double, b: Double): Double =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathAtan2(a, b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cosh(n: Double): Double =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathCosh(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expm1(n: Double): Double =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathExpm1(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expm1f(n: Float): Float =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathExpm1f(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hypot(x: Double, y: Double): Double =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathHypot(x, y) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hypotf(x: Float, y: Float): Float =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathHypotf(x.toDouble(), y.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log1p(n: Double): Double =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathLog1p(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log1pf(n: Float): Float =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathLog1pf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sinh(n: Double): Double =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathSinh(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tan(n: Double): Double =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathTan(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tanh(n: Double): Double =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathTanh(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tgamma(n: Double): Double =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathTgamma(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tgammaf(n: Float): Float =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathTgammaf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lgammaR(n: Double, s: RustMutableReference<Int>): Double =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lgammafR(n: Float, s: RustMutableReference<Int>): Float =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erf(n: Double): Double =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathErf(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erff(n: Float): Float =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathErff(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erfc(n: Double): Double =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathErfc(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erfcf(n: Float): Float =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathErfcf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun acosf128(n: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun acoshf128(n: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asinf128(n: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asinhf128(n: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atanf128(n: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atan2f128(a: F128, b: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cbrtf128(n: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun coshf128(n: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expm1f128(n: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hypotf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log1pf128(n: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sinhf128(n: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tanf128(n: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tanhf128(n: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tgammaf128(n: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lgammaf128R(n: F128, s: RustMutableReference<Int>): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erff128(n: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erfcf128(n: F128): F128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun acosf(n: Float): Float =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathAcosf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun acoshf(n: Float): Float =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathAcoshf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asinf(n: Float): Float =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathAsinf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asinhf(n: Float): Float =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathAsinhf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atan2f(a: Float, b: Float): Float =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathAtan2f(a.toDouble(), b.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atanf(n: Float): Float =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathAtanf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun coshf(n: Float): Float =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathCoshf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sinhf(n: Float): Float =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathSinhf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tanf(n: Float): Float =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathTanf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tanhf(n: Float): Float =
    if (stdSysCmathNapiAvailable) wasmRustlangStdSysCmathTanhf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")
