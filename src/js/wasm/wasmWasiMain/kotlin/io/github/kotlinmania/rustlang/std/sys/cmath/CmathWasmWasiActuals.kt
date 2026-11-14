@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.std.sys.cmath

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

// The host must provide the C companion wrapper exports in import module std.
@kotlin.wasm.WasmImport("std", "stdSysCmathAcos")
private external fun wasiStdSysCmathAcos(n: Double): Double

public actual fun acos(n: Double): Double =
    wasiStdSysCmathAcos(n).toDouble()

@kotlin.wasm.WasmImport("std", "stdSysCmathAcosh")
private external fun wasiStdSysCmathAcosh(n: Double): Double

public actual fun acosh(n: Double): Double =
    wasiStdSysCmathAcosh(n).toDouble()

@kotlin.wasm.WasmImport("std", "stdSysCmathAsin")
private external fun wasiStdSysCmathAsin(n: Double): Double

public actual fun asin(n: Double): Double =
    wasiStdSysCmathAsin(n).toDouble()

@kotlin.wasm.WasmImport("std", "stdSysCmathAsinh")
private external fun wasiStdSysCmathAsinh(n: Double): Double

public actual fun asinh(n: Double): Double =
    wasiStdSysCmathAsinh(n).toDouble()

@kotlin.wasm.WasmImport("std", "stdSysCmathAtan")
private external fun wasiStdSysCmathAtan(n: Double): Double

public actual fun atan(n: Double): Double =
    wasiStdSysCmathAtan(n).toDouble()

@kotlin.wasm.WasmImport("std", "stdSysCmathAtan2")
private external fun wasiStdSysCmathAtan2(a: Double, b: Double): Double

public actual fun atan2(a: Double, b: Double): Double =
    wasiStdSysCmathAtan2(a, b).toDouble()

@kotlin.wasm.WasmImport("std", "stdSysCmathCosh")
private external fun wasiStdSysCmathCosh(n: Double): Double

public actual fun cosh(n: Double): Double =
    wasiStdSysCmathCosh(n).toDouble()

@kotlin.wasm.WasmImport("std", "stdSysCmathExpm1")
private external fun wasiStdSysCmathExpm1(n: Double): Double

public actual fun expm1(n: Double): Double =
    wasiStdSysCmathExpm1(n).toDouble()

@kotlin.wasm.WasmImport("std", "stdSysCmathExpm1f")
private external fun wasiStdSysCmathExpm1f(n: Float): Float

public actual fun expm1f(n: Float): Float =
    wasiStdSysCmathExpm1f(n).toFloat()

@kotlin.wasm.WasmImport("std", "stdSysCmathHypot")
private external fun wasiStdSysCmathHypot(x: Double, y: Double): Double

public actual fun hypot(x: Double, y: Double): Double =
    wasiStdSysCmathHypot(x, y).toDouble()

@kotlin.wasm.WasmImport("std", "stdSysCmathHypotf")
private external fun wasiStdSysCmathHypotf(x: Float, y: Float): Float

public actual fun hypotf(x: Float, y: Float): Float =
    wasiStdSysCmathHypotf(x, y).toFloat()

@kotlin.wasm.WasmImport("std", "stdSysCmathLog1p")
private external fun wasiStdSysCmathLog1p(n: Double): Double

public actual fun log1p(n: Double): Double =
    wasiStdSysCmathLog1p(n).toDouble()

@kotlin.wasm.WasmImport("std", "stdSysCmathLog1pf")
private external fun wasiStdSysCmathLog1pf(n: Float): Float

public actual fun log1pf(n: Float): Float =
    wasiStdSysCmathLog1pf(n).toFloat()

@kotlin.wasm.WasmImport("std", "stdSysCmathSinh")
private external fun wasiStdSysCmathSinh(n: Double): Double

public actual fun sinh(n: Double): Double =
    wasiStdSysCmathSinh(n).toDouble()

@kotlin.wasm.WasmImport("std", "stdSysCmathTan")
private external fun wasiStdSysCmathTan(n: Double): Double

public actual fun tan(n: Double): Double =
    wasiStdSysCmathTan(n).toDouble()

@kotlin.wasm.WasmImport("std", "stdSysCmathTanh")
private external fun wasiStdSysCmathTanh(n: Double): Double

public actual fun tanh(n: Double): Double =
    wasiStdSysCmathTanh(n).toDouble()

@kotlin.wasm.WasmImport("std", "stdSysCmathTgamma")
private external fun wasiStdSysCmathTgamma(n: Double): Double

public actual fun tgamma(n: Double): Double =
    wasiStdSysCmathTgamma(n).toDouble()

@kotlin.wasm.WasmImport("std", "stdSysCmathTgammaf")
private external fun wasiStdSysCmathTgammaf(n: Float): Float

public actual fun tgammaf(n: Float): Float =
    wasiStdSysCmathTgammaf(n).toFloat()

public actual fun lgammaR(n: Double, s: RustMutableReference<Int>): Double =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysCmathLgammaR")

public actual fun lgammafR(n: Float, s: RustMutableReference<Int>): Float =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysCmathLgammafR")

@kotlin.wasm.WasmImport("std", "stdSysCmathErf")
private external fun wasiStdSysCmathErf(n: Double): Double

public actual fun erf(n: Double): Double =
    wasiStdSysCmathErf(n).toDouble()

@kotlin.wasm.WasmImport("std", "stdSysCmathErff")
private external fun wasiStdSysCmathErff(n: Float): Float

public actual fun erff(n: Float): Float =
    wasiStdSysCmathErff(n).toFloat()

@kotlin.wasm.WasmImport("std", "stdSysCmathErfc")
private external fun wasiStdSysCmathErfc(n: Double): Double

public actual fun erfc(n: Double): Double =
    wasiStdSysCmathErfc(n).toDouble()

@kotlin.wasm.WasmImport("std", "stdSysCmathErfcf")
private external fun wasiStdSysCmathErfcf(n: Float): Float

public actual fun erfcf(n: Float): Float =
    wasiStdSysCmathErfcf(n).toFloat()

public actual fun acosf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysCmathAcosf128")

public actual fun acoshf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysCmathAcoshf128")

public actual fun asinf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysCmathAsinf128")

public actual fun asinhf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysCmathAsinhf128")

public actual fun atanf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysCmathAtanf128")

public actual fun atan2f128(a: F128, b: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysCmathAtan2f128")

public actual fun cbrtf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysCmathCbrtf128")

public actual fun coshf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysCmathCoshf128")

public actual fun expm1f128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysCmathExpm1f128")

public actual fun hypotf128(x: F128, y: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysCmathHypotf128")

public actual fun log1pf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysCmathLog1pf128")

public actual fun sinhf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysCmathSinhf128")

public actual fun tanf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysCmathTanf128")

public actual fun tanhf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysCmathTanhf128")

public actual fun tgammaf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysCmathTgammaf128")

public actual fun lgammaf128R(n: F128, s: RustMutableReference<Int>): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysCmathLgammaf128R")

public actual fun erff128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysCmathErff128")

public actual fun erfcf128(n: F128): F128 =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysCmathErfcf128")

@kotlin.wasm.WasmImport("std", "stdSysCmathAcosf")
private external fun wasiStdSysCmathAcosf(n: Float): Float

public actual fun acosf(n: Float): Float =
    wasiStdSysCmathAcosf(n).toFloat()

@kotlin.wasm.WasmImport("std", "stdSysCmathAcoshf")
private external fun wasiStdSysCmathAcoshf(n: Float): Float

public actual fun acoshf(n: Float): Float =
    wasiStdSysCmathAcoshf(n).toFloat()

@kotlin.wasm.WasmImport("std", "stdSysCmathAsinf")
private external fun wasiStdSysCmathAsinf(n: Float): Float

public actual fun asinf(n: Float): Float =
    wasiStdSysCmathAsinf(n).toFloat()

@kotlin.wasm.WasmImport("std", "stdSysCmathAsinhf")
private external fun wasiStdSysCmathAsinhf(n: Float): Float

public actual fun asinhf(n: Float): Float =
    wasiStdSysCmathAsinhf(n).toFloat()

@kotlin.wasm.WasmImport("std", "stdSysCmathAtan2f")
private external fun wasiStdSysCmathAtan2f(a: Float, b: Float): Float

public actual fun atan2f(a: Float, b: Float): Float =
    wasiStdSysCmathAtan2f(a, b).toFloat()

@kotlin.wasm.WasmImport("std", "stdSysCmathAtanf")
private external fun wasiStdSysCmathAtanf(n: Float): Float

public actual fun atanf(n: Float): Float =
    wasiStdSysCmathAtanf(n).toFloat()

@kotlin.wasm.WasmImport("std", "stdSysCmathCoshf")
private external fun wasiStdSysCmathCoshf(n: Float): Float

public actual fun coshf(n: Float): Float =
    wasiStdSysCmathCoshf(n).toFloat()

@kotlin.wasm.WasmImport("std", "stdSysCmathSinhf")
private external fun wasiStdSysCmathSinhf(n: Float): Float

public actual fun sinhf(n: Float): Float =
    wasiStdSysCmathSinhf(n).toFloat()

@kotlin.wasm.WasmImport("std", "stdSysCmathTanf")
private external fun wasiStdSysCmathTanf(n: Float): Float

public actual fun tanf(n: Float): Float =
    wasiStdSysCmathTanf(n).toFloat()

@kotlin.wasm.WasmImport("std", "stdSysCmathTanhf")
private external fun wasiStdSysCmathTanhf(n: Float): Float

public actual fun tanhf(n: Float): Float =
    wasiStdSysCmathTanhf(n).toFloat()
