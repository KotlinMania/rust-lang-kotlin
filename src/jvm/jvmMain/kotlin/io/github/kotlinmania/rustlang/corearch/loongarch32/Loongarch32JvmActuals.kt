@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch32

private object Loongarch32JniBridge {
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
        val stream = Loongarch32JniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun corearchLoongarch32Cacop(a: Int, b: Int, c: Int)

    @JvmStatic
    external fun corearchLoongarch32Csrrd(a: Int): Int

    @JvmStatic
    external fun corearchLoongarch32Csrwr(a: Int, b: Int): Int

    @JvmStatic
    external fun corearchLoongarch32Csrxchg(a: Int, b: Int, c: Int): Int
}

public actual fun cacop(a: Int, b: Int, c: Int) {
    Loongarch32JniBridge.corearchLoongarch32Cacop(a, b, c)
}

public actual fun csrrd(a: Int): Int =
    Loongarch32JniBridge.corearchLoongarch32Csrrd(a)

public actual fun csrwr(a: Int, b: Int): Int =
    Loongarch32JniBridge.corearchLoongarch32Csrwr(a, b)

public actual fun csrxchg(a: Int, b: Int, c: Int): Int =
    Loongarch32JniBridge.corearchLoongarch32Csrxchg(a, b, c)
