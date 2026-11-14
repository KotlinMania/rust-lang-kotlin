@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.ffi

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object FfiJniBridge {
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
                "windows" -> "core_jni.dll"
                "macos" -> "libcore_jni.dylib"
                else -> "libcore_jni.so"
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
        val resourcePath = "io/github/kotlinmania/rustlang/core/native/$hostPlatform-$hostArch/$libFileName"
        val stream = FfiJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
        if (stream != null) {
            val suffix = "." + libFileName.substringAfterLast('.')
            val extracted = java.nio.file.Files.createTempFile("core_jni-", suffix).toFile()
            extracted.deleteOnExit()
            stream.use { input ->
                extracted.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            System.load(extracted.absolutePath)
            return
        }
        System.loadLibrary("core_jni")
    }

    @JvmStatic
    external fun coreFfiMemcpy(dest: Long, src: Long, n: Long): Long

    @JvmStatic
    external fun coreFfiMemmove(dest: Long, src: Long, n: Long): Long

    @JvmStatic
    external fun coreFfiMemset(s: Long, c: Int, n: Long): Long

    @JvmStatic
    external fun coreFfiMemcmp(s1: Long, s2: Long, n: Long): Int

    @JvmStatic
    external fun coreFfiBcmp(s1: Long, s2: Long, n: Long): Int

    @JvmStatic
    external fun coreFfiStrlen(s: Long): Long
}

public actual fun memcpy(dest: MutPtr, src: ConstPtr, n: ULong): MutPtr =
    MutPtr(FfiJniBridge.coreFfiMemcpy(dest.rawAddress, src.rawAddress, n.toLong()))

public actual fun memmove(dest: MutPtr, src: ConstPtr, n: ULong): MutPtr =
    MutPtr(FfiJniBridge.coreFfiMemmove(dest.rawAddress, src.rawAddress, n.toLong()))

public actual fun memset(s: MutPtr, c: CInt, n: ULong): MutPtr =
    MutPtr(FfiJniBridge.coreFfiMemset(s.rawAddress, c, n.toLong()))

public actual fun memcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt =
    FfiJniBridge.coreFfiMemcmp(s1.rawAddress, s2.rawAddress, n.toLong())

public actual fun bcmp(s1: ConstPtr, s2: ConstPtr, n: ULong): CInt =
    FfiJniBridge.coreFfiBcmp(s1.rawAddress, s2.rawAddress, n.toLong())

public actual fun strlen(s: ConstPtr): ULong =
    FfiJniBridge.coreFfiStrlen(s.rawAddress).toULong()
