@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarchshared

private object LoongarchsharedJniBridge {
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
        val stream = LoongarchsharedJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun corearchLoongarchsharedExternDbar(a: Int)

    @JvmStatic
    external fun corearchLoongarchsharedExternIbar(a: Int)

    @JvmStatic
    external fun corearchLoongarchsharedExternMovgr2fcsr(a: Int, b: Int)

    @JvmStatic
    external fun corearchLoongarchsharedExternMovfcsr2gr(a: Int): Int

    @JvmStatic
    external fun corearchLoongarchsharedExternIocsrrdB(a: Int): Int

    @JvmStatic
    external fun corearchLoongarchsharedExternIocsrrdH(a: Int): Int

    @JvmStatic
    external fun corearchLoongarchsharedExternIocsrrdW(a: Int): Int

    @JvmStatic
    external fun corearchLoongarchsharedExternIocsrwrB(a: Int, b: Int)

    @JvmStatic
    external fun corearchLoongarchsharedExternIocsrwrH(a: Int, b: Int)

    @JvmStatic
    external fun corearchLoongarchsharedExternIocsrwrW(a: Int, b: Int)

    @JvmStatic
    external fun corearchLoongarchsharedBreakTarget(a: Int)

    @JvmStatic
    external fun corearchLoongarchsharedExternCpucfg(a: Int): Int

    @JvmStatic
    external fun corearchLoongarchsharedExternSyscall(a: Int)

    @JvmStatic
    external fun corearchLoongarchsharedExternFrecipeS(a: Float): Float

    @JvmStatic
    external fun corearchLoongarchsharedExternFrecipeD(a: Double): Double

    @JvmStatic
    external fun corearchLoongarchsharedExternFrsqrteS(a: Float): Float

    @JvmStatic
    external fun corearchLoongarchsharedExternFrsqrteD(a: Double): Double
}

public actual fun externDbar(a: Int) {
    LoongarchsharedJniBridge.corearchLoongarchsharedExternDbar(a)
}

public actual fun externIbar(a: Int) {
    LoongarchsharedJniBridge.corearchLoongarchsharedExternIbar(a)
}

public actual fun externMovgr2fcsr(a: Int, b: Int) {
    LoongarchsharedJniBridge.corearchLoongarchsharedExternMovgr2fcsr(a, b)
}

public actual fun externMovfcsr2gr(a: Int): Int =
    LoongarchsharedJniBridge.corearchLoongarchsharedExternMovfcsr2gr(a)

public actual fun externIocsrrdB(a: Int): Int =
    LoongarchsharedJniBridge.corearchLoongarchsharedExternIocsrrdB(a)

public actual fun externIocsrrdH(a: Int): Int =
    LoongarchsharedJniBridge.corearchLoongarchsharedExternIocsrrdH(a)

public actual fun externIocsrrdW(a: Int): Int =
    LoongarchsharedJniBridge.corearchLoongarchsharedExternIocsrrdW(a)

public actual fun externIocsrwrB(a: Int, b: Int) {
    LoongarchsharedJniBridge.corearchLoongarchsharedExternIocsrwrB(a, b)
}

public actual fun externIocsrwrH(a: Int, b: Int) {
    LoongarchsharedJniBridge.corearchLoongarchsharedExternIocsrwrH(a, b)
}

public actual fun externIocsrwrW(a: Int, b: Int) {
    LoongarchsharedJniBridge.corearchLoongarchsharedExternIocsrwrW(a, b)
}

public actual fun breakTarget(a: Int) {
    LoongarchsharedJniBridge.corearchLoongarchsharedBreakTarget(a)
}

public actual fun externCpucfg(a: Int): Int =
    LoongarchsharedJniBridge.corearchLoongarchsharedExternCpucfg(a)

public actual fun externSyscall(a: Int) {
    LoongarchsharedJniBridge.corearchLoongarchsharedExternSyscall(a)
}

public actual fun externFrecipeS(a: Float): Float =
    LoongarchsharedJniBridge.corearchLoongarchsharedExternFrecipeS(a)

public actual fun externFrecipeD(a: Double): Double =
    LoongarchsharedJniBridge.corearchLoongarchsharedExternFrecipeD(a)

public actual fun externFrsqrteS(a: Float): Float =
    LoongarchsharedJniBridge.corearchLoongarchsharedExternFrsqrteS(a)

public actual fun externFrsqrteD(a: Double): Double =
    LoongarchsharedJniBridge.corearchLoongarchsharedExternFrsqrteD(a)
