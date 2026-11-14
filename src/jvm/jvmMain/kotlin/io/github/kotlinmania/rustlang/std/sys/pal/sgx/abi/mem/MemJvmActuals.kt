@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.mem

private object SysPalSgxAbiMemJniBridge {
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
                "windows" -> "std_jni.dll"
                "macos" -> "libstd_jni.dylib"
                else -> "libstd_jni.so"
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
        val resourcePath = "io/github/kotlinmania/rustlang/std/native/$hostPlatform-$hostArch/$libFileName"
        val stream = SysPalSgxAbiMemJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
        if (stream != null) {
            val suffix = "." + libFileName.substringAfterLast('.')
            val extracted = java.nio.file.Files.createTempFile("std_jni-", suffix).toFile()
            extracted.deleteOnExit()
            stream.use { input ->
                extracted.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            System.load(extracted.absolutePath)
            return
        }
        System.loadLibrary("std_jni")
    }

    @JvmStatic
    external fun stdSysPalSgxAbiMemGetENCLAVESIZE(): Long

    @JvmStatic
    external fun stdSysPalSgxAbiMemGetHEAPBASE(): Long

    @JvmStatic
    external fun stdSysPalSgxAbiMemGetHEAPSIZE(): Long
}

public actual val ENCLAVE_SIZE: ULong
    get() = SysPalSgxAbiMemJniBridge.stdSysPalSgxAbiMemGetENCLAVESIZE().toULong()

public actual val HEAP_BASE: ULong
    get() = SysPalSgxAbiMemJniBridge.stdSysPalSgxAbiMemGetHEAPBASE().toULong()

public actual val HEAP_SIZE: ULong
    get() = SysPalSgxAbiMemJniBridge.stdSysPalSgxAbiMemGetHEAPSIZE().toULong()
