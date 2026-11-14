@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.riscvshared

private object RiscvsharedZbJniBridge {
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
        val stream = RiscvsharedZbJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun corearchRiscvsharedZbOrcB64(rs1: Long): Long

    @JvmStatic
    external fun corearchRiscvsharedZbClmul64(rs1: Long, rs2: Long): Long

    @JvmStatic
    external fun corearchRiscvsharedZbClmulh64(rs1: Long, rs2: Long): Long

    @JvmStatic
    external fun corearchRiscvsharedZbClmulr64(rs1: Long, rs2: Long): Long
}

public actual fun orcB64(rs1: Long): Long =
    RiscvsharedZbJniBridge.corearchRiscvsharedZbOrcB64(rs1)

public actual fun clmul64(rs1: Long, rs2: Long): Long =
    RiscvsharedZbJniBridge.corearchRiscvsharedZbClmul64(rs1, rs2)

public actual fun clmulh64(rs1: Long, rs2: Long): Long =
    RiscvsharedZbJniBridge.corearchRiscvsharedZbClmulh64(rs1, rs2)

public actual fun clmulr64(rs1: Long, rs2: Long): Long =
    RiscvsharedZbJniBridge.corearchRiscvsharedZbClmulr64(rs1, rs2)
