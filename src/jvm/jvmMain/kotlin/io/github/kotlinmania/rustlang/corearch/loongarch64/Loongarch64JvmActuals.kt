@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64

private object Loongarch64JniBridge {
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
        val stream = Loongarch64JniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun corearchLoongarch64ExternCrcWBW(a: Int, b: Int): Int

    @JvmStatic
    external fun corearchLoongarch64ExternCrcWHW(a: Int, b: Int): Int

    @JvmStatic
    external fun corearchLoongarch64ExternCrcWWW(a: Int, b: Int): Int

    @JvmStatic
    external fun corearchLoongarch64ExternCrcWDW(a: Long, b: Int): Int

    @JvmStatic
    external fun corearchLoongarch64ExternCrccWBW(a: Int, b: Int): Int

    @JvmStatic
    external fun corearchLoongarch64ExternCrccWHW(a: Int, b: Int): Int

    @JvmStatic
    external fun corearchLoongarch64ExternCrccWWW(a: Int, b: Int): Int

    @JvmStatic
    external fun corearchLoongarch64ExternCrccWDW(a: Long, b: Int): Int

    @JvmStatic
    external fun corearchLoongarch64ExternCacop(a: Long, b: Long, c: Long)

    @JvmStatic
    external fun corearchLoongarch64ExternCsrrd(a: Int): Long

    @JvmStatic
    external fun corearchLoongarch64ExternCsrwr(a: Long, b: Int): Long

    @JvmStatic
    external fun corearchLoongarch64ExternCsrxchg(a: Long, b: Long, c: Int): Long

    @JvmStatic
    external fun corearchLoongarch64ExternIocsrrdD(a: Int): Long

    @JvmStatic
    external fun corearchLoongarch64ExternIocsrwrD(a: Long, b: Int)

    @JvmStatic
    external fun corearchLoongarch64ExternAsrtle(a: Long, b: Long)

    @JvmStatic
    external fun corearchLoongarch64ExternAsrtgt(a: Long, b: Long)

    @JvmStatic
    external fun corearchLoongarch64ExternLddir(a: Long, b: Long): Long

    @JvmStatic
    external fun corearchLoongarch64ExternLdpte(a: Long, b: Long)
}

public actual fun externCrcWBW(a: Int, b: Int): Int =
    Loongarch64JniBridge.corearchLoongarch64ExternCrcWBW(a, b)

public actual fun externCrcWHW(a: Int, b: Int): Int =
    Loongarch64JniBridge.corearchLoongarch64ExternCrcWHW(a, b)

public actual fun externCrcWWW(a: Int, b: Int): Int =
    Loongarch64JniBridge.corearchLoongarch64ExternCrcWWW(a, b)

public actual fun externCrcWDW(a: Long, b: Int): Int =
    Loongarch64JniBridge.corearchLoongarch64ExternCrcWDW(a, b)

public actual fun externCrccWBW(a: Int, b: Int): Int =
    Loongarch64JniBridge.corearchLoongarch64ExternCrccWBW(a, b)

public actual fun externCrccWHW(a: Int, b: Int): Int =
    Loongarch64JniBridge.corearchLoongarch64ExternCrccWHW(a, b)

public actual fun externCrccWWW(a: Int, b: Int): Int =
    Loongarch64JniBridge.corearchLoongarch64ExternCrccWWW(a, b)

public actual fun externCrccWDW(a: Long, b: Int): Int =
    Loongarch64JniBridge.corearchLoongarch64ExternCrccWDW(a, b)

public actual fun externCacop(a: Long, b: Long, c: Long) {
    Loongarch64JniBridge.corearchLoongarch64ExternCacop(a, b, c)
}

public actual fun externCsrrd(a: Int): Long =
    Loongarch64JniBridge.corearchLoongarch64ExternCsrrd(a)

public actual fun externCsrwr(a: Long, b: Int): Long =
    Loongarch64JniBridge.corearchLoongarch64ExternCsrwr(a, b)

public actual fun externCsrxchg(a: Long, b: Long, c: Int): Long =
    Loongarch64JniBridge.corearchLoongarch64ExternCsrxchg(a, b, c)

public actual fun externIocsrrdD(a: Int): Long =
    Loongarch64JniBridge.corearchLoongarch64ExternIocsrrdD(a)

public actual fun externIocsrwrD(a: Long, b: Int) {
    Loongarch64JniBridge.corearchLoongarch64ExternIocsrwrD(a, b)
}

public actual fun externAsrtle(a: Long, b: Long) {
    Loongarch64JniBridge.corearchLoongarch64ExternAsrtle(a, b)
}

public actual fun externAsrtgt(a: Long, b: Long) {
    Loongarch64JniBridge.corearchLoongarch64ExternAsrtgt(a, b)
}

public actual fun externLddir(a: Long, b: Long): Long =
    Loongarch64JniBridge.corearchLoongarch64ExternLddir(a, b)

public actual fun externLdpte(a: Long, b: Long) {
    Loongarch64JniBridge.corearchLoongarch64ExternLdpte(a, b)
}
