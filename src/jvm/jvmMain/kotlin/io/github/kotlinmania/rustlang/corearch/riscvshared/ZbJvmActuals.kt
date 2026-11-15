@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.riscvshared

private object CorearchRiscvsharedZbJniBridge {
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
        val stream = CorearchRiscvsharedZbJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun rustlangCorearchRiscvsharedZbOrcB64(rs1: Long): Long

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZbClmul64(rs1: Long, rs2: Long): Long

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZbClmulh64(rs1: Long, rs2: Long): Long

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZbClmulr64(rs1: Long, rs2: Long): Long
}

public actual fun orcB64(rs1: Long): Long =
    CorearchRiscvsharedZbJniBridge.rustlangCorearchRiscvsharedZbOrcB64(rs1)

public actual fun clmul64(rs1: Long, rs2: Long): Long =
    CorearchRiscvsharedZbJniBridge.rustlangCorearchRiscvsharedZbClmul64(rs1, rs2)

public actual fun clmulh64(rs1: Long, rs2: Long): Long =
    CorearchRiscvsharedZbJniBridge.rustlangCorearchRiscvsharedZbClmulh64(rs1, rs2)

public actual fun clmulr64(rs1: Long, rs2: Long): Long =
    CorearchRiscvsharedZbJniBridge.rustlangCorearchRiscvsharedZbClmulr64(rs1, rs2)
