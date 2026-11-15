@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.cmath

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

private val stdSysCmathNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun acos(n: Double): Double =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathAcos(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun acosh(n: Double): Double =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathAcosh(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asin(n: Double): Double =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathAsin(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asinh(n: Double): Double =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathAsinh(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atan(n: Double): Double =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathAtan(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atan2(a: Double, b: Double): Double =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathAtan2(a, b) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cosh(n: Double): Double =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathCosh(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expm1(n: Double): Double =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathExpm1(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun expm1f(n: Float): Float =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathExpm1f(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hypot(x: Double, y: Double): Double =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathHypot(x, y) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hypotf(x: Float, y: Float): Float =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathHypotf(x.toDouble(), y.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log1p(n: Double): Double =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathLog1p(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun log1pf(n: Float): Float =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathLog1pf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sinh(n: Double): Double =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathSinh(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tan(n: Double): Double =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathTan(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tanh(n: Double): Double =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathTanh(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tgamma(n: Double): Double =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathTgamma(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tgammaf(n: Float): Float =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathTgammaf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lgammaR(n: Double, s: RustMutableReference<Int>): Double =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lgammafR(n: Float, s: RustMutableReference<Int>): Float =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erf(n: Double): Double =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathErf(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erff(n: Float): Float =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathErff(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erfc(n: Double): Double =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathErfc(n) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun erfcf(n: Float): Float =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathErfcf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

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
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathAcosf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun acoshf(n: Float): Float =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathAcoshf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asinf(n: Float): Float =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathAsinf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asinhf(n: Float): Float =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathAsinhf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atan2f(a: Float, b: Float): Float =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathAtan2f(a.toDouble(), b.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atanf(n: Float): Float =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathAtanf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun coshf(n: Float): Float =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathCoshf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sinhf(n: Float): Float =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathSinhf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tanf(n: Float): Float =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathTanf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tanhf(n: Float): Float =
    if (stdSysCmathNapiAddon != null) (stdSysCmathNapiAddon.rustlangStdSysCmathTanhf(n.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")
