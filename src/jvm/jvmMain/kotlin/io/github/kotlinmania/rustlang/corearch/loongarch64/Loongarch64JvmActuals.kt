@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64

private object CorearchLoongarch64JniBridge {
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
        val stream = CorearchLoongarch64JniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun rustlangCorearchLoongarch64CrcWBW(a: Int, b: Int): Int

    @JvmStatic
    external fun rustlangCorearchLoongarch64CrcWHW(a: Int, b: Int): Int

    @JvmStatic
    external fun rustlangCorearchLoongarch64CrcWWW(a: Int, b: Int): Int

    @JvmStatic
    external fun rustlangCorearchLoongarch64CrcWDW(a: Long, b: Int): Int

    @JvmStatic
    external fun rustlangCorearchLoongarch64CrccWBW(a: Int, b: Int): Int

    @JvmStatic
    external fun rustlangCorearchLoongarch64CrccWHW(a: Int, b: Int): Int

    @JvmStatic
    external fun rustlangCorearchLoongarch64CrccWWW(a: Int, b: Int): Int

    @JvmStatic
    external fun rustlangCorearchLoongarch64CrccWDW(a: Long, b: Int): Int

    @JvmStatic
    external fun rustlangCorearchLoongarch64Cacop(a: Long, b: Long, c: Long)

    @JvmStatic
    external fun rustlangCorearchLoongarch64Csrrd(a: Int): Long

    @JvmStatic
    external fun rustlangCorearchLoongarch64Csrwr(a: Long, b: Int): Long

    @JvmStatic
    external fun rustlangCorearchLoongarch64Csrxchg(a: Long, b: Long, c: Int): Long

    @JvmStatic
    external fun rustlangCorearchLoongarch64IocsrrdD(a: Int): Long

    @JvmStatic
    external fun rustlangCorearchLoongarch64IocsrwrD(a: Long, b: Int)

    @JvmStatic
    external fun rustlangCorearchLoongarch64Asrtle(a: Long, b: Long)

    @JvmStatic
    external fun rustlangCorearchLoongarch64Asrtgt(a: Long, b: Long)

    @JvmStatic
    external fun rustlangCorearchLoongarch64Lddir(a: Long, b: Long): Long

    @JvmStatic
    external fun rustlangCorearchLoongarch64Ldpte(a: Long, b: Long)
}

public actual fun crcWBW(a: Int, b: Int): Int =
    CorearchLoongarch64JniBridge.rustlangCorearchLoongarch64CrcWBW(a, b)

public actual fun crcWHW(a: Int, b: Int): Int =
    CorearchLoongarch64JniBridge.rustlangCorearchLoongarch64CrcWHW(a, b)

public actual fun crcWWW(a: Int, b: Int): Int =
    CorearchLoongarch64JniBridge.rustlangCorearchLoongarch64CrcWWW(a, b)

public actual fun crcWDW(a: Long, b: Int): Int =
    CorearchLoongarch64JniBridge.rustlangCorearchLoongarch64CrcWDW(a, b)

public actual fun crccWBW(a: Int, b: Int): Int =
    CorearchLoongarch64JniBridge.rustlangCorearchLoongarch64CrccWBW(a, b)

public actual fun crccWHW(a: Int, b: Int): Int =
    CorearchLoongarch64JniBridge.rustlangCorearchLoongarch64CrccWHW(a, b)

public actual fun crccWWW(a: Int, b: Int): Int =
    CorearchLoongarch64JniBridge.rustlangCorearchLoongarch64CrccWWW(a, b)

public actual fun crccWDW(a: Long, b: Int): Int =
    CorearchLoongarch64JniBridge.rustlangCorearchLoongarch64CrccWDW(a, b)

public actual fun cacop(a: Long, b: Long, c: Long) {
    CorearchLoongarch64JniBridge.rustlangCorearchLoongarch64Cacop(a, b, c)
}

public actual fun csrrd(a: Int): Long =
    CorearchLoongarch64JniBridge.rustlangCorearchLoongarch64Csrrd(a)

public actual fun csrwr(a: Long, b: Int): Long =
    CorearchLoongarch64JniBridge.rustlangCorearchLoongarch64Csrwr(a, b)

public actual fun csrxchg(a: Long, b: Long, c: Int): Long =
    CorearchLoongarch64JniBridge.rustlangCorearchLoongarch64Csrxchg(a, b, c)

public actual fun iocsrrdD(a: Int): Long =
    CorearchLoongarch64JniBridge.rustlangCorearchLoongarch64IocsrrdD(a)

public actual fun iocsrwrD(a: Long, b: Int) {
    CorearchLoongarch64JniBridge.rustlangCorearchLoongarch64IocsrwrD(a, b)
}

public actual fun asrtle(a: Long, b: Long) {
    CorearchLoongarch64JniBridge.rustlangCorearchLoongarch64Asrtle(a, b)
}

public actual fun asrtgt(a: Long, b: Long) {
    CorearchLoongarch64JniBridge.rustlangCorearchLoongarch64Asrtgt(a, b)
}

public actual fun lddir(a: Long, b: Long): Long =
    CorearchLoongarch64JniBridge.rustlangCorearchLoongarch64Lddir(a, b)

public actual fun ldpte(a: Long, b: Long) {
    CorearchLoongarch64JniBridge.rustlangCorearchLoongarch64Ldpte(a, b)
}
