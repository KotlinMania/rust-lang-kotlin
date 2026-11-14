@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.num.imp.libm

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.num.complex.Complex

private object NumImpLibmJniBridge {
    init {
        loadNativeLibrary()
    }

    private fun loadNativeLibrary() {
        val osRaw = System.getProperty("os.name", "").lowercase()
        val archRaw = System.getProperty("os.arch", "").lowercase()
        val hostPlatform =
            when {
                osRaw.contains("win") -> "windows"
                osRaw.contains("mac") || osRaw.contains("darwin") -> "macos"
                else -> "linux"
            }
        val hostArch =
            when {
                archRaw == "aarch64" || archRaw == "arm64" -> "aarch64"
                archRaw == "amd64" || archRaw == "x86_64" -> "x86_64"
                else -> archRaw
            }
        val libFileName =
            when (hostPlatform) {
                "windows" -> "core_jni.dll"
                "macos" -> "libcore_jni.dylib"
                else -> "libcore_jni.so"
            }
        val pathSep = System.getProperty("path.separator", ":")
        val libPathProp = System.getProperty("java.library.path", "")
        for (dirEntry in libPathProp.split(pathSep)) {
            if (dirEntry.isEmpty()) continue
            val candidate = java.io.File(dirEntry, libFileName)
            if (candidate.isFile) {
                System.load(candidate.absolutePath)
                return
            }
        }
        val resourcePath = "io/github/kotlinmania/rustlang/core/native/$hostPlatform-$hostArch/$libFileName"
        val stream = NumImpLibmJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
        if (stream != null) {
            val suffix = "." + libFileName.substringAfterLast('.')
            val extracted = java.nio.file.Files.createTempFile("core_jni-", suffix).toFile()
            extracted.deleteOnExit()
            stream.use { input ->
                extracted.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            System.load(extracted.absolutePath)
            return
        }
        System.loadLibrary("core_jni")
    }

    @JvmStatic
    external fun coreNumImpLibmPowidf2(a: Double, b: Int): Double

    @JvmStatic
    external fun coreNumImpLibmPowisf2(a: Float, b: Int): Float

    @JvmStatic
    external fun coreNumImpLibmCbrt(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmCbrtf(n: Float): Float

    @JvmStatic
    external fun coreNumImpLibmCeil(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmCeilf(x: Float): Float

    @JvmStatic
    external fun coreNumImpLibmCopysign(x: Double, y: Double): Double

    @JvmStatic
    external fun coreNumImpLibmCopysignf(x: Float, y: Float): Float

    @JvmStatic
    external fun coreNumImpLibmFabs(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmFabsf(x: Float): Float

    @JvmStatic
    external fun coreNumImpLibmFdim(a: Double, b: Double): Double

    @JvmStatic
    external fun coreNumImpLibmFdimf(a: Float, b: Float): Float

    @JvmStatic
    external fun coreNumImpLibmFloor(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmFloorf(x: Float): Float

    @JvmStatic
    external fun coreNumImpLibmFma(x: Double, y: Double, z: Double): Double

    @JvmStatic
    external fun coreNumImpLibmFmaf(x: Float, y: Float, z: Float): Float

    @JvmStatic
    external fun coreNumImpLibmFmax(x: Double, y: Double): Double

    @JvmStatic
    external fun coreNumImpLibmFmaxf(x: Float, y: Float): Float

    @JvmStatic
    external fun coreNumImpLibmFmaximum(x: Double, y: Double): Double

    @JvmStatic
    external fun coreNumImpLibmFmaximumf(x: Float, y: Float): Float

    @JvmStatic
    external fun coreNumImpLibmFmin(x: Double, y: Double): Double

    @JvmStatic
    external fun coreNumImpLibmFminf(x: Float, y: Float): Float

    @JvmStatic
    external fun coreNumImpLibmFminimum(x: Double, y: Double): Double

    @JvmStatic
    external fun coreNumImpLibmFminimumf(x: Float, y: Float): Float

    @JvmStatic
    external fun coreNumImpLibmFmod(x: Double, y: Double): Double

    @JvmStatic
    external fun coreNumImpLibmFmodf(x: Float, y: Float): Float

    @JvmStatic
    external fun coreNumImpLibmRint(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmRintf(x: Float): Float

    @JvmStatic
    external fun coreNumImpLibmRound(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmRoundeven(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmRoundevenf(x: Float): Float

    @JvmStatic
    external fun coreNumImpLibmRoundf(x: Float): Float

    @JvmStatic
    external fun coreNumImpLibmSqrt(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmSqrtf(x: Float): Float

    @JvmStatic
    external fun coreNumImpLibmTrunc(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmTruncf(x: Float): Float

    @JvmStatic
    external fun coreNumImpLibmAcos(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmAsin(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmAtan(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmAtan2(x: Double, y: Double): Double

    @JvmStatic
    external fun coreNumImpLibmCos(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmCosh(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmErf(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmErfc(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmExp(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmExp2(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmExpm1(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmHypot(x: Double, y: Double): Double

    @JvmStatic
    external fun coreNumImpLibmLdexp(f: Double, n: Int): Double

    @JvmStatic
    external fun coreNumImpLibmLog(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmLog10(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmLog1p(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmLog2(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmPow(x: Double, y: Double): Double

    @JvmStatic
    external fun coreNumImpLibmSin(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmSinh(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmTan(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmTanh(x: Double): Double

    @JvmStatic
    external fun coreNumImpLibmTgamma(x: Double): Double
}

internal actual fun powidf2(a: Double, b: Int): Double =
    NumImpLibmJniBridge.coreNumImpLibmPowidf2(a, b)

internal actual fun powisf2(a: kotlin.Float, b: Int): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmPowisf2(a, b)

internal actual fun powitf2(a: F128, b: Int): F128 =
    null as F128

internal actual fun cbrt(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmCbrt(x)

internal actual fun cbrtf(n: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmCbrtf(n)

internal actual fun ceil(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmCeil(x)

internal actual fun ceilf(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmCeilf(x)

internal actual fun ceilf128(x: F128): F128 =
    null as F128

internal actual fun ceilf16(x: F16): F16 =
    null as F16

internal actual fun copysign(x: Double, y: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmCopysign(x, y)

internal actual fun copysignf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmCopysignf(x, y)

internal actual fun copysignf128(x: F128, y: F128): F128 =
    null as F128

internal actual fun copysignf16(x: F16, y: F16): F16 =
    null as F16

internal actual fun fabs(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmFabs(x)

internal actual fun fabsf(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmFabsf(x)

internal actual fun fabsf128(x: F128): F128 =
    null as F128

internal actual fun fabsf16(x: F16): F16 =
    null as F16

internal actual fun fdim(a: Double, b: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmFdim(a, b)

internal actual fun fdimf(a: kotlin.Float, b: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmFdimf(a, b)

internal actual fun fdimf128(x: F128, y: F128): F128 =
    null as F128

internal actual fun fdimf16(x: F16, y: F16): F16 =
    null as F16

internal actual fun floor(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmFloor(x)

internal actual fun floorf(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmFloorf(x)

internal actual fun floorf128(x: F128): F128 =
    null as F128

internal actual fun floorf16(x: F16): F16 =
    null as F16

internal actual fun fma(x: Double, y: Double, z: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmFma(x, y, z)

internal actual fun fmaf(x: kotlin.Float, y: kotlin.Float, z: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmFmaf(x, y, z)

internal actual fun fmaf128(x: F128, y: F128, z: F128): F128 =
    null as F128

internal actual fun fmaf16(x: F16, y: F16, z: F16): F16 =
    null as F16

internal actual fun fmax(x: Double, y: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmFmax(x, y)

internal actual fun fmaxf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmFmaxf(x, y)

internal actual fun fmaxf128(x: F128, y: F128): F128 =
    null as F128

internal actual fun fmaxf16(x: F16, y: F16): F16 =
    null as F16

internal actual fun fmaximum(x: Double, y: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmFmaximum(x, y)

internal actual fun fmaximumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmFmaximumf(x, y)

internal actual fun fmaximumf128(x: F128, y: F128): F128 =
    null as F128

internal actual fun fmaximumf16(x: F16, y: F16): F16 =
    null as F16

internal actual fun fmin(x: Double, y: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmFmin(x, y)

internal actual fun fminf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmFminf(x, y)

internal actual fun fminf128(x: F128, y: F128): F128 =
    null as F128

internal actual fun fminf16(x: F16, y: F16): F16 =
    null as F16

internal actual fun fminimum(x: Double, y: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmFminimum(x, y)

internal actual fun fminimumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmFminimumf(x, y)

internal actual fun fminimumf128(x: F128, y: F128): F128 =
    null as F128

internal actual fun fminimumf16(x: F16, y: F16): F16 =
    null as F16

internal actual fun fmod(x: Double, y: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmFmod(x, y)

internal actual fun fmodf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmFmodf(x, y)

internal actual fun fmodf128(x: F128, y: F128): F128 =
    null as F128

internal actual fun fmodf16(x: F16, y: F16): F16 =
    null as F16

internal actual fun rint(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmRint(x)

internal actual fun rintf(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmRintf(x)

internal actual fun rintf128(x: F128): F128 =
    null as F128

internal actual fun rintf16(x: F16): F16 =
    null as F16

internal actual fun round(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmRound(x)

internal actual fun roundeven(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmRoundeven(x)

internal actual fun roundevenf(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmRoundevenf(x)

internal actual fun roundevenf128(x: F128): F128 =
    null as F128

internal actual fun roundevenf16(x: F16): F16 =
    null as F16

internal actual fun roundf(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmRoundf(x)

internal actual fun roundf128(x: F128): F128 =
    null as F128

internal actual fun roundf16(x: F16): F16 =
    null as F16

internal actual fun sqrt(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmSqrt(x)

internal actual fun sqrtf(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmSqrtf(x)

internal actual fun sqrtf128(x: F128): F128 =
    null as F128

internal actual fun sqrtf16(x: F16): F16 =
    null as F16

internal actual fun trunc(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmTrunc(x)

internal actual fun truncf(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmTruncf(x)

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
    NumImpLibmJniBridge.coreNumImpLibmAcos(x)

internal actual fun asin(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmAsin(x)

internal actual fun atan(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmAtan(x)

internal actual fun atan2(x: Double, y: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmAtan2(x, y)

internal actual fun cos(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmCos(x)

internal actual fun cosh(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmCosh(x)

internal actual fun erf(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmErf(x)

internal actual fun erfc(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmErfc(x)

internal actual fun exp(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmExp(x)

internal actual fun exp2(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmExp2(x)

internal actual fun expm1(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmExpm1(x)

internal actual fun hypot(x: Double, y: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmHypot(x, y)

internal actual fun ldexp(f: Double, n: Int): Double =
    NumImpLibmJniBridge.coreNumImpLibmLdexp(f, n)

internal actual fun log(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmLog(x)

internal actual fun log10(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmLog10(x)

internal actual fun log1p(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmLog1p(x)

internal actual fun log2(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmLog2(x)

internal actual fun pow(x: Double, y: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmPow(x, y)

internal actual fun sin(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmSin(x)

internal actual fun sinh(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmSinh(x)

internal actual fun tan(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmTan(x)

internal actual fun tanh(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmTanh(x)

internal actual fun tgamma(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmTgamma(x)

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
