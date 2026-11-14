@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.cmath

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.stdSysCmathAcos
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.stdSysCmathAcosh
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.stdSysCmathAsin
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.stdSysCmathAsinh
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.stdSysCmathAtan
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.stdSysCmathAtan2
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.stdSysCmathCosh
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.stdSysCmathErf
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.stdSysCmathErfc
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.stdSysCmathErfcf
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.stdSysCmathErff
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.stdSysCmathExpm1
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.stdSysCmathExpm1f
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.stdSysCmathHypot
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.stdSysCmathHypotf
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.stdSysCmathLog1p
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.stdSysCmathLog1pf
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.stdSysCmathSinh
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.stdSysCmathTan
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.stdSysCmathTanh
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.stdSysCmathTgamma
import io.github.kotlinmania.rustlang.std.sys.cmath.cinterop.stdSysCmathTgammaf

public actual fun acos(n: Double): Double =
    stdSysCmathAcos(n)

public actual fun acosh(n: Double): Double =
    stdSysCmathAcosh(n)

public actual fun asin(n: Double): Double =
    stdSysCmathAsin(n)

public actual fun asinh(n: Double): Double =
    stdSysCmathAsinh(n)

public actual fun atan(n: Double): Double =
    stdSysCmathAtan(n)

public actual fun atan2(a: Double, b: Double): Double =
    stdSysCmathAtan2(a, b)

public actual fun cosh(n: Double): Double =
    stdSysCmathCosh(n)

public actual fun expm1(n: Double): Double =
    stdSysCmathExpm1(n)

public actual fun expm1f(n: Float): Float =
    stdSysCmathExpm1f(n)

public actual fun hypot(x: Double, y: Double): Double =
    stdSysCmathHypot(x, y)

public actual fun hypotf(x: Float, y: Float): Float =
    stdSysCmathHypotf(x, y)

public actual fun log1p(n: Double): Double =
    stdSysCmathLog1p(n)

public actual fun log1pf(n: Float): Float =
    stdSysCmathLog1pf(n)

public actual fun sinh(n: Double): Double =
    stdSysCmathSinh(n)

public actual fun tan(n: Double): Double =
    stdSysCmathTan(n)

public actual fun tanh(n: Double): Double =
    stdSysCmathTanh(n)

public actual fun tgamma(n: Double): Double =
    stdSysCmathTgamma(n)

public actual fun tgammaf(n: Float): Float =
    stdSysCmathTgammaf(n)

public actual fun lgammaR(n: Double, s: RustMutableReference<Int>): Double =
    0.0

public actual fun lgammafR(n: Float, s: RustMutableReference<Int>): Float =
    0.0f

public actual fun erf(n: Double): Double =
    stdSysCmathErf(n)

public actual fun erff(n: Float): Float =
    stdSysCmathErff(n)

public actual fun erfc(n: Double): Double =
    stdSysCmathErfc(n)

public actual fun erfcf(n: Float): Float =
    stdSysCmathErfcf(n)

public actual fun acosf128(n: F128): F128 =
    null as F128

public actual fun acoshf128(n: F128): F128 =
    null as F128

public actual fun asinf128(n: F128): F128 =
    null as F128

public actual fun asinhf128(n: F128): F128 =
    null as F128

public actual fun atanf128(n: F128): F128 =
    null as F128

public actual fun atan2f128(a: F128, b: F128): F128 =
    null as F128

public actual fun cbrtf128(n: F128): F128 =
    null as F128

public actual fun coshf128(n: F128): F128 =
    null as F128

public actual fun expm1f128(n: F128): F128 =
    null as F128

public actual fun hypotf128(x: F128, y: F128): F128 =
    null as F128

public actual fun log1pf128(n: F128): F128 =
    null as F128

public actual fun sinhf128(n: F128): F128 =
    null as F128

public actual fun tanf128(n: F128): F128 =
    null as F128

public actual fun tanhf128(n: F128): F128 =
    null as F128

public actual fun tgammaf128(n: F128): F128 =
    null as F128

public actual fun lgammaf128R(n: F128, s: RustMutableReference<Int>): F128 =
    null as F128

public actual fun erff128(n: F128): F128 =
    null as F128

public actual fun erfcf128(n: F128): F128 =
    null as F128
