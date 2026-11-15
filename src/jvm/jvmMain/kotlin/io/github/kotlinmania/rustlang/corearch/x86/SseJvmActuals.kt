@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.x86.M128

private object CorearchX86SseJniBridge {
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
        val stream = CorearchX86SseJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun rustlangCorearchX86SseSfence()

    @JvmStatic
    external fun rustlangCorearchX86SseStmxcsr(p: Long)

    @JvmStatic
    external fun rustlangCorearchX86SseLdmxcsr(p: Long)

    @JvmStatic
    external fun rustlangCorearchX86SsePrefetch(p: Long, rw: Int, loc: Int, ty: Int)
}

public actual fun rcpss(a: M128): M128 =
    M128()

public actual fun rcpps(a: M128): M128 =
    M128()

public actual fun rsqrtss(a: M128): M128 =
    M128()

public actual fun rsqrtps(a: M128): M128 =
    M128()

public actual fun minss(a: M128, b: M128): M128 =
    M128()

public actual fun minps(a: M128, b: M128): M128 =
    M128()

public actual fun maxss(a: M128, b: M128): M128 =
    M128()

public actual fun maxps(a: M128, b: M128): M128 =
    M128()

public actual fun cmpps(a: M128, b: M128, imm8: Byte): M128 =
    M128()

public actual fun comieqSs(a: M128, b: M128): Int =
    0

public actual fun comiltSs(a: M128, b: M128): Int =
    0

public actual fun comileSs(a: M128, b: M128): Int =
    0

public actual fun comigtSs(a: M128, b: M128): Int =
    0

public actual fun comigeSs(a: M128, b: M128): Int =
    0

public actual fun comineqSs(a: M128, b: M128): Int =
    0

public actual fun ucomieqSs(a: M128, b: M128): Int =
    0

public actual fun ucomiltSs(a: M128, b: M128): Int =
    0

public actual fun ucomileSs(a: M128, b: M128): Int =
    0

public actual fun ucomigtSs(a: M128, b: M128): Int =
    0

public actual fun ucomigeSs(a: M128, b: M128): Int =
    0

public actual fun ucomineqSs(a: M128, b: M128): Int =
    0

public actual fun cvtss2si(a: M128): Int =
    0

public actual fun cvttss2si(a: M128): Int =
    0

public actual fun sfence() {
    CorearchX86SseJniBridge.rustlangCorearchX86SseSfence()
}

public actual fun stmxcsr(p: MutPtr) {
    CorearchX86SseJniBridge.rustlangCorearchX86SseStmxcsr(p.rawAddress)
}

public actual fun ldmxcsr(p: ConstPtr) {
    CorearchX86SseJniBridge.rustlangCorearchX86SseLdmxcsr(p.rawAddress)
}

public actual fun prefetch(p: ConstPtr, rw: Int, loc: Int, ty: Int) {
    CorearchX86SseJniBridge.rustlangCorearchX86SsePrefetch(p.rawAddress, rw, loc, ty)
}

public actual fun cmpss(a: M128, b: M128, imm8: Byte): M128 =
    M128()
