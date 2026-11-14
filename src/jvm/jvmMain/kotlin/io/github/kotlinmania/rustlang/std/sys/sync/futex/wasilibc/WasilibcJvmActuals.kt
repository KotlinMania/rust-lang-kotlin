@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.sync.futex.wasilibc

import io.github.kotlinmania.libc.CInt
import io.github.kotlinmania.libc.CUint
import io.github.kotlinmania.libc.fuchsia.ClockidT
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object SysSyncFutexWasilibcJniBridge {
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
                "windows" -> "std_jni.dll"
                "macos" -> "libstd_jni.dylib"
                else -> "libstd_jni.so"
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
        val resourcePath = "io/github/kotlinmania/rustlang/std/native/$hostPlatform-$hostArch/$libFileName"
        val stream = SysSyncFutexWasilibcJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
        if (stream != null) {
            val suffix = "." + libFileName.substringAfterLast('.')
            val extracted = java.nio.file.Files.createTempFile("std_jni-", suffix).toFile()
            extracted.deleteOnExit()
            stream.use { input ->
                extracted.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            System.load(extracted.absolutePath)
            return
        }
        System.loadLibrary("std_jni")
    }

    @JvmStatic
    external fun stdSysSyncFutexWasilibcWasilibcFutexWait(addr: Long, value: Int, clock: Int, at: Long, flags: Int): Int

    @JvmStatic
    external fun stdSysSyncFutexWasilibcWasilibcFutexWake(addr: Long, count: Int, flags: Int): Int
}

public actual fun wasilibcFutexWait(addr: MutPtr, value: CInt, clock: ClockidT, at: ConstPtr, flags: CUint): CInt =
    SysSyncFutexWasilibcJniBridge.stdSysSyncFutexWasilibcWasilibcFutexWait(addr.rawAddress, value, clock, at.rawAddress, flags.toInt())

public actual fun wasilibcFutexWake(addr: ConstPtr, count: CInt, flags: CUint): CInt =
    SysSyncFutexWasilibcJniBridge.stdSysSyncFutexWasilibcWasilibcFutexWake(addr.rawAddress, count, flags.toInt())
