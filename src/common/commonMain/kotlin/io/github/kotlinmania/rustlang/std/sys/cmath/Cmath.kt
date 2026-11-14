// port-lint: source sys/cmath.rs
package io.github.kotlinmania.rustlang.std.sys.cmath

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

// These symbols are all defined by `libm`,
// or by `compiler - builtins` on unsupported platforms.
public expect fun acos(n: Double): Double

public expect fun acosh(n: Double): Double

public expect fun asin(n: Double): Double

public expect fun asinh(n: Double): Double

public expect fun atan(n: Double): Double

public expect fun atan2(a: Double, b: Double): Double

public expect fun cosh(n: Double): Double

public expect fun expm1(n: Double): Double

public expect fun expm1f(n: Float): Float

public expect fun hypot(x: Double, y: Double): Double

public expect fun hypotf(x: Float, y: Float): Float

public expect fun log1p(n: Double): Double

public expect fun log1pf(n: Float): Float

public expect fun sinh(n: Double): Double

public expect fun tan(n: Double): Double

public expect fun tanh(n: Double): Double

public expect fun tgamma(n: Double): Double

public expect fun tgammaf(n: Float): Float

public expect fun lgammaR(n: Double, s: RustMutableReference<Int>): Double

public expect fun lgammafR(n: Float, s: RustMutableReference<Int>): Float

public expect fun erf(n: Double): Double

public expect fun erff(n: Float): Float

public expect fun erfc(n: Double): Double

public expect fun erfcf(n: Float): Float

public expect fun acosf128(n: F128): F128

public expect fun acoshf128(n: F128): F128

public expect fun asinf128(n: F128): F128

public expect fun asinhf128(n: F128): F128

public expect fun atanf128(n: F128): F128

public expect fun atan2f128(a: F128, b: F128): F128

public expect fun cbrtf128(n: F128): F128

public expect fun coshf128(n: F128): F128

public expect fun expm1f128(n: F128): F128

public expect fun hypotf128(x: F128, y: F128): F128

public expect fun log1pf128(n: F128): F128

public expect fun sinhf128(n: F128): F128

public expect fun tanf128(n: F128): F128

public expect fun tanhf128(n: F128): F128

public expect fun tgammaf128(n: F128): F128

public expect fun lgammaf128R(n: F128, s: RustMutableReference<Int>): F128

public expect fun erff128(n: F128): F128

public expect fun erfcf128(n: F128): F128

// On AIX, we don't have lgammafR only the f64 version, so we can
// use the f64 version lgammaR
public fun lgammafR(n: Float, s: RustMutableReference<Int>): Float = lgammaR(n.into(), s).toFloat()
