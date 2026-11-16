@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.armshared.barrier

private object ArmsharedBarrierJniBridge {
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
        val stream = ArmsharedBarrierJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun corearchArmsharedBarrierDmb(arg0: Int)

    @JvmStatic
    external fun corearchArmsharedBarrierDsb(arg0: Int)

    @JvmStatic
    external fun corearchArmsharedBarrierIsb(arg0: Int)
}

public actual fun dmb(arg0: Int) {
    ArmsharedBarrierJniBridge.corearchArmsharedBarrierDmb(arg0)
}

public actual fun dsb(arg0: Int) {
    ArmsharedBarrierJniBridge.corearchArmsharedBarrierDsb(arg0)
}

public actual fun isb(arg0: Int) {
    ArmsharedBarrierJniBridge.corearchArmsharedBarrierIsb(arg0)
}
