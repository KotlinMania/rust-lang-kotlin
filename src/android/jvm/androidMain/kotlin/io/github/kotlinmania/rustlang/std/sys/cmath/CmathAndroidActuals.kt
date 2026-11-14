@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.cmath

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

private object SysCmathJniBridge {
    init {
        System.loadLibrary("std_jni")
    }

    @JvmStatic
    external fun stdSysCmathAcos(n: Double): Double

    @JvmStatic
    external fun stdSysCmathAcosh(n: Double): Double

    @JvmStatic
    external fun stdSysCmathAsin(n: Double): Double

    @JvmStatic
    external fun stdSysCmathAsinh(n: Double): Double

    @JvmStatic
    external fun stdSysCmathAtan(n: Double): Double

    @JvmStatic
    external fun stdSysCmathAtan2(a: Double, b: Double): Double

    @JvmStatic
    external fun stdSysCmathCosh(n: Double): Double

    @JvmStatic
    external fun stdSysCmathExpm1(n: Double): Double

    @JvmStatic
    external fun stdSysCmathExpm1f(n: Float): Float

    @JvmStatic
    external fun stdSysCmathHypot(x: Double, y: Double): Double

    @JvmStatic
    external fun stdSysCmathHypotf(x: Float, y: Float): Float

    @JvmStatic
    external fun stdSysCmathLog1p(n: Double): Double

    @JvmStatic
    external fun stdSysCmathLog1pf(n: Float): Float

    @JvmStatic
    external fun stdSysCmathSinh(n: Double): Double

    @JvmStatic
    external fun stdSysCmathTan(n: Double): Double

    @JvmStatic
    external fun stdSysCmathTanh(n: Double): Double

    @JvmStatic
    external fun stdSysCmathTgamma(n: Double): Double

    @JvmStatic
    external fun stdSysCmathTgammaf(n: Float): Float

    @JvmStatic
    external fun stdSysCmathErf(n: Double): Double

    @JvmStatic
    external fun stdSysCmathErff(n: Float): Float

    @JvmStatic
    external fun stdSysCmathErfc(n: Double): Double

    @JvmStatic
    external fun stdSysCmathErfcf(n: Float): Float

    @JvmStatic
    external fun stdSysCmathAcosf(n: Float): Float

    @JvmStatic
    external fun stdSysCmathAcoshf(n: Float): Float

    @JvmStatic
    external fun stdSysCmathAsinf(n: Float): Float

    @JvmStatic
    external fun stdSysCmathAsinhf(n: Float): Float

    @JvmStatic
    external fun stdSysCmathAtan2f(a: Float, b: Float): Float

    @JvmStatic
    external fun stdSysCmathAtanf(n: Float): Float

    @JvmStatic
    external fun stdSysCmathCoshf(n: Float): Float

    @JvmStatic
    external fun stdSysCmathSinhf(n: Float): Float

    @JvmStatic
    external fun stdSysCmathTanf(n: Float): Float

    @JvmStatic
    external fun stdSysCmathTanhf(n: Float): Float
}

public actual fun acos(n: Double): Double =
    SysCmathJniBridge.stdSysCmathAcos(n)

public actual fun acosh(n: Double): Double =
    SysCmathJniBridge.stdSysCmathAcosh(n)

public actual fun asin(n: Double): Double =
    SysCmathJniBridge.stdSysCmathAsin(n)

public actual fun asinh(n: Double): Double =
    SysCmathJniBridge.stdSysCmathAsinh(n)

public actual fun atan(n: Double): Double =
    SysCmathJniBridge.stdSysCmathAtan(n)

public actual fun atan2(a: Double, b: Double): Double =
    SysCmathJniBridge.stdSysCmathAtan2(a, b)

public actual fun cosh(n: Double): Double =
    SysCmathJniBridge.stdSysCmathCosh(n)

public actual fun expm1(n: Double): Double =
    SysCmathJniBridge.stdSysCmathExpm1(n)

public actual fun expm1f(n: Float): Float =
    SysCmathJniBridge.stdSysCmathExpm1f(n)

public actual fun hypot(x: Double, y: Double): Double =
    SysCmathJniBridge.stdSysCmathHypot(x, y)

public actual fun hypotf(x: Float, y: Float): Float =
    SysCmathJniBridge.stdSysCmathHypotf(x, y)

public actual fun log1p(n: Double): Double =
    SysCmathJniBridge.stdSysCmathLog1p(n)

public actual fun log1pf(n: Float): Float =
    SysCmathJniBridge.stdSysCmathLog1pf(n)

public actual fun sinh(n: Double): Double =
    SysCmathJniBridge.stdSysCmathSinh(n)

public actual fun tan(n: Double): Double =
    SysCmathJniBridge.stdSysCmathTan(n)

public actual fun tanh(n: Double): Double =
    SysCmathJniBridge.stdSysCmathTanh(n)

public actual fun tgamma(n: Double): Double =
    SysCmathJniBridge.stdSysCmathTgamma(n)

public actual fun tgammaf(n: Float): Float =
    SysCmathJniBridge.stdSysCmathTgammaf(n)

public actual fun lgammaR(n: Double, s: RustMutableReference<Int>): Double =
    0.0

public actual fun lgammafR(n: Float, s: RustMutableReference<Int>): Float =
    0.0f

public actual fun erf(n: Double): Double =
    SysCmathJniBridge.stdSysCmathErf(n)

public actual fun erff(n: Float): Float =
    SysCmathJniBridge.stdSysCmathErff(n)

public actual fun erfc(n: Double): Double =
    SysCmathJniBridge.stdSysCmathErfc(n)

public actual fun erfcf(n: Float): Float =
    SysCmathJniBridge.stdSysCmathErfcf(n)

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

public actual fun acosf(n: Float): Float =
    SysCmathJniBridge.stdSysCmathAcosf(n)

public actual fun acoshf(n: Float): Float =
    SysCmathJniBridge.stdSysCmathAcoshf(n)

public actual fun asinf(n: Float): Float =
    SysCmathJniBridge.stdSysCmathAsinf(n)

public actual fun asinhf(n: Float): Float =
    SysCmathJniBridge.stdSysCmathAsinhf(n)

public actual fun atan2f(a: Float, b: Float): Float =
    SysCmathJniBridge.stdSysCmathAtan2f(a, b)

public actual fun atanf(n: Float): Float =
    SysCmathJniBridge.stdSysCmathAtanf(n)

public actual fun coshf(n: Float): Float =
    SysCmathJniBridge.stdSysCmathCoshf(n)

public actual fun sinhf(n: Float): Float =
    SysCmathJniBridge.stdSysCmathSinhf(n)

public actual fun tanf(n: Float): Float =
    SysCmathJniBridge.stdSysCmathTanf(n)

public actual fun tanhf(n: Float): Float =
    SysCmathJniBridge.stdSysCmathTanhf(n)
