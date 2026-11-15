@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.num.imp.libm

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.num.complex.Complex

private object CoreNumImpLibmJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCoreNumImpLibmPowidf2(a: Double, b: Int): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmPowisf2(a: Float, b: Int): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmCbrt(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmCbrtf(n: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmCeil(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmCeilf(x: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmCopysign(x: Double, y: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmCopysignf(x: Float, y: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmFabs(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmFabsf(x: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmFdim(a: Double, b: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmFdimf(a: Float, b: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmFloor(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmFloorf(x: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmFma(x: Double, y: Double, z: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmFmaf(x: Float, y: Float, z: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmFmax(x: Double, y: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmFmaxf(x: Float, y: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmFmaximum(x: Double, y: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmFmaximumf(x: Float, y: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmFmin(x: Double, y: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmFminf(x: Float, y: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmFminimum(x: Double, y: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmFminimumf(x: Float, y: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmFmod(x: Double, y: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmFmodf(x: Float, y: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmRint(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmRintf(x: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmRound(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmRoundeven(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmRoundevenf(x: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmRoundf(x: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmSqrt(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmSqrtf(x: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmTrunc(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmTruncf(x: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmAcosf(n: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmAsinf(n: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmAtan2f(a: Float, b: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmAtanf(n: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmCosf(x: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmCoshf(n: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmErfcf(x: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmErff(x: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmExp2f(x: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmExpf(x: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmExpm1f(n: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmHypotf(x: Float, y: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmLdexpf(f: Float, n: Int): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmLog10f(x: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmLog1pf(n: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmLog2f(x: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmLogf(x: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmPowf(x: Float, y: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmSinf(x: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmSinhf(n: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmTanf(n: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmTanhf(n: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmTgammaf(x: Float): Float

    @JvmStatic
    external fun rustlangCoreNumImpLibmAcos(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmAsin(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmAtan(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmAtan2(x: Double, y: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmCos(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmCosh(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmErf(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmErfc(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmExp(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmExp2(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmExpm1(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmHypot(x: Double, y: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmLdexp(f: Double, n: Int): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmLog(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmLog10(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmLog1p(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmLog2(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmPow(x: Double, y: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmSin(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmSinh(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmTan(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmTanh(x: Double): Double

    @JvmStatic
    external fun rustlangCoreNumImpLibmTgamma(x: Double): Double
}

public actual fun powidf2(a: Double, b: Int): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmPowidf2(a, b)

public actual fun powisf2(a: kotlin.Float, b: Int): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmPowisf2(a, b)

public actual fun powitf2(a: F128, b: Int): F128 =
    F128()

public actual fun cbrt(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmCbrt(x)

public actual fun cbrtf(n: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmCbrtf(n)

public actual fun ceil(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmCeil(x)

public actual fun ceilf(x: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmCeilf(x)

public actual fun ceilf128(x: F128): F128 =
    F128()

public actual fun ceilf16(x: F16): F16 =
    F16()

public actual fun copysign(x: Double, y: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmCopysign(x, y)

public actual fun copysignf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmCopysignf(x, y)

public actual fun copysignf128(x: F128, y: F128): F128 =
    F128()

public actual fun copysignf16(x: F16, y: F16): F16 =
    F16()

public actual fun fabs(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmFabs(x)

public actual fun fabsf(x: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmFabsf(x)

public actual fun fabsf128(x: F128): F128 =
    F128()

public actual fun fabsf16(x: F16): F16 =
    F16()

public actual fun fdim(a: Double, b: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmFdim(a, b)

public actual fun fdimf(a: kotlin.Float, b: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmFdimf(a, b)

public actual fun fdimf128(x: F128, y: F128): F128 =
    F128()

public actual fun fdimf16(x: F16, y: F16): F16 =
    F16()

public actual fun floor(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmFloor(x)

public actual fun floorf(x: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmFloorf(x)

public actual fun floorf128(x: F128): F128 =
    F128()

public actual fun floorf16(x: F16): F16 =
    F16()

public actual fun fma(x: Double, y: Double, z: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmFma(x, y, z)

public actual fun fmaf(x: kotlin.Float, y: kotlin.Float, z: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmFmaf(x, y, z)

public actual fun fmaf128(x: F128, y: F128, z: F128): F128 =
    F128()

public actual fun fmaf16(x: F16, y: F16, z: F16): F16 =
    F16()

public actual fun fmax(x: Double, y: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmFmax(x, y)

public actual fun fmaxf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmFmaxf(x, y)

public actual fun fmaxf128(x: F128, y: F128): F128 =
    F128()

public actual fun fmaxf16(x: F16, y: F16): F16 =
    F16()

public actual fun fmaximum(x: Double, y: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmFmaximum(x, y)

public actual fun fmaximumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmFmaximumf(x, y)

public actual fun fmaximumf128(x: F128, y: F128): F128 =
    F128()

public actual fun fmaximumf16(x: F16, y: F16): F16 =
    F16()

public actual fun fmin(x: Double, y: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmFmin(x, y)

public actual fun fminf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmFminf(x, y)

public actual fun fminf128(x: F128, y: F128): F128 =
    F128()

public actual fun fminf16(x: F16, y: F16): F16 =
    F16()

public actual fun fminimum(x: Double, y: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmFminimum(x, y)

public actual fun fminimumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmFminimumf(x, y)

public actual fun fminimumf128(x: F128, y: F128): F128 =
    F128()

public actual fun fminimumf16(x: F16, y: F16): F16 =
    F16()

public actual fun fmod(x: Double, y: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmFmod(x, y)

public actual fun fmodf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmFmodf(x, y)

public actual fun fmodf128(x: F128, y: F128): F128 =
    F128()

public actual fun fmodf16(x: F16, y: F16): F16 =
    F16()

public actual fun rint(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmRint(x)

public actual fun rintf(x: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmRintf(x)

public actual fun rintf128(x: F128): F128 =
    F128()

public actual fun rintf16(x: F16): F16 =
    F16()

public actual fun round(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmRound(x)

public actual fun roundeven(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmRoundeven(x)

public actual fun roundevenf(x: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmRoundevenf(x)

public actual fun roundevenf128(x: F128): F128 =
    F128()

public actual fun roundevenf16(x: F16): F16 =
    F16()

public actual fun roundf(x: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmRoundf(x)

public actual fun roundf128(x: F128): F128 =
    F128()

public actual fun roundf16(x: F16): F16 =
    F16()

public actual fun sqrt(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmSqrt(x)

public actual fun sqrtf(x: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmSqrtf(x)

public actual fun sqrtf128(x: F128): F128 =
    F128()

public actual fun sqrtf16(x: F16): F16 =
    F16()

public actual fun trunc(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmTrunc(x)

public actual fun truncf(x: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmTruncf(x)

public actual fun truncf128(x: F128): F128 =
    F128()

public actual fun truncf16(x: F16): F16 =
    F16()

public actual fun mulsc3(a: kotlin.Float, b: kotlin.Float, c: kotlin.Float, d: kotlin.Float): Complex<kotlin.Float> =
    null as Complex<kotlin.Float>

public actual fun muldc3(a: Double, b: Double, c: Double, d: Double): Complex<Double> =
    null as Complex<Double>

public actual fun divsc3(a: kotlin.Float, b: kotlin.Float, c: kotlin.Float, d: kotlin.Float): Complex<kotlin.Float> =
    null as Complex<kotlin.Float>

public actual fun divdc3(a: Double, b: Double, c: Double, d: Double): Complex<Double> =
    null as Complex<Double>

public actual fun rustMulhc3(a: F16, b: F16, c: F16, d: F16): Complex<F16> =
    null as Complex<F16>

public actual fun rustMultc3(a: F128, b: F128, c: F128, d: F128): Complex<F128> =
    null as Complex<F128>

public actual fun rustDivhc3(a: F16, b: F16, c: F16, d: F16): Complex<F16> =
    null as Complex<F16>

public actual fun rustDivtc3(a: F128, b: F128, c: F128, d: F128): Complex<F128> =
    null as Complex<F128>

public actual fun acosf(n: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmAcosf(n)

public actual fun asinf(n: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmAsinf(n)

public actual fun atan2f(a: kotlin.Float, b: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmAtan2f(a, b)

public actual fun atanf(n: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmAtanf(n)

public actual fun cosf(x: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmCosf(x)

public actual fun coshf(n: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmCoshf(n)

public actual fun erfcf(x: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmErfcf(x)

public actual fun erff(x: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmErff(x)

public actual fun exp2f(x: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmExp2f(x)

public actual fun expf(x: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmExpf(x)

public actual fun expm1f(n: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmExpm1f(n)

public actual fun hypotf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmHypotf(x, y)

public actual fun ldexpf(f: kotlin.Float, n: Int): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmLdexpf(f, n)

public actual fun log10f(x: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmLog10f(x)

public actual fun log1pf(n: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmLog1pf(n)

public actual fun log2f(x: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmLog2f(x)

public actual fun logf(x: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmLogf(x)

public actual fun powf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmPowf(x, y)

public actual fun sinf(x: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmSinf(x)

public actual fun sinhf(n: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmSinhf(n)

public actual fun tanf(n: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmTanf(n)

public actual fun tanhf(n: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmTanhf(n)

public actual fun tgammaf(x: kotlin.Float): kotlin.Float =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmTgammaf(x)

public actual fun acos(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmAcos(x)

public actual fun asin(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmAsin(x)

public actual fun atan(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmAtan(x)

public actual fun atan2(x: Double, y: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmAtan2(x, y)

public actual fun cos(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmCos(x)

public actual fun cosh(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmCosh(x)

public actual fun erf(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmErf(x)

public actual fun erfc(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmErfc(x)

public actual fun exp(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmExp(x)

public actual fun exp2(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmExp2(x)

public actual fun expm1(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmExpm1(x)

public actual fun hypot(x: Double, y: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmHypot(x, y)

public actual fun ldexp(f: Double, n: Int): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmLdexp(f, n)

public actual fun log(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmLog(x)

public actual fun log10(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmLog10(x)

public actual fun log1p(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmLog1p(x)

public actual fun log2(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmLog2(x)

public actual fun pow(x: Double, y: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmPow(x, y)

public actual fun sin(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmSin(x)

public actual fun sinh(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmSinh(x)

public actual fun tan(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmTan(x)

public actual fun tanh(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmTanh(x)

public actual fun tgamma(x: Double): Double =
    CoreNumImpLibmJniBridge.rustlangCoreNumImpLibmTgamma(x)

public actual fun acosf128(x: F128): F128 =
    F128()

public actual fun asinf128(x: F128): F128 =
    F128()

public actual fun atanf128(x: F128): F128 =
    F128()

public actual fun cbrtf128(x: F128): F128 =
    F128()

public actual fun cosf128(x: F128): F128 =
    F128()

public actual fun erff128(x: F128): F128 =
    F128()

public actual fun expf128(x: F128): F128 =
    F128()

public actual fun exp2f128(x: F128): F128 =
    F128()

public actual fun expm1f128(x: F128): F128 =
    F128()

public actual fun hypotf128(x: F128, y: F128): F128 =
    F128()

public actual fun ldexpf128(f: F128, n: Int): F128 =
    F128()

public actual fun log10f128(x: F128): F128 =
    F128()

public actual fun log1pf128(x: F128): F128 =
    F128()

public actual fun log2f128(x: F128): F128 =
    F128()

public actual fun logf128(x: F128): F128 =
    F128()

public actual fun powf128(x: F128, y: F128): F128 =
    F128()

public actual fun sinf128(x: F128): F128 =
    F128()

public actual fun tanf128(x: F128): F128 =
    F128()

public actual fun tanhf128(x: F128): F128 =
    F128()

public actual fun tgammaf128(x: F128): F128 =
    F128()
