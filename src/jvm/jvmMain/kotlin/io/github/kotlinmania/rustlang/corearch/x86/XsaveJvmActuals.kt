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
            val extracted =
                java.nio.file.Files
                    .createTempFile("kmcore_jni-", suffix)
                    .toFile()
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
    external fun corearchX86XsaveXsave(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX86XsaveXrstor(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX86XsaveXsetbv(v: Int, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX86XsaveXgetbv(v: Int): Long

    @JvmStatic
    external fun corearchX86XsaveXsaveopt(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX86XsaveXsavec(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX86XsaveXsaves(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX86XsaveXrstors(p: Long, hi: Int, lo: Int)
}

public actual fun xsave(p: MutPtr, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveXsave(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun xrstor(p: ConstPtr, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveXrstor(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun xsetbv(v: UInt, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveXsetbv(v.toInt(), hi.toInt(), lo.toInt())
}

public actual fun xgetbv(v: UInt): Long =
    X86XsaveJniBridge.corearchX86XsaveXgetbv(v.toInt())

public actual fun xsaveopt(p: MutPtr, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveXsaveopt(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun xsavec(p: MutPtr, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveXsavec(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun xsaves(p: MutPtr, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveXsaves(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun xrstors(p: ConstPtr, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveXrstors(p.rawAddress, hi.toInt(), lo.toInt())
}
