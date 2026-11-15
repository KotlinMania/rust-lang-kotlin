@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.glibc.io.sys.statvfs

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*

private object NewGlibcIoSysStatvfsJniBridge {
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
        val resourcePath = "io/github/kotlinmania/rustlang/libc/native/$hostPlatform-$hostArch/$libFileName"
        val stream = NewGlibcIoSysStatvfsJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun libcNewGlibcIoSysStatvfsStatvfs(path: Long, buf: Long): Int

    @JvmStatic
    external fun libcNewGlibcIoSysStatvfsFstatvfs(fd: Int, buf: Long): Int

    @JvmStatic
    external fun libcNewGlibcIoSysStatvfsStatvfs64(path: Long, buf: Long): Int

    @JvmStatic
    external fun libcNewGlibcIoSysStatvfsFstatvfs64(fd: Int, buf: Long): Int
}

public actual fun statvfs(path: ConstPtr, buf: MutPtr): CInt =
    NewGlibcIoSysStatvfsJniBridge.libcNewGlibcIoSysStatvfsStatvfs(path.rawAddress, buf.rawAddress)

public actual fun fstatvfs(fd: CInt, buf: MutPtr): CInt =
    NewGlibcIoSysStatvfsJniBridge.libcNewGlibcIoSysStatvfsFstatvfs(fd, buf.rawAddress)

public actual fun statvfs64(path: ConstPtr, buf: MutPtr): CInt =
    NewGlibcIoSysStatvfsJniBridge.libcNewGlibcIoSysStatvfsStatvfs64(path.rawAddress, buf.rawAddress)

public actual fun fstatvfs64(fd: CInt, buf: MutPtr): CInt =
    NewGlibcIoSysStatvfsJniBridge.libcNewGlibcIoSysStatvfsFstatvfs64(fd, buf.rawAddress)
