@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object X8664XsaveJniBridge {
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
        val stream = X8664XsaveJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun corearchX8664XsaveExternXsave64(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX8664XsaveExternXrstor64(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX8664XsaveExternXsaveopt64(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX8664XsaveExternXsavec64(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX8664XsaveExternXsaves64(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX8664XsaveExternXrstors64(p: Long, hi: Int, lo: Int)
}

public actual fun externXsave64(p: MutPtr, hi: UInt, lo: UInt) {
    X8664XsaveJniBridge.corearchX8664XsaveExternXsave64(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun externXrstor64(p: ConstPtr, hi: UInt, lo: UInt) {
    X8664XsaveJniBridge.corearchX8664XsaveExternXrstor64(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun externXsaveopt64(p: MutPtr, hi: UInt, lo: UInt) {
    X8664XsaveJniBridge.corearchX8664XsaveExternXsaveopt64(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun externXsavec64(p: MutPtr, hi: UInt, lo: UInt) {
    X8664XsaveJniBridge.corearchX8664XsaveExternXsavec64(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun externXsaves64(p: MutPtr, hi: UInt, lo: UInt) {
    X8664XsaveJniBridge.corearchX8664XsaveExternXsaves64(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun externXrstors64(p: ConstPtr, hi: UInt, lo: UInt) {
    X8664XsaveJniBridge.corearchX8664XsaveExternXrstors64(p.rawAddress, hi.toInt(), lo.toInt())
}
