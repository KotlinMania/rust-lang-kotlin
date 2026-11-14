@file:OptIn(ExperimentalUnsignedTypes::class)

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
                "windows" -> "kmcore_jni.dll"
                "macos" -> "libkmcore_jni.dylib"
                else -> "libkmcore_jni.so"
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
            val extracted = java.nio.file.Files.createTempFile("kmcore_jni-", suffix).toFile()
            extracted.deleteOnExit()
            stream.use { input ->
                extracted.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            System.load(extracted.absolutePath)
            return
        }
        System.loadLibrary("kmcore_jni")
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
    external fun coreNumImpLibmAcosf(n: Float): Float

    @JvmStatic
    external fun coreNumImpLibmAsinf(n: Float): Float

    @JvmStatic
    external fun coreNumImpLibmAtan2f(a: Float, b: Float): Float

    @JvmStatic
    external fun coreNumImpLibmAtanf(n: Float): Float

    @JvmStatic
    external fun coreNumImpLibmCosf(x: Float): Float

    @JvmStatic
    external fun coreNumImpLibmCoshf(n: Float): Float

    @JvmStatic
    external fun coreNumImpLibmErfcf(x: Float): Float

    @JvmStatic
    external fun coreNumImpLibmErff(x: Float): Float

    @JvmStatic
    external fun coreNumImpLibmExp2f(x: Float): Float

    @JvmStatic
    external fun coreNumImpLibmExpf(x: Float): Float

    @JvmStatic
    external fun coreNumImpLibmExpm1f(n: Float): Float

    @JvmStatic
    external fun coreNumImpLibmHypotf(x: Float, y: Float): Float

    @JvmStatic
    external fun coreNumImpLibmLdexpf(f: Float, n: Int): Float

    @JvmStatic
    external fun coreNumImpLibmLog10f(x: Float): Float

    @JvmStatic
    external fun coreNumImpLibmLog1pf(n: Float): Float

    @JvmStatic
    external fun coreNumImpLibmLog2f(x: Float): Float

    @JvmStatic
    external fun coreNumImpLibmLogf(x: Float): Float

    @JvmStatic
    external fun coreNumImpLibmPowf(x: Float, y: Float): Float

    @JvmStatic
    external fun coreNumImpLibmSinf(x: Float): Float

    @JvmStatic
    external fun coreNumImpLibmSinhf(n: Float): Float

    @JvmStatic
    external fun coreNumImpLibmTanf(n: Float): Float

    @JvmStatic
    external fun coreNumImpLibmTanhf(n: Float): Float

    @JvmStatic
    external fun coreNumImpLibmTgammaf(x: Float): Float

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

public actual fun powidf2(a: Double, b: Int): Double =
    NumImpLibmJniBridge.coreNumImpLibmPowidf2(a, b)

public actual fun powisf2(a: kotlin.Float, b: Int): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmPowisf2(a, b)

public actual fun powitf2(a: F128, b: Int): F128 =
    F128()

public actual fun cbrt(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmCbrt(x)

public actual fun cbrtf(n: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmCbrtf(n)

public actual fun ceil(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmCeil(x)

public actual fun ceilf(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmCeilf(x)

public actual fun ceilf128(x: F128): F128 =
    F128()

public actual fun ceilf16(x: F16): F16 =
    F16()

public actual fun copysign(x: Double, y: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmCopysign(x, y)

public actual fun copysignf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmCopysignf(x, y)

public actual fun copysignf128(x: F128, y: F128): F128 =
    F128()

public actual fun copysignf16(x: F16, y: F16): F16 =
    F16()

public actual fun fabs(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmFabs(x)

public actual fun fabsf(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmFabsf(x)

public actual fun fabsf128(x: F128): F128 =
    F128()

public actual fun fabsf16(x: F16): F16 =
    F16()

public actual fun fdim(a: Double, b: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmFdim(a, b)

public actual fun fdimf(a: kotlin.Float, b: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmFdimf(a, b)

public actual fun fdimf128(x: F128, y: F128): F128 =
    F128()

public actual fun fdimf16(x: F16, y: F16): F16 =
    F16()

public actual fun floor(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmFloor(x)

public actual fun floorf(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmFloorf(x)

public actual fun floorf128(x: F128): F128 =
    F128()

public actual fun floorf16(x: F16): F16 =
    F16()

public actual fun fma(x: Double, y: Double, z: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmFma(x, y, z)

public actual fun fmaf(x: kotlin.Float, y: kotlin.Float, z: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmFmaf(x, y, z)

public actual fun fmaf128(x: F128, y: F128, z: F128): F128 =
    F128()

public actual fun fmaf16(x: F16, y: F16, z: F16): F16 =
    F16()

public actual fun fmax(x: Double, y: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmFmax(x, y)

public actual fun fmaxf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmFmaxf(x, y)

public actual fun fmaxf128(x: F128, y: F128): F128 =
    F128()

public actual fun fmaxf16(x: F16, y: F16): F16 =
    F16()

public actual fun fmaximum(x: Double, y: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmFmaximum(x, y)

public actual fun fmaximumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmFmaximumf(x, y)

public actual fun fmaximumf128(x: F128, y: F128): F128 =
    F128()

public actual fun fmaximumf16(x: F16, y: F16): F16 =
    F16()

public actual fun fmin(x: Double, y: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmFmin(x, y)

public actual fun fminf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmFminf(x, y)

public actual fun fminf128(x: F128, y: F128): F128 =
    F128()

public actual fun fminf16(x: F16, y: F16): F16 =
    F16()

public actual fun fminimum(x: Double, y: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmFminimum(x, y)

public actual fun fminimumf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmFminimumf(x, y)

public actual fun fminimumf128(x: F128, y: F128): F128 =
    F128()

public actual fun fminimumf16(x: F16, y: F16): F16 =
    F16()

public actual fun fmod(x: Double, y: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmFmod(x, y)

public actual fun fmodf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmFmodf(x, y)

public actual fun fmodf128(x: F128, y: F128): F128 =
    F128()

public actual fun fmodf16(x: F16, y: F16): F16 =
    F16()

public actual fun rint(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmRint(x)

public actual fun rintf(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmRintf(x)

public actual fun rintf128(x: F128): F128 =
    F128()

public actual fun rintf16(x: F16): F16 =
    F16()

public actual fun round(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmRound(x)

public actual fun roundeven(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmRoundeven(x)

public actual fun roundevenf(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmRoundevenf(x)

public actual fun roundevenf128(x: F128): F128 =
    F128()

public actual fun roundevenf16(x: F16): F16 =
    F16()

public actual fun roundf(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmRoundf(x)

public actual fun roundf128(x: F128): F128 =
    F128()

public actual fun roundf16(x: F16): F16 =
    F16()

public actual fun sqrt(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmSqrt(x)

public actual fun sqrtf(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmSqrtf(x)

public actual fun sqrtf128(x: F128): F128 =
    F128()

public actual fun sqrtf16(x: F16): F16 =
    F16()

public actual fun trunc(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmTrunc(x)

public actual fun truncf(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmTruncf(x)

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
    NumImpLibmJniBridge.coreNumImpLibmAcosf(n)

public actual fun asinf(n: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmAsinf(n)

public actual fun atan2f(a: kotlin.Float, b: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmAtan2f(a, b)

public actual fun atanf(n: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmAtanf(n)

public actual fun cosf(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmCosf(x)

public actual fun coshf(n: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmCoshf(n)

public actual fun erfcf(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmErfcf(x)

public actual fun erff(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmErff(x)

public actual fun exp2f(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmExp2f(x)

public actual fun expf(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmExpf(x)

public actual fun expm1f(n: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmExpm1f(n)

public actual fun hypotf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmHypotf(x, y)

public actual fun ldexpf(f: kotlin.Float, n: Int): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmLdexpf(f, n)

public actual fun log10f(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmLog10f(x)

public actual fun log1pf(n: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmLog1pf(n)

public actual fun log2f(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmLog2f(x)

public actual fun logf(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmLogf(x)

public actual fun powf(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmPowf(x, y)

public actual fun sinf(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmSinf(x)

public actual fun sinhf(n: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmSinhf(n)

public actual fun tanf(n: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmTanf(n)

public actual fun tanhf(n: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmTanhf(n)

public actual fun tgammaf(x: kotlin.Float): kotlin.Float =
    NumImpLibmJniBridge.coreNumImpLibmTgammaf(x)

public actual fun acos(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmAcos(x)

public actual fun asin(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmAsin(x)

public actual fun atan(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmAtan(x)

public actual fun atan2(x: Double, y: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmAtan2(x, y)

public actual fun cos(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmCos(x)

public actual fun cosh(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmCosh(x)

public actual fun erf(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmErf(x)

public actual fun erfc(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmErfc(x)

public actual fun exp(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmExp(x)

public actual fun exp2(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmExp2(x)

public actual fun expm1(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmExpm1(x)

public actual fun hypot(x: Double, y: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmHypot(x, y)

public actual fun ldexp(f: Double, n: Int): Double =
    NumImpLibmJniBridge.coreNumImpLibmLdexp(f, n)

public actual fun log(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmLog(x)

public actual fun log10(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmLog10(x)

public actual fun log1p(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmLog1p(x)

public actual fun log2(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmLog2(x)

public actual fun pow(x: Double, y: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmPow(x, y)

public actual fun sin(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmSin(x)

public actual fun sinh(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmSinh(x)

public actual fun tan(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmTan(x)

public actual fun tanh(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmTanh(x)

public actual fun tgamma(x: Double): Double =
    NumImpLibmJniBridge.coreNumImpLibmTgamma(x)

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
