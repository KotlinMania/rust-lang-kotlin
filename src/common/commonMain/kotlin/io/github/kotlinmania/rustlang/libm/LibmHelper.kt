// port-lint: source libm_helper.rs
package io.github.kotlinmania.rustlang.libm

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.libm.math.acos.acos
import io.github.kotlinmania.rustlang.libm.math.acosf.acosf
import io.github.kotlinmania.rustlang.libm.math.acosh.acosh
import io.github.kotlinmania.rustlang.libm.math.acoshf.acoshf
import io.github.kotlinmania.rustlang.libm.math.arch.aarch64.fma.fma
import io.github.kotlinmania.rustlang.libm.math.arch.aarch64.fma.fmaf
import io.github.kotlinmania.rustlang.libm.math.arch.aarch64.rounding.rint
import io.github.kotlinmania.rustlang.libm.math.arch.aarch64.rounding.rintf
import io.github.kotlinmania.rustlang.libm.math.arch.aarch64.rounding.rintf16
import io.github.kotlinmania.rustlang.libm.math.arch.aarch64.sqrt.sqrt
import io.github.kotlinmania.rustlang.libm.math.arch.aarch64.sqrt.sqrtf
import io.github.kotlinmania.rustlang.libm.math.arch.aarch64.sqrt.sqrtf16
import io.github.kotlinmania.rustlang.libm.math.asin.asin
import io.github.kotlinmania.rustlang.libm.math.asinf.asinf
import io.github.kotlinmania.rustlang.libm.math.asinh.asinh
import io.github.kotlinmania.rustlang.libm.math.asinhf.asinhf
import io.github.kotlinmania.rustlang.libm.math.atan.atan
import io.github.kotlinmania.rustlang.libm.math.atan2.atan2
import io.github.kotlinmania.rustlang.libm.math.atan2f.atan2f
import io.github.kotlinmania.rustlang.libm.math.atanf.atanf
import io.github.kotlinmania.rustlang.libm.math.atanh.atanh
import io.github.kotlinmania.rustlang.libm.math.atanhf.atanhf
import io.github.kotlinmania.rustlang.libm.math.cbrt.cbrt
import io.github.kotlinmania.rustlang.libm.math.cbrtf.cbrtf
import io.github.kotlinmania.rustlang.libm.math.ceil.ceil
import io.github.kotlinmania.rustlang.libm.math.ceil.ceilf
import io.github.kotlinmania.rustlang.libm.math.ceil.ceilf128
import io.github.kotlinmania.rustlang.libm.math.ceil.ceilf16
import io.github.kotlinmania.rustlang.libm.math.copysign.copysign
import io.github.kotlinmania.rustlang.libm.math.copysign.copysignf
import io.github.kotlinmania.rustlang.libm.math.copysign.copysignf128
import io.github.kotlinmania.rustlang.libm.math.copysign.copysignf16
import io.github.kotlinmania.rustlang.libm.math.cos.cos
import io.github.kotlinmania.rustlang.libm.math.cosf.cosf
import io.github.kotlinmania.rustlang.libm.math.cosh.cosh
import io.github.kotlinmania.rustlang.libm.math.coshf.coshf
import io.github.kotlinmania.rustlang.libm.math.erf.erf
import io.github.kotlinmania.rustlang.libm.math.erf.erfc
import io.github.kotlinmania.rustlang.libm.math.erff.erfcf
import io.github.kotlinmania.rustlang.libm.math.erff.erff
import io.github.kotlinmania.rustlang.libm.math.exp10.exp10
import io.github.kotlinmania.rustlang.libm.math.exp10f.exp10f
import io.github.kotlinmania.rustlang.libm.math.exp2.exp2
import io.github.kotlinmania.rustlang.libm.math.exp2f.exp2f
import io.github.kotlinmania.rustlang.libm.math.exp2m1.exp2m1
import io.github.kotlinmania.rustlang.libm.math.exp2m1f.exp2m1f
import io.github.kotlinmania.rustlang.libm.math.exp2m1f16.exp2m1f16
import io.github.kotlinmania.rustlang.libm.math.expf.expf
import io.github.kotlinmania.rustlang.libm.math.expm1.expm1
import io.github.kotlinmania.rustlang.libm.math.expm1f.expm1f
import io.github.kotlinmania.rustlang.libm.math.fabs.fabs
import io.github.kotlinmania.rustlang.libm.math.fabs.fabsf
import io.github.kotlinmania.rustlang.libm.math.fabs.fabsf128
import io.github.kotlinmania.rustlang.libm.math.fabs.fabsf16
import io.github.kotlinmania.rustlang.libm.math.fdim.fdim
import io.github.kotlinmania.rustlang.libm.math.fdim.fdimf
import io.github.kotlinmania.rustlang.libm.math.fdim.fdimf128
import io.github.kotlinmania.rustlang.libm.math.fdim.fdimf16
import io.github.kotlinmania.rustlang.libm.math.floor.floor
import io.github.kotlinmania.rustlang.libm.math.floor.floorf
import io.github.kotlinmania.rustlang.libm.math.floor.floorf128
import io.github.kotlinmania.rustlang.libm.math.floor.floorf16
import io.github.kotlinmania.rustlang.libm.math.fma.fmaf128
import io.github.kotlinmania.rustlang.libm.math.fmaf16.fmaf16
import io.github.kotlinmania.rustlang.libm.math.fminfmax.fmax
import io.github.kotlinmania.rustlang.libm.math.fminfmax.fmaxf
import io.github.kotlinmania.rustlang.libm.math.fminfmax.fmaxf128
import io.github.kotlinmania.rustlang.libm.math.fminfmax.fmaxf16
import io.github.kotlinmania.rustlang.libm.math.fminfmax.fmin
import io.github.kotlinmania.rustlang.libm.math.fminfmax.fminf
import io.github.kotlinmania.rustlang.libm.math.fminfmax.fminf128
import io.github.kotlinmania.rustlang.libm.math.fminfmax.fminf16
import io.github.kotlinmania.rustlang.libm.math.fminimumfmaximum.fmaximum
import io.github.kotlinmania.rustlang.libm.math.fminimumfmaximum.fmaximumf
import io.github.kotlinmania.rustlang.libm.math.fminimumfmaximum.fmaximumf128
import io.github.kotlinmania.rustlang.libm.math.fminimumfmaximum.fmaximumf16
import io.github.kotlinmania.rustlang.libm.math.fminimumfmaximum.fminimum
import io.github.kotlinmania.rustlang.libm.math.fminimumfmaximum.fminimumf
import io.github.kotlinmania.rustlang.libm.math.fminimumfmaximum.fminimumf128
import io.github.kotlinmania.rustlang.libm.math.fminimumfmaximum.fminimumf16
import io.github.kotlinmania.rustlang.libm.math.fminimumfmaximumnum.fmaximumNum
import io.github.kotlinmania.rustlang.libm.math.fminimumfmaximumnum.fmaximumNumf
import io.github.kotlinmania.rustlang.libm.math.fminimumfmaximumnum.fmaximumNumf128
import io.github.kotlinmania.rustlang.libm.math.fminimumfmaximumnum.fmaximumNumf16
import io.github.kotlinmania.rustlang.libm.math.fminimumfmaximumnum.fminimumNum
import io.github.kotlinmania.rustlang.libm.math.fminimumfmaximumnum.fminimumNumf
import io.github.kotlinmania.rustlang.libm.math.fminimumfmaximumnum.fminimumNumf128
import io.github.kotlinmania.rustlang.libm.math.fminimumfmaximumnum.fminimumNumf16
import io.github.kotlinmania.rustlang.libm.math.fmod.fmod
import io.github.kotlinmania.rustlang.libm.math.fmod.fmodf
import io.github.kotlinmania.rustlang.libm.math.fmod.fmodf128
import io.github.kotlinmania.rustlang.libm.math.fmod.fmodf16
import io.github.kotlinmania.rustlang.libm.math.frexp.frexp
import io.github.kotlinmania.rustlang.libm.math.frexp.frexpf
import io.github.kotlinmania.rustlang.libm.math.frexp.frexpf128
import io.github.kotlinmania.rustlang.libm.math.frexp.frexpf16
import io.github.kotlinmania.rustlang.libm.math.generic.sqrt.Exp
import io.github.kotlinmania.rustlang.libm.math.hypot.hypot
import io.github.kotlinmania.rustlang.libm.math.hypotf.hypotf
import io.github.kotlinmania.rustlang.libm.math.ilogb.ilogb
import io.github.kotlinmania.rustlang.libm.math.ilogb.ilogbf
import io.github.kotlinmania.rustlang.libm.math.ilogb.ilogbf128
import io.github.kotlinmania.rustlang.libm.math.ilogb.ilogbf16
import io.github.kotlinmania.rustlang.libm.math.j0.j0
import io.github.kotlinmania.rustlang.libm.math.j0.y0
import io.github.kotlinmania.rustlang.libm.math.j0f.j0f
import io.github.kotlinmania.rustlang.libm.math.j0f.y0f
import io.github.kotlinmania.rustlang.libm.math.j1.j1
import io.github.kotlinmania.rustlang.libm.math.j1.y1
import io.github.kotlinmania.rustlang.libm.math.j1f.j1f
import io.github.kotlinmania.rustlang.libm.math.j1f.y1f
import io.github.kotlinmania.rustlang.libm.math.jn.jn
import io.github.kotlinmania.rustlang.libm.math.jn.yn
import io.github.kotlinmania.rustlang.libm.math.jnf.jnf
import io.github.kotlinmania.rustlang.libm.math.jnf.ynf
import io.github.kotlinmania.rustlang.libm.math.ldexp.ldexp
import io.github.kotlinmania.rustlang.libm.math.ldexp.ldexpf
import io.github.kotlinmania.rustlang.libm.math.ldexp.ldexpf128
import io.github.kotlinmania.rustlang.libm.math.ldexp.ldexpf16
import io.github.kotlinmania.rustlang.libm.math.lgamma.lgamma
import io.github.kotlinmania.rustlang.libm.math.lgammaf.lgammaf
import io.github.kotlinmania.rustlang.libm.math.lgammafr.lgammafR
import io.github.kotlinmania.rustlang.libm.math.lgammar.lgammaR
import io.github.kotlinmania.rustlang.libm.math.log.log
import io.github.kotlinmania.rustlang.libm.math.log10.log10
import io.github.kotlinmania.rustlang.libm.math.log10f.log10f
import io.github.kotlinmania.rustlang.libm.math.log1p.log1p
import io.github.kotlinmania.rustlang.libm.math.log1pf.log1pf
import io.github.kotlinmania.rustlang.libm.math.log2.log2
import io.github.kotlinmania.rustlang.libm.math.log2f.log2f
import io.github.kotlinmania.rustlang.libm.math.logf.logf
import io.github.kotlinmania.rustlang.libm.math.modf.modf
import io.github.kotlinmania.rustlang.libm.math.modff.modff
import io.github.kotlinmania.rustlang.libm.math.nextafter.nextafter
import io.github.kotlinmania.rustlang.libm.math.nextafterf.nextafterf
import io.github.kotlinmania.rustlang.libm.math.pow.pow
import io.github.kotlinmania.rustlang.libm.math.powf.powf
import io.github.kotlinmania.rustlang.libm.math.remainder.remainder
import io.github.kotlinmania.rustlang.libm.math.remainderf.remainderf
import io.github.kotlinmania.rustlang.libm.math.remquo.remquo
import io.github.kotlinmania.rustlang.libm.math.remquof.remquof
import io.github.kotlinmania.rustlang.libm.math.rint.rintf128
import io.github.kotlinmania.rustlang.libm.math.round.roundf
import io.github.kotlinmania.rustlang.libm.math.round.roundf128
import io.github.kotlinmania.rustlang.libm.math.round.roundf16
import io.github.kotlinmania.rustlang.libm.math.roundeven.roundeven
import io.github.kotlinmania.rustlang.libm.math.roundeven.roundevenf
import io.github.kotlinmania.rustlang.libm.math.roundeven.roundevenf128
import io.github.kotlinmania.rustlang.libm.math.roundeven.roundevenf16
import io.github.kotlinmania.rustlang.libm.math.scalbn.scalbn
import io.github.kotlinmania.rustlang.libm.math.scalbn.scalbnf
import io.github.kotlinmania.rustlang.libm.math.scalbn.scalbnf128
import io.github.kotlinmania.rustlang.libm.math.scalbn.scalbnf16
import io.github.kotlinmania.rustlang.libm.math.sin.sin
import io.github.kotlinmania.rustlang.libm.math.sincos.sincos
import io.github.kotlinmania.rustlang.libm.math.sincosf.sincosf
import io.github.kotlinmania.rustlang.libm.math.sinf.sinf
import io.github.kotlinmania.rustlang.libm.math.sinh.sinh
import io.github.kotlinmania.rustlang.libm.math.sinhf.sinhf
import io.github.kotlinmania.rustlang.libm.math.sqrt.sqrtf128
import io.github.kotlinmania.rustlang.libm.math.support.env.Round
import io.github.kotlinmania.rustlang.libm.math.tan.tan
import io.github.kotlinmania.rustlang.libm.math.tanf.tanf
import io.github.kotlinmania.rustlang.libm.math.tanh.tanh
import io.github.kotlinmania.rustlang.libm.math.tanhf.tanhf
import io.github.kotlinmania.rustlang.libm.math.tgamma.tgamma
import io.github.kotlinmania.rustlang.libm.math.tgammaf.tgammaf
import io.github.kotlinmania.rustlang.libm.math.trunc.trunc
import io.github.kotlinmania.rustlang.libm.math.trunc.truncf
import io.github.kotlinmania.rustlang.libm.math.trunc.truncf128
import io.github.kotlinmania.rustlang.libm.math.trunc.truncf16

/**
 * Generic helper for libm functions, abstracting over f32 and f64. <br/>
 * # Type Parameter:
 * - `T`: Either `Float` or `Double`
 *
 * # Examples
 * ```kotlin
 * import libm.Libm
 *
 * val PI_F32: Float = 3.1415927410e00f
 * val PI_F64: Double = 3.1415926535897931160e00
 * check(Libm.cos(0.0f) == libm.cosf(0.0))
 * check(Libm.sin(PI_F32) == libm.sinf(PI_F32))
 * check(Libm.cos(0.0) == libm.cos(0.0))
 * check(Libm.sin(PI_F64) == libm.sin(PI_F64))
 * ```
 */
public open class Libm<T>(
    public var value: Unit,
) {
    public var field0: Unit
        get() = value
        set(v) {
            value = v
        }

    public companion object
}

// verify-apilist-start
public fun Libm.Companion.acos(x: kotlin.Float): kotlin.Float = acosf(x)

public fun Libm.Companion.acosh(x: kotlin.Float): kotlin.Float = acoshf(x)

public fun Libm.Companion.asin(x: kotlin.Float): kotlin.Float = asinf(x)

public fun Libm.Companion.asinh(x: kotlin.Float): kotlin.Float = asinhf(x)

public fun Libm.Companion.atan(x: kotlin.Float): kotlin.Float = atanf(x)

public fun Libm.Companion.atan2(y: kotlin.Float, x: kotlin.Float): kotlin.Float = atan2f(y, x)

public fun Libm.Companion.atanh(x: kotlin.Float): kotlin.Float = atanhf(x)

public fun Libm.Companion.cbrt(x: kotlin.Float): kotlin.Float = cbrtf(x)

public fun Libm.Companion.ceil(x: kotlin.Float): kotlin.Float = ceilf(x)

public fun Libm.Companion.copysign(x: kotlin.Float, y: kotlin.Float): kotlin.Float = copysignf(x, y)

public fun Libm.Companion.cos(x: kotlin.Float): kotlin.Float = cosf(x)

public fun Libm.Companion.cosh(x: kotlin.Float): kotlin.Float = coshf(x)

public fun Libm.Companion.erf(x: kotlin.Float): kotlin.Float = erff(x)

public fun Libm.Companion.erfc(x: kotlin.Float): kotlin.Float = erfcf(x)

public fun Libm.Companion.exp(x: kotlin.Float): kotlin.Float = expf(x)

public fun Libm.Companion.exp10(x: kotlin.Float): kotlin.Float = exp10f(x)

public fun Libm.Companion.exp2(x: kotlin.Float): kotlin.Float = exp2f(x)

public fun Libm.Companion.exp2m1(x: kotlin.Float): kotlin.Float = exp2m1f(x)

public fun Libm.Companion.expm1(x: kotlin.Float): kotlin.Float = expm1f(x)

public fun Libm.Companion.fabs(x: kotlin.Float): kotlin.Float = fabsf(x)

public fun Libm.Companion.fdim(x: kotlin.Float, y: kotlin.Float): kotlin.Float = fdimf(x, y)

public fun Libm.Companion.floor(x: kotlin.Float): kotlin.Float = floorf(x)

public fun Libm.Companion.fma(x: kotlin.Float, y: kotlin.Float, z: kotlin.Float): kotlin.Float = fmaf(x, y, z)

public fun Libm.Companion.fmax(x: kotlin.Float, y: kotlin.Float): kotlin.Float = fmaxf(x, y)

public fun Libm.Companion.fmin(x: kotlin.Float, y: kotlin.Float): kotlin.Float = fminf(x, y)

public fun Libm.Companion.fmod(x: kotlin.Float, y: kotlin.Float): kotlin.Float = fmodf(x, y)

public fun Libm.Companion.frexp(x: kotlin.Float): Pair<kotlin.Float, Int> = frexpf(x)

public fun Libm.Companion.hypot(x: kotlin.Float, y: kotlin.Float): kotlin.Float = hypotf(x, y)

public fun Libm.Companion.ilogb(x: kotlin.Float): Int = ilogbf(x)

public fun Libm.Companion.j0(x: kotlin.Float): kotlin.Float = j0f(x)

public fun Libm.Companion.j1(x: kotlin.Float): kotlin.Float = j1f(x)

public fun Libm.Companion.jn(n: Int, x: kotlin.Float): kotlin.Float = jnf(n, x)

public fun Libm.Companion.ldexp(x: kotlin.Float, n: Int): kotlin.Float = ldexpf(x, n)

public fun Libm.Companion.lgamma(x: kotlin.Float): kotlin.Float = lgammaf(x)

public fun Libm.Companion.lgammaR(x: kotlin.Float): Pair<kotlin.Float, Int> = lgammafR(x)

public fun Libm.Companion.log(x: kotlin.Float): kotlin.Float = logf(x)

public fun Libm.Companion.log10(x: kotlin.Float): kotlin.Float = log10f(x)

public fun Libm.Companion.log1p(x: kotlin.Float): kotlin.Float = log1pf(x)

public fun Libm.Companion.log2(x: kotlin.Float): kotlin.Float = log2f(x)

public fun Libm.Companion.modf(x: kotlin.Float): Pair<kotlin.Float, kotlin.Float> = modff(x)

public fun Libm.Companion.nextafter(x: kotlin.Float, y: kotlin.Float): kotlin.Float = nextafterf(x, y)

public fun Libm.Companion.pow(x: kotlin.Float, y: kotlin.Float): kotlin.Float = powf(x, y)

public fun Libm.Companion.remainder(x: kotlin.Float, y: kotlin.Float): kotlin.Float = remainderf(x, y)

public fun Libm.Companion.remquo(x: kotlin.Float, y: kotlin.Float): Pair<kotlin.Float, Int> = remquof(x, y)

public fun Libm.Companion.rint(x: kotlin.Float): kotlin.Float = rintf(x)

public fun Libm.Companion.round(x: kotlin.Float): kotlin.Float = roundf(x)

public fun Libm.Companion.roundeven(x: kotlin.Float): kotlin.Float = roundevenf(x)

public fun Libm.Companion.scalbn(x: kotlin.Float, n: Int): kotlin.Float = scalbnf(x, n)

public fun Libm.Companion.sin(x: kotlin.Float): kotlin.Float = sinf(x)

public fun Libm.Companion.sincos(x: kotlin.Float): Pair<kotlin.Float, kotlin.Float> = sincosf(x)

public fun Libm.Companion.sinh(x: kotlin.Float): kotlin.Float = sinhf(x)

public fun Libm.Companion.sqrt(x: kotlin.Float): kotlin.Float = sqrtf(x)

public fun Libm.Companion.tan(x: kotlin.Float): kotlin.Float = tanf(x)

public fun Libm.Companion.tanh(x: kotlin.Float): kotlin.Float = tanhf(x)

public fun Libm.Companion.tgamma(x: kotlin.Float): kotlin.Float = tgammaf(x)

public fun Libm.Companion.trunc(x: kotlin.Float): kotlin.Float = truncf(x)

public fun Libm.Companion.y0(x: kotlin.Float): kotlin.Float = y0f(x)

public fun Libm.Companion.y1(x: kotlin.Float): kotlin.Float = y1f(x)

public fun Libm.Companion.yn(n: Int, x: kotlin.Float): kotlin.Float = ynf(n, x)

public fun Libm.Companion.acos(x: Double): Double = acos(x)

public fun Libm.Companion.acosh(x: Double): Double = acosh(x)

public fun Libm.Companion.asin(x: Double): Double = asin(x)

public fun Libm.Companion.asinh(x: Double): Double = asinh(x)

public fun Libm.Companion.atan(x: Double): Double = atan(x)

public fun Libm.Companion.atan2(y: Double, x: Double): Double = atan2(y, x)

public fun Libm.Companion.atanh(x: Double): Double = atanh(x)

public fun Libm.Companion.cbrt(x: Double): Double = cbrt(x)

public fun Libm.Companion.ceil(x: Double): Double = ceil(x)

public fun Libm.Companion.copysign(x: Double, y: Double): Double = copysign(x, y)

public fun Libm.Companion.cos(x: Double): Double = cos(x)

public fun Libm.Companion.cosh(x: Double): Double = cosh(x)

public fun Libm.Companion.erf(x: Double): Double = erf(x)

public fun Libm.Companion.erfc(x: Double): Double = erfc(x)

public fun Libm.Companion.exp(x: Double): Double = Exp(x)

public fun Libm.Companion.exp10(x: Double): Double = exp10(x)

public fun Libm.Companion.exp2(x: Double): Double = exp2(x)

public fun Libm.Companion.exp2m1(x: Double): Double = exp2m1(x)

public fun Libm.Companion.expm1(x: Double): Double = expm1(x)

public fun Libm.Companion.fabs(x: Double): Double = fabs(x)

public fun Libm.Companion.fdim(x: Double, y: Double): Double = fdim(x, y)

public fun Libm.Companion.floor(x: Double): Double = floor(x)

public fun Libm.Companion.fma(x: Double, y: Double, z: Double): Double = fma(x, y, z)

public fun Libm.Companion.fmax(x: Double, y: Double): Double = fmax(x, y)

public fun Libm.Companion.fmaximum(x: Double, y: Double): Double = fmaximum(x, y)

public fun Libm.Companion.fmaximumNum(x: Double, y: Double): Double = fmaximumNum(x, y)

public fun Libm.Companion.fmaximumNumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float = fmaximumNumf(x, y)

public fun Libm.Companion.fmaximumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float = fmaximumf(x, y)

public fun Libm.Companion.fmin(x: Double, y: Double): Double = fmin(x, y)

public fun Libm.Companion.fminimum(x: Double, y: Double): Double = fminimum(x, y)

public fun Libm.Companion.fminimumNum(x: Double, y: Double): Double = fminimumNum(x, y)

public fun Libm.Companion.fminimumNumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float = fminimumNumf(x, y)

public fun Libm.Companion.fminimumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float = fminimumf(x, y)

public fun Libm.Companion.fmod(x: Double, y: Double): Double = fmod(x, y)

public fun Libm.Companion.frexp(x: Double): Pair<Double, Int> = frexp(x)

public fun Libm.Companion.hypot(x: Double, y: Double): Double = hypot(x, y)

public fun Libm.Companion.ilogb(x: Double): Int = ilogb(x)

public fun Libm.Companion.j0(x: Double): Double = j0(x)

public fun Libm.Companion.j1(x: Double): Double = j1(x)

public fun Libm.Companion.jn(n: Int, x: Double): Double = jn(n, x)

public fun Libm.Companion.ldexp(x: Double, n: Int): Double = ldexp(x, n)

public fun Libm.Companion.lgamma(x: Double): Double = lgamma(x)

public fun Libm.Companion.lgammaR(x: Double): Pair<Double, Int> = lgammaR(x)

public fun Libm.Companion.log(x: Double): Double = log(x)

public fun Libm.Companion.log10(x: Double): Double = log10(x)

public fun Libm.Companion.log1p(x: Double): Double = log1p(x)

public fun Libm.Companion.log2(x: Double): Double = log2(x)

public fun Libm.Companion.modf(x: Double): Pair<Double, Double> = modf(x)

public fun Libm.Companion.nextafter(x: Double, y: Double): Double = nextafter(x, y)

public fun Libm.Companion.pow(x: Double, y: Double): Double = pow(x, y)

public fun Libm.Companion.remainder(x: Double, y: Double): Double = remainder(x, y)

public fun Libm.Companion.remquo(x: Double, y: Double): Pair<Double, Int> = remquo(x, y)

public fun Libm.Companion.rint(x: Double): Double = rint(x)

public fun Libm.Companion.round(x: Double): Double = Round(x)

public fun Libm.Companion.roundeven(x: Double): Double = roundeven(x)

public fun Libm.Companion.scalbn(x: Double, n: Int): Double = scalbn(x, n)

public fun Libm.Companion.sin(x: Double): Double = sin(x)

public fun Libm.Companion.sincos(x: Double): Pair<Double, Double> = sincos(x)

public fun Libm.Companion.sinh(x: Double): Double = sinh(x)

public fun Libm.Companion.sqrt(x: Double): Double = sqrt(x)

public fun Libm.Companion.tan(x: Double): Double = tan(x)

public fun Libm.Companion.tanh(x: Double): Double = tanh(x)

public fun Libm.Companion.tgamma(x: Double): Double = tgamma(x)

public fun Libm.Companion.trunc(x: Double): Double = trunc(x)

public fun Libm.Companion.y0(x: Double): Double = y0(x)

public fun Libm.Companion.y1(x: Double): Double = y1(x)

public fun Libm.Companion.yn(n: Int, x: Double): Double = yn(n, x)

public fun Libm.Companion.ceil(x: F16): F16 = ceilf16(x)

public fun Libm.Companion.copysign(x: F16, y: F16): F16 = copysignf16(x, y)

public fun Libm.Companion.exp2m1(x: F16): F16 = exp2m1f16(x)

public fun Libm.Companion.fabs(x: F16): F16 = fabsf16(x)

public fun Libm.Companion.fdim(x: F16, y: F16): F16 = fdimf16(x, y)

public fun Libm.Companion.floor(x: F16): F16 = floorf16(x)

public fun Libm.Companion.fma(x: F16, y: F16, z: F16): F16 = fmaf16(x, y, z)

public fun Libm.Companion.fmax(x: F16, y: F16): F16 = fmaxf16(x, y)

public fun Libm.Companion.fmaximumNum(x: F16, y: F16): F16 = fmaximumNumf16(x, y)

public fun Libm.Companion.fmaximumf16(x: F16, y: F16): F16 = fmaximumf16(x, y)

public fun Libm.Companion.fmin(x: F16, y: F16): F16 = fminf16(x, y)

public fun Libm.Companion.fminimum(x: F16, y: F16): F16 = fminimumf16(x, y)

public fun Libm.Companion.fminimumNum(x: F16, y: F16): F16 = fminimumNumf16(x, y)

public fun Libm.Companion.fmod(x: F16, y: F16): F16 = fmodf16(x, y)

public fun Libm.Companion.frexp(x: F16): Pair<F16, Int> = frexpf16(x)

public fun Libm.Companion.ilogb(x: F16): Int = ilogbf16(x)

public fun Libm.Companion.ldexp(x: F16, n: Int): F16 = ldexpf16(x, n)

public fun Libm.Companion.rint(x: F16): F16 = rintf16(x)

public fun Libm.Companion.round(x: F16): F16 = roundf16(x)

public fun Libm.Companion.roundeven(x: F16): F16 = roundevenf16(x)

public fun Libm.Companion.scalbn(x: F16, n: Int): F16 = scalbnf16(x, n)

public fun Libm.Companion.sqrtf(x: F16): F16 = sqrtf16(x)

public fun Libm.Companion.truncf(x: F16): F16 = truncf16(x)

public fun Libm.Companion.ceil(x: F128): F128 = ceilf128(x)

public fun Libm.Companion.copysign(x: F128, y: F128): F128 = copysignf128(x, y)

public fun Libm.Companion.fabs(x: F128): F128 = fabsf128(x)

public fun Libm.Companion.fdim(x: F128, y: F128): F128 = fdimf128(x, y)

public fun Libm.Companion.floor(x: F128): F128 = floorf128(x)

public fun Libm.Companion.fma(x: F128, y: F128, z: F128): F128 = fmaf128(x, y, z)

public fun Libm.Companion.fmax(x: F128, y: F128): F128 = fmaxf128(x, y)

public fun Libm.Companion.fmaximum(x: F128, y: F128): F128 = fmaximumf128(x, y)

public fun Libm.Companion.fmaximumNum(x: F128, y: F128): F128 = fmaximumNumf128(x, y)

public fun Libm.Companion.fmin(x: F128, y: F128): F128 = fminf128(x, y)

public fun Libm.Companion.fminimum(x: F128, y: F128): F128 = fminimumf128(x, y)

public fun Libm.Companion.fminimumNum(x: F128, y: F128): F128 = fminimumNumf128(x, y)

public fun Libm.Companion.fmod(x: F128, y: F128): F128 = fmodf128(x, y)

public fun Libm.Companion.frexp(x: F128): Pair<F128, Int> = frexpf128(x)

public fun Libm.Companion.ilogb(x: F128): Int = ilogbf128(x)

public fun Libm.Companion.ldexp(x: F128, n: Int): F128 = ldexpf128(x, n)

public fun Libm.Companion.rint(x: F128): F128 = rintf128(x)

public fun Libm.Companion.round(x: F128): F128 = roundf128(x)

public fun Libm.Companion.roundeven(x: F128): F128 = roundevenf128(x)

public fun Libm.Companion.scalbn(x: F128, n: Int): F128 = scalbnf128(x, n)

public fun Libm.Companion.sqrt(x: F128): F128 = sqrtf128(x)

public fun Libm.Companion.trunc(x: F128): F128 = truncf128(x)
