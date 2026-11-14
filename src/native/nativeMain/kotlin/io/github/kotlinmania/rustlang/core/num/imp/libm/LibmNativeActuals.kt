@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.num.imp.libm

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.num.complex.Complex
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmAcos
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmAsin
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmAtan
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmAtan2
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmCbrt
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmCbrtf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmCeil
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmCeilf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmCopysign
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmCopysignf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmCos
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmCosh
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmErf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmErfc
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmExp
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmExp2
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmExpm1
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmFabs
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmFabsf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmFdim
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmFdimf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmFloor
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmFloorf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmFma
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmFmaf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmFmax
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmFmaxf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmFmaximum
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmFmaximumf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmFmin
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmFminf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmFminimum
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmFminimumf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmFmod
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmFmodf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmHypot
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmLdexp
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmLog
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmLog10
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmLog1p
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmLog2
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmPow
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmPowidf2
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmPowisf2
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmRint
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmRintf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmRound
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmRoundeven
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmRoundevenf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmRoundf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmSin
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmSinh
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmSqrt
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmSqrtf
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmTan
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmTanh
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmTgamma
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmTrunc
import io.github.kotlinmania.rustlang.core.num.imp.libm.cinterop.coreNumImpLibmTruncf

internal actual fun powidf2(a: Double, b: Int): Double =
    coreNumImpLibmPowidf2(a, b)

internal actual fun powisf2(a: kotlin.Float, b: Int): kotlin.Float =
    coreNumImpLibmPowisf2(a, b)

internal actual fun powitf2(a: F128, b: Int): F128 =
    null as F128

internal actual fun cbrt(x: Double): Double =
    coreNumImpLibmCbrt(x)

internal actual fun cbrtf(n: kotlin.Float): kotlin.Float =
    coreNumImpLibmCbrtf(n)

internal actual fun ceil(x: Double): Double =
    coreNumImpLibmCeil(x)

internal actual fun ceilf(x: kotlin.Float): kotlin.Float =
    coreNumImpLibmCeilf(x)

internal actual fun ceilf128(x: F128): F128 =
    null as F128

internal actual fun ceilf16(x: F16): F16 =
    null as F16

internal actual fun copysign(x: Double, y: Double): Double =
    coreNumImpLibmCopysign(x, y)

internal actual fun copysignf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    coreNumImpLibmCopysignf(x, y)

internal actual fun copysignf128(x: F128, y: F128): F128 =
    null as F128

internal actual fun copysignf16(x: F16, y: F16): F16 =
    null as F16

internal actual fun fabs(x: Double): Double =
    coreNumImpLibmFabs(x)

internal actual fun fabsf(x: kotlin.Float): kotlin.Float =
    coreNumImpLibmFabsf(x)

internal actual fun fabsf128(x: F128): F128 =
    null as F128

internal actual fun fabsf16(x: F16): F16 =
    null as F16

internal actual fun fdim(a: Double, b: Double): Double =
    coreNumImpLibmFdim(a, b)

internal actual fun fdimf(a: kotlin.Float, b: kotlin.Float): kotlin.Float =
    coreNumImpLibmFdimf(a, b)

internal actual fun fdimf128(x: F128, y: F128): F128 =
    null as F128

internal actual fun fdimf16(x: F16, y: F16): F16 =
    null as F16

internal actual fun floor(x: Double): Double =
    coreNumImpLibmFloor(x)

internal actual fun floorf(x: kotlin.Float): kotlin.Float =
    coreNumImpLibmFloorf(x)

internal actual fun floorf128(x: F128): F128 =
    null as F128

internal actual fun floorf16(x: F16): F16 =
    null as F16

internal actual fun fma(x: Double, y: Double, z: Double): Double =
    coreNumImpLibmFma(x, y, z)

internal actual fun fmaf(x: kotlin.Float, y: kotlin.Float, z: kotlin.Float): kotlin.Float =
    coreNumImpLibmFmaf(x, y, z)

internal actual fun fmaf128(x: F128, y: F128, z: F128): F128 =
    null as F128

internal actual fun fmaf16(x: F16, y: F16, z: F16): F16 =
    null as F16

internal actual fun fmax(x: Double, y: Double): Double =
    coreNumImpLibmFmax(x, y)

internal actual fun fmaxf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    coreNumImpLibmFmaxf(x, y)

internal actual fun fmaxf128(x: F128, y: F128): F128 =
    null as F128

internal actual fun fmaxf16(x: F16, y: F16): F16 =
    null as F16

internal actual fun fmaximum(x: Double, y: Double): Double =
    coreNumImpLibmFmaximum(x, y)

internal actual fun fmaximumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    coreNumImpLibmFmaximumf(x, y)

internal actual fun fmaximumf128(x: F128, y: F128): F128 =
    null as F128

internal actual fun fmaximumf16(x: F16, y: F16): F16 =
    null as F16

internal actual fun fmin(x: Double, y: Double): Double =
    coreNumImpLibmFmin(x, y)

internal actual fun fminf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    coreNumImpLibmFminf(x, y)

internal actual fun fminf128(x: F128, y: F128): F128 =
    null as F128

internal actual fun fminf16(x: F16, y: F16): F16 =
    null as F16

internal actual fun fminimum(x: Double, y: Double): Double =
    coreNumImpLibmFminimum(x, y)

internal actual fun fminimumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    coreNumImpLibmFminimumf(x, y)

internal actual fun fminimumf128(x: F128, y: F128): F128 =
    null as F128

internal actual fun fminimumf16(x: F16, y: F16): F16 =
    null as F16

internal actual fun fmod(x: Double, y: Double): Double =
    coreNumImpLibmFmod(x, y)

internal actual fun fmodf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    coreNumImpLibmFmodf(x, y)

internal actual fun fmodf128(x: F128, y: F128): F128 =
    null as F128

internal actual fun fmodf16(x: F16, y: F16): F16 =
    null as F16

internal actual fun rint(x: Double): Double =
    coreNumImpLibmRint(x)

internal actual fun rintf(x: kotlin.Float): kotlin.Float =
    coreNumImpLibmRintf(x)

internal actual fun rintf128(x: F128): F128 =
    null as F128

internal actual fun rintf16(x: F16): F16 =
    null as F16

internal actual fun round(x: Double): Double =
    coreNumImpLibmRound(x)

internal actual fun roundeven(x: Double): Double =
    coreNumImpLibmRoundeven(x)

internal actual fun roundevenf(x: kotlin.Float): kotlin.Float =
    coreNumImpLibmRoundevenf(x)

internal actual fun roundevenf128(x: F128): F128 =
    null as F128

internal actual fun roundevenf16(x: F16): F16 =
    null as F16

internal actual fun roundf(x: kotlin.Float): kotlin.Float =
    coreNumImpLibmRoundf(x)

internal actual fun roundf128(x: F128): F128 =
    null as F128

internal actual fun roundf16(x: F16): F16 =
    null as F16

internal actual fun sqrt(x: Double): Double =
    coreNumImpLibmSqrt(x)

internal actual fun sqrtf(x: kotlin.Float): kotlin.Float =
    coreNumImpLibmSqrtf(x)

internal actual fun sqrtf128(x: F128): F128 =
    null as F128

internal actual fun sqrtf16(x: F16): F16 =
    null as F16

internal actual fun trunc(x: Double): Double =
    coreNumImpLibmTrunc(x)

internal actual fun truncf(x: kotlin.Float): kotlin.Float =
    coreNumImpLibmTruncf(x)

internal actual fun truncf128(x: F128): F128 =
    null as F128

internal actual fun truncf16(x: F16): F16 =
    null as F16

internal actual fun mulsc3(a: kotlin.Float, b: kotlin.Float, c: kotlin.Float, d: kotlin.Float): Complex<kotlin.Float> =
    null as Complex<kotlin.Float>

internal actual fun muldc3(a: Double, b: Double, c: Double, d: Double): Complex<Double> =
    null as Complex<Double>

internal actual fun divsc3(a: kotlin.Float, b: kotlin.Float, c: kotlin.Float, d: kotlin.Float): Complex<kotlin.Float> =
    null as Complex<kotlin.Float>

internal actual fun divdc3(a: Double, b: Double, c: Double, d: Double): Complex<Double> =
    null as Complex<Double>

internal actual fun rustMulhc3(a: F16, b: F16, c: F16, d: F16): Complex<F16> =
    null as Complex<F16>

internal actual fun rustMultc3(a: F128, b: F128, c: F128, d: F128): Complex<F128> =
    null as Complex<F128>

internal actual fun rustDivhc3(a: F16, b: F16, c: F16, d: F16): Complex<F16> =
    null as Complex<F16>

internal actual fun rustDivtc3(a: F128, b: F128, c: F128, d: F128): Complex<F128> =
    null as Complex<F128>

internal actual fun acos(x: Double): Double =
    coreNumImpLibmAcos(x)

internal actual fun asin(x: Double): Double =
    coreNumImpLibmAsin(x)

internal actual fun atan(x: Double): Double =
    coreNumImpLibmAtan(x)

internal actual fun atan2(x: Double, y: Double): Double =
    coreNumImpLibmAtan2(x, y)

internal actual fun cos(x: Double): Double =
    coreNumImpLibmCos(x)

internal actual fun cosh(x: Double): Double =
    coreNumImpLibmCosh(x)

internal actual fun erf(x: Double): Double =
    coreNumImpLibmErf(x)

internal actual fun erfc(x: Double): Double =
    coreNumImpLibmErfc(x)

internal actual fun exp(x: Double): Double =
    coreNumImpLibmExp(x)

internal actual fun exp2(x: Double): Double =
    coreNumImpLibmExp2(x)

internal actual fun expm1(x: Double): Double =
    coreNumImpLibmExpm1(x)

internal actual fun hypot(x: Double, y: Double): Double =
    coreNumImpLibmHypot(x, y)

internal actual fun ldexp(f: Double, n: Int): Double =
    coreNumImpLibmLdexp(f, n)

internal actual fun log(x: Double): Double =
    coreNumImpLibmLog(x)

internal actual fun log10(x: Double): Double =
    coreNumImpLibmLog10(x)

internal actual fun log1p(x: Double): Double =
    coreNumImpLibmLog1p(x)

internal actual fun log2(x: Double): Double =
    coreNumImpLibmLog2(x)

internal actual fun pow(x: Double, y: Double): Double =
    coreNumImpLibmPow(x, y)

internal actual fun sin(x: Double): Double =
    coreNumImpLibmSin(x)

internal actual fun sinh(x: Double): Double =
    coreNumImpLibmSinh(x)

internal actual fun tan(x: Double): Double =
    coreNumImpLibmTan(x)

internal actual fun tanh(x: Double): Double =
    coreNumImpLibmTanh(x)

internal actual fun tgamma(x: Double): Double =
    coreNumImpLibmTgamma(x)

internal actual fun acosf128(x: F128): F128 =
    null as F128

internal actual fun asinf128(x: F128): F128 =
    null as F128

internal actual fun atanf128(x: F128): F128 =
    null as F128

internal actual fun cbrtf128(x: F128): F128 =
    null as F128

internal actual fun cosf128(x: F128): F128 =
    null as F128

internal actual fun erff128(x: F128): F128 =
    null as F128

internal actual fun expf128(x: F128): F128 =
    null as F128

internal actual fun exp2f128(x: F128): F128 =
    null as F128

internal actual fun expm1f128(x: F128): F128 =
    null as F128

internal actual fun hypotf128(x: F128, y: F128): F128 =
    null as F128

internal actual fun ldexpf128(f: F128, n: Int): F128 =
    null as F128

internal actual fun log10f128(x: F128): F128 =
    null as F128

internal actual fun log1pf128(x: F128): F128 =
    null as F128

internal actual fun log2f128(x: F128): F128 =
    null as F128

internal actual fun logf128(x: F128): F128 =
    null as F128

internal actual fun powf128(x: F128, y: F128): F128 =
    null as F128

internal actual fun sinf128(x: F128): F128 =
    null as F128

internal actual fun tanf128(x: F128): F128 =
    null as F128

internal actual fun tanhf128(x: F128): F128 =
    null as F128

internal actual fun tgammaf128(x: F128): F128 =
    null as F128
