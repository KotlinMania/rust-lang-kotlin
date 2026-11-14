@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.std.sys.cmath

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

@JsFun("() => { if (typeof globalThis.__km_napi_std !== 'undefined') return globalThis.__km_napi_std !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_std = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_std = null; return false; } try { globalThis.__km_napi_std = req('@kotlinmania/std-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['std_native.node', 'build/node-ffi/std_native.node', 'build/node-ffi/release/std_native.node', 'build/node-ffi/debug/std_native.node', 'src/js/node-std/build/Release/std_native.node', 'src/js/node-std/build/Debug/std_native.node', 'src/js/node-std/dist/build/Release/std_native.node', 'src/js/node-std/dist/build/Debug/std_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_std = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_std = null; return false; }")
private external fun initSysCmathNapiAddon(): Boolean

private val sysCmathNapiAvailable: Boolean = initSysCmathNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathAcos(p0)")
private external fun wasmStdSysCmathAcos(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathAcosh(p0)")
private external fun wasmStdSysCmathAcosh(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathAsin(p0)")
private external fun wasmStdSysCmathAsin(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathAsinh(p0)")
private external fun wasmStdSysCmathAsinh(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathAtan(p0)")
private external fun wasmStdSysCmathAtan(p0: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysCmathAtan2(p0, p1)")
private external fun wasmStdSysCmathAtan2(p0: Double, p1: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathCosh(p0)")
private external fun wasmStdSysCmathCosh(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathExpm1(p0)")
private external fun wasmStdSysCmathExpm1(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathExpm1f(p0)")
private external fun wasmStdSysCmathExpm1f(p0: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysCmathHypot(p0, p1)")
private external fun wasmStdSysCmathHypot(p0: Double, p1: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysCmathHypotf(p0, p1)")
private external fun wasmStdSysCmathHypotf(p0: Double, p1: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathLog1p(p0)")
private external fun wasmStdSysCmathLog1p(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathLog1pf(p0)")
private external fun wasmStdSysCmathLog1pf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathSinh(p0)")
private external fun wasmStdSysCmathSinh(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathTan(p0)")
private external fun wasmStdSysCmathTan(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathTanh(p0)")
private external fun wasmStdSysCmathTanh(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathTgamma(p0)")
private external fun wasmStdSysCmathTgamma(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathTgammaf(p0)")
private external fun wasmStdSysCmathTgammaf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathErf(p0)")
private external fun wasmStdSysCmathErf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathErff(p0)")
private external fun wasmStdSysCmathErff(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathErfc(p0)")
private external fun wasmStdSysCmathErfc(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathErfcf(p0)")
private external fun wasmStdSysCmathErfcf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathAcosf(p0)")
private external fun wasmStdSysCmathAcosf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathAcoshf(p0)")
private external fun wasmStdSysCmathAcoshf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathAsinf(p0)")
private external fun wasmStdSysCmathAsinf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathAsinhf(p0)")
private external fun wasmStdSysCmathAsinhf(p0: Double): Double

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysCmathAtan2f(p0, p1)")
private external fun wasmStdSysCmathAtan2f(p0: Double, p1: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathAtanf(p0)")
private external fun wasmStdSysCmathAtanf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathCoshf(p0)")
private external fun wasmStdSysCmathCoshf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathSinhf(p0)")
private external fun wasmStdSysCmathSinhf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathTanf(p0)")
private external fun wasmStdSysCmathTanf(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_std.stdSysCmathTanhf(p0)")
private external fun wasmStdSysCmathTanhf(p0: Double): Double

public actual fun acos(n: Double): Double =
    if (sysCmathNapiAvailable) wasmStdSysCmathAcos(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun acosh(n: Double): Double =
    if (sysCmathNapiAvailable) wasmStdSysCmathAcosh(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asin(n: Double): Double =
    if (sysCmathNapiAvailable) wasmStdSysCmathAsin(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asinh(n: Double): Double =
    if (sysCmathNapiAvailable) wasmStdSysCmathAsinh(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atan(n: Double): Double =
    if (sysCmathNapiAvailable) wasmStdSysCmathAtan(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atan2(a: Double, b: Double): Double =
    if (sysCmathNapiAvailable) wasmStdSysCmathAtan2(a, b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cosh(n: Double): Double =
    if (sysCmathNapiAvailable) wasmStdSysCmathCosh(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expm1(n: Double): Double =
    if (sysCmathNapiAvailable) wasmStdSysCmathExpm1(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expm1f(n: Float): Float =
    if (sysCmathNapiAvailable) wasmStdSysCmathExpm1f(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hypot(x: Double, y: Double): Double =
    if (sysCmathNapiAvailable) wasmStdSysCmathHypot(x, y) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hypotf(x: Float, y: Float): Float =
    if (sysCmathNapiAvailable) wasmStdSysCmathHypotf(x.toDouble(), y.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log1p(n: Double): Double =
    if (sysCmathNapiAvailable) wasmStdSysCmathLog1p(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log1pf(n: Float): Float =
    if (sysCmathNapiAvailable) wasmStdSysCmathLog1pf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sinh(n: Double): Double =
    if (sysCmathNapiAvailable) wasmStdSysCmathSinh(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tan(n: Double): Double =
    if (sysCmathNapiAvailable) wasmStdSysCmathTan(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tanh(n: Double): Double =
    if (sysCmathNapiAvailable) wasmStdSysCmathTanh(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tgamma(n: Double): Double =
    if (sysCmathNapiAvailable) wasmStdSysCmathTgamma(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tgammaf(n: Float): Float =
    if (sysCmathNapiAvailable) wasmStdSysCmathTgammaf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lgammaR(n: Double, s: RustMutableReference<Int>): Double =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lgammafR(n: Float, s: RustMutableReference<Int>): Float =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erf(n: Double): Double =
    if (sysCmathNapiAvailable) wasmStdSysCmathErf(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erff(n: Float): Float =
    if (sysCmathNapiAvailable) wasmStdSysCmathErff(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erfc(n: Double): Double =
    if (sysCmathNapiAvailable) wasmStdSysCmathErfc(n) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erfcf(n: Float): Float =
    if (sysCmathNapiAvailable) wasmStdSysCmathErfcf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

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
    if (sysCmathNapiAvailable) wasmStdSysCmathAcosf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun acoshf(n: Float): Float =
    if (sysCmathNapiAvailable) wasmStdSysCmathAcoshf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asinf(n: Float): Float =
    if (sysCmathNapiAvailable) wasmStdSysCmathAsinf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asinhf(n: Float): Float =
    if (sysCmathNapiAvailable) wasmStdSysCmathAsinhf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atan2f(a: Float, b: Float): Float =
    if (sysCmathNapiAvailable) wasmStdSysCmathAtan2f(a.toDouble(), b.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atanf(n: Float): Float =
    if (sysCmathNapiAvailable) wasmStdSysCmathAtanf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun coshf(n: Float): Float =
    if (sysCmathNapiAvailable) wasmStdSysCmathCoshf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sinhf(n: Float): Float =
    if (sysCmathNapiAvailable) wasmStdSysCmathSinhf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tanf(n: Float): Float =
    if (sysCmathNapiAvailable) wasmStdSysCmathTanf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tanhf(n: Float): Float =
    if (sysCmathNapiAvailable) wasmStdSysCmathTanhf(n.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")
