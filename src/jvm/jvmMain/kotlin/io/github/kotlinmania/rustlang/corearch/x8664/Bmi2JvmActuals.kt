@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

private object X8664Bmi2JniBridge {
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
                "windows" -> "corearch_jni.dll"
                "macos" -> "libcorearch_jni.dylib"
                else -> "libcorearch_jni.so"
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
        val resourcePath = "io/github/kotlinmania/rustlang/corearch/native/$hostPlatform-$hostArch/$libFileName"
        val stream = X8664Bmi2JniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
        if (stream != null) {
            val suffix = "." + libFileName.substringAfterLast('.')
            val extracted = java.nio.file.Files.createTempFile("corearch_jni-", suffix).toFile()
            extracted.deleteOnExit()
            stream.use { input ->
                extracted.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            System.load(extracted.absolutePath)
            return
        }
        System.loadLibrary("corearch_jni")
    }

    @JvmStatic
    external fun corearchX8664Bmi2X86Bmi2Bzhi64(x: Long, y: Long): Long

    @JvmStatic
    external fun corearchX8664Bmi2X86Bmi2Pdep64(x: Long, y: Long): Long

    @JvmStatic
    external fun corearchX8664Bmi2X86Bmi2Pext64(x: Long, y: Long): Long
}

public actual fun x86Bmi2Bzhi64(x: ULong, y: ULong): ULong =
    X8664Bmi2JniBridge.corearchX8664Bmi2X86Bmi2Bzhi64(x.toLong(), y.toLong()).toULong()

public actual fun x86Bmi2Pdep64(x: ULong, y: ULong): ULong =
    X8664Bmi2JniBridge.corearchX8664Bmi2X86Bmi2Pdep64(x.toLong(), y.toLong()).toULong()

public actual fun x86Bmi2Pext64(x: ULong, y: ULong): ULong =
    X8664Bmi2JniBridge.corearchX8664Bmi2X86Bmi2Pext64(x.toLong(), y.toLong()).toULong()
