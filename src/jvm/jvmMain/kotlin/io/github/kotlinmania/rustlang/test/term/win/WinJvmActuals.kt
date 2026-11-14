@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.test.term.win

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object TermWinJniBridge {
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
                "windows" -> "test_jni.dll"
                "macos" -> "libtest_jni.dylib"
                else -> "libtest_jni.so"
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
        val resourcePath = "io/github/kotlinmania/rustlang/test/native/$hostPlatform-$hostArch/$libFileName"
        val stream = TermWinJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
        if (stream != null) {
            val suffix = "." + libFileName.substringAfterLast('.')
            val extracted = java.nio.file.Files.createTempFile("test_jni-", suffix).toFile()
            extracted.deleteOnExit()
            stream.use { input ->
                extracted.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            System.load(extracted.absolutePath)
            return
        }
        System.loadLibrary("test_jni")
    }

    @JvmStatic
    external fun testTermWinSetConsoleTextAttribute(handle: Long, attr: Short): Int

    @JvmStatic
    external fun testTermWinGetStdHandle(which: Int): Long

    @JvmStatic
    external fun testTermWinGetConsoleScreenBufferInfo(handle: Long, info: Long): Int
}

public actual fun setConsoleTextAttribute(handle: Handle, attr: Word): Bool =
    TermWinJniBridge.testTermWinSetConsoleTextAttribute(handle.rawAddress, attr.toShort())

public actual fun getStdHandle(which: Dword): Handle =
    MutPtr(TermWinJniBridge.testTermWinGetStdHandle(which.toInt()))

public actual fun getConsoleScreenBufferInfo(handle: Handle, info: MutPtr): Bool =
    TermWinJniBridge.testTermWinGetConsoleScreenBufferInfo(handle.rawAddress, info.rawAddress)
