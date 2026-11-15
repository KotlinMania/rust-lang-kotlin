@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.cmath

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathAcos
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathAcosf
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathAcosh
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathAcoshf
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathAsin
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathAsinf
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathAsinh
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathAsinhf
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathAtan
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathAtan2
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathAtan2f
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathAtanf
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathCosh
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathCoshf
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathErf
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathErfc
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathErfcf
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathErff
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathExpm1
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathExpm1f
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathHypot
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathHypotf
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathLog1p
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathLog1pf
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathSinh
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathSinhf
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathTan
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathTanf
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathTanh
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathTanhf
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathTgamma
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.rustlangStdSysCmathTgammaf
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun acos(n: Double): Double =
    rustlangStdSysCmathAcos(n)

public actual fun acosh(n: Double): Double =
    rustlangStdSysCmathAcosh(n)

public actual fun asin(n: Double): Double =
    rustlangStdSysCmathAsin(n)

public actual fun asinh(n: Double): Double =
    rustlangStdSysCmathAsinh(n)

public actual fun atan(n: Double): Double =
    rustlangStdSysCmathAtan(n)

public actual fun atan2(a: Double, b: Double): Double =
    rustlangStdSysCmathAtan2(a, b)

public actual fun cosh(n: Double): Double =
    rustlangStdSysCmathCosh(n)

public actual fun expm1(n: Double): Double =
    rustlangStdSysCmathExpm1(n)

public actual fun expm1f(n: Float): Float =
    rustlangStdSysCmathExpm1f(n)

public actual fun hypot(x: Double, y: Double): Double =
    rustlangStdSysCmathHypot(x, y)

public actual fun hypotf(x: Float, y: Float): Float =
    rustlangStdSysCmathHypotf(x, y)

public actual fun log1p(n: Double): Double =
    rustlangStdSysCmathLog1p(n)

public actual fun log1pf(n: Float): Float =
    rustlangStdSysCmathLog1pf(n)

public actual fun sinh(n: Double): Double =
    rustlangStdSysCmathSinh(n)

public actual fun tan(n: Double): Double =
    rustlangStdSysCmathTan(n)

public actual fun tanh(n: Double): Double =
    rustlangStdSysCmathTanh(n)

public actual fun tgamma(n: Double): Double =
    rustlangStdSysCmathTgamma(n)

public actual fun tgammaf(n: Float): Float =
    rustlangStdSysCmathTgammaf(n)

public actual fun lgammaR(n: Double, s: RustMutableReference<Int>): Double =
    0.0

public actual fun lgammafR(n: Float, s: RustMutableReference<Int>): Float =
    0.0f

public actual fun erf(n: Double): Double =
    rustlangStdSysCmathErf(n)

public actual fun erff(n: Float): Float =
    rustlangStdSysCmathErff(n)

public actual fun erfc(n: Double): Double =
    rustlangStdSysCmathErfc(n)

public actual fun erfcf(n: Float): Float =
    rustlangStdSysCmathErfcf(n)

public actual fun acosf128(n: F128): F128 =
    F128()

public actual fun acoshf128(n: F128): F128 =
    F128()

public actual fun asinf128(n: F128): F128 =
    F128()

public actual fun asinhf128(n: F128): F128 =
    F128()

public actual fun atanf128(n: F128): F128 =
    F128()

public actual fun atan2f128(a: F128, b: F128): F128 =
    F128()

public actual fun cbrtf128(n: F128): F128 =
    F128()

public actual fun coshf128(n: F128): F128 =
    F128()

public actual fun expm1f128(n: F128): F128 =
    F128()

public actual fun hypotf128(x: F128, y: F128): F128 =
    F128()

public actual fun log1pf128(n: F128): F128 =
    F128()

public actual fun sinhf128(n: F128): F128 =
    F128()

public actual fun tanf128(n: F128): F128 =
    F128()

public actual fun tanhf128(n: F128): F128 =
    F128()

public actual fun tgammaf128(n: F128): F128 =
    F128()

public actual fun lgammaf128R(n: F128, s: RustMutableReference<Int>): F128 =
    F128()

public actual fun erff128(n: F128): F128 =
    F128()

public actual fun erfcf128(n: F128): F128 =
    F128()

public actual fun acosf(n: Float): Float =
    rustlangStdSysCmathAcosf(n)

public actual fun acoshf(n: Float): Float =
    rustlangStdSysCmathAcoshf(n)

public actual fun asinf(n: Float): Float =
    rustlangStdSysCmathAsinf(n)

public actual fun asinhf(n: Float): Float =
    rustlangStdSysCmathAsinhf(n)

public actual fun atan2f(a: Float, b: Float): Float =
    rustlangStdSysCmathAtan2f(a, b)

public actual fun atanf(n: Float): Float =
    rustlangStdSysCmathAtanf(n)

public actual fun coshf(n: Float): Float =
    rustlangStdSysCmathCoshf(n)

public actual fun sinhf(n: Float): Float =
    rustlangStdSysCmathSinhf(n)

public actual fun tanf(n: Float): Float =
    rustlangStdSysCmathTanf(n)

public actual fun tanhf(n: Float): Float =
    rustlangStdSysCmathTanhf(n)
