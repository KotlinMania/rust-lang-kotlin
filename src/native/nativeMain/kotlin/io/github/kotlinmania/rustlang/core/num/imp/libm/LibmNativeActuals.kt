@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.num.imp.libm

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmAcos
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmAcosf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmAsin
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmAsinf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmAtan
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmAtan2
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmAtan2f
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmAtanf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmCbrt
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmCbrtf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmCeil
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmCeilf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmCopysign
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmCopysignf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmCos
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmCosf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmCosh
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmCoshf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmErf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmErfc
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmErfcf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmErff
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmExp
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmExp2
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmExp2f
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmExpf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmExpm1
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmExpm1f
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmFabs
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmFabsf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmFdim
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmFdimf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmFloor
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmFloorf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmFma
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmFmaf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmFmax
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmFmaxf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmFmaximum
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmFmaximumf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmFmin
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmFminf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmFminimum
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmFminimumf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmFmod
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmFmodf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmHypot
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmHypotf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmLdexp
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmLdexpf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmLog
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmLog10
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmLog10f
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmLog1p
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmLog1pf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmLog2
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmLog2f
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmLogf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmPow
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmPowf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmPowidf2
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmPowisf2
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmRint
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmRintf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmRound
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmRoundeven
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmRoundevenf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmRoundf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmSin
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmSinf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmSinh
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmSinhf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmSqrt
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmSqrtf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmTan
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmTanf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmTanh
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmTanhf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmTgamma
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmTgammaf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmTrunc
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.rustlangCoreNumImpLibmTruncf
import io.github.kotlinmania.rustlang.num.complex.Complex
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun powidf2(a: Double, b: Int): Double =
    rustlangCoreNumImpLibmPowidf2(a, b)

public actual fun powisf2(a: kotlin.Float, b: Int): kotlin.Float =
    rustlangCoreNumImpLibmPowisf2(a, b)

public actual fun powitf2(a: F128, b: Int): F128 =
    F128()

public actual fun cbrt(x: Double): Double =
    rustlangCoreNumImpLibmCbrt(x)

public actual fun cbrtf(n: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmCbrtf(n)

public actual fun ceil(x: Double): Double =
    rustlangCoreNumImpLibmCeil(x)

public actual fun ceilf(x: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmCeilf(x)

public actual fun ceilf128(x: F128): F128 =
    F128()

public actual fun ceilf16(x: F16): F16 =
    F16()

public actual fun copysign(x: Double, y: Double): Double =
    rustlangCoreNumImpLibmCopysign(x, y)

public actual fun copysignf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmCopysignf(x, y)

public actual fun copysignf128(x: F128, y: F128): F128 =
    F128()

public actual fun copysignf16(x: F16, y: F16): F16 =
    F16()

public actual fun fabs(x: Double): Double =
    rustlangCoreNumImpLibmFabs(x)

public actual fun fabsf(x: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmFabsf(x)

public actual fun fabsf128(x: F128): F128 =
    F128()

public actual fun fabsf16(x: F16): F16 =
    F16()

public actual fun fdim(a: Double, b: Double): Double =
    rustlangCoreNumImpLibmFdim(a, b)

public actual fun fdimf(a: kotlin.Float, b: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmFdimf(a, b)

public actual fun fdimf128(x: F128, y: F128): F128 =
    F128()

public actual fun fdimf16(x: F16, y: F16): F16 =
    F16()

public actual fun floor(x: Double): Double =
    rustlangCoreNumImpLibmFloor(x)

public actual fun floorf(x: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmFloorf(x)

public actual fun floorf128(x: F128): F128 =
    F128()

public actual fun floorf16(x: F16): F16 =
    F16()

public actual fun fma(x: Double, y: Double, z: Double): Double =
    rustlangCoreNumImpLibmFma(x, y, z)

public actual fun fmaf(x: kotlin.Float, y: kotlin.Float, z: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmFmaf(x, y, z)

public actual fun fmaf128(x: F128, y: F128, z: F128): F128 =
    F128()

public actual fun fmaf16(x: F16, y: F16, z: F16): F16 =
    F16()

public actual fun fmax(x: Double, y: Double): Double =
    rustlangCoreNumImpLibmFmax(x, y)

public actual fun fmaxf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmFmaxf(x, y)

public actual fun fmaxf128(x: F128, y: F128): F128 =
    F128()

public actual fun fmaxf16(x: F16, y: F16): F16 =
    F16()

public actual fun fmaximum(x: Double, y: Double): Double =
    rustlangCoreNumImpLibmFmaximum(x, y)

public actual fun fmaximumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmFmaximumf(x, y)

public actual fun fmaximumf128(x: F128, y: F128): F128 =
    F128()

public actual fun fmaximumf16(x: F16, y: F16): F16 =
    F16()

public actual fun fmin(x: Double, y: Double): Double =
    rustlangCoreNumImpLibmFmin(x, y)

public actual fun fminf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmFminf(x, y)

public actual fun fminf128(x: F128, y: F128): F128 =
    F128()

public actual fun fminf16(x: F16, y: F16): F16 =
    F16()

public actual fun fminimum(x: Double, y: Double): Double =
    rustlangCoreNumImpLibmFminimum(x, y)

public actual fun fminimumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmFminimumf(x, y)

public actual fun fminimumf128(x: F128, y: F128): F128 =
    F128()

public actual fun fminimumf16(x: F16, y: F16): F16 =
    F16()

public actual fun fmod(x: Double, y: Double): Double =
    rustlangCoreNumImpLibmFmod(x, y)

public actual fun fmodf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmFmodf(x, y)

public actual fun fmodf128(x: F128, y: F128): F128 =
    F128()

public actual fun fmodf16(x: F16, y: F16): F16 =
    F16()

public actual fun rint(x: Double): Double =
    rustlangCoreNumImpLibmRint(x)

public actual fun rintf(x: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmRintf(x)

public actual fun rintf128(x: F128): F128 =
    F128()

public actual fun rintf16(x: F16): F16 =
    F16()

public actual fun round(x: Double): Double =
    rustlangCoreNumImpLibmRound(x)

public actual fun roundeven(x: Double): Double =
    rustlangCoreNumImpLibmRoundeven(x)

public actual fun roundevenf(x: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmRoundevenf(x)

public actual fun roundevenf128(x: F128): F128 =
    F128()

public actual fun roundevenf16(x: F16): F16 =
    F16()

public actual fun roundf(x: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmRoundf(x)

public actual fun roundf128(x: F128): F128 =
    F128()

public actual fun roundf16(x: F16): F16 =
    F16()

public actual fun sqrt(x: Double): Double =
    rustlangCoreNumImpLibmSqrt(x)

public actual fun sqrtf(x: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmSqrtf(x)

public actual fun sqrtf128(x: F128): F128 =
    F128()

public actual fun sqrtf16(x: F16): F16 =
    F16()

public actual fun trunc(x: Double): Double =
    rustlangCoreNumImpLibmTrunc(x)

public actual fun truncf(x: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmTruncf(x)

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
    rustlangCoreNumImpLibmAcosf(n)

public actual fun asinf(n: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmAsinf(n)

public actual fun atan2f(a: kotlin.Float, b: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmAtan2f(a, b)

public actual fun atanf(n: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmAtanf(n)

public actual fun cosf(x: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmCosf(x)

public actual fun coshf(n: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmCoshf(n)

public actual fun erfcf(x: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmErfcf(x)

public actual fun erff(x: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmErff(x)

public actual fun exp2f(x: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmExp2f(x)

public actual fun expf(x: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmExpf(x)

public actual fun expm1f(n: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmExpm1f(n)

public actual fun hypotf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmHypotf(x, y)

public actual fun ldexpf(f: kotlin.Float, n: Int): kotlin.Float =
    rustlangCoreNumImpLibmLdexpf(f, n)

public actual fun log10f(x: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmLog10f(x)

public actual fun log1pf(n: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmLog1pf(n)

public actual fun log2f(x: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmLog2f(x)

public actual fun logf(x: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmLogf(x)

public actual fun powf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmPowf(x, y)

public actual fun sinf(x: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmSinf(x)

public actual fun sinhf(n: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmSinhf(n)

public actual fun tanf(n: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmTanf(n)

public actual fun tanhf(n: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmTanhf(n)

public actual fun tgammaf(x: kotlin.Float): kotlin.Float =
    rustlangCoreNumImpLibmTgammaf(x)

public actual fun acos(x: Double): Double =
    rustlangCoreNumImpLibmAcos(x)

public actual fun asin(x: Double): Double =
    rustlangCoreNumImpLibmAsin(x)

public actual fun atan(x: Double): Double =
    rustlangCoreNumImpLibmAtan(x)

public actual fun atan2(x: Double, y: Double): Double =
    rustlangCoreNumImpLibmAtan2(x, y)

public actual fun cos(x: Double): Double =
    rustlangCoreNumImpLibmCos(x)

public actual fun cosh(x: Double): Double =
    rustlangCoreNumImpLibmCosh(x)

public actual fun erf(x: Double): Double =
    rustlangCoreNumImpLibmErf(x)

public actual fun erfc(x: Double): Double =
    rustlangCoreNumImpLibmErfc(x)

public actual fun exp(x: Double): Double =
    rustlangCoreNumImpLibmExp(x)

public actual fun exp2(x: Double): Double =
    rustlangCoreNumImpLibmExp2(x)

public actual fun expm1(x: Double): Double =
    rustlangCoreNumImpLibmExpm1(x)

public actual fun hypot(x: Double, y: Double): Double =
    rustlangCoreNumImpLibmHypot(x, y)

public actual fun ldexp(f: Double, n: Int): Double =
    rustlangCoreNumImpLibmLdexp(f, n)

public actual fun log(x: Double): Double =
    rustlangCoreNumImpLibmLog(x)

public actual fun log10(x: Double): Double =
    rustlangCoreNumImpLibmLog10(x)

public actual fun log1p(x: Double): Double =
    rustlangCoreNumImpLibmLog1p(x)

public actual fun log2(x: Double): Double =
    rustlangCoreNumImpLibmLog2(x)

public actual fun pow(x: Double, y: Double): Double =
    rustlangCoreNumImpLibmPow(x, y)

public actual fun sin(x: Double): Double =
    rustlangCoreNumImpLibmSin(x)

public actual fun sinh(x: Double): Double =
    rustlangCoreNumImpLibmSinh(x)

public actual fun tan(x: Double): Double =
    rustlangCoreNumImpLibmTan(x)

public actual fun tanh(x: Double): Double =
    rustlangCoreNumImpLibmTanh(x)

public actual fun tgamma(x: Double): Double =
    rustlangCoreNumImpLibmTgamma(x)

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
