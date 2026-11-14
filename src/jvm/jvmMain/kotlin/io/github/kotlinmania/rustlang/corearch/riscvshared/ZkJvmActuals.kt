@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.riscvshared

private object RiscvsharedZkJniBridge {
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
        val stream = RiscvsharedZkJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun corearchRiscvsharedZkSm4ed(rs1: Int, rs2: Int, bs: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkSm4ks(rs1: Int, rs2: Int, bs: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkSm3p0(rs1: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkSm3p1(rs1: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkSha256sig0(rs1: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkSha256sig1(rs1: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkSha256sum0(rs1: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkSha256sum1(rs1: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkXperm864(rs1: Long, rs2: Long): Long

    @JvmStatic
    external fun corearchRiscvsharedZkXperm464(rs1: Long, rs2: Long): Long
}

public actual fun sm4ed(rs1: Int, rs2: Int, bs: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkSm4ed(rs1, rs2, bs)

public actual fun sm4ks(rs1: Int, rs2: Int, bs: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkSm4ks(rs1, rs2, bs)

public actual fun sm3p0(rs1: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkSm3p0(rs1)

public actual fun sm3p1(rs1: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkSm3p1(rs1)

public actual fun sha256sig0(rs1: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkSha256sig0(rs1)

public actual fun sha256sig1(rs1: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkSha256sig1(rs1)

public actual fun sha256sum0(rs1: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkSha256sum0(rs1)

public actual fun sha256sum1(rs1: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkSha256sum1(rs1)

public actual fun xperm864(rs1: Long, rs2: Long): Long =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkXperm864(rs1, rs2)

public actual fun xperm464(rs1: Long, rs2: Long): Long =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkXperm464(rs1, rs2)
