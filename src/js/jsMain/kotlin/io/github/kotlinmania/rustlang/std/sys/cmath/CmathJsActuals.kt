@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.cmath

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

private val sysCmathNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_std !== 'undefined') return globalThis.__km_napi_std; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/std-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_std = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['std_native.node', 'build/node-ffi/std_native.node', 'build/node-ffi/release/std_native.node', 'build/node-ffi/debug/std_native.node', 'src/js/node-std/build/Release/std_native.node', 'src/js/node-std/build/Debug/std_native.node', 'src/js/node-std/dist/build/Release/std_native.node', 'src/js/node-std/dist/build/Debug/std_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_std = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_std = null; return null; })()")

public actual fun acos(n: Double): Double =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathAcos(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun acosh(n: Double): Double =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathAcosh(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asin(n: Double): Double =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathAsin(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asinh(n: Double): Double =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathAsinh(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atan(n: Double): Double =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathAtan(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atan2(a: Double, b: Double): Double =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathAtan2(a, b) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cosh(n: Double): Double =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathCosh(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expm1(n: Double): Double =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathExpm1(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expm1f(n: Float): Float =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathExpm1f(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hypot(x: Double, y: Double): Double =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathHypot(x, y) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hypotf(x: Float, y: Float): Float =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathHypotf(x.toDouble(), y.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log1p(n: Double): Double =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathLog1p(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log1pf(n: Float): Float =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathLog1pf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sinh(n: Double): Double =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathSinh(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tan(n: Double): Double =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathTan(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tanh(n: Double): Double =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathTanh(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tgamma(n: Double): Double =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathTgamma(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tgammaf(n: Float): Float =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathTgammaf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lgammaR(n: Double, s: RustMutableReference<Int>): Double =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lgammafR(n: Float, s: RustMutableReference<Int>): Float =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erf(n: Double): Double =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathErf(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erff(n: Float): Float =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathErff(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erfc(n: Double): Double =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathErfc(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erfcf(n: Float): Float =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathErfcf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

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
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathAcosf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun acoshf(n: Float): Float =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathAcoshf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asinf(n: Float): Float =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathAsinf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asinhf(n: Float): Float =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathAsinhf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atan2f(a: Float, b: Float): Float =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathAtan2f(a.toDouble(), b.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atanf(n: Float): Float =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathAtanf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun coshf(n: Float): Float =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathCoshf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sinhf(n: Float): Float =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathSinhf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tanf(n: Float): Float =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathTanf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tanhf(n: Float): Float =
    if (sysCmathNapiAddon != null) (sysCmathNapiAddon.stdSysCmathTanhf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")
