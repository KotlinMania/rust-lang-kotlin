@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.cmath

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

private object StdSysCmathJniBridge {
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
        val resourcePath = "io/github/kotlinmania/rustlang/native/$hostPlatform-$hostArch/$libFileName"
        val stream = StdSysCmathJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun rustlangStdSysCmathAcos(n: Double): Double

    @JvmStatic
    external fun rustlangStdSysCmathAcosh(n: Double): Double

    @JvmStatic
    external fun rustlangStdSysCmathAsin(n: Double): Double

    @JvmStatic
    external fun rustlangStdSysCmathAsinh(n: Double): Double

    @JvmStatic
    external fun rustlangStdSysCmathAtan(n: Double): Double

    @JvmStatic
    external fun rustlangStdSysCmathAtan2(a: Double, b: Double): Double

    @JvmStatic
    external fun rustlangStdSysCmathCosh(n: Double): Double

    @JvmStatic
    external fun rustlangStdSysCmathExpm1(n: Double): Double

    @JvmStatic
    external fun rustlangStdSysCmathExpm1f(n: Float): Float

    @JvmStatic
    external fun rustlangStdSysCmathHypot(x: Double, y: Double): Double

    @JvmStatic
    external fun rustlangStdSysCmathHypotf(x: Float, y: Float): Float

    @JvmStatic
    external fun rustlangStdSysCmathLog1p(n: Double): Double

    @JvmStatic
    external fun rustlangStdSysCmathLog1pf(n: Float): Float

    @JvmStatic
    external fun rustlangStdSysCmathSinh(n: Double): Double

    @JvmStatic
    external fun rustlangStdSysCmathTan(n: Double): Double

    @JvmStatic
    external fun rustlangStdSysCmathTanh(n: Double): Double

    @JvmStatic
    external fun rustlangStdSysCmathTgamma(n: Double): Double

    @JvmStatic
    external fun rustlangStdSysCmathTgammaf(n: Float): Float

    @JvmStatic
    external fun rustlangStdSysCmathErf(n: Double): Double

    @JvmStatic
    external fun rustlangStdSysCmathErff(n: Float): Float

    @JvmStatic
    external fun rustlangStdSysCmathErfc(n: Double): Double

    @JvmStatic
    external fun rustlangStdSysCmathErfcf(n: Float): Float

    @JvmStatic
    external fun rustlangStdSysCmathAcosf(n: Float): Float

    @JvmStatic
    external fun rustlangStdSysCmathAcoshf(n: Float): Float

    @JvmStatic
    external fun rustlangStdSysCmathAsinf(n: Float): Float

    @JvmStatic
    external fun rustlangStdSysCmathAsinhf(n: Float): Float

    @JvmStatic
    external fun rustlangStdSysCmathAtan2f(a: Float, b: Float): Float

    @JvmStatic
    external fun rustlangStdSysCmathAtanf(n: Float): Float

    @JvmStatic
    external fun rustlangStdSysCmathCoshf(n: Float): Float

    @JvmStatic
    external fun rustlangStdSysCmathSinhf(n: Float): Float

    @JvmStatic
    external fun rustlangStdSysCmathTanf(n: Float): Float

    @JvmStatic
    external fun rustlangStdSysCmathTanhf(n: Float): Float
}

public actual fun acos(n: Double): Double =
    StdSysCmathJniBridge.rustlangStdSysCmathAcos(n)

public actual fun acosh(n: Double): Double =
    StdSysCmathJniBridge.rustlangStdSysCmathAcosh(n)

public actual fun asin(n: Double): Double =
    StdSysCmathJniBridge.rustlangStdSysCmathAsin(n)

public actual fun asinh(n: Double): Double =
    StdSysCmathJniBridge.rustlangStdSysCmathAsinh(n)

public actual fun atan(n: Double): Double =
    StdSysCmathJniBridge.rustlangStdSysCmathAtan(n)

public actual fun atan2(a: Double, b: Double): Double =
    StdSysCmathJniBridge.rustlangStdSysCmathAtan2(a, b)

public actual fun cosh(n: Double): Double =
    StdSysCmathJniBridge.rustlangStdSysCmathCosh(n)

public actual fun expm1(n: Double): Double =
    StdSysCmathJniBridge.rustlangStdSysCmathExpm1(n)

public actual fun expm1f(n: Float): Float =
    StdSysCmathJniBridge.rustlangStdSysCmathExpm1f(n)

public actual fun hypot(x: Double, y: Double): Double =
    StdSysCmathJniBridge.rustlangStdSysCmathHypot(x, y)

public actual fun hypotf(x: Float, y: Float): Float =
    StdSysCmathJniBridge.rustlangStdSysCmathHypotf(x, y)

public actual fun log1p(n: Double): Double =
    StdSysCmathJniBridge.rustlangStdSysCmathLog1p(n)

public actual fun log1pf(n: Float): Float =
    StdSysCmathJniBridge.rustlangStdSysCmathLog1pf(n)

public actual fun sinh(n: Double): Double =
    StdSysCmathJniBridge.rustlangStdSysCmathSinh(n)

public actual fun tan(n: Double): Double =
    StdSysCmathJniBridge.rustlangStdSysCmathTan(n)

public actual fun tanh(n: Double): Double =
    StdSysCmathJniBridge.rustlangStdSysCmathTanh(n)

public actual fun tgamma(n: Double): Double =
    StdSysCmathJniBridge.rustlangStdSysCmathTgamma(n)

public actual fun tgammaf(n: Float): Float =
    StdSysCmathJniBridge.rustlangStdSysCmathTgammaf(n)

public actual fun lgammaR(n: Double, s: RustMutableReference<Int>): Double =
    0.0

public actual fun lgammafR(n: Float, s: RustMutableReference<Int>): Float =
    0.0f

public actual fun erf(n: Double): Double =
    StdSysCmathJniBridge.rustlangStdSysCmathErf(n)

public actual fun erff(n: Float): Float =
    StdSysCmathJniBridge.rustlangStdSysCmathErff(n)

public actual fun erfc(n: Double): Double =
    StdSysCmathJniBridge.rustlangStdSysCmathErfc(n)

public actual fun erfcf(n: Float): Float =
    StdSysCmathJniBridge.rustlangStdSysCmathErfcf(n)

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
    StdSysCmathJniBridge.rustlangStdSysCmathAcosf(n)

public actual fun acoshf(n: Float): Float =
    StdSysCmathJniBridge.rustlangStdSysCmathAcoshf(n)

public actual fun asinf(n: Float): Float =
    StdSysCmathJniBridge.rustlangStdSysCmathAsinf(n)

public actual fun asinhf(n: Float): Float =
    StdSysCmathJniBridge.rustlangStdSysCmathAsinhf(n)

public actual fun atan2f(a: Float, b: Float): Float =
    StdSysCmathJniBridge.rustlangStdSysCmathAtan2f(a, b)

public actual fun atanf(n: Float): Float =
    StdSysCmathJniBridge.rustlangStdSysCmathAtanf(n)

public actual fun coshf(n: Float): Float =
    StdSysCmathJniBridge.rustlangStdSysCmathCoshf(n)

public actual fun sinhf(n: Float): Float =
    StdSysCmathJniBridge.rustlangStdSysCmathSinhf(n)

public actual fun tanf(n: Float): Float =
    StdSysCmathJniBridge.rustlangStdSysCmathTanf(n)

public actual fun tanhf(n: Float): Float =
    StdSysCmathJniBridge.rustlangStdSysCmathTanhf(n)
