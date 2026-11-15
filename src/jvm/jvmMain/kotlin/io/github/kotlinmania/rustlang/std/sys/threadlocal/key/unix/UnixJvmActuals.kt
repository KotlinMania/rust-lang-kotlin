@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.threadlocal.key.unix

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.ffi.CInt

private object StdSysThreadlocalKeyUnixJniBridge {
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
        val stream = StdSysThreadlocalKeyUnixJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun rustlangStdSysThreadlocalKeyUnixPthreadKeyCreate(key: Long, destructor: Long): Int

    @JvmStatic
    external fun rustlangStdSysThreadlocalKeyUnixPthreadGetspecific(key: Int): Long

    @JvmStatic
    external fun rustlangStdSysThreadlocalKeyUnixPthreadSetspecific(key: Int, value: Long): Int

    @JvmStatic
    external fun rustlangStdSysThreadlocalKeyUnixPthreadKeyDelete(key: Int): Int
}

public actual fun pthreadKeyCreate(key: MutPtr, destructor: PthreadKeyCreateDestructorCallback): CInt =
    StdSysThreadlocalKeyUnixJniBridge.rustlangStdSysThreadlocalKeyUnixPthreadKeyCreate(key.rawAddress, 0L)

public actual fun pthreadGetspecific(key: PthreadKeyT): MutPtr =
    MutPtr(StdSysThreadlocalKeyUnixJniBridge.rustlangStdSysThreadlocalKeyUnixPthreadGetspecific(key.toInt()))

public actual fun pthreadSetspecific(key: PthreadKeyT, value: ConstPtr): CInt =
    StdSysThreadlocalKeyUnixJniBridge.rustlangStdSysThreadlocalKeyUnixPthreadSetspecific(key.toInt(), value.rawAddress)

public actual fun pthreadKeyDelete(key: PthreadKeyT): CInt =
    StdSysThreadlocalKeyUnixJniBridge.rustlangStdSysThreadlocalKeyUnixPthreadKeyDelete(key.toInt())
