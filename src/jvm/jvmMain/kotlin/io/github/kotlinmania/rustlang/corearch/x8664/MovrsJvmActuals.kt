@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

private object X8664MovrsJniBridge {
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
                "windows" -> "corearch_jni.dll"
                "macos" -> "libcorearch_jni.dylib"
                else -> "libcorearch_jni.so"
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
        val stream = X8664MovrsJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
        if (stream != null) {
            val suffix = "." + libFileName.substringAfterLast('.')
            val extracted = java.nio.file.Files.createTempFile("corearch_jni-", suffix).toFile()
            extracted.deleteOnExit()
            stream.use { input ->
                extracted.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            System.load(extracted.absolutePath)
            return
        }
        System.loadLibrary("corearch_jni")
    }

    @JvmStatic
    external fun corearchX8664MovrsMovrsqi(src: Long): Byte

    @JvmStatic
    external fun corearchX8664MovrsMovrshi(src: Long): Short

    @JvmStatic
    external fun corearchX8664MovrsMovrssi(src: Long): Int

    @JvmStatic
    external fun corearchX8664MovrsMovrsdi(src: Long): Long
}

public actual fun movrsqi(src: ConstPtr): Byte =
    X8664MovrsJniBridge.corearchX8664MovrsMovrsqi(src.rawAddress)

public actual fun movrshi(src: ConstPtr): Short =
    X8664MovrsJniBridge.corearchX8664MovrsMovrshi(src.rawAddress)

public actual fun movrssi(src: ConstPtr): Int =
    X8664MovrsJniBridge.corearchX8664MovrsMovrssi(src.rawAddress)

public actual fun movrsdi(src: ConstPtr): Long =
    X8664MovrsJniBridge.corearchX8664MovrsMovrsdi(src.rawAddress)
