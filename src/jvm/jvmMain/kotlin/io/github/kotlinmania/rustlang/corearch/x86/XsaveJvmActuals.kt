@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object X86XsaveJniBridge {
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
        val stream = X86XsaveJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun corearchX86XsaveExternXsave(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX86XsaveExternXrstor(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX86XsaveExternXsetbv(v: Int, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX86XsaveExternXgetbv(v: Int): Long

    @JvmStatic
    external fun corearchX86XsaveExternXsaveopt(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX86XsaveExternXsavec(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX86XsaveExternXsaves(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX86XsaveExternXrstors(p: Long, hi: Int, lo: Int)
}

public actual fun externXsave(p: MutPtr, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveExternXsave(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun externXrstor(p: ConstPtr, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveExternXrstor(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun externXsetbv(v: UInt, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveExternXsetbv(v.toInt(), hi.toInt(), lo.toInt())
}

public actual fun externXgetbv(v: UInt): Long =
    X86XsaveJniBridge.corearchX86XsaveExternXgetbv(v.toInt())

public actual fun externXsaveopt(p: MutPtr, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveExternXsaveopt(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun externXsavec(p: MutPtr, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveExternXsavec(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun externXsaves(p: MutPtr, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveExternXsaves(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun externXrstors(p: ConstPtr, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveExternXrstors(p.rawAddress, hi.toInt(), lo.toInt())
}
