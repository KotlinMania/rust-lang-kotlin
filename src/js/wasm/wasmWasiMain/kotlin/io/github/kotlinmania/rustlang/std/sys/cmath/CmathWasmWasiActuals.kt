@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.std.sys.cmath

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathAcos")
private external fun wasiRustlangStdSysCmathAcos(n: Double): Double

public actual fun acos(n: Double): Double =
    wasiRustlangStdSysCmathAcos(n).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathAcosh")
private external fun wasiRustlangStdSysCmathAcosh(n: Double): Double

public actual fun acosh(n: Double): Double =
    wasiRustlangStdSysCmathAcosh(n).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathAsin")
private external fun wasiRustlangStdSysCmathAsin(n: Double): Double

public actual fun asin(n: Double): Double =
    wasiRustlangStdSysCmathAsin(n).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathAsinh")
private external fun wasiRustlangStdSysCmathAsinh(n: Double): Double

public actual fun asinh(n: Double): Double =
    wasiRustlangStdSysCmathAsinh(n).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathAtan")
private external fun wasiRustlangStdSysCmathAtan(n: Double): Double

public actual fun atan(n: Double): Double =
    wasiRustlangStdSysCmathAtan(n).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathAtan2")
private external fun wasiRustlangStdSysCmathAtan2(a: Double, b: Double): Double

public actual fun atan2(a: Double, b: Double): Double =
    wasiRustlangStdSysCmathAtan2(a, b).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathCosh")
private external fun wasiRustlangStdSysCmathCosh(n: Double): Double

public actual fun cosh(n: Double): Double =
    wasiRustlangStdSysCmathCosh(n).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathExpm1")
private external fun wasiRustlangStdSysCmathExpm1(n: Double): Double

public actual fun expm1(n: Double): Double =
    wasiRustlangStdSysCmathExpm1(n).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathExpm1f")
private external fun wasiRustlangStdSysCmathExpm1f(n: Float): Float

public actual fun expm1f(n: Float): Float =
    wasiRustlangStdSysCmathExpm1f(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathHypot")
private external fun wasiRustlangStdSysCmathHypot(x: Double, y: Double): Double

public actual fun hypot(x: Double, y: Double): Double =
    wasiRustlangStdSysCmathHypot(x, y).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathHypotf")
private external fun wasiRustlangStdSysCmathHypotf(x: Float, y: Float): Float

public actual fun hypotf(x: Float, y: Float): Float =
    wasiRustlangStdSysCmathHypotf(x, y).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathLog1p")
private external fun wasiRustlangStdSysCmathLog1p(n: Double): Double

public actual fun log1p(n: Double): Double =
    wasiRustlangStdSysCmathLog1p(n).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathLog1pf")
private external fun wasiRustlangStdSysCmathLog1pf(n: Float): Float

public actual fun log1pf(n: Float): Float =
    wasiRustlangStdSysCmathLog1pf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathSinh")
private external fun wasiRustlangStdSysCmathSinh(n: Double): Double

public actual fun sinh(n: Double): Double =
    wasiRustlangStdSysCmathSinh(n).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathTan")
private external fun wasiRustlangStdSysCmathTan(n: Double): Double

public actual fun tan(n: Double): Double =
    wasiRustlangStdSysCmathTan(n).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathTanh")
private external fun wasiRustlangStdSysCmathTanh(n: Double): Double

public actual fun tanh(n: Double): Double =
    wasiRustlangStdSysCmathTanh(n).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathTgamma")
private external fun wasiRustlangStdSysCmathTgamma(n: Double): Double

public actual fun tgamma(n: Double): Double =
    wasiRustlangStdSysCmathTgamma(n).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathTgammaf")
private external fun wasiRustlangStdSysCmathTgammaf(n: Float): Float

public actual fun tgammaf(n: Float): Float =
    wasiRustlangStdSysCmathTgammaf(n).toFloat()

public actual fun lgammaR(n: Double, s: RustMutableReference<Int>): Double =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysCmathLgammaR")

public actual fun lgammafR(n: Float, s: RustMutableReference<Int>): Float =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysCmathLgammafR")

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathErf")
private external fun wasiRustlangStdSysCmathErf(n: Double): Double

public actual fun erf(n: Double): Double =
    wasiRustlangStdSysCmathErf(n).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathErff")
private external fun wasiRustlangStdSysCmathErff(n: Float): Float

public actual fun erff(n: Float): Float =
    wasiRustlangStdSysCmathErff(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathErfc")
private external fun wasiRustlangStdSysCmathErfc(n: Double): Double

public actual fun erfc(n: Double): Double =
    wasiRustlangStdSysCmathErfc(n).toDouble()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathErfcf")
private external fun wasiRustlangStdSysCmathErfcf(n: Float): Float

public actual fun erfcf(n: Float): Float =
    wasiRustlangStdSysCmathErfcf(n).toFloat()

public actual fun acosf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysCmathAcosf128")

public actual fun acoshf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysCmathAcoshf128")

public actual fun asinf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysCmathAsinf128")

public actual fun asinhf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysCmathAsinhf128")

public actual fun atanf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysCmathAtanf128")

public actual fun atan2f128(a: F128, b: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysCmathAtan2f128")

public actual fun cbrtf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysCmathCbrtf128")

public actual fun coshf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysCmathCoshf128")

public actual fun expm1f128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysCmathExpm1f128")

public actual fun hypotf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysCmathHypotf128")

public actual fun log1pf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysCmathLog1pf128")

public actual fun sinhf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysCmathSinhf128")

public actual fun tanf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysCmathTanf128")

public actual fun tanhf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysCmathTanhf128")

public actual fun tgammaf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysCmathTgammaf128")

public actual fun lgammaf128R(n: F128, s: RustMutableReference<Int>): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysCmathLgammaf128R")

public actual fun erff128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysCmathErff128")

public actual fun erfcf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysCmathErfcf128")

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathAcosf")
private external fun wasiRustlangStdSysCmathAcosf(n: Float): Float

public actual fun acosf(n: Float): Float =
    wasiRustlangStdSysCmathAcosf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathAcoshf")
private external fun wasiRustlangStdSysCmathAcoshf(n: Float): Float

public actual fun acoshf(n: Float): Float =
    wasiRustlangStdSysCmathAcoshf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathAsinf")
private external fun wasiRustlangStdSysCmathAsinf(n: Float): Float

public actual fun asinf(n: Float): Float =
    wasiRustlangStdSysCmathAsinf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathAsinhf")
private external fun wasiRustlangStdSysCmathAsinhf(n: Float): Float

public actual fun asinhf(n: Float): Float =
    wasiRustlangStdSysCmathAsinhf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathAtan2f")
private external fun wasiRustlangStdSysCmathAtan2f(a: Float, b: Float): Float

public actual fun atan2f(a: Float, b: Float): Float =
    wasiRustlangStdSysCmathAtan2f(a, b).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathAtanf")
private external fun wasiRustlangStdSysCmathAtanf(n: Float): Float

public actual fun atanf(n: Float): Float =
    wasiRustlangStdSysCmathAtanf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathCoshf")
private external fun wasiRustlangStdSysCmathCoshf(n: Float): Float

public actual fun coshf(n: Float): Float =
    wasiRustlangStdSysCmathCoshf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathSinhf")
private external fun wasiRustlangStdSysCmathSinhf(n: Float): Float

public actual fun sinhf(n: Float): Float =
    wasiRustlangStdSysCmathSinhf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathTanf")
private external fun wasiRustlangStdSysCmathTanf(n: Float): Float

public actual fun tanf(n: Float): Float =
    wasiRustlangStdSysCmathTanf(n).toFloat()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysCmathTanhf")
private external fun wasiRustlangStdSysCmathTanhf(n: Float): Float

public actual fun tanhf(n: Float): Float =
    wasiRustlangStdSysCmathTanhf(n).toFloat()
