@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.riscvshared

private object CorearchRiscvsharedZkJniBridge {
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
        val stream = CorearchRiscvsharedZkJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun rustlangCorearchRiscvsharedZkSm4ed(rs1: Int, rs2: Int, bs: Int): Int

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZkSm4ks(rs1: Int, rs2: Int, bs: Int): Int

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZkSm3p0(rs1: Int): Int

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZkSm3p1(rs1: Int): Int

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZkSha256sig0(rs1: Int): Int

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZkSha256sig1(rs1: Int): Int

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZkSha256sum0(rs1: Int): Int

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZkSha256sum1(rs1: Int): Int

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZkXperm864(rs1: Long, rs2: Long): Long

    @JvmStatic
    external fun rustlangCorearchRiscvsharedZkXperm464(rs1: Long, rs2: Long): Long
}

public actual fun sm4ed(rs1: Int, rs2: Int, bs: Int): Int =
    CorearchRiscvsharedZkJniBridge.rustlangCorearchRiscvsharedZkSm4ed(rs1, rs2, bs)

public actual fun sm4ks(rs1: Int, rs2: Int, bs: Int): Int =
    CorearchRiscvsharedZkJniBridge.rustlangCorearchRiscvsharedZkSm4ks(rs1, rs2, bs)

public actual fun sm3p0(rs1: Int): Int =
    CorearchRiscvsharedZkJniBridge.rustlangCorearchRiscvsharedZkSm3p0(rs1)

public actual fun sm3p1(rs1: Int): Int =
    CorearchRiscvsharedZkJniBridge.rustlangCorearchRiscvsharedZkSm3p1(rs1)

public actual fun sha256sig0(rs1: Int): Int =
    CorearchRiscvsharedZkJniBridge.rustlangCorearchRiscvsharedZkSha256sig0(rs1)

public actual fun sha256sig1(rs1: Int): Int =
    CorearchRiscvsharedZkJniBridge.rustlangCorearchRiscvsharedZkSha256sig1(rs1)

public actual fun sha256sum0(rs1: Int): Int =
    CorearchRiscvsharedZkJniBridge.rustlangCorearchRiscvsharedZkSha256sum0(rs1)

public actual fun sha256sum1(rs1: Int): Int =
    CorearchRiscvsharedZkJniBridge.rustlangCorearchRiscvsharedZkSha256sum1(rs1)

public actual fun xperm864(rs1: Long, rs2: Long): Long =
    CorearchRiscvsharedZkJniBridge.rustlangCorearchRiscvsharedZkXperm864(rs1, rs2)

public actual fun xperm464(rs1: Long, rs2: Long): Long =
    CorearchRiscvsharedZkJniBridge.rustlangCorearchRiscvsharedZkXperm464(rs1, rs2)
