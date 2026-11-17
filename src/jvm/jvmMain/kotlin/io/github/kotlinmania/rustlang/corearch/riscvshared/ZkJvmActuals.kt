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
        val resourcePath = "io/github/kotlinmania/rustlang/corearch/native/$hostPlatform-$hostArch/$libFileName"
        val stream = RiscvsharedZkJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun corearchRiscvsharedZkExternSm4ed(rs1: Int, rs2: Int, bs: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkExternSm4ks(rs1: Int, rs2: Int, bs: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkExternSm3p0(rs1: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkExternSm3p1(rs1: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkExternSha256sig0(rs1: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkExternSha256sig1(rs1: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkExternSha256sum0(rs1: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkExternSha256sum1(rs1: Int): Int

    @JvmStatic
    external fun corearchRiscvsharedZkXperm864(rs1: Long, rs2: Long): Long

    @JvmStatic
    external fun corearchRiscvsharedZkXperm464(rs1: Long, rs2: Long): Long
}

public actual fun externSm4ed(rs1: Int, rs2: Int, bs: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkExternSm4ed(rs1, rs2, bs)

public actual fun externSm4ks(rs1: Int, rs2: Int, bs: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkExternSm4ks(rs1, rs2, bs)

public actual fun externSm3p0(rs1: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkExternSm3p0(rs1)

public actual fun externSm3p1(rs1: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkExternSm3p1(rs1)

public actual fun externSha256sig0(rs1: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkExternSha256sig0(rs1)

public actual fun externSha256sig1(rs1: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkExternSha256sig1(rs1)

public actual fun externSha256sum0(rs1: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkExternSha256sum0(rs1)

public actual fun externSha256sum1(rs1: Int): Int =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkExternSha256sum1(rs1)

public actual fun xperm864(rs1: Long, rs2: Long): Long =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkXperm864(rs1, rs2)

public actual fun xperm464(rs1: Long, rs2: Long): Long =
    RiscvsharedZkJniBridge.corearchRiscvsharedZkXperm464(rs1, rs2)
